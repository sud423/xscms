package com.susd.controllers;

import com.susd.application.NewCategoriesService;
import com.susd.domain.site.NewCategories;
import com.susd.domain.site.NewCategoriesRepository;
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
@RequestMapping(value = "/newcategories",method= RequestMethod.GET)
public class NewCategoriesController {

    @Autowired
    private NewCategoriesService newCategoriesService;

    @Autowired
    private NewCategoriesRepository newCategoriesRepository;

    @RequestMapping(value = "/index",method= RequestMethod.GET)
    public String index(){
        return  "newcategories/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DatatableResult<NewCategories> query(HttpServletRequest request, DatatableParam param, String keyword) {

        DatatableResult<NewCategories> result = newCategoriesService.queryByKeyword(keyword, param);

        return result;
    }
    @RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
    public String edit(Map<String, Object> map, @PathVariable(name = "id", required = false) Integer id) {
        if (id!=null&& id > 0) {
            NewCategories items=newCategoriesRepository.findById(id);
            map.put("model",items);
        }
        return "newcategories/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public OptResult save(NewCategories category) {
        return newCategoriesService.save(category);
    }
}
