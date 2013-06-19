package com.tinywebgears.gaedownload.webapp;

import java.io.Serializable;
import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.tinywebgears.gaedownload.core.dao.FileRepository;
import com.tinywebgears.gaedownload.core.dao.UserRepository;
import com.tinywebgears.gaedownload.core.model.User;
import com.tinywebgears.gaedownload.core.service.DefaultUserServices;
import com.tinywebgears.gaedownload.core.service.UserServicesIF;
import com.vaadin.Application;
import com.vaadin.service.ApplicationContext;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class WebApplication extends Application implements ApplicationContext.TransactionListener
{
    private static ThreadLocal<WebApplication> currentApplication = new ThreadLocal<WebApplication>();

    private final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    private final UserServicesIF userServices;
    private final UserSession session = new UserSession();
    private Boolean initialized = false;
    private Window mainWindow;
    private HorizontalLayout headerLayout;
    private HorizontalLayout loginForm;
    private HorizontalLayout logoutForm;
    private VerticalLayout userInfoLayout;
    private Label emailLabel;
    private Button downloadButton;
    private HorizontalLayout downloadLayout;

    public WebApplication()
    {
        userServices = new DefaultUserServices(DatabaseServices.userRepo, DatabaseServices.fileRepo);
    }

    /**
     * This method is called by Vaadin application manager once the application is accessed by user.
     */
    public void init()
    {
        setTheme("gaedownload");
        getContext().addTransactionListener(this);
    }

    /**
     * See @ApplicationContext.TransactionListener
     */
    public void transactionStart(Application application, Object servletRequest)
    {
        if (application == WebApplication.this)
        {
            currentApplication.set(this);
            // Check the user's login status based on servlet request parameters.
            checkLoginLogoutRequest((HttpServletRequest) servletRequest);
        }
    }

    /**
     * See @ApplicationContext.TransactionListener
     */
    public void transactionEnd(Application application, Object o)
    {
    }

    /**
     * This method is called by Vaadin once the application is closed. This will be called on every request when
     * application is run on GAE.
     */
    @Override
    public void close()
    {
        logger.trace("Application is closing....");
        super.close();
    }

    public UserServicesIF getUserServices()
    {
        return userServices;
    }

    private void checkLoginLogoutRequest(HttpServletRequest request)
    {
        logger.trace("checkLoginLogoutRequest - initialized? " + initialized);
        Principal userPrincipal = request.getUserPrincipal();

        if (initialized
                && ((userPrincipal != null && session.getUsername() != null) || (userPrincipal == null && session
                        .getUsername() == null)))
        {
            return;
        }

        UserService userService = UserServiceFactory.getUserService();

        if (mainWindow == null)
        {
            logger.debug("Creating main window");
            Resource loginUrl = new ExternalResource(userService.createLoginURL(request.getRequestURI()));
            Resource logoutUrl = new ExternalResource(userService.createLogoutURL(request.getRequestURI()));
            initUiComponents(loginUrl, logoutUrl);
            setLoggedIn(null);
        }

        if (request.getUserPrincipal() != null)
        {
            // Login information is provided in the request.
            String userid = request.getUserPrincipal().getName();
            String nickname = userService.getCurrentUser().getNickname();
            logger.info("User logged in: " + userid + " nick name: " + nickname + " admin: "
                    + userService.isUserAdmin());
            session.setSession(userid, userid, userService.isUserAdmin(), nickname);
            try
            {
                User user = userServices.setUser(userid, userid, nickname);
                setLoggedIn(user);
            }
            catch (Exception e)
            {
                logger.error("Unable to log in: " + e);
                showMessage("Unable to log you in: " + e.getLocalizedMessage());
            }
        }
        else
        {
            // No user information is provided in the request.
            if (session.getUsername() != null)
            {
                // Logout is requested.
                logger.info("User logged out: " + session.getUsername());
                showMessage("You successfully logged out.");
                try
                {
                    userServices.clearUser();
                    session.clearSession();
                    setLoggedIn(null);
                }
                catch (Exception e)
                {
                    logger.warn("Error occured while logging out: " + e);
                    showMessage("Couldn't log you out: " + e.getLocalizedMessage());
                }
                finally
                {
                    ((WebApplicationContext) getContext()).getHttpSession().invalidate();
                    // close();
                }
            }
            else
            {
                // Application is initialized without login info.
                logger.debug("Application reloaded");
            }
        }
    }

    /**
     * This method is used by internal components to access global resources/services.
     * 
     * @return The current instance of web application
     */
    public static WebApplication getInstance()
    {
        return currentApplication.get();
    }

    public UserSession getUserSession()
    {
        return session;
    }

    public void showMessage(String description)
    {
        if (mainWindow != null)
            mainWindow.showNotification("", description, Notification.TYPE_HUMANIZED_MESSAGE);
    }

    private void initUiComponents(Resource loginUrl, Resource logoutUrl)
    {
        mainWindow = new Window("Simple GAE Application using Vaadin");
        mainWindow.setSizeFull();
        setMainWindow(mainWindow);

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();
        mainWindow.addComponent(mainLayout);

        headerLayout = new HorizontalLayout();
        headerLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        mainLayout.addComponent(headerLayout);

        loginForm = new HorizontalLayout();
        // loginForm.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        loginForm.setSpacing(true);
        Label loginLabel = new Label("Please use this button to log in.");
        loginLabel.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        loginForm.addComponent(loginLabel);
        loginForm.addComponent(new Link("Login", loginUrl));

        logoutForm = new HorizontalLayout();
        // logoutForm.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        logoutForm.setSpacing(true);
        emailLabel = new Label("");
        emailLabel.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        logoutForm.addComponent(emailLabel);
        logoutForm.addComponent(new Link("Logout", logoutUrl));

        downloadButton = new Button("Download");
        downloadButton.addListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(ClickEvent event)
            {
                downloadFile();
            }
        });
        downloadLayout = new HorizontalLayout();
        downloadLayout.setMargin(true, false, true, false);
        downloadLayout.setSpacing(true);
        downloadLayout.addComponent(new Label("Press this button to download a sample file."));
        downloadLayout.addComponent(downloadButton);

        userInfoLayout = new VerticalLayout();
        mainLayout.addComponent(userInfoLayout);
        initialized = true;
    }

    private void setLoggedIn(User user)
    {
        headerLayout.removeAllComponents();
        userInfoLayout.removeAllComponents();
        if (user == null)
        {
            headerLayout.addComponent(loginForm);
        }
        else
        {
            headerLayout.addComponent(logoutForm);
            emailLabel.setValue(user.getEmail());
            Label welcomeLabel = new Label("Welcome " + user.getNickname());
            welcomeLabel.setWidth(100, Sizeable.UNITS_PERCENTAGE);
            userInfoLayout.addComponent(welcomeLabel);
            Label lastLoginLabel = new Label();
            if (user.getLastLoginDate() != null)
                lastLoginLabel.setValue("Your last login time was " + user.getLastLoginDate());
            else
                lastLoginLabel.setValue("This is the first time you log in.");
            lastLoginLabel.setWidth(100, Sizeable.UNITS_PERCENTAGE);
            userInfoLayout.addComponent(lastLoginLabel);
            userInfoLayout.addComponent(downloadLayout);
            userServices.updateUserLoginDate(user.getKey(), new Date());
        }
    }

    private void downloadFile()
    {
        String fileName = "sample.txt";
        String fileContents = "Sample Contents";
        userServices.removeAllFiles();
        Resource resource = userServices.serveTextFile(fileName, fileContents.getBytes());
        // Redirecting to servlet in order to download the file
        mainWindow.open(resource, "_blank");
    }

    private static class DatabaseServices implements Serializable
    {
        private final static UserRepository userRepo;
        private final static FileRepository fileRepo;

        static
        {
            userRepo = new UserRepository();
            fileRepo = new FileRepository();
        }
    }
}
