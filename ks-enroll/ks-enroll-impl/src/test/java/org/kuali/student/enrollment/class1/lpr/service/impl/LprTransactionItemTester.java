/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.test.util.IdEntityTester;
import org.kuali.student.enrollment.test.util.ListOfStringTester;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class LprTransactionItemTester {

    public void add2ForCreate(List<LprTransactionItemInfo> expected) {
        LprTransactionItemInfo item = new LprTransactionItemInfo();
        item.setTypeKey(LprServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY);
        item.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        item.setNewLuiId("Luiid1");
        item.setPersonId("person1");
        expected.add(item);
        item = new LprTransactionItemInfo();
        item.setTypeKey(LprServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY);
        item.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        item.setNewLuiId("Luiid2");
        item.setPersonId("person1");
        expected.add(item);
    }

    public List<LprTransactionItemInfo> findItem(List<LprTransactionItemInfo> items, String id) {
        List<LprTransactionItemInfo> list = new ArrayList<LprTransactionItemInfo>();
        for (LprTransactionItemInfo actual : items) {
            if (id.equals(actual.getId())) {
                list.add(actual);
            }
        }
        return list;
    }

    public void check(List<LprTransactionItemInfo> expectedList, List<LprTransactionItemInfo> actualList) {
        if (expectedList.size() != actualList.size()) {
            this.dump(expectedList, actualList);
        }
        assertEquals(expectedList.size(), actualList.size());
        List<LprTransactionItemInfo> expectedSorted = new ArrayList<LprTransactionItemInfo>(expectedList);
//        Collections.sort(expectedSorted, new LprTransactionItemInfoComparator());
        List<LprTransactionItemInfo> actualSorted = new ArrayList<LprTransactionItemInfo>(actualList);
//        Collections.sort(actualSorted, new LprTransactionItemInfoComparator());
        for (int i = 0; i < expectedSorted.size(); i++) {
            LprTransactionItemInfo expected = expectedSorted.get(i);
            LprTransactionItemInfo actual = actualSorted.get(i);
            if (expected.getId() != null) {
                assertEquals(i + "", expected.getId(), actual.getId());
            }
            new IdEntityTester().check(expected, actual);
            assertEquals(expected.getPersonId(), actual.getPersonId());
            assertEquals(expected.getNewLuiId(), actual.getNewLuiId());
            assertEquals(expected.getExistingLuiId(), actual.getExistingLuiId());
            new ListOfStringTester().check(expected.getResultValuesGroupKeys(), actual.getResultValuesGroupKeys());
            new LprTransactionItemResultTester ().check(expected.getLprTransactionItemResult(), actual.getLprTransactionItemResult());
        }
    }

    public void dump(List<LprTransactionItemInfo> expectedList, List<LprTransactionItemInfo> actualList) {
        System.out.println("Original List");
        this.dump(expectedList);
        System.out.println("Updated List");
        this.dump(actualList);
    }

    public void dump(List<LprTransactionItemInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            LprTransactionItemInfo expected = list.get(i);
            System.out.println(i + ".) " + expected.getId() + "=" + expected.getExistingLuiId() + "\t" + expected.getNewLuiId());
        }
    }

    private static class LprTransactionItemInfoComparator implements Comparator<LprTransactionItemInfo> {

        @Override
        public int compare(LprTransactionItemInfo o1, LprTransactionItemInfo o2) {
        	
        	String k1 = calcSortKey(o1);
        	String k2 = calcSortKey(o2);
        	
            return k1.compareTo(k2);
        }

        private String calcSortKey(LprTransactionItemInfo item) {
            StringBuilder sb = new StringBuilder();
            
            boolean existingElement = false;
            
            if (item.getId() != null) {
                sb.append(item.getId());
                existingElement = true;
            }
          
            if (item.getNewLuiId() != null) {
            	
            	if (existingElement)
            		  sb.append("\t");
            	
                sb.append(item.getNewLuiId());
                
                existingElement = true;
            }
            if (item.getExistingLuiId() != null) {
            	if (existingElement)
          		  sb.append("\t");
            	
                sb.append(item.getExistingLuiId());
            }
            return sb.toString();
        }
    }
}
