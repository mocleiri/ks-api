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

import java.util.ArrayList;
import java.util.HashMap;

import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 *
 * @author Kuali Student Team
 *
 */
@RemoteServiceRelativePath("rpcservices/CluProposalRpcService")
public interface CluProposalRpcService extends BaseRpcService{
    public CluProposal createProposal(CluProposal cluProposal);
    public CluProposal startProposalWorkflow(CluProposal cluProposal);
    public CluProposal saveProposal(CluProposal cluProposal);
    public CluProposal submitProposal(CluProposal cluProposal);
    public CluProposal deleteProposal(String id);
    public CluProposal getProposal(String id);
    public CluProposal getCluProposalFromWorkflowId(String docId);
    public String getActionsRequested(CluProposal cluProposal);
    public Boolean adhocRequest(String docId, String recipientPrincipalId,String requestType, String annotation);
    public Boolean approveProposal(CluProposal cluProposal);
    public Boolean disapproveProposal(CluProposal cluProposal);
    public Boolean acknowledgeProposal(CluProposal cluProposal);
    public Boolean addCollaborator(String docId, String recipientPrincipalId, String collabType, boolean participationRequired, String respondBy);
    public HashMap<String, ArrayList<String>> getCollaborators(String docId);
    public Boolean loginBackdoor(String backdoorId);


    public CluProposalModelDTO createProposal(CluProposalModelDTO cluProposalDTO);
    public CluProposalModelDTO saveProposal(CluProposalModelDTO cluProposalDTO);
    public CluProposalModelDTO getProposalModelDTO(String id);
}
