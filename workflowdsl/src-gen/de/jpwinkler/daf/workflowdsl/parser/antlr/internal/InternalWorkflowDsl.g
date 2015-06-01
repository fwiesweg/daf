/*
* generated by Xtext
*/
grammar InternalWorkflowDsl;

options {
	superClass=AbstractInternalAntlrParser;
	
}

@lexer::header {
package de.jpwinkler.daf.workflowdsl.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package de.jpwinkler.daf.workflowdsl.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import de.jpwinkler.daf.workflowdsl.services.WorkflowDslGrammarAccess;

}

@parser::members {

 	private WorkflowDslGrammarAccess grammarAccess;
 	
    public InternalWorkflowDslParser(TokenStream input, WorkflowDslGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }
    
    @Override
    protected String getFirstRuleName() {
    	return "WorkflowModel";	
   	}
   	
   	@Override
   	protected WorkflowDslGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}
}

@rulecatch { 
    catch (RecognitionException re) { 
        recover(input,re); 
        appendSkippedTokens();
    } 
}




// Entry rule entryRuleWorkflowModel
entryRuleWorkflowModel returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getWorkflowModelRule()); }
	 iv_ruleWorkflowModel=ruleWorkflowModel 
	 { $current=$iv_ruleWorkflowModel.current; } 
	 EOF 
;

// Rule WorkflowModel
ruleWorkflowModel returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		{ 
	        newCompositeNode(grammarAccess.getWorkflowModelAccess().getElementsWorkflowElementParserRuleCall_0()); 
	    }
		lv_elements_0_0=ruleWorkflowElement		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getWorkflowModelRule());
	        }
       		add(
       			$current, 
       			"elements",
        		lv_elements_0_0, 
        		"WorkflowElement");
	        afterParserOrEnumRuleCall();
	    }

)
)*
;





// Entry rule entryRuleWorkflowElement
entryRuleWorkflowElement returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getWorkflowElementRule()); }
	 iv_ruleWorkflowElement=ruleWorkflowElement 
	 { $current=$iv_ruleWorkflowElement.current; } 
	 EOF 
;

// Rule WorkflowElement
ruleWorkflowElement returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getWorkflowElementAccess().getTargetParserRuleCall_0()); 
    }
    this_Target_0=ruleTarget
    { 
        $current = $this_Target_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getWorkflowElementAccess().getStepParserRuleCall_1()); 
    }
    this_Step_1=ruleStep
    { 
        $current = $this_Step_1.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getWorkflowElementAccess().getModuleSetParserRuleCall_2()); 
    }
    this_ModuleSet_2=ruleModuleSet
    { 
        $current = $this_ModuleSet_2.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getWorkflowElementAccess().getVariableParserRuleCall_3()); 
    }
    this_Variable_3=ruleVariable
    { 
        $current = $this_Variable_3.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleTarget
entryRuleTarget returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getTargetRule()); }
	 iv_ruleTarget=ruleTarget 
	 { $current=$iv_ruleTarget.current; } 
	 EOF 
;

// Rule Target
ruleTarget returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='target' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getTargetAccess().getTargetKeyword_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getTargetRule());
	        }
        }
	otherlv_1=RULE_ID
	{
		newLeafNode(otherlv_1, grammarAccess.getTargetAccess().getStepStepCrossReference_1_0()); 
	}

)
))
;





// Entry rule entryRuleModuleSet
entryRuleModuleSet returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getModuleSetRule()); }
	 iv_ruleModuleSet=ruleModuleSet 
	 { $current=$iv_ruleModuleSet.current; } 
	 EOF 
;

// Rule ModuleSet
ruleModuleSet returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='moduleset' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getModuleSetAccess().getModulesetKeyword_0());
    }
(
(
		lv_name_1_0=RULE_ID
		{
			newLeafNode(lv_name_1_0, grammarAccess.getModuleSetAccess().getNameIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getModuleSetRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_1_0, 
        		"ID");
	    }

)
)	otherlv_2='{' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getModuleSetAccess().getLeftCurlyBracketKeyword_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getModuleSetAccess().getModuleSetEntriesModuleSetEntryParserRuleCall_3_0()); 
	    }
		lv_moduleSetEntries_3_0=ruleModuleSetEntry		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getModuleSetRule());
	        }
       		add(
       			$current, 
       			"moduleSetEntries",
        		lv_moduleSetEntries_3_0, 
        		"ModuleSetEntry");
	        afterParserOrEnumRuleCall();
	    }

)
)*	otherlv_4='}' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getModuleSetAccess().getRightCurlyBracketKeyword_4());
    }
)
;





// Entry rule entryRuleMODULE_SET_ENTRY_TYPE
entryRuleMODULE_SET_ENTRY_TYPE returns [String current=null] 
	:
	{ newCompositeNode(grammarAccess.getMODULE_SET_ENTRY_TYPERule()); } 
	 iv_ruleMODULE_SET_ENTRY_TYPE=ruleMODULE_SET_ENTRY_TYPE 
	 { $current=$iv_ruleMODULE_SET_ENTRY_TYPE.current.getText(); }  
	 EOF 
;

// Rule MODULE_SET_ENTRY_TYPE
ruleMODULE_SET_ENTRY_TYPE returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
	kw='csv' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getMODULE_SET_ENTRY_TYPEAccess().getCsvKeyword_0()); 
    }

    |
	kw='doors' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getMODULE_SET_ENTRY_TYPEAccess().getDoorsKeyword_1()); 
    }

    |
	kw='doorsurl' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getMODULE_SET_ENTRY_TYPEAccess().getDoorsurlKeyword_2()); 
    }

    |
	kw='csvfolder' 
    {
        $current.merge(kw);
        newLeafNode(kw, grammarAccess.getMODULE_SET_ENTRY_TYPEAccess().getCsvfolderKeyword_3()); 
    }
)
    ;





// Entry rule entryRuleModuleSetEntry
entryRuleModuleSetEntry returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getModuleSetEntryRule()); }
	 iv_ruleModuleSetEntry=ruleModuleSetEntry 
	 { $current=$iv_ruleModuleSetEntry.current; } 
	 EOF 
;

// Rule ModuleSetEntry
ruleModuleSetEntry returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		{ 
	        newCompositeNode(grammarAccess.getModuleSetEntryAccess().getTypeMODULE_SET_ENTRY_TYPEParserRuleCall_0_0()); 
	    }
		lv_type_0_0=ruleMODULE_SET_ENTRY_TYPE		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getModuleSetEntryRule());
	        }
       		set(
       			$current, 
       			"type",
        		lv_type_0_0, 
        		"MODULE_SET_ENTRY_TYPE");
	        afterParserOrEnumRuleCall();
	    }

)
)(
(
		lv_reference_1_0=RULE_STRING
		{
			newLeafNode(lv_reference_1_0, grammarAccess.getModuleSetEntryAccess().getReferenceSTRINGTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getModuleSetEntryRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"reference",
        		lv_reference_1_0, 
        		"STRING");
	    }

)
))
;





// Entry rule entryRuleVariable
entryRuleVariable returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getVariableRule()); }
	 iv_ruleVariable=ruleVariable 
	 { $current=$iv_ruleVariable.current; } 
	 EOF 
;

// Rule Variable
ruleVariable returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getVariableAccess().getSimpleVariableParserRuleCall_0()); 
    }
    this_SimpleVariable_0=ruleSimpleVariable
    { 
        $current = $this_SimpleVariable_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getVariableAccess().getArrayVariableParserRuleCall_1()); 
    }
    this_ArrayVariable_1=ruleArrayVariable
    { 
        $current = $this_ArrayVariable_1.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleSimpleVariable
entryRuleSimpleVariable returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getSimpleVariableRule()); }
	 iv_ruleSimpleVariable=ruleSimpleVariable 
	 { $current=$iv_ruleSimpleVariable.current; } 
	 EOF 
;

// Rule SimpleVariable
ruleSimpleVariable returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='var' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getSimpleVariableAccess().getVarKeyword_0());
    }
(
(
		lv_name_1_0=RULE_ID
		{
			newLeafNode(lv_name_1_0, grammarAccess.getSimpleVariableAccess().getNameIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getSimpleVariableRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_1_0, 
        		"ID");
	    }

)
)	otherlv_2='=' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getSimpleVariableAccess().getEqualsSignKeyword_2());
    }
(
(
		lv_value_3_0=RULE_STRING
		{
			newLeafNode(lv_value_3_0, grammarAccess.getSimpleVariableAccess().getValueSTRINGTerminalRuleCall_3_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getSimpleVariableRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"value",
        		lv_value_3_0, 
        		"STRING");
	    }

)
))
;





// Entry rule entryRuleArrayVariable
entryRuleArrayVariable returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getArrayVariableRule()); }
	 iv_ruleArrayVariable=ruleArrayVariable 
	 { $current=$iv_ruleArrayVariable.current; } 
	 EOF 
;

// Rule ArrayVariable
ruleArrayVariable returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='var' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getArrayVariableAccess().getVarKeyword_0());
    }
(
(
		lv_name_1_0=RULE_ID
		{
			newLeafNode(lv_name_1_0, grammarAccess.getArrayVariableAccess().getNameIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getArrayVariableRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_1_0, 
        		"ID");
	    }

)
)	otherlv_2='[]' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getArrayVariableAccess().getLeftSquareBracketRightSquareBracketKeyword_2());
    }
	otherlv_3='=' 
    {
    	newLeafNode(otherlv_3, grammarAccess.getArrayVariableAccess().getEqualsSignKeyword_3());
    }
	otherlv_4='{' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getArrayVariableAccess().getLeftCurlyBracketKeyword_4());
    }
((
(
		lv_items_5_0=RULE_STRING
		{
			newLeafNode(lv_items_5_0, grammarAccess.getArrayVariableAccess().getItemsSTRINGTerminalRuleCall_5_0_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getArrayVariableRule());
	        }
       		addWithLastConsumed(
       			$current, 
       			"items",
        		lv_items_5_0, 
        		"STRING");
	    }

)
)(	otherlv_6=',' 
    {
    	newLeafNode(otherlv_6, grammarAccess.getArrayVariableAccess().getCommaKeyword_5_1_0());
    }
(
(
		lv_items_7_0=RULE_STRING
		{
			newLeafNode(lv_items_7_0, grammarAccess.getArrayVariableAccess().getItemsSTRINGTerminalRuleCall_5_1_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getArrayVariableRule());
	        }
       		addWithLastConsumed(
       			$current, 
       			"items",
        		lv_items_7_0, 
        		"STRING");
	    }

)
))*)?	otherlv_8='}' 
    {
    	newLeafNode(otherlv_8, grammarAccess.getArrayVariableAccess().getRightCurlyBracketKeyword_6());
    }
)
;





// Entry rule entryRuleStep
entryRuleStep returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getStepRule()); }
	 iv_ruleStep=ruleStep 
	 { $current=$iv_ruleStep.current; } 
	 EOF 
;

// Rule Step
ruleStep returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getStepAccess().getModelConstructorStepParserRuleCall_0()); 
    }
    this_ModelConstructorStep_0=ruleModelConstructorStep
    { 
        $current = $this_ModelConstructorStep_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getStepAccess().getModelOperationStepParserRuleCall_1()); 
    }
    this_ModelOperationStep_1=ruleModelOperationStep
    { 
        $current = $this_ModelOperationStep_1.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleModelConstructorStep
entryRuleModelConstructorStep returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getModelConstructorStepRule()); }
	 iv_ruleModelConstructorStep=ruleModelConstructorStep 
	 { $current=$iv_ruleModelConstructorStep.current; } 
	 EOF 
;

// Rule ModelConstructorStep
ruleModelConstructorStep returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='constructor' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getModelConstructorStepAccess().getConstructorKeyword_0());
    }
(
(
		lv_name_1_0=RULE_ID
		{
			newLeafNode(lv_name_1_0, grammarAccess.getModelConstructorStepAccess().getNameIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getModelConstructorStepRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_1_0, 
        		"ID");
	    }

)
)	otherlv_2='{' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getModelConstructorStepAccess().getLeftCurlyBracketKeyword_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getModelConstructorStepAccess().getFeaturesOperationFeatureParserRuleCall_3_0()); 
	    }
		lv_features_3_0=ruleOperationFeature		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getModelConstructorStepRule());
	        }
       		add(
       			$current, 
       			"features",
        		lv_features_3_0, 
        		"OperationFeature");
	        afterParserOrEnumRuleCall();
	    }

)
)*	otherlv_4='}' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getModelConstructorStepAccess().getRightCurlyBracketKeyword_4());
    }
)
;





// Entry rule entryRuleModelOperationStep
entryRuleModelOperationStep returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getModelOperationStepRule()); }
	 iv_ruleModelOperationStep=ruleModelOperationStep 
	 { $current=$iv_ruleModelOperationStep.current; } 
	 EOF 
;

// Rule ModelOperationStep
ruleModelOperationStep returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='op' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getModelOperationStepAccess().getOpKeyword_0());
    }
(
(
		lv_name_1_0=RULE_ID
		{
			newLeafNode(lv_name_1_0, grammarAccess.getModelOperationStepAccess().getNameIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getModelOperationStepRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_1_0, 
        		"ID");
	    }

)
)	otherlv_2='{' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getModelOperationStepAccess().getLeftCurlyBracketKeyword_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getModelOperationStepAccess().getFeaturesOperationFeatureParserRuleCall_3_0()); 
	    }
		lv_features_3_0=ruleOperationFeature		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getModelOperationStepRule());
	        }
       		add(
       			$current, 
       			"features",
        		lv_features_3_0, 
        		"OperationFeature");
	        afterParserOrEnumRuleCall();
	    }

)
)*	otherlv_4='}' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getModelOperationStepAccess().getRightCurlyBracketKeyword_4());
    }
)
;





// Entry rule entryRuleOperationFeature
entryRuleOperationFeature returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getOperationFeatureRule()); }
	 iv_ruleOperationFeature=ruleOperationFeature 
	 { $current=$iv_ruleOperationFeature.current; } 
	 EOF 
;

// Rule OperationFeature
ruleOperationFeature returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getOperationFeatureAccess().getDependencyFeatureParserRuleCall_0()); 
    }
    this_DependencyFeature_0=ruleDependencyFeature
    { 
        $current = $this_DependencyFeature_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getOperationFeatureAccess().getImplementationFeatureParserRuleCall_1()); 
    }
    this_ImplementationFeature_1=ruleImplementationFeature
    { 
        $current = $this_ImplementationFeature_1.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getOperationFeatureAccess().getForFeatureParserRuleCall_2()); 
    }
    this_ForFeature_2=ruleForFeature
    { 
        $current = $this_ForFeature_2.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getOperationFeatureAccess().getSourceFeatureParserRuleCall_3()); 
    }
    this_SourceFeature_3=ruleSourceFeature
    { 
        $current = $this_SourceFeature_3.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleSourceFeature
entryRuleSourceFeature returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getSourceFeatureRule()); }
	 iv_ruleSourceFeature=ruleSourceFeature 
	 { $current=$iv_ruleSourceFeature.current; } 
	 EOF 
;

// Rule SourceFeature
ruleSourceFeature returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='source' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getSourceFeatureAccess().getSourceKeyword_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getSourceFeatureRule());
	        }
        }
	otherlv_1=RULE_ID
	{
		newLeafNode(otherlv_1, grammarAccess.getSourceFeatureAccess().getModuleSetModuleSetCrossReference_1_0()); 
	}

)
))
;





// Entry rule entryRuleForFeature
entryRuleForFeature returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getForFeatureRule()); }
	 iv_ruleForFeature=ruleForFeature 
	 { $current=$iv_ruleForFeature.current; } 
	 EOF 
;

// Rule ForFeature
ruleForFeature returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='for' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getForFeatureAccess().getForKeyword_0());
    }
(
(
		lv_loopVar_1_0=RULE_ID
		{
			newLeafNode(lv_loopVar_1_0, grammarAccess.getForFeatureAccess().getLoopVarIDTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getForFeatureRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"loopVar",
        		lv_loopVar_1_0, 
        		"ID");
	    }

)
)	otherlv_2='in' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getForFeatureAccess().getInKeyword_2());
    }
(
(
		lv_arrayVar_3_0=RULE_ID
		{
			newLeafNode(lv_arrayVar_3_0, grammarAccess.getForFeatureAccess().getArrayVarIDTerminalRuleCall_3_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getForFeatureRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"arrayVar",
        		lv_arrayVar_3_0, 
        		"ID");
	    }

)
)	otherlv_4='{' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getForFeatureAccess().getLeftCurlyBracketKeyword_4());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getForFeatureAccess().getFeaturesOperationFeatureParserRuleCall_5_0()); 
	    }
		lv_features_5_0=ruleOperationFeature		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getForFeatureRule());
	        }
       		add(
       			$current, 
       			"features",
        		lv_features_5_0, 
        		"OperationFeature");
	        afterParserOrEnumRuleCall();
	    }

)
)*	otherlv_6='}' 
    {
    	newLeafNode(otherlv_6, grammarAccess.getForFeatureAccess().getRightCurlyBracketKeyword_6());
    }
)
;





// Entry rule entryRuleDependencyFeature
entryRuleDependencyFeature returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getDependencyFeatureRule()); }
	 iv_ruleDependencyFeature=ruleDependencyFeature 
	 { $current=$iv_ruleDependencyFeature.current; } 
	 EOF 
;

// Rule DependencyFeature
ruleDependencyFeature returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='dependency' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getDependencyFeatureAccess().getDependencyKeyword_0());
    }
(
(
		{
			if ($current==null) {
	            $current = createModelElement(grammarAccess.getDependencyFeatureRule());
	        }
        }
	otherlv_1=RULE_ID
	{
		newLeafNode(otherlv_1, grammarAccess.getDependencyFeatureAccess().getStepStepCrossReference_1_0()); 
	}

)
)(	otherlv_2='{' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getDependencyFeatureAccess().getLeftCurlyBracketKeyword_2_0());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getDependencyFeatureAccess().getVariablesVariableParserRuleCall_2_1_0()); 
	    }
		lv_variables_3_0=ruleVariable		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getDependencyFeatureRule());
	        }
       		add(
       			$current, 
       			"variables",
        		lv_variables_3_0, 
        		"Variable");
	        afterParserOrEnumRuleCall();
	    }

)
)*	otherlv_4='}' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getDependencyFeatureAccess().getRightCurlyBracketKeyword_2_2());
    }
)?(
(
		lv_name_5_0=RULE_ID
		{
			newLeafNode(lv_name_5_0, grammarAccess.getDependencyFeatureAccess().getNameIDTerminalRuleCall_3_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getDependencyFeatureRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"name",
        		lv_name_5_0, 
        		"ID");
	    }

)
)?)
;





// Entry rule entryRuleImplementationFeature
entryRuleImplementationFeature returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getImplementationFeatureRule()); }
	 iv_ruleImplementationFeature=ruleImplementationFeature 
	 { $current=$iv_ruleImplementationFeature.current; } 
	 EOF 
;

// Rule ImplementationFeature
ruleImplementationFeature returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='implementation' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getImplementationFeatureAccess().getImplementationKeyword_0());
    }
(
(
		lv_implementation_1_0=RULE_STRING
		{
			newLeafNode(lv_implementation_1_0, grammarAccess.getImplementationFeatureAccess().getImplementationSTRINGTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getImplementationFeatureRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"implementation",
        		lv_implementation_1_0, 
        		"STRING");
	    }

)
))
;





RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;


