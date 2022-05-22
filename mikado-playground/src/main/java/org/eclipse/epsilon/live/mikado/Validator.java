package org.eclipse.epsilon.live.mikado;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
		
		
		if(validationresults!=null && validationresults.size()>0) {
			
			response.addProperty("error", ("Validation errors: " + validationresults).toString());
			
		}
		
		
	}
	
public Collection<UnsatisfiedConstraint> run( String kpiFlexmi,  String scFlexmi, OutputStream outputStream, JsonObject response)  {
	Set<UnsatisfiedConstraint> constraints=null;
	try {
		IEolModule module = new EvlModule();
		
		module.parse(new File("src/main/resources/validate.evl"));
		
		if (!module.getParseProblems().isEmpty()) {
			
			System.out.println(module.getParseProblems().get(0).toString());
						
		}

		String kpiEmfatic =  Files.readString(Paths.get(new File("src/main/resources/kpi.emf").toURI()));

				
		String scEmfatic = Files.readString(Paths.get(new File("src/main/resources/smart_city.emf").toURI()));
		
		
		module.getContext().setOutputStream(new PrintStream(outputStream));
		
		//return runEvl((EvlModule) module, kpiFlexmi, kpiEmfatic, scFlexmi, scEmfatic, response); 
		
		InMemoryEmfModel kpimodel = getInMemoryFlexmiModel(kpiFlexmi, kpiEmfatic);
		kpimodel.setName("M");
		
		module.getContext().getModelRepository().addModel(kpimodel);
		
		InMemoryEmfModel scmodel = getInMemoryFlexmiModel(scFlexmi, scEmfatic);
		
		scmodel.setName("sc");
		
		module.getContext().getModelRepository().addModel(scmodel);
		
		 constraints = (Set<UnsatisfiedConstraint>) module.execute();
	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			System.out.println(exceptionAsString);
		}
		
		return  constraints;	
		
	}
	
	
public static void main(String[] args) throws Exception {
		
		//new RunEvaluation().getEPackage("package foo;");
	
			String kpiFlexmi =Files.readString(Paths.get(new File("src/main/resources/mykpi.flexmi").toURI()));
			String scFlexmi = Files.readString(Paths.get(new File("src/main/resources/aq.flexmi").toURI()));
			//System.out.println(scFlexmi);
		System.out.println(new Validator().run(kpiFlexmi, scFlexmi,
				
				System.out, new JsonObject()));
	}

}
