/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.util.ConcreteKeyValue;
import org.kuali.rice.core.util.KeyValue;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.service.EnumerationManagementService;

public class SubjectAreaKeyValues extends KeyValuesBase implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final String SUBJECT_AREA_ENUM_KEY = "kuali.lu.subjectArea";
    
    private EnumerationManagementService enumService;
    
    
    @Override
    public List<KeyValue> getKeyValues() {
        
        List<EnumeratedValueInfo> subjectAreas;
        
        try {
            subjectAreas = getEnumService().getEnumeratedValues(SUBJECT_AREA_ENUM_KEY, null, null, null);
        } catch (DoesNotExistException e) {
            throw new RuntimeException("No subject areas found! There should be some in the database", e);
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        }
        
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        
        for(EnumeratedValueInfo e : subjectAreas) {
            keyValues.add(new ConcreteKeyValue(e.getEnumerationKey(), e.getValue()));
        }
        
        return keyValues;
    }
    
    protected EnumerationManagementService getEnumService() {
        if(enumService == null) {
            enumService = (EnumerationManagementService)GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/enumerationmanagement","EnumerationManagementService"));
        }
        return this.enumService;
    }

}
