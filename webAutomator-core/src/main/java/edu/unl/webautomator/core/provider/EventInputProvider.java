/*
 * Copyright 2015 Gigon Bae
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package edu.unl.webautomator.core.provider;

/**
 * Created by gigony on 12/6/14.
 */

import edu.unl.webautomator.core.model.EventType;

import java.util.List;
import java.util.Map;

/**
 * Procedure of event input argumentation
 * <ol>
 * <li> Check if an input string to the event exists. Finish if the event type does not require input string or the input string is not <i>null</i>.</li>
 * <li> Execute <i>custom event input handlers</i> to get proper event input.</li>
 * <li> Execute <i>type-based default event input handlers</i> if event input string was not filled.</li>
 * </ol>
 */
public interface EventInputProvider<E, V> {

  void addCustomEventInputHandler(EventType eventType, EventInputHandler<E, V> inputHandler);

  void setDefaultEventInputHandler(String eventType, EventInputHandler<E, V> inputHandler);

  List<TypeHandlerPair<EventType, EventInputHandler<E, V>>> getCustomEventInputHandler();

  Map<String, EventInputHandler<E, V>> getDefaultEventInputHandler();
}
