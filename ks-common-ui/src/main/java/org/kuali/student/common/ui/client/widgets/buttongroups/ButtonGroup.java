package org.kuali.student.common.ui.client.widgets.buttongroups;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonLayout;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class ButtonGroup<T extends ButtonEnum> extends Composite{
    private List<Callback<T>> callbacks = new ArrayList<Callback<T>>();
    protected Map<T, KSButton> buttonMap = new HashMap<T, KSButton>();
    protected ButtonLayout layout;

    public void addCallback(Callback<T> callback) {
        callbacks.add(callback);
    }

    public List<Callback<T>> getCallbacks() {
        return callbacks;
    }
    
    public void setButtonText(T key, String text){
        buttonMap.get(key).setText(text);
    }
    
    public void setFocusedButton(T key){
        buttonMap.get(key).setFocus(true);
    }
    
    public KSButton getButton(T key){
        return buttonMap.get(key);
    }
    
    /**
     * This method is optional, the button panel can be contained inside of a parent panel which
     * will limit it's maximum size.
     * 
     * This method provides an alternative, the button panel will "wrap" the content and become the
     * proper size based on maximum size of the content. 
     * 
     * @param content - the content the button panel will align itself next to
     */
    public void setContent(Widget content){
        layout.setContent(content);
    }
}
