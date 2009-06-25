package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.TextArea;

/**
 * KSTextArea wraps gwt TextArea.  This class provides most of the same functionality, but sets KS css styles
 * for its default look and a variety of TextArea events (for improved browser compatibility and customizability).
 * 
 * @author Kuali Student Team
 *
 */
public class KSTextArea extends TextArea{

    /**
     * Creates a new empty text area.
     * 
     */
    public KSTextArea() {
        super();
        setupDefaultStyle();
    }

    /**
     * Creates a new text area using the text area element specified.
     * 
     * @param element a <TextArea> element
     */
    public KSTextArea(Element element) {
        super(element);
        setupDefaultStyle();
    }

    /**
     * This method sets the default style for the text area and text area events.
     * 
     */
    private void setupDefaultStyle() {
        addStyleName(KSStyles.KS_TEXTAREA_STYLE);
        
        this.addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                KSTextArea.this.removeStyleName(KSStyles.KS_TEXTAREA_FOCUS_STYLE);
                
            }   
        }); 

        this.addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                KSTextArea.this.addStyleName(KSStyles.KS_TEXTAREA_FOCUS_STYLE);

            }       
        });
        
        this.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                KSTextArea.this.addStyleName(KSStyles.KS_TEXTAREA_HOVER_STYLE);
                
            }       
        });
        
        this.addMouseOutHandler(new MouseOutHandler(){

            public void onMouseOut(MouseOutEvent event) {
                KSTextArea.this.removeStyleName(KSStyles.KS_TEXTAREA_HOVER_STYLE);
                
            }
            
        });
        
    }
    
    

}
