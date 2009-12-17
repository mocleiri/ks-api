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
import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * This class manages the users interactions when building/updating Learning Objectives within the
 * context of managing CLUs.  It allows the user to type in LO text directly or execute a search and
 * select one or more of the returned LOs.
 *
 * Users can then re-organize LOs on the screen including altering the sequence and creating sub LOs
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 *
 */
public class LOBuilder extends Composite  implements HasModelDTOValue {
    
    private static String type;
    private static String state;
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

    private static final int NUM_INITIAL_LOS = 5;



    protected LOBuilder() {
        //TODO: should this be an error?  Can we set realistic defaults?

    }

    public LOBuilder(String luType, String luState, String luGroup) {
        super();
        initWidget(main);

        type = luType;
        state = luState;
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

        searchLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_LINK));
        searchLink.addClickHandler(searchClickHandler);

        instructions = new KSLabel(getLabel(LUConstants.LO_INSTRUCTIONS));
        searchLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_LINK));
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



    
    @Override
    public void setValue(ModelDTOValue modelDTOValue) {
        loList.setValue(modelDTOValue);

    }

    @Override
    public void setValue(ModelDTOValue value, boolean fireEvents) {
        setValue(value, fireEvents);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public ModelDTOValue getValue() {
        return loList.getValue();
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void updateModelDTOValue() {
        loList.updateModelDTOValue();
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {
        return loList.addValueChangeHandler(handler);
    }


    private static String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    }

    public static class LearningObjectiveList extends Composite /*implements HasModelDTOValue*/{
        protected List<ModelDTO> modelDTOList = new ArrayList<ModelDTO>();
        OutlineNodeModel outlineModel = new OutlineNodeModel();
        OutlineManager outlineComposite = new OutlineManager();
        
        public LearningObjectiveList(){
            super.initWidget(outlineComposite);
            List<String> list = new ArrayList<String>();
            list.add("");
            addSelectedLOs(list);
        }
        public ModelDTOValue getValue() {
            ModelDTOValue.ListType list = new ModelDTOValue.ListType();
            modelDTOList = new ArrayList<ModelDTO>();
            // get from outline model
            OutlineNode[] outlineNodes = outlineModel.toOutlineNodes(); 
            for(OutlineNode outlineNode: outlineNodes){
                ModelDTO modelDTO = new ModelDTO();
                ModelDTOValue.StringType str = new ModelDTOValue.StringType();
                str.set(((TextBox)outlineNode.getUserObject()).getText());
                modelDTO.put("value", str);

                ModelDTOValue.IntegerType intT = new ModelDTOValue.IntegerType();
                intT.set(modelDTOList.size());
                modelDTO.put("sequence",intT);
                
                intT = new ModelDTOValue.IntegerType();
                intT.set(outlineNode.getIndentLevel());
                modelDTO.put("level",intT);
                modelDTOList.add(modelDTO);
            }
            // fill the list of ModelDTO to ModelDTOValue.ModelDTOType 
            for(ModelDTO dto:modelDTOList){
                ModelDTOValue.ModelDTOType dtoValue = new ModelDTOValue.ModelDTOType();
                dtoValue.set(dto);
                list.get().add(dtoValue);
            }
            return list;
        }
        public void setValue(ModelDTOValue value) {
            ModelDTOValue.ListType list = (ModelDTOValue.ListType)value;
            modelDTOList = new ArrayList<ModelDTO>();
            // fill the ModelDTOValue.ModelDTOType to List<ModelDTO>
            for(ModelDTOValue dto : list.get()){
                ModelDTOValue.ModelDTOType dtoType = (ModelDTOValue.ModelDTOType)dto;
                modelDTOList.add(dtoType.get());
            }
            reDraw();
        }

        public void addSelectedLOs(List<String> loDescription) {
            for(String strValue:loDescription){
                ModelDTO modelDTO = new ModelDTO();
                ModelDTOValue.StringType str = new ModelDTOValue.StringType();
                str.set(strValue);
                modelDTO.put("value", str);

                ModelDTOValue.IntegerType intT = new ModelDTOValue.IntegerType();
                intT.set(new Integer( modelDTOList.size()));
                modelDTO.put("sequence",intT);
                
                intT = new ModelDTOValue.IntegerType();
                intT.set(0);
                modelDTO.put("level",intT);
                
                modelDTOList.add(modelDTO);
            }
            reDraw();
        }
        private void reDraw(){
          outlineModel.clearNodes();
          for (int i = 0; i < modelDTOList.size(); i++) {
            OutlineNode aNode = new OutlineNode();
            aNode.setModel(outlineModel);
            aNode.setUserObject(new TextBox());
            
            String strvalue = ((ModelDTOValue.StringType)modelDTOList.get(i).get("value")).get();
            int level = ((ModelDTOValue.IntegerType)modelDTOList.get(i).get("level")).get();
            int sequence = ((ModelDTOValue.IntegerType)modelDTOList.get(i).get("sequence")).get();
            // the LO from server should be in the right order
            ((TextBox)aNode.getUserObject()).setText(strvalue);
            aNode.setIndentLevel(level);
            outlineModel.addOutlineNode(aNode);
          }
          outlineComposite.setModel(outlineModel);
          outlineModel.addChangeHandler(new ChangeHandler() {
            public void onChange(ChangeEvent event) {
              outlineComposite.render();
            }
          });

          outlineComposite.render();
          
        }
        public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {
//            for (HasModelDTOValue widget : modelDTOValueWidgets) {
  //              widget.addValueChangeHandler(handler);
    //        }
            return new NOOPListValueChangeHandler();
        }
        public void updateModelDTOValue() {
            
        }

        public void setValue(ModelDTOValue value, boolean fireEvents) {
         
            
        }
        private class NOOPListValueChangeHandler implements HandlerRegistration {
            public void removeHandler() {
            }
        }  
    }
/*
    public class LearningObjectiveList extends SimpleMultiplicityComposite {
        private static final String STYLE_HIGHLIGHTED_ITEM = "KS-LOBuilder-Highlighted-Item";
        private static final String DESC_KEY = "desc";

        {
            setAddItemLabel(getLabel(LUConstants.LEARNING_OBJECTIVE_ADD_LABEL_KEY));
//            setShowDelete(false);
        }

        @Override
        public void redraw() {
            super.redraw();
            // populate with at least NUM_INITIAL_LOS items,
            // even if there aren't that many defined yet
            int startIdx = null == modelDTOList ? 0 : modelDTOList.get().size();
            for (int i = startIdx; i < NUM_INITIAL_LOS; i++) {
                addItem();
            }
        }

        @Override
        public Widget createItem() {
            final MultiplicitySection multi = new MultiplicitySection(CluDictionaryClassNameHelper.LO_INFO_CLASS,
                    "kuali.lo.type.singleUse", "draft");
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);

            final LOPicker picker = new LOPicker();
            picker.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {


                }
            });
            picker.addMoveUpAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    moveUp(multi);
                }
            });
            picker.addMoveDownAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    moveDown(multi);
                }
            });
            picker.addDeleteAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    delete(multi);
                }
            });
            FieldDescriptor fd = new FieldDescriptor("desc", null, Type.STRING, picker);
            ns.addField(fd);
            ns.addStyleName("KS-LOBuilder-Section");
            multi.addSection(ns);

            return multi;
        }

        private void removeOldHighlights() {
            List<HasModelDTOValue> widgets = modelDTOValueWidgets;
            for (HasModelDTOValue w: widgets) {
                ((Widget)w).removeStyleName(STYLE_HIGHLIGHTED_ITEM);
            }
        }

        private void addHighlights(List<Integer> selectedWidgets) {
            for (int i: selectedWidgets) {
                Widget w = (Widget)modelDTOValueWidgets.get(i);
                w.addStyleName(STYLE_HIGHLIGHTED_ITEM);

            }
        }

        private void addSelectedLOs(List<String> selected) {
            this.removeOldHighlights();
            List<Integer> addedLos = new ArrayList<Integer>();
            if (selected.size() > 0){
                for (String loDesc : selected ) {
                    int a = this.addSelectedLO(loDesc);
                    addedLos.add(a);
                }
            }
            this.redraw(); 
            this.addHighlights(addedLos);                   
        }

        private int addSelectedLO(String loDescription) {

            int widgetKey = -1;
            boolean foundEmptyWidget = false;

            for (ModelDTOValue v: modelDTOList.get()) {
                ModelDTO model = ((ModelDTOType) v).get();
                widgetKey++;
                if (model.get(DESC_KEY) == null) {
                    model.put(DESC_KEY, loDescription);
                    foundEmptyWidget = true;
                    break;
                }
            }
            if (!foundEmptyWidget) {
                this.addItem();
                widgetKey = modelDTOList.get().size()-1;
                //FIXME: is the new item always going to be the last one?
                ModelDTOValue v = modelDTOList.get().get(widgetKey);
                ModelDTO model = ((ModelDTOType) v).get();
                model.put(DESC_KEY, loDescription);
            }

            return widgetKey;
        }

        private void moveUp(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index ==0){
                return;
            }
            LearningObjectiveList.this.itemsPanel.remove(decrator);
            LearningObjectiveList.this.itemsPanel.insert(decrator,index -1 );

        }
        private void moveDown(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index == LearningObjectiveList.this.itemsPanel.getWidgetCount()-1){
                return;
            }
            LearningObjectiveList.this.itemsPanel.remove(decrator);
            LearningObjectiveList.this.itemsPanel.insert(decrator,index+1 );

        }
        private void delete(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index == -1){
                return;
            }
            super.decorateItemWidget(item);
            LearningObjectiveList.this.itemsPanel.remove(decrator);
        }
    }
    
   
*/
}
