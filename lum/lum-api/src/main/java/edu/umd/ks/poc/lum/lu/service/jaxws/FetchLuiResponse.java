
package edu.umd.ks.poc.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.4-incubator
 * Fri Apr 18 16:30:06 EDT 2008
 * Generated source version: 2.0.4-incubator
 * 
 */

@XmlRootElement(name = "fetchLuiResponse", namespace = "http://edu.umd.ks/poc/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchLuiResponse", namespace = "http://edu.umd.ks/poc/lum/lu")

public class FetchLuiResponse {

    @XmlElement(name = "return")
    private edu.umd.ks.poc.lum.lu.dto.LuiInfo _return;

    public edu.umd.ks.poc.lum.lu.dto.LuiInfo get_return() {
        return this._return;
    }
    
    public void set_return( edu.umd.ks.poc.lum.lu.dto.LuiInfo new_return ) {
        this._return = new_return;
    }
    
}

