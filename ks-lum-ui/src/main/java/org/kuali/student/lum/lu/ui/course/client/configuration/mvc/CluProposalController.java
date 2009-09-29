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
package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class CluProposalController extends PagedSectionLayout{
    private Model<CluProposalModelDTO> cluProposalModel;
    private Model<Collaborators.CollaboratorModel> collaboratorModel;

	private String docId = null;
	
	private final String CLU_PROPOSAL_ID_KEY   = "proposalInfo/id";
	private final String CLU_PROPOSAL_NAME_KEY = "proposalInfo/name";

	private String PROPOSAL_STATE = "draft.private";	
    private String PROPOSAL_TYPE = "kuali.proposal.type.course.create";
    
	private final String CLU_TYPE = "kuali.lu.type.CreditCourse";
	private final String CLU_STATE = "draft";
	
	private final String REFERENCE_TYPE = "referenceType.clu";
	
	CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
    private boolean savedOnce=false;
    
    private KSButton saveButton = new KSButton("Save", new ClickHandler(){
        public void onClick(ClickEvent event) {
            fireApplicationEvent(new SaveActionEvent());
        }
    });
        
    private KSButton testButton = new KSButton("Get Proposal Info", new ClickHandler(){
        public void onClick(ClickEvent event) {
            cluProposalRpcServiceAsync.getProposal(cluProposalModel.get().getString(CLU_PROPOSAL_ID_KEY), 
                    new AsyncCallback<CluProposalModelDTO>(){

                public void onFailure(Throwable caught) {
                	Window.alert("Error loading Proposal: "+caught.getMessage());
                }

                public void onSuccess(CluProposalModelDTO result) {                    
                    Window.alert(result.toString());
                }
                
            });
        }       
    });
    
    public CluProposalController(String docId) {
    	super();
    	this.docId = docId;
    	init();
	}
    
    public CluProposalController(){
        super();
        init();
    }
    
    private void init(){
        final String objectKey = "org.kuali.student.lum.lu.dto.CluInfo";
        String typeKey ="type";
        String stateKey = "state";
        
        LuConfigurer.configureCluProposal(this, objectKey, typeKey, stateKey);
        addButton(saveButton);
        addButton(testButton);
        cluProposalModel = null;
        this.setModelDTOType(CluProposalModelDTO.class);
        addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
            public void doSave(SaveActionEvent saveAction) {
                GWT.log("CluProposalController received save action request.", null);
                doSaveAction(saveAction);
            }            
        });
        requestModel(CluProposalModelDTO.class, new ModelRequestCallback(){

            @Override
            public void onModelReady(Model model) {
                CluProposalController.this.setModelDTO((ModelDTO)model.get(), CluDictionaryClassNameHelper.getClasstoObjectKeyMap());
                
            }

            @Override
            public void onRequestFail(Throwable cause) {
                // TODO joeyin - THIS METHOD NEEDS JAVADOCS
                
            }
            
        });
    }
        
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
     */
    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return LuConfigurer.LuSections.class;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
        if (modelType == CluProposalModelDTO.class){
            if (cluProposalModel == null){
            	if(docId!=null){
            		cluProposalRpcServiceAsync.getCluProposalFromWorkflowId(docId, new AsyncCallback<CluProposalModelDTO>(){

            			@Override
						public void onFailure(Throwable caught) {
            				Window.alert("error loading Clu: "+docId+". "+caught.getMessage());
            				createNewCluProposalModel();
            				callback.onModelReady(cluProposalModel);
						}

						@Override
						public void onSuccess(CluProposalModelDTO result) {
		            		cluProposalModel = new Model<CluProposalModelDTO>();
		                	cluProposalModel.put(result);
							callback.onModelReady(cluProposalModel);
						}
            			
            		});
            		
            	}else{
                    createNewCluProposalModel();            	    
                    callback.onModelReady(cluProposalModel);
            	}
                
            } else {
                callback.onModelReady(cluProposalModel); 
            }
        } else if(modelType == ReferenceModel.class){
        	if (cluProposalModel != null){
        		ReferenceModel ref = new ReferenceModel();

        		//FIXME: test code
        		if(cluProposalModel.get() != null && cluProposalModel.get().getString(CLU_PROPOSAL_ID_KEY) != null){
            		ref.setReferenceId(cluProposalModel.get().getString(CLU_PROPOSAL_ID_KEY));
        		}
        		else{
        			ref.setReferenceId(null);
        		}
        		
        		ref.setReferenceTypeKey(REFERENCE_TYPE);
        		ref.setReferenceType(CLU_TYPE);
        		ref.setReferenceState(CLU_STATE);
        		Model<ReferenceModel> model = new Model<ReferenceModel>();
        		model.put(ref);
        		callback.onModelReady(model);
        	}
        } else if(modelType == Collaborators.CollaboratorModel.class){
        	//Update the collabmodel with info from the CluProposal Model
        	//Create a new one if it does not yet exist
        	if(null==collaboratorModel){
        		Collaborators.CollaboratorModel collab = new Collaborators.CollaboratorModel();
        		collaboratorModel = new Model<Collaborators.CollaboratorModel>();
        		collaboratorModel.put(collab);
        	}
        	String proposalId="";
        	if(cluProposalModel!=null&&cluProposalModel.get()!=null&&cluProposalModel.get().get(CLU_PROPOSAL_ID_KEY)!=null){
        		proposalId=cluProposalModel.get().getString(CLU_PROPOSAL_ID_KEY);
        	}
        	collaboratorModel.get().setProposalId(proposalId);    
        	callback.onModelReady(collaboratorModel);
        } else {
            super.requestModel(modelType, callback);
        }
    }
    
    private void createNewCluProposalModel(){
        cluProposalModel = new Model<CluProposalModelDTO>();                
        cluProposalModel.put(new CluProposalModelDTO());
        
        cluProposalModel.get().put("cluInfo/type", CLU_TYPE);
        cluProposalModel.get().put("cluInfo/state", CLU_STATE);

        cluProposalModel.get().put("proposalInfo/type", PROPOSAL_TYPE);
        cluProposalModel.get().put("proposalInfo/state", PROPOSAL_STATE);        
    }
    
    public void doSaveAction(SaveActionEvent saveActionEvent){
        String proposalName = ((ModelDTO)cluProposalModel.get()).getString(CLU_PROPOSAL_NAME_KEY);

        if (proposalName == null){
            showStartSection();
        } else {
            saveProposalClu(saveActionEvent);
        }
    }
    
    public void saveProposalClu(final SaveActionEvent saveActionEvent){
        View v = getCurrentView();
        getCurrentView().updateModel();
        
        if(!savedOnce){
            cluProposalRpcServiceAsync.createProposal(cluProposalModel.get(), new AsyncCallback<CluProposalModelDTO>(){
                public void onFailure(Throwable caught) {
                    caught.printStackTrace();                        
                }

                public void onSuccess(CluProposalModelDTO result) {
                    cluProposalModel.put(result);
                    saveActionEvent.doActionComplete();                    
                }
            });
            savedOnce = true;
        }
        else{
            
            cluProposalRpcServiceAsync.saveProposal(cluProposalModel.get(), new AsyncCallback<CluProposalModelDTO>(){
                public void onFailure(Throwable caught) {
                    caught.printStackTrace();                    
                }

                public void onSuccess(CluProposalModelDTO result) {
                    cluProposalModel.put(result);
                    saveActionEvent.doActionComplete();                    
                }
            });
        }        
    }
}
