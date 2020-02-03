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
public interface BaseTransactionManager<T,D> {
		
	
	public abstract BaseOmnibusDTO<T, D> initiateBaseOmnibusDTO() throws WorkflowException;
	/**
	 * @return
	 * @throws WorkflowException
	 */
	public abstract List<T> getTransactionModel(List<?> inputList) throws WorkflowException;
	
	/**
	 * @param transaction
	 * @param omnibusDTO
	 */
	public void saveTransaction(T transaction);

	/**
	 * @param transaction
	 * @param e
	 */
	public void updateTransactionWhenException(T transaction, WorkflowException e);	

}
