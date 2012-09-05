<%--
 Copyright 2005-2012 The Kuali Foundation

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

<channel:portalChannelTop channelTitle="KRAD Components (continued)" />
<div class="body">
    <strong>Progressive Disclosure</strong>
    <ul class="chan">
        <li></li>
    </ul>

    <strong>Lookups, etc</strong>
    <ul class="chan">
        <li></li>
    </ul>

    <strong>Collections</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Collection action image buttons"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionActionImages-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection column sorting"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionColumnSorting-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection row selection and toggle"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionRowSelection-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection table options"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionOptions-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection with horizontal stacked layout"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionHorizontal-View&methodToCall=collection" /></li>
    </ul>

    <strong>Button / Header Levels</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Buttons"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-Buttons-View&methodToCall=start" /></li>
    </ul>
</div>
<channel:portalChannelBottom />
