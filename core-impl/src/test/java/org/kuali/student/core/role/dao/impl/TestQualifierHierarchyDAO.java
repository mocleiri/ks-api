package org.kuali.student.core.role.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.role.entity.Qualifier;
import org.kuali.student.core.role.entity.QualifierHierarchy;
import org.kuali.student.core.role.entity.QualifierType;
import org.kuali.student.core.role.dao.QualifierHierarchyDAO;
import org.kuali.student.core.role.dao.QualifierHierarchyDAOLoader;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.springframework.test.annotation.Rollback;

@PersistenceFileLocation("classpath:META-INF/role-persistence.xml")
public class TestQualifierHierarchyDAO extends AbstractTransactionalDaoTest {
    
    @Dao(value = "org.kuali.student.core.role.dao.impl.QualifierHierarchyDAOImpl")
    public QualifierHierarchyDAO qualifierHierarchyDAO;
    
   
    public boolean rollback = true;
    
    @Before
    public void onSetUp() {
        QualifierHierarchyDAOLoader loader = new QualifierHierarchyDAOLoader();
        loader.setDao(qualifierHierarchyDAO);
        loader.run();
    }
       
    @Test
    public void testLoadedData() {
        System.out.println("\n\n\n calling test loaded data");
        
        Qualifier term200807 = qualifierHierarchyDAO.findQualifierByType("Term", "200807");
        
        List<Qualifier> lqf = qualifierHierarchyDAO.findQualifiersAtOrAboveQualifier(term200807);
        for(Qualifier q : lqf){
            System.out.println("ret tree qualifier : " + q.getName());
        }
        
        
        Qualifier fall = qualifierHierarchyDAO.findQualifierByType("Term Type", "Fall");
        
        List<Qualifier> lqf2 = qualifierHierarchyDAO.findQualifiersAtOrBelowQualifier(fall);
        for(Qualifier q : lqf2){
          System.out.println("ret tree qualifier : " + q.getName());
        }
    }
    
    @Test
    public void testCompositeQualifier(){
            
        HashMap<String, String> qualifiersMap = new HashMap<String, String>();
        qualifiersMap.put("term-001-for-my-cqt", "summer-for-cq0");
        qualifiersMap.put("course-001-for-my-cqt", "phys800-for-cq0");
        qualifiersMap.put("section-001-for-my-cqt", "sec009-for-cq0");
            
        Qualifier retQ = qualifierHierarchyDAO.findCompositeQualifierByType(qualifiersMap);
        assertTrue(retQ.getName().equals("My-term-composite-qualifier0"));  
    }
    
    @Test
    public void testFetchQualifierHierarchyByName(){
        QualifierHierarchy qh = qualifierHierarchyDAO.fetchQualifierHierarchyByName("QH-TERM");
        assertTrue(qh.getName().equals("QH-TERM"));
    }
}
