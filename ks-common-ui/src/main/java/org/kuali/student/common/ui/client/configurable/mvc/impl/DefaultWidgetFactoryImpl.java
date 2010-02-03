package org.kuali.student.common.ui.client.configurable.mvc.impl;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.search.AdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.client.widgets.search.SuggestBoxWAdvSearch;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.MetadataInterrogator;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Metadata.WriteAccess;
import org.kuali.student.core.assembly.data.MetadataInterrogator.ConstraintIds;

import com.google.gwt.user.client.ui.Widget;

public class DefaultWidgetFactoryImpl extends DefaultWidgetFactory {	

	@Override
	public Widget getWidget(FieldDescriptor field) {
		return getWidget(field.getMetadata());
	}

	@Override
	public Widget getWidget(Metadata meta) {
		WidgetConfigInfo config = new WidgetConfigInfo();
		if (meta != null) {
			config.access = meta.getWriteAccess();
			config.isMultiLine = MetadataInterrogator.isMultilined(meta);
			config.isRichText = MetadataInterrogator.hasConstraint(meta, ConstraintIds.RICH_TEXT);
			config.maxLength = MetadataInterrogator.getSmallestMaxLength(meta);
			config.type = meta.getDataType();
			config.lookupMeta = meta.getLookupMetadata();
			config.additionalLookups = meta.getAdditionalLookups();			
		}
		return _getWidget(config);
	}

	@Override
	public Widget getWidget(LookupParamMetadata meta) {
		WidgetConfigInfo config = new WidgetConfigInfo();
		if (meta != null) {
			config.access = meta.getWriteAccess();
			config.type = meta.getDataType();
			config.lookupMeta = meta.getChildLookup();
		}
		return _getWidget(config);
	}

	private Widget _getWidget(WidgetConfigInfo config) {
		Widget result = null;
			
		if (config.access != null && config.access == WriteAccess.NEVER) {
			
			result = new KSLabel();
			
		// create a suggest box and/or advanced search widget if lookup metadata exists
		} else if (config.lookupMeta != null) {
			
			SearchPanel picker;
			if ((config.additionalLookups != null) && (config.additionalLookups.size() > 0)) {
				//TODO
				picker = null;
			} else {
				picker = new SearchPanel(config.lookupMeta);				
			}
	        final AdvancedSearchWindow courseSearchWindow = new AdvancedSearchWindow(config.lookupMeta.getName(), picker);
	        KSTextBox adminDepTextBox = new KSTextBox();   //FIXME this will be a suggest box
	        final SuggestBoxWAdvSearch adminDepPicker = new SuggestBoxWAdvSearch(adminDepTextBox, courseSearchWindow);
	        result = adminDepPicker;
	        
	        courseSearchWindow.addSelectionCompleteCallback(new Callback<List<SelectedResults>>(){
	            public void exec(List<SelectedResults> results) {
	                if (results.size() > 0){
	                	adminDepPicker.getSuggestBox().setText(results.get(0).getDisplayKey());
	                	adminDepPicker.getSuggestBox().setValue(results.get(0).getReturnKey());
	                    courseSearchWindow.hide();
	                }                
	            }            
	        });
	        
		} else {
			
			switch (config.type) {
			case BOOLEAN:
				result = new KSCheckBox();
				break;
				
			case DATE:
			case TRUNCATED_DATE:
				result = new KSDatePicker();
				break;
				
			case DATA:
				if (config.isRichText) {
					result = new KSRichEditor();
				}
				break;				
			}			
		}		
		
		if (result == null) {
			// default to textbox or textarea
			if (config.isMultiLine) {
				result = new KSTextArea();
			} else {
				KSTextBox text = new KSTextBox();
				if (config.maxLength != null) {
					text.setMaxLength(config.maxLength);
				}
				result = text;
			}
		}
		
		return result;
	}

	class WidgetConfigInfo {
		public DataType type = null;
		public Integer maxLength = null;
		public WriteAccess access = null;
		public boolean isRichText = false;
		public boolean isMultiLine = false;
		public LookupMetadata lookupMeta = null;
		public List<LookupMetadata> additionalLookups = null;
	}
}
