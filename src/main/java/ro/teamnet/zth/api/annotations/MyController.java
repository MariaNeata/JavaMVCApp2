package ro.teamnet.zth.api.annotations;

import java.lang.annotation.*;

/**
 * Created by MN on 5/6/2015.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyController {
    String urlPath();
}


