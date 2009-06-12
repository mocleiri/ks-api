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
package org.kuali.student.ui.kitchensink.client.kscommons.selectable;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import java.util.ArrayList;
import java.util.List;



import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.pagetable.AbstractTableSelectable;
import org.kuali.student.common.ui.client.widgets.pagetable.GenericTableModel;
import org.kuali.student.common.ui.client.widgets.pagetable.PagingScrollTableBuilder;
import org.kuali.student.common.ui.client.widgets.pagetable.TableSelectionToLabelHandler;
import org.kuali.student.ui.kitchensink.client.kscommons.dto.FirstNameColumnDefinition;
import org.kuali.student.ui.kitchensink.client.kscommons.dto.LastNameColumnDefinition;
import org.kuali.student.ui.kitchensink.client.kscommons.dto.Person;
import org.kuali.student.ui.kitchensink.client.kscommons.dto.PersonDTOs;

import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.PagingOptions;
import com.google.gwt.gen2.table.client.PagingScrollTable;

/**
 * An example of a paging table that extends 
 * @see class com.google.gwt.gen2.table.client.AbstractScrollTable
 * Rows are mapped to an example dto that implements 
 * @see org.kuali.student.core.dto.Idable
 * Where all columns contain text, the text of every selected row is copied
 * to a Label
 * Multi-row selection is the selection policy, only the selections on the current page are 
 * copied to the Label. Selections on previous pages are not kept
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 * @param <T>
 *
 */
public class PageSelectableExample extends AbstractTableSelectable<Person> {
    
    
    protected KSLabel label;
    private PagingOptions createPagingOptions(PagingScrollTable<Person> pagingScrollTable) {
        PagingOptions pagingOptions = new PagingOptions(pagingScrollTable); 
        pagingOptions.setPixelSize(pagingScrollTable.getOffsetWidth(), pagingOptions.getOffsetHeight());
        return pagingOptions;
    }
    
    public PageSelectableExample() {
        super();
        main.addStyleName(STYLE_EXAMPLE);
        label = new KSLabel("Paging and scrollable table with sortable columns" +
                "\nthat displays multi row selections for the current page: ", false);
        
        main.add(label);
        pagingScrollTable = new PagingScrollTableBuilder<Person>().tablePixelSize(220, 200).
            cacheTable(10, 10).columnDefinitions(createColumnDefinitions()).
            build(new GenericTableModel<Person>(new PersonDTOs().getPersons()));
        pagingScrollTable.getDataTable().addRowSelectionHandler(new TableSelectionToLabelHandler(pagingScrollTable.getDataTable(),selection));
        
        main.add(createTableAndSelectionPanel(createPagingOptions(pagingScrollTable)));
        super.initWidget(main);
    }
    @Override
    protected List<AbstractColumnDefinition<Person, ?>> createColumnDefinitions() {
        List<AbstractColumnDefinition<Person, ?>> columnDefs = new ArrayList<AbstractColumnDefinition<Person, ?>>();
        String firstNameHdr = "First Name";
        columnDefs.add((AbstractColumnDefinition<Person, ?>) new FirstNameColumnDefinition(firstNameHdr));
        
        String lastNameHdr = "Last Name";
        columnDefs.add((AbstractColumnDefinition<Person, ?>) new LastNameColumnDefinition(lastNameHdr));
        return columnDefs; 
    }

}