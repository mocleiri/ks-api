package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.GlassPanel;

public class KSLightBox {
    private final PopupPanel pop = new PopupPanel(false, true);
    private final GlassPanel glass = new GlassPanel(false);
    private final AbsolutePanel parentPanel;
    private boolean showing = false;

    public KSLightBox() {
        this.parentPanel = RootPanel.get();
    }

    public void show() {
        if (!showing) {
            glass.getElement().setAttribute("zIndex", "" + KSZIndexStack.pop());
            parentPanel.add(glass, 0, 0);
            pop.getElement().setAttribute("zIndex", "" + KSZIndexStack.pop());
        }
        pop.center();
        showing = true;
    }

    public void hide() {
        pop.hide();
        KSZIndexStack.push();
        parentPanel.remove(glass);
        KSZIndexStack.push();
        showing = false;
    }

    public Widget getWidget() {
        return pop.getWidget();
    }

    public void setWidget(Widget w) {
        pop.setWidget(w);
    }
    public void add(CloseHandler handler){
        pop.addCloseHandler(handler);
    }

}