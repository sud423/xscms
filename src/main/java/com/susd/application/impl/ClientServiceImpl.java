package com.susd.application.impl;

import java.text.ParseException;
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.ClientService;
import com.susd.domain.site.User;
import com.susd.domain.site.UserRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.dto.ClientDto;
import com.susd.dto.SelectDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;


@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public OptResult save(User user) {
		if (Validate.isValid(user)) {

			int res = 0;
			if (user.getId() == 0) {
				
				user.setVersion(1);
				user.setAddTime(new Date());
				user.setType((byte) 10);
				user.setTenantId(SessionManager.getTenantId());
				res = userRepository.add(user);
			} else {
				User old = userRepository.findUserById(user.getId());
				if (old.getVersion() != user.getVersion()) {
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				}
				user.setStatus(old.getStatus());
				res = userRepository.edit(user);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(user);
	}

	@Override
	public DatatableResult<ClientDto> queryByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<User> users = userRepository.findClientByKeyword(keyword,SessionManager.getTenantId());

		PageInfo<User> pagecInfo = new PageInfo<User>(users);
		
		
		
		ModelMapper modelMapper = new ModelMapper();
		
		
		//针对内部list的转换
        Converter<ArrayList<User>,ArrayList<ClientDto>> converter = new AbstractConverter<ArrayList<User>,ArrayList<ClientDto>>() {
        	@Override
            protected ArrayList<ClientDto> convert(ArrayList<User> source) {
        	PropertyMap<User, ClientDto> clientMap = new PropertyMap<User, ClientDto>() {
    			@Override
    			protected void configure() {
    				try {
    					map().setAddTime(source.getAddTime());
    					map().setNickName(source.getUserLogin().getNickName());
    				} catch (ParseException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    		};
    		modelMapper.addMappings(clientMap);
            return modelMapper.map(source,new TypeToken<ArrayList<ClientDto>>(){}.getType());
        	}
        };
        PropertyMap<PageInfo<User>,PageInfo<ClientDto>> propertyMap = new PropertyMap<PageInfo<User>, PageInfo<ClientDto>>() {
            @Override
            protected void configure() {
                using(converter).map(source.getList(),destination.getList());
            }
        };
		
		modelMapper.addMappings(propertyMap);
		
		PageInfo<ClientDto> pageInfo = modelMapper.map(pagecInfo, new TypeToken<PageInfo<ClientDto>>() {}.getType());
		

		return new DatatableResult<ClientDto>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult audit(int userId, byte status, String reason) {
		User old = userRepository.findUserById(userId);

		if (old == null || old.getId() == 0)
			return OptResult.Failed("待审核的账户不存在");

		old.audit(status, reason);
		
		int res=userRepository.edit(old);
		if(res>0)
			return OptResult.Successed();
		return OptResult.Failed("数据提交失败，请稍候再试");
	}

	/**
	 * 获取客户
	 * @param q
	 * @return
	 */
	public List<SelectDto> findClient(String q){
		List<User> users = userRepository.findClient(q,SessionManager.getTenantId());

		PropertyMap<User, SelectDto> userMap = new PropertyMap<User, SelectDto>() {
			@Override
			protected void configure() {
				map().setText(source.getName());
			}
		};
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(userMap);

		List<SelectDto> dtos = modelMapper.map(users, new TypeToken<List<SelectDto>>() {
		}.getType());

		return dtos;
	}
}
