package org.kuali.student.enrollment.class1.krms.tree;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.student.enrollment.class1.krms.tree.node.TreeNode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Peggy
 * Date: 2/4/13
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleViewTreeBuilder {

    private static final long serialVersionUID = 1L;

   public Tree<TreeNode, String> buildTree(RuleDefinitionContract rule){
        Tree myTree = new Tree<TreeNode, String>();

        Node<TreeNode, String> rootNode = new Node<TreeNode, String>();
        rootNode.setNodeLabel("root");
        rootNode.setNodeType("rootNode");
        rootNode.setData(new TreeNode("Rule:"));
        myTree.setRootElement(rootNode);

        if (rule != null){
            PropositionDefinitionContract prop = rule.getProposition();
            buildPreviewTree(rootNode, prop);
        }

        //Underline the first node in the preview.
        if ((rootNode.getChildren() != null) && (rootNode.getChildren().size() > 0)){
            Node<TreeNode, String> firstNode = rootNode.getChildren().get(0);
            firstNode.setNodeType("subruleHeader subruleElement");
            firstNode.setNodeLabel(firstNode.getNodeLabel() + ":");
        }

        return myTree;
    }

    private void buildPreviewTree(Node<TreeNode, String> currentNode, PropositionDefinitionContract prop){
        if (prop != null) {

            Node<TreeNode, String> newNode = new Node<TreeNode, String>();
            newNode.setNodeLabel(this.buildNodeLabel(prop));
            newNode.setNodeType("subruleElement");

            TreeNode tNode = new TreeNode(prop.getDescription());
            newNode.setData(tNode);
            currentNode.getChildren().add(newNode);

            if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())){

                boolean first = true;
                List<? extends PropositionDefinitionContract> nodeChildren = prop.getCompoundComponents();
                for (PropositionDefinitionContract child : nodeChildren){
                    // add an opcode node in between each of the children.
                    if (!first){
                        //addOpCodeNode(newNode, propositionEditor);
                        Node<TreeNode, String> opNode = new Node<TreeNode, String>();
                        if (LogicalOperator.AND.getCode().equalsIgnoreCase(prop.getCompoundOpCode())){
                            opNode.setNodeLabel("AND");
                        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(prop.getCompoundOpCode())){
                            opNode.setNodeLabel("OR");
                        }

                        opNode.setData(new TreeNode(prop.getCompoundOpCode()));
                        newNode.getChildren().add(opNode);
                    }
                    first = false;
                    // call to build the childs node
                    buildPreviewTree(newNode, child);
                }
            }

        }
    }

    protected String buildNodeLabel(PropositionDefinitionContract proposition){
        return StringEscapeUtils.escapeHtml(proposition.getDescription());
    }
}
