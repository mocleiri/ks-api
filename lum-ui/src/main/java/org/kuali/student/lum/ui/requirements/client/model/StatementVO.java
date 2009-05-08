package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.common.ui.client.widgets.table.Token;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

public class StatementVO extends Token implements Serializable {

    private static final long serialVersionUID = 1L;
    private LuStatementInfo luStatementInfo;
    private List<ReqComponentVO> reqComponentVOs;
    private List<StatementVO> statementVOs;
    private boolean checkBoxOn;
    
    public StatementVO() {
        init();
    }
    
    public StatementVO(LuStatementInfo luStatementInfo) {
        init();
        setLuStatementInfo(luStatementInfo);
    }    
    
    private void init() {
        reqComponentVOs = new ArrayList<ReqComponentVO>();
        statementVOs = new ArrayList<StatementVO>();
    }
    
    public boolean isCheckBoxOn() {
        return checkBoxOn;
    }

    public void setCheckBoxOn(boolean checkBoxOn) {
        this.checkBoxOn = checkBoxOn;
    }

    public void printTree(Node node) {        
        int level = 0;
        ReqComponentVO content;
//        level++;
        
        if (node == null) {
            System.out.println("null node found");
            return;
        }
        
        level = node.getDistance(node);
        if (node.getUserObject() != null) {
            Token token = (Token) node.getUserObject();
            //content = (ReqComponentVO) token.value;
            System.out.println("Node level " + level + ", content: " + token.value);
        }
        else System.out.println("Node user object null, level: " + level);
        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChildAt(i);
            if (child.isLeaf()) {
                Token token = (Token) child.getUserObject();
                content = (ReqComponentVO) child.getUserObject();
                System.out.println("Node level " + child.getDistance(child) + ", content: " + content);
            } else {
                printTree(child);
            }
        }
    }
       
    /**
     * Gets the immediate parent statement of reqComponentVO
     * Example: (a and b) or (c and d) or (e)
     *     Where the statements are enclosed in brackets.  So in this example
     *     there are 3 statements.  Namely (a and b), (c and d), and (e).  
     *     There are 5 requirement components a, b, c, d, and e. If
     *     requirement component b is passed in as a parameter, then (a and b)
     *     is returned.  If e is passed in, then (e) is returned.  If d is
     *     is passed in, then (c and d) is returned.
     * @param reqComponentVO
     * @return
     */
    public StatementVO getEnclosingStatementVO(StatementVO root, ReqComponentVO reqComponentVO) {
        StatementVO result = null;
        result = doGetEnclosingStatementVO(root, reqComponentVO);
        return result;
    }
    
    private StatementVO doGetEnclosingStatementVO(StatementVO statementVO, 
            ReqComponentVO reqComponentVO) {
        StatementVO result = null;
        List<StatementVO> statementVOs =
            (statementVO == null)? null : statementVO.getStatementVOs();
        List<ReqComponentVO> reqComponentVOs =
            (statementVO == null)? null : statementVO.getReqComponentVOs();
        
        if (statementVOs != null && !statementVOs.isEmpty()) {
            for (StatementVO subStatementVO : statementVOs) {
                List<ReqComponentVO>subStatementReqComponentVOs = subStatementVO.getReqComponentVOs();
                if (subStatementReqComponentVOs.size() == 1) {
                    if (subStatementReqComponentVOs.get(0) == reqComponentVO) {
                        result = statementVO;
                        break;
                    }
                }
                else {
                    result = doGetEnclosingStatementVO(subStatementVO, reqComponentVO);
                    // found the enclosing statement exit
                    if (result != null) {
                        break;
                    }
                }
            }
        } else if (reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            for (ReqComponentVO leafReqComponentVO : reqComponentVOs) {
                if (leafReqComponentVO == reqComponentVO) {
                    result = statementVO;
                    break;
                }
            }
        }
        return result;
    }
    
    public StatementVO getParentStatementVO(StatementVO root, StatementVO nodeStatement) {
        StatementVO parentStatementVO = null;
        if (nodeStatement == root) {
            parentStatementVO = null;
        } else {
            parentStatementVO = doGetParentStatementVO(root, nodeStatement);
        }
        return parentStatementVO;
    }
    
    private StatementVO doGetParentStatementVO(StatementVO statementVO, StatementVO nodeStatement) {
        StatementVO parentStatementVO = null;
        if (statementVO.getStatementVOCount() > 0) {
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                if (childStatementVO == nodeStatement) {
                    return statementVO;
                } else {
                    parentStatementVO = doGetParentStatementVO(childStatementVO, nodeStatement);
                }
            }
        }
        return parentStatementVO;
    }
    
    /**
     * returns the number of leaf requirement componentVO of the this statement
     * excluding those compound sub statements.
     * e.g. if this method is called on S1, the return value will be 2. 
     * <pre>
     *                    S1:OR
     *           S2:OR    S3:OR   S4:AND
     *             a        b     c d e
     * </pre>
     * @return
     */
    public int getImmediateChildReqComponentCount() {
        int result = 0;
        if (reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            result = reqComponentVOs.size();
        } else if (statementVOs != null && !statementVOs.isEmpty()) {
            for (StatementVO subStatementVO : statementVOs) {
                List<ReqComponentVO> subStatementReqComponentVOs =
                    subStatementVO.getReqComponentVOs();
                if (subStatementReqComponentVOs != null &&
                        subStatementReqComponentVOs.size() == 1) {
                    result++;
                }
            }
        }
        return result;
    }
    
    private void validate() {
        if (statementVOs != null && !statementVOs.isEmpty() &&
                reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            throw new java.lang.IllegalStateException(
                    "Requirement components and statements can not exist together in a statement");
        }
        checkDuplicateRC(this, new ArrayList<ReqComponentVO>());
    }
    
    private void checkDuplicateRC(StatementVO statementVO, List<ReqComponentVO> seenRCs) {
        if (statementVO.getStatementVOs() != null &&
                !statementVO.getStatementVOs().isEmpty()) {
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                checkDuplicateRC(childStatementVO, seenRCs);
            }
        } else {
            if (statementVO.getReqComponentVOs() != null &&
                    !statementVO.getReqComponentVOs().isEmpty()) {
                for (ReqComponentVO childReqComponent : statementVO.getReqComponentVOs()) {
                    if (seenRCs.contains(childReqComponent)) {
                        throw new java.lang.IllegalStateException(
                                "statement and sub statements cannot have duplicated components");
                    } else {
                        seenRCs.add(childReqComponent);
                    }
                }
            }
        }
    }
    
    public void addStatementVO(StatementVO statementVO) {
        doAddStatementVO(statementVO);
        validate();
    }
    
    private void doAddStatementVO(StatementVO statementVO) {
        // if there are currently requirement components in this StatementVO
        // move all the existing requirement components into separate wrapping 
        // StatementVOs
        if (reqComponentVOs != null && !reqComponentVOs.isEmpty()) {
            List<ReqComponentVO> tempReqComponentVOs = 
                new ArrayList<ReqComponentVO>(reqComponentVOs);
            for (ReqComponentVO currReqComponentVO : tempReqComponentVOs) {
                StatementVO wrapStatementVO = new StatementVO(luStatementInfo);
                wrapStatementVO.addReqComponentVO(currReqComponentVO);
                reqComponentVOs.remove(currReqComponentVO);
                statementVOs.add(wrapStatementVO);
            }
        }
        statementVOs.add(statementVO);
    }
    
    public void addReqComponentVO(ReqComponentVO reqComponentVO) {
        doAddReqComponentVO(reqComponentVO);
        validate();
    }
    
    private void doAddReqComponentVO(ReqComponentVO reqComponentVO) {
        // if there are sub statements in this statement already
        // add a new sub statement with the same operator as this statement
        // and a the requirement component
        if (statementVOs != null && !statementVOs.isEmpty()) {
            LuStatementInfo newLuStatementInfo = new LuStatementInfo();
            StatementVO newStatementVO = new StatementVO();
            newLuStatementInfo.setOperator(luStatementInfo.getOperator());
            newStatementVO.setLuStatementInfo(newLuStatementInfo);
            newStatementVO.getReqComponentVOs().add(reqComponentVO);
            statementVOs.add(newStatementVO);
        } else {
            reqComponentVOs.add(reqComponentVO);
        }
    }
    
    public void removeStatementVO(StatementVO statementVO) {
        statementVOs.remove(statementVO);
        validate();
    }   

    public void removeReqComponentVO(ReqComponentVO reqComponentVO) {
        doRemoveReqComponentVO(reqComponentVO);
        validate();
    }
    
    private void doRemoveReqComponentVO(ReqComponentVO reqComponentVO) {
        if (statementVOs != null && !statementVOs.isEmpty()) {
            List<StatementVO> tempStatementVOs = new ArrayList<StatementVO>(statementVOs);
            for (StatementVO subStatementVO : tempStatementVOs) {
                List<ReqComponentVO> subStatementReqComponentVOs = 
                    (subStatementVO == null)? null : subStatementVO.getReqComponentVOs();
                if (subStatementReqComponentVOs != null &&
                        subStatementReqComponentVOs.size() == 1 &&
                        subStatementReqComponentVOs.get(0) == reqComponentVO) {
                    subStatementVO.removeReqComponentVO(reqComponentVO);
                    // cleans up empty statements with neither statements nor requirement components.
                    statementVOs.remove(subStatementVO);
                }
            }
        } else { 
            reqComponentVOs.remove(reqComponentVO);
        }
    }
    
    public LuStatementInfo getLuStatementInfo() {
        return luStatementInfo;
    }

    public void setLuStatementInfo(LuStatementInfo luStatementInfo) {
        this.luStatementInfo = luStatementInfo;
    }    
    
    public List<ReqComponentVO> getReqComponentVOs() {
        return reqComponentVOs;
    }

    public List<StatementVO> getStatementVOs() {
        return statementVOs;
    }
    
    public void clearStatementAndReqComponents() {
        if (statementVOs != null) {
            statementVOs.clear();
        }
        if (reqComponentVOs != null) {
            reqComponentVOs.clear();
        }
    }
    
    public void shiftReqComponent(String shiftType, 
            final ReqComponentVO reqComponentVO) {
        if (statementVOs != null && !statementVOs.isEmpty()) {
            // the statementVO that wraps the reqComponentVO
            StatementVO reqComponentVOWrap = null;
            for (StatementVO currStatementVO : statementVOs) {
                List<ReqComponentVO> currReqComponentVOs =
                    (currStatementVO == null)? null : 
                        currStatementVO.getReqComponentVOs();
                if (currReqComponentVOs != null &&
                        currReqComponentVOs.size() == 1 &&
                        currReqComponentVOs.get(0) == reqComponentVO) {
                    reqComponentVOWrap = currStatementVO;
                }
            }
            if (reqComponentVOWrap != null) {
                swapElement(statementVOs, reqComponentVOWrap, shiftType);
            }
        } else if (reqComponentVOs != null && reqComponentVOs.size() > 1) {
            swapElement(reqComponentVOs, reqComponentVO, shiftType);
        }
    }
    
    private <T> void swapElement(List<T> elements, T element, String direction) {
        int elementIndex = 0;
        if (elements != null && elements.size() > 1) {
            for (T currElement :
                elements) {
                if (direction != null && direction.equals("RIGHT")) {
                    if (currElement == element &&
                            elementIndex + 1 < elements.size()) {
                        Collections.swap(elements, elementIndex,
                                elementIndex + 1);
                        break;
                    }
                } else if (direction != null && direction.equals("LEFT")) {
                    if (currElement == element &&
                            elementIndex > 0) {
                        Collections.swap(elements, elementIndex,
                                elementIndex - 1);
                        break;
                    }
                }
                elementIndex++;
            }
        }
    }

    public Node getTree() {        
        Node node = new Node();
        addChildrenNodes(node, this);
        //printTree(node);
        return node;
    }
    
    private void addChildrenNodes(Node node, StatementVO statementVO) {
        List<StatementVO> statementVOs = statementVO.getStatementVOs();
        List<ReqComponentVO> reqComponentVOs = statementVO.getReqComponentVOs();
        
        if (statementVOs != null) {
//            node.setUserObject(statementVO);
            setOperatorNode(node, statementVO);
            for (int i = 0; i < statementVOs.size(); i++) {
                StatementVO childStatementVO = statementVOs.get(i);
                Node childNode = new Node();
                node.addNode(childNode);
                addChildrenNodes(childNode, childStatementVO);
            }
        }

        if (reqComponentVOs != null) {
            //System.out.println("VO size: " + reqComponentVOs.size());
            for (int rcIndex = 0, rcCount = reqComponentVOs.size(); rcIndex < rcCount; rcIndex++) {
                ReqComponentVO childReqComponentVO = reqComponentVOs.get(rcIndex);
                if (rcCount > 1) {
                    //System.out.println("TESTING 00---> " + childReqComponentVO.getReqComponentInfo().getDesc() + " ### " + childReqComponentVO.getReqComponentInfo().getReqCompField().size());
                    node.addNode(new Node(childReqComponentVO));
                } else {
                    //System.out.println("TESTING 0---> " + childReqComponentVO.getReqComponentInfo().getReqCompField().size());
                    node.setUserObject(childReqComponentVO);
                }
            }
        }        
    }
    
    private void setOperatorNode(Node node, StatementVO statementVO) {
        if (statementVO.getLuStatementInfo() != null &&
                statementVO.getLuStatementInfo().getOperator() ==
                    StatementOperatorTypeKey.AND) {
            statementVO.type = Token.And;
            statementVO.value = "and";
            node.setUserObject(statementVO);
        } else if (statementVO.getLuStatementInfo() != null &&
                statementVO.getLuStatementInfo().getOperator() ==
                    StatementOperatorTypeKey.OR) {
            statementVO.type = Token.Or;
            statementVO.value = "or";
            node.setUserObject(statementVO);
        }
    }
    
    public List<StatementVO> getSelectedStatementVOs() {
        List<StatementVO> selectedStatementVOs = new ArrayList<StatementVO>();
        return doGetSelectedStatmentVOs(this, selectedStatementVOs);
    }
    
    private List<StatementVO> doGetSelectedStatmentVOs(StatementVO statementVO,
            List<StatementVO> selectedStatementVOs) {
        List<StatementVO> childrenStatementVOs = statementVO.getStatementVOs();
        if (statementVO.isCheckBoxOn()) {
            selectedStatementVOs.add(statementVO);
        }
        // check children
        if (childrenStatementVOs != null && !childrenStatementVOs.isEmpty()) {
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                doGetSelectedStatmentVOs(childStatementVO, selectedStatementVOs);
            }
        }
        return selectedStatementVOs;
    }
    
    public List<ReqComponentVO> getSelectedReqComponentVOs() {
        List<ReqComponentVO> selectedReqComponentVOs = new ArrayList<ReqComponentVO>();
        return doGetSelectedReqComponentVOs(this, selectedReqComponentVOs);
    }
    
    private List<ReqComponentVO> doGetSelectedReqComponentVOs(StatementVO statementVO,
            List<ReqComponentVO> selectedReqComponentVOs) {
        List<ReqComponentVO> childernReqComponentVOs = statementVO.getReqComponentVOs();
        List<StatementVO> childrenStatementVOs = statementVO.getStatementVOs();
        if (childernReqComponentVOs != null && !childernReqComponentVOs.isEmpty()) {
            for (ReqComponentVO childReqComponentVO : childernReqComponentVOs) {
                if (childReqComponentVO.isCheckBoxOn()) {
                    selectedReqComponentVOs.add(childReqComponentVO);
                }
            }
        }
        if (childrenStatementVOs != null && !childrenStatementVOs.isEmpty()) {
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                doGetSelectedReqComponentVOs(childStatementVO, selectedReqComponentVOs);
            }
        }
        return selectedReqComponentVOs;
    }
    
    /**
     * 
     * @param statementVO
     * @return
     */
    public int getNestingDepth() {
        return doGetNestingDepth(this);
    }
    
    private int doGetNestingDepth(StatementVO statementVO) {
        int depth = 0;
        List<StatementVO> statementVOs = getStatementVOs();
        if (this == statementVO) {
            return depth;
        }
        if (statementVOs != null && !statementVOs.isEmpty()) {
            for (StatementVO childStatementVO : statementVOs) {
                depth = depth + doGetNestingDepth(childStatementVO);
            }
        }
        return depth;
    }
    
    @Override
    public String toString() {
        StringBuilder sbResult = new StringBuilder();
        sbResult.append(value);
        return sbResult.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
    
    public int getReqComponentVOCount() {
        return (reqComponentVOs == null)? 0 : reqComponentVOs.size();
    }
    
    public int getStatementVOCount() {
        return (statementVOs == null)? 0 : statementVOs.size();
    }
    
    public int getChildCount() {
        return getReqComponentVOCount() + getStatementVOCount();
    }
    
    public boolean isWrapperStatementVO() {
        boolean result = false;
        if (getReqComponentVOCount() == 1 && getStatementVOCount() == 0) {
            result = true;
        }
        return result;
    }
    
    /**
     * looks for statements where all sub statements are wrapped statements and
     * unwrap them
     */
    public void unwrapRCs() {
        doUnwrapRCs(this);
    }
    
    private List<ReqComponentVO> doUnwrapRCs(StatementVO statementVO) {
        int numWrappedS = 0;
        List<ReqComponentVO> wrappedRCs = null;
        List<ReqComponentVO> runningWrappedRCs = new ArrayList<ReqComponentVO>();
        if (statementVO.getStatementVOCount() > 0) {
            System.out.println("doUnwrapRCs statementVO: " + statementVO);
            System.out.println("doUnwrapRCs statementVO.getStatementVOs(): " + statementVO.getStatementVOs());
            for (StatementVO subS : statementVO.getStatementVOs()) {
                if (subS.isWrapperStatementVO()) {
                    runningWrappedRCs.add(subS.getReqComponentVOs().get(0));
                    numWrappedS++;
                }
//                else {
//                    List<ReqComponentVO> subSUwrappableRCs = doUnwrapRCs(subS);
//                    if (subSUwrappableRCs != null && !subSUwrappableRCs.isEmpty()) {
//                        List<StatementVO> tempStatementVOs =
//                            new ArrayList<StatementVO>(statementVO.getSelectedStatementVOs());
//                        for (StatementVO subStatementVO : tempStatementVOs) {
//                            statementVO.removeStatementVO(subStatementVO);
//                        }
//                        for (ReqComponentVO rc : subSUwrappableRCs) {
//                            statementVO.addReqComponentVO(rc);
//                        }
//                    }
//                }
            }
            if (numWrappedS == statementVO.getChildCount()) {
                wrappedRCs = runningWrappedRCs;
            } else {
                wrappedRCs = null;
            }
        }
        return wrappedRCs;
    }
    
    /**
     * goes through the tree recursively to delete statements with no child.
     */
    public void cleanupStatementVO() {
        doCleanupStatementVO(this, null);
    }
    
    private void doCleanupStatementVO(StatementVO statementVO, StatementVO parent) {
        if (statementVO.getStatementVOCount() == 0 &&
                statementVO.getReqComponentVOCount() == 0) {
            if (parent != null) {
                parent.removeStatementVO(statementVO);
            }
        } else if (statementVO.getStatementVOCount() > 0) {
            for (StatementVO subS : statementVO.getStatementVOs()) {
                doCleanupStatementVO(subS, statementVO);
            }
        }
    }

    public String getPrintableStatement() {
        StringBuilder sbResult = null;
        Map<String, ReqComponentVO> reqComponentMap = new HashMap<String, ReqComponentVO>();
        sbResult = doPrintStatement(new StringBuilder(),
                this, reqComponentMap);
        return sbResult.toString();
    }
    
    private StringBuilder doPrintStatement(StringBuilder inSbResult, 
            StatementVO statementVO,
            Map<String, ReqComponentVO> reqComponentMap) {
        List<StatementVO> currStatementVOs = (statementVO == null)? null :
            statementVO.getStatementVOs();
        List<ReqComponentVO> currReqComponentVOs = (statementVO == null)? null :
            statementVO.getReqComponentVOs();
        if (currStatementVOs != null && !currStatementVOs.isEmpty()) {
            int statementCounter = 0;
            for (StatementVO childStatementVO : statementVO.getStatementVOs()) {
                if (statementCounter > 0) {
                    StatementOperatorTypeKey operator =
                        (statementVO == null ||
                                statementVO.getLuStatementInfo() == null)? null :
                                    statementVO.getLuStatementInfo().getOperator();
                    inSbResult.append(" " + operator + " ");
                }
                inSbResult.append("(");
                inSbResult.append(doPrintStatement(new StringBuilder(), childStatementVO, reqComponentMap).toString());
                inSbResult.append(")");
                statementCounter++;
            }
        } else if (currReqComponentVOs != null && !currReqComponentVOs.isEmpty()) {
            int rcCounter = 0;
            for (ReqComponentVO childReqComponentInfo : currReqComponentVOs) {
                String rcId = Integer.toString(reqComponentMap.size());
                if (rcCounter > 0) {
                    StatementOperatorTypeKey operator =
                        (statementVO == null ||
                                statementVO.getLuStatementInfo() == null)? null :
                                    statementVO.getLuStatementInfo().getOperator();
                    inSbResult.append(" " + operator + " ");
                }
                reqComponentMap.put(rcId,
                        childReqComponentInfo);
                inSbResult.append(rcId);
                rcCounter++;
            }
        }
        return inSbResult;
    }

}
