package org.kuali.student.enrollment.class1.krms.tree;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class1.krms.tree.node.TreeNode;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/12
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class RulePreviewTreeBuilder extends RuleViewTreeBuilder {

    private RuleEditor ruleEditor;

    @Override
    public Tree<TreeNode, String> buildTree(RuleDefinitionContract rule){
        this.ruleEditor = (RuleEditor) rule;
        return super.buildTree(ruleEditor);
    }

    @Override
    protected String buildNodeLabel(PropositionDefinitionContract proposition){

        PropositionEditor prop = (PropositionEditor) proposition;
        //Add the proposition with alpha code in the map if it doesn't already exist.
        if (null == prop.getKey()) {
            prop.setKey((String) ruleEditor.getAlpha().next());
        }

        //Build the node label.
        String prefix = "<b>" + prop.getKey() + ".</b> ";
        return prefix + StringEscapeUtils.escapeHtml(prop.getDescription());
    }
}
