package com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public class LookupMethodBuilder extends JavaMethodBaseBuilder {

	private String lookupType = "LookupType";
	private String lookupByType = "LookupByType";

	private String entityName = "";
	private String idType = "";

	@Override
	public String buildMethodForRepository(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		entityName = omnibusDTO.getTransaction().getEntity().getName();
		idType = omnibusDTO.getTransaction().getEntity().getFields().get(0).getType();
		return super.buildMethodForRepository(omnibusDTO)
				+ String.format("\tList<%1$s> findBy%2$s(String %3$s);\r\n\r\n" + "\tList<%1$s> findAll(Sort sort);",
						entityName, lookupType, CommonUtils.getLowerCaseFirstChar(lookupType));
	}

	@Override
	public String buildMethodForDBGatewayInterface(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		
		return super.buildMethodForDBGatewayInterface(omnibusDTO) + String.format(
				"	public List<%1$s> findBy%2$s(String %3$s);\r\n" + "\r\n" + "	public List<%1$s> findAllSorted();",
				entityName, lookupType, CommonUtils.getLowerCaseFirstChar(lookupType));
	}

	@Override
	public String buildMethodForDBGateway(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		return super.buildMethodForDBGateway(omnibusDTO) + String.format(
				"	@Override\r\n" + "	public List<%1$s> findBy%2$s(String %3$s) {\r\n"
						+ "		return repository.findBy%2$s(%3$s);\r\n" + "	}\r\n" + "\r\n" + "	@Override\r\n"
						+ "	public List<%1$s> findAllSorted() {\r\n"
						+ "		return repository.findAll(Sort.by(\"order\").ascending());		\r\n" + "	}",
				entityName, lookupType, CommonUtils.getLowerCaseFirstChar(lookupType));
	}

	@Override
	public String buildMethodForBusinessObject(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return super.buildMethodForBusinessObject(omnibusDTO) + String.format(
				"	public List<%1$s> findAllSorted() {		\r\n"
						+ "		return dbGateway.findAllSorted();		\r\n" + "	}\r\n" + "\r\n" + "	\r\n"
						+ "	public List<%1$s> get%2$s(String %4$s) {\r\n"
						+ "		return dbGateway.findBy%3$s(%4$s);\r\n" + "	}	\r\n" + "\r\n" + "	\r\n"
						+ "	public List<%1$s> get%2$ss(List<String> %3$ss) {\r\n"
						+ "		List<%1$s> %4$sList=new ArrayList<%1$s>();				\r\n"
						+ "		for(String %3$s: %3$ss) {\r\n"
						+ "			%4$sList.addAll(this.get%2$s(%3$s));	\r\n" + "		}\r\n"
						+ "		return %4$sList;\r\n" + "	}\r\n" + "\r\n" + "	\r\n"
						+ "	public Map<Integer, String> getMapBy%1$ss(List<String> %4$ss) {\r\n"
						+ "		Map<Integer, String> map=new HashMap<Integer, String>();\r\n"
						+ "		for(String %4$s: %4$ss) {\r\n" + "			for(%1$s item:this.get%2$s(%4$s)) {\r\n"
						+ "				map.put(item.getId(), item.getValue());\r\n" + "			}	\r\n"
						+ "		}\r\n" + "		return map;\r\n" + "	}",
				entityName, lookupByType, lookupType, CommonUtils.getLowerCaseFirstChar(lookupType));
	}

	@Override
	public String buildMethodForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return super.buildMethodForReadService(omnibusDTO)
				+ String.format("\r\n\tpublic Map<%1$s, String> getMapAll%2$s();", idType, entityName);
	}

	@Override
	public String buildMethodForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return super.buildMethodForReadServiceImpl(omnibusDTO)
				+ String.format("\r\n\t@Override\r\n" + "	public Map<%1$s, String> getMapAll%2$s() {\r\n"
						+ "		Map<%1$s, String> map = new HashMap<%1$s, String>();\r\n"
						+ "		for (%2$s item : this.getAll%2$s()) {\r\n"
						+ "			map.put(item.getId(), item.getValue());\r\n" + "		}\r\n"
						+ "		return map;\r\n" + "	}", idType, entityName);
	}

}
