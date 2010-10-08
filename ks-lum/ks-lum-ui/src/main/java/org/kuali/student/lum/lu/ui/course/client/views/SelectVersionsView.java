package org.kuali.student.lum.lu.ui.course.client.views;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.search.SearchResultsTable;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Metadata.WriteAccess;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SortDirection;
import org.kuali.student.lum.lu.ui.course.client.controllers.VersionsController;
import org.kuali.student.lum.lu.ui.main.client.AppLocations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class SelectVersionsView extends ViewComposite{


	private VerticalFieldLayout layout = new VerticalFieldLayout();
	//private KSDocumentHeader header = new KSDocumentHeader();
	private ButtonLayout buttons = new ButtonLayout();
	SearchResultsTable table = new SearchResultsTable();
	MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
	private String id1;
	private String id2;
	
	private VersionsController parent;

	public SelectVersionsView(VersionsController controller, String name,
			Enum<?> viewType) {
		super(controller, name, viewType);
		this.initWidget(layout);
		layout.addStyleName("selectVersions");
		//layout.add(header);
		//header.addStyleName("Lum-DocumentHeader-Spacing");
		//header.setTitle(name);
		layout.addWidget(table);
		layout.addButtonLayout(buttons);
		parent = controller;
		//layout.addWidget(widget)
		layout.setLayoutTitle(SectionTitle.generateH2Title("Select Versions"));
		layout.setInstructions("Select two drafts to compare, or select one to view");
		buttons.addButton(new KSButton("Show Version(s)", new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(table.getSelectedIds().size() == 1){
					id1 = table.getSelectedIds().get(0);
					id2 = null;
					getController().showView(VersionsController.Views.VERSION_VIEW);
				}
				else if(table.getSelectedIds().size() == 2){
					id1 = table.getSelectedIds().get(0);
					id2 = table.getSelectedIds().get(1);
					getController().showView(VersionsController.Views.VERSION_COMPARE);
				}
				else{
					Window.alert("Please select either a single version to VIEW or two versions to COMPARE");
				}
				
			}
		}));
		buttons.addButton(new KSButton("Return to Current Course", ButtonStyle.ANCHOR_LARGE_CENTERED, new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ViewContext context = new ViewContext();
				context.setId(parent.getCurrentVersionId());
				context.setIdType(IdType.OBJECT_ID);
				HistoryManager.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), context);
			}
		}));
	}

	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
		
    	Metadata searchMetadata = parent.getDefinition().getMetadata(QueryPath.concat("searchCourseVersions"));
    	//List<LookupMetadata> additionalLookups = searchMetadata.getAdditionalLookups();
    	LookupMetadata versionSearch = searchMetadata.getInitialLookup();
/*    	for(int i = 0; i < additionalLookups.size(); i++){
    		if(additionalLookups.get(i).getWidget() == LookupMetadata.Widget.ADVANCED_LIGHTBOX){
    			versionSearch = additionalLookups.get(i);
    			break;
    		}
    	}*/
    	table.performSearch(generateRequest(versionSearch), versionSearch.getResults(), versionSearch.getResultReturnKey(), false);
		onReadyCallback.exec(true);

	}
	
	private SearchRequest generateRequest(LookupMetadata versionSearch){
    	SearchRequest sr = new SearchRequest();
        List<SearchParam> params = new ArrayList<SearchParam>();
        SearchParam param = new SearchParam();
        param.setKey("lu.queryParam.cluVersionIndId");
        param.setValue(parent.getVersionIndId());
        params.add(param);
        sr.setSortDirection(SortDirection.DESC);
        sr.setParams(params);
        sr.setSearchKey(versionSearch.getSearchTypeId());
        if (versionSearch.getResultSortKey() != null) {
            sr.setSortColumn(versionSearch.getResultSortKey());
        }
        return sr;
	}

	
	public String getId1() {
		return id1;
	}

	public String getId2() {
		return id2;
	}
}
