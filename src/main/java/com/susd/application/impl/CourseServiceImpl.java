package com.susd.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.CourseService;
import com.susd.domain.site.Course;
import com.susd.domain.site.CourseRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class CourseServiceImpl implements CourseService  {

	@Autowired
	private CourseRepository courseRepository;
	
	@Override
	public DatatableResult<Course> findByKeyword(String keyword, DatatableParam param) {
		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		
		List<Course> courses=courseRepository.findByKeyword(keyword, SessionManager.getTenantId());
		
		PageInfo<Course> pageInfo= new PageInfo<Course>(courses);
		return new DatatableResult<>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult delete(int id) {
		Course course=courseRepository.findCourseById(id);
		if(!(course != null) || course.getId()==0) {
			return OptResult.Failed("待删除的信息不存在");
		}
		course.setStatus((byte)20);
		int result=courseRepository.update(course);
		
		if(result>0)
			return OptResult.Successed();
		
		return OptResult.Failed("删除失败，请稍候重试");
	}

	@Override
	public OptResult save(Course course) {
		if (Validate.isValid(course)) {

			int res;
			course.setStatus((byte)1);
			if (course.getId() == 0) {
				course.setUserId(SessionManager.getUserId());
				course.setAddTime(new Date());
				course.setTenantId(SessionManager.getTenantId());
				course.setVersion(1);
				res=courseRepository.add(course);
			} else {
				Course old=courseRepository.findCourseById(course.getId());
				if(old.getVersion()!=course.getVersion())
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				res=courseRepository.update(course);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(course);
	}

}
