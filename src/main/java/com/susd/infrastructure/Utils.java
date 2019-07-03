package com.susd.infrastructure;

import com.susd.controllers.KindEditorUploadController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
import java.util.UUID;

public class Utils {

	// 日志输出对象
	private static Log log = LogFactory.getLog(KindEditorUploadController.class);
//	public static void main(String[] args) {
//		List<String> l = new ArrayList<String>();
//		for (int i = 0; i < 10000; i++) {
//			l.add(generateCode());
//		}
//
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		for (String item : l) {
//			if (map.containsKey(item)) {
//				map.put(item, map.get(item).intValue() + 1);
//			} else {
//				map.put(item, new Integer(1));
//			}
//		}
//		Iterator<String> keys = map.keySet().iterator();
//		while (keys.hasNext()) {
//			String key = keys.next();
//			System.out.println(key + "重复" + map.get(key).intValue() + "次");
//		}
//	}

	public static String generateCode() {
		long now = System.currentTimeMillis();// 一个13位的时间戳
		
		int hashCode = Math.abs(UUID.randomUUID().toString().hashCode());
		
		return getPeriod() + String.format("%011d", hashCode) + now;
	}

	
	public static String getPeriod() {
		SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
		String format2 = format.format(new Date());
		return format2;
	}

	/**
	 * 组合新的文件路径
	 * @param realPath
	 * @param fileExt
	 * @param fileDir
	 * @return
	 */
	public static String createSavePath(String realPath,String fileExt,String fileDir){
		StringBuilder tempPath = new StringBuilder(realPath);
		tempPath.append("/").append(fileDir).append("/");
		SimpleDateFormat folderNameFormat = new SimpleDateFormat("yyyyMM");
		tempPath.append(folderNameFormat.format(new Date()));
		File temp = new File(tempPath.toString());
		if (!temp.exists())
			temp.mkdirs();

		tempPath.append("/").append(Utils.generateCode());
		tempPath.append(".").append(fileExt);
		return tempPath.toString().replaceAll("\\\\", "/");
	}

	/**
	 * @category 拷贝文件
	 * @param src 源文件
	 * @param tar 目标路径
	 */
	public static void copy(InputStream src, String tar) {
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
			log.error("Copy File is Fail, Because " + e);
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
}
