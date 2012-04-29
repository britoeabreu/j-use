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

import org.tzi.use.uml.ocl.type.Type;

public abstract class JavaTypes
{

	/***********************************************************
	 * @return
	 ***********************************************************/
	public static String getJavaInterfaceType(Type oclType)
	{
		if (oclType.isOrderedSet())	return "SortedSet<" + javaType(oclType) + ">";
		if (oclType.isSet())					return "Set<" 			+ javaType(oclType) + ">";
		if (oclType.isSequence())		return "Queue<"		+ javaType(oclType) + ">";
		if (oclType.isBag())				return "List<"			+ javaType(oclType) + ">";
		return javaType(oclType);
	}

	/***********************************************************
	 * @return
	 ***********************************************************/
	public static String getJavaImplementationType(Type oclType)
	{
		if (oclType.isOrderedSet())	return "TreeSet<"			+ javaType(oclType) + ">";
		if (oclType.isSet())					return "HashSet<"		+ javaType(oclType) + ">";
		if (oclType.isSequence())		return "ArrayDeque<"	+ javaType(oclType) + ">";
		if (oclType.isBag())				return "ArrayList<"		+ javaType(oclType) + ">";
		return javaType(oclType);
	}

	/***********************************************************
	 * @param oclType
	 * @return
	 ***********************************************************/
	public static String javaType(Type oclType)
	{
		// System.out.println(oclType);
		if (oclType.isNumber())
			return "int";
		if (oclType.isInteger())
			return "int";
		if (oclType.isReal())
			return "double";
		if (oclType.isBoolean())
			return "boolean";
		if (oclType.isString())
			return "String";
		if (oclType.isEnum())
			return oclType.toString();
		if (oclType.isObjectType())
			return oclType.toString();
		if (oclType.isTrueObjectType())
			return oclType.toString();
		if (oclType.isTrueOclAny())
			return "Object";
		if (oclType.isVoidType())
			return "void";
		if (oclType.isDate())
			return "Date";
		if (oclType.isSet())
			return oclType.toString().substring(4, oclType.toString().length()-1);	
		if (oclType.isOrderedSet())
			return oclType.toString().substring(11, oclType.toString().length()-1);
		if (oclType.isBag())
			return oclType.toString().substring(4, oclType.toString().length()-1);
		if (oclType.isSequence())
			return oclType.toString().substring(9, oclType.toString().length()-1);
//		if (oclType.isCollection(true))
//		return "Set<Object>";
//	if (oclType.isTrueCollection())
//		return "TrueCollection";
//	if (oclType.isTrueSet())
//		return "TrueSet";
//	if (oclType.isTrueSequence())
//		return "TrueSequence";
//	if (oclType.isTrueOrderedSet())
//		return "Number";
//	if (oclType.isTrueBag())
//		return "TrueBag";
//	if (oclType.isInstantiableCollection())
//		return "InstantiableCollection";
//	if (oclType.isTupleType(true))
//	return "Tuple";
		
		return "ERROR!";
	}

	/***********************************************************
	 * @param oclType
	 * @return
	 ***********************************************************/
	public static String javaDummyValue(Type oclType)
	{
		if (oclType.isNumber())
			return "-1";
		if (oclType.isInteger())
			return "-1";
		if (oclType.isReal())
			return "-1.0";
		if (oclType.isBoolean())
			return "true";
		if (oclType.isString())
			return "null";
		if (oclType.isEnum())
			return "null";
		if (oclType.isCollection(true))
			return "null";
		if (oclType.isTrueCollection())
			return "null";
		if (oclType.isSet())
			return "null";
		if (oclType.isTrueSet())
			return "null";
		if (oclType.isSequence())
			return "null";
		if (oclType.isTrueSequence())
			return "null";
		if (oclType.isOrderedSet())
			return "null";
		if (oclType.isTrueOrderedSet())
			return "null";
		if (oclType.isBag())
			return "null";
		if (oclType.isTrueBag())
			return "null";
		if (oclType.isInstantiableCollection())
			return "null";
		if (oclType.isObjectType())
			return "null";
		if (oclType.isTrueObjectType())
			return "null";
		if (oclType.isTrueOclAny())
			return "null";
		if (oclType.isTupleType(true))
			return "null";
		if (oclType.isVoidType())
			return "";
		if (oclType.isDate())
			return "null";
		return "ERROR!";
	}
}