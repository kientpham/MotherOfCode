package com.kientpham.motherofcode.easywebapp.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "userinterface")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class UserInterface implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "domain")	
	private String domain;
	
	@XmlAttribute(name = "controller")	
	private String controller;	
		
	public String getDomain() {
		return this.domain;
	}
	
	public String getController() {
		return this.controller;
	}
		
}
