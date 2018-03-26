------------------------------------------------------------------------
------------------------------------------------------------------------
reset

 -- model Navio

!create Navio_Model: Model

!set Navio_Model.name := 'Navio'

-- converted "05-01-2002_00:40:43"

------------------------------------------------------------------------

!create TypesPackage_Package: Package

!set TypesPackage_Package.name := 'TypesPackage'

!insert (Navio_Model, TypesPackage_Package) into NamespaceImpl_ModelElementImpl

------------------------------------------------------------------------
-- DataTypes
------------------------------------------------------------------------

!create Integer_Class: MMClass
!set Integer_Class.name := 'Integer'
!insert (TypesPackage_Package, Integer_Class) into NamespaceImpl_ModelElementImpl

!create Long_Class: MMClass 
!set Long_Class.name :='Long'
!set Long_Class.isRoot := true
!set Long_Class.isLeaf := true
!set Long_Class.isAbstract := false
!insert (TypesPackage_Package, Long_Class) into NamespaceImpl_ModelElementImpl

!create Boolean_Class: MMClass
!set Boolean_Class.name := 'Boolean'
!insert (TypesPackage_Package, Boolean_Class) into NamespaceImpl_ModelElementImpl

!create String_Class: MMClass
!set String_Class.name := 'String'
!insert (TypesPackage_Package, String_Class) into NamespaceImpl_ModelElementImpl

!create Date_Class: MMClass 
!set Date_Class.name :='Date'
!set Date_Class.isRoot := true
!set Date_Class.isLeaf := true
!set Date_Class.isAbstract := false
!insert (TypesPackage_Package, Date_Class) into NamespaceImpl_ModelElementImpl 

!create Double_Class: MMClass 
!set Double_Class.name :='Double'
!set Double_Class.isRoot := true
!set Double_Class.isLeaf := true
!set Double_Class.isAbstract := false
!insert (TypesPackage_Package, Double_Class) into NamespaceImpl_ModelElementImpl 

!create Currency_Class: MMClass 
!set Currency_Class.name :='Currency'
!set Currency_Class.isRoot := true
!set Currency_Class.isLeaf := true
!set Currency_Class.isAbstract := false
!insert (TypesPackage_Package, Currency_Class) into NamespaceImpl_ModelElementImpl 

!create Time_Class: MMClass 
!set Time_Class.name :='Time'
!set Time_Class.isRoot := true
!set Time_Class.isLeaf := true
!set Time_Class.isAbstract := false
!insert (TypesPackage_Package, Time_Class) into NamespaceImpl_ModelElementImpl 

------------------------------------------------------------------------
-- Dummy class for missing info about types
------------------------------------------------------------------------

!create UNKNOWN_Class: MMClass


------------------------------------------------------------------------
-- Enumerated Types
------------------------------------------------------------------------

!create EnumType_1: MMClass
!set EnumType_1.name := 'EnumType_1'
!insert (Navio_Model, EnumType_1) into NamespaceImpl_ModelElementImpl


------------------------------------------------------------------------

!create Posicionamento_Package: Package

!set Posicionamento_Package.name := 'Posicionamento'

!insert (Navio_Model, Posicionamento_Package) into NamespaceImpl_ModelElementImpl

------------------------------------------------------------------------

--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Posicao_Class: MMClass 

!set Posicao_Class.name :='Posicao'

!set Posicao_Class.isRoot := true

!set Posicao_Class.isLeaf := true

!set Posicao_Class.isAbstract := false

!insert (Posicionamento_Package, Posicao_Class) into NamespaceImpl_ModelElementImpl 


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Coordenada_Class: MMClass 

!set Coordenada_Class.name :='Coordenada'

!set Coordenada_Class.isRoot := true

!set Coordenada_Class.isLeaf := true

!set Coordenada_Class.isAbstract := false

!insert (Posicionamento_Package, Coordenada_Class) into NamespaceImpl_ModelElementImpl 


-----------------------------------------------------------------------------------
!create Coordenada_graus_Attribute: Attribute 

!set Coordenada_graus_Attribute.name :='graus'

!set Coordenada_graus_Attribute.visibility := #private

!insert (Coordenada_Class, Coordenada_graus_Attribute) into Classifier_Feature

!insert (Coordenada_graus_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Coordenada_minutos_Attribute: Attribute 

!set Coordenada_minutos_Attribute.name :='minutos'

!set Coordenada_minutos_Attribute.visibility := #private

!insert (Coordenada_Class, Coordenada_minutos_Attribute) into Classifier_Feature

!insert (Coordenada_minutos_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Coordenada_segundos_Attribute: Attribute 

!set Coordenada_segundos_Attribute.name :='segundos'

!set Coordenada_segundos_Attribute.visibility := #private

!insert (Coordenada_Class, Coordenada_segundos_Attribute) into Classifier_Feature

!insert (Coordenada_segundos_Attribute, Double_Class) into StructuralFeature_Classifier


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Instante_Class: MMClass 

!set Instante_Class.name :='Instante'

!set Instante_Class.isRoot := true

!set Instante_Class.isLeaf := true

!set Instante_Class.isAbstract := false

!insert (Posicionamento_Package, Instante_Class) into NamespaceImpl_ModelElementImpl 


-----------------------------------------------------------------------------------
!create Instante_ano_Attribute: Attribute 

!set Instante_ano_Attribute.name :='ano'

!set Instante_ano_Attribute.visibility := #private

!insert (Instante_Class, Instante_ano_Attribute) into Classifier_Feature

!insert (Instante_ano_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Instante_mes_Attribute: Attribute 

!set Instante_mes_Attribute.name :='mes'

!set Instante_mes_Attribute.visibility := #private

!insert (Instante_Class, Instante_mes_Attribute) into Classifier_Feature

!insert (Instante_mes_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Instante_dia_Attribute: Attribute 

!set Instante_dia_Attribute.name :='dia'

!set Instante_dia_Attribute.visibility := #private

!insert (Instante_Class, Instante_dia_Attribute) into Classifier_Feature

!insert (Instante_dia_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Instante_hora_Attribute: Attribute 

!set Instante_hora_Attribute.name :='hora'

!set Instante_hora_Attribute.visibility := #private

!insert (Instante_Class, Instante_hora_Attribute) into Classifier_Feature

!insert (Instante_hora_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Instante_minuto_Attribute: Attribute 

!set Instante_minuto_Attribute.name :='minuto'

!set Instante_minuto_Attribute.visibility := #private

!insert (Instante_Class, Instante_minuto_Attribute) into Classifier_Feature

!insert (Instante_minuto_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Instante_segundo_Attribute: Attribute 

!set Instante_segundo_Attribute.name :='segundo'

!set Instante_segundo_Attribute.visibility := #private

!insert (Instante_Class, Instante_segundo_Attribute) into Classifier_Feature

!insert (Instante_segundo_Attribute, Integer_Class) into StructuralFeature_Classifier


------------------------------------------------------------------------

!create Pessoal_Package: Package

!set Pessoal_Package.name := 'Pessoal'

!insert (Navio_Model, Pessoal_Package) into NamespaceImpl_ModelElementImpl

------------------------------------------------------------------------
--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create CategoriaProfissional_Class: MMClass 

!set CategoriaProfissional_Class.name :='CategoriaProfissional'

!set CategoriaProfissional_Class.isRoot := true

!set CategoriaProfissional_Class.isLeaf := true

!set CategoriaProfissional_Class.isAbstract := false

!insert (Pessoal_Package, CategoriaProfissional_Class) into NamespaceImpl_ModelElementImpl 


-----------------------------------------------------------------------------------
!create CategoriaProfissional_designacao_Attribute: Attribute 

!set CategoriaProfissional_designacao_Attribute.name :='designacao'

!set CategoriaProfissional_designacao_Attribute.visibility := #public

!insert (CategoriaProfissional_Class, CategoriaProfissional_designacao_Attribute) into Classifier_Feature

!insert (CategoriaProfissional_designacao_Attribute, String_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create CategoriaProfissional_vencimento_Attribute: Attribute 

!set CategoriaProfissional_vencimento_Attribute.name :='vencimento'

!set CategoriaProfissional_vencimento_Attribute.visibility := #private

!insert (CategoriaProfissional_Class, CategoriaProfissional_vencimento_Attribute) into Classifier_Feature

!insert (CategoriaProfissional_vencimento_Attribute, Currency_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create CategoriaProfissional_Escreve_Operation: Operation

!set CategoriaProfissional_Escreve_Operation.name :='Escreve'

!insert (CategoriaProfissional_Class, CategoriaProfissional_Escreve_Operation) into Classifier_Feature

!insert (CategoriaProfissional_Escreve_Operation, UNKNOWN_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create CategoriaProfissional_ActualizaVencimento_Operation: Operation

!set CategoriaProfissional_ActualizaVencimento_Operation.name :='ActualizaVencimento'

!insert (CategoriaProfissional_Class, CategoriaProfissional_ActualizaVencimento_Operation) into Classifier_Feature

!insert (CategoriaProfissional_ActualizaVencimento_Operation, Boolean_Class) into BehavioralFeature_Classifier

!create ActualizaVencimento_novo_valor_Parameter: Parameter

!set ActualizaVencimento_novo_valor_Parameter.name := 'novo_valor'

!insert (CategoriaProfissional_ActualizaVencimento_Operation, ActualizaVencimento_novo_valor_Parameter) into BehavioralFeature_Parameter

!insert (ActualizaVencimento_novo_valor_Parameter, Currency_Class) into Parameter_Classifier


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Empregado_Class: MMClass 

!set Empregado_Class.name :='Empregado'

!set Empregado_Class.isRoot := true

!set Empregado_Class.isLeaf := false

!set Empregado_Class.isAbstract := true

!insert (Pessoal_Package, Empregado_Class) into NamespaceImpl_ModelElementImpl 


-----------------------------------------------------------------------------------
!create Empregado_nome_Attribute: Attribute 

!set Empregado_nome_Attribute.name :='nome'

!set Empregado_nome_Attribute.visibility := #private

!insert (Empregado_Class, Empregado_nome_Attribute) into Classifier_Feature

!insert (Empregado_nome_Attribute, String_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Empregado_morada_Attribute: Attribute 

!set Empregado_morada_Attribute.name :='morada'

!set Empregado_morada_Attribute.visibility := #private

!insert (Empregado_Class, Empregado_morada_Attribute) into Classifier_Feature

!insert (Empregado_morada_Attribute, String_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Empregado_no_contribuinte_Attribute: Attribute 

!set Empregado_no_contribuinte_Attribute.name :='no_contribuinte'

!set Empregado_no_contribuinte_Attribute.visibility := #private

!insert (Empregado_Class, Empregado_no_contribuinte_Attribute) into Classifier_Feature

!insert (Empregado_no_contribuinte_Attribute, Long_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Empregado_Promove_Operation: Operation

!set Empregado_Promove_Operation.name :='Promove'

!insert (Empregado_Class, Empregado_Promove_Operation) into Classifier_Feature

!insert (Empregado_Promove_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Empregado_AlteraMorada_Operation: Operation

!set Empregado_AlteraMorada_Operation.name :='AlteraMorada'

!insert (Empregado_Class, Empregado_AlteraMorada_Operation) into Classifier_Feature

!insert (Empregado_AlteraMorada_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Empregado_Aposenta_Operation: Operation

!set Empregado_Aposenta_Operation.name :='Aposenta'

!insert (Empregado_Class, Empregado_Aposenta_Operation) into Classifier_Feature

!insert (Empregado_Aposenta_Operation, Boolean_Class) into BehavioralFeature_Classifier


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Embarque_Class: MMClass 

!set Embarque_Class.name :='Embarque'

!set Embarque_Class.isRoot := true

!set Embarque_Class.isLeaf := true

!set Embarque_Class.isAbstract := false

!insert (Pessoal_Package, Embarque_Class) into NamespaceImpl_ModelElementImpl 


-----------------------------------------------------------------------------------
!create Embarque_data_embarque_Attribute: Attribute 

!set Embarque_data_embarque_Attribute.name :='data_embarque'

!set Embarque_data_embarque_Attribute.visibility := #private

!insert (Embarque_Class, Embarque_data_embarque_Attribute) into Classifier_Feature

!insert (Embarque_data_embarque_Attribute, Date_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Embarque_date_desembarque_Attribute: Attribute 

!set Embarque_date_desembarque_Attribute.name :='date_desembarque'

!set Embarque_date_desembarque_Attribute.visibility := #private

!insert (Embarque_Class, Embarque_date_desembarque_Attribute) into Classifier_Feature

!insert (Embarque_date_desembarque_Attribute, Date_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Embarque_funcao_Attribute: Attribute 

!set Embarque_funcao_Attribute.name :='funcao'

!set Embarque_funcao_Attribute.visibility := #private

!insert (Embarque_Class, Embarque_funcao_Attribute) into Classifier_Feature

!insert (Embarque_funcao_Attribute, CategoriaProfissional_Class) into StructuralFeature_Classifier

--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create FuncionarioMar_Class: MMClass 

!set FuncionarioMar_Class.name :='FuncionarioMar'

!set FuncionarioMar_Class.isRoot := false

!set FuncionarioMar_Class.isLeaf := false

!set FuncionarioMar_Class.isAbstract := true

!insert (Pessoal_Package, FuncionarioMar_Class) into NamespaceImpl_ModelElementImpl 

!create Empregado_FuncionarioMar_Generalization: Generalization

!set Empregado_FuncionarioMar_Generalization.name :='Empregado_FuncionarioMar'

!set Empregado_FuncionarioMar_Generalization.discriminator :=''

!insert (Empregado_FuncionarioMar_Generalization, Empregado_Class) into Generalization_GeneralizableElementImpl1

!insert (Empregado_FuncionarioMar_Generalization, FuncionarioMar_Class) into Generalization_GeneralizableElementImpl2


-----------------------------------------------------------------------------------
!create FuncionarioMar_Embarca_Operation: Operation

!set FuncionarioMar_Embarca_Operation.name :='Embarca'

!insert (FuncionarioMar_Class, FuncionarioMar_Embarca_Operation) into Classifier_Feature

!insert (FuncionarioMar_Embarca_Operation, Boolean_Class) into BehavioralFeature_Classifier

!create Embarca_destino_Parameter: Parameter

!set Embarca_destino_Parameter.name := 'destino'

!insert (FuncionarioMar_Embarca_Operation, Embarca_destino_Parameter) into BehavioralFeature_Parameter

-- FBA 2013
--!insert (Embarca_destino_Parameter, Embarcacao_Class) into Parameter_Classifier

!create Embarca_funcao_Parameter: Parameter

!set Embarca_funcao_Parameter.name := 'funcao'

!insert (FuncionarioMar_Embarca_Operation, Embarca_funcao_Parameter) into BehavioralFeature_Parameter

!insert (Embarca_funcao_Parameter, CategoriaProfissional_Class) into Parameter_Classifier


-----------------------------------------------------------------------------------
!create FuncionarioMar_Desembarca_Operation: Operation

!set FuncionarioMar_Desembarca_Operation.name :='Desembarca'

!insert (FuncionarioMar_Class, FuncionarioMar_Desembarca_Operation) into Classifier_Feature

!insert (FuncionarioMar_Desembarca_Operation, Boolean_Class) into BehavioralFeature_Classifier


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Tripulante_Class: MMClass 

!set Tripulante_Class.name :='Tripulante'

!set Tripulante_Class.isRoot := false

!set Tripulante_Class.isLeaf := false

!set Tripulante_Class.isAbstract := true

!insert (Pessoal_Package, Tripulante_Class) into NamespaceImpl_ModelElementImpl 

!create FuncionarioMar_Tripulante_Generalization: Generalization

!set FuncionarioMar_Tripulante_Generalization.name :='FuncionarioMar_Tripulante'

!set FuncionarioMar_Tripulante_Generalization.discriminator :=''

!insert (FuncionarioMar_Tripulante_Generalization, FuncionarioMar_Class) into Generalization_GeneralizableElementImpl1

!insert (FuncionarioMar_Tripulante_Generalization, Tripulante_Class) into Generalization_GeneralizableElementImpl2


-----------------------------------------------------------------------------------
!create Tripulante_NavioCorrente_Operation: Operation

!set Tripulante_NavioCorrente_Operation.name :='NavioCorrente'

!insert (Tripulante_Class, Tripulante_NavioCorrente_Operation) into Classifier_Feature

-- FBA 2013
-- !insert (Tripulante_NavioCorrente_Operation, Embarcacao_Class) into BehavioralFeature_Classifier

-----------------------------------------------------------------------------------
!create Tripulante_DiasEmbarcado_Operation: Operation

!set Tripulante_DiasEmbarcado_Operation.name :='DiasEmbarcado'

!insert (Tripulante_Class, Tripulante_DiasEmbarcado_Operation) into Classifier_Feature

!insert (Tripulante_DiasEmbarcado_Operation, Integer_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Tripulante_FuncaoCorrente_Operation: Operation

!set Tripulante_FuncaoCorrente_Operation.name :='FuncaoCorrente'

!insert (Tripulante_Class, Tripulante_FuncaoCorrente_Operation) into Classifier_Feature

!insert (Tripulante_FuncaoCorrente_Operation, CategoriaProfissional_Class) into BehavioralFeature_Classifier


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Oficial_Class: MMClass 

!set Oficial_Class.name :='Oficial'

!set Oficial_Class.isRoot := false

!set Oficial_Class.isLeaf := false

!set Oficial_Class.isAbstract := true

!insert (Pessoal_Package, Oficial_Class) into NamespaceImpl_ModelElementImpl 

!create Tripulante_Oficial_Generalization: Generalization

!set Tripulante_Oficial_Generalization.name :='Tripulante_Oficial'

!set Tripulante_Oficial_Generalization.discriminator :=''

!insert (Tripulante_Oficial_Generalization, Tripulante_Class) into Generalization_GeneralizableElementImpl1

!insert (Tripulante_Oficial_Generalization, Oficial_Class) into Generalization_GeneralizableElementImpl2


-----------------------------------------------------------------------------------
!create Oficial_Promove_Operation: Operation

!set Oficial_Promove_Operation.name :='Promove'

!insert (Oficial_Class, Oficial_Promove_Operation) into Classifier_Feature

!insert (Oficial_Promove_Operation, Boolean_Class) into BehavioralFeature_Classifier



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create OficialMaquinas_Class: MMClass 

!set OficialMaquinas_Class.name :='OficialMaquinas'

!set OficialMaquinas_Class.isRoot := false

!set OficialMaquinas_Class.isLeaf := true

!set OficialMaquinas_Class.isAbstract := false

!insert (Pessoal_Package, OficialMaquinas_Class) into NamespaceImpl_ModelElementImpl 

!create Oficial_OficialMaquinas_Generalization: Generalization

!set Oficial_OficialMaquinas_Generalization.name :='Oficial_OficialMaquinas'

!set Oficial_OficialMaquinas_Generalization.discriminator :=''

!insert (Oficial_OficialMaquinas_Generalization, Oficial_Class) into Generalization_GeneralizableElementImpl1

!insert (Oficial_OficialMaquinas_Generalization, OficialMaquinas_Class) into Generalization_GeneralizableElementImpl2


-----------------------------------------------------------------------------------
!create OficialMaquinas_SupervisionaManutencao_Operation: Operation

!set OficialMaquinas_SupervisionaManutencao_Operation.name :='SupervisionaManutencao'

!insert (OficialMaquinas_Class, OficialMaquinas_SupervisionaManutencao_Operation) into Classifier_Feature

!insert (OficialMaquinas_SupervisionaManutencao_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create OficialMaquinas_CalculaConsumos_Operation: Operation

!set OficialMaquinas_CalculaConsumos_Operation.name :='CalculaConsumos'

!insert (OficialMaquinas_Class, OficialMaquinas_CalculaConsumos_Operation) into Classifier_Feature

!insert (OficialMaquinas_CalculaConsumos_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create OficialMaquinas_VigiaFuncionamento_Operation: Operation

!set OficialMaquinas_VigiaFuncionamento_Operation.name :='VigiaFuncionamento'

!insert (OficialMaquinas_Class, OficialMaquinas_VigiaFuncionamento_Operation) into Classifier_Feature

!insert (OficialMaquinas_VigiaFuncionamento_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create OficialMaquinas_Aposenta_Operation: Operation

!set OficialMaquinas_Aposenta_Operation.name :='Aposenta'

!insert (OficialMaquinas_Class, OficialMaquinas_Aposenta_Operation) into Classifier_Feature

!insert (OficialMaquinas_Aposenta_Operation, Boolean_Class) into BehavioralFeature_Classifier


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create FuncionarioTerra_Class: MMClass 

!set FuncionarioTerra_Class.name :='FuncionarioTerra'

!set FuncionarioTerra_Class.isRoot := false

!set FuncionarioTerra_Class.isLeaf := true

!set FuncionarioTerra_Class.isAbstract := false

!insert (Pessoal_Package, FuncionarioTerra_Class) into NamespaceImpl_ModelElementImpl 

!create Empregado_FuncionarioTerra_Generalization: Generalization

!set Empregado_FuncionarioTerra_Generalization.name :='Empregado_FuncionarioTerra'

!set Empregado_FuncionarioTerra_Generalization.discriminator :=''

!insert (Empregado_FuncionarioTerra_Generalization, Empregado_Class) into Generalization_GeneralizableElementImpl1

!insert (Empregado_FuncionarioTerra_Generalization, FuncionarioTerra_Class) into Generalization_GeneralizableElementImpl2


-----------------------------------------------------------------------------------
!create FuncionarioTerra_Promove_Operation: Operation

!set FuncionarioTerra_Promove_Operation.name :='Promove'

!insert (FuncionarioTerra_Class, FuncionarioTerra_Promove_Operation) into Classifier_Feature

!insert (FuncionarioTerra_Promove_Operation, Boolean_Class) into BehavioralFeature_Classifier


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Desembarcado_Class: MMClass 

!set Desembarcado_Class.name :='Desembarcado'

!set Desembarcado_Class.isRoot := false

!set Desembarcado_Class.isLeaf := true

!set Desembarcado_Class.isAbstract := false

!insert (Pessoal_Package, Desembarcado_Class) into NamespaceImpl_ModelElementImpl 

!create FuncionarioMar_Desembarcado_Generalization: Generalization

!set FuncionarioMar_Desembarcado_Generalization.name :='FuncionarioMar_Desembarcado'

!set FuncionarioMar_Desembarcado_Generalization.discriminator :=''

!insert (FuncionarioMar_Desembarcado_Generalization, FuncionarioMar_Class) into Generalization_GeneralizableElementImpl1

!insert (FuncionarioMar_Desembarcado_Generalization, Desembarcado_Class) into Generalization_GeneralizableElementImpl2



-----------------------------------------------------------------------------------
!create Desembarcado_data_desembarque_Attribute: Attribute 

!set Desembarcado_data_desembarque_Attribute.name :='data_desembarque'

!set Desembarcado_data_desembarque_Attribute.visibility := #private

!insert (Desembarcado_Class, Desembarcado_data_desembarque_Attribute) into Classifier_Feature

!insert (Desembarcado_data_desembarque_Attribute, Date_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Desembarcado_doente_Attribute: Attribute 

!set Desembarcado_doente_Attribute.name :='doente'

!set Desembarcado_doente_Attribute.visibility := #private

!insert (Desembarcado_Class, Desembarcado_doente_Attribute) into Classifier_Feature

!insert (Desembarcado_doente_Attribute, Boolean_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Desembarcado_DiasDesembarcado_Operation: Operation

!set Desembarcado_DiasDesembarcado_Operation.name :='DiasDesembarcado'

!insert (Desembarcado_Class, Desembarcado_DiasDesembarcado_Operation) into Classifier_Feature

!insert (Desembarcado_DiasDesembarcado_Operation, Integer_Class) into BehavioralFeature_Classifier

--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Marinheiro_Class: MMClass 

!set Marinheiro_Class.name :='Marinheiro'

!set Marinheiro_Class.isRoot := false

!set Marinheiro_Class.isLeaf := true

!set Marinheiro_Class.isAbstract := false

!insert (Pessoal_Package, Marinheiro_Class) into NamespaceImpl_ModelElementImpl 

!create Tripulante_Marinheiro_Generalization: Generalization

!set Tripulante_Marinheiro_Generalization.name :='Tripulante_Marinheiro'

!set Tripulante_Marinheiro_Generalization.discriminator :=''

!insert (Tripulante_Marinheiro_Generalization, Tripulante_Class) into Generalization_GeneralizableElementImpl1

!insert (Tripulante_Marinheiro_Generalization, Marinheiro_Class) into Generalization_GeneralizableElementImpl2


-----------------------------------------------------------------------------------
!create Marinheiro_PicaMetal_Operation: Operation

!set Marinheiro_PicaMetal_Operation.name :='PicaMetal'

!insert (Marinheiro_Class, Marinheiro_PicaMetal_Operation) into Classifier_Feature

!insert (Marinheiro_PicaMetal_Operation, Integer_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Marinheiro_PintaNavio_Operation: Operation

!set Marinheiro_PintaNavio_Operation.name :='PintaNavio'

!insert (Marinheiro_Class, Marinheiro_PintaNavio_Operation) into Classifier_Feature

!insert (Marinheiro_PintaNavio_Operation, Integer_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Marinheiro_GovernaNavio_Operation: Operation

!set Marinheiro_GovernaNavio_Operation.name :='GovernaNavio'

!insert (Marinheiro_Class, Marinheiro_GovernaNavio_Operation) into Classifier_Feature

!insert (Marinheiro_GovernaNavio_Operation, Integer_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Marinheiro_PassaCabos_Operation: Operation

!set Marinheiro_PassaCabos_Operation.name :='PassaCabos'

!insert (Marinheiro_Class, Marinheiro_PassaCabos_Operation) into Classifier_Feature

!insert (Marinheiro_PassaCabos_Operation, Integer_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Marinheiro_Promove_Operation: Operation

!set Marinheiro_Promove_Operation.name :='Promove'

!insert (Marinheiro_Class, Marinheiro_Promove_Operation) into Classifier_Feature

!insert (Marinheiro_Promove_Operation, Boolean_Class) into BehavioralFeature_Classifier



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Mestre_Class: MMClass 

!set Mestre_Class.name :='Mestre'

!set Mestre_Class.isRoot := false

!set Mestre_Class.isLeaf := true

!set Mestre_Class.isAbstract := false

!insert (Pessoal_Package, Mestre_Class) into NamespaceImpl_ModelElementImpl 

!create Tripulante_Mestre_Generalization: Generalization

!set Tripulante_Mestre_Generalization.name :='Tripulante_Mestre'

!set Tripulante_Mestre_Generalization.discriminator :=''

!insert (Tripulante_Mestre_Generalization, Tripulante_Class) into Generalization_GeneralizableElementImpl1

!insert (Tripulante_Mestre_Generalization, Mestre_Class) into Generalization_GeneralizableElementImpl2


-----------------------------------------------------------------------------------
!create Mestre_GerePaiolMantimentos_Operation: Operation

!set Mestre_GerePaiolMantimentos_Operation.name :='GerePaiolMantimentos'

!insert (Mestre_Class, Mestre_GerePaiolMantimentos_Operation) into Classifier_Feature

!insert (Mestre_GerePaiolMantimentos_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Mestre_ControlaMarinheiros_Operation: Operation

!set Mestre_ControlaMarinheiros_Operation.name :='ControlaMarinheiros'

!insert (Mestre_Class, Mestre_ControlaMarinheiros_Operation) into Classifier_Feature

!insert (Mestre_ControlaMarinheiros_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Mestre_DefineTarefas_Operation: Operation

!set Mestre_DefineTarefas_Operation.name :='DefineTarefas'

!insert (Mestre_Class, Mestre_DefineTarefas_Operation) into Classifier_Feature

!insert (Mestre_DefineTarefas_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Mestre_GerePaiolPecas_Operation: Operation

!set Mestre_GerePaiolPecas_Operation.name :='GerePaiolPecas'

!insert (Mestre_Class, Mestre_GerePaiolPecas_Operation) into Classifier_Feature

!insert (Mestre_GerePaiolPecas_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Mestre_Promove_Operation: Operation

!set Mestre_Promove_Operation.name :='Promove'

!insert (Mestre_Class, Mestre_Promove_Operation) into Classifier_Feature

!insert (Mestre_Promove_Operation, Boolean_Class) into BehavioralFeature_Classifier



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create OficialPiloto_Class: MMClass 

!set OficialPiloto_Class.name :='OficialPiloto'

!set OficialPiloto_Class.isRoot := false

!set OficialPiloto_Class.isLeaf := true

!set OficialPiloto_Class.isAbstract := false

!insert (Pessoal_Package, OficialPiloto_Class) into NamespaceImpl_ModelElementImpl 

!create Oficial_OficialPiloto_Generalization: Generalization

!set Oficial_OficialPiloto_Generalization.name :='Oficial_OficialPiloto'

!set Oficial_OficialPiloto_Generalization.discriminator :=''

!insert (Oficial_OficialPiloto_Generalization, Oficial_Class) into Generalization_GeneralizableElementImpl1

!insert (Oficial_OficialPiloto_Generalization, OficialPiloto_Class) into Generalization_GeneralizableElementImpl2


-----------------------------------------------------------------------------------
!create OficialPiloto_ObservaEstrelas_Operation: Operation

!set OficialPiloto_ObservaEstrelas_Operation.name :='ObservaEstrelas'

!insert (OficialPiloto_Class, OficialPiloto_ObservaEstrelas_Operation) into Classifier_Feature

!insert (OficialPiloto_ObservaEstrelas_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create OficialPiloto_DeterminaPosicao_Operation: Operation

!set OficialPiloto_DeterminaPosicao_Operation.name :='DeterminaPosicao'

!insert (OficialPiloto_Class, OficialPiloto_DeterminaPosicao_Operation) into Classifier_Feature

!insert (OficialPiloto_DeterminaPosicao_Operation, Posicao_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create OficialPiloto_CalculaRumo_Operation: Operation

!set OficialPiloto_CalculaRumo_Operation.name :='CalculaRumo'

!insert (OficialPiloto_Class, OficialPiloto_CalculaRumo_Operation) into Classifier_Feature

!insert (OficialPiloto_CalculaRumo_Operation, Double_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create OficialPiloto_MedeAlturaSol_Operation: Operation

!set OficialPiloto_MedeAlturaSol_Operation.name :='MedeAlturaSol'

!insert (OficialPiloto_Class, OficialPiloto_MedeAlturaSol_Operation) into Classifier_Feature

!insert (OficialPiloto_MedeAlturaSol_Operation, Double_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create OficialPiloto_MedeAzimuteSol_Operation: Operation

!set OficialPiloto_MedeAzimuteSol_Operation.name :='MedeAzimuteSol'

!insert (OficialPiloto_Class, OficialPiloto_MedeAzimuteSol_Operation) into Classifier_Feature

!insert (OficialPiloto_MedeAzimuteSol_Operation, Double_Class) into BehavioralFeature_Classifier


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create CedulaMaritima_Class: MMClass 

!set CedulaMaritima_Class.name :='CedulaMaritima'

!set CedulaMaritima_Class.isRoot := true

!set CedulaMaritima_Class.isLeaf := true

!set CedulaMaritima_Class.isAbstract := false

!insert (Pessoal_Package, CedulaMaritima_Class) into NamespaceImpl_ModelElementImpl 



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Armador_Class: MMClass 

!set Armador_Class.name :='Armador'

!set Armador_Class.isRoot := true

!set Armador_Class.isLeaf := true

!set Armador_Class.isAbstract := false

!insert (Pessoal_Package, Armador_Class) into NamespaceImpl_ModelElementImpl 


-----------------------------------------------------------------------------------
!create Armador_nome_Attribute: Attribute 

!set Armador_nome_Attribute.name :='nome'

!set Armador_nome_Attribute.visibility := #private

!insert (Armador_Class, Armador_nome_Attribute) into Classifier_Feature

!insert (Armador_nome_Attribute, String_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Armador_morada_sede_Attribute: Attribute 

!set Armador_morada_sede_Attribute.name :='morada_sede'

!set Armador_morada_sede_Attribute.visibility := #private

!insert (Armador_Class, Armador_morada_sede_Attribute) into Classifier_Feature

!insert (Armador_morada_sede_Attribute, String_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Armador_capital_social_Attribute: Attribute 

!set Armador_capital_social_Attribute.name :='capital_social'

!set Armador_capital_social_Attribute.visibility := #private

!insert (Armador_Class, Armador_capital_social_Attribute) into Classifier_Feature

!insert (Armador_capital_social_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Armador_no_contribuinte_Attribute: Attribute 

!set Armador_no_contribuinte_Attribute.name :='no_contribuinte'

!set Armador_no_contribuinte_Attribute.visibility := #private

!insert (Armador_Class, Armador_no_contribuinte_Attribute) into Classifier_Feature

!insert (Armador_no_contribuinte_Attribute, Integer_Class) into StructuralFeature_Classifier


------------------------------------------------------------------------

!create Embarcacoes_Package: Package

!set Embarcacoes_Package.name := 'Embarcacoes'

!insert (Navio_Model, Embarcacoes_Package) into NamespaceImpl_ModelElementImpl

------------------------------------------------------------------------

--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Item_Class: MMClass 

!set Item_Class.name :='Item'

!set Item_Class.isRoot := true

!set Item_Class.isLeaf := false

!set Item_Class.isAbstract := false

!insert (Embarcacoes_Package, Item_Class) into NamespaceImpl_ModelElementImpl 



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create NaoPerecivel_Class: MMClass 

!set NaoPerecivel_Class.name :='NaoPerecivel'

!set NaoPerecivel_Class.isRoot := false

!set NaoPerecivel_Class.isLeaf := false

!set NaoPerecivel_Class.isAbstract := false

!insert (Embarcacoes_Package, NaoPerecivel_Class) into NamespaceImpl_ModelElementImpl 

!create Item_NaoPerecivel_Generalization: Generalization

!set Item_NaoPerecivel_Generalization.name :='Item_NaoPerecivel'

!set Item_NaoPerecivel_Generalization.discriminator :=''

!insert (Item_NaoPerecivel_Generalization, Item_Class) into Generalization_GeneralizableElementImpl1

!insert (Item_NaoPerecivel_Generalization, NaoPerecivel_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Perecivel_Class: MMClass 

!set Perecivel_Class.name :='Perecivel'

!set Perecivel_Class.isRoot := false

!set Perecivel_Class.isLeaf := false

!set Perecivel_Class.isAbstract := false

!insert (Embarcacoes_Package, Perecivel_Class) into NamespaceImpl_ModelElementImpl 

!create Item_Perecivel_Generalization: Generalization

!set Item_Perecivel_Generalization.name :='Item_Perecivel'

!set Item_Perecivel_Generalization.discriminator :=''

!insert (Item_Perecivel_Generalization, Item_Class) into Generalization_GeneralizableElementImpl1

!insert (Item_Perecivel_Generalization, Perecivel_Class) into Generalization_GeneralizableElementImpl2


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Sobressalente_Class: MMClass 

!set Sobressalente_Class.name :='Sobressalente'

!set Sobressalente_Class.isRoot := false

!set Sobressalente_Class.isLeaf := true

!set Sobressalente_Class.isAbstract := false

!insert (Embarcacoes_Package, Sobressalente_Class) into NamespaceImpl_ModelElementImpl 

!create NaoPerecivel_Sobressalente_Generalization: Generalization

!set NaoPerecivel_Sobressalente_Generalization.name :='NaoPerecivel_Sobressalente'

!set NaoPerecivel_Sobressalente_Generalization.discriminator :=''

!insert (NaoPerecivel_Sobressalente_Generalization, NaoPerecivel_Class) into Generalization_GeneralizableElementImpl1

!insert (NaoPerecivel_Sobressalente_Generalization, Sobressalente_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Apresto_Class: MMClass 

!set Apresto_Class.name :='Apresto'

!set Apresto_Class.isRoot := false

!set Apresto_Class.isLeaf := true

!set Apresto_Class.isAbstract := false

!insert (Embarcacoes_Package, Apresto_Class) into NamespaceImpl_ModelElementImpl 

!create NaoPerecivel_Apresto_Generalization: Generalization

!set NaoPerecivel_Apresto_Generalization.name :='NaoPerecivel_Apresto'

!set NaoPerecivel_Apresto_Generalization.discriminator :=''

!insert (NaoPerecivel_Apresto_Generalization, NaoPerecivel_Class) into Generalization_GeneralizableElementImpl1

!insert (NaoPerecivel_Apresto_Generalization, Apresto_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Alimento_Class: MMClass 

!set Alimento_Class.name :='Alimento'

!set Alimento_Class.isRoot := false

!set Alimento_Class.isLeaf := true

!set Alimento_Class.isAbstract := false

!insert (Embarcacoes_Package, Alimento_Class) into NamespaceImpl_ModelElementImpl 

!create Perecivel_Alimento_Generalization: Generalization

!set Perecivel_Alimento_Generalization.name :='Perecivel_Alimento'

!set Perecivel_Alimento_Generalization.discriminator :=''

!insert (Perecivel_Alimento_Generalization, Perecivel_Class) into Generalization_GeneralizableElementImpl1

!insert (Perecivel_Alimento_Generalization, Alimento_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Armazem_Class: MMClass 

!set Armazem_Class.name :='Armazem'

!set Armazem_Class.isRoot := true

!set Armazem_Class.isLeaf := false

!set Armazem_Class.isAbstract := true

!insert (Embarcacoes_Package, Armazem_Class) into NamespaceImpl_ModelElementImpl 



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create PaiolConves_Class: MMClass 

!set PaiolConves_Class.name :='PaiolConves'

!set PaiolConves_Class.isRoot := true

!set PaiolConves_Class.isLeaf := true

!set PaiolConves_Class.isAbstract := false

!insert (Embarcacoes_Package, PaiolConves_Class) into NamespaceImpl_ModelElementImpl 


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create PaiolMaquina_Class: MMClass 

!set PaiolMaquina_Class.name :='PaiolMaquina'

!set PaiolMaquina_Class.isRoot := true

!set PaiolMaquina_Class.isLeaf := true

!set PaiolMaquina_Class.isAbstract := false

!insert (Embarcacoes_Package, PaiolMaquina_Class) into NamespaceImpl_ModelElementImpl 


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create CamaraFrigorifica_Class: MMClass 

!set CamaraFrigorifica_Class.name :='CamaraFrigorifica'

!set CamaraFrigorifica_Class.isRoot := true

!set CamaraFrigorifica_Class.isLeaf := true

!set CamaraFrigorifica_Class.isAbstract := false

!insert (Embarcacoes_Package, CamaraFrigorifica_Class) into NamespaceImpl_ModelElementImpl 


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Veiculo_Class: MMClass 

!set Veiculo_Class.name :='Veiculo'

!set Veiculo_Class.isRoot := true

!set Veiculo_Class.isLeaf := false

!set Veiculo_Class.isAbstract := false

!insert (Embarcacoes_Package, Veiculo_Class) into NamespaceImpl_ModelElementImpl 


-----------------------------------------------------------------------------------
!create Veiculo_numero_lugares_Attribute: Attribute 

!set Veiculo_numero_lugares_Attribute.name :='numero_lugares'

!set Veiculo_numero_lugares_Attribute.visibility := #private

!insert (Veiculo_Class, Veiculo_numero_lugares_Attribute) into Classifier_Feature

!insert (Veiculo_numero_lugares_Attribute, Integer_Class) into StructuralFeature_Classifier

--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Maritimo_Class: MMClass 

!set Maritimo_Class.name :='Maritimo'

!set Maritimo_Class.isRoot := false

!set Maritimo_Class.isLeaf := false

!set Maritimo_Class.isAbstract := false

!insert (Embarcacoes_Package, Maritimo_Class) into NamespaceImpl_ModelElementImpl 

!create Veiculo_Maritimo_Generalization: Generalization

!set Veiculo_Maritimo_Generalization.name :='Veiculo_Maritimo'

!set Veiculo_Maritimo_Generalization.discriminator :=''

!insert (Veiculo_Maritimo_Generalization, Veiculo_Class) into Generalization_GeneralizableElementImpl1

!insert (Veiculo_Maritimo_Generalization, Maritimo_Class) into Generalization_GeneralizableElementImpl2



-----------------------------------------------------------------------------------
!create Maritimo_numero_helices_Attribute: Attribute 

!set Maritimo_numero_helices_Attribute.name :='numero_helices'

!set Maritimo_numero_helices_Attribute.visibility := #private

!insert (Maritimo_Class, Maritimo_numero_helices_Attribute) into Classifier_Feature

!insert (Maritimo_numero_helices_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Maritimo_velocidade_maxima_Attribute: Attribute 

!set Maritimo_velocidade_maxima_Attribute.name :='velocidade_maxima'

!set Maritimo_velocidade_maxima_Attribute.visibility := #private

!insert (Maritimo_Class, Maritimo_velocidade_maxima_Attribute) into Classifier_Feature

!insert (Maritimo_velocidade_maxima_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Maritimo_autonomia_Attribute: Attribute 

!set Maritimo_autonomia_Attribute.name :='autonomia'

!set Maritimo_autonomia_Attribute.visibility := #private

!insert (Maritimo_Class, Maritimo_autonomia_Attribute) into Classifier_Feature

!insert (Maritimo_autonomia_Attribute, Integer_Class) into StructuralFeature_Classifier


--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Terrestre_Class: MMClass 

!set Terrestre_Class.name :='Terrestre'

!set Terrestre_Class.isRoot := false

!set Terrestre_Class.isLeaf := false

!set Terrestre_Class.isAbstract := false

!insert (Embarcacoes_Package, Terrestre_Class) into NamespaceImpl_ModelElementImpl 

!create Veiculo_Terrestre_Generalization: Generalization

!set Veiculo_Terrestre_Generalization.name :='Veiculo_Terrestre'

!set Veiculo_Terrestre_Generalization.discriminator :=''

!insert (Veiculo_Terrestre_Generalization, Veiculo_Class) into Generalization_GeneralizableElementImpl1

!insert (Veiculo_Terrestre_Generalization, Terrestre_Class) into Generalization_GeneralizableElementImpl2



-----------------------------------------------------------------------------------
!create Terrestre_numero_eixos_Attribute: Attribute 

!set Terrestre_numero_eixos_Attribute.name :='numero_eixos'

!set Terrestre_numero_eixos_Attribute.visibility := #private

!insert (Terrestre_Class, Terrestre_numero_eixos_Attribute) into Classifier_Feature

!insert (Terrestre_numero_eixos_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Terrestre_tipo_pneus_Attribute: Attribute 

!set Terrestre_tipo_pneus_Attribute.name :='tipo_pneus'

!set Terrestre_tipo_pneus_Attribute.visibility := #private

!insert (Terrestre_Class, Terrestre_tipo_pneus_Attribute) into Classifier_Feature

!insert (Terrestre_tipo_pneus_Attribute, String_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Terrestre_numero_portas_Attribute: Attribute 

!set Terrestre_numero_portas_Attribute.name :='numero_portas'

!set Terrestre_numero_portas_Attribute.visibility := #private

!insert (Terrestre_Class, Terrestre_numero_portas_Attribute) into Classifier_Feature

!insert (Terrestre_numero_portas_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Terrestre_velocidade_maxima_Attribute: Attribute 

!set Terrestre_velocidade_maxima_Attribute.name :='velocidade_maxima'

!set Terrestre_velocidade_maxima_Attribute.visibility := #private

!insert (Terrestre_Class, Terrestre_velocidade_maxima_Attribute) into Classifier_Feature

!insert (Terrestre_velocidade_maxima_Attribute, Integer_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Terrestre_autonomia_Attribute: Attribute 

!set Terrestre_autonomia_Attribute.name :='autonomia'

!set Terrestre_autonomia_Attribute.visibility := #private

!insert (Terrestre_Class, Terrestre_autonomia_Attribute) into Classifier_Feature

!insert (Terrestre_autonomia_Attribute, Integer_Class) into StructuralFeature_Classifier

--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Anfibio_Class: MMClass 

!set Anfibio_Class.name :='Anfibio'

!set Anfibio_Class.isRoot := false

!set Anfibio_Class.isLeaf := true

!set Anfibio_Class.isAbstract := false

!insert (Embarcacoes_Package, Anfibio_Class) into NamespaceImpl_ModelElementImpl 

!create Terrestre_Anfibio_Generalization: Generalization

!set Terrestre_Anfibio_Generalization.name :='Terrestre_Anfibio'

!set Terrestre_Anfibio_Generalization.discriminator :=''

!insert (Terrestre_Anfibio_Generalization, Terrestre_Class) into Generalization_GeneralizableElementImpl1

!insert (Terrestre_Anfibio_Generalization, Anfibio_Class) into Generalization_GeneralizableElementImpl2


!create Maritimo_Anfibio_Generalization: Generalization

!set Maritimo_Anfibio_Generalization.name :='Maritimo_Anfibio'

!set Maritimo_Anfibio_Generalization.discriminator :=''

!insert (Maritimo_Anfibio_Generalization, Maritimo_Class) into Generalization_GeneralizableElementImpl1

!insert (Maritimo_Anfibio_Generalization, Anfibio_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Propulsao_Class: MMClass 

!set Propulsao_Class.name :='Propulsao'

!set Propulsao_Class.isRoot := true

!set Propulsao_Class.isLeaf := false

!set Propulsao_Class.isAbstract := false

!insert (Embarcacoes_Package, Propulsao_Class) into NamespaceImpl_ModelElementImpl 



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Vela_Class: MMClass 

!set Vela_Class.name :='Vela'

!set Vela_Class.isRoot := false

!set Vela_Class.isLeaf := false

!set Vela_Class.isAbstract := false

!insert (Embarcacoes_Package, Vela_Class) into NamespaceImpl_ModelElementImpl 

!create Propulsao_Vela_Generalization: Generalization

!set Propulsao_Vela_Generalization.name :='Propulsao_Vela'

!set Propulsao_Vela_Generalization.discriminator :=''

!insert (Propulsao_Vela_Generalization, Propulsao_Class) into Generalization_GeneralizableElementImpl1

!insert (Propulsao_Vela_Generalization, Vela_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Motor_Class: MMClass 

!set Motor_Class.name :='Motor'

!set Motor_Class.isRoot := false

!set Motor_Class.isLeaf := false

!set Motor_Class.isAbstract := false

!insert (Embarcacoes_Package, Motor_Class) into NamespaceImpl_ModelElementImpl 

!create Propulsao_Motor_Generalization: Generalization

!set Propulsao_Motor_Generalization.name :='Propulsao_Motor'

!set Propulsao_Motor_Generalization.discriminator :=''

!insert (Propulsao_Motor_Generalization, Propulsao_Class) into Generalization_GeneralizableElementImpl1

!insert (Propulsao_Motor_Generalization, Motor_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Misto_Class: MMClass 

!set Misto_Class.name :='Misto'

!set Misto_Class.isRoot := false

!set Misto_Class.isLeaf := true

!set Misto_Class.isAbstract := false

!insert (Embarcacoes_Package, Misto_Class) into NamespaceImpl_ModelElementImpl 

!create Motor_Misto_Generalization: Generalization

!set Motor_Misto_Generalization.name :='Motor_Misto'

!set Motor_Misto_Generalization.discriminator :=''

!insert (Motor_Misto_Generalization, Motor_Class) into Generalization_GeneralizableElementImpl1

!insert (Motor_Misto_Generalization, Misto_Class) into Generalization_GeneralizableElementImpl2


!create Vela_Misto_Generalization: Generalization

!set Vela_Misto_Generalization.name :='Vela_Misto'

!set Vela_Misto_Generalization.discriminator :=''

!insert (Vela_Misto_Generalization, Vela_Class) into Generalization_GeneralizableElementImpl1

!insert (Vela_Misto_Generalization, Misto_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Embarcacao_Class: MMClass 

!set Embarcacao_Class.name :='Embarcacao'

!set Embarcacao_Class.isRoot := true

!set Embarcacao_Class.isLeaf := false

!set Embarcacao_Class.isAbstract := false

!insert (Embarcacoes_Package, Embarcacao_Class) into NamespaceImpl_ModelElementImpl 

!insert (Posicionamento_Package, Embarcacao_Class) into NamespaceImpl_ModelElementImpl 
-----------------------------------------------------------------------------------
!create Embarcacao_nome_Attribute: Attribute 

!set Embarcacao_nome_Attribute.name :='nome'

!set Embarcacao_nome_Attribute.visibility := #protected

!insert (Embarcacao_Class, Embarcacao_nome_Attribute) into Classifier_Feature

!insert (Embarcacao_nome_Attribute, String_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Embarcacao_arqueacao_Attribute: Attribute 

!set Embarcacao_arqueacao_Attribute.name :='arqueacao'

!set Embarcacao_arqueacao_Attribute.visibility := #private

!insert (Embarcacao_Class, Embarcacao_arqueacao_Attribute) into Classifier_Feature

!insert (Embarcacao_arqueacao_Attribute, Long_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Embarcacao_calado_Attribute: Attribute 

!set Embarcacao_calado_Attribute.name :='calado'

!set Embarcacao_calado_Attribute.visibility := #public

!insert (Embarcacao_Class, Embarcacao_calado_Attribute) into Classifier_Feature

!insert (Embarcacao_calado_Attribute, Double_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Embarcacao_data_construcao_Attribute: Attribute 

!set Embarcacao_data_construcao_Attribute.name :='data_construcao'

!set Embarcacao_data_construcao_Attribute.visibility := #private

!insert (Embarcacao_Class, Embarcacao_data_construcao_Attribute) into Classifier_Feature

!insert (Embarcacao_data_construcao_Attribute, Date_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Embarcacao_andamento_Attribute: Attribute 

!set Embarcacao_andamento_Attribute.name :='andamento'

!set Embarcacao_andamento_Attribute.visibility := #private

!insert (Embarcacao_Class, Embarcacao_andamento_Attribute) into Classifier_Feature

!insert (Embarcacao_andamento_Attribute, Double_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Embarcacao_rumo_Attribute: Attribute 

!set Embarcacao_rumo_Attribute.name :='rumo'

!set Embarcacao_rumo_Attribute.visibility := #private

!insert (Embarcacao_Class, Embarcacao_rumo_Attribute) into Classifier_Feature

!insert (Embarcacao_rumo_Attribute, Double_Class) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Embarcacao_situacao_Attribute: Attribute 

!set Embarcacao_situacao_Attribute.name :='situacao'

!set Embarcacao_situacao_Attribute.visibility := #private

!insert (Embarcacao_Class, Embarcacao_situacao_Attribute) into Classifier_Feature

!insert (Embarcacao_situacao_Attribute, EnumType_1) into StructuralFeature_Classifier

-----------------------------------------------------------------------------------
!create Embarcacao_Atracar_Operation: Operation

!set Embarcacao_Atracar_Operation.name :='Atracar'

!insert (Embarcacao_Class, Embarcacao_Atracar_Operation) into Classifier_Feature

!insert (Embarcacao_Atracar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Fundear_Operation: Operation

!set Embarcacao_Fundear_Operation.name :='Fundear'

!insert (Embarcacao_Class, Embarcacao_Fundear_Operation) into Classifier_Feature

!insert (Embarcacao_Fundear_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Aproar_Operation: Operation

!set Embarcacao_Aproar_Operation.name :='Aproar'

!insert (Embarcacao_Class, Embarcacao_Aproar_Operation) into Classifier_Feature

!insert (Embarcacao_Aproar_Operation, Boolean_Class) into BehavioralFeature_Classifier

!create Aproar_novo_rumo_Parameter: Parameter

!set Aproar_novo_rumo_Parameter.name := 'novo_rumo'

!insert (Embarcacao_Aproar_Operation, Aproar_novo_rumo_Parameter) into BehavioralFeature_Parameter

!insert (Aproar_novo_rumo_Parameter, Posicao_Class) into Parameter_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Navegar_Operation: Operation

!set Embarcacao_Navegar_Operation.name :='Navegar'

!insert (Embarcacao_Class, Embarcacao_Navegar_Operation) into Classifier_Feature

!insert (Embarcacao_Navegar_Operation, Boolean_Class) into BehavioralFeature_Classifier

!create Navegar_nos_Parameter: Parameter

!set Navegar_nos_Parameter.name := 'nos'

!insert (Embarcacao_Navegar_Operation, Navegar_nos_Parameter) into BehavioralFeature_Parameter

!insert (Navegar_nos_Parameter, Integer_Class) into Parameter_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Projectar_Operation: Operation

!set Embarcacao_Projectar_Operation.name :='Projectar'

!insert (Embarcacao_Class, Embarcacao_Projectar_Operation) into Classifier_Feature

!insert (Embarcacao_Projectar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Construir_Operation: Operation

!set Embarcacao_Construir_Operation.name :='Construir'

!insert (Embarcacao_Class, Embarcacao_Construir_Operation) into Classifier_Feature

!insert (Embarcacao_Construir_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Ensaiar_Operation: Operation

!set Embarcacao_Ensaiar_Operation.name :='Ensaiar'

!insert (Embarcacao_Class, Embarcacao_Ensaiar_Operation) into Classifier_Feature

!insert (Embarcacao_Ensaiar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Aprovar_Operation: Operation

!set Embarcacao_Aprovar_Operation.name :='Aprovar'

!insert (Embarcacao_Class, Embarcacao_Aprovar_Operation) into Classifier_Feature

!insert (Embarcacao_Aprovar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Reprovar_Operation: Operation

!set Embarcacao_Reprovar_Operation.name :='Reprovar'

!insert (Embarcacao_Class, Embarcacao_Reprovar_Operation) into Classifier_Feature

!insert (Embarcacao_Reprovar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_TerminarTrabalhos_Operation: Operation

!set Embarcacao_TerminarTrabalhos_Operation.name :='TerminarTrabalhos'

!insert (Embarcacao_Class, Embarcacao_TerminarTrabalhos_Operation) into Classifier_Feature

!insert (Embarcacao_TerminarTrabalhos_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Avariar_Operation: Operation

!set Embarcacao_Avariar_Operation.name :='Avariar'

!insert (Embarcacao_Class, Embarcacao_Avariar_Operation) into Classifier_Feature

!insert (Embarcacao_Avariar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Manter_Operation: Operation

!set Embarcacao_Manter_Operation.name :='Manter'

!insert (Embarcacao_Class, Embarcacao_Manter_Operation) into Classifier_Feature

!insert (Embarcacao_Manter_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Abater_Operation: Operation

!set Embarcacao_Abater_Operation.name :='Abater'

!insert (Embarcacao_Class, Embarcacao_Abater_Operation) into Classifier_Feature

!insert (Embarcacao_Abater_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Desmantelar_Operation: Operation

!set Embarcacao_Desmantelar_Operation.name :='Desmantelar'

!insert (Embarcacao_Class, Embarcacao_Desmantelar_Operation) into Classifier_Feature

!insert (Embarcacao_Desmantelar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Reclassificar_Operation: Operation

!set Embarcacao_Reclassificar_Operation.name :='Reclassificar'

!insert (Embarcacao_Class, Embarcacao_Reclassificar_Operation) into Classifier_Feature

!insert (Embarcacao_Reclassificar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Encalhar_Operation: Operation

!set Embarcacao_Encalhar_Operation.name :='Encalhar'

!insert (Embarcacao_Class, Embarcacao_Encalhar_Operation) into Classifier_Feature

!insert (Embarcacao_Encalhar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Desencalhar_Operation: Operation

!set Embarcacao_Desencalhar_Operation.name :='Desencalhar'

!insert (Embarcacao_Class, Embarcacao_Desencalhar_Operation) into Classifier_Feature

!insert (Embarcacao_Desencalhar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Degradar_Operation: Operation

!set Embarcacao_Degradar_Operation.name :='Degradar'

!insert (Embarcacao_Class, Embarcacao_Degradar_Operation) into Classifier_Feature

!insert (Embarcacao_Degradar_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Corroer_Operation: Operation

!set Embarcacao_Corroer_Operation.name :='Corroer'

!insert (Embarcacao_Class, Embarcacao_Corroer_Operation) into Classifier_Feature

!insert (Embarcacao_Corroer_Operation, Boolean_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Afundar_Operation: Operation

!set Embarcacao_Afundar_Operation.name :='Afundar'

!insert (Embarcacao_Class, Embarcacao_Afundar_Operation) into Classifier_Feature

!insert (Embarcacao_Afundar_Operation, UNKNOWN_Class) into BehavioralFeature_Classifier


-----------------------------------------------------------------------------------
!create Embarcacao_Manobrar_Operation: Operation

!set Embarcacao_Manobrar_Operation.name :='Manobrar'

!insert (Embarcacao_Class, Embarcacao_Manobrar_Operation) into Classifier_Feature

!insert (Embarcacao_Manobrar_Operation, Boolean_Class) into BehavioralFeature_Classifier


---------------------------------
-- FBA 2013
!insert (Tripulante_NavioCorrente_Operation, Embarcacao_Class) into BehavioralFeature_Classifier
!insert (Embarca_destino_Parameter, Embarcacao_Class) into Parameter_Classifier

--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Porao_Class: MMClass 

!set Porao_Class.name :='Porao'

!set Porao_Class.isRoot := true

!set Porao_Class.isLeaf := true

!set Porao_Class.isAbstract := false

!insert (Embarcacoes_Package, Porao_Class) into NamespaceImpl_ModelElementImpl 



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Conves_Class: MMClass 

!set Conves_Class.name :='Conves'

!set Conves_Class.isRoot := true

!set Conves_Class.isLeaf := true

!set Conves_Class.isAbstract := false

!insert (Embarcacoes_Package, Conves_Class) into NamespaceImpl_ModelElementImpl 



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Ponte_Class: MMClass 

!set Ponte_Class.name :='Ponte'

!set Ponte_Class.isRoot := true

!set Ponte_Class.isLeaf := true

!set Ponte_Class.isAbstract := false

!insert (Embarcacoes_Package, Ponte_Class) into NamespaceImpl_ModelElementImpl 



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create CasaMaquina_Class: MMClass 

!set CasaMaquina_Class.name :='CasaMaquina'

!set CasaMaquina_Class.isRoot := true

!set CasaMaquina_Class.isLeaf := true

!set CasaMaquina_Class.isAbstract := false

!insert (Embarcacoes_Package, CasaMaquina_Class) into NamespaceImpl_ModelElementImpl 



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

!create Tanque_Class: MMClass 

!set Tanque_Class.name :='Tanque'

!set Tanque_Class.isRoot := true

!set Tanque_Class.isLeaf := true

!set Tanque_Class.isAbstract := false

!insert (Embarcacoes_Package, Tanque_Class) into NamespaceImpl_ModelElementImpl 



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create InstalacoesTripulacao_Class: MMClass 

!set InstalacoesTripulacao_Class.name :='InstalacoesTripulacao'

!set InstalacoesTripulacao_Class.isRoot := true

!set InstalacoesTripulacao_Class.isLeaf := true

!set InstalacoesTripulacao_Class.isAbstract := false

!insert (Embarcacoes_Package, InstalacoesTripulacao_Class) into NamespaceImpl_ModelElementImpl 






--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create RodaLeme_Class: MMClass 

!set RodaLeme_Class.name :='RodaLeme'

!set RodaLeme_Class.isRoot := true

!set RodaLeme_Class.isLeaf := true

!set RodaLeme_Class.isAbstract := false

!insert (Embarcacoes_Package, RodaLeme_Class) into NamespaceImpl_ModelElementImpl 

-----------------------------------------------------------------------------------
!create RodaLeme_RodaEstibordo_Operation: Operation

!set RodaLeme_RodaEstibordo_Operation.name :='RodaEstibordo'

!insert (RodaLeme_Class, RodaLeme_RodaEstibordo_Operation) into Classifier_Feature

!insert (RodaLeme_RodaEstibordo_Operation, Boolean_Class) into BehavioralFeature_Classifier



-----------------------------------------------------------------------------------
!create RodaLeme_RodaBombordo_Operation: Operation

!set RodaLeme_RodaBombordo_Operation.name :='RodaBombordo'

!insert (RodaLeme_Class, RodaLeme_RodaBombordo_Operation) into Classifier_Feature

!insert (RodaLeme_RodaBombordo_Operation, Boolean_Class) into BehavioralFeature_Classifier



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create GiroBussula_Class: MMClass 

!set GiroBussula_Class.name :='GiroBussula'

!set GiroBussula_Class.isRoot := true

!set GiroBussula_Class.isLeaf := true

!set GiroBussula_Class.isAbstract := false

!insert (Embarcacoes_Package, GiroBussula_Class) into NamespaceImpl_ModelElementImpl 

-----------------------------------------------------------------------------------
!create GiroBussula_Calibra_Operation: Operation

!set GiroBussula_Calibra_Operation.name :='Calibra'

!insert (GiroBussula_Class, GiroBussula_Calibra_Operation) into Classifier_Feature

!insert (GiroBussula_Calibra_Operation, UNKNOWN_Class) into BehavioralFeature_Classifier



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Relogio_Class: MMClass 

!set Relogio_Class.name :='Relogio'

!set Relogio_Class.isRoot := true

!set Relogio_Class.isLeaf := true

!set Relogio_Class.isAbstract := false

!insert (Embarcacoes_Package, Relogio_Class) into NamespaceImpl_ModelElementImpl 

-----------------------------------------------------------------------------------
!create Relogio_Acerta_Operation: Operation

!set Relogio_Acerta_Operation.name :='Acerta'

!insert (Relogio_Class, Relogio_Acerta_Operation) into Classifier_Feature

!insert (Relogio_Acerta_Operation, UNKNOWN_Class) into BehavioralFeature_Classifier

!create Acerta_nova_hora_Parameter: Parameter

!set Acerta_nova_hora_Parameter.name := 'nova_hora'

!insert (Relogio_Acerta_Operation, Acerta_nova_hora_Parameter) into BehavioralFeature_Parameter

!insert (Acerta_nova_hora_Parameter, Time_Class) into Parameter_Classifier


-----------------------------------------------------------------------------------
!create Relogio_ForneceHora_Operation: Operation

!set Relogio_ForneceHora_Operation.name :='ForneceHora'

!insert (Relogio_Class, Relogio_ForneceHora_Operation) into Classifier_Feature

!insert (Relogio_ForneceHora_Operation, Time_Class) into BehavioralFeature_Classifier



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create TabelasNauticas_Class: MMClass 

!set TabelasNauticas_Class.name :='TabelasNauticas'

!set TabelasNauticas_Class.isRoot := true

!set TabelasNauticas_Class.isLeaf := true

!set TabelasNauticas_Class.isAbstract := false

!insert (Embarcacoes_Package, TabelasNauticas_Class) into NamespaceImpl_ModelElementImpl 

-----------------------------------------------------------------------------------
!create TabelasNauticas_DaPosicao_Operation: Operation

!set TabelasNauticas_DaPosicao_Operation.name :='DaPosicao'

!insert (TabelasNauticas_Class, TabelasNauticas_DaPosicao_Operation) into Classifier_Feature

!insert (TabelasNauticas_DaPosicao_Operation, Posicao_Class) into BehavioralFeature_Classifier

!create DaPosicao_altura_sol_Parameter: Parameter

!set DaPosicao_altura_sol_Parameter.name := 'altura_sol'

!insert (TabelasNauticas_DaPosicao_Operation, DaPosicao_altura_sol_Parameter) into BehavioralFeature_Parameter

!insert (DaPosicao_altura_sol_Parameter, Coordenada_Class) into Parameter_Classifier

!create DaPosicao_azimute_sol_Parameter: Parameter

!set DaPosicao_azimute_sol_Parameter.name := 'azimute_sol'

!insert (TabelasNauticas_DaPosicao_Operation, DaPosicao_azimute_sol_Parameter) into BehavioralFeature_Parameter

!insert (DaPosicao_azimute_sol_Parameter, Coordenada_Class) into Parameter_Classifier

!create DaPosicao_instante_observacao_Parameter: Parameter

!set DaPosicao_instante_observacao_Parameter.name := 'instante_observacao'

!insert (TabelasNauticas_DaPosicao_Operation, DaPosicao_instante_observacao_Parameter) into BehavioralFeature_Parameter

!insert (DaPosicao_instante_observacao_Parameter, Instante_Class) into Parameter_Classifier



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Recreio_Class: MMClass 

!set Recreio_Class.name :='Recreio'

!set Recreio_Class.isRoot := false

!set Recreio_Class.isLeaf := true

!set Recreio_Class.isAbstract := false

!insert (Embarcacoes_Package, Recreio_Class) into NamespaceImpl_ModelElementImpl 

!create Embarcacao_Recreio_Generalization: Generalization

!set Embarcacao_Recreio_Generalization.name :='Embarcacao_Recreio'

!set Embarcacao_Recreio_Generalization.discriminator :=''

!insert (Embarcacao_Recreio_Generalization, Embarcacao_Class) into Generalization_GeneralizableElementImpl1

!insert (Embarcacao_Recreio_Generalization, Recreio_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Pesca_Class: MMClass 

!set Pesca_Class.name :='Pesca'

!set Pesca_Class.isRoot := false

!set Pesca_Class.isLeaf := true

!set Pesca_Class.isAbstract := false

!insert (Embarcacoes_Package, Pesca_Class) into NamespaceImpl_ModelElementImpl 

!create Embarcacao_Pesca_Generalization: Generalization

!set Embarcacao_Pesca_Generalization.name :='Embarcacao_Pesca'

!set Embarcacao_Pesca_Generalization.discriminator :=''

!insert (Embarcacao_Pesca_Generalization, Embarcacao_Class) into Generalization_GeneralizableElementImpl1

!insert (Embarcacao_Pesca_Generalization, Pesca_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Comercio_Class: MMClass 

!set Comercio_Class.name :='Comercio'

!set Comercio_Class.isRoot := false

!set Comercio_Class.isLeaf := false

!set Comercio_Class.isAbstract := false

!insert (Embarcacoes_Package, Comercio_Class) into NamespaceImpl_ModelElementImpl 

!create Embarcacao_Comercio_Generalization: Generalization

!set Embarcacao_Comercio_Generalization.name :='Embarcacao_Comercio'

!set Embarcacao_Comercio_Generalization.discriminator :=''

!insert (Embarcacao_Comercio_Generalization, Embarcacao_Class) into Generalization_GeneralizableElementImpl1

!insert (Embarcacao_Comercio_Generalization, Comercio_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Guerra_Class: MMClass 

!set Guerra_Class.name :='Guerra'

!set Guerra_Class.isRoot := false

!set Guerra_Class.isLeaf := true

!set Guerra_Class.isAbstract := false

!insert (Embarcacoes_Package, Guerra_Class) into NamespaceImpl_ModelElementImpl 

!create Embarcacao_Guerra_Generalization: Generalization

!set Embarcacao_Guerra_Generalization.name :='Embarcacao_Guerra'

!set Embarcacao_Guerra_Generalization.discriminator :=''

!insert (Embarcacao_Guerra_Generalization, Embarcacao_Class) into Generalization_GeneralizableElementImpl1

!insert (Embarcacao_Guerra_Generalization, Guerra_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Petroleiro_Class: MMClass 

!set Petroleiro_Class.name :='Petroleiro'

!set Petroleiro_Class.isRoot := false

!set Petroleiro_Class.isLeaf := true

!set Petroleiro_Class.isAbstract := false

!insert (Embarcacoes_Package, Petroleiro_Class) into NamespaceImpl_ModelElementImpl 

!create Comercio_Petroleiro_Generalization: Generalization

!set Comercio_Petroleiro_Generalization.name :='Comercio_Petroleiro'

!set Comercio_Petroleiro_Generalization.discriminator :=''

!insert (Comercio_Petroleiro_Generalization, Comercio_Class) into Generalization_GeneralizableElementImpl1

!insert (Comercio_Petroleiro_Generalization, Petroleiro_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create Quimico_Class: MMClass 

!set Quimico_Class.name :='Quimico'

!set Quimico_Class.isRoot := false

!set Quimico_Class.isLeaf := true

!set Quimico_Class.isAbstract := false

!insert (Embarcacoes_Package, Quimico_Class) into NamespaceImpl_ModelElementImpl 

!create Comercio_Quimico_Generalization: Generalization

!set Comercio_Quimico_Generalization.name :='Comercio_Quimico'

!set Comercio_Quimico_Generalization.discriminator :=''

!insert (Comercio_Quimico_Generalization, Comercio_Class) into Generalization_GeneralizableElementImpl1

!insert (Comercio_Quimico_Generalization, Quimico_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create CargaGeral_Class: MMClass 

!set CargaGeral_Class.name :='CargaGeral'

!set CargaGeral_Class.isRoot := false

!set CargaGeral_Class.isLeaf := true

!set CargaGeral_Class.isAbstract := false

!insert (Embarcacoes_Package, CargaGeral_Class) into NamespaceImpl_ModelElementImpl 

!create Comercio_CargaGeral_Generalization: Generalization

!set Comercio_CargaGeral_Generalization.name :='Comercio_CargaGeral'

!set Comercio_CargaGeral_Generalization.discriminator :=''

!insert (Comercio_CargaGeral_Generalization, Comercio_Class) into Generalization_GeneralizableElementImpl1

!insert (Comercio_CargaGeral_Generalization, CargaGeral_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create PortaContentores_Class: MMClass 

!set PortaContentores_Class.name :='PortaContentores'

!set PortaContentores_Class.isRoot := false

!set PortaContentores_Class.isLeaf := true

!set PortaContentores_Class.isAbstract := false

!insert (Embarcacoes_Package, PortaContentores_Class) into NamespaceImpl_ModelElementImpl 

!create Comercio_PortaContentores_Generalization: Generalization

!set Comercio_PortaContentores_Generalization.name :='Comercio_PortaContentores'

!set Comercio_PortaContentores_Generalization.discriminator :=''

!insert (Comercio_PortaContentores_Generalization, Comercio_Class) into Generalization_GeneralizableElementImpl1

!insert (Comercio_PortaContentores_Generalization, PortaContentores_Class) into Generalization_GeneralizableElementImpl2



--:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
!create RollOnRollOff_Class: MMClass 

!set RollOnRollOff_Class.name :='RollOnRollOff'

!set RollOnRollOff_Class.isRoot := false

!set RollOnRollOff_Class.isLeaf := true

!set RollOnRollOff_Class.isAbstract := false

!insert (Embarcacoes_Package, RollOnRollOff_Class) into NamespaceImpl_ModelElementImpl 

!create Comercio_RollOnRollOff_Generalization: Generalization

!set Comercio_RollOnRollOff_Generalization.name :='Comercio_RollOnRollOff'

!set Comercio_RollOnRollOff_Generalization.discriminator :=''

!insert (Comercio_RollOnRollOff_Generalization, Comercio_Class) into Generalization_GeneralizableElementImpl1

!insert (Comercio_RollOnRollOff_Generalization, RollOnRollOff_Class) into Generalization_GeneralizableElementImpl2



------------------------------------------------------------------------
-- Associations of Posicionamento
------------------------------------------------------------------------


!create momento_obtencao_Association: Association

!set momento_obtencao_Association.name := 'momento_obtencao'

!insert (Posicionamento_Package,momento_obtencao_Association) into NamespaceImpl_ModelElementImpl



!create momento_obtencao_Instante_AssociationEnd: AssociationEnd

!set momento_obtencao_Instante_AssociationEnd.name := 'instante'

!set momento_obtencao_Instante_AssociationEnd.aggregation_ := #none

!set momento_obtencao_Instante_AssociationEnd.multiplicity := '*'

!insert (momento_obtencao_Association,momento_obtencao_Instante_AssociationEnd) into Association_AssociationEnd

!insert (momento_obtencao_Instante_AssociationEnd,Instante_Class) into AssociationEnd_Classifier1



!create momento_obtencao_Posicao_AssociationEnd : AssociationEnd

!set momento_obtencao_Posicao_AssociationEnd .name := 'posicao'

!set momento_obtencao_Posicao_AssociationEnd .aggregation_ := #none

!set momento_obtencao_Posicao_AssociationEnd .multiplicity := '*'

!insert (momento_obtencao_Association,momento_obtencao_Posicao_AssociationEnd ) into Association_AssociationEnd

!insert (momento_obtencao_Posicao_AssociationEnd ,Posicao_Class) into AssociationEnd_Classifier1





!create Posicao_Embarcacao_1_Association: Association

!set Posicao_Embarcacao_1_Association.name := 'Posicao_Embarcacao_1'

!insert (Posicionamento_Package,Posicao_Embarcacao_1_Association) into NamespaceImpl_ModelElementImpl



!create Posicao_Embarcacao_1_Posicao_AssociationEnd: AssociationEnd

!set Posicao_Embarcacao_1_Posicao_AssociationEnd.name := 'posicao'

!set Posicao_Embarcacao_1_Posicao_AssociationEnd.aggregation_ := #none

!set Posicao_Embarcacao_1_Posicao_AssociationEnd.multiplicity := '0..1'

!insert (Posicao_Embarcacao_1_Association,Posicao_Embarcacao_1_Posicao_AssociationEnd) into Association_AssociationEnd

!insert (Posicao_Embarcacao_1_Posicao_AssociationEnd,Posicao_Class) into AssociationEnd_Classifier1



!create Posicao_Embarcacao_1_Embarcacao_AssociationEnd : AssociationEnd

!set Posicao_Embarcacao_1_Embarcacao_AssociationEnd .name := 'embarcacao'

!set Posicao_Embarcacao_1_Embarcacao_AssociationEnd .aggregation_ := #none

!set Posicao_Embarcacao_1_Embarcacao_AssociationEnd .multiplicity := '*'

!insert (Posicao_Embarcacao_1_Association,Posicao_Embarcacao_1_Embarcacao_AssociationEnd ) into Association_AssociationEnd

!insert (Posicao_Embarcacao_1_Embarcacao_AssociationEnd ,Embarcacao_Class) into AssociationEnd_Classifier1





!create Coordenada_Posicao_2_Association: Association

!set Coordenada_Posicao_2_Association.name := 'Coordenada_Posicao_2'

!insert (Posicionamento_Package,Coordenada_Posicao_2_Association) into NamespaceImpl_ModelElementImpl



!create Coordenada_Posicao_2_Coordenada_AssociationEnd: AssociationEnd

!set Coordenada_Posicao_2_Coordenada_AssociationEnd.name := 'coordenada'

!set Coordenada_Posicao_2_Coordenada_AssociationEnd.aggregation_ := #composite

!set Coordenada_Posicao_2_Coordenada_AssociationEnd.multiplicity := '1'

!insert (Coordenada_Posicao_2_Association,Coordenada_Posicao_2_Coordenada_AssociationEnd) into Association_AssociationEnd

!insert (Coordenada_Posicao_2_Coordenada_AssociationEnd,Coordenada_Class) into AssociationEnd_Classifier1



!create Coordenada_Posicao_2_Posicao_AssociationEnd : AssociationEnd

!set Coordenada_Posicao_2_Posicao_AssociationEnd .name := 'posicao'

!set Coordenada_Posicao_2_Posicao_AssociationEnd .aggregation_ := #composite

!set Coordenada_Posicao_2_Posicao_AssociationEnd .multiplicity := '1'

!insert (Coordenada_Posicao_2_Association,Coordenada_Posicao_2_Posicao_AssociationEnd ) into Association_AssociationEnd

!insert (Coordenada_Posicao_2_Posicao_AssociationEnd ,Posicao_Class) into AssociationEnd_Classifier1





!create Posicao_Embarcacao_3_Association: Association

!set Posicao_Embarcacao_3_Association.name := 'Posicao_Embarcacao_3'

!insert (Posicionamento_Package,Posicao_Embarcacao_3_Association) into NamespaceImpl_ModelElementImpl



!create Posicao_Embarcacao_3_Posicao_AssociationEnd: AssociationEnd

!set Posicao_Embarcacao_3_Posicao_AssociationEnd.name := 'posicao'

!set Posicao_Embarcacao_3_Posicao_AssociationEnd.aggregation_ := #none

!set Posicao_Embarcacao_3_Posicao_AssociationEnd.multiplicity := '1'

!insert (Posicao_Embarcacao_3_Association,Posicao_Embarcacao_3_Posicao_AssociationEnd) into Association_AssociationEnd

!insert (Posicao_Embarcacao_3_Posicao_AssociationEnd,Posicao_Class) into AssociationEnd_Classifier1



!create Posicao_Embarcacao_3_Embarcacao_AssociationEnd : AssociationEnd

!set Posicao_Embarcacao_3_Embarcacao_AssociationEnd .name := 'embarcacao'

!set Posicao_Embarcacao_3_Embarcacao_AssociationEnd .aggregation_ := #none

!set Posicao_Embarcacao_3_Embarcacao_AssociationEnd .multiplicity := '*'

!insert (Posicao_Embarcacao_3_Association,Posicao_Embarcacao_3_Embarcacao_AssociationEnd ) into Association_AssociationEnd

!insert (Posicao_Embarcacao_3_Embarcacao_AssociationEnd ,Embarcacao_Class) into AssociationEnd_Classifier1





!create Posicao_Coordenada_4_Association: Association

!set Posicao_Coordenada_4_Association.name := 'Posicao_Coordenada_4'

!insert (Posicionamento_Package,Posicao_Coordenada_4_Association) into NamespaceImpl_ModelElementImpl



!create Posicao_Coordenada_4_Posicao_AssociationEnd: AssociationEnd

!set Posicao_Coordenada_4_Posicao_AssociationEnd.name := 'posicao'

!set Posicao_Coordenada_4_Posicao_AssociationEnd.aggregation_ := #aggregate

!set Posicao_Coordenada_4_Posicao_AssociationEnd.multiplicity := '*'

!insert (Posicao_Coordenada_4_Association,Posicao_Coordenada_4_Posicao_AssociationEnd) into Association_AssociationEnd

!insert (Posicao_Coordenada_4_Posicao_AssociationEnd,Posicao_Class) into AssociationEnd_Classifier1



!create Posicao_Coordenada_4_Coordenada_AssociationEnd : AssociationEnd

!set Posicao_Coordenada_4_Coordenada_AssociationEnd .name := 'coordenada'

!set Posicao_Coordenada_4_Coordenada_AssociationEnd .aggregation_ := #aggregate

!set Posicao_Coordenada_4_Coordenada_AssociationEnd .multiplicity := '1'

!insert (Posicao_Coordenada_4_Association,Posicao_Coordenada_4_Coordenada_AssociationEnd ) into Association_AssociationEnd

!insert (Posicao_Coordenada_4_Coordenada_AssociationEnd ,Coordenada_Class) into AssociationEnd_Classifier1





!create Posicao_Embarcacao_5_Association: Association

!set Posicao_Embarcacao_5_Association.name := 'Posicao_Embarcacao_5'

!insert (Posicionamento_Package,Posicao_Embarcacao_5_Association) into NamespaceImpl_ModelElementImpl



!create Posicao_Embarcacao_5_Posicao_AssociationEnd: AssociationEnd

!set Posicao_Embarcacao_5_Posicao_AssociationEnd.name := 'posicao'

!set Posicao_Embarcacao_5_Posicao_AssociationEnd.aggregation_ := #none

!set Posicao_Embarcacao_5_Posicao_AssociationEnd.multiplicity := '1..*'

!insert (Posicao_Embarcacao_5_Association,Posicao_Embarcacao_5_Posicao_AssociationEnd) into Association_AssociationEnd

!insert (Posicao_Embarcacao_5_Posicao_AssociationEnd,Posicao_Class) into AssociationEnd_Classifier1



!create Posicao_Embarcacao_5_Embarcacao_AssociationEnd : AssociationEnd

!set Posicao_Embarcacao_5_Embarcacao_AssociationEnd .name := 'embarcacao'

!set Posicao_Embarcacao_5_Embarcacao_AssociationEnd .aggregation_ := #none

!set Posicao_Embarcacao_5_Embarcacao_AssociationEnd .multiplicity := '1'

!insert (Posicao_Embarcacao_5_Association,Posicao_Embarcacao_5_Embarcacao_AssociationEnd ) into Association_AssociationEnd

!insert (Posicao_Embarcacao_5_Embarcacao_AssociationEnd ,Embarcacao_Class) into AssociationEnd_Classifier1




 -- end Associations of Posicionamento

------------------------------------------------------------------------
-- Associations of Pessoal
------------------------------------------------------------------------


!create possui_Association: Association

!set possui_Association.name := 'possui'

!insert (Pessoal_Package,possui_Association) into NamespaceImpl_ModelElementImpl



!create possui_CategoriaProfissional_AssociationEnd: AssociationEnd

!set possui_CategoriaProfissional_AssociationEnd.name := 'categoriaProfissional'

!set possui_CategoriaProfissional_AssociationEnd.aggregation_ := #none

!set possui_CategoriaProfissional_AssociationEnd.multiplicity := '1'

!insert (possui_Association,possui_CategoriaProfissional_AssociationEnd) into Association_AssociationEnd

!insert (possui_CategoriaProfissional_AssociationEnd,CategoriaProfissional_Class) into AssociationEnd_Classifier1



!create possui_Empregado_AssociationEnd : AssociationEnd

!set possui_Empregado_AssociationEnd .name := 'empregado'

!set possui_Empregado_AssociationEnd .aggregation_ := #none

!set possui_Empregado_AssociationEnd .multiplicity := '0..*'

!insert (possui_Association,possui_Empregado_AssociationEnd ) into Association_AssociationEnd

!insert (possui_Empregado_AssociationEnd ,Empregado_Class) into AssociationEnd_Classifier1





!create tem_Association: Association

!set tem_Association.name := 'tem'

!insert (Pessoal_Package,tem_Association) into NamespaceImpl_ModelElementImpl



!create tem_CedulaMaritima_AssociationEnd: AssociationEnd

!set tem_CedulaMaritima_AssociationEnd.name := 'cedulaMaritima'

!set tem_CedulaMaritima_AssociationEnd.aggregation_ := #none

!set tem_CedulaMaritima_AssociationEnd.multiplicity := '1'

!insert (tem_Association,tem_CedulaMaritima_AssociationEnd) into Association_AssociationEnd

!insert (tem_CedulaMaritima_AssociationEnd,CedulaMaritima_Class) into AssociationEnd_Classifier1



!create tem_FuncionarioMar_AssociationEnd : AssociationEnd

!set tem_FuncionarioMar_AssociationEnd .name := 'funcionarioMar'

!set tem_FuncionarioMar_AssociationEnd .aggregation_ := #none

!set tem_FuncionarioMar_AssociationEnd .multiplicity := '1'

!insert (tem_Association,tem_FuncionarioMar_AssociationEnd ) into Association_AssociationEnd

!insert (tem_FuncionarioMar_AssociationEnd ,FuncionarioMar_Class) into AssociationEnd_Classifier1





!create assume_Association: Association

!set assume_Association.name := 'assume'

!insert (Pessoal_Package,assume_Association) into NamespaceImpl_ModelElementImpl



!create assume_CategoriaProfissional_AssociationEnd: AssociationEnd

!set assume_CategoriaProfissional_AssociationEnd.name := 'categoriaProfissional'

!set assume_CategoriaProfissional_AssociationEnd.aggregation_ := #none

!set assume_CategoriaProfissional_AssociationEnd.multiplicity := '1'

!insert (assume_Association,assume_CategoriaProfissional_AssociationEnd) into Association_AssociationEnd

!insert (assume_CategoriaProfissional_AssociationEnd,CategoriaProfissional_Class) into AssociationEnd_Classifier1



!create assume_Tripulante_AssociationEnd : AssociationEnd

!set assume_Tripulante_AssociationEnd .name := 'tripulante'

!set assume_Tripulante_AssociationEnd .aggregation_ := #none

!set assume_Tripulante_AssociationEnd .multiplicity := '0..*'

!insert (assume_Association,assume_Tripulante_AssociationEnd ) into Association_AssociationEnd

!insert (assume_Tripulante_AssociationEnd ,Tripulante_Class) into AssociationEnd_Classifier1





!create Armador_Empregado_6_Association: Association

!set Armador_Empregado_6_Association.name := 'Armador_Empregado_6'

!insert (Pessoal_Package,Armador_Empregado_6_Association) into NamespaceImpl_ModelElementImpl



!create Armador_Empregado_6_Armador_AssociationEnd: AssociationEnd

!set Armador_Empregado_6_Armador_AssociationEnd.name := 'armador'

!set Armador_Empregado_6_Armador_AssociationEnd.aggregation_ := #none

!set Armador_Empregado_6_Armador_AssociationEnd.multiplicity := '0..1'

!insert (Armador_Empregado_6_Association,Armador_Empregado_6_Armador_AssociationEnd) into Association_AssociationEnd

!insert (Armador_Empregado_6_Armador_AssociationEnd,Armador_Class) into AssociationEnd_Classifier1



!create Armador_Empregado_6_Empregado_AssociationEnd : AssociationEnd

!set Armador_Empregado_6_Empregado_AssociationEnd .name := 'empregado'

!set Armador_Empregado_6_Empregado_AssociationEnd .aggregation_ := #none

!set Armador_Empregado_6_Empregado_AssociationEnd .multiplicity := '*'

!insert (Armador_Empregado_6_Association,Armador_Empregado_6_Empregado_AssociationEnd ) into Association_AssociationEnd

!insert (Armador_Empregado_6_Empregado_AssociationEnd ,Empregado_Class) into AssociationEnd_Classifier1





!create Comandante_Association: Association

!set Comandante_Association.name := 'Comandante'

!insert (Pessoal_Package,Comandante_Association) into NamespaceImpl_ModelElementImpl



!create Comandante_Embarcacao_AssociationEnd: AssociationEnd

!set Comandante_Embarcacao_AssociationEnd.name := 'embarcacao'

!set Comandante_Embarcacao_AssociationEnd.aggregation_ := #none

!set Comandante_Embarcacao_AssociationEnd.multiplicity := '1'

!insert (Comandante_Association,Comandante_Embarcacao_AssociationEnd) into Association_AssociationEnd

!insert (Comandante_Embarcacao_AssociationEnd,Embarcacao_Class) into AssociationEnd_Classifier1



!create Comandante_OficialPiloto_AssociationEnd : AssociationEnd

!set Comandante_OficialPiloto_AssociationEnd .name := 'oficialPiloto'

!set Comandante_OficialPiloto_AssociationEnd .aggregation_ := #none

!set Comandante_OficialPiloto_AssociationEnd .multiplicity := '1'

!insert (Comandante_Association,Comandante_OficialPiloto_AssociationEnd ) into Association_AssociationEnd

!insert (Comandante_OficialPiloto_AssociationEnd ,OficialPiloto_Class) into AssociationEnd_Classifier1





!create Embarcacao_Tripulante_7_Association: Association

!set Embarcacao_Tripulante_7_Association.name := 'Embarcacao_Tripulante_7'

!insert (Pessoal_Package,Embarcacao_Tripulante_7_Association) into NamespaceImpl_ModelElementImpl



!create Embarcacao_Tripulante_7_Embarcacao_AssociationEnd: AssociationEnd

!set Embarcacao_Tripulante_7_Embarcacao_AssociationEnd.name := 'embarcacao'

!set Embarcacao_Tripulante_7_Embarcacao_AssociationEnd.aggregation_ := #none

!set Embarcacao_Tripulante_7_Embarcacao_AssociationEnd.multiplicity := '1..*'

!insert (Embarcacao_Tripulante_7_Association,Embarcacao_Tripulante_7_Embarcacao_AssociationEnd) into Association_AssociationEnd

!insert (Embarcacao_Tripulante_7_Embarcacao_AssociationEnd,Embarcacao_Class) into AssociationEnd_Classifier1



!create Embarcacao_Tripulante_7_Tripulante_AssociationEnd : AssociationEnd

!set Embarcacao_Tripulante_7_Tripulante_AssociationEnd .name := 'tripulante'

!set Embarcacao_Tripulante_7_Tripulante_AssociationEnd .aggregation_ := #none

!set Embarcacao_Tripulante_7_Tripulante_AssociationEnd .multiplicity := '1..*'

!insert (Embarcacao_Tripulante_7_Association,Embarcacao_Tripulante_7_Tripulante_AssociationEnd ) into Association_AssociationEnd

!insert (Embarcacao_Tripulante_7_Tripulante_AssociationEnd ,Tripulante_Class) into AssociationEnd_Classifier1





!create Timoneiro_Association: Association

!set Timoneiro_Association.name := 'Timoneiro'

!insert (Pessoal_Package,Timoneiro_Association) into NamespaceImpl_ModelElementImpl



!create Timoneiro_Marinheiro_AssociationEnd: AssociationEnd

!set Timoneiro_Marinheiro_AssociationEnd.name := 'marinheiro'

!set Timoneiro_Marinheiro_AssociationEnd.aggregation_ := #none

!set Timoneiro_Marinheiro_AssociationEnd.multiplicity := '0..1'

!insert (Timoneiro_Association,Timoneiro_Marinheiro_AssociationEnd) into Association_AssociationEnd

!insert (Timoneiro_Marinheiro_AssociationEnd,Marinheiro_Class) into AssociationEnd_Classifier1



!create Timoneiro_Embarcacao_AssociationEnd : AssociationEnd

!set Timoneiro_Embarcacao_AssociationEnd .name := 'embarcacao'

!set Timoneiro_Embarcacao_AssociationEnd .aggregation_ := #none

!set Timoneiro_Embarcacao_AssociationEnd .multiplicity := '0..1'

!insert (Timoneiro_Association,Timoneiro_Embarcacao_AssociationEnd ) into Association_AssociationEnd

!insert (Timoneiro_Embarcacao_AssociationEnd ,Embarcacao_Class) into AssociationEnd_Classifier1





!create CategoriaProfissional_Empregado_8_Association: Association

!set CategoriaProfissional_Empregado_8_Association.name := 'CategoriaProfissional_Empregado_8'

!insert (Pessoal_Package,CategoriaProfissional_Empregado_8_Association) into NamespaceImpl_ModelElementImpl



!create CategoriaProfissional_Empregado_8_CategoriaProfissional_AssociationEnd: AssociationEnd

!set CategoriaProfissional_Empregado_8_CategoriaProfissional_AssociationEnd.name := 'categoriaProfissional'

!set CategoriaProfissional_Empregado_8_CategoriaProfissional_AssociationEnd.aggregation_ := #none

!set CategoriaProfissional_Empregado_8_CategoriaProfissional_AssociationEnd.multiplicity := '*'

!insert (CategoriaProfissional_Empregado_8_Association,CategoriaProfissional_Empregado_8_CategoriaProfissional_AssociationEnd) into Association_AssociationEnd

!insert (CategoriaProfissional_Empregado_8_CategoriaProfissional_AssociationEnd,CategoriaProfissional_Class) into AssociationEnd_Classifier1



!create CategoriaProfissional_Empregado_8_Empregado_AssociationEnd : AssociationEnd

!set CategoriaProfissional_Empregado_8_Empregado_AssociationEnd .name := 'empregado'

!set CategoriaProfissional_Empregado_8_Empregado_AssociationEnd .aggregation_ := #none

!set CategoriaProfissional_Empregado_8_Empregado_AssociationEnd .multiplicity := '*'

!insert (CategoriaProfissional_Empregado_8_Association,CategoriaProfissional_Empregado_8_Empregado_AssociationEnd ) into Association_AssociationEnd

!insert (CategoriaProfissional_Empregado_8_Empregado_AssociationEnd ,Empregado_Class) into AssociationEnd_Classifier1




!create Embarcacao_Embarque_8_Association : Association

!set Embarcacao_Embarque_8_Association.name := 'Embarcacao_Embarque_8'

!insert (Pessoal_Package,Embarcacao_Embarque_8_Association ) into NamespaceImpl_ModelElementImpl



!create Embarcacao_Embarque_8_Embarcacao_AssociationEnd: AssociationEnd

!set Embarcacao_Embarque_8_Embarcacao_AssociationEnd.name := 'embarcacao'

!set Embarcacao_Embarque_8_Embarcacao_AssociationEnd.multiplicity := ' 1 ' 

!insert (Embarcacao_Embarque_8_Association,Embarcacao_Embarque_8_Embarcacao_AssociationEnd) into Association_AssociationEnd

!insert (Embarcacao_Embarque_8_Embarcacao_AssociationEnd,Embarcacao_Class) into AssociationEnd_Classifier1



!create Embarcacao_Embarque_8_Embarque_AssociationEnd : AssociationEnd

!set Embarcacao_Embarque_8_Embarque_AssociationEnd .name := 'embarque'

!set Embarcacao_Embarque_8_Embarque_AssociationEnd .multiplicity := '1..*'

!insert (Embarcacao_Embarque_8_Association,Embarcacao_Embarque_8_Embarque_AssociationEnd ) into Association_AssociationEnd

!insert (Embarcacao_Embarque_8_Embarque_AssociationEnd ,Embarque_Class) into AssociationEnd_Classifier1





!create Embarcacao_Embarque_8_Tripulante_AssociationEnd: AssociationEnd

!set Embarcacao_Embarque_8_Tripulante_AssociationEnd.name := 'embarcacao'

!set Embarcacao_Embarque_8_Tripulante_AssociationEnd.multiplicity := ' 1 '

!insert (Embarcacao_Embarque_8_Association,Embarcacao_Embarque_8_Tripulante_AssociationEnd) into Association_AssociationEnd

!insert (Embarcacao_Embarque_8_Tripulante_AssociationEnd,Tripulante_Class) into AssociationEnd_Classifier1





 -- end Associations of Pessoal

------------------------------------------------------------------------
-- Associations of Embarcacoes
------------------------------------------------------------------------


!create Embarcacao_Armazem_11_Association: Association

!set Embarcacao_Armazem_11_Association.name := 'Embarcacao_Armazem_11'

!insert (Embarcacoes_Package,Embarcacao_Armazem_11_Association) into NamespaceImpl_ModelElementImpl



!create Embarcacao_Armazem_11_Embarcacao_AssociationEnd: AssociationEnd

!set Embarcacao_Armazem_11_Embarcacao_AssociationEnd.name := 'embarcacao'

!set Embarcacao_Armazem_11_Embarcacao_AssociationEnd.aggregation_ := #aggregate

!set Embarcacao_Armazem_11_Embarcacao_AssociationEnd.multiplicity := '1'

!insert (Embarcacao_Armazem_11_Association,Embarcacao_Armazem_11_Embarcacao_AssociationEnd) into Association_AssociationEnd

!insert (Embarcacao_Armazem_11_Embarcacao_AssociationEnd,Embarcacao_Class) into AssociationEnd_Classifier1



!create Embarcacao_Armazem_11_Armazem_AssociationEnd : AssociationEnd

!set Embarcacao_Armazem_11_Armazem_AssociationEnd .name := 'armazem'

!set Embarcacao_Armazem_11_Armazem_AssociationEnd .aggregation_ := #aggregate

!set Embarcacao_Armazem_11_Armazem_AssociationEnd .multiplicity := '0..*'

!insert (Embarcacao_Armazem_11_Association,Embarcacao_Armazem_11_Armazem_AssociationEnd ) into Association_AssociationEnd

!insert (Embarcacao_Armazem_11_Armazem_AssociationEnd ,Armazem_Class) into AssociationEnd_Classifier1





!create Armazem_CamaraFrigorifica_12_Association: Association

!set Armazem_CamaraFrigorifica_12_Association.name := 'Armazem_CamaraFrigorifica_12'

!insert (Embarcacoes_Package,Armazem_CamaraFrigorifica_12_Association) into NamespaceImpl_ModelElementImpl



!create Armazem_CamaraFrigorifica_12_Armazem_AssociationEnd: AssociationEnd

!set Armazem_CamaraFrigorifica_12_Armazem_AssociationEnd.name := 'armazem'

!set Armazem_CamaraFrigorifica_12_Armazem_AssociationEnd.aggregation_ := #none

!set Armazem_CamaraFrigorifica_12_Armazem_AssociationEnd.multiplicity := '*'

!insert (Armazem_CamaraFrigorifica_12_Association,Armazem_CamaraFrigorifica_12_Armazem_AssociationEnd) into Association_AssociationEnd

!insert (Armazem_CamaraFrigorifica_12_Armazem_AssociationEnd,Armazem_Class) into AssociationEnd_Classifier1



!create Armazem_CamaraFrigorifica_12_CamaraFrigorifica_AssociationEnd : AssociationEnd

!set Armazem_CamaraFrigorifica_12_CamaraFrigorifica_AssociationEnd .name := 'camaraFrigorifica'

!set Armazem_CamaraFrigorifica_12_CamaraFrigorifica_AssociationEnd .aggregation_ := #none

!set Armazem_CamaraFrigorifica_12_CamaraFrigorifica_AssociationEnd .multiplicity := '*'

!insert (Armazem_CamaraFrigorifica_12_Association,Armazem_CamaraFrigorifica_12_CamaraFrigorifica_AssociationEnd ) into Association_AssociationEnd

!insert (Armazem_CamaraFrigorifica_12_CamaraFrigorifica_AssociationEnd ,CamaraFrigorifica_Class) into AssociationEnd_Classifier1





!create Propulsao_Embarcacao_13_Association: Association

!set Propulsao_Embarcacao_13_Association.name := 'Propulsao_Embarcacao_13'

!insert (Embarcacoes_Package,Propulsao_Embarcacao_13_Association) into NamespaceImpl_ModelElementImpl



!create Propulsao_Embarcacao_13_Propulsao_AssociationEnd: AssociationEnd

!set Propulsao_Embarcacao_13_Propulsao_AssociationEnd.name := 'propulsao'

!set Propulsao_Embarcacao_13_Propulsao_AssociationEnd.aggregation_ := #none

!set Propulsao_Embarcacao_13_Propulsao_AssociationEnd.multiplicity := '*'

!insert (Propulsao_Embarcacao_13_Association,Propulsao_Embarcacao_13_Propulsao_AssociationEnd) into Association_AssociationEnd

!insert (Propulsao_Embarcacao_13_Propulsao_AssociationEnd,Propulsao_Class) into AssociationEnd_Classifier1



!create Propulsao_Embarcacao_13_Embarcacao_AssociationEnd : AssociationEnd

!set Propulsao_Embarcacao_13_Embarcacao_AssociationEnd .name := 'embarcacao'

!set Propulsao_Embarcacao_13_Embarcacao_AssociationEnd .aggregation_ := #none

!set Propulsao_Embarcacao_13_Embarcacao_AssociationEnd .multiplicity := '*'

!insert (Propulsao_Embarcacao_13_Association,Propulsao_Embarcacao_13_Embarcacao_AssociationEnd ) into Association_AssociationEnd

!insert (Propulsao_Embarcacao_13_Embarcacao_AssociationEnd ,Embarcacao_Class) into AssociationEnd_Classifier1



!create Embarcacao_Porao_14_Association: Association

!set Embarcacao_Porao_14_Association.name := 'Embarcacao_Porao_14'

!insert (Embarcacoes_Package,Embarcacao_Porao_14_Association) into NamespaceImpl_ModelElementImpl



!create Embarcacao_Porao_14_Embarcacao_AssociationEnd: AssociationEnd

!set Embarcacao_Porao_14_Embarcacao_AssociationEnd.name := 'embarcacao'

!set Embarcacao_Porao_14_Embarcacao_AssociationEnd.aggregation_ := #aggregate

!set Embarcacao_Porao_14_Embarcacao_AssociationEnd.multiplicity := '*'

!insert (Embarcacao_Porao_14_Association,Embarcacao_Porao_14_Embarcacao_AssociationEnd) into Association_AssociationEnd

!insert (Embarcacao_Porao_14_Embarcacao_AssociationEnd,Embarcacao_Class) into AssociationEnd_Classifier1



!create Embarcacao_Porao_14_Porao_AssociationEnd : AssociationEnd

!set Embarcacao_Porao_14_Porao_AssociationEnd .name := 'porao'

!set Embarcacao_Porao_14_Porao_AssociationEnd .aggregation_ := #aggregate

!set Embarcacao_Porao_14_Porao_AssociationEnd .multiplicity := '0..*'

!insert (Embarcacao_Porao_14_Association,Embarcacao_Porao_14_Porao_AssociationEnd ) into Association_AssociationEnd

!insert (Embarcacao_Porao_14_Porao_AssociationEnd ,Porao_Class) into AssociationEnd_Classifier1


!create Embarcacao_Conves_15_Association: Association

!set Embarcacao_Conves_15_Association.name := 'Embarcacao_Conves_15'

!insert (Embarcacoes_Package,Embarcacao_Conves_15_Association) into NamespaceImpl_ModelElementImpl



!create Embarcacao_Conves_15_Embarcacao_AssociationEnd: AssociationEnd

!set Embarcacao_Conves_15_Embarcacao_AssociationEnd.name := 'embarcacao'

!set Embarcacao_Conves_15_Embarcacao_AssociationEnd.aggregation_ := #aggregate

!set Embarcacao_Conves_15_Embarcacao_AssociationEnd.multiplicity := '*'

!insert (Embarcacao_Conves_15_Association,Embarcacao_Conves_15_Embarcacao_AssociationEnd) into Association_AssociationEnd

!insert (Embarcacao_Conves_15_Embarcacao_AssociationEnd,Embarcacao_Class) into AssociationEnd_Classifier1



!create Embarcacao_Conves_15_Conves_AssociationEnd : AssociationEnd

!set Embarcacao_Conves_15_Conves_AssociationEnd .name := 'conves'

!set Embarcacao_Conves_15_Conves_AssociationEnd .aggregation_ := #aggregate

!set Embarcacao_Conves_15_Conves_AssociationEnd .multiplicity := '1..*'

!insert (Embarcacao_Conves_15_Association,Embarcacao_Conves_15_Conves_AssociationEnd ) into Association_AssociationEnd

!insert (Embarcacao_Conves_15_Conves_AssociationEnd ,Conves_Class) into AssociationEnd_Classifier1





!create Embarcacao_Ponte_16_Association: Association

!set Embarcacao_Ponte_16_Association.name := 'Embarcacao_Ponte_16'

!insert (Embarcacoes_Package,Embarcacao_Ponte_16_Association) into NamespaceImpl_ModelElementImpl



!create Embarcacao_Ponte_16_Embarcacao_AssociationEnd: AssociationEnd

!set Embarcacao_Ponte_16_Embarcacao_AssociationEnd.name := 'embarcacao'

!set Embarcacao_Ponte_16_Embarcacao_AssociationEnd.aggregation_ := #composite

!set Embarcacao_Ponte_16_Embarcacao_AssociationEnd.multiplicity := '1'

!insert (Embarcacao_Ponte_16_Association,Embarcacao_Ponte_16_Embarcacao_AssociationEnd) into Association_AssociationEnd

!insert (Embarcacao_Ponte_16_Embarcacao_AssociationEnd,Embarcacao_Class) into AssociationEnd_Classifier1



!create Embarcacao_Ponte_16_Ponte_AssociationEnd : AssociationEnd

!set Embarcacao_Ponte_16_Ponte_AssociationEnd .name := 'ponte'

!set Embarcacao_Ponte_16_Ponte_AssociationEnd .aggregation_ := #composite

!set Embarcacao_Ponte_16_Ponte_AssociationEnd .multiplicity := '0..1'

!insert (Embarcacao_Ponte_16_Association,Embarcacao_Ponte_16_Ponte_AssociationEnd ) into Association_AssociationEnd

!insert (Embarcacao_Ponte_16_Ponte_AssociationEnd ,Ponte_Class) into AssociationEnd_Classifier1





!create Embarcacao_InstalacoesTripulacao_17_Association: Association

!set Embarcacao_InstalacoesTripulacao_17_Association.name := 'Embarcacao_InstalacoesTripulacao_17'

!insert (Embarcacoes_Package,Embarcacao_InstalacoesTripulacao_17_Association) into NamespaceImpl_ModelElementImpl



!create Embarcacao_InstalacoesTripulacao_17_Embarcacao_AssociationEnd: AssociationEnd

!set Embarcacao_InstalacoesTripulacao_17_Embarcacao_AssociationEnd.name := 'embarcacao'

!set Embarcacao_InstalacoesTripulacao_17_Embarcacao_AssociationEnd.aggregation_ := #aggregate

!set Embarcacao_InstalacoesTripulacao_17_Embarcacao_AssociationEnd.multiplicity := '*'

!insert (Embarcacao_InstalacoesTripulacao_17_Association,Embarcacao_InstalacoesTripulacao_17_Embarcacao_AssociationEnd) into Association_AssociationEnd

!insert (Embarcacao_InstalacoesTripulacao_17_Embarcacao_AssociationEnd,Embarcacao_Class) into AssociationEnd_Classifier1



!create Embarcacao_InstalacoesTripulacao_17_InstalacoesTripulacao_AssociationEnd : AssociationEnd

!set Embarcacao_InstalacoesTripulacao_17_InstalacoesTripulacao_AssociationEnd .name := 'instalacoesTripulacao'

!set Embarcacao_InstalacoesTripulacao_17_InstalacoesTripulacao_AssociationEnd .aggregation_ := #aggregate

!set Embarcacao_InstalacoesTripulacao_17_InstalacoesTripulacao_AssociationEnd .multiplicity := '0..1'

!insert (Embarcacao_InstalacoesTripulacao_17_Association,Embarcacao_InstalacoesTripulacao_17_InstalacoesTripulacao_AssociationEnd ) into Association_AssociationEnd

!insert (Embarcacao_InstalacoesTripulacao_17_InstalacoesTripulacao_AssociationEnd ,InstalacoesTripulacao_Class) into AssociationEnd_Classifier1





!create Embarcacao_CasaMaquina_18_Association: Association

!set Embarcacao_CasaMaquina_18_Association.name := 'Embarcacao_CasaMaquina_18'

!insert (Embarcacoes_Package,Embarcacao_CasaMaquina_18_Association) into NamespaceImpl_ModelElementImpl



!create Embarcacao_CasaMaquina_18_Embarcacao_AssociationEnd: AssociationEnd

!set Embarcacao_CasaMaquina_18_Embarcacao_AssociationEnd.name := 'embarcacao'

!set Embarcacao_CasaMaquina_18_Embarcacao_AssociationEnd.aggregation_ := #composite

!set Embarcacao_CasaMaquina_18_Embarcacao_AssociationEnd.multiplicity := '*'

!insert (Embarcacao_CasaMaquina_18_Association,Embarcacao_CasaMaquina_18_Embarcacao_AssociationEnd) into Association_AssociationEnd

!insert (Embarcacao_CasaMaquina_18_Embarcacao_AssociationEnd,Embarcacao_Class) into AssociationEnd_Classifier1



!create Embarcacao_CasaMaquina_18_CasaMaquina_AssociationEnd : AssociationEnd

!set Embarcacao_CasaMaquina_18_CasaMaquina_AssociationEnd .name := 'casaMaquina'

!set Embarcacao_CasaMaquina_18_CasaMaquina_AssociationEnd .aggregation_ := #composite

!set Embarcacao_CasaMaquina_18_CasaMaquina_AssociationEnd .multiplicity := '0..1'

!insert (Embarcacao_CasaMaquina_18_Association,Embarcacao_CasaMaquina_18_CasaMaquina_AssociationEnd ) into Association_AssociationEnd

!insert (Embarcacao_CasaMaquina_18_CasaMaquina_AssociationEnd ,CasaMaquina_Class) into AssociationEnd_Classifier1





!create Embarcacao_Tanque_19_Association: Association

!set Embarcacao_Tanque_19_Association.name := 'Embarcacao_Tanque_19'

!insert (Embarcacoes_Package,Embarcacao_Tanque_19_Association) into NamespaceImpl_ModelElementImpl



!create Embarcacao_Tanque_19_Embarcacao_AssociationEnd: AssociationEnd

!set Embarcacao_Tanque_19_Embarcacao_AssociationEnd.name := 'embarcacao'

!set Embarcacao_Tanque_19_Embarcacao_AssociationEnd.aggregation_ := #aggregate

!set Embarcacao_Tanque_19_Embarcacao_AssociationEnd.multiplicity := '*'

!insert (Embarcacao_Tanque_19_Association,Embarcacao_Tanque_19_Embarcacao_AssociationEnd) into Association_AssociationEnd

!insert (Embarcacao_Tanque_19_Embarcacao_AssociationEnd,Embarcacao_Class) into AssociationEnd_Classifier1



!create Embarcacao_Tanque_19_Tanque_AssociationEnd : AssociationEnd

!set Embarcacao_Tanque_19_Tanque_AssociationEnd .name := 'tanque'

!set Embarcacao_Tanque_19_Tanque_AssociationEnd .aggregation_ := #aggregate

!set Embarcacao_Tanque_19_Tanque_AssociationEnd .multiplicity := '1..*'

!insert (Embarcacao_Tanque_19_Association,Embarcacao_Tanque_19_Tanque_AssociationEnd ) into Association_AssociationEnd

!insert (Embarcacao_Tanque_19_Tanque_AssociationEnd ,Tanque_Class) into AssociationEnd_Classifier1





!create Ponte_RodaLeme_20_Association: Association

!set Ponte_RodaLeme_20_Association.name := 'Ponte_RodaLeme_20'

!insert (Embarcacoes_Package,Ponte_RodaLeme_20_Association) into NamespaceImpl_ModelElementImpl



!create Ponte_RodaLeme_20_Ponte_AssociationEnd: AssociationEnd

!set Ponte_RodaLeme_20_Ponte_AssociationEnd.name := 'ponte'

!set Ponte_RodaLeme_20_Ponte_AssociationEnd.aggregation_ := #aggregate

!set Ponte_RodaLeme_20_Ponte_AssociationEnd.multiplicity := '*'

!insert (Ponte_RodaLeme_20_Association,Ponte_RodaLeme_20_Ponte_AssociationEnd) into Association_AssociationEnd

!insert (Ponte_RodaLeme_20_Ponte_AssociationEnd,Ponte_Class) into AssociationEnd_Classifier1



!create Ponte_RodaLeme_20_RodaLeme_AssociationEnd : AssociationEnd

!set Ponte_RodaLeme_20_RodaLeme_AssociationEnd .name := 'rodaLeme'

!set Ponte_RodaLeme_20_RodaLeme_AssociationEnd .aggregation_ := #aggregate

!set Ponte_RodaLeme_20_RodaLeme_AssociationEnd .multiplicity := '1'

!insert (Ponte_RodaLeme_20_Association,Ponte_RodaLeme_20_RodaLeme_AssociationEnd ) into Association_AssociationEnd

!insert (Ponte_RodaLeme_20_RodaLeme_AssociationEnd ,RodaLeme_Class) into AssociationEnd_Classifier1





!create Ponte_GiroBussula_21_Association: Association

!set Ponte_GiroBussula_21_Association.name := 'Ponte_GiroBussula_21'

!insert (Embarcacoes_Package,Ponte_GiroBussula_21_Association) into NamespaceImpl_ModelElementImpl



!create Ponte_GiroBussula_21_Ponte_AssociationEnd: AssociationEnd

!set Ponte_GiroBussula_21_Ponte_AssociationEnd.name := 'ponte'

!set Ponte_GiroBussula_21_Ponte_AssociationEnd.aggregation_ := #aggregate

!set Ponte_GiroBussula_21_Ponte_AssociationEnd.multiplicity := '*'

!insert (Ponte_GiroBussula_21_Association,Ponte_GiroBussula_21_Ponte_AssociationEnd) into Association_AssociationEnd

!insert (Ponte_GiroBussula_21_Ponte_AssociationEnd,Ponte_Class) into AssociationEnd_Classifier1



!create Ponte_GiroBussula_21_GiroBussula_AssociationEnd : AssociationEnd

!set Ponte_GiroBussula_21_GiroBussula_AssociationEnd .name := 'giroBussula'

!set Ponte_GiroBussula_21_GiroBussula_AssociationEnd .aggregation_ := #aggregate

!set Ponte_GiroBussula_21_GiroBussula_AssociationEnd .multiplicity := '0..1'

!insert (Ponte_GiroBussula_21_Association,Ponte_GiroBussula_21_GiroBussula_AssociationEnd ) into Association_AssociationEnd

!insert (Ponte_GiroBussula_21_GiroBussula_AssociationEnd ,GiroBussula_Class) into AssociationEnd_Classifier1





!create Ponte_Relogio_22_Association: Association

!set Ponte_Relogio_22_Association.name := 'Ponte_Relogio_22'

!insert (Embarcacoes_Package,Ponte_Relogio_22_Association) into NamespaceImpl_ModelElementImpl



!create Ponte_Relogio_22_Ponte_AssociationEnd: AssociationEnd

!set Ponte_Relogio_22_Ponte_AssociationEnd.name := 'ponte'

!set Ponte_Relogio_22_Ponte_AssociationEnd.aggregation_ := #aggregate

!set Ponte_Relogio_22_Ponte_AssociationEnd.multiplicity := '*'

!insert (Ponte_Relogio_22_Association,Ponte_Relogio_22_Ponte_AssociationEnd) into Association_AssociationEnd

!insert (Ponte_Relogio_22_Ponte_AssociationEnd,Ponte_Class) into AssociationEnd_Classifier1



!create Ponte_Relogio_22_Relogio_AssociationEnd : AssociationEnd

!set Ponte_Relogio_22_Relogio_AssociationEnd .name := 'relogio'

!set Ponte_Relogio_22_Relogio_AssociationEnd .aggregation_ := #aggregate

!set Ponte_Relogio_22_Relogio_AssociationEnd .multiplicity := '1..*'

!insert (Ponte_Relogio_22_Association,Ponte_Relogio_22_Relogio_AssociationEnd ) into Association_AssociationEnd

!insert (Ponte_Relogio_22_Relogio_AssociationEnd ,Relogio_Class) into AssociationEnd_Classifier1





!create Ponte_TabelasNauticas_23_Association: Association

!set Ponte_TabelasNauticas_23_Association.name := 'Ponte_TabelasNauticas_23'

!insert (Embarcacoes_Package,Ponte_TabelasNauticas_23_Association) into NamespaceImpl_ModelElementImpl



!create Ponte_TabelasNauticas_23_Ponte_AssociationEnd: AssociationEnd

!set Ponte_TabelasNauticas_23_Ponte_AssociationEnd.name := 'ponte'

!set Ponte_TabelasNauticas_23_Ponte_AssociationEnd.aggregation_ := #aggregate

!set Ponte_TabelasNauticas_23_Ponte_AssociationEnd.multiplicity := '*'

!insert (Ponte_TabelasNauticas_23_Association,Ponte_TabelasNauticas_23_Ponte_AssociationEnd) into Association_AssociationEnd

!insert (Ponte_TabelasNauticas_23_Ponte_AssociationEnd,Ponte_Class) into AssociationEnd_Classifier1



!create Ponte_TabelasNauticas_23_TabelasNauticas_AssociationEnd : AssociationEnd

!set Ponte_TabelasNauticas_23_TabelasNauticas_AssociationEnd .name := 'tabelasNauticas'

!set Ponte_TabelasNauticas_23_TabelasNauticas_AssociationEnd .aggregation_ := #aggregate

!set Ponte_TabelasNauticas_23_TabelasNauticas_AssociationEnd .multiplicity := '0..*'

!insert (Ponte_TabelasNauticas_23_Association,Ponte_TabelasNauticas_23_TabelasNauticas_AssociationEnd ) into Association_AssociationEnd

!insert (Ponte_TabelasNauticas_23_TabelasNauticas_AssociationEnd ,TabelasNauticas_Class) into AssociationEnd_Classifier1




 -- end Associations of Embarcacoes




 -- end model Navio