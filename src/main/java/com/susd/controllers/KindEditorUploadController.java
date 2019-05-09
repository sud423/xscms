package com.susd.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.susd.infrastructure.Utils;

@RequestMapping(value = "/kindeditor")
@Controller
public class KindEditorUploadController {

	// 日志输出对象
	private static Log log = LogFactory.getLog(KindEditorUploadController.class);

	// 文件目录名称
	private String fileDir;

	// 文件后缀名称
	private String fileExt;
	// 当前站点上下文
//	private String pageCtx;
	// 站点真实路径
	@Value("${relPath}")
	private String relPath;
	// 上传文件保存路径
	private String savePath;
	// 允许上传文件后缀MAP数组
	private static final HashMap<String, String> extMap = new HashMap<String, String>();
	// 允许上传文件大小MAP数组
	private static final HashMap<String, Long> sizeMap = new HashMap<String, Long>();
	// 上传文件存放根目录
//	private String filePath = "D:\\eclipse-workspace\\upload";

	static {
		// 初始后缀名称MAP数组
		extMap.put("image", "gif,jpg,jpeg,png,bmp,GIF,JPG,JPEG,PNG,BMP");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,txt,zip,rar");
		// 初始文件大小MAP数组
		sizeMap.put("image", 10 * 1024 * 1024l);
		sizeMap.put("flash", 30 * 1024 * 1024 * 1024l);
		sizeMap.put("media", 30 * 1024 * 1024 * 1024l);
		sizeMap.put("file", 10 * 1024 * 1024 * 1024l);
	}

	/**
	 * @category 文件、图片上传
	 * @param imgFile
	 * @param request
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("upload")
	@ResponseBody
	public Map<String, Object> upload(@RequestParam(value = "uploadFile", required = false) MultipartFile imgFile,
			HttpServletRequest request, @RequestParam(value = "dir", required = false) String dir) throws IOException {
		// 初始相关变量
		Map<String, Object> execute = new HashMap<String, Object>();
//		ServletContext context = request.getSession().getServletContext();
//		pageCtx = context.getContextPath().concat(filePath);
//		relPath = context.getRealPath(filePath);
		fileDir = dir;
		if (null == dir || dir.isEmpty()) {
			fileDir = "file";
		}
		// 检查是否有上传文件
		if (null == imgFile) {
			execute.put("error", 1);
			execute.put("message", "请选择上传文件.");
			return execute;
		}
		// 检查上传文件保存目录是否存在或可写
		if (!isExistOrRwFolder()) {
			execute.put("error", 1);
			execute.put("message", "上传文件保存目录不存在或\n是没有写入权限,请检查.");
			return execute;
		}
		// 检查目录名称是否正确
		if (!extMap.containsKey(fileDir)) {
			execute.put("error", 1);
			execute.put("message", "目录名不正确,请检查.");
			return execute;
		}
		// 计算出文件后缀名
		// String tempFileName = FilenameUtils.getName(imgFile
		// .getOriginalFilename());
		fileExt = FilenameUtils.getExtension(imgFile.getOriginalFilename());
		// 检查上传文件类型
		if (!Arrays.<String>asList(extMap.get(fileDir).split(",")).contains(fileExt)) {
			execute.put("error", 1);
			execute.put("message", "上传文件的格式被拒绝,\n只允许" + extMap.get(fileDir) + "格式的文件.");
			return execute;
		}
		// 检查上传文件的大小
		long maxSize = sizeMap.get(fileDir);
		if (imgFile.getSize() > maxSize) {
			execute.put("error", 1);
			String size = null;
			if (maxSize < 1024) {
				size = maxSize + "B";
			}
			if (maxSize > 1024 && maxSize < 1024 * 1024) {
				size = maxSize / 1024 + "KB";
			}
			if (maxSize > 1024 * 1024) {
				size = maxSize / (1024 * 1024) + "MB";
			}
			execute.put("message", "上传文件大小超过限制,只允\n许上传小于 " + size + " 的文件.");
			return execute;
		}
		// 生成新的文件名,并按日期分类
		newSavePath();
		// 拷贝上传文件至指定存放目录
		copy(imgFile.getInputStream(), savePath);
		// 计算出文件输出路径
		int point = savePath.lastIndexOf("/") - 6;
		StringBuilder url = new StringBuilder("/Upload/");
		url.append(fileDir).append("/");
		url.append(savePath.substring(point));
		// 返回上传文件的输出路径至前端
		execute.put("error", 0);
		execute.put("url", url.toString());
		return execute;
	}

	/**
	 * 判断文件上传保存的文件夹是否存在或可写
	 * 
	 * @return 如果存在且可写返回"true",否则返回"false"
	 */
	private boolean isExistOrRwFolder() {
		if (null == relPath || relPath.isEmpty()) {
			return false;
		}
		File folder = new File(relPath);
		// 文件路径不存在则创建目录
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (!folder.isDirectory())
			return false;
		if (!folder.canWrite())
			return false;
		return true;
	}

	/**
	 * @category 生成新的文件名,且按日期分类管理
	 */
	private void newSavePath() {
		StringBuilder tempPath = new StringBuilder(relPath);
		tempPath.append("/").append(fileDir).append("/");
		SimpleDateFormat folderNameFormat = new SimpleDateFormat("yyyyMM");
		tempPath.append(folderNameFormat.format(new Date()));
		File temp = new File(tempPath.toString());
		if (!temp.exists())
			temp.mkdirs();
//		SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMddkkmmss_S");
		tempPath.append("/").append(Utils.generateCode());
		tempPath.append(".").append(fileExt);
		savePath = tempPath.toString().replaceAll("\\\\", "/");
	}

	/**
	 * @category 拷贝文件
	 * @param src 源文件
	 * @param tar 目标路径
	 */
	private void copy(InputStream src, String tar) {
		// 判断源文件或目标路径是否为空
		if (null == src || null == tar || tar.isEmpty()) {
			return;
		}
		// InputStream srcIs = null;
		OutputStream tarOs = null;
		try {
			// srcIs = new FileInputStream(src);
			File tarFile = new File(tar);
			tarOs = new FileOutputStream(tarFile);
			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = src.read(buffer))) {
				tarOs.write(buffer, 0, n);
			}
		} catch (IOException e) {
			log.error("Copy File is Fali, Because " + e);
		} finally {
			try {
				if (null != src) {
					src.close();
				}
				if (null != tarOs) {
					tarOs.close();
				}
			} catch (IOException e) {
				log.error("Close Stream is Fail, Because " + e);
			}
		}
	}

	/**
	 * @category 管理文件上传的请求以及目录管理
	 * @param dir
	 * @param request
	 * @param path
	 * @param order
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("manager")
	@ResponseBody
	public Map<String, Object> manager(String dir, HttpServletRequest request, String path, String order) {
		// 初始相关变量
		Map<String, Object> execute = new HashMap<String, Object>();
//		ServletContext context = request.getSession().getServletContext();
//		pageCtx = context.getContextPath().concat(filePath);
//		relPath = context.getRealPath(filePath);
		fileDir = dir;
		if (null == dir || dir.isEmpty()) {
			fileDir = "file";
		}
		if (!extMap.containsKey(fileDir)) {
			execute.put("error", 1);
			execute.put("message", "目录名不正确,请检查.");
			return execute;
		}
		String tempPath = null == path ? fileDir.concat("/") : fileDir.concat("/" + path);
		String curPath = "/Upload/".concat(tempPath);
		String curFileDir = relPath.concat("/" + tempPath);
		String curDir = path;
		String moveupDir = "";
		// 检查当前目录是否为根目录
		if (!"".equals(path)) {
			String str = curDir.substring(0, curDir.length() - 1);
			moveupDir = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}
		// 检查..命令
		if (path.indexOf("..") >= 0) {
			execute.put("error", 1);
			execute.put("message", "不允许使用..命令返回上一层.");
			return execute;
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			execute.put("error", 1);
			execute.put("message", "文件路径不合法.");
			return execute;
		}
		// 检查当前目录
		File curPathFile = new File(curFileDir);
		if (!curPathFile.isDirectory()) {
			execute.put("error", 1);
			execute.put("message", "当前目录不存在.");
			return execute;
		}
		// 遍历目录取的文件信息
		@SuppressWarnings("rawtypes")
		List<HashMap> fileList = new ArrayList<HashMap>();
		if (curPathFile.listFiles() != null) {
			for (File file : curPathFile.listFiles()) {
				HashMap<String, Object> hash = new HashMap<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}
		// 文件排序方式
		String tempOrder = order != null ? order.toLowerCase() : "name";
		if ("size".equals(tempOrder)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(tempOrder)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		// 输出遍历后的文件信息数据
		execute.put("moveup_dir_path", moveupDir);
		execute.put("current_dir_path", curDir);
		execute.put("current_url", curPath);
		execute.put("total_count", fileList.size());
		execute.put("file_list", fileList);
		return execute;
	}

	/**
	 * @category 查询服务器的文件并根据文件名称排序
	 */
	@SuppressWarnings("rawtypes")
	public class NameComparator implements Comparator {

		// 复写比较器
		@Override
		public int compare(Object a, Object b) {
			HashMap hashA = (HashMap) a;
			HashMap hashB = (HashMap) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
			}
		}
	}

	/**
	 * @category 查询服务器的文件并根据文件大小排序
	 */
	@SuppressWarnings("rawtypes")
	public class SizeComparator implements Comparator {

		// 复写比较器
		@Override
		public int compare(Object a, Object b) {
			HashMap hashA = (HashMap) a;
			HashMap hashB = (HashMap) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
					return 1;
				} else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	/**
	 * @category 查询服务器的文件并根据文件类型排序
	 */
	@SuppressWarnings("rawtypes")
	public class TypeComparator implements Comparator {

		// 复写比较器
		@Override
		public int compare(Object a, Object b) {
			HashMap hashA = (HashMap) a;
			HashMap hashB = (HashMap) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
			}
		}
	}
}
