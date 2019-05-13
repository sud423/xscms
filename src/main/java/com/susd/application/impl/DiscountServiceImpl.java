package com.susd.application.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.DiscountService;
import com.susd.domain.activities.Discount;
import com.susd.domain.activities.DiscountRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public DatatableResult<Discount> findByKeyword(String keyword, DatatableParam param) {
        PageHelper.startPage(param.getPageIndex(), param.getLength(), true);

        List<Discount> discounts = discountRepository.findByKeyword(keyword, SessionManager.getTenantId());

        PageInfo<Discount> results = new PageInfo<>(discounts);

        return new DatatableResult<>(results, param.getDraw());
    }

    @Override
    public OptResult save(Discount discount) {
        if (Validate.isValid(discount)) {

            int res = 0;
            if (discount.getId() == 0) {

                discount.setAddTime(new Date());
                discount.setTenantId(SessionManager.getTenantId());// 所属租户编号
                discount.setUserId(SessionManager.getUserId());// 维护人编号
                res = discountRepository.add(discount);
            } else {
                Discount old = discountRepository.findById(discount.getId());
                if (old.getVersion() != discount.getVersion()) {
                    return OptResult.Failed("信息已修改过，请刷新后重新修改");
                }
                res = discountRepository.update(discount);
            }
            if (res > 0)
                return OptResult.Successed();
            return OptResult.Failed("信息保存失败，请稍候重试");
        }

        return Validate.verify(discount);
    }
}
