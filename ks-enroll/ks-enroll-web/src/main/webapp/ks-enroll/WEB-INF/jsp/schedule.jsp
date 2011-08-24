<%--
 Copyright 2006-2007 The Kuali Foundation

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
<%@ include file="/krad/WEB-INF/jsp/tldHeader.jsp" %>

<tiles:useAttribute name="control"
                    classname="org.kuali.student.enrollment.uif.control.ScheduleControl"/>
<tiles:useAttribute name="field" classname="org.kuali.rice.krad.uif.field.AttributeField"/>

<div id="${control.id}">
    <table class=".schedule">
        <tr class="dayRow">
            <c:forEach var="day" items="${control.days}">
                <th>${day}</th>
            </c:forEach>
        </tr>
        <c:forEach var="time" items="${control.times}">
            <tr class="timeRow">
                <th>${time}</th>
                <c:forEach var="day" items="${control.days}">
                    <td class="${day}">
                        <div style="position:relative"></div>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
    <table class=".scheduleKey">
        <tr>
            <th>Key</th>
            <th style="text-align: left">Name</th>
        </tr>
    </table>
    <krad:script value="
           jq('#' +'${control.id}').ready(function() {
                jq('#' +'${control.id}').schedule(${control.scheduleOptions});
           });
    "/>
</div>