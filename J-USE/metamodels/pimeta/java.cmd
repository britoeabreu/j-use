-- Developed by:   Sérgio Bryton [bryton@di.fct.unl.pt] 
-- Developed at:    QUASAR Research Group - August, 2007

-- Description: PIMETA instantiation with Java Features and Dependencies for usage with the USE tool.

-- Problems:

-- 1. no news is good news...

-- Limitations: 

-- unknown. which is not a good sign...

-- Todo:

-- 1. check some dependencies validity and find some examples for them

-------------------------------------------------------------
-- Java                                                    --
-------------------------------------------------------------

!create OOP : Paradigm
!set OOP.name := 'Object-Oriented Paradigm'

!create Java : Formalism
!set Java.name := 'Java'

!insert (Java, OOP) into implements

-------------------- ModuleTypes --------------

!create Package : ModularFeatureType
!set Package.name := 'package'
!insert (Java, Package) into offers

!create Class: ModularFeatureType
!set Class.name := 'class'
!insert (Java, Class) into offers

!create Interface : ModularFeatureType
!set Interface.name := 'interface'
!insert (Java, Interface) into offers

!create Enumeration : ModularFeatureType
!set Enumeration.name := 'enumeration'
!insert (Java, Enumeration) into offers

!create Method : ModularFeatureType
!set Method.name := 'method'
!insert (Java, Method) into offers

!create Constructor : ModularFeatureType
!set Constructor.name := 'constructor'
!insert (Java, Constructor) into offers

-------------------- FeatureTypes --------------

!create Field : AtomicFeatureType
!set Field.name := 'field'
!insert (Java, Field) into offers

!create LocalVariable : AtomicFeatureType
!set LocalVariable.name := 'local variable'
!insert (Java, LocalVariable) into offers

!create Parameter : AtomicFeatureType
!set Enumeration.name := 'parameter'
!insert (Java, Parameter) into offers

!create ReturnValue : AtomicFeatureType
!set Enumeration.name := 'return value'
!insert (Java, ReturnValue) into offers

!create Exception : AtomicFeatureType
!set Exception.name := 'exception'
!insert (Java, Exception) into offers

-------------------- ModularFeatureType Aggregates ------

-- package

! insert (Package, Package) into aggregates

! insert (Package, Class) into aggregates

! insert (Package, Interface) into aggregates

-- class

! insert (Class, Class) into aggregates

! insert (Class, Interface) into aggregates

! insert (Class, Method) into aggregates

! insert (Class, Constructor) into aggregates

! insert (Class, Field) into aggregates

! insert (Class, Enumeration) into aggregates

-- Method

! insert (Method, LocalVariable) into aggregates

! insert (Method, Class) into aggregates

! insert (Method, Parameter) into aggregates

! insert (Method, ReturnValue) into aggregates

! insert (Method, Enumeration) into aggregates

-- Constructor

! insert (Constructor, LocalVariable) into aggregates

! insert (Constructor, Class) into aggregates

! insert (Constructor, Parameter) into aggregates

! insert (Constructor, Enumeration) into aggregates

-- Interface

! insert (Interface, Method) into aggregates

! insert (Interface, Constructor) into aggregates

! insert (Interface, Enumeration) into aggregates

! insert (Interface, Field) into aggregates

! insert (Interface, Class) into aggregates

! insert (Interface, Interface) into aggregates


-------------------- DependencyTypes --------------

-- class

!create ClassInheritance : DependencyType 
!set ClassInheritance.name := 'class inheritance'
!insert (Java, ClassInheritance) into provides
!insert (Class, ClassInheritance) into dependencyTypeOriginDefinition 
!insert (Class, ClassInheritance) into dependencyTypeDestinationDefinition 

!create Implements : DependencyType 
!set Implements.name := 'implements'
!insert (Java, Implements) into provides
!insert (Class, Implements) into dependencyTypeOriginDefinition 
!insert (Class, Implements) into dependencyTypeDestinationDefinition 

!create ClassImportsPackage : DependencyType
!set ClassImportsPackage.weight := 4
!set ClassImportsPackage.name := 'class imports package'
!insert (Java, ClassImportsPackage) into provides
!insert (Class, ClassImportsPackage) into dependencyTypeOriginDefinition 
!insert (Package, ClassImportsPackage) into dependencyTypeDestinationDefinition 

-- vou aqui
!create ClassImportsClass : DependencyType
!set ClassImportsClass.name := 'class imports class'
!insert (Java, ClassImportsClass) into provides
!insert (Class, ClassImportsClass) into dependencyTypeOriginDefinition 
!insert (Class, ClassImportsClass) into dependencyTypeDestinationDefinition 

-- can't remember example for this one
-- !create ClassUsesEnumeration : DependencyType between (Class, Enumeration)
-- !set ClassUsesEnumeration.name := 'class uses enumeration'
-- !insert (Java, ClassUsesEnumeration) into provides

-- can't remember example for this one
-- !create ClassUsesMethod : DependencyType between (Class, Method)
-- !set ClassUsesMethod.name := 'class uses method'
-- !insert (Java, ClassUsesMethod) into provides

-- can't remember example for this one
-- !create ClassUsesConstructor : DependencyType between (Class, Constructor)
-- !set ClassUsesConstructor.name := 'class uses constructor'
-- !insert (Java, ClassUsesConstructor) into provides

-- interface

!create InterfaceInheritance : DependencyType
!set InterfaceInheritance.name := 'interface inheritance'
!insert (Java, InterfaceInheritance) into provides
!insert (Interface, InterfaceInheritance) into dependencyTypeOriginDefinition 
!insert (Interface, InterfaceInheritance) into dependencyTypeDestinationDefinition 

!create InterfaceImportsPackage : DependencyType
!set InterfaceImportsPackage.name := 'interface imports package'
!insert (Java, InterfaceImportsPackage) into provides
!insert (Interface, InterfaceImportsPackage) into dependencyTypeOriginDefinition 
!insert (Package, InterfaceImportsPackage) into dependencyTypeDestinationDefinition 

!create InterfaceImportsClass : DependencyType
!set InterfaceImportsClass.name := 'interface imports class'
!insert (Java, InterfaceImportsClass) into provides
!insert (Interface, InterfaceImportsClass) into dependencyTypeOriginDefinition 
!insert (Class, InterfaceImportsClass) into dependencyTypeDestinationDefinition 

-- method

!create MethodUsesField : DependencyType
!set MethodUsesField.name := 'method uses field'
!insert (Java, MethodUsesField) into provides
!insert (Method, MethodUsesField) into dependencyTypeOriginDefinition 
!insert (Field, MethodUsesField) into dependencyTypeDestinationDefinition 

!create MethodUsesEnumeration : DependencyType 
!set MethodUsesEnumeration.name := 'method uses enumeration'
!insert (Java, MethodUsesEnumeration) into provides
!insert (Method, MethodUsesEnumeration) into dependencyTypeOriginDefinition 
!insert (Enumeration, MethodUsesEnumeration) into dependencyTypeDestinationDefinition 

!create MethodCallsMethod : DependencyType
!set MethodCallsMethod.weight := 1
!set MethodCallsMethod.name := 'method calls method'
!insert (Java, MethodCallsMethod) into provides
!insert (Method, MethodCallsMethod) into dependencyTypeOriginDefinition 
!insert (Method, MethodCallsMethod) into dependencyTypeDestinationDefinition 

!create MethodCallsConstructor : DependencyType
!set MethodCallsConstructor.name := 'method calls constructor'
!insert (Java, MethodCallsConstructor) into provides
!insert (Method, MethodCallsConstructor) into dependencyTypeOriginDefinition 
!insert (Constructor, MethodCallsConstructor) into dependencyTypeDestinationDefinition 

-- can a method throw an interface as an exception in its signature??
!create MethodRaisesException : DependencyType
!set MethodRaisesException.name := 'method raises exception'
!insert (Java, MethodRaisesException) into provides
!insert (Method, MethodRaisesException) into dependencyTypeOriginDefinition 
!insert (Class, MethodRaisesException) into dependencyTypeDestinationDefinition 

-- constructor

!create ConstructorUsesField : DependencyType 
!set ConstructorUsesField.name := 'constructor uses field'
!insert (Java, ConstructorUsesField) into provides
!insert (Constructor, ConstructorUsesField) into dependencyTypeOriginDefinition 
!insert (Field, ConstructorUsesField) into dependencyTypeDestinationDefinition 

!create ConstructorUsesEnumeration : DependencyType 
!set ConstructorUsesEnumeration.name := 'constructor uses enumeration'
!insert (Java, ConstructorUsesEnumeration) into provides
!insert (Constructor, ConstructorUsesEnumeration) into dependencyTypeOriginDefinition 
!insert (Enumeration, ConstructorUsesEnumeration) into dependencyTypeDestinationDefinition 

!create ConstructorCallsMethod : DependencyType
!set ConstructorCallsMethod.name := 'constructor calls method'
!insert (Java, ConstructorCallsMethod) into provides
!insert (Constructor, ConstructorCallsMethod) into dependencyTypeOriginDefinition 
!insert (Method, ConstructorCallsMethod) into dependencyTypeDestinationDefinition 

!create ConstructorCallsConstructor : DependencyType 
!set ConstructorCallsConstructor.name := 'constructor calls constructor'
!insert (Java, ConstructorCallsConstructor) into provides
!insert (Constructor, ConstructorCallsConstructor) into dependencyTypeOriginDefinition 
!insert (Constructor, ConstructorCallsConstructor) into dependencyTypeDestinationDefinition 

-- can a constructor throw an interface as an exception in its signature??
-- can a constructor thrown an exception??
!create ConstructorRaisesException : DependencyType 
!set ConstructorRaisesException.name := 'constructor raises exception'
!insert (Java, ConstructorRaisesException) into provides
!insert (Constructor, ConstructorRaisesException) into dependencyTypeOriginDefinition 
!insert (Class, ConstructorRaisesException) into dependencyTypeDestinationDefinition 

-- return value

!create ReturnType : DependencyType
!set ReturnType.name := 'return value type'
!insert (Java, ReturnType) into provides
!insert (ReturnValue, ReturnType) into dependencyTypeOriginDefinition 
!insert (Class, ReturnType) into dependencyTypeDestinationDefinition 

-- parameter

!create ParameterType : DependencyType
!set ParameterType.name := 'parameter type'
!insert (Java, ParameterType) into provides
!insert (Parameter, ParameterType) into dependencyTypeOriginDefinition 
!insert (Class, ParameterType) into dependencyTypeDestinationDefinition 

-- field

!create FieldType : DependencyType
!set FieldType.name := 'field type'
!insert (Java, FieldType) into provides
!insert (Field, FieldType) into dependencyTypeOriginDefinition 
!insert (Class, FieldType) into dependencyTypeDestinationDefinition 

-- example: during its definition
!create FieldUsesMethod : DependencyType
!set FieldUsesMethod.name := 'field uses method'
!insert (Java, FieldUsesMethod) into provides
!insert (Field, FieldUsesMethod) into dependencyTypeOriginDefinition 
!insert (Method, FieldUsesMethod) into dependencyTypeDestinationDefinition 

-- example: during its definition
!create FieldUsesConstructor : DependencyType
!set FieldUsesConstructor.name := 'field uses constructor'
!insert (Java, FieldUsesConstructor) into provides
!insert (Field, FieldUsesConstructor) into dependencyTypeOriginDefinition 
!insert (Constructor, FieldUsesConstructor) into dependencyTypeDestinationDefinition 

-- local variable

!create LocalVariableType : DependencyType
!set LocalVariableType.name := 'local variable type'
!insert (Java, LocalVariableType) into provides
!insert (LocalVariable, LocalVariableType) into dependencyTypeOriginDefinition 
!insert (Class, LocalVariableType) into dependencyTypeDestinationDefinition 

-- example: during its definition
!create LocalVariableUsesMethod : DependencyType  
!set LocalVariableUsesMethod.name := 'local variable uses method'
!insert (Java, LocalVariableUsesMethod) into provides
!insert (LocalVariable, LocalVariableUsesMethod) into dependencyTypeOriginDefinition 
!insert (Method, LocalVariableUsesMethod) into dependencyTypeDestinationDefinition 

-- example: during its definition
!create LocalVariableUsesConstructor : DependencyType 
!set LocalVariableUsesConstructor.name := 'local variable uses constructor'
!insert (Java, LocalVariableUsesConstructor) into provides
!insert (LocalVariable, LocalVariableUsesConstructor) into dependencyTypeOriginDefinition 
!insert (Constructor, LocalVariableUsesConstructor) into dependencyTypeDestinationDefinition 
