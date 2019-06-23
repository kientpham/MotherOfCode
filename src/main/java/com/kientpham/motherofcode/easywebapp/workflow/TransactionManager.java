package com.kientpham.motherofcode.easywebapp.workflow;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.kientpham.motherofcode.easywebapp.model.Application;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.OmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.Service;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseTransactionManager;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeFacade;

public class TransactionManager implements BaseTransactionManager<TransactionModel>{

	@Override
	public void saveTransaction(TransactionModel transaction) {	
	}

	@Override
	public void updateTransactionWhenException(TransactionModel transaction, WorkflowException e) {		
	}
	
	@Override
	public List<TransactionModel> getTransactionModel(List<?> inputList) throws WorkflowException {		
		Application app=this.getApplicationFromXML(inputList.get(0).toString());
		CodeFacade codeFacade=new CodeFacade(inputList.get(1).toString());
		
		List<TransactionModel> listTransaction=new ArrayList<TransactionModel>();		
		List<Service> services = app.getServices();
		for (Service service : services) {
			List<Entity> entities = service.getEntities();			
			for (Entity entity : entities) {
				TransactionModel model=new TransactionModel();
				model.setApplication(app);
				model.setService(service);
				model.setEntity(entity);	
				model.setCodeFacade(codeFacade);
				model.setOmnibusDto(new OmnibusDTO());
				listTransaction.add(model);
			}
		}
		
//		transactionModel.setCodeFacade(codeFacade);
//		Application app = transactionModel.getApplication();
//		transaction.setXmlModelFile(inputList.get(0).toString());
//		transaction.setLanguage(inputList.get(1).toString());
//		transaction.setOmnibusDto(new OmnibusDTO());
		
		return listTransaction;
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
