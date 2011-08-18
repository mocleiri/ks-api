package org.kuali.student.lum.program.client.major.proposal;

import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.view.CatalogInformationViewConfiguration;
import org.kuali.student.lum.program.client.major.view.LearningObjectivesViewConfiguration;
import org.kuali.student.lum.program.client.major.view.MajorKeyProgramInfoViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ManagingBodiesViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ProgramRequirementsViewConfiguration;
import org.kuali.student.lum.program.client.major.view.SpecializationsViewConfiguration;
import org.kuali.student.lum.program.client.major.view.SupportingDocsViewConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import com.google.gwt.user.client.ui.Widget;

/**
 * This configuration is used to define what widgets should appear on the screen
 * for the major proposal controller.  
 * <p>
 * Note that this is pretty much a copy of the major discipline controller, 
 * but this controller has a couple extra tabs .
 * 
 * @author cmann
 */
public class MajorProposalSummaryConfiguration extends AbstractControllerConfiguration {

    public MajorProposalSummaryConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, ProgramProperties.get().program_menu_sections_summary(), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    /**
     * This is an inherited method called by the framework to layout components on the screen.
     */
    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
    	
        // Initialize tabs on left of screen
        MajorKeyProgramInfoViewConfiguration majorInfoViewConfig = MajorKeyProgramInfoViewConfiguration.createSpecial(controller);
        configurationManager.registerConfiguration(majorInfoViewConfig);
        configurationManager.registerConfiguration(ManagingBodiesViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(SpecializationsViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(CatalogInformationViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(new ProgramRequirementsViewConfiguration(controller));
        configurationManager.registerConfiguration(LearningObjectivesViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(SupportingDocsViewConfiguration.createSpecial(controller));

        // Add the work flow utilities to the screen light box to the screen
        // Instance of check ensures we only do this for majors
        if (controller instanceof MajorProposalController){
            
            // Grab the work flow utilities widget we initialized in the controller
            WorkflowUtilities workflowUtilities = ((MajorProposalController) controller).getWfUtilities();
            workflowUtilities.addSubmitCallback(new Callback<Boolean>() {

                @Override
                public void exec(Boolean result) {
                    if (result) {
                        ((MajorProposalController) controller).setStatus();
                    }

                }
            });
            
            // Get a reference to the widget so we can add it to the root section of the screen
            Widget widget = workflowUtilities.getWorkflowActionsWidget();
            
            // Enable widget so it is visible
            // TODO: Is this needed here or can we do this when displaying widget
            workflowUtilities.enableWorkflowActionsWidgets(true);
            
            // Add the widget to the root section of the screen
            rootSection.addWidget(widget); 
        }
 
        
        // Loop over all configurations in the configuration manager
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }    
     }
}
