/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.CustomNestedSection;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.configurable.mvc.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.VerticalSectionView;
import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.Type;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentPanel;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.lum.lu.ui.course.client.configuration.CustomSectionView;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.viewclu.ViewCluConfigurer;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;
import org.kuali.student.lum.lu.ui.course.client.widgets.OrgPicker;

import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for creating a proposal.
 *
 * @author Kuali Student Team
 *
 */
public class LuConfigurer {

    //FIXME:  Initialize type and state
    private static String type;
    private static String state;


    public enum LuSections{
        CLU_BEGIN, AUTHOR, GOVERNANCE, COURSE_LOGISTICS, COURSE_INFO, LEARNING_OBJECTIVES,
        COURSE_REQUISITES, ACTIVE_DATES, FINANCIALS, PGM_REQUIREMENTS, ATTACHMENTS, COMMENTS, DOCUMENTS,
        DEMO_SECTION
    }

    public static void configureCluProposal(ConfigurableLayout layout, String objectKey, String typeKey, String stateKey){
        addCluStartSection(layout);
        addGovernanceSection(layout);
        addCourseLogistics(layout);
        addCourseInfoSection(layout, objectKey, typeKey, stateKey);
        addCourseRequisitesSection(layout);
        addActiveDatesSection(layout);
        addFinancialsSection(layout);
        addProgramRequirements(layout);
        addProposalSummarySection(layout);

        layout.addTool(new CollaboratorTool());
        layout.addTool(new CommentPanel(LuSections.COMMENTS, LUConstants.TOOL_COMMENTS));
        layout.addTool(new DocumentTool(LuSections.DOCUMENTS, LUConstants.TOOL_DOCUMENTS));
    }

    public static void addProposalSummarySection(ConfigurableLayout layout){
        layout.addSection(new String[] {getLabel(LUConstants.SUMMARY_LABEL_KEY)}, ViewCluConfigurer.generateSummarySection());
    }

    public static void addCluStartSection(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.CLU_BEGIN, "Start", CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title("Proposal Information"));
        section.setInstructions("Enter the following to save a draft of the clu proposal.");

        section.addField(new FieldDescriptor("proposalInfo/name", "Proposal Title", Type.STRING));
        //This will need to be a person picker
        section.addField(new FieldDescriptor("proposalInfo/proposerPerson", "Originating Faculty Member",Type.LIST, new PersonList())) ;
        ((PagedSectionLayout)layout).addStartSection(section);
    }

    /**
     * Adds a section for adding or modifying rules associated with a given course (program).
     *
     * @param layout - a content pane to which this section is added to
     * @return
     */
    private static void addCourseRequisitesSection(ConfigurableLayout layout) {
        CustomSectionView section = new CustomSectionView(LuSections.COURSE_REQUISITES,
                getLabel(LUConstants.REQUISITES_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(getLabel(LUConstants.REQUISITES_LABEL_KEY)));

        VerticalSection startDate = new VerticalSection();
        startDate.setSectionTitle(SectionTitle.generateH2Title("Test"));

		/* TODO: Once we have a section that allows for flow between rule screens
        VerticalSection enrollmentSection = new VerticalSection();
        enrollmentSection.setSectionTitle(SectionTitle.generateH2Title("Enrollment Restrictions"));
        enrollmentSection.addField(new FieldDescriptor("rationalle", "Rationalle", Type.STRING));
        enrollmentSection.addField(new FieldDescriptor("rules", "Rules", Type.STRING));
        enrollmentSection.addWidget(new KSButton());
        section.addSection(enrollmentSection);   */

        layout.addSection(new String[] {getLabel(LUConstants.REQUISITES_LABEL_KEY)}, section);
    }

    private static void addActiveDatesSection(ConfigurableLayout layout) {
        VerticalSectionView section = new VerticalSectionView(LuSections.ACTIVE_DATES,
                getLabel(LUConstants.ACTIVE_DATES_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(getLabel(LUConstants.ACTIVE_DATES_LABEL_KEY)));

        VerticalSection startDate = new VerticalSection();
        startDate.setSectionTitle(SectionTitle.generateH5Title("Start Date"));
        startDate.addField(new FieldDescriptor("cluInfo/effectiveDate", "When will this course be active?", Type.DATE, new KSDatePicker()));

        VerticalSection endDate = new VerticalSection();
        endDate.setSectionTitle(SectionTitle.generateH5Title("End Date"));
        endDate.addField(new FieldDescriptor("cluInfo/expirationDate", "When will this course become inactive?", Type.DATE, new KSDatePicker()));

        section.addSection(startDate);
        section.addSection(endDate);

        layout.addSection(new String[] {getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, section);
    }

    private static void addFinancialsSection(ConfigurableLayout layout) {
        VerticalSectionView section = new VerticalSectionView(LuSections.FINANCIALS,
                getLabel(LUConstants.FINANCIALS_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(getLabel(LUConstants.FINANCIALS_LABEL_KEY)));

        //TODO ALL KEYS in this section are place holders until we know actual keys
        VerticalSection feeType = new VerticalSection();
        feeType.setSectionTitle(SectionTitle.generateH2Title("Fee Type"));
        feeType.addField(new FieldDescriptor("cluInfo/feeType", null, Type.STRING));

        VerticalSection feeAmount = new VerticalSection();
        feeAmount.setSectionTitle(SectionTitle.generateH2Title("Fee Amount"));
        feeAmount.addField(new FieldDescriptor("cluInfo/feeAmount", "$", Type.STRING));
        feeAmount.addField(new FieldDescriptor("cluInfo/taxable", "Taxable", Type.STRING));//TODO checkboxes go here instead
        feeAmount.addField(new FieldDescriptor("cluInfo/feeDesc", "Description", Type.STRING, new KSTextArea()));
        feeAmount.addField(new FieldDescriptor("cluInfo/internalNotation", "Internal Fee Notation", Type.STRING, new KSTextArea()));

        section.addSection(feeType);
        section.addSection(feeAmount);
        layout.addSection(new String[] {getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, section);
    }

    private static void addProgramRequirements(ConfigurableLayout layout) {
        VerticalSectionView section = new VerticalSectionView(LuSections.PGM_REQUIREMENTS,
                getLabel(LUConstants.PROGRAM_REQUIREMENTS_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(getLabel(LUConstants.PROGRAM_REQUIREMENTS_LABEL_KEY)));

        layout.addSection(new String[] {getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, section);

    }

    public static void addGovernanceSection(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.GOVERNANCE,
                getLabel(LUConstants.GOVERNANCE_LABEL_KEY),
                CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(getLabel(LUConstants.GOVERNANCE_LABEL_KEY)));

        section.addField(new FieldDescriptor("cluInfo/academicSubjectOrgs", "Curriculum Oversight", Type.STRING, new OrgListPicker()));
        section.addField(new FieldDescriptor("cluInfo/campusLocationList", "Campus Location", Type.STRING, new CampusLocationList()));
        section.addField(new FieldDescriptor("cluInfo/adminOrg", "Administering Organization", Type.STRING, new OrgPicker()));
        section.addField(new FieldDescriptor("cluInfo/primaryInstructor/personId", "PrimaryInstructor Id", Type.STRING));

        layout.addSection(new String[] {getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, section);
    }

    public static void addCourseInfoSection(ConfigurableLayout layout, String objectKey, String typeKey, String stateKey){
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_INFO,
                getLabel(LUConstants.INFORMATION_LABEL_KEY),
                CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(getLabel(LUConstants.INFORMATION_LABEL_KEY)));

        //FIXME: Label should be key to messaging, field type should come from dictionary?


        //COURSE NUMBER
        CustomNestedSection courseNumber = new CustomNestedSection();
        courseNumber.setSectionTitle(SectionTitle.generateH5Title("Course Number")); //Section title constants)
        courseNumber.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
        courseNumber.addField(new FieldDescriptor("cluInfo/officialIdentifier/division", null, Type.STRING));//TODO OrgSearch goes here?
        courseNumber.addField(new FieldDescriptor("cluInfo/officialIdentifier/suffixCode", null, Type.STRING));
        courseNumber.nextRow();

            //CROSS LISTED
/*
            VerticalSection crossListed = new VerticalSection();
            crossListed.setSectionTitle(SectionTitle.generateH2Title("Cross Listed (offered under alternate course numbers)"));
            crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
            crossListed.addField(new FieldDescriptor("crossListClus", null, Type.LIST, new CrossListedList()));//TODO Key is probably wrong
*/
            //OFFERED JOINTLY
/*
            VerticalSection offeredJointly = new VerticalSection();
            offeredJointly.setSectionTitle(SectionTitle.generateH2Title("Offered Jointly (co-located with another course)"));
            offeredJointly.setInstructions("Enter an existing course or proposal.");
            offeredJointly.addField(new FieldDescriptor("jointClus", null, Type.LIST, new OfferedJointlyList()));//TODO Key is probably wrong
*/

            //Version Codes
/*
        VerticalSection versionCodes = new VerticalSection();
            versionCodes.setSectionTitle(SectionTitle.generateH2Title("Version Codes"));
            versionCodes.addField(new FieldDescriptor("luLuRelationType.alias", null, Type.LIST, new VersionCodeList()));//TODO Key is probably wrong
*/

/*
        courseNumber.addSection(crossListed);
        courseNumber.nextRow();
        courseNumber.addSection(offeredJointly);
        courseNumber.nextRow();
        courseNumber.addSection(versionCodes);
*/
        section.addSection(courseNumber);

        KSTextArea textArea = new KSTextArea();
        textArea.setWidth("50");
        FieldDescriptor fd = new FieldDescriptor("cluInfo/officialIdentifier/longName", "Proposed Course Title", Type.STRING);
//        Callback<Boolean> callback =  getSubjectValidationCallback(fd,objectKey);
  //      fd.setValidationCallBack(callback);
        section.addField(fd);
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/shortName", "Transcript Title", Type.STRING));
        section.addField(new FieldDescriptor("cluInfo/desc", "Course Description", Type.MODELDTO, new KSRichEditor()));
        section.addField(new FieldDescriptor("cluInfo/marketingDesc", "Marketing Description", Type.MODELDTO, new KSRichEditor()));

        layout.addSection(new String[] {getLabel(LUConstants.INFORMATION_LABEL_KEY)}, section);
    }

//    public static Callback<Boolean> getSubjectValidationCallback(final FieldDescriptor subjectFD, final String objectKey){
  //      return new Callback<Boolean>() {
    //        @Override
      //      public void exec(Boolean result) {
        //        ModelDTOConstraintSetupFactory bc = new ModelDTOConstraintSetupFactory();
          //      final Validator val = new Validator(bc, true);
            //    final ValidateResultEvent e = new ValidateResultEvent();
              //          ObjectStructure objStructure = Application.getApplicationContext().getDictionaryData(objectKey);
                //        if(objStructure == null){
                  //          Window.alert("Cannot load dictionary(object structure)");
                    //    }
                      //  List<ValidationResultContainer> results = val.validateTypeStateObject((ModelDTO) m.get(), objStructure);
                        //e.setValidationResult(results);// filled by calling the real validate code
                        //Controller.findController(subjectFD.getFieldWidget()).fireApplicationEvent(e);


//            }
  //      };
   // }
    public static class CrossListedList extends MultiplicityComposite{

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo");//TODO Probably totally wrong

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("cluInfo/department", "Department", Type.STRING));//TODO OrgSearch goes here, wrong key
            ns.nextRow();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("cluInfo/division", "Subject Code", Type.STRING));//TODO OrgSearch goes here?
            ns.addField(new FieldDescriptor("cluInfo/suffixCode", "Course Number", Type.STRING));
            multi.addSection(ns);

            return multi;
        }

    }

    public static class OfferedJointlyList extends MultiplicityComposite{

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo");//TODO Probably totally wrong

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("cluInfo/courseTitle", "Course Number or Title", Type.STRING));//TODO wrong key
            multi.addSection(ns);

            return multi;
        }

    }

    public static class VersionCodeList extends MultiplicityComposite{

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo");//TODO Probably totally wrong

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("cluInfo/versionCode", "Code", Type.STRING));//TODO wrong key
            ns.addField(new FieldDescriptor("cluInfo/title", "Title", Type.STRING));//TODO wrong key
            multi.addSection(ns);

            return multi;
        }

    }

    public static void addCourseLogistics(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_LOGISTICS,
                getLabel(LUConstants.LOGISTICS_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(getLabel(LUConstants.LOGISTICS_LABEL_KEY)));

        //CREDITS
        VerticalSection credits = new VerticalSection();
        credits.setSectionTitle(SectionTitle.generateH3Title("Credits"));
        //TODO: These needs to be mapped to learning results
        credits.addField(new FieldDescriptor("cluInfo/creditType", "Credit Type", Type.STRING));
        credits.addField(new FieldDescriptor("cluInfo/creditValue", "Credit Value", Type.STRING));
        credits.addField(new FieldDescriptor("cluInfo/maxCredits", "Maximum Credits", Type.STRING));

        //LEARNING RESULTS
        VerticalSection learningResults = new VerticalSection();
        learningResults.setSectionTitle(SectionTitle.generateH3Title("Learning Results"));
        learningResults.addField(new FieldDescriptor("cluInfo/evalType", "Evaluation Type", Type.STRING)); //TODO EVAL TYPE ENUMERATION ????

        VerticalSection scheduling = new VerticalSection();
        scheduling.setSectionTitle(SectionTitle.generateH3Title("Scheduling"));
        scheduling.addField(new FieldDescriptor("cluInfo/offeredAtpTypes", "Term", Type.STRING, new AtpTypeList()));
        scheduling.addField(new FieldDescriptor("cluInfo/stdDuration/timeQuantity", "Duration", Type.INTEGER)); //TODO DURATION ENUMERATION

        //COURSE FORMATS
        VerticalSection courseFormats = new VerticalSection();
        courseFormats.setSectionTitle(SectionTitle.generateH3Title("Course Formats"));
        courseFormats.addField(new FieldDescriptor("courseFormats", null, Type.LIST, new CourseFormatList()));

        section.addSection(credits);
        section.addSection(learningResults);
        section.addSection(scheduling);
        section.addSection(courseFormats);

        layout.addSection(new String[] {getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, section);
    }

    public static class CourseFormatList extends MultiplicityComposite{
        {
            setAddItemLabel("Add Additional Format");
            setItemLabel("Course Format");
        }

        public Widget createItem() {
            MultiplicitySection item = new MultiplicitySection("CluInfo");
            //TODO: Do we need ability to add hidden fields, so key/value pairs can be set into ModelDTO
            //e.g. addHiddenField("type", "kuali.lu.type.CreditCourseFormatShell");

            item.addField(new FieldDescriptor("activities", null, Type.LIST, new CourseActivityList()));
            return new CourseActivityList();
        }
    }

    public static class CourseActivityList extends MultiplicityComposite{

        {
            setAddItemLabel("Add Activity");
            setItemLabel("Activity");
        }

        public Widget createItem() {
            MultiplicitySection item = new MultiplicitySection("CluInfo");
            CustomNestedSection activity = new CustomNestedSection();
            activity.addField(new FieldDescriptor("cluInfo/type", "Activity Type", Type.STRING, new CluActivityType()));
            activity.nextRow();

            /* CreditInfo is deprecated, needs to be replaced with learning results
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("creditInfo", "Credit Value", Type.STRING));
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("evalType", "Learning Result", Type.STRING));
            activity.nextRow();
            */

            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("cluInfo/term", "Term", Type.STRING, new AtpTypeList()));
            activity.addField(new FieldDescriptor("cluInfo/stdDuration/timeQuantity", "Duration", Type.INTEGER)); //TODO dropdown need here?
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("cluInfo/intensity/timeQuantity", "Contact Hours", Type.STRING));
            //TODO PER WHATEVER
            activity.addField(new FieldDescriptor("defaultEnrollmentEstimate", "Class Size", Type.STRING));

            item.addSection(activity);

            return item;
        }

    }

    // TODO look up field label and type from dictionary & messages

//    private static Type translateDictType(String dictType) {
//        if (dictType.equalsIgnoreCase("String"))
//            return Type.STRING;
//        else
//            return null;
//    }
//
//    private static FieldDescriptor createMVCFieldDescriptor(String fieldName,
//            String objectKey, String type, String state  ) {
//
//        Field f = LUDictionaryManager.getInstance().getField(objectKey, type, state, fieldName);
//
//        FieldDescriptor fd =
//            new FieldDescriptor(f.getKey(),
//                    getLabel(type, state, f.getKey() ),
//                    translateDictType(f.getFieldDescriptor().getDataType())
//            );
//        return fd;
//    }
//
//
//    private static String getLabel(String type, String state, String fieldId) {
//        String labelKey = type + ":" + state + ":" + fieldId;
//        System.out.println(labelKey);
//        return context.getMessage(labelKey);
//    }
//

    //FIXME: This is a temp widget impl for the Curriculum Oversight field. Don't yet know if this
    //will be a multiple org select field, in which case we need a multiple org select picker widget.
    //Otherwise if it's single org picker, need a way to bind a HasText widget to ModelDTOList
    public static class OrgListPicker extends KSSelectItemWidgetAbstract{
        private OrgPicker orgPicker;

        public OrgListPicker(){
            orgPicker = new OrgPicker();
            initWidget(orgPicker);
        }

        public void deSelectItem(String id) {
            throw new UnsupportedOperationException();
        }

        public List<String> getSelectedItems() {
            ArrayList<String> selectedItems = new ArrayList<String>();
            selectedItems.add(orgPicker.getValue());
            return selectedItems;
        }

        public boolean isEnabled() {
            return true;
        }

        public void onLoad() {
        }

        public void redraw() {
            throw new UnsupportedOperationException();
        }

        public void selectItem(String id) {
            orgPicker.setValue(id);
        }

        public void setEnabled(boolean b) {
            throw new UnsupportedOperationException();
        }

        public boolean isMultipleSelect(){
            return true;
        }
    }

    //FIXME: Create a configurable checkbox list which can obtain values via RPC calls
    public static class CampusLocationList extends KSCheckBoxList{
        public CampusLocationList(){
            SimpleListItems campusLocations = new SimpleListItems();

            campusLocations.addItem("North County Campus", "North County Campus");
            campusLocations.addItem("South County Campus", "South County Campus");
            campusLocations.addItem("Extended Studies Campus", "Extended Studies Campus");
            campusLocations.addItem("All Campuses", "All Campuses");

            super.setListItems(campusLocations);
        }
    }

    //FIXME: Create a configurable drop down list which can obtain values via RPC calls
    public static class CluActivityType extends KSDropDown{
        public CluActivityType(){
            SimpleListItems activityTypes = new SimpleListItems();

            activityTypes.addItem("kuali.lu.type.activity.Tutorial", "Tutorial");
            activityTypes.addItem("kuali.lu.type.activity.Lecture", "Lecture");
            activityTypes.addItem("kuali.lu.type.activity.WebLecture", "WebLecture");
            activityTypes.addItem("kuali.lu.type.activity.Discussion", "Discussion");
            activityTypes.addItem("kuali.lu.type.activity.Lab", "Lab");
            activityTypes.addItem("kuali.lu.type.activity.Directed", "Directed");
            activityTypes.addItem("kuali.lu.type.activity.WebDiscuss", "WebDiscuss");

            super.setListItems(activityTypes);
        }
    }

    //FIXME: Is this what should be part of the term field
    public static class AtpTypeList extends KSDropDown{
        public AtpTypeList(){
            SimpleListItems activityTypes = new SimpleListItems();

            activityTypes.addItem("atpType.semester.fall", "Fall");
            activityTypes.addItem("atpType.semester.spring", "Spring");
            activityTypes.addItem("atpType.semester.summer", "Summer");
            activityTypes.addItem("atpType.semester.winter", "Winter");

            super.setListItems(activityTypes);
        }
    }

    //FIXME: This needs to be a proper person picker (or some other widget person entry widget)
    public static class PersonList extends KSDropDown{
        public PersonList(){
            SimpleListItems people = new SimpleListItems();

            String userId = Application.getApplicationContext().getUserId();
            people.addItem(userId, userId);
            people.addItem("altPerson", "Jon Doe");

            super.setListItems(people);

            this.selectItem(userId);
        }

        //FIXME: This is a hack, since field is a list, but wireframe allows only single select
        public boolean isMultipleSelect(){
            return true;
        }
    }

    public static class CollaboratorTool extends ToolView{
        public CollaboratorTool(){
            super(LuSections.AUTHOR, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS);
        }

        @Override
        protected Widget createWidget() {
            return new Collaborators();
        }

    }
    
    private static String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(type, state, labelKey);
    }
}
