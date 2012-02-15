package org.kuali.student.enrollment.class2.acal.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcademicTermWrapper {

    private static final long serialVersionUID = 4898118410378641665L;

    private TermInfo termInfo;

    private String name;
    private int instructionalDays;
    private String termType;
    private Date startDate;
    private Date endDate;

    private String termNameForUI;

    private List<KeyDateWrapper> keydates = new ArrayList<KeyDateWrapper>();

    private boolean readOnly = false;

    public AcademicTermWrapper(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TermInfo getTermInfo() {
        return termInfo;
    }

    public void setTermInfo(TermInfo termInfo) {
        this.termInfo = termInfo;
        if (termInfo != null){
            if (StringUtils.equals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY,termInfo.getStateKey())){
                readOnly = true;
            }
        }
    }

    public int getInstructionalDays() {
        return instructionalDays;
    }

    public void setInstructionalDays(int instructionalDays) {
        this.instructionalDays = instructionalDays;
    }

    public List<KeyDateWrapper> getKeydates() {
        return keydates;
    }

    public void setKeydates(List<KeyDateWrapper> keydates) {
        this.keydates = keydates;
    }

    public String getTermNameForUI() {
        return termNameForUI;
    }

    public void setTermNameForUI(String termNameForUI) {
        this.termNameForUI = termNameForUI;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
