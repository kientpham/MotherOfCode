package com.kientpham.motherofcode.easywebapp.workflow.factory;

import java.util.List;

public interface PackageInterface {

	String buildPackageName(String domainName);
	
	String buildDomainName(List<String> listDomain);
	
	String generateCodeFile(String code, String filePath, String type);
	 
}
