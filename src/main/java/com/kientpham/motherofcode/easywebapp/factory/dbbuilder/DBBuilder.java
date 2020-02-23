package com.kientpham.motherofcode.easywebapp.factory.dbbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public class DBBuilder implements BaseBuilder<TransactionModel, SharedDTO> {

	
	@Override
	public void execute(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) throws WorkflowException {
		
		this.writeToFile(omniBusDTO, this.generateDBTables(omniBusDTO) + this.generateRelationshipTables(omniBusDTO));

	}

	private void writeToFile(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO, String string) {
		// TODO Auto-generated method stub
		
	}
	
	private String generateDBTables(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) {
		String code="";
		
		for (TransactionModel model: omniBusDTO.getSharedDTO().getTransactionList()) {
			for (Field field:model.getEntity().getFields()) {
				
			}
			
		}
		
		
		return code;
	}

	private String generateRelationshipTables(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
