package io.ioak.emailflow.config;

import io.ioak.emailflow.application.email.EmailServer;
import io.ioak.emailflow.application.emailprocessing.EmailServerResource;
import io.ioak.emailflow.application.emailprocessing.EmailServerTemplateResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MailProcessor {

    @Async
    public void sendEmailAsync(EmailServerResource resource, EmailServer server) throws MessagingException{
        sendEmail(resource, server);
    }

    public boolean sendEmailSync(EmailServerResource resource, EmailServer server) throws MessagingException{
        return sendEmail(resource, server);
    }

    @Async
    public void sendEmailUsingTemplateAsync(EmailServerTemplateResource resource, EmailServer server,
                                              String subjectTemplate, String bodyTemplate) throws MessagingException {
        sendEmailUsingTemplate(resource, server, subjectTemplate, bodyTemplate);
    }

    public boolean sendEmailUsingTemplateSync(EmailServerTemplateResource resource, EmailServer server,
                                              String subjectTemplate, String bodyTemplate) throws MessagingException {
        return sendEmailUsingTemplate(resource, server, subjectTemplate, bodyTemplate);
    }

    private boolean sendEmailUsingTemplate(EmailServerTemplateResource resource, EmailServer server,
                                    String subjectTemplate, String bodyTemplate) throws MessagingException {

        EmailServerResource emailServerResource = new EmailServerResource();
        emailServerResource.setTo(resource.getTo());
        emailServerResource.setCc(resource.getCc());
        emailServerResource.setBcc(resource.getBcc());
        emailServerResource.setBody(velocityWithStringTemplate(bodyTemplate, resource.getParameters()));
        emailServerResource.setSubject(velocityWithStringTemplate(subjectTemplate, resource.getParameters()));
        emailServerResource.setAsync(resource.isAsync());

        return sendEmail(emailServerResource, server);
    }

    private boolean sendEmail(EmailServerResource resource, EmailServer server) throws MessagingException{

        Properties props = new Properties();
        props.put("mail.smtp.host", server.getHost());
        props.put("mail.smtp.port", server.getPort());
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable",true);

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(server.getSender(), server.getPassword());
                    }
                });

        try{
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(server.getSender()));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(resource.getTo().stream().collect(Collectors.joining(","))));
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(resource.getCc().stream().collect(Collectors.joining(","))));
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(resource.getBcc().stream().collect(Collectors.joining(","))));
            message.setSubject(resource.getSubject());
            message.setText(resource.getBody());

            Transport.send(message);
            log.info("Mail send successfully to :"+resource.getTo());
            return true;
        }catch(SendFailedException e){
            log.error("message could not be sent to recipients"+e);
            throw e;
        }catch(MessagingException e) {
            log.error("Error occured during sending mail"+e);
            throw e;
        }
    }

    public static VelocityEngine getVelocityEngine(){

        VelocityEngine ve = new VelocityEngine();
        Properties props = new Properties();

        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init(props);
        return ve;
    }

    public static String getHtmlByTemplateAndContext(String templateName, Map<String, String> values){

        VelocityEngine ve = getVelocityEngine();

        Template template = ve.getTemplate("template-email/" + templateName, "UTF-8");

        VelocityContext context = new VelocityContext();

        for (Map.Entry<String,String> entry : values.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        StringWriter writer = new StringWriter();
        template.merge( context, writer );
        return  writer.toString();
    }



    private static String velocityWithStringTemplate(String inputTemplate, Map<String, String> templateValues) {
        // Initialize the engine.
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
        //engine.setProperty("runtime.log.logsystem.log4j.logger", LOGGER.getName());
        engine.setProperty(Velocity.RESOURCE_LOADER, "string");
        engine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        engine.addProperty("string.resource.loader.repository.static", "false");
        //  engine.addProperty("string.resource.loader.modificationCheckInterval", "1");
        engine.init();

        // Initialize my template repository. You can replace the "Hello $w" with your String.

        StringResourceRepository repo = (StringResourceRepository) engine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
        repo.putStringResource("template", inputTemplate);


        // Set parameters for my template.
        VelocityContext context = new VelocityContext();
        templateValues.forEach((k, v) -> {
            context.put(k, v);
        });

        // Get and merge the template with my parameters.
        Template template = engine.getTemplate("template");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return  writer.toString();
    }
}
