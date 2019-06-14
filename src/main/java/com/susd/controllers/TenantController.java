package com.susd.controllers;

import com.susd.application.TenantService;
import com.susd.domain.identity.Tenant;
import com.susd.domain.identity.TenantRepository;
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
@RequestMapping(value = "tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TenantRepository tenantRepository;

    /**
     * 租户管理首页
     * @return
     */
    @RequestMapping(value="/index", method = RequestMethod.GET)
    public String index() {
        return "tenant/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DatatableResult<Tenant> query(HttpServletRequest request, DatatableParam param, String keyword) {

        DatatableResult<Tenant> result = tenantService.findByKeyword(keyword, param);

        return result;
    }

    @RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
    public String edit(Map<String, Object> map, @PathVariable(name = "id", required = false) Integer id) {
        if (id!=null && id > 0) {
            Tenant tenant = tenantRepository.findTenantById(id);
            map.put("model", tenant);
        }
        return "sysrole/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public OptResult save(HttpServletRequest request, Tenant tenant) {

        return tenantService.save(tenant);
    }
}
