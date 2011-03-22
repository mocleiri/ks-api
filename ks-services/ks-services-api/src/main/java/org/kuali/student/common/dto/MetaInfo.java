/**
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
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.MetaInfc;


/**
 *Create and last update info for the structure. This is optional and treated as read only since the data is set by the internals of the service during maintenance operations.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaInfo implements MetaInfc, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final String versionInd;

    @XmlElement
    private final Date createTime;

    @XmlElement
    private final String createId;

    @XmlElement
    private final Date updateTime;

    @XmlElement
    private final String updateId;

    private MetaInfo() {
    	versionInd = null;
    	createTime = null;
    	createId = null;
    	updateTime = null;
    	updateId = null;
    }
    
    private MetaInfo(MetaInfc builder) {
    	this.versionInd = builder.getVersionInd();
    	this.createTime = null != builder.getCreateTime() ? new Date(builder.getCreateTime().getTime()) : null;
    	this.createId = builder.getCreateId();
    	this.updateTime = null != builder.getUpdateTime() ? new Date(builder.getUpdateTime().getTime()) : null;
    	this.updateId = builder.getUpdateId();
	}

	/**
     * An indicator of the version of the thing being described with this meta information. This is set by the service implementation and will be used to determine conflicts in updates.
     */
    public String getVersionInd() {
        return versionInd;
    }

    /**
     * The date and time the thing being described with this meta information was last updated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * The principal who created the thing being described with this meta information
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * The date and time the thing being described with this meta information was last updated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * The principal who last updated the thing being described with this meta information
     */
    public String getUpdateId() {
        return updateId;
    }
    
    public static class Builder implements MetaInfc {
    	
    	private String versionInd;
		private Date createTime;
		private String createId;
		private Date updateTime;
		private String updateId;
		
		public Builder() {}
		
    	public Builder(MetaInfc metaInfo) {
    		if (null != metaInfo) {
	    		this.versionInd = metaInfo.getVersionInd();
	    		this.createTime = metaInfo.getCreateTime();
	    		this.createId = metaInfo.getCreateId();
	    		this.updateTime = metaInfo.getUpdateTime();
	    		this.updateId = metaInfo.getUpdateId();
    		}
		}
    	
		public Builder setVersionInd(String versionInd) {
			this.versionInd = versionInd;
			return this;
		}

		public Builder setCreateTime(Date createTime) {
			this.createTime = createTime;
			return this;
		}

		public Builder setCreateId(String createId) {
			this.createId = createId;
			return this;
		}
		
		public Builder setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
			return this;
		}

		public Builder setUpdateId(String updateId) {
			this.updateId = updateId;
			return this;
		}

    	public MetaInfo build() {
    		return new MetaInfo(this);
    	}

		@Override
		public String getVersionInd() {
			return versionInd;
		}

		@Override
		public Date getCreateTime() {
			return createTime;
		}

		@Override
		public String getCreateId() {
			return createId;
		}

		@Override
		public Date getUpdateTime() {
			return updateTime;
		}

		@Override
		public String getUpdateId() {
			return updateId;
		}
	}

}
