
package org.kuali.student.core.search.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Tue Nov 18 17:07:24 EST 2008
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "findSearchTypesByCriteria", namespace = "http://org.kuali.student/core/search")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findSearchTypesByCriteria", namespace = "http://org.kuali.student/core/search")

public class FindSearchTypesByCriteria {

    @XmlElement(name = "searchCriteriaTypeKey")
    private java.lang.String searchCriteriaTypeKey;

    public java.lang.String getSearchCriteriaTypeKey() {
        return this.searchCriteriaTypeKey;
    }

    public void setSearchCriteriaTypeKey(java.lang.String newSearchCriteriaTypeKey)  {
        this.searchCriteriaTypeKey = newSearchCriteriaTypeKey;
    }

}

