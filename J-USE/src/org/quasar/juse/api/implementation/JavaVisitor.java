package org.quasar.juse.api.implementation;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.type.EnumType;

public abstract class JavaVisitor extends FileUtilities implements IJavaVisitor 
{
	@Override
	public abstract void printFileHeader(String typeName);

	@Override
	public abstract void printEnumType(EnumType theEnumType);

	@Override
	public abstract void printClassHeader(MClass theClass);
	
	@Override
	public abstract void printAllInstances(MClass theClass);

	@Override
	public abstract void printAttributes(MClass theClass);
	
	@Override
	public abstract void printDefaultConstructor(MClass theClass);
	
	@Override
	public abstract void printParameterizedConstructor(MClass theClass);
	
	@Override
	public abstract void printBasicGettersSetters(MClass theClass);
	
	@Override
	public abstract void printNavigators(MClass theClass);
	
	@Override
	public abstract void printMEMBER2ASSOCIATIVE(AssociationInfo aInfo);
	
	@Override
	public abstract void printMEMBER2MEMBER(AssociationInfo aInfo);
	
	@Override
	public abstract void printONE2ONE(AssociationInfo aInfo);
	
	@Override
	public abstract void printONE2MANY(AssociationInfo aInfo);
	
	@Override
	public abstract void printMANY2MANY(AssociationInfo aInfo);

	@Override
	public abstract void printSoilOperation(MOperation op);

	@Override
	public abstract void printToString(MClass theClass);
	
	@Override
	public abstract void printInvariants(MClass theClass);
}
