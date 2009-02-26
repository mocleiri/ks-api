package org.kuali.student.rules.lumgui.client.model;

public class LumModel implements LumModelObject {
    
    public enum FieldName{
        STATEMENT_ID, SHOW_ALGEBRA, CURRENT_VIEW,
        RATIONALE, PRE_REQ_COURSES, NATURAL_LANGUAGE;
    }
    public enum LumView {
        SIMPLE_VIEW, COMPLEX_VIEW
    }
    private String statementId;
    private LumView currentView;
    private String rationale;
    private String preReqCourses;
    private String naturalLanguage;
    private boolean showAlgebra;

    public LumView getCurrentView() {
        return currentView;
    }
    public void setCurrentView(LumView currentView) {
        this.currentView = currentView;
    }
    public boolean isShowAlgebra() {
        return showAlgebra;
    }
    public void setShowAlgebra(boolean showAlgebra) {
        this.showAlgebra = showAlgebra;
    }
    public String getUniqueId() {
        return getStatementId();
    }
    public String getStatementId() {
        return statementId;
    }
    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }
    public String getRationale() {
        return rationale;
    }
    public void setRationale(String rationale) {
        this.rationale = rationale;
    }
    public String getPreReqCourses() {
        return preReqCourses;
    }
    public void setPreReqCourses(String preReqCourses) {
        this.preReqCourses = preReqCourses;
    }
    public String getNaturalLanguage() {
        return naturalLanguage;
    }
    public void setNaturalLanguage(String naturalLanguage) {
        this.naturalLanguage = naturalLanguage;
    }
    public Object getValue(String fieldName) {
        FieldName enumFieldName = FieldName.valueOf(fieldName);
        if (enumFieldName == FieldName.STATEMENT_ID) {
            return getStatementId();
        } else if (enumFieldName == FieldName.SHOW_ALGEBRA) {
            return new Boolean(isShowAlgebra());
        } else if (enumFieldName == FieldName.CURRENT_VIEW) {
            return getCurrentView();
        } else if (enumFieldName == FieldName.RATIONALE) {
            return getRationale();
        } else if (enumFieldName == FieldName.PRE_REQ_COURSES) {
            return getPreReqCourses();
        } else if (enumFieldName == FieldName.NATURAL_LANGUAGE) {
            return getNaturalLanguage();
        } else {
            throw new RuntimeException("Unknown fieldName " + fieldName);
        }
    }
        
}
