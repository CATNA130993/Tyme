package core;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Common {

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss_SSS");
        String systime = sdf.format(new Date());
        systime = systime.replace(":", "");
        systime = systime.replace("-", "");
        return systime;
    }

    public static void sendEmailReport(String title, int pass, int fail, List<String> passScenarios, List<String> failScenarios) throws Exception {
        final String username = "catnademo@gmail.com";
        final String password = "ikbipndfotkmhbrz";
        String content = null;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
//
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("catna@yopmail.com"));
            message.setSubject("Automation Report " + title);

            BodyPart messageBodyPart = new MimeBodyPart();
            content = "Please see the automated report \n";
            content = content + " *** Scenario number pass: " + pass;
            for (String passScenario : passScenarios) {
                content = content + "\n" + " ==== " + passScenario + "\n";
            }
            content = content + "\n\n";
            content = content + " *** Scenario number fail: " + fail;
            for (String failScenario : failScenarios) {
                content = content + "\n" + " ==== " + failScenario + "\n";
            }
            messageBodyPart.setText(content);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Sending email done !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}