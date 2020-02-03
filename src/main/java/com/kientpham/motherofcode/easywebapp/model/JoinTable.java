package com.kientpham.motherofcode.easywebapp.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "joinTable")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class JoinTable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "relation")
	private String relation;
	
	@XmlAttribute(name = "type")
	private String type;
	
	@XmlAttribute(name = "field")
	private String field;
	
	@XmlAttribute(name = "name")
	private String name;
	
	@XmlAttribute(name = "joinColumns")
	private String joinColumns;
	
	@XmlAttribute(name = "inverseJoinColumns")
	private String inverseJoinColumns;	
	
	public String getRelation() {
		return this.relation;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getColumnName() {
		return this.name;
	}
	
	public String getJoinColumns() {
		return this.joinColumns;
	}
	
	public String getInverseJoinColumns() {
		return this.inverseJoinColumns;
	}
	
	public String getField() {
		return this.field;
	}
}
