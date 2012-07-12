package org.sis.ancmessaging.server.utility;

import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.Service;

import java.util.ArrayList;
import java.util.List;

public class SmsMessageHandler {
	private String recipient;
	private String content;
    private boolean statusReportEnabled;

    public void setStatusReportEnabled(boolean statusReportEnabled) {
        this.statusReportEnabled = statusReportEnabled;
    }

    public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

    public void setContent(String content) {
		this.content = content;
	}

	public SmsMessageHandler() {

	}

    public String sendMessage() throws Exception {
    	OutboundMessage msg = new OutboundMessage(recipient, content);
        msg.setStatusReport(statusReportEnabled);
		Service.getInstance().sendMessage(msg);
		return msg.getRefNo();
	}

    public List<InboundMessage> readMessages() throws Exception {
        List<InboundMessage> msgList = new ArrayList<InboundMessage>();
        Service.getInstance().readMessages(msgList, InboundMessage.MessageClasses.ALL);
        for (InboundMessage msg : msgList) {
            Service.getInstance().deleteMessage(msg);
        }
        return msgList;
    }

}
