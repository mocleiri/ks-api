package org.kuali.student.common.ui.client.validator;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.BooleanType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ByteType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.CharacterType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.DateType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.DoubleType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.FloatType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.IntegerType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ListType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.LongType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.MapType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.server.mvc.dto.BeanMappingException;
import org.kuali.student.common.ui.server.mvc.dto.MapContext;
import org.kuali.student.common.validator.ConstraintDataProvider;

public class ModelDTOConstraintDataProvider implements ConstraintDataProvider {
    ModelDTO modelDTO = null;

    @Override
    public String getObjectId() {

        if (modelDTO == null) {
            return null;
        }
        ModelDTOValue modelDTOValue = modelDTO.get("id");
        if (modelDTOValue == null) {
            return null;
        }
        return ((StringType) modelDTOValue).get();
    }

    @Override
    public Object getValue(String fieldKey) {

        if (modelDTO == null) {
            return null;
        }

        ModelDTOValue modelDTOValue = modelDTO.get(fieldKey);
        if (modelDTOValue == null) {
            return null;
        }
        switch (modelDTOValue.getType()) {
            case STRING:
                return ((StringType) modelDTOValue).get();
            case CHARACTER:
                return ((CharacterType) modelDTOValue).get();
            case INTEGER:
                return ((IntegerType) modelDTOValue).get();
            case LONG:
                return ((LongType) modelDTOValue).get();
            case FLOAT:
                return ((FloatType) modelDTOValue).get();
            case DOUBLE:
                return ((DoubleType) modelDTOValue).get();
            case BYTE:
                return ((ByteType) modelDTOValue).get();
            case BOOLEAN:
                return ((BooleanType) modelDTOValue).get();
            case DATE:
                return ((DateType) modelDTOValue).get();
            case LIST:
                return ((ListType) modelDTOValue).get();
            case MAP:
                return ((MapType) modelDTOValue).get();
            case MODELDTO:
                return ((ModelDTOType) modelDTOValue).get();
        }
        return null;
    }

    @Override
    public Boolean hasField(String fieldKey) {
        if (modelDTO == null) {
            return false;
        }
        return modelDTO.get(fieldKey) != null;
    }

    @Override
    public void initialize(Object o) {
        if (o instanceof ModelDTO) {
            modelDTO = (ModelDTO) o;
        } else {
            throw new IllegalArgumentException("The object must be an instance of ModelDTO");
        }
    }
}
