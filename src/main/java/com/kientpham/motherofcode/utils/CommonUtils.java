package com.kientpham.motherofcode.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class CommonUtils {

	public static void writeToFile(String text, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(text);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getObjectNameFromDomain(String fullDomain) {
		String[] s = fullDomain.split("\\.");
		return s[s.length - 1];
	}
	
	public static String combineTwoString(String s1, String s2) {
		return s1+s2;
	}
	
	public static String combineTwoStringWithDot(String s1, String s2) {
		if (s1.isEmpty()) return s2;
		if (s2.isEmpty()) return s1;
		return String.format("%1$s.%2$s", s1,s2);
	}

	public static String getLowerCaseFirstChar(String className) {
		return Character.toString(className.charAt(0)).toLowerCase() + className.substring(1);
	}
	
	public static String getUpperCaseFirstChar(String className) {
		return Character.toString(className.charAt(0)).toUpperCase() + className.substring(1);
	}
	
	public static String getColumnName(String name) {
		String[] arrName = name.split("(?=\\p{Upper})");
		String column="";
		for (String aName:arrName) {
			column+=getUpperCaseFirstChar(aName) + " ";
		}
		return column;
	}

}
