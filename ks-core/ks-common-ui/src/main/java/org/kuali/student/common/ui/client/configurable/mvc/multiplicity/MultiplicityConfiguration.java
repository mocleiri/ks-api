/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * <p/>
 * http://www.osedu.org/licenses/ECL-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"                       
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * <p/>
 */
package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Metadata;


 /**
  *
  * MultiplicityConfiguration is passed into a MultiplicitySection to control the creation of the multiplicity.
  *
  * This class is used to control the Multiplicity, e.g. if its updateable, has headers, min number of items, labels etc.
  * It also holds FieldDescriptors for the fields required to be included in the multiplicity. They are
  * held in a HashMap keyed by rows, i.e. Map<Integer, List<FieldDescriptor>> .
  *
  * The parent for the multiplicity refers to the path to the high level field that owns
  * the repeating elements, e.g. course/versions is the parent field for course version fields versionCode and
  * versionTitle
  *
  * For multiplicities nested inside another multiplicity, create a MultiplicityConfiguration for the nested
  * multiplicity and call setNestedConfig to add the lower level multiplicity
  *
  *
 */
public class MultiplicityConfiguration {

    private MultiplicityType multiplicityType;
    
    private StyleType styleType;
    private boolean updateable = false;

    private String itemLabel;
    private String addItemLabel;
    
    private Metadata metaData;

    int row = 0;
    
    private FieldDescriptor parentFd;
    private Map<Integer, List<FieldDescriptor>> fields = new HashMap<Integer, List<FieldDescriptor>>();
    protected Map<String, String> concatenatedFields = new HashMap<String, String>();
    
    private MultiplicityConfiguration nestedConfig ;
    private boolean showHeaders;

    public static enum MultiplicityType {GROUP, TABLE }
    public static enum StyleType {TOP_LEVEL, SUB_LEVEL}


     /**
     *
     * Creates a new MultiplicityConfiguration.
     *
     *
     * @param multiplicityType      the type of multiplicity required, MultiplicityType
     * @param styleType             the style type for this multiplicity, StyleType
     * @param metaData              the metadata for the parentFd
     */
     public MultiplicityConfiguration(MultiplicityType multiplicityType, StyleType styleType, Metadata metaData) {
        this.multiplicityType = multiplicityType;
        this.styleType = styleType;
        this.metaData = metaData;
    }

    /**
     * Causes a line throw so that the subsequent fields are placed on the next line of the screen
     *
     */
    public void nextLine() {
    	row++;
    }

    /**
     * Includes this field (as defined by the FieldDescriptor) on the current line at the next horizontal position
     *
     *
     * @param fieldDescriptor
     */
    public void addField(FieldDescriptor fieldDescriptor) {
    	
        List<FieldDescriptor> fds;
        if (fields.containsKey(row)) {
            fds = fields.get(row);
        }
        else {
            fds = new ArrayList<FieldDescriptor>();
        }
        fds.add(fieldDescriptor);
        fields.put(row, fds);
    }

	/**
	 * Concatenates multiple field values into a single table cell
     *
     *  !!!!!  Currently only implemented for MultiplicityTable !!!!!
     *
     * The parentField should define a field with a Data value. The fieldKey should resolve to a single value
	 * field in that Data value. The binding will iterate the values in the Data object
	 * and concatenate the named fieldKeys into the same cell in the current row of the table
	 * separated by commas.
     *
     * e.g. parentFD = "course/versions", fieldKey = "versionCode", version codes will be concatenated to
     * display "code1, code2, code3" in a single table cell
	 *
	 * @param fieldKey
	 */
	public void addConcatenatedField(FieldDescriptor parentField, String fieldKey){
		addField(parentField);
		concatenatedFields.put(parentField.getFieldKey(), fieldKey);
	}

    /**
     * Creates a copy of this MultiplicityConfiguration. Used when creating a new MultiplicityItem
     *
     * @return  MultiplicityConfiguration
     */
    public MultiplicityConfiguration copy() {
        MultiplicityConfiguration copy = new MultiplicityConfiguration(getMultiplicityType(), getStyleType(), getMetaData());
        copy.setAddItemLabel(getAddItemLabel());
        copy.setItemLabel(getItemLabel());
        copy.setMetaData(getMetaData());
        copy.setUpdateable(isUpdateable());
        if (getNestedConfig() != null) {
            copy.setNestedConfig(getNestedConfig().copy());
        }
        copy.setParentFd(new FieldDescriptor(getParentFd().getFieldKey(), new MessageKeyInfo(getParentFd().getFieldLabel()), getParentFd().getMetadata()));
        for (Integer row  : getFields().keySet()) {
            List<FieldDescriptor> fields = getFields().get(row);
            for (FieldDescriptor fd : fields) {
                FieldDescriptor newfd = new FieldDescriptor(fd.getFieldKey(), new MessageKeyInfo(fd.getFieldLabel()), fd.getMetadata());
                copy.addField(newfd);
            }
            copy.nextLine();
        }
        return copy;
    }

    public MultiplicityType getMultiplicityType() {
        return multiplicityType;
    }

     /**
      * Sets the MultiplicityType required for this config
      *
      * Valid values are defined in {@link #MultiplicityConfiguration.MultiplicityType}
      * @param multiplicityType
      */
     public void setMultiplicityType(MultiplicityType multiplicityType) {
        this.multiplicityType = multiplicityType;
    }

	public FieldDescriptor getParentFd() {
		return parentFd;
	}

    /**
     * The parent fd defines the high level parent field that contains the repeating elements
     *
     * @param parentFd
     */
    public void setParentFd(FieldDescriptor parentFd) {
		this.parentFd = parentFd;
	}

	public Map<Integer, List<FieldDescriptor>> getFields() {
		return fields;
	}

    public String getItemLabel() {
        return itemLabel;
    }

    /**
     * Sets text to be used as the header for each multiplicity item
     * @param itemLabel
     */
    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public String getAddItemLabel() {
        return addItemLabel;
    }

    /**
     * Sets text to be used as the button text to add a new empty item in the multiplicity
     * @param addItemLabel
     */
    public void setAddItemLabel(String addItemLabel) {
        this.addItemLabel = addItemLabel;
    }

	public StyleType getStyleType() {
		return styleType;
	}

	public void setStyleType(StyleType styleType) {
		this.styleType = styleType;
	}

	public MultiplicityConfiguration getNestedConfig() {
		return nestedConfig;
	}

    /**
     * If this multiplicity is to contain other nested multiplicities, create a MultiplicityConfiguration for the child
     * multiplicity and set as a nested config in the parent.
     *
     * @param config
     */
    public void setNestedConfig(MultiplicityConfiguration config) {
		this.nestedConfig = config;
	}

    public boolean isShowHeaders() {
         return showHeaders;
    }

    /**
     *
     * @param showHeaders
     */
    public void setShowHeaders(boolean showHeaders) {
        this.showHeaders = showHeaders;
    }

	public Map<String, String> getConcatenatedFields() {
		return concatenatedFields;
	}

    public boolean isUpdateable() {
        return updateable;
    }

    /**
     * Sets if this multiplicity will be updateable or display.
     * Add delete buttons/links will not be shown for display multiplicities
     *
     * @param updateable
     */
    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

    public MultiplicityType getLayoutType() {
        return multiplicityType;
    }

    public void setLayoutType(MultiplicityType multiplicityType) {
        this.multiplicityType = multiplicityType;
    }

	public Metadata getMetaData() {
		return metaData;
	}

	public void setMetaData(Metadata metaData) {
		this.metaData = metaData;
	}
    
    
}

