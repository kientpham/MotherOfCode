package com.kientpham.motherofcode.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class PackageUtils {

	public static String buildDomainName(List<String> domains) {
		String packageName = "";
		for (String domain : domains) {
			if (!packageName.isEmpty())
				packageName += '.';
			packageName += domain;
		}
		return packageName;
	}

	public static String buildFilePath(List<String> domains) {
		String filePath = "";
		for (String domain : domains) {
			if(!filePath.isEmpty())
				filePath+=File.separator;
			
			if (domain.contains(".")) {
				filePath+=getPath(domain);
			}else {
				filePath+=domain;
			}
		}
		return filePath+".java";
	}
	
	public static String getObjectNameFromDomain(String fullDomain) {
		String[] s=fullDomain.split("\\.");
		return s[s.length-1];
	}

	private static String getPath(String pathWithDot) {
		String[] sub = pathWithDot.split("\\.");
		String path = "";
		for (int i = 0; i < sub.length; i++) {
			//if (!path.isEmpty())
			path += File.separator+ sub[i];
		}
		return path;
	}
	
	public static void writeToFile(String text, String fileName) {
		try {
			File file = new File(fileName);
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
}
