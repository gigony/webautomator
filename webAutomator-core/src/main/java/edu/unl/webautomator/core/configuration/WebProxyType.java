package edu.unl.webautomator.core.configuration;

/**
 * Created by gigony on 12/7/14.
 */
public enum WebProxyType {
    //TODO - verify values
    NONE(0),
    MANUAL(1),
    BROWSER(3), // check
    AUTO(4),
    SYSTEM(5);

    private int value;

    private WebProxyType(final int val) {
        this.value = val;
    }

    public int toIntValue() {
        return this.value;
    }
}
