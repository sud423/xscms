package com.susd.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.susd.application.AnnouncementService;
import com.susd.domain.site.Announcement;
import com.susd.domain.site.AnnouncementRepository;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@RequestMapping(value = "/activity")
@Controller
public class ActivityController {
	
	@Autowired
	private AnnouncementService announcementService;
	@Autowired
	private AnnouncementRepository announcementRepository;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		
		return "activity/index";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<Announcement> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<Announcement> result = announcementService.findByKeyword(keyword, param);

		return result;
	}
	
	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map,
			@PathVariable(name = "id", required = false) Integer id) {
				if (id!=null&& id > 0) {
					Announcement ann=announcementRepository.findAnnouncementById(id);
					map.put("model",ann);
		}
		return "activity/edit";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(Announcement ann) {
		return announcementService.save(ann);
	}
	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public OptResult delete (int activityId) {
		return announcementService.delete(activityId);
	}
}
