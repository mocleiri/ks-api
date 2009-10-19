/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp.impl.swing;

import javax.swing.JLabel;
import org.kuali.student.comp.impl.ContextHelper;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplayAuthenticatedUser;
import org.kuali.student.comp.infc.KSUIComponent.Callback;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.service.dto.PrincipalId;

/**
 *
 * @author nwright
 */
public class SwingDisplayAuthenticatedUser extends AbstractSwingUIIField
 implements KSDisplayAuthenticatedUser
{

 private KSContext context;

 public SwingDisplayAuthenticatedUser (KSContext context)
 {
  super (new JLabel ());
  this.context = context;
  this.setLabel ("Hello");
 }

 private JLabel getJLabel ()
 {
  return (JLabel) getComponent ();
 }

 private Callback callback;

 @Override
 public void display (Callback callback)
 {
  this.callback = callback;
  try
  {
   PrincipalId id =
    new ContextHelper (context).getAuthenticatedUser ();
   this.setValue (id.getId ());
  }
  catch (OperationFailedException ex)
  {
   new ContextHelper (context).handleUnrecoverableException (this.getClass ().
    getName (), ex);
   return;
  }
 }

 @Override
 public boolean getReadOnly ()
 {
  return true;
 }

 @Override
 public void setReadOnly (boolean readonly)
 {
  // ignore always read-only
 }

 @Override
 public String getValue ()
 {
  return getJLabel ().getText ();
 }

 @Override
 public void setValue (Object value)
 {
  getJLabel ().setText (value.toString ());
 }

}
