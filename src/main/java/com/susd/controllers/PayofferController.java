package com.susd.controllers;

import com.susd.application.DiscountService;
import com.susd.domain.activities.Discount;
import com.susd.domain.activities.DiscountRepository;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 设置用户支付优惠区间
 */
@Controller
@RequestMapping(value = "/payset")
public class PayofferController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountRepository discountRepository;

    @RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
    public String index(){

        return "payoffer/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DatatableResult<Discount> query(HttpServletRequest request, DatatableParam param, String keyword) {

        DatatableResult<Discount> result = discountService.findByKeyword(keyword, param);

        return result;
    }
    @RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
    public String edit(Map<String, Object> map,
                       @PathVariable(name = "id", required = false) Integer id) {
        if (id!=null&& id > 0) {
            Discount discount=discountRepository.findById(id);
            map.put("model",discount);
        }
        return "cla/edit";
    }

    /**
     * 保存数据
     *
     * @param request
     * @param discount    折扣信息
     * @return 返回保存的结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public OptResult save(HttpServletRequest request, Discount discount) {

        return discountService.save(discount);
    }
}
