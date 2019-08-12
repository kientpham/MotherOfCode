package com.kientpham.motherofcode.easywebapp.workflow.factory.methodbuilder;

import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.utils.PackageUtils;

public class BuildMethodHasPaging extends JavaMethodBuilder{	

	@Override
	public String buildMethodForRepository(TransactionModel transaction) {
		Entity entity=transaction.getEntity();
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
	public String buildMethodForDBGatewayInterface(TransactionModel transaction) {
		String pageOutput = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getPagingOutput());
		String pageInput = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getPagingInput());
		return String.format(
				"\tpublic Page<%1$s> findAll(Pageable pageRequest);\r\n\r\n"
						+ "\tpublic %2$s<%1$s> search(%3$s pagingInput);",
				transaction.getEntity().getName(), pageOutput, pageInput);
	}

	@Override
	public String buildMethodForDBGateway(TransactionModel transaction) {
		String pagingRepo = PackageUtils
				.getObjectNameFromDomain(transaction.getFullDomainDTO().getRepositoryPagingDomain());
		String code = String.format("\t@Autowired\r\n" + "\tprivate %1$s pagingRepository;\r\n\r\n", pagingRepo);

		String pageOutput = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getPagingOutput());
		String pageInput = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getPagingInput());
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
				+ "		return pageOutput;\r\n" + "	}\r\n\r\n", transaction.getEntity().getName(), pageOutput,
				pageInput);

		return code;
	}

	@Override
	public String buildMethodForBusinessObject(TransactionModel transaction) {
		String dbGatewayInterface = PackageUtils
				.getObjectNameFromDomain(transaction.getFullDomainDTO().getDbGatewayInterface());
		return String.format(
				"\r\n\tpublic Page<%2$s> getAllPaging(Pageable pageRequest){\r\n"
						+ "		return dbGateway.findAll(pageRequest);\r\n" + "	}	\r\n\r\n"
						+ "	public PagingOutputDTO<%2$s> searchPaging(PagingInputDTO pagingInput) {\r\n"
						+ "		\r\n" + "		return dbGateway.search(pagingInput);\r\n" + "	}",
				dbGatewayInterface, transaction.getEntity().getName());
	}

	@Override
	public String buildMethodForServiceInterface(TransactionModel transaction) {
		return String.format(
				"\r\n\tpublic PagingOutputDTO<%1$s> search%1$s(PagingInputDTO pagingInput);\r\n" + "	\r\n"
						+ "\tpublic Page<%1$s> getAll%1$s(Pageable pageRequest);\r\n\r\n",
				transaction.getEntity().getName());
	}

	@Override
	public String buildMethodForServiceClass(TransactionModel transaction) {
		String entityName = transaction.getEntity().getName();
		String domainObject = entityName.toLowerCase() + "Domain";
		return String.format("\r\n\t@Override\r\n" + "	public Page<%1$s> getAll%1$s(Pageable pageRequest) {\r\n"
				+ "		return %2$s.getAllPaging(pageRequest);\r\n" + "	}\r\n" + "	@Override\r\n"
				+ "	public PagingOutputDTO<User> search%1$s(PagingInputDTO pagingInput) {\r\n"
				+ "		return %2$s.searchPaging(pagingInput);\r\n" + "	}", entityName, domainObject);
	}

}
