package org.kuali.student.ui.kitchensink.client.kscommons.button;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;

public class ButtonExample extends Composite {

    final SimplePanel main = new SimplePanel();
    FlexTable table = new FlexTable();
    final KSLabel title = GWT.create(KSLabel.class);
    
    final KSButton button1 = GWT.create(KSButton.class);
    final KSButton button2 = GWT.create(KSButton.class);
    final KSButton button3 = GWT.create(KSButton.class);

    public ButtonExample() {
        table.addStyleName(STYLE_EXAMPLE);
        
        title.init("Click a button", false);
        
        button1.init("Number 1",new MyClickHandler());
        button2.init("Number 2",new MyClickHandler());
        button3.init("Number 3",new MyClickHandler());
             
        int row = 0;
        table.setWidget(row, 1, title);
        
        row++;
        table.setWidget(row,0, button1);
        table.setWidget(row,1, button2);
        table.setWidget(row,2, button3);
        
        main.add(table);

        super.initWidget(main);
    }
    
    public class MyClickHandler implements ClickHandler  {

        @Override
        public void onClick(ClickEvent event) {
//TODO
//            KSButton b = (KSButton)(event.getSource());
//            
//            Window.alert("You clicked button " + b.getButton().getText());
            
        }     
    }
}
