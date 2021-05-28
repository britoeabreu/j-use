/*
 * J-USE - Java prototyping for the UML based specification environment (USE)
 * Copyright (C) 2021 Fernando Brito e Abreu, QUASAR research group
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.quasar.juse.api.implementation;

import org.quasar.juse.api.JUSE_ProgramingFacade;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MElementAnnotation;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.mm.statemachines.MRegion;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
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
import org.tzi.use.uml.ocl.value.ObjectValue;

import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.OrderedSetValue;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.CollectionValue;

import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.Log;

/**********************************************************************************
 * @author fba
 * @version 19 April 2012 - Original version
 * @version October 2018 - Extended and commented version
 * @version March 2019 - New operations added due to the development of the test suite
 * @version 23 May 2021 - Naming conventions enforced, added link retrieval operations and programming by contract operations moved
 *          to subclass
 *********************************************************************************/

public class ProgramingFacade extends BasicFacade implements JUSE_ProgramingFacade
{

    /**
     * Basic constructor of ProgramingFacade
     */
    public ProgramingFacade()
    {
	super();
    }

    // ------------------ OCL EXPRESSIONS -----------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#oclEvaluator(java.lang.String)
     */
    public Value oclEvaluator(String expression)
    {
	assert expression.length() > 0;

	Log.setTrace(false);

	Log.setDebug(false);

	Expression expr = OCLCompiler.compileExpression(this.getSystem().model(), this.getSystem().state(),
		new ByteArrayInputStream(expression.getBytes()), "<input>", new PrintWriter(System.err),
		this.getSystem().varBindings());

	// compile errors?
	if (expr == null)
	    return UndefinedValue.instance;

	// // evaluate it with current system state (non verbose)
	Evaluator evaluator = new Evaluator(false);

	Value val = null;
	try
	{
	    val = evaluator.eval(expr, this.getSystem().state(), this.getSystem().varBindings(), null);
	} catch (MultiplicityViolationException e)
	{
	    System.out.println("-> " + "Could not evaluate." + e.getMessage());
	}
	return val;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#toObject(org.tzi.use.uml.ocl.value.Value)
     */
    public Object toObject(Value v)
    {
	assert v != null;

	if (v.isObject())
	    return ((ObjectValue) v).value();

	if (v.isInteger())
	    return Integer.valueOf(((IntegerValue) v).value());

	if (v.isReal())
	    return Double.valueOf(((RealValue) v).value());

	if (v.isBoolean())
	    return Boolean.valueOf(((BooleanValue) v).value());

	if (v instanceof StringValue)
	    return new String(((StringValue) v).value());

	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#toCollection(org.tzi.use.uml.ocl.value.Value)
     */
    public Collection<Object> toCollection(Value v)
    {
	assert v != null;
	assert v.isCollection();

	if (v.isOrderedSet())
	    return new TreeSet<Object>(((OrderedSetValue) v).collection());

	if (v.isSet())
	    return new HashSet<Object>(((SetValue) v).collection());

	if (v.isSequence())
	    return new ArrayDeque<Object>(((SequenceValue) v).collection());

	if (v.isBag())
	    return new ArrayList<Object>(((BagValue) v).collection());
//	  
//	  if (oclType.isKindOfTupleType(VoidHandling.INCLUDE_VOID)) return
//	  javaTupleType((TupleType) oclType);
//	  
	return null;
    }

    // ------------------ MODELING ELEMENTS -----------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allClasses()
     */
    public Collection<MClass> allClasses()
    {
	return this.getSystem().model().classes();
//	return new HashSet<MClass>(getSystem().model().classes());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getClassByName(java.lang.String)
     */
    public MClass getClassByName(String className)
    {
	assert className != null;

	return this.getSystem().model().getClass(className);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allClassAttributes(org.tzi.use.uml.sys.MClass)
     */
    public Collection<MAttribute> allClassAttributes(MClass theClass)
    {
	assert theClass != null;

	return theClass.allAttributes();
//	return new ArrayList<MAttribute>(theClass.allAttributes());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allClassAttributes(java.lang.String)
     */
    public Collection<MAttribute> allClassAttributes(String theClass)
    {
	return this.allClassAttributes(this.getClassByName(theClass));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allClassOperations(org.tzi.use.uml.sys.MClass)
     */
    public Collection<MOperation> allClassOperations(MClass theClass)
    {
	assert theClass != null;

	return theClass.allOperations();
//	return new ArrayList<MOperation>(theClass.allOperations());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allClassOperations(java.lang.String)
     */
    public Collection<MOperation> allClassOperations(String theClass)
    {
	return this.allClassOperations(this.getClassByName(theClass));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttributeValue(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute)
     */
    public Value getObjectAttributeValue(MObject theObject, MAttribute theAttribute)
    {
	assert theObject != null;
	assert theAttribute != null;

	return theObject.state(this.getSystem().state()).attributeValue(theAttribute);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttributeValue(org.tzi.use.uml.sys.MObject, java.lang.String)
     */
    public Value getObjectAttributeValue(MObject theObject, String theAttribute)
    {
	return this.getObjectAttributeValue(theObject, this.getAttributeByName(theObject, theAttribute));
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute)
     */
    public Object getObjectAttribute(MObject theObject, MAttribute theAttribute)
    {
	return this.toObject(this.getObjectAttributeValue(theObject, theAttribute));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttribute(org.tzi.use.uml.sys.MObject, java.lang.String)
     */
    public Object getObjectAttribute(MObject theObject, String theAttribute)
    {
	return this.toObject(this.getObjectAttributeValue(theObject, theAttribute));
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#operationByName(java.lang.String, java.lang.String)
     */
    public MOperation getOperationByName(String className, String operationName)
    {
	assert className != null;
	assert operationName != null;

	MClass theClass = this.getClassByName(className);

	return theClass == null ? null : theClass.operation(operationName, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allAssociations()
     */
    public Collection<MAssociation> allAssociations()
    {
	return this.getSystem().model().associations();
//	return new HashSet<MAssociation>(getSystem().model().associations());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.use.api.USEfacade#associationByName(java.lang.String)
     */
    public MAssociation getAssociationByName(String associationName)
    {
	assert associationName != null;

	return this.getSystem().model().getAssociation(associationName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allAssociationClasses()
     */
    public Collection<MAssociationClass> allAssociationClasses()
    {
	return this.getSystem().model().getAssociationClassesOnly();
//	return new HashSet<MAssociationClass>(getSystem().model().getAssociationClassesOnly());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#associationClassByName(java.lang.String)
     */
    public MAssociationClass getAssociationClassByName(String associationClassName)
    {
	assert associationClassName != null;

	return this.getSystem().model().getAssociationClass(associationClassName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allEnumTypes()
     */
    public Collection<EnumType> allEnumTypes()
    {
	return this.getSystem().model().enumTypes();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#enumByName(java.lang.String)
     */
    public EnumType getEnumByName(String enumName)
    {
	assert enumName != null;

	return this.getSystem().model().enumType(enumName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allStateMachines()
     */
    public Collection<MStateMachine> allStateMachines()
    {
	Collection<MStateMachine> result = new HashSet<MStateMachine>();
	for (final MClass cls : this.getSystem().model().classes())
	    for (final MStateMachine sm : cls.getOwnedProtocolStateMachines())
		result.add(sm);

	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#stateMachineByName(java.lang.String)
     */
    public MStateMachine getStateMachineByName(String stateMachineName)
    {
	assert stateMachineName != null;

	for (MStateMachine sm : this.allStateMachines())
	    if (sm.name().toUpperCase().equals(stateMachineName.toUpperCase()))
		return sm;
	return null;
    }

    // ---------------------------- OBJECTS (CRUD) ----------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#reset()
     */
    public void reset()
    {
	this.getSystem().reset();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#createObject(java.lang.String, java.lang.String)
     */
    public MObject createObject(String objectId, String className)
    {
	return this.createObject(objectId, this.getClassByName(className));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#createObject(java.lang.String, org.tzi.use.uml.mm.MClass)
     */
    public MObject createObject(String objectId, MClass theClass)
    {
	assert objectId != null;
	assert theClass != null;

	MObject result = null;
	try
	{
	    result = this.getSystem().state().createObject(theClass, objectId);
	} catch (MSystemException e)
	{
	    e.printStackTrace();
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#objectByName(java.lang.String)
     */
    public MObject getObjectByName(String objectId)
    {
	assert objectId != null;

	return this.getSystem().state().objectByName(objectId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allInstances()
     */
    public Set<MObject> allObjects()
    {
	return this.getSystem().state().allObjects();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allInstances(org.tzi.use.uml.mm.MClass)
     */
    public Collection<MObject> allObjects(MClass theClass)
    {
	assert theClass != null;

	return this.getSystem().state().objectsOfClass(theClass);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allInstances(java.lang.String)
     */
    public Collection<MObject> allObjects(String className)
    {
	return this.allObjects(this.getClassByName(className));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#setObjectAttribute(org.tzi.use.uml.sys.MObject,
     * org.tzi.use.uml.mm.MAttribute, org.tzi.use.uml.ocl.value.Value)
     */
    public void setObjectAttribute(MObject theObject, MAttribute theAttribute, Value attributeValue)
    {
	assert theObject != null;
	assert theAttribute != null;
	assert attributeValue != null;

	theObject.state(this.getSystem().state()).setAttributeValue(theAttribute, attributeValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#setObjectAttribute(org.tzi.use.uml.sys.MObject, java.lang.String,
     * org.tzi.use.uml.ocl.value.Value)
     */
    public void setObjectAttribute(MObject theObject, String theAttribute, Value attributeValue)
    {
	this.setObjectAttribute(theObject, this.getAttributeByName(theObject, theAttribute), attributeValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getAttributeByName(org.tzi.use.uml.sys.MClass, java.lang.String)
     */
    public MAttribute getAttributeByName(MClass theClass, String attributeName)
    {
	assert theClass != null;

	return theClass.attribute(attributeName, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getAttributeByName(org.tzi.use.uml.sys.MObject, java.lang.String)
     */
    public MAttribute getAttributeByName(MObject theObject, String attributeName)
    {
	assert theObject != null;

	return getAttributeByName(theObject.cls(), attributeName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#deleteObject(org.tzi.use.uml.sys.MObject)
     */
    public void deleteObject(MObject theObject)
    {
	assert theObject != null;

	this.getSystem().state().deleteObject(theObject);
    }

    // ------------------ LINK OBJECTS (CRUD) ---------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.String, java.lang.String, java.util.List)
     */
    public MLinkObject createLinkObject(String objectId, String associativeClassName, List<MObject> members)
    {
	return this.createLinkObject(objectId, getAssociationClassByName(associativeClassName), members);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.String, org.tzi.use.uml.mm.MAssociationClass,
     * java.util.List)
     */
    public MLinkObject createLinkObject(String objectId, MAssociationClass theAssociativeClass, List<MObject> members)
    {
	assert theAssociativeClass != null;
	assert members != null;
	assert members.size() == 2;

	MLinkObject result = null;
	try
	{
	    result = this.getSystem().state().createLinkObject(theAssociativeClass, objectId, members, null);
	} catch (MSystemException e)
	{
	    e.printStackTrace();
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#linkObjectByName(java.lang.String)
     */
    public MLinkObject getLinkObjectByName(String linkObjectId)
    {
	assert linkObjectId != null;

	MObject obj = this.getObjectByName(linkObjectId);
	return obj instanceof MLinkObject ? (MLinkObject) obj : null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allLinkObjects()
     */
    public Collection<MLinkObject> allLinkObjects()
    {
	Collection<MLinkObject> result = new HashSet<MLinkObject>();
	for (MAssociationClass ac : allAssociationClasses())
	    for (MObject lo : this.getSystem().state().objectsOfClass(ac))
		result.add((MLinkObject) lo);
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allLinkObjects(java.lang.String)
     */
    public Collection<MLinkObject> allLinkObjects(String associationClassName)
    {
	return this.allLinkObjects(getAssociationClassByName(associationClassName));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allLinkObjects(org.tzi.use.uml.mm.MAssociationClass)
     */
    @SuppressWarnings("unchecked")
    public Collection<MLinkObject> allLinkObjects(MAssociationClass theAssociationClass)
    {
	assert theAssociationClass != null;

	return (Set<MLinkObject>) ((Set<?>) allObjects(theAssociationClass));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#setLinkObjectAttribute(org.tzi.use.uml.sys.MLinkObject,
     * org.tzi.use.uml.mm.MAttribute, org.tzi.use.uml.ocl.value.Value)
     */
    public void setLinkObjectAttribute(MLinkObject theLinkObject, MAttribute theAttribute, Value attributeValue)
    {
	this.setObjectAttribute(theLinkObject, theAttribute, attributeValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#setLinkObjectAttribute(org.tzi.use.uml.sys.MLinkObject, java.lang.String,
     * org.tzi.use.uml.ocl.value.Value)
     */
    public void setLinkObjectAttribute(MLinkObject theLinkObject, String theAttribute, Value attributeValue)
    {
	this.setObjectAttribute(theLinkObject, theAttribute, attributeValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getLinkObjectAttributeValue(org.tzi.use.uml.sys.MLinkObject,
     * org.tzi.use.uml.mm.MAttribute)
     */
    public Value getLinkObjectAttributeValue(MLinkObject theLinkObject, MAttribute theAttribute)
    {
	return this.getObjectAttributeValue(theLinkObject, theAttribute);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getLinkObjectAttributeValue(org.tzi.use.uml.sys.MLinkObject, java.lang.String)
     */
    public Value getLinkObjectAttributeValue(MLinkObject theLinkObject, String theAttribute)
    {
	return this.getObjectAttributeValue(theLinkObject, theAttribute);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getLinkObjectAttribute(org.tzi.use.uml.sys.MLinkObject,
     * org.tzi.use.uml.mm.MAttribute)
     */
    public Object getLinkObjectAttribute(MLinkObject theLinkObject, MAttribute theAttribute)
    {
	return this.getObjectAttribute(theLinkObject, theAttribute);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getLinkObjectAttribute(org.tzi.use.uml.sys.MLinkObject, java.lang.String)
     */
    public Object getLinkObjectAttribute(MLinkObject theLinkObject, String theAttribute)
    {
	return this.getObjectAttribute(theLinkObject, theAttribute);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(org.tzi.use.uml.sys.MLinkObject)
     */
    public void deleteLinkObject(MLinkObject theLinkObject)
    {
	this.deleteObject(theLinkObject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(org.tzi.use.uml.mm.MAssociationClass, java.util.List)
     */
    public void deleteLinkObject(MAssociationClass theAssociationClass, List<MObject> members)
    {
	this.deleteLink(theAssociationClass, members);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(java.lang.String, java.util.List)
     */
    public void deleteLinkObject(String theAssociationClass, List<String> members)
    {
	List<MObject> memberObjects = Arrays.asList(getObjectByName(members.get(0)), getObjectByName(members.get(1)));

	this.deleteLink(this.getAssociationByName(theAssociationClass), memberObjects);
    }

    // ------------------ LINKS (CRD) ----------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#createLink(org.tzi.use.uml.mm.MAssociation, java.util.List)
     */
    public MLink createLink(MAssociation theAssociation, List<MObject> members)
    {
	assert theAssociation != null;
	assert members != null;
	assert members.size() == 2;

	MLink result = null;
	try
	{
	    result = this.getSystem().state().createLink(theAssociation, members, null);
	} catch (MSystemException e)
	{
	    e.printStackTrace();
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#createLink(java.lang.String, java.util.List)
     */
    public MLink createLink(String theAssociation, List<String> members)
    {
	List<MObject> memberObjects = Arrays.asList(getObjectByName(members.get(0)), getObjectByName(members.get(1)));

	return this.createLink(getAssociationByName(theAssociation), memberObjects);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getLink(org.tzi.use.uml.mm.MAssociation, java.util.List)
     */
    public MLink getLink(MAssociation theAssociation, List<MObject> members)
    {
	assert theAssociation != null;
	assert members != null;
	assert members.size() == 2;

	return this.getSystem().state().linkBetweenObjects(theAssociation, members, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getLink(org.tzi.use.uml.mm.MAssociation, java.util.List)
     */
    public MLink getLink(String theAssociation, List<String> members)
    {
	List<MObject> memberObjects = Arrays.asList(getObjectByName(members.get(0)), getObjectByName(members.get(1)));

	return this.getLink(getAssociationByName(theAssociation), memberObjects);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allLinks()
     */
    public Collection<MLink> allLinks()
    {
	return this.getSystem().state().allLinks();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allLinks(java.lang.String)
     */
    public Collection<MLink> allLinks(String associationName)
    {
	return this.allLinks(getAssociationByName(associationName));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#allLinks(org.tzi.use.uml.mm.MAssociation)
     */
    public Collection<MLink> allLinks(MAssociation theAssociation)
    {
	return this.getSystem().state().linksOfAssociation(theAssociation).links();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getSourceObject(org.tzi.use.uml.mm.MLink)
     */
    public MObject getSourceObject(MLink theLink)
    {
	return theLink.linkedObjectsAsArray()[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getTargetObject(org.tzi.use.uml.mm.MLink)
     */
    public MObject getTargetObject(MLink theLink)
    {
	return theLink.linkedObjectsAsArray()[1];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#deleteLink(org.tzi.use.uml.sys.MLink)
     */
    public void deleteLink(MLink theLink)
    {
	assert theLink != null;

	try
	{
	    this.getSystem().state().deleteLink(theLink);
	} catch (MSystemException e)
	{
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#deleteLink(org.tzi.use.uml.mm.MAssociation, java.util.List)
     */
    public void deleteLink(MAssociation theAssociation, List<MObject> members)
    {
	assert theAssociation != null;
	assert members != null;
	assert members.size() == 2;

	try
	{
	    this.getSystem().state().deleteLink(theAssociation, members, null);
	} catch (MSystemException e)
	{
	    e.printStackTrace();
	}
    }

    // ------------------ ANNOTATIONS ----------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#getAnnotations(org.tzi.use.uml.mm.MModelElement)
     */
    public Map<String, MElementAnnotation> getAnnotations(MModelElement element)
    {
	assert element != null;

	return element.getAllAnnotations();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#printAnnotations
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

    // ------------------ UTILITIES ----------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingFacade#printModel
     */
    public final void printModel()
    {
	for (MClass aClass : this.allClasses())
	{
	    if (aClass.isAnnotated())
		this.printAnnotations("CLASS: " + aClass.name(), aClass);

	    for (MAttribute attribute : aClass.attributes())
		if (attribute.isAnnotated())
		    this.printAnnotations("ATTRIBUTE: " + aClass.name() + "." + attribute.name(), attribute);

	    for (MOperation operation : aClass.operations())
	    {
		if (operation.isAnnotated())
		    this.printAnnotations("OPERATION: " + aClass.name() + "." + operation.name(), operation);

		for (MPrePostCondition pre : operation.preConditions())
		    if (pre.isAnnotated())
			this.printAnnotations("PRE: " + aClass.name() + "." + operation.name() + "::" + pre.name(), pre);

		for (MPrePostCondition post : operation.postConditions())
		    if (post.isAnnotated())
			this.printAnnotations("POST: " + aClass.name() + "." + operation.name() + "::" + post.name(), post);
	    }

	    for (MClassInvariant invariant : this.getSystem().model().classInvariants())
		if (invariant.cls().equals(aClass) && invariant.isAnnotated())
		    this.printAnnotations("INV: " + aClass.name() + "::" + invariant.name(), invariant);
	}
    }

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
	    result += this.getSystem().model().getStats();
	else
	{
	    result += this.getSystem().model().classes().size() + "\t" + this.getSystem().model().associations().size() + "\t"
		    + this.getSystem().model().classInvariants().size() + "\t" + this.getSystem().model().prePostConditions().size()
		    + "\t" + this.allStateMachines().size() + "\t";

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

}