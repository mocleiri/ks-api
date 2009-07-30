package org.kuali.student.common.ui.client.mvc;

import com.google.gwt.user.client.ui.Composite;

/**
 * Abstract class implementing the View interface, which has a handle to it's controller.
 * 
 * @author Kuali Student Team
 */
public abstract class ViewComposite extends Composite implements View {
    private final Controller controller;
    private final String name;

    /**
     * Constructs a new view with an associated controller and view name
     * 
     * @param controller
     *            the controller associated with the view
     * @param name
     *            the view name
     */
    public ViewComposite(Controller controller, String name) {
        this.controller = controller;
        this.name = name;
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
        return name;
    }

    /**
     * Used to clear view 
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#clear()
     */
    public void clear(){
        //do nothing
    }
    
    
    /**
     * Update the model that is associated with this view. This will normally be
     * called by the controller.
     *  
     * @see org.kuali.student.common.ui.client.mvc.View#updateModel()
     */
    public void updateModel(){        
    }
    
}