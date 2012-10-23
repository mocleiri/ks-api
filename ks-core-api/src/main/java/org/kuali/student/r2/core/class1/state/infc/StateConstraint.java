package org.kuali.student.r2.core.class1.state.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.core.class1.state.dto.Cardinality;

import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StateConstraint extends IdNamelessEntity {

    /**
     * Specifies how the constraints are applied, that is, existence of some, all or none
     *
     * @name Cardinality
     * @required
     */
    public Cardinality getCardinality();

    /**
     * Related objects' state key
     *
     * @name Related State Keys
     * @impl For example, if the cardinality is 'none', then none of the related objects should exist in the given states
     */
    public List<String> getRelatedStateKeys();

    /**
     * Business rule that specifies the constraint
     *
     * @name Agenda Id
     * @impl For example, "Can't cancel course with > 10 students"
     */
    public String getAgendaId();
}
