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
package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * This class manages the users interactions when building/updating Learning Objectives within the
 * context of managing CLUs.  It allows the user to type in LO text directly or execute a search and
 * select one or more of the returned LOs.
 *
 * Users can then re-organize LOs on the screen including altering the sequence and creating sub LOs
 *
 * @author Kuali Student Team
 *
 */
public class LOBuilder extends Composite implements HasValue<List<OutlineNode<LOPicker>>> {
    
    private static String type;
    private static String state;
    private static String repoKey;
    private static String messageGroup;
    
    LOSearchWindow searchWindow;   

    VerticalPanel main = new VerticalPanel();

    HorizontalPanel searchMainPanel = new HorizontalPanel();
    SimplePanel searchSpacerPanel = new SimplePanel();
    HorizontalPanel searchLinkPanel = new HorizontalPanel();


    VerticalPanel loPanel = new VerticalPanel();

    KSLabel searchLink;
    LearningObjectiveList loList;
    KSLabel instructions ;


    protected LOBuilder() {
        //TODO: should this be an error?  Can we set realistic defaults?
    }

    public LOBuilder(String luType, String luState, String luGroup, String loRepoKey) {
        super();
        initWidget(main);

        type = luType;
        state = luState;
        repoKey = loRepoKey;
        messageGroup = luGroup;

        ClickHandler searchClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (searchWindow == null) {
                    
                    ConfirmCancelGroup buttons = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

                        @Override
                        public void exec(ConfirmCancelEnum result) {
                            switch(result){
                                case CONFIRM:
                                    loList.addSelectedLOs(searchWindow.getLoSelections());
                                    searchWindow.hide();
                                    break;
                                case CANCEL:
                                    searchWindow.hide();
                                    break;
                            }
                        }
                    });

                    searchWindow = new LOSearchWindow(messageGroup, type, state, buttons ); 


                }
                else {
                    searchWindow.reset();
                }
                searchWindow.show();
            }
        };


        instructions = new KSLabel(getLabel(LUConstants.LO_INSTRUCTIONS_KEY));
        searchLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_LINK_KEY));
        searchLink.addClickHandler(searchClickHandler);
        KSImage searchImage = Theme.INSTANCE.getCommonImages().getSearchIcon();
        searchImage.addClickHandler(searchClickHandler);

        searchLinkPanel.add(searchImage);
        searchLinkPanel.add(searchLink);
        searchSpacerPanel.add(new KSLabel(""));

        searchMainPanel.add(searchSpacerPanel);
        searchMainPanel.add(searchLinkPanel);

        loList = new LearningObjectiveList();
        loPanel.add(loList);

        searchImage.addStyleName("KS-LOBuilder-Search-Image");
        searchLink.addStyleName("KS-LOBuilder-Search-Link");
        searchSpacerPanel.addStyleName("KS-LOBuilder-Spacer-Panel");
        searchMainPanel.addStyleName("KS-LOBuilder-Search-Panel");
        loPanel.addStyleName("KS-LOBuilder-LO-Panel");
        instructions.addStyleName("KS-LOBuilder-Instructions");

        main.add(searchMainPanel);
        main.add(instructions);
        main.add(loPanel);
    }
    
	/**
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
	 */
	@Override
	public void setValue(List<OutlineNode<LOPicker>> value, boolean fireEvents) {
		setValue(value, false);
	}
	
    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
     */
    @Override
    public void setValue(List<OutlineNode<LOPicker>> data) {
        loList.setValue(data);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public List<OutlineNode<LOPicker>> getValue() {
        return loList.getValue();
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<OutlineNode<LOPicker>>> handler) {
        return loList.addValueChangeHandler(handler);
    }

    private static String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    }

    /**
	 * @return the type
	 */
	public static String getType() {
		return type;
	}

	/**
	 * @return the state
	 */
	public static String getState() {
		return state;
	}

	public static String getRepoKey() {
		return repoKey;
	}

	/**
	 * @return the messageGroup
	 */
	public static String getMessageGroup() {
		return messageGroup;
	}

	public static class LearningObjectiveList extends Composite {
        OutlineNodeModel<LOPicker> outlineModel = new OutlineNodeModel<LOPicker>();
        OutlineManager outlineComposite = new OutlineManager();
        VerticalPanel mainPanel = new VerticalPanel();
        
        public LearningObjectiveList(){
            mainPanel.add(outlineComposite);
            KSLabel addnew = new KSLabel("Add new Learning Objective");
            addnew.addStyleName("KS-LOBuilder-New");
            mainPanel.add(addnew);
            addnew.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                    setValue(getValue()); 
                    appendLO("");
                    reDraw();
                }
            });
            super.initWidget(mainPanel);
            
            outlineComposite.setModel(outlineModel);
            outlineModel.addChangeHandler(new ChangeHandler() {
              public void onChange(ChangeEvent event) {
                outlineComposite.render();
              }
            });
            
            List<String> list = new ArrayList<String>();
            list.add("");
            addSelectedLOs(list);
        }

        public List<OutlineNode<LOPicker>> getValue() {
        	return outlineModel.getOutlineNodes();
        }
        
        public void setValue(List<OutlineNode<LOPicker>> value) {
        	outlineModel.clearNodes();
        	outlineModel.getOutlineNodes().addAll(value);
            reDraw();
        }
        
        private void appendLO(String loValue){
            OutlineNode<LOPicker> aNode = new OutlineNode<LOPicker>();
            LOPicker newPicker = new LOPicker(messageGroup, type, state, repoKey);
            
            newPicker.setLOText(loValue);
            aNode.setUserObject(newPicker);
            aNode.setModel(outlineModel);
            
            outlineModel.addOutlineNode(aNode);
        }
        public void addSelectedLOs(List<String> loDescription) {
            for (String strValue : loDescription){
                appendLO(strValue);
            }
            reDraw();
        }
        private void reDraw(){
          outlineComposite.render();
        }
        public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<OutlineNode<LOPicker>>> handler) {
            return new NOOPListValueChangeHandler();
        }
            
        private class NOOPListValueChangeHandler implements HandlerRegistration {
            public void removeHandler() {
            }
        }  
    }
}
