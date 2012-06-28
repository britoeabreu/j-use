/***********************************************************
 * Filename: BPMNvalidatorTests.java
 * Created:  28 de Jun de 2012
 ***********************************************************/
package org.quasar.bpmn2.validator.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

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
	private final static String				SOIL_DIRECTORY		= "TestSuiteModels";
	
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
		// System.out.println("\t# objects = " + api.allObjects().size() + "\t\t# links = " + api.allLinks().size() + "\n");
		if (getFailedInvariants().size()>1)
			for (MClassInvariant anInvariant : getFailedInvariants())
				printInvariantInfo(anInvariant);
	}
	
	/***********************************************************
	* @return
	***********************************************************/
	private Set<MClassInvariant> getFailedInvariants()
	{
		Set<MClassInvariant> result = new HashSet<MClassInvariant>();
		for (MClassInvariant anInvariant : api.allInvariants())
		{
			if (!api.check(anInvariant))
				result.add(anInvariant);
		}
		return result;
	}
	
	/***********************************************************
	* @param invariantName
	* @return
	***********************************************************/
	private boolean invariantFails(String invariantName)
	{
		for (MClassInvariant anInvariant : getFailedInvariants())
		{
			// System.out.println("INVARIANT NAME>" + anInvariant.name() + "<");
			if (anInvariant.name().equals(invariantName))
				return true;
		}
		return false;
	}
	
	/***********************************************************
	* @param anInvariant
	***********************************************************/
	private void printInvariantInfo(MClassInvariant anInvariant)
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
					case 'B':
						System.out.print("[Best practice violation]");
						break;
					case 'I':
						System.out.print("[Information Message]");
						break;
					default:
						System.out.print("UNKNOWN MESSAGE TYPE!!!");
				}
				System.out.print("> " + anInvariant.getAnnotationValue(key, "msg"));
				System.out.println(" (" + anInvariant.getAnnotationValue(key, "origin") + ")");
			}
	}

	/***********************************************************
	* 
	***********************************************************/
	private void testStub()
	{
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		String invariantName = stack[2].getMethodName();
		
//		System.out.println(">>>" +invariantName );
		
		api.readSOIL(SOIL_DIRECTORY + "/PASS/" + invariantName + ".cmd", true);
		assertEquals(0, getFailedInvariants().size());

		api.readSOIL(SOIL_DIRECTORY + "/FAIL/" + invariantName + ".cmd", true);
		assertEquals(1, getFailedInvariants().size());
//		System.out.println("DEBUG>" +invariantFails(invariantName));
		assertTrue(invariantFails(invariantName));
	}
	
	/***********************************************************
	* 
	***********************************************************/
	@Test
	public void aCollaborationCanOnlyHaveOneInplicitProcess()
	{
		testStub();
	}

	/***********************************************************
	* 
	***********************************************************/
	@Test
	public void existsCatchSignalEvent()
	{
		testStub();
	}
	
}
