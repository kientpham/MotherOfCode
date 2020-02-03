package com.kientpham.motherofcode.easywebapp.workflow;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.kientpham.motherofcode.baseworkflow.RequestHandlerBase;
import com.kientpham.motherofcode.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.easywebapp.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.factory.PackageInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.classname.ClassNameBuilderBase;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.classname.ClassNamePaging;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder.ImportLibBuilderBase;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder.ImportLibForCategory;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder.ImportLibForPaging;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder.ImportLibForUser;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.BuildMethodForCategory;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.BuildMethodForUser;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.BuildMethodHasPaging;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.MethodBuilderBase;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.packagebuilder.JavaPackageBuilder;
import com.kientpham.motherofcode.easywebapp.model.Application;
import com.kientpham.motherofcode.easywebapp.model.CodeFactory;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.FullDomainDTO;
import com.kientpham.motherofcode.easywebapp.model.Service;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeBuilder;
import com.kientpham.motherofcode.utils.Const;

public class RequestHandler implements RequestHandlerBase<TransactionModel>{

	EasyWebAppFactory factory=new EasyWebAppFactory();
	
	@Override
	public List<TransactionModel> startProcessing(List<?> inputParams) throws WorkflowException {
		Application app = this.getApplicationFromXML(inputParams.get(0).toString());
		CodeBuilder codeFacade = new CodeBuilder(inputParams.get(1).toString());

		List<TransactionModel> listTransaction = new ArrayList<TransactionModel>();
		List<Service> services = app.getServices();
		for (Service service : services) {
			List<Entity> entities = service.getEntities();
			for (Entity entity : entities) {
				TransactionModel model = new TransactionModel();
				model.setApplication(app);
				model.setService(service);
				model.setEntity(entity);
				model.setCodeFacade(codeFacade);
				model.setCodeFactory(this.getCodeFactory(inputParams.get(1).toString(), entity));
				FullDomainDTO domainDTO = new FullDomainDTO();
				model.setFullDomainDTO(domainDTO);
				listTransaction.add(model);
			}
		}
		return factory.startWorkflow(listTransaction);
	}
	
	private CodeFactory getCodeFactory(String language, Entity entity) {
		CodeFactory codeFactory = new CodeFactory();
		PackageInterface packageBuilder = null;
		List<ImportLibInterface> importLibBuilderList = new ArrayList<ImportLibInterface>();
		List<ClassNameInterface> classNameBuilderList = new ArrayList<ClassNameInterface>();
		List<MethodBuilderInterface> methodBuilderList = new ArrayList<MethodBuilderInterface>();

		if (language.equals(Const.JAVA)) {
			packageBuilder = new JavaPackageBuilder();
			String type = "";
			if (entity.getType() != null) {
				type = entity.getType();
			}
			switch (type) {
			case Const.CATEGORY:
				importLibBuilderList.add(new ImportLibForCategory());
				methodBuilderList.add(new BuildMethodForCategory());
				break;
			case Const.USER:
				importLibBuilderList.add(new ImportLibForUser());
				methodBuilderList.add(new BuildMethodForUser());
				break;
			default:
				importLibBuilderList.add(new ImportLibBuilderBase());
				methodBuilderList.add(new MethodBuilderBase());
				break;
			}
			if (entity.hasPaging() != null) {
				importLibBuilderList.add(new ImportLibForPaging());
				classNameBuilderList.add(new ClassNamePaging());
				methodBuilderList.add(new BuildMethodHasPaging());
			} else {
				classNameBuilderList.add(new ClassNameBuilderBase());
			}
		}

		codeFactory.setPackageBuilder(packageBuilder);
		codeFactory.setImportLibBuilderList(importLibBuilderList);
		codeFactory.setClassNameBuilderList(classNameBuilderList);
		codeFactory.setMethodBuilderList(methodBuilderList);
		return codeFactory;

	}

	private Application getApplicationFromXML(String xmlFileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(xmlFileName);// "CodeModel.xml");
		File file = new File(resource.getFile());
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Application.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (Application) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
