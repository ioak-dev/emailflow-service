package io.ioak.emailflow.application.emailprocessing;


import io.ioak.emailflow.application.email.EmailServer;
import io.ioak.emailflow.application.email.EmailServerRepository;
import io.ioak.emailflow.application.template.Template;
import io.ioak.emailflow.application.template.TemplateRepository;
import io.ioak.emailflow.config.MailProcessor;
import io.ioak.emailflow.space.SpaceHolder;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail/{spaceId}")
@Slf4j
public class EmailProcessingController {

    @Autowired
    private MailProcessor mailProcessor;

    @Autowired
    private SpaceHolder spaceHolder;

    @Autowired
    private EmailServerRepository emailServerRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @ApiOperation(value = "Create and update a EmailConfig",response = Template.class)
    @PostMapping("/{projectReference}/{emailConfigReference}")
    public void sendMail(@PathVariable String projectReference,
                                      @PathVariable String emailConfigReference,
                                      @RequestBody EmailServerResource resource) {
        EmailServer emailServer = emailServerRepository.findByReference(emailConfigReference);
        mailProcessor.send(resource, emailServer);
    }

    @ApiOperation(value = "Create and update a EmailConfig",response = Template.class)
    @PostMapping("/{projectReference}/{emailConfigReference}/{templatereference}")
    public void sendMailWithTemplate(@PathVariable String projectReference,
                                                  @PathVariable String emailConfigReference,
                                                  @PathVariable String templatereference,
                                                  @RequestBody EmailServerTemplateResource resource) {
        EmailServer emailServer = emailServerRepository.findByReference(emailConfigReference);
        Template template = templateRepository.findByReference(templatereference);
        mailProcessor.sendWithTemplate(resource, emailServer,
                template.getSubject(), template.getBody());

    }
}
