package com.susd.controllers;

import com.susd.domain.law.Lawyer;
import com.susd.application.LawyerService;
import com.susd.domain.law.LawyerRepository;
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
@RequestMapping(value = "/lawyer", method = RequestMethod.GET)
public class LawyerController {

    @Autowired
    private LawyerService lawyerService;

    @Autowired
    private LawyerRepository lawyerRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "lawyer/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DatatableResult<Lawyer> query(HttpServletRequest request, DatatableParam param, String keyword) {

        DatatableResult<Lawyer> result = lawyerService.queryByKeyword(keyword, param);

        return result;
    }
    @RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
    public String edit(Map<String, Object> map, @PathVariable(name = "id", required = false) Integer id) {
        if (id!=null&& id > 0) {
            Lawyer lawyer=lawyerRepository.findById(id);
            map.put("model",lawyer);
        }
        return "lawyer/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public OptResult save(Lawyer lawyer) {
        return lawyerService.save(lawyer);
    }

}
