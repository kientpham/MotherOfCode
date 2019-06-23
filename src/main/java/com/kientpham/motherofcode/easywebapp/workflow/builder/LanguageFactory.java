package com.kientpham.motherofcode.easywebapp.workflow.builder;

import java.util.ArrayList;
import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.Application;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.Service;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeFacade;


public class LanguageFactory implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transactionModel) throws WorkflowException {
		CodeFacade codeFacade=new CodeFacade(transactionModel.getLanguage());
		transactionModel.setCodeFacade(codeFacade);
		Application app = transactionModel.getApplication();
		List<Service> services = app.getServices();
		for (Service service : services) {
			List<Entity> entities = service.getEntities();
			List<String> listDomain = new ArrayList<String>();
			listDomain.add(app.getDomain());
			listDomain.add(service.getName());
			listDomain.add(service.getEntityDomain());
			for (Entity entity : entities) {
		
			}
		}
	}

}
