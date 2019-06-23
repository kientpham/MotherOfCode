package com.kientpham.motherofcode.mainfactory.codefactory.java;

import com.kientpham.motherofcode.mainfactory.codefactory.MethodConstruction;

public class JavaMethodConstruction implements MethodConstruction {

	@Override
	public String defaulConstructor(String className) {
		return String.format("\tpublic %1$s() {\r\n\t\t//default constructor\r\n\t}", className);
	}
}
