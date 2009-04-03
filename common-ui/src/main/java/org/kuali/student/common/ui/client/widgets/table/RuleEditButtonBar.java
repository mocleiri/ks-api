package org.kuali.student.common.ui.client.widgets.table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

public class RuleEditButtonBar extends FlowPanel {
  private Button andButton = new Button("And");
  private Button orButton = new Button("Or");
  private Button toggleButton = new Button("Toggle");
  private Button removeFromGroupButton = new Button("Remove from Group");
  private Button addToGroupButton = new Button("Add to Group");
  private Button undoButton = new Button("Undo");
 private Button redoButton = new Button("Redo");
  
  private RuleEditorModel ruleEditTable;
  
  public RuleEditButtonBar(RuleEditorModel t){
      this.ruleEditTable = t;
      add(andButton);
      add(orButton);
      add(toggleButton);
      add(removeFromGroupButton);
      add(addToGroupButton);
      add(undoButton);
      add(redoButton);
      disableAllButton();
      
      ruleEditTable.addModelChangedEvent(new ModelChangedListener(){
          @Override
          public void modelChanged(RuleEditorModel model) {
              refreshState();
              
          }
      });
      addAndButtonClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doAnd();
              //refreshState();
          }
      });
      addOrButtonClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doOr();
              //refreshState();
          }
      });
      addAddToGroupButtonClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doAddToGroup();
              //refreshState();
          }
      });
      addRemoveFromGroupButtonClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doRemoveFromGroup();
              //refreshState();
          }
      });
      addToggleButtonClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doToggle();
              //refreshState();
          }
      });
      addUndoButtonClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doUndo();
              //refreshState();
          }
      });
      
      addRedoButtonClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doRedo();
              //refreshState();
          }
      });
  }
  public void addAndButtonClickHandler(ClickHandler ch){
      andButton.addClickHandler(ch);
  }
  public void addOrButtonClickHandler(ClickHandler ch){
      orButton.addClickHandler(ch);
  }
  public void addToggleButtonClickHandler(ClickHandler ch){
      toggleButton.addClickHandler(ch);
  }
  public void addRemoveFromGroupButtonClickHandler(ClickHandler ch){
      removeFromGroupButton.addClickHandler(ch);
  }
  public void addAddToGroupButtonClickHandler(ClickHandler ch){
      addToGroupButton.addClickHandler(ch);
  }
  public void addUndoButtonClickHandler(ClickHandler ch){
      undoButton.addClickHandler(ch);
  }
  public void addRedoButtonClickHandler(ClickHandler ch){
      redoButton.addClickHandler(ch);
  }

  private void disableAllButton(){
      andButton.setEnabled(false);
      orButton.setEnabled(false);
      toggleButton.setEnabled(false);
      removeFromGroupButton.setEnabled(false);
      addToGroupButton.setEnabled(false);
      undoButton.setEnabled(false);
      redoButton.setEnabled(false);
  }
  private void hideAllButton(){
      andButton.setVisible(false);
      orButton.setVisible(false);
      toggleButton.setVisible(false);
      removeFromGroupButton.setVisible(false);
      addToGroupButton.setVisible(false);
      undoButton.setVisible(false);
      redoButton.setVisible(false);
  }

  public void refreshState(){
      disableAllButton();
     // hideAllButton();
      if( ruleEditTable.isAndOrOrable()){
          andButton.setEnabled(true);
          orButton.setEnabled(true);
          andButton.setVisible(true);
          orButton.setVisible(true);
      }
      if(ruleEditTable.isTogglable()){
          toggleButton.setEnabled(true);
          toggleButton.setVisible(true);
      }

      if(ruleEditTable.isAddable()){
          addToGroupButton.setEnabled(true);
          addToGroupButton.setVisible(true);
          
      }
      if(ruleEditTable.isRemovable()){
          removeFromGroupButton.setEnabled(true);
          removeFromGroupButton.setVisible(true);
      }
      
      if(ruleEditTable.canRedo()){
          redoButton.setEnabled(true);
          redoButton.setVisible(true);
      }
      if(ruleEditTable.canUndo()){
          undoButton.setEnabled(true);
          undoButton.setVisible(true);
      }      
  }
}
