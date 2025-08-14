package ddamap.domain;

import ddamap.util.GeometryUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "stations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Station {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) private String stationId;              // 대여소 ID - "ST-10"
    @Column(nullable = false) private String locGroup;                              // 대여소그룹명 - "마포구"
    @Column(nullable = false) private int no;                                       // 대여소 번호 - 00108
    @Column(nullable = false) private String name;                                  // 대여소 이름 - "서교동 사거리"
    @Column(nullable = false) private String address;                               // 주소 - "서울특별시 마포구 양화로 93"
    @Column(nullable = false) private double latitude;                              // 대여소 위도
    @Column(nullable = false) private double longitude;                             // 대여소 경도
    @Column(columnDefinition = "geography(Point,4326)") private Point geography;    // PostGIS 컬럼

    @CreationTimestamp @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Station(Long id, String stationId, String locGroup, int no, String name,
                   String address, double latitude, double longitude,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.stationId = stationId;
        this.locGroup = locGroup;
        this.no = no;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        updateGeo();
    }

    @PrePersist
    @PreUpdate
    public void updateGeo() {
        this.geography = GeometryUtils.createPoint(this.longitude, this.latitude);
    }
}
