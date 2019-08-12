package com.kientpham.motherofcode.easywebapp.workflow.factory;

import java.util.List;

import com.kientpham.motherofcode.utils.Const;

public class JavaPackageBuilder implements PackageInterface{

	@Override
	public String buildPackageName(String domainName) {
		return "package "+ domainName + ";\r\n";
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
	public String generateCodeFile(String code, String filePath,String type) {
		String extension="";
		switch (type){
			case Const.CODE:
				extension=".java";
				break;
			case Const.HTML:
				extension=".html";
				break;
		}
		if (type==Const.CODE)
			
		
		return null;
	}

}
