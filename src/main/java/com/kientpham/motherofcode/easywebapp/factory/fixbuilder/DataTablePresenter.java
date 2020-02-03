package com.kientpham.motherofcode.easywebapp.factory.fixbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.FixClassBaseBuilder;
import com.kientpham.motherofcode.easywebapp.factory.FixClassInterface;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.Const;

public class DataTablePresenter extends FixClassBaseBuilder {

	@Override
	protected String buildCodeBody(FixClassInterface fixClassBuilder,
			BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return fixClassBuilder.buildDataTablePresenter(omnibusDTO);
	}

	@Override
	protected String getDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return omnibusDTO.getSharedDTO().getFixDomainDTO().getDataTablePresenter();
	}

}
