package com.susd.infrastructure;

import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
import java.util.UUID;

public class Utils {

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
}
