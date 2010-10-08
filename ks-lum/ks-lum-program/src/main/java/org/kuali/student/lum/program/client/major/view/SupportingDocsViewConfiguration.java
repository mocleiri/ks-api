package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class SupportingDocsViewConfiguration extends AbstractSectionConfiguration {

    public SupportingDocsViewConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_VIEW, ProgramProperties.get().program_menu_sections_supportingDocuments(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {

    }
}
