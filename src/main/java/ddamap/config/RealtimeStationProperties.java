package ddamap.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * yml 설정값 바인딩 - 실시간 대여소 정보
 */
@Component
@ConfigurationProperties("bikeseoul.realtime")
@Getter
@Setter
public class RealtimeStationProperties {
    private String key;
    private String baseUrl;
}
