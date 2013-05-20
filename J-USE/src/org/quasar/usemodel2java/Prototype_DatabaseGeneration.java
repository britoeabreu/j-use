/***********************************************************
 * Filename: MainExample.java
 * Created:  24 de Mar de 2013
 ***********************************************************/
package org.quasar.usemodel2java;

import org.quasar.juse.api.JUSE_PrototypeGeneratorFacade;
import org.quasar.juse.api.implementation.PrototypeGeneratorFacade;


/***********************************************************
 * @author fba 24 de Mar de 2013
 * 
 ***********************************************************/
public final class Prototype_DatabaseGeneration
{
	private final static String	USE_BASE_DIRECTORY	= "C:/Program Files (x86)/use-3.0.6";
	private final static String	DB4O_JAR	= "db4o-8.0.249.16098-core-java5.jar";		

	private final static String	JAVA_WORKSPACE		= "D:/Dropbox/TEACH/Java/workspace";
	private final static String	BUSINESSLAYER_NAME	= "businessLayer";
	private final static String DATABASE_DIRECTORY = "database";
	private final static String LIBRARY_DIRECTORY = "lib";
	
//	private final static String	TARGET_PACKAGE = "org.quasar.airNova";
//	private final static String	MODEL_DIRECTORY		= "D:/Dropbox/TEACH/UML/Exemplos/PT_RUTIEO_CompanhiaAerea_O";
//	private final static String	MODEL_FILE			= "AirNova.use";
//	private final static String	SOIL_FILE = "AirNovaMicroteste3.soil";
//	private final static String	CMD_FILE = "AirNovaMicroteste3_data.cmd";	
	
	private final static String	TARGET_PACKAGE = "org.quasar.copaPaises";
	private final static String	MODEL_DIRECTORY = "D:/Dropbox/TEACH/UML/Exemplos/PT_RUTISEO_Futebol_O";
	private final static String	MODEL_FILE = "CopaPaises_20120416.use";
	private final static String	SOIL_FILE = "dados_euro2012.soil";
	private final static String	CMD_FILE = "euro2012_data.cmd";	
	
	/***********************************************************
	 * @param args
	 * @throws InterruptedException
	 ***********************************************************/
	public static void main(String[] args) throws InterruptedException
	{	 
		JUSE_PrototypeGeneratorFacade api = new PrototypeGeneratorFacade();
		
		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE);
		
		api.readSOIL(SOIL_FILE, true);
		
		api.command("info state");
		
		// api.dumpState("Fernando Brito e Abreu", JAVA_WORKSPACE, CMD_FILE, false);

		api.storeState(JAVA_WORKSPACE, TARGET_PACKAGE, BUSINESSLAYER_NAME, DATABASE_DIRECTORY, LIBRARY_DIRECTORY, DB4O_JAR);		
	}	
}