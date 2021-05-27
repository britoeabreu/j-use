package org.quasar.juse.api;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quasar.juse.api.implementation.ProgramingByContractFacade;
import org.quasar.juse.api.implementation.ProgramingFacade;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.EnumValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;

/**
 * @author fba
 *
 */
public class JUSE_ProgramingByContractFacadeTest
{
    private final static String MODEL_DIRECTORY = "models/Football";
    private final static String MODEL_FILE = "CopaPaises(2020).use";
//	private final static String SOIL_FILE = "mundial&euro(2020).soil";
//	private final static String CMD_FILE = "dump.cmd";

    static JUSE_ProgramingByContractFacade api = new ProgramingByContractFacade();

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
	api.initialize(null, MODEL_DIRECTORY);
	api.compileSpecification(MODEL_FILE, true);
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
	api.reset();
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

    // ===================================================================================

    
    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#allInvariants()}.
     */
    @Test
    public final void testAllInvariants()
    {
	assertEquals(14, api.allInvariants().size());
    }
    
    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#allInvariants(org.tzi.use.uml.mm.MClassInvariant)}.
     */
    @Test
    public final void testAllInvariantsMClass()
    {
	assertEquals(3, api.allInvariants(api.getClassByName("Campeonato")).size());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#allPreConditions()}.
     */
    @Test
    public final void testAllPreConditions()
    {
	assertEquals(9, api.allPreConditions().size());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#allPreConditions(org.tzi.use.uml.mm.MOperation)}.
     */
    @Test
    public final void testAllPreConditionsMOperation()
    {
	assertEquals(5, api.allPreConditions(api.getOperationByName("Pais",  "inicializa")).size());
    }
    
    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#allPostConditions()}.
     */
    @Test
    public final void testAllPostConditions()
    {
	assertEquals(2, api.allPostConditions().size());
    }
    
    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#allPostConditions(org.tzi.use.uml.mm.MOperation)}.
     */
    @Test
    public final void testAllPostConditionsMOperation()
    {
	assertEquals(2, api.allPostConditions(api.getOperationByName("Cidade",  "inicializa")).size());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#getInvariantByName(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testGetInvariantByName()
    {
	assertEquals("MinutoCorreto", api.getInvariantByName("Golo", "MinutoCorreto").name());
	assertEquals("Golo", api.getInvariantByName("Golo", "MinutoCorreto").cls().name());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#getPreConditionByName(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testGetPreConditionByName()
    {
	MPrePostCondition preCondition = api.getPreConditionByName("Pais", "inicializa", "Pais_NomeNaoVazio");
	
	assertEquals("Pais", preCondition.cls().name());
	assertEquals("inicializa", preCondition.operation().name());
	assertEquals("Pais_NomeNaoVazio", preCondition.name());
    }
    
    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#getPostConditionByName(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testGetPostConditionByName()
    {
	MPrePostCondition postCondition = api.getPostConditionByName("Cidade", "inicializa", "Cidade_NomeUnico");
	
	assertEquals("Cidade", postCondition.cls().name());
	assertEquals("inicializa", postCondition.operation().name());
	assertEquals("Cidade_NomeUnico", postCondition.name());
    }
    
    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#associationCoverage()}.
     */
	@Test
	public final void testAssociationCoverage()
	{
		assertEquals(0.41176, api.associationCoverage(), 0.001);
	}

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingByContractFacade#associationEndCoverage()}.
     */
	@Test
	public final void testAssociationEndCoverage()
	{
		assertEquals(0.2647, api.associationEndCoverage(), 0.001);
	}


    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#checkStructure()}.
     */
	@Test
	public final void testCheckStructure()
	{
		assertTrue(api.checkStructure());
	}
    
	
	    /**
	     * Test method for
	     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#checkInvariants()}.
	     */
		@Test
		public final void testCheckInvariants()
		{
			assertTrue(api.checkInvariants());
		}
		
		/**
	     * Test method for
	     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#checkInvariant(org.tzi.use.uml.mm.MClassInvariant)}.
	     */
	    @Test
	    public final void testCheckInvariant()
	    {
		for (MClassInvariant invariant : api.allInvariants())
		    assertTrue(api.checkInvariant(invariant));

		int falseInvariants = 0;
		for (MClassInvariant invariant : api.allInvariants())
		    if (!api.checkInvariant(invariant))
			falseInvariants++;

		assertEquals(0, falseInvariants);
	    }

//	    /**
//	     * Test method for
//	     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#checkPreCondition(org.tzi.use.uml.mm.MPrePostCondition)}.
//	     */
//	    @Test
//	    public final void testCheckPreCondition()
//	    {
//		MPrePostCondition preCondition = api.getPreConditionByName("Pais", "inicializa", "Pais_NomeNaoVazio");
//		
//		api.command("!create uk: Pais");
//		api.command("!create europa: Continente");
//		api.command("!uk.inicializa('Reino Unido', 70000, 56900, europa)");
//
//		MObject ru = api.getObjectByName("uk");
//
//		System.out.println(">>" + api.getObjectAttribute(ru, "nome") + "<<");
//		assertEquals("Reino Unido", api.getObjectAttribute(ru, "nome"));
//
//		System.out.println(">>" + api.getObjectAttribute(ru, "nome") + "<<");
//		assertEquals(70000, api.getObjectAttribute(ru, "populacao"));
//
//		System.out.println(">>" + api.getObjectAttribute(ru, "nome") + "<<");
//		assertEquals(56900, api.getObjectAttribute(ru, "area"));
//		
//		assertTrue(api.checkPreCondition(preCondition));
//	    }
//	    
//	    /**
//	     * Test method for
//	     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#checkPostCondition(org.tzi.use.uml.mm.MPrePostCondition)}.
//	     */
//	    @Test
//	    public final void testCheckPostCondition()
//	    {
//		MPrePostCondition postCondition = api.getPostConditionByName("Cidade", "inicializa", "Cidade_NomeUnico");
//		
//		assertTrue(api.checkPostCondition(postCondition));		
//	    }	    
	 
}
