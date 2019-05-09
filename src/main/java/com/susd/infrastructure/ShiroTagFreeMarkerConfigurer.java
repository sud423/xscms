package com.susd.infrastructure;

import java.io.IOException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.TemplateException;

public class ShiroTagFreeMarkerConfigurer extends FreeMarkerConfigurer {
	@Override
	public void afterPropertiesSet() throws IOException, TemplateException {
		super.afterPropertiesSet();
		ShiroTags tags = new ShiroTags();
        tags.put("hasAnyPermissions", new HasAnyPermissionsTag());
		this.getConfiguration().setSharedVariable("shiro", tags);
	}
}
