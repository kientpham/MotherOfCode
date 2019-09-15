package com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder;

import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.utils.Const;

public class ImportLibForUser extends ImportLibBuilderBase {

	@Override
	public String importForServiceClass(TransactionModel transaction) {
		String appDomain=transaction.getApplication().getDomain() +"."+transaction.getService().getName();
		
		return super.importForServiceClass(transaction) + JavaConst.ARRAYLIST + JavaConst.HASHSET + JavaConst.ARRAYS
				+ JavaConst.SET + "import " + appDomain +"."+ transaction.getService().getEntityDomain() + "." + Const.GROUP
				+ ";\r\n" + "import " + appDomain+"."+transaction.getService().getEntityDomain() + "." + Const.PERMISSION
				+ ";\r\n";
	}	

}
