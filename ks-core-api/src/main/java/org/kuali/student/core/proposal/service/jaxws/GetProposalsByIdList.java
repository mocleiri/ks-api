
package org.kuali.student.core.proposal.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Wed Sep 02 14:48:07 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getProposalsByIdList", namespace = "http://student.kuali.org/wsdl/proposal")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getProposalsByIdList", namespace = "http://student.kuali.org/wsdl/proposal")

public class GetProposalsByIdList {

    @XmlElement(name = "proposalIdList")
    private java.util.List<java.lang.String> proposalIdList;

    public java.util.List<java.lang.String> getProposalIdList() {
        return this.proposalIdList;
    }

    public void setProposalIdList(java.util.List<java.lang.String> newProposalIdList)  {
        this.proposalIdList = newProposalIdList;
    }

}

