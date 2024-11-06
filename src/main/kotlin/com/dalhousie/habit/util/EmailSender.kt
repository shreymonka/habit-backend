package com.dalhousie.habit.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component
class EmailSender(private val mailSender: JavaMailSender) {

    @Value("\${spring.mail.username}")
    private lateinit var senderEmail: String

    fun sendOtpToMail(otp: String, email: String) {
        val subject = getOtpMailSubject()
        val content = getOtpMailContent(otp)
        sendEmail(email, subject, content)
    }

    private fun getOtpMailSubject() = "One time password for resetting your password"

    private fun getOtpMailContent(otp: String) =
        """
        <p>Your OTP for resetting your password is <strong>$otp</strong>. Please enter this OTP in the application 
        to proceed with resetting your password.</p>
        """.trimIndent()

    private fun sendEmail(email: String, subject: String, content: String) {
        val message = mailSender.createMimeMessage().apply {
            MimeMessageHelper(this).apply {
                setFrom(senderEmail, "Habit")
                setTo(email)
                setSubject(subject)
                setText(content, true)
            }
        }
        mailSender.send(message)
    }
}
