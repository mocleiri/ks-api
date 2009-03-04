package org.kuali.student.ui.kitchensink.client.kscommons.datepicker;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_DATE_PICKER;


import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DatePickerExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    
    final KSDatePicker datePicker = new KSDatePicker();
    final KSLabel label = new KSLabel("Click in the box to open the date picker: ", false);

    public DatePickerExample() {
        
        main.addStyleName(STYLE_EXAMPLE);
        datePicker.addStyleName(STYLE_DATE_PICKER);

        
// Can't do this as have no access to DatePicker in KSDate Picker
//        ksDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>(){
//
//            public void onValueChange(ValueChangeEvent<Date> event) {
//                
//              Window.alert("You picked " + ksDatePicker.getValue());
//            }   
//        });
               
        main.add(label);
        main.add(datePicker);

        super.initWidget(main);
    }
}
