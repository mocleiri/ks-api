package org.kuali.student.ui.kitchensink.client.kscommons.radiobutton;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class RadioButtonExampleDescriptor extends KitchenSinkExample {
    public RadioButtonExampleDescriptor() {
        super();
        super.addResource("java", "RadioButtonExample.java", "kscommons/radiobutton/RadioButtonExample.java", "Example usage of KSRadioButton.");
    }
    public String getDescription() {       
        return "RadioButton is a mutually-exclusive selection radio button widget"; 
    }

    public Widget getExampleWidget() {
        return new RadioButtonExample();
    }
 
    public String getTitle() {
        return "KSRadioButton";
    }

}
