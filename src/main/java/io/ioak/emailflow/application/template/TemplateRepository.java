package io.ioak.emailflow.application.template;

import io.ioak.emailflow.application.email.EmailConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TemplateRepository extends MongoRepository<Template, String> {
    List<Template> findAllByProjectId(String projectId);
}
