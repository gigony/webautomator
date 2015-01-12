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

package edu.unl.webautomator.core.util;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Created by gigony on 12/18/14.
 */
public final class IOHelper {

  private IOHelper() {
  }


  public static String getResourceAsString(final String path) {
    try {
      System.out.println(new File(ClassLoader.getSystemResource("").toURI()).getAbsolutePath());
      InputStream stream = new BufferedInputStream(new FileInputStream(new File(ClassLoader.getSystemResource("").toURI().getPath(), path)));
      String content = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
      return content;
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    } catch (URISyntaxException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }
}
