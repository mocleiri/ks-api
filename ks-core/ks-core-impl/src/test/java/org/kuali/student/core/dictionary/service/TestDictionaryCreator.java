package org.kuali.student.core.dictionary.service;

import org.junit.Test;
import java.beans.IntrospectionException;
import java.io.IOException;

import org.kuali.student.common.dictionary.service.impl.DictionaryCreator;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.TagInfo;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.dto.RefDocRelationInfo;


public class TestDictionaryCreator
{

 @Test
 public void testRunDictinoaryCreator ()
   throws IOException,
          IntrospectionException,
          SecurityException,
          NoSuchFieldException
 {
  new DictionaryCreator ().execute (RefDocRelationInfo.class, "target/ks-RefDocRelationInfo-dictinoary-context-generated.xml");
  new DictionaryCreator ().execute (DocumentInfo.class, "target/ks-DocumentInfo-dictinoary-context-generated.xml");
  new DictionaryCreator ().execute (TagInfo.class, "target/ks-TagInfo-dictinoary-context-generated.xml");
  new DictionaryCreator ().execute (CommentInfo.class, "target/ks-CommentInfo-dictinoary-context-generated.xml");
 }
}
