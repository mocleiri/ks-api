//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.10.21 at 02:14:18 PM PDT 
//


package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;



@XmlAccessorType(XmlAccessType.FIELD)
public class SearchSelector implements Serializable{

	private static final long serialVersionUID = 1L;
    
    @XmlAttribute(required = true)
    protected String key;

    /**
     * Get search key
     * @return search Key
     */
    public String getKey() {
		return key;
	}

    /**
     * Set search key
     * @param key
     */
	public void setKey(String key) {
		this.key = key;
	}
}
