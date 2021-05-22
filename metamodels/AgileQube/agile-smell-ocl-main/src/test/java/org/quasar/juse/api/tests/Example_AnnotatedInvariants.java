/***********************************************************
 * Filename: MainExample.java
 * Created:  24 de Mar de 2012
 ***********************************************************/
package org.quasar.juse.api.tests;

import org.quasar.juse.api.JUSE_BasicFacade;
import org.quasar.juse.api.JUSE_ProgramingFacade;
import org.quasar.juse.api.implementation.BasicFacade;
import org.quasar.juse.api.implementation.ProgramingFacade;
import org.tzi.use.uml.mm.MClassInvariant;

/***********************************************************
 * @author fba 24 de Mar de 2012
 * 
 ***********************************************************/
public final class Example_AnnotatedInvariants
{
	private final static String	USE_BASE_DIRECTORY	= "D:\\Google Drive\\EclipseWorkspace\\use-5.0.1";

	private final static String	MODEL_DIRECTORY		= "D:/Dropbox/Anacleto/Metamodels/BPMN2";
	private final static String	MODEL_FILE			= "BPMN2.0n.use";
	private final static String	SOIL_FILE			= "BPMN2.0k.cmd";
	
	

	/***********************************************************
	 * @param args
	 * @throws InterruptedException
	 ***********************************************************/
	public static void main(String[] args) throws InterruptedException
	{
		// testBasicFacade(args);

		testProgramingFacade_BPMN2(args);

	}

	/***********************************************************
	* 
	***********************************************************/
	static void testBasicFacade(String[] args)
	{
		JUSE_BasicFacade api = new BasicFacade();

		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE, true);

		api.command("check");

		api.createShell();
	}

	/***********************************************************
	* 
	***********************************************************/
	static void testProgramingFacade_BPMN2(String[] args)
	{
		JUSE_ProgramingFacade api = new ProgramingFacade();

		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE, true);

		System.out.println("\t# classes = " + api.allClasses().size() + "\t\t# associations = " + api.allAssociations().size() + "\n");

		api.readSOIL(MODEL_DIRECTORY, SOIL_FILE, true);
		
		System.out.println("\t# objects = " + api.allObjects().size() + "\t\t# links = " + api.allLinks().size() + "\n");
		
		System.out.println("\nINVARIANTS:");
		for (MClassInvariant anInvariant : api.allInvariants())
		{
			if (!api.check(anInvariant))
			{
				System.out.print("\tchecking invariant '" + anInvariant.cls().name() + "::" + anInvariant.name() + "' : ");
				if (anInvariant.getAllAnnotations().keySet().isEmpty())
					System.out.println("FAIL [Message missing ...]");
				else
					for (String key : anInvariant.getAllAnnotations().keySet())
					{
						switch (anInvariant.getAnnotationValue(key, "type").charAt(0))
						{
							case 'S':
								System.out.print("[BPM2 specification non-conformance]");
								break;
							case 'W':
								System.out.print("[Warning Message]");
								break;
							case 'I':
								System.out.print("[Information Message]");
								break;
							case 'P':
								System.out.print("[Plain Message]");
								break;
							default:
								System.out.print("UNKNOWN MESSAGE TYPE!!!");
						}
						System.out.print("> " + anInvariant.getAnnotationValue(key, "msg"));
						System.out.println(" (" + anInvariant.getAnnotationValue(key, "origin") + ")");
					}
			}
		}

		// System.out.println("\nPRE-CONDITIONS:");
		// for (MPrePostCondition aPre : api.allPreConditions())
		// {
		// for (String key : aPre.getAllAnnotations().keySet())
		// System.out.println("\t@" + key + "(type=\"" + aPre.getAnnotationValue(key, "type") + "\", msg=\""
		// + aPre.getAnnotationValue(key, "msg") + "\")");
		// }
		//
		// System.out.println("\nPOST-CONDITIONS:");
		// for (MPrePostCondition aPost : api.allPostConditions())
		// {
		// for (String key : aPost.getAllAnnotations().keySet())
		// System.out.println("\t@" + key + "(type=\"" + aPost.getAnnotationValue(key, "type") + "\", msg=\""
		// + aPost.getAnnotationValue(key, "msg") + "\")");
		// }

		// api.command("check");

		// api.command("info vars");

		// api.command("info state");

		// api.createShell();
	}

}