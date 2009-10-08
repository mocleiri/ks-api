package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.search.dto.QueryParamValue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrgPicker extends Composite implements HasValue<String>, HasFocusHandlers, HasBlurHandlers {
	
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
	final SearchSuggestOracle orgSearchOracle = new SearchSuggestOracle(orgRpcServiceAsync,
	        "org.search.orgByShortNameAndType", 
	        "org.queryParam.orgShortName",
	        "org.queryParam.orgId", 
	        "org.resultColumn.orgId", 
	        "org.resultColumn.orgShortName");
    final KSSuggestBox suggestBox = new KSSuggestBox(orgSearchOracle);
	
	final KSAdvancedSearchWindow orgSearchWidget = new KSAdvancedSearchWindow(orgRpcServiceAsync, "org.search.orgQuickViewByHierarchyShortName","org.resultColumn.orgId");   
    
    VerticalPanel root = new VerticalPanel();
    
    private final FocusGroup focus = new FocusGroup(this);
    
	public OrgPicker() {
		super();

		// FIXME when org search window is displayed, call focus.setSuppressed(true), and set it to false afterwards
		focus.addWidget(suggestBox);
		
		initWidget(root);
		orgSearchOracle.setTextWidget(suggestBox.getTextBox());
		
		//Restrict searches to Department Types
		ArrayList<QueryParamValue> params = new ArrayList<QueryParamValue>();
		QueryParamValue orgTypeParam = new QueryParamValue();
		orgTypeParam.setKey("org.queryParam.orgType");
		orgTypeParam.setValue("kuali.org.Department");
		params.add(orgTypeParam);
		orgSearchOracle.setAdditionalQueryParams(params);
		
		root.add(suggestBox);

	}

	@Override
	public String getValue() {
		return suggestBox.getSelectedId();
	}

	@Override
	public void setValue(String value) {
	    suggestBox.reset();
	    suggestBox.setValue(value);
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		// TODO Auto-generated method stub
		setValue(value);		
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return suggestBox.addValueChangeHandler(handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		super.fireEvent(event);
	}
	
	public void clear(){
	    suggestBox.reset();
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return focus.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return focus.addBlurHandler(handler);
	}
}
