package io.ioak.emailflow.application.emailprocessing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailServerResource {
    private String to;
    private String subject;
    private String body;
}
