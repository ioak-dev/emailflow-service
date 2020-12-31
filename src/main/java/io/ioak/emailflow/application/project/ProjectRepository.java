package io.ioak.emailflow.application.project;

import io.ioak.emailflow.application.email.EmailServer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
    Project findByReference(String reference);
}
