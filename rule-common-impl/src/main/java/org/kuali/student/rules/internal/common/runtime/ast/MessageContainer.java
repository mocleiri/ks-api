package org.kuali.student.rules.internal.common.runtime.ast;

import java.util.HashMap;
import java.util.Map;

public class MessageContainer {
	private Map<String, BooleanMessage> messageMap = new HashMap<String, BooleanMessage>();
	
	public void addMessage(BooleanMessage message) {
		this.messageMap.put(message.getMessageId(), message);
	}
	
	public BooleanMessage getMessage(String id) {
		return this.messageMap.get(id);
	}
	
	public Map<String, BooleanMessage> getMessageMap() {
		return this.messageMap;
	}
}
