package org.apache.torque.mojo;

import org.kuali.core.db.torque.PrettyPrint;
import org.kuali.core.db.torque.Utils;
import org.kuali.db.DatabaseEvent;
import org.kuali.db.DatabaseListener;
import org.apache.maven.plugin.logging.Log;

/**
 * A mojo friendly listener for database events
 */
public class MojoDatabaseListener implements DatabaseListener {
	PrettyPrint prettyPrint;
	Log log;
	Utils utils;
	int updateStatusInterval = 150;

	public MojoDatabaseListener() {
		this(null, null, null);
	}

	public MojoDatabaseListener(PrettyPrint prettyPrint, Log log, Utils utils) {
		super();
		this.prettyPrint = prettyPrint;
		this.log = log;
		this.utils = utils;
	}

	@Override
	public void messageLogged(DatabaseEvent event) {
		switch (event.getPriority()) {
		case DEBUG:
			getLog().debug(event.getMessage());
			break;
		case INFO:
			getLog().info(event.getMessage());
			break;
		case WARN:
			getLog().warn(event.getMessage());
			break;
		case ERROR:
			getLog().error(event.getMessage(), event.getException());
			break;
		default:
			getLog().warn("Unknown message priority " + event.getPriority() + " for message: " + event.getMessage());
			break;
		}
	}

	@Override
	public void beginTransaction(DatabaseEvent event) {
		prettyPrint = new PrettyPrint("[INFO] " + event.getTransaction().getResourceLocation());
		utils.left(prettyPrint);
	}

	@Override
	public void finishTransaction(DatabaseEvent event) {
		utils.right(prettyPrint);
		prettyPrint = null;
	}

	@Override
	public void beforeExecuteSQL(DatabaseEvent event) {
		int totalStatements = event.getTotalStatements();
		if ((totalStatements % updateStatusInterval) == 0) {
			System.out.print(".");
			prettyPrint.setMsg(prettyPrint.getMsg() + ".");
		}
	}

	@Override
	public void afterExecuteSQL(DatabaseEvent event) {
	}

	@Override
	public void afterProcessingSQLResults(DatabaseEvent event) {
		// TODO Auto-generated method stub

	}

	public PrettyPrint getPrettyPrint() {
		return prettyPrint;
	}

	public void setPrettyPrint(PrettyPrint pp) {
		this.prettyPrint = pp;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Utils getUtils() {
		return utils;
	}

	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	public int getUpdateStatusInterval() {
		return updateStatusInterval;
	}

	public void setUpdateStatusInterval(int updateStatusInterval) {
		this.updateStatusInterval = updateStatusInterval;
	}

}
