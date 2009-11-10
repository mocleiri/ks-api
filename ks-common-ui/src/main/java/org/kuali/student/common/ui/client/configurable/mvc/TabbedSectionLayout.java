package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.event.ValidateResultHandler;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.HasActionState.ActionState;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;
import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSListMenuImpl;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel.TabPosition;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabbedSectionLayout extends LayoutController implements ConfigurableLayout{

	//FIXME: Better way to manage hierarchy, ordering, and handle to views
	private final Map<String, KSMenuItemData> menuHierarchyMap = new HashMap<String, KSMenuItemData>();

	private Map<String, TabLayout> tabLayoutMap = new HashMap<String, TabLayout>();
	
	
	private Map<String, String> sectionNameTabMap = new HashMap<String, String>();
	
    private KSLightBox startSectionWindow = new KSLightBox();
    private SectionView startSectionView;
    
	private boolean loaded = false;
	
	Enum<?> defaultView = null;
	
	private Class<? extends ModelDTO> modelDTOType;
	
	private KSTabPanel tabPanel = new KSTabPanel();
	private KSTitleContainerImpl container = new KSTitleContainerImpl();
	
	private class TabLayout extends Composite{
		HorizontalPanel layout = new HorizontalPanel();
		SimplePanel content = new SimplePanel();
		VerticalPanel contentLayout = new VerticalPanel();
		KSListMenuImpl menu = new KSListMenuImpl();
		//KSBasicMenu menu = new KSBasicMenu();
		private int currSectionIdx = 0;
		protected final ArrayList<View> orderedSectionViews = new ArrayList<View>();
		private final HorizontalPanel sectionButtonPanel = new HorizontalPanel();
		private final ArrayList<KSMenuItemData> sectionMenuItems = new ArrayList<KSMenuItemData>();
		private final List<KSMenuItemData> topLevelMenuItems = new ArrayList<KSMenuItemData>();
		Map<String, KSListPanel> sectionMap = new HashMap<String, KSListPanel>();
		boolean menuAdded = false;
		boolean shown = false;
		boolean renderedOnce = false;
		Enum<?> tabDefaultView = null;
		
		private KSButton nextButton = new KSButton("Save & Continue", new ClickHandler(){
	        public void onClick(final ClickEvent event) {
                
                final SaveActionEvent saveActionEvent = new SaveActionEvent();
                saveActionEvent.setAcknowledgeRequired(false);
                saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
                    public void onActionComplete(ActionEvent action) {
                        int nextSectionIndex = currSectionIdx + 1;
                        // FIXME this is not safe for all sorts of reasons, do not call handlers directly like this.
                        sectionMenuItems.get(nextSectionIndex).getClickHandler().onClick(event);
                    }                    
                });
                
                fireApplicationEvent(saveActionEvent);	            	            
	        }	    
	    }
		);
		
		public TabLayout(){
			sectionButtonPanel.add(nextButton);
			menu.setItems(topLevelMenuItems);
			contentLayout.add(content);
			contentLayout.add(sectionButtonPanel);
			layout.add(contentLayout);
			this.initWidget(layout);
		}
		
		public void init(){
			menu.setItems(topLevelMenuItems);
		}
		
		public void setContent(Widget content){
			this.content.setWidget(content);
		}

		public void addMenuItem(String[] hierarchy, final SectionView section) {
			String path = "";
			KSMenuItemData current = null;
			for (int i=1; i<hierarchy.length; i++) {
				path = path + "/" + hierarchy[i];
				KSMenuItemData item = menuHierarchyMap.get(path);
				if (item == null) {
					item = new KSMenuItemData(hierarchy[i]);
					if (current == null) {
						topLevelMenuItems.add(item);
						current = item;
					} else {
						current.addSubItem(item);
						current = item;
					}
					menuHierarchyMap.put(path, item);
				} else {
					current = item;
				}
			}
			
			final KSMenuItemData sectionItem = new KSMenuItemData(section.getName());
			current.addSubItem(sectionItem);
			sectionMenuItems.add(sectionItem);
			orderedSectionViews.add(section);
			
			sectionItem.setClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
				    
				    int newMenuItemIdx = sectionMenuItems.indexOf(sectionItem);
				    if (currSectionIdx != newMenuItemIdx){
	                    currSectionIdx = newMenuItemIdx;
	                    sectionButtonPanel.setVisible(true);
	    			    showView(section.getViewEnum());
				    }
				}
			});
			
			if(!menuAdded){
				layout.insert(menu, 0);
				menuAdded = true;
			}
			
			if (tabDefaultView == null){
			    tabDefaultView = section.getViewEnum();
			}
		}

		public void renderView(View view) {
			renderedOnce = true;
			content.setWidget((Widget)view);
			if(menuAdded){
			    if (currSectionIdx == sectionMenuItems.size() - 1){
			        nextButton.setVisible(false);
			    } else {
			        nextButton.setVisible(true);
			    }
		        currSectionIdx = orderedSectionViews.indexOf(view);
		        if(currSectionIdx == -1){
		            return;
		        }
				sectionMenuItems.get(currSectionIdx).setSelected(true);
			}
			else{
				nextButton.setVisible(false);
			}
		}

		public void removeContent() {
			content.clear();
		}

		public void addButton(KSButton button) {
			sectionButtonPanel.add(button);
		}

		public void clear() {
	        for (View view:orderedSectionViews){
	            view.clear();            
	        }
			
		}

		public void updateModel() {
            for(View sectionView : orderedSectionViews){
            	sectionView.updateModel();
            }
			
		}

		public void beforeShow() {
			if(!shown){
				showView(tabDefaultView);
			}
		}
	}
	
	private void init(){
    	for(TabLayout layout: tabLayoutMap.values()){
			layout.init();
		}
		addApplicationEventHandler(ValidateResultEvent.TYPE, new ValidateResultHandler() {
            @Override
            public void onValidateResult(ValidateResultEvent event) {
               List<ValidationResultContainer> list = event.getValidationResult();
               startSectionView.processValidationResults(list);
            }
        });
	}
	
	public TabbedSectionLayout(){
		container.setContent(tabPanel);
		container.setTitle("New Course Proposal");
		container.setStatus("Unsubmitted Proposal");
		container.setLinkText("Workflow Overview");
		super.initWidget(container);
	}
	
	@Override
	protected <V extends Enum<?>> View getView(V viewType) {
		return sectionViewMap.get(viewType.name());
	}

	@Override
	public Class<? extends Enum<?>> getViewsEnum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void hideView(View view) {
		//Does nothing: no need to hide, it view is always replaced in this layout
/*		String tabName = sectionNameTabMap.get(view.getName());
		TabLayout layout = tabLayoutMap.get(tabName);
		layout.removeContent();*/
	}

	@Override
	protected void renderView(View view) {
		String tabName = sectionNameTabMap.get(view.getName());
		if(!(tabPanel.getSelectedTabKey().equals(tabName))){
			tabPanel.selectTab(tabName);
		}
		TabLayout layout = tabLayoutMap.get(tabName);
		
		layout.renderView(view);
		view.getName();
	}

	@Override
	public void showDefaultView() {
        if (!loaded){
            init();
            loaded = true;
        }
                   
        showView(defaultView);
	}

	@Override
	public void addSection(String[] hierarchy, final SectionView section) {
		String tabKey = hierarchy[0];
		
		final TabLayout layout;
		if(!(tabPanel.hasTabKey(tabKey))){
			layout = new TabLayout();
			tabLayoutMap.put(tabKey, layout);
			tabPanel.addTab(tabKey, tabKey, layout);
		}
		else{
			layout = tabLayoutMap.get(tabKey);
		}
		
		if(hierarchy.length > 1){
			layout.addMenuItem(hierarchy, section);
		}
		else{
			layout.renderView(section);
		}

		
		sectionNameTabMap.put(section.getName(), tabKey);
		sectionViewMap.put(section.getViewEnum().name(), section);
		section.setController(this);
		
		if (defaultView == null){
		    defaultView = section.getViewEnum();
		}
		
		tabPanel.addTabCustomCallback(tabKey, new Callback<String>(){

			@Override
			public void exec(String result) {
				layout.beforeShow();
			}
			
		});
		
	}

	@Override
	public void addTool(final ToolView tool) {
		String tabKey = tool.getName();
		
		TabLayout layout;
		if(!(tabPanel.hasTabKey(tabKey))){
			layout = new TabLayout();
			tabLayoutMap.put(tabKey, layout);
			tabPanel.addTab(tabKey, tabKey, layout, TabPosition.RIGHT);
		}
		else{
			layout = tabLayoutMap.get(tabKey);
		}
		
		tabPanel.addTabCustomCallback(tabKey, new Callback<String>(){

			@Override
			public void exec(String result) {
				//layout.beforeShow();
				showView(tool.getViewEnum());
			}
			
		});
		
		//layout.renderView(tool);
		sectionNameTabMap.put(tool.getName(), tabKey);
		sectionViewMap.put(tool.getViewEnum().name(), tool);
        tool.setController(this);
		
	}
	
    public void showStartSection(){
        startSectionView.beforeShow();
        startSectionWindow.show();
    }
    
    public SectionView getStartSection(){
        return startSectionView;
    }
    
	public void addStartSection(final SectionView section){
	    startSectionView = section;
	    
	    HorizontalPanel buttonPanel = new HorizontalPanel();
	    
	    VerticalPanel panel = new VerticalPanel();        	    
	    panel.add(section);	    
	    buttonPanel.add(new KSButton("Save",new ClickHandler(){
            public void onClick(ClickEvent event) {
                section.updateModel();
                SaveActionEvent saveActionEvent = new SaveActionEvent();
                
                saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
                    public void onActionComplete(ActionEvent action) {
                        startSectionWindow.hide();
                    }                    
                });
                
                fireApplicationEvent(saveActionEvent);
            }	        
	    }));
	    buttonPanel.add(new KSButton("Cancel", new ClickHandler(){
            public void onClick(ClickEvent event) {
                startSectionWindow.hide();
            }	        
	    }));

	    panel.add(buttonPanel);
        section.setController(this);
	    startSectionWindow.setWidget(panel);
	}
    
    public Class<? extends ModelDTO> getModelDTOType() {
        return modelDTOType;
    }

    public void setModelDTOType(Class<? extends ModelDTO> modelDTOType) {
        this.modelDTOType = modelDTOType;
    }
    
	public void addButton(KSButton button){
		for(TabLayout layout: tabLayoutMap.values()){
			layout.addButton(button);
		}
	        
	}
	    
    public void clear(){
    	
    	for(TabLayout layout: tabLayoutMap.values()){
			layout.clear();
		}

        
    }

    public void updateModel(){
    	for(TabLayout layout: tabLayoutMap.values()){
			layout.updateModel();
		}
    }
}
