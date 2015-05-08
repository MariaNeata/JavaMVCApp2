package ro.teamnet.zth.app;

import java.util.List;
import ro.teamnet.zth.app.dao.JobDao;
import ro.teamnet.zth.app.domain.Job;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JobDaoTest {

    static Job job = new Job();
    JobDao jobDao = new JobDao();

    @Test
    public void aTestInsertDepartment() {
        job.setJobTitle("title");
        job.setMaxSalary(10);
        job.setMinSalary(1);
        job = jobDao.insertJob(job);

        assertEquals(jobDao.getJobById(job.getId()), job);
    }

    @Test
    public void bTestUpdateDepartment() {
        job.setMaxSalary(20);
        job = jobDao.updateJob(job);

        assertEquals(jobDao.getJobById(job.getId()), job);
    }

    @Test
    public void cTestDeleteJob() {
        jobDao.deleteJob(job);
        Job locById = jobDao.getJobById(job.getId());

        assertNull(locById);
    }

    @Test
    public void dGetAllJobs() {
        List<Job> oldLoc = jobDao.getAllJobs();
        //add new job
        job.setJobTitle("title");
        job.setMaxSalary(10);
        job.setMinSalary(1);
        jobDao.insertJob(job);
        List<Job> newLoc = jobDao.getAllJobs();

        assertEquals(oldLoc.size(), newLoc.size()-1);
    }

}
