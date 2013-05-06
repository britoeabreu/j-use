package org.quasar.juse.api.implementation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

import org.quasar.juse.api.JUSE_ProgramingFacade;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.RealValue;
import org.tzi.use.uml.ocl.value.StringValue;

public abstract class ModelSnapshotLoader
{
	private final static String	USE_BASE_DIRECTORY	= "C:/Program Files (x86)/use-3.0.6";
	private final static String	MODEL_DIRECTORY		= "D:/Dropbox/TEACH/UML/Exemplos/PT_RUTISEO_Futebol_O";
	private final static String	MODEL_FILE			= "CopaPaises.use";
	private final static String	INSTANCES_FILE		= "euro2012.soil";

	private final static String	TARGET_PACKAGE		= "org.quasar.copaPaises";
	private final static String	LAYER_NAME			= "businessLayer";

	/***********************************************************
	 * @param args
	 * @throws InterruptedException
	 ***********************************************************/
	public static void main(String[] args) throws InterruptedException
	{
		loadDataFromUSE(args);
	}

	
	/***********************************************************
	* @param args
	***********************************************************/
	private static void loadDataFromUSE(String[] args)
	{
		JUSE_ProgramingFacade uf = new ProgramingFacade();

		uf.initialize(args, USE_BASE_DIRECTORY, MODEL_DIRECTORY);

		MSystem system = uf.compileSpecification(MODEL_FILE);

		uf.command("check");
		uf.command("info vars");

		uf.readSOIL(INSTANCES_FILE, true);

		uf.command("info state");
		// uf.command("check");

		loadPrototype(system);

		infoPrototypeState(system);
	}

	
	/***********************************************************
	 * @param aClass
	 * @return
	 ***********************************************************/
	private static String qualifiedName(MClass aClass)
	{
		return TARGET_PACKAGE + "." + LAYER_NAME + "." + aClass.name();
	}

	
	/***********************************************************
	 * @param model
	 ***********************************************************/
	private static void loadPrototype(MSystem system)
	{
		MModel model = system.model();
		MSystemState state = system.state();

		System.out.println("State: " + state.name());

		for (MClass theClass : model.classes())
			if (!theClass.isAbstract())
				try
				{
					loadDataFromUSE(theClass, state);
				}
				catch (SecurityException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InstantiationException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (ClassNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (NoSuchMethodException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	/***********************************************************
	 * @param aClass
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 ***********************************************************/
	private static void loadDataFromUSE(MClass aClass, MSystemState state) throws InstantiationException,
					IllegalAccessException, InvocationTargetException, ClassNotFoundException, SecurityException,
					NoSuchMethodException
	{

		Class c = Class.forName(qualifiedName(aClass));

		Constructor javaConstructor = c.getDeclaredConstructor();
		// m.setAccessible(true);

		for (MObject useObject : state.objectsOfClass(aClass))
		{
			Object javaObject = javaConstructor.newInstance();
			MObjectState useObjectState = useObject.state(state);
			
//			System.out.println(useObject.name() + ": " + aClass.name());
			
			for (MAttribute attribute : aClass.attributes())
				setJavaObjectAttribute(c, javaObject, useObjectState, attribute);

			for (MAssociation association: aClass.associations())
				setJavaObjectAssociation(c, javaObject, useObjectState, association);
				
//			System.out.println(javaObject);
//			System.out.println("----------------------------------------");
		}
	}

	/***********************************************************
	* @param c
	* @param javaObject
	* @param useObjectState
	* @param attribute
	***********************************************************/
	private static void setJavaObjectAttribute(Class c, Object javaObject, MObjectState useObjectState, MAttribute attribute)
	{				
		// System.out.println("\t" + attribute.name() + " = " + useObjectState.attributeValue(attribute));
		
		Method m = null;
		String methodName = "set" + capitalize(attribute.name());
		if (useObjectState.attributeValue(attribute).isDefined())
		{
			try
			{
				m = c.getDeclaredMethod(methodName, toClass(attribute.type()));

				if (attribute.type().isBoolean())
					m.invoke(javaObject, ((BooleanValue) useObjectState.attributeValue(attribute)).value());
				if (attribute.type().isInteger())
					m.invoke(javaObject, ((IntegerValue) useObjectState.attributeValue(attribute)).value());
				if (attribute.type().isReal())
					m.invoke(javaObject, ((RealValue) useObjectState.attributeValue(attribute)).value());
				if (attribute.type().isString())
					m.invoke(javaObject, ((StringValue) useObjectState.attributeValue(attribute)).value());
			}
			catch (IllegalArgumentException e)
			{
				System.out.println("IllegalArgumentException: " + c.getName() + "." + m.getName() + "(" + useObjectState.attributeValue(attribute) + " : " + attribute.type()+")");
			}
			catch (NoSuchMethodException e)
			{
				System.out.println("NoSuchMethodException: " + c.getName() + "." + m.getName() + "(" + useObjectState.attributeValue(attribute) + " : " + attribute.type()+")");
			}
			catch (ClassNotFoundException e)
			{
				System.out.println("ClassNotFoundException: " + c.getName() + "." + m.getName() + "(" + useObjectState.attributeValue(attribute) + " : " + attribute.type()+")");
			}
			catch (IllegalAccessException e)
			{
				System.out.println("IllegalAccessException: " + c.getName() + "." + m.getName() + "(" + useObjectState.attributeValue(attribute) + " : " + attribute.type()+")");
			}
			catch (InvocationTargetException e)
			{
				System.out.println("InvocationTargetException: " + c.getName() + "." + m.getName() + "(" + useObjectState.attributeValue(attribute) + " : " + attribute.type()+")");	
			}
		}
	}
	
	/***********************************************************
	* @param c
	* @param javaObject
	* @param useObjectState
	* @param association
	***********************************************************/
	private static void setJavaObjectAssociation(Class c, Object javaObject, MObjectState useObjectState, MAssociation association)
	{				
		// System.out.println("\t" + attribute.name() + " = " + useObjectState.attributeValue(attribute));
		
//		Method m = null;
//		String methodName = "set" + capitalize(association.name());
//		if (useObjectState..attributeValue(association).isDefined())
//		{
//			try
//			{
//				m = c.getDeclaredMethod(methodName, toClass(attribute.type()));
//
//				if (attribute.type().isBoolean())
//					m.invoke(javaObject, ((BooleanValue) useObjectState.attributeValue(attribute)).value());
//				if (attribute.type().isInteger())
//					m.invoke(javaObject, ((IntegerValue) useObjectState.attributeValue(attribute)).value());
//				if (attribute.type().isReal())
//					m.invoke(javaObject, ((RealValue) useObjectState.attributeValue(attribute)).value());
//				if (attribute.type().isString())
//					m.invoke(javaObject, ((StringValue) useObjectState.attributeValue(attribute)).value());
//			}
//			catch (IllegalArgumentException e)
//			{
//				System.out.println("IllegalArgumentException: " + c.getName() + "." + m.getName() + "(" + useObjectState.attributeValue(attribute) + " : " + attribute.type()+")");
//			}
//			catch (NoSuchMethodException e)
//			{
//				System.out.println("NoSuchMethodException: " + c.getName() + "." + m.getName() + "(" + useObjectState.attributeValue(attribute) + " : " + attribute.type()+")");
//			}
//			catch (ClassNotFoundException e)
//			{
//				System.out.println("ClassNotFoundException: " + c.getName() + "." + m.getName() + "(" + useObjectState.attributeValue(attribute) + " : " + attribute.type()+")");
//			}
//			catch (IllegalAccessException e)
//			{
//				System.out.println("IllegalAccessException: " + c.getName() + "." + m.getName() + "(" + useObjectState.attributeValue(attribute) + " : " + attribute.type()+")");
//			}
//			catch (InvocationTargetException e)
//			{
//				System.out.println("InvocationTargetException: " + c.getName() + "." + m.getName() + "(" + useObjectState.attributeValue(attribute) + " : " + attribute.type()+")");	
//			}
//		}
	}
	
	/***********************************************************
	 * @param oclType
	 * @return
	 * @throws ClassNotFoundException
	 ***********************************************************/
	private static Class toClass(Type oclType) throws ClassNotFoundException
	{
		// System.out.println(oclType);
		if (oclType.isNumber())
			return int.class;
		if (oclType.isInteger())
			return int.class;
		if (oclType.isReal())
			return double.class;
		if (oclType.isBoolean())
			return boolean.class;
		if (oclType.isString())
			return String.class;
		if (oclType.isEnum())
			return Class.forName(TARGET_PACKAGE + "." + LAYER_NAME + "." + oclType.toString());
		if (oclType.isObjectType())
			return Class.forName(TARGET_PACKAGE + "." + LAYER_NAME + "." + oclType.toString());
		if (oclType.isTrueObjectType())
			return Class.forName(TARGET_PACKAGE + "." + LAYER_NAME + "." + oclType.toString());
		if (oclType.isTrueOclAny())
			return Object.class;
		if (oclType.isVoidType())
			return void.class;
		if (oclType.isDate())
			return Date.class;
		if (oclType.isOrderedSet())
			return Class.forName(oclType.toString().substring(11, oclType.toString().length() - 1));
		if (oclType.isSet())
			return Class.forName(oclType.toString().substring(4, oclType.toString().length() - 1));
		// if (oclType.isCollection(true))
		// return "Set<Object>";
		// if (oclType.isTrueCollection())
		// return "TrueCollection";
		// if (oclType.isTrueSet())
		// return "TrueSet";
		// if (oclType.isSequence())
		// return "Sequence";
		// if (oclType.isTrueSequence())
		// return "TrueSequence";
		// if (oclType.isTrueOrderedSet())
		// return "Number";
		// if (oclType.isBag())
		// return "Bag";
		// if (oclType.isTrueBag())
		// return "TrueBag";
		// if (oclType.isInstantiableCollection())
		// return "InstantiableCollection";
		// if (oclType.isTupleType(true))
		// return "Tuple";

		return null;
	}

	/***********************************************************
	 * @param s
	 * @return
	 ***********************************************************/
	private static String capitalize(String s)
	{
		return s.toUpperCase().substring(0, 1) + s.substring(1);
	}

	/***********************************************************
	 * @param model
	 ***********************************************************/
	private static void infoPrototypeState(MSystem system)
	{
		int total = 0;
		System.out.println("class            #objects   #inherited objects");
		System.out.println("--------------------------------------------------------------");
		for (MClass theClass : system.model().classes())
		{
			int totalClasse = infoClassState(theClass);
			if (!theClass.isAbstract())
				total += totalClasse;
		}
		System.out.println("--------------------------------------------------------------");
		System.out.printf("%-19s %5d\n", "total", total);
	}

	/***********************************************************
	 * @param aClass
	 ***********************************************************/
	private static int infoClassState(MClass aClass)
	{
		int result = 0;
		try
		{
			Class c = Class.forName(qualifiedName(aClass));
			if (aClass.isAbstract())
			{
				Method m = c.getDeclaredMethod("allInstances");
				result = ((Collection<Object>) m.invoke(null)).size();
				System.out.printf("%-19s %5d %12d\n", "(" + aClass.name() + ")", 0, result);
			}
			else
			{
				Field f = c.getDeclaredField("allInstances");
				result = ((Collection<Object>) f.get(null)).size();
				System.out.printf("%-19s %5d %12d\n", aClass.name(), result, 0);
			}
		}
		catch (Throwable e)
		{
			System.err.println(e);
		}
		return result;
	}

	// private static void infoJavaState()
	// {
	// System.out.println("Java class          : #objects + #objects in subclasses");
	// System.out.println("--------------------------------------------------");
	//
	// for (MClass theClass : model.classes())
	// {
	// try
	// {
	// Class<?> c = Class.forName(theClass.name());
	// Object t = c.newInstance();
	//
	// Method[] allMethods = c.getDeclaredMethods();
	// for (Method m : allMethods)
	// {
	// String mname = m.getName();
	// if (!mname.startsWith("test") || (m.getGenericReturnType() != boolean.class))
	// {
	// continue;
	// }
	// Type[] pType = m.getGenericParameterTypes();
	// if ((pType.length != 1) || Locale.class.isAssignableFrom(pType[0].getClass()))
	// {
	// continue;
	// }
	//
	// out.format("invoking %s()%n", mname);
	// try
	// {
	// m.setAccessible(true);
	// Object o = m.invoke(t, new Locale(null, null, null));
	// out.format("%s() returned %b%n", mname, (Boolean) o);
	//
	// // Handle any exceptions thrown by method to be invoked.
	// }
	// catch (InvocationTargetException x)
	// {
	// Throwable cause = x.getCause();
	// err.format("invocation of %s failed: %s%n", mname, cause.getMessage());
	// }
	// }
	//
	// // production code should handle these exceptions more gracefully
	// }
	// catch (ClassNotFoundException x)
	// {
	// x.printStackTrace();
	// }
	// catch (InstantiationException x)
	// {
	// x.printStackTrace();
	// }
	// catch (IllegalAccessException x)
	// {
	// x.printStackTrace();
	// }
	// }
	// }
}