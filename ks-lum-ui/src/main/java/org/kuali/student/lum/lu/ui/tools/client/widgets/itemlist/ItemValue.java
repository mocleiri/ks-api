package org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;

import com.google.gwt.user.client.ui.Widget;

public interface ItemValue<V> {

    public List<Widget> generateDisplayWidgets();
    public String getId();
    public void setId(String id);
    public Callback<V> getDeleteCallback();
    public void setDeleteCallback(Callback<V> deleteCallback);
    public void setEditable(boolean editable);
    public boolean isEditable();
}
