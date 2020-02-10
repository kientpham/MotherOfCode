package com.kientpham.motherofcode.easywebapp.model;

import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharedDTO {
	
	private Application application;	
	
	private CodeFactory codeFactory;
	
	private FixDomainDTO fixDomainDTO;
	
	private String lookUpEntityName;
	
	private List<TransactionModel> transactionList;
	
	private HashMap<String, FullDomainDTO> fullDomainTable;
	
	public FullDomainDTO getFullDomainDTO(String entityName) {
		return this.fullDomainTable.get(entityName);
	}
	
	public Entity getEntityByName(String entityName) {
		for (TransactionModel transaction:this.transactionList) {
			if (transaction.getEntity().getName().equals(entityName))
				return transaction.getEntity();
		}
		return null;
	}

}
