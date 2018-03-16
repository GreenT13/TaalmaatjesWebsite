package com.apon.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageResource {
    // Should overwrite this variable in Taalmaatjes.java if you want to change the language.
    private static Locale locale = new Locale("nl");

    private static MessageResource ourInstance = new MessageResource();

    public static MessageResource getInstance() {
        return ourInstance;
    }

    private ResourceBundle messageResource;

    private MessageResource() {
        messageResource = ResourceBundle.getBundle("MessageResources", locale);
    }

    public String getValue(String messageCode, Object... messageArguments) {
        try {
            return MessageFormat.format(messageResource.getString(messageCode), messageArguments);
        } catch (MissingResourceException e) {
            return "???NL." + messageCode + "???";
        }
    }
}

