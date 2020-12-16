package io.ioak.emailflow.application.project;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Setter
@Getter
@NoArgsConstructor
@Document(collection = "project")
public class Project {

    private String id;
    private String name;
    private String reference;
    private String description;
}
