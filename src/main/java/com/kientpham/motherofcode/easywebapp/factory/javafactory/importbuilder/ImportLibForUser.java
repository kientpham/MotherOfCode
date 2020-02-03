package com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaCommon;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.Const;

public class ImportLibForUser extends ImportLibBuilderBase {

	@Override
	public String importForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		String permissionEntityDomain=omnibusDTO.getSharedDTO().getFullDomainTable().get(Const.PERMISSION).getEntityDomain();
		return super.importForReadServiceImpl(omnibusDTO) + JavaCommon.importDomain(permissionEntityDomain) + JavaConst.ARRAYLIST + JavaConst.HASHSET + JavaConst.ARRAYS
				+ JavaConst.SET;
		// + JavaCommon.importDomain(String.format("%1$s.%2$s.%3$s", appDomain,
		// omnibusDTO.getTransaction().getService().getEntityDomain(), Const.GROUP))
		// + JavaCommon.importDomain(String.format("%1$s.%2$s.%3$s", appDomain,
		// omnibusDTO.getTransaction().getService().getEntityDomain(),
		// Const.PERMISSION));
	}

}
