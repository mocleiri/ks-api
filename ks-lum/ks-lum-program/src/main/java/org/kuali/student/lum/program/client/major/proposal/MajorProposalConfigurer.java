package org.kuali.student.lum.program.client.major.proposal;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.edit.CatalogInformationEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.CollaboratorsEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.LearningObjectivesEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.MajorKeyProgramInfoEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.ManagingBodiesEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.ProgramRequirementsEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.SpecializationsEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.SupportingDocsEditConfiguration;

/** 
 * @author Igor
 */
public class MajorProposalConfigurer extends AbstractProgramConfigurer {

    public MajorProposalConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(new MajorProposalInformationEditConfiguration());
        programSectionConfigManager.registerConfiguration(new MajorProposalChangeImpactEditConfiguration());
        programSectionConfigManager.registerConfiguration(new MajorKeyProgramInfoEditConfiguration());
        programSectionConfigManager.registerConfiguration(new ManagingBodiesEditConfiguration());
        programSectionConfigManager.registerConfiguration(new SpecializationsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new CatalogInformationEditConfiguration());
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new LearningObjectivesEditConfiguration());
        programSectionConfigManager.registerConfiguration(new CollaboratorsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new SupportingDocsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new MajorProposalSummaryConfiguration());
    }
}
