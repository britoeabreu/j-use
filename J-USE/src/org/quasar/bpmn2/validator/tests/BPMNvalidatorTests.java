/***********************************************************
 * Filename: BPMNvalidatorTests.java
 * Created:  28 de Jun de 2012
 ***********************************************************/
package org.quasar.bpmn2.validator.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quasar.juse.api.JUSE_ProgramingFacade;
import org.quasar.juse.api.implementation.JUSEfacadeImplementation;
import org.tzi.use.uml.mm.MClassInvariant;

/***********************************************************
 * @author fba 28 de Jun de 2012
 * 
 ***********************************************************/
public class BPMNvalidatorTests
{
	private final static String				USE_BASE_DIRECTORY	= "C:/Program Files (x86)/use-3.0.4";

	private final static String				MODEL_DIRECTORY		= "D:/Dropbox/Anacleto/Metamodels/BPMN2";
	private final static String				MODEL_FILE			= "BPMN2.0n.use";
	private final static String				SOIL_DIRECTORY		= "TestSuiteModels/";

	private static JUSE_ProgramingFacade	api					= new JUSEfacadeImplementation();

	/***********************************************************
	 * @throws java.lang.Exception
	 ***********************************************************/
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		String[] args = { "-oclAnyCollectionsChecks:I" };

		api.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		api.compileSpecification(MODEL_FILE);

		System.out.println("\t# classes = " + api.allClasses().size() + "\t\t# associations = " + api.allAssociations().size()
						+ "\n");
	}

	/***********************************************************
	 * @throws java.lang.Exception
	 ***********************************************************/
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	/***********************************************************
	 * @throws java.lang.Exception
	 ***********************************************************/
	@Before
	public void setUp() throws Exception
	{
		System.out.println("-------------------------------------------------------------------------------");
	}

	/***********************************************************
	 * @throws java.lang.Exception
	 ***********************************************************/
	@After
	public void tearDown() throws Exception
	{
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
								System.out.print("[BPMN2 specification non-conformance]");
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
	}

	@Test
	public void aCollaborationCanOnlyHaveOneInplicitProcess_TRUE()
	{
		api.readSOIL(SOIL_DIRECTORY + "aCollaborationCanOnlyHaveOneInplicitProcess_TRUE.cmd", true);
	}
	
	@Test
	public void aCollaborationCanOnlyHaveOneInplicitProcess_FALSE()
	{
		api.readSOIL(SOIL_DIRECTORY + "aCollaborationCanOnlyHaveOneInplicitProcess_FALSE.cmd", true);
	}

}
