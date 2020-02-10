package com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.domain;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;

import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;
import com.kientpham.motherofcode.utils.PackageUtils;

public class ServiceMethodDomain {

	private String entityName;

	private String editDomain;

	private String businessDomainName;

	private String lookupService;

	private String lookupEntity;

	private String idType;

	public ServiceMethodDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		entityName = omnibusDTO.getTransaction().getEntity().getName();
		editDomain = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
		idType = omnibusDTO.getTransaction().getEntity().getIdType();
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
				"\r\n\t@Autowired\r\n" + "	private %4$s %2$s;\r\n" + "\r\n\t@Override\r\n"
						+ "	public %1$s get%1$sById(%3$s id) {\r\n" + "		return new %1$s(%2$s.findById(id));\r\n"
						+ "	}\r\n",
				editDomain, CommonUtils.getLowerCaseFirstChar(businessDomainName), idType, businessDomainName);
	}

	private String buildMethodReadAdv(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String dataTableDTO = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain());

		return this.getPrivateField(lookupService) + String.format(
				"\r\n\t@Override\r\n\tpublic List<%5$s> getList%5$s() {\r\n"
						+ "		Map<Integer, String> lookupMap = %4$s.getMapAll%3$s();\r\n"
						+ "		List<%1$s> all%1$s = %6$s.findAll();\r\n"
						+ "		List<%5$s> %2$sTable = new ArrayList<%5$s>();\r\n"
						+ "		for (%1$s %2$s :all%1$s) {\r\n"
						+ " 			%2$sTable.add(new %5$s(%2$s, lookupMap));\r\n" + "		}\r\n"
						+ "		return %2$sTable;\r\n" + "	}\r\n",
				entityName, entityName.toLowerCase(), lookupEntity, CommonUtils.getLowerCaseFirstChar(lookupService),
				dataTableDTO, CommonUtils.getLowerCaseFirstChar(businessDomainName));
	}

	private String buildListJoinDTO(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				String joinType = CommonUtils.getObjectNameFromDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getJoinListDomain());
				String joinDomainObject = CommonUtils.getObjectNameFromDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getBussinessDomain());
				String joinEntityObject = CommonUtils.getObjectNameFromDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getEntityDomain());
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)
						|| joinTable.getRelation().equals(JavaConst.ONETOMANY)) {
					Entity joinEntity = omnibusDTO.getSharedDTO().getEntityByName(joinEntityObject);
					code += this.getPrivateField(joinDomainObject) + String.format(
							"\r\n\t@Override\r\n\tpublic List<%1$s> get%1$s(%7$s %4$sId) {\r\n"
									+ "		if (%4$sId!=null) {\r\n"
									+ "\t\t\treturn %3$s.get%1$s(%5$s.findById(%4$sId).get%6$ss().stream().map(%6$s::get%8$s).collect(Collectors.toSet()));\r\n"
									+ "		}\r\n" + "		return %3$s.get%1$s(null);\r\n" + "	}\r\n",
							joinType, joinDomainObject, CommonUtils.getLowerCaseFirstChar(joinDomainObject),
							entityName.toLowerCase(), CommonUtils.getLowerCaseFirstChar(businessDomainName),
							joinEntityObject, idType, CommonUtils.getUpperCaseFirstChar(joinEntity.getIdFieldName()));
				}
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
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getBussinessDomain()));
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
		String editModelClass = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
		String editModelObject = CommonUtils.getLowerCaseFirstChar(editModelClass);
		return String.format(
				"\r\n\t@Override\r\n\tpublic %3$s save%1$s(%3$s %4$s) {\r\n" + "\t\t%1$s %2$s=%5$s.get%1$sEntity(%4$s);\r\n"
						+ "\t\t%6$s;" + "\t\t%5$s.save(%2$s);\r\n"
						+ "\t\treturn %4$s;\r\n" + "\t}",
				entityName, entityName.toLowerCase(), editModelClass, editModelObject,
				CommonUtils.getLowerCaseFirstChar(businessDomainName), this.buildGetListJoinEntity(omnibusDTO));
	}

	private String buildGetListJoinEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		String editModelClass = CommonUtils.getLowerCaseFirstChar(PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain()));
		if (omnibusDTO.getTransaction().getEntity().hasJoinTable()) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				String joinDomainObject = CommonUtils.getObjectNameFromDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getBussinessDomain());
				String joinEntityObject = CommonUtils.getObjectNameFromDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getEntityDomain());
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)
						|| joinTable.getRelation().equals(JavaConst.ONETOMANY)) {
					code += String.format("%1$s.set%2$ss(%3$s.findByListIds(%4$s.get%2$ss()));\r\n",
							CommonUtils.getLowerCaseFirstChar(entityName), joinEntityObject, CommonUtils.getLowerCaseFirstChar(joinDomainObject),
							editModelClass);
				}
			}
		}
		return code;
	}

	private String getPrivateField(String type) {
		return String.format("\r\n\t@Autowired\r\n" + "\tprivate %1$s %2$s" + JavaConst.NEWLINE, type,
				CommonUtils.getLowerCaseFirstChar(type));
	}

}
