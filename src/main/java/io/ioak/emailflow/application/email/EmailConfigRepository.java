package io.ioak.emailflow.application.email;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmailConfigRepository extends MongoRepository<EmailConfig, String> {
        List<EmailConfig> findAllByProjectId(String projectId);

        EmailConfig findByReference(String reference);
}
