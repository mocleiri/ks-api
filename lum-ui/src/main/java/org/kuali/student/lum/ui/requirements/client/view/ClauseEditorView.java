package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.ui.requirements.client.RulesUtilities;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ClauseEditorView extends ViewComposite {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    
    public enum reqCompFieldDefinitions { TODO }
    
    //view's widgets
    private static final int NOF_BASIC_RULE_TYPES = 3;   
    private static final int CATALOG_TEMLATE = 0; 
    private static final int EXAMPLE_TEMLATE = 1; 
    private static final int COMPOSITION_TEMLATE = 2; 
    private static final String SIMPLE_RULE_RB_GROUP = "RuleTypesGroup";
    private static final String RULE_TYPES_OTHER = "Other:";
    private List<KSRadioButton> rbRuleType = new ArrayList<KSRadioButton>();
    private FocusHandler ruleTypeSelectionHandler = null;    
    
    private Panel mainPanel = new SimplePanel();
    private VerticalPanel addEditRuleView = new VerticalPanel();
    private HorizontalPanel ruleDetailsPanel = new HorizontalPanel(); 
    private KSButton btnCancelView = new KSButton("Cancel");
    private KSButton addReqComp = new KSButton("Add Rule");
    private KSButton updateReqComp = new KSButton("Update Rule");
    private KSDropDown compReqTypesList = new KSDropDown();
    private SimplePanel reqCompDesc = new SimplePanel();  
    private KSLabel exampleText = new KSLabel();    
    
    //view's data
    private boolean addNewReqComp;
    private ReqComponentTypeInfo selectedReqType;
    private ReqComponentInfo editedReqComp; 
    private ReqComponentVO editedReqCompVO;     
    private List<ReqComponentTypeInfo> reqCompTypeList;     //list of all Requirement Component Types
    private ListItems listItemReqCompTypes;                 //list of advanced Requirement Component Types
    private List<ReqComponentTypeInfo> advReqCompTypeList = new ArrayList<ReqComponentTypeInfo>();     //list of advanced Requirement Component Types    
    private List<KSTextBox> reqCompWidgets = new ArrayList<KSTextBox>();
    private Map<String, String> clusData = new HashMap<String, String>(); 
    private Map<String, String> cluSetsData = new HashMap<String, String>();
    private Model<PrereqInfo> modelPrereqInfo;
    private static int tempCounterID = 2000;

    public ClauseEditorView(Controller controller) {
        super(controller, "Clause Editor View");
        super.initWidget(mainPanel);
        setupHandlers();
        setReqComponentList();
    }
    
    public void beforeShow() {
                     
        if (compReqTypesList.getSelectedItem() != null) {
            compReqTypesList.deSelectItem(compReqTypesList.getSelectedItem());
        }
        
        getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
            public void onModelReady(Model<PrereqInfo> theModel) {
                modelPrereqInfo = theModel;
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        });                 

        
        getController().requestModel(ReqComponentVO.class, new ModelRequestCallback<ReqComponentVO>() {
            public void onModelReady(Model<ReqComponentVO> theModel) {
                
                if (theModel != null) {
                    List<ReqComponentVO> selectedReqComp = new ArrayList<ReqComponentVO>();
                    selectedReqComp.addAll(theModel.getValues());
                   
                    //true if we are editing existing rule
                    if (selectedReqComp.size() > 0) {
                        addNewReqComp = false;
                        editedReqCompVO = theModel.get(selectedReqComp.get(0).getId());                       
                        editedReqComp = editedReqCompVO.getReqComponentInfo();        
                        for (int i = 0; i < reqCompTypeList.size(); i++) {
                            if (editedReqComp.getType().equals(reqCompTypeList.get(i).getId())) {
                                selectedReqType = reqCompTypeList.get(i); 
                                break;
                            }                              
                        }                          
                        
                    } else {
                        //create a basic structure for a new rule
                        addNewReqComp = true;
                        setupNewEditedReqComp(null);    
                        selectedReqType = null;
                    }                    
                }
                redraw();
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        });                  
    }
    
    public void redraw() {   
        addEditRuleView.clear();
        addEditRuleView.setStyleName("KS-Rules-FullWidth");
        
        //1. show view HEADING
        KSLabel Heading = new KSLabel((addNewReqComp ? "Add" : "Edit") + " Prerequisite Rule");
        Heading.setStyleName("KS-Rules-FullWidth"); 
        Heading.setStyleName("KS-ReqMgr-Heading");
        addEditRuleView.add(Heading);
        
        //2. show RULE TYPES
        HorizontalPanel ruleTypesPanel = new HorizontalPanel();        
        
        //show requirement component label
        SimplePanel labelPanel = new SimplePanel();       
        labelPanel.setStyleName("KS-ReqCompEditor-EditFirstColumn");        
        KSLabel reqTypeLabel = new KSLabel("Rule");
        reqTypeLabel.setStyleName("KS-RuleEditor-SubHeading"); 
        labelPanel.add(reqTypeLabel);
        ruleTypesPanel.add(labelPanel);               

        //show list of rule types
        displayReqComponentTypes(ruleTypesPanel);
        addEditRuleView.add(ruleTypesPanel); 
                
        SimplePanel verticalSpacer2 = new SimplePanel();
        verticalSpacer2.setHeight("30px");
        addEditRuleView.add(verticalSpacer2);        
        
        //3. show SELECTED RULE TYPE DETAILS         
        displayReqComponentDetails();              
        addEditRuleView.add(ruleDetailsPanel); 
                       
        //buttons at the bottom        
        HorizontalPanel tempPanelButtons = new HorizontalPanel();        
        tempPanelButtons.setStyleName("KS-ReqCompEditor-BottomButtons");        
        tempPanelButtons.add(btnCancelView);
        btnCancelView.setStyleName("KS-Rules-Standard-Button");   
        if (addNewReqComp) {
            tempPanelButtons.add(addReqComp);
            addReqComp.setStyleName("KS-Rules-Standard-Button");
            if (selectedReqType == null) {
                addReqComp.setEnabled(false);
            }
        } else {
            tempPanelButtons.add(updateReqComp);
            updateReqComp.setStyleName("KS-Rules-Standard-Button");
            if (selectedReqType == null) {
                updateReqComp.setEnabled(false);
            }            
        }                                
        
        addEditRuleView.add(tempPanelButtons);
        addEditRuleView.setStyleName("Content-Margin");
        mainPanel.clear();
        mainPanel.add(addEditRuleView);
        
        //updateExampleContext(); TODO - download all necessary contexts?
    }
    
    //show basic and advanced requirement types
    private void displayReqComponentTypes(Panel container) {
        
        //TODO list of basic and advanced types based on configuration somewhere
        
        //show radio button for each basic Requirement Component Type
        VerticalPanel rbPanel = new VerticalPanel();
        for (int i = 0; i < NOF_BASIC_RULE_TYPES; i++) {
            KSRadioButton newButton = new KSRadioButton(SIMPLE_RULE_RB_GROUP, reqCompTypeList.get(i).getDesc());
            rbRuleType.add(newButton);
            newButton.addFocusHandler(ruleTypeSelectionHandler);
            if ((selectedReqType != null) && reqCompTypeList.get(i).getId().equals(selectedReqType.getId())) {
                newButton.setValue(true);
            }
            rbPanel.add(newButton);            
        }
        
        //now add a drop down for advanced Requirement Component Types
        HorizontalPanel hodler = new HorizontalPanel();
        KSRadioButton newButton = new KSRadioButton(SIMPLE_RULE_RB_GROUP, RULE_TYPES_OTHER);
        rbRuleType.add(newButton);
        hodler.add(newButton); 
        compReqTypesList.setStyleName("KS-Radio-Dropdown");
       
        if (selectedReqType != null) { 
            ListItems advancedTypes = compReqTypesList.getListItems();
            List<String> ids = advancedTypes.getItemIds();
            for (int i = 0; i < advancedTypes.getItemCount(); i++) {            
                if (advancedTypes.getItemText(ids.get(i)).equals(selectedReqType.getName())) {
                    compReqTypesList.selectItem(ids.get(i));
                    break;
                }
            }
        }

        hodler.add(compReqTypesList);        
        rbPanel.add(hodler);                
        container.add(rbPanel);    
    }
    
    
    private void displayReqComponentDetails() {
        
        //TODO generic function that will retrieve all data required to display details of this req. componenet type...
        
        //true if no Requirement Component Type selected
        ruleDetailsPanel.clear();
        if (selectedReqType == null) {
            return;
        }       
        
        requirementsRpcServiceAsync.getReqComponentTemplates(editedReqComp, new AsyncCallback<String[]>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                System.out.println(caught.getMessage());
                caught.printStackTrace();
            }
            
            public void onSuccess(final String[] templates) {
                displayReqComponentDetailsCont(templates);
            } 
        });  
    }
    
    private void displayReqComponentDetailsCont(String[] templates) {                
        
        SimplePanel emptyPanel = new SimplePanel();       
        emptyPanel.setStyleName("KS-ReqCompEditor-EditFirstColumn"); 
        ruleDetailsPanel.add(emptyPanel);
        
        //show heading
        VerticalPanel reqCompDetailsExampleContainerPanel = new VerticalPanel(); 
        KSLabel reqCompTypeName = new KSLabel(selectedReqType.getDesc());
        reqCompTypeName.setStyleName("KS-Rules-BoldText");
        reqCompDetailsExampleContainerPanel.add(reqCompTypeName);

        //show details
        HorizontalPanel reqCompDetailsExamplePanel = new HorizontalPanel();
        SimplePanel reqCompDetailsPanel = new SimplePanel();
        reqCompDetailsPanel.setStyleName("KS-Rules-ReqCompEdit-Width");
                
        displayReqComponentText(templates[COMPOSITION_TEMLATE], reqCompDesc, (editedReqComp == null ? null : editedReqComp.getReqCompField()));
        reqCompDetailsPanel.add(reqCompDesc);
        
        reqCompDetailsExamplePanel.add(reqCompDetailsPanel);
        
        //show example
        VerticalPanel examplePanel = new VerticalPanel();        
        examplePanel.setSpacing(0);   
        KSLabel exampleText1 = new KSLabel("Example:");
        exampleText1.setStyleName("KS-RuleEditor-ExampleText1");
        examplePanel.add(exampleText1);
        exampleText.setText(templates[EXAMPLE_TEMLATE]);
        exampleText.setStyleName("KS-RuleEditor-ExampleText2");
        examplePanel.add(exampleText);
        reqCompDetailsExamplePanel.add(examplePanel);
        
        reqCompDetailsExampleContainerPanel.add(reqCompDetailsExamplePanel);
        
        SimplePanel verticalSpacer2 = new SimplePanel();
        verticalSpacer2.setHeight("30px");
        reqCompDetailsExampleContainerPanel.add(verticalSpacer2); 
        
        reqCompDetailsExampleContainerPanel.add(new KSLabel(templates[CATALOG_TEMLATE]));        
        
        ruleDetailsPanel.add(reqCompDetailsExampleContainerPanel);
    }   

    private void setupHandlers() {
         
        btnCancelView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().showView(PrereqViews.COMPLEX);
            }
        });
        
        addReqComp.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent event) { 
                
                if (updateFields() == false) {
                    return;
                }
                
                editedReqComp.setType(selectedReqType.getId());                         

                //add new req. component (rule) to the top level of the rule
                PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(modelPrereqInfo);
                StatementVO statementVO = prereqInfo.getStatementVO();
                // in the case when there is currently no statement...
                // i.e. the user creates the rules from scratch.
                if (statementVO == null) {
                    LuStatementInfo newLuStatementInfo = new LuStatementInfo();
                    statementVO = new StatementVO();
                    newLuStatementInfo.setOperator(StatementOperatorTypeKey.AND);
                    statementVO.setLuStatementInfo(newLuStatementInfo);
                    prereqInfo.setStatementVO(statementVO);
                }
                statementVO.addReqComponentVO(editedReqCompVO);                                                                                              
                statementVO.clearSelections();
                editedReqCompVO.setCheckBoxOn(true);
                updateNLAndExit();
            }
        });   
        
        updateReqComp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {                

                if (updateFields() == false) {
                    return;
                }                
                
                if (modelPrereqInfo != null) {
                    PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(modelPrereqInfo);
                    StatementVO statementVO = prereqInfo.getStatementVO();
                    prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                    statementVO.clearSelections();
                    editedReqCompVO.setCheckBoxOn(true);
                }
                updateNLAndExit();                
            }
        });        
        
        ruleTypeSelectionHandler = new FocusHandler() {
            public void onFocus(FocusEvent event) {
                
                addReqComp.setEnabled(true);
                updateReqComp.setEnabled(true);
                
                KSRadioButton btn = ((KSRadioButton) event.getSource());
                
                if (compReqTypesList.getSelectedItem() != null) {
                    compReqTypesList.deSelectItem(compReqTypesList.getSelectedItem());
                }                
                
                for (int i = 0; i < NOF_BASIC_RULE_TYPES; i++) {
                    if (btn.getText().trim().equals(reqCompTypeList.get(i).getDesc().trim())) {
                        selectedReqType = reqCompTypeList.get(i); 
                        if (addNewReqComp) {
                            setupNewEditedReqComp(selectedReqType.getId());
                        } else {
                            editedReqComp.setType(selectedReqType.getId());
                        }
                        displayReqComponentDetails();
                        return;
                    }                              
                }                                                                                
            }
        };               
        
        compReqTypesList.addSelectionChangeHandler(new SelectionChangeHandler() {  
             public void onSelectionChange(KSSelectItemWidgetAbstract w) {
                 addReqComp.setEnabled(true);
                 updateReqComp.setEnabled(true);
                 for (KSRadioButton button : rbRuleType) {
                     button.setValue(button.getText().equals(RULE_TYPES_OTHER) ? true : false);                     
                 }
                 
                 List<String> ids = w.getSelectedItems();
                 selectedReqType = advReqCompTypeList.get(Integer.valueOf(ids.get(0)));
                 if (addNewReqComp) {
                     setupNewEditedReqComp(selectedReqType.getId());                 
                 } else {
                     editedReqComp.setType(selectedReqType.getId());
                 }
                 displayReqComponentDetails();             
         }});
        
    }
    
    /**
     * reads and check what are in the widgets and update the fields in editedReqComp
     */
    private boolean updateFields() {
        List<ReqCompFieldInfo> fields = null;
        StringBuffer cluIds = new StringBuffer();

        if (reqCompWidgets != null && !reqCompWidgets.isEmpty()) {
            fields = new ArrayList<ReqCompFieldInfo>();
            editedReqComp.setReqCompField(fields);
            for (KSTextBox reqCompWidget : reqCompWidgets) {
                ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
                fieldInfo.setId(reqCompWidget.getName());
                fieldInfo.setValue(reqCompWidget.getText());
                if (checkField(fieldInfo) == false) {
                    return false;
                }

                if (fieldInfo.getId().equals("reqCompFieldType.clu")) {
                    cluIds.append((cluIds.length() > 0 ? ", " : "") + fieldInfo.getValue()); 
                } else {
                    fields.add(fieldInfo);
                }
            }
            if (cluIds.length() > 0) {
                ReqCompFieldInfo fieldInfo = new ReqCompFieldInfo();
                fieldInfo.setId("reqCompFieldType.clu");
                fieldInfo.setValue(cluIds.toString());
                fields.add(fieldInfo);
            }
        }
        editedReqComp.setReqCompField(fields);
        return true;
    }
    
    private boolean checkField(ReqCompFieldInfo fieldInfo) {

        if (fieldInfo != null) {
            String fieldId = fieldInfo.getId().replaceFirst("reqCompFieldType.", "");            
            String fieldValue = fieldInfo.getValue();
            
            if (fieldValue.trim().isEmpty()) {
                Window.alert("Please enter all fields");
                return false;
            }            
                      
            if (fieldId.equals("clu")) {
                String cluId = clusData.get(fieldValue);
                if (cluId != null) {
                    fieldInfo.setValue(cluId);
                    return true;
                }
                if (fieldValue.contains(",")) {
                    Window.alert("Please enter only one course");                  
                } else {                
                    Window.alert("Cannot find course '" + fieldValue + "'");
                }
                return false;
            }
           
            if (fieldId.equals("cluSet")) {
                String cluSetId = cluSetsData.get(fieldValue);              
                if (cluSetId != null) {
                    fieldInfo.setValue(cluSetId);
                    return true;
                }                                
                if (fieldValue.contains(",")) {
                    Window.alert("Please enter only one course set");                  
                } else {
                    Window.alert("Cannot find course set '" + fieldValue + "'");
                }
                return false;
            }            
        }
        return true;
    }
    
    private void displayReqComponentText(String reqInfoDesc, SimplePanel parentWidget, final List<ReqCompFieldInfo> fields) { 
       
        // resets the list of reqCompWidgets to make sure it is created fresh.
        reqCompWidgets.clear();
        parentWidget.clear();
        FlowPanel innerReqComponentTextPanel = new FlowPanel();       
        innerReqComponentTextPanel.setStyleName("KS-Rules-FullWidth");
        final String[] tokens = reqInfoDesc.split("[<>]");
        boolean isValueWidget = true;
        
        for (int i = 0; i < tokens.length; i++) {
            
            isValueWidget = !isValueWidget;
            //this token is a text only
            if (isValueWidget == false) {
                KSLabel text = new KSLabel(tokens[i]);
                text.addStyleName("KS-Rules-FlexPanelFix");  
                innerReqComponentTextPanel.add(text);
                continue;
            }
            
            //TODO use ENUMs and Switch()
            String tag = tokens[i].replaceFirst("reqCompFieldType.", "");
            tag = tag.replaceFirst("reqCompFiledType.", "");

            if ((tag.equals("requiredCount")) || (tag.equals("gpa")) || (tag.equals("totalCredits"))) {
                final KSTextBox valueWidget = new KSTextBox();
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tokens[i]);
                valueWidget.setText(getSpecificFieldValue(fields, tokens[i]));
                valueWidget.setWidth("50px");
                valueWidget.setStyleName("KS-Textbox-Fix");
                SimplePanel tempPanel = new SimplePanel();
                tempPanel.addStyleName("KS-Rules-FlexPanelFix");
                tempPanel.add(valueWidget); 
                innerReqComponentTextPanel.add(tempPanel);                
                continue;                                
            }
            
            if (tag.equals("clu")) {
                final KSTextBox valueWidget = new KSTextBox();
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tokens[i]);
                valueWidget.setText(getSpecificFieldValue(fields, tokens[i]));
                valueWidget.setWidth("100px");
                valueWidget.setStyleName("KS-Textbox-Fix");
                VerticalPanel tempPanel = new VerticalPanel();
                tempPanel.addStyleName("KS-Rules-FlexPanelFix");
                tempPanel.add(valueWidget);                

                final SearchDialog searchDialog = new SearchDialog(getController(), clusData);
                searchDialog.addCourseAddHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        String origFieldValue = valueWidget.getText();
                        int fieldValueCount = 0;
                        origFieldValue = (origFieldValue == null)? "" : origFieldValue;
                        StringBuilder newFieldValue = new StringBuilder("");
                        SortedSet<String> newValues = new TreeSet<String>();
                        newValues.addAll(Arrays.asList(origFieldValue.split(", +")));
                        newValues.addAll(searchDialog.getSelections());
                        for (String newValue : newValues) {
                            if (fieldValueCount > 0 && newFieldValue.toString().trim().length() > 0) {
                                newFieldValue.append(", ");
                            }
                            newFieldValue.append(newValue);
                            fieldValueCount++;
                        }
                        valueWidget.setText(newFieldValue.toString());
                    }
                });
                tempPanel.add(searchDialog);
                innerReqComponentTextPanel.add(tempPanel);
                
                continue;                                
            }            
            
            if (tag.equals("cluSet")) {
                final KSTextBox valueWidget = new KSTextBox();
                reqCompWidgets.add(valueWidget);
                valueWidget.setName(tokens[i]);
                final ReqCompFieldInfo reqCompFieldInfo = getReqCompFieldInfo(fields, tokens[i]);
                valueWidget.setText(getSpecificFieldValue(fields, tokens[i]));             
                valueWidget.setWidth("250px");                           
                valueWidget.setStyleName("KS-Textbox-Fix"); 
                valueWidget.addStyleName("KS-Rules-FlexPanelFix");
                VerticalPanel tempPanel = new VerticalPanel();
                tempPanel.setStyleName("KS-Rules-FlexPanelFix");
                tempPanel.add(valueWidget);                     
                final SearchDialog searchDialog = new SearchDialog(getController(), cluSetsData);
                searchDialog.addCourseAddHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        String origFieldValue = valueWidget.getText();
                        int fieldValueCount = 0;
                        origFieldValue = (origFieldValue == null)? "" : origFieldValue;
                        StringBuilder newFieldValue = new StringBuilder("");
                        SortedSet<String> newValues = new TreeSet<String>();
                        newValues.addAll(Arrays.asList(origFieldValue.split(", +")));
                        newValues.addAll(searchDialog.getSelections());
                        for (String newValue : newValues) {
                            if (fieldValueCount > 0 && newFieldValue.toString().trim().length() > 0) {
                                newFieldValue.append(", ");
                            }
                            newFieldValue.append(newValue);
                            fieldValueCount++;
                        }
                        valueWidget.setText(newFieldValue.toString());
                    }
                });
                tempPanel.add(searchDialog);
                innerReqComponentTextPanel.add(tempPanel);
                continue;                                
            }       
        }
        parentWidget.setWidget(innerReqComponentTextPanel);
    }
       
    private ReqCompFieldInfo getReqCompFieldInfo(List<ReqCompFieldInfo> fields, String key) {
        ReqCompFieldInfo result = null;
        if (fields == null) {
            return null;
        }
        for (ReqCompFieldInfo fieldInfo : fields) {
            if (fieldInfo.getId().equals(key)) {
                result = fieldInfo;
            }
        }
        return result;
    }
        
    private String getSpecificFieldValue(List<ReqCompFieldInfo> fields, String key) {
        ReqCompFieldInfo reqCompFieldInfo = getReqCompFieldInfo(fields, key);
        String result = null;
        
        //if we are showing new req. comp. type then show empty fields
        if (reqCompFieldInfo == null) {
            return "";
        }
        
        result = reqCompFieldInfo.getValue();
        result = (result == null)? "" : result;    
        return result;
    }            
    
    private void setupNewEditedReqComp(String reqCompID) {            
        editedReqComp = new ReqComponentInfo();
        editedReqComp.setDesc("");      //will be set after user is finished with all changes
        editedReqComp.setId(Integer.toString(tempCounterID++));  //TODO       
        editedReqComp.setReqCompField(null); //fieldList);
        if (reqCompID != null) editedReqComp.setType(reqCompID);
        editedReqCompVO = new ReqComponentVO(editedReqComp);             
    }
    
    /**
     * returns a comma delimited string that represents the union of the originalValue and selectedValues
     * @param originalValue
     * @param selectedValues
     * @return
     */
    private String combineValues(String originalValue, List<String> selectedValues) {
        int fieldValueCount = 0;
        String tempOriginalValue = 
            (originalValue == null)? "" : originalValue;
        StringBuilder newFieldValue = new StringBuilder("");
        SortedSet<String> newValues = new TreeSet<String>();
        newValues.addAll(Arrays.asList(tempOriginalValue.split(", +")));
        newValues.addAll(selectedValues);
        for (String newValue : newValues) {
            if (fieldValueCount > 0 && 
                    newFieldValue.toString().trim().length() > 0) {
                newFieldValue.append(", ");
            }
            newFieldValue.append(newValue);
            fieldValueCount++;
        }
        return newFieldValue.toString();
    }    

    public void setReqComponentList() {        
        
        getController().requestModel(ReqComponentTypeInfo.class, new ModelRequestCallback<ReqComponentTypeInfo>() {
            public void onModelReady(Model<ReqComponentTypeInfo> theModel) {
                reqCompTypeList = new ArrayList<ReqComponentTypeInfo>();
                reqCompTypeList.addAll(theModel.getValues());
                
                for(int i = NOF_BASIC_RULE_TYPES; i < reqCompTypeList.size(); i++){
                    advReqCompTypeList.add(reqCompTypeList.get(i));
                }                

                listItemReqCompTypes = new ListItems() {                    
                    @Override
                    public List<String> getAttrKeys() {
                        List<String> attributes = new ArrayList<String>();
                        attributes.add("Key");
                        return attributes;
                    }

                    @Override
                    public String getItemAttribute(String id, String attrkey) {
                        String value = null;
                        Integer index;
                        try{
                            index = Integer.valueOf(id);
                            value = advReqCompTypeList.get(index).getDesc();
                        } catch (Exception e) {
                        }

                        return value;
                    }                    
                    
                    @Override
                    public int getItemCount() {    
                        return advReqCompTypeList.size();
                    }

                    @Override
                    public List<String> getItemIds() {
                        List<String> ids = new ArrayList<String>();
                        for(int i = 0; i < advReqCompTypeList.size(); i++){
                            ids.add(String.valueOf(i));
                        }
                        return ids;
                    }

                    @Override
                    public String getItemText(String id) {
                        return getItemAttribute(id, "?");
                    }
                };  
                
                compReqTypesList.setListItems(listItemReqCompTypes);               
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        });                                               
    }

    private void updateNLAndExit() {
        requirementsRpcServiceAsync.getNaturalLanguageForReqComponentInfo(editedReqComp, "KUALI.CATALOG", new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                caught.printStackTrace();
            }
            
            public void onSuccess(final String reqCompNaturalLanguage) {                               
                editedReqCompVO.setTypeDesc(reqCompNaturalLanguage);
                PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(modelPrereqInfo);
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                getController().showView(PrereqViews.COMPLEX);
            } 
        });        
    }

    private Map<String, String> getClusData() {
        return clusData;
    }

    public void setClusData(Map<String, String> clusData) {
        this.clusData = clusData;
    }

    private Map<String, String> getCluSetsData() {
        return cluSetsData;
    }

    public void setCluSetsData(Map<String, String> cluSetsData) {
        this.cluSetsData = cluSetsData;
    }    
    
    /**
     * TODO test method remove when real clu codes are retrieved.
     * @return
     */
    private Map<String, String> getTestCluCodes() {
        Map<String, String> cluCodes = new HashMap<String, String>();
        cluCodes.put("12345", "CHEM 100");
        cluCodes.put("123444", "CLUSET-NL-1");
        return cluCodes;
    }
    
}
