package app.entities;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Data // Opretter vores getter og setter
@Entity // Sikre at JPA ved hvad denne klasse er.
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // laver vores unikke id.
    private long id;
    @Column(nullable = false, unique = true)
    private String trackingNumber;
    @Column(nullable = false)
    private String senderName;
    @Column(nullable = false)
    private String receiverName;

    @Enumerated(EnumType.STRING) // Enum gemmes som string i databasen
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;
    @Column(nullable = false)
    private LocalDateTime updated;
}
