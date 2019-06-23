package com.kientpham.motherofcode.mainfactory.baseworkflow;

/**
 * @author Kien Pham - trungkienbk@gmail.com
 *
 * @param <D>
 *            Generic OmnibusDTO.
 */
public interface BaseBuilder<T> {
	/**
	 * 
	 * @param transaction
	 * @param omnibusDTO
	 * @throws ServiceException
	 */
	public abstract void execute(T transactionModel) throws WorkflowException;

}
