package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

import com.google.gwt.user.client.ui.FlowPanel;

public class CustomNestedSection extends Section{
    
    private FlowPanel panel = new FlowPanel();
    private RowDescriptor currentRow = new RowDescriptor();
 
    public CustomNestedSection(){
        super.initWidget(panel);
    }
    
    public void nextRow(){
        rows.add(currentRow);
        currentRow = new RowDescriptor();
    }

    @Override
    public void clear() {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
    
    public FieldLabelType getCurrentFieldLabelType() {
        return currentRow.getCurrentFieldLabelType();
    }

    public void setCurrentFieldLabelType(FieldLabelType currentFieldLabelType) {
        currentRow.setCurrentFieldLabelType(currentFieldLabelType);
    }
    
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        fields.add(fieldDescriptor);
        currentRow.addField(fieldDescriptor);
    }

    @Override
    public void addSection(Section section) {
        sections.add(section);
        currentRow.addSection(section);
    }

    @Override
    public void redraw() {
        
        //Add a "REAL" redraw/recreate capability here?  Not needed at the current time
        panel.clear();
        //TODO add title type check here
        panel.add(generateTitlePanel());
        panel.add(instructionsLabel);
        for(Section ns: sections){
            ns.redraw();
        }
        if(currentRow.getFields().size() != 0 || currentRow.getSections().size() != 0){
            rows.add(currentRow);
        }
        for(RowDescriptor r: rows){
            panel.add(r);
        }
        
        
    }

    @Override
    public void validate(Callback<org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel> callback) {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }

	@Override
	public void processValidationResults(List<ValidationResultContainer> results) {
		for(RowDescriptor r: rows){
			r.clearValidationMessages();
			for(FieldDescriptor f: r.getFields()){
				for(ValidationResultContainer vc: results){
					if(vc.getElement().equals(f.getFieldKey())){
						r.setValidationMessages(vc.getValidationResults());
					}
				}
			}
		}
		
		for(Section s: sections){
			s.processValidationResults(results);
		}
		
	}
    
    

}
