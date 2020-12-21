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
@Document(collection = "server")
public class EmailServer {

    private String id;
    private String name;
    private String reference;
    private String sender;
    private String host;
    private String port;
    private String password;
    private String projectId;
}
