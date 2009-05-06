package org.kuali.student.lum.ui.requirements.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldTypeInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.ui.requirements.client.model.CourseRuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

/**
 * @author Zdenek Zraly
 */
public class RequirementsServiceImpl implements RequirementsService {

	LuService service;
	
    public String getNaturalLanguageForReqComponentInfo(ReqComponentInfo compInfo, String nlUsageTypeKey) throws Exception {
        
        String naturalLanguage = "";           
        
        try {             
            naturalLanguage = service.getNaturalLanguageForReqComponentInfo(compInfo, nlUsageTypeKey);            
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }                      
        
        return naturalLanguage;
    }	
	    
    public String getNaturalLanguageForLuStatementInfo(String cluId, LuStatementInfo luStatementInfo, String nlUsageTypeKey) throws Exception {
                     
        String NLStatement = "";
        try {        
            NLStatement = service.getNaturalLanguageForLuStatementInfo(cluId, luStatementInfo, nlUsageTypeKey);            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to get natural language for clu: " + cluId + " and luStamentId: " + luStatementInfo.getId(), ex);
        }; 
        
        return NLStatement;
    }
    
	public CourseRuleInfo getCourseAndRulesInfo(String cluId) throws Exception {
	  
        CourseRuleInfo courseInfo = new CourseRuleInfo();
        
        //retrieve course info
        CluInfo cluInfo;
        try {        
            cluInfo = service.getClu(cluId);                   
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to retrieve cluInfo for clu " + cluId, ex);
        }                
        courseInfo.setCourseInfo(cluInfo);	  
        courseInfo.setId(cluInfo.getId());
	    
	    //retrieve all statements associated with given course (we could retrieve only pre and co-req ?)
        List<LuStatementInfo> luStatementInfoList;
        try {        
            luStatementInfoList = service.getLuStatementsForClu(cluId);            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to retrieve LuStatements for clu " + cluId, ex);
        }
	    courseInfo.setLuStatementInfoList(luStatementInfoList); 
	    
	    return courseInfo;
	}
	
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception {
                
        List<ReqComponentTypeInfo> reqComponentTypeInfoList;
        try {        
            reqComponentTypeInfoList = service.getReqComponentTypesForLuStatementType(luStatementTypeKey);                                   
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to find Requirement Component Types based on LU Statement Type Key:" + luStatementTypeKey, ex);
        }
        
        return reqComponentTypeInfoList;
    }  
    
    public List<Result> getAllClus() throws Exception {
        
        List<Result> clus;
        List<Result> cluCodes = null;
        try {
            List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>(0);           
            clus = service.searchForResults("lu.search.clus", queryParamValues);
            if (clus != null) {
                for (Result result : clus) {
                    Result cluCodeResult = new Result();
                    List<ResultCell> cluCodeResultCells = new ArrayList<ResultCell>();
                    ResultCell cluCodeResultCell = new ResultCell();
                    cluCodes = (cluCodes == null)? new ArrayList<Result>() : cluCodes;
                    String shortName = service.getClu(result.getResultCells().get(0).getValue()).getOfficialIdentifier().getShortName();
                    shortName = (shortName == null)? "" : shortName;
                    if (shortName.trim().equals("Shortname")) continue;
                    shortName = shortName.replace(',', '/');
                    cluCodeResultCell.setKey("");
                    cluCodeResultCell.setValue(shortName);
                    cluCodeResultCells.add(cluCodeResultCell);
                    cluCodeResult.setResultCells(cluCodeResultCells);
                    cluCodes.add(cluCodeResult);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to retrieve Clus", ex);
        }

        return cluCodes;
    }      
    
    //retrieve statement based on CLU ID and STATEMENT TYPE
    public LuStatementInfo getLuStatementForCluAndStatementType(String cluId, String luStatementTypeKey) throws Exception {
        
        try {
            List<LuStatementInfo> stmtInfoList = service.getLuStatementsForClu(cluId);
            
            for (LuStatementInfo statementInfo : stmtInfoList) {
                if (statementInfo.getType().equals(luStatementTypeKey)) {
                    return statementInfo;
                }
            }
            
            System.out.println("Did not find LuStatementInfo based on cluid: " + cluId + " and luStatementTypeKey: " + luStatementTypeKey);                                       
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to find Lu Statement based on CLU id:" + cluId + " and Statement type key: " + luStatementTypeKey, ex);
        }
        
        return null;
    }
    
    public StatementVO getStatementVO(String cluId, String luStatementTypeKey) throws Exception {
        
        LuStatementInfo luStatementInfo = getLuStatementForCluAndStatementType(cluId, luStatementTypeKey);        
        
        StatementVO rootStatementVO = new StatementVO(luStatementInfo);
        if (luStatementInfo != null) {
            String error = composeStatementVO(luStatementInfo, rootStatementVO);
            if (error.isEmpty() == false) {
                throw new Exception(error + "cluId: " + cluId + ", luStatementTypeKey: " + luStatementTypeKey);            
            }
        }
        
        return rootStatementVO;        
    }      
    
    private String composeStatementVO(LuStatementInfo luStatementInfo, StatementVO statementVO) throws Exception {
        
        List<String> statementIDs = luStatementInfo.getLuStatementIds();       
        List<String> reqComponentIDs = luStatementInfo.getReqComponentIds();
        
        if ((statementIDs != null) && (reqComponentIDs != null) && (statementIDs.size() > 0) && (reqComponentIDs.size() > 0))
        {
            return "Internal error: found both Statements and Requirement Components on the same level of boolean expression";
        }
        
        if ((statementIDs != null) && (statementIDs.size() > 0)) {
            //retrieve all statements
            for (String stmtID : statementIDs) {
                LuStatementInfo tempStmtInfo;                    
                try {
                    tempStmtInfo = service.getLuStatement(stmtID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("Unable to retrieve Lu Statemetn based on luStatementID: " + stmtID, ex);
                }
                StatementVO tempStmtVO = new StatementVO(tempStmtInfo);
                composeStatementVO(tempStmtInfo, tempStmtVO);
                statementVO.addStatementVO(tempStmtVO);
            }            
        } else {
            //retrieve all req. component LEAFS
            for (String reqCompID : reqComponentIDs) {
                    
                ReqComponentInfo tempReqCompInfo;
                try {
                    tempReqCompInfo = service.getReqComponent(reqCompID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("Unable to retrieve Lu Statemetn based on reqComponentID: " + reqCompID, ex);
                }
                
                ReqComponentVO tempReqCompVO = new ReqComponentVO(tempReqCompInfo);
                
                try {
                    tempReqCompVO.setTypeDesc(service.getReqComponentType(tempReqCompInfo.getType()).getDesc());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("Unable to retrieve Lu Statemetn based on reqComponentID: " + reqCompID, ex);
                }                                

                statementVO.addReqComponentVO(tempReqCompVO);
            }               
        }        
        
        return "";
    }     
    
    public String getRuleRationale(String cluId, String luStatementTypeKey) throws Exception {        
        return "To be prepared for this course, students must have in-depth knowledge of the topics from at least on introductory education course.";
    }        
        
    
    /******************************************************************************************************************
     * 
     *                                                     GETTERS & SETTERS 
     *
     *******************************************************************************************************************/         
        
    public LuService getService() {
        return service;
    }

    public void setService(LuService service) {
        this.service = service;
    } 	
}
