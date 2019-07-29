package com.kientpham.motherofcode.mainfactory.codefactory;

public interface ClassConstruction {

	String entityAnnotated(String tableName);

	String getsetAnnotated();

	String getClassNameImplements(String className, String parent);
	
	String getClassNameExtends(String className, String parent);

	String getCrudRepositoryInterface(String interfaceName, String entityName, String idType);

	String getRepositoryInterface(String interfaceName, String entityName, String idType);

	String componentAnnotated();

	String className(String className);

	String interfaceName(String className);

}
