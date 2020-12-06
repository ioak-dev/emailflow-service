package io.ioak.emailflow.space;

import com.google.common.base.Strings;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
public class SpaceHolder {

    @Value("${spring.data.mongodb.database}")
    private String defaultSpaceDB;

    private String spaceId;

    private boolean useAdminDb;

    public SpaceHolder(String spaceId) {
        this.spaceId = spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = Strings.isNullOrEmpty(spaceId) ? defaultSpaceDB : spaceId;
    }

    public String getSpaceId() {
        return useAdminDb ? defaultSpaceDB : this.spaceId;
    }

    public void clear() {
        this.spaceId = null;
        this.useAdminDb = false;
    }
}
