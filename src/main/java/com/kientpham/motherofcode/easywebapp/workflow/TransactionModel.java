package com.kientpham.motherofcode.easywebapp.workflow;

import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.Application;
import com.kientpham.motherofcode.easywebapp.model.CodeBuilder;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.OmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.Service;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeFacade;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionModel {
	
	private Application application;
	
	private Service service;
	
	private Entity entity;
	
	List<String> listDomain;
	
	private String xmlModelFile;
	
	private String language;
	
	
	
	
	private String outputCode;
	
	private OmnibusDTO omnibusDto;
	
	private CodeBuilder codeBuilder;
	
	private CodeFacade codeFacade;
	
}
