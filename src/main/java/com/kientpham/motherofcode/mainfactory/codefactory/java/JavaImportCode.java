package com.kientpham.motherofcode.mainfactory.codefactory.java;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.codefactory.ImportCode;

public class JavaImportCode implements ImportCode {

	@Override
	public String importEntity(boolean hasJoinTable) {
		String sImports = "import javax.persistence.Entity;\r\n" + "import javax.persistence.Column;\r\n"
				+ "import javax.persistence.GeneratedValue;\r\n" + "import javax.persistence.GenerationType;\r\n"
				+ "import javax.persistence.Id;\r\n" + "import javax.persistence.Table;\r\n";

		if (hasJoinTable) {
			sImports += "import javax.persistence.JoinTable;\r\n" + "import javax.persistence.ManyToMany;\r\n"
					+ "import javax.persistence.JoinColumn;\r\n";
		}

		return sImports;

	}

	@Override
	public String lombokGetterSetter() {
		return "import lombok.Getter;\r\n" + "import lombok.Setter;\r\n";
	}

	@Override
	public String serializable() {
		return "import java.io.Serializable;\r\n";
	}

	@Override
	public String list() {
		return "import java.util.List;\r\n";
	}
	
	@Override
	public String dataSort() {
		return "import org.springframework.data.domain.Sort;\r\n";
	}	

	@Override
	public String crudRepository(String entityFullDomain) {
		return "import org.springframework.data.repository.CrudRepository;\r\n" + "import " + entityFullDomain+";\r\n";
	}
	
	@Override
	public String repository(String entityFullDomain) {
		return "import org.springframework.data.domain.Page;\r\n" + 
				"import org.springframework.data.domain.Pageable;\r\n" + 
				"import org.springframework.data.repository.Repository;\r\n" + 
				"import org.springframework.data.jpa.repository.Query;\r\n"+
				"import org.springframework.data.repository.query.Param;\r\n"+
				"import " + entityFullDomain+";\r\n";
	}

	@Override
	public String importSpringComponent() {
		return "import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
				"import org.springframework.stereotype.Component;";
	}

	@Override
	public String importDomain(String domain) {
		return "import " + domain+";\r\n";
	}
	
	@Override
	public String importListArray() {
		return "import java.util.ArrayList;\r\n" + 
				"import java.util.HashMap;\r\n" + 
				"import java.util.List;\r\n" + 
				"import java.util.Map;\r\n" + 
				"import java.util.HashSet;\r\n"+
				"import java.util.Arrays;\r\n"+
				"import java.util.Set;\r\n";
	}
	
	@Override
	public String importSpringPageble() {
		return "import org.springframework.data.domain.Page;\r\n" + 
				"import org.springframework.data.domain.Pageable;";
	}

	@Override
	public String importSpringPaging() {

		return "import org.springframework.data.domain.Page;\r\n" +				 
				"import org.springframework.data.domain.Pageable;\r\n" ;
	}
	
	@Override
	public String importSpringPageRequest() {

		return  "import org.springframework.data.domain.PageRequest;\r\n" +				 
				"import org.springframework.data.domain.Sort;\r\n";
	}


	

}
