package org.kuali.student.lum.program.client;

import com.google.gwt.core.client.GWT;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuSectionController;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;
import org.kuali.student.lum.program.client.view.ProgramViewConfigurer;

/**
 * @author Igor
 */
public class ProgramController extends MenuSectionController {

    protected final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);

    protected boolean initialized = false;

    protected final DataModel programModel = new DataModel();

    protected WorkQueue modelRequestQueue;

    protected AbstractProgramConfigurer configurer;

    public ProgramController() {
        super("");
        setViewContext(new ViewContext());
        initialize();
    }

    private void initialize() {
        super.setDefaultModelId(ProgramConstants.PROGRAM_MODEL_ID);
        super.registerModel(ProgramConstants.PROGRAM_MODEL_ID, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (modelRequestQueue == null) {
                    modelRequestQueue = new WorkQueue();
                }

                WorkQueue.WorkItem workItem = new WorkQueue.WorkItem() {
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (programModel.getRoot() == null || programModel.getRoot().size() == 0) {
                            initModel(callback, workCompleteCallback);
                        } else {
                            callback.onModelReady(programModel);
                            workCompleteCallback.exec(true);
                        }
                    }

                };
                modelRequestQueue.submit(workItem);
            }
        });
    }

    private void initModel(final ModelRequestCallback<DataModel> callback, final Callback<Boolean> workCompleteCallback) {
        programRemoteService.getData(getViewContext().getId(), new AbstractCallback<Data>(ProgramProperties.get().common_retrievingData()) {

            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
            }

            @Override
            public void onSuccess(Data result) {
                super.onSuccess(result);
                programModel.setRoot(result);
                callback.onModelReady(programModel);
                workCompleteCallback.exec(true);
            }
        });
    }

    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        init(new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                if (result) {
                    showDefaultView(onReadyCallback);
                } else {
                    onReadyCallback.exec(false);
                }
            }
        });
    }


    protected void init(final Callback<Boolean> onReadyCallback) {
        if (initialized) {
            onReadyCallback.exec(true);
        } else {
            String idType = null;
            String viewContextId = null;
            if (getViewContext().getIdType() != null) {
                idType = getViewContext().getIdType().toString();
                viewContextId = getViewContext().getId();
                if (getViewContext().getIdType() == ViewContext.IdType.COPY_OF_OBJECT_ID) {
                    viewContextId = null;
                }
            }

            programRemoteService.getMetadata(idType, viewContextId, new AbstractCallback<Metadata>() {

                @Override
                public void onSuccess(Metadata result) {
                    super.onSuccess(result);
                    DataModelDefinition def = new DataModelDefinition(result);
                    programModel.setDefinition(def);
                    init(def);
                    initialized = true;
                    onReadyCallback.exec(true);
                }

                @Override
                public void onFailure(Throwable caught) {
                    super.onFailure(caught);
                    onReadyCallback.exec(false);

                }
            });
        }
    }

    protected void init(DataModelDefinition modelDefinition) {
        configurer.setModelDefinition(modelDefinition);
        if (null == programModel.getRoot()) {
            programModel.setRoot(new Data());
        }
        configurer.configure(this);
        this.setContentTitle("Programs");
        initialized = true;
    }

    @Override
    public void setViewContext(ViewContext viewContext) {
        super.setViewContext(viewContext);
        if (viewContext.getId() != null && !viewContext.getId().isEmpty()) {
            viewContext.setPermissionType(PermissionType.OPEN);
        } else {
            viewContext.setPermissionType(PermissionType.INITIATE);
        }
    }
}