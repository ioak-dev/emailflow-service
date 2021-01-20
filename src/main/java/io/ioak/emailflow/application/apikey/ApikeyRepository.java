package io.ioak.emailflow.application.apikey;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ApikeyRepository extends MongoRepository<Apikey, String> {
        Apikey findByKey(String key);
        void deleteAllByKey(String key);
}
