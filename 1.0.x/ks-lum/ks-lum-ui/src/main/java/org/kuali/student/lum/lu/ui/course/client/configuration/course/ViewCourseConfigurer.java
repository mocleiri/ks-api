/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.lum.lu.ui.course.client.configuration.course;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.DisplayMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.CollapsableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;

import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for creating a proposal.
 *
 * TODO: The following is a list of items that need to be fixed.
 *  1) All hardcoded drop downs need to be replaced with one populated via an enumeration lookup
 *  2) Any pickers (eg. org, course, needs to be replaced wtih proper lookup based search pickers
 *  
 * @author Kuali Student Team
 *
 */
public class ViewCourseConfigurer 
implements 
CreditCourseConstants,
CreditCourseFormatConstants,
CreditCourseActivityConstants,
MetaInfoConstants,
CreditCourseDurationConstants,
FeeInfoConstants,
LearningObjectiveConstants
{

	private static final String PATH_SEPARATOR = "/";
	//FIXME: Temp paths waiting for DOL changes
    private static final String ACADEMIC_SUBJECT_ORGS_PATH = "fees/0/attributes/OversightName";
	private static final String TERMS_OFFERED_PATH = "fees/0/attributes/TermOffered";
	private static final String ADMIN_ORG_DEPT_NAME_PATH = "fees/0/attributes/DeptOrgName";
	private static final String COURSE_TYPE_PATH = "fees/0/attributes/CourseType";
    private static final String VERSION_TITLE_PATH = "versionTitle";
	private static final String VERSION_CODE_PATH = "versionCode";
	private static final String VERSION_COURSE_NUMBER_SUFFIX_PATH = "courseNumberSuffix";
	private static final String VERSION_SUBJECT_AREA_PATH = "subjectArea";
    private static final String LO_DESCRIPTION_PATH = "includedSingleUseLo/description/plain";
    private static final String ALTERNATE_ADMIN_ORG_ID_PATH = "orgId";
    private static final String STATEMENTS_PATH = "statements";
    
    //FIXME:  Initialize type and state from selected cluId
    private String type = "course";
    private String state = "draft";
    private String groupName;
    
    private DataModelDefinition modelDefinition;

    public enum Sections{
        BASIC_INFO, COMPREHENSIVE_INFO
    }

    public void setModelDefinition(DataModelDefinition modelDefinition){
        this.modelDefinition = modelDefinition;
    }    
    
    public void generateLayout(ConfigurableLayout layout) {
        groupName = LUConstants.COURSE_GROUP_NAME;

        layout.addSection(new String[] {getLabel(LUConstants.CURRENT_VIEW_LABEL_KEY)}, generateDetails());

    }
    
    private SectionView generateDetails() {
        VerticalSectionView section = initSectionView(Sections.BASIC_INFO, LUConstants.COURSE_DETAILS_LABEL_KEY);
               
        section.addSection(generateBasicSection());
        section.addSection(generateComprehensiveSection());

        
        return section; 
    }

	private BaseSection generateBasicSection() {
		
		VerticalSection section = new VerticalSection();
		
		addField(section, CreditCourseConstants.COURSE_TITLE, getLabel(LUConstants.TITLE_LABEL_KEY), new KSLabel());
        addField(section, DESCRIPTION+ PATH_SEPARATOR + "plain", getLabel(LUConstants.DESCRIPTION_LABEL_KEY), new KSLabel());

        addField(section,  CreditCourseConstants.STATE, getLabel(LUConstants.STATE_LABEL_KEY), new KSLabel());
        
        //FIXME: This is a hack to return id/key field names rather than the id/key. Fix in M5
        addField(section, COURSE_TYPE_PATH, getLabel(LUConstants.TYPE_LABEL_KEY), new KSLabel());
        addField(section, ADMIN_ORG_DEPT_NAME_PATH, getLabel(LUConstants.DEPT_LABEL_KEY), new KSLabel());

//FIXME        addField(section, CREDITS,  getLabel(LUConstants.CREDITS_LABEL_KEY), new KSLabel());
        addField(section, STATEMENTS_PATH, getLabel(LUConstants.REQUISITES_LABEL_KEY), new StatementList());
        addField(section,  FORMATS, getLabel(LUConstants.FORMATS_LABEL_KEY), new CourseFormatList(FORMATS));
//FIXME        addField(section, FEES + "/" + "id", getLabel(LUConstants.FINANCIALS_LABEL_KEY), new KSLabel());
        addField(section, CAMPUS_LOCATIONS, getLabel(LUConstants.CAMPUS_LOCATION_LABEL_KEY), new CampusLocationList());

        addField(section,  PRIMARY_INSTRUCTOR, getLabel(LUConstants.PRIMARY_INSTRUCTOR_LABEL_KEY), new KSLabel());
        addField(section, CROSS_LISTINGS, getLabel(LUConstants.CROSS_LISTED_LABEL_KEY), new CrossListedList(CROSS_LISTINGS));
        
        return section;
	}

    private CollapsableSection generateComprehensiveSection() {
    	
  	    CollapsableSection section = new CollapsableSection("More Details");

//FIXME  In M4 Only one term offered can be set so don't need a list here. Fix for M5
//      addField(section, CreditCourseConstants.TERMS_OFFERED, "Terms Offered", new TermsOfferedList());
        addField(section, TERMS_OFFERED_PATH, getLabel(LUConstants.TERMS_OFFERED_LABEL_KEY), new KSLabel());
        addField(section, CreditCourseConstants.DURATION + PATH_SEPARATOR + TERM_TYPE, getLabel(LUConstants.DURATION_TYPE_LABEL_KEY), new KSLabel());
        addField(section, CreditCourseConstants.DURATION + PATH_SEPARATOR + QUANTITY, getLabel(LUConstants.DURATION_QUANTITY_LABEL_KEY), new KSLabel());
        addField(section, TRANSCRIPT_TITLE, getLabel(LUConstants.SHORT_TITLE_LABEL_KEY), new KSLabel());

//FIXME  In M4 Only one primary admin org can be set so don't need a list here. Fix for M5
//      addField(section, CreditCourseConstants.ALT_ADMIN_ORGS, null, new AlternateAdminOrgList(ALT_ADMIN_ORGS));
        addField(section,  VERSIONS, getLabel(LUConstants.VERSION_CODES_LABEL_KEY), new VersionCodeList(VERSIONS));
        addField(section,  JOINTS, getLabel(LUConstants.JOINT_OFFERINGS_LABEL_KEY), new OfferedJointlyList(JOINTS));

        addField(section, EFFECTIVE_DATE, getLabel(LUConstants.EFFECTIVE_DATE_LABEL_KEY), new KSLabel());
        addField(section, EXPIRATION_DATE, getLabel(LUConstants.EXPIRATION_DATE_LABEL_KEY), new KSLabel());

//FIXME  In M4 Only one academic subject org can be set so don't need a list here. Fix for M5
//      addField(section, ACADEMIC_SUBJECT_ORGS, getLabel(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY), new AcademicSubjectOrgList());
        addField(section, ACADEMIC_SUBJECT_ORGS_PATH, getLabel(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY), new KSLabel());

        addField(section, COURSE_SPECIFIC_LOS,  getLabel(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY),  new LearningObjectiveList(COURSE_SPECIFIC_LOS));
        
        return section;

    }

    private class AlternateAdminOrgList extends DisplayMultiplicityComposite{

        private final String parentPath;
        public AlternateAdminOrgList(String parentPath){
            this.parentPath = parentPath;
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            VerticalSection item = new VerticalSection();
            addField(item, ALTERNATE_ADMIN_ORG_ID_PATH, null, new KSLabel(), path);
            return item;
        }
    }

    private class AlternateInstructorList extends DisplayMultiplicityComposite{

        private final String parentPath;
        public AlternateInstructorList(String parentPath){
            this.parentPath = parentPath;
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            VerticalSection item = new VerticalSection();
            addField(item, "personId", null, new KSLabel(), path);
            return item;
        }
    }

    private class CampusLocationList extends KSLabelList {
        public CampusLocationList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    private class CluActivityTypeList extends KSLabelList{
        public CluActivityTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    private class ContactHoursAtpTypeList extends KSLabelList{
        public ContactHoursAtpTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }

    }

    //FIXME CourseFormatList - not sure what needs to be here
    private class CourseFormatList extends DisplayMultiplicityComposite {
        private final String parentPath;
        public CourseFormatList(String parentPath){
//            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
        }

        public Widget createItem() {
            VerticalSection item = new VerticalSection();
            addField(item, ACTIVITIES, null, new CourseActivityList(QueryPath.concat(parentPath, String.valueOf(itemCount-1), ACTIVITIES).toString()),
                    parentPath + PATH_SEPARATOR + String.valueOf(itemCount -1));
            return item;
        }
    }

    //FIXME CourseActivityList - not sure what needs to be here
    private class CourseActivityList extends DisplayMultiplicityComposite {

        private final String parentPath;
        public CourseActivityList(String parentPath){
//            super(StyleType.SUB_LEVEL);
            this.parentPath = parentPath;
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection activity = new GroupSection();
            addField(activity, ACTIVITY_TYPE, null, new KSLabel(), path);
//            activity.nextLine();
//
//            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), new KSLabel(), path);
//            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.TIME_UNIT, "Duration Type", new KSLabel(), path);
//
//            activity.nextLine();
//            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.HRS, "Contact Hours" , new KSLabel(), path);
//            addField(activity, DEFAULT_ENROLLMENT_ESTIMATE, getLabel(LUConstants.CLASS_SIZE_LABEL_KEY), new KSLabel(), path);

            return activity;
        }
    }

    private class CrossListedList extends DisplayMultiplicityComposite {
        private final String parentPath;
        public CrossListedList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection group = new GroupSection();
            addField(group, SUBJECT_AREA, null, new KSLabel(), path);
            addField(group, COURSE_NUMBER_SUFFIX, null, new KSLabel(), path);

            return group;
        }
    }

    private class AcademicSubjectOrgList extends KSLabelList{

        public AcademicSubjectOrgList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }

    }

//    private class TermsOfferedList extends DisplayMultiplicityComposite{
//        private final String parentPath;
//        public TermsOfferedList(String parentPath){
//            this.parentPath = parentPath;
//        }
//
//        @Override
//        public Widget createItem() {
//            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
//            GroupSection group = new GroupSection();
//            addField(group, String.valueOf(itemCount-1), null, new KSLabel(), path);
//
//            return group;
//        }
//    }

    private class TermsOfferedList extends KSLabelList{
        public TermsOfferedList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }

    }
    
    private class DurationAtpTypeList extends KSLabelList{
        public DurationAtpTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    private class StatementList extends KSLabelList {
        
    	public StatementList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }
    
    private class LearningObjectiveList extends DisplayMultiplicityComposite {
		private final String parentPath;
        public LearningObjectiveList(String parentPath){
            this.parentPath = parentPath;
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, LO_DESCRIPTION_PATH, null, new KSLabel(), path);

            return ns;
        }
    }

    private class VersionCodeList extends DisplayMultiplicityComposite {
		private final String parentPath;
        public VersionCodeList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, VERSION_SUBJECT_AREA_PATH,  null, new KSLabel(), path);
            addField(ns, VERSION_COURSE_NUMBER_SUFFIX_PATH,  null, new KSLabel(), path);
            addField(ns, VERSION_CODE_PATH,  null, new KSLabel(), path);

            addField(ns, VERSION_TITLE_PATH, null, new KSLabel(), path);

            return ns;
        }
    }

    private class OfferedJointlyList extends DisplayMultiplicityComposite {

		private final String parentPath;
        public OfferedJointlyList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection groupSection = new GroupSection();
            addField(groupSection, "subjectArea", getLabel(LUConstants.CODE_LABEL_KEY), new KSLabel(), path);
            addField(groupSection, "courseNumberSuffix", getLabel(LUConstants.CODE_LABEL_KEY), new KSLabel(), path);
            addField(groupSection, "courseTitle", getLabel(LUConstants.TITLE_LABEL_KEY), new KSLabel(), path);

            return groupSection;
        }
    } 

    private VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), CourseConfigurer.CLU_PROPOSAL_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);

        return section;
    }

    protected String getTabKey() {
    	return getLabel(LUConstants.CURRENT_VIEW_LABEL_KEY);
    }

    private String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    }

    private SectionTitle getH1Title(String labelKey) {
        return SectionTitle.generateH1Title(getLabel(labelKey));
    } 

    private SectionTitle getH2Title(String labelKey) {
        return SectionTitle.generateH2Title(getLabel(labelKey));
    } 

    private SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    } 

    private SectionTitle getH4Title(String labelKey) {
        return SectionTitle.generateH4Title(getLabel(labelKey));
    } 

    private SectionTitle getH5Title(String labelKey) {
        return SectionTitle.generateH5Title(getLabel(labelKey));
    }


    private void addField(Section section, String fieldKey, String fieldLabel) {
        addField(section, fieldKey, fieldLabel, null, null);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, Widget widget) {
        addField(section, fieldKey, fieldLabel, widget, null);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, String parentPath) {
        addField(section, fieldKey, fieldLabel, null, parentPath);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptor fd = new FieldDescriptor(path.toString(), fieldLabel, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
    }

}