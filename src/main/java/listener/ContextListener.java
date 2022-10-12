package listener;

import config.Config;
import config.HsqlDatabaseConfig;
import config.PostgresqlDatabaseConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebListener;
import orders.OrdersFormServlet;
import orders.OrdersServlet;
import orders.TestServlet;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var ctx = new AnnotationConfigApplicationContext(
                Config.class,
//                HsqlDatabaseConfig.class
                PostgresqlDatabaseConfig.class
        );

        ServletRegistration registration = sce.getServletContext().addServlet("orders-servlet", ctx.getBean(OrdersServlet.class));
        registration.addMapping("/api/orders");
        ServletRegistration registration1 = sce.getServletContext().addServlet("orders-form-servlet", ctx.getBean(OrdersFormServlet.class));
        registration1.addMapping("/orders/form");
        ServletRegistration registration2 = sce.getServletContext().addServlet("test-servlet", ctx.getBean(TestServlet.class));
        registration2.addMapping("/test");
    }
}
