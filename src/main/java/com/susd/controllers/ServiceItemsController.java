package com.susd.controllers;

import com.susd.application.ServiceItemsService;
import com.susd.domain.law.ServiceItems;
import com.susd.domain.law.ServiceItemsRepository;
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

@Controller
@RequestMapping(value = "/serviceitems", method = RequestMethod.GET)
public class ServiceItemsController {
    @Autowired
    private ServiceItemsService serviceItemsService;

    @Autowired
    private ServiceItemsRepository serviceItemsRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "lawyer/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DatatableResult<ServiceItems> query(HttpServletRequest request, DatatableParam param, String keyword) {

        DatatableResult<ServiceItems> result = serviceItemsService.queryByKeyword(keyword, param);

        return result;
    }
    @RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
    public String edit(Map<String, Object> map, @PathVariable(name = "id", required = false) Integer id) {
        if (id!=null&& id > 0) {
            ServiceItems items=serviceItemsRepository.findById(id);
            map.put("model",items);
        }
        return "news/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public OptResult save(ServiceItems lawyer) {
        return serviceItemsService.save(lawyer);
    }
}
