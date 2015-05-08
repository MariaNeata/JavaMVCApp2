package ro.teamnet.zth.api.em;

import java.util.List;
import ro.teamnet.zth.app.domain.Department;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Diana.Diaconu on 4/22/2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntityManagerImplTest {

    EntityManagerImpl entityManager = new EntityManagerImpl();
    static Department dep = new Department();

    @Test
    public void aTestInsert() {
        dep.setDepartmentName("test unitar");
        dep = entityManager.insert(dep);

        assertEquals(entityManager.findById(Department.class, dep.getId()), dep);
    }

    @Test
    public void bTestUpdate() {
        dep.setDepartmentName("test unitar2");
        dep = entityManager.update(dep);

        assertEquals(entityManager.findById(Department.class, dep.getId()), dep);
    }

    @Test
    public void cTestDelete() {
        entityManager.delete(dep);
        Department depById = entityManager.findById(Department.class, dep.getId());

        assertNull(depById);
    }

    @Test
    public void dFindAll() {
        List<Department> oldDeps = entityManager.findAll(Department.class);
        //add new dep
        dep.setDepartmentName("test unitar");
        dep = entityManager.insert(dep);
        List<Department> newDeps = entityManager.findAll(Department.class);

        assertEquals(oldDeps.size(), newDeps.size() - 1);
    }

}

