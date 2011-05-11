package org.kuali.student.r2.lum.lrc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.infc.KeyEntity;
import org.w3c.dom.Element;

/**
 * @author sambit
 */

public interface ResultValue extends KeyEntity, Serializable {

	/**
	 * Result Value Name string Name of the result value. This is likely related
	 * to the type, value and/or scale, but this is not required.
	 */
	public String getName();

	/**
	 * Result Value string Value of the result. Typically corresponds with the
	 * short coded form of the result(ex. "A", "4.0", "97.0", "B.S" etc.)
	 * scaleKey Scale Identifier scaleKey
	 */
	public String getValue();

	/**
	 * Rank string Rank of the result value within the scale. Standards around
	 * uniqueness and meaning of value are described in the information about
	 * the scale.
	 */
	public String getRank();

	/**
	 * Effective Date dateTime Date and time that this result value became
	 * effective. This is a similar concept to the effective date on enumerated
	 * values. When an expiration date has been specified, this field must be
	 * less than or equal to the expiration date.
	 */
	public Date getEffectiveDate();

	/**
	 * Expiration Date dateTime Date and time that this result value expires.
	 * This is a similar concept to the expiration date on enumerated values. If
	 * specified, this should be greater than or equal to the effective date. If
	 * this field is not specified, then no expiration date has been currently
	 * defined and should automatically be considered greater than the effective
	 * date.
	 */
	public Date getExpirationDate();

	public List<Element> get_futureElements();

	public String getId();

}
