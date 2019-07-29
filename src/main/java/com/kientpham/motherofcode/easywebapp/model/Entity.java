package com.kientpham.motherofcode.easywebapp.model;

import java.util.List;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "entity")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Entity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "name")
	private String name;
	
	@XmlAttribute(name = "table")
	private String table;	
	
	@XmlAttribute(name = "hasPaging")
	private String hasPaging;
	
	@XmlAttribute(name = "type")
	private String type;
	
    @XmlElement(name = "field")
	List<Field> fields;
	
    @XmlElement(name = "joinTable")
	List<JoinTable> joinTables;
    
    public String getName() {
    	return this.name;
    }
    
    public String getTable() {
    	return this.table;
    }
    
    public String hasPaging() {
    	return this.hasPaging;
    }
    
    public String getType() {
    	return this.type;
    }
    
    public List<Field> getFields(){
    	return this.fields;
    }
    
    public List<JoinTable> getJoinTables(){
    	return this.joinTables;
    }
}
