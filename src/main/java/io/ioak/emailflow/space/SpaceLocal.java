package io.ioak.emailflow.space;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "config")
public class SpaceLocal {

    @Id
    private String id;
    private String language;
}
