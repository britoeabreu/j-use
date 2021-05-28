package org.quasar.juse.api;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import org.quasar.juse.api.implementation.BasicFacade;
import org.tzi.use.config.Options;
import org.tzi.use.uml.sys.MSystemException;

/**
 * @author fba
 *
 */
public class JUSE_BasicFacadeTest
{
//    private final static String MODEL_DIRECTORY = "models/Football";
//    private final static String MODEL_FILE = "CopaPaises(2020).use";
//    private final static String SOIL_FILE = "mundial&euro(2020).soil";

    private final static String MODEL_DIRECTORY = "metamodels/PIMETA";
    private final static String MODEL_FILE = "pimeta.use";
    private final static String SOIL_FILE = "java.cmd";

    private final static String CMD_FILE = "dump.cmd";

    static JUSE_BasicFacade api;

    @BeforeClass
    public static void setUpBeforeClass()
    {
    }

    @AfterClass
    public static void tearDownAfterClass()
    {
    }

    @Before
    public void setUp()
    {
	api = new BasicFacade();
    }


    @After
    public void tearDown()
    {
	if (api.getSystem() != null)
	    api.getSystem().reset();
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#getSystem()}.
     */
    @Test
    public final void testGetSystem()
    {
	assertNull(api.getSystem());
	api.initialize(null, MODEL_DIRECTORY);
	api.compileSpecification(MODEL_FILE, false);
	assertNotNull(api.getSystem());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#initialize(java.lang.String[], java.lang.String)}.
     */
    @Test
    public final void testInitialize()
    {
	assertNull(api.getSystem());
	api.initialize(null, MODEL_DIRECTORY);
	assertEquals(MODEL_DIRECTORY, System.getProperty("user.dir"));
	assertEquals(Paths.get(MODEL_DIRECTORY), Options.getLastDirectory());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#compileSpecification(java.lang.String, boolean)}.
     */
    @Test
    public final void testCompileSpecification()
    {
	api.initialize(null, MODEL_DIRECTORY);
	assertNull(api.getSystem());
	api.compileSpecification("-------", false);
	assertNull(api.getSystem());
	api.compileSpecification(null, false);
	assertTrue(api.getSystem().model().classes().isEmpty());
	api.compileSpecification(MODEL_FILE, false);
	assertFalse(api.getSystem().model().classes().isEmpty());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#readSOIL(java.lang.String, java.lang.String, boolean)}.
     */
    @Test
    public final void testReadSOIL()
    {
	api.initialize(null, MODEL_DIRECTORY);
	api.compileSpecification(MODEL_FILE, false);
	assertFalse(api.getSystem().state().hasObjects());
	assertTrue(api.readSOIL(MODEL_DIRECTORY, SOIL_FILE, true));
	assertTrue(api.getSystem().state().hasObjects());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_BasicFacade#dumpState(java.lang.String, java.lang.String, java.lang.String, boolean)}.
     */
    @Test
    public final void testDumpState()
    {
	api.initialize(null, MODEL_DIRECTORY);
	api.compileSpecification(MODEL_FILE, false);
	assertTrue(api.readSOIL(MODEL_DIRECTORY, SOIL_FILE, false));
	assertTrue(api.dumpState("FBA", MODEL_DIRECTORY, CMD_FILE, false));
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
	api.initialize(null, MODEL_DIRECTORY);
	api.compileSpecification(MODEL_FILE, false);
	assertTrue(api.readSOIL(MODEL_DIRECTORY, SOIL_FILE, false));
	api.command("info state");
    }

//    /**
//     * Test method for {@link org.quasar.juse.api.JUSE_BasicFacade#createShell()}.
//     */
//    @Test
//    public final void testCreateShell()
//    {
//	api.initialize(null, MODEL_DIRECTORY);
//	api.compileSpecification(MODEL_FILE, false);
//	assertTrue(api.readSOIL(MODEL_DIRECTORY, SOIL_FILE, false));
//	api.createShell();
//    }

}
