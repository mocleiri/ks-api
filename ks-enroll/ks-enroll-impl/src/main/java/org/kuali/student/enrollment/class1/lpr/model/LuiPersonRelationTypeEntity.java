package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.TypeEntity;

/**
 * @author sambit
 */
@Entity
@Table(name = "KSEN_LPR_TYPE")
public class LuiPersonRelationTypeEntity extends TypeEntity<LuiPersonRelationAttributeEntity> {

}
