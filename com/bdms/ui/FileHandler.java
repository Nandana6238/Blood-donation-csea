import java.util.*;

class FileHandler {
    public List<Donor> loadDonors() {
        List<Donor> donors = new ArrayList<>();
        donors.add(new Donor("Nayana", 22));
        donors.add(new Donor("Rahul", 35));
        donors.add(new Donor("Meera", 45));
        return donors;
    }
}
