package io.ioak.emailflow.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JwtResorce {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserData {
        private UserResource data;
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResource {
        private String _id;
        private String firstName;
        private String lastName;
        private String email;
        private String token;
    }
}
