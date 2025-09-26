package com.marketplace.emailservice.model;

public class EmailRequest {
    private String toEmail;
    private String subject;
    private String body;
    private String username;

    // Constructors
    public EmailRequest() {}

    public EmailRequest(String toEmail, String subject, String body, String username) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
        this.username = username;
    }

    // Getters and Setters
    public String getToEmail() { return toEmail; }
    public void setToEmail(String toEmail) { this.toEmail = toEmail; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}