package com.kientpham.motherofcode.easywebapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FullDomainDTO {
	
	private String entityDomain;	

	private String repositoryDomain;	
	
	private String repositoryPagingDomain;
	
	private String dbGateway;
	
	private String dbGatewayImpl;
	
	private String bussinessDomain;
	
	private String readService;
	
	private String readServiceImpl;
	
	private String writeService;
	
	private String writeServiceImpl;
	
	private String editModelDomain;
	
	private String tableModelDomain;
	
	private String joinListDomain;
	
	private String controllerDomain;
	
//	public String toString() {
//		return entityDomain + Const.RETURN+ repositoryDomain+Const.RETURN+serviceInterfaceDomain;
//	}
	
}
