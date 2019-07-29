package com.kientpham.motherofcode.easywebapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullDomainDTO {
	
	private String entityDomain;
	
	private String PagingInput;
	
	private String PagingOutput;

	private String repositoryDomain;	
	
	private String repositoryPagingDomain;
	
	private String dbGatewayInterface;
	
	private String bussinessDomain;
	
	private String serviceDomain;
	
}
