package org.kuali.student.core.organization.ui.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrgLocateName extends Composite {
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    
    DeckPanel w = new DeckPanel();
    Panel root = new HorizontalPanel();
    private KSCheckBoxList orgTypes;
    SimplePanel resultPanel = new SimplePanel();
    KSSelectableTableList resultTable = null;
    KSLabel noResults = new KSLabel("No organizations found.");

    boolean loaded = false;

    public OrgLocateName() {
        super.initWidget(w);
        w.add(root);

        final Grid grid = new Grid(3, 9);
        final ClickHandler handler = new ClickHandler() {

            ToggleButton source = null;

            @Override
            public void onClick(ClickEvent event) {
                KSImage image = new KSImage("images/loading.gif");
                resultPanel.setWidget(image);
                
                if(source != null)
                    source.setDown(false);
                if(event != null)
                    source = (ToggleButton) event.getSource();
                source.setDown(true);

                List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>();
                QueryParamValue qpv1 = new QueryParamValue();
                qpv1.setKey("org.queryParam.orgShortName");
                qpv1.setValue(source.getText() + '%');
                queryParamValues.add(qpv1);

                orgRpcServiceAsync.searchForResults("org.search.orgQuickLongViewByFirstLetter", queryParamValues, new AsyncCallback<List<Result>>() {

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(List<Result> result) {
                        if (result == null || result.size() <= 0) {
                            resultPanel.setWidget(noResults);
                        } else {
                            final Map<String, Result> orgInfoMap = new HashMap<String, Result>();
                            for (Result r : result) {
                                List<String> selectedItems = orgTypes.getSelectedItems();
                                if (selectedItems.isEmpty() || selectedItems.contains(r.getResultCells().get(2).getValue()))
                                    orgInfoMap.put(r.getResultCells().get(0).getValue(), r);
                            }
                            ListItems items = new ListItems() {

                                public List<String> getAttrKeys() {
                                    return Arrays.asList("Organization Name");
                                }

                                public String getItemAttribute(String id, String attrkey) {
                                    Result r = orgInfoMap.get(id);

                                    if (attrkey.equals("Organization Name")) {
                                        return r.getResultCells().get(1).getValue();
                                    }

                                    return null;
                                }

                                @Override
                                public int getItemCount() {
                                    return orgInfoMap.size();
                                }

                                @Override
                                public List<String> getItemIds() {
                                    List<String> keys = new ArrayList<String>();

                                    for (String s : orgInfoMap.keySet()) {
                                        keys.add(s);
                                    }

                                    return keys;
                                }

                                @Override
                                public String getItemText(String id) {
                                    return ((Result) orgInfoMap.get(id)).getResultCells().get(1).getValue();
                                }

                            };
                            // OrgInfoList orgInfoList = new OrgInfoList(result);
                            resultTable = new KSSelectableTableList();
                            resultTable.setMultipleSelect(false);
                            resultTable.setListItems(items);
                            resultPanel.setWidget(resultTable);
                            resultTable.addSelectionChangeHandler(new SelectionChangeHandler() {

                                @Override
                                public void onSelectionChange(final KSSelectItemWidgetAbstract selected) {
                                    if(selected.getSelectedItem() != null) {
                                        final OrganizationWidget orgCreatePanel = new OrganizationWidget(selected.getSelectedItem(), OrganizationWidget.Scope.ORG_MODIFY_ALL);
                                        orgCreatePanel.addCloseButton("Back", new ClickHandler() {
                                            @Override
                                            public void onClick(ClickEvent event) {
                                                w.remove(w.getWidgetCount() - 1);
                                                w.showWidget(w.getWidgetCount() - 1);
                                            }
                                        });
                                        w.add(orgCreatePanel);
                                        w.showWidget(w.getWidgetCount() - 1);
                                    }
                                }});
                            // selectButton.setVisible(true);
                        }
                    }
                });
            }
        };
        for (int i = 0; i < grid.getRowCount(); i++)
            for (int j = 0; j < grid.getColumnCount(); j++) {
                grid.setWidget(i, j, new ToggleButton(i * grid.getColumnCount() + j == 26 ? "#" : Character.toString((char) (65 + i * grid.getColumnCount() + j)), handler));
            }

        orgTypes = new KSCheckBoxList();
        orgTypes.addStyleName("KS-CheckBox-List");
        orgTypes.addSelectionChangeHandler(new SelectionChangeHandler() {
            @Override
            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
                handler.onClick(null);
            }});

        Panel browsePanel = new VerticalPanel();
        browsePanel.add(grid);
        browsePanel.add(new HTML("<hr/>"));
        KSLabel subheader = new KSLabel("Limit Results");
        subheader.addStyleName("KS-Label-Subheader");
        browsePanel.add(subheader);
        KSLabel checkboxLbl = new KSLabel("Type of Organization");
        checkboxLbl.addStyleName("KS-Label-Checkbox");
        browsePanel.add(checkboxLbl);
        browsePanel.add(orgTypes);

        resultPanel.addStyleName("KS-Org-Search-Widget-Results");

        root.add(browsePanel);
        root.add(resultPanel);

    }

    protected void onLoad() {
        if (!loaded) {
            orgRpcServiceAsync.getOrgTypes(new AsyncCallback<List<OrgTypeInfo>>() {

                @Override
                public void onFailure(Throwable caught) {}

                @Override
                public void onSuccess(List<OrgTypeInfo> result) {
                    final Map<String, String> values = new LinkedHashMap<String, String>();
                    for (OrgTypeInfo orgTypeInfo : result) {
                        values.put(orgTypeInfo.getId(), orgTypeInfo.getName());
                    }
                    final ListItems items = new ListItems() {

                        @Override
                        public List<String> getAttrKeys() {
                            return null;
                        }

                        @Override
                        public String getItemAttribute(String id, String attrkey) {
                            return null;
                        }

                        @Override
                        public int getItemCount() {
                            return values.size();
                        }

                        @Override
                        public List<String> getItemIds() {
                            return new ArrayList<String>(values.keySet());
                        }

                        @Override
                        public String getItemText(String id) {
                            return values.get(id);
                        }

                    };
                    orgTypes.setListItems(items);
                }
            });
        }
        while(w.getWidgetCount() != 1)
            w.remove(w.getWidgetCount() - 1);
        w.showWidget(0);

    }
}
