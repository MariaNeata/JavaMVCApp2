package ro.teamnet.zth.app;

import java.util.List;
import ro.teamnet.zth.app.dao.LocationDao;
import ro.teamnet.zth.app.domain.Location;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Diana.Diaconu on 4/22/2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocationDaoTest {

    static Location location = new Location();
    LocationDao locationDao = new LocationDao();

    @Test
    public void aTestInsertDepartment() {
        location.setCity("Bucuresti test");
        location.setPostalCode("1234");
        location.setStateProvince("Buc test");
        location.setStreetAddress("test adresa");
        location = locationDao.insertLocation(location);

        assertEquals(locationDao.getLocationById(location.getId()), location);
    }

    @Test
    public void bTestUpdateDepartment() {
        location.setStreetAddress("new postal code");
        location = locationDao.updateLocation(location);

        assertEquals(locationDao.getLocationById(location.getId()), location);
    }

    @Test
    public void cTestDeleteLocation() {
        locationDao.deleteLocation(location);
        Location locById = locationDao.getLocationById(location.getId());

        assertNull(locById);
    }

    @Test
    public void dGetAllLocations() {
        List<Location> oldLoc = locationDao.getAllLocations();
        //add new location
        location.setCity("Bucuresti test2");
        location.setPostalCode("1234");
        location.setStateProvince("Buc test2");
        location.setStreetAddress("test adresa2");
        locationDao.insertLocation(location);
        List<Location> newLoc = locationDao.getAllLocations();

        assertEquals(oldLoc.size(), newLoc.size()-1);
    }
}
