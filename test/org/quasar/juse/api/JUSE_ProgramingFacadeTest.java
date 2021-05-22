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
public class JUSE_ProgramingFacadeTest
{
//	private final static String	USE_BASE_DIRECTORY	= "D:\\Google Drive\\EclipseWorkspace\\use-5.1.0";

	private final static String MODEL_DIRECTORY = "D:/My Drive/TEACH/UML/_MODELS(USE)/PT_RUTISEO_Futebol_O/CopaPaises";
//private final static String MODEL_DIRECTORY = "G:/O meu disco/TEACH/UML/_MODELS(USE)/PT_RUTISEO_Futebol_O/CopaPaises";
	private final static String MODEL_FILE = "CopaPaises.use";
//	private final static String SOIL_FILE = "euro2012.soil";
//	private final static String CMD_FILE = "dump.cmd";
	
	static JUSE_ProgramingFacade api = new ProgramingFacade();
	
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
		api.reset();
	}

	//===================================================================================
	
		/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allClasses()}.
	 */
	@Test
	public final void testAllClasses()
	{
		assertEquals(17, api.allClasses().size());
		assertTrue(api.allClasses().contains(api.classByName("Pais")));
	}
	
	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allAssociations()}.
	 */
	@Test
	public final void testAllAssociations()
	{
		assertEquals(17, api.allAssociations().size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allStateMachines()}.
	 */
	@Test
	public final void testAllStateMachines()
	{
		assertEquals(0, api.allStateMachines().size());
	}
	
	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allInvariants()}.
	 */
	@Test
	public final void testAllInvariants()
	{
		assertEquals(13, api.allInvariants().size());
	}
	
	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allPreConditions()}.
	 */
	@Test
	public final void testAllPreConditions()
	{
		assertEquals(9, api.allPreConditions().size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allPostConditions()}.
	 */
	@Test
	public final void testAllPostConditions()
	{
		assertEquals(0, api.allPostConditions().size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#getAnnotations(org.tzi.use.uml.mm.MModelElement)}.
	 */
	@Test
	public final void testGetAnnotations()
	{
//		MOperation op = api.operationByName("Pais", "inicializa");
//		assertEquals(5, api.getAnnotations(api.operationByName("Pais", "inicializa")).size());
//		
//		System.out.println("Pais::inicializa >>>>>" + api.getAnnotations(api.operationByName("Pais", "inicializa")));
		

		for (MClass aClass: api.allClasses())
		{
			if (aClass.isAnnotated())
				api.printAnnotations("CLASS: " + aClass.name(), aClass);
			
			for (MAttribute attribute: aClass.attributes())
				if (attribute.isAnnotated())
					api.printAnnotations("ATTRIBUTE: " + aClass.name() + "." + attribute.name(), attribute);

			for (MOperation operation: aClass.operations())
			{
				if (operation.isAnnotated())
					api.printAnnotations("OPERATION: " + aClass.name() + "." + operation.name(), operation);
			
				for (MPrePostCondition pre: operation.preConditions())
					if (pre.isAnnotated())
						api.printAnnotations("PRE: " + aClass.name() + "." + operation.name() + "::" + pre.name(), pre);
				
				for (MPrePostCondition post: operation.postConditions())
					if (post.isAnnotated())
						api.printAnnotations("POST: " + aClass.name() + "." + operation.name() + "::" + post.name(), post);
			}
			
			for (MClassInvariant invariant: api.allInvariants())
				if (invariant.cls().equals(aClass) && invariant.isAnnotated())
					api.printAnnotations("INV: " + aClass.name() + "::" + invariant.name(), invariant);
		}

	}

	
	//============================================================================
	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#statistics(boolean)}.
	 */
	@Test
	public final void testStatistics()
	{
		MObject ronaldo = api.createObject("ronaldo", "Jogador");
		api.setObjectAttribute(ronaldo, "nome", new StringValue("Cristiano Ronaldo"));
		
		MObject iniesta = api.createObject("iniesta", "Jogador");

		MObject euro2012 = api.createObject("euro2012", "Campeonato");
		MObject spain = api.createObject("spain", "Pais");
		MObject real = api.createObject("real", "Clube");
		MObject barca = api.createObject("barca", "Clube");

		MObject game1 = api.createObject("game1", "Jogo");
		MLinkObject spainGame1 = api.createLinkObject("spainGame1", "Equipa", Arrays.asList(spain, game1));
		
		MLinkObject euro2012_spain = api.createLinkObject("euro2012_spain", "Participacao",	Arrays.asList(spain, euro2012));
		MLinkObject spainGame1_iniesta = api.createLinkObject("spainGame1_iniesta", "Membro", Arrays.asList(iniesta, spainGame1));
		
		MLink spain_real = api.createLink("Pais_Clube", Arrays.asList(spain, real));
		MLink spain_barca = api.createLink("Pais_Clube", Arrays.asList(spain, barca));

		MLink ronaldo_real = api.createLink("Clube_Jogador", Arrays.asList(real, ronaldo));
		MLink iniesta_barca = api.createLink("Clube_Jogador", Arrays.asList(barca, iniesta));
		
		MObject cartao1 = api.createObject("cartao1",  "Cartao");
		api.setObjectAttribute(cartao1, "tipo", new EnumValue(api.enumByName("TipoCartao"), "Vermelho"));
		api.setObjectAttribute(cartao1, "minuto", new IntegerValue(57));
		
		MLink cartao1_iniesta = api.createLink("Membro_Cartao", Arrays.asList(spainGame1_iniesta, cartao1));
	
		api.setObjectAttribute(spainGame1_iniesta, "minuto_entrada", new IntegerValue(0));
		api.setObjectAttribute(spainGame1_iniesta, "minuto_saida", new IntegerValue(80));

		System.out.println("_____________________________________________________");
		api.command("info state");
		System.out.println("_____________________________________________________");

		api.setObjectAttribute(ronaldo, "nome", new StringValue("Cristiano Ronaldo"));
		api.setObjectAttribute(ronaldo, "posicao", new EnumValue(api.enumByName("PosicaoJogador"), "Avancado"));
		
		System.out.println(api.getObjectAttribute(ronaldo, "nome"));
		System.out.println(api.getObjectAttribute(ronaldo, "posicao"));

		for (MObject clube : api.allObjects("Clube"))
			System.out.println("CLUBE > " + clube);

		for (MObject jogador : api.allObjects("Jogador"))
			System.out.println("JOGADOR > " + jogador);

		System.out.println("_____________________________________________________");
		api.command("info vars");
		System.out.println("_____________________________________________________");
		
		// Model CopaPaises (17 classes, 17 associations, 13 invariants, 27 operations, 9 pre-/postconditions, 0 state machines)
		assertTrue(api.statistics(false).contains("17"));
		assertTrue(api.statistics(false).contains("13"));
		assertTrue(api.statistics(false).contains("9"));
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allObjects()}.
	 */
	@Test
	public final void testAllObjects()
	{
		testStatistics();
		assertEquals(11, api.allObjects().size());
	}
	
	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allObjects(java.lang.String)}.
	 */
	@Test
	public final void testAllObjectsString()
	{
		testStatistics();
		assertEquals(2, api.allObjects("Jogador").size());
		assertEquals(1, api.allObjects("Campeonato").size());
		assertEquals(2, api.allObjects("Clube").size());
		assertEquals(1, api.allObjects("Pais").size());
		assertEquals(1, api.allObjects("Participacao").size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allObjects(org.tzi.use.uml.mm.MClass)}.
	 */
	@Test
	public final void testAllObjectsMClass()
	{
		testStatistics();
		assertEquals(2, api.allObjects(api.classByName("Jogador")).size());
		assertEquals(1, api.allObjects(api.classByName("Campeonato")).size());
		assertEquals(2, api.allObjects(api.classByName("Clube")).size());
		assertEquals(1, api.allObjects(api.classByName("Pais")).size());
		assertEquals(1, api.allObjects(api.classByName("Participacao")).size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allLinks()}.
	 */
	@Test
	public final void testAllLinks()
	{
		testStatistics();
		assertEquals(8, api.allLinks().size());
	}

	
	//=========================================================
	
	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createObject(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testCreateObjectStringString()
	{
		MObject ronaldo = api.createObject("ronaldo", "Jogador");
		assertEquals("Jogador", ronaldo.cls().name());
		assertEquals("ronaldo", ronaldo.name());
		assertEquals(1, api.allObjects("Jogador").size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createObject(java.lang.String, org.tzi.use.uml.mm.MClass)}.
	 */
	@Test
	public final void testCreateObjectStringMClass()
	{
		MObject europa = api.createObject("europa", api.classByName("Continente"));
		assertEquals("Continente", europa.cls().name());
		assertEquals("europa", europa.name());
		assertEquals(1, api.allObjects("Continente").size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteObject(org.tzi.use.uml.sys.MObject)}.
	 */
	@Test
	public final void testDeleteObject()
	{
		testCreateObjectStringString();
		testCreateObjectStringMClass();
		assertEquals(1, api.allObjects("Jogador").size());
		assertEquals(1, api.allObjects("Continente").size());

		api.deleteObject(api.objectByName("ronaldo"));
		assertEquals(0, api.allObjects("Jogador").size());

		api.deleteObject(api.objectByName("europa"));
		assertEquals(0, api.allObjects("Continente").size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.String, java.lang.String, java.util.List)}.
	 */
	@Test
	public final void testCreateLinkObjectStringStringListOfMObject()
	{
		MObject spain = api.createObject("spain", "Pais");
		MObject euro2012 = api.createObject("euro2012", "Campeonato");

		MLinkObject euro2012_spain = api.createLinkObject("euro2012_spain", "Participacao",	Arrays.asList(spain, euro2012));
		assertEquals("euro2012_spain", euro2012_spain.name());
		assertEquals("Participacao", euro2012_spain.cls().name());
		assertTrue(euro2012_spain.linkedObjects().contains(spain));
		assertTrue(euro2012_spain.linkedObjects().contains(euro2012));
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.String, org.tzi.use.uml.mm.MAssociationClass, java.util.List)}.
	 */
	@Test
	public final void testCreateLinkObjectStringMAssociationClassListOfMObject()
	{
		MObject portugal = api.createObject("portugal", "Pais");
		MObject world2018 = api.createObject("world2018", "Campeonato");

		MLinkObject world2018_portugal = api.createLinkObject("world2018_portugal", api.associationClassByName("Participacao"), Arrays.asList(portugal, world2018));
		assertEquals("world2018_portugal", world2018_portugal.name());
		assertEquals("Participacao", world2018_portugal.cls().name());
		assertTrue(world2018_portugal.linkedObjects().contains(portugal));
		assertTrue(world2018_portugal.linkedObjects().contains(world2018));
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(org.tzi.use.uml.sys.MLinkObject)}.
	 */
	@Test
	public final void testDeleteLinkObject()
	{
		testCreateLinkObjectStringStringListOfMObject();
		testCreateLinkObjectStringMAssociationClassListOfMObject();
		assertEquals(2, api.allObjects("Participacao").size());
		
		api.deleteLinkObject(api.linkObjectByName("euro2012_spain"));
		api.deleteLinkObject(api.linkObjectByName("world2018_portugal"));
		assertEquals(0, api.allObjects("Participacao").size());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#setObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute, org.tzi.use.uml.ocl.value.Value)}.
	 */
	@Test
	public final void testSetObjectAttribute()
	{
		MObject lisboa = api.createObject("lisboa", api.classByName("Cidade"));
		
//		for (MAttribute att: lisboa.cls().attributes())
//			System.out.println(">>>"+att.toString());
		
		api.setObjectAttribute(lisboa, api.attributeByName(lisboa, "nome"), new StringValue("Lisboa"));	
		api.setObjectAttribute(lisboa, api.attributeByName(lisboa, "populacao"), new IntegerValue(1000000));	
		api.setObjectAttribute(lisboa, api.attributeByName(lisboa, "capital"), BooleanValue.TRUE);
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute)}.
	 */
	@Test
	public final void testGetObjectAttribute()
	{
		testSetObjectAttribute();
		MObject lisboa = api.objectByName("lisboa");
		
		assertEquals("'Lisboa'", api.getObjectAttribute(lisboa, api.attributeByName(lisboa, "nome")).toString());
		assertEquals("1000000", api.getObjectAttribute(lisboa, api.attributeByName(lisboa, "populacao")).toString());
		assertEquals("true", api.getObjectAttribute(lisboa, api.attributeByName(lisboa, "capital")).toString());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLink(java.lang.String, java.util.List)}.
	 */
	@Test
	public final void testCreateLinkStringListOfMObject()
	{
		MObject real = api.createObject("real", "Clube");
		MObject ronaldo = api.createObject("ronaldo", "Jogador");
		
		MLink real_ronaldo = api.createLink("Clube_Jogador", Arrays.asList(real, ronaldo));
		assertTrue(real_ronaldo.linkedObjects().contains(real));
		assertTrue(real_ronaldo.linkedObjects().contains(ronaldo));
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLink(org.tzi.use.uml.mm.MAssociation, java.util.List)}.
	 */
	@Test
	public final void testCreateLinkMAssociationListOfMObject()
	{
		MObject barca = api.createObject("barca", "Clube");
		MObject iniesta = api.createObject("iniesta", "Jogador");

		MLink barca_iniesta = api.createLink("Clube_Jogador", Arrays.asList(barca, iniesta));
		assertTrue(barca_iniesta.linkedObjects().contains(barca));
		assertTrue(barca_iniesta.linkedObjects().contains(iniesta));
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteLink(org.tzi.use.uml.mm.MLink)}.
	 */
	@Test
	public final void testDeleteLink()
	{
		testCreateLinkStringListOfMObject();
		for (MLink link: api.allLinks("Clube_Jogador"))
			api.deleteLink(link);
		
		testCreateLinkMAssociationListOfMObject();
		for (MLink link: api.allLinks("Clube_Jogador"))
			api.deleteLink(link);
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteLink(org.tzi.use.uml.mm.MAssociation, java.util.List)}.
	 */
	@Test
	public final void testDeleteLinkMAssociationListofMObject()
	{
		testCreateLinkStringListOfMObject();
		testCreateLinkMAssociationListOfMObject();
		assertEquals(2, api.allLinks("Clube_Jogador").size());
		
		api.deleteLink(api.associationByName("Clube_Jogador"), Arrays.asList(api.objectByName("real"), api.objectByName("ronaldo")));
		api.deleteLink(api.associationByName("Clube_Jogador"), Arrays.asList(api.objectByName("barca"), api.objectByName("iniesta")));
		
		assertEquals(0, api.allLinks("Clube_Jogador").size());
	};
	
	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#objectByName(java.lang.String)}.
	 */
	@Test
	public final void testObjectByName()
	{
		MObject sporting = api.createObject("sporting", "Clube");
		api.setObjectAttribute(sporting, api.attributeByName(sporting, "nome"),  new StringValue("Sporting Clube de Portugal"));
		
		MObject guessWho = api.objectByName("sporting");
		assertEquals("'Sporting Clube de Portugal'", api.getObjectAttribute(guessWho, api.attributeByName(guessWho, "nome")).toString());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#classByName(java.lang.String)}.
	 */
	@Test
	public final void testClassByName()
	{
		assertEquals("Continente", api.classByName("Continente").name());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#enumByName(java.lang.String)}.
	 */
	@Test
	public final void testEnumByName()
	{
		assertEquals("TipoCartao", api.enumByName("TipoCartao").name());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#associationClassByName(java.lang.String)}.
	 */
	@Test
	public final void testAssociationClassByName()
	{
		assertEquals("Equipa", api.associationClassByName("Equipa").name());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#associationByName(java.lang.String)}.
	 */
	@Test
	public final void testAssociationByName()
	{
		assertEquals("Continente_Pais", api.associationByName("Continente_Pais").name());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#attributeByName(org.tzi.use.uml.sys.MObject, java.lang.String)}.
	 */
	@Test
	public final void testAttributeByName()
	{
		MObject sporting = api.createObject("sporting", "Clube");
		api.setObjectAttribute(sporting, api.attributeByName(sporting, "nome"),  new StringValue("Sporting Clube de Portugal"));
		assertEquals("'Sporting Clube de Portugal'", api.getObjectAttribute(sporting, api.attributeByName(sporting, "nome")).toString());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#operationByName(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testOperationByName()
	{
		MOperation op = api.operationByName("Pais", "inicializa");
		assertEquals("Pais", op.cls().name());
		assertEquals("inicializa", op.name());
	}
	
	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#invariantByName(java.lang.String)}.
	 */
	@Test
	public final void testInvariantByName()
	{
		assertEquals("MinutoCorreto", api.invariantByName("MinutoCorreto").name());
		assertEquals("Golo", api.invariantByName("MinutoCorreto").cls().name());
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#associationCoverage()}.
	 */
//	@Test
//	public final void testAssociationCoverage()
//	{
//		fail("Not yet implemented"); // TODO
//	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#associationEndCoverage()}.
	 */
//	@Test
//	public final void testAssociationEndCoverage()
//	{
//		fail("Not yet implemented"); // TODO
//	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#check(org.tzi.use.uml.mm.MClassInvariant)}.
	 */
	@Test
	public final void testCheck()
	{
		for (MClassInvariant invariant: api.allInvariants())
			assertTrue(api.check(invariant));

		testStatistics();
		
		int falseInvariants = 0;
		for (MClassInvariant invariant: api.allInvariants())
			if (!api.check(invariant)) falseInvariants++;

		assertEquals(4, falseInvariants);
	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#checkStructure()}.
	 */
//	@Test
//	public final void testCheckStructure()
//	{
//		fail("Not yet implemented"); // TODO
//	}

	/**
	 * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#oclEvaluator(java.lang.String)}.
	 */
	@Test
	public final void testOclEvaluator()
	{
		testStatistics();
//		System.out.println("OCL Evaluator>"+api.oclEvaluator("Jogador.allInstances").toStringWithType()+"<");
		
		String OCLexpression = "Jogador.allInstances->size";
		Value value = api.oclEvaluator(OCLexpression);
		assertEquals(2, api.toObject(value));

		OCLexpression = "spain.cidades->isEmpty()";
		value = api.oclEvaluator(OCLexpression);
		assertEquals(true, api.toObject(value));
		
		OCLexpression = "ronaldo.nome";
		value = api.oclEvaluator(OCLexpression);
		assertEquals("Cristiano Ronaldo", api.toObject(value));
		
		OCLexpression = "(spainGame1_iniesta.minuto_saida - spainGame1_iniesta.minuto_entrada)/90";
		value = api.oclEvaluator(OCLexpression);
		assertEquals(80/90.0, (Double) api.toObject(value), 0.001);
}

}
