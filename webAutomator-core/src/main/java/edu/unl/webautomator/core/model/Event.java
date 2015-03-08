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

package edu.unl.webautomator.core.model;

import java.util.List;

/**
 * Created by gigony on 1/12/15.
 */
// @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface Event<E> {

  int size();

  E get(int index);

  Event<E> addPreCondition(E element);

  Event<E> addAction(E element);

  Event<E> addPostCondition(E element);

  /**
   * This may return null;
   * @return
   */
  List<E> getPreConditions();

  E getPreCondition(int index);

  E getFirstPreCondition();

  E getLastPreCondition();

  int getPreConditionSize();

  List<E> getActions();

  E getAction(int index);

  E getAction();

  E getFirstAction();

  E getLastAction();

  int getActionSize();

  /**
   * This may return null;
   * @return
   */
  List<E> getPostConditions();

  E getPostCondition(int index);

  E getFirstPostCondition();

  E getLastPostCondition();

  int getPostConditionSize();
}
