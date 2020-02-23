package com.kientpham.motherofcode.easywebapp.factory.dbbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public interface DBInterface {

	public String generateTable(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO);
	
	public String generateField(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO);
	
	public String generateConstraint(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO);
}
