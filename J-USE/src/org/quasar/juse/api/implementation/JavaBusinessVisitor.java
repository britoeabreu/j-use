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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.expr.*;
import org.tzi.use.uml.ocl.type.*;
import org.tzi.use.util.StringUtil;

public class JavaBusinessVisitor extends JavaVisitor
{
	private MModel			model;
	private String			author;
	private String			basePackageName;
	private String			businessLayerName;
	private String			persistenceLayerName;
	private String			presentationLayerName;
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
	 * @param persistenceLayerName
	 *            Relative name of the layer package where the source code for the persistence layer is to be placed
	 * @param presentationLayerName
	 *            Relative name of the layer package where the source code for the presentation layer is to be placed
	 ***********************************************************/
	public JavaBusinessVisitor(MModel model, String author, String basePackageName, String businessLayerName,
					String persistenceLayerName, String presentationLayerName)
	{
		this.model = model;
		this.author = author;
		this.basePackageName = basePackageName;
		this.businessLayerName = businessLayerName;
		this.persistenceLayerName = persistenceLayerName;
		this.presentationLayerName = presentationLayerName;
		this.util = new ModelUtilities(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printFileHeader(java.lang.String)
	 */
	@Override
	public void printFileHeader(String typeName, String layerName)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		// get current date time with Date()
		Date date = new Date();

		println("/**********************************************************************");
		println("* Filename: " + typeName + ".java");
		println("* Created: " + dateFormat.format(date));
		println("* @author " + author);
		println("**********************************************************************/");
		println("package " + basePackageName + "." + layerName + ";");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printEnumType(org.tzi.use.uml.ocl.type.EnumType)
	 */
	@Override
	public void printEnumType(EnumType t, String layerName)
	{
		printFileHeader(t.name(), layerName);
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
				println("private " + JavaTypes.javaInterfaceType(attribute.getType()) + " " + attribute.getName() + " = "
								+ " new " + JavaTypes.javaImplementationType(attribute.getType()) + "();");
			else
				println("private " + JavaTypes.javaInterfaceType(attribute.getType()) + " " + attribute.getName() + ";");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printClassHeader(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printClassHeader(MClass theClass, String layerName)
	{
		printFileHeader(theClass.name(), layerName);
		// visitAnnotations(e);

		printImports(theClass);

		print("public ");
		if (theClass.isAbstract())
			print("abstract ");
		print("class " + theClass.name());

		Set<MClass> parents = theClass.parents();
		if (!parents.isEmpty())
			print(" extends " + StringUtil.fmtSeq(parents.iterator(), ","));
		
		println(" implements Comparable<Object>");
		println("{");
	}

	/***********************************************************
	 * @param theClass
	 ***********************************************************/
	private void printImports(MClass theClass)
	{
		println("import " + basePackageName + "." + persistenceLayerName + ".Database;");
		println();

		Set<Type> classTypes = new HashSet<Type>();

		for (AttributeInfo attribute : AttributeInfo.getAttributesInfo(theClass))
			if (attribute.getKind() != AssociationKind.ASSOCIATIVE2MEMBER)
				classTypes.add(attribute.getType());

		for (AssociationInfo association : AssociationInfo.getAssociationsInfo(theClass))
			if (association.getKind() != AssociationKind.ASSOCIATIVE2MEMBER)
				classTypes.add(association.getTargetAE().getType());

		for (MOperation operation : theClass.allOperations())
		{
			classTypes.add(operation.resultType());
			for (VarDecl v : operation.paramList())
				classTypes.add(v.type());
		}

		// System.out.println("-------------------------" + theClass.name()
		// + "..........................................................");
		// for (Type oclType : classTypes)
		// System.out.println(oclType);
		// System.out.println();

		Set<String> imports = JavaTypes.javaImportDeclarations(classTypes);

		for (String importDeclaration : imports)
			println(importDeclaration);
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printAllInstances(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printAllInstances(MClass theClass)
	{
		println();
		println("/***********************************************************");
		println("* @return all instances of class " + theClass.name());
		println("***********************************************************/");
		print("public static Set<" + theClass.name() + "> ");
		println(theClass.isAbstract() ? "allInstancesAbstract()" : "allInstances()");
		println("{");
		incIndent();
		println("return Database.allInstances(" + theClass.name() + ".class);");
		decIndent();
		println("}");
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
		decIndent();
		println("}");
		println();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.implementation.IJavaVisitor#printAssociativeConstructors(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printAssociativeConstructor(MClass theClass)
	{
		println("/**********************************************************************");
		println("* Associative constructor");
		for (AttributeInfo attribute : AttributeInfo.getAttributesInfo(theClass))
			if (attribute.getKind() == AssociationKind.ASSOCIATIVE2MEMBER)
				println("* @param " + attribute.getName() + " the " + attribute.getName() + " to initialize");
		println("**********************************************************************/");

		print("public " + theClass.name() + "(");

		List<AttributeInfo> attributes = AttributeInfo.getAttributesInfo(theClass);
		boolean first = true;
		for (int i = 0; i < attributes.size(); i++)
		{
			if (attributes.get(i).getKind() == AssociationKind.ASSOCIATIVE2MEMBER)
			{
				print(JavaTypes.javaInterfaceType(attributes.get(i).getType()) + " " + attributes.get(i).getName());
				if (first)
				{
					print(", ");
					first = false;
				}
			}
		}
		println(")");
		println("{");
		incIndent();

		for (AttributeInfo attribute : AttributeInfo.getAttributesInfo(theClass))
			if (attribute.getKind() == AssociationKind.ASSOCIATIVE2MEMBER)
				println("this." + attribute.getName() + " = " + attribute.getName() + ";");

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

		if (inheritedAttributes.size() + theClass.attributes().size() == 0)
			return;

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
			print(JavaTypes.javaInterfaceType(inheritedAttributes.get(i).getType()) + " "
							+ inheritedAttributes.get(i).getName());
			if (i < inheritedAttributes.size() - 1)
				print(", ");
		}

		List<AttributeInfo> attributes = AttributeInfo.getAttributesInfo(theClass);
		if (inheritedAttributes.size() > 0 && attributes.size() > 0)
			print(", ");
		for (int i = 0; i < attributes.size(); i++)
		{
			print(JavaTypes.javaInterfaceType(attributes.get(i).getType()) + " " + attributes.get(i).getName());
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
			default:
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
			println("public " + JavaTypes.javaInterfaceType(currentAttribute.getType()) + " " + currentAttribute.getName()
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
							+ JavaTypes.javaInterfaceType(currentAttribute.getType()) + " " + currentAttribute.getName() + ")");
			println("{");
			incIndent();
			println("this." + currentAttribute.getName() + " = " + currentAttribute.getName() + ";");
			decIndent();
			println("}");
			println();

			if (currentAttribute.getKind() == AssociationKind.MANY2MANY)
			{
				// String otherType = JavaTypes.javaPrimitiveType(currentAttribute.getType());
				// String otherType = JavaTypes.getJavaInterfaceType(currentAttribute.getType());
				String otherType = JavaTypes.oclCollectionInnerType(((CollectionType) currentAttribute.getType())).shortName();
				String otherName = otherType.toLowerCase();
				printHeaderBasicGettersSetters(theClass, currentAttribute, "single setter");
				println("* @param " + otherName + " the " + otherName + " to add");
				println("**********************************************************************/");
				println("public void add" + capitalize(currentAttribute.getName()) + "(" + otherType + " " + otherName + ")");
				println("{");
				incIndent();
				println("this." + currentAttribute.getName() + ".add(" + otherName + ");");
				decIndent();
				println("}");
				println();
			}
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
			print("for (" + associativeClass + " x : " + associativeClass);
			println(associationClass.isAbstract() ? ".allInstancesAbstract())" : ".allInstances())");
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
			print("for (" + associativeClass + " x : " + associativeClass);
			println(associationClass.isAbstract() ? ".allInstancesAbstract())" : ".allInstances())");
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

		String targetInterfaceType = JavaTypes.javaInterfaceType(targetAE.getType());
		String targetImplementationType = JavaTypes.javaImplementationType(targetAE.getType());

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
			print("for (" + associativeClass + " x : " + associativeClass);
			println(associationClass.isAbstract() ? ".allInstancesAbstract())" : ".allInstances())");
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
			print("for (" + associativeClass + " x : " + associativeClass);
			println(associationClass.isAbstract() ? ".allInstancesAbstract())" : ".allInstances())");
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
			print("for (" + associativeClass + " x : " + associativeClass);
			println(associationClass.isAbstract() ? ".allInstancesAbstract())" : ".allInstances())");
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
			print("for (" + associativeClass + " x : " + associativeClass);
			println(associationClass.isAbstract() ? ".allInstancesAbstract())" : ".allInstances())");
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

		String targetInterfaceType = JavaTypes.javaInterfaceType(targetAE.getType());
		// String targetImplementationType = JavaTypes.getJavaImplementationType(targetAE.getType());

		// String allInstances = aInfo.getTargetAE().cls().isAbstract() ? "allInstances()" : "allInstances";

		println("/**********************************************************************");
		println("* ONE2ONE getter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]");
		println("* @return the " + targetRole + " of the " + sourceRole);
		println("**********************************************************************/");
		println("public " + targetInterfaceType + " " + targetRole + "()");
		println("{");
		incIndent();
		// println("for (" + targetInterfaceType + " x : " + targetInterfaceType + "." + allInstances + ")");
		print("for (" + targetInterfaceType + " x : " + targetInterfaceType);
		println(targetAE.cls().isAbstract() ? ".allInstancesAbstract())" : ".allInstances())");
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

		String targetInterfaceType = JavaTypes.javaInterfaceType(targetAE.getType());
		String targetImplementationType = JavaTypes.javaImplementationType(targetAE.getType());

		// String allInstances = aInfo.getTargetAE().cls().isAbstract() ? "allInstances()" : "allInstances";

		println("/**********************************************************************");
		println("* ONE2MANY getter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @return the " + targetRole + " of the " + sourceRole);
		println("**********************************************************************/");
		println("public " + targetInterfaceType + " " + targetRole + "()");
		println("{");
		incIndent();
		println(targetInterfaceType + " result = new " + targetImplementationType + "();");
		print("for (" + targetClass + " x : " + targetClass);
		println(targetAE.cls().isAbstract() ? ".allInstancesAbstract())" : ".allInstances())");
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
		println("* ONE2MANY multiple setter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
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

		println("/**********************************************************************");
		println("* ONE2MANY single setter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @param " + targetClass.toLowerCase() + " the " + targetClass.toLowerCase() + " to add");
		println("**********************************************************************/");
		println("public void add" + capitalize(targetRole) + "(" + targetClass + " " + targetClass.toLowerCase() + ")");
		println("{");
		incIndent();
		println(targetClass.toLowerCase() + ".set" + capitalize(sourceRole) + "(this);");
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

		String targetInterfaceType = JavaTypes.javaInterfaceType(targetAE.getType());
		String targetImplementationType = JavaTypes.javaImplementationType(targetAE.getType());

		// String allInstances = aInfo.getTargetAE().cls().isAbstract() ? "allInstances()" : "allInstances";

		println("/**********************************************************************");
		println("* MANY2MANY getter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @return the " + targetRole + " of the " + sourceRole);
		println("**********************************************************************/");
		println("public " + targetInterfaceType + " " + targetRole + "()");
		println("{");
		incIndent();
		println(targetInterfaceType + " result = new " + targetImplementationType + "();");
		print("for (" + targetClass + " x : " + targetClass);
		println(targetAE.cls().isAbstract() ? ".allInstancesAbstract())" : ".allInstances())");
		incIndent();
		println("if (x." + sourceRole + "() != null && x." + sourceRole + "().contains(this))");
		incIndent();
		println("result.add(x);");
		decIndent();
		decIndent();
		println("return result;");
		decIndent();
		println("}");
		println();

		println("/**********************************************************************");
		println("* MANY2MANY multiple setter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
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

		println("/**********************************************************************");
		println("* MANY2MANY single setter for " + sourceClass + "[" + sourceMultiplicity + "] <-> " + targetClass + "["
						+ targetMultiplicity + "]" + (targetAE.getType().isOrderedSet() ? " ordered" : ""));
		println("* @param " + targetClass.toLowerCase() + " the " + targetClass.toLowerCase() + " to add");
		println("**********************************************************************/");
		println("public void add" + capitalize(targetRole) + "(" + targetClass + " " + targetClass.toLowerCase() + ")");
		println("{");
		incIndent();
		println(targetClass.toLowerCase() + ".add" + capitalize(sourceRole) + "(this);");
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
		print("public " + (op.hasResultType() ? JavaTypes.javaInterfaceType(op.resultType()) : "void") + " ");
		print(op.name() + "(");
		VarDecl decl = null;
		for (int i = 0; i < op.paramList().size(); i++)
		{
			decl = op.paramList().varDecl(i);
			print(JavaTypes.javaInterfaceType(decl.type()) + " " + decl.name());
			if (i < op.paramList().size() - 1)
				print(", ");
		}
		println(")");
		println("{");
		incIndent();

		if (op.hasExpression())
		{
			printlnc("TODO");
			printlnc("return " + op.expression().toString());
		}
		else
		{
			if (op.hasStatement())
			{
				printlnc("" + op.getStatement());
				// printlnc(op.getStatement().toConcreteSyntax(4, 4));
				// String[] temp = op.getStatement().toString().split(";");
				// for (int i = 0; i < temp.length; i++)
				// printlnc(temp[i] + ";");
			}
			if (op.hasExpression())
				printlnc("" + op.expression());
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
		// println("/* (non-Javadoc)");
		// println("* @see java.lang.Object#toString()");
		// println("*/");
		// println("@Override");
		println("/**********************************************************************");
		println("* Object serializer");
		println("**********************************************************************/");
		println("public String toString()");
		println("{");
		incIndent();
		print("return \"" + theClass.name() + " [");
		if (theClass.allParents().size() > 0)
			print("\" + super.toString() + \" ");
		List<AttributeInfo> attributes = AttributeInfo.getAttributesInfo(theClass);
		for (int i = 0; i < attributes.size(); i++)
		{
			print(attributes.get(i).getName() + "=\" + " + attributes.get(i).getName() + " + \"");
			if (i < attributes.size() - 1)
				print(", ");
		}
		println("]\";");
		decIndent();
		println("}");
		println();
	}

	/* (non-Javadoc)
	 * @see org.quasar.juse.api.implementation.IJavaVisitor#printCompareTo(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public void printCompareTo(MClass theClass)
	{
		println("/**********************************************************************");
		println("* @param other " + theClass.name() + " to compare to the current one");
		println("* @return");
		println("**********************************************************************/");
		println("public int compareTo(Object other)");
		println("{");
		incIndent();
		println("return this.hashCode() - ((" + theClass.name() +") other).hashCode();");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.implementation.JavaVisitor#printTupleTypes(java.lang.Integer, java.lang.String)
	 */
	@Override
	public void printTupleTypes(Integer parameterNumber, String layerName)
	{
		printFileHeader("Tuple" + parameterNumber, businessLayerName);

		print("public class Tuple" + parameterNumber + "<");
		for (int i = 0; i < parameterNumber; i++)
		{
			print("T" + i);
			if (i < parameterNumber - 1)
				print(", ");
		}
		println(">");

		println("{");
		incIndent();

		for (int i = 0; i < parameterNumber; i++)
			println("private T" + i + " t" + i + ";");
		println();

		println("/***********************************************************");
		for (int i = 0; i < parameterNumber; i++)
			println("* @param t" + i);
		println("***********************************************************/");
		print("public Tuple" + parameterNumber + "(");
		for (int i = 0; i < parameterNumber; i++)
		{
			print("T" + i + " t" + i);
			if (i < parameterNumber - 1)
				print(", ");
		}
		println(")");
		println("{");
		incIndent();
		for (int i = 0; i < parameterNumber; i++)
			println("this.t" + i + "= t" + i + ";");
		decIndent();
		println("}");
		println();

		for (int i = 0; i < parameterNumber; i++)
		{
			println("/***********************************************************");
			println("* @return the t" + i);
			println("***********************************************************/");
			println("public T" + i + " getT" + i + "()");
			println("{");
			incIndent();
			println("return t" + i + ";");
			decIndent();
			println("}");
			println();
		}

		for (int i = 0; i < parameterNumber; i++)
		{
			println("/***********************************************************");
			println("* @param t" + i + " the t" + i + " to set");
			println("***********************************************************/");
			println("public void setT" + i + "(T" + i + " t" + i + ")");
			println("{");
			incIndent();
			println("this.t" + i + " = t" + i + ";");
			decIndent();
			println("}");
			println();
		}

		decIndent();
		println("}");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.implementation.JavaVisitor#printMain()
	 */
	@Override
	public void printMain()
	{
		printFileHeader("Main_" + model.name(), presentationLayerName);

		println("import " + basePackageName + "." + businessLayerName + ".*;");
		println("import " + basePackageName + "." + persistenceLayerName + ".Database;");
		println();
		println("import java.util.Scanner;");
		println();

		println("public abstract class Main_", model.name());
		println("{");
		incIndent();
		println("/***********************************************************");
		println("* @param args");
		println("***********************************************************/");
		println("public static void main(String[] args)");
		println("{");
		incIndent();
		println("Database.open(\"database\", \"" + model.name() + "\", \"db4o\");");
		println();
		println("boolean over = false;");
		println();
		println("do");
		println("{");
		incIndent();
		println("Scanner in = new Scanner(System.in);");
		println();
		println("displayMenu();");
		println();
		println("int option;");
		println("try");
		println("{");
		incIndent();
		println("String answer = in.next();");
		println("option = Integer.parseInt(answer);");
		decIndent();
		println("}");
		println("catch (NumberFormatException e)");
		println("{");
		incIndent();
		println("System.out.println(\"Invalid input!...\\n\");");
		println("continue;");
		decIndent();
		println("}");
		println();
		println("switch (option)");
		println("{");
		incIndent();
		println("case 0:");
		incIndent();
		println("over = true;");
		println("break;");
		decIndent();
		int i = 1;
		for (MClass cls : model.classes())
		{
			println("case " + i + ":");
			incIndent();
			println("showResults(" + cls.name() + ".class);");
			println("break;");
			decIndent();
			i++;
		}
		println("default:");
		incIndent();
		println("System.out.println(\"Invalid option!...\\n\");");
		println("break;");
		decIndent();
		println("}");
		decIndent();
		println("}");
		println("while (!over);");
		println();
		println("Database.close();");
		decIndent();
		println("}");
		println();
		println("/***********************************************************");
		println("* The main menu of the " + model.name() + " information system");
		println("***********************************************************/");
		println("public static void displayMenu()");
		println("{");
		incIndent();
		println("System.out.println(\"------------------------------------\");");
		println("System.out.println(\"" + model.name() + " Information System\");");
		println("System.out.println(\"------------------------------------\");");
		println("System.out.println(\"0) EXIT\");");
		i = 1;
		for (MClass cls : model.classes())
		{
			println("System.out.println(\"" + i + ") " + cls.name() + "\");");
			i++;
		}

		println("System.out.println();");
		println("System.out.print(\"OPTION> \");");
		decIndent();
		println("}");
		println();
		println("/***********************************************************");
		println("* @param c the class whose instances we want to show");
		println("***********************************************************/");
		println("public static void showResults(Class<?> c)");
		println("{");
		incIndent();
		println("System.out.println(\"---------------------------------------------------------------------------------------------------------------------\");");
		println("System.out.println(\"| \" + Database.allInstances(c).size() + \" instances of class \" + c.getSimpleName());");
		println("System.out.println(\"---------------------------------------------------------------------------------------------------------------------\");");
		println("for (Object o : Database.allInstances(c))");
		incIndent();
		println("System.out.println(o);");
		decIndent();
		println("System.out.println();");
		decIndent();
		println("}");
		decIndent();
		println("}");
	}
}