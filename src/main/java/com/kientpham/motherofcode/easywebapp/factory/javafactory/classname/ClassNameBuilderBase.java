package com.kientpham.motherofcode.easywebapp.factory.javafactory.classname;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public class ClassNameBuilderBase implements ClassNameInterface {

	@Override
	public String buildClassController(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.RESTCONTROLLER + className(
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getControllerDomain()));
	}

	@Override
	public String buildClassEditModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return this.buildClassNameWithGetterSetter(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
	}

	@Override
	public String buildClassTableModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return this.buildClassNameWithGetterSetter(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain());
	}

	@Override
	public String buildClassJoinList(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() == null)
			return "";

		return JavaConst.GETSETANNOTATED + this.buildClassNameImplements(
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getJoinListDomain()),
				CommonUtils.getObjectNameFromDomain(JavaConst.SERIALIZABLE));
	}

	@Override
	public String buildClassForEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		return String.format("@Entity\r\n" + "@Table(name = \"%1$s\")\r\n",
				omnibusDTO.getTransaction().getEntity().getTable()) + JavaConst.GETSETANNOTATED
				+ className(omnibusDTO.getTransaction().getEntity().getName());
	}

	@Override
	public String buildClassForRepository(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format("\r\npublic interface %1$s extends CrudRepository<%2$s, %3$s> {\r\n",
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getRepositoryDomain()),
				omnibusDTO.getTransaction().getEntity().getName(),
				omnibusDTO.getTransaction().getEntity().getFields().get(0).getType().toString());
	}

	@Override
	public String buildClassForRepositoryPaging(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}

	@Override
	public String buildClassForDBGatewayInterface(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return buildInterfaceName(
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getDbGateway()));
	}

	@Override
	public String buildClassForDBGateway(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.COMPONENTANNOTATED + buildClassNameImplements(
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getDbGatewayImpl()),
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getDbGateway()));
	}

	@Override
	public String buildClassForBusinessObject(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.COMPONENTANNOTATED + className(
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getBussinessDomain()));
	}

	@Override
	public String buildClassForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		return buildInterfaceName(
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getReadService()));
	}

	@Override
	public String buildClassForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.COMPONENTANNOTATED + buildClassNameImplements(
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getReadServiceImpl()),
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getReadService()));
	}
	
	@Override
	public String buildClassForWriteService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return buildInterfaceName(
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getWriteService()));
	}

	@Override
	public String buildClassForWriteServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.COMPONENTANNOTATED + buildClassNameImplements(
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getWriteServiceImpl()),
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getWriteService()));
	}

	private String buildClassNameWithGetterSetter(String fullDomain) {
		return JavaConst.GETSETANNOTATED + className(CommonUtils.getObjectNameFromDomain(fullDomain));
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
