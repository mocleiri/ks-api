	/*
	 * Copyright 2010 The Kuali Foundation
	 *
	 * Licensed under the Educational Community License, Version 2.0 (the "License");
	 * you may not use this file except in compliance with the License.
	 * You may	obtain a copy of the License at
	 *
	 * 	http://www.osedu.org/licenses/ECL-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS,
	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	 * See the License for the specific language governing permissions and
	 * limitations under the License.
	 */
	package org.kuali.student.service.lu.dev.api;
	
	
	import java.io.Serializable;
	import java.util.Date;
	
	
	public class MetaBean
	 implements MetaInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String versionInd;
		
		/**
		* Set Version Indicator
		*
		* An indicator of the version of the thing being described with this meta 
		* information. This is set by the service implementation and will be used to 
		* determine conflicts in updates.
		*/
		@Override
		public void setVersionInd(String versionInd)
		{
			this.versionInd = versionInd;
		}
		
		/**
		* Get Version Indicator
		*
		* An indicator of the version of the thing being described with this meta 
		* information. This is set by the service implementation and will be used to 
		* determine conflicts in updates.
		*/
		@Override
		public String getVersionInd()
		{
			return this.versionInd;
		}
						
		private Date createTime;
		
		/**
		* Set Date/Time Created
		*
		* The date and time the thing being described with this meta information was 
		* created
		*/
		@Override
		public void setCreateTime(Date createTime)
		{
			this.createTime = createTime;
		}
		
		/**
		* Get Date/Time Created
		*
		* The date and time the thing being described with this meta information was 
		* created
		*/
		@Override
		public Date getCreateTime()
		{
			return this.createTime;
		}
						
		private String createId;
		
		/**
		* Set Created By Identifier
		*
		* The principal who created the thing being described with this meta information
		*/
		@Override
		public void setCreateId(String createId)
		{
			this.createId = createId;
		}
		
		/**
		* Get Created By Identifier
		*
		* The principal who created the thing being described with this meta information
		*/
		@Override
		public String getCreateId()
		{
			return this.createId;
		}
						
		private Date updateTime;
		
		/**
		* Set Date/Time Last Updated
		*
		* The date and time the thing being described with this meta information was last 
		* updated. This would be filled in on initial creation as well.
		*/
		@Override
		public void setUpdateTime(Date updateTime)
		{
			this.updateTime = updateTime;
		}
		
		/**
		* Get Date/Time Last Updated
		*
		* The date and time the thing being described with this meta information was last 
		* updated. This would be filled in on initial creation as well.
		*/
		@Override
		public Date getUpdateTime()
		{
			return this.updateTime;
		}
						
		private String updateId;
		
		/**
		* Set Updated By Identifier
		*
		* The principal who last updated the thing being described with this meta 
		* information. This would be filled in on initial creation as well.
		*/
		@Override
		public void setUpdateId(String updateId)
		{
			this.updateId = updateId;
		}
		
		/**
		* Get Updated By Identifier
		*
		* The principal who last updated the thing being described with this meta 
		* information. This would be filled in on initial creation as well.
		*/
		@Override
		public String getUpdateId()
		{
			return this.updateId;
		}
						
	}

