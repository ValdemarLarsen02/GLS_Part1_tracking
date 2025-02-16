package app;

import app.DAO.LocationDAO;
import app.DAO.PackageDAO;
import app.DAO.ShipmentDAO;
import app.entities.DeliveryStatus;
import app.entities.Location;
import app.entities.Package;
import app.entities.Shipment;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LocationDAO locationDAO = new LocationDAO();
        PackageDAO packageDAO = new PackageDAO();
        ShipmentDAO shipmentDAO = new ShipmentDAO();

        // Opret og gem nye lokationer
        Location loc1 = new Location();
        loc1.setLatitude(55.6761);
        loc1.setLongitude(12.5683);
        loc1.setAddress("Copenhagen, Denmark");
        locationDAO.save(loc1);

        Location loc2 = new Location();
        loc2.setLatitude(48.8566);
        loc2.setLongitude(2.3522);
        loc2.setAddress("Paris, France");
        locationDAO.save(loc2);

        // Opret og gem en pakke
        Package newPackage = new Package();
        newPackage.setTrackingNumber("TRACK123");
        newPackage.setSenderName("Sender A");
        newPackage.setReceiverName("Receiver B");
        newPackage.setDeliveryStatus(DeliveryStatus.PENDING);
        newPackage.setUpdated(LocalDateTime.now());
        packageDAO.save(newPackage);

        // Opret og gem en forsendelse
        Shipment shipment = new Shipment();
        shipment.setAPackage(newPackage);
        shipment.setSourceLocation(loc1);
        shipment.setDestinationLocation(loc2);
        shipment.setShipmentDate(LocalDateTime.now());
        shipmentDAO.save(shipment);

        // Hent og print data
        Package foundPackage = packageDAO.getPackage("TRACK123");
        System.out.println("🔎 Fundet pakke: " + foundPackage);

        List<Shipment> shipments = shipmentDAO.findAll();
        System.out.println("🚚 Alle forsendelser: " + shipments);

        List<Location> locations = locationDAO.findAll();
        System.out.println("📍 Alle lokationer: " + locations);
    }
}
