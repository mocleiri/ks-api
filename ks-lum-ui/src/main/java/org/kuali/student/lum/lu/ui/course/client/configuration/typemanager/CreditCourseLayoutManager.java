/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration.typemanager;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.PropertyBinding;
import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.event.SaveHandler;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.SimpleConfigurableSection;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.AcademicContentLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.AdminstrativeLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.AttachmentsLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.ProposalInformationLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.StudentEligibilityLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.ViewsLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextBox;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class CreditCourseLayoutManager {

    LuRpcServiceAsync luRpcServiceAsync = GWT.create(LuRpcService.class);
    
    private Validator validator;

//    protected  CreditCourseLayoutManager() {
//        super();
//    }
//
//    public CreditCourseLayoutManager( Validator validator) {
//        super();
//        this.validator = validator;
//    }

    public DefaultCreateUpdateLayout<CluProposal> getCreateUpdateLayout(String type, String state) {

        DefaultCreateUpdateLayout<CluProposal> layout = new DefaultCreateUpdateLayout<CluProposal>();
        CluProposal cluProposal = new CluProposal();
        CluInfo cluInfo = new CluInfo();
        cluInfo.setState(LUConstants.LU_STATE_PROPOSED);
        
        cluProposal.setCluInfo(cluInfo);        
        layout.setObject(cluProposal);
        
        layout = addStartSection(layout);
        layout = addViewsSection(layout, type, state);
        layout = addProposalInformationSection(layout, type, state);
        layout = addAcademicContentSection(layout, type, state);
        layout = addStudentEligibilitySection(layout, type, state);
        layout = addAdministrativeSection(layout, type, state);
        layout = addAttachmentsSection(layout, type, state);
                
        return layout;
    }

    private DefaultCreateUpdateLayout<CluProposal> addStartSection(DefaultCreateUpdateLayout<CluProposal> layout){
        final SimpleConfigurableSection<CluProposal> startCluProposalSection = new SimpleConfigurableSection<CluProposal>();  
        
        layout.addStartSection(startCluProposalSection
                .addField(new ConfigurableField<CluProposal>()
                        .setBinding(new PropertyBinding<CluProposal>() {
                            @Override
                            public Object getValue(CluProposal object) {
                                return object.getCluInfo().getOfficialIdentifier().getLongName();
                            }
                            @Override
                            public void setValue(CluProposal object, Object value) {
                                CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
                                cluIdentifier.setLongName((String)value);
                                object.getCluInfo().setOfficialIdentifier(cluIdentifier);
                            }
                        })
                        .setFormField(new KSFormField("proposedCourseTitle", "Proposed Course Title")
                        .setWidget(new TextBox())
                        .setHelpInfo(new HelpInfo("myhelpid", "help short version", "help title",
                        "http://www.google.com/")
                        )
                        )
                )
                .setSectionTitle("Begin Proposal")
                .setParentLayout(layout)
        );
        
        layout.addSaveStartSectionHandler(new SaveHandler(){
            public void onSave(SaveEvent saveEvent) {
                startCluProposalSection.updateObject();
                CluInfo cluInfo = ((CluProposal)startCluProposalSection.getParentLayout().getObject()).getCluInfo();
                luRpcServiceAsync.createClu(LUConstants.LU_TYPE_COURSE, cluInfo, new AsyncCallback<CluInfo>(){

                    @Override
                    public void onFailure(Throwable caught) {
                        //TODO: How to display error and prevent continue                        
                    }

                    @Override
                    public void onSuccess(CluInfo result) {
                        ((CluProposal)startCluProposalSection.getParentLayout().getObject()).setCluInfo(result);
                    }                    
                });
            }});
        return layout;
    }
    
    private DefaultCreateUpdateLayout<CluProposal> addViewsSection(DefaultCreateUpdateLayout<CluProposal> layout,
        String type, String state) {
            ViewsLayoutManager manager = new ViewsLayoutManager(layout);
            return manager.addSection( type, state);
    }

    private DefaultCreateUpdateLayout<CluProposal> addProposalInformationSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {
        ProposalInformationLayoutManager manager = new ProposalInformationLayoutManager(layout);
        return manager.addSection( type, state);

    }

    private DefaultCreateUpdateLayout<CluProposal> addAcademicContentSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        AcademicContentLayoutManager manager = new AcademicContentLayoutManager(layout);
        return manager.addSection( type, state);
    }

    private DefaultCreateUpdateLayout<CluProposal> addStudentEligibilitySection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        StudentEligibilityLayoutManager manager = new StudentEligibilityLayoutManager(layout);
        return manager.addSection( type, state);

    }

    private DefaultCreateUpdateLayout<CluProposal> addAdministrativeSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        AdminstrativeLayoutManager manager = new AdminstrativeLayoutManager(layout);
        return manager.addSection( type, state);
    }

    private DefaultCreateUpdateLayout<CluProposal> addAttachmentsSection(DefaultCreateUpdateLayout<CluProposal> layout,
            String type, String state) {

        AttachmentsLayoutManager manager = new AttachmentsLayoutManager(layout);
        return manager.addSection( type, state);
    }

}
