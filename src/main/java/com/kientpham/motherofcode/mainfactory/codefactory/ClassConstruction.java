package com.kientpham.motherofcode.mainfactory.codefactory;

public interface ClassConstruction {

	String entityAnnotated(String tableName);

	String lombokAnnotated();

	String getClassName(String className, String inheritance, String parent);

	String getRepositoryInterface(String interfaceName, String entityName, String idType);

}
