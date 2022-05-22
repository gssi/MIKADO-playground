package org.eclipse.epsilon.live.mikado;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;

import org.eclipse.epsilon.egl.IEglModule;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.flexmi.actions.InMemoryFlexmiModel;

import com.google.common.io.Resources;
import com.google.gson.JsonObject;

public class RunEvaluation extends EpsilonLiveFunction{

	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		// TODO Auto-generated method stub
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
			run( request.get("kpiFlexmi").getAsString(), 
			request.get("scFlexmi").getAsString(),
			bos, response);
			
			response.addProperty("output", bos.toString());
	
		
		
	}
	
public void run( String kpiFlexmi,  String scFlexmi, OutputStream outputStream, JsonObject response) throws Exception {
		
		IEolModule module = new EtlModule();
		
		module.parse(new File("src/main/resources/kpi2eval.etl"));
		if (!module.getParseProblems().isEmpty()) {
			response.addProperty("error", module.getParseProblems().get(0).toString());
			return;
		}
	
		
		String kpiEmfatic =  Files.readString(Paths.get(new File("src/main/resources/kpi.emf").toURI()));

				
		String scEmfatic =  Files.readString(Paths.get(new File("src/main/resources/smart_city.emf").toURI()));

		
		module.getContext().setOutputStream(new PrintStream(outputStream));
		
		 runEtl((EtlModule) module, kpiFlexmi, kpiEmfatic, scFlexmi, scEmfatic, response); return;
			
		
	}
	
	protected void runEtl(EtlModule module, String kpiflexmi, String kpiEmfatic, String scFlexmi, String scEmfatic, JsonObject response) throws Exception {
		
		InMemoryEmfModel sourceModel = getInMemoryFlexmiModel(kpiflexmi, kpiEmfatic);
		
		sourceModel.setName("Source");
		
		scFlexmi = "?nsuri: http://cs.gssi.it/smartcity\n"+scFlexmi;
		kpiflexmi = "?nsuri: http://cs.gssi.it/kpi \n"+kpiflexmi;
		
		InMemoryEmfModel scModel = getInMemoryFlexmiModel(scFlexmi, scEmfatic);
		scModel.setName("sc");
		
		
		InMemoryEmfModel targetModel = getBlankInMemoryModel(kpiEmfatic);
		targetModel.setName("Target");
		
		module.getContext().getModelRepository().addModel(sourceModel);
		module.getContext().getModelRepository().addModel(scModel);
		module.getContext().getModelRepository().addModel(targetModel);
		
		module.execute();
		
		//to save the evaluated model on the server
		targetModel.store();
		String result = new Flexmi2HTML().run(targetModel);
		
		response.addProperty("evalresult", result);
		
	}
	
public static void main(String[] args) throws Exception {
		
		//new RunEvaluation().getEPackage("package foo;");
	
			String kpiFlexmi = Files.readString(Paths.get(new File("src/main/resources/mykpi.flexmi").toURI()));
			String scFlexmi = Files.readString(Paths.get(new File("src/main/resources/aq.flexmi").toURI()));
		
		new RunEvaluation().run(kpiFlexmi, scFlexmi,
				
				System.out, new JsonObject());
	}

}
