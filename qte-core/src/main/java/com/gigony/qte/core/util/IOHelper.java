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

package com.gigony.qte.core.util;

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
      File file = new File(ClassLoader.getSystemResource(path).toURI().getPath());
      return getFileContentAsString(file);
    } catch (URISyntaxException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }

  public static String getFileContentAsString(final File file) {
    //System.out.println("######" + file.getAbsolutePath());
    InputStream stream = null;
    String content = null;
    try {
      stream = new BufferedInputStream(new FileInputStream(file));
      content = CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
    return content;
  }
}
