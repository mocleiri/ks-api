/*
 * Copyright 2013 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.fee.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.CurrencyAmount;

import java.util.Date;
import java.util.List;


/**
 * The Fee is drawn from the CatalogFee and applied to something (ref
 * object) with a charge, like a CourseOffering. The CatalogFee
 * constrains the Fee.
 *
 * The Fee has one of three flavors indicated by its Type:
 *
 * <dl>
 *    <dt>Flat</dt> <dd>A fee that doesn't vary. The amount is
 *                  constrained by the minimum and maximum amount
 *                  range in the Fee Catalog.</dd>
 *  
 *    <dt>Fixed Credit</dt> <dd>A fee per credit where the total
 *                          amount is the fee multiplied by the
 *                          credits (or units) determined by what this
 *                          Fee applies to. The amount is constrained
 *                          by the minimum and maximum amount range in
 *                          the Fee Catalog.</dd>
 * 
 *    <dt>Flexible Credit</dt> <dd>A specific fee for each credit
 *                             value. The list of flexible credit
 *                             amounts is constrained by the list of
 *                             acceptable credits amounts in the Fee
 *                             Catalog.</dd> 
 * </dl>
 *
 * @author Kuali Student Services
 */

public interface Fee
    extends IdEntity {

    /**
     * The CatalogFee identifier to which this Fee belongs.
     * 
     * @return the catalog fee Id
     * @name Catalog Fee Id
     * @required
     * @readOnly
     */
    public String getCatalogFeeId();

    /**
     * The URI of the reference object to which this Fee applies.
     *
     * @return the URI
     * @name Reference Object URI
     */
    public String getRefObjectURI();

    /**
     * The identifier of the reference objects to which this Fee
     * applies. There may be multiple references, but all of the same
     * type as indicated by the reference object URI.
     *
     * @return the Ids of the reference objects
     * @name Reference Object Ids
     */
    public List<String> getRefObjectIds();

    /**
     * The ATP for which this Fee is in effect. The ATP should be
     * constrained by the list of applicable ATP Ids in the Fee
     * Catalog.
     *
     * In the case of a Course Offering, this ATP is the same as (or a
     * parent of) the Course Offering ATP.
     * 
     * @return the ATP Id
     * @name ATP Id
     */
    public String getAtpId();

    /**
     * The amount for a flat or fixed credit fee. This amount should
     * be constrained by the minimum and maxmimum range in the Fee
     * Catalog for flat and fixed credit Fees.
     * 
     * @return the amount
     * @name Amount
     */
    public CurrencyAmount getAmount();

    /**
     * The list of flexible credit amounts. This list should be
     * constrained by the list of flexible credit amounts in the
     * Fee Catalog
     * 
     * @return the list of flexible credit amounts
     * @name Flexible Credit Amounts
     */
    public List<? extends FlexibleCreditAmount> getFlexibleCreditAmounts();

    /**
     * The transaction code. The transaction code can differ from the
     * default type in the Fee Catalog of
     * CatlogFee.canOverrideTransactionCode is true.
     *
     * @return the transaction code
     * @name Transaction Code
     */
    public String getTransactionCode();

    /**
     * The transaction date used for fee processing.
     *
     * @return the transaction date
     * @name Transaction Date
     */
    public Date getTransactionDate();

    /**
     * The transaction date type key. The transaction date type can
     * differ from the default type in the Fee Catalog of
     * CatlogFee.canOverrideTransactionDateTypeKey is true.
     *
     * @return the transaction date type key
     * @name Transaction Date Type Key
     */
    public String getTransactionDateTypeKey();
}
