package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by MN on 5/6/2015.
 */
@MyController(urlPath = "/locations")
public class LocationController {
    @MyRequestMethod(urlPath = "/all")
    public String getAllLocations() {
        return "All Locations";
    }

    @MyRequestMethod(urlPath = "/one")
    public String getOneLocation() {
        return "oneLocation";
    }
}
