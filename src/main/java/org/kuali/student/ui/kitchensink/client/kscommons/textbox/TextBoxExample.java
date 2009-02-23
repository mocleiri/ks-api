package org.kuali.student.ui.kitchensink.client.kscommons.textbox;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TextBoxExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final KSTextBox textBox = KSWidgetFactory.getTextBoxInstance();
 

    public TextBoxExample() {
        main.addStyleName(STYLE_EXAMPLE);

        main.add(textBox);
        super.initWidget(main);
    }


}
