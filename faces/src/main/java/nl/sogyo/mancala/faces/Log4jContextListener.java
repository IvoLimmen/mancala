package nl.sogyo.mancala.faces;

import java.net.MalformedURLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

public class Log4jContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent sce) {

        LogManager.shutdown();
    }

    public void contextInitialized(ServletContextEvent sce) {

        try {

            PropertyConfigurator.configure(
                sce.getServletContext().getResource("/WEB-INF/classes/log4j.properties"));
        }
        catch (MalformedURLException e) {

            e.printStackTrace();
        }
    }
}
