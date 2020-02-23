package com.kientpham.motherofcode.easywebapp.factory.interfaces;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public interface MethodBuilderInterface {
	
	public String buildMethodForEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildMethodForRepository(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildMethodForRepositoryPaging(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildMethodForDBGatewayInterface(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildMethodForDBGateway(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);	
	
	public String buildMethodForBusinessObject(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildMethodForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildMethodForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildMethodForWriteService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String buildMethodForWriteServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	String buildMethodForEditModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildMethodForTableModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildMethodForJoinList(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	public String buildMethodForController(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	
	
	
}
