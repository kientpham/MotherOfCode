package com.kientpham.motherofcode.easywebapp.factory.uibuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.EntityBaseBuilder;
import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public class EditPresenterBuilder{// extends EntityBaseBuilder {

//	@Override
//	protected String getBuilderDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
//		return String.format("%1$s.%2$s.%3$s",
//				omnibusDTO.getSharedDTO().getApplication().getUserInterface().getDomain(),
//				omnibusDTO.getSharedDTO().getApplication().getUserInterface().getPresenter(),
//				omnibusDTO.getTransaction().getEntity().getName().toLowerCase());
//	}
//
//	@Override
//	protected String buildClassName(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
//		return String.format("%1$s%2$s", super.buildClassName(omnibusDTO), "EditPresenter");
//	}
//
//	@Override
//	protected void saveOutputDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO, String outputDomain) {
//		omnibusDTO.getTransaction().getFullDomainDTO().setEditPresenter(outputDomain);
//	}
//
//	@Override
//	protected String getImportCode(ImportLibInterface importLibBuilder,
//			BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
//		return importLibBuilder.importForEditPresenter(omnibusDTO);
//	}
//
//	@Override
//	protected String getClassName(ClassNameInterface classNameBuilder,
//			BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
//		return classNameBuilder.buildClassEditPresenter(omnibusDTO);
//	}
//
//	@Override
//	protected String getMethodCode(MethodBuilderInterface methodBuilder,
//			BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
//		return methodBuilder.buildMethodForEditPresenter(omnibusDTO);
//	}

}
