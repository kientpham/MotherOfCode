package com.kientpham.motherofcode.mainfactory.codefactory;

import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.codefactory.java.JavaClassConstruction;
import com.kientpham.motherofcode.mainfactory.codefactory.java.JavaFieldConstruction;
import com.kientpham.motherofcode.mainfactory.codefactory.java.JavaImportCode;
import com.kientpham.motherofcode.mainfactory.codefactory.java.JavaMethodConstruction;
import com.kientpham.motherofcode.mainfactory.codefactory.java.JavaPackageName;
import com.kientpham.motherofcode.utils.Const;

public class CodeBuilder {
	
	private StringBuilder sbCode=new StringBuilder();	
	
	private PackageName packageName;
	
	private ImportCode importCode;

	private ClassConstruction classConstruction;

	private FieldConstruction fieldConstruction;

	private MethodConstruction methodConstruction;
	
	public String getCode() {
		String code= sbCode.toString()+"}";
		sbCode=new StringBuilder();
		return code;
	}

	public CodeBuilder(String language) {
		if (language.equals(Const.JAVA)) {
			importCode = new JavaImportCode();
			packageName = new JavaPackageName();
			classConstruction = new JavaClassConstruction();
			fieldConstruction = new JavaFieldConstruction();
			methodConstruction = new JavaMethodConstruction();
		}
	}
	
	private void appendLine(String s) {
		sbCode.append(s+"\r\n");
	}

	/*
	 * Package name and file path ==================================================
	 */

	public void buildPackageName(String domain) {
		appendLine(packageName.buildPackageName(domain));		
	}

	/*
	 * Imports =================================================
	 */
	
	public void importDomain(String domain) {
		appendLine(importCode.importDomain(domain));
	}
	
	public void importSpringData() {
		appendLine(importCode.importSpringPaging());
	}
	
	public void importEntity(boolean hasJoinTable) {
		appendLine(importCode.importEntity(hasJoinTable));		
	}

	public void lombokGetterSetter() {
		appendLine(importCode.lombokGetterSetter());
	}

	public void serializable() {
		appendLine(importCode.serializable());
	}

	public void importlist() {
		appendLine(importCode.list());
	}
	
	public void importSort() {
		appendLine(importCode.dataSort());		
	}
	
	public void importSpringPageble() {
		appendLine(importCode.importSpringPageble());		
	}
	
	public void importSpringPageRequest() {
		appendLine(importCode.importSpringPageRequest());		
	}

	public void crudRepository(String entityFullDomain) {
		appendLine(importCode.crudRepository(entityFullDomain));
	}
	
	public void pageRepository(String entityFullDomain) {
		appendLine(importCode.repository(entityFullDomain));
	}
	
	public void importSpringComponent(){
		appendLine(importCode.importSpringComponent());
	}
	
	public void importListArray() {
		appendLine(importCode.importListArray());		
	}
	

	/*
	 * Class Annotated and Name ==================================================
	 */

	public void componentAnnotated() {
		appendLine(classConstruction.componentAnnotated());		
	}	
	
	public void entityAnnotated(String tableName) {
		appendLine(classConstruction.entityAnnotated(tableName));
	}

	public void getsetAnnotated() {
		appendLine(classConstruction.getsetAnnotated());
	}
	
	public void className(String className) {
		appendLine(classConstruction.className(className));		
	}
	
	public void interfaceName(String className) {
		appendLine(classConstruction.interfaceName(className));	
	}
	
	public void classNameImplements(String className, String parent) {
		appendLine(classConstruction.getClassNameImplements(className, parent));
	}
	
	public void classNameExtends(String className, String parent) {
		appendLine(classConstruction.getClassNameExtends(className, parent));
	}

	public void crudRepositoryInterface(String interfaceName, String entityName, String idType) {
		appendLine(classConstruction.getCrudRepositoryInterface(interfaceName, entityName, idType));
	}
	
	public void repositoryInterface(String interfaceName, String entityName, String idType) {
		appendLine(classConstruction.getRepositoryInterface(interfaceName, entityName, idType));
	}

	/*
	 * Fields ====================================================
	 */
	public void idFieldAnnotated() {
		appendLine(fieldConstruction.idFieldAnnotated());
	}

	public void normalFieldAnnoted(String columnName) {
		appendLine(fieldConstruction.normalFieldAnnoted(columnName));
	}

	public void getFieldName(String type, String fieldName) {
		appendLine(fieldConstruction.getFieldName(type, fieldName));
	}

	public void getJoinFields(JoinTable joinTable) {
		appendLine(fieldConstruction.getJoinFields(joinTable));
	}

	/*
	 * Methods ======================================================
	 */
	public void defaulConstructor(String className) {
		appendLine(methodConstruction.defaulConstructor(className));
	}
	
	public void addMethod(String methodCode) {
		appendLine(methodCode);
	}
	
	public void searchEntity(Entity entity) {
		appendLine(methodConstruction.searchEntity(entity));
	}

	public void buildDBGatewayClass(TransactionModel transaction, boolean hasPaging) {
		appendLine(methodConstruction.buildDBGateway(transaction,hasPaging));
		
	}
	
	public void buildDBGatewayInterface(TransactionModel transaction, boolean hasPaging) {
		appendLine(methodConstruction.buildDBGatewayInterface(transaction,hasPaging));
		
	}

	public void buildBusinessDomainClass(TransactionModel transaction, boolean hasPaging) {
		appendLine(methodConstruction.buildBusinessObject(transaction,hasPaging));
		
	}

	public void buildServiceInterface(TransactionModel transaction, boolean hasPaging) {
		appendLine(methodConstruction.buildServiceInterface(transaction,hasPaging));
		
	}

	public void buildServiceClass(TransactionModel transaction, boolean hasPaging) {
		appendLine(methodConstruction.buildServiceClass(transaction,hasPaging));		
	}

	public void buildCategoryRepo(TransactionModel transaction) {
		appendLine(methodConstruction.buildCategoryRepo(transaction));
		
	}



	


}
