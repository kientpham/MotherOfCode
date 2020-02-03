package com.kientpham.motherofcode.easywebapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullDomainDTO {
	
	private String pagingInput;
	
	private String pagingOutput;
	
	private String entityDomain;
	
	private String repositoryDomain;	
	
	private String repositoryPagingDomain;
	
	private String dbGatewayInterface;
	
	private String dbGateway;
	
	private String bussinessDomain;
	
	private String serviceInterfaceDomain;
	
	private String serviceDomain;
	
}
