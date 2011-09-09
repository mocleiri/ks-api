package org.kuali.student.lum.program.client.major.proposal;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.edit.CatalogInformationEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.CollaboratorsEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.LearningObjectivesEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.ManagingBodiesEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.ProgramRequirementsEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.SpecializationsEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.SupportingDocsEditConfiguration;

/** 
 * @author Igor
 */
public class MajorProposalConfigurer extends AbstractProgramConfigurer {

	protected ConfigurationManager proposalSummarySectionConfigurer;
	
    public MajorProposalConfigurer() {

    	programSectionConfigManager = new ConfigurationManager(this);
		programSectionConfigManager.registerConfiguration(new MajorProposalInformationEditConfiguration());
        programSectionConfigManager.registerConfiguration(new MajorProposalChangeImpactEditConfiguration());
        programSectionConfigManager.registerConfiguration(new MajorProposalKeyProgramInfoEditConfiguration());
        programSectionConfigManager.registerConfiguration(new ManagingBodiesEditConfiguration());
        programSectionConfigManager.registerConfiguration(new SpecializationsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new CatalogInformationEditConfiguration());
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new LearningObjectivesEditConfiguration());
        programSectionConfigManager.registerConfiguration(new CollaboratorsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new SupportingDocsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new MajorProposalSummaryConfiguration(true));
        
        
        proposalSummarySectionConfigurer = new ConfigurationManager(this);
        proposalSummarySectionConfigurer.registerConfiguration(new MajorProposalSummaryConfiguration(false));
    }
    
    @Override
    public ConfigurationManager getProgramSectionConfigManager() {
    	if (modelDefinition.getMetadata().isCanEdit()) {
    		return programSectionConfigManager;
    	} else {
    		return proposalSummarySectionConfigurer;    	
    	}
    }



}
