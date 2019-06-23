package com.kientpham.motherofcode.utils;

import java.io.File;
import java.util.List;

public final class PackageUtils {

	public static String buildPackageName(List<String> domains) {
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
		return filePath;
	}

	private static String getPath(String pathWithDot) {
		String[] sub = pathWithDot.split(".");
		String path = "";
		for (int i = 0; i < sub.length; i++) {
			if (!path.isEmpty())
				path += File.separator;
			path += sub[i];
		}
		return path;
	}
}
