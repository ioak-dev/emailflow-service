package io.ioak.emailflow.config.mongo;

import com.google.common.base.Strings;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories("io.ioak.emailflow")
public class MongoConfiguration {
    @Value("${spring.data.mongodb.uri}")
    String mongoUri;

    @Value("${spring.data.mongodb.database}")
    String mongoDefaultDb;

    @Bean
    public MongoClient mongoClient() {
        String environmentUrl = Strings.isNullOrEmpty(System.getenv("MONGODB_URI")) ? mongoUri : System.getenv("MONGODB_URI");

        MongoClientURI uri = new MongoClientURI(environmentUrl);
        return new MongoClient(uri);
    }

    @Bean
    @Autowired
    public MongoDbFactory mongoDbFactory(MongoClient mongoClient) {
        String defaultDatabaseName = Strings.isNullOrEmpty(System.getenv("MONGODB_DB")) ? mongoDefaultDb : System.getenv("MONGODB_DB");
        return new MultispaceMongoDbFactory(mongoClient, defaultDatabaseName);
    }


}
