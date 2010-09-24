package org.kuali.student.core.comments.ui.client.widgets.decisiontool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.HasReferenceId;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.service.CommentRpcService;
import org.kuali.student.common.ui.client.service.CommentRpcServiceAsync;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.client.widgets.table.scroll.Column;
import org.kuali.student.common.ui.client.widgets.table.scroll.DefaultTableModel;
import org.kuali.student.common.ui.client.widgets.table.scroll.Row;
import org.kuali.student.common.ui.client.widgets.table.scroll.RowComparator;
import org.kuali.student.common.ui.client.widgets.table.scroll.Table;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.MembershipInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DecisionPanel implements HasReferenceId, ToolView {

	private CommentRpcServiceAsync commentServiceAsync = GWT
			.create(CommentRpcService.class);
	private OrgRpcServiceAsync orgProposalRpcServiceAsync = GWT
			.create(OrgRpcService.class);
	private String referenceId;
	private String referenceTypeKey;
	private String referenceType;
	private String referenceState;
	private Map referenceAttributes;
	private KSLightBox commentLightBox;
	private VerticalFlowPanel contentPanel;
	private VerticalPanel titlePanel;
	private VerticalPanel scrollPanel;
	private SectionTitle title;
	private SectionTitle proposalNameHeader;
	private Table table;
	private Controller controller;
	private Enum<?> viewEnum;
	private String viewName; // View name is being used as menu item label
	private String decisionTypeKey;
	private ArrayList<String> personIds;

	private static String APPROVE_DECISION = "kuali.comment.type.workflowDecisionRationale.approve";
	private static String REJECT_DECISION = "kuali.comment.type.workflowDecisionRationale.reject";
	private static String RETURN_DECISION = "kuali.comment.type.workflowDecisionRationale.return";
	private static String ACK_DECISION = "kuali.comment.type.workflowDecisionRationale.acknowledge";
	private static String FYI_DECISION = "kuali.comment.type.workflowDecisionRationale.fyi";

	private DefaultTableModel tableModel;

	public DecisionPanel(Enum<?> viewEnum, String viewName,
			String decisionTypeKey) {
		this.viewName = viewName;
		this.viewEnum = viewEnum;
		this.decisionTypeKey = decisionTypeKey;
		init();
	}

	private void init() {
		if (commentLightBox == null) {
			commentLightBox = new KSLightBox();
		}
		KSButton closeActionButton = new KSButton(getMessage("wrapperPanelClose"));
		closeActionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				commentLightBox.hide();
			}
		});
		scrollPanel = new VerticalPanel();
		contentPanel = new VerticalFlowPanel();
		titlePanel = new VerticalPanel();
		// contentPanel.add(htmlLabel);
		table = new Table();
		table.getScrollPanel().setHeight("400px");
		table.setTableModel(tableModel);
		scrollPanel.add(table);
		scrollPanel.add(closeActionButton);
		contentPanel.add(titlePanel);
		contentPanel.add(scrollPanel);
		// commentLightBox.setWidget(titlePanel);
		commentLightBox.setWidget(contentPanel);
		commentLightBox.setSize(600, 400);

	}

	private void referesh() {
		contentPanel.clear();
		init();
		StringBuilder titleTextSb = new StringBuilder();
		titleTextSb.append(referenceAttributes.get("name"));
		proposalNameHeader = SectionTitle.generateH3Title(titleTextSb
				.toString());
		titlePanel.add(proposalNameHeader);
		title = SectionTitle.generateH1Title("Proposal Decisions");
		title.addStyleName("ks-layout-header");
		titlePanel.add(title);
		getDecisions();
	}

	private void getDecisions() {
		try {
			commentServiceAsync.getComments(referenceId, referenceTypeKey,
					new KSAsyncCallback<List<CommentInfo>>() {

						@Override
						public void onSuccess(final List<CommentInfo> result) {
							if(result!=null){
								getPersonNames(result);
							}
							else{
								initializeDecisionTable();
							}

						}

					});
		} catch (Exception e) {
			Window.alert("Failed to refresh Comments");
			GWT.log("refresh Comments Failed", e);
		}
	}

	private void getPersonNames(final List<CommentInfo> comments) {
		personIds = new ArrayList<String>();
		for (CommentInfo comment : comments) {
			if(comment.getMetaInfo().getCreateId()!=null){
				personIds.add(comment.getMetaInfo().getCreateId());
			}
			else{
				personIds.add("");
			}
		}

		orgProposalRpcServiceAsync.getNamesForPersonIds(personIds,
				new KSAsyncCallback<Map<String, MembershipInfo>>() {

					@Override
					public void onSuccess(Map<String, MembershipInfo> members) {
						redrawDecisionTable(comments, members);

					}
				});
		

		// onReadyCallback.exec(true);
	}

	private void redrawDecisionTable(List<CommentInfo> commentInfos,
			Map<String, MembershipInfo> members) {
		if (commentInfos != null) {
			int rowIndex = 0;
			for (final CommentInfo commentInfo : commentInfos) {
				int columnIndex = 0;
				if (rowIndex == 0) {
					initializeDecisionTable();
					rowIndex++;
				}
				ResultRow theRow = new ResultRow();
				if (commentInfo.getType().equals(APPROVE_DECISION)) {
					theRow.setId(commentInfo.getId());
					theRow.setValue("Decision", "Approved");
				}
				if (commentInfo.getType().equals(REJECT_DECISION)) {
					theRow.setId(commentInfo.getId());
					theRow.setValue("Decision", "Reject");
				}
				if (commentInfo.getType().equals(RETURN_DECISION)) {
					theRow.setId(commentInfo.getId());
					theRow.setValue("Decision", "Sent for Revisions");
				}
				if (commentInfo.getType().equals(ACK_DECISION)) {
					theRow.setId(commentInfo.getId());
					theRow.setValue("Decision", "Acknowledged");
				}
				if (commentInfo.getType().equals(FYI_DECISION)) {
					theRow.setId(commentInfo.getId());
					theRow.setValue("Decision", "FYI");
				}
				SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
				StringBuilder rationaleDate = new StringBuilder(dateformat
						.format(commentInfo.getMetaInfo().getCreateTime()));

				theRow.setId(commentInfo.getId());
				theRow.setValue("Date", rationaleDate.toString());

				if (members.get(commentInfo.getMetaInfo().getCreateId()) != null) {
					MembershipInfo memberInfo = members.get(commentInfo
							.getMetaInfo().getCreateId());
					StringBuilder memberName = new StringBuilder();
					memberName.append(memberInfo.getFirstName());
					memberName.append(" ");
					memberName.append(memberInfo.getLastName());
					theRow.setId(commentInfo.getId());
					theRow.setValue("Actor", memberName.toString());
				}
				theRow.setId(commentInfo.getId());
				theRow.setValue("Rationale", commentInfo.getCommentText()
						.getPlain());

				tableModel.addRow(new RationaleRow(theRow));

			}
			tableModel.fireTableDataChanged();

		}
	}

	class RationaleRow extends Row {

		ResultRow row;

		public RationaleRow(ResultRow row) {
			this.row = row;
		}

		@Override
		public Object getCellData(String columnId) {
			return row.getValue(columnId);
		}

		@Override
		public void setCellData(String columnId, Object newValue) {
			row.setValue(columnId, newValue.toString());
		}

		@Override
		public String toString() {
			return row.toString();
		}

		public ResultRow getResultRow() {
			return row;
		}
	}

	private void initializeDecisionTable() {
		ArrayList<String> tableHeaderList = new ArrayList<String>();
		tableHeaderList.add("Decision");
		tableHeaderList.add("Date");
		tableHeaderList.add("Actor");
		tableHeaderList.add("Rationale");
		tableModel = new DefaultTableModel();

		for (String tableHeader : tableHeaderList) {
			Column col1 = new Column();
			col1.setId(tableHeader);
			String header = Application.getApplicationContext().getUILabel("",
					null, null, tableHeader);
			col1.setName(header);
			if (tableHeader.equals("Rationale")) {
				col1.setWidth("200px");
			} else if (tableHeader.equals("Actor")) {
				col1.setWidth("150px");
			} else {
				col1.setWidth("100px");
			}
			if (tableHeader.equals("Date")) {
				col1.setAscendingRowComparator(new FieldAscendingRowComparator(
						"Date"));
			}
			tableModel.addColumn(col1);
		}

		table.setTableModel(tableModel);
	}

	@Override
	public String getReferenceId() {

		return this.referenceId;
	}

	@Override
	public String getReferenceState() {

		return this.referenceState;
	}

	@Override
	public String getReferenceType() {

		return this.referenceType;
	}

	@Override
	public String getReferenceTypeKey() {

		return this.referenceTypeKey;
	}

	public Map getReferenceAttributes() {
		return referenceAttributes;
	}

	@Override
	public void setReferenceId(String id) {
		this.referenceId = id;

	}

	@Override
	public void setReferenceState(String state) {
		this.referenceState = state;

	}

	@Override
	public void setReferenceType(String type) {
		this.referenceType = type;

	}

	@Override
	public void setReferenceTypeKey(String key) {
		this.referenceTypeKey = key;

	}

	public void setReferenceAttributes(Map referenceAttributes) {
		this.referenceAttributes = referenceAttributes;
	}

	@Override
	public KSImage getImage() {

		return Theme.INSTANCE.getCommonImages().getCommentIcon();
	}

	@Override
	public Enum<?> getViewEnum() {

		return viewEnum;
	}

	@Override
	public void setController(Controller controller) {

		this.controller = controller;
	}

	@Override
	public Widget asWidget() {

		return commentLightBox.getWidget();
	}

	@Override
	public boolean beforeHide() {

		return true;
	}

	@Override
	public void beforeShow(Callback<Boolean> onReadyCallback) {
		onReadyCallback.exec(true);

	}

	@Override
	public void clear() {

	}

	@Override
	public Controller getController() {

		return this.controller;
	}

	@Override
	public String getName() {

		return this.viewName;
	}

	@Override
	public void updateModel() {

	}

	@Override
	public String collectHistory(String historyStack) {

		return null;
	}

	@Override
	public void onHistoryEvent(String historyStack) {

	}

	@Override
	public void collectBreadcrumbNames(List<String> names) {

	}

	public void show() {
		controller.requestModel(ReferenceModel.class,
				new ModelRequestCallback<ReferenceModel>() {
					public void onModelReady(ReferenceModel model) {
						setReferenceId(model.getReferenceId());
						setReferenceTypeKey(model.getReferenceTypeKey());
						setReferenceType(model.getReferenceType());
						setReferenceState(model.getReferenceState());
						setReferenceAttributes(model.getReferenceAttributes());
						// refreshComments();
						referesh();
						commentLightBox.show();
					}

					public void onRequestFail(Throwable cause) {
						Window.alert(cause.toString());
					}
				});
	}
    private static String getMessage(final String messageId) {
        return Application.getApplicationContext().getMessage(messageId);
    }
}

class FieldAscendingRowComparator extends RowComparator {

	String columnId;

	FieldAscendingRowComparator(String columnId) {
		this.columnId = columnId;
	}

	@Override
	public int compare(Row row0, Row row1) {
		String id0, id1;

		id0 = (String) row0.getCellData(columnId);
		id1 = (String) row1.getCellData(columnId);

		return id0.compareTo(id1);
	}
	
}

class FieldDescendingRowComparator extends RowComparator {

	String columnId;

	FieldDescendingRowComparator(String columnId) {
		this.columnId = columnId;
	}

	@Override
	public int compare(Row row0, Row row1) {
		String id0, id1;

		id0 = (String) row0.getCellData(columnId);
		id1 = (String) row1.getCellData(columnId);

		return id1.compareTo(id0);
	}
}
