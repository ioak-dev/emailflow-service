package io.ioak.emailflow.application.template;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Setter
@Getter
@NoArgsConstructor
@Document(collection = "template")
public class Template {

    private String id;
    private String name;
    private String reference;
    private String subject;
    private String body;
    private String projectId;
}
