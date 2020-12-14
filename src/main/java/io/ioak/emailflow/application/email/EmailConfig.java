package io.ioak.emailflow.application.email;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Setter
@Getter
@NoArgsConstructor
@Document(collection = "emailconfig")
public class EmailConfig {

    private String id;
    private String fromEmail;
    private String host;
    private String port;
    private String password;
    private String projectId;
}
