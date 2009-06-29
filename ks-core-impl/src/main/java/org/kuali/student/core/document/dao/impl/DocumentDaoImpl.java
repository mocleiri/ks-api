/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.document.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.document.dao.DocumentDao;
import org.kuali.student.core.document.entity.Document;
import org.kuali.student.core.document.entity.DocumentCategory;
import org.kuali.student.core.exceptions.DoesNotExistException;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class DocumentDaoImpl extends AbstractSearchableCrudDaoImpl implements DocumentDao {
    @PersistenceContext(unitName = "Document")
    @Override
    public void setEm(EntityManager em) {
        super.setEm(em);
    }

    @Override
    public Boolean addDocumentCategoryToDocument(String documentId, String documentCategoryKey) throws DoesNotExistException {
       
        DocumentCategory category = fetch(DocumentCategory.class, documentCategoryKey);
        Document doc = fetch(Document.class, documentId);
        List<DocumentCategory> categoryList = doc.getCategoryList();
        categoryList.add(category);
        doc.setCategoryList(categoryList);
        update(doc);
        return true;
    }

    @Override
    public List<DocumentCategory> getCategoriesByDocument(String documentId) {

        Document doc=null;
        try {
            doc = fetch(Document.class, documentId);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        }
        List<DocumentCategory> categories = doc.getCategoryList();
        
        return categories;
    }

    @Override
    public List<Document> getDocumentsByIdList(List<String> documentIdList) throws DoesNotExistException {
        List<Document> documents = new ArrayList<Document>();
        for(String documentId: documentIdList){
            Document document = new Document();
            document = fetch(Document.class,documentId);
            documents.add(document);
        }
        return documents;
    }

    @Override
    public Boolean removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey) throws DoesNotExistException {
        Document document = fetch(Document.class,documentId);
        List<DocumentCategory> categories = document.getCategoryList();
        for(DocumentCategory category:categories){
            if(category.getId().equals(documentCategoryKey)){
                categories.remove(category);
            }
        }
        document.setCategoryList(categories);
        update(document);
        return true;
    }



}
