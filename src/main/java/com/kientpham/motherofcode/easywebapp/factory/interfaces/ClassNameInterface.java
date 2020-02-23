package com.kientpham.motherofcode.easywebapp.factory.interfaces;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public interface ClassNameInterface {

	public String buildClassForEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildClassForRepository(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildClassForRepositoryPaging(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildClassForDBGateway(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildClassForDBGatewayInterface(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildClassForBusinessObject(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildClassForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildClassForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildClassForWriteService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildClassForWriteServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildClassEditModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildClassTableModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildClassJoinList(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

//	String buildClassEditPresenter(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
//
//	String buildClassListPresenter(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildClassController(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);	
}
