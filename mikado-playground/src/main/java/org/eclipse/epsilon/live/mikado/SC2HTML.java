package org.eclipse.epsilon.live.mikado;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.execute.context.Variable;

import com.google.gson.JsonObject;


public class SC2HTML extends EpsilonLiveFunction {

	public static void main(String[] args) throws Exception {
		String scFlexmi = Files.readString(Paths.get(new File("src/main/resources/aq.flexmi").toURI()));
		System.out.println(
				new SC2HTML().
				run(scFlexmi));
	}
	
	@Override
	public void serviceImpl(JsonObject request, JsonObject response) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		Instant start = Instant.now();
	
		String html = run(request.get("scFlexmi").getAsString());
		
		response.addProperty("scDiagram", html);
		
		
		Instant end = Instant.now();
		String exectime = "Code gen: "+Duration.between(start,end).toMillis()+" ms";
		response.addProperty("output",exectime+"\n"+ bos.toString());
	}
	
	
	protected String run(String flexmi) throws Exception {
		String scEmfatic =  Files.readString(Paths.get(new File("src/main/resources/smart_city.emf").toURI()));
				
		
		return run(getInMemoryFlexmiModel(flexmi, scEmfatic));
	}
	
	protected String run(InMemoryEmfModel model, Variable... variables) throws Exception {
		
		EglTemplateFactoryModuleAdapter module = new EglTemplateFactoryModuleAdapter();
		module.parse(new File("src/main/resources/SC2html.egl"));
		model.setName("M");
	
		module.getContext().getModelRepository().addModel(model);
		module.getContext().getFrameStack().put(variables);
		
		return module.execute() + "";
	}

}
