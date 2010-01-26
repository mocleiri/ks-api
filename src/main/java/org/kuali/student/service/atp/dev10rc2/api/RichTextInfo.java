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
package org.kuali.student.service.atp.dev10rc2.api;



public interface RichTextInfo
{
	
	/**
	* Set Plain Text
	*
	* Plain version of the text. This may be used for searching.
	*/
	public void setPlain(String plain);
	
	/**
	* Get Plain Text
	*
	* Plain version of the text. This may be used for searching.
	*/
	public String getPlain();
	
	
	
	/**
	* Set Formatted Text
	*
	* Formatted version of the text.
	*/
	public void setFormatted(String formatted);
	
	/**
	* Get Formatted Text
	*
	* Formatted version of the text.
	*/
	public String getFormatted();
	
	
	
}

