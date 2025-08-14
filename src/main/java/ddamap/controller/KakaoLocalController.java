package ddamap.controller;

import ddamap.client.KakaoLocalClient;
import ddamap.dto.Coordinate;
import ddamap.service.KakaoLocalSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coordinate")
public class KakaoLocalController {

    private final KakaoLocalSearchServiceImpl searchService;

    @GetMapping
    public ResponseEntity<Coordinate> getCoordinate(@RequestParam String query) {
        return searchService.getCoordinate(query)
                .map(coordinate -> ResponseEntity.ok().body(coordinate))
                .orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping("/address")
//    public ResponseEntity<?> getCoordinateByAddress(@RequestParam String address) {
//        return client.getCoordinateByAddress(address)
//                .map(coordinate -> ResponseEntity.ok().body(coordinate))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // TODO : getCoordinateByKeyword() 구현 및 주소/키워드 검색 판별 전략 구현
//    @GetMapping("/keyword")
//    public ResponseEntity<?> getCoordinateByKeyword(@RequestParam String keyword) {
//        return client.getCoordinateByKeyword(keyword)
//                .map(coordinate -> ResponseEntity.ok().body(coordinate))
//                .orElse(ResponseEntity.notFound().build());
//    }

}
