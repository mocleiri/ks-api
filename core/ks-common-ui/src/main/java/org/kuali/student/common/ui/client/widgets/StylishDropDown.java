package org.kuali.student.common.ui.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.MenuChangeEvent;
import org.kuali.student.common.ui.client.widgets.menus.MenuEventHandler;
import org.kuali.student.common.ui.client.widgets.menus.MenuSelectEvent;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu.MenuImageLocation;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSListMenuImpl;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class StylishDropDown extends Composite{
	
	private ClickablePanel namePanel = new ClickablePanel();
	private boolean showSelectedItem = false;
	private SimplePanel container = new SimplePanel();
	private PopupPanel menuPanel = new PopupPanel();
	private KSListMenuImpl menu = new KSListMenuImpl();
	private HorizontalPanel layout = new HorizontalPanel();
	private KSLabel titleLabel = new KSLabel();
	private KSImage titleImage = Theme.INSTANCE.getCommonImages().getSpacer();
	private HorizontalPanel titleLayout = new HorizontalPanel();
	private KSImage defaultArrow = Theme.INSTANCE.getCommonImages().getDropDownIconBlack();
	private boolean mouseOver = false;
	private MenuImageLocation imgLoc = MenuImageLocation.RIGHT;
	
	private ClickHandler panelHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			if(!menuPanel.isShowing()){
				StylishDropDown.this.showMenu();
			}
			else{
				StylishDropDown.this.hideMenu();
			}
			
		}
		
	};
	
	private MenuEventHandler menuHandler = new MenuEventHandler(){

		@Override
		public void onChange(MenuChangeEvent e) {
			//Not needed?
			
		}

		@Override
		public void onSelect(MenuSelectEvent e) {
			KSMenuItemData i = (KSMenuItemData) e.getSource();
			StylishDropDown.this.hideMenu();
			if(showSelectedItem){
				titleLayout.clear();
				titleLabel.setText(i.getLabel());
				titleLayout.add(titleLabel);
				if(i.getShownIcon() != null){
					titleLayout.add(i.getShownIcon());
				}
			}
			
		}
	};
	
	public StylishDropDown(String title){
		titleLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		titleLabel.setText(title);
		titleLayout.add(titleLabel);
		titleLayout.add(titleImage);
		init();
	}
	
	public StylishDropDown(String title, KSImage image, MenuImageLocation imgLoc){
		titleLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		titleLabel.setText(title);
		titleImage = image;
		titleLayout.add(titleLabel);
		if(imgLoc == MenuImageLocation.RIGHT){
			titleLayout.add(titleImage);
		}
		else{
			titleLayout.insert(titleImage, 0);
		}
		menu.setImageLocation(imgLoc);
		init();
	}
	
	public StylishDropDown(Widget widget){
		titleLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		titleLayout.add(widget);
		init();
	}
	
	private void init(){
		layout.clear();
		layout.setWidth("100%");
		layout.add(titleLayout);
		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		layout.add(defaultArrow);

		namePanel.setWidget(layout);
		menu.addGlobalMenuItemSelectCallback(new Callback<KSMenuItemData>(){

			@Override
			public void exec(KSMenuItemData item) {
				if(item.getClickHandler() != null){
					StylishDropDown.this.hideMenu();
					if(showSelectedItem){
						titleLabel.setText(item.getLabel());
						if(item.getShownIcon() != null){
							titleLayout.remove(titleImage);
							Image image = item.getShownIcon().getImage();
							titleImage = new KSImage(image.getUrl(), image.getOriginLeft(), 
									image.getOriginTop(), image.getWidth(), image.getHeight());
							if(imgLoc == MenuImageLocation.RIGHT){
								titleLayout.add(titleImage);
							}
							else{
								titleLayout.insert(titleImage, 0);
							}
							
						}
					}
				}
			}
		});
		menuPanel.setWidget(menu);
		namePanel.addClickHandler(panelHandler);
		menuPanel.setAutoHideEnabled(true);
		menuPanel.addAutoHidePartner(namePanel.getElement());
		menuPanel.addCloseHandler(new CloseHandler<PopupPanel>(){
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				// TODO Auto-generated method stub
				
			}
		});
		container.setWidget(namePanel);
		this.initWidget(container);
		titleLabel.addStyleName("KS-CutomDropDown-TitleLabel");
		layout.addStyleName("KS-CustomDropDown-TitlePanel");
		defaultArrow.addStyleName("KS-CustomDropDown-Arrow");
	}
	
	public void showMenu(){
		menuPanel.setPopupPosition(this.getAbsoluteLeft(), this.getAbsoluteTop() + this.getOffsetHeight());
		menuPanel.show();
	}
	
	public void hideMenu(){
		menuPanel.hide();
	}
	
	public void setArrowImage(KSImage arrow){
		layout.remove(defaultArrow);
		arrow.addStyleName("KS-CustomDropDown-Arrow");
		layout.add(arrow);
	}
	
	public void setItems(List<KSMenuItemData> items){
		for(KSMenuItemData item: items){
			item.addMenuEventHandler(MenuSelectEvent.TYPE, menuHandler);
			item.addMenuEventHandler(MenuChangeEvent.TYPE, menuHandler);
		}
		menu.setItems(items);
		
	}
	
	public void setImageLocation(MenuImageLocation loc){
		imgLoc = loc;
		menu.setImageLocation(loc);
	}
	
	@Override
	public void addStyleName(String style){
		this.getWidget().addStyleName(style);
		menu.addStyleName(style);
	}
	
	public boolean isShowingSelectedItem() {
		return showSelectedItem;
	}

	public void setShowSelectedItem(boolean showSelectedItem) {
		this.showSelectedItem = showSelectedItem;
	}
	
	
}
