import java.util.*;

class DonorManager {
    private List<Donor> donors;
    private FileHandler fileHandler;

    DonorManager(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.donors = fileHandler.loadDonors();
    }

    public void viewAllDonors() {
        if (donors.isEmpty()) {
            System.out.println("No donors available.");
        } else {
            for (Donor d : donors) {
                System.out.println(d);
            }
        }
    }

    public void checkEligibility(int age) {
        if (age >= 18 && age <= 60) {
            System.out.println("Eligible for blood donation.");
        } else {
            System.out.println("Not eligible for blood donation.");
        }
    }

    public static void main(String[] args) {
        FileHandler fh = new FileHandler();
        DonorManager dm = new DonorManager(fh);

        dm.viewAllDonors();
        dm.checkEligibility(25);
        dm.checkEligibility(65);
    }
}
