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

package com.gigony.qte.core.provider;

/**
 * Created by gigony on 3/3/15.
 */
public interface EventInputHandler<E, V> {

  /**
   * Note: <i>element</i> can be <i>null</i>.
   *
   * @param event event
   * @param element concrete element
   * @return input text for the event
   */
  String getInput(E event, V element);
}
