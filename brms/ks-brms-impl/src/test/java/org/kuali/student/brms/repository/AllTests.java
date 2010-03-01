/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.brms.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( 
    {
        org.kuali.student.brms.repository.RuleEngineRepositoryTest.class,
        org.kuali.student.brms.repository.util.ObjectUtilTest.class,
        org.kuali.student.brms.repository.rule.AllTests.class,
        org.kuali.student.brms.repository.drools.AllTests.class,
        org.kuali.student.brms.translators.drools.RuleSetTranslatorTest.class
    } )
public class AllTests {
}
