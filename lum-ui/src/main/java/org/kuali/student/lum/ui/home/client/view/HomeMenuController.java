package org.kuali.student.lum.ui.home.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.ui.requirements.client.view.CourseRequisiteView;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HomeMenuController extends Controller implements View {
    
    public enum MenuViews {
        DEFAULT_VIEW, CREATE_COURSE_VIEW, MODIFY_COURSE_VIEW
    }
    
    public View defaultPanel = new DefaultPanel(this);
   // public View createCoursePanel = new CreateCreditCoursePanel(this);
    public View modifyCoursePanel = new CourseRequisiteView(this, "test");
    
    public HorizontalPanel mainPanel = new HorizontalPanel();
    public VerticalPanel menuPanel = new VerticalPanel();
    public SimplePanel contentPanel = new SimplePanel();
    
    private EventHandler handler = new EventHandler();
    
    public static String CREATE_COURSE = "Create Course";
    public static String MODIFY_COURSE = "Modify Course";
    
    private MenuItemPanel createCourse = new MenuItemPanel(CREATE_COURSE);
    private MenuItemPanel modifyCourse = new MenuItemPanel(MODIFY_COURSE);
    
    private List<MenuItemPanel> menuItems = new ArrayList<MenuItemPanel>();
    
    private class MenuItemPanel extends Composite{
        KSLabel itemLabel = new KSLabel();
        FocusPanel thePanel = new FocusPanel();
        boolean selected = false;
        
        public MenuItemPanel(String itemName){
            itemLabel.setText(itemName);
            itemLabel.setWordWrap(true);
            thePanel.addStyleName("Course-Home-Menu-Item");
            itemLabel.addStyleName("Course-Home-Menu-Label");
            thePanel.addClickHandler(handler);
            thePanel.addMouseOverHandler(handler);
            thePanel.addMouseOutHandler(handler);
            thePanel.addFocusHandler(handler);
            thePanel.addBlurHandler(handler);
            thePanel.add(itemLabel);
            this.initWidget(thePanel);
        }
        
        public void deSelect(){
            selected = false;
            thePanel.removeStyleName("Course-Home-Menu-Item-Selected");
            itemLabel.removeStyleName("Course-Home-Menu-Label-Selected");
        }
        
        public void select(){
            selected = true;
            thePanel.addStyleName("Course-Home-Menu-Item-Selected");
            itemLabel.addStyleName("Course-Home-Menu-Label-Selected");
        }
        
        public FocusPanel getPanel(){
            return thePanel;
        }
    }
    
    private class EventHandler implements ClickHandler, MouseOverHandler, MouseOutHandler, FocusHandler, BlurHandler{

        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            //deselect menu items
            for(MenuItemPanel m : menuItems){
                m.deSelect();
            }

            if (sender == createCourse.getPanel()) {
                createCourse.select();
                showView(MenuViews.CREATE_COURSE_VIEW);
            }
            else if(sender == modifyCourse.getPanel()){
                modifyCourse.select();
                showView(MenuViews.MODIFY_COURSE_VIEW);
            }            
        }

        @Override
        public void onMouseOver(MouseOverEvent event) {
            Widget sender = (Widget) event.getSource();
            sender.addStyleName("Course-Home-Menu-Item-Hover");
        }

        @Override
        public void onMouseOut(MouseOutEvent event) {
            Widget sender = (Widget) event.getSource();
            sender.removeStyleName("Course-Home-Menu-Item-Hover");
        }

        @Override
        public void onFocus(FocusEvent event) {
            // no function yet
            
        }

        @Override
        public void onBlur(BlurEvent event) {
            // no function yet
            
        }       
    }
    
    public HomeMenuController(){
        mainPanel.setStyleName("Course-Home-Main-Panel");
        menuPanel.setStyleName("Course-Home-Menu-Panel");
        contentPanel.setStyleName("Course-Home-Content-Panel");   
        setupMenuItems();
        mainPanel.add(menuPanel);
        mainPanel.add(contentPanel);
        this.initWidget(mainPanel);
    }
    
    private void setupMenuItems(){
        addMenuItem(createCourse);
        addMenuItem(modifyCourse);
    }
    
    private void addMenuItem(MenuItemPanel panel){
        menuPanel.add(panel);
        menuItems.add(panel);
    }
    
    //Controller Methods

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((MenuViews) viewType) {
            case DEFAULT_VIEW:
                return defaultPanel;
            case CREATE_COURSE_VIEW:
                return null; //createCoursePanel;
            case MODIFY_COURSE_VIEW:
                return modifyCoursePanel;
            default:
                return defaultPanel;
        }
    }

    @Override
    protected void hideView(View view) {
        contentPanel.clear();
    }

    @Override
    protected void renderView(View view) {
        contentPanel.setWidget((ViewComposite) view);
    }

    @Override
    public void showDefaultView() {
        showView(MenuViews.DEFAULT_VIEW);
        
    }

    @Override
    public boolean beforeHide() {
        return true;
    }

    @Override
    public void beforeShow() {
        //Not needed
    }

    @Override
    public Controller getController() {
        return this.getParentController();
    }

    @Override
    public String getName() {
        return "LUM Home Menu";
    }    
}
