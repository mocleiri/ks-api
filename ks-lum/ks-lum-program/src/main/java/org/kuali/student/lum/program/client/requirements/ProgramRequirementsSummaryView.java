package org.kuali.student.lum.program.client.requirements;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.rules.RulePreviewWidget;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class ProgramRequirementsSummaryView extends VerticalSectionView {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);

    //view's widgets
    private FlowPanel layout = new FlowPanel();
    private Map<String, KSLabel> noRuleTextMap = new HashMap<String, KSLabel>();
    private ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.SaveCancelEnum.SAVE, ButtonEnumerations.SaveCancelEnum.CANCEL);    

    //view's data
    private ProgramRequirementsViewController parentController;
    private ProgramRequirementsDataModel rules;
    private boolean isReadOnly;
    private boolean isRulesDisplayed = false;
    private static int tempProgReqInfoID = 9999;
    private static final String NEW_PROG_REQ_ID = "NEWPROGREQ";
    private static final String NEW_STMT_TREE_ID = "NEWSTMTTREE";

    private Map<String, SpanPanel> perProgramRequirementTypePanel = new HashMap<String, SpanPanel>();

    public ProgramRequirementsSummaryView(final ProgramRequirementsViewController parentController, Enum<?> viewEnum, String name,
                                                            String modelId, ProgramRequirementsDataModel rulesData, boolean isReadOnly) {
        super(viewEnum, name, modelId);
        this.parentController = parentController;
        rules = rulesData;
        rules.setInitialized(false);
        this.isReadOnly = isReadOnly;
        setupButtons();
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {

        //only when user wants to see rules then load requirements from database if they haven't been loaded yet
        if (!rules.isInitialized()) {
            rules.retrieveProgramRequirements(new Callback<Boolean>() {
                @Override
                public void exec(Boolean result) {
                    if (result) {
                        displayRules();
                    }
                    onReadyCallback.exec(result);
                }
            });
            return;
        }

        //for read-only view, we don't need to worry about rules being added or modified
        if (isReadOnly) {
            displayRules();            
            onReadyCallback.exec(true);
            return;
        }

        //return if user did not added or updated a rule
        parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE, new Callback<View>(){
			@Override
			public void exec(View result) {
				ProgramRequirementsManageView manageView = (ProgramRequirementsManageView) result;
                
				//return if user did not added or updated a rule
                if (!manageView.isDirty() || !manageView.isUserClickedSaveButton()) {
                    onReadyCallback.exec(true);
                    return;
                }

                //update the rule because user added or edited the rule
                ((SectionView)parentController.getCurrentView()).setIsDirty(false);
                manageView.setUserClickedSaveButton(false);

                //if rule storage updated successfully, update the display as well
                Map<StatementTypeInfo, ProgramRequirementInfo> affectedRule = rules.updateRules(manageView.getRuleTree(), manageView.getRelatedProgramReqInfoId(), manageView.isNewRule());
                if (affectedRule != null) {
                    StatementTypeInfo affectedStatementTypeInfo = new StatementTypeInfo();
                    for (final StatementTypeInfo stmtTypeInfo : affectedRule.keySet()) {
                        affectedStatementTypeInfo = stmtTypeInfo;
                    }
                    SpanPanel reqPanel = perProgramRequirementTypePanel.get(affectedStatementTypeInfo.getId());
                    for (int i = 0; i < reqPanel.getWidgetCount(); i++) {
                        RulePreviewWidget rulePreviewWidget = (RulePreviewWidget)reqPanel.getWidget(i);
                        if (rules.compareStatementTrees(affectedRule.get(affectedStatementTypeInfo).getStatement(), rulePreviewWidget.getStatementTreeViewInfo())) {
                                RulePreviewWidget newRulePreviewWidget = getUpdatedProgramRequirement(reqPanel, affectedStatementTypeInfo, affectedRule.get(affectedStatementTypeInfo));
                                reqPanel.insert(newRulePreviewWidget, i);
                                reqPanel.remove(rulePreviewWidget);
                        }
                    }
                }

		        onReadyCallback.exec(true);
			}
		});        
    }

    @Override
    public void updateModel() {
        parentController.requestModel(new ModelRequestCallback() {
            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert(cause.getMessage());
                GWT.log("Unable to retrieve model for program requirements view", cause);
            }
            @Override
            public void onModelReady(Model model) {
                rules.updateModelFromLocalData(((DataModel)model).getRoot());
            }
        });
    }

    public void displayRules() {
        isRulesDisplayed = true;        
        remove(layout);
        layout.clear();

        //display 'Program Requirements' page title (don't add if read only because the section itself will display the title)
        if (!isReadOnly) {
            SectionTitle pageTitle = SectionTitle.generateH2Title(ProgramProperties.get().programRequirements_summaryViewPageTitle());
            pageTitle.setStyleName("KS-Program-Requirements-Section-header");  //make the header orange
            layout.add(pageTitle);
        }
        
        //iterate and display rules for each Program Requirement type e.g. Entrance Requirements, Completion Requirements
        Boolean firstRequirement = true;
        for (StatementTypeInfo stmtTypeInfo : rules.getStoredStatementTypes()) {

            //create and display one type of program requirement section
            SpanPanel requirementsPanel = new SpanPanel();
            perProgramRequirementTypePanel.put(stmtTypeInfo.getId(), requirementsPanel);
            displayRequirementSectionForGivenType(requirementsPanel, stmtTypeInfo, firstRequirement);
            firstRequirement = false;

            //now display each requirement for this Program Requirement type
            for (ProgramRequirementInfo ruleInfo : rules.getStoredProgRequirements(stmtTypeInfo)) {
                addProgramRequirement(requirementsPanel, stmtTypeInfo, ruleInfo, ProgramRequirementsDataModel.requirementState.STORED);
            }
        }

        //save and cancel buttons
        layout.add(actionCancelButtons);

        addWidget(layout);
    }

    private void displayRequirementSectionForGivenType(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, boolean firstRequirement) {

        //display header for this Program Requirement type e.g. Entrance Requirements; make the header plural
        SectionTitle title = SectionTitle.generateH3Title(getWordPlural(stmtTypeInfo.getName()));
        title.setStyleName((firstRequirement ? "KS-Program-Requirements-Preview-Rule-Type-First-Header" : "KS-Program-Requirements-Preview-Rule-Type-Header"));  //make the header orange
        layout.add(title);

        //add rule description
        KSLabel ruleTypeDesc = new KSLabel(stmtTypeInfo.getDescr());
        ruleTypeDesc.addStyleName("KS-Program-Requirements-Preview-Rule-Type-First-Desc");        
        layout.add(ruleTypeDesc);        

        //display "Add Rule" button if user is in 'edit' mode
        if (!isReadOnly) {
            String addRuleLabel = ProgramProperties.get().programRequirements_summaryViewPageAddRule(stmtTypeInfo.getName());
            KSButton addProgramReqBtn = new KSButton(addRuleLabel, KSButtonAbstract.ButtonStyle.FORM_SMALL);
            addProgramReqBtn.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                        showAddProgramRequirementDialog(requirementsPanel, stmtTypeInfo);
                    }
                });
            layout.add(addProgramReqBtn);
        }

        layout.add(requirementsPanel);
        
        //add widget for displaying "No entrance requirement currently exist for this program"
        String noRuleText = ProgramProperties.get().programRequirements_summaryViewPageNoRule(stmtTypeInfo.getName().toLowerCase());
        KSLabel ruleDesc = new KSLabel(noRuleText);
        ruleDesc.addStyleName("KS-Program-Requirements-Preview-No-Rule-Text");
        noRuleTextMap.put(stmtTypeInfo.getName(), ruleDesc);
        setupNoRuleText(stmtTypeInfo);
        layout.add(ruleDesc);
    }

    private void addProgramRequirement(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, final ProgramRequirementInfo progReqInfo,
                                       ProgramRequirementsDataModel.requirementState ruleInitialState) {
        //first add new program requirement into the map of rules
        rules.getStoredProgReqsAndStates(stmtTypeInfo).put(progReqInfo, ruleInitialState);
        final RulePreviewWidget rulePreviewWidget = new RulePreviewWidget(stmtTypeInfo.getName(), progReqInfo.getShortTitle(), progReqInfo.getDescr().getPlain(),
                                                    progReqInfo.getStatement(), isReadOnly);

        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeInfo, progReqInfo);

        requirementsPanel.add(rulePreviewWidget);
    }

    private RulePreviewWidget getUpdatedProgramRequirement(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo, final ProgramRequirementInfo progReqInfo) {

        final RulePreviewWidget rulePreviewWidget = new RulePreviewWidget(stmtTypeInfo.getName(), progReqInfo.getShortTitle(), progReqInfo.getDescr().getPlain(),
                                                    progReqInfo.getStatement(), isReadOnly);
        addRulePreviewWidgetHandlers(requirementsPanel, rulePreviewWidget, stmtTypeInfo, progReqInfo);
        return rulePreviewWidget;
    }

    private void addRulePreviewWidgetHandlers(final SpanPanel requirementsPanel, final RulePreviewWidget rulePreviewWidget, final StatementTypeInfo stmtTypeInfo,
                                              final ProgramRequirementInfo progReqInfo) {

        final LinkedHashMap<ProgramRequirementInfo, ProgramRequirementsDataModel.requirementState> rulesPerType = rules.getStoredProgReqsAndStates(stmtTypeInfo);

        rulePreviewWidget.addRequirementEditButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
            showEditProgramRequirementDialog(requirementsPanel, stmtTypeInfo.getName());
            }
        });

        rulePreviewWidget.addRequirementDeleteButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                final ConfirmationDialog dialog = new ConfirmationDialog(
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRuleDialogTitle(),
                        ProgramProperties.get().programRequirements_summaryViewPageDeleteRuleDialogMsg());

                dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                    @Override
                    public void onClick(ClickEvent event) {
                        if (rulesPerType.get(progReqInfo) == ProgramRequirementsDataModel.requirementState.ADDED) {
                            //we completely remove a rule that was added and then deleted without any save between
                            rulesPerType.remove(progReqInfo);
                        } else {
                            rulesPerType.put(progReqInfo, ProgramRequirementsDataModel.requirementState.DELETED);  //overwrite previous state
                        }

                        //remove rule from display
                        requirementsPanel.remove(rulePreviewWidget);
                        setupNoRuleText(stmtTypeInfo);

                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });

        /* SUB-RULE edit/delete/add link&buttons handlers */
        rulePreviewWidget.addSubRuleAddButtonClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                    final StatementTreeViewInfo newRule = new StatementTreeViewInfo();
                    newRule.setId(generateStatementTreeId());
                    newRule.setType(stmtTypeInfo.getId());
                    RichTextInfo text = new RichTextInfo();
                    text.setPlain("");
                    newRule.setDesc(text);
                	parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE, new Callback<View>(){

        				@Override
        				public void exec(View result) {
        					((ProgramRequirementsManageView) result).setRuleTree(newRule, true, progReqInfo.getId());
        					parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
        				}
        			});
            }
        });

        rulePreviewWidget.addSubRuleEditCallback(editRuleCallback);
    }

    private void showAddProgramRequirementDialog(final SpanPanel requirementsPanel, final StatementTypeInfo stmtTypeInfo) {

        String addRuleText = ProgramProperties.get().programRequirements_summaryViewPageAddRule(stmtTypeInfo.getName());
        final KSLightBox dialog = new KSLightBox(addRuleText);

	    ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.AddCancelEnum.ADD, ButtonEnumerations.AddCancelEnum.CANCEL);

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.AddCancelEnum.ADD) {
                    //- call validate
                    //- call update method on vertical section view
                    //create a new program requirement

                    ProgramRequirementInfo newProgramInfo = new ProgramRequirementInfo();
                    newProgramInfo.setId(NEW_PROG_REQ_ID + Integer.toString(tempProgReqInfoID++));   //set unique id
                    newProgramInfo.setType("kuali.lu.type.Requirement");
                    //TODO remove after dialog fields implemented:
                    RichTextInfo text = new RichTextInfo();
                    text.setPlain("These are classes or sequences that a student must have completed in order to register" +
                                                " in the course. For example, students must have completed 3 classes with a specific GPA.");
                    newProgramInfo.setDescr(text);
                    newProgramInfo.setShortTitle("Expected Total Credits: 50 - 60");

                    //create a top level statement tree
                    StatementTreeViewInfo stmtTree = new StatementTreeViewInfo();
                    stmtTree.setId(generateStatementTreeId());
                    stmtTree.setType(stmtTypeInfo.getId());
                    RichTextInfo text2 = new RichTextInfo();
                    text2.setPlain("");
                    stmtTree.setDesc(text2);                    
                    stmtTree.setOperator(StatementOperatorTypeKey.AND); //AND is top level operator for rules within a Program Requirement

                    //add new statement to the rule because even if user cancel on rule manage screen, we want to have at least one statement present
                    newProgramInfo.setStatement(stmtTree);

                    addProgramRequirement(requirementsPanel, stmtTypeInfo, newProgramInfo, ProgramRequirementsDataModel.requirementState.ADDED);
                }
                dialog.hide();
            }
        });

        //TODO need proper dialog here
        //VerticalSectionView layout = new VerticalSectionView();
        //layout.addStyleName("KS-Advanced-Search-Window");
        SpanPanel layout = new SpanPanel();
		layout.add(new KSLabel("TEST"));
		layout.add(actionCancelButtons);

        dialog.setSize(600,400);
        dialog.setWidget(layout);
        dialog.show();
    }

    private void showEditProgramRequirementDialog(final SpanPanel requirementsPanel, final String ruleTypeName) {

        String addRuleText = ProgramProperties.get().programRequirements_summaryViewPageAddRule(ruleTypeName);
        final KSLightBox dialog = new KSLightBox(addRuleText);

	    ActionCancelGroup actionCancelButtons = new ActionCancelGroup(ButtonEnumerations.UpdateCancelEnum.UPDATE, ButtonEnumerations.UpdateCancelEnum.CANCEL);

        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
                if (result == ButtonEnumerations.UpdateCancelEnum.CANCEL) {
                 dialog.hide();
                } else {   //create a new program requirement
                    //- call validate
                    //- call update method on vertical section view
                    //true if user is editing existing program requirement
                }
            }
        });

        //TODO need proper dialog here
        //VerticalSectionView layout = new VerticalSectionView();
        //layout.addStyleName("KS-Advanced-Search-Window");
        SpanPanel layout = new SpanPanel();
		layout.add(new KSLabel("TEST"));
		layout.add(actionCancelButtons);

        dialog.setSize(600,400);
        dialog.setWidget(layout);
        dialog.show();
    }

    private void setupNoRuleText(StatementTypeInfo stmtTypeInfo) {
        noRuleTextMap.get(stmtTypeInfo.getName()).setVisible(rules.isRuleExists(stmtTypeInfo));
    }

    protected Callback<StatementTreeViewInfo> editRuleCallback = new Callback<StatementTreeViewInfo>(){
        public void exec(final StatementTreeViewInfo stmtTreeInfo) {
        	parentController.getView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE, new Callback<View>(){

				@Override
				public void exec(View result) {
					((ProgramRequirementsManageView) result).setRuleTree(stmtTreeInfo, false, null);
					parentController.showView(ProgramRequirementsViewController.ProgramRequirementsViews.MANAGE);
				}
			});
            
        }
    };

    private void setupButtons() {
        actionCancelButtons.addStyleName("KS-Program-Requirements-Save-Button");
        actionCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>(){
             @Override
            public void exec(ButtonEnumerations.ButtonEnum result) {
               updateModel();
            }
        });
    }

    private String getWordPlural(String word) {
        return (word.endsWith("s") ? word : word + "s");
    }

    static public String generateStatementTreeId() {
        return (NEW_STMT_TREE_ID + Integer.toString(tempProgReqInfoID++));
    }

    public void setRules(ProgramRequirementsDataModel rules) {
        this.rules = rules;
        this.rules.setInitialized(false);
    }
}