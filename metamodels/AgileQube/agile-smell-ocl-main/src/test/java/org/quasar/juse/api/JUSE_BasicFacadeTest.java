/**
 * 
 */
package org.quasar.juse.api;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quasar.juse.api.implementation.ProgramingFacade;

/**
 * @author fba
 *
 */
public class JUSE_BasicFacadeTest
{
	private final static String	USE_BASE_DIRECTORY	= "/Users/utelemaco/Downloads/use-6.0.0";

	private final static String MODEL_DIRECTORY = "/Users/utelemaco/development/sandbox/ocl/agile-smell-ocl/src/main/resources/";
	private final static String MODEL_FILE = "agileProjectMetamodel.use";
	private final static String SOIL_FILE = "agileProjectMetamodel.soil";
	private final static String CMD_FILE = "dump.cmd";
	
	static JUSE_ProgramingFacade api = new ProgramingFacade();
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		api.initialize(null, USE_BASE_DIRECTORY, MODEL_DIRECTORY);
		api.compileSpecification(MODEL_FILE, false);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#initialize(java.lang.String[], java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testInitialize()
	{
		assertNotNull(api);
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#compileSpecification(java.lang.String, boolean)}.
	 */
	@Test
	public final void testCompileSpecification()
	{
		assertFalse(api.allClasses().isEmpty());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#readSOIL(java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	public final void testReadSOIL()
	{
		assertTrue(api.readSOIL(MODEL_DIRECTORY, SOIL_FILE, true));
		assertFalse(api.allObjects().isEmpty());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#dumpState(java.lang.String, java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	public final void testDumpState()
	{
		assertTrue(api.readSOIL(MODEL_DIRECTORY, SOIL_FILE, false));
		api.dumpState("FBA", MODEL_DIRECTORY, CMD_FILE, false);
		File cmdFile = new File(MODEL_DIRECTORY + "/" + CMD_FILE);
		assertTrue(cmdFile.isFile());
		assertTrue(cmdFile.delete());
		assertFalse(cmdFile.isFile());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#command(java.lang.String)}.
	 */
	@Test
	public final void testCommand()
	{
		api.command("info state");
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#createShell()}.
	 */
	@Test
	public final void testCreateShell()
	{
		// api.createShell();
	}

}
