package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ListType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;

public class SelectItemWidgetBinding implements PropertyBinding<KSSelectItemWidgetAbstract>{

    public static SelectItemWidgetBinding INSTANCE = new SelectItemWidgetBinding();
    
    private SelectItemWidgetBinding(){}
    
    @Override
    public ModelDTOValue getValue(KSSelectItemWidgetAbstract object) {
        //TODO NEED AN IS MULTIPLESELECTABLE BOOLEAN ACCESSOR
        //Does not support multi select yet
        ModelDTOValue modelValue = new StringType();
        ((StringType) modelValue).set(object.getSelectedItem());
        return modelValue;
    }

    @Override
    public void setValue(KSSelectItemWidgetAbstract object, ModelDTOValue value) {
        if(value instanceof StringType){
            //is a single id
            String id = ((StringType) value).get();
          //TODO NEED A CLEAR METHOD!!!!
            object.selectItem(id);
        }
        else if(value instanceof ListType){
            //is multiple ids
            List<ModelDTOValue> ids = ((ListType) value).get();
            //TODO NEED A CLEAR METHOD!!!!
            //List<String> selectableIds = object.getListItems().getItemIds();
            for(ModelDTOValue mv : ids){
                if(mv instanceof StringType){
                    String id = ((StringType) mv).get();
                    object.selectItem(id);
                }
            }    
        }
    }
}
