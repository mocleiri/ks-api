/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.ui.course.client.configuration.course;

import java.util.List;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.common.ui.client.configurable.mvc.TabbedSectionLayout;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.event.ValidateResultHandler;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.HasActionState.ActionState;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.LuConfigurer;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class CourseProposalController extends TabbedSectionLayout{ 
    private Model<org.kuali.student.common.assembly.client.Model> cluProposalModel; 
    private Model<Collaborators.CollaboratorModel> collaboratorModel;
    
    private WorkQueue modelRequestQueue;

	private String docId = null;
	private String proposalType = null;
	private String cluType = null;
	private SaveActionEvent currentSaveEvent = null;
    private boolean processingSave = false;
    private String proposalId = null;
	
	private final String CLU_PROPOSAL_ID_KEY   = "proposalInfo/id";
	private final String CLU_PROPOSAL_NAME_KEY = "proposalInfo/name";

	private String PROPOSAL_STATE = "draft.private";	
	private final String CLU_STATE = "draft";
	
	private final String REFERENCE_TYPE = "referenceType.clu";
	
	CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
    private boolean savedOnce=false;
    
    private KSButton saveButton = new KSButton("Save", new ClickHandler(){
        public void onClick(ClickEvent event) {
            fireApplicationEvent(new SaveActionEvent());
        }
    });
        
    private KSButton quitButton = new KSButton("Quit", new ClickHandler(){
        public void onClick(ClickEvent event) {
            Controller parentController = CourseProposalController.this.getParentController(); 
            parentController.fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.HOME_MENU, event));
        }
    });
        
    public CourseProposalController(){
        super();
        init();
    }
    
    private void init(){
        
        CourseConfigurer.configureCourseProposal(this);
        
/*
        else if (proposalType == LUConstants.PROPOSAL_TYPE_PROGRAM_CREATE) { 
        	proposalType = LUConstants.PROPOSAL_TYPE_COURSE_CREATE; //FIXME: remove when we have programs in dictionary...
        	cluType = LUConstants.CLU_TYPE_CREDIT_COURSE; //FIXME: remove when we have programs in dictionary...
        	CourseConfigurer.configureProgramProposal(this, objectKey, typeKey, stateKey);
        }
*/        
        
        addButton(saveButton);
        addButton(quitButton);
        
        addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
            public void doSave(SaveActionEvent saveAction) {
                GWT.log("CluProposalController received save action request.", null);
                doSaveAction(saveAction);
            }            
        });
        
        addApplicationEventHandler(ValidateResultEvent.TYPE, new ValidateResultHandler() {
            @Override
            public void onValidateResult(ValidateResultEvent event) {
            	if(processingSave){
            		List<ValidationResultContainer> list = event.getValidationResult();
            		ErrorLevel errorLevel = checkForErrors(list);
            		if(errorLevel.equals(ErrorLevel.ERROR)){
            			//TODO replace with a ks modal
            			Window.alert("Validation failed.  The proposal could not be saved.  Please check fields for errors.");
            		}
            		else if(errorLevel.equals(ErrorLevel.WARN)){
            			//TODO do something else for warning level?
            			saveProposalClu(currentSaveEvent);
            		}
            		else{
            			saveProposalClu(currentSaveEvent);
            		}
            		processingSave = false;
            		currentSaveEvent = null;
            	}
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
    public void requestModel(String modelId, final ModelRequestCallback modelRequestCallback) {
        if (modelRequestQueue == null){
            modelRequestQueue = new WorkQueue();
        }

        if (modelId.equals(CourseConfigurer.CLU_PROPOSAL_MODEL)){
            WorkItem workItem = new WorkItem(){
                @Override
                public void exec(Callback<Boolean> workCompleteCallback) {
                    if (cluProposalModel == null){
                        if(docId!=null){
                            getCluProposalFromWorkflowId(modelRequestCallback, workCompleteCallback);
                        } else if (proposalId != null){
                            getCluProposalFromProposalId(modelRequestCallback, workCompleteCallback);
                        } else{
                            createNewCluProposalModel(modelRequestCallback, workCompleteCallback);
                        }                
                    } else {
                        modelRequestCallback.onModelReady(cluProposalModel);
                        workCompleteCallback.exec(true);
                    }
                }               
            };
            modelRequestQueue.submit(workItem);
        } else{
            super.requestModel(modelId, modelRequestCallback);
        }
    }

    
    @SuppressWarnings("unchecked")
    @Override
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
        if(modelType == ReferenceModel.class){
        	if (cluProposalModel != null){
        		ReferenceModel ref = new ReferenceModel();

        		//FIXME: test code
        		if(cluProposalModel.get() != null && cluProposalModel.get().get("cluInfo/id") != null){
            		ref.setReferenceId((String)cluProposalModel.get().get("cluInfo/id"));
        		}
        		else{
        			ref.setReferenceId(null);
        		}
        		
        		ref.setReferenceTypeKey(REFERENCE_TYPE);
        		ref.setReferenceType(cluType);
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
        		proposalId=cluProposalModel.get().get(CLU_PROPOSAL_ID_KEY);
        	}
        	collaboratorModel.get().setProposalId(proposalId);    
        	callback.onModelReady(collaboratorModel);
        } else {
            super.requestModel(modelType, callback);
        }       
    }
    
    @SuppressWarnings("unchecked")        
    private void getCluProposalFromWorkflowId(final ModelRequestCallback callback, Callback<Boolean> workCompleteCallback){
/*        
        cluProposalRpcServiceAsync.getCluProposalFromWorkflowId(docId, new AsyncCallback<CluProposalModelDTO>(){

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+docId+". "+caught.getMessage());
                createNewCluProposalModel();
                callback.onModelReady(cluProposalModel);
                //Set it up for validation in layoutController
                CourseProposalController.this.setModelDTO(cluProposalModel.get(), CluDictionaryClassNameHelper.getClasstoObjectKeyMap());

            }

            @Override
            public void onSuccess(CluProposalModelDTO result) {
                cluProposalModel = new Model<CluProposalModelDTO>();
                cluProposalModel.put(result);
                callback.onModelReady(cluProposalModel);
                CourseProposalController.this.setModelDTO(cluProposalModel.get(), CluDictionaryClassNameHelper.getClasstoObjectKeyMap());                
            }
            
        });
*/
    }
    
    @SuppressWarnings("unchecked")    
    private void getCluProposalFromProposalId(final ModelRequestCallback callback, Callback<Boolean> workCompleteCallback){
/*
        cluProposalRpcServiceAsync.getProposal(proposalId, 
                new AsyncCallback<CluProposalModelDTO>(){

            public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel();
                callback.onModelReady(cluProposalModel);
                CourseProposalController.this.setModelDTO(cluProposalModel.get(), CluDictionaryClassNameHelper.getClasstoObjectKeyMap());                
            }

            public void onSuccess(CluProposalModelDTO result) {                    
                cluProposalModel = new Model<CluProposalModelDTO>();
                cluProposalModel.put(result);
                callback.onModelReady(cluProposalModel);
                CourseProposalController.this.setModelDTO(cluProposalModel.get(), CluDictionaryClassNameHelper.getClasstoObjectKeyMap());                
            }});                                        
*/    
    }
    
    @SuppressWarnings("unchecked")
    private void createNewCluProposalModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        if (cluProposalModel == null){
            cluProposalModel = new Model<org.kuali.student.common.assembly.client.Model>();

            cluProposalRpcServiceAsync.getCluProposalModelDefinition(CourseConfigurer.CLU_PROPOSAL_MODEL, 
                new AsyncCallback<org.kuali.student.common.assembly.client.Model>(){

                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Failed to get model definition from server.");                        
                    }

                    @Override
                    public void onSuccess(org.kuali.student.common.assembly.client.Model result) {
                        cluProposalModel.put(result);                           
                        callback.onModelReady(cluProposalModel);
                        workCompleteCallback.exec(true);
                        GWT.log("Retrieved course clu proposal model defintion.", null);
                    }                
            });
                        
        } else {
            cluProposalModel.get().setRoot(new Data());
            callback.onModelReady(cluProposalModel);
            workCompleteCallback.exec(true);            
        }
    }

    
    public void doSaveAction(SaveActionEvent saveActionEvent){       
        String proposalName = cluProposalModel.get().get(CLU_PROPOSAL_NAME_KEY);
        currentSaveEvent = saveActionEvent;
        if (proposalName == null){
            showStartSection();
        } else {
            getStartSection().updateModel();
            
            getCurrentView().updateModel();
            
            this.updateModel();
            
            saveProposalClu(saveActionEvent);
//            processingSave=true;
//            View v = getCurrentView();
//        	if(v instanceof SectionView){
//        		((SectionView) v).setFieldHasHadFocusFlags(true);
//        		this.validate((SectionView)v);
//        	}
        }       
    }
    
    public void saveProposalClu(final SaveActionEvent saveActionEvent){
        final KSLightBox saveWindow = new KSLightBox();
        final KSLabel saveMessage = new KSLabel(saveActionEvent.getMessage() + "...");
        final OkGroup buttonGroup = new OkGroup(new Callback<OkEnum>(){
                
                @Override
                public void exec(OkEnum result) {
                    saveWindow.hide();
                    saveActionEvent.doActionComplete();                
                }
            });

        buttonGroup.setWidth("250px");
        buttonGroup.getButton(OkEnum.Ok).setEnabled(false);
        buttonGroup.setContent(saveMessage);

        
        if (saveActionEvent.isAcknowledgeRequired()){
            saveWindow.setWidget(buttonGroup);
        } else {
            saveWindow.setWidget(saveMessage);
        }
        saveWindow.show();

        if(cluProposalModel.get().get("cluInfo/id") == null){
            cluProposalRpcServiceAsync.saveData(cluProposalModel.get().getRoot(), new AsyncCallback<Data>(){
                public void onFailure(Throwable caught) {
                    GWT.log("Save Failed.", caught);
                    saveWindow.setWidget(buttonGroup);
                    saveMessage.setText("Save Failed!  Please Try Again.");
                    buttonGroup.getButton(OkEnum.Ok).setEnabled(true);                    
                }

                public void onSuccess(Data result) {
                    cluProposalModel.get().setRoot(result);
                    if (saveActionEvent.isAcknowledgeRequired()){
                        saveMessage.setText("Save Successful");
                        buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
                    } else {
                        saveWindow.hide();
                        saveActionEvent.doActionComplete();                        
                    }                    
                }
            });
            savedOnce = true;
        }
    }


    public String getDocId() {
        return docId;
    }


    public void setDocId(String docId) {
        this.docId = docId;
        this.proposalId = null;
        this.cluProposalModel = null;
    }


    public String getProposalId() {
        return proposalId;
    }


    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
        this.docId = null;
        this.cluProposalModel = null;        
    }
    
    public void clear(String proposalType, String cluType){
        super.clear();
        this.proposalType = proposalType;
        this.cluType = cluType;
        if (cluProposalModel != null){
            this.cluProposalModel.get().setRoot(new Data());            
        }
        this.setModelDTO(null, null);
        this.docId = null;
        this.proposalId = null;
        this.savedOnce=false;
    }
    
    
}
