package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.dto.RuleManagementWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/04/04
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnrolRuleManagementWrapper extends RuleManagementWrapper {

    private String cluDescription;
    private String atpCode;
    private List<CluInformation> clusInRange;

    public String getCluDescription() {
        return cluDescription;
    }

    public void setCluDescription(String cluDescription) {
        this.cluDescription = cluDescription;
    }

    public String getAtpCode() {
        return atpCode;
    }

    public void setAtpCode(String atpCode) {
        this.atpCode = atpCode;
    }

    public EnrolRuleEditor getEnrolRuleEditor(){
        return (EnrolRuleEditor) this.getRuleEditor();
    }

    public List<CluInformation> getClusInRange() {
        if(this.clusInRange==null){
            this.clusInRange = new ArrayList<CluInformation>();
        }
        return this.clusInRange;
    }

    public void setClusInRange(List<CluInformation> clusInRange) {
        this.clusInRange = clusInRange;
    }

}
