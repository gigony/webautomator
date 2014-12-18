package edu.unl.webautomator.core.extractor;

import com.google.inject.Inject;
import edu.unl.webautomator.core.WebAutomator;

/**
 * Created by gigony on 12/6/14.
 */
public class WebEventExtractor implements EventExtractor {
    private WebAutomator webAutomator;

    @Inject
    public WebEventExtractor(final WebAutomator automator) {
        this.webAutomator = automator;
    }
}
