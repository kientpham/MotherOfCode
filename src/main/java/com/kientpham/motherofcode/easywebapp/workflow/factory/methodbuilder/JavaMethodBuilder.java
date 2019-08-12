package com.kientpham.motherofcode.easywebapp.workflow.factory.methodbuilder;

import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.easywebapp.workflow.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.utils.PackageUtils;

public class JavaMethodBuilder implements MethodBuilderInterface {

	@Override
	public String buildMethodForEntity(TransactionModel transaction) {
		String code = "";
		for (Field field : transaction.getEntity().getFields()) {
			if (field.getIdentity() != null)
				code += "\t@Id\r\n" + "\t@GeneratedValue(strategy=GenerationType.IDENTITY)";

			code += String.format("\t@Column(name = \"%1$s\")", field.getColumn());
			code += String.format("\tprivate %1$s %2$s;\r\n", field.getType(), field.getName());
		}

		if (transaction.getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : transaction.getEntity().getJoinTables()) {
				code += "\t" + joinTable.getRelation() + "\r\n" + String.format(
						"\t@JoinTable(name = \"%1$s\", joinColumns = { @JoinColumn(name = \"%2$s\") }, inverseJoinColumns = {\r\n"
								+ "\t\t@JoinColumn(name = \"%3$s\") })",
						joinTable.getName(), joinTable.getJoinColumns(), joinTable.getInverseJoinColumns()) + "\r\n"
						+ String.format("\tprivate List<%1$s> %2$s;", joinTable.getType(), joinTable.getField())
						+ "\r\n";
			}
		}
		code += String.format("\tpublic %1$s() {\r\n\t\t//default constructor\r\n\t}",
				transaction.getEntity().getName());
		return code;

	}

	@Override
	public String buildMethodForDBGateway(TransactionModel transaction) {
		return String.format("\t@Autowired\r\n" + "\tprivate %3$s repository;\r\n\r\n" + "\t@Override\r\n"
				+ "\tpublic List<%1$s> findAll() {\r\n" + "\t\treturn (List<%1$s>) repository.findAll();\r\n"
				+ "\t}\r\n\r\n" + "\t@Override\r\n" + "\tpublic %1$s findById(%2$s id) {\r\n"
				+ "\t\treturn repository.findById(id).orElse(new %1$s());\r\n" + "\t}\r\n\r\n" + "\t@Override\r\n"
				+ "	public List<%1$s> findByListIds(List<%2$s> listId) {		\r\n"
				+ "		return (List<%1$s>) repository.findAllById(listId);\r\n" + "	}\r\n\r\n" + "\t@Override\r\n"
				+ "\tpublic void deleteById(%2$s id) {\r\n" + "\t\trepository.deleteById(id);		\r\n"
				+ "\t}\r\n\r\n" + "\t@Override\r\n" + "\tpublic %1$s save(%1$s entity) {\r\n"
				+ "\t\treturn repository.save(entity);\r\n" + "\t}\r\n", transaction.getEntity().getName(),
				transaction.getEntity().getFields().get(0).getType(),
				PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getRepositoryDomain()));
	}

	@Override
	public String buildMethodForDBGatewayInterface(TransactionModel transaction) {
		return String.format(
				"\tpublic List<%1$s> findAll();\r\n\r\n" + "\tpublic %1$s findById(%2$s id);\r\n\r\n"
						+ "\tpublic List<%1$s> findByListIds(List<%2$s> listId);\r\n\r\n"
						+ "\tpublic void deleteById(%2$s id);\r\n\r\n" + "\tpublic %1$s save(%1$s entity);\r\n\r\n",
				transaction.getEntity().getName(), transaction.getEntity().getFields().get(0).getType());
	}

	@Override
	public String buildMethodForBusinessObject(TransactionModel transaction) {
		String dbGatewayInterface = PackageUtils
				.getObjectNameFromDomain(transaction.getFullDomainDTO().getDbGatewayInterface());
		String s = String.format(
				"	@Autowired\r\n" + "	private %1$s dbGateway;\r\n" + "	\r\n"
						+ "	public List<%2$s> findAll(){\r\n" + "		return dbGateway.findAll();\r\n" + "	}\r\n"
						+ "	\r\n" + "	public List<%2$s> findByListIds(List<%3$s> ids){\r\n"
						+ "		return dbGateway.findByListIds(ids);\r\n" + "	}\r\n" + "	\r\n"
						+ "	public %2$s getById(%3$s id) {\r\n" + "		return dbGateway.findById(id);\r\n" + "	}\r\n"
						+ "	\r\n" + "	public void deleteById(%3$s id) {\r\n" + "		dbGateway.deleteById(id);\r\n"
						+ "	}\r\n" + "	\r\n" + "	public %2$s save(%2$s entity) {\r\n"
						+ "		return dbGateway.save(entity);\r\n" + "	}\r\n",
				dbGatewayInterface, transaction.getEntity().getName(),
				transaction.getEntity().getFields().get(0).getType());
		return s;
	}

	@Override
	public String buildMethodForServiceInterface(TransactionModel transaction) {
		return String.format("\tpublic List<%1$s> getAll%1$s();\r\n" + "	\r\n"
				+ "	public List<%1$s> getList%1$sByIds(List<%2$s> listId);\r\n" + "	\r\n"
				+ "	public %1$s get%1$sById(%2$s id);\r\n" + "	\r\n" + "	public void delete%1$sById(%2$s id);\r\n"
				+ "\r\n" + "	public void deleteList%1$s(List<%2$s> ids);\r\n" + "	\r\n"
				+ "	public %1$s save%1$s(%1$s entity);\r\n", transaction.getEntity().getName(),
				transaction.getEntity().getFields().get(0).getType());
	}

	@Override
	public String buildMethodForServiceClass(TransactionModel transaction) {
		String domainType = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getBussinessDomain());
		String entityName = transaction.getEntity().getName();
		String domainObject = entityName.toLowerCase() + "Domain";
		String idType = transaction.getEntity().getFields().get(0).getType();
		String s = String.format("	@Autowired\r\n" + "	private %4$s %2$s;	\r\n" + "	@Override\r\n"
				+ "	public List<%1$s> getList%1$sByIds(List<%3$s> listId) {		 \r\n"
				+ "		return %2$s.findByListIds(listId);\r\n" + "	}\r\n" + "	@Override\r\n"
				+ "	public List<%1$s> getAll%1$s() {		\r\n" + "		return (List<%1$s>)%2$s.findAll();\r\n"
				+ "	}\r\n" + "	@Override\r\n" + "	public %1$s get%1$sById(%3$s id) {\r\n"
				+ "		return %2$s.getById(id);\r\n" + "	}	\r\n" + "	@Override\r\n"
				+ "	public void deleteList%1$s(List<%3$s> ids) {\r\n" + "		for (%3$s id:ids) {\r\n"
				+ "			%2$s.deleteById(id);\r\n" + "		}		\r\n" + "	}\r\n" + "	@Override\r\n"
				+ "	public void delete%1$sById(%3$s id) {\r\n" + "		%2$s.deleteById(id);		\r\n" + "	}\r\n"
				+ "	@Override\r\n" + "	public %1$s save%1$s(%1$s entity) {\r\n"
				+ "		return %2$s.save(entity);\r\n" + "	}", entityName, domainObject, idType, domainType);

		return s;
	}

	@Override
	public String buildMethodForRepository(TransactionModel transaction) {
		return "";
	}

}
