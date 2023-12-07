package backend.Notifications;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailing {
    public static void sendMail(String messageToSend, String receiverEmail) {
        //Recipient's email ID needs to be mentioned.
        String from = "envisonaryapp@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        // properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        //  properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.port", "465");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("envisonaryapp@gmail.com", "bbmr dmgl avvs cndv");
            }
        });
        session.setDebug(true);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            message.setSubject("Envisionary Prediction Notification!");
            message.setText(messageToSend);
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully.");
        } catch (MessagingException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        sendMail("Test message", "bmlguitarist@gmail.com");
    }
}