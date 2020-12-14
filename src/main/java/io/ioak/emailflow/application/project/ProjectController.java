package io.ioak.emailflow.application.project;

import io.ioak.emailflow.application.template.Template;
import io.ioak.emailflow.space.SpaceHolder;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/{spaceId}")
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private SpaceHolder spaceHolder;


    @ApiOperation(value = "view list of Project",response = Template.class)
    @GetMapping
    public ResponseEntity<List<Project>> get() {
        return ResponseEntity.ok(repository.findAll());
    }

    @ApiOperation(value = "view list of Project",response = Template.class)
    @GetMapping("/id/{id}")
    public ResponseEntity<Project> getById(@PathVariable String id) {
        return ResponseEntity.ok(repository.findById(id).get());
    }


    @ApiOperation(value = "Create and update a Project",response = Template.class)
    @PostMapping
    public ResponseEntity<Project> update(@RequestBody Project request) {
        return ResponseEntity.ok(repository.save(request));
    }

    @ApiOperation(value = "Delete all Project",response = Template.class)
    @DeleteMapping
    public void deleteAll() {
        repository.deleteAll();
    }

    @ApiOperation(value = "Delete Project by Id",response = Template.class)
    @DeleteMapping(value = "/id")
    public void deleteById(@PathVariable String id) {
        repository.deleteById(id);
    }
}
