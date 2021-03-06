package com.kientpham.motherofcode.easywebapp.model;

import java.util.List;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "application")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Application implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "name")	
	private String name;
	
	@XmlAttribute(name = "title")
	private String title;
	
	@XmlAttribute(name = "domain")
	private String domain;
	
	@XmlAttribute(name = "appPath")
	private String appPath;
	
	@XmlAttribute(name = "database")
	private String database;
	
	@XmlAttribute(name = "dbms")
	private String dbms;
	
	@XmlElement(name = "userinterface")
	private UserInterface userinterface;
	
	@XmlElement(name = "service")
	private List<Service> services;
	
	@XmlElement(name = "commonDomain")	
	private String commonDomain;
	
	@Override
	public String toString() {		
		return String.format("Name: %1$s, title: %2$s", this.name, this.title);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDbms() {
		return this.dbms;
	}
	
	public String getDatabase() {
		return this.database;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDomain() {
		return this.domain;
	}
	
	public String getAppPath() {
		return this.appPath;
	}
	
	public List<Service> getServices(){
		return this.services;
	}
	
	public UserInterface getUserInterface() {
		return this.userinterface;
	}
	
	public String getCommonDomain() {
		return this.commonDomain;
	}
}
