/***********************************************************
 * Filename: MainExample.java
 * Created:  24 de Mar de 2012
 ***********************************************************/
package org.quasar.juse.api;

import java.util.Arrays;
import java.util.Map;

import org.quasar.juse.api.implementation.JUSEfacadeImplementation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MElementAnnotation;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;

/***********************************************************
 * @author fba 24 de Mar de 2012
 * 
 ***********************************************************/
public final class MainExample_Programming
{
	private final static String	USE_BASE_DIRECTORY	= "C:/Program Files (x86)/use-3.0.3";

//	 private final static String MODEL_DIRECTORY = "D:/TEACH/UML/Exemplos/PT_RUTISEO_Futebol_O";
//	 private final static String MODEL_FILE = "CopaPaises_20120416.use";
//	 private final static String SOIL_FILE = "euro2012.soil";

	private final static String	MODEL_DIRECTORY		= "D:/Dropbox/Anacleto/Metamodels/BPMN2";
	private final static String	MODEL_FILE			= "BPMN2.0m.use";
	private final static String	SOIL_FILE			= "????????";

	/***********************************************************
	 * @param args
	 * @throws InterruptedException
	 ***********************************************************/
	public static void main(String[] args) throws InterruptedException
	{
//		testBasicFacade(args);

		testProgramingFacade_BPMN2(args);

//		 testProgramingFacade_CopaPaises(args);
	}

	/***********************************************************
	* 
	***********************************************************/
	static void testBasicFacade(String[] args)
	{
		JUSE_BasicFacade api = new JUSEfacadeImplementation();

		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE);

		api.command("check");

		api.createShell();
	}

	/***********************************************************
	* 
	***********************************************************/
	static void testProgramingFacade_BPMN2(String[] args)
	{
		JUSE_ProgramingFacade api = new JUSEfacadeImplementation();

		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE);

		System.out.println("Number of classes -> " + api.allClasses().size());

		// Map<String, MElementAnnotation> m;
		for (MClass aClass : api.allClasses())
			if (aClass.isAnnotated())
			{
				System.out.println(aClass.name() + " IS ANNOTATED!");
				for (Map.Entry<String, MElementAnnotation> e : api.allAnnotations(aClass).entrySet())
					System.out.println("ANNOTATION> " + e.getKey() + ": " + e.getValue());
			}
		
		//api.command("check");

		api.command("info vars");

		// api.command("info state");
		
		api.createShell();
	}

	/***********************************************************
	* 
	***********************************************************/
	static void testProgramingFacade_CopaPaises(String[] args)
	{
		JUSE_ProgramingFacade api = new JUSEfacadeImplementation();

		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE);

		api.command("check");

		api.command("info vars");

		MObject ronaldo = api.createObject("ronaldo", api.classByName("Jogador"));
		MObject messi = api.createObject("messi", api.classByName("Jogador"));

		MObject euro2012 = api.createObject("euro2012", api.classByName("Campeonato"));
		MObject spain = api.createObject("spain", api.classByName("Pais"));
		MObject real = api.createObject("real", api.classByName("Clube"));
		MObject barca = api.createObject("barca", api.classByName("Clube"));
		MLinkObject euro2012_spain = api.createLinkObject("euro2012_spain", api.associationClassByName("Participacao"),
						Arrays.asList(spain, euro2012));

		MLink spain_real = api.createLink(api.associationByName("Pais_Clube"), Arrays.asList(spain, real));
		MLink spain_barca = api.createLink(api.associationByName("Pais_Clube"), Arrays.asList(spain, barca));

		MLink ronaldo_real = api.createLink(api.associationByName("Clube_Jogador"), Arrays.asList(real, ronaldo));
		MLink messi_barca = api.createLink(api.associationByName("Clube_Jogador"), Arrays.asList(barca, messi));

		api.command("info state");

		api.setObjectAttribute(ronaldo, api.attributeByName(ronaldo, "nome"), new StringValue("Cristiano Ronaldo"));

		System.out.println(api.getObjectAttribute(ronaldo, api.attributeByName(ronaldo, "nome")));

		for (MObject clube : api.allInstances(api.classByName("Clube")))
			System.out.println("CLUBE > " + clube);

		for (MObject jogador : api.allInstances(api.classByName("Jogador")))
			System.out.println("JOGADOR > " + jogador);

//		api.createShell();
	}

}