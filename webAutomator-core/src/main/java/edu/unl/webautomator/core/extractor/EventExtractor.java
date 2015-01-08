package edu.unl.webautomator.core.extractor;

import edu.unl.webautomator.core.model.Event;

import java.util.List;

/**
 * Created by gigony on 12/6/14.
 */
public interface EventExtractor {
  List<? extends Event> getAvailableEvents();
}
