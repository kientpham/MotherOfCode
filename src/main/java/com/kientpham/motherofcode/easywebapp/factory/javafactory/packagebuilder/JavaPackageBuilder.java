package com.kientpham.motherofcode.easywebapp.factory.javafactory.packagebuilder;

import java.io.File;
import java.util.List;

import com.kientpham.motherofcode.easywebapp.factory.PackageInterface;

public class JavaPackageBuilder implements PackageInterface{

	@Override
	public String buildPackageName(String domainName) {
		String packageName=domainName.substring(0,domainName.lastIndexOf('.'));
		return "package "+ packageName+ ";\r\n";
	}

	@Override
	public String buildDomainName(List<String> listDomain) {
		String packageName = "";
		for (String domain : listDomain) {
			if (!packageName.isEmpty())
				packageName += '.';
			packageName += domain;
		}
		return packageName;
	}
	
	@Override
	public String buildFilePath(List<String> domains) {
		String filePath = "\\java";
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
	
	private String getPath(String pathWithDot) {
		String[] sub = pathWithDot.split("\\.");
		String path = "";
		for (int i = 0; i < sub.length; i++) {
			path += File.separator+ sub[i];
		}
		return path;
	}

	@Override
	public String buildPackageFooter() {		
		return "\r\n}";
	}

	@Override
	public String buildFilePath(String domains) {
		String filePath = "\\src\\main\\java\\";
		filePath +=domains.replace('.', File.separatorChar);	
		return filePath+".java";
	}

}
