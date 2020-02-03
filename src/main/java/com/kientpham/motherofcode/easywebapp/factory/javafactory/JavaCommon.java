package com.kientpham.motherofcode.easywebapp.factory.javafactory;

public final class JavaCommon {

	public static String importDomain(String domain) {
		return String.format(JavaConst.IMPORT + "%1$s" + JavaConst.NEWLINE, domain);
	}
}
