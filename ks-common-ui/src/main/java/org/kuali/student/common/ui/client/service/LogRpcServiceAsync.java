package org.kuali.student.common.ui.client.service;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LogRpcServiceAsync {
    /**
     * Sends a List<LogMessage> of messages to the server.
     * @param clientContextInfo any configuration or runtime information about the client
     * @param messages the messages to log
     * @param callback callback to be invoked on response
     */
    public void sendLog(Map<String, String> clientContextInfo, String log, AsyncCallback<Boolean> callback);
}
