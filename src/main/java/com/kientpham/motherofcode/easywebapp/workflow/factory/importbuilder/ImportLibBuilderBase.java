package com.kientpham.motherofcode.easywebapp.workflow.factory.importbuilder;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.easywebapp.workflow.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.workflow.factory.common.JavaConst;
import com.kientpham.motherofcode.easywebapp.workflow.factory.common.JavaUtils;

public class ImportLibBuilderBase implements ImportLibInterface {

	@Override
	public String importForEntity(TransactionModel transaction) {
		String sImports = "import javax.persistence.Entity;\r\n" + "import javax.persistence.Column;\r\n"
				+ "import javax.persistence.GeneratedValue;\r\n" + "import javax.persistence.GenerationType;\r\n"
				+ "import javax.persistence.Id;\r\n" + "import javax.persistence.Table;\r\n";
		if (transaction.getEntity().getJoinTables() != null) {
			sImports += "import javax.persistence.JoinTable;\r\n" + "import javax.persistence.ManyToMany;\r\n"
					+ "import javax.persistence.JoinColumn;\r\n" + JavaConst.LIST;
		}
		sImports += JavaConst.LOMBOKGETSET;
		return sImports;
	}

	@Override
	public String importForRepository(TransactionModel transaction) {
		return JavaConst.LIST + JavaConst.CRUDREPOSITORY 
				+ JavaUtils.importDomain(transaction.getFullDomainDTO().getEntityDomain() );
	}

	@Override
	public String importForDBGatewayInterface(TransactionModel transaction) {		
		return JavaConst.LIST + JavaConst.SORT+
				JavaUtils.importDomain(transaction.getFullDomainDTO().getEntityDomain());
	}
	
	@Override
	public String importForDBGateway(TransactionModel transaction) {
		return JavaConst.LIST+JavaConst.SORT+JavaConst.AUTOWIRED+JavaConst.COMPONENT
				+JavaUtils.importDomain(transaction.getFullDomainDTO().getEntityDomain())
				+JavaUtils.importDomain(transaction.getFullDomainDTO().getRepositoryDomain())
				+JavaUtils.importDomain(transaction.getFullDomainDTO().getDbGatewayInterface());
	}
	
	@Override
	public String importForBusinessObject(TransactionModel transaction) {
		
		return JavaConst.LIST+JavaConst.AUTOWIRED+JavaConst.COMPONENT
				+JavaUtils.importDomain(transaction.getFullDomainDTO().getEntityDomain())
				+JavaUtils.importDomain(transaction.getFullDomainDTO().getDbGatewayInterface());
	}

	@Override
	public String importForServiceInterface(TransactionModel transaction) {
		
		return JavaConst.LIST+
				JavaUtils.importDomain(transaction.getFullDomainDTO().getEntityDomain());
	}

	@Override
	public String importForServiceClass(TransactionModel transaction) {		
		return JavaConst.LIST +JavaConst.AUTOWIRED + JavaConst.COMPONENT
				+JavaUtils.importDomain(transaction.getFullDomainDTO().getEntityDomain())
				+JavaUtils.importDomain(transaction.getFullDomainDTO().getServiceDomain())
				+JavaUtils.importDomain(transaction.getFullDomainDTO().getBussinessDomain());
	}

}
