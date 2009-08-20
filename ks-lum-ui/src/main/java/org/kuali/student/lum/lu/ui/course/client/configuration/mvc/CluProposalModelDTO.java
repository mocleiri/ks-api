/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ListType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.lum.lu.dto.CluInfo;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 * 
 * @author Kuali Student Team
 */
public class CluProposalModelDTO extends ModelDTO {

    private static final long serialVersionUID = 1L;
    
    private ModelDTOValue.ListType courseFormats;
    private ModelDTOValue.ListType crossListClus;
    private ModelDTOValue.ListType jointClus;
    private transient ModelDTOAdapter adapter;

    /**
     * @param className
     */
    public CluProposalModelDTO() {
        //super("CluInfo");
        super(CluDictionaryClassNameHelper.CLU_INFO_CLASS);
        //this.setAdapter();
        
    }

    /**
     * The CluProposalModelDTO extends the base ModelDTO("CluInfo") to add additional
     * properties to a clu, eg. Activity clus.
     * 
     * @see org.kuali.student.common.ui.client.mvc.dto.ModelDTO#put(java.lang.String, org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void put(String key, ModelDTOValue value) {
        if ("courseFormats".equals(key)){
            courseFormats = (ModelDTOValue.ListType)value;
        } else if ("crossListClus".equals(key)){
            crossListClus = (ModelDTOValue.ListType)value;
        } else if ("jointClus".equals(key)){
            jointClus = (ModelDTOValue.ListType)value;
        } else if (key.contains("/")){
            //ModelDTOAdapter.set(this, key, value, CluInfo.class.getName(), ((StringType) this.get("type")).get(), ((StringType) this.get("state")).get() );
        	if(adapter == null){
        		adapter = new ModelDTOAdapter(this, CluDictionaryClassNameHelper.getObjectKeytoClassMap(), "cluInfo", ((StringType) this.get("type")).get(), ((StringType) this.get("state")).get());
        	}
        	adapter.put(key, value);
        } else {
            super.put(key, value);            
        }
    }


    /**
     * @see org.kuali.student.common.ui.client.mvc.dto.ModelDTO#get(java.lang.String)
     */
    @Override
    public ModelDTOValue get(String key) {
        if ("courseFormats".equals(key)){
            return courseFormats;
        } else if ("crossListClus".equals(key)){
            return crossListClus;
        } else if ("jointClus".equals(key)){
            return jointClus;
        } else if (key.contains("/")){
        	if(adapter == null){
        		adapter = new ModelDTOAdapter(this, CluDictionaryClassNameHelper.getObjectKeytoClassMap(), "cluInfo", ((StringType) this.get("type")).get(), ((StringType) this.get("state")).get());
        	}
            return adapter.get(key);
        } else {
            return super.get(key);            
        }
    }

    /**
     * Returns the activity clus associated with this model
     * 
     * @return
     */
    public ModelDTOValue.ListType getCourseFormats() {
        return courseFormats;
    }

    public ModelDTOValue.ListType getCrossListClus() {
        return crossListClus;
    }

    public ModelDTOValue.ListType getJointClus() {
        return jointClus;
    }
    
    public String toString(){
        return super.toString() + 
        (crossListClus != null ? " crossListClus= " + crossListClus.toString():"") + 
        (courseFormats != null ? " courseFormats= " + courseFormats.toString():"") +
        (jointClus != null ? " jointClus= " + jointClus.toString():"");
    }
}
