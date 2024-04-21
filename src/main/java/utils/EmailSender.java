package utils;

import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class EmailSender {

    private static String USEREMAIL = "test@test.com";
    private static String PASSWORDEMAIL = "123456";
    private static String FROM = "test@test.com";

    public static void sendmail(String subject, String body,String to,String ccEmail,String bccEmail, String[] attachFiles) {

        try {

            Properties properties = System.getProperties();

            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.host", "mail.test.com");
            properties.setProperty("mail.smtp.port", "587");
            Session mailSession = Session.getInstance(properties,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(USEREMAIL, PASSWORDEMAIL);
                        }
                    });


            // creates a new e-mail message
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(FROM));


            //TO
            String[] toArray = to.split("#");
            InternetAddress[] toAddresses = new InternetAddress[toArray.length];
            for(int i=0;i < toArray.length; i++) {
                toAddresses[i] = new InternetAddress(toArray[i]);
            }

            msg.setRecipients(Message.RecipientType.TO, toAddresses);

            //CC
            if(ccEmail!=null) {
                String[] ccArray = ccEmail.split("#");
                InternetAddress[] ccAddresses = new InternetAddress[ccArray.length];
                for(int i=0;i < ccArray.length; i++) {
                    ccAddresses[i] = new InternetAddress(ccArray[i]);
                }
                msg.setRecipients(Message.RecipientType.CC,ccAddresses);
            }

            //BCC
            if(bccEmail!=null) {
                String[] bccArray = bccEmail.split("#");
                InternetAddress[] bccAddresses = new InternetAddress[bccArray.length];
                for(int i=0;i < bccArray.length; i++) {
                    bccAddresses[i] = new InternetAddress(bccArray[i]);
                }
                msg.setRecipients(Message.RecipientType.BCC,bccAddresses);
            }

            //Μessage Subject
            msg.setSubject(subject);

            //Μessage Date
            msg.setSentDate(new Date());

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            //Message
            messageBodyPart.setContent(body, "text/html;charset=utf-8");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // adds attachments
            if (attachFiles != null && attachFiles.length > 0) {
                for (String filePath : attachFiles) {
                    MimeBodyPart attachPart = new MimeBodyPart();

                    try {
                        attachPart.attachFile(filePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    multipart.addBodyPart(attachPart);
                }
            }

            // sets the multi-part as e-mail's content
            msg.setContent(multipart);

            // sends the e-mail
            Transport.send(msg);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ψάχνει στα εισεερχόμενα μηνήματα τουα χρήστη pearlapp@pearl-rail.com για να δεί άμα θα βρέι κάποιο εισερχόμενο μήνυμα
     * που έχει τον ίδιο τίτλο με τον τίτλο του μηνήματος τον οποίο παέι να στείλει.
     * Άμα υπάρχει ka;nei reply σε σε αυτό το μήνυμα.
     * Ειδάλλως στέλνει ένα μήνυμα μόνο του.
     *
     * @param subject
     * @param body
     * @param to
     * @param bccEmail
     * @param ccEmail
     * @param attachFiles
     * @throws MessagingException
     */
    public static void replymail(String subject, String body, String to, String bccEmail, String ccEmail, String[] attachFiles) throws MessagingException {

        try {

            Properties properties = System.getProperties();

            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.host", "smtp.office365.com");
            properties.setProperty("mail.smtp.port", "587");


            properties.put("mail.store.protocol", "pop3");
            properties.put("mail.pop3s.host", "outlook.office365.com");
            properties.put("mail.pop3s.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            javax.mail.Session mailSession = javax.mail.Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(USEREMAIL, PASSWORDEMAIL);
                        }
                    });


            // Get a Store object and connect to the current host
            Store store = mailSession.getStore("pop3s");
            store.connect("outlook.office365.com", USEREMAIL,
                    PASSWORDEMAIL);

            //Βρίσκουμε τον φάκελο inbox
            Folder folder = store.getFolder("inbox");
            if (!folder.exists()) {
                log.info("inbox not found");
            }

            //Ανοίγουμε τον φάκελο μόνο για ανανωση
            folder.open(Folder.READ_ONLY);

            //Παίρνουμε τα μηνύματα.
            Message[] messages = folder.getMessages();

            Message tempMessage = null;
            if (messages.length != 0) {

                for (int i = 0, n = messages.length; i < n; i++) {
                    //Κοιτάμε άμα υπάρχει κάποιο μήνυμα το οποίο έχει τον ίδιο τίτλο με τον τίτλο του email που θέλω να στείλω.
                    if (messages[i].getSubject().equals(subject)) {

                        tempMessage = messages[i];
                        break;
                    }
                }
            }

            //Φτιάχνω το replyMessage
            if (!Objects.isNull(tempMessage)) {
                Message message2 = tempMessage;
                Date date = message2.getSentDate();
                // Βάζω όλους τους αποδέκτες απο το παλαιό email
                String from = InternetAddress.toString(message2.getFrom());
                if (from != null) {
                    log.info("From: " + from);
                }
                String replyTo = InternetAddress.toString(message2
                        .getReplyTo());
                if (replyTo != null) {
                    log.info("Reply-to: " + replyTo);
                }
                String to2 = InternetAddress.toString(message2
                        .getRecipients(Message.RecipientType.TO));
                if (to != null) {
                    log.info("To: " + to2);
                }

                String subject2 = message2.getSubject();
                if (subject != null) {
                    log.info("Subject: " + subject2);
                }
                Date sent2 = message2.getSentDate();
                if (sent2 != null) {
                    log.info("Sent: " + sent2);
                }

                Message replyMessage = new MimeMessage(mailSession);
                replyMessage = (MimeMessage) message2.reply(false);
                replyMessage.setFrom(new InternetAddress("nightwatch@pearl-rail.com"));


                // creates message part
                MimeBodyPart messageBodyPart = new MimeBodyPart();

                //Message
                messageBodyPart.setContent(body, "text/html;charset=utf-8");

                // creates multi-part
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);


                // sets the multi-part as e-mail's content
                replyMessage.setContent(multipart);

                replyMessage.setReplyTo(message2.getReplyTo());


                // Send the message by authenticating the SMTP server
                // Create a Transport instance and call the sendMessage
                Transport t = mailSession.getTransport("smtp");
                try {
                    //connect to the smpt server using transport instance
                    //change the user and password accordingly
                    t.connect(USEREMAIL, PASSWORDEMAIL);
                    t.sendMessage(replyMessage,
                            replyMessage.getAllRecipients());
                } finally {
                    t.close();
                }
                log.info("message replied successfully ....");

                // close the store and folder objects
                folder.close(false);
                store.close();
            } else {
                // Στην περίπτωη που δεν υπάρχει email με ίδιο τίτλο με αυτό το οποίο έχω τότε στέλνω ένα  email καινούργιο.
                // creates a new e-mail message
                Message msg = new MimeMessage(mailSession);
                InternetAddress[] toAddresses = {new InternetAddress(to)};

                msg.setFrom(new InternetAddress("nightwatch@pearl-rail.com"));
//                msg.setFrom(new InternetAddress(USEREMAIL));
                msg.setRecipients(Message.RecipientType.TO, toAddresses);

                //BCC
                if (bccEmail != null) {
                    msg.setRecipients(Message.RecipientType.BCC,
                            InternetAddress.parse(bccEmail));
                }

                //CC
                if (ccEmail != null) {
                    msg.setRecipients(Message.RecipientType.CC,
                            InternetAddress.parse(ccEmail));
                }

                //Μessage Subject
                msg.setSubject(subject);

                //Μessage Date
                msg.setSentDate(new Date());

                // creates message part
                MimeBodyPart messageBodyPart = new MimeBodyPart();

                //Message
                messageBodyPart.setContent(body, "text/html;charset=utf-8");

                // creates multi-part
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                // adds attachments
                if (attachFiles != null && attachFiles.length > 0) {
                    for (String filePath : attachFiles) {
                        MimeBodyPart attachPart = new MimeBodyPart();

                        try {
                            attachPart.attachFile(filePath);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        multipart.addBodyPart(attachPart);
                    }
                }

                // sets the multi-part as e-mail's content
                msg.setContent(multipart);

                // sends the e-mail
                Transport.send(msg);
//                Transport t = mailSession.getTransport("smtp");
//                t.connect(USER, PASSWORD);
//                t.sendMessage(msg,
//                        msg.getAllRecipients());
            }

        } catch (Exception ex) {
            log.error(String.valueOf(ex));
        }
    }


//    public static void send2(String subject, String body, String to, String bccEmail, String ccEmail, String[]
//            attachFiles, Boolean replytoAll) {
//
//        try {
//
//            Properties properties = System.getProperties();
//
//            properties.setProperty("mail.smtp.auth", "true");
//            properties.setProperty("mail.smtp.starttls.enable", "true");
//            properties.setProperty("mail.smtp.host", "smtp.office365.com");
//            properties.setProperty("mail.smtp.port", "587");
//
//
//            properties.put("mail.store.protocol", "pop3");
//            properties.put("mail.pop3s.host", "outlook.office365.com");
//            properties.put("mail.pop3s.port", "995");
//            properties.put("mail.pop3.starttls.enable", "true");
//            properties.put("mail.smtp.auth", "true");
//            properties.put("mail.smtp.starttls.enable", "true");
////            properties.put("mail.smtp.host", "relay.jangosmtp.net");
////            properties.put("mail.smtp.port", "25");
//            Session session = Session.getDefaultInstance(properties);
//
//            Date date = null;
//
//
//            javax.mail.Session mailSession = javax.mail.Session.getInstance(properties,
//                    new javax.mail.Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(USER, PASSWORD);
//                        }
//                    });
//
//
//            // creates a new e-mail message
//            Message msg = new MimeMessage(mailSession);
//
////            msg.setFrom(new InternetAddress("operations@pearl-rail.com"));
//
//
//            // Get a Store object and connect to the current host
//            Store store = session.getStore("pop3s");
//            store.connect("outlook.office365.com", "pearlapp@pearl-rail.com",
//                    "Qud44097");
//
//            Folder folder = store.getFolder("inbox");
//            if (!folder.exists()) {
//                System.out.println("inbox not found");
//                System.exit(0);
//            }
//            folder.open(Folder.READ_ONLY);
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    System.in));
//
//            Message[] messages = folder.getMessages();
//            if (messages.length != 0) {
//
//                for (int i = 0, n = messages.length; i < n; i++) {
//                    Message tempmessage = messages[i];
//                    date = tempmessage.getSentDate();
//                    // Get all the information from the message
//                    String from = InternetAddress.toString(tempmessage.getFrom());
//                    if (from != null) {
//                        System.out.println("From: " + from);
//                    }
//                    String replyTo = InternetAddress.toString(tempmessage
//                            .getReplyTo());
//                    if (replyTo != null) {
//                        System.out.println("Reply-to: " + replyTo);
//                    }
//                    to = InternetAddress.toString(tempmessage
//                            .getRecipients(Message.RecipientType.TO));
//                    if (to != null) {
//                        System.out.println("To: " + to);
//                    }
//
//                    subject = tempmessage.getSubject();
//                    if (subject != null) {
//                        System.out.println("Subject: " + subject);
//                    }
//                    Date sent = tempmessage.getSentDate();
//                    if (sent != null) {
//                        System.out.println("Sent: " + sent);
//                    }
//
////                    System.out.print("Do you want to reply [y/n] : ");
////                    String ans = reader.readLine();
//                    if (replytoAll) {
//
//
//                        msg.setFrom(new InternetAddress("pearlapp@pearl-rail.com"));
//                        InternetAddress[] toAddresses = {new InternetAddress("pearlapp@pearl-rail.com")};
//
//
//                        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//
//                        msg = (MimeMessage) tempmessage.reply(false);
////                        msg.setFrom(new InternetAddress(to));
//                        msg.setText("Thanks this is a body");
//                        msg.setReplyTo(tempmessage.getReplyTo());
//
//                        //Μessage Subject
//                        msg.setSubject(subject);
//                        // Send the message by authenticating the SMTP server
//                        // Create a Transport instance and call the sendMessage
//                        Transport t = session.getTransport("smtp");
//                        try {
//                            //connect to the smpt server using transport instance
//                            //change the user and password accordingly
//                            t.connect(USER, PASSWORD);
//                            t.sendMessage(msg,
//                                    msg.getAllRecipients());
//                        } finally {
//                            t.close();
//                        }
//                        System.out.println("message replied successfully ....");
//
//                        // close the store and folder objects
//                        folder.close(false);
//                        store.close();
//                    }
//                }//end of for loop
//                // close the store and folder objects
////                folder.close(false);
////                store.close();
//            }
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void send1() {
//
//        Date date = null;
//        Properties properties = System.getProperties();
//
//        properties.setProperty("mail.smtp.auth", "true");
//        properties.setProperty("mail.smtp.starttls.enable", "true");
//        properties.setProperty("mail.smtp.host", "smtp.office365.com");
//        properties.setProperty("mail.smtp.port", "587");
//
//
//        properties.put("mail.store.protocol", "pop3");
//        properties.put("mail.pop3s.host", "outlook.office365.com");
//        properties.put("mail.pop3s.port", "995");
//        properties.put("mail.pop3.starttls.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
////            properties.put("mail.smtp.host", "relay.jangosmtp.net");
////            properties.put("mail.smtp.port", "25");
//        Session session = Session.getDefaultInstance(properties);
//        // session.setDebug(true);
//        try {
//            // Get a Store object and connect to the current host
//            Store store = session.getStore("pop3s");
//            store.connect("outlook.office365.com", "pearlapp@pearl-rail.com",
//                    "Qud44097");//change the user and password accordingly
//
//            Folder folder = store.getFolder("inbox");
//            if (!folder.exists()) {
//                System.out.println("inbox not found");
//                System.exit(0);
//            }
//            folder.open(Folder.READ_ONLY);
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    System.in));
//
//            Message[] messages = folder.getMessages();
//            if (messages.length != 0) {
//
//                for (int i = 0, n = messages.length; i < n; i++) {
//                    Message message = messages[i];
//                    message.setSubject("Tsotzolas");
//                    date = message.getSentDate();
//                    // Get all the information from the message
//                    String from = InternetAddress.toString(message.getFrom());
//                    if (from != null) {
//                        System.out.println("From: " + from);
//                    }
//                    String replyTo = InternetAddress.toString(message
//                            .getReplyTo());
//                    if (replyTo != null) {
//                        System.out.println("Reply-to: " + replyTo);
//                    }
//                    String to = InternetAddress.toString(message
//                            .getRecipients(Message.RecipientType.TO));
//                    if (to != null) {
//                        System.out.println("To: " + to);
//                    }
//
//                    String subject = message.getSubject();
//                    if (subject != null) {
//                        System.out.println("Subject: " + subject);
//                    }
//                    Date sent = message.getSentDate();
//                    if (sent != null) {
//                        System.out.println("Sent: " + sent);
//                    }
//                    if (true) {
//                        Message replyMessage = new MimeMessage(session);
//                        replyMessage = (MimeMessage) message.reply(false);
//                        replyMessage.setFrom(new InternetAddress(to));
//                        replyMessage.setText("Thanks");
//                        replyMessage.setReplyTo(message.getReplyTo());
//
//                        // Send the message by authenticating the SMTP server
//                        // Create a Transport instance and call the sendMessage
//                        Transport t = session.getTransport("smtp");
//                        try {
//                            //connect to the smpt server using transport instance
//                            //change the user and password accordingly
//                            t.connect("pearlapp@pearl-rail.com", "Qud44097");
//                            t.sendMessage(replyMessage,
//                                    replyMessage.getAllRecipients());
//                        } finally {
//                            t.close();
//                        }
//                        System.out.println("message replied successfully ....");
//
//                        // close the store and folder objects
//                        folder.close(false);
//                        store.close();
//
//                    }
//                }//end of for loop
//
//            } else {
//                System.out.println("There is no msg....");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}
