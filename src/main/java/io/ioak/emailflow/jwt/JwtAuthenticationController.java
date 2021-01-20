package io.ioak.emailflow.jwt;

import io.ioak.emailflow.space.SpaceHolder;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/auth/{spaceId}")
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpaceHolder spaceHolder;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @ApiOperation(value = "Create jwt Token", response = String.class)
    @GetMapping(value = "/session/{token}")
    protected ResponseEntity<?> validateToken(@PathVariable String token, @PathVariable String spaceId) throws Exception {

        String customURL = "http://127.0.0.1:8020/auth/" + "space/" + spaceId + "/session/" + token;

        try {
            ResponseEntity<JwtResorce.UserResource> responseEntity = restTemplate.getForEntity(customURL, JwtResorce.UserResource.class);

            JwtResorce.UserResource userResource = responseEntity.getBody();
            spaceHolder.setSpaceId("emailflow_"+spaceId);
            if (userResource != null) {
                String userId = tokenUtil.extractUserWithSecurityKey(userResource.getToken(), "jwtsecret");
                User user = userRepository.findById(userId).orElse(null);
                if (user != null) {
                    userRepository.save(user);
                }
                //String responseToken = jwtTokenUtil.generateTokenWithUser(userResource);
                JwtResorce.UserResource userResource1 = getUserResource(user, jwtTokenUtil.generateTokenWithUser(userResource));
                return ResponseEntity.ok(getUserData(userResource1));
            }

        } catch (Exception e) {

        }
        return null;
    }

    @ApiOperation(value = "Create jwt Token", response = String.class)
    @GetMapping(value = "/session/appspace/{token}")
    protected ResponseEntity<?> validateTokenAppspace(@PathVariable String token, @PathVariable String spaceId) throws Exception {

        String customURL = "http://127.0.0.1:8020/auth/" + "appspace/" + spaceId + "/session/" + token;

        try {
            ResponseEntity<JwtResorce.UserResource> responseEntity = restTemplate.getForEntity(customURL, JwtResorce.UserResource.class);

            JwtResorce.UserResource userResource = responseEntity.getBody();
            spaceHolder.setSpaceId("emailflow_"+spaceId);
            if (userResource != null) {
                String userId = tokenUtil.extractUserWithSecurityKey(userResource.getToken(), "jwtsecret");
                User user = userRepository.findById(userId).orElse(null);
                if (user == null) {
                    user = new User();
                }
                user.setEmail(userResource.getEmail());
                user.setFirstName(userResource.getFirstName());
                user.setLastName(userResource.getLastName());
                userRepository.save(user);
                JwtResorce.UserResource userResource1 = getUserResource(user, userResource.getToken());
                return ResponseEntity.ok(getUserData(userResource1));
            }

        } catch (Exception e) {

        }
        return null;
    }

    private JwtResorce.UserResource getUserResource(User user, String token) {
        JwtResorce.UserResource userResource = new JwtResorce.UserResource();
        userResource.set_id(user.getId());
        userResource.setFirstName(user.getFirstName());
        userResource.setLastName(user.getLastName());
        userResource.setEmail(user.getEmail());
        userResource.setToken(token);

        return userResource;
    }

    private JwtResorce.UserData getUserData(JwtResorce.UserResource user) {
        JwtResorce.UserData userData = new JwtResorce.UserData();
        userData.setData(user);

        return userData;
    }




}
