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

import org.quasar.juse.api.JUSE_BasicFacade;
import org.quasar.juse.api.JUSE_CodeGeneratorFacade;
import org.quasar.juse.api.JUSE_ProgramingFacade;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.config.Options;
import org.tzi.use.main.Session;
import org.tzi.use.main.shell.Shell;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MElementAnnotation;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.MSystemState.DeleteObjectResult;
import org.tzi.use.util.Log;
import org.tzi.use.util.USEWriter;

/***********************************************************
* @author fba
* 25 de Abr de 2012
*
***********************************************************/
public class JUSEfacadeImplementation implements JUSE_ProgramingFacade, JUSE_CodeGeneratorFacade
{
	private Session	session	= new Session();
	private MModel	model	= null;
	private MSystem	system	= null;
	private Shell	shell	= null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#initialize(java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	public JUSE_BasicFacade initialize(String[] args, String useBaseDirectory, String modelDirectory)
	{
		// set System.out to the OldUSEWriter to protocol the output.
		System.setOut(USEWriter.getInstance().getOut());
		// set System.err to the OldUSEWriter to protocol the output.
		System.setErr(USEWriter.getInstance().getErr());

		String[] initialArgs = { "-H=" + useBaseDirectory};

		// Command line arguments or in Eclipse "Run As/Run Configurations/Arguments" are appended with 
		// the initialArgs (USE instalation directory)

			String[] args2 = new String[args.length+1];
			for (int i=0; i<args.length; i++)
				args2[i] = args[i];
			args2[args.length] = initialArgs[0];
			
		// read and set global options, setup application properties
		Options.processArgs(args2);

		Options.doGUI = false;
		Options.doPLUGIN = false;

		// System.out.println("user.dir=" + System.getProperty("user.dir"));
		// System.out.println("lastDirectory=" + Options.getLastDirectory());

		// set current model directory
		if (modelDirectory != null)
		{
			System.setProperty("user.dir", modelDirectory);
			Options.setLastDirectory(modelDirectory);
		}
		// System.out.println("user.dir=" + System.getProperty("user.dir"));
		// System.out.println("lastDirectory=" + Options.getLastDirectory());

		// compile spec if filename given as command line argument
		if (Options.specFilename != null)
			compileSpecification(Options.specFilename);
		
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#compileSpecification(java.lang.String)
	 */
	@Override
	public MSystem compileSpecification(String specificationFilename)
	{
		// compile spec if filename given as argument
		if (specificationFilename != null)
		{
			specificationFilename = System.getProperty("user.dir") + "/" + specificationFilename;

			FileInputStream specStream = null;
			try
			{
				Log.verbose("compiling specification " + specificationFilename);
				specStream = new FileInputStream(specificationFilename);
				model = USECompiler.compileSpecification(specStream, specificationFilename, new PrintWriter(System.err),
								new ModelFactory());
			}
			catch (FileNotFoundException e)
			{
				Log.error("File `" + specificationFilename + "' not found.");
				System.exit(1);
			}
			finally
			{
				if (specStream != null)
					try
					{
						specStream.close();
					}
					catch (IOException ex)
					{
						// ignored
					}
			}

			// compile errors?
			if (model == null)
			{
				System.exit(1);
			}

			if (Options.compileOnly)
			{
				Log.verbose("no errors.");
				if (Options.compileAndPrint)
				{
					model = USECompiler.compileSpecification(specStream, specificationFilename, new PrintWriter(System.err),
									new ModelFactory());
				}
				System.exit(0);
			}

			// print some info about model
			Log.verbose(model.getStats());
		}

		else
		{
			model = new ModelFactory().createModel("empty model");
			Log.verbose("using empty model.");
		}
		// create system, session and shell
		system = new MSystem(model);

		session.setSystem(system);
		Shell.createInstance(session, null);
		shell = Shell.getInstance();

		return system;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#readSOIL(java.lang.String, boolean)
	 */
	@Override
	public void readSOIL(String modelInstancesFilename, boolean quiet)
	{
		if (model == null)
		{
			System.out.println("Please compile the specification first!");
			return;
		}

		modelInstancesFilename = System.getProperty("user.dir") + "/" + modelInstancesFilename;

		Options.quiet = quiet;

		System.out.println("readSOIL (" + modelInstancesFilename + ")");

		// Unfortunately none of these 2 simple options work :( ...
		// shell.cmdRead(modelInstancesFilename, true);
		// shell.processLineSafely("readq " + modelInstancesFilename);

		// ... so let's do it the hard way ...
		FileReader fr = null;
		try
		{
			fr = new FileReader(modelInstancesFilename);

			BufferedReader br = new BufferedReader(fr);
			String s = null;
			try
			{
				while ((s = br.readLine()) != null)
				{
					if (!quiet)
						System.out.println(s);
					shell.processLineSafely(s);
				}
				fr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#dumpState(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void dumpState(String author, String javaWorkspace, String cmdFile, boolean verbose)
	{
		if (model == null)
		{
			System.out.println("Please compile the specification first!");
			return;
		}

		System.out.println("Dumping model snapshot to " + cmdFile);

		String targetDirectory = javaWorkspace + "/" + model.name() + "/data";

		MSystemState systemState = system.state();
		FileUtilities fileUtilities = new FileUtilities();
		String command;

		fileUtilities.openOutputFile(targetDirectory, cmdFile);

		fileUtilities.println("-------------------------------------------------------------------");
		fileUtilities.println("-- author: " + author);
		fileUtilities.println("-------------------------------------------------------------------");
		fileUtilities.println("reset");
		fileUtilities.println();

		// generate regular objects
		for (MObject theObject : systemState.allObjects())
			if (!(theObject instanceof MLink))
			{
				command = "!create " + theObject.name() + ": " + theObject.cls().name();
				fileUtilities.println(command);
				if (verbose)
					System.out.println(command);
			}

		fileUtilities.println("-------------------------------------------------------------------");

		// generate regular link objects whose connected objects are regular objects
		for (MObject theObject : systemState.allObjects())
			if (theObject instanceof MLink)
			{
				MLink theLink = (MLink) theObject;

				if (!(theLink.linkedObjects().get(0) instanceof MLink) && !(theLink.linkedObjects().get(1) instanceof MLink))
				{
					command = "!create " + theObject.name() + ": " + theObject.cls().name() + " between ("
									+ theLink.linkedObjects().get(0).name() + ", " + theLink.linkedObjects().get(1).name()
									+ ")";
					fileUtilities.println(command);
					if (verbose)
						System.out.println(command);
				}
			}

		// generate regular link objects whose connected objects are link objects
		for (MObject theObject : systemState.allObjects())
			if (theObject instanceof MLink)
			{
				MLink theLink = (MLink) theObject;

				if (theLink.linkedObjects().get(0) instanceof MLink || theLink.linkedObjects().get(1) instanceof MLink)
				{
					command = "!create " + theObject.name() + ": " + theObject.cls().name() + " between ("
									+ theLink.linkedObjects().get(0).name() + ", " + theLink.linkedObjects().get(1).name()
									+ ")";
					fileUtilities.println(command);
					if (verbose)
						System.out.println(command);
				}
			}

		fileUtilities.println("-------------------------------------------------------------------");

		// generate regular links
		for (MLink theLink : systemState.allLinks())
			if (!(theLink instanceof MObject))
			{
				command = "!insert (" + theLink.linkedObjects().get(0).name() + ", " + theLink.linkedObjects().get(1).name()
								+ ") into " + theLink.association().name();
				fileUtilities.println(command);
				if (verbose)
					System.out.println(command);
			}

		fileUtilities.println("-------------------------------------------------------------------");

		// set objects state
		for (MObject theObject : systemState.allObjects())
		{
			MObjectState objectState = theObject.state(systemState);
			for (MAttribute attribute : theObject.cls().allAttributes())
			{
				command = "!set " + theObject.name() + "." + attribute.name() + " := " + objectState.attributeValue(attribute);
				fileUtilities.println(command);
				if (verbose)
					System.out.println(command);
			}
		}

		fileUtilities.closeOutputFile();

		// print some info about snapshot
		System.out.println("Specification " + model.name() + " snapshot (" + systemState.allObjects().size() + " objects, "
						+ systemState.allLinks().size() + " links)");

		System.out.println("Model snapshot dump concluded!\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#command(java.lang.String)
	 */
	@Override
	public void command(String commandLine)
	{
		shell.processLineSafely(commandLine);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#createShell()
	 */
	@Override
	public void createShell()
	{
		Thread t = new Thread(shell);
		t.start();

		// wait on exit from shell (this thread never returns)
		try
		{
			t.join();
		}
		catch (InterruptedException ex)
		{
			// ignored
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#javaGeneration(java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void javaGeneration(String author, String javaWorkspace, String basePackageName, String businessLayerName,
					String presentationLayerName)
	{
		if (model == null)
		{
			System.out.println("Please compile the specification first!");
			return;
		}

		// AssociationInfo.testGetAssociationInfo(model);
		// ModelUtilities mu = new ModelUtilities(model);
		// mu.printModelUtilities();

		System.out.println("Java plugin for USE version 1.0.0, Copyright (C) 2012 QUASAR Group");
		System.out.println("generating Java code ...");

		JavaVisitor visitor = new JavaBusinessVisitor(model, author, basePackageName, businessLayerName);

		String targetDirectory = javaWorkspace + "/" + model.name() + "/src/" + basePackageName.replace('.', '/') + "/"
						+ businessLayerName;

		// visitAnnotations(e);

		// print user-defined data types
		for (EnumType t : model.enumTypes())
		{
			if (visitor.openOutputFile(targetDirectory, t.name() + ".java"))
			{
				// visitAnnotations(t);
				visitor.printEnumType(t);
				visitor.println();
			}
		}

		// visit classes
		for (MClass cls : model.classes())
		{
			if (visitor.openOutputFile(targetDirectory, cls.name() + ".java"))
			{
				visitor.printClassHeader(cls);

				visitor.incIndent();

				visitor.printAllInstances(cls);

				visitor.printAttributes(cls);

				visitor.printDefaultConstructor(cls);

				visitor.printParameterizedConstructor(cls);

				visitor.printBasicGettersSetters(cls);

				visitor.printNavigators(cls);

				for (MOperation op : cls.operations())
					visitor.printSoilOperation(op);

				visitor.printToString(cls);

				visitor.decIndent();
				visitor.println("}");

				visitor.closeOutputFile();

				// System.out.println(cls.name() + " ... done!");
			}
		}
		ModelUtilities util = new ModelUtilities(model);
		System.out.println("Java project " + model.name() + " (" + util.numberClasses() + " classes, "
						+ util.numberAttributes() + " attributes, " + util.numberOperations() + " operations)");

		System.out.println("Java code generation concluded!\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#createObject(java.lang.String, java.lang.String)
	 */
	@Override
	public MObject createObject(String objectId, MClass theClass)
	{
		assert theClass != null;

		MObject result = null;
		try
		{
			result = system.state().createObject(theClass, objectId);
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
	 * @see org.quasar.use.api.USEfacade#deleteObject(org.tzi.use.uml.sys.MObject)
	 */
	@Override
	public DeleteObjectResult deleteObject(MObject theObject)
	{
		assert theObject != null;

		return system.state().deleteObject(theObject);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#createLinkObject(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public MLinkObject createLinkObject(String objectId, MAssociationClass theAssociativeClass, List<MObject> members)
	{
		assert theAssociativeClass != null;
		assert members != null;
		assert members.size() == 2;

		MLinkObject result = null;
		try
		{
			result = system.state().createLinkObject(theAssociativeClass, objectId, members, null);
		}
		catch (MSystemException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.quasar.use.api.USEfacade#deleteLinkObject(org.tzi.use.uml.sys.MLinkObject)
	 */
	@Override
	public DeleteObjectResult deleteLinkObject(MLinkObject theLinkObject)
	{
		assert theLinkObject != null;

		return system.state().deleteObject(theLinkObject);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#setObjectAttribute(java.lang.String, java.lang.String, org.tzi.use.uml.ocl.value.Value)
	 */
	@Override
	public void setObjectAttribute(MObject theObject, MAttribute theAttribute, Value attributeValue)
	{
		assert theObject != null;
		assert theAttribute != null;

			theObject.state(system.state()).setAttributeValue(theAttribute, attributeValue);
	}

	/* (non-Javadoc)
	 * @see org.quasar.use.api.USEfacade#getObjectAttribute(org.tzi.use.uml.sys.MObject, org.tzi.use.uml.mm.MAttribute)
	 */
	@Override
	public Value getObjectAttribute(MObject theObject, MAttribute theAttribute)
	{
		assert theObject != null;
		assert theAttribute != null;
		
		return theObject.state(system.state()).attributeValue(theAttribute);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#createLink(java.lang.String, java.util.List)
	 */
	@Override
	public MLink createLink(MAssociation theAssociation, List<MObject> members)
	{
		assert theAssociation != null;
		assert members != null;
		assert members.size() == 2;

		MLink result = null;
		try
		{
			result = system.state().createLink(theAssociation, members, null);
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
	 * @see org.quasar.use.api.USEfacade#deleteLink(java.lang.String, java.util.List)
	 */
	@Override
	public DeleteObjectResult deleteLink(MAssociation theAssociation, List<MObject> members)
	{
		assert theAssociation != null;
		assert members != null;
		assert members.size() == 2;

		DeleteObjectResult result = null;
		try
		{
			result = system.state().deleteLink(theAssociation, members, null);
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
	 * @see org.quasar.use.api.USEfacade#objectByName(java.lang.String)
	 */
	@Override
	public MObject objectByName(String objectId)
	{
		return system.state().objectByName(objectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#classByName(java.lang.String)
	 */
	@Override
	public MClass classByName(String className)
	{
		return model.getClass(className);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#associationClassByName(java.lang.String)
	 */
	@Override
	public MAssociationClass associationClassByName(String associationClassName)
	{
		return model.getAssociationClass(associationClassName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quasar.use.api.USEfacade#associationByName(java.lang.String)
	 */
	@Override
	public MAssociation associationByName(String associationName)
	{
		return model.getAssociation(associationName);
	}

	/* (non-Javadoc)
	 * @see org.quasar.use.api.USEfacade#attributeByName(org.tzi.use.uml.sys.MObject, java.lang.String)
	 */
	@Override
	public MAttribute attributeByName(MObject theObject, String attributeName)
	{
		assert theObject != null;

		return theObject.cls().attribute(attributeName, true);
	}
	
	
	/* (non-Javadoc)
	 * @see org.quasar.juse.api.JUSE_ProgramingFacade#AllInstances(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	public Set<MObject> allInstances(MClass theClass)
	{
		return system.state().objectsOfClass(theClass);
	}
	
	
	public Set<MClass> allClasses()
	{
		System.out.println("ANNOTATION>" + model.getAnnotation("FlowElement2"));
		return new HashSet<MClass>(model.classes());
	}
	
	
	public Map<String, MElementAnnotation> allAnnotations(MClass theClass)
	{
//		theClass.getAnnotation("FlowElement2");
		return 		model.getAllAnnotations();

//		return theClass.getAllAnnotations();
	}
		
}