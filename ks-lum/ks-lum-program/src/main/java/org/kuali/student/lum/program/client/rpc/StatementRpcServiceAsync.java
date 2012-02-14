package org.kuali.student.lum.program.client.rpc;

import java.util.List;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.lum.lu.dto.CluInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StatementRpcServiceAsync extends BaseRpcServiceAsync {
    public void getStatementTypesForStatementTypeForCourse(String statementTypeKey, AsyncCallback<List<StatementTypeInfo>> callback, ContextInfo contextInfo);
    public void getStatementTypesForStatementType(String statementTypeKey, AsyncCallback<List<StatementTypeInfo>> callback, ContextInfo contextInfo);
    public void getReqComponentTypesForStatementType(String luStatementTypeKey, AsyncCallback<List<ReqComponentTypeInfo>> callback, ContextInfo contextInfo);
    public void translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language, AsyncCallback<String> callback, ContextInfo contextInfo);
    public void translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey, String language, AsyncCallback<String> callback, ContextInfo contextInfo);
	public void translateReqComponentToNLs(ReqComponentInfoUi reqComp, String[] nlUsageTypeKeys, String temlateLanguage, AsyncCallback<List<String>> callback, ContextInfo contextInfo);
    public void getClu(String cluId, AsyncCallback<CluInfo> callback, ContextInfo contextInfo);
    public void getCurrentVersion(String refObjectTypeURI, String refObjectId, AsyncCallback<VersionDisplayInfo> callback, ContextInfo contextInfo);
}
