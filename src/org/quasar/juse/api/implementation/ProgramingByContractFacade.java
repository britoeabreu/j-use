/*
 * J-USE - Java prototyping for the UML based specification environment (USE)
 * Copyright (C) 2021 Fernando Brito e Abreu, QUASAR research group
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.quasar.juse.api.implementation;

import org.quasar.juse.api.JUSE_ProgramingByContractFacade;
import org.quasar.juse.api.JUSE_ProgramingFacade;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.analysis.coverage.CoverageAnalyzer;
import org.tzi.use.analysis.coverage.CoverageData;

import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.mm.statemachines.MRegion;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.type.BagType;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.type.OrderedSetType;
import org.tzi.use.uml.ocl.type.SequenceType;
import org.tzi.use.uml.ocl.type.SetType;
import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.RealValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.OrderedSetValue;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.Log;

/**********************************************************************************
 * @author fba 23 May 2021 - Original version
 *********************************************************************************/

public class ProgramingByContractFacade extends ProgramingFacade implements JUSE_ProgramingByContractFacade
{

    public ProgramingByContractFacade()
    {
	super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#allInvariants()
     */
    public Set<MClassInvariant> allInvariants()
    {
	return new HashSet<MClassInvariant>(getSystem().model().classInvariants());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#allInvariants(org.tzi.use.uml.mm.MClass)
     */
    public Set<MClassInvariant> allInvariants(MClass aClass)
    {
	return new HashSet<MClassInvariant>(getSystem().model().classInvariants(aClass));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#getInvariantByName(java.lang.String, java.lang.String)
     */
    public MClassInvariant getInvariantByName(String className, String invariantName)
    {
	assert className != null;
	assert invariantName != null;	
	
	return getSystem().model().getClassInvariant(className + "::" + invariantName);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#getPreConditionByName(java.lang.String, java.lang.String, java.lang.String)
     */
    public MPrePostCondition getPreConditionByName(String className, String operationName, String preconditionName)
    {
	assert className != null;
	assert operationName != null;
	assert preconditionName != null;	
	
	MOperation theOperation = this.getOperationByName(className, operationName);
	
	if (theOperation == null)
	    return null;
	else
	    for (MPrePostCondition pc: theOperation.preConditions())
		if (pc.name().equals(preconditionName))
		    return pc;
	return null;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#getPostConditionByName(java.lang.String, java.lang.String, java.lang.String)
     */
    public MPrePostCondition getPostConditionByName(String className, String operationName, String postconditionName)
    {
	assert className != null;
	assert operationName != null;
	assert postconditionName != null;	
	
	MOperation theOperation = this.getOperationByName(className, operationName);
	
	if (theOperation == null)
	    return null;
	else
	    for (MPrePostCondition pc: theOperation.postConditions())
		if (pc.name().equals(postconditionName))
		    return pc;
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#allPreConditions()
     */
    public Collection<MPrePostCondition> allPreConditions()
    {
	return this.getSystem().model().preConditions();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#allPreConditions(org.tzi.use.uml.mm.MOperation)
     */
    public Collection<MPrePostCondition> allPreConditions(MOperation anOperation)
    {
	return anOperation.preConditions();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#allPreConditions()
     */
    public Collection<MPrePostCondition> allPostConditions()
    {
	return this.getSystem().model().postConditions();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#allPostConditions(org.tzi.use.uml.mm.MOperation)
     */
    public Collection<MPrePostCondition> allPostConditions(MOperation anOperation)
    {
	return anOperation.postConditions();
    }

    // ===================================================================================

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#checkStructure()
     */
    public boolean checkStructure()
    {
//	System.setOut(new java.io.PrintStream(new OutputStream()
//	{
//	    public void write(int b)
//	    {
//	    }
//	}));

	return getSystem().state().checkStructure(new PrintWriter(System.out));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#checkInvariants()
     */
    public boolean checkInvariants()
    {
	return getSystem().state().check(new PrintWriter(System.out), false, false, true, new ArrayList<String>());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#checkInvariant(org.tzi.use.uml.mm.MClassInvariant)
     */
    public boolean checkInvariant(MClassInvariant anInvariant)
    {
	assert anInvariant != null;

	EvalContext context = new EvalContext(null, getSystem().state(), getSystem().varBindings(), null, "\t");

	return ((BooleanValue) anInvariant.expandedExpression().eval(context)).isTrue();
    }

//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#checkPreCondition(org.tzi.use.uml.mm.MPrePostCondition)
//     */
//    public boolean checkPreCondition(MPrePostCondition preCondition)
//    {
//	assert preCondition.isPre();
//
//	EvalContext context = new EvalContext(null, getSystem().state(), getSystem().varBindings(), null, "\t");
//
//	return ((BooleanValue) preCondition.expression().eval(context)).isTrue();
//    }
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#checkPostCondition(org.tzi.use.uml.mm.MPrePostCondition)
//     */
//    public boolean checkPostCondition(MPrePostCondition postCondition)
//    {
//	assert !postCondition.isPre();
//
//	EvalContext context = new EvalContext(null, getSystem().state(), getSystem().varBindings(), null, "\t");
//
//	return ((BooleanValue) postCondition.expression().eval(context)).isTrue();
//    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#associationCoverage()
     */
    public double associationCoverage()
    {
	MModel model = getSystem().model();

	Map<MModelElement, CoverageData> data = CoverageAnalyzer.calculateTotalCoverage(model, true);
	// The 2nd argument, when true, means that operation expressions will also
	// be considered

	// Map<MAssociation, Integer>coverageAssociation =
	// coverageMap.get(getSystem().model()).getAssociationCoverage();

	CoverageData theData = data.get(model);

	Map<MAssociation, Integer> associationCoverage = theData.getAssociationCoverage();

	int assocSize = 0;
	int assocCovered = 0;

	for (MAssociation assoc : model.associations())
	{
	    // System.out.println("\t"+assoc.name()+ "\t" +
	    // associationCoverage.get(assoc));
	    assocSize++;
	    if (associationCoverage.get(assoc) != null)
		assocCovered++;
	}

	return ((double) assocCovered) / assocSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.quasar.juse.api.JUSE_ProgramingByContractFacade#associationEndCoverage()
     */
    public double associationEndCoverage()
    {
	MModel model = getSystem().model();

	Map<MModelElement, CoverageData> data = CoverageAnalyzer.calculateTotalCoverage(model, true);
	// The 2nd argument, when true, means that operation expressions will also
	// be considered

	// Map<MAssociation, Integer>coverageAssociation =
	// coverageMap.get(getSystem().model()).getAssociationCoverage();

	CoverageData theData = data.get(model);

	Map<MAssociationEnd, Integer> associationEndCoverage = theData.getAssociationEndCoverage();

	int aendSize = 0;
	int aendCovered = 0;

	for (MAssociation assoc : model.associations())
	    for (MAssociationEnd aend : assoc.associationEnds())
	    {
		// System.out.println("\t"+aend.nameAsRolename()+ "\t" +
		// associationEndCoverage.get(aend));
		aendSize++;
		if (associationEndCoverage.get(aend) != null)
		    aendCovered++;
	    }

	return ((double) aendCovered) / aendSize;
    }

}