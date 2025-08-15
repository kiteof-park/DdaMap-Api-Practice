package ddamap.respository;

// 프로젝션 인터페이스
public interface NearbyStationBase {
    String getStationId();
    String getName();
    double getLatitude();
    double getLongitude();
    double getDistance();
}

