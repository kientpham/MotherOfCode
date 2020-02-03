package com.kientpham.motherofcode.easywebapp.factory;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public interface ImportLibInterface {	
	
	public String importForEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String importForRepository(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String importForRepositoryPaging(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String importForDBGatewayInterface(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String importForDBGateway(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	public String importForBusinessObject(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String importForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String importForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String importForWriteService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);
	
	public String importForWriteServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	public String importForEditModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String importForTableModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String importForJoinList(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String importForController(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);	
	
	
	
}
