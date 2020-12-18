package io.ioak.emailflow.application.email;

import io.ioak.emailflow.application.template.Template;
import io.ioak.emailflow.application.template.TemplateRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email/{spaceId}")
@Slf4j
public class EmailConfigController {

    @Autowired
    private EmailConfigRepository repository;


    @ApiOperation(value = "view list of EmailConfig",response = Template.class)
    @GetMapping
    public ResponseEntity<List<EmailConfig>> get() {
        return ResponseEntity.ok(repository.findAll());
    }

    @ApiOperation(value = "view list of Project",response = Template.class)
    @GetMapping("/id/{id}")
    public ResponseEntity<EmailConfig> getById(@PathVariable String id) {
        return ResponseEntity.ok(repository.findById(id).get());
    }

    @ApiOperation(value = "view list of Project",response = Template.class)
    @GetMapping("/project/id/{id}")
    public ResponseEntity<List<EmailConfig>> getByProjectId(@PathVariable String id) {
        return ResponseEntity.ok(repository.findAllByProjectId(id));
    }

    @ApiOperation(value = "Create and update a EmailConfig",response = Template.class)
    @PostMapping
    public ResponseEntity<EmailConfig> update(@RequestBody EmailConfig request) {
        return ResponseEntity.ok(repository.save(request));
    }

    @ApiOperation(value = "Delete all EmailConfig",response = Template.class)
    @DeleteMapping
    public void deleteAll() {
        repository.deleteAll();
    }

    @ApiOperation(value = "Delete Template by Id",response = Template.class)
    @DeleteMapping(value = "/id")
    public void deleteById(@PathVariable String id) {
        repository.deleteById(id);
    }
}
