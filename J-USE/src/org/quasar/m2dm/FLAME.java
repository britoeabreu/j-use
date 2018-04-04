package org.quasar.m2dm;

//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;

//import org.quasar.juse.api.JUSE_BasicFacade;
import org.quasar.juse.api.JUSE_ProgramingFacade;
//import org.quasar.juse.api.implementation.BasicFacade;
import org.quasar.juse.api.implementation.ProgramingFacade;
import org.tzi.use.uml.sys.MObject;

//import org.tzi.use.uml.ocl.value.EnumValue;
//import org.tzi.use.uml.mm.Annotatable;
//import org.tzi.use.uml.mm.MClass;
//import org.tzi.use.uml.mm.MClassInvariant;
//import org.tzi.use.uml.mm.MElementAnnotation;
//import org.tzi.use.uml.mm.MPrePostCondition;
//import org.tzi.use.uml.ocl.value.StringValue;
//import org.tzi.use.uml.sys.MLink;
//import org.tzi.use.uml.sys.MLinkObject;
//import org.tzi.use.uml.sys.MObject;

/***********************************************************
 * @author fba 24 de Mar de 2012
 * 
 ***********************************************************/
public final class FLAME
{
	private final static String	USE_BASE_DIRECTORY	= "D:/Google Drive/EclipseWorkspace/use-5.0.0";
	
	private final static String	WORKING_DIRECTORY	= "metamodels\\UML1.3";
	
//	 private final static String WORKING_DIRECTORY = "D:\\Google Drive\\TOPICS\\_ModelDrivenEngineering\\OCL&METRICS\\MOOD_FLAME";
	
	private final static String METAMODEL_FILE = "UML13_v14.use";

	private final static String SOIL_FILE = "Navio2013.cmd";

	private static JUSE_ProgramingFacade api;

	/***********************************************************
	 * @param args
	 * @throws InterruptedException
	 ***********************************************************/
	public static void main(String[] args) throws InterruptedException
	{
		 loadFLAME(args);		
	}


	/***********************************************************
	* 
	***********************************************************/
	static void loadFLAME(String[] args)
	{
		api = new ProgramingFacade();

		api.initialize(args, USE_BASE_DIRECTORY, WORKING_DIRECTORY);

		api.compileSpecification(METAMODEL_FILE, true);

//		api.command("check");

		api.readSOIL(WORKING_DIRECTORY, SOIL_FILE, false);
		
//		api.command("info vars");

//		 api.command("info state");

		// api.createShell();
		 
	//	 System.out.println(api.oclEvaluator("MMClass.allInstances"));
		 
		 System.out.println(api.oclEvaluator("MMClass.allInstances"));
		 
		 for (MObject instance: api.allInstances("MMClass"))
			 outputMetricsLine(instance, "CHIN()", "DESN()", "PARN()", "ASCN()", 
					 "NAN()", "DAN()", "IAN()", "OAN()", "AAN()", "NON()", "DON()",	"ION()", "OON()", "AON()");	
		 
		 System.out.println("________________________________________________________________");

		 System.out.println(api.oclEvaluator("Package.allInstances"));
		 
		 for (MObject instance: api.allInstances("Package"))
			 outputMetricsLine(instance, "AIF()", "OIF()", "IIF()", "AHF()", "OHF()", "AHEF()", "OHEF()");
	}
	
	
	
	static void outputMetricsLine(MObject target, String... metrics)
	{
		System.out.print(target.toString() + "\t");
		for (String metric: metrics)
			System.out.print(api.oclEvaluator(target.toString() + "." + metric) + "\t");
		System.out.println();
	}
	
	
	
	
}
