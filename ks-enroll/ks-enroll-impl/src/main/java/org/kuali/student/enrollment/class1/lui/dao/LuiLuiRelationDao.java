package org.kuali.student.enrollment.class1.lui.dao;

import java.util.List;

import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class LuiLuiRelationDao extends GenericEntityDao<LuiLuiRelationEntity>{
    @SuppressWarnings({"unchecked"})
    public List<LuiLuiRelationEntity> getLuiLuiRelationsByLui(String luiId) {
    	return (List<LuiLuiRelationEntity>) em.createQuery("from LuiLuiRelationEntity rel where rel.lui.id=:luiId OR rel.relatedLui.id=:luiId").setParameter("luiId", luiId).getResultList();
    }
    
    @SuppressWarnings({"unchecked"})
	public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationTypeKey){
		return (List<String>) em.createQuery("select rel.lui.id from LuiLuiRelationEntity rel where rel.relatedLui.id=:relatedLuiId and rel.luiLuiRelationType=:luLuRelationTypeKey")
        .setParameter("relatedLuiId", relatedLuiId)
		.setParameter("luLuRelationTypeKey", luLuRelationTypeKey)
		.getResultList();
	}
	
    @SuppressWarnings({"unchecked"})
	public List<LuiEntity> getLuisByRelation(String relatedLuiId, String luLuRelationTypeKey){
        return (List<LuiEntity>) em.createQuery("select rel.lui from LuiLuiRelationEntity rel where rel.relatedLui.id=:relatedLuiId and rel.luiLuiRelationType=:luLuRelationTypeKey")
		.setParameter("relatedLuiId", relatedLuiId)
		.setParameter("luLuRelationTypeKey", luLuRelationTypeKey)
		.getResultList();
	}

    @SuppressWarnings({"unchecked"})
	public List<String> getRelatedLuisByLuiId(String luiId, String luLuRelationTypeKey){
		return (List<String>) em.createQuery("select rel.lui.id from LuiLuiRelationEntity rel where rel.lui.id=:luiId and rel.luiLuiRelationType.id=:luLuRelationTypeKey")
		.setParameter("luiId", luiId)
		.setParameter("luLuRelationTypeKey", luLuRelationTypeKey)
		.getResultList();
	}

}
