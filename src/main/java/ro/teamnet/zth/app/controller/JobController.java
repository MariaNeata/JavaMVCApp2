package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by MN on 5/6/2015.
 */
@MyController(urlPath = "/jobs")
public class JobController {
    @MyRequestMethod(urlPath = "/all")
    public String getAllJobs(){
        return "All jobs";
    }
    @MyRequestMethod(urlPath = "/one")
    public String getOneJob()
    {
        return "allJobs";
    }
}
