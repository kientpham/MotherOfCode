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
	
	@XmlElement(name = "entity")
	List<Entity> entities;
	
	public String getName() {
		return this.name;
	}
	
	public List<Entity> getEntities(){
		return this.entities;
	}
}
