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
		String[] s=fullDomain.split("\\.");
		return s[s.length-1];
	}

}
