package com.kientpham.motherofcode.easywebapp.workflow;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.baseworkflow.BaseTransactionManager;
import com.kientpham.motherofcode.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.easywebapp.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.FixClassInterface;
import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.factory.PackageInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaFixClassBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.classname.ClassNameBuilderBase;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.classname.ClassNamePaging;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder.ImportLibBuilderBase;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder.ImportLibForLookup;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder.ImportLibForPaging;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder.ImportLibForUser;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.LookupMethodBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.UserMethodBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.PagingMethodBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.methodbuilder.JavaMethodBaseBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.packagebuilder.JavaPackageBuilder;
import com.kientpham.motherofcode.easywebapp.model.Application;
import com.kientpham.motherofcode.easywebapp.model.CodeFactory;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.FixDomainDTO;
import com.kientpham.motherofcode.easywebapp.model.FullDomainDTO;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.Service;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.Const;

public class TransactionManager implements BaseTransactionManager<TransactionModel, SharedDTO> {

	private Application application;

	private String language;

	@Override
	public void saveTransaction(TransactionModel transaction) {
	}

	@Override
	public void updateTransactionWhenException(TransactionModel transaction, WorkflowException e) {
	}

	@Override
	public BaseOmnibusDTO<TransactionModel, SharedDTO> initiateBaseOmnibusDTO() throws WorkflowException {

		BaseOmnibusDTO<TransactionModel, SharedDTO> baseOmnibusDTO = new BaseOmnibusDTO<TransactionModel, SharedDTO>();
		SharedDTO shareDTO = new SharedDTO();
		shareDTO.setFixDomainDTO(new FixDomainDTO());
		shareDTO.setApplication(application);
		shareDTO.setCodeFactory(this.getCodeFactory(language, null));;
		shareDTO.setFullDomainTable(new HashMap<String, FullDomainDTO>());
		baseOmnibusDTO.setSharedDTO(shareDTO);
		return baseOmnibusDTO;
	}

	@Override
	public List<TransactionModel> getTransactionModel(List<?> inputList) throws WorkflowException {
		application = this.getApplicationFromXML(inputList.get(0).toString());
		language=inputList.get(1).toString();
		List<TransactionModel> listTransaction = new ArrayList<TransactionModel>();
		List<Service> services = application.getServices();
		for (Service service : services) {
			List<Entity> entities = service.getEntities();
			for (Entity entity : entities) {
				TransactionModel model = new TransactionModel();
				model.setEntity(entity);
				model.setCodeFactory(this.getCodeFactory(language, entity));
				listTransaction.add(model);
			}
		}
		return listTransaction;
	}

	private CodeFactory getCodeFactory(String language, Entity entity) {
		CodeFactory codeFactory = new CodeFactory();
		PackageInterface packageBuilder = null;
		FixClassInterface fixClassBuilder=null;

		List<ImportLibInterface> importLibBuilderList = new ArrayList<ImportLibInterface>();
		List<ClassNameInterface> classNameBuilderList = new ArrayList<ClassNameInterface>();
		List<MethodBuilderInterface> methodBuilderList = new ArrayList<MethodBuilderInterface>();

		if (language.equals(Const.JAVA)) {
			packageBuilder = new JavaPackageBuilder();
			fixClassBuilder=new JavaFixClassBuilder();
			setListBuildersForJava(entity, importLibBuilderList, classNameBuilderList, methodBuilderList);
		}
		codeFactory.setFixClassBuilder(fixClassBuilder);
		codeFactory.setPackageBuilder(packageBuilder);
		codeFactory.setImportLibBuilderList(importLibBuilderList);
		codeFactory.setClassNameBuilderList(classNameBuilderList);
		codeFactory.setMethodBuilderList(methodBuilderList);
		return codeFactory;

	}

	private void setListBuildersForJava(Entity entity, List<ImportLibInterface> importLibBuilderList,
			List<ClassNameInterface> classNameBuilderList, List<MethodBuilderInterface> methodBuilderList) {
		if (entity == null) {
			importLibBuilderList.add(new ImportLibBuilderBase());
			classNameBuilderList.add(new ClassNameBuilderBase());
			methodBuilderList.add(new JavaMethodBaseBuilder());
		} else {
			String type = "";
			if (entity.getType() != null) {
				type = entity.getType();
			}
			switch (type) {
			case Const.ENITY_TYPE_LOOKUP:
				 importLibBuilderList.add(new ImportLibForLookup());
				 methodBuilderList.add(new LookupMethodBuilder());
				break;
			case Const.USER:
				 importLibBuilderList.add(new ImportLibForUser());
				 methodBuilderList.add(new UserMethodBuilder());
				break;
			default:
				importLibBuilderList.add(new ImportLibBuilderBase());
				methodBuilderList.add(new JavaMethodBaseBuilder());
				break;
			}
			if (entity.hasPaging() != null) {
				 importLibBuilderList.add(new ImportLibForPaging());
				 classNameBuilderList.add(new ClassNamePaging());
				 methodBuilderList.add(new PagingMethodBuilder());
			} else {
				classNameBuilderList.add(new ClassNameBuilderBase());
			}
		}
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
