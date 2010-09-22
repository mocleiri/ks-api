package org.kuali.student.lum.lu.ui.course.client.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.lum.common.client.lo.CategoryManagement;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.main.client.configuration.CurriculumHomeConfigurer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CurriculumHomeView extends ViewComposite{
	
	private final SpanPanel container = new SpanPanel();
	MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
	CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
	CurriculumHomeConfigurer configurer = GWT.create(CurriculumHomeConfigurer.class);
	
	public CurriculumHomeView(Controller controller, Enum<?> viewType) {
		super(controller, "", viewType);
		this.initWidget(container);
		setup();
	}
	
	protected void setup(){
        metadataServiceAsync.getMetadata("search", "", "", new KSAsyncCallback<Metadata>() {
            @Override
            public void handleFailure(Throwable caught) {
            	container.add(configurer.configure(null));
                throw new RuntimeException("Could not verify authorization: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Metadata metadata) {
            	container.add(configurer.configure(metadata));
            }
        });       
	}
	
	
	@Deprecated
    private void addIfPermitted(PermissionType permType, String searchType) {
    	addIfPermitted(permType, searchType, new HashMap<String,String>());
    }
    
    @Deprecated
    private void addIfPermitted(PermissionType permType, final String searchType, Map<String,String> permissionAttributes) {
        cluProposalRpcServiceAsync.isAuthorized(permType, permissionAttributes, new KSAsyncCallback<Boolean>() {
            @Override
            public void handleFailure(Throwable caught) {
                throw new RuntimeException("Could not verify authorization: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Boolean result) {
                //NOTE: quick hack; does not matter because this all goes away with new Curriculum Management home screen
                if(result) {
/*                    if (searchType.equals("Courses")) {
                        addCourseSearchWindow(); 
                    } else if (searchType.equals("Majors")){
                        addMajorSearchWindow();
                    } else {
                        addProposalSearchWindow();
                    }*/
                }                
            }
        });
    }
	

}
