package org.kuali.student.core.organization.ui.client.view;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.visualization.client.AjaxLoader;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.Handler;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.OrgChart.Options;

public class OrgChartWidget extends Composite {
    DeckPanel w = new DeckPanel();
	ScrollPanel root = new ScrollPanel();
	String orgId;
	String hierarchyId;
	int maxLevels;
	boolean loaded = false;

	public OrgChartWidget(String orgId, String hierarchyId, int maxLevels) {
		super.initWidget(w);
		w.add(root);

		root.setStyleName("ks-orgChart");
		this.orgId=orgId;
		this.hierarchyId=hierarchyId;
		this.maxLevels=maxLevels;

        KSLabel lbl = new KSLabel("Please Wait...");
        w.add(lbl);
        w.showWidget(1);
	}

	protected void onLoad() {
	    if (!loaded){
            Runnable onLoadCallback = new Runnable() {
                public void run() {

                	OrgRpcService.Util.getInstance().getOrgDisplayTree(orgId, hierarchyId, maxLevels, new AsyncCallback<List<OrgTreeInfo> >(){

    					public void onFailure(Throwable caught) {
    						Window.alert(caught.getMessage());
    					}

    					public void onSuccess(List<OrgTreeInfo>  results) {

    						final DataTable data = DataTable.create();
    	                    data.addColumn(ColumnType.STRING, "OrgId");
    	                    data.addColumn(ColumnType.STRING, "ParentOrgId");
    						data.addRows(results.size() + 1);

    						int lineCount=0;

    						for(OrgTreeInfo orgTreeInfo:results){
     							data.setCell(lineCount, 0, orgTreeInfo.getOrgId(), orgTreeInfo.getDisplayName(), null);

    							if(orgTreeInfo.getParentId()!=null && !"".equals(orgTreeInfo.getParentId())){
    			                	data.setCell(lineCount, 1, orgTreeInfo.getParentId(), null, null);
    			                }

    							lineCount++;
    		               	}

    	                    final Options orgChartOpts = Options.create();
    	                    orgChartOpts.setOption("size", "small");
    	                    final OrgChart o = new OrgChart(data, orgChartOpts);

    	                    o.addSelectHandler(new SelectHandler(){

    							public void onSelect(SelectEvent event) {
   		                        	JsArray<Selection> selections = o.getSelections();
		        					if (selections.length() != 1) {
		        						Window.alert("Logic error. No Organization selected");
		        						return;
		        					}
    		                        final OrganizationWidget orgCreatePanel = new OrganizationWidget(data.getValueString(selections.get(0).getRow(), 0), OrganizationWidget.Scope.ORG_MODIFY_ALL);
    		                        orgCreatePanel.addCloseButton("Close", new ClickHandler() {
    		                            @Override
    		                            public void onClick(ClickEvent event) {
    		                                w.remove(w.getWidgetCount() - 1);
    		                                w.showWidget(w.getWidgetCount() - 1);
    		                            }
    		                        });
    		                        orgCreatePanel.addSelectionHandler(new SelectionHandler<OrgInfo>(){
                                        @Override
                                        public void onSelection(SelectionEvent<OrgInfo> event) {
                                            data.setFormattedValue(o.getSelections().get(0).getRow(), 0, event.getSelectedItem().getLongName());
                                            o.draw(data, orgChartOpts);
                                        }
                                    });
    		                        w.add(orgCreatePanel);
    		                        w.showWidget(w.getWidgetCount() - 1);
    							}

    	                    });

    	                    root.add(o);
    	                    while(w.getWidgetCount() != 1)
    	                        w.remove(w.getWidgetCount() - 1);
    	                    w.showWidget(0);


    					}
                	});
                }
              };

              // Load the visualization api, passing the onLoadCallback to be called
              // when loading is done.
              AjaxLoader.loadVisualizationApi(onLoadCallback, OrgChart.PACKAGE);

              loaded=true;
	    } else {
            while(w.getWidgetCount() != 1)
                w.remove(w.getWidgetCount() - 1);
            w.showWidget(0);
	    }

	}
}
