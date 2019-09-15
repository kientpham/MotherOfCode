package com.kientpham.motherofcode.mainfactory.baseworkflow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kien Pham - trungkienbk@gmail.com
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */
public class MasterWorkflow<T> {

	private List<BaseBuilder<T>> builderList;

	private BaseTransactionManager<T> baseTransaction;
	
	private List<BaseBuilder<T>> preExecuteBuilderList;
	
	public void setPreExecuteBuilder(BaseBuilder<T> builder) {
		if (preExecuteBuilderList==null)
			preExecuteBuilderList=new ArrayList<BaseBuilder<T>>();
		preExecuteBuilderList.add(builder);
	}

	/**
	 * 
	 * @param baseTransaction
	 */
	public void setBaseTransactionManager(BaseTransactionManager<T> baseTransaction) {
		this.baseTransaction = baseTransaction;
	}

	/**
	 * @param builder
	 */
	public void setFirstBuilder(BaseBuilder<T> builder) {
		builderList = new ArrayList<BaseBuilder<T>>();
		builderList.add(builder);
	}

	/**
	 * @param builder
	 */
	public void setNextBuilder(BaseBuilder<T> builder) {
		builderList.add(builder);
	}

	/**
	 * Go through each transaction to process
	 * 
	 * @param transactionList
	 * @throws ServiceException
	 */
	public void executeWorkflow(List<T> transactionList) throws WorkflowException {
		if (baseTransaction == null || builderList == null) {
			throw new WorkflowException("Could not excute the work flow");
		}		
		preExecute(transactionList);		
		execute(transactionList);
	}

	private void execute(List<T> transactionList) {
		for (T transaction : transactionList) {		
			try {
				for (BaseBuilder<T> builder : builderList) {
					builder.execute(transaction);
				}
			} catch (WorkflowException e) {
				baseTransaction.updateTransactionWhenException(transaction, e);
			} finally {
				baseTransaction.saveTransaction(transaction);
			}
		}
	}

	private void preExecute(List<T> transactionList) throws WorkflowException {
		if (preExecuteBuilderList!=null) {
			for (BaseBuilder<T> builder : preExecuteBuilderList) {
				builder.execute(transactionList.get(0));
			}
		}
	}

}
