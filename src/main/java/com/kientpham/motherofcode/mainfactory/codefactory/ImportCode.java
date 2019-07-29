package com.kientpham.motherofcode.mainfactory.codefactory;


public interface ImportCode {

	public String importEntity(boolean hasJoinTable);

	public String lombokGetterSetter();

	public String serializable();

	public String list();

	public String crudRepository(String entityFullDomain);

	public String repository(String entityFullDomain);

	public String importSpringComponent();

	public String importDomain(String domain);

	public String importSpringPaging();

	public String importSpringPageble();

	public String importSpringPageRequest();

	String dataSort();

	String importListArray();

}
