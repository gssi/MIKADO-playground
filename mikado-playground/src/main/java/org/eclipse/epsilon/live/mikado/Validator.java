package org.eclipse.epsilon.live.mikado;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;

import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;

import com.google.gson.JsonObject;

public class Validator  extends EpsilonLiveFunction{

	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		// TODO Auto-generated method stub
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		Collection<UnsatisfiedConstraint> validationresults = run(request.get("kpiFlexmi").getAsString(), 
				request.get("scFlexmi").getAsString(),
				bos, response);

		response.addProperty("output", bos.toString());
		
		if(validationresults.size()>0) {
			
			response.addProperty("error", ("Validation errors: " + validationresults).toString());
			
		}
		
		
	}
	
public Collection<UnsatisfiedConstraint> run( String kpiFlexmi,  String scFlexmi, OutputStream outputStream, JsonObject response) throws Exception {
		
		IEolModule module = new EvlModule();
		
		module.parse(new File("src/main/resources/validate.evl"));
	
		if (!module.getParseProblems().isEmpty()) {
			
			System.out.println(module.getParseProblems().get(0).toString());
			//response.addProperty("error", module.getParseProblems().get(0).toString());
			
		}

		String kpiEmfatic =  Files.readString(Paths.get(getClass().getResource("kpi.emf").toURI()));

				
		String scEmfatic =  Files.readString(Paths.get(getClass().getResource("smart_city.emf").toURI()));

		
		module.getContext().setOutputStream(new PrintStream(outputStream));
		
		return runEvl((EvlModule) module, kpiFlexmi, kpiEmfatic, scFlexmi, scEmfatic, response); 
			
		
	}
	
	protected Collection<UnsatisfiedConstraint> runEvl( EvlModule module, String kpiflexmi, String kpiEmfatic, String scFlexmi, String scEmfatic, JsonObject response)  {
		
		Set<UnsatisfiedConstraint> constraints = null;
		System.out.println("qui");
		try {
		module.parse(new File("src/main/resources/validate.evl"));
		
		if (!module.getParseProblems().isEmpty()) {
			
			//response.addProperty("error", module.getParseProblems().get(0).toString());
			System.out.println(module.getParseProblems().get(0).toString());
			
		}
		
		InMemoryEmfModel kpimodel = getInMemoryFlexmiModel(kpiflexmi, kpiEmfatic);
		kpimodel.setName("M");
		
		module.getContext().getModelRepository().addModel(kpimodel);
		
		InMemoryEmfModel scmodel = getInMemoryFlexmiModel(scFlexmi, scEmfatic);
		
		scmodel.setName("sc");
		
		module.getContext().getModelRepository().addModel(scmodel);
		
		constraints = module.execute();
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  constraints;
		
	}
	
public static void main(String[] args) throws Exception {
		
		//new RunEvaluation().getEPackage("package foo;");
	
			String kpiFlexmi = Files.readString(Paths.get(RunEvaluation.class.getResource("mykpi.flexmi").toURI()));
			String scFlexmi = Files.readString(Paths.get(RunEvaluation.class.getResource("aq.flexmi").toURI()));
			//System.out.println(scFlexmi);
		System.out.println(new Validator().run(kpiFlexmi, scFlexmi,
				
				System.out, new JsonObject()));
	}

}
