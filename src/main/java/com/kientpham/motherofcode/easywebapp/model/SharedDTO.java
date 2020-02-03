package com.kientpham.motherofcode.easywebapp.model;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharedDTO {
	
	private Application application;	
	
	private CodeFactory codeFactory;
	
	private FixDomainDTO fixDomainDTO;
	
	private String lookUpEntityName;
	
	private HashMap<String, FullDomainDTO> fullDomainTable;
	
	public FullDomainDTO getFullDomainDTO(String entityName) {
		return this.fullDomainTable.get(entityName);
	}
}
