package com.tinywebgears.echosample.custom.client.ui;

import java.util.Date;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

public class VEchoComponent extends Widget implements Paintable
{
    /** Set the tagname used to statically resolve widget from UIDL. */
    public static final String TAGNAME = "echo";

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-" + TAGNAME;

    /** Component identifier in UIDL communications. */
    String uidlId;

    /** Reference to the server connection object. */
    ApplicationConnection client;

    /**
     * The constructor should first call super() to initialize the component and then handle any initialization relevant
     * to Vaadin.
     */
    public VEchoComponent()
    {
        setElement(Document.get().createDivElement());
        // This method call of the Paintable interface sets the component
        // style name in DOM tree
        setStyleName(CLASSNAME);
    }

    public void updateFromUIDL(UIDL uidl, ApplicationConnection client)
    {
        // This call should be made first. Ensure correct implementation,
        // and let the containing layout manage caption, etc.
        if (client.updateComponent(this, uidl, true))
        {
            return;
        }

        // Save reference to server connection object to be able to send
        // user interaction later
        this.client = client;

        // Save the UIDL identifier for the component
        uidlId = uidl.getId();

        if (uidl.getBooleanAttribute("doit"))
        {
            Date date = new Date();
            client.updateVariable(uidlId, "echo-response", date.toString(), true);
        }
    }
}
