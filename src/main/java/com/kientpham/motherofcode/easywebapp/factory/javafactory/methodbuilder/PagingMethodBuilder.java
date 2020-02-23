package com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;
import com.kientpham.motherofcode.utils.PackageUtils;

public class PagingMethodBuilder implements MethodBuilderInterface {

	@Override
	public String buildMethodForController(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String listPresenter = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFullDomainDTO(entityName).getReadService());
		return String.format("\r\n\t@RequestMapping(value = \"/tablepage\", method = RequestMethod.POST)\r\n"
				+ "	@ResponseBody\r\n"
				+ "	public TablePage list%1$ss(@RequestBody PaginationCriteria paginationCriteria) {		\r\n"
				+ "		return %3$s.get%1$ssListPaging(paginationCriteria);\r\n" + "	}\r\n", entityName,
				entityName.toLowerCase(), CommonUtils.getLowerCaseFirstChar(listPresenter), omnibusDTO.getTransaction().getService().getName());
	}

	@Override
	public String buildMethodForRepositoryPaging(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		Entity entity = omnibusDTO.getTransaction().getEntity();
		String code = String.format("\t@Query(\"SELECT u FROM %1$s u WHERE ", entity.getName());
		String where = "";
		for (Field field : entity.getFields()) {
			if (!where.isEmpty())
				where += " OR ";
			where += "LOWER(u." + field.getName() + ") LIKE LOWER(CONCAT(\'%\',:searchTerm, \'%\')) ";
		}
		code += where + "\")"
				+ String.format(
						"\r\n\tPage<%1$s> search%1$s(@Param(\"searchTerm\") String searchTerm, Pageable pageRequest);",
						entity.getName());
		return code;
	}

	@Override
	public String buildMethodForDBGatewayInterface(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String pageOutput = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingOutput());
		String pageInput = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingInput());
		return String.format(
				"\tpublic Page<%1$s> findAll(Pageable pageRequest);\r\n\r\n"
						+ "\tpublic %2$s<%1$s> search(%3$s pagingInput);",
				omnibusDTO.getTransaction().getEntity().getName(), pageOutput, pageInput);
	}

	@Override
	public String buildMethodForDBGateway(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String pagingRepo = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getRepositoryPagingDomain());
		String code = String.format("\t@Autowired\r\n" + "\tprivate %1$s pagingRepository;\r\n\r\n", pagingRepo);

		String pageOutput = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingOutput());
		String pageInput = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingInput());
		code += String.format("\r\n\t@Override\r\n" + "	public Page<%1$s> findAll(Pageable pageRequest) {\r\n"
				+ "		return pagingRepository.findAll(pageRequest);\r\n" + "	}\r\n\r\n" + "	@Override\r\n"
				+ "	public %2$s<%1$s> search(%3$s pagingInput) {\r\n"
				+ "		String searchTerm = pagingInput.getSearchTerm();\r\n"
				+ "		String sortColumn = pagingInput.getSortedColumnName();\r\n"
				+ "		Sort sort = (pagingInput.getOrder().equalsIgnoreCase(\"ASC\")) ? Sort.by(sortColumn).ascending()\r\n"
				+ "				: Sort.by(sortColumn).descending();\r\n"
				+ "		Pageable pageRequest = PageRequest.of(pagingInput.getStart() / pagingInput.getLength(), pagingInput.getLength(),\r\n"
				+ "				sort);\r\n" + "		Page<%1$s> page;\r\n" + "		if (searchTerm.isEmpty()) {\r\n"
				+ "			page = pagingRepository.findAll(pageRequest);\r\n" + "		} else {\r\n"
				+ "			page = pagingRepository.search%1$s(searchTerm, pageRequest);\r\n" + "		}\r\n"
				+ "		%2$s<%1$s> pageOutput = new %2$s<%1$s>();\r\n"
				+ "		pageOutput.setContent(page.getContent());\r\n"
				+ "		pageOutput.setTotalElements((int) page.getTotalElements());\r\n" + "\r\n"
				+ "		return pageOutput;\r\n" + "	}\r\n\r\n", omnibusDTO.getTransaction().getEntity().getName(),
				pageOutput, pageInput);

		return code;
	}

	@Override
	public String buildMethodForBusinessObject(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String dbGatewayInterface = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getDbGateway());
		return String.format(
				"\r\n\tpublic Page<%2$s> getAllPaging(Pageable pageRequest){\r\n"
						+ "		return dbGateway.findAll(pageRequest);\r\n" + "	}	\r\n\r\n"
						+ "	public PagingOutputDTO<%2$s> searchPaging(PagingInputDTO pagingInput) {\r\n"
						+ "		\r\n" + "		return dbGateway.search(pagingInput);\r\n" + "	}",
				dbGatewayInterface, omnibusDTO.getTransaction().getEntity().getName());
	}

	@Override
	public String buildMethodForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(
				"\r\n\tpublic PagingOutputDTO<%1$s> search%1$s(PagingInputDTO pagingInput);\r\n" + "	\r\n"
						+ "\tpublic Page<%1$s> getAll%1$s(Pageable pageRequest);\r\n\r\n"
						+ "\tpublic TablePage get%1$ssListPaging(PaginationCriteria paginationCriteria);\r\n",
				omnibusDTO.getTransaction().getEntity().getName());
	}

	@Override
	public String buildMethodForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String domainObject = entityName.toLowerCase() + "Domain";
		return String
				.format("\r\n\t@Override\r\n" + "	public Page<%1$s> getAll%1$s(Pageable pageRequest) {\r\n"
						+ "		return %2$s.getAllPaging(pageRequest);\r\n" + "	}\r\n" + "	@Override\r\n"
						+ "	public PagingOutputDTO<User> search%1$s(PagingInputDTO pagingInput) {\r\n"
						+ "		return %2$s.searchPaging(pagingInput);\r\n" + "	}", entityName, domainObject)
				+ this.buildMethodForListPresenter(omnibusDTO);

	}

	private String buildMethodForListPresenter(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String map = "";
		int n = 1;
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			if (field.getSort() != null) {
				map += String.format("\r\n\t\t\tput(%1$s, \"%2$s\");", n, field.getName());
				n++;
			}
		}
		String code = String.format("\r\n\tMap<Integer, String> columnMap = new HashMap<Integer, String>() {\r\n"
				+ "		private static final long serialVersionUID = 1L;\r\n" + "		{%1$s\r\n\t\t}\r\n" + "	};\r\n",
				map);

		String pagingInputDTO = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingInput());
		String pagingOutputDTO = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingOutput());
		String businessDomainName = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getBussinessDomain());
		String lookupEntity = omnibusDTO.getSharedDTO().getLookUpEntityName();
		String lookupService = CommonUtils.getObjectNameFromDomain(
				omnibusDTO.getSharedDTO().getFullDomainTable().get(lookupEntity).getReadService());
		// .getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getCategoryService());
		String dataTable = PackageUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain());

		// code +=String.format("\r\n\t@Autowired\r\n" +
		// " private %1$s %2$s;\r\n", lookupService,
		// CommonUtils.getLowerCaseFirstChar(lookupService));

		code += String.format("\r\n\tpublic TablePage get%1$ssListPaging(PaginationCriteria paginationCriteria) {\r\n"
				+ "\r\n" + "		%2$s pagingInput = new %2$s();\r\n"
				+ "		pagingInput.setStart(paginationCriteria.getStart());\r\n"
				+ "		pagingInput.setLength(paginationCriteria.getLength());\r\n"
				+ "		pagingInput.setSearchTerm(paginationCriteria.getSearch().getValue());\r\n"
				+ "		pagingInput.setSortedColumnName(columnMap.get(paginationCriteria.getOrder().get(0).getColumn()));\r\n"
				+ "		pagingInput.setOrder(paginationCriteria.getOrder().get(0).getDir());\r\n" + "\r\n"
				+ "		%3$s<%1$s> pagingOutput = %7$s.searchPaging(pagingInput);\r\n" + "\r\n"
				+ "		List<%6$s> list%1$sTable = this.convertList%1$sToDataTable(pagingOutput.getContent(),\r\n"
				+ "				%5$s.getMapAll%8$s());\r\n" + "\r\n"
				+ "		return DataTablePresenter.buildTablePage(list%1$sTable, paginationCriteria.getDraw(),\r\n"
				+ "				(int) pagingOutput.getTotalElements());\r\n" + "	}", entityName, pagingInputDTO,
				pagingOutputDTO, entityName.toLowerCase(), CommonUtils.getLowerCaseFirstChar(lookupService), dataTable,
				CommonUtils.getLowerCaseFirstChar(businessDomainName), lookupEntity);

		code += String.format(
				"\r\n\tprivate List<%3$s> convertList%1$sToDataTable(List<%1$s> %2$sList, Map<Integer, String> categoryMap) {\r\n"
						+ "		List<%3$s> list = new ArrayList<%3$s>();\r\n"
						+ "		for (%1$s %2$s : %2$sList) {\r\n"
						+ "			list.add(new %3$s(%2$s, categoryMap));\r\n" + "		}\r\n"
						+ "		return list;\r\n" + "	}",
				entityName, entityName.toLowerCase(), dataTable);
		return code;
	}

	@Override
	public String buildMethodForEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}

	@Override
	public String buildMethodForRepository(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}

	@Override
	public String buildMethodForEditModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String buildMethodForTableModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String buildMethodForJoinList(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String buildMethodForWriteService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String buildMethodForWriteServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

}
