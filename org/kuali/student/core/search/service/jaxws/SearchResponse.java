
package org.kuali.student.core.search.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Mon Jan 25 15:15:40 MST 2010
 * Generated source version: 2.2
 */

@XmlRootElement(name = "searchResponse", namespace = "http://student.kuali.org/wsdl/proposal")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchResponse", namespace = "http://student.kuali.org/wsdl/proposal")

public class SearchResponse {

    @XmlElement(name = "return")
    private org.kuali.student.core.search.newdto.SearchResult _return;

    public org.kuali.student.core.search.newdto.SearchResult getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.core.search.newdto.SearchResult new_return)  {
        this._return = new_return;
    }

}

