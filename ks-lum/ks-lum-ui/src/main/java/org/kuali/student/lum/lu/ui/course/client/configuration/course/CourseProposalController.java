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

package org.kuali.student.lum.lu.ui.course.client.configuration.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.application.ViewContext.IdType;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuSectionController;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabbedSectionLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.event.ChangeViewActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.event.SubmitProposalEvent;
import org.kuali.student.common.ui.client.event.SubmitProposalHandler;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.event.ValidateRequestHandler;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.service.WorkflowRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.workflow.ui.client.widgets.ContentConfigurer;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedController;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowToolbar;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseReqSummaryHolder;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.widgets.CollaboratorTool;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Controller for course proposal screens
 *
 * @author Kuali Student Team
 *
 */

public class CourseProposalController extends MenuSectionController implements RequiresAuthorization, WorkflowEnhancedController{

    //RPC Services
    CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);

    //Models
    private final DataModel cluProposalModel = new DataModel();

    //Configuration
    AbstractCourseConfigurer cfg = GWT.create(CourseConfigurer.class);

    private WorkQueue modelRequestQueue;

    private WorkflowUtilities workflowUtil;

    private boolean initialized = false;

    private BlockingTask initializingTask = new BlockingTask("Loading");
    private BlockingTask loadDataTask = new BlockingTask("Retrieving Data");

    public CourseProposalController(){
        super(CourseProposalController.class.getName());
        setViewContext(new ViewContext());
        initialize();
    }

    public CourseProposalController(ViewContext viewContext){
        super(CourseProposalController.class.getName());
        setViewContext(viewContext);
        initialize();
    }

    public CourseProposalController(ViewContext viewContext, KSTitleContainerImpl layoutTitle){
        super(CourseProposalController.class.getName());
        //this.setContentTitle()
        setViewContext(viewContext);
        initialize();
    }

    private void initialize() {
        super.setDefaultModelId(cfg.getModelId());
        super.registerModel(cfg.getModelId(), new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (modelRequestQueue == null){
                    modelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (cluProposalModel.getRoot() == null || cluProposalModel.getRoot().size() == 0){
                            if(getViewContext().getIdType() == IdType.DOCUMENT_ID){
                                getCluProposalFromWorkflowId(callback, workCompleteCallback);
                            } else if (getViewContext().getIdType() == IdType.KS_KEW_OBJECT_ID){
                                getCluProposalFromProposalId(callback, workCompleteCallback);
                            } else if (getViewContext().getIdType() == IdType.COPY_OF_OBJECT_ID){
                                getNewProposalWithCopyOfClu(callback, workCompleteCallback);
                            } else{
                                createNewCluProposalModel(callback, workCompleteCallback);
                            }
                        } else {
                            callback.onModelReady(cluProposalModel);
                            workCompleteCallback.exec(true);
                        }
                    }
                };
                modelRequestQueue.submit(workItem);

            }

        });
        super.addApplicationEventHandler(ValidateRequestEvent.TYPE, new ValidateRequestHandler() {

            @Override
            public void onValidateRequest(ValidateRequestEvent event) {
                requestModel(new ModelRequestCallback<DataModel>() {
                    @Override
                    public void onModelReady(DataModel model) {
                        model.validate(new Callback<List<ValidationResultInfo>>() {
                            @Override
                            public void exec(List<ValidationResultInfo> result) {
                                ValidateResultEvent e = new ValidateResultEvent();
                                e.setValidationResult(result);
                                fireApplicationEvent(e);
                            }
                        });
                    }

                    @Override
                    public void onRequestFail(Throwable cause) {
                        GWT.log("Unable to retrieve model for validation", cause);
                    }

                });
            }

        });
    }

    private KSButton getSaveButton(){
        return new KSButton("Save & Continue", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        fireApplicationEvent(new SaveActionEvent());
                    }
                });
    }

    private void init(final Callback<Boolean> onReadyCallback) {

        if (initialized) {
            onReadyCallback.exec(true);
        } else {
            KSBlockingProgressIndicator.addTask(initializingTask);

            String idType = null;
            String viewContextId = null;
            // The switch was added due to the way permissions currently work.
            // For a new Create Course Proposal or Modify Course we send nulls so that permissions are not checked.
            if(getViewContext().getIdType() != null){
                idType = getViewContext().getIdType().toString();
                viewContextId = getViewContext().getId();
                if(getViewContext().getIdType()==ViewContext.IdType.COPY_OF_OBJECT_ID){
                    viewContextId = null;
                }

//              switch (getViewContext().getIdType()) {
//                    case KS_KEW_OBJECT_ID :
//                        idType = getViewContext().getIdType().toString();
//                        viewContextId = getViewContext().getId();
//                        break;
//                    case DOCUMENT_ID :
//                        idType = getViewContext().getIdType().toString();
//                        viewContextId = getViewContext().getId();
//                        break;
//                }
            }

            cluProposalRpcServiceAsync.getMetadata(idType, viewContextId,
                    new AsyncCallback<Metadata>(){

                public void onFailure(Throwable caught) {
                            onReadyCallback.exec(false);
                            KSBlockingProgressIndicator.removeTask(initializingTask);
                            throw new RuntimeException("Failed to get model definition.", caught);
                        }

                        public void onSuccess(Metadata result) {
                            DataModelDefinition def = new DataModelDefinition(result);
                            cluProposalModel.setDefinition(def);
                            init(def);
                            initialized = true;
                            onReadyCallback.exec(true);
                            KSBlockingProgressIndicator.removeTask(initializingTask);
                        }
                });
        }
    }

    private void init(DataModelDefinition modelDefinition){
        //FIXME: [KSCOR-225] This needs to be moved to the configurer
        workflowUtil = new WorkflowUtilities((WorkflowRpcServiceAsync)GWT.create(CreditCourseProposalRpcService.class), this,
                "proposal/id", createOnWorkflowSubmitSuccessHandler());
        workflowUtil.setRequiredFieldPaths(new String[]{"course/department"});

        cfg.setModelDefinition(modelDefinition);
        cfg.configure(this);
        this.setContentTitle("New Course");

        if (!initialized){
            //TODO make a class with static message helper methods
            addCommonButton(LUConstants.COURSE_SECTIONS, getSaveButton());

            addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
                public void doSave(SaveActionEvent saveAction) {
                    GWT.log("CluProposalController received save action request.", null);
                    doSaveAction(saveAction);
                }
            });

            addApplicationEventHandler(SubmitProposalEvent.TYPE, new SubmitProposalHandler(){
                public void onSubmitProposal() {
                    GWT.log("CluProposalController received submit proposal request.", null);
                    CourseProposalController.this.updateModel();
                }
            });
        }

        initialized = true;
    }

    private CloseHandler<KSLightBox> createOnWorkflowSubmitSuccessHandler() {
        CloseHandler<KSLightBox> handler = new CloseHandler<KSLightBox>(){
            @Override
            public void onClose(CloseEvent<KSLightBox> event) {
                //Reload the lum main entrypoint
                Window.Location.reload();
            }
        };
        return handler;
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
     */
    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return cfg.getViewsEnum();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
        if(modelType == ReferenceModel.class){
            if (cluProposalModel != null){
                ReferenceModel ref = new ReferenceModel();

                if(cluProposalModel.get(cfg.getProposalIdPath()) != null){
                    ref.setReferenceId((String)cluProposalModel.get(cfg.getProposalIdPath()));
                } else {
                    ref.setReferenceId(null);
                }

                ref.setReferenceTypeKey(cfg.getProposalReferenceTypeKey());
                ref.setReferenceType(cfg.getProposalReferenceObjectType());
                ref.setReferenceState(getViewContext().getState());

                callback.onModelReady(ref);
            }
        } else if(modelType == CollaboratorTool.CollaboratorModel.class){
            CollaboratorTool.CollaboratorModel collaboratorModel = new CollaboratorTool.CollaboratorModel();
            String proposalId=null;
            if(cluProposalModel!=null && cluProposalModel.get(cfg.getProposalIdPath())!=null){
                proposalId=cluProposalModel.get(cfg.getProposalIdPath());
            }
            collaboratorModel.setDataId(proposalId);
            callback.onModelReady(collaboratorModel);

        }else if (modelType == LuData.class){
            requestModel(cfg.getModelId(), callback);
        } else {
            super.requestModel(modelType, callback);
        }

    }

    @SuppressWarnings("unchecked")
    private void getCluProposalFromWorkflowId(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        KSBlockingProgressIndicator.addTask(loadDataTask);
        cluProposalRpcServiceAsync.getDataFromWorkflowId(getViewContext().getId(), new AsyncCallback<Data>(){

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal from docId: "+getViewContext().getId()+". "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);

            }

            @Override
            public void onSuccess(Data result) {
                cluProposalModel.setRoot(result);
                setProposalHeaderTitle();
                callback.onModelReady(cluProposalModel);
                workCompleteCallback.exec(true);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

        });

    }

    @SuppressWarnings("unchecked")
    private void getCluProposalFromProposalId(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        KSBlockingProgressIndicator.addTask(loadDataTask);
        cluProposalRpcServiceAsync.getData(getViewContext().getId(), new AsyncCallback<Data>(){

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

            @Override
            public void onSuccess(Data result) {
                cluProposalModel.setRoot(result);
                setProposalHeaderTitle();
                callback.onModelReady(cluProposalModel);
                workCompleteCallback.exec(true);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

        });
    }

    @SuppressWarnings("unchecked")
    private void getNewProposalWithCopyOfClu(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        KSBlockingProgressIndicator.addTask(loadDataTask);
        cluProposalRpcServiceAsync.getNewProposalWithCopyOfClu(getViewContext().getId(), new AsyncCallback<Data>(){

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

            @Override
            public void onSuccess(Data result) {
                cluProposalModel.setRoot(result);
                setProposalHeaderTitle();
                callback.onModelReady(cluProposalModel);
                workCompleteCallback.exec(true);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

        });
    }

    @SuppressWarnings("unchecked")
    private void createNewCluProposalModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        cluProposalModel.setRoot(new LuData());
        callback.onModelReady(cluProposalModel);
        workCompleteCallback.exec(true);
    }


    public void doSaveAction(final SaveActionEvent saveActionEvent){
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
                /* This is to update model with data from current section only */
                //getCurrentView().updateModel();
                CourseProposalController.this.updateModel();

                if (isStartViewShowing()){
                    //This call required so fields in start section, which also appear in
                    //other sections don't get overridden from updateModel call above.
                    getStartPopupView().updateModel();
                }

                model.validate(new Callback<List<ValidationResultInfo>>() {
                    @Override
                    public void exec(List<ValidationResultInfo> result) {

                        boolean isSectionValid = isValid(result, true);

                        if(isSectionValid){
                            if (startSectionRequired()){
                                showStartPopup(NO_OP_CALLBACK);
                            }
                            else{
                                saveProposalClu(saveActionEvent);
                            }
                        }
                        else{
                            Window.alert("Save failed.  Please check fields for errors.");
                        }

                    }
                });
            }

            @Override
            public void onRequestFail(Throwable cause) {
                GWT.log("Unable to retrieve model for validation and save", cause);
            }

        });

    }

    public boolean startSectionRequired(){
        String proposalId = cluProposalModel.get(cfg.getProposalIdPath());

        //Defaulting the courseTitle to proposalTitle, this way course data gets set and assembler doesn't
        //complain. This may not be the correct approach.
        String courseTitle = cluProposalModel.get(cfg.getCourseTitlePath());
        if (courseTitle == null){
            String proposalTitle = cluProposalModel.get(cfg.getProposalTitlePath());
            cluProposalModel.set(QueryPath.parse(cfg.getCourseTitlePath()), proposalTitle);
        }

        return proposalId==null && !CourseProposalController.this.isStartViewShowing();
    }

    public void saveProposalClu(final SaveActionEvent saveActionEvent){
        final KSLightBox saveWindow = new KSLightBox();
        saveWindow.removeCloseLink();
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

        final Callback<Throwable> saveFailedCallback = new Callback<Throwable>() {

            @Override
            public void exec(Throwable caught) {
                 GWT.log("Save Failed.", caught);
                 saveWindow.setWidget(buttonGroup);
                 saveMessage.setText("Save Failed!  Please Try Again.");
                 buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
            }

        };
        try {
            cluProposalRpcServiceAsync.saveData(cluProposalModel.getRoot(), new AsyncCallback<DataSaveResult>(){
                public void onFailure(Throwable caught) {
                   saveFailedCallback.exec(caught);
                }

                public void onSuccess(DataSaveResult result) {
                    // FIXME [KSCOR-225] needs to check validation results and display messages if validation failed
                    cluProposalModel.setRoot(result.getValue());
                    View currentView = getCurrentView();
                    if (currentView instanceof SectionView){
                        ((SectionView)currentView).updateView(cluProposalModel);
                        ((SectionView) currentView).resetDirtyFlags();
                    }
                    if (saveActionEvent.isAcknowledgeRequired()){
                        saveMessage.setText("Save Successful");
                        buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
                    } else {
                        saveWindow.hide();
                        saveActionEvent.doActionComplete();
                    }
                    workflowUtil.refresh();
                    setProposalHeaderTitle();
                    CourseProposalController.this.showNextViewOnMenu();
                }
            });
        } catch (Exception e) {
            saveFailedCallback.exec(e);
        }

    }

    public void clear(){
        super.clear();
        if (cluProposalModel != null){
            this.cluProposalModel.setRoot(new LuData());
        }
    }


    @Override
    public void showDefaultView(final Callback<Boolean> onReadyCallback) {
        init(new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                if (result) {
                    doShowDefaultView(onReadyCallback);
                } else {
                    onReadyCallback.exec(false);
                }
            }
        });
    }

    private void doShowDefaultView(final Callback<Boolean> onReadyCallback) {
        super.showDefaultView(onReadyCallback);
    }

    @Override
    public void setParentController(Controller controller) {
        super.setParentController(controller);
        if (CourseReqSummaryHolder.getView() != null) {
            CourseReqSummaryHolder.getView().setTheController(controller);
            CourseReqSummaryHolder.getView().redraw();
        }
    }

    @Override
    public void checkAuthorization(final PermissionType permissionType, final AuthorizationCallback authCallback) {
        Map<String,String> attributes = new HashMap<String,String>();
//      if (StringUtils.isNotBlank(getViewContext().getId())) {
        GWT.log("Attempting Auth Check.", null);
        if ( (getViewContext().getId() != null) && (!"".equals(getViewContext().getId())) ) {
            attributes.put(getViewContext().getIdType().toString(), getViewContext().getId());
        }
        cluProposalRpcServiceAsync.isAuthorized(permissionType, attributes, new AsyncCallback<Boolean>(){

            @Override
            public void onFailure(Throwable caught) {
                authCallback.isNotAuthorized("Error checking authorization.");
                GWT.log("Error checking proposal authorization.", caught);
                Window.alert("Error Checking Proposal Authorization: "+caught.getMessage());
            }

            @Override
            public void onSuccess(Boolean result) {
                GWT.log("Succeeded checking auth for permission type '" + permissionType + "' with result: " + result, null);
                if (Boolean.TRUE.equals(result)) {
                    authCallback.isAuthorized();
                }
                else {
                    authCallback.isNotAuthorized("User is not authorized: " + permissionType);
                }
            }
        });
    }

    @Override
    public boolean isAuthorizationRequired() {
        return true;
    }

    @Override
    public void setAuthorizationRequired(boolean required) {
        throw new UnsupportedOperationException();
    }

    protected void setProposalHeaderTitle(){
        this.setContentTitle(cfg.getProposalHeaderTitle(cluProposalModel));
    }

    @Override
    public WorkflowUtilities getWfUtilities() {
        return workflowUtil;
    }

    @Override
    public boolean beforeViewChange() {
        boolean okToChange = super.beforeViewChange();
        //We do this check here because theoretically the subcontroller views checked above
        //will display their own messages to the user to give them a reason why the view
        //change has been cancelled, otherwise continue to check for reasons not to change
        //with this controller
        if(okToChange){
            if(this.getCurrentView() instanceof SectionView){
                if(((SectionView)this.getCurrentView()).isDirty()){
                    okToChange = false;
                    Window.alert("Dirty fields exist");
                }
            }
        }
        return okToChange;
    }
}