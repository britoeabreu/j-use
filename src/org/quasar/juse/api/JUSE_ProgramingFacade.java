/*
 * J-USE - Java prototyping for the UML based specification environment (USE)
 * Copyright (C) 2018 Fernando Brito e Abrey, QUASAR research group
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

package org.quasar.juse.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MElementAnnotation;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;


/***********************************************************
* @author fba
* 19 April 2012 - Original version
* 16 October 2018 - Extended and commented version
***********************************************************/
public interface JUSE_ProgramingFacade extends JUSE_BasicFacade
{
	
	/***********************************************************
	* @param expression OCL expression to be evaluated
	* @return resulting value of a valid OCL expression or an undefined value if expression is invalid
	***********************************************************/
	public Value oclEvaluator(String expression);
	
	
	/***********************************************************
	 * @param v a value in the USE type system
	 * @return a corresponding boxed value of v in the Java type system
	***********************************************************/
	public Object toObject(Value v);
	
	//------------------ MODELING ELEMENTS -----------------------------------------------

	/***********************************************************
	* @param verbose if true produces a more verbose output
	* @return string with some statistics about the model: # classes, # associations, # invariants, and # operations.
	***********************************************************/
	public String statistics(boolean verbose);


	/***********************************************************
	* @param element model element
	* @return map containing the annotations of the given model element
	***********************************************************/
	public Map<String, MElementAnnotation> a (MModelElement element);

	
	/***********************************************************
	* @param element model element
	***********************************************************/
	public void printAnnotations(String prompt, MModelElement element);
	
	
	/***********************************************************
	* @return all classes defined in the model
	***********************************************************/
	public Collection<MClass> allClasses();

			
	/***********************************************************
	* @param className class name
	* @return class with the given name
	***********************************************************/
	public MClass classByName(String className);

	
	/***********************************************************
	* @param theClass the class whose attributes we want
	* @return the list of class attributes
	***********************************************************/
	public List<MAttribute> getAttributes(MClass theClass);
	
	
	/***********************************************************
	* @param theClass the class whose operations we want
	* @return the list of class operations
	***********************************************************/
	public List<MOperation> getOperations(MClass theClass);
	
	
	/***********************************************************
	* @param className class name
	* @param operationName operation name
	* @return identified operation in the identified class, if it exists, or null otherwise
	***********************************************************/
	public MOperation operationByName(String className, String operationName);
	
	
	/***********************************************************
	* @return all association classes defined in the model
	***********************************************************/
	public Collection<MAssociationClass> allAssociationClasses();

			
	/***********************************************************
	* @param associationClassName association class name
	* @return association class with the given name
	***********************************************************/
	public MAssociationClass associationClassByName(String associationClassName);
	
	
	/***********************************************************
	* @return all associations defined in the model
	***********************************************************/
	public Collection<MAssociation> allAssociations();	
	

	/***********************************************************
	* @param associationName association name
	* @return association with the given name
	***********************************************************/
	public MAssociation associationByName(String associationName);

	
	/***********************************************************
	* @return all enumerated types defined in the model
	***********************************************************/
	public Collection<EnumType> allEnumTypes();
		

	/***********************************************************
	* @param enumName enumerated type name
	* @return enumerated type with the given name
	***********************************************************/
	public EnumType enumByName(String enumName);
	
	
	/***********************************************************
	* @return all state machines defined in the model
	***********************************************************/
	public Collection<MStateMachine> allStateMachines();	

	
	/***********************************************************
	* @param stateMachineName state machine name
	* @return state machine with given name or null if it does not exist
 	***********************************************************/
	public MStateMachine stateMachineByName(String stateMachineName);
	
	
	/***********************************************************
	* System is set to an empty state (no objects, no links)
	***********************************************************/
	public void reset();	
	
	
	//------------------ DESIGN BY CONTRACT ----------------------------------------------

	/***********************************************************
	* @return all invariants defined in the model
	***********************************************************/
	public Collection<MClassInvariant> allInvariants();
	
	
	/***********************************************************
	* @param invariantName invariant name (must be given as <code>class::inv</code>)
	* @return class invariant with given name or null if it does not exist
 	***********************************************************/
	public MClassInvariant invariantByName(String invariantName);
	
	
	/***********************************************************
	* @return all preconditions defined in the model
	***********************************************************/
	public Collection<MPrePostCondition> allPreConditions();
	
	
	/***********************************************************
	* @return all post-conditions defined in the model
	***********************************************************/
	public Collection<MPrePostCondition> allPostConditions();

	
	/***********************************************************
	* @param anInvariant invariant being checked
	* @return <code>true</code> if the invariant is fulfilled; <code>false</code> otherwise 
	* throws exception when the invariant cannot be evaluated (e.g. precondition failure)
	***********************************************************/
	public boolean check(MClassInvariant anInvariant) throws RuntimeException;

	
	/***********************************************************
	* @return <code>true</code> if all cardinality constraints are fulfilled; <code>false</code> otherwise
	***********************************************************/
	public boolean checkStructure();
	
	
	/***********************************************************
	* @return association coverage by invariants (a ratio)
	***********************************************************/
	public double associationCoverage();
	
	
	/***********************************************************
	* @return association end coverage by invariants (a ratio)
	***********************************************************/
	public double associationEndCoverage();

	
	//------------------ OBJECTS (CRUD) --------------------------------------------------
	
	/***********************************************************
	* @param objectId identifier of the object to create
	* @param className name of the class to instantiate
	* @return created object
	***********************************************************/
	public MObject createObject(String objectId, String className);
	
	
	/***********************************************************
	* @param objectId identifier of the object to create
	* @param theClass class to instantiate
	* @return created object
	***********************************************************/
	public MObject createObject(String objectId, MClass theClass);
	
	
	/***********************************************************
	* @param objectId object identifier
	* @return object with the given identifier
	***********************************************************/
	public MObject objectByName(String objectId);
	
	
	/***********************************************************
	* @return existing objects
	***********************************************************/
	public Collection<MObject> allObjects();
	
	
	/***********************************************************
	* @param className
	* @return existing objects for the class whose name is given
	***********************************************************/
	public Collection<MObject> allObjects(String className);

	
	/***********************************************************
	* @param theClass
	* @return existing objects for the given class
	***********************************************************/
	public Collection<MObject> allObjects(MClass theClass);

	
	/***********************************************************
	* @param theObject target object
	* @param theAttribute attribute to set
	* @param attributeValue value to be given to the attribute
	***********************************************************/
	public void setObjectAttribute(MObject theObject, MAttribute theAttribute, Value attributeValue);

	
	/***********************************************************
	* @param theObject target object
	* @param theAttribute attribute to set
	* @param attributeValue value to be given to the attribute
	***********************************************************/
	public void setObjectAttribute(MObject theObject, String theAttribute, Value attributeValue);
	
	
	/***********************************************************
	* @param theObject target object
	* @param theAttribute attribute to get
	* @return value of the attribute
	***********************************************************/
	public Value getObjectAttribute(MObject theObject, MAttribute theAttribute);
	
	
	/***********************************************************
	* @param theObject target object
	* @param theAttribute attribute to get
	* @return value of the attribute
	***********************************************************/
	public Value getObjectAttribute(MObject theObject, String theAttribute);
	
	
	/***********************************************************
	* @param theObject target object
	* @param attributeName attribute name
	* @return attribute with the given name, or null if it does not exist
	***********************************************************/
	public MAttribute attributeByName(MObject theObject, String attributeName);	

	
	/***********************************************************
	* @param theObject object to delete
	***********************************************************/
	public void deleteObject(MObject theObject);
	
	
	//------------------ LINK OBJECTS (CRUD) ---------------------------------------------
	
	/***********************************************************
	* @param objectId identifier of the link object to create
	* @param associationClassName name of the association class to instantiate
	* @param members participant objects in the link object
	* @return created link object
	***********************************************************/
	public MLinkObject createLinkObject(String objectId, String associationClassName, List<MObject> members);
	
	
	/***********************************************************
	* @param objectId identifier of the link object to create
	* @param theAssociationClass association class to instantiate
	* @param members participant objects in the link object
	* @return created link object
	***********************************************************/
	public MLinkObject createLinkObject(String objectId, MAssociationClass theAssociationClass, List<MObject> members);
	
	
	/***********************************************************
	* @param linkObjectId link object identifier
	* @return link object with the given identifier, or null if it does not exist
	***********************************************************/
	public MLinkObject linkObjectByName(String linkObjectId);
	
	
	/***********************************************************
	* @return existing link objects
	***********************************************************/
	public Collection<MLinkObject> allLinkObjects();
	
	
	/***********************************************************
	* @param associationClassName
	* @return existing link objects for the association class whose name is given
	***********************************************************/
	public Collection<MLinkObject> allLinkObjects(String associationClassName);

	
	/***********************************************************
	* @param theAssociationClass
	* @return existing link objects for the given association class
	***********************************************************/
	public Collection<MLinkObject> allLinkObjects(MAssociationClass theAssociationClass);
	
	
	/***********************************************************
	* @param theLinkObject target link object
	* @param theAttribute attribute to set
	* @param attributeValue value to be given to the attribute
	***********************************************************/
	public void setLinkObjectAttribute(MLinkObject theLinkObject, MAttribute theAttribute, Value attributeValue);

	
	/***********************************************************
	* @param theLinkObject target link object
	* @param theAttribute attribute to get
	* @return value of the attribute
	***********************************************************/
	public Value getLinkObjectAttribute(MLinkObject theLinkObject, MAttribute theAttribute);
	

	/***********************************************************
	* @param theLinkObject target link object
	* @param theAttribute attribute to get
	* @return value of the attribute
	***********************************************************/
	public Value getLinkObjectAttribute(MLinkObject theLinkObject, String theAttribute);
	
	
	/***********************************************************
	* @param theLinkObject link object to delete
	***********************************************************/
	public void deleteLinkObject(MLinkObject theLinkObject);
	
	
	/***********************************************************
	* @param theAssociationClass association class whose instance (link object) we want to delete 
	* @param members participant objects in the object link to delete
	***********************************************************/
	public void deleteLinkObject(MAssociationClass theAssociationClass, List<MObject> members);

	
	//------------------ LINKS (CRD) ----------------------------------------------------
	
	/***********************************************************
	* @param theAssociation association to instantiate 
	* @param members participant objects in the link
	* @return created link
	***********************************************************/
	public MLink createLink(String theAssociation, List<MObject> members);

	
	/***********************************************************
	* @param theAssociation association to instantiate
	* @param members participant objects in the link
	* @return created link
	***********************************************************/
	public MLink createLink(MAssociation theAssociation, List<MObject> members);

	
	/***********************************************************
	* @return existing links
	***********************************************************/
	public Collection<MLink> allLinks();
	
	
	/***********************************************************
	 * @param theAssociation
	 * @return existing links for the association with the given name
	***********************************************************/
	public Collection<MLink> allLinks(String associationName);

	
	/***********************************************************
	 * @param theAssociation
	 * @return existing links for the given association
	***********************************************************/
	public Collection<MLink> allLinks(MAssociation theAssociation);

	
	/***********************************************************
	* @param theLink link to delete
	***********************************************************/
	public void deleteLink(MLink theLink);

	
	/***********************************************************
	* @param theAssociation association whose instance (link) we want to delete 
	* @param members participant objects in the link to delete
	***********************************************************/
	public void deleteLink(MAssociation theAssociation, List<MObject> members);

	
	public MObject getSourceObject(MLink theLink);
	

	public MObject getTargetObject(MLink theLink);
}