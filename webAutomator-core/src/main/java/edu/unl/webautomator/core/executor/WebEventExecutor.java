package edu.unl.webautomator.core.executor;


import com.google.inject.Inject;
import edu.unl.webautomator.core.WebAutomator;

/**
 * Created by gigony on 12/6/14.
 */
public class WebEventExecutor implements EventExecutor {
    private WebAutomator webAutomator;

    @Inject
    public WebEventExecutor(final WebAutomator automator) {
        this.webAutomator = automator;
    }
}
