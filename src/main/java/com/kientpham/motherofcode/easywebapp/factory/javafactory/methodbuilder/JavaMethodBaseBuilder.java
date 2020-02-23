package com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder;

import java.util.List;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.domain.ServiceMethodDomain;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;
import com.kientpham.motherofcode.utils.Const;
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

		String idType = omnibusDTO.getTransaction().getEntity().getIDField().getType();

		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		code += String.format("\r\n\t@RequestMapping(value=\"/%3$s\", method=RequestMethod.GET)\r\n"
				+ "	public List<%1$s> get%1$s(){\r\n" + "		return %2$s.getList%1$s();		\r\n" + "	}\r\n",
				tableModel, CommonUtils.getLowerCaseFirstChar(readService), tableModel.toLowerCase());
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				String joinListType = CommonUtils.getObjectNameFromDomain(
						omnibusDTO.getSharedDTO().getFullDomainDTO(joinTable.getType()).getJoinListDomain());
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)) {
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
						+ "\t\t%3$s.save%1$s(%4$s);\r\n" + "\t\treturn \"Saved Successfully!\";\r\n" + "\t}\r\n",
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
		Entity entity = omnibusDTO.getTransaction().getEntity();
		String titleField = "";
		for (String title : entity.getTitleField().split(",")) {
			titleField += String.format("\r\n\tprivate String %1$s;\r\n", title);
		}

		return String.format(
				"\r\n\tprivate static final long serialVersionUID = 1L;\r\n" + "\r\n\tprivate %1$s %2$s;\r\n" + "\t%3$s"
						+ "\r\n\tprivate String isChecked;",
				entity.getIDField().getType(), entity.getIDField().getName(), titleField);
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
						omnibusDTO.getSharedDTO().getEntityByName(joinTable.getType()).getIDField().getType(),
						joinTable.getField()) + "\r\n";
			}
		}
		code += String.format("\r\n\tpublic %1$s() {\r\n\t\t//default constructor\r\n\t}", PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain()));
		String entityName = omnibusDTO.getTransaction().getEntity().getName();

		String fields = "";
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			String fieldName = CommonUtils.getUpperCaseFirstChar(field.getName());
			fields += String.format("\t\t\tthis.%1$s=%2$s.get%3$s();\r\n", field.getName(), entityName.toLowerCase(),
					fieldName);
		}

		code += String.format(
				"\r\n\tpublic %1$s(%2$s %3$s) {\r\n" + "\t\tif(%3$s !=null){\r\n" + "%4$s" + "\t\t}\r\n\t}\r\n",
				PackageUtils
						.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain()),
				entityName, entityName.toLowerCase(), fields);

		return code;
	}

	@Override
	public String buildMethodForTableModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			if (field.isIdOrShowInTable()) {
				if (field.isIdField()) {
					code += String.format("\tprivate %1$s %2$s;\r\n", field.getType(), field.getName());
				} else {
					code += String.format("\tprivate String %1$s;\r\n", field.getName());
				}
			}
		}
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String entityObj=CommonUtils.getLowerCaseFirstChar(entityName);
		code += String.format("\r\n\tpublic %1$s(%2$s %3$s, Map<Integer,String> lookupMap) {\r\n",
				PackageUtils
						.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain()),
				entityName, entityObj);
		code += String.format("\t\tif(%1$s !=null){\r\n", entityObj);
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			if (field.isIdOrShowInTable()) {
				String toString = (!field.isIdField() && !field.getType().equalsIgnoreCase(JavaConst.STRING)) ? ".toString()"
						: "";
				String fieldName = CommonUtils.getUpperCaseFirstChar(field.getName());
				if (!field.getLookupType().isEmpty()) {
					code += String.format("\t\t\tthis.%1$s=lookupMap.get(%2$s.get%3$s());\r\n", field.getName(),
							entityObj, fieldName);
				} else {
					if (!field.getType().equalsIgnoreCase(JavaConst.STRING)) {
						code+=String.format("\t\t\tif (%1$s.get%2$s()!=null)\r\n\t", entityObj,fieldName);
					}
					if (field.getType().equals(Const.DATE)) {
						String dateStringUtil=CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getDateStringUtils());
						code += String.format("\t\t\tthis.%1$s=%4$s.convertDateToString(%2$s.get%3$s(), %4$s.DISPLAY_DATE_PATTERN);\r\n", field.getName(),
								entityObj, fieldName, dateStringUtil);
					}else {
						code += String.format("\t\t\tthis.%1$s=%2$s.get%3$s()%4$s;\r\n", field.getName(),
							entityObj, fieldName, toString);
					}
				}
			}
		}
		code += "\t\t}\r\n\t}";
		return code;
	}

	@Override
	public String buildMethodForEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			if (field.isIdField())
				code += "\t@Id\r\n";

			if (field.getFieldType().equals(Const.AUTO))
				code += "\t@GeneratedValue(strategy=GenerationType.IDENTITY)\r\n";

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
		Entity entity = omnibusDTO.getTransaction().getEntity();
		String entityName = entity.getName();
		String dbGatewayInterface = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getDbGateway());
		String code = String.format("	@Autowired\r\n" + "	private %1$s dbGateway;\r\n" + "	\r\n",
				dbGatewayInterface);
		List<JoinTable> listJoinTable = entity.getJoinTables();
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
				entity.getIDField().getType());

		String editModelClass = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
		String editModelObject = CommonUtils.getLowerCaseFirstChar(editModelClass);

		code += String.format("\r\n\tpublic %1$s get%1$sEntity(%3$s %4$s) {\r\n" + "\t\t%1$s %2$s=new %1$s();\r\n",
				entityName, entityName.toLowerCase(), editModelClass, editModelObject);

		if (!entity.getIDField().getFieldType().equals(Const.AUTO) || !entity.getCreatedDateField().isEmpty()) {
			String condition = "";
			if (entity.getIDField().getType().equals(JavaConst.STRING)) {
				condition = String.format("%1$s.get%2$s().isEmpty()", editModelObject,
						CommonUtils.getUpperCaseFirstChar(entity.getIDField().getName()));
			} else {
				condition = String.format("%1$s.get%2$s()==null", editModelObject,
						CommonUtils.getUpperCaseFirstChar(entity.getIDField().getName()));
			}
			code += String.format("\t\tif (%1$s) {\r\n" + "			%2$s" + "			%3$s" + "\t\t}\r\n", condition,
					this.getGenerateID(omnibusDTO), this.setCreatedDate(omnibusDTO));
		}

		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			if (field.getFieldType().equals(Const.LAST_UPDATED)) {
				code += String.format("\t\t%1$s.set%2$s(%3$s.getCurentTimeUTC());\r\n",
						CommonUtils.getLowerCaseFirstChar(entityName),
						CommonUtils.getUpperCaseFirstChar(field.getName()), PackageUtils.getObjectNameFromDomain(
								omnibusDTO.getSharedDTO().getFixDomainDTO().getDateStringUtils()));
			} else {
				code += String.format("\t\t%1$s.set%3$s(%2$s.get%3$s());\r\n", entityName.toLowerCase(),
						editModelObject, CommonUtils.getUpperCaseFirstChar(field.getName()));
			}
		}
		code += String.format("\t\treturn %1$s;\r\n\t}\r\n", entityName.toLowerCase());
		return code;
	}

	private String getGenerateID(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String editModelObject = CommonUtils.getLowerCaseFirstChar(PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain()));
		Entity entity = omnibusDTO.getTransaction().getEntity();

		if (entity.getIDField().getFieldType().equals(Const.AUTO))
			return "";
		String value = "";
		if (entity.getIDField().getType().equals(Const.UUID)) {
			value = "UUID.randomUUID()";
		} else if (entity.getIDField().getType().equals(JavaConst.STRING)) {
			if (entity.getIDField().getFieldType().equals(Const.UUID))
				value = "UUID.randomUUID().toString()";
			else if (entity.getIDField().getFieldType().contains(Const.RANDOM)) {
				String[] randomArr = entity.getIDField().getFieldType().split("_");
				int n = 8;
				if (randomArr.length > 1)
					n = Integer.valueOf(randomArr[1]);
				value = String.format("%1$s.getRandomString(%2$s)", PackageUtils
						.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getDateStringUtils()), n);
			}
		} else if (entity.getIDField().getType().equals(JavaConst.INTEGER)
				|| entity.getIDField().getType().equals(JavaConst.LONG)) {
			value = String.format("%1$s.hashCode()", editModelObject);
		}

		return String.format("%1$s.set%2$s(%3$s);\r\n", editModelObject,
				CommonUtils.getUpperCaseFirstChar(entity.getIDField().getName()), value);

	}

	private String setCreatedDate(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String editModelObject = CommonUtils.getLowerCaseFirstChar(PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain()));
		Entity entity = omnibusDTO.getTransaction().getEntity();
		return (!entity.getCreatedDateField().isEmpty()) ? String.format("%1$s.set%2$s(%3$s.getCurentTimeUTC());\r\n",
				editModelObject, CommonUtils.getUpperCaseFirstChar(entity.getCreatedDateField()),
				PackageUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getDateStringUtils()))
				: "";
	}

	private String buildGetJoinListDTOMethod(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String idType = omnibusDTO.getTransaction().getEntity().getIDField().getType();
		String idField = CommonUtils
				.getUpperCaseFirstChar(omnibusDTO.getTransaction().getEntity().getIDField().getName());
		String titleField = "";
		for (String title : omnibusDTO.getTransaction().getEntity().getTitleField().split(",")) {
			titleField += String.format("\t\t\t%1$sJoin.set%2$s(%1$s.get%2$s());\r\n", entityName.toLowerCase(),
					CommonUtils.getUpperCaseFirstChar(title));
		}
		String joinListDTO = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getJoinListDomain());
		return String.format("\r\n\tpublic List<%3$s> get%3$s(Set<%4$s> ids) {\r\n"
				+ "		List<%3$s> returnList=new ArrayList<%3$s>(); \r\n"
				+ "		List<%1$s> all%1$s=this.findAll(); \r\n" + "		for (%1$s %2$s: all%1$s) {\r\n"
				+ "			%3$s %2$sJoin=new %3$s();\r\n" + "			%2$sJoin.set%5$s(%2$s.get%5$s());\r\n" + "%6$s"
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
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)) {
					code += String.format("\r\n\tpublic List<%1$s> get%1$s(%3$s %2$sId);\r\n", joinType,
							CommonUtils.getLowerCaseFirstChar(omnibusDTO.getTransaction().getEntity().getName()),
							omnibusDTO.getTransaction().getEntity().getIDField().getType());
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
