package org.kuali.student.lum.program.client.edit;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.program.client.framework.AbstractConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class LearningObjectivesEditConfiguration extends AbstractConfiguration<ProgramEditConfigurer> {
    @Override
    public View getView() {
        return new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_EDIT, ProgramProperties.get().program_menu_sections_learningObjectives(), ProgramConstants.PROGRAM_MODEL_ID);
    }
}
