package org.kuali.student.lum.program.client.major.view;

import java.util.List;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentList;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentListBinding;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.ui.client.widgets.rules.RulePreviewWidget;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorEditableHeader;
import org.kuali.student.lum.program.client.major.MajorManager;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.proposal.MajorProposalController;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsSummaryView;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsViewController;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProgramRequirementsViewConfiguration extends AbstractControllerConfiguration {

    private ProgramRequirementsViewController progReqcontroller;

    private ProgramRequirementsDataModel rules;
    private ProgramRequirementsDataModel rulesComp;

    public ProgramRequirementsViewConfiguration(boolean special) {
        progReqcontroller = new ProgramRequirementsViewController(controller, MajorManager.getEventBus(), 
                                    ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_VIEW, true,
                                    (special ? new MajorEditableHeader(ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_EDIT) : null));
    }

    public ProgramRequirementsViewConfiguration(boolean special, boolean reloadRequirements) {
        progReqcontroller = new ProgramRequirementsViewController(controller, MajorManager.getEventBus(),
                                    ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_VIEW, true,
                                    (special ? new MajorEditableHeader(ProgramProperties.get().program_menu_sections_requirements(), ProgramSections.PROGRAM_REQUIREMENTS_EDIT) : null), reloadRequirements);
    }

    public ProgramRequirementsViewConfiguration(Controller controller) {
        String title = ProgramProperties.get().program_menu_sections_requirements();
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_REQUIREMENTS_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(title, ProgramSections.PROGRAM_REQUIREMENTS_EDIT));
        this.controller = controller;
    }

    @Override
    protected void buildLayout() {
    	if (controller instanceof MajorProposalController || controller instanceof MajorEditController)
    	{	
    		VerticalSection section = new VerticalSection();
    		section.addSection(createProgramRequirementsSectionEdit());
    		rootSection.addSection(section);
    	}	
    	else  	
            rootSection = progReqcontroller.getProgramRequirementsView(); 	
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
        if (progReqcontroller != null) {
            progReqcontroller.setParentController(controller);
        }
    }
       
    // Side-by-side comparison (when controller is not null)  
    private SummaryTableSection createProgramRequirementsSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createProgramRequirementsSectionEditBlock());

        return section;
    }

    @SuppressWarnings("unchecked")
  	public SummaryTableFieldBlock createProgramRequirementsSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		if (controller instanceof MajorProposalController)
  		{	
  			rules = ((MajorProposalController) controller).getReqDataModel();
  			rulesComp = ((MajorProposalController) controller).getReqDataModelComp();
  		} else if (controller instanceof MajorEditController)
  		{	
  			rules = ((MajorEditController) controller).getReqDataModel();
  			rulesComp = ((MajorEditController) controller).getReqDataModelComp();
  		}
  		
  		// one row per requirement type
        for (StatementTypeInfo stmtType : rules.getStmtTypes()) {
            SummaryTableFieldRow arow;
            arow = new SummaryTableFieldRow(addRequisiteField(new FlowPanel(), stmtType), 
            				addRequisiteFieldComp(new FlowPanel(), stmtType));

            block.addSummaryTableFieldRow(arow);
        }

  		return block;
  	} 

    private FieldDescriptorReadOnly addRequisiteField(final FlowPanel panel,
            final StatementTypeInfo stmtType) {

        final ModelWidgetBinding<FlowPanel> widgetBinding = new ModelWidgetBinding<FlowPanel>() {

            @Override
            public void setModelValue(FlowPanel panel, DataModel model, String path) {}

            @Override
            public void setWidgetValue(final FlowPanel panel, DataModel model, //Don't place a breakpoint here:  It will stall debugging for some unknown reason!
                    String path) {
                panel.clear();
                List<ProgramRequirementInfo> programRequirementInfos = rules.getProgReqInfo(stmtType.getId());
                for (ProgramRequirementInfo rule : programRequirementInfos) {
                    Integer internalProgReqID = rules.getInternalProgReqID(rule);
                    String stmtTypeId = rule.getStatement().getType();

                    int minCredits = (rule.getMinCredits() == null ? 0 : rule.getMinCredits());
                    int maxCredits = (rule.getMaxCredits() == null ? 0 : rule.getMaxCredits());
                    String plainDesc = (rule.getDescr() == null ? "" : rule.getDescr().getPlain());
                    final RulePreviewWidget ruleWidget = new RulePreviewWidget(internalProgReqID, rule.getShortTitle(),
                    		getTotalCreditsString(minCredits, maxCredits), plainDesc, rule.getStatement(),
                    		true, ProgramRequirementsSummaryView.getCluSetWidgetList(rule.getStatement()));
                    panel.add(ruleWidget);
                }
            }
        };

        FieldDescriptorReadOnly requisiteField = new FieldDescriptorReadOnly(
        		ProgramConstants.ID, new MessageKeyInfo(stmtType.getName()), null, panel);
        requisiteField.setWidgetBinding(widgetBinding);

        return requisiteField;
    }

    private FieldDescriptorReadOnly addRequisiteFieldComp(
            final FlowPanel panel, final StatementTypeInfo stmtType) {

        final ModelWidgetBinding<FlowPanel> widgetBinding = new ModelWidgetBinding<FlowPanel>() {

            @Override
            public void setModelValue(FlowPanel panel, DataModel model,
                    String path) {
                }

            @Override
            public void setWidgetValue(final FlowPanel panel, DataModel model, //Don't place a breakpoint here:  It will stall debugging for some unknown reason!
                    String path) {
                panel.clear();
                List<ProgramRequirementInfo> programRequirementInfos = rulesComp.getProgReqInfo(stmtType.getId());
                for (ProgramRequirementInfo rule : programRequirementInfos) {
                    Integer internalProgReqID = rulesComp.getInternalProgReqID(rule);
                    String stmtTypeId = rule.getStatement().getType();

                    int minCredits = (rule.getMinCredits() == null ? 0 : rule.getMinCredits());
                    int maxCredits = (rule.getMaxCredits() == null ? 0 : rule.getMaxCredits());
                    String plainDesc = (rule.getDescr() == null ? "" : rule.getDescr().getPlain());
                    final RulePreviewWidget ruleWidget = new RulePreviewWidget(internalProgReqID, rule.getShortTitle(),
                    		getTotalCreditsString(minCredits, maxCredits), plainDesc, rule.getStatement(),
                    		true, ProgramRequirementsSummaryView.getCluSetWidgetList(rule.getStatement()));
                    panel.add(ruleWidget);
                }
            }
        };

        FieldDescriptorReadOnly requisiteField = new FieldDescriptorReadOnly(
        		ProgramConstants.ID, new MessageKeyInfo(stmtType.getName()), null, panel);
        requisiteField.setWidgetBinding(widgetBinding);

        return requisiteField;
    }
    
    private String getTotalCreditsString(int min, int max) {
        return "Expected Total Credits:" + min + "-" + max;
    }

}
