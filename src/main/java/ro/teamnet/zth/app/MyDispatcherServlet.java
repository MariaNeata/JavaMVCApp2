package ro.teamnet.zth.app;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MN on 5/6/2015.
 */
public class MyDispatcherServlet extends HttpServlet {
    Map<String, MethodAttributes> allowedMethods = new HashMap<String, MethodAttributes>();

    private void dispatchReply(String method, HttpServletRequest req, HttpServletResponse resp) {

        try {
            Object r = dispatch(req, resp);
            reply(r, req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DispatchException de) {
            try {
                sendException(de, resp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void sendException(DispatchException de, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.printf("Nu s-a mapat url");

    }


    private Object dispatch(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String path = req.getPathInfo();

        MethodAttributes methodAttributes = allowedMethods.get(path);
        if (methodAttributes != null) {
            String controllerClass = methodAttributes.getControllerClass();
            Class<?> controller = Class.forName(controllerClass);
            Object newControllerInstance = controller.newInstance();
            String methodName = methodAttributes.getMethodName();
            Method method = controller.getMethod(methodName, methodAttributes.getMethodParameterTypes());
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            if (parameterAnnotations.length > 0) {
                MyRequestParam annotation = (MyRequestParam) parameterAnnotations[0][0];
                List<String> methodParamValues = new ArrayList<>();
                String valOfParamName = req.getParameter(annotation.paramName());
                methodParamValues.add(valOfParamName);
                return method.invoke(newControllerInstance, methodParamValues.toArray(new String[0]));
            } else return method.invoke(newControllerInstance);
        }

        /*if (path.startsWith("/employees")) {
            if (path.startsWith("/employees/one")) {
                EmployeeController ec = new EmployeeController();
                return ec.getOneEmployee();

            } else
            {
                EmployeeController employeeController = new EmployeeController();
                String allEmployees = employeeController.getAllEmployees();
                return allEmployees;
            }
        }
        if (path.startsWith("/departments")) {
            DepartmentController dc = new DepartmentController();
            String allDepartments = dc.getAllDepartments();
            return allDepartments;
        }
        if (path.startsWith("/jobs")) {
            JobController dc = new JobController();
            String allJobs = dc.getAllJobs();
            return allJobs;
        }*/

        throw new DispatchException();


    }

    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        //out.printf(r.toString());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(r);
        out.write(json);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          /*Delegate to someone (an application controller) */
        dispatchReply("GET", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       /*Delegate to someone (an application controller) */
        dispatchReply("POST", req, resp);
    }

    @Override
    public void init() throws ServletException {
        try {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.teamnet.zth.app.controller");

            allowedMethods = getAllowedMethods(classes);
            System.out.println(allowedMethods);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, MethodAttributes> getAllowedMethods(Iterable<Class> classes) {
        for (Class controller : classes) {
            if (controller.isAnnotationPresent(MyController.class)) {
                MyController myCtrlAn = (MyController) controller.getAnnotation(MyController.class);
                String controllerUrlPath = myCtrlAn.urlPath();
                Method[] controllerMethods = controller.getMethods();
                for (Method controllerMethod : controllerMethods) {
                    if (controllerMethod.isAnnotationPresent(MyRequestMethod.class)) {
                        MyRequestMethod myRqAn = (MyRequestMethod) controllerMethod.getAnnotation(MyRequestMethod.class);
                        String key = controllerUrlPath + myRqAn.urlPath();
                        MethodAttributes methodAttributes = new MethodAttributes();
                        methodAttributes.setControllerClass(controller.getName());
                        methodAttributes.setMethodName(controllerMethod.getName());
                        methodAttributes.setMethodType(myRqAn.methodType());
                        methodAttributes.setMethodParameterTypes(controllerMethod.getParameterTypes());
                        allowedMethods.put(key, methodAttributes);
                    }

                }


            }
        }
        return allowedMethods;
    }


}
