package io.ioak.emailflow.space;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SpaceService {

    Space create(DatasetResource.SpaceResource entity);

    Space update(String name, DatasetResource.SpaceResource entity);

    Space archive(String name);

    Space delete(Space entity);

    Optional<Space> findByName(String name);

    List<Space> listAll();

    Page<Space> list(Pageable pageable);

    boolean exists(String name);
}
