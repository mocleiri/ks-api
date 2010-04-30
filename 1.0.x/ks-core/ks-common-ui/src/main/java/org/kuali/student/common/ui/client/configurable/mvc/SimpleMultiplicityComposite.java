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

package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

@Deprecated
public abstract class SimpleMultiplicityComposite extends Composite implements HasModelDTOValue {

    protected FlowPanel mainPanel = new FlowPanel();
    protected FlowPanel itemsPanel = new FlowPanel();
    protected ModelDTOValue.ListType modelDTOList = new ModelDTOValue.ListType();
    protected List<HasModelDTOValue> modelDTOValueWidgets;
    protected boolean loaded = false;
    protected int itemCount = 0;
//  protected boolean updateable = true;
    protected String addItemLabel;
    protected boolean useDeleteLabel = true;
    protected boolean showAdd = true;
    protected boolean showDelete = true;


    //Initialization block
    {
        modelDTOList.set(new ArrayList<ModelDTOValue>());
        initWidget(mainPanel);

    };

    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
     */
    @Override
    public void setValue(ModelDTOValue modelDTOValue) {
        if(modelDTOValue != null){
            assert(modelDTOValue instanceof ModelDTOValue.ListType);
            this.modelDTOList = (ModelDTOValue.ListType)modelDTOValue;
            redraw();
        }
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
     */
    @Override
    public void setValue(ModelDTOValue value, boolean fireEvents) {
        setValue(value);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public ModelDTOValue getValue() {
        return modelDTOList;
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void updateModelDTOValue() {
        for (int i=0; i < modelDTOValueWidgets.size(); i++){
            modelDTOValueWidgets.get(i).updateModelDTOValue();
        }
    }

    // FIXME
    // TODO - this is overridden in MultiplicityComposite, and then overridden
    // back to this method in MultiplicityCompositeWithLabels. If we weren't
    // getting a new widget(s) to do this stuff in the next 6 weeks, I'd refactor
    // the whole mess, but...M2 looms
    protected Widget generateAddWidget() {
        Label addWidget =  new Label(addItemLabel);
        addWidget.addStyleName("KS-Multiplicity-Link-Label");
        addWidget.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                addItem();
            }
        });
        return addWidget;
    }

    /**
     *
     *  This method will remove all data associated with this mode
     *
     */
    public void clear() {
        itemCount = 0;
        if (itemsPanel != null){itemsPanel.clear();}
        //TODO: Should this do iterate over each widget and call their clear()
        if (modelDTOValueWidgets != null){modelDTOValueWidgets.clear();}
        //if (modelDTOList != null){modelDTOList.get().clear();}
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {
        for (HasModelDTOValue widget : modelDTOValueWidgets) {
            widget.addValueChangeHandler(handler);
        }
        return new NOOPListValueChangeHandler();
    }

    public void setAddItemLabel(String addItemLabel) {
        this.addItemLabel = addItemLabel;
    }

    /**
     * This must return a widget that implements the HasModelDTOValue interface. This method
     * will be used when user clicks the "Add" button to add a new item to the list of items.
     *
     * @return
     */
    public abstract Widget createItem();


    public void setDisplayOnly() {
        this.showAdd = false;
        this.showDelete = false;
    }

    public void setShowAdd(boolean showAdd) {
        this.showAdd = showAdd;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }

    protected Widget decorateItemWidget(Widget listItem ) {
        return new SimpleMultiplicityItemWidgetDecorator(listItem);
    }

    private class SimpleMultiplicityItemWidgetDecorator extends Composite {
        VerticalFlowPanel itemPanel;

        private SimpleMultiplicityItemWidgetDecorator(Widget listItem){
            itemPanel = new VerticalFlowPanel();
            initWidget(itemPanel);
            if (listItem instanceof SimpleMultiplicityComposite){
                ((SimpleMultiplicityComposite) listItem).redraw();
            }
            itemCount++;
            itemPanel.addStyleName("KS-Multiplicity-Item");

            itemPanel.add(listItem);
            if (showDelete) {
                itemPanel.add(generateRemoveWidget(listItem, this));
            }
        }
    }

    /**
     * Init method to support lazy initialization of multiplicity composite
     *
     */
    public void redraw() {
        //Setup if not already loaded
        if (!loaded) {
            modelDTOValueWidgets = new ArrayList<HasModelDTOValue>();

            mainPanel.addStyleName("KS-Multiplicity-Composite");
            mainPanel.add(itemsPanel);

            if (showAdd) {
                mainPanel.add(generateAddWidget());
            }
            loaded = true;
        } else {
            clear();
        }

        //Update the composite with values from ModelDTO
        if (modelDTOList != null){
            List<ModelDTOValue> modelDTOValueList = modelDTOList.get();
            GWT.log("ModelDTOList = " + modelDTOList.toString(), null);
            GWT.log("ModelDTOValueList length = " + modelDTOValueList.size(), null);
            for (int i=0; i < modelDTOValueList.size(); i++){
                addItem(modelDTOValueList.get(i));
            }
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        redraw();
    }

    /**
     * 
     * This method creates a new item, populated using values from the modelDTOValue object
     * 
     * @param modelDTOValue
     */
    private void addItem(ModelDTOValue modelDTOValue) {
        Widget newItemWidget = createItem();
        Widget listItem = decorateItemWidget(newItemWidget);
        ((HasModelDTOValue)newItemWidget).setValue(modelDTOValue);
        GWT.log("ModelDTOValueActual = " + modelDTOValue.toString(), null);
        GWT.log("ModelDTOValue in Section = " + ((HasModelDTOValue)newItemWidget).getValue().toString(), null);
        itemsPanel.add(listItem);
        modelDTOValueWidgets.add((HasModelDTOValue)newItemWidget);
    }

    /**
     * This method creates a new empty item.
     *
     */
    protected void addItem() {
        Widget newItemWidget = createItem();
        Widget listItem = decorateItemWidget(newItemWidget);

        itemsPanel.add(listItem);
//      if (newItemWidget instanceof SimpleMultiplicityComposite){
//      ((SimpleMultiplicityComposite)newItemWidget).redraw();
//      }

        modelDTOList.get().add(((HasModelDTOValue)newItemWidget).getValue());
        modelDTOValueWidgets.add((HasModelDTOValue)newItemWidget);
    }

    private void removeItem(final Widget listItem, final Composite parent) {
        ModelDTOValue listItemValue = ((HasModelDTOValue) listItem).getValue();
        modelDTOList.get().remove(listItemValue);
        itemsPanel.remove(parent);
        modelDTOValueWidgets.remove(listItem);
    }

    protected Widget generateRemoveWidget(final Widget listItem, final Composite parent) {
        ClickHandler ch = new ClickHandler(){
            public void onClick(ClickEvent event) {
                removeItem(listItem, parent);
            }


        };

        Widget returnWidget;
        if (useDeleteLabel) {
            Label deleteLabel = new Label("Delete");
            deleteLabel.addStyleName("KS-Multiplicity-Link-Label");
            deleteLabel.addClickHandler(ch);
            returnWidget = deleteLabel;
        }
        else {
            returnWidget = new KSButton("-", ch);
        }
        return returnWidget;
    }

    private class NOOPListValueChangeHandler implements HandlerRegistration {

        /* (non-Javadoc)
         * @see com.google.gwt.event.shared.HandlerRegistration#removeHandler()
         */
        @Override
        public void removeHandler() {
        }

    }     
}
