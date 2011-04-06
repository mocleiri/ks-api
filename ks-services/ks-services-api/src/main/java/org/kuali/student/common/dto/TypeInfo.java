/*
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.TypeInfc;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class TypeInfo extends HasAttributesInfo implements TypeInfc, Serializable {
	
    @XmlAttribute
	private final String key;
	
	@XmlElement
	private final String name;
	
	@XmlElement(name ="desc")
	private final String descr;

	@XmlElement
	private final Date effectiveDate;
	
	@XmlElement
	private final Date expirationDate;
	
	@XmlElement
	private final String refObjectURI;
	
	private TypeInfo() {
		key = null;
		name = null;
		descr = null;
		effectiveDate = null;
		expirationDate = null;
		refObjectURI = null;
	}
		
	private TypeInfo(TypeInfc builder) {
		super(builder);
		this.key = builder.getKey();
		this.name = builder.getName();
		this.descr = builder.getDescr();
    	this.effectiveDate = null != builder.getEffectiveDate() ? new Date(builder.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != builder.getExpirationDate() ? new Date(builder.getExpirationDate().getTime()) : null;
    	this.refObjectURI = builder.getRefObjectURI();
	}
	
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }
    
    @Override
    public String getRefObjectURI() {
        return refObjectURI;
    }
    
    public static class Builder extends HasAttributesInfo.Builder implements TypeInfc {
    	private String key;
		private String name;
		private String descr;
		private Date effectiveDate;
		private Date expirationDate;
		private String refObjectURI;

		public Builder() {}
    	
    	public Builder(TypeInfc typeInfo) {
    		super(typeInfo);
    		this.key = typeInfo.getKey();
    		this.name = typeInfo.getName();
    		this.descr = typeInfo.getDescr();
    		this.effectiveDate = typeInfo.getEffectiveDate();
    		this.expirationDate = typeInfo.getExpirationDate();
    		this.refObjectURI = typeInfo.getRefObjectURI();
    	}

        public TypeInfo build() {
            return new TypeInfo(this);
        } 
        
		@Override
		public String getKey() {
			return key;
		}

		@Override
		public Date getEffectiveDate() {
			return effectiveDate;
		}

		@Override
		public Date getExpirationDate() {
			return expirationDate;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getDescr() {
			return descr;
		}

        @Override
        public String getRefObjectURI() {
            return refObjectURI;
        }
    }
}
