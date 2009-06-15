/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.service;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public interface CluProposalRpcServiceAsync extends BaseRpcServiceAsync{
    public void createProposal(CluProposal cluProposal, AsyncCallback<CluProposal> callback);  
    public void createAndRouteProposal(CluProposal cluProposal, AsyncCallback<CluProposal> callback);
    public void saveProposal(CluProposal cluProposal, AsyncCallback<CluProposal> callback);
    public void submitProposal(CluProposal cluProposal, AsyncCallback<CluProposal> callback);
    public void deleteProposal(String id, AsyncCallback<CluProposal> callback);
    public void getProposal(String id, AsyncCallback<CluProposal> callback);
    public void getCluProposalFromWorkflowId(String docId, AsyncCallback<CluProposal> callback);
    public void getActionsRequested(CluProposal cluProposal, AsyncCallback<String> callback);
    public void approveProposal(CluProposal cluProposal, AsyncCallback<Boolean> callback);
    public void acknowledgeProposal(CluProposal cluProposal, AsyncCallback<Boolean> callback);
    public void disapproveProposal(CluProposal cluProposal, AsyncCallback<Boolean> callback);
    public void loginBackdoor(String backdoorId, AsyncCallback<Boolean> callback);
}
