package lk.ijse.jobzillabackend.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String userName) {
        String subject = "🎉 Welcome to JobZilla, " + userName + "!";
        String body = "<h2>Welcome to JobZilla, " + userName + "!</h2>" +
                      "<p>We are excited to have you on board. Start exploring new job opportunities now!</p>" +
                      "<p>🚀 <a href='https://jobzilla.com'>Get Started</a></p>" +
                      "<p>Best regards, <br> The JobZilla Team</p>";

        sendHtmlEmail(toEmail, subject, body);
    }

    private void sendHtmlEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
            System.out.println("Welcome email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
