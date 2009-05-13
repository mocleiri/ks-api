package org.kuali.student.lum.ui.requirements.client.model;

import java.util.ArrayList;
import java.util.List;

public class EditHistory {

    private List<StatementVO> histories = new ArrayList<StatementVO>();
    private final int MAX_NUM_HISTORIES = 5;
    private int currHistoryIndex = -1;
    
    public void save(StatementVO history) {
        StatementVO cloned = null;
        List<StatementVO> newHistories = new ArrayList<StatementVO>();
        // replace out of date histories with the new history
        if (currHistoryIndex >= 0 &&
                currHistoryIndex < histories.size()) {
            for (int i = 0; i < currHistoryIndex + 1; i++) {
                newHistories.add(histories.get(i));
            }
            histories = newHistories;
        }
        currHistoryIndex++;
        cloned = ObjectClonerUtil.clone(history);
        histories.add(cloned);
        
        // delete old element
        if (histories.size() == MAX_NUM_HISTORIES) {
            histories.remove(0);
        }
    }
    
    public StatementVO undo() {
        StatementVO history = null;
        if (isUndoable()) {
            currHistoryIndex--;
        }
        history = ObjectClonerUtil.clone(histories.get(currHistoryIndex));
        return history;
    }
    
    public StatementVO redo() {
        StatementVO history = null;
        if (isRedoable()) {
            currHistoryIndex++;
        }
        history = ObjectClonerUtil.clone(histories.get(currHistoryIndex));
        return history;
    }
    
    public boolean isUndoable() {
        return currHistoryIndex > 0;
    }
    
    public boolean isRedoable() {
        return currHistoryIndex < histories.size() - 1;
    }
    
    
}
