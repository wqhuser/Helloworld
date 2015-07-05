package util.db;

public class StringUtil {

	public static String upcaseFirstChar(String s) {
		String firstChar = s.substring(0, 1);
		return s.replaceFirst(firstChar, firstChar.toUpperCase());
	}

	public static String lowercaseFirstChar(String s) {
		String firstChar = s.substring(0, 1);
		return s.replaceFirst(firstChar, firstChar.toLowerCase());
	}

	public static Object colNameConvert(String columnName) {
		String[] strings = columnName.split("_");
		StringBuilder sb = new StringBuilder(strings[0]);
		for (int i = 1; i < strings.length; i++)
			sb.append(upcaseFirstChar(strings[i]));
		return sb.toString();
	}

	public static String convertDBName(String name) {
		char[] arr = name.toCharArray();
		StringBuilder sb = new StringBuilder();
		sb.append(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < 97)
				sb.append("_").append((char) (arr[i] + 32));
			else
				sb.append(arr[i]);
		}
		return sb.toString();
	}

	public static boolean isEmpty(String s) {
		if(null == s || s.equals(""))
				return true;
		return false;
	}
}
