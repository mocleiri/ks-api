package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.LayoutConfigurationException;
import org.kuali.student.common.ui.client.configurable.PropertyBinding;
import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;
import org.kuali.student.lum.lu.dto.CluInfo;

import com.google.gwt.user.client.ui.TextBox;

public class LULayoutFactory {
    private final ObjectStructure structure;
    private final Validator validator;
    private final Map<String, Map<String, FieldDescriptor>> indexedFields = new HashMap<String, Map<String,FieldDescriptor>>();

    public LULayoutFactory(ObjectStructure structure, Validator validator) {
        this.structure = structure;
        this.validator = validator;
    }

    public ConfigurableLayout<CluInfo> getLayout(String type, String state) {

        if (type.equalsIgnoreCase(LUConstants.LU_TYPE_CREDIT_COURSE)) {
            return getCreditCourseLayout(type, state);
        }
//      else if (type.equalsIgnoreCase(LUConstants.LU_TYPE_NON_CREDIT_COURSE) {
//      return getNonCreditCourseLayout(type, state);
//      }  etc....
        else {
            throw new LayoutConfigurationException("Invalid type: " + type);            
        }

    }

    /**
     * This method returns a page layout configured for creation or updating of particular state of 
     * a credit course LU
     * 
     * Current valid states listed here :- 
     *    https://test.kuali.org/confluence/display/KULSTU/LuConfig.Constraints.LuTypeLuState
     * 
     * 
     * @param type
     * @param state
     * @return
     */
    private ConfigurableLayout<CluInfo> getCreditCourseLayout(String type, String state) {
        if (state.equalsIgnoreCase(LUConstants.LU_STATE_ACTIVATED)) {
            return getActivatedCreditCourseLayout(type, state);
        }
        else if (state.equalsIgnoreCase(LUConstants.LU_STATE_APPROVED)) {
            return getApprovedCreditCourseLayout(type, state);
        }
        else if (state.equalsIgnoreCase(LUConstants.LU_STATE_NOT_APPROVED)) {
            return getNotApprovedCreditCourseLayout(type, state);
        }
        else if (state.equalsIgnoreCase(LUConstants.LU_STATE_PROPOSED)) {
            return getProposedCreditCourseLayout(type, state);
        }
        else if (state.equalsIgnoreCase(LUConstants.LU_STATE_RETIRED)) {
            return getRetiredCreditCourseLayout(type, state);
        }
        else if (state.equalsIgnoreCase(LUConstants.LU_STATE_SUBMITTED)) {
            return getSubmittedCreditCourseLayout(type, state);
        }
        else if (state.equalsIgnoreCase(LUConstants.LU_STATE_WITHDRAWN)) {
            return getWithdrawnCreditCourseLayout(type, state);
        }
        else {
            throw new LayoutConfigurationException("Invalid type/state: " + type + ", " + state);
        }
    }

    private ConfigurableLayout<CluInfo> getActivatedCreditCourseLayout(String type, String state) {

        // TODO hjohnson - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    private ConfigurableLayout<CluInfo> getApprovedCreditCourseLayout(String type, String state) {

        // TODO hjohnson - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    private ConfigurableLayout<CluInfo> getProposedCreditCourseLayout(String type, String state) {

        final Map<String, FieldDescriptor> fields = getFields(type, state);

        return new DefaultCreateUpdateLayout<CluInfo>() {{

            addViewSection("Overview", 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addViewSection("Checklist", 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addViewSection("Proposal Summary", 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Proposal Information", "Author + Collaborators"}, 
                    new SimpleConfigurableSection<CluInfo>()
                    .addField(new ConfigurableField<CluInfo>()
                            .setBinding(new PropertyBinding<CluInfo>() {
                                @Override
                                public Object getValue(CluInfo object) {
                                    // TODO figure out how to get the originating faculty member
                                    return "Bob";
                                }
                                @Override
                                public void setValue(CluInfo object, Object value) {
                                    // TODO figure out which field is the originating faculty member
                                }
                            })
                            .setFormField(new KSFormField("originatingFacultyMember", "Originating Faculty Member")
                            .setWidget(new TextBox())
                            .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                            "http://www.google.com/")
                            )
//                            .addConstraint(new DictionaryConstraint(validator, fields.get("originatingFacultyMember")))
                            )
                    )
                    .addField(new ConfigurableField<CluInfo>()
                            .setBinding(new PropertyBinding<CluInfo>() {
                                @Override
                                public Object getValue(CluInfo object) {
                                    // TODO figure out how to get the administrative delegate
                                    return "Jim";
                                }
                                @Override
                                public void setValue(CluInfo object, Object value) {
                                    // TODO figure out which field is the administrative delegate
                                }
                            })
                            .setFormField(new KSFormField("administrativeDelegate", "Administrative Delegate")
                            .setWidget(new TextBox())
                            .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                            "http://www.google.com/")
                            )
                            //.addConstraint(new DictionaryConstraint(validator, fields.get("administrativeDelegate")))
                            )
                    )
                    .setSectionTitle("Author + Collaborators")
                    .setInstructions("Instructions go here...")
                    .setParentLayout(this)

            );


            addSection(new String[] {"Proposal Information", "Governance"}, 
                    new SimpleConfigurableSection<CluInfo>()
                    .addField(new ConfigurableField<CluInfo>()
                            .setBinding(new PropertyBinding<CluInfo>() {
                                @Override
                                public Object getValue(CluInfo object) {
                                    // TODO figure out how to get the curriculum oversight
                                    return "graduate";
                                }
                                @Override
                                public void setValue(CluInfo object, Object value) {
                                    // TODO figure out which field is the curriculum oversight
                                }
                            })
                            .setFormField(new KSFormField("curriculumOversight", "Curriculum Oversight")
                            .setWidget(new TextBox())
                            .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                            "http://www.google.com/")
                            )
//                            .addConstraint(new DictionaryConstraint(validator, fields.get("curriculumOversight")))
                            )
                    )
                    .addField(new ConfigurableField<CluInfo>()
                            .setBinding(new PropertyBinding<CluInfo>() {
                                @Override
                                public Object getValue(CluInfo object) {
                                    // finally, a field with an obvious cluinfo mapping
                                    return object.getAdminOrg();
                                }
                                @Override
                                public void setValue(CluInfo object, Object value) {
                                    object.setAdminOrg(value.toString());
                                }
                            })
                            .setFormField(new KSFormField("adminOrg", "Administering Organization")
                            .setWidget(new TextBox())
                            .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                            "http://www.google.com/")
                            )
//                            .addConstraint(new DictionaryConstraint(validator, fields.get("adminOrg")))
                            )
                    )
                    .setSectionTitle("Governance")
                    .setInstructions("Instructions go here...")
                    .setParentLayout(this)					

            );

            addSection(new String[] {"Proposal Information", "Course Format"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Academic Content", "Course Information"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Academic Content", "Learning Objectives"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Academic Content", "Syllabus"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Academic Content", "Learning Results"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Student Eligibility", "Course Restrictions"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Student Eligibility", "Pre + Co Requisites"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Administrative", "Credits"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Administrative", "Active Dates"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Administrative", "Financials"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Administrative", "Program Requirements"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

            addSection(new String[] {"Attachments", "Supporting Documents"}, 
                    new SimpleConfigurableSection<CluInfo>()
            );

        }};


    }

    /**
     * This method ...
     * 
     * @return
     */
    private ConfigurableLayout<CluInfo> getNotApprovedCreditCourseLayout(String type, String state) {

        // TODO hjohnson - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This method ...
     * 
     * @return
     */
    private ConfigurableLayout<CluInfo> getRetiredCreditCourseLayout(String type, String state) {

        // TODO hjohnson - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This method ...
     * 
     * @return
     */
    private ConfigurableLayout<CluInfo> getSubmittedCreditCourseLayout(String type, String state) {

        // TODO hjohnson - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This method ...
     * 
     * @return
     */
    private ConfigurableLayout<CluInfo> getWithdrawnCreditCourseLayout(String type, String state) {

        // TODO hjohnson - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    private Map<String, FieldDescriptor> getFields(String type, String state) {

        Map<String, FieldDescriptor> result = indexedFields.get(type.toLowerCase() + ":" + state.toLowerCase());

        if (result == null) {
            for (Type t : structure.getType()) {
                if (t.getKey().equalsIgnoreCase(type)) {
                    for (State s : t.getState()) {
                        if (s.getKey().equalsIgnoreCase(state)) {
                            result = new HashMap<String, FieldDescriptor>();
                            for (Field f : s.getField()) {
                                result.put(f.getKey(), f.getFieldDescriptor());
                            }
                            indexedFields.put(type.toLowerCase() + ":" + state.toLowerCase(), result);
                        }
                    }
                    break;
                }
            }
        }

        return result;
    }


}



