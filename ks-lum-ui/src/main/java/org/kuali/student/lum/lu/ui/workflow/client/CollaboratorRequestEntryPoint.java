package org.kuali.student.lum.lu.ui.workflow.client;

import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CollaboratorRequestEntryPoint implements EntryPoint{
	
    private CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
	private ServerPropertiesRpcServiceAsync serverPropertiesRpcServiceAsync = GWT.create(ServerPropertiesRpcService.class);
	
    private VerticalPanel rootPanel = new VerticalPanel();

    String docId;
    String backdoorId;
    
	private KSButton wfApproveButton = new KSButton("Approve", new ClickHandler(){
		public void onClick(ClickEvent event) {
			cluProposalRpcServiceAsync.approveDocument(docId, new AsyncCallback<Boolean>(){
				public void onFailure(
						Throwable caught) {
					Window.alert("Error approving Proposal");
				}
				public void onSuccess(
						Boolean result) {
					if(result){
						Window.alert("Proposal was approved");
						redirect();
					}else{
						Window.alert("Error approving Proposal");
					}
				}
			});
		}        
	});

	private KSButton  wfDisApproveButton = new KSButton("Disapprove", new ClickHandler(){
        public void onClick(ClickEvent event) {
			cluProposalRpcServiceAsync.disapproveDocument(docId, new AsyncCallback<Boolean>(){
				public void onFailure(
						Throwable caught) {
					Window.alert("Error disapproving Proposal");
				}
				public void onSuccess(
						Boolean result) {
					if(result){
						Window.alert("Proposal was disapproved");
						redirect();
					}else{
						Window.alert("Error disapproving Proposal");
					}
				}
				
			});
        }        
    });
    
    @Override
	public void onModuleLoad() {
    	rootPanel.add(new KSLabel("You've been invited to collaborate"));
        docId = Window.Location.getParameter("docId");
        backdoorId = Window.Location.getParameter("backdoorId");
        
        if(docId!=null){
            if(backdoorId!=null){
                cluProposalRpcServiceAsync.loginBackdoor(backdoorId, new AsyncCallback<Boolean>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(Boolean result) {
                        if(!result){
                            Window.alert("Error with backdoor login");
                        }
                        showView();
                    }
                });
            }else{
                showView();
            }
        }
		RootPanel.get().add(rootPanel);		
	}
    
	private void showView() {
		rootPanel.add(wfApproveButton);
		rootPanel.add(wfDisApproveButton);
	}

	private void redirect() {
		// TODO Auto-generated method stub
		serverPropertiesRpcServiceAsync.get("ks.lum.MainEntryPoint", new AsyncCallback<String>(){
			public void onFailure(Throwable caught) {
				Window.alert("errorgetting system property");
				Window.Location.assign("http://localhost:8080/ks-lum-web/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp");
			}
			public void onSuccess(String result) {
				Window.Location.assign(result);				
			}
		});
		
	}
}
