package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.widgets.RichTextEditor;
import org.kuali.student.core.dto.RichTextInfo;

public class RichTextBinding implements PropertyBinding<RichTextEditor>{
    public static RichTextBinding INSTANCE = new RichTextBinding();
    
    private RichTextBinding(){}

    @Override
    public ModelDTOValue getValue(RichTextEditor object) {
        ModelDTO richTextModel = new ModelDTO(RichTextInfo.class.getName());
        StringType plain = new StringType();
        plain.set(object.getText());
        richTextModel.put("plain", plain);
        
        StringType formatted = new StringType();
        formatted.set(object.getHTML());
        richTextModel.put("formatted", formatted);
        StringType type = new StringType("kuali.not.applicable");
        StringType state = new StringType("(n/a)");
        richTextModel.put("type", type);
        richTextModel.put("state", state);
        
        ModelDTOType value = new ModelDTOType();
        value.set(richTextModel);
        
        return value;
    }

    @Override
    public void setValue(RichTextEditor object, ModelDTOValue value) {
        if (value != null && object != null) {
            if(value instanceof ModelDTOType){
                String className = ((ModelDTOType) value).get().getClassName();
                if(className.equalsIgnoreCase(RichTextInfo.class.getName())){
                    StringType formatted = (StringType) (((ModelDTOType) value).get()).get("formatted");
                    StringType plain = (StringType) (((ModelDTOType) value).get()).get("plain");
                    if (formatted != null){
                        object.setHTML(formatted.get());
                    }
                    else if(plain != null){
                    	object.setText(plain.get());
                    }
                    else{
                    	object.setHTML("");
                    }
                }
                else{
                    throw new UnsupportedOperationException("Richtext editors must have richTextInfo models as their values");
                }
            }
            else{
                throw new UnsupportedOperationException("Richtext editors must have richTextInfo models as their values");
            }
        }
        else if(value == null && object != null){
        	object.setHTML("");
        }
        
    }
}