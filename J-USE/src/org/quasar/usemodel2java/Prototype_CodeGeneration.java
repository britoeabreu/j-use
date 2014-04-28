/***********************************************************
 * Filename: MainExample.java
 * Created:  24 de Mar de 2012
 ***********************************************************/
package org.quasar.usemodel2java;

import org.quasar.juse.api.JUSE_PrototypeGeneratorFacade;
import org.quasar.juse.api.implementation.PrototypeGeneratorFacade;

/***********************************************************
 * @author fba 24 de Mar de 2012
 * 
 ***********************************************************/
public final class Prototype_CodeGeneration
{
	private final static String	USE_BASE_DIRECTORY	= "C:/Program Files (x86)/use-3.0.7";
	
	private final static String	JAVA_WORKSPACE		= "D:/Dropbox/TEACH/Java/workspace";
	private final static String	BUSINESSLAYER_NAME	= "businessLayer";
	private final static String	PRESENTATIONLAYER_NAME	= "presentationLayer";
	private final static String	PERSISTENCELAYER_NAME	= "persistenceLayer";

	private final static String LIBRARY_DIRECTORY = "lib";
	private final static String	DB4O_JAR	= "db4o-8.0.249.16098-core-java5.jar";		

//	private final static String	MODEL_DIRECTORY		= "D:/Dropbox/TEACH/UML/Exemplos/UK_TO_RoyalLoyal_RXUC/USE";
//	private final static String	MODEL_FILE			= "RoyalAndLoyal.use";
//	private final static String	TARGET_PACKAGE = "org.quasar.royalLoyal";	
	
//	private final static String	MODEL_DIRECTORY		= "D:/Dropbox/TEACH/UML/Exemplos/PT_RUTIEO_CompanhiaAerea_O";
//	private final static String	MODEL_FILE			= "AirNova_RegrasNegocio.use";
//	private final static String	TARGET_PACKAGE = "org.quasar.airNova";	
	
	
	private final static String	MODEL_DIRECTORY = "D:/Dropbox/TEACH/UML/Exemplos/PT_RUTISEO_Futebol_O/CopaPaises_microtest";
	private final static String	MODEL_FILE = "CopaPaises.use";
	private final static String	TARGET_PACKAGE = "org.quasar.copaPaises";
	
//	private final static String	MODEL_DIRECTORY		= "D:/TEACH/UML/Exemplos/UK_TO_RoyalLoyal_RXUC/USE";
//	private final static String	MODEL_FILE			= "Royal_Loyal.use";
//	private final static String	TARGET_PACKAGE = "org.quasar.royalLoyal";
//	
//	private final static String	MODEL_DIRECTORY		= "D:/TEACH/UML/Exemplos/UK_TO_RoyalLoyal_RXUC/USE";
//	private final static String	MODEL_FILE = "RoyalAndLoyal_model.use";
//	private final static String	TARGET_PACKAGE = "org.quasar.royal";
	
//	private final static String	MODEL_DIRECTORY		= "C:/Program Files (x86)/use-3.0.2/examples/soil/projectworld";
//	private final static String	MODEL_FILE = "ProjectWorld_old.use";
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

		api.javaGeneration("Fernando Brito e Abreu", JAVA_WORKSPACE, TARGET_PACKAGE, 	BUSINESSLAYER_NAME, 
						PRESENTATIONLAYER_NAME, PERSISTENCELAYER_NAME, LIBRARY_DIRECTORY, DB4O_JAR);
	}	
}