package com.kientpham.motherofcode.mainfactory.codefactory.java;

import com.kientpham.motherofcode.mainfactory.codefactory.ClassConstruction;

public class JavaClassConstruction implements ClassConstruction {

	@Override
	public String entityAnnotated(String tableName) {
		return String.format("@Entity\r\n" + "@Table(name = \"%1$s\")", tableName);
	}

	@Override
	public String getsetAnnotated() {
		return "@Getter\r\n" + "@Setter";
	}

	@Override
	public String className(String className) {	
		return String.format("\r\npublic class %1$s", className).trim()+"{\r\n";
	}
	
	@Override
	public String interfaceName(String className) {
		return String.format("\r\npublic interface %1$s", className).trim()+"{\r\n";
	}
	
	@Override
	public String getClassNameImplements(String className, String parent) {
		return String.format("\r\npublic class %1$s implements %2$s", className, parent).trim()+"{\r\n";
	}
	
	@Override
	public String getClassNameExtends(String className, String parent) {		
		return String.format("\r\npublic class %1$s extends %2$s", className, parent).trim()+"{\r\n";
	}

	@Override
	public String getCrudRepositoryInterface(String interfaceName, String entityName, String idType) {
		return String.format("\r\npublic interface %1$s extends CrudRepository<%2$s, %3$s> {\r\n", interfaceName,
				entityName, idType);
	}
	
	@Override
	public String getRepositoryInterface(String interfaceName, String entityName, String idType) {
		String code=String.format("\r\npublic interface %1$s extends Repository<%2$s, %3$s> {\r\n\r\n", interfaceName,
				entityName, idType) +
				"\tPage<User> findAll(Pageable pageRequest);\r\n";	
		
		return code;
	}

	@Override
	public String componentAnnotated() {
		return "@Component";
	}



}
