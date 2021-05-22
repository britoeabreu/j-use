/***********************************************************
 * Filename: BPMNvalidatorTests.java
 * Created:  28 de Jun de 2012
 ***********************************************************/
package org.quasar.bpmn2.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.quasar.juse.api.JUSE_ProgramingFacade;
import org.quasar.juse.api.implementation.ProgramingFacade;
import org.quasar.toolkit.FileSystemUtilities;

import org.tzi.use.uml.mm.MClassInvariant;

/***********************************************************
 * @author fba
 * 
 ***********************************************************/

public class BPMNvalidatorTests
{
    private final static String MODEL_DIRECTORY = "metamodels/BPMN2";
    private final static String MODEL_FILE = "BPMN2.0n.use";

    private final static String SOIL_DIRECTORY = MODEL_DIRECTORY;
    private final static String SOIL_FILE = "BPMN2.0k.cmd";

    private final static Set<Character> INVARIANT_TYPES = new HashSet<Character>(Arrays.asList('S', 'B', 'I'));

    private static JUSE_ProgramingFacade api = new ProgramingFacade();

    StackTraceElement[] stack = null;
    String methodName = "";

    /***********************************************************
     * @throws java.lang.Exception
     ***********************************************************/
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
	String[] args = { "-oclAnyCollectionsChecks:I" };

	api.initialize(args, MODEL_DIRECTORY);

	api.compileSpecification(MODEL_FILE, true);

	System.out.println("\t# classes = " + api.allClasses().size() + "\t\t# associations = "
		+ api.allAssociations().size() + "\n");

	api.readSOIL(MODEL_DIRECTORY, SOIL_FILE, true);

	System.out
		.println("\t# objects = " + api.allObjects().size() + "\t\t# links = " + api.allLinks().size() + "\n");

	System.out.println("===================================================");
	System.out.println("ALL INVARIANTS [" + api.allInvariants().size() + "]");
	System.out.println("===================================================");

	for (MClassInvariant anInvariant : api.allInvariants())
	    printInvariantInfo(anInvariant);

	for (char type : INVARIANT_TYPES)
	{
	    System.out.println("===================================================");
	    System.out.println("INVARIANTS BY TYPE: " + invariantTypeDescription(type));
	    System.out.println("===================================================");

	    for (MClassInvariant anInvariant : api.allInvariants())
		if (anInvariant.isAnnotated() && invariantIsTypeOf(anInvariant, type))
		    printInvariantInfo(anInvariant);
	}

	System.out.println("===================================================");
	System.out.println("ALL FAILED INVARIANTS [" + getFailedInvariants().size() + "]");
	System.out.println("===================================================");

	Set<MClassInvariant> failed = getFailedInvariants();
	for (MClassInvariant anInvariant : failed)
	    printInvariantInfo(anInvariant);

	for (char type : INVARIANT_TYPES)
	{
	    System.out.println("===================================================");
	    System.out.println("FAILED INVARIANTS BY TYPE: " + invariantTypeDescription(type) + " ["
		    + getFailedInvariants(type).size() + "]");
	    System.out.println("===================================================");

	    Set<MClassInvariant> failedOfType = getFailedInvariants(type);
	    for (MClassInvariant anInvariant : failedOfType)
		printInvariantInfo(anInvariant);

	}

	api.reset();

//	generateUnitTests();
//	generateUnitTestsSoilFiles();
    }

    /***********************************************************
     * @throws java.lang.Exception
     ***********************************************************/
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
	try
	{

	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    }

    /***********************************************************
    * 
    ***********************************************************/
    public static void generateUnitTests()
    {
	for (MClassInvariant anInvariant : api.allInvariants())
	{
	    System.out.println("/***********************************************************");
	    System.out.println("*");
	    System.out.println("***********************************************************/");
	    System.out.println("@Test");
	    System.out.println("public void " + anInvariant.cls().name() + "_" + anInvariant.name() + "()");
	    System.out.println("{");
	    System.out.println("	testStub();");
	    System.out.println("}\n");
	}
    }

    /***********************************************************
    * 
    ***********************************************************/
    public static void generateUnitTestsSoilFiles()
    {    
	for (MClassInvariant anInvariant : api.allInvariants())
	{
	FileSystemUtilities.createDirectory(SOIL_DIRECTORY + "/PASS/");
	FileSystemUtilities.createDirectory(SOIL_DIRECTORY + "/FAIL/");
	
	String invariantName = anInvariant.cls().name() + "_" + anInvariant.name();
	FileSystemUtilities.createFile(SOIL_DIRECTORY + "/PASS/" + invariantName + ".soil");
	FileSystemUtilities.createFile(SOIL_DIRECTORY + "/FAIL/" + invariantName + ".soil");
	}
    }

    /***********************************************************
     * @return
     ***********************************************************/
    private static Set<MClassInvariant> getInvariants(char type)
    {
	Set<MClassInvariant> result = new HashSet<MClassInvariant>();
	for (MClassInvariant anInvariant : api.allInvariants())
	{
	    if (anInvariant.isAnnotated())
		if (invariantType(anInvariant) == type)
		    result.add(anInvariant);
	}
	return result;
    }

    /***********************************************************
     * @return
     ***********************************************************/
    private static Set<MClassInvariant> getFailedInvariants(Collection<MClassInvariant> setOfInvariants)
    {
	Set<MClassInvariant> result = new HashSet<MClassInvariant>();
	for (MClassInvariant anInvariant : setOfInvariants)
	{
	    if (!api.check(anInvariant))
		result.add(anInvariant);
	}
	return result;
    }

    /***********************************************************
     * @return
     ***********************************************************/
    private static Set<MClassInvariant> getFailedInvariants()
    {
	return getFailedInvariants(api.allInvariants());
    }

    /***********************************************************
     * @return
     ***********************************************************/
    private static Set<MClassInvariant> getFailedInvariants(char type)
    {
	return getFailedInvariants(getInvariants(type));
    }

    /***********************************************************
     * @param invariantName
     * @return
     ***********************************************************/
    private static boolean invariantFails(String invariantName)
    {
	return !api.check(api.invariantByName(invariantName));
    }

    /***********************************************************
     * @param anInvariant
     * @param attributeName
     * @return
     ***********************************************************/
    private static String invariantValue(MClassInvariant anInvariant, String attributeName)
    {
	assertTrue(anInvariant.isAnnotated());

	assertEquals(1, anInvariant.getAllAnnotations().size());

	Set<String> keySet = anInvariant.getAllAnnotations().keySet();

	assertEquals(1, keySet.size());

	// System.out.println(">>> keySet.size()=" + keySet.size() + " <<<");

	for (String key : keySet)
	    return anInvariant.getAnnotationValue(key, attributeName);

	return null;
    }

    /***********************************************************
     * @param anInvariant
     * @return
     ***********************************************************/
    private static char invariantType(MClassInvariant anInvariant)
    {
	assertTrue(anInvariant.isAnnotated());

	return invariantValue(anInvariant, "type").charAt(0);
    }

    /***********************************************************
     * @param anInvariant
     * @return
     ***********************************************************/
    private static boolean invariantIsTypeOf(MClassInvariant anInvariant, char type)
    {
	assertTrue(anInvariant.isAnnotated());

	return invariantType(anInvariant) == type;
    }

    /***********************************************************
     * @param anInvariant
     * @return
     ***********************************************************/
    private static String invariantTypeDescription(MClassInvariant anInvariant)
    {
	assertTrue(anInvariant.isAnnotated());

	return invariantTypeDescription(invariantType(anInvariant));
    }

    /***********************************************************
     * @param type
     * @return
     ***********************************************************/
    private static String invariantTypeDescription(char type)
    {
	switch (type)
	{
	case 'S': // Specification
	    return "OMG specification non-conformance";
	case 'B': // Best practice violation
	    return "Best practice violation";
	case 'I': // Information
	    return "Information Message";
	default:
	    return "UNKNOWN MESSAGE TYPE!!!";
	}
    }

    /***********************************************************
     * @param anInvariant
     ***********************************************************/
    private static void printInvariantInfo(MClassInvariant anInvariant)
    {
	System.out.print("\tINVARIANT '" + anInvariant.cls().name() + "::" + anInvariant.name() + "' "
		+ (api.check(anInvariant) ? "[PASS]" : "[FAIL]") + ": ");
	if (anInvariant.getAllAnnotations().isEmpty())
	    System.out.println("(Message missing ...)");
	else
	{
	    System.out.print("(" + invariantTypeDescription(anInvariant) + ")");
	    System.out.print(" > " + invariantValue(anInvariant, "msg"));
	    System.out.println(" [" + invariantValue(anInvariant, "origin") + "]");
	}
    }

    /***********************************************************
     * @throws java.lang.Exception
     ***********************************************************/
    @Before
    public void setUp() throws Exception
    {
    }

    /***********************************************************
     * @throws java.lang.Exception
     ***********************************************************/
    @After
    public void tearDown()
    {
    }
    
    /***********************************************************
    * 
    ***********************************************************/
    private void testStub()
    {
	stack = Thread.currentThread().getStackTrace();
	String invariantName = stack[2].getMethodName();
	MClassInvariant theInvariant = api.invariantByName(invariantName.substring(invariantName.indexOf('_') + 1));

	api.readSOIL(SOIL_DIRECTORY + "/PASS", invariantName + ".soil", false);
//	assertEquals(0, getFailedInvariants('S').size());
//	printInvariantInfo(theInvariant);
	assertTrue(api.check(theInvariant));
	api.reset();
	
	api.readSOIL(SOIL_DIRECTORY + "/FAIL", invariantName + ".soil", false);
//	 assertTrue(getFailedInvariants('S').size() > 0); //
//	printInvariantInfo(theInvariant);
//	assertFalse(api.check(theInvariant));
	api.reset();
    }
    

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_catchingErrorEventCanOnlyBeAttachedToActivityBoundary()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_throwingMessageEventHasOutgoingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Message_allowedConnectors()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SequenceFlow_sourceMustBeExclusiveInclusiveGateway()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_explicitStartEvRequiresNoActivOrGatWithoutInSeqFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_throwingEscalationIntermediateEventIsNonInterrupting()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void BoundaryEvent_noIncomingSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SubProcess_selfContainedData()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_startEventsHaveNoIncomingSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void StartEvent_interruptingTypesRestrictedToEventSubProcess()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_multipleIncomingMesgFlowHasMultipleType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SubProcess_onlyOneNoneStartEventInEmbeddedSubProcess()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Process_catchingSignalEventHasThrowEventWithSameName()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_namedCatchingErrorEventHasThrowEventWithSameName()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Gateway_multipleExitsDataBasedExclusiveGatewayMustHaveConditions()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowNode_exclusivePathsMergingIntermediateByGateway()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_namesOfCatchAndThrowLinkEventMustMatch()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SequenceFlow_sourceCannotBeTarget()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void StartEvent_cannotHaveIncomingButHaveOutgoingSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SequenceFlow_sourceMustNotBeEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_existsCatchSignalEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void BoundaryEvent_oneOutgoingSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Gateway_diverging()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_bp_limitOfFlowNodesPerDiagram()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_outgoingMessageFlowHasMessageMultipleType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void IntermediateThrowEvent_mandatorySequenceFlowTypedEvents()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void EventBasedGateway_bp_useTimerIntermediateEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void MessageFlow_doNotConnectToWhiteBox()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SubProcess_messageFlowConsistency()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_nonInterruptingStartEventsHostedOnlyByEventSubProcess()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowNode_mandatoryIncomingAndOutgoingSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void BoundaryEvent_allowedEventType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void EndEvent_allowedEventType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void IntermediateCatchEvent_allowedEventType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void IntermediateThrowEvent_allowedEventType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Process_publicProcessIsNotExecutable()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Gateway_mergingParallelGwayIsPrecededBySplitWithParallelGway()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void MessageFlow_allowedTarget()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Gateway_mergingExclusiveGatewayIsPrecededBySplitWithExclusiveGateway()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_bp_activitiesMustBeName()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void StartEvent_topLevelProcessHasRestrictedTypeOfStartEvents()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_bp_useSendReceiveTaskOrThrowCatchMesInterEvents()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void MessageFlow_mustCrossBordersOfPools()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void StartEvent_compensationStartEventInSubProcess()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void EventBasedGateway_receiveTasksWithoutMessageEventVV()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_catchErrorEventTriggerExceptionFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Event_bp_splitingEventShouldNotOccurAndOneTargetIsNeeded()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_throwingErrorEventHasACatchErrorEventWithSameNameXORUnnamed()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SendTask_bp_hasOutgoingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SequenceFlow_mustNotCrossBordersOfPools()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Activity_compensationActivityNoIncomingAndOutgoing()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void BoundaryEvent_nonInterruptingEventPathMergedByAnInclusiveGateway()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_escalationEventIsIntermediateOrEndEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ManualTask_mustNotHaveIncomingOutgoingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ScriptTask_mustNotHaveIncomingOutgoingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Transaction_multiPathsRequireTerminateEndEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_errorEventIsAlwaysEndEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_interrCatchingEscalationMustNotMixNamedAndUnnamedEvents()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void MessageFlow_bp_hasMessageName()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_bp_noUnnamedSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Association_connectedToTextAnnotation()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void EventBasedGateway_receiveTasksWithoutBoundaryEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void IntermediateCatchEvent_bp_incomingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void StartEvent_bp_incomingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ReceiveTask_mustNotHaveOutgoingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SubProcess_noIncomingAndOutgoingSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_throwingCompensationEventMatchOneOfActivitiesToBeCancelled()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SubProcess_noMoreThanOneNoneStartEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_nameOfThrowLinkEventsMustBeDefined()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SubProcess_eventSubProcessesDoNotAllowUntypedStartEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void DataObject_connectedDataAssociation()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void DataStore_connectedDataAssociation()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void EventBasedGateway_moreThanTwoSequenceFlows()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void TextAnnotation_connectToAssociation()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_bp_unnamedSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_endEventsHaveNoOutgoingSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_implicitStartAndEndEventsRequiresSurrogateActivitiesOrGateways()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_bp_subProcessHasMoreThanOneEndEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_throwingLinkEventHasOnlyIncomingSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void BoundaryEvent_interruptingEventPathMergedByAnExclusiveGateway()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Association_connectTwoTextAnnotation()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_intermediateEventsMustHaveIncAndOutSeqFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void IntermediateThrowEvent_mandatorySequence()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_nameOfCatchLinkEventsMustBeUnique()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void MessageFlow_allowedSource()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_catchEscalationEventTriggerExceptionFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Collaboration_aCollaborationCanOnlyHaveOneInplicitProcess()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void StartEvent_escalationStartEventRestrictedToEventSubProcess()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Process_bp_activitiesNameMustBeUnique()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_catchingMessageEventHasIncomingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_explicitNodeWithoutIncSeqFlowRequiresNoStartEv()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void InclusiveGateway_bp_matchMergingAndSplitting()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ParallelGateway_bp_matchMergingAndSplitting()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_multipleOutgoingMesgFlowHasMultipleType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SubProcess_bp_matchGatewayName()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Transaction_trowingCompensatingEventsNotAllowed()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void StartEvent_messageStartEventHasIncomingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ReceiveTask_bp_hasIncomingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SubProcess_eventSubProcessStartEventAllowed()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_nameOfCatchLinkEventsMustBeDefined()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void EventBasedGateway_targetMustBeSpecific()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_catchEscalationEventIsInterruptingEventRequiresEndEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void StartEvent_errorStartEventRestrictedToEventSubProcess()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Activity_mustHaveIncomingSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void BoundaryEvent_transactionCanOnlyHaveCancelEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Gateway_converging()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SequenceFlow_sourceMustNotBeParalellComplexGateway()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_incomingMessageFlowHasMessageMultipleType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void IntermediateThrowEvent_bp_outgoingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void DataObjectReference_dataObjectAtSameContainerOrParent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_incomingMessageFlowNotAllowedMessageType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_implicitStartEventsRequiresImplicitEndEvents()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CallActivity_callableElementIsReusable()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SequenceFlow_conditionalCannotBeUsedIfOnlyOne()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_namedCatchingEscalationEventHasThrowEventWithSameName()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Gateway_mergingExclusiveGatewayExclusiveTokens()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Process_noMoreThanOneUntypedStartEventInProcess()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_bp_useExplicitStartAndEndEvents()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void EventBasedGateway_targetElementsMustHaveOnlyOneIncomingSeqFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_bp_gatewayWithSeveralInputsAndSeveralOutputs()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_nonInterrCatchingEscalationMustNotMixNamedAndUnnamedEvents()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ParallelGateway_bp_preventDeadlock()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_catchEscalationEventIsBoundaryEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_compensationActivityMustBeCalled()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SequenceFlow_sourceMustNotBeEventBasedGateway()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_catchingErrorMustNotMixNamedAndUnnamedEvents()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SendTask_mustNotHaveIncomingMessageFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Gateway_mergeOrFork()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_bp_matchEndEventToGatewayName()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void StartEvent_eventSubProcessAllowedEventType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ExclusiveGateway_bp_useDefaultCondition()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowNode_bp_useDefaultCondition()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void InclusiveGateway_bp_useDefaultCondition()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ManualTask_multipleIncomingInActivityMustHaveConditions()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void ThrowEvent_throwingEscalationEndEventIsInterrupting()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_catchingCompensationEventCanOnlyBeAttachedToActivityBoundary()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_mandatoryIncomingOrOutgoingSequenceFlow()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void FlowElementsContainer_bp_useOnlyOneStartEvent()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void IntermediateCatchEvent_bp_shouldHaveName()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void IntermediateThrowEvent_bp_shouldHaveName()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void StartEvent_bp_shouldHaveName()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_outgoingMessageFlowNotAllowedMessageType()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void SequenceFlow_cannotCrossContainerBoundaries()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void Activity_multipleExitsActivityMustHaveConditions()
    {
	testStub();
    }

    /***********************************************************
    *
    ***********************************************************/
    @Test
    public void CatchEvent_catchingLinkEventHasOnlyOutgoingSequenceFlow()
    {
	testStub();
    }

} // end class BPMNvalidatorTests
