package com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public class ImportLibForLookup extends ImportLibBuilderBase{

	@Override
	public String importForRepository(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {		
		return super.importForRepository(omnibusDTO)+JavaConst.SORT + JavaConst.LIST;
	}

	@Override
	public String importForDBGateway(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return super.importForDBGateway(omnibusDTO) + JavaConst.SORT ;
	}
	
	@Override
	public String importForBusinessObject(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return super.importForBusinessObject(omnibusDTO) + JavaConst.ARRAYLIST + JavaConst.HASHMAP + JavaConst.MAP;
	}

	@Override
	public String importForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {		
		return super.importForReadService(omnibusDTO) + JavaConst.MAP;
	}

	@Override
	public String importForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {		
		return super.importForReadServiceImpl(omnibusDTO) + JavaConst.MAP +JavaConst.HASHMAP;
	}
}
