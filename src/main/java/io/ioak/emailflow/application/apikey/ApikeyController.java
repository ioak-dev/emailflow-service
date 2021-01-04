package io.ioak.emailflow.application.apikey;

import io.ioak.emailflow.application.template.Template;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apikey/{spaceId}")
@Slf4j
public class ApikeyController {

    @Autowired
    private ApikeyRepository repository;


    @ApiOperation(value = "view list of EmailConfig",response = Template.class)
    @GetMapping
    public ResponseEntity<List<Apikey>> get() {
        return ResponseEntity.ok(repository.findAll());
    }

    @ApiOperation(value = "view list of Project",response = Template.class)
    @DeleteMapping("/apikey/{apikey}")
    public void deleteByApikey(@PathVariable String apikey) {
        repository.deleteAllByKey(apikey);
    }

    @ApiOperation(value = "Create and update a EmailConfig",response = Template.class)
    @PostMapping
    public ResponseEntity<Apikey> create(@RequestBody Apikey request) {
        // TBD AMAR
//        request.setKey(new UUID().toString());
        return ResponseEntity.ok(repository.save(request));
    }

    @ApiOperation(value = "Delete Template by Id",response = Template.class)
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable String id) {
        repository.deleteById(id);
    }
}
