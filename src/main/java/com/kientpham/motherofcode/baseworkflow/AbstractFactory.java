package com.kientpham.motherofcode.baseworkflow;

import java.util.List;


/**
 * @author Kien Pham - trungkienbk@gmail.com
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */

public abstract class AbstractFactory<T,D> {
	
	protected MasterWorkflow<T,D> workflow ;
	
	protected BaseTransactionManager<T,D> transactionManager; 
	
	/**
	 * @return
	 */
	protected abstract MasterWorkflow<T,D> initiateWorkflow();

	/**
	 * 
	 * @return message
	 */
	public List<T> processRequest(List<?> inputList) {
		
		try {					
			workflow = this.initiateWorkflow();
			List<T> transactionList = transactionManager.getTransactionModel(inputList);
			BaseOmnibusDTO<T,D> baseOmniBusDTO=transactionManager.initiateBaseOmnibusDTO(transactionList);
			workflow.executeWorkflow(transactionList,baseOmniBusDTO);
			return transactionList;
		} catch (WorkflowException e) {
			return null;
		}
	}
}