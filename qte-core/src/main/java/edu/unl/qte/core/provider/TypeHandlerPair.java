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

package edu.unl.qte.core.provider;

import com.google.common.base.Objects;

/**
 * Created by gigony on 3/3/15.
 */
public class TypeHandlerPair<K, V> {
  private K key;
  private V value;

  public TypeHandlerPair(final K key, final V value) {
    this.key = key;
    this.value = value;
  }

  public final K getKey() {
    return this.key;
  }

  public final V getValue() {
    return this.value;
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.key);
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj instanceof TypeHandlerPair) {
      TypeHandlerPair that = (TypeHandlerPair) obj;
      return this.key.equals(that.key);
    }
    return false;
  }
}
