/***********************************************************
 * Filename: MainExample.java
 * Created:  24 de Mar de 2012
 ***********************************************************/
package org.quasar.juse.api;

import java.util.Arrays;

import org.quasar.juse.api.implementation.JUSEfacadeImplementation;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;

/***********************************************************
 * @author fba 24 de Mar de 2012
 * 
 ***********************************************************/
public final class MainExample_CodeGeneration
{
	private final static String	USE_BASE_DIRECTORY	= "C:/Program Files (x86)/use-3.0.3";
	private final static String	JAVA_WORKSPACE		= "D:/TEACH/Java/workspace";
	private final static String	BUSINESSLAYER_NAME	= "businessLayer";
	private final static String	PRESENTATIONLAYER_NAME	= "presentationLayer";
	
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
		JUSE_CodeGeneratorFacade api = new JUSEfacadeImplementation();
		
		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE);
		
		api.readSOIL(SOIL_FILE, true);

		api.command("info state");

		api.javaGeneration("Fernando Brito e Abreu", JAVA_WORKSPACE, TARGET_PACKAGE, BUSINESSLAYER_NAME, PRESENTATIONLAYER_NAME);
		
		 api.dumpState("Fernando Brito e Abreu", JAVA_WORKSPACE, CMD_FILE, false);
	}
}