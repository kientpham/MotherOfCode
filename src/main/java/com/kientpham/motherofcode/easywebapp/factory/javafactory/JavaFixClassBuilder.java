package com.kientpham.motherofcode.easywebapp.factory.javafactory;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.FixClassInterface;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public class JavaFixClassBuilder implements FixClassInterface {

	@Override
	public String buildPagingOutput(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(
				JavaConst.LIST + JavaConst.LOMBOKGETSET + "\r\n" + JavaConst.GETSETANNOTATED
						+ "public class %1$s<T>{\r\n\tprivate List<T> content;\r\n\r\n\tprivate int totalElements;",
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingOutput())
						.trim());
	}

	@Override
	public String buildPagingInput(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(
				JavaConst.LOMBOKGETSET + JavaConst.GETSETANNOTATED + "public class %1$s<T>" + "{\r\n"
						+ "\tprivate int start;\r\n" + "	\r\n" + "	private int length;\r\n" + "	\r\n"
						+ "	private String searchTerm;\r\n" + "	\r\n" + "	private String sortedColumnName;\r\n"
						+ "	\r\n" + "	private String order;\r\n",
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingInput())
						.trim());
	}

	@Override
	public String buildColumn(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(
				JavaConst.LOMBOKGETSET + JavaConst.GETSETANNOTATED + "public class %1$s {\r\n"
						+ "    private String data;\r\n" + "\r\n" + "    private String name;\r\n" + "\r\n"
						+ "    private boolean searchable;\r\n" + "\r\n" + "    private boolean orderable;\r\n" + "\r\n"
						+ "    private SearchCriteria search;",
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getColumnClass()));
	}

	@Override
	public String buildOrderingCriteria(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(
				JavaConst.LOMBOKGETSET + JavaConst.GETSETANNOTATED + "public class %1$s {\r\n" + "\r\n"
						+ "    public static final String ASC = \"asc\";\r\n"
						+ "    public static final String DESC = \"desc\";\r\n" + "\r\n" + "    /**\r\n"
						+ "     * Column to which ordering should be applied. This is an index reference to the\r\n"
						+ "     * columns array of information that is also submitted to the server.\r\n"
						+ "     */\r\n" + "    private int column;\r\n" + "    \r\n" + "    /**\r\n"
						+ "     * Ordering direction for this column. It will be asc or desc to indicate\r\n"
						+ "     * ascending ordering or descending ordering, respectively.\r\n" + "     */\r\n"
						+ "    private String dir;\r\n" + "",
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getOrderingCriteria()));
	}

	@Override
	public String buildPaginationCriteria(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(JavaConst.LIST + JavaConst.LOMBOKGETSET + JavaConst.GETSETANNOTATED
				+ "public class %1$s {\r\n" + "	/**\r\n"
				+ "     * Draw counter. This is used by DataTables to ensure that the Ajax returns from\r\n"
				+ "     * server-side processing requests are drawn in sequence by DataTables.\r\n"
				+ "     * This is used as part of the draw return parameter.\r\n" + "     */\r\n"
				+ "    private int draw;\r\n" + "\r\n" + "    /**\r\n"
				+ "     * Paging first record indicator. This is the start point in the current data\r\n"
				+ "     * set (0 index based - i.e. 0 is the first record).\r\n" + "     */\r\n"
				+ "    private int start;\r\n" + "\r\n" + "    /**\r\n"
				+ "     * Number of records that the table can display in the current draw. \r\n"
				+ "     * Note that this can be -1 to indicate that all records should be returned \r\n" + "     */\r\n"
				+ "    private int length;\r\n" + "\r\n" + "    private SearchCriteria search;\r\n" + "\r\n"
				+ "    private List<OrderingCriteria> order;\r\n" + "\r\n" + "    private List<Column> columns;",
				CommonUtils
						.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPaginationCriteria()));
	}

	@Override
	public String buildSearchCriteria(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(JavaConst.LOMBOKGETSET + JavaConst.GETSETANNOTATED + "public class %1$s {\r\n"
				+ "    /**\r\n" + "     * Search value. To be applied to all columns which have searchable as true.\r\n"
				+ "     */\r\n" + "    private String value;\r\n" + "\r\n" + "    /**\r\n"
				+ "     * true if the filter should be treated as a regular expression for advanced searching, false otherwise.\r\n"
				+ "     */\r\n" + "    private boolean regex;",
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getSearchCriteria()));
	}

	@Override
	public String buildTablePage(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(JavaConst.LIST + JavaConst.MAP
				+ JavaCommon.importDomain("com.fasterxml.jackson.annotation.JsonInclude") + JavaConst.LOMBOKGETSET
				+ "\r\n" + JavaConst.GETSETANNOTATED + "public class %1$s<T>{\r\n\t" + "    /**\r\n"
				+ "     * The draw counter that this object is a response to - from the draw parameter sent as part of the data request.\r\n"
				+ "     */\r\n" + "    private int draw;\r\n" + "\r\n" + "    /**\r\n"
				+ "     * Total records, before filtering.\r\n" + "     */\r\n" + "    private long recordsTotal;\r\n"
				+ "\r\n" + "    /**\r\n" + "     * Total records, after filtering \r\n" + "     */\r\n"
				+ "    private long recordsFiltered;\r\n" + "\r\n" + "    /**\r\n"
				+ "     * The data to be displayed in the table. This is an array of data source objects, one for each row, which will be used by DataTables.\r\n"
				+ "     */\r\n" + "    private List<Map<String, String>> data;\r\n" + "    \r\n"
				+ "    //private List<User> data;\r\n" + "\r\n" + "    /**\r\n"
				+ "     * Inform the user of this error by passing back the error message to be displayed using this parameter. Do not include if there is no error.\r\n"
				+ "     */\r\n" + "    @JsonInclude()\r\n" + "    private String error;",
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getTablePage()).trim());
	}

	@Override
	public String buildDataTablePresenter(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format(JavaConst.LIST + JavaConst.ARRAYLIST + JavaConst.HASHMAP + JavaConst.MAP+JavaConst.ITERATOR 
				+ JavaConst.MAP_ENTRY + JavaConst.LOMBOKGETSET + "\r\n"
				+ JavaCommon.importDomain("java.util.stream.Collectors") + "\r\n"
				+ JavaCommon.importDomain("org.apache.logging.log4j.util.Strings") + "\r\n"
				+ JavaCommon.importDomain("com.fasterxml.jackson.core.type.TypeReference")
				+ JavaCommon.importDomain("com.fasterxml.jackson.databind.ObjectMapper")
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getTablePage()) + "\r\n"
				+ JavaConst.GETSETANNOTATED + "public class %1$s<T>{\r\n"
				+ "\tprivate static ObjectMapper objectMapper = new ObjectMapper();\r\n" + "\r\n"
				+ "	public static TablePage buildTablePage(List<?> data, int draw, int totalSize) {\r\n"
				+ "		TablePage page = new TablePage();\r\n" + "		page.setDraw(draw);\r\n"
				+ "		page.setRecordsTotal(totalSize);\r\n" + "		page.setRecordsFiltered(totalSize);\r\n"
				+ "		List<Map<String, String>> records = getData(data);\r\n" + "		page.setData(records);\r\n"
				+ "		return page;\r\n" + "	}\r\n" + "\r\n"
				+ "	private static List<Map<String, String>> getData(List<?> data) {\r\n"
				+ "		List<Map<String, String>> records = new ArrayList<>(data.size());\r\n"
				+ "		data.forEach(i -> {\r\n"
				+ "			Map<String, Object> oldMap = objectMapper.convertValue(i, new TypeReference<Map<String, Object>>() {\r\n"
				+ "			});\r\n" + "			Map<String, Object> newMap = new HashMap<String, Object>();\r\n"
				+ "			Iterator<Entry<String, Object>> it = oldMap.entrySet().iterator();\r\n"
				+ "			int column = 0;\r\n" + "			while (it.hasNext()) {\r\n"
				+ "				Entry<String, Object> pair = it.next();\r\n"
				+ "				newMap.put(Integer.toString(column), (pair.getValue() != null) ? pair.getValue().toString() : Strings.EMPTY);\r\n"
				+ "				column++;\r\n" + "			}\r\n"
				+ "			records.add(newMap.entrySet().stream().collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue().toString())));\r\n"
				+ "		});\r\n" + "		return records;\r\n" + "	}",
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getDataTablePresenter())
						.trim());
	}

}
