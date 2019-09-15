package com.kientpham.motherofcode.easywebapp.workflow;

import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.Application;
import com.kientpham.motherofcode.easywebapp.model.CodeFactory;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.FullDomainDTO;
import com.kientpham.motherofcode.easywebapp.model.Service;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionModel {
	
	private Application application;
	
	private Service service;
	
	private Entity entity;
	
	List<String> listDomain;	
		
	private String outputCode;
	
	private FullDomainDTO fullDomainDTO;
		
	private CodeBuilder codeFacade;
	
	private CodeFactory codeFactory;
	
}
