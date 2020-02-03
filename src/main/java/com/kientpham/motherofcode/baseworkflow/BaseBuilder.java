package com.kientpham.motherofcode.baseworkflow;

/**
 * @author Kien Pham - trungkienbk@gmail.com
 *
 * @param <D>
 *            Generic OmnibusDTO.
 */
public interface BaseBuilder<T,D> {
	/**
	 * 
	 * @param transaction
	 * @param omnibusDTO
	 * @throws ServiceException
	 */	
	public abstract void execute(BaseOmnibusDTO<T,D> omniBusDTO) throws WorkflowException;
	
}
