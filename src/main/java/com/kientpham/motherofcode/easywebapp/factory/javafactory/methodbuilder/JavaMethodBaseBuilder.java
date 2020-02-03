package com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.domain.ServiceMethodDomain;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.FullDomainDTO;
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
		String service = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getReadService());

		String code = this.getField(service);

		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		code += String.format("\r\n\t@RequestMapping(value=\"/get%1$s\", method=RequestMethod.POST)\r\n"
				+ "	public List<%1$s> get%1$s(){\r\n" + "		return %2$s.getList%1$s();		\r\n" + "	}\r\n",
				tableModel, CommonUtils.getLowerCaseFirstChar(service));
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				String joinListType = CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFullDomainDTO(joinTable.getType())
						.getJoinListDomain());
				code += String.format(
						"\r\n\t@RequestMapping(value=\"/get%3$s\", method=RequestMethod.POST)\r\n"
								+ "	public List<%3$s> get%3$s(@RequestBody(required=true) int id){\r\n"
								+ "		return %2$s.get%3$s(id);\r\n" + "	}",
						entityName,  CommonUtils.getLowerCaseFirstChar(service), joinListType);
			}
		}
//		code += String.format(
//				"\r\n\t@RequestMapping(value=\"/get%1$s\",method=RequestMethod.GET)\r\n"
//						+ "	public ResponseEntity<%2$s> get%1$s(@RequestParam(value=\"id\") Integer id){\r\n"
//						+ "		return new ResponseEntity<%2$s>(%3$s.get%2$s(id),HttpStatus.OK);\r\n" + "	}\r\n",
//				entityName, editModel, CommonUtils.getLowerCaseFirstChar(service));
//
//		code += String.format(
//				"\r\n\t@RequestMapping(value=\"/save%1$sn\", method=RequestMethod.POST)\r\n"
//						+ "	public String save%1$s(@RequestBody(required=true) %2$s %4$s) {\r\n"
//						+ "		return %3$s.save%1$s(%4$s);\r\n" + "	}\r\n",
//				entityName, editModel, CommonUtils.getLowerCaseFirstChar(service),
//				CommonUtils.getLowerCaseFirstChar(editModel));
//
//		code += String.format(
//				"\r\n\t@RequestMapping(value=\"/delete%1$s\", method=RequestMethod.POST)\r\n"
//						+ "	public String delete%1$s(@RequestBody(required=true) List<Integer> ids) {\r\n"
//						+ "		return %2$s.delete%1$s(ids);\r\n}\r\n",
//				entityName, CommonUtils.getLowerCaseFirstChar(service));

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
		return String.format(
				"\r\n\tprivate static final long serialVersionUID = 1L;\r\n" + "\r\n\tprivate Integer %1$sId;\r\n"
						+ "\r\n\tprivate String %1$sName;\r\n" + "\r\n\tprivate String isChecked;",
				omnibusDTO.getTransaction().getEntity().getName().toLowerCase());
	}

	@Override
	public String buildMethodForEditModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			code += String.format("\tprivate %1$s %2$s;\r\n", field.getType(), field.getName());
		}
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				code += String.format("\tprivate List<%1$s> %2$s;", "Integer", joinTable.getField()) + "\r\n";
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
		code += String.format("\r\n\tpublic %1$s(%2$s %3$s,Map<Integer,String> categoryMap) {\r\n",
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
				code += String.format("\t\t\tthis.%1$s=categoryMap.get(%2$s.get%3$s());\r\n", field.getName(),
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
						+ "\r\n"
						+ String.format("\tprivate List<%1$s> %2$s;", joinTable.getType(), joinTable.getField())
						+ "\r\n";
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
		String dbGatewayInterface = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getDbGateway());
		String s = String.format("	@Autowired\r\n" + "	private %1$s dbGateway;\r\n" + "	\r\n"
				+ "	public List<%2$s> findAll(){\r\n" + "		return dbGateway.findAll();\r\n" + "	}\r\n"
				+ "	\r\n" + "	public List<%2$s> findByListIds(List<%3$s> ids){\r\n"
				+ "		return dbGateway.findByListIds(ids);\r\n" + "	}\r\n" + "	\r\n"
				+ "	public %2$s findById(%3$s id) {\r\n" + "		return dbGateway.findById(id);\r\n" + "	}\r\n"
				+ "	\r\n" + "	public void deleteById(%3$s id) {\r\n" + "		dbGateway.deleteById(id);\r\n"
				+ "	}\r\n" + "	\r\n" + "	public %2$s save(%2$s entity) {\r\n"
				+ "		return dbGateway.save(entity);\r\n" + "	}\r\n", dbGatewayInterface,
				omnibusDTO.getTransaction().getEntity().getName(),
				omnibusDTO.getTransaction().getEntity().getFields().get(0).getType());
		return s;
	}

	@Override
	public String buildMethodForWriteService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(
				"\tpublic void delete%1$sById(%2$s id);\r\n" + "\r\n"
						+ "	public void deleteList%1$s(List<%2$s> ids);\r\n" + "	\r\n"
						+ "	public %1$s save%1$s(%1$s entity);\r\n",
				omnibusDTO.getTransaction().getEntity().getName(),
				omnibusDTO.getTransaction().getEntity().getFields().get(0).getType());
	}

	@Override
	public String buildMethodForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";		
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {				
				String joinType= CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getJoinListDomain());				
				
				code += String.format("\r\n\tpublic List<%1$s> get%1$s(Integer %2$sId);\r\n",joinType,CommonUtils.getLowerCaseFirstChar(omnibusDTO.getTransaction().getEntity().getName()));
			}
		}
		return code + String.format(
				"\r\n\tpublic List<%1$s> getAll%1$s();\r\n"
						+ "\r\n\tpublic List<%1$s> getList%1$sByIds(List<%2$s> listId);\r\n"
						+ "\r\n\tpublic %1$s get%1$sById(%2$s id);" + "\r\n\tpublic List<%3$s> getList%3$s();\r\n",
				omnibusDTO.getTransaction().getEntity().getName(),
				omnibusDTO.getTransaction().getEntity().getFields().get(0).getType(), CommonUtils
						.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain()));
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
