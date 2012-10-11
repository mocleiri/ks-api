/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by bobhurt on 8/7/12
 */
package org.kuali.student.enrollment.kitchensink;

import org.kuali.rice.krad.web.form.UifFormBase;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KitchenSinkForm extends UifFormBase {
    private static final long serialVersionUID = 4678031668930436995L;

    private String stringField1;
    private String stringField2;
    private String stringField3;
    private String stringField4;
    private String stringField5;
    private Boolean checkboxSelected;
    private List<String> checkboxSelections;
    private List<KitchenSinkFormCollection1> collection;
    //private List<KitchenSinkFormCollection1> collection2;
    private List<String> multiSelections;
    private String radioButtonSelection;
    private String dropdownSelection;

    private List<UITestObject> list1 = new ArrayList<UITestObject>();
    private List<UITestObject> list3 = new ArrayList<UITestObject>();

    private List<DisplayScheduleMockData> displayScheduleMockDataList = DisplayScheduleMockData.mockTestData();
    private List<ActivityMockData> activityList;

    public KitchenSinkForm() {
        super();
    }

    @Override
    public void postBind(HttpServletRequest request) {
        super.postBind(request);
    }

    public String getStringField1() {
        return stringField1;
    }
    public void setStringField1(String stringField1) {
        this.stringField1 = stringField1;
    }

    public String getStringField2() {
        return stringField2;
    }
    public void setStringField2(String stringField2) {
        this.stringField2 = stringField2;
    }

    public String getStringField3() {
        return stringField3;
    }
    public void setStringField3(String stringField3) {
        this.stringField3 = stringField3;
    }

    public String getStringField4() {
        return stringField4;
    }
    public void setStringField4(String stringField4) {
        this.stringField4 = stringField4;
    }

    public String getStringField5() {
        return stringField5;
    }
    public void setStringField5(String stringField5) {
        this.stringField5 = stringField5;
    }

    public Boolean getCheckboxSelected() {
        return checkboxSelected;
    }
    public void setCheckboxSelected(Boolean checkboxSelected) {
        this.checkboxSelected = checkboxSelected;
    }

    public List<String> getCheckboxSelections() {
        return checkboxSelections;
    }
    public void setCheckboxSelections(List<String> checkboxSelections) {
        this.checkboxSelections = checkboxSelections;
    }

    public List<KitchenSinkFormCollection1> getCollection() {
        return collection;
    }
    public void setCollection(List<KitchenSinkFormCollection1> collection) {
        this.collection = collection;
    }

    /**
     * @return the list1
     */
    public List<UITestObject> getList1() {
        return this.list1;
    }

    /**
     * @param list1 the list1 to set
     */
    public void setList1(List<UITestObject> list1) {
        this.list1 = list1;
    }

    /**
     * @return the list3
     */
    public List<UITestObject> getList3() {
        return this.list3;
    }

    /**
     * @param list3 the list3 to set
     */
    public void setList3(List<UITestObject> list3) {
        this.list3 = list3;
    }

    public List<DisplayScheduleMockData> getDisplayScheduleMockDataList() {
        return displayScheduleMockDataList;
    }

    public void setDisplayScheduleMockDataList(List<DisplayScheduleMockData> displayScheduleMockDataList) {
        this.displayScheduleMockDataList = displayScheduleMockDataList;
    }

    public List<ActivityMockData> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityMockData> activityList) {
        this.activityList = activityList;
    }

// Collections.xml removed so this isn't necessary
//    public List<KitchenSinkFormCollection1> getCollection2() {
//        return collection2;
//    }
//    public void setCollection2(List<KitchenSinkFormCollection1> collection2) {
//        this.collection2 = collection2;
//    }

    public List<String> getMultiSelections() {
        return multiSelections;
    }
    public void setMultiSelections(List<String> multiSelections) {
        this.multiSelections = multiSelections;
    }

    public String getRadioButtonSelection() {
        return radioButtonSelection;
    }
    public void setRadioButtonSelection(String radioButtonSelection) {
        this.radioButtonSelection = radioButtonSelection;
    }

    public String getDropdownSelection() {
        return dropdownSelection;
    }
    public void setDropdownSelection(String dropdownSelection) {
        this.dropdownSelection = dropdownSelection;
    }
}