package com.kientpham.motherofcode.mainfactory.codefactory;

import java.util.List;

public interface PackageName {

	String buildPackageName(String domain);

	String buildFilePath(List<String> domains);

	String buildDomainName(List<String> domains);

}
