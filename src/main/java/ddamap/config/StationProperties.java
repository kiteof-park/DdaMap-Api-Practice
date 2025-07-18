package ddamap.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * yml설정값 바인딩
 */
@Component
@ConfigurationProperties(prefix = "bikeseoul.api")
@Getter
@Setter
public class StationProperties {
    private String key;
    private String baseUrl;
}
