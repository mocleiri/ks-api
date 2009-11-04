package org.kuali.student.common.ui.theme.standard.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface KSClientBundle extends ImmutableResourceBundle{
	public static final KSClientBundle INSTANCE =  GWT.create(KSClientBundle.class);

    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSGeneral.css")
	public CssResource generalCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSAccordionMenu.css")
	public CssResource accordionMenuCss();    
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSAccordionPanel.css")
	public CssResource accordionPanelCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSBlockingProgressIndicator.css")
    public CssResource breadcrumbCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSBreadcrumb.css")
	public CssResource blockingProgressIndicatorCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSButton.css")
	public CssResource buttonCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSCheckbox.css")
	public CssResource checkboxCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSDatePicker.css")
	public CssResource datepickerCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSDisclosureSection.css")
	public CssResource disclosureSectionCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSDropDown.css")
    public CssResource ErrorDialogCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSErrorDialog.css")
	public CssResource dropDownCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSFloatPanel.css")
	public CssResource floatPanelCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSFormLayout.css")
	public CssResource formLayoutCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSHelpLink.css")
	public CssResource headerCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/Header.css")
    public CssResource helpLinkCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSImage.css")
	public CssResource imageCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSInfoDialogPanel.css")
	public CssResource infoDialogPanelCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSModalDialogPanel.css")
	public CssResource modalDialogPanelCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSLabel.css")
	public CssResource labelCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSListBox.css")
	public CssResource listBoxCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSRadioButton.css")
	public CssResource radioButtonCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSResizablePanel.css")
	public CssResource resizablePanelCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSRichTextEditor.css")
	public CssResource richTextEditorCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSTable.css")
	public CssResource tableCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSTextArea.css")
	public CssResource textAreaCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSTextBox.css")
	public CssResource textBoxCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSDialogPanel.css")
	public CssResource dialogPanelCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSProgressIndicator.css")
	public CssResource progressIndicatorCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSConfirmButtonPanel.css")
	public CssResource confirmButtonPanelCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSConfirmationDialog.css")
	public CssResource confirmationDialogCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSScrollTable.css")
    public CssResource scrollTableCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSSidebar.css")
	public CssResource sidebarCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSToolTip.css")
	public CssResource toolTipCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSBasicMenu.css")
    public CssResource basicMenuCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSPickList.css")
    public CssResource pickListCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSSelectableTableList.css")
    public CssResource selectableTableListCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSAdvancedSearchWindow.css")
    public CssResource advancedSearchWindowCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSSuggestBoxPicker.css")
    public CssResource suggestBoxPickerCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSButtonLayout.css")
    public CssResource buttonLayoutCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSCustomFlowPanel.css")
    public CssResource flowPanelCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSMultiplicity.css")
    public CssResource multiplicityCss();
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/CommentTool.css")
    public CssResource commentToolCss();   
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/KSSectionTitle.css")
    public CssResource sectionTitleCss(); 
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/DebugPanel.css")
    public CssResource debugPanelCss();
	@Resource("org/kuali/student/common/ui/theme/standard/public/css/KSRequiredMarker.css")
	public CssResource requiredMarkerCss();
	@Resource("org/kuali/student/common/ui/theme/standard/public/css/UploadProgressBar.css")
	public CssResource uploadProgressBarCss(); 
	@Resource("org/kuali/student/common/ui/theme/standard/public/css/KSValidationLayout.css")
	public CssResource validationLayoutCss();
	@Resource("org/kuali/student/common/ui/theme/standard/public/css/KSTabPanel.css")
	public CssResource tabPanelCss();
	@Resource("org/kuali/student/common/ui/theme/standard/public/css/KSTitleContainer.css")
	public CssResource titleContainerCss();
	@Resource("org/kuali/student/common/ui/theme/standard/public/css/KSWrapper.css")
	public CssResource wrapperCss();
	
	 //Common Images***************************************************
	
	@Resource("org/kuali/student/common/ui/theme/standard/public/images/common/kru_banner2.jpg")
	public ImageResource headerImage();
	
    // set the resource value to an image of a question mark
    @Resource("org/kuali/student/common/ui/theme/standard/public/images/common/help-16x16.png")
    public ImageResource helpIcon();
    
    // set the resource value to an image of a green checkmark
    @Resource("org/kuali/student/common/ui/theme/standard/public/images/common/agt_action_success.png")
    public ImageResource okIcon();
    
    // set the resource value to an image of a red stop sign with x
    @Resource("org/kuali/student/common/ui/theme/standard/public/images/common/agt_stop.png")
    public ImageResource errorIcon();
    
    // set the resource value to an image of a yellow triangle with exclamation point
    @Resource("org/kuali/student/common/ui/theme/standard/public/images/common/messagebox_warning.png")
    public ImageResource warningIcon();
    
    @Resource("org/kuali/student/common/ui/theme/standard/public/images/common/asterisk_orange.png")
    public ImageResource asterisk();
    
    @Resource("org/kuali/student/common/ui/theme/standard/public/images/common/blue-gradient.jpg")
    public DataResource blueGradient();
    
    @Resource("org/kuali/student/common/ui/theme/standard/public/css/border-radius.htc")
    public DataResource ieRadius();
    
    @Resource("org/kuali/student/common/ui/theme/standard/public/images/common/14_pencil.png")
    public ImageResource editComment();
    
    @Resource("org/kuali/student/common/ui/theme/standard/public/images/common/fileclose.png")
    public ImageResource deleteComment();
    
    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/bold.gif")
    public ImageResource bold();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/createLink.gif")
    public ImageResource createLink();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/hr.gif")
    public ImageResource hr();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/indent.gif")
    public ImageResource indent();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/insertImage.gif")
    public ImageResource insertImage();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/italic.gif")
    public ImageResource italic();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/justifyCenter.gif")
    public ImageResource justifyCenter();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/justifyLeft.gif")
    public ImageResource justifyLeft();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/justifyRight.gif")
    public ImageResource justifyRight();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/ol.gif")
    public ImageResource ol();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/outdent.gif")
    public ImageResource outdent();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/removeFormat.gif")
    public ImageResource removeFormat();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/removeLink.gif")
    public ImageResource removeLink();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/strikeThrough.gif")
    public ImageResource strikeThrough();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/subscript.gif")
    public ImageResource subscript();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/superscript.gif")
    public ImageResource superscript();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/ul.gif")
    public ImageResource ul();

    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/underline.gif")
    public ImageResource underline();
    
    @Resource("org/kuali/student/common/ui/theme/standard/public/images/richTextEditor/popout.png")
    public ImageResource popout();
    

}
