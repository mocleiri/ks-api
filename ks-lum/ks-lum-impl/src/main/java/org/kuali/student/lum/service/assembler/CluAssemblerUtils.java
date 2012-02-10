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
package org.kuali.student.lum.service.assembler;

import org.kuali.student.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.common.assembly.data.AssemblyException;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.lum.lu.service.LuService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This is a description of what this class does - jimt don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class CluAssemblerUtils {
    private LuService luService;
    private LearningObjectiveService loService;
 // TODO KSCM    private LoAssembler loAssembler;

    public List<String> assembleCluResults(List<String> resultTypes, List<CluResultInfo> cluResults) throws AssemblyException{
		if(resultTypes==null){
			throw new AssemblyException("result types can not be null");
		}
		List<String> results = new ArrayList<String>();
		//Loop through all the CluResults to find those with the matching types
		for(CluResultInfo cluResult:cluResults){
			if(resultTypes.contains(cluResult.getType())){
				//Loop through all options and add to the list of Strings
				for(ResultOptionInfo resultOption: cluResult.getResultOptions()){
					results.add(resultOption.getResultComponentId());
				}
			}
		}
		return results;
	}

    public BaseDTOAssemblyNode<?, ?> disassembleCluResults(String cluId,
			String cluState, List<String> options, NodeOperation operation, String resultType,
			String resultsDescription, String resultDescription) throws AssemblyException {
		BaseDTOAssemblyNode<List<String>, CluResultInfo> cluResultNode = new BaseDTOAssemblyNode<List<String>, CluResultInfo>(null);
		if(resultType==null){
			throw new AssemblyException("resultType can not be null");
		}

		// Get the current options and put them in a map of option type id/cluResult
		Map<String, ResultOptionInfo> currentResults = new HashMap<String, ResultOptionInfo>();

		CluResultInfo cluResult = null;

        //TODO Check this for proper handling of multiple CluResultInfos?  
		//If this is not a create, lookup the results for this clu
		if (!NodeOperation.CREATE.equals(operation)) {
			try {
				List<CluResultInfo> cluResultList = luService.getCluResultByClu(cluId, new ContextInfo());

				for (CluResultInfo currentResult : cluResultList) {
					if (resultType.equals(currentResult.getType())) {
						cluResult = currentResult;
						if(NodeOperation.DELETE.equals(operation)){
							//if this is a delete, then we only need the CluResultInfo
							cluResultNode.setOperation(NodeOperation.DELETE);
						}else{
							//Find all the Result options and store in a map for easy access later
							cluResultNode.setOperation(NodeOperation.UPDATE);
							
							for(ResultOptionInfo resultOption:currentResult.getResultOptions()){
								// Set the state of the result option so it appears in the KSLU_RSLT_OPT table
							    // States include Draft, Approved, Superseded etc.
							    resultOption.setState(cluState);
							    currentResults.put(resultOption.getResultComponentId(), resultOption);
							}
						}
					}
				}
			} catch (DoesNotExistException e) {
			} catch (InvalidParameterException e) {
				throw new AssemblyException("Error getting related " + resultsDescription, e);
			} catch (MissingParameterException e) {
				throw new AssemblyException("Error getting related " + resultsDescription, e);
			} catch (OperationFailedException e) {
				throw new AssemblyException("Error getting related " + resultsDescription, e);
			}
		}

		//If this is a delete we don't need the result options, just the CluResultInfo
		if(!NodeOperation.DELETE.equals(operation)){
			if(cluResult == null){
				//Create a new resultInfo of the given type if one does not exist and set operation to Create
				cluResult = new CluResultInfo();
				cluResult.setCluId(cluId);
				cluResult.setState(cluState);
				cluResult.setType(resultType);
				RichTextInfo desc = new RichTextInfo();
				desc.setPlain(resultsDescription);
				cluResult.setDesc(desc);
				cluResult.setEffectiveDate(new Date());
				cluResultNode.setOperation(NodeOperation.CREATE);
			}

			
			cluResult.setResultOptions(new ArrayList<ResultOptionInfo>());

            // Set the state of the result so it appears in the KSLU_CLU_RSLT table
            // States include Draft, Approved, Superseded etc.
			cluResult.setState(cluState);
			
			// Loop through all the credit options in this clu
			for (String optionType : options) {
				if(currentResults.containsKey(optionType)){
					//If the option exists already copy it to the new list of result options
					ResultOptionInfo resultOptionInfo = currentResults.get(optionType);
                    
					// Set the state of the result option so it appears in the KSLU_RSLT_OPT table
                    // States include Draft, Approved, Superseded etc.
					resultOptionInfo.setState(cluState);
					cluResult.getResultOptions().add(resultOptionInfo);
				}else{
					//Otherwise create a new result option
					ResultOptionInfo resultOptionInfo = new ResultOptionInfo();
					RichTextInfo desc = new RichTextInfo();
					desc.setPlain(resultDescription);
					resultOptionInfo.setDesc(desc);
					resultOptionInfo.setResultComponentId(optionType);
                    
					// Set the state of the result option so it appears in the KSLU_RSLT_OPT table
                    // States include Draft, Approved, Superseded etc.
                    resultOptionInfo.setState(cluState);

					cluResult.getResultOptions().add(resultOptionInfo);
				}
			}
		}

		cluResultNode.setNodeData(cluResult);
		return cluResultNode;
	}

    public List<LoDisplayInfo> assembleLos(String cluId, boolean shallowBuild) throws AssemblyException {
        List<LoDisplayInfo> loInfos = new ArrayList<LoDisplayInfo>();
        try {
            List<CluLoRelationInfo> cluLoRelations = luService.getCluLoRelationsByClu(cluId, new ContextInfo());
            for (CluLoRelationInfo cluLoRelation : cluLoRelations) {
                String loId = cluLoRelation.getLoId();
                LoInfo lo = loService.getLo(loId, new ContextInfo());
// TODO KSCM                loInfos.add(loAssembler.assemble(lo, null, shallowBuild));
            }
        } catch (Exception e) {
            throw new AssemblyException("Error getting learning objectives", e);
        }

        return loInfos;
    }
    
	public List<BaseDTOAssemblyNode<?, ?>> disassembleLos(String cluId, String cluState, List<LoDisplayInfo> loInfos,
			NodeOperation operation) throws AssemblyException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		// TODO Auto-generated method stub
		List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

		// Get the current formats and put them in a map of format id/relation
		// id
		Map<String, CluLoRelationInfo> currentCluLoRelations = new HashMap<String, CluLoRelationInfo>();
		try {
			List<CluLoRelationInfo> cluLoRelations = luService.getCluLoRelationsByClu(cluId, new ContextInfo());
			for(CluLoRelationInfo cluLoRelation:cluLoRelations){
// TODO KSCM				if(CluAssemblerConstants.CLU_LO_CLU_SPECIFIC_RELATION.equals(cluLoRelation.getType())){
// TODO KSCM					currentCluLoRelations.put(cluLoRelation.getLoId(), cluLoRelation);
				// TODO KSCM}
			}
		} catch (DoesNotExistException e) {
		} catch (Exception e) {
			throw new AssemblyException("Error finding related Los", e);
		}

		// Loop through all the los in this clu
		for(LoDisplayInfo loDisplay : loInfos){

			// If this is a clu create/new lo update then all los will be created
		    if (NodeOperation.CREATE == operation
		            || (NodeOperation.UPDATE == operation &&  !currentCluLoRelations.containsKey(loDisplay.getLoInfo().getId()))) {

                // the lo does not exist, so create
                // Assemble and add the lo
		    	loDisplay.getLoInfo().setId(null);
// TODO KSCM		    	loDisplay.getLoInfo().setState(cluState);
		    	// TODO KSCM                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
		    	// TODO KSCM                        .disassemble(loDisplay, NodeOperation.CREATE);
		    	// TODO KSCM                results.add(loNode);

                // Create the relationship and add it as well
                CluLoRelationInfo relation = new CluLoRelationInfo();
                relation.setCluId(cluId);
             // TODO KSCM                relation.setLoId(loNode.getNodeData().getId());
             // TODO KSCM                relation.setType(CluAssemblerConstants.CLU_LO_CLU_SPECIFIC_RELATION);
                
                // Relations can be either Active or Suspended
                // For now, we set them all to Active
                // DO NOT use states like draft, superseded, etc for relations
                relation.setState(DtoConstants.STATE_ACTIVE);

                BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo> relationNode = new BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo>(
                        null);
                relationNode.setNodeData(relation);
                relationNode.setOperation(NodeOperation.CREATE);

                results.add(relationNode);
            } else if (NodeOperation.UPDATE == operation
					&& currentCluLoRelations.containsKey(loDisplay.getLoInfo().getId())) {
				// On update, we need to change the state of the LO to
                // match the state of the parent program
            	// TODO KSCM                loDisplay.getLoInfo().setState(cluState);
            	// TODO KSCM                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
            	// TODO KSCM                		.disassemble(loDisplay, NodeOperation.UPDATE);
            	// TODO KSCM				results.add(loNode);

				// remove this entry from the map so we can tell what needs to
				// be deleted at the end
				currentCluLoRelations.remove(loDisplay.getLoInfo().getId());
			} else if (NodeOperation.DELETE == operation
                    && currentCluLoRelations.containsKey(loDisplay.getLoInfo().getId())) {

                // Delete the Format and its relation
				CluLoRelationInfo relationToDelete = currentCluLoRelations.get(loDisplay.getLoInfo().getId());
                BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo>(
                        null);
                relationToDeleteNode.setNodeData(relationToDelete);
                relationToDeleteNode.setOperation(NodeOperation.DELETE);
                results.add(relationToDeleteNode);

             // TODO KSCM                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
             // TODO KSCM        				.disassemble(loDisplay, NodeOperation.DELETE);
             // TODO KSCM                results.add(loNode);

                // remove this entry from the map so we can tell what needs to
                // be deleted at the end
                currentCluLoRelations.remove(loDisplay.getLoInfo().getId());
			}
		}

        // Now any leftover lo ids are no longer needed, so delete
        // los and relations
        for (Entry<String, CluLoRelationInfo> entry : currentCluLoRelations.entrySet()) {
            // Create a new relation with the id of the relation we want to
            // delete
        	CluLoRelationInfo relationToDelete = entry.getValue();
            BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo>(
                    null);
            relationToDeleteNode.setNodeData(relationToDelete);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);

            try {
				LoInfo loToDelete = loService.getLo(entry.getKey(), new ContextInfo());
			} catch (PermissionDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         // TODO KSCM            LoDisplayInfo loDisplayToDelete = loAssembler.assemble(loToDelete, null, false);
         // TODO KSCM            BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
         // TODO KSCM            		.disassemble(loDisplayToDelete, NodeOperation.DELETE);
         // TODO KSCM            results.add(loNode);
        }

		return results;
	}

    // Spring setters
    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public void setLoService(LearningObjectiveService loService) {
        this.loService = loService;
    }

 // TODO KSCM    public void setLoAssembler(LoAssembler loAssembler) {
 // TODO KSCM        this.loAssembler = loAssembler;
 // TODO KSCM    }
}
