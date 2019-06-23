package com.kientpham.motherofcode;

import java.util.Arrays;
import java.util.List;

import com.kientpham.motherofcode.easywebapp.workflow.EasyWebAppManager;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.utils.Const;


public class ApplicationMain {

	public static void main(String[] args) {
		List<String> inputValues=Arrays.asList("CodeModel.xml",Const.JAVA);	
		EasyWebAppManager factory=new EasyWebAppManager();
		List<TransactionModel> transactionList=factory.processRequest(inputValues);
		StringBuilder sb=new StringBuilder();
		for (TransactionModel transaction:transactionList) {
			sb.append(transaction.getOutputCode());
		}					
		System.out.println(sb.toString());
	}

}
