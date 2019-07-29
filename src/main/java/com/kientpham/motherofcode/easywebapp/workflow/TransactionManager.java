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
import com.kientpham.motherofcode.easywebapp.model.FullDomainDTO;
import com.kientpham.motherofcode.easywebapp.model.Service;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseTransactionManager;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeBuilder;

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
		CodeBuilder codeFacade=new CodeBuilder(inputList.get(1).toString());
		
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
				FullDomainDTO domainDTO=new FullDomainDTO();
				domainDTO.setPagingInput("com.kienp.webapp.common.dto.paging.PagingInputDTO");
				domainDTO.setPagingOutput("com.kienp.webapp.common.dto.paging.PagingOutputDTO");
				model.setFullDomainDTO(domainDTO);
				listTransaction.add(model);
			}
		}		
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
