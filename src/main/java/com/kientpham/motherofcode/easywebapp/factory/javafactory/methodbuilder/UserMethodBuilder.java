package com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;
import com.kientpham.motherofcode.utils.Const;

public class UserMethodBuilder extends JavaMethodBaseBuilder{

	@Override
	public String buildMethodForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		
		return super.buildMethodForReadService(omnibusDTO)+ String.format("\tpublic List<String> getListFeature(%1$s userId);\r\n", omnibusDTO.getTransaction().getEntity().getFields().get(0).getType());		
	}
	
	@Override
	public String buildMethodForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String entityName = omnibusDTO.getTransaction().getEntity().getName();		
		String idType=omnibusDTO.getTransaction().getEntity().getFields().get(0).getType();
		String businessDomainName = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getBussinessDomain());
		String permission=CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFullDomainTable().get(Const.PERMISSION).getEntityDomain());
		String group=CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFullDomainTable().get(Const.GROUP).getEntityDomain());
		
		return super.buildMethodForReadServiceImpl(omnibusDTO)+ String.format("@Override\r\n" + 
				"	public List<String> getListFeature(%2$s userId) {\r\n" + 
				"		%1$s user = %3$s.findById(userId);\r\n" + 
				"		List<%5$s> groupList = user.get%5$ss();\r\n" + 
				"		List<%4$s> permissionList = new ArrayList<%4$s>();\r\n" + 
				"		for (%5$s group : groupList) {\r\n" + 
				"			permissionList.addAll(group.get%4$ss());\r\n" + 
				"		}\r\n" + 
				"		Set<String> featureList = new HashSet<String>();\r\n" + 
				"		for (%4$s permission : permissionList) {\r\n" + 
				"			String menuPath=permission.getMenuPath();\r\n" + 
				"			if (permission.getToggle() && menuPath!=null && !menuPath.isEmpty()) {\r\n" + 
				"				featureList.addAll(Arrays.asList(permission.getMenuPath().trim().split(\"\\\\\\\\\")));\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"		return new ArrayList<String>(featureList);\r\n" + 
				"	}", entityName,idType,CommonUtils.getLowerCaseFirstChar(businessDomainName), permission, group);
	}

}
