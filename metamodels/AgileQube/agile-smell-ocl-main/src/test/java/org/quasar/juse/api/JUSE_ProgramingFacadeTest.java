/**
 * 
 */
package org.quasar.juse.api;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quasar.juse.api.implementation.ProgramingFacade;
import org.tzi.use.uml.ocl.value.EnumValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;

/**
 * @author fba
 *
 */
public class JUSE_ProgramingFacadeTest
{
	private final static String	USE_BASE_DIRECTORY	= "D:\\Google Drive\\EclipseWorkspace\\use-5.0.1";

	private final static String MODEL_DIRECTORY = "G:/O meu disco/TEACH/UML/_MODELS/PT_RUTISEO_Futebol_O/CopaPaises";
	private final static String MODEL_FILE = "CopaPaises.use";
	private final static String SOIL_FILE = "euro2012.soil";
	private final static String CMD_FILE = "dump.cmd";
	
	static JUSE_ProgramingFacade api = new ProgramingFacade();
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		api.initialize(null, USE_BASE_DIRECTORY, MODEL_DIRECTORY);
		api.compileSpecification(MODEL_FILE, true);


		MObject ronaldo = api.createObject("ronaldo", "Jogador");
		MObject messi = api.createObject("messi", "Jogador");

		MObject euro2012 = api.createObject("euro2012", "Campeonato");
		MObject spain = api.createObject("spain", "Pais");
		MObject real = api.createObject("real", "Clube");
		MObject barca = api.createObject("barca", "Clube");

		MLinkObject euro2012_spain = api.createLinkObject("euro2012_spain", "Participacao",	Arrays.asList(spain, euro2012));

		MLink spain_real = api.createLink("Pais_Clube", Arrays.asList(spain, real));
		MLink spain_barca = api.createLink("Pais_Clube", Arrays.asList(spain, barca));

		MLink ronaldo_real = api.createLink("Clube_Jogador", Arrays.asList(real, ronaldo));
		MLink messi_barca = api.createLink("Clube_Jogador", Arrays.asList(barca, messi));

		System.out.println("_____________________________________________________");
		api.command("info state");
		System.out.println("_____________________________________________________");

		api.setObjectAttribute(ronaldo, api.attributeByName(ronaldo, "nome"), new StringValue("Cristiano Ronaldo"));
		api.setObjectAttribute(ronaldo, api.attributeByName(ronaldo, "posicao"), new EnumValue(api.enumByName("PosicaoJogador"), "Avancado"));
		
		System.out.println(api.getObjectAttribute(ronaldo, api.attributeByName(ronaldo, "nome")));
		System.out.println(api.getObjectAttribute(ronaldo, api.attributeByName(ronaldo, "posicao")));

		for (MObject clube : api.allObjects("Clube"))
			System.out.println("CLUBE > " + clube);

		for (MObject jogador : api.allObjects("Jogador"))
			System.out.println("JOGADOR > " + jogador);

		System.out.println("_____________________________________________________");
		api.command("info vars");
		System.out.println("_____________________________________________________");
		
		System.out.println("OCL Evaluator>"+api.oclEvaluator("Jogador.allInstances->size").toStringWithType()+"<");
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
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#statistics(boolean)}.
	 */
	@Test
	public final void testStatistics()
	{
		// Model CopaPaises (17 classes, 17 associations, 18 invariants, 27 operations, 0 pre-/postconditions, 0 state machines)
		assertTrue(api.statistics(false).contains("17"));
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createObject(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testCreateObjectStringString()
	{
		assertEquals(2, api.allObjects("Jogador").size());
		assertEquals(1, api.allObjects("Campeonato").size());
		assertEquals(2, api.allObjects("Clube").size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createObject(java.lang.String, org.tzi.use.uml.mm.MClass)}.
	 */
	@Test
	public final void testCreateObjectStringMClass()
	{
		MObject europaMObject = api.createObject("europa", api.classByName("Continente"));	
		assertEquals(1, api.allObjects("Continente").size());
		api.deleteObject(europaMObject);
		assertEquals(0, api.allObjects("Continente").size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteObject(org.tzi.use.uml.sys.MObject)}.
	 */
	@Test
	public final void testDeleteObject()
	{
		// already tested in testCreateObjectStringMClass()
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.String, java.lang.String, java.util.List)}.
	 */
	@Test
	public final void testCreateLinkObjectStringStringListOfMObject()
	{
		// createLinkObjectStringStringListOfMObject() used in setUpBeforeClass()
		assertEquals(1, api.allObjects("Participacao").size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.String, org.tzi.use.uml.mm.MAssociationClass, java.util.List)}.
	 */
	@Test
	public final void testCreateLinkObjectStringMAssociationClassListOfMObject()
	{
		MObject world2018_MObject = api.createObject("world2018", "Campeonato");
		assertEquals(2, api.allObjects("Campeonato").size());	

		MObject portugal_MObject = api.createObject("portugal", "Pais");
		assertEquals(2, api.allObjects("Pais").size());	

		MLinkObject word2018_portugal_MObject = api.createLinkObject("world2018_portugal", api.associationClassByName("Participacao"), Arrays.asList(portugal_MObject, world2018_MObject));
		assertEquals(2, api.allObjects("Participacao").size());	

		api.deleteLinkObject(word2018_portugal_MObject);
		assertEquals(1, api.allObjects("Participacao").size());	

		api.deleteObject(portugal_MObject);
		assertEquals(1, api.allObjects("Pais").size());	
		
		api.deleteObject(world2018_MObject);
		assertEquals(1, api.allObjects("Campeonato").size());	
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(org.tzi.use.uml.sys.MLinkObject)}.
	 */
	@Test
	public final void testDeleteLinkObject()
	{
		// already tested in testCreateLinkObjectStringMAssociationClassListOfMObject()
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#setObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute, org.tzi.use.uml.ocl.value.Value)}.
	 */
	@Test
	public final void testSetObjectAttribute()
	{
		MObject europaMObject = api.createObject("europaContinente", api.classByName("Continente"));	
		assertEquals(1, api.allObjects("Continente").size());
		
		api.setObjectAttribute(europaMObject, api.attributeByName(europaMObject, "nome"), new StringValue("Europa"));	
		assertTrue(api.getObjectAttribute(europaMObject, api.attributeByName(europaMObject, "nome")).toString().contains("Europa"));
		api.deleteObject(europaMObject);
		assertEquals(0, api.allObjects("Continente").size());	
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute)}.
	 */
	@Test
	public final void testGetObjectAttribute()
	{
		// already tested in testSetObjectAttribute()
		assertEquals(1, api.allObjects("Participacao").size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLink(java.lang.String, java.util.List)}.
	 */
	@Test
	public final void testCreateLinkStringListOfMObject()
	{
//		MLink spain_real = api.createLink("Pais_Clube", Arrays.asList(spain, real));
//		MLink spain_barca = api.createLink("Pais_Clube", Arrays.asList(spain, barca));
//
//		MLink ronaldo_real = api.createLink("Clube_Jogador", Arrays.asList(real, ronaldo));
//		MLink messi_barca = api.createLink("Clube_Jogador", Arrays.asList(barca, messi));
		
		// createLinkStringListOfMObject() used in setUpBeforeClass()
		assertEquals(1, api.allObjects("Participacao").size());
		
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLink(org.tzi.use.uml.mm.MAssociation, java.util.List)}.
	 */
	@Test
	public final void testCreateLinkMAssociationListOfMObject()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteLink(org.tzi.use.uml.mm.MAssociation, java.util.List)}.
	 */
	@Test
	public final void testDeleteLink()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allObjects(java.lang.String)}.
	 */
	@Test
	public final void testAllInstancesString()
	{
		assertEquals(2, api.allObjects(api.classByName("Jogador")).size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allObjects(org.tzi.use.uml.mm.MClass)}.
	 */
	@Test
	public final void testAllInstancesMClass()
	{
		assertEquals(2, api.allObjects("Jogador").size());
		assertEquals(1, api.allObjects("Campeonato").size());
		assertEquals(2, api.allObjects("Clube").size());
		assertEquals(1, api.allObjects("Pais").size());
		assertEquals(1, api.allObjects("Participacao").size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#objectByName(java.lang.String)}.
	 */
	@Test
	public final void testObjectByName()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#classByName(java.lang.String)}.
	 */
	@Test
	public final void testClassByName()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#enumByName(java.lang.String)}.
	 */
	@Test
	public final void testEnumByName()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#associationClassByName(java.lang.String)}.
	 */
	@Test
	public final void testAssociationClassByName()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#associationByName(java.lang.String)}.
	 */
	@Test
	public final void testAssociationByName()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#attributeByName(org.tzi.use.uml.sys.MObject, java.lang.String)}.
	 */
	@Test
	public final void testAttributeByName()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#invariantByName(java.lang.String)}.
	 */
	@Test
	public final void testInvariantByName()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allClasses()}.
	 */
	@Test
	public final void testAllClasses()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#associationCoverage()}.
	 */
	@Test
	public final void testAssociationCoverage()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#associationEndCoverage()}.
	 */
	@Test
	public final void testAssociationEndCoverage()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allAssociations()}.
	 */
	@Test
	public final void testAllAssociations()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allObjects()}.
	 */
	@Test
	public final void testAllObjects()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allStateMachines()}.
	 */
	@Test
	public final void testAllStateMachines()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allLinks()}.
	 */
	@Test
	public final void testAllLinks()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allInvariants()}.
	 */
	@Test
	public final void testAllInvariants()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allPreConditions()}.
	 */
	@Test
	public final void testAllPreConditions()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allPostConditions()}.
	 */
	@Test
	public final void testAllPostConditions()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#annotations(org.tzi.use.uml.mm.MModelElement)}.
	 */
	@Test
	public final void testAnnotations()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#check(org.tzi.use.uml.mm.MClassInvariant)}.
	 */
	@Test
	public final void testCheck()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#checkStructure()}.
	 */
	@Test
	public final void testCheckStructure()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#oclEvaluator(java.lang.String)}.
	 */
	@Test
	public final void testOclEvaluator()
	{
		fail("Not yet implemented"); // TODO
	}

}
