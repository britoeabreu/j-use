package org.quasar.juse.api;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quasar.juse.api.implementation.ProgramingFacade;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MElementAnnotation;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.EnumValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
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
    private final static String MODEL_DIRECTORY = "models/Football";
    private final static String MODEL_FILE = "CopaPaises(2020).use";

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

    // ===================================================================================

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allClasses()}.
     */
    @Test
    public final void testAllClasses()
    {
	assertEquals(19, api.allClasses().size());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allClassAttributes(org.tzi.use.uml.sys.MClass)}.
     */
    @Test
    public final void testAllClassAttributes()
    {
	assertEquals(4, api.allClassAttributes(api.getClassByName("Campeonato")).size());
	assertEquals(3, api.allClassAttributes(api.getClassByName("Cidade")).size());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allClassAttributes(java.lang.String)}.
     */
    @Test
    public final void testAllClassAttributesString()
    {
	assertEquals(4, api.allClassAttributes("Campeonato").size());
	assertEquals(3, api.allClassAttributes("Cidade").size());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allClassOperations(org.tzi.use.uml.sys.MClass)}.
     */
    @Test
    public final void testAllClassOperations()
    {
	assertEquals(2, api.allClassOperations(api.getClassByName("Campeonato")).size());
	assertEquals(1, api.allClassOperations(api.getClassByName("Cidade")).size());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allClassOperations(java.lang.String)}.
     */
    @Test
    public final void testAllClassOperationsString()
    {
	assertEquals(2, api.allClassOperations("Campeonato").size());
	assertEquals(1, api.allClassOperations("Cidade").size());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allAssociationClasses()}.
     */
    @Test
    public final void testAllAssociationClasses()
    {
	assertEquals(3, api.allAssociationClasses().size());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allEnumTypes()}.
     */
    @Test
    public final void testAllEnumTypes()
    {
	assertEquals(4, api.allEnumTypes().size());
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
	assertEquals(1, api.allStateMachines().size());
    }

    /**
     * Test method for @see org.quasar.juse.api.JUSE_ProgramingFacade#getStateMachineByName(java.lang.String) /** Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#getStateMachineByName()}.
     */
    @Test
    public final void testGetStateMachineByName()
    {
	assertNotNull(api.getStateMachineByName("Situacao"));
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#getAnnotations(org.tzi.use.uml.mm.MModelElement)}.
     */
    @Test
    public final void testGetAnnotations()
    {
	MOperation op = api.getOperationByName("Pais", "inicializa");
	assertEquals(1, api.getAnnotations(api.getOperationByName("Pais", "inicializa")).size());

	Map<String, MElementAnnotation> annotations = api.getAnnotations(api.getOperationByName("Pais", "inicializa"));

	MElementAnnotation parameters = annotations.get("parameters");

//	System.out.println("Pais.inicializa(\n\t" + parameters.getAnnotationValue("nome_") + "\n\t"
//		+ parameters.getAnnotationValue("populacao_") + "\n\t" + parameters.getAnnotationValue("area_") + "\n\t"
//		+ parameters.getAnnotationValue("continente_") + ")");
    }


    // ============================================================================
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

	MLinkObject euro2012_spain = api.createLinkObject("euro2012_spain", "Participacao", Arrays.asList(spain, euro2012));
	MLinkObject spainGame1_iniesta = api.createLinkObject("spainGame1_iniesta", "Membro", Arrays.asList(iniesta, spainGame1));

	MLink spain_real = api.createLink(api.getAssociationByName("Pais_Clube"), Arrays.asList(spain, real));
	MLink spain_barca = api.createLink(api.getAssociationByName("Pais_Clube"), Arrays.asList(spain, barca));

	MLink ronaldo_real = api.createLink(api.getAssociationByName("Clube_Jogador"), Arrays.asList(real, ronaldo));
	MLink iniesta_barca = api.createLink(api.getAssociationByName("Clube_Jogador"), Arrays.asList(barca, iniesta));

	MObject cartao1 = api.createObject("cartao1", "Cartao");
	api.setObjectAttribute(cartao1, "tipo", new EnumValue(api.getEnumByName("TipoCartao"), "Vermelho"));
	api.setObjectAttribute(cartao1, "minuto", IntegerValue.valueOf(57));

	MLink cartao1_iniesta = api.createLink(api.getAssociationByName("Membro_Cartao"),
		Arrays.asList(spainGame1_iniesta, cartao1));

	api.setObjectAttribute(spainGame1_iniesta, "minuto_entrada", IntegerValue.valueOf(0));
	api.setObjectAttribute(spainGame1_iniesta, "minuto_saida", IntegerValue.valueOf(80));

	api.setObjectAttribute(ronaldo, "nome", new StringValue("Cristiano Ronaldo"));
	api.setObjectAttribute(ronaldo, "posicao", new EnumValue(api.getEnumByName("PosicaoJogador"), "Avancado"));

//	System.out.println(api.getObjectAttribute(ronaldo, "nome"));
//	System.out.println(api.getObjectAttribute(ronaldo, "posicao"));

//	for (MObject clube : api.allObjects("Clube"))
//	    System.out.println("CLUBE > " + clube);
//
//	for (MObject jogador : api.allObjects("Jogador"))
//	    System.out.println("JOGADOR > " + jogador);

//	System.out.println("_____________________________________________________");
//	api.command("info vars");
//	System.out.println("_____________________________________________________");

	// Model CopaPaises (19 classes, 17 associations, 14 invariants, 42 operations,
	// 11 pre-/postconditions, 1 state machines)
	// api.statistics(true);

	assertTrue(api.statistics(false).contains("19"));
	assertTrue(api.statistics(false).contains("17"));
	assertTrue(api.statistics(false).contains("14"));
	assertTrue(api.statistics(false).contains("11"));
	assertTrue(api.statistics(false).contains("1"));
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allObjects()}.
     */
    @Test
    public final void testAllObjects()
    {
	this.testStatistics();
	assertEquals(11, api.allObjects().size());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allObjects(java.lang.String)}.
     */
    @Test
    public final void testAllObjectsString()
    {
	this.testStatistics();
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
	this.testStatistics();
	assertEquals(2, api.allObjects(api.getClassByName("Jogador")).size());
	assertEquals(1, api.allObjects(api.getClassByName("Campeonato")).size());
	assertEquals(2, api.allObjects(api.getClassByName("Clube")).size());
	assertEquals(1, api.allObjects(api.getClassByName("Pais")).size());
	assertEquals(1, api.allObjects(api.getClassByName("Participacao")).size());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allLinks()}.
     */
    @Test
    public final void testAllLinks()
    {
	this.testStatistics();
	assertEquals(8, api.allLinks().size());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#allLinkObjects()}.
     */
    @Test
    public final void testAllLinkObjects()
    {
	this.testStatistics();
	assertEquals(3, api.allLinkObjects().size());
    }

    // ================= OBJECTS ========================================

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
	MObject europa = api.createObject("europa", api.getClassByName("Continente"));
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

	api.deleteObject(api.getObjectByName("ronaldo"));
	assertEquals(0, api.allObjects("Jogador").size());

	api.deleteObject(api.getObjectByName("europa"));
	assertEquals(0, api.allObjects("Continente").size());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#setObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute, org.tzi.use.uml.ocl.value.Value)}.
     */
    @Test
    public final void testSetObjectAttributeMObjectMAttributeValue()
    {
	MObject lisboa = api.createObject("lisboa", api.getClassByName("Cidade"));

	api.setObjectAttribute(lisboa, api.getAttributeByName(lisboa, "nome"), new StringValue("Lisboa"));
	api.setObjectAttribute(lisboa, api.getAttributeByName(lisboa, "populacao"), IntegerValue.valueOf(1000000));
	api.setObjectAttribute(lisboa, api.getAttributeByName(lisboa, "capital"), BooleanValue.TRUE);
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#setObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute, org.tzi.use.uml.ocl.value.Value)}.
     */
    @Test
    public final void testSetObjectAttributeMObjectStringValue()
    {
	MObject porto = api.createObject("porto", api.getClassByName("Cidade"));

	api.setObjectAttribute(porto, "nome", new StringValue("Porto"));
	api.setObjectAttribute(porto, "populacao", IntegerValue.valueOf(400000));
	api.setObjectAttribute(porto, "capital", BooleanValue.FALSE);
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute)}.
     */
    @Test
    public final void testGetObjectAttributeMObjectMAttribute()
    {
	testSetObjectAttributeMObjectMAttributeValue();

	MObject lisboa = api.getObjectByName("lisboa");

	assertEquals("Lisboa", api.getObjectAttribute(lisboa, api.getAttributeByName(lisboa, "nome")).toString());
	assertEquals("1000000", api.getObjectAttribute(lisboa, api.getAttributeByName(lisboa, "populacao")).toString());
	assertEquals("true", api.getObjectAttribute(lisboa, api.getAttributeByName(lisboa, "capital")).toString());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute)}.
     */
    @Test
    public final void testGetObjectAttributeMObjectString()
    {
	testSetObjectAttributeMObjectStringValue();

	MObject porto = api.getObjectByName("porto");

	assertEquals("Porto", api.getObjectAttribute(porto, "nome").toString());
	assertEquals("400000", api.getObjectAttribute(porto, "populacao").toString());
	assertEquals("false", api.getObjectAttribute(porto, "capital").toString());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLink(java.lang.String, java.util.List)}.
     */
    @Test
    public final void testCreateLinkStringListOfMObject()
    {
	MObject real = api.createObject("real", "Clube");
	MObject ronaldo = api.createObject("ronaldo", "Jogador");

	MLink real_ronaldo = api.createLink("Clube_Jogador", Arrays.asList("real", "ronaldo"));

	assertTrue(real_ronaldo.linkedObjects().contains(real));
	assertTrue(real_ronaldo.linkedObjects().contains(ronaldo));

	assertSame(real, api.getSourceObject(real_ronaldo));
	assertSame(ronaldo, api.getTargetObject(real_ronaldo));
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLink(org.tzi.use.uml.mm.MAssociation, java.util.List)}.
     */
    @Test
    public final void testCreateLinkMAssociationListOfMObject()
    {
	MObject barca = api.createObject("barca", "Clube");
	MObject iniesta = api.createObject("iniesta", "Jogador");

	MLink barca_iniesta = api.createLink(api.getAssociationByName("Clube_Jogador"), Arrays.asList(barca, iniesta));

	assertTrue(barca_iniesta.linkedObjects().contains(barca));
	assertTrue(barca_iniesta.linkedObjects().contains(iniesta));

	assertSame(barca, api.getSourceObject(barca_iniesta));
	assertSame(iniesta, api.getTargetObject(barca_iniesta));
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteLink(org.tzi.use.uml.mm.MLink)}.
     */
    @Test
    public final void testDeleteLinkMLink()
    {
	testCreateLinkStringListOfMObject();
	testCreateLinkMAssociationListOfMObject();

	MObject real = api.getObjectByName("real");
	MObject ronaldo = api.getObjectByName("ronaldo");
	assertNotNull(real);
	assertNotNull(ronaldo);

	MLink real_ronaldo = api.getLink(api.getAssociationByName("Clube_Jogador"), Arrays.asList(real, ronaldo));
	assertNotNull(real_ronaldo);

	MLink barca_iniesta = api.getLink("Clube_Jogador", Arrays.asList("barca", "iniesta"));

	assertFalse(real_ronaldo.linkedObjects().isEmpty());
	assertTrue(real_ronaldo.linkedObjects().contains(real));
	assertTrue(real_ronaldo.linkedObjects().contains(ronaldo));

	api.deleteLink(real_ronaldo);
	api.deleteLink(barca_iniesta);

	assertNull(api.getLink(api.getAssociationByName("Clube_Jogador"), Arrays.asList(real, ronaldo)));
	assertNull(api.getLink("Clube_Jogador", Arrays.asList("barca", "iniesta")));
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteLink(org.tzi.use.uml.mm.MAssociation, java.util.List)}.
     */
    @Test
    public final void testDeleteLinkMAssociationListofMObject()
    {
	testCreateLinkStringListOfMObject();
	testCreateLinkMAssociationListOfMObject();

	assertEquals(2, api.allLinks("Clube_Jogador").size());

	api.deleteLink(api.getAssociationByName("Clube_Jogador"),
		Arrays.asList(api.getObjectByName("real"), api.getObjectByName("ronaldo")));
	api.deleteLink(api.getAssociationByName("Clube_Jogador"),
		Arrays.asList(api.getObjectByName("barca"), api.getObjectByName("iniesta")));

	assertEquals(0, api.allLinks("Clube_Jogador").size());
    };

    /* ===================== LINK OBJECTS ========================================== */

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.String, java.lang.String, java.util.List)}.
     */
    @Test
    public final void testCreateLinkObjectStringStringListOfMObject()
    {
	MObject spain = api.createObject("spain", "Pais");
	MObject euro2012 = api.createObject("euro2012", "Campeonato");

	MLinkObject euro2012_spain = api.createLinkObject("euro2012_spain", "Participacao", Arrays.asList(spain, euro2012));
	assertEquals("euro2012_spain", euro2012_spain.name());
	assertEquals("Participacao", euro2012_spain.cls().name());
	assertTrue(euro2012_spain.linkedObjects().contains(spain));
	assertTrue(euro2012_spain.linkedObjects().contains(euro2012));
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.String, org.tzi.use.uml.mm.MAssociationClass, java.util.List)}.
     */
    @Test
    public final void testCreateLinkObjectStringMAssociationClassListOfMObject()
    {
	MObject portugal = api.createObject("portugal", "Pais");
	MObject world2018 = api.createObject("world2018", "Campeonato");

	MLinkObject world2018_portugal = api.createLinkObject("world2018_portugal", api.getAssociationClassByName("Participacao"),
		Arrays.asList(portugal, world2018));
	assertEquals("world2018_portugal", world2018_portugal.name());
	assertEquals("Participacao", world2018_portugal.cls().name());
	assertTrue(world2018_portugal.linkedObjects().contains(portugal));
	assertTrue(world2018_portugal.linkedObjects().contains(world2018));
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(org.tzi.use.uml.sys.MLinkObject)}.
     */
    @Test
    public final void testDeleteLinkObjectMLinkObject()
    {
	testCreateLinkObjectStringStringListOfMObject();
	testCreateLinkObjectStringMAssociationClassListOfMObject();

	assertEquals(2, api.allObjects("Participacao").size());

	api.deleteLinkObject(api.getLinkObjectByName("euro2012_spain"));
	api.deleteLinkObject(api.getLinkObjectByName("world2018_portugal"));

	assertEquals(0, api.allLinkObjects("Participacao").size());
	assertEquals(0, api.allObjects("Participacao").size());
	assertEquals(0, api.allLinks("Participacao").size());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(java.lang.String, java.util.List)}.
     */
    @Test
    public final void testDeleteLinkObjectStringList()
    {
	testCreateLinkObjectStringStringListOfMObject();
	testCreateLinkObjectStringMAssociationClassListOfMObject();

	assertEquals(2, api.allLinkObjects("Participacao").size());
	assertEquals(2, api.allObjects("Participacao").size());
	assertEquals(2, api.allLinks("Participacao").size());

	api.deleteLinkObject("Participacao", Arrays.asList("spain", "euro2012"));
	api.deleteLinkObject("Participacao", Arrays.asList("portugal", "world2018"));

	assertEquals(0, api.allLinkObjects("Participacao").size());
	assertEquals(0, api.allObjects("Participacao").size());
	assertEquals(0, api.allLinks("Participacao").size());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(org.tzi.use.uml.sys.MAssociationClass, java.util.List)}.
     */
    @Test
    public final void testDeleteLinkObjectMAssociationClassList()
    {
	testCreateLinkObjectStringStringListOfMObject();
	testCreateLinkObjectStringMAssociationClassListOfMObject();

	assertEquals(2, api.allLinkObjects("Participacao").size());
	assertEquals(2, api.allObjects("Participacao").size());
	assertEquals(2, api.allLinks("Participacao").size());

	MObject euro2012 = api.getObjectByName("euro2012");
	MObject spain = api.getObjectByName("spain");
	MObject world2018 = api.getObjectByName("world2018");
	MObject portugal = api.getObjectByName("portugal");

	api.deleteLinkObject(api.getAssociationClassByName("Participacao"), Arrays.asList(spain, euro2012));
	api.deleteLinkObject(api.getAssociationClassByName("Participacao"), Arrays.asList(portugal, world2018));

	assertEquals(0, api.allLinkObjects("Participacao").size());
	assertEquals(0, api.allObjects("Participacao").size());
	assertEquals(0, api.allLinks("Participacao").size());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#setLinkObjectAttribute(org.tzi.use.uml.sys.MLinkObject, java.lang.String, org.tzi.use.uml.ocl.value.Value)}.
     */
    @Test
    public final void testSetLinkObjectAttributeMLinkObjectStringValue()
    {
	MLinkObject world2018_portugal = api.createLinkObject("world2018_portugal", api.getAssociationClassByName("Participacao"),
		Arrays.asList(api.createObject("portugal", "Pais"), api.createObject("world2018", "Campeonato")));

	assertEquals("false", api.getLinkObjectAttribute(world2018_portugal, "organizador").toString());
	assertEquals("0", api.getLinkObjectAttribute(world2018_portugal, "classificacao").toString());

	api.setLinkObjectAttribute(world2018_portugal, "organizador", BooleanValue.TRUE);
	api.setLinkObjectAttribute(world2018_portugal, "classificacao", IntegerValue.valueOf(13));
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#getLinkObjectAttribute(org.tzi.use.uml.sys.MObject, java.lang.String)}.
     */
    @Test
    public final void testGetLinkObjectAttributeMLinkObjectString()
    {
	this.testSetLinkObjectAttributeMLinkObjectStringValue();

	MLinkObject world2018_portugal = api.getLinkObjectByName("world2018_portugal");

	assertEquals("true", api.getLinkObjectAttribute(world2018_portugal, "organizador").toString());
	assertEquals("13", api.getLinkObjectAttribute(world2018_portugal, "classificacao").toString());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#setLinkObjectAttribute(org.tzi.use.uml.sys.MLinkObject, org.tzi.use.uml.mm.MAttribute, org.tzi.use.uml.ocl.value.Value)}.
     */
    @Test
    public final void testSetLinkObjectAttributeMLinkObjectMAttributeValue()
    {
	MLinkObject euro2012_spain = api.createLinkObject("euro2012_spain", api.getAssociationClassByName("Participacao"),
		Arrays.asList(api.createObject("spain", "Pais"), api.createObject("euro2018", "Campeonato")));

	assertEquals("false", api.getLinkObjectAttribute(euro2012_spain, "organizador").toString());
	assertEquals("0", api.getLinkObjectAttribute(euro2012_spain, "classificacao").toString());

	api.setLinkObjectAttribute(euro2012_spain, api.getAttributeByName(euro2012_spain, "organizador"), BooleanValue.TRUE);
	api.setLinkObjectAttribute(euro2012_spain, api.getAttributeByName(euro2012_spain, "classificacao"),
		IntegerValue.valueOf(10));
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#getLinkObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute)}.
     */
    @Test
    public final void testGetLinkObjectAttributeMLinkObjectMAttribute()
    {
	testSetLinkObjectAttributeMLinkObjectMAttributeValue();

	MLinkObject euro2012_spain = api.getLinkObjectByName("euro2012_spain");

	assertEquals("true",
		api.getLinkObjectAttribute(euro2012_spain, api.getAttributeByName(euro2012_spain, "organizador")).toString());
	assertEquals("10",
		api.getLinkObjectAttribute(euro2012_spain, api.getAttributeByName(euro2012_spain, "classificacao")).toString());
    }

    /* ===================================================================================== */

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#getObjectByName(java.lang.String)}.
     */
    @Test
    public final void testGetObjectByName()
    {
	MObject sporting = api.createObject("sporting", "Clube");
	api.setObjectAttribute(sporting, api.getAttributeByName(sporting, "nome"), new StringValue("Sporting Clube de Portugal"));

	MObject guessWho = api.getObjectByName("sporting");
	assertEquals("Sporting Clube de Portugal",
		api.getObjectAttribute(guessWho, api.getAttributeByName(guessWho, "nome")).toString());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#getClassByName(java.lang.String)}.
     */
    @Test
    public final void testGetClassByName()
    {
	assertEquals("Continente", api.getClassByName("Continente").name());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#getEnumByName(java.lang.String)}.
     */
    @Test
    public final void testGetEnumByName()
    {
	assertEquals("TipoCartao", api.getEnumByName("TipoCartao").name());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#getAssociationClassByName(java.lang.String)}.
     */
    @Test
    public final void testGetAssociationClassByName()
    {
	assertEquals("Equipa", api.getAssociationClassByName("Equipa").name());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#getAssociationByName(java.lang.String)}.
     */
    @Test
    public final void testGetAssociationByName()
    {
	assertEquals("Continente_Pais", api.getAssociationByName("Continente_Pais").name());
    }

    /**
     * Test method for
     * {@link org.quasar.juse.api.JUSE_ProgramingFacade#getAttributeByName(org.tzi.use.uml.sys.MObject, java.lang.String)}.
     */
    @Test
    public final void testGetAttributeByName()
    {
	MObject sporting = api.createObject("sporting", "Clube");
	api.setObjectAttribute(sporting, api.getAttributeByName(sporting, "nome"), new StringValue("Sporting Clube de Portugal"));
	assertEquals("Sporting Clube de Portugal",
		api.getObjectAttribute(sporting, api.getAttributeByName(sporting, "nome")).toString());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#getOperationByName(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testOperationByName()
    {
	MOperation op = api.getOperationByName("Pais", "inicializa");
	assertEquals("Pais", op.cls().name());
	assertEquals("inicializa", op.name());
    }

    /**
     * Test method for {@link org.quasar.juse.api.JUSE_ProgramingFacade#oclEvaluator(java.lang.String)}.
     */
    @Test
    public final void testOclEvaluator()
    {
	testStatistics();

	Value value;

	String OCLexpression = "Jogador.allInstances->size";
	value = api.oclEvaluator(OCLexpression);
	assertEquals(2, api.toObject(value));

	OCLexpression = "spain.cidades->isEmpty()";
	value = api.oclEvaluator(OCLexpression);
	assertEquals(true, api.toObject(value));

	OCLexpression = "ronaldo.nome";
	value = api.oclEvaluator(OCLexpression);
	assertEquals("Cristiano Ronaldo", api.toObject(value));

	OCLexpression = "(spainGame1_iniesta.minuto_saida - spainGame1_iniesta.minuto_entrada)/90";
	value = api.oclEvaluator(OCLexpression);
	assertEquals(80 / 90.0, api.toObject(value));
//	assertEquals(80 / 90.0, (Double) api.toObject(value), 0.001);

	OCLexpression = "Jogador.allInstances->any(nome='Cristiano Ronaldo')";
	value = api.oclEvaluator(OCLexpression);
	assertEquals(api.getObjectByName("ronaldo"), api.toObject(value));

	OCLexpression = "Jogador.allInstances";		// Set
	value = api.oclEvaluator(OCLexpression);
//	System.out.println(">>"+value+"<<");
	assertEquals("HashSet", api.toCollection(value).getClass().getSimpleName());
//	System.out.println(">>"+api.toCollection(value)+"<<");
	
	OCLexpression = "Jogador.allInstances->asOrderedSet()";
	value = api.oclEvaluator(OCLexpression);
//	System.out.println(">>"+value+"<<");
	assertEquals("TreeSet", api.toCollection(value).getClass().getSimpleName());
//	System.out.println(">>"+api.toCollection(value)+"<<");
	
	OCLexpression = "Jogador.allInstances->asBag()";
	value = api.oclEvaluator(OCLexpression);
//	System.out.println(">>"+value+"<<");
	assertEquals("ArrayList", api.toCollection(value).getClass().getSimpleName());
//	System.out.println(">>"+api.toCollection(value)+"<<");
	
	OCLexpression = "Jogador.allInstances->asSequence()";
	value = api.oclEvaluator(OCLexpression);
//	System.out.println(">>"+value+"<<");
	assertEquals("ArrayDeque", api.toCollection(value).getClass().getSimpleName());
//	System.out.println(">>"+api.toCollection(value)+"<<");
    }

}
