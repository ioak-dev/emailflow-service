package io.ioak.emailflow.space;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping({"/api/common/space"})
@Api(value = "space", description = "Space-related operations")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private SpaceHolder spaceHolder;

    @ApiOperation(value = "Provide list of Space",response = Space.class)
    @GetMapping("/all")
    public List<Space> listAll() {
        return spaceService.listAll();
    }

    @ApiOperation(value = "Provide page of Space",response = Space.class)
    @GetMapping
    public Page<Space> list(Pageable pageable) {
        return spaceService.list(pageable);
    }

    @ApiOperation(value = "Provide Space by name",response = Space.class)
    @GetMapping("/{name}")
    protected ResponseEntity<?> getbyName(@PathVariable String name) {
        Space space = spaceService.findByName(name).orElse(null);
        return ResponseEntity.ok().body(space);
    }

    @ApiOperation(value = "Create a Space",response = Space.class)
    @PostMapping
    protected ResponseEntity<?> create(@RequestBody DatasetResource.SpaceResource entity) {
        if (spaceService.exists(entity.getName())) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok().body(spaceService.create(entity));
    }

    @ApiOperation(value = "Update a Space",response = Space.class)
    @PutMapping("/{name}/update")
    protected ResponseEntity<?> update(@PathVariable String name, @RequestBody DatasetResource.SpaceResource entity) {
        return ResponseEntity.ok().body(spaceService.update(name, entity));
    }

    @ApiOperation(value = "Archive a space",response = Space.class)
    @PutMapping("/{name}/archive")
    protected ResponseEntity<?> archive(@PathVariable String name) {
        return ResponseEntity.ok().body(spaceService.archive(name));
    }

    @ApiOperation(value = "Delete a space")
    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        spaceService.delete(spaceService.findByName(name).get());
        return ResponseEntity.noContent().build();
    }

}
