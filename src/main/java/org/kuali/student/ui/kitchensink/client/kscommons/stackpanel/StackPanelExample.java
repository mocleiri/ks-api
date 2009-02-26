package org.kuali.student.ui.kitchensink.client.kscommons.stackpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStackPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StackPanelExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final KSStackPanel stackPanel = GWT.create(KSStackPanel.class);
    final KSLabel label = new KSLabel("Click in the box to open the stack: ", false);

    public StackPanelExample() {

        main.addStyleName(STYLE_EXAMPLE);
        
        try {
            stackPanel.getStackPanel().add(new KSImage("images/flower1.jpg"), "Orange");
//            stackPanel.getStackPanel().add(new Image("images/flower2.jpg"), "Blue");
//            stackPanel.getStackPanel().add(new Image("images/flower3.jpg"), "Yellow");
        } catch (Exception e) {
            Window.alert("Error" + e.toString());
        }
        main.add(stackPanel);
        super.initWidget(main);
    }

}
