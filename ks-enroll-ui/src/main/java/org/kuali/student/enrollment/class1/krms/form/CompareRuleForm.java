/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Paul on 2012/11/22
 */
package org.kuali.student.enrollment.class1.krms.form;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.impl.ui.TermParameter;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.RulePreviewer;
import org.kuali.student.enrollment.uif.form.KSUifForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CompareRuleForm extends KSUifForm {


    private String cluId;
    private RuleEditor ruleEditor = new RuleEditor();
    private  Tree<TreeNode, String> cmRule;


    public RuleEditor getRuleEditor() {
        return ruleEditor;
    }
    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public Tree<TreeNode, String> getCmRule() {
        return cmRule;
    }

    public void setCmRule(Tree<TreeNode, String> cmRule) {
        this.cmRule = cmRule;
    }

}
