package com.kientpham.motherofcode.easywebapp.factory.basebuilder;

import com.kientpham.motherofcode.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.PackageInterface;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public abstract class AbstractBuilder implements BaseBuilder<TransactionModel, SharedDTO>{
	
	@Override
	public void execute(BaseOmnibusDTO<TransactionModel, SharedDTO> baseOmnibusDTO) throws WorkflowException {
		PackageInterface packageBuilder=baseOmnibusDTO.getSharedDTO().getCodeFactory().getPackageBuilder();
		StringBuilder code=new StringBuilder();
		String codeBody=generateCode(baseOmnibusDTO);		
		if (codeBody.trim().isEmpty()) return;	
		code.append(packageBuilder.buildPackageName(this.getDomain(baseOmnibusDTO)));
		code.append(codeBody);		
		this.writeToFileForCode(baseOmnibusDTO, code);
		System.out.println(code.toString());	
	}	
	
	abstract protected String getDomain(BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO);

	abstract protected String generateCode(BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO);	
	
	protected void writeToFileForCode(BaseOmnibusDTO<TransactionModel,SharedDTO> baseOmnibusDTO, StringBuilder code) {
		PackageInterface packageBuilder=baseOmnibusDTO.getSharedDTO().getCodeFactory().getPackageBuilder();
		code.append(packageBuilder.buildPackageFooter());		
		String filePath = baseOmnibusDTO.getSharedDTO().getApplication().getAppPath()+ packageBuilder.buildFilePath(this.getDomain(baseOmnibusDTO));		
		CommonUtils.writeToFile(code.toString(), filePath);			
	}

}
