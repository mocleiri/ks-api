package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.RadioButton;

/**
 * KSRadioButton wraps gwt RadioButton.  This class provides most of the same functionality, but sets KS css styles
 * for its default look and a variety of RadioButton events (for improved browser compatibility and customizability).
 * 
 * @author Kuali Student Team
 *
 */
public class KSRadioButton extends RadioButton{

    /**
     * Creates a new radio button associated with a particular group name, and initialized with the given label (optionally treated as HTML). 
     * 
     * @param name the name of the radio button group
     * @param label the text to appear in the label
     * @param asHTML true to treat the label as HTML, false otherwise.
     */
    public KSRadioButton(String name, String label, boolean asHTML) {
        super(name, label, asHTML);
        setupDefaultStyle();
    }

    /**
     * Creates a new radio associated with a particular group name, and initialized with the given HTML label. 
     * 
     * @param name the name of the radio button group
     * @param label the text to appear in the label
     */
    public KSRadioButton(String name, String label) {
        super(name, label);
        setupDefaultStyle();
    }

    /**
     * Creates a new radio associated with a particular group name.
     * 
     * @param name the name of the radio button group
     */
    public KSRadioButton(String name) {
        super(name);
        setupDefaultStyle();
    }

    /**
     * This method sets the default style for the radio button and radio button events.
     * 
     */
    private void setupDefaultStyle() {
        addStyleName(KSStyles.KS_RADIO_STYLE);
        
        this.addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                KSRadioButton.this.removeStyleName(KSStyles.KS_RADIO_FOCUS_STYLE);
                
            }   
        }); 

        this.addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                KSRadioButton.this.addStyleName(KSStyles.KS_RADIO_FOCUS_STYLE);

            }       
        });
        
        //hover does not function correctly on radio buttons
/*        this.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                KSRadioButton.this.addStyleName(KSStyles.KS_RADIO_HOVER_STYLE);
                
            }       
        });
        
        this.addMouseOutHandler(new MouseOutHandler(){

            public void onMouseOut(MouseOutEvent event) {
                KSRadioButton.this.removeStyleName(KSStyles.KS_RADIO_HOVER_STYLE);
                
            }
            
        });*/
        
        this.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent event) {
                if(KSRadioButton.this.getValue()){
                    KSRadioButton.this.addStyleName(KSStyles.KS_RADIO_CHECKED_STYLE);
                }
                else{
                    KSRadioButton.this.removeStyleName(KSStyles.KS_RADIO_CHECKED_STYLE);
                }
                
            }
            
        });
        
    }
    

}
