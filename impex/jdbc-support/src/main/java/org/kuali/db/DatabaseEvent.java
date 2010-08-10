package org.kuali.db;

import java.util.Date;

/**
 * Pojo for a database event
 */
public class DatabaseEvent {
	String message;
	MessagePriority priority;
	Date timestamp;
	Throwable exception;
	Transaction transaction;

	public DatabaseEvent() {
		this(null);
	}

	public DatabaseEvent(String message) {
		this(message, MessagePriority.INFO);
	}

	public DatabaseEvent(String message, MessagePriority priority) {
		this(message, priority, new Date(), null, null);
	}

	public DatabaseEvent(String message, MessagePriority priority, Date timestamp, Throwable exception, Transaction transaction) {
		super();
		this.message = message;
		this.priority = priority;
		this.timestamp = timestamp;
		this.exception = exception;
		this.transaction = transaction;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessagePriority getPriority() {
		return priority;
	}

	public void setPriority(MessagePriority priority) {
		this.priority = priority;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}
