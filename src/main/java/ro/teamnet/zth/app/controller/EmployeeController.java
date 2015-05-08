package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.app.MyDispatcherServlet;
import ro.teamnet.zth.app.dao.EmployeeDao;
import ro.teamnet.zth.app.domain.Employee;
import ro.teamnet.zth.app.service.EmployeeServiceImpl;

import java.util.List;

/**
 * Created by MN on 5/6/2015.
 */
@MyController(urlPath = "/employees")
public class EmployeeController {


    @MyRequestMethod(urlPath = "/all")
    public List<Employee> getAllEmployees() {
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        return employeeService.findAllEmployees();
    }

    @MyRequestMethod(urlPath = "/one")
    public Employee getOneEmployee(@MyRequestParam(paramName="idEmployee") String idEmployee) {
        EmployeeServiceImpl employee = new EmployeeServiceImpl();
        return employee.findOneEmployee(Integer.parseInt(idEmployee));

    }
}
