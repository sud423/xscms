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
import com.susd.infrastructure.Utils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    // 文件目录名称
    private String fileDir;

    // 文件后缀名称
    private String fileExt;

    // 站点真实路径
    @Value("${relPath}")
    private String relPath;

    // 上传文件保存路径
    private String savePath;

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
    public OptResult save(@RequestParam(value = "docFile", required = false) MultipartFile file, News news)  throws IOException {
        if (file.isEmpty()) {
            return OptResult.Failed("请选择要上传的文件");
        }
        fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        savePath=Utils.createSavePath(relPath,fileExt,"doc");

        //Utils.copy(file.getInputStream(),savePath);


        return newsService.save(news,file.getName(),fileExt,savePath,file.getInputStream());
    }

}
