/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.WarnContainer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.reporting.ReportExportWidget;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSItemLabel;
import org.kuali.student.common.ui.client.widgets.ULPanel;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTable;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableModel;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

/**
 * 
 * This is a description of what this class does - pctsw don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class ExportUtils {
    public static final String PDF = "PDF";
    public static final String DOC = "DOC";
    public static final String XLS = "XLS";

    /**
     * 
     * Inspect the given widget for the value and add it to the export element object.
     * 
     * @param exportItem
     * @param fieldWidget
     * @param setFirstFieldValue
     * @param viewName
     * @param sectionName
     * @return
     */
    public static ExportElement getExportItemDetails(ExportElement exportItem, Widget fieldWidget, boolean setFirstFieldValue, String viewName, String sectionName) {
        
        if (!fieldWidget.getParent().getElement().getStyle().getDisplay().equals("none")){
            if (fieldWidget instanceof HasText) {
                HasText itemHasTextValue = (HasText) fieldWidget;
                setFieldValue(exportItem, setFirstFieldValue, itemHasTextValue.getText());
                Element element = fieldWidget.getElement();
                System.out.println("Inner Html:" + element.getInnerHTML());
                Element parentElement = fieldWidget.getParent().getElement();
                System.out.println("Parent Inner Html:" + parentElement.getInnerHTML());
                
                if (fieldWidget.getElement().getStyle().getFontStyle().equalsIgnoreCase("italic")){
                    exportItem.setPrintType(ExportElement.ITALIC);
                }
            } else if (fieldWidget instanceof KSSelectedList) {
                KSSelectedList selectedList = (KSSelectedList) fieldWidget;
                List<KSItemLabel> selectedItems = selectedList.getSelectedItems();
                String values = new String();
                for (int j = 0; j < selectedItems.size(); j++) {
                    values = selectedItems.get(j).getDisplayText();
                }
                setFieldValue(exportItem, setFirstFieldValue, values);
                
            } else if (fieldWidget instanceof KSPicker) {
                KSPicker picker = (KSPicker) fieldWidget;
                if (picker.getInputWidget() instanceof HasText) {
                    HasText item = (HasText) picker.getInputWidget();
                    setFieldValue(exportItem, setFirstFieldValue, item.getText());
                } else if (picker.getInputWidget() instanceof KSLabelList) {
                    String fieldValue = null;
                    KSLabelList widget = (KSLabelList) picker.getInputWidget();
                    List<String> selected = widget.getSelectedItemsForExport();
                    for (int j = 0; j < selected.size(); j++) {
                        if (fieldValue == null) {
                            fieldValue = new String(selected.get(j));
                        } else {
                            fieldValue = fieldValue + ", " + selected.get(j);
                        }
                    }
                    setFieldValue(exportItem, setFirstFieldValue, fieldValue);
                }
                
            } else if (fieldWidget instanceof ListBox) {
                ListBox listBox = (ListBox) fieldWidget;
                setFieldValue(exportItem, setFirstFieldValue, listBox.getItemText(listBox.getSelectedIndex()));
            } else if (fieldWidget instanceof SectionTitle) {
                try {
                    SectionTitle sectionTitle = (SectionTitle) fieldWidget;
                    setFieldValue(exportItem, setFirstFieldValue, sectionTitle.getExportFieldValue());
                    if (!exportItem.getValue().contains("<b>")){
                        exportItem.setPrintType(ExportElement.BOLD);
                    }
                } catch (Exception e) {
                    // ignore, section tile interface problem - only in debugging.");
                }

            } else if (fieldWidget instanceof ReportExportWidget) {
        	
                if (fieldWidget.isVisible()){
                    ReportExportWidget widget = (ReportExportWidget) fieldWidget;
                    if (widget.isExportElement() ) {
                        exportItem.setSubset(widget.getExportElementSubset(exportItem));
                        setFieldValue(exportItem, setFirstFieldValue, widget.getExportFieldValue());
                    }
                }
            } else if (fieldWidget instanceof ComplexPanel) {
                if(fieldWidget.isVisible()) {
                    exportItem.setSubset(ExportUtils.getDetailsForWidget(fieldWidget, viewName, sectionName));
                }

            } else {
                // don't set anything
            }
        
            
        }
        return exportItem;
    }


    /**
     * 
     * This method sets the extracted string value.
     * 
     * @param exportItem
     * @param setFirstFieldValue
     * @param fieldValue
     */
    private static void setFieldValue(ExportElement exportItem, boolean setFirstFieldValue, String fieldValue) {
        if (setFirstFieldValue) {
            exportItem.setFieldValue(fieldValue);
        } else {
            exportItem.setFieldValue2(fieldValue);
        }
    }
    
    

    /**
     * This method gets the current controller based on the widget that was passed to it.
     * 
     * @param theWidget
     * @return currentController
     */
    public static Controller getController(Widget theWidget) {
        // TODO Nina - This can't be the correct way of getting handle to
        // Controller, isn't there a better way??
        if (theWidget != null) {

            if (theWidget instanceof Controller) {
                Controller controller = (Controller) theWidget;
                return controller;
            } else {
                return getController(theWidget.getParent());
            }
        } else {
            return null;
        }
    }

    public static void handleExportClickEvent(Controller currentController, String format, String reportTitle) {
        List<ExportElement> exportElements = new ArrayList<ExportElement>();
        exportElements = currentController.getExportElementsFromView();
        if (exportElements != null && exportElements.size() > 0) {
            debugExportElementsArray(exportElements);
            currentController.doReportExport(exportElements, format, reportTitle);
        }
    }

    public static void debugExportElementsArray(List<ExportElement> exportElements) {
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        for (int i = 0; i < exportElements.size(); i++) {
            System.out.println(exportElements.get(i).toString());
            debutExportElementsArraySubList(exportElements.get(i).getSubset());
        }
    }

    private static void debutExportElementsArraySubList(List<ExportElement> exportElements) {
        if (exportElements != null) {
            System.out.println("Sub list : ");
            for (int j = 0; j < exportElements.size(); j++) {
                ExportElement element = exportElements.get(j);
                System.out.println(element.toString());
                debutExportElementsArraySubList(element.getSubset());

            }
        }
    }

    /**
     * 
     * Retrieve the sub elements from the table section.
     * 
     * @param tableSection
     * @param exportElements
     * @return
     */
    public static ArrayList<ExportElement> getDetailsForWidget(SummaryTableSection tableSection, ArrayList<ExportElement> exportElements) {
        SummaryTable sumTable = tableSection.getSummaryTable();
        SummaryTableModel model = sumTable.getModel();
        List<SummaryTableBlock> tableSectionList = model.getSectionList();
        for (int i = 0; i < tableSectionList.size(); i++) {
            SummaryTableBlock item = tableSectionList.get(i);
            String blockName = item.getTitle();
            List<SummaryTableRow> rowItems = item.getSectionRowList();
            for (int j = 0; j < rowItems.size(); j++) {
                ExportElement element = new ExportElement();
				SummaryTableRow row = rowItems.get(j);
				element.setSectionName(blockName);
				element.setViewName(blockName);
				element.setFieldLabel(row.getTitle());
				element.setMandatory(row.isRequired());
                Widget fdWidget = row.getCell1();
                if (row.isShown()) {
                    if (fdWidget != null) {
                        //
                        if (fdWidget instanceof KSListPanel) {
                            element.setSubset(ExportUtils.getDetailsForWidget(fdWidget, blockName, blockName));
                        } else {
                            //
                            element = ExportUtils.getExportItemDetails(element, fdWidget, true, blockName, blockName);
                        }
                    } else {
                        if (row.getTitle() != null) {
                            element.setFieldLabel(row.getTitle());
                        }
                    }
                    //
                    Widget fdWidget2 = row.getCell2();
                    if (fdWidget2 != null) {
                        element = ExportUtils.getExportItemDetails(element, fdWidget2, false, blockName, blockName);

                    } else {
                        if (row.getTitle() != null) {
                            element.setFieldLabel(row.getTitle());
                        }
                    }
                    if (element != null && element.getViewName() != null) {
                        exportElements.add(element);
                    }
                }
                
            }
        }
        return exportElements;
    }

    /**
     * 
     * Retrieves the sub elements from a container widget.
     * 
     * @param currentViewWidget
     * @param viewName
     * @param sectionName
     * @return
     */
    public static List<ExportElement> getDetailsForWidget(Widget currentViewWidget, String viewName, String sectionName) {
        List<ExportElement> childElements = new ArrayList<ExportElement>();
        if (!currentViewWidget.getParent().getElement().getStyle().getDisplay().equals("none")){
            if (currentViewWidget instanceof Section) {
                Section widgetHasFields = (Section) currentViewWidget;
                List<FieldDescriptor> widgetFields = widgetHasFields.getFields();
                for (FieldDescriptor field : widgetFields) {
                    ExportElement exportItem = createExportElement(viewName, sectionName, childElements, field.getFieldElement().getFieldWidget());
                    exportItem.setFieldLabel(field.getFieldLabel());
                }
            } else if (currentViewWidget instanceof KSListPanel) {
                KSListPanel ksListPanelWidget = (KSListPanel) currentViewWidget;
                WidgetCollection children = ksListPanelWidget.getChildren();
                for (int i = 0; i < children.size(); i++) {
                    createExportElement(viewName, sectionName, childElements, children.get(i));
                }

            } else if (currentViewWidget instanceof ULPanel) {
                ComplexPanel complexPanel = (ComplexPanel) currentViewWidget;
                if (complexPanel.isVisible()){
                    for (int i = 0; i < complexPanel.getWidgetCount(); i++) {
                        Widget child = complexPanel.getWidget(i);
                        if (child instanceof FlowPanel){
                            List<ExportElement> subset = ExportUtils.getDetailsForWidget(child, viewName, sectionName);
                            if (subset != null && subset.size() > 0){
                                subset.get(0).setPrintType(ExportElement.LIST);
                                childElements.addAll(subset);
                            }
                            
                        } else if (!(child instanceof KSButton)
                                && !(child instanceof WarnContainer)) {
                            ExportElement exportItem = createExportElement(viewName, sectionName, childElements, child);
                            exportItem.setPrintType(ExportElement.LIST);
                        }
                    }
                }
                
            } else if (currentViewWidget instanceof ComplexPanel) {
                ComplexPanel complexPanel = (ComplexPanel) currentViewWidget;
                if (complexPanel.isVisible()){
                    for (int i = 0; i < complexPanel.getWidgetCount(); i++) {
                        Widget child = complexPanel.getWidget(i);
                        if (child instanceof FlowPanel){
                            List<ExportElement> subset = ExportUtils.getDetailsForWidget(child, viewName, sectionName);
                            if (subset != null && subset.size() > 0){
                                childElements.addAll(subset);
                            }
                            
                        } else if (!(child instanceof KSButton)
                                && !(child instanceof WarnContainer)) {
                            createExportElement(viewName, sectionName, childElements, child);
                        }
                    }
                }
            } else {

                System.out.println("ExportUtils does not cater for this type..." + currentViewWidget.getClass().getName());

            }
        }
        return childElements;
    }

    public static ArrayList<ExportElement> getExportElementsFromView(Widget currentViewWidget, ArrayList<ExportElement> exportElements, String viewName, String sectionName) {
        if (exportElements == null) {
            exportElements = new ArrayList<ExportElement>();
        }
        if (currentViewWidget.getParent() == null || !currentViewWidget.getParent().getElement().getStyle().getDisplay().equals("none")) {
            if (currentViewWidget instanceof VerticalSectionView) {
                Section widgetHasFields = (Section) currentViewWidget;
                List<FieldDescriptor> widgetFields = widgetHasFields.getFields();
                for (FieldDescriptor field : widgetFields) {
                    Widget child = field.getFieldElement().getFieldWidget();
                    ExportElement exportItem = createExportElement(viewName, sectionName, exportElements, child);
                    exportItem.setFieldLabel(field.getFieldLabel());
                }
                if ((currentViewWidget instanceof BaseSection) && (widgetHasFields.getFields().size() == 0)) {
                    BaseSection bSection = (BaseSection) currentViewWidget;
                    createExportElement(viewName, sectionName, exportElements, bSection.getLayout());
                }
            }
        }
        return exportElements;
    }
    
    /**
     * 
     * Creates a new export element with its sub elements.
     * 
     * @param viewName
     * @param sectionName
     * @param childElements
     * @param child
     * @return
     */
    private static ExportElement createExportElement(String viewName, String sectionName, List<ExportElement> childElements, Widget child) {
        ExportElement exportItem = new ExportElement();
        exportItem.setSectionName(sectionName);
        exportItem.setViewName(viewName);
                            
        exportItem = getExportItemDetails(exportItem, child, true, viewName, sectionName);
        ExportUtils.addElementToElementArray(childElements, exportItem);
        return exportItem;
    }

    // Only add element if it is not null
    public static List<ExportElement> addElementToElementArray(List<ExportElement> elementArray, ExportElement element) {
        if (element.getFieldLabel() != null || element.getFieldValue() != null || element.getFieldValue2() != null || (element.getSubset() != null && element.getSubset().size() > 0)) {
            elementArray.add(element);
        }
        return elementArray;
    }

}