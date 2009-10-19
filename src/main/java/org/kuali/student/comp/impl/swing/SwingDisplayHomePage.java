/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplayHomePage;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.kuali.student.comp.infc.KSDisplayAuthenticatedUser;
import org.kuali.student.comp.infc.KSDisplaySelectFromUserTaskList;
import org.kuali.student.comp.infc.KSDisplaySelectProposalTypesUserCanStart;

/**
 *
 * @author nwright
 */
public class SwingDisplayHomePage extends AbstractSwingUIContainer
 implements KSDisplayHomePage
{

 private KSContext context;

 public SwingDisplayHomePage (KSContext context)
 {
  super (new JFrame ());
  this.context = context;
  init ();
 }

 private JFrame getFrame ()
 {
  return (JFrame) getContainer ();
 }

 private void init ()
 {
  //JDesktopPane desktop = new JDesktopPane ();
  getFrame ().setContentPane (new Box (BoxLayout.Y_AXIS));

  getFrame ().addWindowListener (new java.awt.event.WindowAdapter ()
  {

   @Override
   public void windowClosing (WindowEvent winEvt)
   {
    getCallback ().onDone ();
   }

  });

  getFrame ().setTitle ("KS Home Page");
  getFrame ().getContentPane ().add (new JLabel ("Welcome to Kuali Student"));
 }

 private KSDisplayAuthenticatedUser displayUser;

 @Override
 public void setDisplayAuthenticatedUser (KSDisplayAuthenticatedUser displayUser)
 {
  this.displayUser = displayUser;
 }

 private KSDisplaySelectFromUserTaskList displayTaskList;

 @Override
 public void setDisplaySelectFromUserTaskList (
  KSDisplaySelectFromUserTaskList displayTaskList)
 {
  this.displayTaskList = displayTaskList;
 }

 private KSDisplaySelectProposalTypesUserCanStart displayProposalTypes;

 @Override
 public void setDisplaySelectProposalTypesUserCanStart (
  KSDisplaySelectProposalTypesUserCanStart proposalTypes)
 {
  this.displayProposalTypes = proposalTypes;
 }

 private Callback callback;

 protected Callback getCallback ()
 {
  return callback;
 }

 @Override
 public void display (Callback callback)
 {
  this.callback = callback;
  this.add (displayUser);
  this.add (displayTaskList);
  this.add (displayProposalTypes);
  displayUser.display (callback);
  displayTaskList.display (callback);
  displayProposalTypes.display (callback);
  //getFrame ().pack ();
  //Make the big window be indented 50 pixels from each edge
  //of the screen.
  int inset = 50;
  Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
  getFrame ().setBounds (inset, inset,
                   screenSize.width - inset * 2,
                   screenSize.height - inset * 2);
  //getFrame ().pack ();
  getFrame ().setVisible (true);
 }

}
