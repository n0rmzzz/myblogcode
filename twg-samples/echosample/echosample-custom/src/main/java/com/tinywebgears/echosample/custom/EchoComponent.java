package com.tinywebgears.echosample.custom;

import java.io.Serializable;
import java.util.Map;

import com.tinywebgears.echosample.custom.client.ui.VEchoComponent;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.ClientWidget;

@ClientWidget(VEchoComponent.class)
public class EchoComponent extends AbstractComponent
{
    private final EchoResponseListener responseListener;

    private boolean doit = false;

    public EchoComponent(EchoResponseListener responseListener)
    {
        this.responseListener = responseListener;
    }

    public void sendEcho()
    {
        doit = true;
        requestRepaint();
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException
    {
        super.paintContent(target);
        if (doit)
        {
            target.addAttribute("doit", true);
        }
        doit = false;
    }

    @Override
    public void changeVariables(Object source, Map variables)
    {
        if (variables.containsKey("echo-response") && !isReadOnly())
        {
            final String responseDate = (String) variables.get("echo-response");
            if (responseListener != null)
                responseListener.echoResponseReceived(responseDate);
        }
    }

    public static interface EchoResponseListener extends Serializable
    {
        void echoResponseReceived(String response);
    }
}
