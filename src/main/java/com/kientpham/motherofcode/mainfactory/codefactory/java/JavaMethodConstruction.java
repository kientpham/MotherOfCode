package com.kientpham.motherofcode.mainfactory.codefactory.java;



import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.codefactory.MethodConstruction;
import com.kientpham.motherofcode.utils.Const;
import com.kientpham.motherofcode.utils.PackageUtils;

public class JavaMethodConstruction implements MethodConstruction {

	@Override
	public String defaulConstructor(String className) {
		return String.format("\tpublic %1$s() {\r\n\t\t//default constructor\r\n\t}", className);
	}

	@Override
	public String searchEntity(Entity entity) {
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
	public String buildDBGateway(TransactionModel transaction, boolean hasPaging) {
		String code = "";
		String entityName = transaction.getEntity().getName();

		if (hasPaging) {
			String pagingRepo = PackageUtils
					.getObjectNameFromDomain(transaction.getFullDomainDTO().getRepositoryPagingDomain());
			code += String.format("\t@Autowired\r\n" + "\tprivate %1$s pagingRepository;\r\n\r\n", pagingRepo);
		}
		String entityRepo = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getRepositoryDomain());
		code += String.format(
				"\t@Autowired\r\n" + "\tprivate %3$s repository;\r\n\r\n" + "\t@Override\r\n"
						+ "\tpublic List<%1$s> findAll() {\r\n" + "\t\treturn (List<%1$s>) repository.findAll();\r\n"
						+ "\t}\r\n\r\n" + "\t@Override\r\n" + "\tpublic %1$s findById(%2$s id) {\r\n"
						+ "\t\treturn repository.findById(id).orElse(new %1$s());\r\n" + "\t}\r\n\r\n"
						+ "\t@Override\r\n" + "	public List<%1$s> findByListIds(List<%2$s> listId) {		\r\n"
						+ "		return (List<%1$s>) repository.findAllById(listId);\r\n" + "	}\r\n\r\n" + "\t@Override\r\n"
						+ "\tpublic void deleteById(%2$s id) {\r\n" + "\t\trepository.deleteById(id);		\r\n"
						+ "\t}\r\n\r\n" + "\t@Override\r\n" + "\tpublic %1$s save(%1$s entity) {\r\n"
						+ "\t\treturn repository.save(entity);\r\n" + "\t}\r\n",
				entityName, transaction.getEntity().getFields().get(0).getType(), entityRepo);
		if (hasPaging) {
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
					+ "		return pageOutput;\r\n" + "	}\r\n\r\n", entityName, pageOutput, pageInput);
		}
		
		String type=transaction.getEntity().getType();
		if(type!=null&& type.equalsIgnoreCase(Const.CATEGORY)) {
			code+=String.format("	@Override\r\n" + 
					"	public List<%1$s> findByCategoryGroup(String categoryGroup) {\r\n" + 
					"		return repository.findByCategoryGroup(categoryGroup);\r\n" + 
					"	}\r\n" + 
					"\r\n" + 
					"	@Override\r\n" + 
					"	public List<%1$s> findAllSorted() {\r\n" + 
					"		List<%1$s> listCat=repository.findAll(Sort.by(\"order\").ascending());		\r\n" + 
					"		return listCat;\r\n" + 
					"	}", entityName);
		}
		
		return code;
	}

	@Override
	public String buildDBGatewayInterface(TransactionModel transaction, boolean hasPaging) {
		String s = String.format(
				"\tpublic List<%1$s> findAll();\r\n\r\n" + "\tpublic %1$s findById(%2$s id);\r\n\r\n"
						+ "\tpublic List<%1$s> findByListIds(List<%2$s> listId);\r\n\r\n"
						+ "\tpublic void deleteById(%2$s id);\r\n\r\n" + "\tpublic %1$s save(%1$s entity);\r\n\r\n",
				transaction.getEntity().getName(), transaction.getEntity().getFields().get(0).getType());
		if (hasPaging) {
			String pageOutput = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getPagingOutput());
			String pageInput = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getPagingInput());
			s += String.format(
					"\tpublic Page<%1$s> findAll(Pageable pageRequest);\r\n\r\n"
							+ "\tpublic %2$s<%1$s> search(%3$s pagingInput);",
					transaction.getEntity().getName(), pageOutput, pageInput);
		}
		String type=transaction.getEntity().getType();
		if(type!=null&& type.equalsIgnoreCase(Const.CATEGORY)) {
		s+=String.format("	public List<%1$s> findByCategoryGroup(String categoryGroup);\r\n" + 
				"\r\n" + 
				"	public List<%1$s> findAllSorted();", transaction.getEntity().getName());
		}
		return s;
	}

	@Override
	public String buildBusinessObject(TransactionModel transaction, boolean hasPaging) {
		String dbGatewayInterface = PackageUtils
				.getObjectNameFromDomain(transaction.getFullDomainDTO().getDbGatewayInterface());
		String s = String.format(
				"	@Autowired\r\n" + "	private %1$s dbGateway;\r\n" + "	\r\n"
						+ "	public List<%2$s> findAll(){\r\n" + "		return dbGateway.findAll();\r\n"
						+ "	}\r\n" + "	\r\n" + "	public List<%2$s> findByListIds(List<%3$s> ids){\r\n"
						+ "		return dbGateway.findByListIds(ids);\r\n" + "	}\r\n" + "	\r\n"
						+ "	public %2$s getById(%3$s id) {\r\n" + "		return dbGateway.findById(id);\r\n" + "	}\r\n"
						+ "	\r\n" + "	public void deleteById(%3$s id) {\r\n" + "		dbGateway.deleteById(id);\r\n"
						+ "	}\r\n" + "	\r\n" + "	public %2$s save(%2$s entity) {\r\n"
						+ "		return dbGateway.save(entity);\r\n" + "	}\r\n",
				dbGatewayInterface, transaction.getEntity().getName(),
				transaction.getEntity().getFields().get(0).getType());
		if (hasPaging) {
			s += String.format(
					"\r\n\tpublic Page<%2$s> getAllPaging(Pageable pageRequest){\r\n"
							+ "		return dbGateway.findAll(pageRequest);\r\n" + "	}	\r\n\r\n"
							+ "	public PagingOutputDTO<%2$s> searchPaging(PagingInputDTO pagingInput) {\r\n"
							+ "		\r\n" + "		return dbGateway.search(pagingInput);\r\n" + "	}",
					dbGatewayInterface, transaction.getEntity().getName());
		}

		String type=transaction.getEntity().getType();
		if(type!=null&& type.equalsIgnoreCase(Const.CATEGORY)) {
			s+=String.format("	public List<%1$s> findAllSorted() {		\r\n" + 
					"		List<%1$s> listCat=dbGateway.findAllSorted();		\r\n" + 
					"		return listCat;\r\n" + 
					"	}\r\n" + 
					"\r\n" + 
					"	\r\n" + 
					"	public List<%1$s> getCategoryByGroup(String categoryGroup) {\r\n" + 
					"		return dbGateway.findByCategoryGroup(categoryGroup);\r\n" + 
					"	}	\r\n" + 
					"\r\n" + 
					"	\r\n" + 
					"	public List<%1$s> getCategoryByGroups(List<String> categoryGroups) {\r\n" + 
					"		List<%1$s> categoryList=new ArrayList<%1$s>();				\r\n" + 
					"		for(String catGroup: categoryGroups) {\r\n" + 
					"			categoryList.addAll(this.getCategoryByGroup(catGroup));	\r\n" + 
					"		}\r\n" + 
					"		return categoryList;\r\n" + 
					"	}\r\n" + 
					"\r\n" + 
					"	\r\n" + 
					"	public Map<Integer, String> getCategoryMapByGroups(List<String> categoryGroups) {\r\n" + 
					"		Map<Integer, String> map=new HashMap<Integer, String>();\r\n" + 
					"		for(String catGroup: categoryGroups) {\r\n" + 
					"			for(Category cat:this.getCategoryByGroup(catGroup)) {\r\n" + 
					"				map.put(cat.getId(), cat.getValue());\r\n" + 
					"			}	\r\n" + 
					"		}\r\n" + 
					"		return map;\r\n" + 
					"	}", transaction.getEntity().getName());
		}
		return s;
	}

	@Override
	public String buildServiceInterface(TransactionModel transaction, boolean hasPaging) {
		String s = String.format("\tpublic List<%1$s> getAll%1$s();\r\n" + "	\r\n"
				+ "	public List<%1$s> getList%1$sByIds(List<%2$s> listId);\r\n" + "	\r\n"
				+ "	public %1$s get%1$sById(%2$s id);\r\n" + "	\r\n" + "	public void delete%1$sById(%2$s id);\r\n"
				+ "\r\n" + "	public void deleteList%1$s(List<%2$s> ids);\r\n" + "	\r\n"
				+ "	public %1$s save%1$s(%1$s entity);\r\n", transaction.getEntity().getName(),
				transaction.getEntity().getFields().get(0).getType());
		if (hasPaging) {
			s += String.format(
					"\r\n\tpublic PagingOutputDTO<%1$s> search%1$s(PagingInputDTO pagingInput);\r\n" + "	\r\n"
							+ "\tpublic Page<%1$s> getAll%1$s(Pageable pageRequest);\r\n\r\n",
					transaction.getEntity().getName());
		}
		String type=transaction.getEntity().getType();
		if(type!=null&& type.equalsIgnoreCase(Const.USER)) {
			s+=String.format("public List<String> getListFeature(%1$s userId);\r\n", transaction.getEntity().getFields().get(0).getType());
		} else if(type!=null&& type.equalsIgnoreCase(Const.CATEGORY)) {
			s+=String.format("\r\n\tpublic Map<%1$s, String> getMapAllCategory();", transaction.getEntity().getFields().get(0).getType());
		}
		return s;
	}

	@Override
	public String buildServiceClass(TransactionModel transaction, boolean hasPaging) {
		String domainType = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getBussinessDomain());
		String entityName = transaction.getEntity().getName();
		String domainObject = entityName.toLowerCase() + "Domain";
		String idType=transaction.getEntity().getFields().get(0).getType();
		String s = String.format("	@Autowired\r\n" + "	private %4$s %2$s;	\r\n" + "	@Override\r\n"
				+ "	public List<%1$s> getList%1$sByIds(List<%3$s> listId) {		 \r\n"
				+ "		return %2$s.findByListIds(listId);\r\n" + "	}\r\n" + "	@Override\r\n"
				+ "	public List<%1$s> getAll%1$s() {		\r\n" + "		return (List<%1$s>)%2$s.findAll();\r\n"
				+ "	}\r\n" + "	@Override\r\n" + "	public %1$s get%1$sById(%3$s id) {\r\n"
				+ "		return %2$s.getById(id);\r\n" + "	}	\r\n" + "	@Override\r\n"
				+ "	public void deleteList%1$s(List<%3$s> ids) {\r\n" + "		for (%3$s id:ids) {\r\n"
				+ "			%2$s.deleteById(id);\r\n" + "		}		\r\n" + "	}\r\n" + "	@Override\r\n"
				+ "	public void delete%1$sById(%3$s id) {\r\n" + "		%2$s.deleteById(id);		\r\n" + "	}\r\n"
				+ "	@Override\r\n" + "	public %1$s save%1$s(%1$s entity) {\r\n"
				+ "		return %2$s.save(entity);\r\n" + "	}", entityName, domainObject,
				idType, domainType);

		if (hasPaging) {
			s += String.format("\r\n\t@Override\r\n" + "	public Page<%1$s> getAll%1$s(Pageable pageRequest) {\r\n"
					+ "		return %2$s.getAllPaging(pageRequest);\r\n" + "	}\r\n" + "	@Override\r\n"
					+ "	public PagingOutputDTO<User> search%1$s(PagingInputDTO pagingInput) {\r\n"
					+ "		return %2$s.searchPaging(pagingInput);\r\n" + "	}", entityName, domainObject);
		}
		String type=transaction.getEntity().getType();
		if(type!=null&& type.equalsIgnoreCase(Const.USER)) {
			s+=String.format("@Override\r\n" + 
					"	public List<String> getListFeature(%2$s userId) {\r\n" + 
					"		%1$s user = userDomain.getById(userId);\r\n" + 
					"		List<Group> groupList = user.getGroups();\r\n" + 
					"		List<Permission> permissionList = new ArrayList<Permission>();\r\n" + 
					"		for (Group group : groupList) {\r\n" + 
					"			permissionList.addAll(group.getPermissions());\r\n" + 
					"		}\r\n" + 
					"		Set<String> featureList = new HashSet<String>();\r\n" + 
					"		for (Permission permission : permissionList) {\r\n" + 
					"			String menuPath=permission.getMenuPath();\r\n" + 
					"			if (permission.getToggle() && menuPath!=null && !menuPath.isEmpty()) {\r\n" + 
					"				featureList.addAll(Arrays.asList(permission.getMenuPath().trim().split(\"\\\\\\\\\")));\r\n" + 
					"			}\r\n" + 
					"		}\r\n" + 
					"		return new ArrayList<String>(featureList);\r\n" + 
					"	}", entityName,idType);
		}else if(type!=null&& type.equalsIgnoreCase(Const.CATEGORY)) {
			s+=String.format("\r\n\t@Override\r\n" + 
					"	public Map<%1$s, String> getMapAllCategory() {\r\n" + 
					"		Map<%1$s, String> map = new HashMap<%1$s, String>();\r\n" + 
					"		for (Category cat : this.getAllCategory()) {\r\n" + 
					"			map.put(cat.getId(), cat.getValue());\r\n" + 
					"		}\r\n" + 
					"		return map;\r\n" + 
					"	}", transaction.getEntity().getFields().get(0).getType());
		}
		
		
		return s;
	}

	@Override
	public String buildCategoryRepo(TransactionModel transaction) {
		
		return String.format("\tList<%1$s> findByCategoryGroup(String categoryGroup);\r\n\r\n" + 
				"\tList<%1$s> findAll(Sort sort);", transaction.getEntity().getName());
	}
}
