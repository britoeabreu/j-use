/***********************************************************
 * Filename: MainExample.java
 * Created:  24 de Mar de 2013
 ***********************************************************/
package org.quasar.usemodel2java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.quasar.juse.api.JUSE_ProgramingFacade;
import org.quasar.juse.api.JUSE_PrototypeGeneratorFacade;
import org.quasar.juse.api.implementation.ProgramingFacade;
import org.quasar.juse.api.implementation.PrototypeGeneratorFacade;

/***********************************************************
 * @author fba 24 de Mar de 2013
 * 
 ***********************************************************/
public final class Prototype_DatabaseGeneration
{
	private final static String	USE_BASE_DIRECTORY		= "D:\\Dropbox\\TEACH\\Java\\workspace\\use-4.0.0";

	private final static String	JAVA_WORKSPACE		= "D:/Dropbox/TEACH/Java/workspace";
	private final static String	BUSINESSLAYER_NAME	= "businessLayer";
	private final static String	DATABASE_DIRECTORY	= "database";

	 private final static String TARGET_PACKAGE = "org.quasar.airNova";
	 private final static String MODEL_DIRECTORY = "D:/Dropbox/TEACH/UML/Exemplos/PT_RUTIEO_CompanhiaAerea_O/Data";
	 private final static String MODEL_FILE = "AirNova.use";
	 private final static String SOIL_FILE = "AirNova4.soil";
	 private final static String CMD_FILE = "AirNova4.cmd";

	// private final static String MODEL_DIRECTORY = "D:/Dropbox/_SHARED/PedroCoimbra/JavaModel";
	// private final static String MODEL_FILE = "JavaMMv3_FLAME.use";
	// private final static String SOIL_FILE = "AirNova.soil";
	// private final static String CMD_FILE = "AirNova.cmd";

	// private final static String TARGET_PACKAGE = "org.quasar.royalLoyal";
	// private final static String MODEL_DIRECTORY = "D:/Dropbox/TEACH/UML/Exemplos/UK_TO_RoyalLoyal_RXUC/USE";
	// private final static String MODEL_FILE = "RoyalAndLoyal.use";
	// private final static String SOIL_FILE = "RoyalAndLoyal_correctSnapshot.soil";
	// private final static String CMD_FILE = "RoyalAndLoyal_correctSnapshot.cmd";

//	private final static String	TARGET_PACKAGE		= "org.quasar.phoenix";
//	private final static String	MODEL_DIRECTORY		= "D:/Dropbox/TEACH/UML/Exemplos/PT_RUTIEO_OrganizacaoAcademica_OC/USE";
//	private final static String	MODEL_FILE			= "Phoenix2.use";
//	private final static String	SOIL_FILE			= "PHOENIXdata2.cmd";
//	private final static String	CMD_FILE			= "PHOENIXdata2.cmd";

	// private final static String TARGET_PACKAGE = "org.quasar.copaPaises";
	// private final static String MODEL_DIRECTORY = "D:/Dropbox/TEACH/UML/Exemplos/PT_RUTISEO_Futebol_O/CopaPaises_microtest";
	// private final static String MODEL_FILE = "CopaPaises.use";
	// private final static String SOIL_FILE = "dadosMicrotest3.soil";
	// private final static String CMD_FILE = "dadosMicrotest3.cmd";

	/***********************************************************
	 * @param args
	 * @throws InterruptedException
	 ***********************************************************/
	public static void main(String[] args) throws InterruptedException
	{
		// System.out.println(d.replaceAll("\\\\", "\\\\\\\\"));

		// mainLoadData();

		mainRoundDataTrip();
	}

	/***********************************************************
	 * @param args
	 * @throws InterruptedException
	 ***********************************************************/
	public static void mainLoadData()
	{
		JUSE_ProgramingFacade api = new ProgramingFacade();

		String[] args = new String[0];

		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE, true);

		if (api.readSOIL(MODEL_DIRECTORY + "/", SOIL_FILE, false))
		{
			String oclExpression = null;
			do
			{
				System.out.print("Enter OCL expression: ");

				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				try
				{
					oclExpression = br.readLine();
					System.out.println(api.oclEvaluator(oclExpression));
				}
				catch (IOException ioe)
				{
					System.out.println("IO error trying to read oclExpression!");
					System.exit(1);
				}
			}
			while (!oclExpression.equals("0"));

		}

	}

	/***********************************************************
	 * @param args
	 * @throws InterruptedException
	 ***********************************************************/
	public static void mainRoundDataTrip()
	{
		JUSE_PrototypeGeneratorFacade api = new PrototypeGeneratorFacade();

		String[] args = new String[0];

		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE, true);

		if (api.readSOIL(MODEL_DIRECTORY, SOIL_FILE, false))
		{
			api.command("info state");

			api.storeState(JAVA_WORKSPACE, TARGET_PACKAGE, BUSINESSLAYER_NAME, DATABASE_DIRECTORY);
		}

		// api.dumpState("Fernando Brito e Abreu", MODEL_DIRECTORY, CMD_FILE, false);
		//
		// api.command("reset");
		// api.command("info state");
		//
		// api.readSOIL(MODEL_DIRECTORY, CMD_FILE, false);
		api.command("info state");
	}
}