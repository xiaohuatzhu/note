package cn.tedu.note.util;

public class StrUtil {
	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	/** 将name转换为name(i)并返回 */
	public static String reName(String oldName) {
		String regex = ".*\\(\\d+\\)$";
		System.out.println(regex);
		if (oldName.matches(regex)) {
			String str = oldName.substring(oldName.lastIndexOf('(') + 1, oldName.length() - 1);
			System.out.println(str);
			return oldName.substring(0, oldName.lastIndexOf('(')) + "(" + (Integer.parseInt(str) + 1) + ")";
		} else {
			return oldName + "(1)";
		}
	}

	// public static void main(String[] args) {
	// System.out.println(reName("wdt"));
	// }
}
