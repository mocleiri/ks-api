package org.kuali.student.common.ws.handler;

import java.io.PrintStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class DebugHandler implements SOAPHandler<SOAPMessageContext> {

	private PrintStream err;
	private PrintStream out;
	
	public DebugHandler()	{
		this(System.out, System.err);
	}
	
	public DebugHandler(PrintStream ps)	{
		this(ps, ps);
	}
	
	public DebugHandler(PrintStream out, PrintStream err)	{
		this.out = out;
		this.err = err;
	}
	
	@Override
	public Set<QName> getHeaders() {
		// does not handle any headers
		return null;
	}

	@Override
	public void close(MessageContext context) {
		// no cleanup needed
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return this.printMessage(context, this.err);
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		return this.printMessage(context, this.out);
	}
	
	private boolean printMessage(SOAPMessageContext context, PrintStream ps)	{
		String message = "DebugHandler ";
		
		Boolean out = (Boolean)context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if(out.booleanValue())
			message += "Out: ";
		else
			message += "In: ";
		
		ps.print(message);
		
		try	{
			context.getMessage().writeTo(ps);
			ps.println();
		}
		catch(Exception ex)	{
			ex.printStackTrace(this.err);
		}
		
		return true;
	}

}
