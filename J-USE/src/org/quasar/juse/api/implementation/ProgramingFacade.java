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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MElementAnnotation;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState.DeleteObjectResult;

/***********************************************************
 * @author fba 25 de Abr de 2012
 * 
 ***********************************************************/
public class ProgramingFacade extends BasicFacade implements JUSE_ProgramingFacade
{

	public ProgramingFacade()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#createObject(java.lang.String, java.lang.String)
	 */
	public MObject createObject(String objectId, String theClass)
	{
		return createObject(objectId, classByName(theClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#createObject(java.lang.String, org.tzi.use.uml.mm.MClass)
	 */
	public MObject createObject(String objectId, MClass theClass)
	{
		assert theClass != null;

		MObject result = null;
		try
		{
			result = getSystem().state().createObject(theClass, objectId);
		}
		catch (MSystemException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#deleteObject(org.tzi.use.uml.sys.MObject)
	 */
	public DeleteObjectResult deleteObject(MObject theObject)
	{
		assert theObject != null;

		return getSystem().state().deleteObject(theObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#createLinkObject(java.lang.String, java.lang.String, java.util.List)
	 */
	public MLinkObject createLinkObject(String objectId, String theAssociativeClass, List<MObject> members)
	{
		return createLinkObject(objectId, associationClassByName(theAssociativeClass), members);
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
			result = getSystem().state().createLinkObject(theAssociativeClass, objectId, members, null);
		}
		catch (MSystemException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#deleteLinkObject(org.tzi.use.uml.sys.MLinkObject)
	 */
	public DeleteObjectResult deleteLinkObject(MLinkObject theLinkObject)
	{
		assert theLinkObject != null;

		return getSystem().state().deleteObject(theLinkObject);
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

		theObject.state(getSystem().state()).setAttributeValue(theAttribute, attributeValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#getObjectAttribute(org.tzi.use.uml.sys.MObject,
	 * org.tzi.use.uml.mm.MAttribute)
	 */
	public Value getObjectAttribute(MObject theObject, MAttribute theAttribute)
	{
		assert theObject != null;
		assert theAttribute != null;

		return theObject.state(getSystem().state()).attributeValue(theAttribute);
	}

	@Override
	public MLink createLink(String theAssociation, List<MObject> members)
	{
		return createLink(associationByName(theAssociation), members);
	}

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
			result = getSystem().state().createLink(theAssociation, members, null);
		}
		catch (MSystemException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#deleteLink(org.tzi.use.uml.mm.MAssociation, java.util.List)
	 */
	public DeleteObjectResult deleteLink(MAssociation theAssociation, List<MObject> members)
	{
		assert theAssociation != null;
		assert members != null;
		assert members.size() == 2;

		DeleteObjectResult result = null;
		try
		{
			result = getSystem().state().deleteLink(theAssociation, members, null);
		}
		catch (MSystemException e)
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
	public MObject objectByName(String objectId)
	{
		return getSystem().state().objectByName(objectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#classByName(java.lang.String)
	 */
	public MClass classByName(String className)
	{
		return getSystem().model().getClass(className);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#enumByName(java.lang.String)
	 */
	public EnumType enumByName(String enumName)
	{
		return getSystem().model().enumType(enumName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#associationClassByName(java.lang.String)
	 */
	public MAssociationClass associationClassByName(String associationClassName)
	{
		return getSystem().model().getAssociationClass(associationClassName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#associationByName(java.lang.String)
	 */
	public MAssociation associationByName(String associationName)
	{
		return getSystem().model().getAssociation(associationName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#attributeByName(org.tzi.use.uml.sys.MObject, java.lang.String)
	 */
	public MAttribute attributeByName(MObject theObject, String attributeName)
	{
		assert theObject != null;

		return theObject.cls().attribute(attributeName, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allInstances(java.lang.String)
	 */
	public Set<MObject> allInstances(String theClass)
	{
		return allInstances(classByName(theClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allInstances(org.tzi.use.uml.mm.MClass)
	 */
	public Set<MObject> allInstances(MClass theClass)
	{
		return getSystem().state().objectsOfClass(theClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allClasses()
	 */
	public Collection<MClass> allClasses()
	{
		return getSystem().model().classes();
		// return new HashSet<MClass>(getSystem().model().classes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allAssociations()
	 */
	public Collection<MAssociation> allAssociations()
	{
		return getSystem().model().associations();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allObjects()
	 */
	public Collection<MObject> allObjects()
	{
		return getSystem().state().allObjects();
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
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allInvariants()
	 */
	public Collection<MClassInvariant> allInvariants()
	{
		return getSystem().model().classInvariants();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#allPreConditions()
	 */
	public Collection<MPrePostCondition> allPreConditions()
	{
		Collection<MPrePostCondition> result = new ArrayList<MPrePostCondition>();
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
	public Collection<MPrePostCondition> allPostConditions()
	{
		Collection<MPrePostCondition> result = new ArrayList<MPrePostCondition>();
		for (MPrePostCondition assertion : getSystem().model().prePostConditions())
			if (!assertion.isPre())
				result.add(assertion);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#annotations(org.tzi.use.uml.mm.MModelElement)
	 */
	public Map<String, MElementAnnotation> annotations(MModelElement element)
	{
		return element.getAllAnnotations();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#check(org.tzi.use.uml.mm.MClassInvariant)
	 */
	public boolean check(MClassInvariant anInvariant)
	{
		EvalContext context = new EvalContext(null, getSystem().state(), getSystem().varBindings(), null, "\t");

		return ((BooleanValue) anInvariant.expandedExpression().eval(context)).isTrue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#oclEvaluator(java.lang.String)
	 */
	public Value oclEvaluator(String expression)
	{
		assert expression.length() > 0;

		Expression expr = OCLCompiler.compileExpression(getSystem().model(), getSystem().state(), new ByteArrayInputStream(
						expression.getBytes()), "<input>", new PrintWriter(System.err), getSystem().varBindings());

		// compile errors?
		if (expr == null)
			return UndefinedValue.instance;

		// // evaluate it with current system state (non verbose)
		Evaluator evaluator = new Evaluator(false);

		Value val = null;
		try
		{
			val = evaluator.eval(expr, getSystem().state(), getSystem().varBindings(), null);
		}
		catch (MultiplicityViolationException e)
		{
			System.out.println("-> " + "Could not evaluate. " + e.getMessage());
		}
		return val;
	}

}