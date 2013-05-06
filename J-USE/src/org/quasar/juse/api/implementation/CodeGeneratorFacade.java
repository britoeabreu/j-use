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

import org.quasar.juse.api.JUSE_CodeGeneratorFacade;

import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.type.EnumType;

/***********************************************************
 * @author fba 25 de Abr de 2012
 * 
 ***********************************************************/
public class CodeGeneratorFacade extends BasicFacade implements JUSE_CodeGeneratorFacade
{

	public CodeGeneratorFacade()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.juse.api.JUSE_CodeGeneratorFacade#javaGeneration(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void javaGeneration(String author, String javaWorkspace, String basePackageName, String businessLayerName,
					String presentationLayerName)
	{
		if (getSystem().model() == null)
		{
			System.out.println("Please compile the specification first!");
			return;
		}

		// AssociationInfo.testGetAssociationInfo(model);
		// ModelUtilities mu = new ModelUtilities(model);
		// mu.printModelUtilities();

		System.out.println("Java plugin for USE version 1.0.6, Copyright (C) 2012-2013 QUASAR Group");
		System.out.println("generating Java code ...");

		JavaVisitor visitor = new JavaBusinessVisitor(getSystem().model(), author, basePackageName, businessLayerName);

		String targetDirectory = javaWorkspace + "/" + getSystem().model().name() + "/src/" + basePackageName.replace('.', '/') + "/"
						+ businessLayerName;

		// visitAnnotations(e);

		// print user-defined data types
		for (EnumType t : getSystem().model().enumTypes())
		{
			if (visitor.openOutputFile(targetDirectory, t.name() + ".java"))
			{
				// visitAnnotations(t);
				visitor.printEnumType(t);
				visitor.println();
				visitor.closeOutputFile();
			}
		}

		// visit classes
		for (MClass cls : getSystem().model().classes())
		{
			if (visitor.openOutputFile(targetDirectory, cls.name() + ".java"))
			{
				visitor.printClassHeader(cls);

				visitor.incIndent();

				visitor.printAllInstances(cls);

				visitor.printAttributes(cls);

				if (cls instanceof MAssociationClass)
					visitor.printAssociativeConstructor(cls);
				else
					visitor.printDefaultConstructor(cls);
					
				visitor.printParameterizedConstructor(cls);

				visitor.printBasicGettersSetters(cls);

				visitor.printNavigators(cls);

				for (MOperation op : cls.operations())
					visitor.printSoilOperation(op);

				visitor.printToString(cls);

				visitor.printInvariants(cls);

				visitor.decIndent();
				visitor.println("}");

				visitor.closeOutputFile();

				// System.out.println(cls.name() + " ... done!");
			}
		}
		
		if (visitor.openOutputFile(targetDirectory, "Main_" + getSystem().model().name()+ ".java"))
		{
			visitor.printMain();
		}
		
		ModelUtilities util = new ModelUtilities(getSystem().model());
		System.out.println("Java project " + getSystem().model().name() + " (" + util.numberClasses() + " classes, "
						+ util.numberAttributes() + " attributes, " + util.numberOperations() + " operations)");

		System.out.println("Java code generation concluded!\n");
	}

}