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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.CustomNestedSection;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValueBinding;
import org.kuali.student.common.ui.client.configurable.mvc.Section;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.configurable.mvc.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.RemovableItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.UpdatableMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentPanel;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.common.ui.client.widgets.list.HasSelectionChangeHandlers;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.search.AdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.client.widgets.search.SuggestBoxWAdvSearch;
import org.kuali.student.common.ui.client.widgets.selectors.KSSearchComponent;
import org.kuali.student.common.ui.client.widgets.selectors.SearchComponentConfiguration;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.SuggestPicker;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityContactHoursConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseMetadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.removeinm4.LOBuilderBinding;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseRequisitesSectionView;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.LuConfigurer.LuSections;
import org.kuali.student.lum.lu.ui.course.client.configuration.viewclu.ViewCluConfigurer;
import org.kuali.student.lum.lu.ui.course.client.service.AtpRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.AtpRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.widgets.AssemblerTestSection;
import org.kuali.student.lum.lu.ui.course.client.widgets.AtpPicker;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;
import org.kuali.student.lum.lu.ui.course.client.widgets.LOBuilder;
import org.kuali.student.lum.lu.ui.course.client.widgets.OfferedJointlyList;
import org.kuali.student.lum.lu.ui.course.client.widgets.OrgPicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for creating a proposal.
 *
 * TODO: The following is a list of items that need to be fixed.
 * 	1) All hardcoded drop downs need to be replaced with one populated via an enumeration lookup
 *  2) Any pickers (eg. org, course, needs to be replaced wtih proper lookup based search pickers
 *  
 * @author Kuali Student Team
 *
 */
public class CourseConfigurer 
 implements CreditCourseProposalConstants,
 CreditCourseProposalInfoConstants,
 CreditCourseConstants,
 CreditCourseFormatConstants,
 CreditCourseActivityConstants,
 MetaInfoConstants,
 CreditCourseDurationConstants,
 FeeInfoConstants,
 LearningObjectiveConstants
{

    //FIXME:  Initialize type and state
    private String type = "course";
    private String state = "draft";
    private String groupName;

    private boolean WITH_DIVIDER = true;
    private boolean NO_DIVIDER = false;

    public static final String CLU_PROPOSAL_MODEL = "cluProposalModel";

    private DataModelDefinition modelDefinition;
    
    public void setModelDefinition(DataModelDefinition modelDefinition){
    	this.modelDefinition = modelDefinition;
    }
    
    public void configureCourseProposal(ConfigurableLayout layout) {
    	groupName = LUConstants.COURSE_GROUP_NAME;
    	
        addCluStartSection(layout);

        layout.addSection(new String[] {"Edit Proposal", getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, generateGovernanceSection());
        layout.addSection(new String[] {"Edit Proposal", getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, generateCourseLogisticsSection());
        layout.addSection(new String[] {"Edit Proposal", getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateCourseInfoSection());
        layout.addSection(new String[] {"Edit Proposal", getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateLearningObjectivesSection());
        layout.addSection(new String[] {"Edit Proposal", getLabel(LUConstants.STUDENT_ELIGIBILITY_LABEL_KEY)}, generateCourseRequisitesSection());
        layout.addSection(new String[] {"Edit Proposal", getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateActiveDatesSection());
        layout.addSection(new String[] {"Edit Proposal", getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateFinancialsSection());   
        layout.addSection(new String[] {getLabel(LUConstants.SUMMARY_LABEL_KEY)}, generateSummarySection());
        layout.addSection(new String[] {"Assembler Test"}, new AssemblerTestSection(LuSections.ASSEMBLER_TEST, "Assembler Test"));
        
        layout.addTool(new CollaboratorTool());
        layout.addTool(new CommentPanel(LuSections.COMMENTS, LUConstants.TOOL_COMMENTS));
        layout.addTool(new DocumentTool(LuSections.DOCUMENTS, LUConstants.TOOL_DOCUMENTS));
    }

    public SectionView generateSummarySection(){
        VerticalSectionView section = initSectionView(LuSections.SUMMARY, LUConstants.SUMMARY_LABEL_KEY); 
            
        section.addSection(generateSummaryBrief(getH3Title(LUConstants.BRIEF_LABEL_KEY)));
        section.addSection(ViewCluConfigurer.generateSummaryDetails(getH3Title(LUConstants.FULL_VIEW_LABEL_KEY)));
        
        return section;
    }
    
    private VerticalSection generateSummaryBrief(SectionTitle title) {
        VerticalSection section = new VerticalSection();
        section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(title);
        addField(section, PROPOSAL + "/" + TITLE, getLabel(LUConstants.TITLE_LABEL_KEY) +":    ", new KSLabel());
        addField(section, COURSE + "/" + SUBJECT_AREA, getLabel(LUConstants.DIVISION_LABEL_KEY), new KSLabel());
        addField(section, COURSE + "/" + COURSE_NUMBER_SUFFIX, getLabel(LUConstants.SUFFIX_CODE_LABEL_KEY), new KSLabel());
        
        
        // FIXME wilj: add proposal/delegate/collab person info to assembly
        addField(section, PROPOSAL + "/" + PROPOSER_PERSON, getLabel(LUConstants.PROPOSER_LABEL_KEY), new ProposerPersonList());
        addField(section, "proposalInfo/todo", getLabel(LUConstants.DELEGATE_LABEL_KEY), new KSLabel());
        addField(section, "proposalInfo/todo", getLabel(LUConstants.COLLABORATORS_LABEL_KEY), new KSLabel());
        
        // FIXME wilj: add create/update meta info to assembly
        addField(section, PROPOSAL + "/" + META_INFO + "/" + CREATE_TIME, getLabel(LUConstants.CREATED_DATE_LABEL_KEY), new KSLabel());
        addField(section, PROPOSAL + "/" + META_INFO + "/" + UPDATE_TIME, getLabel(LUConstants.LAST_CHANGED_DATE_LABEL_KEY), new KSLabel());
        
        addField(section, COURSE + "/" + DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, getLabel(LUConstants.DESCRIPTION_LABEL_LABEL_KEY), new KSLabel());
       // TODO: Norm: find out why was this prefixed with proposal there is no state on proposal it is on the main object
        addField(section, CreditCourseProposalConstants.STATE, getLabel(LUConstants.STATUS_LABEL_KEY), new KSLabel());
        return section;
    } 
    
    public void addCluStartSection(ConfigurableLayout layout){
        VerticalSectionView section = initSectionView(LuSections.CLU_BEGIN, LUConstants.START_LABEL_KEY); 

        addField(section, PROPOSAL + "/" + TITLE , getLabel(LUConstants.PROPOSAL_TITLE_LABEL_KEY));
        //This will need to be a person picker
        // FIXME wilj: add proposal/delegate/collab person info to assembly
        addField(section, PROPOSAL + "/" + PROPOSER_PERSON, getLabel(LUConstants.PROPOSAL_PERSON_LABEL_KEY), new PersonList()) ;
        layout.addStartSection(section);
    }

    /**
     * Adds a section for adding or modifying rules associated with a given course (program).
     *
     * @param layout - a content pane to which this section is added to
     * @return
     */
    private SectionView generateCourseRequisitesSection() {
        CourseRequisitesSectionView section = new CourseRequisitesSectionView(LuSections.COURSE_REQUISITES, getLabel(LUConstants.REQUISITES_LABEL_KEY));
        section.setSectionTitle(SectionTitle.generateH1Title(getLabel(LUConstants.REQUISITES_LABEL_KEY)));        
        return section;
    }

    private SectionView generateActiveDatesSection() {
        VerticalSectionView section = initSectionView(LuSections.ACTIVE_DATES, LUConstants.ACTIVE_DATES_LABEL_KEY); 

        VerticalSection startDate = initSection(getH3Title(LUConstants.START_DATE_LABEL_KEY), WITH_DIVIDER);
        addField(startDate, COURSE + "/" + EFFECTIVE_DATE, getLabel(LUConstants.EFFECTIVE_DATE_LABEL_KEY));

        VerticalSection endDate = initSection(getH3Title(LUConstants.END_DATE_LABEL_KEY), WITH_DIVIDER);
        addField(endDate, COURSE + "/" + EXPIRATION_DATE, getLabel(LUConstants.EXPIRATION_DATE_LABEL_KEY));

        
        section.addSection(startDate);
        section.addSection(endDate);
        
        return section;
    }

    private SectionView generateFinancialsSection() {
        VerticalSectionView section = initSectionView(LuSections.FINANCIALS, LUConstants.FINANCIALS_LABEL_KEY); 

        //TODO ALL KEYS in this section are place holders until we know actual keys
        VerticalSection feeType = initSection(getH3Title(LUConstants.FEE_TYPE_LABEL_KEY), WITH_DIVIDER);
        addField(feeType, "cluInfo/feeType", null);

        VerticalSection feeAmount = initSection(getH3Title(LUConstants.FEE_DESC_LABEL_KEY), WITH_DIVIDER);
        addField(feeAmount, FEES +"/" + FEE_AMOUNT, getLabel(LUConstants.CURRENCY_SYMBOL_LABEL_KEY));
        addField(feeAmount, FEES + "/" + TAXABLE, getLabel(LUConstants.TAXABLE_SYMBOL_LABEL_KEY));//TODO checkboxes go here instead
        addField(feeAmount, FEES + "/" + FEE_DESC, getLabel(LUConstants.FEE_DESC_LABEL_KEY));
        addField(feeAmount, FEES + "/" + INTERNAL_NOTATION, getLabel(LUConstants.INTERNAL_FEE_NOTIFICATION_LABEL_KEY));

        section.addSection(feeType);
        section.addSection(feeAmount);
        
        return section;
    }

    public SectionView generateGovernanceSection(){
        VerticalSectionView section = initSectionView(LuSections.GOVERNANCE, LUConstants.GOVERNANCE_LABEL_KEY); 

        VerticalSection oversight = initSection(getH3Title(LUConstants.CURRICULUM_OVERSIGHT_LABEL_KEY), WITH_DIVIDER);    
        addField(oversight, COURSE + "/" + ACADEMIC_SUBJECT_ORGS, null, new OrgListPicker());

        VerticalSection campus = initSection(getH3Title(LUConstants.CAMPUS_LOCATION_LABEL_KEY), WITH_DIVIDER);    
        addField(campus, COURSE + "/" + CAMPUS_LOCATIONS, null, new CampusLocationList());

        VerticalSection adminOrgs = initSection(getH3Title(LUConstants.ADMIN_ORG_LABEL_KEY), WITH_DIVIDER);                    
        addField(adminOrgs, COURSE + "/" + DEPARTMENT, null);
        
        section.addSection(oversight);
        section.addSection(campus);
        section.addSection(adminOrgs);
        
        return section;
    }

    public SectionView generateCourseInfoSection(){
        VerticalSectionView section = initSectionView(LuSections.COURSE_INFO, LUConstants.INFORMATION_LABEL_KEY); 

        //FIXME: Label should be key to messaging, field type should come from dictionary?

        //COURSE NUMBER
        CustomNestedSection courseNumber = new CustomNestedSection();
        courseNumber.addStyleName(LUConstants.STYLE_SECTION);
        courseNumber.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        courseNumber.setSectionTitle(getH3Title(LUConstants.IDENTIFIER_LABEL_KEY)); 
        courseNumber.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
        addField(courseNumber, COURSE + "/" + SUBJECT_AREA, null);
        addField(courseNumber, COURSE + "/" + COURSE_NUMBER_SUFFIX, null);
        
        // TODO - hide cross-listed, offered jointly and version codes initially, with
        // clickable label to disclose them
        
        // Cross-listed
        VerticalSection crossListed = new VerticalSection();
        crossListed.setSectionTitle(getH3Title(LUConstants.CROSS_LISTED_ALT_LABEL_KEY));
        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
        addField(crossListed, COURSE + "/" + CROSS_LISTINGS, null, new CrossListedList());
        crossListed.addStyleName("KS-LUM-Section-Divider");
        courseNumber.addSection(crossListed);

        // Offered jointly
        VerticalSection offeredJointly = new VerticalSection();
        offeredJointly.setSectionTitle(getH3Title(LUConstants.JOINT_OFFERINGS_ALT_LABEL_KEY));
        addField(offeredJointly, COURSE + "/" + JOINTS, null, new OfferedJointlyList());
        offeredJointly.addStyleName("KS-LUM-Section-Divider");
        courseNumber.addSection(offeredJointly);

        //Version Codes
        VerticalSection versionCodes = new VerticalSection();
        versionCodes.setSectionTitle(getH3Title(LUConstants.VERSION_CODES_LABEL_KEY));
        addField(versionCodes, COURSE + "/" + VERSIONS, null, new VersionCodeList());
        versionCodes.addStyleName("KS-LUM-Section-Divider");
        courseNumber.addSection(versionCodes);

        VerticalSection longTitle = initSection(getH3Title(LUConstants.TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(longTitle, COURSE + "/" + COURSE_TITLE, null);
        
        VerticalSection shortTitle = initSection(getH3Title(LUConstants.SHORT_TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(shortTitle, COURSE + "/" + TRANSCRIPT_TITLE, null);
        
        VerticalSection description = initSection(getH3Title(LUConstants.DESCRIPTION_LABEL_KEY), WITH_DIVIDER);
        addField(description, COURSE + "/" + DESCRIPTION, null);
        
        VerticalSection rationale = initSection(getH3Title(LUConstants.RATIONALE_LABEL_KEY), WITH_DIVIDER);
        addField(rationale, PROPOSAL + "/" + RATIONALE, null);
        
        section.addSection(courseNumber);
        section.addSection(shortTitle);
        section.addSection(longTitle);
        section.addSection(description);
        section.addSection(rationale);

        return section;
    }

//    public Callback<Boolean> getSubjectValidationCallback(final FieldDescriptor subjectFD, final String objectKey){
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
    

        
    public SectionView generateCourseLogisticsSection() {
        VerticalSectionView section = initSectionView(LuSections.COURSE_LOGISTICS, LUConstants.LOGISTICS_LABEL_KEY); 

        VerticalSection instructors = initSection(getH3Title(LUConstants.INSTRUCTOR_LABEL_KEY), WITH_DIVIDER);
        // FIXME wilj: do we need to set the instructor's orgId? or can we default it at the assembler level?
        addField(instructors, COURSE + "/" + PRIMARY_INSTRUCTOR, null);
//      instructors.addField(new FieldDescriptor("cluInfo/instructors", null, Type.LIST, new AlternateInstructorList()));

        //CREDITS
        //TODO: These needs to be mapped to learning results
//        VerticalSection credits = initSection(getH3Title(LUConstants.CREDITS_LABEL_KEY), WITH_DIVIDER);
//        credits.addField(new FieldDescriptor("cluInfo/creditType", getLabel(LUConstants.CREDIT_LABEL_KEY), Type.STRING));
//        credits.addField(new FieldDescriptor("cluInfo/creditValue", getLabel(LUConstants.CREDIT_VALUE_LABEL_KEY), Type.STRING));
//        credits.addField(new FieldDescriptor("cluInfo/maxCredits", getLabel(LUConstants.MAX_CREDITS_LABEL_KEY), Type.STRING));
//        VerticalSection learningResults = initSection(getH3Title(LUConstants.LEARNING_RESULTS_LABEL_KEY), WITH_DIVIDER);
//        learningResults.addField(new FieldDescriptor("cluInfo/evalType", getLabel(LUConstants.EVALUATION_TYPE_LABEL_KEY), Type.STRING)); //TODO EVAL TYPE ENUMERATION ????

        VerticalSection scheduling = initSection(getH3Title(LUConstants.SCHEDULING_LABEL_KEY), WITH_DIVIDER);
        // FIXME wilj: termsOffered not currently populated by assembler
        
//        addField(scheduling, COURSE + "/" + CreditCourseConstants.DURATION + "/" + TERM_TYPE, getLabel(LUConstants.TERM_LITERAL_LABEL_KEY), new AtpTypeList());
        String termKey = COURSE + "/" + CreditCourseConstants.DURATION + "/" + TERM_TYPE;
        Metadata termMeta = modelDefinition.getMetadata(QueryPath.parse(termKey));
        FieldDescriptor termDescriptor = new FieldDescriptor(termKey, null, termMeta);
        termDescriptor.setFieldWidget(new TermListPicker());
        final QueryPath deptPath = QueryPath.parse(termDescriptor.getFieldKey());
        termDescriptor.setWidgetBinding(new ModelWidgetBinding<TermListPicker>() {
            // FIXME had to create custom binding because the TermListPicker always returns a list, even of 1
            @Override
            public void setModelValue(TermListPicker widget, DataModel model,
                    String path) {
                model.set(deptPath, widget.getSelectedItem());
                
            }
            @Override
            public void setWidgetValue(TermListPicker widget, DataModel model,
                    String path) {
                widget.setText((String) model.get(path));
            }
        });
        scheduling.addField(termDescriptor);
        addField(scheduling, COURSE + "/" + CreditCourseConstants.DURATION + "/" + QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY)); //TODO DURATION ENUMERATION

        //COURSE FORMATS
        VerticalSection courseFormats = initSection(getH3Title(LUConstants.FORMATS_LABEL_KEY), WITH_DIVIDER);
        addField(courseFormats, COURSE + "/" + FORMATS, null, new CourseFormatList(COURSE + "/" + FORMATS));

        section.addSection(instructors);
//        section.addSection(credits);
//        section.addSection(learningResults);
        section.addSection(scheduling);
        section.addSection(courseFormats);

        
        return section;
    }

    private static AdvancedSearchWindow createAtpSearchWindow(){

        Metadata searchMetadata = new CreditCourseMetadata().getMetadata("", "");  //no type or state at this point
        SearchPanel searchPicker = new SearchPanel(searchMetadata.getProperties().get("firstExpectedOffering").getLookupMetadata());
        final AdvancedSearchWindow atpSearchWindow = new AdvancedSearchWindow("Find Session", searchPicker);

//        atpSearchWindow.addSelectionCompleteCallback(new Callback<List<String>>(){
//            public void exec(List<String> event) {
//                final String selected = event.get(0);
//                if (selected.length() > 0){
////                  List<String> selectedItems = event;
////                  ChangeViewStateEvent tempEvent = new ChangeViewStateEvent(LUMViews.VIEW_COURSE, selectedItems);
////                  FindPanel.this.getController().fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.VIEW_COURSE, event));
//                    atpSearchWindow.hide();
//                }                
//            }            
//        });
        return atpSearchWindow;
    }

    private SectionView generateLearningObjectivesSection() {
        VerticalSectionView section = initSectionView(LuSections.LEARNING_OBJECTIVES, LUConstants.LEARNING_OBJECTIVES_LABEL_KEY); 

        VerticalSection los = initSection(null, NO_DIVIDER);    
        FieldDescriptor fd = addField(los,
        								CreditCourseConstants.COURSE_SPECIFIC_L_OS,
        								null,
        								new LOBuilder(type, state, groupName),
        								CreditCourseProposalConstants.COURSE);
        
        // have to do this here, because decision on binding is done in ks-core,
        // and we obviously don't want ks-core referring to LOBuilder
        fd.setWidgetBinding(LOBuilderBinding.INSTANCE);
        
        los.addStyleName("KS-LUM-Section-Divider");
        
        section.addSection(los);
        return section;        
    }
    
    public class CourseFormatList extends UpdatableMultiplicityComposite {
    	private final String parentPath;
        public CourseFormatList(String parentPath){
        	this.parentPath = parentPath;
            setAddItemLabel(getLabel(LUConstants.COURSE_ADD_FORMAT_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.FORMAT_LABEL_KEY));
        }

        public Widget createItem() {
        	VerticalSection item = new VerticalSection();
            addField(item, ACTIVITIES, null, new CourseActivityList(QueryPath.concat(parentPath, String.valueOf(itemCount-1), ACTIVITIES).toString()), parentPath);
            return item;
        }
    }

    public class CourseActivityList extends UpdatableMultiplicityComposite {

    	private final String parentPath;
        public CourseActivityList(String parentPath){
        	this.parentPath = parentPath;
            setAddItemLabel(getLabel(LUConstants.ADD_ACTIVITY_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.ACTIVITY_LITERAL_LABEL_KEY));
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            CustomNestedSection activity = new CustomNestedSection();
            addField(activity, ACTIVITY_TYPE, getLabel(LUConstants.ACTIVITY_TYPE_LABEL_KEY), new CluActivityType(), parentPath);
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
            // FIXME need to get the term offered added to the activity metadata?
//            activity.addField(new FieldDescriptor("term", getLabel(LUConstants.TERM_LITERAL_LABEL_KEY), Type.STRING, new AtpTypeList())); 
            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), path);
            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.TIME_UNIT, "Duration Type", new DurationAtpTypeList(), path);

            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.HRS, "Contact Hours" , path);
            // FIXME look up what the label and implement as dropdown
            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.PER, null,  new ContactHoursAtpTypeList(), path);
            addField(activity, DEFAULT_ENROLLMENT_ESTIMATE, getLabel(LUConstants.CLASS_SIZE_LABEL_KEY), path);

            return activity;
        }

    }

    // TODO look up field label and type from dictionary & messages

//    private Type translateDictType(String dictType) {
//        if (dictType.equalsIgnoreCase("String"))
//            return Type.STRING;
//        else
//            return null;
//    }
//
//    private FieldDescriptor createMVCFieldDescriptor(String fieldName,
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
//    private String getLabel(String type, String state, String fieldId) {
//        String labelKey = type + ":" + state + ":" + fieldId;
//        System.out.println(labelKey);
//        return context.getMessage(labelKey);
//    }
//

    //FIXME: This is a temp widget impl for the Curriculum Oversight field. Don't yet know if this
    //will be a multiple org select field, in which case we need a multiple org select picker widget.
    //Otherwise if it's single org picker, need a way to bind a HasText widget to ModelDTOList
    public class OrgListPicker extends KSSelectItemWidgetAbstract implements SuggestPicker{
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
        
        public void clear(){
            orgPicker.clear();
        }

		@Override
		public HandlerRegistration addFocusHandler(FocusHandler handler) {
			return orgPicker.addFocusHandler(handler);
		}

		@Override
		public HandlerRegistration addBlurHandler(BlurHandler handler) {
			return orgPicker.addBlurHandler(handler);
		}

		@Override
		public String getValue() {
			return orgPicker.getValue();
		}

		@Override
		public void setValue(String value) {
			orgPicker.setValue(value);
			
		}

		@Override
		public void setValue(String value, boolean fireEvents) {
			orgPicker.setValue(value, fireEvents);
			
		}

		@Override
		public HandlerRegistration addValueChangeHandler(
				ValueChangeHandler<String> handler) {
			return orgPicker.addValueChangeHandler(handler);
		}
		
		
		@Override
		public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler handler){
			return orgPicker.addSelectionChangeHandler(handler);
		}

    }
    
    public class TermListPicker extends KSSelectItemWidgetAbstract implements HasText {
        private AtpPicker atpPicker;

        public TermListPicker(){
            atpPicker = new AtpPicker();
            initWidget(atpPicker);
        }

        public void deSelectItem(String id) {
            throw new UnsupportedOperationException();
        }

        public List<String> getSelectedItems() {
            ArrayList<String> selectedItems = new ArrayList<String>();
            selectedItems.add(atpPicker.getText());
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
            atpPicker.setText(id);
        }

        public void setEnabled(boolean b) {
            throw new UnsupportedOperationException();
        }

        public boolean isMultipleSelect(){
            return true;
        }
        
        public void clear(){
            atpPicker.clear();
        }

        @Override
        public HandlerRegistration addFocusHandler(FocusHandler handler) {
            return atpPicker.addFocusHandler(handler);
        }

        @Override
        public HandlerRegistration addBlurHandler(BlurHandler handler) {
            return atpPicker.addBlurHandler(handler);
        }

        @Override
        public String getText() {
            return atpPicker.getText();
        }

        @Override
        public void setText(String value) {
            atpPicker.setText(value);
            
        }

    }
    
    
    // FIXME uncomment and fix AlternateAdminOrgList and AlternateInstructorList
//    public class AlternateAdminOrgList extends MultiplicityCompositeWithLabels {
//        {
//            setAddItemLabel("Add an Alternate Admin Organization");
//            setItemLabel("Organization ID ");
//        }
//
//        @Override
//        public Widget createItem() {
//            MultiplicitySection multi = new MultiplicitySection("");
//            
//            CustomNestedSection ns = new CustomNestedSection();
//            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
//            ns.addField(new FieldDescriptor("orgId", "Organization ID", Type.STRING, new OrgPicker() ));
//            multi.addSection(ns);
//            
//            return multi;
//        }
//    }
//    
//    public class AlternateInstructorList extends MultiplicityCompositeWithLabels {
//        {
//            setAddItemLabel("Add an Alternate Instructor.");
//            setItemLabel("Instructor ID");
//        }
//
//        @Override
//        public Widget createItem() {
//            MultiplicitySection multi = new MultiplicitySection("");
//            
//            CustomNestedSection ns = new CustomNestedSection();
//            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
//            ns.addField(new FieldDescriptor("personId", "Instructor ID", Type.STRING /*, new InstructorPicker() */ ));
//            multi.addSection(ns);
//            
//            return multi;
//        }
//    }

    //FIXME: Create a configurable checkbox list which can obtain values via RPC calls
    public class CampusLocationList extends KSCheckBoxList{
        public CampusLocationList(){
            SimpleListItems campusLocations = new SimpleListItems();

            campusLocations.addItem("NorthCountyCampus", "North County Campus");
            campusLocations.addItem("SouthCountyCampus", "South County Campus");
            campusLocations.addItem("ExtendedStudiesCampus", "Extended Studies Campus");
            campusLocations.addItem("AllCampuses", "All Campuses");

            super.setListItems(campusLocations);
        }
    }

    public class DurationAtpTypeList extends KSDropDown{
        public DurationAtpTypeList(){
            SimpleListItems activityTypes = new SimpleListItems();

            activityTypes.addItem("atpType.duration.week", "Week");
            activityTypes.addItem("atpType.duration.month", "Month");
            activityTypes.addItem("atpType.semester.day", "Day");

            super.setListItems(activityTypes);
        }

    }

    public class ContactHoursAtpTypeList extends KSDropDown{
        public ContactHoursAtpTypeList(){
            SimpleListItems activityTypes = new SimpleListItems();

            activityTypes.addItem("atpType.duration.weekly", "per week");
            activityTypes.addItem("atpType.duration.monthly", "per month");
            activityTypes.addItem("atpType.semester.daily", "per day");

            super.setListItems(activityTypes);
        }

    }

    public class CluActivityType extends KSDropDown{
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
            this.selectItem("kuali.lu.type.activity.Lecture");
        }
    }

    public class AtpTypeList extends KSDropDown{
        public AtpTypeList(){
            SimpleListItems activityTypes = new SimpleListItems();

            activityTypes.addItem("atpType.semester.fall", "Fall");
            activityTypes.addItem("atpType.semester.spring", "Spring");
            activityTypes.addItem("atpType.semester.summer", "Summer");
            activityTypes.addItem("atpType.semester.winter", "Winter");

            super.setListItems(activityTypes);
        }

        public boolean isMultipleSelect(){
            return false;
        }
    }

    public class PersonList extends KSDropDown{
        public PersonList(){
            SimpleListItems people = new SimpleListItems();

            String userId = Application.getApplicationContext().getUserId();
            people.addItem(userId, userId);

            super.setListItems(people);
            this.selectItem(userId);
        }

        public boolean isMultipleSelect(){
            return true;
        }
    }

    public class ProposerPersonList extends KSLabelList {
        public ProposerPersonList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    public class CrossListedList extends UpdatableMultiplicityComposite {        
        {
            setAddItemLabel(getLabel(LUConstants.ADD_CROSS_LISTED_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.CROSS_LISTED_ITEM_LABEL_KEY));
        }
        
        @Override
        public MultiplicityItem getItemDecorator() {
            return new RemovableItem();
        }

        @Override
        public Widget createItem() {
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            addField(ns, DEPARTMENT, getLabel(LUConstants.DEPT_LABEL_KEY), new OrgPicker());
            ns.nextRow();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            addField(ns, SUBJECT_AREA, getLabel(LUConstants.SUBJECT_CODE_LABEL_KEY));
            addField(ns, COURSE_NUMBER_SUFFIX, getLabel(LUConstants.COURSE_NUMBER_LABEL_KEY));
                        
            return ns;
        }
    }
    
    
    
    public class VersionCodeList extends UpdatableMultiplicityComposite {
        {
            setAddItemLabel(getLabel(LUConstants.ADD_VERSION_CODE_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.VERSION_CODE_LABEL_KEY));
        }

        @Override
        public MultiplicityItem getItemDecorator() {
            return new RemovableItem();
        }

        @Override
        public Widget createItem() {
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            addField(ns, "versionCode", getLabel(LUConstants.CODE_LABEL_KEY));
            addField(ns, "versionTitle", getLabel(LUConstants.TITLE_LITERAL_LABEL_KEY));
            
            return ns;
        }
    }
    
    /*
     * Configuring Program specific screens.
     */
    public void configureProgramProposal(ConfigurableLayout layout, String objectKey, String typeKey, String stateKey) {
    	
    	groupName = LUConstants.PROGRAM_GROUP_NAME;
    	
        addCluStartSection(layout);

        layout.addSection(new String[] {getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateProgramInfoSection());       
        
        layout.addTool(new CollaboratorTool());
        layout.addTool(new CommentPanel(LuSections.COMMENTS, LUConstants.TOOL_COMMENTS));
        layout.addTool(new DocumentTool(LuSections.DOCUMENTS, LUConstants.TOOL_DOCUMENTS));
    }
    
    
    public SectionView generateProgramInfoSection(){
        VerticalSectionView section = initSectionView(LuSections.PROGRAM_INFO, LUConstants.INFORMATION_LABEL_KEY); 

        VerticalSection shortTitle = initSection(getH3Title(LUConstants.SHORT_TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(shortTitle, "cluInfo/officialIdentifier/shortName", null);       
        
        section.addSection(shortTitle);

        return section;
    }    
    
    public class CollaboratorTool extends ToolView{
        public CollaboratorTool(){
            super(LuSections.AUTHOR, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS);
        }

        @Override
        protected Widget createWidget() {
            return new Collaborators();
        }

    }
    
    private VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), CLU_PROPOSAL_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(getH1Title(labelKey));
        
        return section;
    }
    
    
    private VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection();
        if (title !=  null) {
          section.setSectionTitle(title);
        }
        section.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return section;
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
    
    // TODO - when DOL is pushed farther down into LOBuilder,
    // revert these 4 methods to returning void again.
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel) {
    	return addField(section, fieldKey, fieldLabel, null, null);
    }
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, Widget widget) {
    	return addField(section, fieldKey, fieldLabel, widget, null);
    }
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, String parentPath) {
        return addField(section, fieldKey, fieldLabel, null, parentPath);
    }
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
    	Metadata meta = modelDefinition.getMetadata(path);
    	
    	FieldDescriptor fd = new FieldDescriptor(path.toString(), fieldLabel, meta);
    	if (widget != null) {
    		fd.setFieldWidget(widget);
    	}
    	section.addField(fd);
    	return fd;
    }
}
