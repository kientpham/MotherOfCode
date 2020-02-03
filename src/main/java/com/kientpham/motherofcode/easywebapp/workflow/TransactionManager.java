package com.kientpham.motherofcode.easywebapp.workflow;

import com.kientpham.motherofcode.baseworkflow.BaseTransactionManager;
import com.kientpham.motherofcode.baseworkflow.WorkflowException;

public class TransactionManager implements BaseTransactionManager<TransactionModel, ShareDTO> {

	@Override
	public void saveTransaction(TransactionModel transaction) {
	}

	@Override
	public void updateTransactionWhenException(TransactionModel transaction, WorkflowException e) {
	}


}
