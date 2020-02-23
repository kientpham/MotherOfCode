package com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaCommon;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.Const;

public class ImportLibBuilderBase implements ImportLibInterface {

	@Override
	public String importForEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String sImports = "import javax.persistence.Entity;\r\n" + "import javax.persistence.Column;\r\n"
				+ "import javax.persistence.GeneratedValue;\r\n" + "import javax.persistence.GenerationType;\r\n"
				+ "import javax.persistence.Id;\r\n" + "import javax.persistence.Table;\r\n\r\n";

		if (omnibusDTO.getTransaction().getEntity().hasFieldType(Const.UUID)) {
			sImports += JavaConst.ImportUUID;
		}
		if (omnibusDTO.getTransaction().getEntity().hasFieldType(Const.DATE)) {
			sImports += JavaConst.DATE;
		}
		if (omnibusDTO.getTransaction().getEntity().hasFieldType(Const.CURRENCY)) {
			sImports += JavaConst.CURRENCY;
		} 		
		if (omnibusDTO.getTransaction().getEntity().hasFieldType(Const.LOCALDATETIME)) {
			sImports += JavaConst.LOCALDATETIME;
		} 
		Set<String> relationSet = new HashSet<String>();
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				relationSet.add(joinTable.getRelation());
				sImports += JavaCommon.importDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getEntityDomain());
			}
			sImports += "\r\nimport javax.persistence.JoinTable;\r\n" + "import javax.persistence.JoinColumn;\r\n"
					+ JavaConst.LIST;
			for (String relation : relationSet) {
				sImports += String.format("import javax.persistence.%1$s;\r\n", relation.substring(1));
			}

		}

		sImports += JavaConst.LOMBOKGETSET;
		return sImports;
	}

	@Override
	public String importForRepository(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {

		return JavaConst.CRUDREPOSITORY
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain())
				+ this.importUUID(omnibusDTO);
	}

	@Override
	public String importForDBGatewayInterface(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.LIST
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain())
				+ this.importUUID(omnibusDTO);
	}

	@Override
	public String importForDBGateway(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.LIST + JavaConst.AUTOWIRED + JavaConst.COMPONENT
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain())
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getRepositoryDomain())
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getDbGateway())
				+ this.importUUID(omnibusDTO);
	}

	@Override
	public String importForBusinessObject(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		Entity entity = omnibusDTO.getTransaction().getEntity();

		if (entity.hasJoinTable()) {
			for (JoinTable joinTable : entity.getJoinTables()) {
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)) {
					code = JavaConst.SET + JavaConst.ARRAYLIST + JavaCommon
							.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getJoinListDomain());
					break;
				}
			}
		}

		if ((entity.getIDField().getType().equals(JavaConst.STRING)
				&& entity.getIDField().getFieldType().contains(Const.RANDOM)) || entity.hasCreatedOrLastUpdated()) {
			code += JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getDateStringUtils());
		}

		if (entity.getIDField().getType().equals(Const.UUID) || (entity.getIDField().getType().equals(JavaConst.STRING)
				&& entity.getIDField().getFieldType().equals(Const.UUID))) {
			code += JavaConst.ImportUUID;
		}

		return code + JavaConst.LIST + JavaConst.AUTOWIRED + JavaConst.COMPONENT
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain())
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getDbGateway())
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());

	}

	@Override
	public String importForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				code += JavaCommon.importDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getJoinListDomain());
			}
		}

		return code + JavaConst.LIST
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain())
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain())
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain())
				+ this.importUUID(omnibusDTO) + Const.RETURN;
	}

	@Override
	public String importForWriteService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.LIST
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain())
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain())
				+ this.importUUID(omnibusDTO) + Const.RETURN;
	}

	@Override
	public String importForWriteServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = JavaConst.LIST + JavaConst.ARRAYLIST + JavaConst.AUTOWIRED + JavaConst.COMPONENT + JavaConst.MAP
				+ this.importUUID(omnibusDTO) + "\r\n";
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain());
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getBussinessDomain());

		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getWriteService());
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				code += JavaCommon.importDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getEntityDomain());
				code += JavaCommon.importDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getBussinessDomain());
				code += JavaCommon.importDomain(
						omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getJoinListDomain());
			}
		}
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain());
		String lookupService = omnibusDTO.getSharedDTO()
				.getFullDomainDTO(omnibusDTO.getSharedDTO().getLookUpEntityName()).getReadService();
		if (!omnibusDTO.getTransaction().getFullDomainDTO().getReadService().equals(lookupService)) {
			code += JavaCommon.importDomain(lookupService);
		}
		return code;
	}

	@Override
	public String importForRepositoryPaging(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}

	@Override
	public String importForEditModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		if (omnibusDTO.getTransaction().getEntity().hasFieldType(Const.DATE)) {
			code += JavaConst.DATE;
		}
		if (omnibusDTO.getTransaction().getEntity().hasFieldType(Const.CURRENCY)) {
			code  += JavaConst.CURRENCY;
		}
		if (omnibusDTO.getTransaction().getEntity().hasFieldType(Const.LOCALDATETIME)) {
			code+= JavaConst.LOCALDATETIME;
		} 
		if (omnibusDTO.getTransaction().getEntity().hasFieldType(Const.UUID)) {
			code += JavaConst.ImportUUID;
		} else if (omnibusDTO.getTransaction().getEntity().hasJoinTable()) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				Entity joinEntity = omnibusDTO.getSharedDTO().getEntityByName(joinTable.getType());
				if ((joinTable.getRelation().equals(JavaConst.ONETOMANY)
						|| joinTable.getRelation().equals(JavaConst.MANYTOMANY))
						&& joinEntity.getIDField().getType().equals(Const.UUID)) {
					code += JavaConst.ImportUUID;
					break;
				}
			}
		}

		return code + JavaConst.LIST
				+ JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain())
				+ JavaConst.LOMBOKGETSET + this.importUUID(omnibusDTO);
	}

	@Override
	public String importForTableModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code="";
		if (omnibusDTO.getTransaction().getEntity().hasFieldType(Const.DATE)) {
			code += JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getDateStringUtils());
		}
		return code+JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain()) + JavaConst.MAP
				+ JavaConst.LOMBOKGETSET + this.importUUID(omnibusDTO);
	}

	@Override
	public String importForJoinList(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() == null)
			return "";
		return JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain())
				+ JavaConst.IMPORT_SERIALIZABLE + JavaConst.LOMBOKGETSET + this.importUUID(omnibusDTO);
	}

	@Override
	public String importForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = JavaConst.LIST + JavaConst.ARRAYLIST + JavaConst.AUTOWIRED + JavaConst.COMPONENT + JavaConst.MAP
				+ this.importUUID(omnibusDTO) + "\r\n";
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEntityDomain());
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getBussinessDomain());
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getReadService());
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());

		List<JoinTable> listJoinTable = omnibusDTO.getTransaction().getEntity().getJoinTables();
		if (listJoinTable != null) {
			for (JoinTable joinTable : listJoinTable) {
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY)) {
					code += JavaConst.COLLECTORS + JavaCommon.importDomain(
							omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getEntityDomain());
					code += JavaCommon.importDomain(
							omnibusDTO.getSharedDTO().getFullDomainTable().get(joinTable.getType()).getReadService());
					code += JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFullDomainTable()
							.get(joinTable.getType()).getJoinListDomain());
					code += JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFullDomainTable()
							.get(joinTable.getType()).getBussinessDomain());
				}
			}
		}
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain());
		String lookupService = omnibusDTO.getSharedDTO()
				.getFullDomainDTO(omnibusDTO.getSharedDTO().getLookUpEntityName()).getReadService();
		if (!omnibusDTO.getTransaction().getFullDomainDTO().getReadService().equals(lookupService)) {
			code += JavaCommon.importDomain(lookupService);
		}
		return code;
	}

	@Override
	public String importForController(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = JavaConst.LIST + "\r\n" + JavaConst.AUTOWIRED + this.importUUID(omnibusDTO);
		code += JavaCommon.importDomain("org.springframework.http.HttpStatus");
		code += JavaCommon.importDomain("org.springframework.http.ResponseEntity");
		code += JavaCommon.importDomain("org.springframework.web.bind.annotation.RequestBody");
		code += JavaCommon.importDomain("org.springframework.web.bind.annotation.RequestMapping");
		code += JavaCommon.importDomain("org.springframework.web.bind.annotation.RequestMethod");
		code += JavaCommon.importDomain("org.springframework.web.bind.annotation.RequestParam");
		code += JavaCommon.importDomain("org.springframework.web.bind.annotation.RestController");
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain());
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain());
		code += JavaCommon.importDomain(omnibusDTO.getTransaction().getFullDomainDTO().getReadService());
		if (omnibusDTO.getTransaction().getEntity().getJoinTables() != null) {
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				code += JavaCommon.importDomain(
						omnibusDTO.getSharedDTO().getFullDomainDTO(joinTable.getType()).getJoinListDomain());
			}
		}
		return code;
	}

	private String importUUID(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return (omnibusDTO.getTransaction().getEntity().getIDField().getType().equals(Const.UUID))
				? JavaConst.ImportUUID
				: "";
	}

}
