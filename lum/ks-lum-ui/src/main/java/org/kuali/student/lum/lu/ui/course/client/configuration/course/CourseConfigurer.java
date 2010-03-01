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

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.UpdatableMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.service.SearchRpcService;
import org.kuali.student.common.ui.client.service.SearchRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentPanel;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.search.AdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SortDirection;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityContactHoursConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCourseSpecificLOsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseMetadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.removeinm4.LOBuilderBinding;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseRequisitesSectionView;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.LuConfigurer.LuSections;
import org.kuali.student.lum.lu.ui.course.client.configuration.viewclu.ViewCluConfigurer;
import org.kuali.student.lum.lu.ui.course.client.widgets.AssemblerTestSection;
import org.kuali.student.lum.lu.ui.course.client.widgets.AtpPicker;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;
import org.kuali.student.lum.lu.ui.course.client.widgets.LOBuilder;
import org.kuali.student.lum.lu.ui.course.client.widgets.OfferedJointlyList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
        //layout.addSection(new String[] {"Edit Proposal", getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateFinancialsSection());
        layout.addSection(new String[] {getLabel(LUConstants.SUMMARY_LABEL_KEY)}, generateSummarySection());

        //layout.addSection(new String[] {"Assembler Test"}, new AssemblerTestSection(LuSections.ASSEMBLER_TEST, "Assembler Test"));

        layout.addTool(new CollaboratorTool());
        layout.addTool(new CommentPanel(LuSections.COMMENTS, LUConstants.TOOL_COMMENTS));
        layout.addTool(new DocumentTool(LuSections.DOCUMENTS, LUConstants.TOOL_DOCUMENTS));
    }

    public SectionView generateSummarySection(){
        VerticalSectionView section = initSectionView(LuSections.SUMMARY, LUConstants.SUMMARY_LABEL_KEY);

        section.addSection(generateSummaryBrief(getH3Title(LUConstants.BRIEF_LABEL_KEY)));
        section.addSection(generateSummaryDetails(getH3Title(LUConstants.FULL_VIEW_LABEL_KEY)));
        return section;
    }

    private VerticalSection generateSummaryDetails(SectionTitle title) {
       return  ViewCluConfigurer.generateSummaryDetails(title);
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
        addField(section, SEARCH + "/" + "findCourse");
        return section;
    }

    private SectionView generateActiveDatesSection() {
        VerticalSectionView section = initSectionView(LuSections.ACTIVE_DATES, LUConstants.ACTIVE_DATES_LABEL_KEY);

        section.addSection(generateActiveDateStartSection());
        section.addSection(generateActiveDateEndSection());

        return section;
    }

    private VerticalSection generateActiveDateEndSection() {
        VerticalSection endDate = initSection(getH3Title(LUConstants.END_DATE_LABEL_KEY), WITH_DIVIDER);
        addField(endDate, COURSE + "/" + EXPIRATION_DATE, getLabel(LUConstants.EXPIRATION_DATE_LABEL_KEY));
        return endDate;
	}

	private VerticalSection generateActiveDateStartSection() {
        VerticalSection startDate = initSection(getH3Title(LUConstants.START_DATE_LABEL_KEY), WITH_DIVIDER);
        addField(startDate, COURSE + "/" + EFFECTIVE_DATE, getLabel(LUConstants.EFFECTIVE_DATE_LABEL_KEY));
        return startDate;
	}

	private SectionView generateFinancialsSection() {
        VerticalSectionView section = initSectionView(LuSections.FINANCIALS, LUConstants.FINANCIALS_LABEL_KEY);

        //TODO ALL KEYS in this section are place holders until we know actual keys
        section.addSection(generateFeeTypeSection());
        section.addSection(generateFeeAmountSection());

        return section;
    }



    private VerticalSection generateFeeTypeSection() {
        //TODO ALL KEYS in this section are place holders until we know actual keys
        VerticalSection feeType = initSection(getH3Title(LUConstants.FEE_TYPE_LABEL_KEY), WITH_DIVIDER);
        addField(feeType, COURSE + "/" + FEES + "/" + FEE_TYPE, null);
        return feeType;
	}

	private VerticalSection generateFeeAmountSection() {
        //TODO ALL KEYS in this section are place holders until we know actual keys
        VerticalSection feeAmount = initSection(getH3Title(LUConstants.FEE_DESC_LABEL_KEY), WITH_DIVIDER);
        addField(feeAmount, COURSE + "/" + FEES + "/" + FEE_AMOUNT, getLabel(LUConstants.CURRENCY_SYMBOL_LABEL_KEY));
        addField(feeAmount, COURSE + "/" + FEES + "/" + TAXABLE, getLabel(LUConstants.TAXABLE_SYMBOL_LABEL_KEY));//TODO checkboxes go here instead
        addField(feeAmount, COURSE + "/" + FEES + "/" + FEE_DESC, getLabel(LUConstants.FEE_DESC_LABEL_KEY));
        addField(feeAmount, COURSE + "/" + FEES + "/" + INTERNAL_NOTATION, getLabel(LUConstants.INTERNAL_FEE_NOTIFICATION_LABEL_KEY));
		return feeAmount;
	}

	public SectionView generateGovernanceSection(){
        VerticalSectionView section = initSectionView(LuSections.GOVERNANCE, LUConstants.GOVERNANCE_LABEL_KEY);

        section.addSection(generateOversightSection());
        section.addSection(generateCampusSection());
        section.addSection(generateAdminOrgsSection());

        return section;
    }

    private VerticalSection generateOversightSection() {
        VerticalSection oversight = initSection(getH3Title(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY), WITH_DIVIDER);
        addField(oversight, COURSE + "/" + ACADEMIC_SUBJECT_ORGS);
        return oversight;
	}

	private VerticalSection generateCampusSection() {
        VerticalSection campus = initSection(getH3Title(LUConstants.CAMPUS_LOCATION_LABEL_KEY), WITH_DIVIDER);
        addField(campus, COURSE + "/" + CAMPUS_LOCATIONS, null, new CampusLocationList());
        return campus;
	}

	private VerticalSection generateAdminOrgsSection() {
        VerticalSection adminOrgs = initSection(getH3Title(LUConstants.ADMIN_ORG_LABEL_KEY), WITH_DIVIDER);
        addField(adminOrgs, COURSE + "/" + DEPARTMENT);
        return adminOrgs;
	}

	public SectionView generateCourseInfoSection(){
        VerticalSectionView section = initSectionView(LuSections.COURSE_INFO, LUConstants.INFORMATION_LABEL_KEY);

        //FIXME: Label should be key to messaging, field type should come from dictionary?

        section.addSection(generateCourseNumberSection());
        section.addSection(generateCourseInfoShortTitleSection());
        section.addSection(generateLongTitleSection());
        section.addSection(generateDescriptionSection());
        section.addSection(generateRationaleSection());

        return section;
    }

	private GroupSection generateCourseNumberSection() {

        //COURSE NUMBER
        GroupSection courseNumber = new GroupSection();
        courseNumber.addStyleName(LUConstants.STYLE_SECTION);
        courseNumber.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        courseNumber.setSectionTitle(getH3Title(LUConstants.IDENTIFIER_LABEL_KEY));
        addField(courseNumber, COURSE + "/" + SUBJECT_AREA, null);
        addField(courseNumber, COURSE + "/" + COURSE_NUMBER_SUFFIX, null);

        // TODO - hide cross-listed, offered jointly and version codes initially, with
        // clickable label to disclose them
        courseNumber.addSection(generateCrossListedSection());
        courseNumber.addSection(generateOfferedJointlySection());
        courseNumber.addSection(generateVersionCodesSection());

        return courseNumber;
	}

	private VerticalSection generateVersionCodesSection() {
        //Version Codes
        VerticalSection versionCodes = new VerticalSection();
        versionCodes.setSectionTitle(getH3Title(LUConstants.VERSION_CODES_LABEL_KEY));
        addField(versionCodes, COURSE + "/" + VERSIONS, null, new VersionCodeList(COURSE + "/" + VERSIONS));
        //versionCodes.addStyleName("KS-LUM-Section-Divider");
        return versionCodes;
	}

	private VerticalSection generateOfferedJointlySection() {
        // Offered jointly
        VerticalSection offeredJointly = new VerticalSection();
        offeredJointly.setSectionTitle(getH3Title(LUConstants.JOINT_OFFERINGS_ALT_LABEL_KEY));
        addField(offeredJointly, COURSE + "/" + JOINTS, null, new OfferedJointlyList());
        //offeredJointly.addStyleName("KS-LUM-Section-Divider");
        return offeredJointly;
	}

	private VerticalSection generateCrossListedSection() {
        // Cross-listed
        VerticalSection crossListed = new VerticalSection();
        crossListed.setSectionTitle(getH3Title(LUConstants.CROSS_LISTED_ALT_LABEL_KEY));
        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
        addField(crossListed, COURSE + "/" + CROSS_LISTINGS, null, new CrossListedList(COURSE + "/" + CROSS_LISTINGS));
        //crossListed.addStyleName("KS-LUM-Section-Divider");
        return crossListed;
	}

	private VerticalSection generateCourseInfoShortTitleSection() {
        VerticalSection shortTitle = initSection(getH3Title(LUConstants.SHORT_TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(shortTitle, COURSE + "/" + TRANSCRIPT_TITLE, null);
        return shortTitle;
	}

	private VerticalSection generateLongTitleSection() {
        VerticalSection longTitle = initSection(getH3Title(LUConstants.TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(longTitle, COURSE + "/" + COURSE_TITLE, null);
        return longTitle;
	}

	private VerticalSection generateDescriptionSection() {
        VerticalSection description = initSection(getH3Title(LUConstants.DESCRIPTION_LABEL_KEY), WITH_DIVIDER);
        //FIXME Temporary fix til we have a real rich text editor
        //addField(description, COURSE + "/" + DESCRIPTION, null);
        addField(description, COURSE + "/" + DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, null);
        return description;
	}

	private VerticalSection generateRationaleSection() {
        VerticalSection rationale = initSection(getH3Title(LUConstants.RATIONALE_LABEL_KEY), WITH_DIVIDER);
        addField(rationale, PROPOSAL + "/" + RATIONALE, null);
        return rationale;
	}

    public SectionView generateCourseLogisticsSection() {
        VerticalSectionView section = initSectionView(LuSections.COURSE_LOGISTICS, LUConstants.LOGISTICS_LABEL_KEY);

//      instructors.addField(new FieldDescriptor("cluInfo/instructors", null, Type.LIST, new AlternateInstructorList()));
//        //CREDITS
//        //TODO: These needs to be mapped to learning results
//        VerticalSection credits = initSection(getH3Title(LUConstants.CREDITS_LABEL_KEY), WITH_DIVIDER);
//        credits.addField(new FieldDescriptor("cluInfo/creditType", getLabel(LUConstants.CREDIT_LABEL_KEY), Type.STRING));
//        credits.addField(new FieldDescriptor("cluInfo/creditValue", getLabel(LUConstants.CREDIT_VALUE_LABEL_KEY), Type.STRING));
//        credits.addField(new FieldDescriptor("cluInfo/maxCredits", getLabel(LUConstants.MAX_CREDITS_LABEL_KEY), Type.STRING));
//        VerticalSection learningResults = initSection(getH3Title(LUConstants.LEARNING_RESULTS_LABEL_KEY), WITH_DIVIDER);
//        learningResults.addField(new FieldDescriptor("cluInfo/evalType", getLabel(LUConstants.EVALUATION_TYPE_LABEL_KEY), Type.STRING)); //TODO EVAL TYPE ENUMERATION ????
        VerticalSection firstExpectedOfferingSection = initSection(getH3Title("First Expected Offering"), WITH_DIVIDER);
        addField(firstExpectedOfferingSection, COURSE + "/" + FIRST_EXPECTED_OFFERING);
        section.addSection(firstExpectedOfferingSection);
        section.addSection(generateInstructorsSection());
//        section.addSection(credits);
//        section.addSection(learningResults);
        section.addSection(generateSchedulingSection());
        section.addSection(generateCourseFormatsSection());


        return section;
    }

    private VerticalSection generateCourseFormatsSection() {
        //COURSE FORMATS
        VerticalSection courseFormats = initSection(getH3Title(LUConstants.FORMATS_LABEL_KEY), WITH_DIVIDER);
        addField(courseFormats, COURSE + "/" + FORMATS, null, new CourseFormatList(COURSE + "/" + FORMATS));
        return courseFormats;
	}

	private VerticalSection generateSchedulingSection() {
        VerticalSection scheduling = initSection(getH3Title(LUConstants.SCHEDULING_LABEL_KEY), WITH_DIVIDER);
        GroupSection duration = new GroupSection();
        addField(duration, COURSE + "/" + CreditCourseConstants.DURATION + "/" + QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY)); //TODO DURATION ENUMERATION
        addField(duration, COURSE + "/" + CreditCourseConstants.DURATION + "/" + TERM_TYPE, "Duration Type", new DurationAtpTypeList());
        scheduling.addSection(duration);
        return scheduling;
	}

	private VerticalSection generateInstructorsSection() {
        VerticalSection instructors = initSection(getH3Title(LUConstants.INSTRUCTOR_LABEL_KEY), WITH_DIVIDER);
        // FIXME wilj: do we need to set the instructor's orgId? or can we default it at the assembler level?
        addField(instructors, COURSE + "/" + PRIMARY_INSTRUCTOR);
        return instructors;
	}

	private static AdvancedSearchWindow createAtpSearchWindow(){

        Metadata searchMetadata = new CreditCourseMetadata().getMetadata("", "");  //no type or state at this point
        SearchPanel searchPicker = new SearchPanel(searchMetadata.getProperties().get("firstExpectedOffering").getInitialLookup());
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
        section.addSection(generateLearningObjectivesNestedSection());
        return section;
    }

    private VerticalSection generateLearningObjectivesNestedSection() {
        VerticalSection los = initSection(null, NO_DIVIDER);
        
        // FIXME - make LOBuilder a section? 
        QueryPath path = QueryPath.concat(null, COURSE + "/" + COURSE_SPECIFIC_L_OS + "/" + "*" + "/" + CreditCourseCourseSpecificLOsConstants.INCLUDED_SINGLE_USE_LO + "/" + "description");
    	Metadata meta = modelDefinition.getMetadata(path);
        
        // FIXME - where should repo key come from?
        FieldDescriptor fd = addField(los,
        								CreditCourseConstants.COURSE_SPECIFIC_L_OS,
        								null,
        								new LOBuilder(type, state, groupName, "kuali.loRepository.key.singleUse", meta),
        								CreditCourseProposalConstants.COURSE);

        // have to do this here, because decision on binding is done in ks-core,
        // and we obviously don't want ks-core referring to LOBuilder
        fd.setWidgetBinding(LOBuilderBinding.INSTANCE);

        los.addStyleName("KS-LUM-Section-Divider");
        return los;
    }

	public class CourseFormatList extends UpdatableMultiplicityComposite {
    	private final String parentPath;
        public CourseFormatList(String parentPath){
        	super(StyleType.TOP_LEVEL);
        	this.parentPath = parentPath;
            setAddItemLabel(getLabel(LUConstants.COURSE_ADD_FORMAT_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.FORMAT_LABEL_KEY));
        }

        public Widget createItem() {
        	VerticalSection item = new VerticalSection();
            addField(item, ACTIVITIES, null, new CourseActivityList(QueryPath.concat(parentPath, String.valueOf(itemCount-1), ACTIVITIES).toString()),
            		parentPath + "/" + String.valueOf(itemCount -1));
            return item;
        }
    }

    public class CourseActivityList extends UpdatableMultiplicityComposite {

    	private final String parentPath;
        public CourseActivityList(String parentPath){
        	super(StyleType.SUB_LEVEL);
        	this.parentPath = parentPath;
            setAddItemLabel(getLabel(LUConstants.ADD_ACTIVITY_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.ACTIVITY_LITERAL_LABEL_KEY));
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection activity = new GroupSection();
            addField(activity, ACTIVITY_TYPE, getLabel(LUConstants.ACTIVITY_TYPE_LABEL_KEY), new CluActivityType(), path);
            activity.nextLine();

            /* CreditInfo is deprecated, needs to be replaced with learning results
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("creditInfo", "Credit Value", Type.STRING));
            activity.nextLine();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("evalType", "Learning Result", Type.STRING));
            activity.nextLine();
            */

            // FIXME need to get the term offered added to the activity metadata?
//            activity.addField(new FieldDescriptor("term", getLabel(LUConstants.TERM_LITERAL_LABEL_KEY), Type.STRING, new AtpTypeList()));
            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), path);
            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.TIME_UNIT, "Duration Type", new DurationAtpTypeList(), path);

            activity.nextLine();
            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.HRS, "Contact Hours" , path);
            // FIXME look up what the label and implement as dropdown
            //FIXME this fields constraints are wrong in its metadata, temporarily commented out
            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.PER, null,  new ContactHoursAtpTypeList(), path);
            addField(activity, DEFAULT_ENROLLMENT_ESTIMATE, getLabel(LUConstants.CLASS_SIZE_LABEL_KEY), path);

            return activity;
        }

    }

/*    //FIXME: This is a temp widget impl for the Curriculum Oversight field. Don't yet know if this
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

    }*/

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
//            GroupSection ns = new GroupSection();
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
//            GroupSection ns = new GroupSection();
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

    public class PersonList extends KSDropDown {
        final SimpleListItems people = new SimpleListItems();

        public PersonList() {
            SearchRpcServiceAsync searchRpcServiceAsync = GWT.create(SearchRpcService.class);
            final PersonList us = this;
            final String userId = Application.getApplicationContext().getUserId();
            
            //FIXME: Commented out search code to display drop down with only current user, and disable select            
            people.addItem(userId, userId);
            us.setListItems(people);
            us.selectItem(userId);
            this.setEnabled(false);
            
            /*   
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setSearchKey("person.search.personQuickViewByGivenName");
            searchRequest.setSortColumn("person.resultColumn.GivenName");
            searchRequest.setSortDirection(SortDirection.ASC);            
            searchRpcServiceAsync.search(searchRequest, new AsyncCallback<SearchResult>() {

                @Override
                public void onSuccess(SearchResult result) {
                    for (SearchResultRow r : result.getRows()) {
                        people.addItem(r.getCells().get(0).getValue(), r.getCells().get(1).getValue());
                    }
                    us.setListItems(people);
                    us.selectItem(userId);
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Unable to contact the SearchService for the list of users");
                    people.addItem(userId, userId);
                    us.setListItems(people);
                    us.selectItem(userId);
                }
            });
            */
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

        private final String parentPath;
        public CrossListedList(String parentPath){
        	super(StyleType.TOP_LEVEL);
        	this.parentPath = parentPath;
        }

/*        @Override
        public MultiplicityItem getItemDecorator(StyleType style) {
            return new RemovableItem();
        }*/

        @Override
        public Widget createItem() {
        	String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, DEPARTMENT, getLabel(LUConstants.DEPT_LABEL_KEY), null, path);
            ns.nextLine();
            addField(ns, SUBJECT_AREA, getLabel(LUConstants.SUBJECT_CODE_LABEL_KEY), path);
            addField(ns, COURSE_NUMBER_SUFFIX, getLabel(LUConstants.COURSE_NUMBER_LABEL_KEY), path);

            return ns;
        }
    }



    public class VersionCodeList extends UpdatableMultiplicityComposite {
        {
            setAddItemLabel(getLabel(LUConstants.ADD_VERSION_CODE_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.VERSION_CODE_LABEL_KEY));
        }
        private final String parentPath;
        public VersionCodeList(String parentPath){
        	super(StyleType.TOP_LEVEL);
        	this.parentPath = parentPath;
        }

/*        @Override
        public MultiplicityItem getItemDecorator(StyleType style) {
            return new RemovableItem();
        }*/

        @Override
        public Widget createItem() {
        	String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, "versionCode", getLabel(LUConstants.CODE_LABEL_KEY), path);
            addField(ns, "versionTitle", getLabel(LUConstants.TITLE_LITERAL_LABEL_KEY), path);

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
        section.addSection(generateShortTitleSection());
        return section;
    }

    private VerticalSection generateShortTitleSection() {
        VerticalSection shortTitle = initSection(getH3Title(LUConstants.SHORT_TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(shortTitle, "cluInfo/officialIdentifier/shortName", null);
        return shortTitle;
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
        SectionTitle viewTitle = getH2Title(labelKey);
        viewTitle.addStyleName("ks-heading-page-title");
        section.setSectionTitle(viewTitle);

        return section;
    }


    private VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection();
        if (title !=  null) {
          title.addStyleName("ks-heading-page-section");
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
    // revert these 5 methods to returning void again.
    private FieldDescriptor addField(Section section, String fieldKey) {
    	return addField(section, fieldKey, null, null, null);
    }    
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
