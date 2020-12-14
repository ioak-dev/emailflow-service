package io.ioak.emailflow.application.email;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailConfigRepository extends MongoRepository<EmailConfig, String> {

}
