package com.kientpham.motherofcode.easywebapp.workflow.factory.methodbuilder;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public class BuildMethodForCategory extends  JavaMethodBuilder{

	@Override
	public String buildMethodForRepository(TransactionModel transaction) {
		return String.format(
				"\tList<%1$s> findByCategoryGroup(String categoryGroup);\r\n\r\n" + "\tList<%1$s> findAll(Sort sort);",
				transaction.getEntity().getName());
	}
	
	@Override
	public String buildMethodForDBGatewayInterface(TransactionModel transaction) {
		
		return String.format("	public List<%1$s> findByCategoryGroup(String categoryGroup);\r\n" + 
				"\r\n" + 
				"	public List<%1$s> findAllSorted();", transaction.getEntity().getName());
	}
	
	@Override
	public String buildMethodForDBGateway(TransactionModel transaction) {
		
		return String.format("	@Override\r\n" + 
				"	public List<%1$s> findByCategoryGroup(String categoryGroup) {\r\n" + 
				"		return repository.findByCategoryGroup(categoryGroup);\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	@Override\r\n" + 
				"	public List<%1$s> findAllSorted() {\r\n" + 
				"		List<%1$s> listCat=repository.findAll(Sort.by(\"order\").ascending());		\r\n" + 
				"		return listCat;\r\n" + 
				"	}", transaction.getEntity().getName());
	}
	
	@Override
	public String buildMethodForBusinessObject(TransactionModel transaction) {
		return String.format("	public List<%1$s> findAllSorted() {		\r\n" + 
				"		List<%1$s> listCat=dbGateway.findAllSorted();		\r\n" + 
				"		return listCat;\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	\r\n" + 
				"	public List<%1$s> getCategoryByGroup(String categoryGroup) {\r\n" + 
				"		return dbGateway.findByCategoryGroup(categoryGroup);\r\n" + 
				"	}	\r\n" + 
				"\r\n" + 
				"	\r\n" + 
				"	public List<%1$s> getCategoryByGroups(List<String> categoryGroups) {\r\n" + 
				"		List<%1$s> categoryList=new ArrayList<%1$s>();				\r\n" + 
				"		for(String catGroup: categoryGroups) {\r\n" + 
				"			categoryList.addAll(this.getCategoryByGroup(catGroup));	\r\n" + 
				"		}\r\n" + 
				"		return categoryList;\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	\r\n" + 
				"	public Map<Integer, String> getCategoryMapByGroups(List<String> categoryGroups) {\r\n" + 
				"		Map<Integer, String> map=new HashMap<Integer, String>();\r\n" + 
				"		for(String catGroup: categoryGroups) {\r\n" + 
				"			for(Category cat:this.getCategoryByGroup(catGroup)) {\r\n" + 
				"				map.put(cat.getId(), cat.getValue());\r\n" + 
				"			}	\r\n" + 
				"		}\r\n" + 
				"		return map;\r\n" + 
				"	}", transaction.getEntity().getName());
	}
	
	@Override
	public String buildMethodForServiceInterface(TransactionModel transaction) {
		return String.format("\r\n\tpublic Map<%1$s, String> getMapAllCategory();", transaction.getEntity().getFields().get(0).getType());
	}
	
	@Override
	public String buildMethodForServiceClass(TransactionModel transaction) {
		return String.format("\r\n\t@Override\r\n" + 
				"	public Map<%1$s, String> getMapAllCategory() {\r\n" + 
				"		Map<%1$s, String> map = new HashMap<%1$s, String>();\r\n" + 
				"		for (Category cat : this.getAllCategory()) {\r\n" + 
				"			map.put(cat.getId(), cat.getValue());\r\n" + 
				"		}\r\n" + 
				"		return map;\r\n" + 
				"	}", transaction.getEntity().getFields().get(0).getType());
	}
}
