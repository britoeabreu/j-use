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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.util.StringUtil;

public class JavaBusinessVisitor extends JavaVisitor
{
	private MModel			model;
	private String			author;
	private String			basePackageName;
	private String			businessLayerName;
	private ModelUtilities	util;

	/***********************************************************
	 * @param model
	 *            The corresponding to the compiled specification
	 * @param author
	 *            The author of the specification
	 * @param basePackageName
	 *            Full name of the base package where the code of the generated Java prototype will be placed
	 * @param businessLayerName
	 *            Relative name of the layer package where the source code for the business layer is to be placed
	 ***********************************************************/
	public JavaBusinessVisitor(MModel model, String author, String basePackageName, String businessLayerName)
	{
		this.model = model;
		this.author = author;
		this.basePackageName = basePackageName;
		this.businessLayerName = businessLayerName;
		this.util = new ModelUtilities(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printFileHeader(java.lang.String)
	 */
	@Override
	public void printFileHeader(String typeName)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		// get current date time with Date()
		Date date = new Date();

		println("/**********************************************************************");
		println("* Filename: " + typeName + ".java");
		println("* Created: " + dateFormat.format(date));
		println("* @author " + author);
		println("**********************************************************************/");
		println("package " + basePackageName + "." + businessLayerName + ";");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printEnumType(org.tzi.use.uml.ocl.type.EnumType)
	 */
	@Override
	public void printEnumType(EnumType t)
	{
		printFileHeader(t.name());
		// visitAnnotations(t);

		println("public enum " + t.name());
		println("{");
		incIndent();
		println(StringUtil.fmtSeq(t.literals(), ", "));
		decIndent();
		println("}");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printAttributes(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printAttributes(MClass theClass)
	{
		for (AttributeInfo attribute : AttributeInfo.getAttributesInfo(theClass))
			if (attribute.getType().isSet() || attribute.getType().isOrderedSet())
				println("private " + JavaTypes.getJavaInterfaceType(attribute.getType()) + " " + attribute.getName() + " = "
								+ " new " + JavaTypes.getJavaImplementationType(attribute.getType()) + "();");
			else
				println("private " + JavaTypes.getJavaInterfaceType(attribute.getType()) + " " + attribute.getName() + ";");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printClassHeader(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printClassHeader(MClass theClass)
	{
		printFileHeader(theClass.name());
		// visitAnnotations(e);

		boolean hasOrderedSets = false;

		for (AttributeInfo attribute : AttributeInfo.getAttributesInfo(theClass))
			if (attribute.getType().isOrderedSet() && attribute.getKind() != AssociationKind.ASSOCIATIVE2MEMBER)
				hasOrderedSets = true;

		for (AssociationInfo association : AssociationInfo.getAssociationsInfo(theClass))
			if (association.getTargetAE().getType().isOrderedSet()
							&& association.getKind() != AssociationKind.ASSOCIATIVE2MEMBER)
				hasOrderedSets = true;

		println("import java.util.Set;");
		println("import java.util.HashSet;");

		if (hasOrderedSets)
		{
			println("import java.util.SortedSet;");
			if (!theClass.isAbstract())
				println("import java.util.TreeSet;");
		}
		println();

		print("public ");
		if (theClass.isAbstract())
			print("abstract ");
		print("class " + theClass.name());

		Set<MClass> parents = theClass.parents();
		if (!parents.isEmpty())
			print(" extends " + StringUtil.fmtSeq(parents.iterator(), ","));
		println();
		println("{");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printAllInstances(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printAllInstances(MClass theClass)
	{
		if (theClass.isAbstract())
		{
			println();
			println("/**********************************************************************");
			println("* @return set with all instances of the current class");
			println("**********************************************************************/");
			println("public static Set<" + theClass.name() + "> allInstances()");
			println("{");
			incIndent();
			println("Set<" + theClass.name() + "> result = new HashSet<" + theClass.name() + ">();");
			for (MClass descendant : theClass.children())
				println("result.addAll(" + descendant.name() + ".allInstances);");
			println("return result;");
			decIndent();
			println("}");
		}
		else
			println("public static Set<" + theClass.name() + "> allInstances = new HashSet<" + theClass.name() + ">();");

		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printDefaultConstructors(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printDefaultConstructor(MClass theClass)
	{
		println("/**********************************************************************");
		println("* Default constructor");
		println("**********************************************************************/");
		println("public " + theClass.name() + "()");
		println("{");
		incIndent();
		if (theClass.allParents().size() > 0)
			println("super();");
		if (!theClass.isAbstract())
			println("allInstances.add(this);");
		decIndent();
		println("}");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printParameterizedConstructors(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printParameterizedConstructor(MClass theClass)
	{
		List<AttributeInfo> inheritedAttributes = new ArrayList<AttributeInfo>();
		for (MClass theParentClass : theClass.allParents())
			inheritedAttributes.addAll(AttributeInfo.getAttributesInfo(theParentClass));

		println("/**********************************************************************");
		println("* Parameterized constructor");
		for (AttributeInfo attribute : inheritedAttributes)
			println("* @param " + attribute.getName() + " the " + attribute.getName() + " to initialize (inherited)");
		for (AttributeInfo attribute : AttributeInfo.getAttributesInfo(theClass))
			println("* @param " + attribute.getName() + " the " + attribute.getName() + " to initialize");
		println("**********************************************************************/");

		print("public " + theClass.name() + "(");
		for (int i = 0; i < inheritedAttributes.size(); i++)
		{
			print(JavaTypes.getJavaInterfaceType(inheritedAttributes.get(i).getType()) + " "
							+ inheritedAttributes.get(i).getName());
			if (i < inheritedAttributes.size() - 1)
				print(", ");
		}

		List<AttributeInfo> attributes = AttributeInfo.getAttributesInfo(theClass);
		if (inheritedAttributes.size() > 0 && attributes.size() > 0)
			print(", ");
		for (int i = 0; i < attributes.size(); i++)
		{
			print(JavaTypes.getJavaInterfaceType(attributes.get(i).getType()) + " " + attributes.get(i).getName());
			if (i < attributes.size() - 1)
				print(", ");
		}
		println(")");
		println("{");
		incIndent();
		if (inheritedAttributes.size() > 0)
		{
			print("super(");
			for (int i = 0; i < inheritedAttributes.size(); i++)
			{
				print(inheritedAttributes.get(i).getName());
				if (i < inheritedAttributes.size() - 1)
					print(", ");
			}
			println(");");
		}
		for (AttributeInfo attribute : AttributeInfo.getAttributesInfo(theClass))
			println("this." + attribute.getName() + " = " + attribute.getName() + ";");
		if (!theClass.isAbstract())
			println("allInstances.add(this);");
		decIndent();
		println("}");
		println();
	}

	/***********************************************************
	 * @param theClass
	 *            The class where the arribute belongs to
	 * @param currentAttribute
	 *            The current attribute
	 * @param tag
	 *            {"getter" | "setter"}
	 ***********************************************************/
	public void printHeaderBasicGettersSetters(MClass theClass, AttributeInfo currentAttribute, String tag)
	{
		println("/**********************************************************************");
		switch (currentAttribute.getKind())
		{
			case NONE:
				println("* " + "Standard attribute " + tag);
				break;
			case ONE2ONE:
				println("* " + currentAttribute.getKind() + " " + tag + " for " + theClass + "[1] <-> "
								+ currentAttribute.getType() + "[1]"
								+ (currentAttribute.getType().isOrderedSet() ? " ordered" : ""));
				break;
			case ONE2MANY:
				println("* " + currentAttribute.getKind() + " " + tag + " for " + theClass + "[*] <-> "
								+ currentAttribute.getType() + "[1]"
								+ (currentAttribute.getType().isOrderedSet() ? " ordered" : ""));
				break;
			case MANY2MANY:
				println("* " + currentAttribute.getKind() + " " + tag + " for " + theClass + "[*] <-> "
								+ currentAttribute.getType() + "[*]"
								+ (currentAttribute.getType().isOrderedSet() ? " ordered" : ""));
				break;
			case ASSOCIATIVE2MEMBER:
				println("* " + currentAttribute.getKind() + " " + tag + " for " + theClass + "[*] <-> "
								+ currentAttribute.getType() + "[1]"
								+ (currentAttribute.getType().isOrderedSet() ? " ordered" : ""));
				break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printBasicGettersSetters(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printBasicGettersSetters(MClass theClass)
	{
		for (AttributeInfo currentAttribute : AttributeInfo.getAttributesInfo(theClass))
		{
			printHeaderBasicGettersSetters(theClass, currentAttribute, "getter");
			println("* @return the " + currentAttribute.getName() + " of the " + theClass.nameAsRolename());
			println("**********************************************************************/");
			println("public " + JavaTypes.getJavaInterfaceType(currentAttribute.getType()) + " " + currentAttribute.getName()
							+ "()");
			println("{");
			incIndent();
			println("return " + currentAttribute.getName() + ";");
			decIndent();
			println("}");
			println();

			printHeaderBasicGettersSetters(theClass, currentAttribute, "setter");
			println("* @param " + currentAttribute.getName() + " the " + currentAttribute.getName() + " to set");
			println("**********************************************************************/");
			println("public void set" + capitalize(currentAttribute.getName()) + "("
							+ JavaTypes.getJavaInterfaceType(currentAttribute.getType()) + " " + currentAttribute.getName()
							+ ")");
			println("{");
			incIndent();
			println("this." + currentAttribute.getName() + " = " + currentAttribute.getName() + ";");
			decIndent();
			println("}");
			println();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printNavigators(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printNavigators(MClass theClass)
	{
		for (AssociationInfo ai : AssociationInfo.getAssociationsInfo(theClass))
		{
			switch (ai.getKind())
			{
				case ASSOCIATIVE2MEMBER:
					// Already performed by the generated getters
					break;
				case MEMBER2ASSOCIATIVE:
					// Already performed in one direction by the collection attribute. This call generates two operations
					// (one getter and one setter) in the other direction
					// System.out.println(ai);
					if (theClass == ai.getSourceAE().cls())
						printMEMBER2ASSOCIATIVE(ai);
					if (theClass == ai.getTargetAE().cls() && ai.getSourceAE().cls() != ai.getTargetAE().cls())
						printMEMBER2ASSOCIATIVE(ai.swapped());
					break;
				case MEMBER2MEMBER:
					// Uses the association class to obtain the assessor to the other member
					// System.out.println(ai);
					if (theClass == ai.getSourceAE().cls())
						printMEMBER2MEMBER(ai);
					if (theClass == ai.getTargetAE().cls() && ai.getSourceAE().cls() != ai.getTargetAE().cls())
						printMEMBER2MEMBER(ai.swapped());
					break;
				case ONE2ONE:
					// Already performed in one direction by the attribute getter. This call generates an operation in the other
					// direction
					// System.out.println(ai);
					if (theClass == ai.getSourceAE().cls()
									&& theClass == util.moreComplexClass(ai.getSourceAE().cls(), ai.getTargetAE().cls()))
						printONE2ONE(ai);
					if (theClass == ai.getTargetAE().cls() && ai.getSourceAE().cls() != ai.getTargetAE().cls()
									&& theClass == util.moreComplexClass(ai.getSourceAE().cls(), ai.getTargetAE().cls()))
						printONE2ONE(ai.swapped());
					break;
				case ONE2MANY:
					// // Already performed in one direction by the collection attribute. This call generates two operations
					// (one getter and one setter) in the other direction
					// System.out.println(ai);
					if (theClass == ai.getSourceAE().cls() && (ai.getTargetAE().isCollection() || ai.getTargetAE().isOrdered()))
						printONE2MANY(ai);
					if (theClass == ai.getTargetAE().cls() && (ai.getSourceAE().isCollection() || ai.getSourceAE().isOrdered())
									&& ai.getSourceAE().cls() != ai.getTargetAE().cls())
						printONE2MANY(ai.swapped());
					break;
				case MANY2MANY:
					// Already performed in one direction by the collection attribute getter. This call generates an operation
					// in the other direction
					// System.out.println(ai);
					if (theClass == ai.getSourceAE().cls()
									&& theClass == util.moreComplexClass(ai.getSourceAE().cls(), ai.getTargetAE().cls()))
						printMANY2MANY(ai);
					if (theClass == ai.getTargetAE().cls() && ai.getSourceAE().cls() != ai.getTargetAE().cls()
									&& theClass == util.moreComplexClass(ai.getSourceAE().cls(), ai.getTargetAE().cls()))
						printMANY2MANY(ai.swapped());
					break;
				default:
					System.out.println("ERROR: " + ai);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.use.api.implementation.IJavaVisitor#printMEMBER2ASSOCIATIVE(org.quasar.use.api.implementation.AssociationInfo)
	 */
	@Override
	public void printMEMBER2ASSOCIATIVE(AssociationInfo aInfo)
	{
		MAssociationEnd sourceAE = aInfo.getSourceAE();
		MAssociationEnd targetAE = aInfo.getTargetAE();
		MAssociationClass associationClass = aInfo.getAssociationClass();

		String sourceClass = sourceAE.cls().name();
		// String targetClass = targetAE.cls().name();
		String associativeClass = associationClass.name();

		String sourceRole = sourceAE.name();
		String associativeRole = associationClass.nameAsRolename();

		MMultiplicity sourceMultiplicity = sourceAE.multiplicity();
		MMultiplicity targetMultiplicity = targetAE.multiplicity();

		String associativeInterfaceType = targetAE.getType().isOrderedSet() ? "SortedSet<" + associativeClass + ">" : (targetAE
						.getType().isSet() ? "Set<" + associativeClass + ">" : associativeClass);

		String associativeImplementationType = targetAE.getType().isOrderedSet() ? "TreeSet<" + associativeClass + ">"
						: (targetAE.getType().isSet() ? "HashSet<" + associativeClass + ">" : associativeClass);

		println("/**********************************************************************");
		println("* MEMBER2ASSOCIATIVE getter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + associativeClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @return the " + associativeRole + " of the " + sourceRole);
		println("**********************************************************************/");
		println("public " + associativeInterfaceType + " " + associativeRole + "()");
		println("{");
		incIndent();
		if (targetAE.getType().isSet() || targetAE.getType().isOrderedSet())
		{
			println(associativeInterfaceType + " result = new " + associativeImplementationType + "();");
			println("for (" + associativeClass + " x : " + associativeClass + ".allInstances)");
			incIndent();
			println("if (x." + sourceRole + "()  ==  this)");
			incIndent();
			println("result.add(x);");
			decIndent();
			decIndent();
			println("return result;");
		}
		else
		{
			println("for (" + associativeClass + " x : " + associativeClass + ".allInstances)");
			incIndent();
			println("if (x." + sourceRole + "()  ==  this)");
			incIndent();
			println("return x;");
			decIndent();
			decIndent();
			println("return null;");
		}
		decIndent();
		println("}");
		println();

		println("/**********************************************************************");
		println("* MEMBER2ASSOCIATIVE setter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + associativeClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @param " + associativeRole + " the " + associativeRole + " to set");
		println("**********************************************************************/");
		println("public void set" + capitalize(associativeRole) + "(" + associativeInterfaceType + " " + associativeRole + ")");
		println("{");
		incIndent();
		if (aInfo.getTargetAE().getType().isSet() || aInfo.getTargetAE().getType().isOrderedSet())
		{
			println("for (" + associativeClass + " x : " + associativeRole + ")");
			incIndent();
			println("x.set" + capitalize(sourceRole) + "(this);");
			decIndent();
		}
		else
			println(associativeRole + ".set" + capitalize(sourceRole) + "(this);");
		decIndent();
		println("}");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printMEMBER2MEMBER(org.quasar.use.api.implementation.AssociationInfo)
	 */
	@Override
	public void printMEMBER2MEMBER(AssociationInfo aInfo)
	{
		MAssociationEnd sourceAE = aInfo.getSourceAE();
		MAssociationEnd targetAE = aInfo.getTargetAE();
		MAssociationClass associationClass = aInfo.getAssociationClass();

		String sourceClass = sourceAE.cls().name();
		String targetClass = targetAE.cls().name();
		String associativeClass = associationClass.name();

		String sourceRole = sourceAE.name();
		String targetRole = targetAE.name();
		// String associativeRole = associationClass.nameAsRolename();

		MMultiplicity sourceMultiplicity = sourceAE.multiplicity();
		MMultiplicity targetMultiplicity = targetAE.multiplicity();

		String targetInterfaceType = JavaTypes.getJavaInterfaceType(targetAE.getType());
		String targetImplementationType = JavaTypes.getJavaImplementationType(targetAE.getType());

		println("/**********************************************************************");
		println("* MEMBER2MEMBER getter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @return the " + targetRole + " of the " + sourceRole);
		println("**********************************************************************/");
		println("public " + targetInterfaceType + " " + targetRole + "()");
		println("{");
		incIndent();
		if (aInfo.getTargetAE().getType().isSet() || aInfo.getTargetAE().getType().isOrderedSet())
		{
			println(targetInterfaceType + " result = new " + targetImplementationType + "();");
			println("for (" + associativeClass + " x : " + associativeClass + ".allInstances)");
			incIndent();
			println("if (x." + sourceRole + "()  ==  this)");
			incIndent();
			println("result.add(x." + targetRole + "());");
			decIndent();
			decIndent();
			println("return result;");
		}
		else
		{
			println("for (" + associativeClass + " x : " + associativeClass + ".allInstances)");
			incIndent();
			println("if (x." + sourceRole + "()  ==  this)");
			incIndent();
			println("return x." + targetRole + "();");
			decIndent();
			decIndent();
			println("return null;");
		}
		decIndent();
		println("}");
		println();

		println("/**********************************************************************");
		println("* MEMBER2MEMBER setter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @param " + targetRole + " the " + targetRole + " to set");
		println("**********************************************************************/");
		println("public void set" + capitalize(targetRole) + "(" + targetInterfaceType + " " + targetRole + ")");
		println("{");
		incIndent();
		if (targetAE.getType().isSet() || targetAE.getType().isOrderedSet())
		{
			println("for (" + targetClass + " t : " + targetRole + ")");
			incIndent();
			println("for (" + associativeClass + " x: " + associativeClass + ".allInstances)");
			incIndent();
			println("if (x." + sourceRole + "() == this)");
			incIndent();
			println("x.set" + capitalize(targetRole) + "(t);");
			decIndent();
			decIndent();
			decIndent();
		}
		else
		{
			println("for (" + associativeClass + " x: " + associativeClass + ".allInstances)");
			incIndent();
			println("if (x." + sourceRole + "() == this)");
			incIndent();
			println("x.set" + capitalize(targetRole) + "(" + targetRole + ");");
			decIndent();
			decIndent();
		}
		decIndent();
		println("}");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printONE2ONE(org.quasar.use.api.implementation.AssociationInfo)
	 */
	@Override
	public void printONE2ONE(AssociationInfo aInfo)
	{
		MAssociationEnd sourceAE = aInfo.getSourceAE();
		MAssociationEnd targetAE = aInfo.getTargetAE();
		// MAssociationClass associationClass = aInfo.getAssociationClass();

		String sourceClass = sourceAE.cls().name();
		String targetClass = targetAE.cls().name();
		// String associativeClass = associationClass.name();

		String sourceRole = sourceAE.name();
		String targetRole = targetAE.name();
		// String associativeRole = associationClass.nameAsRolename();

		MMultiplicity sourceMultiplicity = sourceAE.multiplicity();
		MMultiplicity targetMultiplicity = targetAE.multiplicity();

		String targetInterfaceType = JavaTypes.getJavaInterfaceType(targetAE.getType());
		// String targetImplementationType = JavaTypes.getJavaImplementationType(targetAE.getType());

		String allInstances = aInfo.getTargetAE().cls().isAbstract() ? "allInstances()" : "allInstances";

		println("/**********************************************************************");
		println("* ONE2ONE getter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]");
		println("* @return the " + targetRole + " of the " + sourceRole);
		println("**********************************************************************/");
		println("public " + targetInterfaceType + " " + targetRole + "()");
		println("{");
		incIndent();
		println("for (" + targetInterfaceType + " x : " + targetInterfaceType + "." + allInstances + ")");
		incIndent();
		println("if (x." + sourceRole + "() == this)");
		incIndent();
		println("return x;");
		decIndent();
		decIndent();
		println("return null;");
		decIndent();
		println("}");
		println();

		println("/**********************************************************************");
		println("* ONE2ONE setter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]");
		println("* @param " + targetRole + " the " + targetRole + " to set");
		println("**********************************************************************/");
		println("public void set" + capitalize(targetRole) + "(" + targetClass + " " + targetRole + ")");
		println("{");
		incIndent();
		println(targetRole + ".set" + capitalize(sourceRole) + "(this);");
		decIndent();
		println("}");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printONE2MANY(org.quasar.use.api.implementation.AssociationInfo)
	 */
	@Override
	public void printONE2MANY(AssociationInfo aInfo)
	{
		MAssociationEnd sourceAE = aInfo.getSourceAE();
		MAssociationEnd targetAE = aInfo.getTargetAE();
		// MAssociationClass associationClass = aInfo.getAssociationClass();

		String sourceClass = sourceAE.cls().name();
		String targetClass = targetAE.cls().name();
		// String associativeClass = associationClass.name();

		String sourceRole = sourceAE.name();
		String targetRole = targetAE.name();
		// String associativeRole = associationClass.nameAsRolename();

		MMultiplicity sourceMultiplicity = sourceAE.multiplicity();
		MMultiplicity targetMultiplicity = targetAE.multiplicity();

		String targetInterfaceType = JavaTypes.getJavaInterfaceType(targetAE.getType());
		String targetImplementationType = JavaTypes.getJavaImplementationType(targetAE.getType());

		String allInstances = aInfo.getTargetAE().cls().isAbstract() ? "allInstances()" : "allInstances";

		println("/**********************************************************************");
		println("* ONE2MANY getter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @return the " + targetRole + " of the " + sourceRole);
		println("**********************************************************************/");
		println("public " + targetInterfaceType + " " + targetRole + "()");
		println("{");
		incIndent();
		println(targetInterfaceType + " result = new " + targetImplementationType + "();");
		println("for (" + targetClass + " x : " + targetClass + "." + allInstances + ")");
		incIndent();
		println("if (x." + sourceRole + "()  ==  this)");
		incIndent();
		println("result.add(x);");
		decIndent();
		decIndent();
		println("return result;");
		decIndent();
		println("}");
		println();

		println("/**********************************************************************");
		println("* ONE2MANY setter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @param " + targetRole + " the " + targetRole + " to set");
		println("**********************************************************************/");
		println("public void set" + capitalize(targetRole) + "(" + targetInterfaceType + " " + targetRole + ")");
		println("{");
		incIndent();
		println("for (" + targetClass + " x : " + targetRole + ")");
		incIndent();
		println("x.set" + capitalize(sourceRole) + "(this);");
		decIndent();
		decIndent();
		println("}");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.quasar.use.api.implementation.IJavaVisitor#printNavigatorMANY2MANY(org.quasar.use.api.implementation.AssociationInfo)
	 */
	@Override
	public void printMANY2MANY(AssociationInfo aInfo)
	{
		MAssociationEnd sourceAE = aInfo.getSourceAE();
		MAssociationEnd targetAE = aInfo.getTargetAE();
		// MAssociationClass associationClass = aInfo.getAssociationClass();

		String sourceClass = sourceAE.cls().name();
		String targetClass = targetAE.cls().name();
		// String associativeClass = associationClass.name();

		String sourceRole = sourceAE.name();
		String targetRole = targetAE.name();
		// String associativeRole = associationClass.nameAsRolename();

		MMultiplicity sourceMultiplicity = sourceAE.multiplicity();
		MMultiplicity targetMultiplicity = targetAE.multiplicity();

		String targetInterfaceType = JavaTypes.getJavaInterfaceType(targetAE.getType());
		String targetImplementationType = JavaTypes.getJavaImplementationType(targetAE.getType());

		String allInstances = aInfo.getTargetAE().cls().isAbstract() ? "allInstances()" : "allInstances";

		println("/**********************************************************************");
		println("* MANY2MANY getter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @return the " + targetRole + " of the " + sourceRole);
		println("**********************************************************************/");
		println("public " + targetInterfaceType + " " + targetRole + "()");
		println("{");
		incIndent();
		println(targetInterfaceType + " result = new " + targetImplementationType + "();");
		println("for (" + targetClass + " x : " + targetClass + "." + allInstances + ")");
		incIndent();
		println("if (x." + sourceRole + "().contains(this))");
		incIndent();
		println("result.add(x);");
		decIndent();
		decIndent();
		println("return result;");
		decIndent();
		println("}");
		println();

		println("/**********************************************************************");
		println("* MANY2MANY setter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @param " + targetRole + " the " + targetRole + " to set");
		println("**********************************************************************/");
		println("public void set" + capitalize(targetRole) + "(" + targetInterfaceType + " " + targetRole + ")");
		println("{");
		incIndent();
		println("for (" + targetClass + " x : " + targetRole + ")");
		incIndent();
		println("x." + sourceRole + "().add(this);");
		decIndent();
		decIndent();
		println("}");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printSoilOperation(org.tzi.use.uml.mm.MOperation)
	 */
	@Override
	public void printSoilOperation(MOperation op)
	{
		// visitAnnotations(e);

		println("/**********************************************************************");
		println("* User-defined operation specified in SOIL/OCL");
		for (int i = 0; i < op.paramList().size(); i++)
			println("* @param " + op.paramList().varDecl(i).name() + " the " + op.paramList().varDecl(i).name() + " to set");
		println("**********************************************************************/");
		print("public " + (op.hasResultType() ? JavaTypes.getJavaInterfaceType(op.resultType()) : "void") + " ");
		print(op.name() + "(");
		VarDecl decl = null;
		for (int i = 0; i < op.paramList().size(); i++)
		{
			decl = op.paramList().varDecl(i);
			print(JavaTypes.javaType(decl.type()) + " " + decl.name());
			if (i < op.paramList().size() - 1)
				print(", ");
		}
		println(")");
		println("{");
		incIndent();

		printlnc("TODO");
		if (op.hasExpression())
			printlnc("return " + op.expression().toString());
		else
			if (op.hasStatement())
			{
				String[] temp = op.getStatement().toString().split(";");
				for (int i = 0; i < temp.length; i++)
					printlnc(temp[i] + ";");
			}

		if (op.hasResultType())
			println("return " + JavaTypes.javaDummyValue(op.resultType()) + ";");

		decIndent();
		println("}");
		println();

		if (!op.preConditions().isEmpty())
		{
			printlnc("PRE-CONDITIONS (TODO)");
			println("/*");
			for (MPrePostCondition pre : op.preConditions())
			{
				println("pre " + pre.name());
				incIndent();
				println(pre.expression().toString());
				decIndent();
				println();
			}
			println("*/");
			println();
		}

		if (!op.postConditions().isEmpty())
		{
			printlnc("POST-CONDITIONS (TODO)");
			println("/*");
			for (MPrePostCondition post : op.postConditions())
			{
				println("post " + post.name());
				incIndent();
				println(post.expression().toString());
				decIndent();
				println();
			}
			println("*/");
			println();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printToString(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printToString(MClass theClass)
	{
		println("/* (non-Javadoc)");
		println("* @see java.lang.Object#toString()");
		println("*/");
		println("@Override");
		println("/**********************************************************************");
		println("* Object serializer");
		println("**********************************************************************/");
		println("public String toString()");
		println("{");
		incIndent();
		print("return \"" + theClass.name() + " [");
		List<AttributeInfo> attributes = AttributeInfo.getAttributesInfo(theClass);
		for (int i = 0; i < attributes.size(); i++)
		{
			print(attributes.get(i).getName() + "=\" + " + attributes.get(i).getName() + " + \"");
			if (i < attributes.size() - 1)
				print(", ");
		}
		println("]\\n\";");
		decIndent();
		println("}");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.implementation.JavaVisitor#printInvariants(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printInvariants(MClass theClass)
	{
		if (!model.classInvariants(theClass).isEmpty())
		{
			printlnc("-------------------------------------------------------------------------------");
			printlnc("INVARIANTS (TODO)");
			println("/*");
			for (MClassInvariant inv : model.classInvariants(theClass))
			{
				println("inv " + inv.name());
				incIndent();
				println(inv.bodyExpression().toString());
				decIndent();
				println();
			}
			println("*/");
		}
	}
}