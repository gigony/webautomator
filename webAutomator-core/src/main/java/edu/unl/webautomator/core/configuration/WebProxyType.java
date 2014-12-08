package edu.unl.webautomator.core.configuration;

/**
 * Created by gigony on 12/7/14.
 */
public enum WebProxyType {
    //TODO - change value
    NONE(0),
    MANUAL(1),
    AUTO(2),
    BROWSER(3),
    SYSTEM(4);

    private int value;

    private WebProxyType(int value) {
        this.value = value;
    }

    public int toIntValue() {
        return value;
    }
}
