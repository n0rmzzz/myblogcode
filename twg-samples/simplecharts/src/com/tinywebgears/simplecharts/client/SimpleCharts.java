package com.tinywebgears.simplecharts.client;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.AnnotatedTimeLine;
import com.google.gwt.visualization.client.visualizations.AnnotatedTimeLine.Options;
import com.tinywebgears.simplecharts.shared.FieldVerifier;
import com.tinywebgears.simplecharts.shared.StringHelper;

// Entry point class
public class SimpleCharts implements EntryPoint
{
    // Error message
    private static final String SERVER_ERROR = "An error occurred while attempting to contact the server. "
            + "Please check your network connection and try again.";

    // Remote service proxy
    private final AustralianEquitiesServiceAsync ausequitiesService = GWT.create(AustralianEquitiesService.class);

    // Fields
    final Button chartButton = new Button("Chart");
    final TextBox nameField = new TextBox();
    final Label errorLabel = new Label();
    final PopupPanel chartDialogBox = new PopupPanel();
    final DialogBox errorDialogBox = new DialogBox();
    final Button closeButton = new Button("Close");

    // Entry point method
    @Override
    public void onModuleLoad()
    {
        Runnable onLoadCallback = new Runnable()
        {
            @Override
            public void run()
            {
                startApplication();
            }
        };

        // Load the visualization API, passing the onLoadCallback to be called when loading is done.
        VisualizationUtils.loadVisualizationApi(onLoadCallback, AnnotatedTimeLine.PACKAGE);
    }

    private void startApplication()
    {
        // We can add style names to widgets
        chartButton.addStyleName("chartButton");

        HorizontalPanel securityCodeFormHeader = new HorizontalPanel();
        securityCodeFormHeader.setSpacing(5);
        securityCodeFormHeader.add(new Label("Please enter security code(s) below:"));
        RootPanel.get("securityCodeFormHeader").add(securityCodeFormHeader);

        // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        HorizontalPanel securityCodeForm = new HorizontalPanel();
        securityCodeForm.setSpacing(5);
        securityCodeForm.add(nameField);
        securityCodeForm.add(chartButton);
        RootPanel.get("securityCodeFormContainer").add(securityCodeForm);

        RootPanel.get("errorLabelContainer").add(errorLabel);

        VerticalPanel commentsPanel = new VerticalPanel();
        Label securityCodesLabel = new Label("Supported security codes: ANZ, BHP, DJS, TLS, and MTS.");
        Label dateRangeLabel = new Label("Supported date range: 01 Jan. 2009 - 30 Apr. 2009");
        commentsPanel.add(securityCodesLabel);
        commentsPanel.add(dateRangeLabel);
        RootPanel.get("securityCodeListContainer").add(commentsPanel);

        // Focus the cursor on the name field when the app loads
        nameField.setText("");
        nameField.setFocus(true);

        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        chartButton.addClickHandler(handler);
        nameField.addKeyUpHandler(handler);

        // PopupPanel popupPanel = new PopupPanel();
        // SimplePanel testPanel = new SimplePanel();
        // testPanel.setSize("800px", "600px");
        // popupPanel.setSize("800px", "600px");
        // testPanel.add(new Label("TEST Label"));
        // popupPanel.setWidget(testPanel);
        // popupPanel.center();

        // paintChart("anz, mts");
    }

    private void makeErrorMessageDialog(String message)
    {
        VerticalPanel vp = new VerticalPanel();
        vp.addStyleName("dialogVPanel");

        VerticalPanel vinp = new VerticalPanel();
        vinp.addStyleName("dialogVInnerPanel");

        final HTML serverResponseLabel = new HTML();
        serverResponseLabel.addStyleName("serverResponseLabelError");
        serverResponseLabel.setHTML(message);
        vp.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
        vinp.add(serverResponseLabel);
        vp.add(vinp);
        vp.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        vp.add(closeButton);

        errorDialogBox.setText("Error");
        errorDialogBox.setWidget(vp);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler()
        {
            public void onClick(ClickEvent event)
            {
                errorDialogBox.hide();
            }
        });
    }

    private void makeChartDialog(Map<Date, Map<String, Double>> rawData)
    {
        // Image chartImage = makeChart();

        // Create a pie chart visualization.
        // PieChart pie = new PieChart(createTable(), createOptions());
        // pie.addSelectHandler(createSelectHandler(pie));

        AnnotatedTimeLine timeLine = new AnnotatedTimeLine(createDataTable(rawData), createOptions(), "600px", "400px");

        chartDialogBox.setAnimationEnabled(true);

        VerticalPanel vp = new VerticalPanel();
        vp.addStyleName("dialogVPanel");

        VerticalPanel vinp = new VerticalPanel();
        vinp.addStyleName("dialogVInnerPanel");

        vinp.add(timeLine);

        vp.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
        vp.add(vinp);
        vp.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        vp.add(closeButton);

        chartDialogBox.setWidget(vp);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler()
        {
            public void onClick(ClickEvent event)
            {
                chartDialogBox.hide();
            }
        });
    }

    // Create a handler for the sendButton and nameField
    class MyHandler implements ClickHandler, KeyUpHandler
    {
        public void onClick(ClickEvent event)
        {
            sendNameToServer();
        }

        public void onKeyUp(KeyUpEvent event)
        {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
            {
                sendNameToServer();
            }
        }

        private void sendNameToServer()
        {
            // First, we validate the input.
            errorLabel.setText("");
            String stockCodeList = StringHelper.escapeHtml(nameField.getText());
            if (!FieldVerifier.isValidSecurityCodeList(stockCodeList))
            {
                errorLabel.setText("Please enter at least 3 characters");
                return;
            }

            // Then, we send the input to the server.
            chartButton.setEnabled(false);
            paintChart(stockCodeList);
        }
    }

    private void paintChart(String stockCodeList)
    {
        String[] stockCodes = StringHelper.separateCodes(stockCodeList);
        ausequitiesService.getPriceInfo(stockCodes, new AsyncCallback<Map<Date, Map<String, Double>>>()
        {
            public void onFailure(Throwable caught)
            {
                makeErrorMessageDialog(SERVER_ERROR);
                errorDialogBox.center();
                chartButton.setEnabled(true);
                closeButton.setFocus(true);
            }

            public void onSuccess(Map<Date, Map<String, Double>> result)
            {
                makeChartDialog(result);
                chartDialogBox.center();
                chartButton.setEnabled(true);
                closeButton.setFocus(true);
            }
        });
    }

    private Options createOptions()
    {
        AnnotatedTimeLine.Options options = AnnotatedTimeLine.Options.create();
        options.setDisplayAnnotations(true);
        options.setDisplayZoomButtons(true);
        options.setScaleType(AnnotatedTimeLine.ScaleType.ALLFIXED);
        options.setLegendPosition(AnnotatedTimeLine.AnnotatedLegendPosition.SAME_ROW);
        return options;
    }

    private DataTable createDataTable(Map<Date, Map<String, Double>> rawData)
    {
        DataTable data = DataTable.create();
        data.addColumn(AbstractDataTable.ColumnType.DATE, "Date");
        if (rawData.isEmpty())
            return data;
        for (Entry<Date, Map<String, Double>> entry : rawData.entrySet())
        {
            for (String code : entry.getValue().keySet())
                data.addColumn(AbstractDataTable.ColumnType.NUMBER, code);
            break;
        }
        Integer counter = 0;
        for (Entry<Date, Map<String, Double>> entry : rawData.entrySet())
        {
            data.addRows(1);
            data.setValue(counter, 0, entry.getKey());
            Integer index = 1;
            for (Double price : entry.getValue().values())
            {
                if (price != null)
                    data.setValue(counter, index, price);
                index++;
            }
            counter++;
        }
        return data;
    }
}
