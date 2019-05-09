package com.susd.infrastructure;

public class Substr {
	// 截取字符串长度(中文2个字节，半个中文显示一个)
//	public static String substring(String str, int len) {
//
//		String s = str.trim();
//		if (len > s.length())
//			return str;
//
//		int n = 1;
//		for (int i = 0; i < s.length(); i++) {
//			if ((s.charAt(i) + "t").getBytes().length == 3) {
//				n++;
//			}
//			if (n + 1 >= len) {
//				break;
//			} else {
//				n++;
//			}
//		}
//		return str.substring(0, n);
//	}

	public static String substring(String str, int len) {
		if (str == null || str.equals("") || len == 0) {
			return "";
		}
		char[] charArr = str.toCharArray();
		int count = 0;
		StringBuilder sb = new StringBuilder("");
		for (char c : charArr) {
			if (count < len) {
				if (isChinese(c)) {
					if (count + 1 == len) {
						return sb.toString();

					} else {
						count = count + 2;
						sb.append(c);
					}
				} else {
					count = count + 1;
					sb.append(c);
				}
			} else {
				break;
			}
		}
		return sb.toString();
	}

	private static boolean isChinese(char c) {
		String s = String.valueOf(c);// 字符类型转化成字符串
		return s.getBytes().length > 1 ? true : false;
	}

//	public static void main(String[] args) throws Exception{  
//        // 原始字符串  
//        String s = "我ZWR爱你们JAVA";  
//        System.out.println("原始字符串：" + s + " : 字节数是: " + s.getBytes().length);  
//
///*            System.out.println("截取前1位：" + CutString.substring(s, 1));  
//            System.out.println("截取前2位：" + CutString.substring(s, 2));  
//            System.out.println("截取前4位：" + CutString.substring(s, 4)); */
//            //System.out.println("截取前12位：" + CutString.substring(s, 12));  
//            System.out.println("截取前12字节：" + idgui(s, 11));  
//       
//    }   
}
