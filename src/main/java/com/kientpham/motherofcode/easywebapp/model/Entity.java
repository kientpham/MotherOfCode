package com.kientpham.motherofcode.easywebapp.model;

import java.util.List;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kientpham.motherofcode.utils.CommonUtils;
 
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
	
	@XmlAttribute(name = "titleFields")
	private String titleFields;
	
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
    
    public String getIdType() {
    	for (Field field:this.fields) {
    		if (!field.getIdentity().isEmpty()) { 
    			return field.getType();
    		}
    	}
    	return this.fields.get(0).getType();
    }
    
    public String getIdFieldName() {
    	for (Field field:this.fields) {
    		if (!field.getIdentity().isEmpty()) { 
    			return field.getName();
    		}
    	}
    	return this.fields.get(0).getName();
    }  
    
    public String getTitleField() {
    	if (!this.titleFields.isEmpty())
    		return this.titleFields;
    	return this.fields.get(1).getName();
	}

    
//    public String getTitleField() {
//    	String entityName=CommonUtils.getLowerCaseFirstChar(this.getName());
//    	if (!this.titleFields.isEmpty()) {
//    		String[] titleList=titleFields.split(",");
//    		String returnString="";    		
//    		for (int i=0;i<titleList.length;i++) {
//    			if (i!=0) {
//    				returnString+="+";
//    			}
//    			returnString+=String.format("%1$s.get%2$s()", entityName, CommonUtils.getUpperCaseFirstChar(titleList[i]));
//    			
//    		}
//    		return returnString;
//    	}
//    	return String.format("%1$s.get%2$s()", entityName, CommonUtils.getUpperCaseFirstChar(this.fields.get(1).getName()));
//    }
    
    public boolean hasJoinTable() {
    	return (this.joinTables ==null || this.joinTables.size()==0)?false:true; 
    }
}
