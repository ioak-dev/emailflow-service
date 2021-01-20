package io.ioak.emailflow.space;

import com.mongodb.MongoClient;
import io.ioak.emailflow.space.aspect.UseAdminDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultSpaceService implements SpaceService {


    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private SpaceHolder spaceHolder;

    @Autowired
    private SpaceLocalRepository configRepository;


    @Override
    public Space create(DatasetResource.SpaceResource entity) {
        Space space = Space.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
        Space response = spaceRepository.save(space);
        if (response != null) {
            spaceHolder.setSpaceId(response.getId());
            configRepository.save(SpaceLocal.builder()
                    .id(entity.getId())
                    .language(entity.getLanguage() != null ? entity.getLanguage() : "en")
                    .build());
            spaceHolder.clear();
        }
        return response;
    }

    @Override
    public Space update(String name, DatasetResource.SpaceResource entity) {
        Space current = spaceRepository.findByName(name).orElse(null);
        if (current != null) {
            current.setDescription(entity.getDescription());
            Space response = spaceRepository.save(current);

            if (response != null) {
                spaceHolder.setSpaceId(response.getId());
                SpaceLocal config = configRepository.findById(response.getId()).get();
                config.setLanguage(entity.getLanguage() != null ? entity.getLanguage() : config.getLanguage());
                configRepository.save(config);
                spaceHolder.clear();
            }
            return response;
        }
        return null;
    }

    @Override
    public Space archive(String name) {
        Space space = spaceRepository.findByName(name).orElse(null);
        if (space !=null) {
            space.setStatus(SpaceStatus.Archive);
            return spaceRepository.save(space);
        }
        return null;
    }

    @Override
    public Space delete(Space space) {
        mongoClient.dropDatabase(space.getName());
        spaceRepository.delete(space);
        return space;
    }

    @Override
    @UseAdminDb
    public Optional<Space> findByName(String name) {
        return spaceRepository.findByName(name);
    }

    @Override
    public List<Space> listAll() {
        return mongoOperations.findAll(Space.class);
    }

    @Override
    public Page<Space> list(Pageable pageable) {
        return spaceRepository.findAll(pageable);
    }

    @Override
    public boolean exists(String name) {
        Space space = spaceRepository.findById(name).orElse(null);
        if (space !=null) {
            return true;
        }

        return false;
    }

}
