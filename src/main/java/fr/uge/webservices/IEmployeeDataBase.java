package fr.uge.webservices;

import java.util.Map;

public interface IEmployeeDataBase {
    IEmployee getEmployee(Long id);
    boolean removeEmployee(Long id);
    boolean addEmployee(IEmployee t);
    Map<Long, IEmployee> getEmployeeMap();
}
