package com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.domain;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;

import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;
import com.kientpham.motherofcode.utils.Const;
import com.kientpham.motherofcode.utils.PackageUtils;

public class ServiceMethodDomain {

	private String entityName;

	private String businessDomainName;

	private String lookupService;

	private String lookupEntity;

	private String idType;

	public ServiceMethodDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		entityName = omnibusDTO.getTransaction().getEntity().getName();
		idType = omnibusDTO.getTransaction().getEntity().getFields().get(0).getType();
		businessDomainName = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getBussinessDomain());
		lookupEntity = omnibusDTO.getSharedDTO().getLookUpEntityName();
		lookupService = CommonUtils.getObjectNameFromDomain(
				omnibusDTO.getSharedDTO().getFullDomainTable().get(lookupEntity).getReadService());
	}

	public String buildMethodForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return buildMethodReadBase(omnibusDTO) + buildMethodReadAdv(omnibusDTO) + buildListJoinDTO(omnibusDTO);
	}

	private String buildMethodReadBase(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(
				"	@Autowired\r\n" + "	private %4$s %2$s;	\r\n" + "	@Override\r\n"
						+ "	public List<%1$s> getList%1$sByIds(List<%3$s> listId) {		 \r\n"
						+ "		return %2$s.findByListIds(listId);\r\n" + "	}\r\n" + "	@Override\r\n"
						+ "	public List<%1$s> getAll%1$s() {		\r\n"
						+ "		return (List<%1$s>)%2$s.findAll();\r\n" + "	}\r\n" + "	@Override\r\n"
						+ "	public %1$s get%1$sById(%3$s id) {\r\n" + "		return %2$s.findById(id);\r\n" + "	}\r\n",
				entityName, CommonUtils.getLowerCaseFirstChar(businessDomainName), idType,  businessDomainName);
	}

	private String buildMethodReadAdv(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String dataTableDTO = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain());

		return this.getPrivateField(lookupService) + String.format(
				"\r\n\t@Override\r\n\tpublic List<%5$s> getList%5$s() {\r\n"
						+ "		Map<Integer, String> mapCategory = %4$s.getMapAll%3$s();\r\n"
						+ "		List<%1$s> all%1$s = %6$s.findAll();\r\n"
						+ "		List<%5$s> %2$sTable = new ArrayList<%5$s>();\r\n"
						+ "		for (%1$s %2$s :all%1$s) {\r\n"
						+ " 			%2$sTable.add(new %5$s(%2$s, mapCategory));\r\n" + "		}\r\n"
						+ "		return %2$sTable;\r\n" + "	}\r\n",
				entityName, entityName.toLowerCase(), lookupEntity, CommonUtils.getLowerCaseFirstChar(lookupService),
				dataTableDTO, CommonUtils.getLowerCaseFirstChar(businessDomainName));
	}

	private String buildListJoinDTO(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";		
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {				
				String joinType= CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getJoinListDomain());
				String joinService=CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getReadService());
				
				code += this.getPrivateField(joinService)+ String.format("\r\n\t@Override\r\n\tpublic List<%6$s> get%6$s(Integer %4$sId) {\r\n"
						+ "		List<%6$s> returnList=new ArrayList<%6$s>(); \r\n"
						+ "		for (%1$s %2$s: %7$s.getAll%1$s()) {\r\n" 
						+ "			%6$s %2$sJoin=new %6$s();\r\n"
						+ "			%2$sJoin.set%1$sId(%2$s.getId());\r\n" 
						+ "			%2$sJoin.set%1$sName(%2$s.getName());\r\n"
						+ "			List<%1$s> %4$s%1$sAll= %8$s.findById(%4$sId).get%5$s();\r\n"
						+ "			if (%4$s%1$sAll!=null) {\r\n" 
						+ "				for (%1$s %4$s%1$s:%4$s%1$sAll) {\r\n"
						+ "					if (%2$s.getId()==%4$s%1$s.getId()) {\r\n"
						+ "						%2$sJoin.setIsChecked(\"checked\");\r\n"
						+ "						break;\r\n" 
						+ "					}\r\n" 
						+ "				}\r\n"
						+ "			}\r\n" 
						+ "			returnList.add(%2$sJoin);\r\n" 
						+ "		} \r\n"
						+ " 		return returnList;\r\n" 
						+ "		}", joinTable.getType(), joinTable.getType().toLowerCase(),
						entityName, entityName.toLowerCase(), CommonUtils.getUpperCaseFirstChar(joinTable.getField()),
						joinType, CommonUtils.getLowerCaseFirstChar(joinService), CommonUtils.getLowerCaseFirstChar(businessDomainName));
			}
		}
		return code;
	}

	public String buildMethodForWriteServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		return buildVariables(omnibusDTO) + deleteMethods(omnibusDTO) + saveEntity(omnibusDTO);
	}

	private String buildVariables(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = getPrivateField(businessDomainName);

		if (!omnibusDTO.getTransaction().getEntity().getName().equals(lookupEntity)) {
			code += getPrivateField(lookupService);
		}
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				code += getPrivateField(CommonUtils.getObjectNameFromDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getReadService()));
			}
		}
		return code;
	}

	private String deleteMethods(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(
				"\r\n\t@Override\r\n" + "	public void delete%1$sById(%3$s id) {\r\n"
						+ "		%2$s.deleteById(id);		\r\n" + "	}\r\n\r\n\t@Override\r\n"
						+ "	public void deleteList%1$s(List<%3$s> ids) {\r\n" + "		for (%3$s id:ids) {\r\n"
						+ "			%2$s.deleteById(id);\r\n" + "		}		\r\n" + "	}\r\n",
				entityName, CommonUtils.getLowerCaseFirstChar(businessDomainName), idType);
	}

	private String saveEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		return String.format(
				"\r\n\t@Override\r\n" + "	public %1$s save%1$s(%1$s entity) {\r\n"
						+ "		return %2$s.save(entity);\r\n" + "	}",
				entityName, CommonUtils.getLowerCaseFirstChar(businessDomainName), idType, entityName);
	}

	private String buildMethodForServiceClassOld(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		String code = String.format("\r\n\t@Override\r\n" + "	public void deleteList%1$s(List<%3$s> ids) {\r\n"
				+ "		for (%3$s id:ids) {\r\n" + "			%2$s.deleteById(id);\r\n" + "		}		\r\n"
				+ "	}\r\n" + "	@Override\r\n" + "	public void delete%1$sById(%3$s id) {\r\n"
				+ "		%2$s.deleteById(id);		\r\n" + "	}\r\n" + "	@Override\r\n"
				+ "	public %1$s save%1$s(%1$s entity) {\r\n" + "		return %2$s.save(entity);\r\n" + "	}",
				entityName, businessDomainName, idType, entityName);

		String dataTableDTO = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain());

		// code += String.format("\r\n\tpublic List<%5$s> getList%1$sTable() {\r\n"
		// + " Map<Integer, String> mapCategory = %3$s%4$s.getMapAllCategory();\r\n"
		// + " List<%1$s> all%1$s = %2$s%4$s.getAll%1$s();\r\n"
		// + " List<%5$s> %2$sTable = new ArrayList<%5$s>();\r\n" + " for (%1$s %2$s :
		// all%1$s) {\r\n"
		// + " %2$sTable.add(new %5$s(%2$s, mapCategory));\r\n" + " }\r\n"
		// + " return %2$sTable;\r\n" + " }", entityName, entityName.toLowerCase(),
		// categoryEntity.toLowerCase(), Const.DOMAIN, dataTableDTO);

		return code;
	}

	private String buildMethodForEditPresenter(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String code = "";// getServiceField(entityName);

		String editModelClass = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
		String editModelObject = CommonUtils.getLowerCaseFirstChar(editModelClass);
		code += String.format("\r\n\tpublic %1$s get%1$s(Integer id) {\r\n"
				+ "\t\treturn new %1$s(%3$s%4$s.get%2$sById(id));\r\n" + "\t}", editModelClass, entityName,
				entityName.toLowerCase(), Const.DOMAIN);

		code += String.format(
				"\r\n\tpublic String save%1$s(%3$s %4$s) {\r\n"
						+ "\t\t%1$s %2$s=%2$s%5$s.save%1$s(this.get%1$sEntity(%4$s));\r\n"
						+ "\t\treturn \"The %2$s have been saved succesfully!\";\r\n" + "\t}",
				entityName, entityName.toLowerCase(), editModelClass, editModelObject, Const.DOMAIN);

		code += String.format("\r\n\tprivate %1$s get%1$sEntity(%3$s %4$s) {\r\n" + "\t\t%1$s %2$s=new %1$s();\r\n",
				entityName, entityName.toLowerCase(), editModelClass, editModelObject);

		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			code += String.format("\t\t%1$s.set%3$s(%2$s.get%3$s());\r\n", entityName.toLowerCase(), editModelObject,
					CommonUtils.getUpperCaseFirstChar(field.getName()));
		}
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				code += String.format("\t\t%1$s.set%2$s(%3$s%6$s.getList%4$sByIds(%5$s.get%2$s()));\r\n",
						entityName.toLowerCase(), CommonUtils.getUpperCaseFirstChar(joinTable.getField()),
						joinTable.getType().toLowerCase(), joinTable.getType(), editModelObject, Const.DOMAIN);
			}
		}
		code += String.format("\t\treturn %1$s;\r\n\t}\r\n", entityName.toLowerCase());

		// if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
		// for (JoinTable joinTable :
		// omnibusDTO.getTransaction().getEntity().getJoinTables()) {
		// code += String.format(
		// "\r\n\tpublic List<%1$s%7$s> get%1$sJoin%3$sList(Integer %4$sId) {\r\n"
		// + " List<%1$s%7$s> returnList=new ArrayList<%1$s%7$s>(); \r\n"
		// + " for (%1$s %2$s: %2$s%6$s.getAll%1$s()) {\r\n"
		// + " %1$s%7$s %2$sJoin=new %1$s%7$s();\r\n"
		// + " %2$sJoin.set%1$sId(%2$s.getId());\r\n"
		// + " %2$sJoin.set%1$sName(%2$s.getName());\r\n"
		// + " List<%1$s> %4$s%1$sAll= %4$s%6$s.get%3$sById(%4$sId).get%5$s();\r\n"
		// + " if (%4$s%1$sAll!=null) {\r\n"
		// + " for (%1$s %4$s%1$s:%4$s%1$sAll) {\r\n"
		// + " if (%2$s.getId()==%4$s%1$s.getId()) {\r\n"
		// + " %2$sJoin.setIsChecked(\"checked\");\r\n"
		// + " break;\r\n" + " }\r\n" + " }\r\n"
		// + " }\r\n" + " returnList.add(%2$sJoin);\r\n" + " } \r\n"
		// + " return returnList;\r\n" + " }",
		// joinTable.getType(), joinTable.getType().toLowerCase(), entityName,
		// entityName.toLowerCase(),
		// CommonUtils.getUpperCaseFirstChar(joinTable.getField()), Const.SERVICE,
		// Const.JOINLIST_DTO);
		// }
		// }
		return code;
	}

	private String getPrivateField(String type) {
		return String.format("\r\n\t@Autowired\r\n" + "\tprivate %1$s %2$s" + JavaConst.NEWLINE, type,
				CommonUtils.getLowerCaseFirstChar(type));
	}

	private String getPrivateField(String entity, String type) {
		return String.format("\r\n\t@Autowired\r\n" + "\tprivate %1$s%3$s %2$s%3$s" + JavaConst.NEWLINE, entity,
				entity.toLowerCase(), type);
	}

}
