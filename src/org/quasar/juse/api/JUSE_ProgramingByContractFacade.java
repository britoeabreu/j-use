/**
 * 
 */
package org.quasar.juse.api;

import java.util.Collection;
import java.util.Set;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.value.BooleanValue;

/**
 * @author fba
 *
 */
public interface JUSE_ProgramingByContractFacade extends JUSE_ProgramingFacade
{

    /***********************************************************
     * @return all invariants defined in the model
     ***********************************************************/
    public Collection<MClassInvariant> allInvariants();

    /***********************************************************
     * @param aClass the class whose invariants we want
     * @return all invariants defined for the given class
     ***********************************************************/
    public Set<MClassInvariant> allInvariants(MClass aClass);

    /***********************************************************
     * @param className name of the class where the invariant is defined
     * @param invariantName invariant name
     * @return class invariant with given name or null if it does not exist
     ***********************************************************/
    public MClassInvariant getInvariantByName(String className, String invariantName);
    
    /***********************************************************
     * @param className name of the class where the precondition is defined
     * @param operationName name of the operation where the precondition is defined
     * @param preconditionName precondition name
     * @return precondition with given name or null if it does not exist
     ***********************************************************/
    public MPrePostCondition getPreConditionByName(String className, String operationName, String preconditionName);

    /***********************************************************
     * @param className name of the class where the postcondition is defined
     * @param operationName name of the operation where the postcondition is defined
     * @param postconditionName precondition name
     * @return postcondition with given name or null if it does not exist
     ***********************************************************/
    public MPrePostCondition getPostConditionByName(String className, String operationName, String postconditionName);

    /***********************************************************
     * @return all preconditions defined in the model
     ***********************************************************/
    public Collection<MPrePostCondition> allPreConditions();

    /***********************************************************
     * @param anOperation the operation whose preconditions we want
     * @return all preconditions defined for the given operation
     ***********************************************************/
    public Collection<MPrePostCondition> allPreConditions(MOperation anOperation);

    /***********************************************************
     * @return all post-conditions defined in the model
     ***********************************************************/
    public Collection<MPrePostCondition> allPostConditions();

    /***********************************************************
     * @param anOperation the operation whose postconditions we want
     * @return all postconditions defined for the given operation
     ***********************************************************/
    public Collection<MPrePostCondition> allPostConditions(MOperation anOperation);

    /***********************************************************
     * @param anInvariant invariant being checked
     * @return <code>true</code> if the invariant is fulfilled; <code>false</code> otherwise
     ***********************************************************/
    public boolean checkInvariant(MClassInvariant anInvariant);

//    /***********************************************************
//     * @param preCondition precondition being checked
//     * @return <code>true</code> if the precondition is fulfilled; <code>false</code> otherwise
//     ***********************************************************/
//    public boolean checkPreCondition(MPrePostCondition preCondition);
//
//    /***********************************************************
//     * @param postCondition postcondition being checked
//     * @return <code>true</code> if the postcondition is fulfilled; <code>false</code> otherwise
//     ***********************************************************/
//    public boolean checkPostCondition(MPrePostCondition postCondition);

    /***********************************************************
     * @return <code>true</code> if all cardinality constraints are fulfilled; <code>false</code> otherwise
     ***********************************************************/
    public boolean checkStructure();

    /***********************************************************
     * @return <code>true</code> if all invariants are fulfilled; <code>false</code> otherwise
     ***********************************************************/
    public boolean checkInvariants();
    
    /***********************************************************
     * @return association coverage by invariants (a ratio)
     ***********************************************************/
    public double associationCoverage();

    /***********************************************************
     * @return association end coverage by invariants (a ratio)
     ***********************************************************/
    public double associationEndCoverage();

}
