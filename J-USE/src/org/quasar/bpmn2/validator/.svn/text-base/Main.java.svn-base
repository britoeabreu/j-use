/***********************************************************
 * Filename: MainExample.java
 * Created:  24 de Mar de 2012
 ***********************************************************/
package org.quasar.bpmn2.validator;

import org.quasar.juse.api.JUSE_PrototypeGeneratorFacade;
import org.quasar.juse.api.implementation.PrototypeGeneratorFacade;

/***********************************************************
 * @author fba 24 de Mar de 2012
 * 
 ***********************************************************/
public final class Main
{
	private final static String	USE_BASE_DIRECTORY	= "C:/Program Files (x86)/use-3.0.3";
	private final static String	JAVA_WORKSPACE		= "D:/TEACH/Java/workspace";
	private final static String	BUSINESSLAYER_NAME	= "businessLayer";
	private final static String	PRESENTATIONLAYER_NAME	= "presentationLayer";
	private final static String	PERSISTENCELAYER_NAME	= "persistenceLayer";
	
	private final static String LIBRARY_DIRECTORY = "lib";
	private final static String	DB4O_JAR	= "db4o-8.0.249.16098-core-java5.jar";		
	
	private final static String	MODEL_DIRECTORY = "D:/TEACH/UML/Exemplos/PT_RUTISEO_Futebol_O";
	private final static String	MODEL_FILE = "CopaPaises_20120416.use";
	private final static String	SOIL_FILE = "euro2012.soil";
	private final static String	CMD_FILE = "euro2012_data.cmd";
	private final static String	TARGET_PACKAGE = "org.quasar.copaPaises";
//	
//	private final static String	MODEL_DIRECTORY		= "D:/TEACH/UML/Exemplos/UK_TO_RoyalLoyal_RXUC/USE";
//	private final static String	MODEL_FILE = "RoyalAndLoyal_model.use";
//	private final static String	SOIL_FILE = "RoyalAndLoyal_objects.cmd";
//	private final static String	CMD_FILE = "RoyalAndLoyal_data.cmd";
//	private final static String	TARGET_PACKAGE = "org.quasar.royal";
	
//	private final static String	MODEL_DIRECTORY		= "C:/Program Files (x86)/use-3.0.2/examples/soil/projectworld";
//	private final static String	MODEL_FILE = "ProjectWorld_old.use";
//	private final static String	SOIL_FILE = "contractsInAction.soil";
//	private final static String	CMD_FILE = "ProjectWorld_data.cmd";
//	private final static String	TARGET_PACKAGE = "org.iscte_iul.projects";
	
	
	/***********************************************************
	 * @param args
	 * @throws InterruptedException
	 ***********************************************************/
	public static void main(String[] args) throws InterruptedException
	{	 
		JUSE_PrototypeGeneratorFacade api = new PrototypeGeneratorFacade();
		
		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE, true);
		
		api.readSOIL(MODEL_DIRECTORY, SOIL_FILE, true);

		api.command("info state");

		api.javaGeneration("Fernando Brito e Abreu", JAVA_WORKSPACE, TARGET_PACKAGE, 	BUSINESSLAYER_NAME, 
						PRESENTATIONLAYER_NAME, PERSISTENCELAYER_NAME, LIBRARY_DIRECTORY, DB4O_JAR);
		
		 api.dumpState("Fernando Brito e Abreu", JAVA_WORKSPACE, CMD_FILE, false);
	}
}