<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="East Coast 1 Applications" />
<div class="body">
    <strong>Registration</strong>
    <ul class="chan">
        <%--<li><portal:portalLink displayTitle="true" title="Manage General Environment" url=""/></li>--%>
        <li><portal:portalLink displayTitle="true" title="Manage Registration Windows And Appointments" url="${ConfigProperties.application.url}/kr-krad/registrationWindows?viewId=registrationWindowsManagementView&pageId=selectTermForRegWindows&methodToCall=start"/></li>
        <%--<li><portal:portalLink displayTitle="true" title="Manage Individual Student Appointments" url=""/></li>--%>
        <%--<li><portal:portalLink displayTitle="true" title="Registration Windows Edit Page" url="${ConfigProperties.application.url}/kr-krad/registrationWindows?viewId=registrationWindowsManagementView&pageId=registrationWindowsEditPage&methodToCall=start"/></li>--%>
        <li><portal:portalLink displayTitle="true" title="Registration Windows Lookup and Inquiry " url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
    </ul>
</div>
<channel:portalChannelBottom />
