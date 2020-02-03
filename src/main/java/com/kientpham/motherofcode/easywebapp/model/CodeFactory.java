package com.kientpham.motherofcode.easywebapp.model;

import java.util.List;

import com.kientpham.motherofcode.easywebapp.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.FixClassInterface;
import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.factory.PackageInterface;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeFactory {
	
	private FixClassInterface fixClassBuilder;
	
	private PackageInterface packageBuilder;
	
	private List<ImportLibInterface> importLibBuilderList;
	
	private List<ClassNameInterface> classNameBuilderList; 
	
	private List<MethodBuilderInterface> methodBuilderList;
	
}
