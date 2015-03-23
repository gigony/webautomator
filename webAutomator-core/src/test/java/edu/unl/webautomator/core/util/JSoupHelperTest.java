package edu.unl.webautomator.core.util;

import com.google.common.collect.Sets;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;

public class JSoupHelperTest {

  public final void testGetSelector() throws Exception {
    String html = IOHelper.getResourceAsString("fixture/homepage/nate.html");
    Document document = JSoupHelper.parse(html);

    Elements elements = document.getAllElements();

    Set<String> cssUnique = Sets.newHashSet();
    Set<String> xPathUnique = Sets.newHashSet();

    for (Element element : elements) {
      System.out.println(element.outerHtml().replaceAll("\n", " "));
      String cssSelector = JSoupHelper.getCssSelector(element);
      String xPathSelector = JSoupHelper.getXPathSelector(element);
      System.out.println("\tcss=" + cssSelector);
      System.out.println("\txpath=" + xPathSelector);

      if (cssUnique.contains(cssSelector)) {
        System.err.println("\t[error] css selector is duplicate! (" + cssSelector + ")");
      }
      if (xPathUnique.contains(xPathSelector)) {
        System.err.println("\t[error] xPath selector is duplicate! (" + xPathSelector + ")");
      }
      cssUnique.add(cssSelector);
      xPathUnique.add(xPathSelector);
    }

    System.out.println();
    System.out.println("# Scripts #");
    Elements scripts = document.getElementsByTag("script");
    for (Element script : scripts) {
      System.out.println("\t" + script.html());
    }


  }
}
