/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.ui.tools.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.ValidationMessagePanel;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.history.HistoryStackFrame;
import org.kuali.student.common.ui.client.widgets.field.layout.FieldElement;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class NestedSectionView extends SectionView{
    
    private boolean loaded = false;
    private String modelId;
    
    private DataModel model;
    private FlowPanel layout = new FlowPanel();
    private Class<? extends ModelDTO> modelDTOType;

    public NestedSectionView (Enum<?> viewEnum, String name, Class<? extends ModelDTO> modelDTOType) {     
        super(viewEnum, name);
        super.initWidget(layout);
        this.modelDTOType = modelDTOType; 
        //instructions.setText("INSTRUCTIONS");
        layout.setStyleName("ks-form-module");
        sectionTitle.addStyleName("ks-heading-page-title");
    }
    
    public NestedSectionView(Enum<?> viewEnum, String name, String modelId) {     
        super(viewEnum, name);
        super.initWidget(layout);
        this.modelId = modelId;
        //instructions.setText("INSTRUCTIONS");
        layout.setStyleName("ks-form-module");
        sectionTitle.addStyleName("ks-heading-page-title");
    }
    
    
/*  public VerticalSection(){
        layout.add(this.sectionTitle);
        this.initWidget(layout);
    }
    
    public VerticalSection(SectionTitle title){
        this.sectionTitle = title;
        layout.add(this.sectionTitle);
        this.instructions.setVisible(false);
        layout.add(this.instructions);
        layout.add(this.message);
        this.initWidget(layout);
    }
    
    public VerticalSection(SectionTitle title, String instructions){
        this.sectionTitle = title;
        layout.add(this.sectionTitle);
        this.setInstructions(instructions);
        layout.add(this.instructions);
        layout.add(this.message);
        this.initWidget(layout);
    }*/
    
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback){
        super.beforeShow(new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {
                if (!loaded){
                    layout.clear();
                    layout.add(NestedSectionView.this.sectionTitle);
                    layout.add(NestedSectionView.this.instructions);
                    layout.add(NestedSectionView.this.message);
                    for(Section ns: sections){
                        ns.redraw();
                        NestedSectionView.this.addSectionToLayout((BaseSection)ns);
                    }
                    for(FieldDescriptor f: fields){
                        NestedSectionView.this.addFieldToLayout(f);
                    }
                    
                    loaded = true;
                }

                //Request model and redraw view
                getController().requestModel(modelId, new ModelRequestCallback<DataModel>(){

                    @Override
                    public void onRequestFail(Throwable cause) {
                        Window.alert("Failed to get model: " + getName());
                        onReadyCallback.exec(false);
                    }

                    @Override
                    public void onModelReady(DataModel m) {
                        model = m;
                        redraw();
                        onReadyCallback.exec(true);
                    }
                    
                });
            }
        });

    }
    
    @Override
    protected void addFieldToLayout(FieldDescriptor fieldDescriptor) {
        FieldElement field = new FieldElement(fieldDescriptor);
        FlowPanel fieldContainer = new FlowPanel();
        FlowPanel fieldLayout = new FlowPanel();
        fieldContainer.add(field);
        ValidationMessagePanel validationPanel = new ValidationMessagePanel();
        fieldLayout.add(fieldContainer);
        fieldLayout.add(validationPanel);
        FieldInfo info = new FieldInfo(fieldDescriptor.getFieldLabel(), validationPanel, field, fieldLayout);
        pathFieldInfoMap.put(fieldDescriptor.getFieldKey(), info);
        layout.add(fieldLayout);
        fieldLayout.setStyleName("ks-form-module-group");
        fieldLayout.addStyleName("clearfix");
        fieldContainer.setStyleName("ks-form-module-fields");
/*        FieldElement field = new FieldElement(fieldDescriptor);
        FlowPanel panel = new FlowPanel();
        panel.add(field);
        ValidationMessagePanel validationPanel = new ValidationMessagePanel();
        panel.add(validationPanel);
        pathValidationPanelMap.put(fieldDescriptor.getFieldKey(), validationPanel);
        layout.add(panel);
        panel.setStyleName("ks-form-module-fields");*/
    }

    @Override
    protected void removeSectionFromLayout(BaseSection section) {
        layout.remove(section);
    }

    @Override
    protected void addSectionToLayout(BaseSection section) {
        layout.add(section);
    }
    
    @Override
    public void redraw() {
        super.updateView(model);
        if (sections != null) {
            for(Section ns: sections){
                ns.redraw();
            }
        }
/*        layout.clear();
        layout.add(this.sectionTitle);
        layout.add(this.instructions);
        layout.add(this.message);
        for(Section ns: sections){
            ns.redraw();
        }
        for(FieldDescriptor f: fields){
            this.addFieldToLayout(f);
        }*/
        //Fire validation request here?
    }

    @Override
    public void clear() {
        
    }

    @Override
    protected void addWidgetToLayout(Widget w) {
        layout.add(w);
    }
    
    /**
     * This updates the model
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#updateModel()
     */
    @Override
    @SuppressWarnings("unchecked")
    public void updateModel() {
        if(model!=null){
            super.updateModel(model);
        }
    }

    public void updateView() {
        getController().requestModel(modelId, new ModelRequestCallback<DataModel>(){
            @Override
            public void onModelReady(DataModel m) {
                // TODO review this, shouldn't it assign this.model = m?
                updateView(m);                
            }

        
            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert("Failed to get model");
            }            
        });
        
    }
    
    
    @Override
    public void collectHistory(HistoryStackFrame frame) {
        // do nothing
    }

    @Override
    public void onHistoryEvent(HistoryStackFrame frame) {
        // do nothing
    }
}
