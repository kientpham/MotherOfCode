package com.kientpham.motherofcode.mainfactory.codefactory.java;

import com.kientpham.motherofcode.mainfactory.codefactory.ClassConstruction;

public class JavaClassConstruction implements ClassConstruction {

	@Override
	public String entityAnnotated(String tableName) {
		return String.format("@Entity\r\n" + "@Table(name = \"%1$s\")", tableName);
	}

	@Override
	public String lombokAnnotated() {
		return "@Getter\r\n" + "@Setter\r\n";
	}

	@Override
	public String getClassName(String className, String inheritance, String parent) {
		return String.format("public class %1$s %2$s %3$s", className, inheritance, parent).trim();
	}

	@Override
	public String getRepositoryInterface(String interfaceName, String entityName, String idType) {
		return String.format("public interface %1$s extends CrudRepository<%2$s, %3$s> {\r\n\r\n}", interfaceName,
				entityName, idType);
	}
}
