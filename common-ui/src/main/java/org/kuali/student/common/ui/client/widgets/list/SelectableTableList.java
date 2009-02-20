package org.kuali.student.common.ui.client.widgets.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.visualization.client.AjaxLoader;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;
import com.google.gwt.visualization.client.visualizations.Table.Options.Policy;

public class SelectableTableList extends SelectItemWidget {
    Table table;
    SimplePanel root = new SimplePanel();
    Map<Integer, String> idMapping = new HashMap();
    Map<String, Integer> rowMapping = new HashMap();
    boolean loaded = false;
    
    public SelectableTableList(){
        initWidget(root);
    }
    
    public void deSelectItem(String id) {
        JsArray<Selection> selections = table.getSelections();

        JsArray<Selection> newSelections = (JsArray<Selection>)JsArray.createArray();
        int j = 0;
        for (int i=0; i < selections.length(); i++){
            if (selections.get(i).getRow() != rowMapping.get(id).intValue()){
                newSelections.set(j, selections.get(i));
                j++;
            }
        }       

        table.setSelections(newSelections);
    }

    public List<String> getSelectedItems() {
        JsArray<Selection> selections = table.getSelections();

        List<String> selected = new ArrayList<String>();
        for (int i=0; i < selections.length(); i++){
            selected.add(idMapping.get(new Integer(selections.get(i).getRow())));
        }
        
        return selected;
    }

    public void selectItem(String id) {
        JsArray<Selection> selections = (JsArray<Selection>)JsArray.createArray();
        
        Selection sel = Selection.createRowSelection(rowMapping.get(id).intValue());
        selections.set(0,sel);
        table.setSelections(selections);
    }

    public void onLoad() {               
        Runnable onLoadCallback = new Runnable() {
          public void run() {     
             DataTable data = DataTable.create(); 
             
            ListItems listItems = getListItems();
            List<String> attrKeys = listItems.getAttrKeys();

            for (String attr:attrKeys){
                data.addColumn(ColumnType.STRING, attr);
            }

            int row = 0;
            int col = 0;

            
            for (String id:listItems.getItemIds()){
                data.addRow();
                idMapping.put(row,id);
                rowMapping.put(id,row);
                for (String attr:attrKeys){
                    String value = listItems.getItemAttribute(id, attr);
                    data.setCell(row, col, value, value, null);
                    col++;
                }
                row ++;
                col=0;
            }
            Options options = Options.create();
            options.setPageSize(50);
            options.setPage(Policy.ENABLE);
            
            if (table == null){
                table = new Table();                
                table.addSelectHandler(new SelectHandler(){
                    public void onSelect(SelectEvent event) {
                        fireChangeEvent();
                    }                   
                });
            }

            table.draw(data,options);
            root.setWidget(table);
          }
        };

        AjaxLoader.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
    }
    
}
