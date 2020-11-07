package fr.uge.webservices;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDataBase implements IEmployeeDataBase{
    private Map<Long, IEmployee> employeeMap = new HashMap<>(); //Long for id
    private long idMap = 0;

    public Map<Long, IEmployee> getEmployeeMap() {
        return Collections.unmodifiableMap(employeeMap);
    }

    public IEmployee getEmployee(Long id){
        return employeeMap.get(id);
    }

    public boolean removeEmployee(Long id){
        return  null != employeeMap.remove(id);
    }

    public boolean addEmployee(IEmployee t){
        idMap++;
        employeeMap.put(idMap, t);
        return true;
    }
}
