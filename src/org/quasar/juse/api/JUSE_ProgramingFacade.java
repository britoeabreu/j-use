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
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;

/***********************************************************
* @author fba
* 19 April 2012 - Original version
* 16 October 2018 - Extended and commented version
***********************************************************/
/**
 * @author fba
 *
 */
public interface JUSE_ProgramingFacade extends JUSE_BasicFacade
{     
    
    // ------------------ MODELING ELEMENTS -----------------------------------------------

    /***********************************************************
     * @return all classes defined in the model
     ***********************************************************/
    public Collection<MClass> allClasses();

    /***********************************************************
     * @param className class name
     * @return class with the given name
     ***********************************************************/
    public MClass getClassByName(String className);

    /***********************************************************
     * @param theClass the class whose attributes we want
     * @return the list of class attributes
     ***********************************************************/
    public Collection<MAttribute> allClassAttributes(MClass theClass);

    /***********************************************************
     * @param theClass the name of the class whose attributes we want
     * @return the list of class attributes
     ***********************************************************/
    public Collection<MAttribute> allClassAttributes(String theClass);

    /***********************************************************
     * @param theClass the class whose operations we want
     * @return the list of class operations
     ***********************************************************/
    public Collection<MOperation> allClassOperations(MClass theClass);

    /***********************************************************
     * @param theClass the name of the class whose operations we want
     * @return the list of class operations
     ***********************************************************/
    public Collection<MOperation> allClassOperations(String theClass);

    /***********************************************************
     * @param className     class name
     * @param operationName operation name
     * @return identified operation in the identified class, if it exists, or null otherwise
     ***********************************************************/
    public MOperation getOperationByName(String className, String operationName);

    /***********************************************************
     * @return all association classes defined in the model
     ***********************************************************/
    public Collection<MAssociationClass> allAssociationClasses();

    /***********************************************************
     * @param associationClassName association class name
     * @return association class with the given name
     ***********************************************************/
    public MAssociationClass getAssociationClassByName(String associationClassName);

    /***********************************************************
     * @return all associations defined in the model
     ***********************************************************/
    public Collection<MAssociation> allAssociations();

    /***********************************************************
     * @param associationName association name
     * @return association with the given name
     ***********************************************************/
    public MAssociation getAssociationByName(String associationName);

    /***********************************************************
     * @return all enumerated types defined in the model
     ***********************************************************/
    public Collection<EnumType> allEnumTypes();

    /***********************************************************
     * @param enumName enumerated type name
     * @return enumerated type with the given name
     ***********************************************************/
    public EnumType getEnumByName(String enumName);

    // ------------------ STATE MACHINES -----------------------

    /***********************************************************
     * @return all state machines defined in the model
     ***********************************************************/
    public Collection<MStateMachine> allStateMachines();

    /***********************************************************
     * @param stateMachineName state machine name
     * @return state machine with given name or null if it does not exist
     ***********************************************************/
    public MStateMachine getStateMachineByName(String stateMachineName);

    // ------------------ OBJECTS (CRUD) -----------------------

    /***********************************************************
     * System is set to an empty state (no objects, no links)
     ***********************************************************/
    public void reset();

    /***********************************************************
     * @param objectId  identifier of the object to create
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
    public MObject getObjectByName(String objectId);

    /***********************************************************
     * @return existing objects
     ***********************************************************/
    public Collection<MObject> allObjects();

    /***********************************************************
     * @param className the name of a class
     * @return existing objects for the class whose name is given
     ***********************************************************/
    public Collection<MObject> allObjects(String className);

    /***********************************************************
     * @param theClass a given class
     * @return existing objects for the given class
     ***********************************************************/
    public Collection<MObject> allObjects(MClass theClass);

    /***********************************************************
     * @param theObject      target object
     * @param theAttribute   attribute to set
     * @param attributeValue value to be given to the attribute
     ***********************************************************/
    public void setObjectAttribute(MObject theObject, MAttribute theAttribute, Value attributeValue);

    /***********************************************************
     * @param theObject      target object
     * @param theAttribute   attribute to set
     * @param attributeValue value to be given to the attribute
     ***********************************************************/
    public void setObjectAttribute(MObject theObject, String theAttribute, Value attributeValue);

    /***********************************************************
     * @param theObject    target object
     * @param theAttribute attribute to get
     * @return value of the attribute
     ***********************************************************/
    public Value getObjectAttributeValue(MObject theObject, MAttribute theAttribute);

    /***********************************************************
     * @param theObject    target object
     * @param theAttribute attribute to get
     * @return value of the attribute
     ***********************************************************/
    public Value getObjectAttributeValue(MObject theObject, String theAttribute);

    /***********************************************************
     * @param theObject    target object
     * @param theAttribute attribute to get
     * @return value of the attribute (Java type system)
     ***********************************************************/
    public Object getObjectAttribute(MObject theObject, MAttribute theAttribute);

    /***********************************************************
     * @param theObject    target object
     * @param theAttribute attribute to get
     * @return value of the attribute (Java type system)
     ***********************************************************/
    public Object getObjectAttribute(MObject theObject, String theAttribute);
    
    
    /***********************************************************
     * @param theObject     target object
     * @param attributeName attribute name
     * @return attribute with the given name, or null if it does not exist in the target object
     ***********************************************************/
    public MAttribute getAttributeByName(MObject theObject, String attributeName);
   
    /***********************************************************
     * @param theClass     target class
     * @param attributeName attribute name
     * @return attribute with the given name, or null if it does not exist in the target class
     ***********************************************************/
    public MAttribute getAttributeByName(MClass theClass, String attributeName);

    /***********************************************************
     * @param theObject object to delete
     ***********************************************************/
    public void deleteObject(MObject theObject);

    // ------------------ LINK OBJECTS (CRUD) ----------------------

    /***********************************************************
     * @param objectId             identifier of the link object to create
     * @param associationClassName name of the association class to instantiate
     * @param members              participant objects in the link object
     * @return created link object
     ***********************************************************/
    public MLinkObject createLinkObject(String objectId, String associationClassName, List<MObject> members);

    /***********************************************************
     * @param objectId            identifier of the link object to create
     * @param theAssociationClass association class to instantiate
     * @param members             participant objects in the link object
     * @return created link object
     ***********************************************************/
    public MLinkObject createLinkObject(String objectId, MAssociationClass theAssociationClass, List<MObject> members);

    /***********************************************************
     * @param linkObjectId link object identifier
     * @return link object with the given identifier, or null if it does not exist
     ***********************************************************/
    public MLinkObject getLinkObjectByName(String linkObjectId);

    /***********************************************************
     * @return existing link objects
     ***********************************************************/
    public Collection<MLinkObject> allLinkObjects();

    /***********************************************************
     * @param associationClassName name of the association class
     * @return existing link objects for the association class whose name is given
     ***********************************************************/
    public Collection<MLinkObject> allLinkObjects(String associationClassName);

    /***********************************************************
     * @param theAssociationClass name of the association class
     * @return existing link objects for the given association class
     ***********************************************************/
    public Collection<MLinkObject> allLinkObjects(MAssociationClass theAssociationClass);

    /***********************************************************
     * @param theLinkObject  target link object
     * @param theAttribute   attribute to set
     * @param attributeValue value to be given to the attribute
     ***********************************************************/
    public void setLinkObjectAttribute(MLinkObject theLinkObject, MAttribute theAttribute, Value attributeValue);

    /***********************************************************
     * @param theLinkObject  target link object
     * @param theAttribute   attribute to set
     * @param attributeValue value to be given to the attribute
     ***********************************************************/
    public void setLinkObjectAttribute(MLinkObject theLinkObject, String theAttribute, Value attributeValue);

    /***********************************************************
     * @param theLinkObject target link object
     * @param theAttribute  attribute to get
     * @return value of the attribute
     ***********************************************************/
    public Value getLinkObjectAttributeValue(MLinkObject theLinkObject, MAttribute theAttribute);

    /***********************************************************
     * @param theLinkObject target link object
     * @param theAttribute  attribute to get
     * @return value of the attribute
     ***********************************************************/
    public Value getLinkObjectAttributeValue(MLinkObject theLinkObject, String theAttribute);

    /***********************************************************
     * @param theLinkObject target link object
     * @param theAttribute  attribute to get
     * @return value of the attribute (Java type system)
     ***********************************************************/
    public Object getLinkObjectAttribute(MLinkObject theLinkObject, MAttribute theAttribute);

    /***********************************************************
     * @param theLinkObject target link object
     * @param theAttribute  attribute to get
     * @return value of the attribute (Java type system)
     ***********************************************************/
    public Object getLinkObjectAttribute(MLinkObject theLinkObject, String theAttribute);
    
    /***********************************************************
     * @param theLinkObject link object to delete
     ***********************************************************/
    public void deleteLinkObject(MLinkObject theLinkObject);

    /***********************************************************
     * @param theAssociationClass association class whose instance (link object) we want to delete
     * @param members             participant objects in the object link to delete
     ***********************************************************/
    public void deleteLinkObject(MAssociationClass theAssociationClass, List<MObject> members);

    /***********************************************************
     * @param theAssociationClass association class name whose instance (link object) we want to delete
     * @param members             participant objects' names in the object link to delete
     ***********************************************************/
    public void deleteLinkObject(String theAssociationClass, List<String> members);

    // ------------------ LINKS (CRD) ---------------------------

    /***********************************************************
     * @param theAssociation association to instantiate
     * @param members        participant objects in the link
     * @return created link
     ***********************************************************/
    public MLink createLink(String theAssociation, List<String> members);

    /***********************************************************
     * @param theAssociation association to instantiate
     * @param members        participant objects in the link
     * @return created link
     ***********************************************************/
    public MLink createLink(MAssociation theAssociation, List<MObject> members);

    /***********************************************************
     * @return existing links
     ***********************************************************/
    public Collection<MLink> allLinks();

    /***********************************************************
     * @param associationName name of an association
     * @return existing links for the association with the given name
     ***********************************************************/
    public Collection<MLink> allLinks(String associationName);

    /***********************************************************
     * @param theAssociation an association object
     * @return existing links for the given association
     ***********************************************************/
    public Collection<MLink> allLinks(MAssociation theAssociation);

    /***********************************************************
     * @param theLink link to delete
     ***********************************************************/
    public void deleteLink(MLink theLink);

    /***********************************************************
     * @param theAssociation association whose instance (link) we want to delete
     * @param members        participant objects in the link to delete
     ***********************************************************/
    public void deleteLink(MAssociation theAssociation, List<MObject> members);

    /***********************************************************
     * @param theAssociation a given association
     * @param members a list of two objects
     * @return the link if there is a link (instance of the given association) connecting the given list of objects, otherwise null
     *         is returned.
     ***********************************************************/
    public MLink getLink(MAssociation theAssociation, List<MObject> members);

    /***********************************************************
     * @param theAssociation the name of a given association
     * @param members a list of the names of two objects
     * @return the link if there is a link (instance of the given association) connecting the given list of objects, otherwise null
     *         is returned.
     ***********************************************************/
    public MLink getLink(String theAssociation, List<String> members);

    /***********************************************************
     * @param theLink a given link
     * @return the source object of the given link
     ***********************************************************/
    public MObject getSourceObject(MLink theLink);

    /***********************************************************
     * @param theLink a given link
     * @return the target object of the given link
     * ***********************************************************/
    public MObject getTargetObject(MLink theLink);


    // ------------------ ANNOTATIONS -----------------------------------------------

    /***********************************************************
     * @param element model element
     * @return map containing the annotations of the given model element
     ***********************************************************/
    public Map<String, MElementAnnotation> getAnnotations(MModelElement element);

    /***********************************************************
     * @param prompt a prompt to print before the annotations themselves
     * @param element model element
     ***********************************************************/
    public void printAnnotations(String prompt, MModelElement element);
    

    // ------------------ UTILITIES -----------------------------------------------

    /***********************************************************
     * @param verbose if true produces a more verbose output
     * @return string with some statistics about the model: # classes, # associations, # invariants, and # operations.
     ***********************************************************/
    public String statistics(boolean verbose);
    
    /***********************************************************
     *
     ***********************************************************/
    public void printModel();

    
    // ------------------ OCL EXPRESSIONS ----------------------------------------

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
    
    /***********************************************************
     * @param v a collection value in the USE type system
     * @return a corresponding collection in the Java type system
     ***********************************************************/
    public Collection<Object> toCollection(Value v);
}