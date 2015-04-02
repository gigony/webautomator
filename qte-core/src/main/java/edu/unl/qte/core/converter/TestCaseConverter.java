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

package edu.unl.qte.core.converter;

import edu.unl.qte.core.model.TestCase;

import java.io.FileNotFoundException;

/**
 * Created by gigony on 12/6/14.
 */
public interface TestCaseConverter<E> {
  TestCase<E> loadTestCase(String fileName, String fileType) throws FileNotFoundException;

  void saveTestCase(String fileName, String fileType, TestCase<E> testCase);

  boolean isSupported(String fileType);
}
