package org.kuali.student.krms.test;

import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;

public class CourseSetGPAProposition extends ComparisonOverCourseSetProposition {

    private String courseSetId;
    private Float gpa;
    private ComparisonOperator operator;
    
    private final TermSpecification creditsAsset = new TermSpecification("gpaInACourseAsset", "Float");

    public CourseSetGPAProposition(String courseSetId, Float gpa, ComparisonOperator operator) {
        super(courseSetId);
        this.courseSetId = courseSetId;
        this.gpa = gpa;
        this.operator = operator;
    }

    @Override
    protected boolean performSingleCourseComparison(String courseId) {
        // TODO add hook here for fake value lookup
        
        Float discoveredGpa = 1.0f;
        
        return operator.compare(discoveredGpa, gpa);
    }

}
