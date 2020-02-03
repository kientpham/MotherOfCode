package com.kientpham.motherofcode.easywebapp.factory;

import java.util.List;

public interface PackageInterface {

	String buildPackageName(String domainName);
	
	String buildDomainName(List<String> listDomain);	
	
	String buildFilePath(List<String> domains);
	
	String buildFilePath(String domains);
	
	String buildPackageFooter();
	 
}
