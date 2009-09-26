package org.kuali.student.lum.ui.requirements.client.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;
import org.kuali.student.lum.ui.requirements.client.view.CourseRequisiteView;
import org.kuali.student.lum.ui.requirements.client.view.RuleComponentEditorView;
import org.kuali.student.lum.ui.requirements.client.view.ManageRulesView;
import org.kuali.student.lum.ui.requirements.client.view.RuleExpressionEditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CourseReqManager extends Controller {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    
    public enum PrereqViews {
        RULES_LIST, MANAGE_RULES, CLAUSE_EDITOR, RULE_EXPRESSION_EDITOR
    }

    //controller's widgets
    private final SimplePanel mainPanel = new SimplePanel();
    private final SimplePanel viewPanel = new SimplePanel();
    private final CourseRequisiteView courseRequisiteView = new CourseRequisiteView(this);
    private final ManageRulesView manageRulesView = new ManageRulesView(this);
    private final RuleComponentEditorView rulesEditorView = new RuleComponentEditorView(this);
    private final RuleExpressionEditor ruleExpressionEditorView = new RuleExpressionEditor(this);
    
    //controller's data
    private Model<RuleInfo> ruleInfo;
    private Model<ReqComponentVO>  selectedReqCompVO;    
    private Model<ReqComponentTypeInfo> reqComponentTypes; //all requirements type info (fields and NL templates) for given statement type
    private String luStatementType = "unknown";
    private Map<String, String> listOfAvailableClus = new HashMap<String, String>();   //temporary - will be replaced with search widgets
    private Map<String, String> cluSetsData = new HashMap<String, String>(); 
    
    public CourseReqManager(VerticalPanel displayPanel) {
        super();       
        
        if (displayPanel == null) {        
       	 	super.initWidget(viewPanel);
       	 	viewPanel.add(mainPanel);
        } else {
        	displayPanel.add(mainPanel);
        }
        
        this.ruleInfo = null;
        resetReqCompVOModel();
        loadSearchBoxData();
    }
   
    @Override
    protected void onLoad() {
        //showDefaultView();   instead of showing default view every time user comes back, just show screen user left of last time
    }

    // controller operations
    @Override
    @SuppressWarnings("unchecked")
    public void requestModel(Class modelType, ModelRequestCallback callback) {
        if (modelType.equals(RuleInfo.class)) {
            
            //pass back only rule corresponding to the user selected rule type
            Model<RuleInfo> ruleInfoGivenType = new Model<RuleInfo>();
            ruleInfoGivenType.add(getRuleInfo(luStatementType));                        
            callback.onModelReady(ruleInfoGivenType);
        } else if (modelType.equals(ReqComponentTypeInfo.class)) {
            if (reqComponentTypes == null) {
                retrieveModelData(ReqComponentTypeInfo.class, callback);
            }
            else {
                callback.onModelReady(reqComponentTypes);
            }
        } else if (modelType.equals(ReqComponentVO.class)) {
            callback.onModelReady(selectedReqCompVO);
        }
        else {
            super.requestModel(modelType, callback);
        }
    }
    
    private RuleInfo getRuleInfo(String luStatementTypeKey) {
        if (ruleInfo.getValues() != null && !ruleInfo.getValues().isEmpty()) {
            for (RuleInfo oneRuleInfo : ruleInfo.getValues()) {
                if (oneRuleInfo.getLuStatementTypeKey().equals(luStatementTypeKey)) {
                    return oneRuleInfo;
                }                
            }
        } 
        return null;
    }    

    @Override
    public void showDefaultView() {
        showView(PrereqViews.RULES_LIST);
    }
    
    @Override
    public void renderView(View view) {
        // in this case we know that all of our widgets are composites
        // but we could do view specific rendering, e.g. show a lightbox, etc
        mainPanel.setWidget((ViewComposite) view);
    }

    @Override
    protected void hideView(View view) {
        mainPanel.clear();
    }    

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((PrereqViews) viewType) {
            case RULES_LIST:
	            return courseRequisiteView;
	        case MANAGE_RULES:
                return manageRulesView;
            case CLAUSE_EDITOR:    
                rulesEditorView.setClusData(listOfAvailableClus);
                rulesEditorView.setCluSetsData(cluSetsData);
                return rulesEditorView;
            case RULE_EXPRESSION_EDITOR:
                return ruleExpressionEditorView;
            default:
                return null;
        }
    }

    //TODO: should we retrieve requirement comp. types for all applicable lu statement types?
    public void retrieveModelData(Class<? extends Idable> modelType, final ModelRequestCallback<ReqComponentTypeInfo> callback) {
        
        if (luStatementType == null) {
            throw new IllegalArgumentException();
        }
        
        System.out.println("Retrieving req. comp. types: " + luStatementType);
        
        if (modelType.equals(ReqComponentTypeInfo.class)) {        
            requirementsRpcServiceAsync.getReqComponentTypesForLuStatementType(luStatementType, new AsyncCallback<List<ReqComponentTypeInfo>>() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                    // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
                }
    
                public void onSuccess(final List<ReqComponentTypeInfo> reqComponentTypeInfoList) {  
                    reqComponentTypes = new Model<ReqComponentTypeInfo>();
                    for (ReqComponentTypeInfo reqCompInfo : reqComponentTypeInfoList) {
                        reqComponentTypes.add(reqCompInfo);
                    }      
                    callback.onModelReady(reqComponentTypes);                                   
                }
            });  
        }
    }

    private void loadSearchBoxData() {
    	
    	//the following call will be replaced by search widgets that will query for all existing clus
        requirementsRpcServiceAsync.getAllClus(new AsyncCallback<Map<String, String>>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(final Map<String, String> cluData) {  
                listOfAvailableClus = cluData;                   
            }
        });
        
        cluSetsData.put("CLUSET-NL-3", "CLUSET-NL-3");
        cluSetsData.put("CLUSET-NL-2", "CLUSET-NL-2");
        cluSetsData.put("CLUSET-NL-1", "CLUSET-NL-1");

        
        /* will be removed when search widgets are added
        RequirementsService.Util.getInstance().getAllClusets(new AsyncCallback<Map<String, String>>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(final Map<String, String> cluSetData) {  
                cluSetsData = cluSetData;                   
            }
        }); */         
    }
    
    public Class<? extends Enum<?>> getViewsEnum() {
        return PrereqViews.class;
    }
    
    public void resetReqCompVOModel () {
        this.selectedReqCompVO = new Model<ReqComponentVO>();
    }
    
    public SimplePanel getMainPanel() {
        return mainPanel;
    }
    
    public void setRuleInfoModel(Model<RuleInfo> ruleInfo) {
        this.ruleInfo = ruleInfo;
    }
    
    public Model<RuleInfo> getRuleInfoModel() {
        return ruleInfo;
    }    
    
    public String getLuStatementType() {
        return luStatementType;
    }

    public void setLuStatementType(String luStatementType) {
        this.luStatementType = luStatementType;
    }    
}
