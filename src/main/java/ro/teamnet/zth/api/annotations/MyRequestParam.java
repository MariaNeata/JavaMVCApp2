package ro.teamnet.zth.api.annotations;

import java.lang.annotation.*;

/**
 * Created by MN on 5/7/2015.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestParam {
    String paramName();
}
