
package org.kuali.student.core.search.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Tue Jan 27 08:59:03 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "getSearchResultType", namespace = "http://org.kuali.student/core/search")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSearchResultType", namespace = "http://org.kuali.student/core/search")

public class GetSearchResultType {

    @XmlElement(name = "searchResultTypeKey")
    private java.lang.String searchResultTypeKey;

    public java.lang.String getSearchResultTypeKey() {
        return this.searchResultTypeKey;
    }

    public void setSearchResultTypeKey(java.lang.String newSearchResultTypeKey)  {
        this.searchResultTypeKey = newSearchResultTypeKey;
    }

}

