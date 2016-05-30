package gr.charos.mailer.model;

public class Email {
 
	private String senderEmail;
	private String recipientEmail;
	private String subject;
	private String body;
	
	
	
	 
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getRecipientEmail() {
		return recipientEmail;
	}
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "Email [senderEmail=" + senderEmail + ", recipientEmail=" + recipientEmail
				+ ", subject=" + subject + ", body=" + body + "]";
	}
	
	

}
