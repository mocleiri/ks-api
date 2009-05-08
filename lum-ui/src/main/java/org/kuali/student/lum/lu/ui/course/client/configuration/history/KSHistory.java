package org.kuali.student.lum.lu.ui.course.client.configuration.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeEvent;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeHandler;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;

public class KSHistory implements ValueChangeHandler<String> {
    
    public static final String CONTROLLER_KEY = "view";
    public static final String LAYOUT_KEY = "section";
    public static final String IDABLE_KEY = "id";

    DefaultCreateUpdateLayout<?> layout;
    Controller controller;
    Map<Enum<?>, ConfigurableLayout<?>> layoutMap;
    
    public KSHistory(final Controller controller) {
        this.controller = controller;
        layoutMap = new HashMap<Enum<?>, ConfigurableLayout<?>>();
        History.addValueChangeHandler(this);
        controller.addApplicationEventHandler(ViewChangeEvent.TYPE, new ViewChangeHandler() {
            String key = CONTROLLER_KEY;
            @Override
            public void onViewChange(ViewChangeEvent event) {
                Map<String, List<String>> params = buildListParamMap(History.getToken());
                if(params.get(key) == null) {
                    if(controller.getCurrentViewEnum() != null)
                        History.newItem((params.isEmpty()? "" : History.getToken()+"&")+key+"="+controller.getCurrentViewEnum().name());
                } else {
                    String temp = "";
                    for(String name : params.keySet()) {
                        if(name.equals(key)) {
                            if(controller.getCurrentViewEnum() != null)
                                temp += "&" + name + "=" + controller.getCurrentViewEnum().name();
                        } else {
                            String t = "&"+name+"=";
                            List<String> values = params.get(name);
                            for(String value : values) {
                                temp += t + value;
                            }
                        }
                    }
                    History.newItem(temp.substring(1));
                }
            }
        });
    }
    @Deprecated
    public KSHistory(final Controller controller, DefaultCreateUpdateLayout<?> layout) {
        this.controller = controller;
        this.layout = layout;
        History.newItem("view="+controller.getCurrentViewEnum().name()+(layout.getObject() == null? "": "&id="+layout.getObject().getId()));
        controller.addApplicationEventHandler(ViewChangeEvent.TYPE, new ViewChangeHandler() {
            String key = CONTROLLER_KEY;
            @Override
            public void onViewChange(ViewChangeEvent event) {
                Map<String, List<String>> params = buildListParamMap(History.getToken());
                if(params.get(key) == null) {
                    History.newItem((params.isEmpty()? "" : History.getToken()+"&")+key+"="+controller.getCurrentViewEnum().name());
                } else {
                    String temp = "";
                    for(String name : params.keySet()) {
                        if(name.equals(key)) {
                            temp += "&" + name + "=" + event.getNewView().getName();
                        } else {
                            String t = "&"+name+"=";
                            List<String> values = params.get(name);
                            for(String value : values) {
                                temp += t + value;
                            }
                        }
                    }
                    History.newItem(temp.substring(1));
                }
            }
        });
        layout.addApplicationEventHandler(ViewChangeEvent.TYPE, new ViewChangeHandler() {
            String key = LAYOUT_KEY;
            @Override
            public void onViewChange(ViewChangeEvent event) {
                Map<String, List<String>> params = buildListParamMap(History.getToken());
                if(params.get(key) == null) {
                    History.newItem((params.isEmpty()? "" : History.getToken()+"&")+key+"="+event.getNewView().getName());
                } else {
                    String temp = "";
                    for(String name : params.keySet()) {
                        if(name.equals(key)) {
                            temp += "&" + name + "=" + event.getNewView().getName();
                        } else {
                            String t = "&"+name+"=";
                            List<String> values = params.get(name);
                            for(String value : values) {
                                temp += t + value;
                            }
                        }
                    }
                    History.newItem(temp.substring(1));
                }
            }});
        History.addValueChangeHandler(this);
    }
    
    public void addLayoutToView(Enum<?> view, ConfigurableLayout<?> layout) {
        if(layout == null) {
            System.out.println("!!!LAYOUT IS NULL! BAD BAD BAD!");
            return;
        }
        layoutMap.put(view, layout);
        layout.addApplicationEventHandler(ViewChangeEvent.TYPE, new ViewChangeHandler() {
            String key = LAYOUT_KEY;
            @Override
            public void onViewChange(ViewChangeEvent event) {
                Map<String, List<String>> params = buildListParamMap(History.getToken());
                if(params.get(key) == null) {
                    History.newItem((params.isEmpty()? "" : History.getToken()+"&")+key+"="+event.getNewView().getName());
                } else {
                    String temp = "";
                    for(String name : params.keySet()) {
                        if(name.equals(key)) {
                            temp += "&" + name + "=" + event.getNewView().getName();
                        } else {
                            String t = "&"+name+"=";
                            List<String> values = params.get(name);
                            for(String value : values) {
                                temp += t + value;
                            }
                        }
                    }
                    History.newItem(temp.substring(1));
                }
            }});
    }
    
    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        Map<String, List<String>> params = buildListParamMap(event.getValue());
        if(params.get(CONTROLLER_KEY) != null && !params.get(CONTROLLER_KEY).isEmpty()) {
            Enum<?> view = null;
            try {
                view = valueOf(controller.getViewsEnum(), params.get(CONTROLLER_KEY).get(0)); //only assuming one value, hope this doesn't bite me later
            } catch(IllegalArgumentException e) {
                return; // i'm stopping if there isn't a valid view, even if other stuff is valid
            }
            if(controller.getCurrentViewEnum() == null || !view.equals(controller.getCurrentViewEnum())) {
                controller.showView(view);
            }
            if(params.get(IDABLE_KEY) != null && !params.get(IDABLE_KEY).isEmpty()) {
                String id = params.get(IDABLE_KEY).get(0);
                //TODO impl this better than setting id
//                layout.getObject().setId(id); //commenting this line out since it's probably more trouble than good
            }
            if(params.get(LAYOUT_KEY) != null && !params.get(LAYOUT_KEY).isEmpty()) {
                String path = params.get(LAYOUT_KEY).get(0);
                ConfigurableLayout<?> configurableLayout = layoutMap.get(view);
                if(configurableLayout instanceof DefaultCreateUpdateLayout)
                    ((DefaultCreateUpdateLayout<?>)configurableLayout).selectSection(path); //probably should just be moved up to superclass
            }
        }
    }
    
    protected Enum<?> valueOf(Class<? extends Enum<?>> enumclass, String value) {
        Enum<?> consts[] = enumclass.getEnumConstants();
        for(Enum<?> e : consts) { 
            if(e.name().equals(value))
                return e;
        }
        throw new IllegalArgumentException("Enum not a valid type");
    }
    
    /* stolen from Window.Location */
    protected Map<String, List<String>> buildListParamMap(String queryString) {
        Map<String, List<String>> out = new LinkedHashMap<String, List<String>>();

        if (queryString != null && queryString.length() > 0) {
          String qs = queryString;

          for (String kvPair : qs.split("&")) {
            String[] kv = kvPair.split("=", 2);
            if (kv[0].length() == 0) {
              continue;
            }

            List<String> values = out.get(kv[0]);
            if (values == null) {
              values = new ArrayList<String>();
              out.put(kv[0], values);
            }
            values.add(kv.length > 1 ? URL.decode(kv[1]) : "");
          }
        }

        for (Map.Entry<String, List<String>> entry : out.entrySet()) {
          entry.setValue(Collections.unmodifiableList(entry.getValue()));
        }

        out = Collections.unmodifiableMap(out);

        return out;
      }

}
