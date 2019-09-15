package com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public class BuildMethodForUser extends MethodBuilderBase{

	@Override
	public String buildMethodForServiceInterface(TransactionModel transaction) {
		
		return super.buildMethodForServiceInterface(transaction)+ String.format("public List<String> getListFeature(%1$s userId);\r\n", transaction.getEntity().getFields().get(0).getType());		
	}
	
	@Override
	public String buildMethodForServiceClass(TransactionModel transaction) {
		String entityName = transaction.getEntity().getName();		
		String idType=transaction.getEntity().getFields().get(0).getType();
		return super.buildMethodForServiceClass(transaction)+ String.format("@Override\r\n" + 
				"	public List<String> getListFeature(%2$s userId) {\r\n" + 
				"		%1$s user = userDomain.getById(userId);\r\n" + 
				"		List<Group> groupList = user.getGroups();\r\n" + 
				"		List<Permission> permissionList = new ArrayList<Permission>();\r\n" + 
				"		for (Group group : groupList) {\r\n" + 
				"			permissionList.addAll(group.getPermissions());\r\n" + 
				"		}\r\n" + 
				"		Set<String> featureList = new HashSet<String>();\r\n" + 
				"		for (Permission permission : permissionList) {\r\n" + 
				"			String menuPath=permission.getMenuPath();\r\n" + 
				"			if (permission.getToggle() && menuPath!=null && !menuPath.isEmpty()) {\r\n" + 
				"				featureList.addAll(Arrays.asList(permission.getMenuPath().trim().split(\"\\\\\\\\\")));\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"		return new ArrayList<String>(featureList);\r\n" + 
				"	}", entityName,idType);
	}

}
