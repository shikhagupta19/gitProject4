package in.co.rays.proj4.util;


import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import in.co.rays.proj4.exception.ApplicationException;


/**
 * Email Utility provide Email services.
 * @author Shikha
 *
 */
public class EmailUtility {
	   /**
     * Create Resource Bundle to read properties file
     */
	private static final String FILENAME = "in.co.rays.proj4.bundle.system";
	public static ResourceBundle rb=ResourceBundle.getBundle(FILENAME);
	
    /**
     * Email Server
     */
    private static final String SMTP_HOST_NAME = rb.getString("smtp.server");

    /**
     * Email Server Port
     */
    private static final String SMTP_PORT = rb.getString("smtp.port");

    /**
     * Session Factory, A session is a connection to email server.
     */
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    /**
     * Administrator's email id by which all messages are sent
     */
    private static final String emailFromAddress = rb.getString("email.login");

    /**
     * Administrator email's password
     */
    private static final String emailPassword = rb.getString("email.pwd");

    /**
     * Email server properties
     */

    private static Properties props = new Properties();

    /**
     * Static block to initialize static parameters
     */
    static {
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", SMTP_PORT);
       
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");     
        
    }

    /**
     * Sends an Email
     * 
     * @param emailMessageDTO : Email message
     * @throws ApplicationException
     */
    public static void sendmail(EmailMessage emailMessageDTO)
            throws ApplicationException {

        try {

            // Connection to Mail Server
        	System.out.println("---------sendmail method start----------");
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(emailFromAddress,
                                    emailPassword);
                        }
                    });

            // Make debug mode true to display debug messages at console
            session.setDebug(true);

            // Create a message
            Message msg = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress(emailFromAddress);
            msg.setFrom(addressFrom);

            // Set TO addresses
            String[] emailIds = new String[0];

            if (emailMessageDTO.getTo() != null) {
                emailIds = emailMessageDTO.getTo().split(",");
            }

            // Set CC addresses
            String[] emailIdsCc = new String[0];

            if (emailMessageDTO.getCc() != null) {
                emailIdsCc = emailMessageDTO.getCc().split(",");
            }

            // Set BCC addresses
            String[] emailIdsBcc = new String[0];

            if (emailMessageDTO.getBcc() != null) {
                emailIdsBcc = emailMessageDTO.getBcc().split(",");
            }

            InternetAddress[] addressTo = new InternetAddress[emailIds.length];
            System.out.println("------addressTO array length "+addressTo.length);

            for (int i = 0; i < emailIds.length; i++) {
                addressTo[i] = new InternetAddress(emailIds[i]);
                System.out.println("addressTO i value"+addressTo[i]);
            }

            InternetAddress[] addressCc = new InternetAddress[emailIdsCc.length];

            for (int i = 0; i < emailIdsCc.length; i++) {
                addressCc[i] = new InternetAddress(emailIdsCc[i]);
                System.out.println("addressTO i value"+addressCc[i]);
            }

            InternetAddress[] addressBcc = new InternetAddress[emailIdsBcc.length];

            for (int i = 0; i < emailIdsBcc.length; i++) {
                addressBcc[i] = new InternetAddress(emailIdsBcc[i]);
                System.out.println("addressTO i value"+addressBcc[i]);
            }

            if (addressTo.length > 0) {
                msg.setRecipients(Message.RecipientType.TO, addressTo);
            }

            if (addressCc.length > 0) {
                msg.setRecipients(Message.RecipientType.CC, addressCc);
            }

            if (addressBcc.length > 0) {
                msg.setRecipients(Message.RecipientType.BCC, addressBcc);
            }

            // Setting the Subject and Content Type
            msg.setSubject(emailMessageDTO.getSubject());

            // Set message MIME type
            
            switch (emailMessageDTO.getMessageType()) {
            case EmailMessage.HTML_MSG:
                msg.setContent(emailMessageDTO.getMessage(), "text/html");
                System.out.println("text/html message choose");
                break;
            case EmailMessage.TEXT_MSG:
                msg.setContent(emailMessageDTO.getMessage(), "text/plain");
                System.out.println("text/plain message choose");
                break;

            }
 
                      
            // Send the mail
            System.out.println("--------transport send method call");
            Transport.send(msg);
            System.out.println("--------transport send method end");
            System.out.println("Email send successfully");
          
            

        } catch (Exception ex)
        {
        	ex.printStackTrace();
            throw new ApplicationException("Email " + ex.getMessage());
        }
    }
}
