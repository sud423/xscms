package com.susd.controllers;

import com.susd.application.DictService;
import com.susd.domain.complex.Dict;
import com.susd.domain.complex.DictRepository;
import com.susd.domainservice.identity.SessionManager;
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

@RequestMapping(value="express")
@Controller
public class ExpressController {

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private DictService dictService;

    /**
     * 首页/列表页
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "express/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public DatatableResult<Dict> query(HttpServletRequest request, DatatableParam param, String keyword) {

        DatatableResult<Dict> result = dictService.findByKeyword(keyword,"express" ,param);

        return result;
    }

    /**
     * 编辑页面
     * @param map
     * @param id
     * @return
     */
    @RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
    public String edit(Map<String, Object> map, @PathVariable(name = "id", required = false) Integer id) {
        if (id != null && id > 0) {
            Dict course = dictRepository.findDictById(id);
            map.put("model", course);
        }

        return "express/edit";
    }

    /**
     * 保存
     * @param dict
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public OptResult save(Dict dict) {
        return dictService.save(dict);
    }

    /**
     * 删除
     * @param dictId
     * @return
     */
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    @ResponseBody
    public OptResult delete(int dictId) {
        return dictService.delete(dictId);
    }
}
