package com.ssafy.homes.global.infra;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
public class AppInitListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent sce)  {
        log.info("AppInitListener.contextDestroyed......");
    }

    public void contextInitialized(ServletContextEvent sce)  {
        log.info("AppInitListener.contextInitialized......");
        ServletContext application = sce.getServletContext();
        application.setAttribute("root", application.getContextPath());
    }

	
}
