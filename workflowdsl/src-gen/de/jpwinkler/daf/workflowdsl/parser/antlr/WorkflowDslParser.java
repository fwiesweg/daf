/*
* generated by Xtext
*/
package de.jpwinkler.daf.workflowdsl.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import de.jpwinkler.daf.workflowdsl.services.WorkflowDslGrammarAccess;

public class WorkflowDslParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private WorkflowDslGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected de.jpwinkler.daf.workflowdsl.parser.antlr.internal.InternalWorkflowDslParser createParser(XtextTokenStream stream) {
		return new de.jpwinkler.daf.workflowdsl.parser.antlr.internal.InternalWorkflowDslParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "WorkflowModel";
	}
	
	public WorkflowDslGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(WorkflowDslGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
