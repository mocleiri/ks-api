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
package org.kuali.student.core.comment.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.comment.dao.CommentDao;
import org.kuali.student.core.comment.entity.Reference;
import org.kuali.student.core.comment.entity.Tag;
import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class CommentDaoImpl extends AbstractSearchableCrudDaoImpl implements CommentDao {
    @PersistenceContext(unitName = "Comment")
    @Override
    public void setEm(EntityManager em) {
        super.setEm(em);
    }
    
    
    public List<Tag> getTags(String referenceId, String referenceTypeKey){
        Query query = em.createNamedQuery("Tag.getTags");
        query.setParameter("refId", referenceId);
        query.setParameter("refTypeId", referenceTypeKey);
        List<Tag> tags = query.getResultList();
        return tags;
    }
    
    public List<Tag> getTagsByRefId(String referenceId){
        Query query = em.createNamedQuery("Tag.getTagsByRefId");
        query.setParameter("refId", referenceId);
        List<Tag> tags = query.getResultList();
        return tags;
    }
    
    public List<Tag> getTagsByType(String referenceId, String referenceTypeKey, String tagTypeKey ){
        Query query = em.createNamedQuery("Tag.getTagsByType");
        query.setParameter("refId", referenceId);
        query.setParameter("refTypeId", referenceTypeKey);
        query.setParameter("tagTypeId", tagTypeKey);
        List<Tag> tags = query.getResultList();
        return tags;
    }
    
    public Reference getReference(String referenceId, String referenceType){
        Query query = em.createNamedQuery("Reference.getReference");
        query.setParameter("refId", referenceId);
        query.setParameter("refTypeId", referenceType);
        try{
        Reference reference = (Reference)query.getSingleResult();
        return reference;
        }
        catch(NoResultException e){
            return null;
        }
        
        
    }


}
