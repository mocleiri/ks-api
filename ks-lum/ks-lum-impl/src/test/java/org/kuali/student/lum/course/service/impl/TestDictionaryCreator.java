package org.kuali.student.lum.course.service.impl;

import org.junit.Test;
import java.beans.IntrospectionException;
import java.io.IOException;
import org.kuali.student.lum.lu.dto.CluSetInfo;


public class TestDictionaryCreator
{

 @Test
 public void testRunDictinoaryCreator ()
   throws IOException,
          IntrospectionException,
          SecurityException,
          NoSuchFieldException
 {
  new DictionaryCreator ().execute (CluSetInfo.class, "target/ks-CluSetInfo-dictinoary-context-generated.xml");
 }
}
