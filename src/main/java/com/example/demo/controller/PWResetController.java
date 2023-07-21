package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.user.User;
import com.example.demo.web.UserNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Controller
public class PWResetController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @GetMapping("/password_reset")
    public String showForgotPWForm(){
        return "password_reset_form";
    }

    @PostMapping("/password_reset")
    public String generatePasswordResetEmail(HttpServletRequest request, Model model){
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        try {
            userService.updateResetPasswordToken(token, email);
            String siteURL = request.getRequestURL().toString();
            String resetLink = siteURL.replace(request.getServletPath(), "") + "/password_reset/new_password?token=" + token;
            sendEmail(email, resetLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Don't forget to check your spam folder.");

        } catch (UserNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "password_reset_form";
    }

    @GetMapping("/password_reset/new_password")
    public String resetPasswordForm(@Param(value = "token") String token, Model model){
        Optional<User> user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        if(user.isEmpty()){
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        return "new_password_form";
    }

    @PostMapping("/password_reset/new_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        System.out.println("submitting new form");
        Optional<User> user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user.isEmpty()) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userService.updatePassword(user.get(), password);
            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "message";
    }

    private void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("jobbxapp@gmail.com", "Big Fat Ass Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
