package ro.teamnet.zth.app;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import ro.teamnet.zth.app.dao.EmployeeDao;
import ro.teamnet.zth.app.domain.Employee;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {

    static Employee employee = new Employee();
    EmployeeDao employeeDao = new EmployeeDao();

    @Test
    public void aTestInsertDepartment() {
        employee.setEmail("mail@mail.com");
        employee.setFirstName("fName");
        employee.setLastName("lName");
        employee.setJobId(2);
        employee.setHireDate(new Date(new java.util.Date().getTime()));
        employee.setSalary(BigDecimal.ONE);
        employee = employeeDao.insertEmployee(employee);

        assertEquals(employeeDao.getEmployeeById(employee.getId()), employee);
    }

    @Test
    public void bTestUpdateDepartment() {
        employee.setHireDate(new Date(new java.util.Date().getTime()));
        employee = employeeDao.updateEmployee(employee);

        assertEquals(employeeDao.getEmployeeById(employee.getId()), employee);
    }

    @Test
    public void cTestDeleteEmployee() {
        employeeDao.deleteEmployee(employee);
        Employee locById = employeeDao.getEmployeeById(employee.getId());

        assertNull(locById);
    }

    @Test
    public void dGetAllEmployees() {
        List<Employee> oldLoc = employeeDao.getAllEmployees();
        //add new employee
        employee.setEmail("mail@mail.com");
        employee.setFirstName("fName_all");
        employee.setLastName("lName_all");
        employee.setJobId(2);
        employee = employeeDao.insertEmployee(employee);
        List<Employee> newLoc = employeeDao.getAllEmployees();

        assertEquals(oldLoc.size(), newLoc.size()-1);
    }
}
