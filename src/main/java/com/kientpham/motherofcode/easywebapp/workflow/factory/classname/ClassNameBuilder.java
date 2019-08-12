package com.kientpham.motherofcode.easywebapp.workflow.factory.classname;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.easywebapp.workflow.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.workflow.factory.common.JavaConst;
import com.kientpham.motherofcode.utils.PackageUtils;

public class ClassNameBuilder implements ClassNameInterface {

	@Override
	public String buildClassForEntity(TransactionModel transaction) {

		return String.format("@Entity\r\n" + "@Table(name = \"%1$s\")", transaction.getEntity().getTable())
				+ getsetAnnotated() + className(transaction.getEntity().getName());
	}

	@Override
	public String buildClassForRepository(TransactionModel transaction) {
		return String.format("\r\npublic interface %1$s extends CrudRepository<%2$s, %3$s> {\r\n",
				transaction.getFullDomainDTO().getRepositoryDomain(), transaction.getEntity().getName(),
				transaction.getEntity().getFields().get(0).getType().toString());
	}

	@Override
	public String buildClassForDBGatewayInterface(TransactionModel transaction) {
		return buildInterfaceName(transaction.getFullDomainDTO().getDbGatewayInterface());
	}

	@Override
	public String buildClassForDBGateway(TransactionModel transaction) {
		return JavaConst.COMPONENTANNOTATED + buildClassNameImplements(transaction.getFullDomainDTO().getDbGateway(),
				PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getDbGatewayInterface()));
	}

	@Override
	public String buildClassForBusinessObject(TransactionModel transaction) {
		return JavaConst.COMPONENTANNOTATED + className(transaction.getFullDomainDTO().getBussinessDomain());
	}

	@Override
	public String buildClassForServiceInterface(TransactionModel transaction) {

		return buildInterfaceName(transaction.getFullDomainDTO().getServiceInterfaceDomain());
	}

	@Override
	public String buildClassForServiceClass(TransactionModel transaction) {
		return JavaConst.COMPONENTANNOTATED
				+ buildClassNameImplements(transaction.getFullDomainDTO().getServiceDomain(),
						transaction.getFullDomainDTO().getServiceInterfaceDomain());
	}

	private String getsetAnnotated() {
		return "@Getter\r\n" + "@Setter";
	}

	private String className(String className) {
		return String.format("\r\npublic class %1$s", className).trim() + "{\r\n";
	}

	private String buildInterfaceName(String className) {
		return String.format("\r\npublic interface %1$s", className).trim() + "{\r\n";
	}

	private String buildClassNameImplements(String className, String parent) {
		return String.format("\r\npublic class %1$s implements %2$s", className, parent).trim() + "{\r\n";
	}

}
