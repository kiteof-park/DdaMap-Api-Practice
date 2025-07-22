package ddamap.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kakao.api")
@Getter
@Setter
public class KakaoLocalProperties {
    private String key;
    private String baseUrl;
}
