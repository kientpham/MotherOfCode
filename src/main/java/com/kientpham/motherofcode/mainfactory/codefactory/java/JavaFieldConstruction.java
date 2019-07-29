package com.kientpham.motherofcode.mainfactory.codefactory.java;

import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.mainfactory.codefactory.FieldConstruction;

public class JavaFieldConstruction implements FieldConstruction {

	@Override
	public String idFieldAnnotated() {
		return "\t@Id\r\n" + "\t@GeneratedValue(strategy=GenerationType.IDENTITY)";
	}

	@Override
	public String normalFieldAnnoted(String columnName) {
		return String.format("\t@Column(name = \"%1$s\")", columnName);
	}

	@Override
	public String getFieldName(String type, String fieldName) {
		return String.format("\tprivate %1$s %2$s;\r\n", type, fieldName);
	}

	@Override
	public String getJoinFields(JoinTable joinTable) {
		return "\t" + joinTable.getRelation() + "\r\n" + String.format(
				"\t@JoinTable(name = \"%1$s\", joinColumns = { @JoinColumn(name = \"%2$s\") }, inverseJoinColumns = {\r\n"
						+ "\t\t@JoinColumn(name = \"%3$s\") })",
				joinTable.getName(), joinTable.getJoinColumns(), joinTable.getInverseJoinColumns()) + "\r\n"
				+ String.format("\tprivate List<%1$s> %2$s;", joinTable.getType(), joinTable.getField())+ "\r\n";

	}

}
