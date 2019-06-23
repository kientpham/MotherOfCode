package com.kientpham.motherofcode.mainfactory.codefactory;

import com.kientpham.motherofcode.easywebapp.model.JoinTable;

public interface FieldConstruction {

	String idFieldAnnotated();

	String normalFieldAnnoted(String columnName);

	String getFieldName(String type, String fieldName);

	String getJoinFields(JoinTable joinTable);

}
