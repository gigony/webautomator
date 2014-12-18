package edu.unl.webautomator.core.provider;

import com.google.inject.Inject;
import edu.unl.webautomator.core.WebAutomator;

/**
 * Created by gigony on 12/6/14.
 */
public class WebEventInputProvider implements EventInputProvider {
    private WebAutomator webAutomator;

    @Inject
    public WebEventInputProvider(final WebAutomator automator) {
        this.webAutomator = automator;
    }
}
