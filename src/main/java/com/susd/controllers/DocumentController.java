package com.susd.controllers;

import com.alibaba.fastjson.JSONObject;
import com.susd.application.NewCategoriesService;
import com.susd.application.NewsService;
import com.susd.domain.site.News;
import com.susd.domain.site.NewsRepository;
import com.susd.dto.TreeDto;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/document",method= RequestMethod.GET)
public class DocumentController {

    @Autowired
    private NewCategoriesService newCategoriesService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    @RequestMapping(value = "/index",method= RequestMethod.GET)
    public String index(){
        return  "document/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DatatableResult<News> query(HttpServletRequest request, DatatableParam param, String keyword) {

        DatatableResult<News> result = newsService.findByKeyword(keyword, param);

        return result;
    }
    @RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
    public String edit(Map<String, Object> map, @PathVariable(name = "id", required = false) Integer id) {

        if (id != null && id > 0) {
            News news = newsRepository.findNewsById(id);
            map.put("model", news);
        }

        List<TreeDto> dataSource = newCategoriesService.queryToDropDataSrource();
        String json = JSONObject.toJSONString(dataSource);

        map.put("datasource", json);

        return "document/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public OptResult save(News news) {
        return newsService.save(news);
    }
}
