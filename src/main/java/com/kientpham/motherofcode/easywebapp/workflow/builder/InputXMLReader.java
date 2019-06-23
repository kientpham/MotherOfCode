package com.kientpham.motherofcode.easywebapp.workflow.builder;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.kientpham.motherofcode.easywebapp.model.Application;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;

public class InputXMLReader implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transactionModel) throws WorkflowException {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(transactionModel.getXmlModelFile());// "CodeModel.xml");
		File file = new File(resource.getFile());

		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Application.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Application application = (Application) jaxbUnmarshaller.unmarshal(file);
			transactionModel.setApplication(application);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
