package org.kuali.student.common.ui.client.mvc;

import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired when the model is changed.
 * 
 * @author Kuali Student Team
 * @param <T>
 */
public class ModelChangeEvent<T> extends GwtEvent<ModelChangeHandler<T>> {
    public static final Type<ModelChangeHandler<?>> TYPE = new Type<ModelChangeHandler<?>>();

    /**
     * The actions that can be performed on a model.
     * 
     * @author Kuali Student Team
     */
    public enum Action {
        ADD, REMOVE, UPDATE
    }

    private final Action action;
    private final T value;

    /**
     * Constructs a new ModelChangeEvent with an action and a value
     * 
     * @param action
     * @param value
     */
    public ModelChangeEvent(Action action, T value) {
        this.action = action;
        this.value = value;
    }

    @Override
    protected void dispatch(ModelChangeHandler<T> handler) {
        handler.onModelChange(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Type<ModelChangeHandler<T>> getAssociatedType() {
        return (Type) TYPE;
    }

    /**
     * Returns the action (ADD/UPDATE/REMOVE) associated with the event
     * 
     * @return
     */
    public Action getAction() {
        return this.action;
    }

    /**
     * Returns the object with which the event is associated
     * 
     * @return
     */
    public T getValue() {
        return this.value;
    }

}
