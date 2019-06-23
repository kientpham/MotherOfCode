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
	
	@XmlElement(name = "service")
	private List<Service> services;
	
	@Override
	public String toString() {		
		return String.format("Name: %1$s, title: %2$s", this.name, this.title);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDomain() {
		return this.domain;
	}
	
	public List<Service> getServices(){
		return this.services;
	}
}
