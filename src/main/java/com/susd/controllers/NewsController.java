package com.susd.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.susd.application.NewsService;
import com.susd.domain.site.News;
import com.susd.domain.site.NewsRepository;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@RequestMapping(value = "/news")
@Controller
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsRepository newsRepository;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		
		return "news/index";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<News> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<News> result = newsService.findByKeyword(keyword, param);

		return result;
	}
	
	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map,
			@PathVariable(name = "id", required = false) Integer id) {
				if (id!=null&& id > 0) {
					News news=newsRepository.findNewsById(id);
					map.put("model",news);
		}
		return "news/edit";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(News news) {
		return newsService.save(news);
	}
	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public OptResult delete (int newsId) {
		return newsService.delete(newsId);
	}
}
