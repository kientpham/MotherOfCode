package com.kientpham.motherofcode.mainfactory.codefactory;

public interface ImportCode {

	public String importEntity(boolean hasJoinTable);

	public String lombokGetterSetter();

	public String serializable();

	public String list();

	String crudRepository(String entityFullDomain);
}
