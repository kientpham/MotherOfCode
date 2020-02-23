package com.kientpham.motherofcode.easywebapp.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "field")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Field implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "column")
	private String column;

	@XmlAttribute(name = "type")
	private String type;

	@XmlAttribute(name = "fieldType")
	private String fieldType;

	@XmlAttribute(name = "hidden")
	private String hidden;

	@XmlAttribute(name = "lookupType")
	private String lookupType;

	@XmlAttribute(name = "nullable")
	private String nullable;

	@XmlAttribute(name = "sort")
	private String sort;

	@XmlAttribute(name = "joinField")
	private String joinField;

	@XmlAttribute(name = "width")
	private String width;

	@XmlAttribute(name = "uiType")
	private String uiType;

	@XmlAttribute(name = "required")
	private String required;

	@XmlAttribute(name = "uiText")
	private String uiText;

	public String getName() {
		return (this.name != null) ? this.name : "";
	}

	public String getColumn() {
		return (this.column != null) ? this.column : "";
	}

	public String getType() {
		return (this.type != null) ? this.type : "";
	}

	public String getFieldType() {
		return (this.fieldType != null) ? this.fieldType : "";
	}

	public String getHidden() {
		return (this.hidden != null) ? this.hidden : "";
	}

	public String getLookupType() {
		return (this.lookupType != null) ? this.lookupType : "";
	}

	public String getNullable() {
		return (this.nullable != null) ? this.nullable : "";
	}

	public String getSort() {
		return (this.sort != null) ? this.sort : "";
	}

	public String getWidth() {
		return (this.width != null) ? this.width : "";
	}

	public String getJoinField() {
		return (this.joinField != null) ? this.joinField : "";		
	}

	public String getUiType() {
		return (this.uiType != null) ? this.uiType : "text";		
	}

	public String getRequired() {
		return (this.required != null) ? "required" : "";		
	}

	public String getUIText() {
		return (this.uiText != null) ? this.uiText : "";		
	}

	public boolean isIdField() {
		return this.name.equals("id") ? true : false;
	}

	public boolean isShowInEdit() {
		return (this.isIdField() || this.getHidden().equals("EDIT") || this.getHidden().equals("ALL")) ? false : true;
	}

	public boolean isShowInTable() {
		return (this.isIdField() || this.getHidden().equals("TABLE") || this.getHidden().equals("ALL")) ? false : true;
	}

	public boolean isIdOrShowInTable() {
		return (this.isIdField() || this.hidden == null || (!this.hidden.equals("TABLE") && !this.hidden.equals("ALL") )) ? true : false;
	}

}
