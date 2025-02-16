package app.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package aPackage;

    @ManyToOne
    @JoinColumn(name = "source_location_id", nullable = false)
    private Location sourceLocation;

    @ManyToOne
    @JoinColumn(name = "destination_location_id", nullable = false)
    private Location destinationLocation;

    @Column(nullable = false)
    private LocalDateTime shipmentDate;
}
