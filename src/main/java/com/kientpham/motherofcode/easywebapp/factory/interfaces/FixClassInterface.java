package com.kientpham.motherofcode.easywebapp.factory.interfaces;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public interface FixClassInterface {

	String buildPagingOutput(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildPagingInput(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildColumn(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildOrderingCriteria(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildPaginationCriteria(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildSearchCriteria(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildTablePage(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildDataTablePresenter(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

	String buildDateStringUtilsBody(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO);

}
