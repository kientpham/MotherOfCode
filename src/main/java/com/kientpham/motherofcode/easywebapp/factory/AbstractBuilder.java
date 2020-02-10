package com.kientpham.motherofcode.easywebapp.factory;

import com.kientpham.motherofcode.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.baseworkflow.WorkflowException;
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

		String fullDomain=this.getDomain(baseOmnibusDTO);
		code.append(packageBuilder.buildPackageName(fullDomain));
		code.append(codeBody);
		code.append(packageBuilder.buildPackageFooter());		
		String filePath = baseOmnibusDTO.getSharedDTO().getApplication().getAppPath()+ packageBuilder.buildFilePath(fullDomain);		
		CommonUtils.writeToFile(code.toString(), filePath);		
		System.out.println(code.toString());		
	}	
	
	abstract protected String getDomain(BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO);

	abstract protected String generateCode(BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO);	

}
