package com.kientpham.motherofcode.mainfactory.codefactory.java;

import java.io.File;
import java.util.List;

import com.kientpham.motherofcode.mainfactory.codefactory.PackageName;

public class JavaPackageName implements PackageName{

	@Override
	public String buildPackageName(String domain) {
		
		return "package "+ domain + ";\r\n";
	}
	
	@Override
	public String buildDomainName(List<String> domains) {
		String packageName = "";
		for (String domain : domains) {
			if (!packageName.isEmpty())
				packageName += '.';
			packageName += domain;
		}
		return packageName;
	}

	@Override
	public String buildFilePath(List<String> domains) {
		String filePath = "";
		for (String domain : domains) {
			if(!filePath.isEmpty())
				filePath+=File.separator;
			
			if (domain.contains(".")) {
				filePath+=this.getPath(domain);
			}else {
				filePath+=domain;
			}
		}
		return filePath;
	}

	private String getPath(String pathWithDot) {
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
