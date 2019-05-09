package com.susd.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.susd.application.CourseService;
import com.susd.domain.site.CourseRepository;
import com.susd.domain.complex.Dict;
import com.susd.domain.complex.DictRepository;
import com.susd.domain.site.Course;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@RequestMapping(value = "/course")
@Controller
public class CourseController {

	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private DictRepository dictRepository;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {

		return "course/index";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<Course> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<Course> result = courseService.findByKeyword(keyword, param);

		return result;
	}

	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map, @PathVariable(name = "id", required = false) Integer id) {
		if (id != null && id > 0) {
			Course course = courseRepository.findCourseById(id);
			map.put("model", course);
		}
		List<Dict> academys = dictRepository.findDictByKey("academy");
		map.put("academys", academys);
		List<Dict> classifys = dictRepository.findDictByKey("classify");
		map.put("classifys", classifys);
		return "course/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(Course course) {
		return courseService.save(course);
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public OptResult delete(int courseId) {
		return courseService.delete(courseId);
	}

}
