package com.bdms.service;

import com.bdms.dao.RequestDAO;
import com.bdms.model.Request;

import java.time.LocalDate;
import java.util.List;

public class RequestService {
    private RequestDAO requestDAO;

    public RequestService() {
        this.requestDAO = new RequestDAO();
    }

    // Add a new request
    public void addRequest(Request request) {
        requestDAO.addRequest(request);
    }

    // Get all requests
    public List<Request> getAllRequests() {
        return requestDAO.getAllRequests();
    }

    // Update request (city + date)
    public boolean updateRequest(int id, String city, LocalDate newDate) {
        return requestDAO.updateRequest(id, city, newDate);
    }

    // Delete request
    public boolean deleteRequest(int id) {
        return requestDAO.deleteRequest(id);
    }
}
