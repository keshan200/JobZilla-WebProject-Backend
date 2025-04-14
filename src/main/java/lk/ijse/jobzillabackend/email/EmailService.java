package lk.ijse.jobzillabackend.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String userName) {

        String subject = "🚀 Welcome to JobZilla, " + userName + "!";
        String body = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            line-height: 1.6;\n" +
                "            color: #333333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #ffffff;\n" +
                "        }\n" +
                "        .header {\n" +
                "            background-color: #0050a0;\n" +
                "            color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "            border-radius: 5px 5px 0 0;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            background-color: #f9f9f9;\n" +
                "            border-left: 1px solid #e0e0e0;\n" +
                "            border-right: 1px solid #e0e0e0;\n" +
                "        }\n" +
                "        .section {\n" +
                "            margin-bottom: 20px;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 5px rgba(0,0,0,0.05);\n" +
                "        }\n" +
                "        .section-title {\n" +
                "            font-size: 18px;\n" +
                "            font-weight: bold;\n" +
                "            margin-bottom: 10px;\n" +
                "            color: #0050a0;\n" +
                "            display: flex;\n" +
                "            align-items: center;\n" +
                "        }\n" +
                "        .section-title span {\n" +
                "            margin-right: 10px;\n" +
                "        }\n" +
                "        ul {\n" +
                "            padding-left: 20px;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        li {\n" +
                "            margin-bottom: 5px;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            background-color: #0050a0;\n" +
                "            color: #ffffff;\n" +
                "            padding: 10px 20px;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 4px;\n" +
                "            font-weight: bold;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            background-color: #f0f0f0;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "            font-size: 14px;\n" +
                "            color: #666666;\n" +
                "            border-radius: 0 0 5px 5px;\n" +
                "            border-top: 3px solid #0050a0;\n" +
                "        }\n" +
                "        .divider {\n" +
                "            height: 1px;\n" +
                "            background-color: #e0e0e0;\n" +
                "            margin: 15px 0;\n" +
                "        }\n" +
                "        .contact-info {\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            margin-top: 10px;\n" +
                "        }\n" +
                "        .contact-item {\n" +
                "            margin: 0 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Welcome to JobZilla!</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Dear " + userName + ",</p>\n" +
                "            <p>Thank you for joining JobZilla. We're delighted to welcome you to our platform.</p>\n" +
                "            <p>Your account has been successfully created and is now ready to use. You can start exploring job opportunities tailored to your profile and preferences immediately.</p>\n" +
                "            \n" +
                "            <div class=\"section\">\n" +
                "                <div class=\"section-title\"><span>📋</span> Next Steps</div>\n" +
                "                <ul>\n" +
                "                    <li>Complete your professional profile</li>\n" +
                "                    <li>Set your job preferences</li>\n" +
                "                    <li>Browse our latest openings</li>\n" +
                "                </ul>\n" +
                "                <a href=\"https://www.jobzilla.com/dashboard\" class=\"button\">Get Started</a>\n" +
                "            </div>\n" +
                "            \n" +
                "            <div class=\"section\">\n" +
                "                <div class=\"section-title\"><span>💼</span> Career Resources</div>\n" +
                "                <ul>\n" +
                "                    <li>Resume templates</li>\n" +
                "                    <li>Interview preparation guides</li>\n" +
                "                    <li>Salary negotiation tips</li>\n" +
                "                </ul>\n" +
                "                <a href=\"https://www.jobzilla.com/resources\" class=\"button\">Explore Resources</a>\n" +
                "            </div>\n" +
                "            \n" +
                "            <div class=\"section\">\n" +
                "                <div class=\"section-title\"><span>📱</span> Stay Connected</div>\n" +
                "                <p>Download our mobile app to receive instant notifications about matching positions.</p>\n" +
                "                <div style=\"display: flex; justify-content: center;\">\n" +
                "                    <a href=\"https://apps.apple.com/jobzilla\" style=\"margin-right: 10px;\">App Store</a>\n" +
                "                    <a href=\"https://play.google.com/store/apps/jobzilla\">Google Play</a>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            \n" +
                "            <p>If you have any questions or need assistance, please don't hesitate to contact our support team at <a href=\"mailto:support@jobzilla.com\">support@jobzilla.com</a>.</p>\n" +
                "            \n" +
                "            <p>We look forward to helping you advance your career.</p>\n" +
                "            \n" +
                "            <p>Best regards,<br>The JobZilla Team</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <div><strong>JobZilla, Inc.</strong> | Connect. Apply. Succeed. ✓</div>\n" +
                "            <div class=\"divider\"></div>\n" +
                "            <div class=\"contact-info\">\n" +
                "                <div class=\"contact-item\">📞 (555) 123-4567</div>\n" +
                "                <div class=\"contact-item\">🌐 www.jobzilla.com</div>\n" +
                "                <div class=\"contact-item\">📍 123 Career Avenue, Suite 500</div>\n" +
                "            </div>\n" +
                "            <div class=\"divider\"></div>\n" +
                "            <div style=\"font-size: 12px; margin-top: 10px;\">\n" +
                "                © 2025 JobZilla. All rights reserved.<br>\n" +
                "                <a href=\"https://www.jobzilla.com/privacy\">Privacy Policy</a> | \n" +
                "                <a href=\"https://www.jobzilla.com/terms\">Terms of Service</a> | \n" +
                "                <a href=\"https://www.jobzilla.com/unsubscribe\">Unsubscribe</a>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

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


    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendJobPostConfirmationEmail(String toEmail, String userName, String jobTitle, String companyName, String jobId) {
        String subject = "🎉 Your Job Posting is Live on JobZilla!";
        String body = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            line-height: 1.6;\n" +
                "            color: #333333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #ffffff;\n" +
                "        }\n" +
                "        .header {\n" +
                "            background-color: #0050a0;\n" +
                "            color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "            border-radius: 5px 5px 0 0;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            background-color: #f9f9f9;\n" +
                "            border-left: 1px solid #e0e0e0;\n" +
                "            border-right: 1px solid #e0e0e0;\n" +
                "        }\n" +
                "        .section {\n" +
                "            margin-bottom: 20px;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 15px;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 5px rgba(0,0,0,0.05);\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            background-color: #0050a0;\n" +
                "            color: #ffffff;\n" +
                "            padding: 10px 20px;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 4px;\n" +
                "            font-weight: bold;\n" +
                "            margin: 10px 0;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            background-color: #f0f0f0;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "            font-size: 14px;\n" +
                "            color: #666666;\n" +
                "            border-radius: 0 0 5px 5px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Job Posted Successfully!</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Dear " + userName + ",</p>\n" +
                "            <p>Thank you for posting a job on JobZilla. Your job listing is now live and available for candidates to view.</p>\n" +
                "            \n" +
                "            <div class=\"section\">\n" +
                "                <p><strong>Job Title:</strong> " + jobTitle + "</p>\n" +
                "                <p><strong>Company:</strong> " + companyName + "</p>\n" +
                "                <p><strong>Job ID:</strong> " + jobId + "</p>\n" +
                "            </div>\n" +
                "            \n" +
                "            <p>You can manage your job postings, edit details, or track applications from your dashboard.</p>\n" +
                "            <a href=\"https://www.jobzilla.com/jobs/" + jobId + "\" class=\"button\">View Job Posting</a>\n" +
                "            <a href=\"https://www.jobzilla.com/dashboard\" class=\"button\">Manage Jobs</a>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Thank you for choosing JobZilla. We’re here to help you find the best talent for your team!</p>\n" +
                "            <p>JobZilla Team</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        sendHtmlEmail(toEmail, subject, body);
    }

}
