package com.abproject.athsample.data.mail

import java.util.*
import javax.activation.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import kotlin.collections.ArrayList

/**
 * Created by Abolfazl on 5/19/21
 */
data class Mail(
    var user: String,
    var password: String,
    var to: ArrayList<String>,
    var from: String,
    var port: String = "465",
    var sport: String = "465",
    var host: String = "smtp.gmail.com",
    var subject: String,
    var body: String,
    var auth: Boolean = true,
    var debuggable: Boolean = false,
    var multiPart: Multipart = MimeMultipart()
) : Authenticator() {

    fun createMailcapCommandMap() {
        val mailCapCommandMap: MailcapCommandMap = CommandMap
            .getDefaultCommandMap() as MailcapCommandMap
        mailCapCommandMap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html")
        mailCapCommandMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml")
        mailCapCommandMap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain")
        mailCapCommandMap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed")
        mailCapCommandMap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822")
        CommandMap.setDefaultCommandMap(mailCapCommandMap)
    }

    fun setUsernameAndPasswordForMail(
        username: String,
        password: String
    ) {
        this.user = username
        this.password = password
    }

    fun sendEmail(): Boolean {
        val properties: Properties = initializeProperties()
        if (
            user.isNotEmpty() && password.isNotEmpty()
            && to.isNotEmpty() && from.isNotEmpty()
            && subject.isNotEmpty() && body.isNotEmpty()
        ) {
            val session: Session = Session.getInstance(properties, this)
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(from))

            val addressTo = arrayOfNulls<InternetAddress>(to.size)
            for (i in to.indices) {
                addressTo[i] = InternetAddress(to[i].toString())
            }

            message.setRecipients(MimeMessage.RecipientType.TO, addressTo)
            message.subject = subject
            message.sentDate = Date()

            val messageBodyPart: BodyPart = MimeBodyPart()
            messageBodyPart.setText(body)
            multiPart.addBodyPart(messageBodyPart)

            message.setHeader("X-Priority", "1")

            message.setContent(multiPart)
            Transport.send(message)
            return true
        } else
            return false

    }

    fun addAttachment(fileName: String) {
        val messageBodyPart: BodyPart = MimeBodyPart()
        val source: DataSource = FileDataSource(fileName)
        messageBodyPart.dataHandler = DataHandler(source)
        messageBodyPart.fileName = fileName
        multiPart.addBodyPart(messageBodyPart)
    }

    fun initializeProperties(): Properties {
        val properties = Properties()
        properties["mail.smtp.host"] = host

        if (debuggable)
            properties["mail.debug"] = true

        if (auth)
            properties["mail.smtp.auth"] = true

        properties["mail.smtp.port"] = port
        properties["mail.smtp.socketFactory.port"] = sport
        properties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        properties["mail.smtp.socketFactory.fallback"] = false
        return properties
    }

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(user, password)
    }
}