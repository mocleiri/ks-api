package org.kuali.student.enumeration.web.client.view;

import org.kuali.student.enumeration.dto.Context;
import org.kuali.student.enumeration.dto.Contexts;
import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EnumeratedValueComposit extends Composite {
    FlowPanel content = new FlowPanel();

    TextBox abbrevValueBox = new TextBox();
    TextBox codeBox = new TextBox();
    TextBox effectiveDateBox = new TextBox();
    TextBox expirationDateBox = new TextBox();
    TextBox sortKeyBox = new TextBox();
    TextBox valueBox = new TextBox();

    FlexTable fieldTable = new FlexTable();

    DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
    
    public EnumeratedValueComposit() {
        super.initWidget(content);
        content.add(new HTML("abbrevValueBox"));
        content.add(abbrevValueBox);

        content.add(new HTML("codeBox"));
        content.add(codeBox);

        content.add(new HTML("effectiveDateBox (dd/MM/yyyy)"));
        content.add(effectiveDateBox);

        content.add(new HTML("expirationDateBox (dd/MM/yyyy)"));
        content.add(expirationDateBox);

        content.add(new HTML("sortKeyBox"));
        content.add(sortKeyBox);

        content.add(new HTML("Vaue Box"));
        content.add(valueBox);

        fieldTable.setHTML(0, 0, "Type");
        fieldTable.setHTML(0, 1, "Value");
        
        content.add(fieldTable);
        
        Button addRowToContextTable = new Button("Add Context");
        content.add(addRowToContextTable);
        addRowToContextTable.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                int rowCount = fieldTable.getRowCount();
                fieldTable.setWidget(rowCount, 0, new TextBox());
                fieldTable.setWidget(rowCount, 1, new TextBox());
                
            }
        });
        
    }
    public EnumeratedValue getEnumeratedValue(){
        EnumeratedValue value = new EnumeratedValue();
        value.setAbbrevValue(abbrevValueBox.getText());
        value.setCode(codeBox.getText());
        
        value.setEffectiveDate(dateFormat.parse(effectiveDateBox.getText()));
        value.setExpirationDate(dateFormat.parse(expirationDateBox.getText()));
        
        value.setSortKey(Integer.parseInt(sortKeyBox.getText()));
        value.setValue(valueBox.getText());

        for(int i=1;i<fieldTable.getRowCount();i++){
            Context context  = new Context();
            
            System.out.println(fieldTable.getRowCount());
            TextBox typeBox = (TextBox)fieldTable.getWidget(i, 0);
            context.setType(typeBox.getText());
            
            TextBox valueBox = (TextBox)fieldTable.getWidget(i, 1);
            context.setValue(valueBox.getText());
            
            Contexts contexts = new Contexts();
            contexts.getContext().add(context);
           
            value.setContexts(contexts);
        }
        
        return value;
    }
    public void setEnumeratedValue(EnumeratedValue value) {
        
        
        
        abbrevValueBox.setText(value.getAbbrevValue());
        codeBox.setText(value.getCode());
        effectiveDateBox.setText(dateFormat.format(value.getEffectiveDate()));
        expirationDateBox.setText(dateFormat.format(value.getExpirationDate()));
        sortKeyBox.setText(Integer.toString(value.getSortKey()));
        valueBox.setText(value.getValue());
        int rowIndex = 1;
        for (Context context : value.getContexts().getContext()) {
            
            TextBox typeBox = new TextBox();
            typeBox.setText(context.getType());
            

            TextBox valueBox = new TextBox();
            valueBox.setText(context.getValue());
            
            fieldTable.setWidget(rowIndex, 0, typeBox);
            fieldTable.setWidget(rowIndex, 1, valueBox);
            rowIndex = rowIndex + 1;
        }
    }
}
