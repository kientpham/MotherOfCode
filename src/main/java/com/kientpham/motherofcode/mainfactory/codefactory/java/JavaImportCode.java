package com.kientpham.motherofcode.mainfactory.codefactory.java;

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
	public String crudRepository(String entityFullDomain) {
		return "import org.springframework.data.repository.CrudRepository;\r\n" + "import " + entityFullDomain;
	}

}
