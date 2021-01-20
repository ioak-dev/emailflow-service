package io.ioak.emailflow.config.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.ioak.emailflow.space.SpaceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public class MultispaceMongoDbFactory extends SimpleMongoDbFactory {

    private final String defaultName;

    @Autowired
    private SpaceHolder spaceHolder;

    public MultispaceMongoDbFactory(MongoClient mongo, String defaultDatabaseName) {
        super(mongo, defaultDatabaseName);
        this.defaultName = defaultDatabaseName;
    }

    @Override
    public MongoDatabase getDb() {
        String dbToUse = (spaceHolder.getSpaceId() != null ? spaceHolder.getSpaceId() : this.defaultName);
        return super.getDb(dbToUse);
    }
}
