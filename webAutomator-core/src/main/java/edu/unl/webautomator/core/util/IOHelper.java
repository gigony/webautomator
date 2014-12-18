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
