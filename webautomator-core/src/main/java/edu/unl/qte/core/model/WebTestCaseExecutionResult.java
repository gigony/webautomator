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

package edu.unl.qte.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gigony on 12/7/14.
 */
public class WebTestCaseExecutionResult implements TestCaseExecutionResult<WebState, WebEventElement> {

  private List<WebState> stateList;
  private ExecutionResult result;
  private EventExecutionResult failureInducingEventInfo;
  private TestCase testCase;


  public WebTestCaseExecutionResult() {
    this(null);
  }

  public WebTestCaseExecutionResult(final WebTestCase testCase) {

    this.stateList = new ArrayList<WebState>();
    this.testCase = testCase;
    this.result = ExecutionResult.PASSED;
  }

  @Override
  public final void addState(final int index, final WebState state) {
    this.stateList.add(index, state);
  }

  @Override
  public final void addState(final WebState state) {
    this.stateList.add(state);

  }

  @Override
  public final WebState getState(final int index) {
    return this.stateList.get(index);
  }

  @Override
  public final int size() {
    return this.testCase.size();
  }

  public final int getStateSize() {
    return this.stateList.size();
  }

  @Override
  public final boolean isPassed() {
    return this.result == ExecutionResult.PASSED;
  }

  @Override
  public final EventExecutionResult getFailureInducingEventInfo() {
    return this.failureInducingEventInfo;
  }

  @Override
  public final void setFailureInducingEventInfo(final EventExecutionResult result) {
    this.failureInducingEventInfo = result;
  }

  @Override
  public final TestCase getTestCase() {
    return this.testCase;
  }

  @Override
  public final void setTestCase(final TestCase testCase) {
    this.testCase = testCase;
  }

  @Override
  public final ExecutionResult getResult() {
    return this.result;
  }

  public final void setResult(final ExecutionResult result) {
    this.result = result;
  }


  @Override
  public final Iterator<WebState> iterator() {
    return this.iterator();
  }


  @Override
  public final String toString() {
    String result = String.format("%s (size:%d)", this.result, this.size());
    return result;
  }
}
