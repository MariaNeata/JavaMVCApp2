package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.app.dao.DepartmentDao;
import ro.teamnet.zth.app.domain.Department;

import java.util.List;

/**
 * Created by MN on 5/6/2015.
 */
@MyController(urlPath = "/departments")
public class DepartmentController {
    @MyRequestMethod(urlPath = "/all")
    public List<Department> getAllDepartments() {
        DepartmentDao departmentDao = new DepartmentDao();
        return departmentDao.getAllDepartments();
    }

    @MyRequestMethod(urlPath = "/one")
    public Department getOneDepartment() {
        Department department = new Department();
        department.setId(5);
        department.setDepartmentName("IT");
        return department;
    }

}
