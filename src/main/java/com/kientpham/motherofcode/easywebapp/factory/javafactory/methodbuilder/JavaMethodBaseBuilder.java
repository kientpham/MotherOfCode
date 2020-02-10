package com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder;

import java.util.List;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.domain.ServiceMethodDomain;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;
import com.kientpham.motherofcode.utils.PackageUtils;

public class JavaMethodBaseBuilder implements MethodBuilderInterface {

	@Override
	public String buildMethodForController(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		String tableModel = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain());
		String editModel = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
		String readService = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getReadService());

		String writeService = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getWriteService());

		String code = this.getField(readService) + this.getField(writeService);

		String idType = omnibusDTO.getTransaction().getEntity().getIdType();

		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		code += String.format("\r\n\t@RequestMapping(value=\"/%3$s\", method=RequestMethod.GET)\r\n"
				+ "	public List<%1$s> get%1$s(){\r\n" + "		return %2$s.getList%1$s();		\r\n" + "	}\r\n",
				tableModel, CommonUtils.getLowerCaseFirstChar(readService), tableModel.toLowerCase());
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				String joinListType = CommonUtils.getObjectNameFromDomain(
						omnibusDTO.getSharedDTO().getFullDomainDTO(joinTable.getType()).getJoinListDomain());
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)
						|| joinTable.getRelation().equals(JavaConst.ONETOMANY)) {
					code += String.format(
							"\r\n\t@RequestMapping(value=\"/%4$s\", method=RequestMethod.GET)\r\n"
									+ "	public List<%3$s> get%3$s(@RequestParam %5$s id){\r\n"
									+ "		return %2$s.get%3$s(id);\r\n" + "	}",
							entityName, CommonUtils.getLowerCaseFirstChar(readService), joinListType,
							joinListType.toLowerCase(), idType);
				}
			}
		}
		code += String.format(
				"\r\n\t@RequestMapping(value=\"%1$s\",method=RequestMethod.GET)\r\n"
						+ "	public ResponseEntity<%2$s> get%1$s(@RequestParam %4$s id){\r\n"
						+ "		return new ResponseEntity<%2$s>(%3$s.get%2$sById(id),HttpStatus.OK);\r\n" + "	}\r\n",
				editModel.toLowerCase(), editModel, CommonUtils.getLowerCaseFirstChar(readService), idType);
		
		 code += String.format(
		 "\r\n\t@RequestMapping(value=\"/%5$s\", method=RequestMethod.POST)\r\n"
		 + "\tpublic String save%1$s(@RequestBody(required=true) %2$s %4$s) {\r\n"
		 + "\t\t%3$s.save%1$s(%4$s);\r\n"
		 + "\t\treturn \"Saved Successfully!\";\r\n"
		 + "\t}\r\n",
		 entityName, editModel, CommonUtils.getLowerCaseFirstChar(writeService),
		 CommonUtils.getLowerCaseFirstChar(editModel), editModel.toLowerCase());
		
		code += String.format(
				"\r\n\t@RequestMapping(method=RequestMethod.DELETE)\r\n"
						+ "	public String delete%1$s(@RequestBody(required=true) %3$s id) {\r\n"
						+ "		%2$s.delete%1$sById(id);\r\n" + "		return \"Successfully\";" + "\r\n\t}\r\n",
				entityName, CommonUtils.getLowerCaseFirstChar(writeService), idType);

		return code;
	}

	private String getField(String type) {
		return String.format("\t@Autowired\r\n\tprivate %1$s %2$s" + JavaConst.NEWLINE, type,
				CommonUtils.getLowerCaseFirstChar(type));
	}

	@Override
	public String buildMethodForJoinList(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() == null)
			return "";
		Entity entity=omnibusDTO.getTransaction().getEntity();
		String titleField="";
		for (String title:entity.getTitleField().split(",")) {
			titleField+=String.format("\r\n\tprivate String %1$s;\r\n", title);
		}
		
		return String.format(
				"\r\n\tprivate static final long serialVersionUID = 1L;\r\n" + "\r\n\tprivate %1$s %2$s;\r\n"
						+ "\t%3$s" + "\r\n\tprivate String isChecked;",
						entity.getIdType(), entity.getIdFieldName(),titleField);
	}

	@Override
	public String buildMethodForEditModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			code += String.format("\tprivate %1$s %2$s;\r\n", field.getType(), field.getName());
		}
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				code += String.format("\tprivate List<%1$s> %2$s;",
						omnibusDTO.getSharedDTO().getEntityByName(joinTable.getType()).getIdType(),
						joinTable.getField()) + "\r\n";
			}
		}
		code += String.format("\r\n\tpublic %1$s() {\r\n\t\t//default constructor\r\n\t}", PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain()));
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		code += String.format("\r\n\tpublic %1$s(%2$s %3$s) {\r\n",
				PackageUtils
						.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain()),
				entityName, entityName.toLowerCase());
		code += String.format("\t\tif(%1$s !=null){\r\n", entityName.toLowerCase());
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			String fieldName = CommonUtils.getUpperCaseFirstChar(field.getName());
			code += String.format("\t\t\tthis.%1$s=%2$s.get%3$s();\r\n", field.getName(), entityName.toLowerCase(),
					fieldName);
		}

		code += "\t\t}\r\n\t}";
		return code;
	}

	@Override
	public String buildMethodForTableModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			if (field.getNonViewable() == null) {
				if (field.getIdentity() != null) {
					code += String.format("\tprivate %1$s %2$s;\r\n", field.getType(), field.getName());
				} else {
					code += String.format("\tprivate String %1$s;\r\n", field.getName());
				}
			}
		}
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		code += String.format("\r\n\tpublic %1$s(%2$s %3$s,Map<Integer,String> lookupMap) {\r\n",
				PackageUtils
						.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain()),
				entityName, entityName.toLowerCase());
		code += String.format("\t\tif(%1$s !=null){\r\n", entityName.toLowerCase());
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			if (field.getNonViewable() != null)
				continue;
			String toString = (field.getIdentity() == null && !field.getType().equalsIgnoreCase("String"))
					? ".toString()"
					: "";
			String fieldName = CommonUtils.getUpperCaseFirstChar(field.getName());
			if (field.getLookupType() != null) {
				code += String.format("\t\t\tthis.%1$s=lookupMap.get(%2$s.get%3$s());\r\n", field.getName(),
						entityName.toLowerCase(), fieldName);
			} else {
				code += String.format("\t\t\tthis.%1$s=%2$s.get%3$s()%4$s;\r\n", field.getName(),
						entityName.toLowerCase(), fieldName, toString);
			}
		}
		code += "\t\t}\r\n\t}";
		return code;
	}

	@Override
	public String buildMethodForEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			if (field.getIdentity() != null)
				code += "\t@Id\r\n" + "\t@GeneratedValue(strategy=GenerationType.IDENTITY)\r\n";

			code += String.format("\t@Column(name = \"%1$s\")\r\n", field.getColumn());
			code += String.format("\tprivate %1$s %2$s;\r\n", field.getType(), field.getName());
		}

		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				code += "\t" + joinTable.getRelation() + "\r\n" + String.format(
						"\t@JoinTable(name = \"%1$s\", joinColumns = { @JoinColumn(name = \"%2$s\") }, inverseJoinColumns = {\r\n"
								+ "\t\t@JoinColumn(name = \"%3$s\") })",
						joinTable.getColumnName(), joinTable.getJoinColumns(), joinTable.getInverseJoinColumns())
						+ "\r\n";
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)
						|| joinTable.getRelation().equals(JavaConst.ONETOMANY)) {
					code += String.format("\tprivate List<%1$s> %2$s;", joinTable.getType(), joinTable.getField())
							+ "\r\n";
				} else {
					code += String.format("\tprivate %1$s %2$s;", joinTable.getType(), joinTable.getField()) + "\r\n";
				}

			}
		}
		code += String.format("\tpublic %1$s() {\r\n\t\t//default constructor\r\n\t}",
				omnibusDTO.getTransaction().getEntity().getName());
		return code;

	}

	@Override
	public String buildMethodForDBGateway(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format("\t@Autowired\r\n" + "\tprivate %3$s repository;\r\n\r\n" + "\t@Override\r\n"
				+ "\tpublic List<%1$s> findAll() {\r\n" + "\t\treturn (List<%1$s>) repository.findAll();\r\n"
				+ "\t}\r\n\r\n" + "\t@Override\r\n" + "\tpublic %1$s findById(%2$s id) {\r\n"
				+ "\t\treturn repository.findById(id).orElse(new %1$s());\r\n" + "\t}\r\n\r\n" + "\t@Override\r\n"
				+ "	public List<%1$s> findByListIds(List<%2$s> listId) {		\r\n"
				+ "		return (List<%1$s>) repository.findAllById(listId);\r\n" + "	}\r\n\r\n" + "\t@Override\r\n"
				+ "\tpublic void deleteById(%2$s id) {\r\n" + "\t\trepository.deleteById(id);		\r\n"
				+ "\t}\r\n\r\n" + "\t@Override\r\n" + "\tpublic %1$s save(%1$s entity) {\r\n"
				+ "\t\treturn repository.save(entity);\r\n" + "\t}\r\n",
				omnibusDTO.getTransaction().getEntity().getName(),
				omnibusDTO.getTransaction().getEntity().getFields().get(0).getType(), PackageUtils
						.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getRepositoryDomain()));
	}

	@Override
	public String buildMethodForDBGatewayInterface(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(
				"\tpublic List<%1$s> findAll();\r\n\r\n" + "\tpublic %1$s findById(%2$s id);\r\n\r\n"
						+ "\tpublic List<%1$s> findByListIds(List<%2$s> listId);\r\n\r\n"
						+ "\tpublic void deleteById(%2$s id);\r\n\r\n" + "\tpublic %1$s save(%1$s entity);\r\n\r\n",
				omnibusDTO.getTransaction().getEntity().getName(),
				omnibusDTO.getTransaction().getEntity().getFields().get(0).getType());
	}

	@Override
	public String buildMethodForBusinessObject(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String dbGatewayInterface = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getDbGateway());
		String code = String.format("	@Autowired\r\n" + "	private %1$s dbGateway;\r\n" + "	\r\n",
				dbGatewayInterface);
		List<JoinTable> listJoinTable = omnibusDTO.getTransaction().getEntity().getJoinTables();
		if (listJoinTable != null) {
			for (JoinTable joinTable : listJoinTable) {
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)) {
					code += buildGetJoinListDTOMethod(omnibusDTO);
					break;
				}
			}
		}
		code += String.format("\r\n\tpublic List<%2$s> findAll(){\r\n" + "		return dbGateway.findAll();\r\n"
				+ "	}\r\n" + "	\r\n" + "	public List<%2$s> findByListIds(List<%3$s> ids){\r\n"
				+ "		return dbGateway.findByListIds(ids);\r\n" + "	}\r\n" + "	\r\n"
				+ "	public %2$s findById(%3$s id) {\r\n" + "		if (id==null) return null;\r\n"
				+ "		return dbGateway.findById(id);\r\n" + "	}\r\n" + "\r\n	public void deleteById(%3$s id) {\r\n"
				+ "		dbGateway.deleteById(id);\r\n" + "	}\r\n" + "	\r\n" + "	public %2$s save(%2$s entity) {\r\n"
				+ "		return dbGateway.save(entity);\r\n" + "	}\r\n", dbGatewayInterface, entityName,
				omnibusDTO.getTransaction().getEntity().getFields().get(0).getType());

		String editModelClass = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
		String editModelObject = CommonUtils.getLowerCaseFirstChar(editModelClass);

		code += String.format("\r\n\tpublic %1$s get%1$sEntity(%3$s %4$s) {\r\n" + "\t\t%1$s %2$s=new %1$s();\r\n",
				entityName, entityName.toLowerCase(), editModelClass, editModelObject);

		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			code += String.format("\t\t%1$s.set%3$s(%2$s.get%3$s());\r\n", entityName.toLowerCase(), editModelObject,
					CommonUtils.getUpperCaseFirstChar(field.getName()));
		}
		code += String.format("\t\treturn %1$s;\r\n\t}\r\n", entityName.toLowerCase());
		return code;
	}

	private String buildGetJoinListDTOMethod(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String idType = omnibusDTO.getTransaction().getEntity().getIdType();
		String idField = CommonUtils.getUpperCaseFirstChar(omnibusDTO.getTransaction().getEntity().getIdFieldName());		
		String titleField="";
		for (String title:omnibusDTO.getTransaction().getEntity().getTitleField().split(",")) {
			titleField+=String.format("\t\t\t%1$sJoin.set%2$s(%1$s.get%2$s());\r\n", entityName.toLowerCase(),CommonUtils.getUpperCaseFirstChar(title));
		}
		String joinListDTO = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getJoinListDomain());
		return String.format("\r\n\tpublic List<%3$s> get%3$s(Set<%4$s> ids) {\r\n"
				+ "		List<%3$s> returnList=new ArrayList<%3$s>(); \r\n"
				+ "		List<%1$s> all%1$s=this.findAll(); \r\n" + "		for (%1$s %2$s: all%1$s) {\r\n"
				+ "			%3$s %2$sJoin=new %3$s();\r\n" + "			%2$sJoin.set%5$s(%2$s.get%5$s());\r\n"
				+ "%6$s"
				+ "			if (ids!=null && ids.contains(%2$s.get%5$s())) {\r\n"
				+ "				%2$sJoin.setIsChecked(\"checked\");\r\n" + "			}\r\n"
				+ "			returnList.add(%2$sJoin);\r\n" + "		} \r\n" + " 		return returnList;\r\n"
				+ "	}\r\n", entityName, entityName.toLowerCase(), joinListDTO, idType, idField, titleField);

	}

	@Override
	public String buildMethodForWriteService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String editDTO = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
		return String.format(
				"\r\n\tpublic void delete%1$sById(%2$s id);\r\n" + "\r\n"
						+ "\r\n\tpublic void deleteList%1$s(List<%2$s> ids);\r\n"
						+ "\r\n\tpublic %3$s save%1$s(%3$s %4$s);\r\n",
				omnibusDTO.getTransaction().getEntity().getName(),
				omnibusDTO.getTransaction().getEntity().getFields().get(0).getType(), editDTO,
				CommonUtils.getLowerCaseFirstChar(editDTO));
	}

	@Override
	public String buildMethodForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				String joinType = CommonUtils.getObjectNameFromDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getJoinListDomain());
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)
						|| joinTable.getRelation().equals(JavaConst.ONETOMANY)) {
					code += String.format("\r\n\tpublic List<%1$s> get%1$s(%3$s %2$sId);\r\n", joinType,
							CommonUtils.getLowerCaseFirstChar(omnibusDTO.getTransaction().getEntity().getName()),
							omnibusDTO.getTransaction().getEntity().getIdType());
				}
			}
		}
		String editModel = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
		return code + String.format(
				"\r\n\tpublic %4$s get%4$sById(%2$s id);\r\n" + "\r\n\tpublic List<%3$s> getList%3$s();\r\n",
				omnibusDTO.getTransaction().getEntity().getName(),
				omnibusDTO.getTransaction().getEntity().getFields().get(0).getType(), CommonUtils
						.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain()),
				editModel);
	}

	@Override
	public String buildMethodForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return new ServiceMethodDomain(omnibusDTO).buildMethodForReadServiceImpl(omnibusDTO);
	}

	@Override
	public String buildMethodForWriteServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return new ServiceMethodDomain(omnibusDTO).buildMethodForWriteServiceImpl(omnibusDTO);
	}

	@Override
	public String buildMethodForRepository(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}

	@Override
	public String buildMethodForRepositoryPaging(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

}
