package io.ioak.emailflow.application.apikey;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Setter
@Getter
@NoArgsConstructor
@Document(collection = "apikey")
public class Apikey {

    private String id;
    private ApikeyScope scope;
    private String domainId;
    private String key;
}
