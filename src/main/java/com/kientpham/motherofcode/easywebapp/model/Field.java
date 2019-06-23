package com.kientpham.motherofcode.easywebapp.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Field implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "name")
	private String name;
	
	@XmlAttribute(name = "identity")
	private String identity;
	
	@XmlAttribute(name = "column")
	private String column;
	
	@XmlAttribute(name = "type")
	private String type;
	
	public String getName() {
		return this.name;
	}
	
	public String getIdentity() {
		return this.identity;
	}
	
	public String getColumn() {
		return this.column;
	}
	
	public String getType() {
		return this.type;
	}
}
