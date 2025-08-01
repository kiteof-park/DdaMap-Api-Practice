package ddamap.service;

import ddamap.client.KakaoLocalClient;
import ddamap.dto.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoLocalSearchServiceImpl {

    private final KakaoLocalClient client;

    public Optional<Coordinate> getCoordinate(String query) {
        if (isAddress(query)) {
            return client.getCoordinateByAddress(query);
        }
        return client.getCoordinateByKeyword(query);
    }

    // TODO: 근데 카카오 API는 허준로23만 입력해도 응답 잘 반환하던데 이 경우엔 ..?
        // 허준로23이 /keyword 로 요청됐다가 결과가 없으면 다시 한번  address로 fallback ?
    private boolean isAddress(String query) {
        return query.matches("^(서울(특별시)?\\s?[가-힣]+구\\s?.*)");
    }
}
