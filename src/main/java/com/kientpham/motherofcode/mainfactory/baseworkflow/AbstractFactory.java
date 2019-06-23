package com.kientpham.motherofcode.mainfactory.baseworkflow;

import java.util.List;


/**
 * @author Kien Pham - trungkienbk@gmail.com
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */

public abstract class AbstractFactory<T> {
	
	protected MasterWorkflow<T> workflow ;
	
	protected BaseTransactionManager<T> transactionManager; 
	
	/**
	 * @return
	 */
	protected abstract MasterWorkflow<T> initiateWorkflow();

	/**
	 * 
	 * @return message
	 */
	public List<T> processRequest(List<?> inputList) {
		
		try {					
			workflow = this.initiateWorkflow();
			List<T> transactionList = transactionManager.getTransactionModel(inputList);
			workflow.executeWorkflow(transactionList);
			return transactionList;
		} catch (WorkflowException e) {
			return null;
		}
	}
}