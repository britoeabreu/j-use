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

package org.quasar.juse.api;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MElementAnnotation;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState.DeleteObjectResult;


/***********************************************************
* @author fba
* 19 de Abr de 2012
*
***********************************************************/
public interface JUSE_ProgramingFacade extends JUSE_BasicFacade
{
	
	/***********************************************************
	* @param objectId
	* @param theClass
	* @return
	***********************************************************/
	public MObject createObject(String objectId, MClass theClass);
	
	/***********************************************************
	* @param theObject
	* @return
	***********************************************************/
	public DeleteObjectResult deleteObject(MObject theObject);
	
	/***********************************************************
	* @param objectId
	* @param theAssociativeClass
	* @param members
	* @return
	***********************************************************/
	public MLinkObject createLinkObject(String objectId, MAssociationClass theAssociativeClass, List<MObject> members);
	
	/***********************************************************
	* @param theLinkObject
	* @return
	***********************************************************/
	public DeleteObjectResult deleteLinkObject(MLinkObject theLinkObject);
	
	/***********************************************************
	* @param theObject
	* @param theAttribute
	* @param attributeValue
	***********************************************************/
	public void setObjectAttribute(MObject theObject, MAttribute theAttribute, Value attributeValue);

	/***********************************************************
	* @param theObject
	* @param theAttribute
	* @return
	***********************************************************/
	public Value getObjectAttribute(MObject theObject, MAttribute theAttribute);
	
	/***********************************************************
	* @param theAssociation
	* @param members
	* @return
	***********************************************************/
	public MLink createLink(MAssociation theAssociation, List<MObject> members);

	/***********************************************************
	* @param theAssociation
	* @param members
	* @return
	***********************************************************/
	public DeleteObjectResult deleteLink(MAssociation theAssociation, List<MObject> members);

	/***********************************************************
	* @param className
	* @return
	***********************************************************/
	public Set<MObject> allInstances(MClass theClass);

	/***********************************************************
	* @param objectId
	* @return
	***********************************************************/
	public MObject objectByName(String objectId);
	
	/***********************************************************
	* @param className
	* @return
	***********************************************************/
	public MClass classByName(String className);

	/***********************************************************
	* @param associationClassName
	* @return
	***********************************************************/
	public MAssociationClass associationClassByName(String associationClassName);
	
	/***********************************************************
	* @param associationName
	* @return
	***********************************************************/
	public MAssociation associationByName(String associationName);
	
	/***********************************************************
	* @param attributeName
	* @return
	***********************************************************/
	public MAttribute attributeByName(MObject theObject, String attributeName);
	

//	public void check(MObject theObject, String attributeName);
	
	public Set<MClass> allClasses();
	
	public Map<String, MElementAnnotation> allAnnotations(MClass theClass);
	
}