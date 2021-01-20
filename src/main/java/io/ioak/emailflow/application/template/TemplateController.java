package io.ioak.emailflow.application.template;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template/{spaceId}")
@Slf4j
public class TemplateController {

    @Autowired
    private TemplateRepository repository;


    @ApiOperation(value = "view list of Template",response = Template.class)
    @GetMapping
    public ResponseEntity<List<Template>> get() {
        return ResponseEntity.ok(repository.findAll());
    }

    @ApiOperation(value = "view list of Project",response = Template.class)
    @GetMapping("/id/{id}")
    public ResponseEntity<Template> getById(@PathVariable String id) {
        return ResponseEntity.ok(repository.findById(id).get());
    }

    @ApiOperation(value = "view list of Project",response = Template.class)
    @GetMapping("/project/id/{id}")
    public ResponseEntity<List<Template>> getByProjectId(@PathVariable String id) {
        return ResponseEntity.ok(repository.findAllByProjectId(id));
    }


    @ApiOperation(value = "Create and update a Template",response = Template.class)
    @PostMapping
    public ResponseEntity<Template> update(@RequestBody Template request) {
        return ResponseEntity.ok(repository.save(request));
    }

    @ApiOperation(value = "Delete all Template",response = Template.class)
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
