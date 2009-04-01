package org.kuali.student.core.atp.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.atp.dao.AtpDao;
import org.kuali.student.core.atp.entity.Atp;
import org.kuali.student.core.atp.entity.DateRange;
import org.kuali.student.core.atp.entity.DateRangeType;
import org.kuali.student.core.atp.entity.Milestone;
import org.kuali.student.core.atp.entity.MilestoneType;
import org.kuali.student.core.dao.impl.AbstractCrudDaoImpl;

public class AtpDaoImpl extends AbstractCrudDaoImpl implements AtpDao {

	@PersistenceContext(unitName = "Atp")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	@Override
	public List<Atp> findAtpsByAtpType(String atpTypeId) {
		
		Query q = em.createNamedQuery("Atp.findAtpsByAtpType");
		q.setParameter("atpTypeId", atpTypeId);
		
		@SuppressWarnings("unchecked")
		List<Atp> results = q.getResultList(); 
		return results;
	}

	
	@Override
	public List<Atp> findAtpsByDate(Date searchDate) {
		
		Query q = em.createNamedQuery("Atp.findAtpsByDate");
		q.setParameter("searchDate", searchDate);
		
		@SuppressWarnings("unchecked")
		List<Atp> results = q.getResultList(); 
		return results;
	}
	
	@Override
	public List<Atp> findAtpsByDates(Date startDate, Date endDate) {
		Query q = em.createNamedQuery("Atp.findAtpsByDates");
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		
		@SuppressWarnings("unchecked")
		List<Atp> results = q.getResultList(); 
		return results;
	}

	@Override
	public List<DateRange> findDateRangesByAtp(String atpId) {
		
		Query q = em.createNamedQuery("DateRange.findDateRangesByAtp");
		q.setParameter("atpId", atpId);
		
		@SuppressWarnings("unchecked")
		List<DateRange> results = q.getResultList(); 
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DateRange> findDateRangesByDate(Date searchDate) {
		
		Query q = em.createNamedQuery("DateRange.findDateRangesByDate");
		
		q.setParameter("searchDate", searchDate);
		
		return q.getResultList();
	}

	@Override
	public List<MilestoneType> findMilestoneTypesForAtpType(String atpTypeId) {
		
		Query q = em.createNamedQuery("MilestoneType.findMilestoneTypesForAtpType");
		q.setParameter("atpTypeId", atpTypeId);
		
		@SuppressWarnings("unchecked")
		List<MilestoneType> results = q.getResultList(); 
		return results;
	}

	@Override
	public List<DateRangeType> findDateRangeTypesForAtpType(String atpTypeId) {
		
		Query q = em.createNamedQuery("DateRangeType.findDateRangeTypesForAtpType");
		q.setParameter("atpTypeId", atpTypeId);
		
		@SuppressWarnings("unchecked")
		List<DateRangeType> results = q.getResultList(); 
		return results;
	}
	
	@Override
	public List<Milestone> findMilestonesByAtp(String atpId) {
		
		Query q = em.createNamedQuery("Milestone.findMilestonesByAtp");
		q.setParameter("atpId", atpId);
		
		@SuppressWarnings("unchecked")
		List<Milestone> results = q.getResultList(); 
		return results;
	}

	@Override
	public List<Milestone> findMilestonesByDates(Date startDate, Date endDate) {
		Query q = em.createNamedQuery("Milestone.findMilestonesByDates");

		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		
		@SuppressWarnings("unchecked")
		List<Milestone> results = q.getResultList(); 
		return results;
	}

	@Override
	public List<Milestone> findMilestonesByDatesAndType(
			String milestoneTypeId, Date startDate, Date endDate) {
		Query q = em.createQuery("Milestone.findMilestonesByDatesAndType");
		
		q.setParameter("milestoneTypeId", milestoneTypeId);
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		
		@SuppressWarnings("unchecked")
		List<Milestone> results = q.getResultList(); 
		return results;
	}

}
