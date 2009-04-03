package org.kuali.student.common.ui.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.CssResource;


public interface KSCommonResources extends ImmutableResourceBundle{
    public static final KSCommonResources INSTANCE =  (KSCommonResources) GWT.create(KSCommonResources.class);

    @Resource("org/kuali/student/common/ui/public/KSGeneral.css")
	public CssResource generalCss();
    @Resource("org/kuali/student/common/ui/public/KSAccordionMenu.css")
	public CssResource accordionMenuCss();    
    @Resource("org/kuali/student/common/ui/public/KSAccordionPanel.css")
	public CssResource accordionPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSBlockingProgressIndicator.css")
	public CssResource blockingProgressIndicatorCss();
    @Resource("org/kuali/student/common/ui/public/KSButton.css")
	public CssResource buttonCss();
    @Resource("org/kuali/student/common/ui/public/KSCheckbox.css")
	public CssResource checkboxCss();
    @Resource("org/kuali/student/common/ui/public/KSDatePicker.css")
	public CssResource datepickerCss();
    @Resource("org/kuali/student/common/ui/public/KSDisclosureSection.css")
	public CssResource disclosureSectionCss();
    @Resource("org/kuali/student/common/ui/public/KSDropDown.css")
	public CssResource dropDownCss();
    @Resource("org/kuali/student/common/ui/public/KSFloatPanel.css")
	public CssResource floatPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSFormLayout.css")
	public CssResource formLayoutCss();
    @Resource("org/kuali/student/common/ui/public/KSHelpLink.css")
	public CssResource helpLinkCss();
    @Resource("org/kuali/student/common/ui/public/KSImage.css")
	public CssResource imageCss();
    @Resource("org/kuali/student/common/ui/public/KSInfoDialogPanel.css")
	public CssResource infoDialogPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSModalDialogPanel.css")
	public CssResource modalDialogPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSLabel.css")
	public CssResource labelCss();
    @Resource("org/kuali/student/common/ui/public/KSListBox.css")
	public CssResource listBoxCss();
    @Resource("org/kuali/student/common/ui/public/KSRadioButton.css")
	public CssResource radioButtonCss();
    @Resource("org/kuali/student/common/ui/public/KSResizablePanel.css")
	public CssResource resizablePanelCss();
    @Resource("org/kuali/student/common/ui/public/KSRichTextEditor.css")
	public CssResource richTextEditorCss();
    @Resource("org/kuali/student/common/ui/public/KSTable.css")
	public CssResource tableCss();
    @Resource("org/kuali/student/common/ui/public/KSTextArea.css")
	public CssResource textAreaCss();
    @Resource("org/kuali/student/common/ui/public/KSTextBox.css")
	public CssResource textBoxCss();
    @Resource("org/kuali/student/common/ui/public/KSDialogPanel.css")
	public CssResource dialogPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSProgressIndicator.css")
	public CssResource progressIndicatorCss();
    @Resource("org/kuali/student/common/ui/public/KSConfirmButtonPanel.css")
	public CssResource confirmButtonPanelCss();
    @Resource("org/kuali/student/common/ui/public/KSConfirmationDialog.css")
	public CssResource confirmationDialogCss();
    @Resource("org/kuali/student/common/ui/public/KSSidebar.css")
	public CssResource sidebarCss();
    @Resource("org/kuali/student/common/ui/public/KSToolTip.css")
	public CssResource toolTipCss();
}
