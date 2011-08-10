package org.kuali.student.lum.program.client.major.proposal;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class MajorProposalInformationEditConfiguration extends AbstractSectionConfiguration {

   
    public MajorProposalInformationEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_EDIT, ProgramProperties.get().program_menu_sections_proposalInformation(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title("Proposal Information"));  //TODO: get title from ProgramProperties
        configurer.addField(section, ProgramConstants.PROPOSAL_TITLE_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluProgramTitle()));        
        configurer.addField(section, ProgramConstants.PROPOSAL_TYPE_OF_MODIFICATON_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluModificationType()));        
        configurer.addField(section, ProgramConstants.PROPOSAL_ABSTRACT_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluAbstractType()));
        configurer.addField(section, ProgramConstants.PROPOSAL_RATIONALE_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluProposalRationale()));
        rootSection.addSection(section);     
     }
 
}
