package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RuleNodeWidget extends FocusPanel {
    private Node node;
    private boolean showControls;
    HTML html = new HTML();
    CheckBox checkBox = new CheckBox();
    KSLabel edit = new KSLabel("Edit");
    AndOrButton toggle = new AndOrButton();
    HorizontalPanel checkBoxAndEdit;
    HandlerRegistration editClauseHandlerRegistration;
    
    public RuleNodeWidget(Node n) {
        init(n, true);
    }
    
    public RuleNodeWidget(Node n, boolean showControls) {
        init(n, showControls);
    }
    
    private void init(Node n, boolean showControls) {
        node = n;
        this.showControls = showControls;
        drawNode(n, null, -1, -1); 
    }
    
    public boolean isShowControls() {
        return showControls;
    }

    public void setShowControls(boolean showControls) {
        this.showControls = showControls;
    }

    public Node getNode() {
        return node;
    }
    public boolean isSelected(){
        return checkBox.getValue() == true;
    }
    public void addToggleHandler(ClickHandler ch) {
        toggle.addClickHandler(ch);
    }
    
    public void clearToggleHandler() {
        toggle.removeClickHandler();
    }
    
    public void addEditClauseHandler(ClickHandler ch) {
        editClauseHandlerRegistration = edit.addClickHandler(ch);
    }

    public void clearEditClauseHandler() {
        if (editClauseHandlerRegistration != null) {
            editClauseHandlerRegistration.removeHandler();
        }
    }
    
    public void drawNode(Node n, RuleTable ruleTable,
            int rowIndex, int columnIndex) {
        Object userObject = null;
        node = n;
        userObject = node.getUserObject();

        if (userObject instanceof StatementVO) {
            StatementVO statementVO = (StatementVO) userObject;
            VerticalPanel checkBoxAndToggle = new VerticalPanel();
            super.setWidget(checkBoxAndToggle);
            if (showControls) {
                checkBoxAndToggle.add(checkBox);
                if (statementVO.getLuStatementInfo() != null &&
                        statementVO.getLuStatementInfo().getOperator() ==
                            StatementOperatorTypeKey.OR) {
                    toggle.setValue(AndOrButton.Or);
                } else {
                    toggle.setValue(AndOrButton.And);
                }
                checkBoxAndToggle.add(toggle);
                if (ruleTable != null) {
                    String selectionStyle = (statementVO.isCheckBoxOn() ? "KS-ReqComp-Selected" : "KS-ReqComp-DeSelected");
                    ruleTable.getCellFormatter().setStyleName(
                            rowIndex, columnIndex, selectionStyle);
                }
            } else {
                checkBoxAndToggle.add(html);
                checkBoxAndToggle.setStyleName("KS-ReqComp-DeSelected");
            }
            html.setHTML(node.getUserObject().toString());
            checkBox.setValue(new Boolean(statementVO.isCheckBoxOn()), false);
        } else if (userObject instanceof ReqComponentVO) {
            ReqComponentVO reqComponentVO = (ReqComponentVO) userObject;
            KSLabel rcLabel = null;
            checkBoxAndEdit= new HorizontalPanel();
            checkBoxAndEdit.setStyleName("KS-Rules-Table-Cell-Margin");
            super.setWidget(checkBoxAndEdit);
            if (reqComponentVO.getGuiReferenceLabelId() != null) {
                Node parent = node.getParent();
                if (parent != null) {
                    rcLabel = new KSLabel(reqComponentVO.getGuiReferenceLabelId());
                    rcLabel.getElement().getStyle().setProperty("fontWeight", "bold");
                    rcLabel.getElement().getStyle().setProperty("background", "#E0E0E0");
                    checkBoxAndEdit.add(rcLabel);
                }
            }
            if (showControls) {
                checkBoxAndEdit.add(checkBox);
                edit.addStyleName("KS-Rules-Edit-Link");
                checkBoxAndEdit.add(edit);
                if (ruleTable != null) {
                    String selectionStyle = (reqComponentVO.isCheckBoxOn() ? "KS-ReqComp-Selected" : "KS-ReqComp-DeSelected");
                    ruleTable.getCellFormatter().setStyleName(
                            rowIndex, columnIndex, selectionStyle);
                }
            } else {
                checkBoxAndEdit.add(html);
            }
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
            checkBox.setValue(new Boolean(reqComponentVO.isCheckBoxOn()));
        } else {
            super.setWidget(html);
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
        }
    }
}