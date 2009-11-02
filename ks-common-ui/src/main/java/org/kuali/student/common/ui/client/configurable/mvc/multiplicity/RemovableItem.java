/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.configurable.mvc.Section;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This wraps a widget item
 * 
 * @author Kuali Student Team
 */
public class RemovableItem extends MultiplicityItem {
    private String itemLabel = "Item ";
    private boolean useDeleteLabel = false;
    private boolean loaded = false;
    private KSLabel headerLabel;

    protected VerticalFlowPanel itemPanel = new VerticalFlowPanel();
    
    public RemovableItem(){        
        initWidget(itemPanel);
    }

    public void onLoad(){
    }
    
    private Widget generateRemoveWidget() {
        ClickHandler ch = new ClickHandler() {
            public void onClick(ClickEvent event) {
                getRemoveCallback().exec(RemovableItem.this);
            }
        };

        Widget returnWidget;
        if (useDeleteLabel) {
            Label deleteLabel = new Label("Delete");
            deleteLabel.addStyleName("KS-Multiplicity-Labels");
            deleteLabel.addClickHandler(ch);
            returnWidget = deleteLabel;
        } else {
            returnWidget = new KSButton("-", ch);
        }
        return returnWidget;
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem#clear()
     */
    @Override
    public void clear() {
        // TODO We need a clear/redraw interface to redraw decorated widget
        loaded = false;
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem#redraw()
     */
    @Override
    public void redraw() {
        Widget item = getItemWidget();
        if (!loaded){
            itemPanel.addStyleName("KS-Multiplicity-Item");
    
            HorizontalPanel headerPanel = new HorizontalPanel();
            headerPanel.addStyleName("KS-Multiplicity-Item-Header");
            headerLabel = new KSLabel(itemLabel + " " + getItemKey());
            headerPanel.add(headerLabel);
            headerPanel.add(generateRemoveWidget());
    
            itemPanel.add(headerPanel);
            itemPanel.add(item);
            
            loaded = true;
        }

        headerLabel.setText(itemLabel + " " + getItemKey());
        if (item instanceof Section){
            ((Section)item).redraw();
        }
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public void setUseDeleteLabel(boolean useDeleteLabel) {
        this.useDeleteLabel = useDeleteLabel;
    }

}
