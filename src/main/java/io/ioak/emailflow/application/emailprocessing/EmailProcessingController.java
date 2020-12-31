package io.ioak.emailflow.application.emailprocessing;


import io.ioak.emailflow.application.apikey.Apikey;
import io.ioak.emailflow.application.apikey.ApikeyRepository;
import io.ioak.emailflow.application.apikey.ApikeyScope;
import io.ioak.emailflow.application.email.EmailServer;
import io.ioak.emailflow.application.email.EmailServerRepository;
import io.ioak.emailflow.application.project.Project;
import io.ioak.emailflow.application.project.ProjectRepository;
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
@RequestMapping("/email/{spaceId}")
@Slf4j
public class EmailProcessingController {

    @Autowired
    private MailProcessor mailProcessor;

    @Autowired
    private SpaceHolder spaceHolder;

    @Autowired
    private EmailServerRepository emailServerRepository;

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ApikeyRepository apikeyRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @ApiOperation(value = "Create and update a EmailConfig",response = Template.class)
    @PostMapping("/{projectReference}/{serverReference}")
    public void sendMail(@PathVariable String projectReference,
                                      @PathVariable String serverReference,
                                      @RequestBody EmailServerResource resource) {
        String inputKey = "123";
        Project project = projectRepository.findByReference(projectReference);
        EmailServer emailServer = emailServerRepository.findByReference(serverReference);
        if (isAuthorized(inputKey, project.getId(), emailServer.getId())) {
            mailProcessor.send(resource, emailServer);
        }
    }

    @ApiOperation(value = "Create and update a EmailConfig",response = Template.class)
    @PostMapping("/{projectReference}/{serverReference}/{templatereference}")
    public void sendMailWithTemplate(@PathVariable String projectReference,
                                                  @PathVariable String serverReference,
                                                  @PathVariable String templatereference,
                                                  @RequestBody EmailServerTemplateResource resource) {
        String inputKey = "123";
        Project project = projectRepository.findByReference(projectReference);
        EmailServer emailServer = emailServerRepository.findByReference(serverReference);
        if (isAuthorized(inputKey, project.getId(), emailServer.getId())) {
            Template template = templateRepository.findByReference(templatereference);
            mailProcessor.sendWithTemplate(resource, emailServer,
                    template.getSubject(), template.getBody());
        }

    }
    
    private boolean isAuthorized(String key, String projectId, String serverId) {

        Apikey apikey = apikeyRepository.findByKey(key);

        if (apikey == null
                || (apikey.getScope().equals(ApikeyScope.PROJECT) && !apikey.getDomainId().equals(projectId))
                || (apikey.getScope().equals(ApikeyScope.SERVER) && !apikey.getDomainId().equals(serverId))
        ) {
            return true;
        }

        return true;
    }
}
