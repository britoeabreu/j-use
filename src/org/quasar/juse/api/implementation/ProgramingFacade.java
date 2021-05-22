/*
 * J-USE - Java prototyping for the UML based specification environment (USE)
 * Copyright (C) 2012 Fernando Brito e Abrey, QUASAR research group
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.quasar.juse.api.implementation;

import org.quasar.juse.api.JUSE_ProgramingFacade;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.analysis.coverage.CoverageAnalyzer;
import org.tzi.use.analysis.coverage.CoverageData;

import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MElementAnnotation;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.mm.statemachines.MRegion;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.type.BagType;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.type.OrderedSetType;
import org.tzi.use.uml.ocl.type.SequenceType;
import org.tzi.use.uml.ocl.type.SetType;
import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.RealValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.OrderedSetValue;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.Log;

/***********************************************************
 * @author fba
 * 19 April 2012 - Original version 
 * 16 October 2018 - Extended and commented version
 * 17 March 2019 - New operations added due to the development of the test suite 
 ***********************************************************/

public class ProgramingFacade extends BasicFacade implements JUSE_ProgramingFacade
{

	public ProgramingFacade()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#oclEvaluator(java.lang.String)
	 */
	public Value oclEvaluator(String expression)
	{
		assert expression.length() > 0;

		Log.setTrace(false);

		Log.setDebug(false);

		Expression expr = OCLCompiler.compileExpression(getSystem().model(), getSystem().state(),
				new ByteArrayInputStream(expression.getBytes()), "<input>", new PrintWriter(System.err), getSystem().varBindings());

		// compile errors?
		if (expr == null)
			return UndefinedValue.instance;

		// // evaluate it with current system state (non verbose)
		Evaluator evaluator = new Evaluator(false);

		Value val = null;
		try
		{
			val = evaluator.eval(expr, getSystem().state(), getSystem().varBindings(), null);
		} catch (MultiplicityViolationException e)
		{
			System.out.println("-> " + "Could not evaluate. " + e.getMessage());
		}
		return val;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#toObject(org.tzi.use.uml.ocl.value.Value)
	 */
	public Object toObject(Value v)
	{
		if (v.isInteger())
			return new Integer(((IntegerValue) v).value());
		
		if (v.isReal())
			return new Double(((RealValue) v).value());
		
		if (v.isBoolean())
			return new Boolean(((BooleanValue) v).value());
		
		if (v instanceof StringValue)
			return new String(((StringValue) v).value());
			
		return null;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#toCollection(org.tzi.use.uml.ocl.value.Value)
	 */
	public Collection<Object> toCollection(Value v)
	{
/*
		if (oclType.isTypeOfOrderedSet())
			return "TreeSet<" + javaImplementationType(((OrderedSetType) oclType).elemType()) + ">";

		if (oclType.isTypeOfSet())
			return "HashSet<" + javaImplementationType(((SetType) oclType).elemType()) + ">";
		
		if (oclType.isTypeOfSequence())
			return "ArrayDeque<" + javaImplementationType(((SequenceType) oclType).elemType()) + ">";
		
		if (oclType.isTypeOfBag())
			return "ArrayList<" + javaImplementationType(((BagType) oclType).elemType()) + ">";
		
		if (oclType.isKindOfTupleType(VoidHandling.INCLUDE_VOID))
			return javaTupleType((TupleType) oclType);

		
		if (v.isOrderedSet())
			return ((OrderedSetValue)v).collection();	
		
		if (v.isCollection())
			return new Collection<Object>(((IntegerValue) v).value());

		if (v.isSet())
			return new Set<?>(((IntegerValue) v).value());
		
		if (v.isBag())
			return new List<?>(((RealValue) v).value()); */
			
		return null;
	}

	// ------------------ MODELING ELEMENTS
	// -----------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#statistics()
	 */
	public String statistics(boolean verbose)
	{
		String result = "";
		int transitions = 0;
		int states = 0;

		if (verbose)
			result += getSystem().model().getStats();
		else
		{
			result += getSystem().model().classes().size() + "\t" + getSystem().model().associations().size() + "\t"
					+ getSystem().model().classInvariants().size() + "\t" + getSystem().model().prePostConditions().size() + "\t"
					+ allStateMachines().size() + "\t";

			for (MStateMachine sm : allStateMachines())
				for (MRegion reg : sm.getRegions())
				{
					states += reg.getSubvertices().size();
					transitions += reg.getTransitions().size();
				}

			result += states + "\t" + transitions;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#getAnnotations(org.tzi.use.uml.mm
	 * .MModelElement)
	 */
	public Map<String, MElementAnnotation> getAnnotations(MModelElement element)
	{
		assert element != null;

		return element.getAllAnnotations();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#printAnnotations(org.tzi.use.uml.
	 * mm.MModelElement)
	 */
	public void printAnnotations(String context, MModelElement element)
	{
		assert element != null;

		for (String key : element.getAllAnnotations().keySet())
		{
			System.out.println("[" + context + "]");
			MElementAnnotation annotation = element.getAnnotation(key);
			for (String field : annotation.getValues().keySet())
				System.out.println("\t" + annotation.getName() + "." + field + " -> " + annotation.getAnnotationValue(field));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allClasses()
	 */
	public Set<MClass> allClasses()
	{
		return new HashSet<MClass>(getSystem().model().classes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#classByName(java.lang.String)
	 */
	public MClass classByName(String className)
	{
		assert className != null;

		return getSystem().model().getClass(className);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#getAttributes(org.tzi.use.uml.sys
	 * .MClass)
	 */
	public List<MAttribute> getAttributes(MClass theClass)
	{
		return new ArrayList<MAttribute>(theClass.allAttributes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#MOperation(org.tzi.use.uml.sys.
	 * MClass)
	 */
	public List<MOperation> getOperations(MClass theClass)
	{
		return new ArrayList<MOperation>(theClass.allOperations());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttribute(org.tzi.use.
	 * uml.sys.MObject, org.tzi.use.uml.mm.MAttribute)
	 */
	public Value getObjectAttribute(MObject theObject, MAttribute theAttribute)
	{
		assert theObject != null;
		assert theAttribute != null;
		
		return theObject.state(getSystem().state()).attributeValue(theAttribute);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttribute(org.tzi.use.
	 * uml.sys.MObject, org.tzi.use.uml.mm.MAttribute)
	 */
	public Value getObjectAttribute(MObject theObject, String theAttribute)
	{
		return getObjectAttribute(theObject, attributeByName(theObject, theAttribute));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#operationByName(java.lang.String,
	 * java.lang.String)
	 */
	public MOperation operationByName(String className, String operationName)
	{
		assert className != null;
		assert operationName != null;

		MClass theClass = classByName(className);

		return theClass == null ? null : theClass.operation(operationName, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allAssociations()
	 */
	public Set<MAssociation> allAssociations()
	{
		return new HashSet<MAssociation>(getSystem().model().associations());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#associationByName(java.lang.String)
	 */
	public MAssociation associationByName(String associationName)
	{
		assert associationName != null;

		return getSystem().model().getAssociation(associationName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allAssociationClasses()
	 */
	public Set<MAssociationClass> allAssociationClasses()
	{
		return new HashSet<MAssociationClass>(getSystem().model().getAssociationClassesOnly());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#associationClassByName(java.lang.
	 * String)
	 */
	public MAssociationClass associationClassByName(String associationClassName)
	{
		assert associationClassName != null;

		return getSystem().model().getAssociationClass(associationClassName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allEnumTypes()
	 */
	public Set<EnumType> allEnumTypes()
	{
		return getSystem().model().enumTypes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#enumByName(java.lang.String)
	 */
	public EnumType enumByName(String enumName)
	{
		assert enumName != null;

		return getSystem().model().enumType(enumName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allStateMachines()
	 */
	public Set<MStateMachine> allStateMachines()
	{
		Set<MStateMachine> result = new HashSet<MStateMachine>();
		for (final MClass cls : getSystem().model().classes())
			for (final MStateMachine sm : cls.getOwnedProtocolStateMachines())
				result.add(sm);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#stateMachineByName(java.lang.
	 * String)
	 */
	public MStateMachine stateMachineByName(String stateMachineName)
	{
		assert stateMachineName != null;

		for (MStateMachine sm : allStateMachines())
			if (sm.name().toUpperCase().equals(stateMachineName.toUpperCase()))
				return sm;
		return null;
	}

	// ------------------ DESIGN BY CONTRACT
	// ----------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allInvariants()
	 */
	public Set<MClassInvariant> allInvariants()
	{
		return new HashSet<MClassInvariant>(getSystem().model().classInvariants());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#invariantByName(java.lang.String)
	 */
	public MClassInvariant invariantByName(String invariantName)
	{
		assert invariantName != null;

		for (MClassInvariant inv : getSystem().model().classInvariants())
			if (inv.name().toUpperCase().equals(invariantName.toUpperCase()))
				return inv;
		return null;
		// return getSystem().model().getClassInvariant(invariantName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allPreConditions()
	 */
	public Set<MPrePostCondition> allPreConditions()
	{
		Set<MPrePostCondition> result = new HashSet<MPrePostCondition>();
		for (MPrePostCondition assertion : getSystem().model().prePostConditions())
			if (assertion.isPre())
				result.add(assertion);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allPostConditions()
	 */
	public Set<MPrePostCondition> allPostConditions()
	{
		Set<MPrePostCondition> result = new HashSet<MPrePostCondition>();
		for (MPrePostCondition assertion : getSystem().model().prePostConditions())
			if (!assertion.isPre())
				result.add(assertion);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#check(org.tzi.use.uml.mm.
	 * MClassInvariant)
	 */
	public boolean check(MClassInvariant anInvariant) throws RuntimeException
	{
		assert anInvariant != null;

		EvalContext context = new EvalContext(null, getSystem().state(), getSystem().varBindings(), null, "\t");

		return ((BooleanValue) anInvariant.expandedExpression().eval(context)).isTrue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#checkStructure()
	 */
	public boolean checkStructure()
	{
		System.setOut(new java.io.PrintStream(new OutputStream()
		{
			public void write(int b)
			{
			}
		}));

		return getSystem().state().checkStructure(new PrintWriter(System.out));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#associationCoverage()
	 */
	public double associationCoverage()
	{
		MModel model = getSystem().model();

		Map<MModelElement, CoverageData> data = CoverageAnalyzer.calculateTotalCoverage(model, true);
		// The 2nd argument, when true, means that operation expressions will also
		// be considered

		// Map<MAssociation, Integer>coverageAssociation =
		// coverageMap.get(getSystem().model()).getAssociationCoverage();

		CoverageData theData = data.get(model);

		Map<MAssociation, Integer> associationCoverage = theData.getAssociationCoverage();

		int assocSize = 0;
		int assocCovered = 0;

		for (MAssociation assoc : model.associations())
		{
			// System.out.println("\t"+assoc.name()+ "\t" +
			// associationCoverage.get(assoc));
			assocSize++;
			if (associationCoverage.get(assoc) != null)
				assocCovered++;
		}

		return ((double) assocCovered) / assocSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#associationEndCoverage()
	 */
	public double associationEndCoverage()
	{
		MModel model = getSystem().model();

		Map<MModelElement, CoverageData> data = CoverageAnalyzer.calculateTotalCoverage(model, true);
		// The 2nd argument, when true, means that operation expressions will also
		// be considered

		// Map<MAssociation, Integer>coverageAssociation =
		// coverageMap.get(getSystem().model()).getAssociationCoverage();

		CoverageData theData = data.get(model);

		Map<MAssociationEnd, Integer> associationEndCoverage = theData.getAssociationEndCoverage();

		int aendSize = 0;
		int aendCovered = 0;

		for (MAssociation assoc : model.associations())
			for (MAssociationEnd aend : assoc.associationEnds())
			{
				// System.out.println("\t"+aend.nameAsRolename()+ "\t" +
				// associationEndCoverage.get(aend));
				aendSize++;
				if (associationEndCoverage.get(aend) != null)
					aendCovered++;
			}

		return ((double) aendCovered) / aendSize;
	}

	// ---------------------------- OBJECTS (CRUD)
	// ---------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#createObject(java.lang.String,
	 * java.lang.String)
	 */
	public MObject createObject(String objectId, String className)
	{
		assert objectId != null;
		assert className != null;

		return createObject(objectId, classByName(className));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#createObject(java.lang.String,
	 * org.tzi.use.uml.mm.MClass)
	 */
	public MObject createObject(String objectId, MClass theClass)
	{
		assert objectId != null;
		assert theClass != null;

		MObject result = null;
		try
		{
			result = getSystem().state().createObject(theClass, objectId);
		} catch (MSystemException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#objectByName(java.lang.String)
	 */
	public MObject objectByName(String objectId)
	{
		assert objectId != null;

		return getSystem().state().objectByName(objectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allInstances()
	 */
	public Set<MObject> allObjects()
	{
		return getSystem().state().allObjects();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#allInstances(java.lang.String)
	 */
	public Set<MObject> allObjects(String className)
	{
		assert className != null;

		return allObjects(classByName(className));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#allInstances(org.tzi.use.uml.mm.
	 * MClass)
	 */
	public Set<MObject> allObjects(MClass theClass)
	{
		assert theClass != null;

		return getSystem().state().objectsOfClass(theClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#setObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute,
	 * org.tzi.use.uml.ocl.value.Value)
	 */
	public void setObjectAttribute(MObject theObject, MAttribute theAttribute, Value attributeValue)
	{
		assert theObject != null;
		assert theAttribute != null;

		theObject.state(getSystem().state()).setAttributeValue(theAttribute, attributeValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#setObjectAttribute(org.tzi.use.uml.sys.MObject, java.lang.String,
	 * org.tzi.use.uml.ocl.value.Value)
	 */
	public void setObjectAttribute(MObject theObject, String theAttribute, Value attributeValue)
	{
		setObjectAttribute(theObject, attributeByName(theObject, theAttribute), attributeValue);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#attributeByName(org.tzi.use.uml.sys.MObject, java.lang.String)
	 */
	public MAttribute attributeByName(MObject theObject, String attributeName)
	{
		assert theObject != null;

		return theObject.cls().attribute(attributeName, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#deleteObject(org.tzi.use.uml.sys.
	 * MObject)
	 */
	public void deleteObject(MObject theObject)
	{
		assert theObject != null;

		getSystem().state().deleteObject(theObject);
	}

	// ------------------ LINK OBJECTS (CRUD)
	// ---------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.
	 * String, java.lang.String, java.util.List)
	 */
	public MLinkObject createLinkObject(String objectId, String associativeClassName, List<MObject> members)
	{
		return createLinkObject(objectId, associationClassByName(associativeClassName), members);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.
	 * String, org.tzi.use.uml.mm.MAssociationClass, java.util.List)
	 */
	public MLinkObject createLinkObject(String objectId, MAssociationClass theAssociativeClass, List<MObject> members)
	{
		assert theAssociativeClass != null;
		assert members != null;
		assert members.size() == 2;

		MLinkObject result = null;
		try
		{
			result = getSystem().state().createLinkObject(theAssociativeClass, objectId, members, null);
		} catch (MSystemException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#linkObjectByName(java.lang.
	 * String)
	 */
	public MLinkObject linkObjectByName(String linkObjectId)
	{
		assert linkObjectId != null;

		MObject obj = objectByName(linkObjectId);
		return obj instanceof MLinkObject ? (MLinkObject) obj : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allLinkObjects()
	 */
	public Set<MLinkObject> allLinkObjects()
	{
		Set<MLinkObject> result = new HashSet<MLinkObject>();
		for (MAssociationClass ac : allAssociationClasses())
			for (MObject lo : getSystem().state().objectsOfClass(ac))
				result.add((MLinkObject) lo);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#allLinkObjects(java.lang.String)
	 */
	public Set<MLinkObject> allLinkObjects(String associationClassName)
	{
		assert associationClassName != null;

		return allLinkObjects(associationClassByName(associationClassName));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#allLinkObjects(org.tzi.use.uml.mm
	 * .MAssociationClass)
	 */
	@SuppressWarnings("unchecked")
	public Set<MLinkObject> allLinkObjects(MAssociationClass theAssociationClass)
	{
		assert theAssociationClass != null;

		return (Set<MLinkObject>) ((Set<?>) allObjects(theAssociationClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#setLinkObjectAttribute(org.tzi.
	 * use.uml.sys.MLinkObject, org.tzi.use.uml.mm.MAttribute,
	 * org.tzi.use.uml.ocl.value.Value)
	 */
	public void setLinkObjectAttribute(MLinkObject theLinkObject, MAttribute theAttribute, Value attributeValue)
	{
		this.setObjectAttribute(theLinkObject, theAttribute, attributeValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#setLinkObjectAttribute(org.tzi.use.uml.sys.MLinkObject, java.lang.String, 
	 * org.tzi.use.uml.ocl.value.Value)
	 */
	public void setLinkObjectAttribute(MLinkObject theLinkObject, String theAttribute, Value attributeValue)
	{
		this.setObjectAttribute(theLinkObject, theAttribute, attributeValue);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#getLinkObjectAttribute(org.tzi.
	 * use.uml.sys.MLinkObject, org.tzi.use.uml.mm.MAttribute)
	 */
	public Value getLinkObjectAttribute(MLinkObject theLinkObject, MAttribute theAttribute)
	{
		return this.getObjectAttribute(theLinkObject, theAttribute);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#getLinkObjectAttribute(org.tzi.use.uml.sys.MLinkObject, java.lang.String)
	 */
	public Value getLinkObjectAttribute(MLinkObject theLinkObject, String theAttribute)
	{
		return this.getObjectAttribute(theLinkObject, theAttribute);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(org.tzi.use.uml.
	 * sys.MLinkObject)
	 */
	public void deleteLinkObject(MLinkObject theLinkObject)
	{
		this.deleteObject(theLinkObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(org.tzi.use.uml.
	 * mm.MAssociationClass, java.util.List)
	 */
	public void deleteLinkObject(MAssociationClass theAssociationClass, List<MObject> members)
	{
		this.deleteLink(theAssociationClass, members);
	}

	// ------------------ LINKS (CRD)
	// ----------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#createLink(java.lang.String,
	 * java.util.List)
	 */
	public MLink createLink(String theAssociation, List<MObject> members)
	{
		return createLink(associationByName(theAssociation), members);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#createLink(org.tzi.use.uml.mm.
	 * MAssociation, java.util.List)
	 */
	public MLink createLink(MAssociation theAssociation, List<MObject> members)
	{
		assert theAssociation != null;
		assert members != null;
		assert members.size() == 2;

		MLink result = null;
		try
		{
			result = getSystem().state().createLink(theAssociation, members, null);
		} catch (MSystemException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allLinks()
	 */
	public Collection<MLink> allLinks()
	{
		return getSystem().state().allLinks();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allLinks(java.lang.String)
	 */
	public Collection<MLink> allLinks(String associationName)
	{
		return allLinks(associationByName(associationName));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allLinks(org.tzi.use.uml.mm.
	 * MAssociation)
	 */
	public Collection<MLink> allLinks(MAssociation theAssociation)
	{
		return getSystem().state().linksOfAssociation(theAssociation).links();
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#getSourceObject(org.tzi.use.uml.mm.
	 * MLink)
	 */
	public MObject getSourceObject(MLink theLink)
	{
		return theLink.linkedObjectsAsArray()[0];
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#getTargetObject(org.tzi.use.uml.mm.
	 * MLink)
	 */
	public MObject getTargetObject(MLink theLink)
	{
		return theLink.linkedObjectsAsArray()[1];
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#deleteLink(org.tzi.use.uml.sys.
	 * MLink)
	 */
	public void deleteLink(MLink theLink)
	{
		assert theLink != null;

		try
		{
			getSystem().state().deleteLink(theLink);
		} catch (MSystemException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.juse.api.JUSE_ProgramingFacade#deleteLink(org.tzi.use.uml.mm.
	 * MAssociation, java.util.List)
	 */
	public void deleteLink(MAssociation theAssociation, List<MObject> members)
	{
		assert theAssociation != null;
		assert members != null;
		assert members.size() == 2;

		try
		{
			getSystem().state().deleteLink(theAssociation, members, null);
		} catch (MSystemException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#reset()
	 */
	public void reset()
	{
		getSystem().reset();
	}

}