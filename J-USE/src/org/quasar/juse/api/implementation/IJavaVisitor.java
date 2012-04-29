package org.quasar.juse.api.implementation;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.type.EnumType;

public interface IJavaVisitor
{
	/***********************************************************
	 * @param typeName
	 *            The name of the type (enumerated type, class or interface) to create
	 ***********************************************************/
	public void printFileHeader(String typeName);

	/***********************************************************
	 * @param theEnumType
	 *            The enumerated type whose code is to be generated
	 ***********************************************************/
	public void printEnumType(EnumType theEnumType);

	/***********************************************************
	 * @param theClass
	 *            The class whose header is to be generated
	 ***********************************************************/
	public void printClassHeader(MClass theClass);

	/***********************************************************
	 * @param theClass
	 *            The class whose allInstances method is to be generated
	 ***********************************************************/
	public void printAllInstances(MClass theClass);

	/***********************************************************
	 * @param theClass
	 *            The class whose attributes are to be generated
	 ***********************************************************/
	public void printAttributes(MClass theClass);

	/***********************************************************
	 * @param theClass
	 *            The class whose default constructor is to be generated
	 ***********************************************************/
	public void printDefaultConstructor(MClass theClass);

	/***********************************************************
	 * @param theClass
	 *            The class whose parametrized constructor is to be generated
	 ***********************************************************/
	public void printParameterizedConstructor(MClass theClass);

	/***********************************************************
	 * @param theClass
	 *            The class whose basic getter and setters are to be generated
	 ***********************************************************/
	public void printBasicGettersSetters(MClass theClass);

	/***********************************************************
	 * @param theClass
	 *            The class whose navigators (getters and setters derived from associations) are to be generated
	 ***********************************************************/
	public void printNavigators(MClass theClass);

	/***********************************************************
	 * @param aInfo
	 *            Information about the navigation of type MEMBER2ASSOCIATIVE whose getter and setter are to be generated
	 ***********************************************************/
	public void printMEMBER2ASSOCIATIVE(AssociationInfo aInfo);

	/***********************************************************
	 * @param aInfo
	 *            Information about the navigation of type MEMBER2MEMBER whose getter and setter are to be generated
	 ***********************************************************/
	public void printMEMBER2MEMBER(AssociationInfo aInfo);

	/***********************************************************
	 * @param aInfo
	 *            Information about the navigation of type ONE2ONE whose getter and setter are to be generated
	 ***********************************************************/
	public void printONE2ONE(AssociationInfo aInfo);

	/***********************************************************
	 * @param aInfo
	 *            Information about the navigation of type ONE2MANY whose getter and setter are to be generated
	 ***********************************************************/
	public void printONE2MANY(AssociationInfo aInfo);

	/***********************************************************
	 * @param aInfo
	 *            Information about the navigation of type MANY2MANY whose getter and setter are to be generated
	 ***********************************************************/
	public void printMANY2MANY(AssociationInfo aInfo);

	/***********************************************************
	 * @param op
	 *            The SOIL operation whose code is to be generated
	 ***********************************************************/
	public void printSoilOperation(MOperation op);

	/***********************************************************
	 * @param theClass
	 *            The class whose object serializer is to be generated
	 ***********************************************************/
	public void printToString(MClass theClass);
	
	/***********************************************************
	* @param theClass
	 *            The class whose invariants are to be generated
	***********************************************************/
	public void printInvariants(MClass theClass);
}