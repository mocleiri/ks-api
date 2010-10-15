<%--
 Copyright 2007-2009 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl2.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<c:if test="${ConfigProperties.portal.show.sample.app eq 'true'}">
	<td class="content" valign="top">
	    <mainChannel:ksMessages />
        <mainChannel:ksAtp />
	</td>
</c:if>
<td class="content" valign="top">
<mainChannel:workflow />
</td>
<td class="content" valign="top">
<mainChannel:notification />
</td>
