package io.ioak.emailflow.application.emailprocessing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailServerTemplateResource {
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private Map<String, String> parameters;
}
