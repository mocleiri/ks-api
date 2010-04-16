package org.kuali.student.common.ui.client.widgets.search;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

public class AdvancedSearchWindow {
	private KSLightBox dialog = new KSLightBox();
	private KSLabel titleBar = null;
	private List<Callback<List<SelectedResults>>> callbacks = new ArrayList<Callback<List<SelectedResults>>>();
	private SearchPanel searchPanel;
	private VerticalFlowPanel layout = new VerticalFlowPanel();
 
	public static enum Style {
        PRIMARY("KS-Advanced-Search-Window");

        private String style;

        private Style(String style) {
            this.style = style;
        }

        public String getStyle() {
            return style;
        }
    };


    private ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

        @Override
        public void exec(ConfirmCancelEnum result) {
            switch(result){
                case CONFIRM:
                    List<SelectedResults> selectedItems = searchPanel.getSelectedValues();
                    for(Callback<List<SelectedResults>> callback: callbacks){
                    	callback.exec(selectedItems);
                    }
                    dialog.hide();
                    break;
                case CANCEL:
                    dialog.hide();
                    break;
            }
        }
    });
	
	public AdvancedSearchWindow(String fieldMessageKey, SearchPanel panel){
	    layout.addStyleName(Style.PRIMARY.getStyle());
		searchPanel = panel;
		//TODO use messages here
		titleBar = new KSLabel(fieldMessageKey);
		titleBar.addStyleName("KS-Advanced-Search-Header-Label");
		
		layout.add(titleBar);
		layout.add(panel);
		layout.add(buttonPanel);
		dialog.setWidget(layout);
	}
	
	public void addSelectionCompleteCallback(Callback<List<SelectedResults>> callback){
		callbacks.add(callback);
	}
	
	public void show(){
		searchPanel.setupSearch();
		dialog.show();
	}
	
	public void hide(){	
		dialog.hide();
	}
}
