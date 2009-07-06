package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;

public abstract class LayoutSectionView extends Section implements View{
    private Controller controller;   
    private Enum<?> viewEnum;
    private String viewName;

    /**
     * Constructs a new view with an associated controller and view name
     * 
     * @param controller
     *            the controller associated with the view
     * @param name
     *            the view name
     */
    public LayoutSectionView(Controller controller, Enum<?> viewEnum, String viewName) {
        this.controller = controller;
        this.viewEnum = viewEnum;
        this.viewName = viewName;
    }

    public LayoutSectionView(Enum<?> viewEnum, String viewName) {
        this.controller = null;
        this.viewEnum = viewEnum;
        this.viewName = viewName;
    }
        
    /** 
     * This method gets view name enumeration
     * 
     * @return
     */
    public Enum<?> getViewEnum() {
        return viewEnum;
    }
    
    /**
     * Called by controller before the view is displayed to allow lazy initialization or any other preparatory work to be
     * done.
     */
    public void beforeShow() {
    // do nothing
    }

    /**
     * Called by the controller before the view is hidden to allow the view to perform cleanup or request confirmation from
     * the user, etc. Can cancel the action by returning false.
     * 
     * @return true if the view can be hidden, or false to cancel the action.
     */
    @Override
    public boolean beforeHide() {
        return true;
    }

    /**
     * Returns the controller associated with the view
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#getController()
     */
    @Override
    public Controller getController() {
        return controller;
    }

    /**
     * Returns the view's name
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#getName()
     */
    @Override
    public String getName() {
        return viewName;
    }

    /**
     * Used to clear view 
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#clear()
     */
    public void clear(){
        //do nothing
    }        
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
}
