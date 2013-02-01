/***********************************************************
 * Filename: MainExample.java
 * Created:  24 de Mar de 2012
 ***********************************************************/
package org.quasar.pimeta.loader;

import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;

import org.quasar.juse.api.JUSE_BasicFacade;
import org.quasar.juse.api.JUSE_ProgramingFacade;
import org.quasar.juse.api.implementation.JUSEfacadeImplementation;
//import org.tzi.use.uml.mm.Annotatable;
//import org.tzi.use.uml.mm.MClass;
//import org.tzi.use.uml.mm.MClassInvariant;
//import org.tzi.use.uml.mm.MElementAnnotation;
//import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;

/***********************************************************
 * @author fba 24 de Mar de 2012
 * 
 ***********************************************************/
public final class PIMETA_Loader
{
	private final static String	USE_BASE_DIRECTORY	= "C:/Program Files (x86)/use-3.0.4";

	 private final static String MODEL_DIRECTORY = "D:/Dropbox/TOPICS/_CONSTRUCTION_EVALUATION/ASPECTS/PIMETA";
	 private final static String MODEL_FILE = "pimeta.use";
	 private final static String SOIL_FILE = "cmd/java.cmd";

	

	/***********************************************************
	 * @param args
	 * @throws InterruptedException
	 ***********************************************************/
	public static void main(String[] args) throws InterruptedException
	{
		loadPIMETA(args);
	}


	/***********************************************************
	* 
	***********************************************************/
	static void loadPIMETA(String[] args)
	{
		JUSE_ProgramingFacade api = new JUSEfacadeImplementation();

		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE);

		api.command("check");
		
		api.readSOIL(SOIL_FILE, true);

		api.command("info state");

		api.command("info vars");

	}

}