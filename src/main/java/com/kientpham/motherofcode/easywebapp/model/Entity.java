package com.kientpham.motherofcode.easywebapp.model;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

import javax.swing.text.StyleConstants.ColorConstants;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.utils.CommonUtils;
import com.kientpham.motherofcode.utils.Const;

@XmlRootElement(name = "entity")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Entity implements Serializable {

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

	public List<Field> getFields() {
		return this.fields;
	}

	public List<JoinTable> getJoinTables() {
		return this.joinTables;
	}	

	public String getCreatedDateField() {
		for (Field field : this.fields) {
			if (field.getFieldType().equals(Const.CREATED_DATE)) {
				return field.getName();
			}
		}
		return "";
	}
	
	public Field getIDField() {
		for (Field field : this.fields) {
			if (field.isIdField()) {
				return field;
			}
		}
		return this.fields.get(0);
	}

	public String getTitleField() {
		if (!this.titleFields.isEmpty())
			return this.titleFields;
		return this.fields.get(1).getName();
	}

	public int getNumberOfTitleColumn() {
		return this.titleFields.split(",").length;
	}

	public int getNumberOfTableColumn() {
		int n = 0;
		for (Field field : this.fields) {
			if (field.getHidden() == null)
				n++;
		}
		return n;
	}

	public boolean hasJoinTable() {
		return (this.joinTables == null || this.joinTables.size() == 0) ? false : true;
	}

	public List<JoinTable> getListMulitpleJoinTable() {
		List<JoinTable> list = new ArrayList<JoinTable>();
		if (this.hasJoinTable()) {
			for (JoinTable joinTable : this.joinTables) {
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)
						|| joinTable.getRelation().equals(JavaConst.ONETOMANY)) {
					list.add(joinTable);
				}
			}
		}
		return list;
	}
	
	public boolean hasCreatedOrLastUpdated() {
		for (Field field : this.fields) {
			if (field.getFieldType().equals(Const.CREATED_DATE) || field.getFieldType().equals(Const.LAST_UPDATED))
				return true;
		}
		return false;
	}		
	
	public boolean hasFieldType(String type) {
		for (Field field : this.fields) {
			if (field.getType().equals(type))
				return true;
		}
		return false;
	}

}
