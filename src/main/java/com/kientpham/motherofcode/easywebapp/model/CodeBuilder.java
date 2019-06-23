package com.kientpham.motherofcode.easywebapp.model;

import com.kientpham.motherofcode.mainfactory.codefactory.ClassConstruction;
import com.kientpham.motherofcode.mainfactory.codefactory.FieldConstruction;
import com.kientpham.motherofcode.mainfactory.codefactory.ImportCode;
import com.kientpham.motherofcode.mainfactory.codefactory.MethodConstruction;
import com.kientpham.motherofcode.mainfactory.codefactory.PackageName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeBuilder {
	
	private PackageName packageName;
	
	private ImportCode importCode;
	
	private ClassConstruction classConstruction;
	
	private FieldConstruction fieldConstruction;
	
	private MethodConstruction methodConstruction;

}
