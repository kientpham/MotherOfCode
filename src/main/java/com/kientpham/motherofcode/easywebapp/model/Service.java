package com.kientpham.motherofcode.easywebapp.model;

import java.util.List;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "service")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Service implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "name")	
	private String name;
	
	@XmlElement(name = "entityDomain")	
	private String entityDomain;
	
	@XmlElement(name = "dbgatewayDomain")	
	private String dbgatewayDomain;
	
	@XmlElement(name = "repositoryDomain")	
	private String repositoryDomain;
	
	@XmlElement(name = "businessDomain")	
	private String businessDomain;
	
	@XmlElement(name = "serviceDomain")	
	private String serviceDomain;
	
	@XmlElement(name = "entity")
	List<Entity> entities;
	
	public String getEntityDomain() {
		return this.entityDomain;
	}
	
	public String getDbgatewayDomain() {
		return this.dbgatewayDomain;
	}
	
	public String getRepositoryDomain() {
		return this.repositoryDomain;
	}
	
	public String getBusinessDomain() {
		return this.businessDomain;
	}
	
	public String getServiceDomain() {
		return this.serviceDomain;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Entity> getEntities(){
		return this.entities;
	}
}
