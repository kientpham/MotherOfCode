package com.kientpham.motherofcode.mainfactory.codefactory;

import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.mainfactory.codefactory.java.JavaClassConstruction;
import com.kientpham.motherofcode.mainfactory.codefactory.java.JavaFieldConstruction;
import com.kientpham.motherofcode.mainfactory.codefactory.java.JavaImportCode;
import com.kientpham.motherofcode.mainfactory.codefactory.java.JavaMethodConstruction;
import com.kientpham.motherofcode.mainfactory.codefactory.java.JavaPackageName;
import com.kientpham.motherofcode.utils.Const;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeFacade {

	private PackageName packageName;

	private ImportCode importCode;

	private ClassConstruction classConstruction;

	private FieldConstruction fieldConstruction;

	private MethodConstruction methodConstruction;

	public CodeFacade(String language) {
		if (language.equals(Const.JAVA)) {
			importCode = new JavaImportCode();
			packageName = new JavaPackageName();
			classConstruction = new JavaClassConstruction();
			fieldConstruction = new JavaFieldConstruction();
			methodConstruction = new JavaMethodConstruction();
		}
	}

	/*
	 * Package name and file path ==================================================
	 */

	public String buildPackageName(String domain) {
		return packageName.buildPackageName(domain);
	}

	public String buildFilePath(List<String> domains) {
		return packageName.buildFilePath(domains);
	}

	public String buildDomainName(List<String> domains) {
		return packageName.buildDomainName(domains);
	}

	/*
	 * Imports =================================================
	 */
	public String importEntity(boolean hasJoinTable) {
		return importCode.importEntity(hasJoinTable);
	}

	public String lombokGetterSetter() {
		return importCode.lombokGetterSetter();
	}

	public String serializable() {
		return importCode.serializable();
	}

	public String list() {
		return importCode.list();
	}

	public String crudRepository(String entityFullDomain) {
		return importCode.crudRepository(entityFullDomain);
	}

	/*
	 * Class Annotated and Name ==================================================
	 */

	public String entityAnnotated(String tableName) {
		return classConstruction.entityAnnotated(tableName);
	}

	public String lombokAnnotated() {
		return classConstruction.lombokAnnotated();
	}

	public String getClassName(String className, String inheritance, String parent) {
		return classConstruction.getClassName(className, inheritance, parent);
	}

	public String getRepositoryInterface(String interfaceName, String entityName, String idType) {
		return classConstruction.getRepositoryInterface(interfaceName, entityName, idType);
	}

	/*
	 * Fields ====================================================
	 */
	public String idFieldAnnotated() {
		return fieldConstruction.idFieldAnnotated();
	}

	public String normalFieldAnnoted(String columnName) {
		return fieldConstruction.normalFieldAnnoted(columnName);
	}

	public String getFieldName(String type, String fieldName) {
		return fieldConstruction.getFieldName(type, fieldName);
	}

	public String getJoinFields(JoinTable joinTable) {
		return fieldConstruction.getJoinFields(joinTable);
	}

	/*
	 * Methods ======================================================
	 */
	public String defaulConstructor(String className) {
		return methodConstruction.defaulConstructor(className);
	}
}
