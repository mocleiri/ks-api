package org.kuali.student.r2.core.class1.atp.dao;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.AtpAtpRelationEntity;

import java.util.List;

public class AtpAtpRelationDao extends GenericEntityDao<AtpAtpRelationEntity>{
    
    @SuppressWarnings({"unchecked"})
    public List<AtpAtpRelationEntity> getAtpAtpRelationsByAtp(String atpId) {
    	return (List<AtpAtpRelationEntity>) em.createQuery("from AtpAtpRelationEntity rel where rel.atp.id=:atpId OR rel.relatedAtp.id=:atpId").setParameter("atpId", atpId).getResultList();
    }
    
    @SuppressWarnings({"unchecked"})
    public List<AtpAtpRelationEntity> getAtpAtpRelationsByAtpAndRelationType(String atpId, String relationTypeId) {
    	return (List<AtpAtpRelationEntity>) em.createQuery("from AtpAtpRelationEntity rel where rel.atp.id=:atpId AND rel.atpType.id=:relationTypeId").setParameter("atpId", atpId).setParameter("relationTypeId", relationTypeId).getResultList();
    }

    public List<AtpAtpRelationEntity> getAtpAtpRelationsByAtpsAndRelationType(String atpId, String atpPeerId, String relationTypeId) {
    	return (List<AtpAtpRelationEntity>) em.createQuery("from AtpAtpRelationEntity rel where rel.atp.id=:atpId AND rel.relatedAtp.id=:atpPeerId AND rel.atpType.id=:relationTypeId").setParameter("atpId", atpId).setParameter("atpPeerId", atpPeerId).setParameter("relationTypeId", relationTypeId).getResultList();
    }
}
