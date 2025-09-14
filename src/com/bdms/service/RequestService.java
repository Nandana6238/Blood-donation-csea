import com.bdms.dao.RequestDAO;
import com.bdms.dao.DonorDAO;
import com.bdms.model.Request;
import com.bdms.model.Donor;
import java.util.List;

public class RequestService {
    private RequestDAO requestDAO = new RequestDAO();
    private DonorDAO donorDAO = new DonorDAO();
    private DonorService donorService = new DonorService();

    public List<Request> getAllRequests() {
        return requestDAO.getAllRequests();
    }
 
    public void showRequestHistoryWithEligibleDonors() {
        List<Request> requests = requestDAO.getAllRequests();
        for (Request r : requests) {
            System.out.println("Request: " + r);
            List<Donor> donors = donorDAO.searchByBloodAndCity(r.getBloodGroup(), r.getCity());
            for (Donor d : donors) {
                boolean eligible = donorService.isEligibleToDonate(d);
                System.out.println("  -> " + d.getName() +
                        (eligible ? "  Eligible" : "  Not Eligible"));
            }
        }
    }
}

