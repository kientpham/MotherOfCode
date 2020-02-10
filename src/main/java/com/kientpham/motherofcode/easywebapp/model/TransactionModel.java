package com.kientpham.motherofcode.easywebapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionModel {
			
	private Service service;
	
	private Entity entity;	
	
	private FullDomainDTO fullDomainDTO;
	
	private CodeFactory codeFactory;
	
	private String filePath;
	
	
	
}
