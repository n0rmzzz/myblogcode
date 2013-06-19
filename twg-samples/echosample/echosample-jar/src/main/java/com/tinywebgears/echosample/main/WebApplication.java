package com.tinywebgears.echosample.main;

import java.util.Date;

import com.tinywebgears.echosample.custom.EchoComponent;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class WebApplication extends Application
{
    private Window mainWindow;
    private Integer rounds = 0;
    private Integer totalRounds = 10;
    private EchoComponent echoComponent;

    // SECTION: Lifecycle methods

    public WebApplication()
    {
    }

    public void init()
    {
        mainWindow = new Window("Main Window");
        setMainWindow(mainWindow);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        final Button startButton = new Button("Start");
        buttonsLayout.addComponent(startButton);
        final Button stopButton = new Button("Stop");
        stopButton.setEnabled(false);
        buttonsLayout.addComponent(stopButton);
        mainWindow.addComponent(buttonsLayout);
        final Label statusLabel = new Label("");
        mainWindow.addComponent(statusLabel);
        echoComponent = new EchoComponent(new EchoComponent.EchoResponseListener()
        {
            public void echoResponseReceived(String response)
            {
                busySleep(1000);
                rounds++;
                if (rounds >= totalRounds)
                {
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    mainWindow.showNotification("FINISHED!");
                }
                else
                {
                    statusLabel.setValue("" + rounds + " / " + totalRounds + " finished.");
                    echoComponent.sendEcho();
                }
            }
        });
        mainWindow.addComponent(echoComponent);
        startButton.addListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(ClickEvent event)
            {
                statusLabel.setValue("Started");
                rounds = 0;
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                mainWindow.showNotification("Please wait...");
                echoComponent.sendEcho();
            }
        });
        stopButton.addListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(ClickEvent event)
            {
                stopButton.setEnabled(false);
                startButton.setEnabled(true);
            }
        });
    }

    public static void busySleep(Integer milliSecs)
    {
        Long startDate = new Date().getTime();
        Long now = startDate;
        while (now.compareTo(startDate + milliSecs) < 0)
            now = System.currentTimeMillis();
    }
}
