/**
 *
 */
package org.kuali.student.lum.program.service.impl;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.lum.course.service.assembler.LoAssembler;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerUtils;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;
import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r1.common.dto.DtoConstants;
import org.kuali.student.r1.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r1.core.statement.service.assembler.StatementTreeViewAssembler;
import org.kuali.student.r1.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.r1.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.r1.lum.lu.dto.CluInfo;
import org.kuali.student.r1.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;


/**
 * @author glindholm
 *
 */
public class ProgramRequirementAssembler implements BOAssembler<ProgramRequirementInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(ProgramRequirementAssembler.class);

	private StatementService statementService;
	private StatementTreeViewAssembler statementTreeViewAssembler;
	private LearningObjectiveService loService;
	private CluService cluService;
	private LoAssembler loAssembler;
	private CluAssemblerUtils cluAssemblerUtils;
    private ProgramAssemblerUtils programAssemblerUtils;


	@Override
	public ProgramRequirementInfo assemble(CluInfo clu,
			ProgramRequirementInfo progReqInfo, boolean shallowBuild,ContextInfo contextInfo)
			throws AssemblyException {
		ProgramRequirementInfo progReq = (progReqInfo != null ? progReqInfo : new ProgramRequirementInfo());

		if (clu.getOfficialIdentifier() != null) {
			progReq.setShortTitle(clu.getOfficialIdentifier().getShortName());
			progReq.setLongTitle(clu.getOfficialIdentifier().getLongName());
		}
		progReq.setDescr(clu.getDescr());

		//assembling minCredits & maxCredits
		assembleCredits(clu, progReq);

		if (progReq.getStatement() == null) {
			try {
				List<RefStatementRelationInfo> relations = statementService.getRefStatementRelationsByRef(ProgramAssemblerConstants.PROGRAM_REQUIREMENT, clu.getId());
				StatementTreeViewInfo statementTree = new StatementTreeViewInfo();
				if (relations != null) {
					statementTreeViewAssembler.assemble(statementService.getStatementTreeView(relations.get(0).getStatementId()), statementTree, shallowBuild, contextInfo);
				}
				progReq.setStatement(statementTree);
				} catch (AssemblyException e) {
				throw e;
			} catch (Exception e) {
				throw new AssemblyException(e);
			}
		}

		if (isEmpty(progReq.getLearningObjectives())) {
			/* TODO KSCM-429 progReq.setLearningObjectives(cluAssemblerUtils.assembleLos(clu.getId(), shallowBuild,contextInfo)); */
		}

		progReq.setMetaInfo(clu.getMetaInfo());
		progReq.setType(clu.getType());
		progReq.setAttributes(clu.getAttributes());
		progReq.setId(clu.getId());

		return progReq;
	}

	@Override
	public BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> disassemble(
			ProgramRequirementInfo progReq, NodeOperation operation,ContextInfo contextInfo)
			throws AssemblyException {

		if (progReq == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
		    LOG.error("ProgramRequirementInfo to disassemble is null!");
			throw new AssemblyException("ProgramRequirementInfo can not be null");
		}

		BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> result = new BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo>(null);
		
		// Create the Statement Tree
        StatementTreeViewInfo statement = progReq.getStatement();
        statement.setId(UUIDHelper.genStringUUID(statement.getId()));
        BaseDTOAssemblyNode<StatementTreeViewInfo, StatementTreeViewInfo> statementTree;
		try {
			statementTree = statementTreeViewAssembler.disassemble(statement, operation,contextInfo);
		} catch (AssemblyException e) {
			throw e;
		} catch (Exception e) {
			throw new AssemblyException(e);
		}
        result.getChildNodes().add(statementTree);

		CluInfo clu;
		try {
			clu = (NodeOperation.UPDATE == operation) ?  R1R2ConverterUtil.convert(cluService.getClu(progReq.getId(), contextInfo), CluInfo.class) : new CluInfo();
        } catch (Exception e) {
			throw new AssemblyException("Error getting existing learning unit during program requirement update", e);
        }

        if (operation.equals(NodeOperation.DELETE)) {
            try {
				final List<CluCluRelationInfo> relations = null;
				    cluService.getCluCluRelationsByClu(progReq.getId(), contextInfo);
	            final BaseDTOAssemblyNode<ProgramRequirementInfo, CluCluRelationInfo> cluRelation = new BaseDTOAssemblyNode<ProgramRequirementInfo, CluCluRelationInfo>(null);
	            if (relations.size() > 1) {
	            	throw new AssemblyException("Unable to dissamble ProgramRequirement, more than one CluCluRelation found");
	            } else if (relations.size() == 1) {
	            	cluRelation.setNodeData(relations.get(0));
	            	cluRelation.setOperation(operation);
	            	result.getChildNodes().add(cluRelation);
	            }
			} catch (Exception e) {
				throw new AssemblyException(e);
			}
        }

        BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> cluResult = new BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo>(this);

        cluResult.setNodeData(clu);
        cluResult.setBusinessDTORef(progReq);
        cluResult.setOperation(operation);
        result.getChildNodes().add(cluResult);

        programAssemblerUtils.disassembleBasics(clu, progReq);

		//disassembling minCredits & maxCredits
        disassembleCredits(clu, progReq,contextInfo);

        progReq.setId(clu.getId());
        CluIdentifierInfo official = null != clu.getOfficialIdentifier() ? clu.getOfficialIdentifier() : new CluIdentifierInfo();
        official.setLongName(progReq.getLongTitle());
        official.setShortName(progReq.getShortTitle());
        
        // We decided not to do null checks in the disassembler.  Instead we will just 
        // set state to whatever is passed into the method (I missed this change when working on 1834)      
        official.setState(progReq.getState());
        
        // gotta be this type
        official.setType(ProgramAssemblerConstants.OFFICIAL);
        clu.setOfficialIdentifier(official);

        clu.setDescr(progReq.getDescr());
        if (progReq.getLearningObjectives() != null) {
            disassembleLearningObjectives(progReq, operation, result,contextInfo);
        }

        RefStatementRelationInfo relation;
        if (operation == NodeOperation.CREATE) {
            relation = new RefStatementRelationInfo();
            relation.setId(UUIDHelper.genStringUUID(null));
        } else {
        	try {
        		relation = statementService.getRefStatementRelationsByRef(ProgramAssemblerConstants.PROGRAM_REQUIREMENT, clu.getId()).get(0);
			} catch (Exception e) {
				throw new AssemblyException("Unable to find RefStatementRelation", e);
			}
        }
        relation.setType("clu.prerequisites"); // FIXME Derive from statement and rule types
        relation.setType(ProgramAssemblerConstants.PROGRAM_REFERENCE_TYPE);
        relation.setRefObjectId(clu.getId());
        relation.setRefObjectTypeKey(ProgramAssemblerConstants.PROGRAM_REQUIREMENT);
        relation.setStatementId(statement.getId());
        // Relations can be either Active or Suspended
        // For now, we always use Active (never use draft, etc for relations)
        relation.setState(DtoConstants.STATE_ACTIVE);

        BaseDTOAssemblyNode<ProgramRequirementInfo, RefStatementRelationInfo> relationNode = new BaseDTOAssemblyNode<ProgramRequirementInfo, RefStatementRelationInfo>(null);
        relationNode.setNodeData(relation);
        relationNode.setOperation(operation);

        result.getChildNodes().add(relationNode);
        result.setBusinessDTORef(progReq);
        result.setOperation(operation);
		return result;
	}

	private void disassembleLearningObjectives(ProgramRequirementInfo progReq,
			NodeOperation operation,
			BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> result,ContextInfo contextInfo) throws AssemblyException {
		/* TODO KSCM-429try {
        	
             List<BaseDTOAssemblyNode<?, ?>> loResults = cluAssemblerUtils.disassembleLos(progReq.getId(), progReq.getState(),  progReq.getLearningObjectives(), operation,contextInfo);
            if (loResults != null) {
                result.getChildNodes().addAll(loResults);
            }
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        }*/
	}

	private void disassembleCredits(CluInfo clu, ProgramRequirementInfo progReq,ContextInfo contextInfo){
		Map<String,String> attributes = null != clu.getAttributes() ? clu.getAttributes() : new HashMap<String,String>();

		if(progReq.getMinCredits() != null){
			attributes.put(ProgramAssemblerConstants.MIN_CREDITS, Integer.toString(progReq.getMinCredits()));
		}else{
			attributes.put(ProgramAssemblerConstants.MIN_CREDITS, null);
		}
		if(progReq.getMaxCredits() != null) {
			attributes.put(ProgramAssemblerConstants.MAX_CREDITS, Integer.toString(progReq.getMaxCredits()));
		}else{
			attributes.put(ProgramAssemblerConstants.MAX_CREDITS, null);
		}
			
		clu.setAttributes(attributes);
	}

	private void assembleCredits(CluInfo clu, ProgramRequirementInfo progReq){
		Map<String,String> attributes = clu.getAttributes();
		if(attributes != null){
			String minCredits = attributes.get(ProgramAssemblerConstants.MIN_CREDITS);
			String maxCredits = attributes.get(ProgramAssemblerConstants.MAX_CREDITS);
			progReq.setMinCredits(isEmpty(minCredits)?null:Integer.parseInt(minCredits));
			progReq.setMaxCredits(isEmpty(maxCredits)?null:Integer.parseInt(maxCredits));
		}
	}

	public StatementTreeViewAssembler getStatementTreeViewAssembler() {
		return statementTreeViewAssembler;
	}

	public void setStatementTreeViewAssembler(
			StatementTreeViewAssembler statementTreeViewAssembler) {
		this.statementTreeViewAssembler = statementTreeViewAssembler;
	}

	public StatementService getStatementService() {
		return statementService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

	public LearningObjectiveService getLoService() {
		return loService;
	}

	public void setLoService(LearningObjectiveService loService) {
		this.loService = loService;
	}

	public CluService getLuService() {
		return cluService;
	}

	public void setLuService(CluService cluService) {
		this.cluService = cluService;
	}

	public LoAssembler getLoAssembler() {
		return loAssembler;
	}

	public void setLoAssembler(LoAssembler loAssembler) {
		this.loAssembler = loAssembler;
	}

	public CluAssemblerUtils getCluAssemblerUtils() {
		return cluAssemblerUtils;
	}

	public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
		this.cluAssemblerUtils = cluAssemblerUtils;
	}

	public ProgramAssemblerUtils getProgramAssemblerUtils() {
		return programAssemblerUtils;
	}

	public void setProgramAssemblerUtils(ProgramAssemblerUtils programAssemblerUtils) {
		this.programAssemblerUtils = programAssemblerUtils;
	}
	
}
