package com.kientpham.motherofcode.easywebapp.model;

import java.util.List;

import com.kientpham.motherofcode.easywebapp.factory.interfaces.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.FixClassInterface;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.HtmlEditPageBase;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.PackageInterface;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeFactory {
	
	private FixClassInterface fixClassBuilder;
	
	private PackageInterface packageBuilder;
	
	private HtmlEditPageBase htmlPageBuilder;
	
	private List<ImportLibInterface> importLibBuilderList;
	
	private List<ClassNameInterface> classNameBuilderList; 
	
	private List<MethodBuilderInterface> methodBuilderList;	
	
}
