package org.kuali.student.lum.lu.ui.tools.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.CluItemValue;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class WarningDialog {
    
    private KSLightBox dialog = new KSLightBox();
    private KSLabel titleLabel = null;
    private KSLabel messageLabel = null;
    private KSLabel questionLabel = null;
    private VerticalFlowPanel layout = new VerticalFlowPanel();
    private List<Callback<Boolean>> confirmationCallbacks = new ArrayList<Callback<Boolean>>();
    private KSButton affirmativeButton;
    private KSButton negativeButton;

    public WarningDialog(String title, String message, String question,
            String affirmativeChoice, String negativeChoice) {
        titleLabel = new KSLabel(title);
        messageLabel = new KSLabel(message);
        questionLabel = new KSLabel(question);
        affirmativeButton = new KSButton(affirmativeChoice);
        negativeButton = new KSButton(negativeChoice);
        
        layout.add(titleLabel);
        layout.add(createSpacerPanel("0px","20px"));
        layout.add(messageLabel);
        layout.add(createSpacerPanel("0px","50px"));
        layout.add(questionLabel);
        
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.add(affirmativeButton);
        buttonsPanel.add(createSpacerPanel("10px", "0px"));
        buttonsPanel.add(negativeButton);
        layout.add(createSpacerPanel("0px","5px"));
        layout.add(buttonsPanel);
        
        affirmativeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (confirmationCallbacks != null) {
                    for (Callback<Boolean> confirmationCallback : confirmationCallbacks) {
                        confirmationCallback.exec(new Boolean(true));
                    }
                }
            }
        });
        negativeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (confirmationCallbacks != null) {
                    for (Callback<Boolean> confirmationCallback : confirmationCallbacks) {
                        confirmationCallback.exec(new Boolean(false));
                    }
                }
            }
        });
        dialog.setWidget(layout);
    }
    
    private Panel createSpacerPanel(String width, String height) {
        Panel spacerPanel = new SimplePanel();
        spacerPanel.setWidth(width);
        spacerPanel.setHeight(height);
        return spacerPanel;
    }
    
    public void show() {
        dialog.show();
    }
    
    public void hide() {
        dialog.hide();
    }
    
    public void addConfirmationCallback(Callback<Boolean> confirmationCallback) {
        confirmationCallbacks.add(confirmationCallback);
    }
    
}
