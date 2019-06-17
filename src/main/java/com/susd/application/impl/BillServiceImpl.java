package com.susd.application.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.BillService;
import com.susd.domain.bill.Bill;
import com.susd.domain.bill.BillItem;
import com.susd.domain.bill.BillItemPackage;
import com.susd.domain.bill.BillItemRepository;
import com.susd.domain.bill.BillRepository;
import com.susd.domain.complex.PriceConfig;
import com.susd.domain.complex.PriceConfigRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.dto.BillDto;
import com.susd.dto.BillItemInputDto;
import com.susd.dto.BillItemOutDto;
import com.susd.dto.PackageDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Utils;
import com.susd.infrastructure.Validate;


@Service
public class BillServiceImpl implements BillService {
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private BillItemRepository billItemRepository;
	@Autowired
	private PriceConfigRepository priceConfigRepository;

	@Override
	public DatatableResult<BillDto> queryByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);

		List<Bill> bills = billRepository.findBillByKeyword(keyword, SessionManager.getTenantId(),
				SessionManager.getUserId());
		PageInfo<Bill> billPageInfo=new PageInfo<>(bills);
		ModelMapper modelMapper = new ModelMapper();
		Converter<ArrayList<Bill>, ArrayList<BillDto>> converter = new AbstractConverter<ArrayList<Bill>, ArrayList<BillDto>>() {

			@Override
			protected ArrayList<BillDto> convert(ArrayList<Bill> source) {

				PropertyMap<Bill, BillDto> billMap = new PropertyMap<Bill, BillDto>() {
					@Override
					protected void configure() {
						map().setClientName(source.getUser().getName());
						map().setUserId(source.getUserId());
					}
				};
				modelMapper.addMappings(billMap);
				return modelMapper.map(source, new TypeToken<ArrayList<BillDto>>() {
				}.getType());
			}
		};
		PropertyMap<PageInfo<Bill>, PageInfo<BillDto>> propertyMap = new PropertyMap<PageInfo<Bill>, PageInfo<BillDto>>() {
			@Override
			protected void configure() {
				using(converter).map(source.getList(), destination.getList());
			}
		};
		modelMapper.addMappings(propertyMap);
		
		PageInfo<BillDto> pageInfo = modelMapper.map(billPageInfo, new TypeToken<PageInfo<BillDto>>() {
		}.getType());


		return new DatatableResult<BillDto>(pageInfo, param.getDraw());
	}

	@Override
	public BillItemInputDto countFee(BillItemInputDto input) {
		PriceConfig config = priceConfigRepository.findPrice(input.getProvince(), input.getCity(), input.getExpress(),
				SessionManager.getTenantId(),input.getType());

		if (config == null || config.getId() == 0) {
			input.setVolumeWeight(0);
			input.setWeight(0);
			input.setTotalPrice(0);
			return input;
		}
		float volume = input.calculateTotalVolume();// 计算总体积

		BigDecimal b = new BigDecimal(volume / config.getCoefficient());
		float volumeWeight = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

		input.setVolumeWeight(volumeWeight);

		// 计算最后结算重量
		float weight = input.getActualWeight() > volumeWeight ? input.getActualWeight() : volumeWeight;
		input.setWeight(weight);

		//最低收费
		if(input.getType()==10){

			// 计算价格
			float price = weight * config.getStandardPrice();
			// 与最低价格比较
			float maxPrice = price > config.getLowestPrice() ? price : config.getLowestPrice();
			BigDecimal p = new BigDecimal(maxPrice + input.getCost() + config.getAddFees());
			float totalPrice = p.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

			input.setTotalPrice(totalPrice);// 加上其它费用和额外费用
		}
		else{//首重+续重

			float firstW=input.getWeight()-config.getFirstWeight();
			if(firstW>0){
				float firstTotalPrice=config.getFirstPrice()*config.getFirstWeight();
				float continuedTotalPrice=firstW/config.getContinuedWeight()* config.getContinuedPrice();
				float totalPrice=firstTotalPrice+continuedTotalPrice;
				BigDecimal p = new BigDecimal(totalPrice+input.getCost());
				input.setTotalPrice(p.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue());// 加上其它费用和额外费用
			}
			else{
				BigDecimal m = new BigDecimal(config.getFirstPrice()+input.getCost());
				input.setTotalPrice(m.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue());
			}
		}

		return input;

	}

	@Override
	@Transactional
	public OptResult save(BillItemInputDto input) {
		input = countFee(input);// 重新计算运费
		if (Validate.isValid(input)) {

			float totalPrice = input.getTotalPrice();

			if (input.getItemId() != 0) {
				BillItem item = billItemRepository.findById(input.getItemId());
				if (item != null && item.getId() != 0)
					totalPrice = input.getTotalPrice() - item.getPrice();
			}

			OptResult res = createBill(input.getUserId(), input.getBillId(), totalPrice, input.getVersion(),input.getCost(),input.getType());
			if (res.getCode() != 0)
				return res;

			int billId = (int) res.getResult();

			PropertyMap<BillItemInputDto, BillItem> billItemMap = new PropertyMap<BillItemInputDto, BillItem>() {
				@Override
				protected void configure() {
					map().setId(source.getItemId());
					map().setBillId(source.getBillId());
					map().setUserId(source.getUserId());
					map().setPrice(source.getTotalPrice());
					map().setCount(source.getTotalCount());

				}
			};

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.addMappings(billItemMap);

			int result = 0;
			BillItem item = modelMapper.map(input, BillItem.class);

			if (item.getId() == 0) {
				item.setInit();
				item.setBillId(billId);
				result = billItemRepository.save(item);
			} else {
				result = billItemRepository.update(item);
			}

			if (result == 0)
				return OptResult.Failed("账单创建失败，请稍候重试");

			PropertyMap<PackageDto, BillItemPackage> packageMap = new PropertyMap<PackageDto, BillItemPackage>() {
				@Override
				protected void configure() {

				}
			};

			ModelMapper packModelMapper = new ModelMapper();
			packModelMapper.addMappings(packageMap);

			List<BillItemPackage> packages = packModelMapper.map(input.getPackageDtos(),
					new TypeToken<List<BillItemPackage>>() {
					}.getType());
			billItemRepository.deletePack(item.getId());
			result = billItemRepository.savePack(item.getId(), packages);

			if (result == 0)
				return OptResult.Failed("账单创建失败，请稍候重试");

			return OptResult.Successed();
		}

		return Validate.verify(input);
	}

	/**
	 * 创建总账单，如果 账单已创建则更新总金额
	 * 
	 * @param clientId
	 * @param totalPrice
	 * @return
	 */
	private OptResult createBill(int clientId, int billId, float totalPrice, int version,float cost,byte type) {
		Bill bill = null;
		if (billId == 0)
			bill = billRepository.findByClient(clientId, Utils.getPeriod());
		else
			bill = billRepository.findById(billId);

		int result = 0;
		if (bill == null || bill.getId() == 0) {
			bill = Bill.create(clientId, totalPrice);

			result = billRepository.save(bill);
		} else {
			if (bill.getVersion() != version)
				return OptResult.Failed("账单已更新，请刷新后重新修改");
			float price = bill.getTotalPrice() + totalPrice;
			result = billRepository.updatePrice(bill.getId(), price);
		}

		if (result == 0)
			return OptResult.Failed("账单创建失败，请稍候重试");

		return OptResult.Successed(bill.getId());
	}

	@Override
	public OptResult push(int billId) {
		int result = billRepository.push(billId, SessionManager.getUserId(), new Date());
		if (result == 0)
			return OptResult.Failed("推送失败，请检查账单是否存在");
		return OptResult.Successed();
	}

	@Override
	public DatatableResult<BillItemOutDto> findBillItems(DatatableParam param, int billId) {

		List<BillItem> billItems = billItemRepository.findBillItems(billId);

		PropertyMap<BillItem, BillItemOutDto> billItemMap = new PropertyMap<BillItem, BillItemOutDto>() {
			@Override
			protected void configure() {
				map().setClientName(source.getClient().getName());
				map().setAccountName(source.getAccount().getName());

			}
		};

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(billItemMap);

		List<BillItemOutDto> dtos = modelMapper.map(billItems, new TypeToken<List<BillItemOutDto>>() {
		}.getType());

		PageInfo<BillItemOutDto> pageInfo = new PageInfo<BillItemOutDto>(dtos);
		return new DatatableResult<BillItemOutDto>(pageInfo, param.getDraw());
	}

}
