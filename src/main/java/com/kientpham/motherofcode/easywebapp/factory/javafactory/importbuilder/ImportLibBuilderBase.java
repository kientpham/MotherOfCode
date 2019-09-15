package com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder;

import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public class ImportLibBuilderBase implements ImportLibInterface {

	@Override
	public String importForPagingInput(TransactionModel transaction) {
		return JavaConst.LOMBOKGETSET;
	}
	
	@Override
	public String importForPagingOutput(TransactionModel transaction) {
		return JavaConst.LIST +JavaConst.LOMBOKGETSET;
	}
	
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
		return JavaConst.CRUDREPOSITORY 
				+ "import "+transaction.getFullDomainDTO().getEntityDomain() +";\r\n";
	}

	@Override
	public String importForDBGatewayInterface(TransactionModel transaction) {		
		return JavaConst.LIST +
				"import "+ transaction.getFullDomainDTO().getEntityDomain() +";\r\n";
	}
	
	@Override
	public String importForDBGateway(TransactionModel transaction) {
		return JavaConst.LIST+JavaConst.AUTOWIRED+JavaConst.COMPONENT
				+"import "+transaction.getFullDomainDTO().getEntityDomain() +";\r\n"
				+"import "+transaction.getFullDomainDTO().getRepositoryDomain()+";\r\n"
				+"import "+transaction.getFullDomainDTO().getDbGatewayInterface()+";\r\n";
	}
	
	@Override
	public String importForBusinessObject(TransactionModel transaction) {
		
		return JavaConst.LIST+JavaConst.AUTOWIRED+JavaConst.COMPONENT
				+"import "+transaction.getFullDomainDTO().getEntityDomain()+";\r\n"
				+"import "+transaction.getFullDomainDTO().getDbGatewayInterface()+";\r\n";
	}

	@Override
	public String importForServiceInterface(TransactionModel transaction) {
		
		return JavaConst.LIST+
				"import "+transaction.getFullDomainDTO().getEntityDomain()+";\r\n";
	}

	@Override
	public String importForServiceClass(TransactionModel transaction) {		
		return JavaConst.LIST +JavaConst.AUTOWIRED + JavaConst.COMPONENT
				+"import "+transaction.getFullDomainDTO().getEntityDomain() +";\r\n"
				+"import "+transaction.getFullDomainDTO().getServiceDomain()+";\r\n"
				+"import "+transaction.getFullDomainDTO().getBussinessDomain()+";\r\n";
	}

	@Override
	public String importForRepositoryPaging(TransactionModel transaction) {
		return "";
	}



}
