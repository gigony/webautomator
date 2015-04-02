package edu.unl.qte.core.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringHelperTest {

  @Test
  public final void testGetPortNumFromUrl() throws Exception {
    assertEquals(8080, StringHelper.getPortNumFromUrl("http://localhost:8080/index.html"));
    assertEquals(8080, StringHelper.getPortNumFromUrl("http://localhost:8080"));
    assertEquals(80, StringHelper.getPortNumFromUrl("http://localhost/index.html"));
  }
}
