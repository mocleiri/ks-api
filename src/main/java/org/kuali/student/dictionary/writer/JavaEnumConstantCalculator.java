/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary.writer;

/**
 *
 * @author nwright
 */
public class JavaEnumConstantCalculator
{

 private String name;

 public JavaEnumConstantCalculator (String name)
 {
  this.name = name;
 }

 public String calc ()
 {
  StringBuffer buf = new StringBuffer (name.length () + 3);
  // do the first character so we don't prepend the first with a _ if it is upper
  char c = Character.toUpperCase (name.charAt (0));
  buf.append (c);
  for (int i = 1; i < name.length (); i ++)
  {
   c = name.charAt (i);
   if (Character.isUpperCase (c))
   {
    buf.append ('_');
   }

   buf.append (Character.toUpperCase (c));
  }

  return buf.toString ();
 }

 public String reverse ()
 {
  StringBuffer buf = new StringBuffer (name.length ());
  boolean uppercase = true;
  for (int i = 0; i < name.length (); i ++)
  {
   char c = name.charAt (i);
   if (uppercase)
   {
    c = Character.toUpperCase (c);
    uppercase = false;
   }
   else
   {
    c = Character.toLowerCase (c);
   }
   if (c == '_')
   {
    uppercase = true;
    continue;
   }
   buf.append (c);
  }

  return buf.toString ();
 }

}
