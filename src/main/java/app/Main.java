package app;

import app.DAO.PackageDAO;
import app.entities.DeliveryStatus;
import app.entities.Package;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PackageDAO packageDAO = new PackageDAO();

        //Laver vores pakke
        Package newPackage = new Package();
        newPackage.setTrackingNumber("va881231231211");
        newPackage.setSenderName("Afsender 1");
        newPackage.setReceiverName("Modtager 1");
        newPackage.setDeliveryStatus(DeliveryStatus.PENDING);
        newPackage.setUpdated(LocalDateTime.now());


        //packageDAO.save(newPackage); // gemmer til databasen


        Package foundPackage = packageDAO.getPackage("va88123123121");
        System.out.println("Fundet pakke: " + (foundPackage != null ? foundPackage.toString() : "Ikke fundet!"));


        //Henter en liste med alle pakker
        List<Package> packages = packageDAO.getAllPackages();

        DeliveryStatus newStatus = DeliveryStatus.DELIVERED; // Ny ENUM-status

        // Opdater status
        packageDAO.updateDeliveryStatus("va881231231211", newStatus);

        packageDAO.removePackage("va88123123121");


    }
}