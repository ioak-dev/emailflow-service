package io.ioak.emailflow.space;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DatasetResource {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SpaceResource {

        private String id;
        private String name;
        private String description;
        private String language;
        private SpaceStatus status;
    }

}
