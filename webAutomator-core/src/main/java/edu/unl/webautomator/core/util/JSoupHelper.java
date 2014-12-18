package edu.unl.webautomator.core.util;

import com.google.common.base.*;
import com.google.common.collect.Sets;
import org.jsoup.nodes.*;

import java.util.*;

/**
 * Created by gigony on 12/11/14.
 */
public final class JSoupHelper {

  private JSoupHelper() {
  }

  public static String getIdOrName(final Element elem) {
    String id = elem.attr("id");
    if ("".equals(id)) {
      id = elem.attr("name");
    }
    return id;
  }

  public static String getCssSelector(final Element node) {
    return getCssPath(node, true);
  }

  public static String getJSoupCssSelector(final Element node) {
    return node.cssSelector();
  }

  public static class PathStep {

    private final String value;
    private final boolean optimized;

    public PathStep(final String val, final boolean opti) {
      this.value = val;
      this.optimized = opti;
    }

    public final String getValue() {
      return this.value;
    }

    public final boolean isOptimized() {
      return this.optimized;
    }
  }

  // Referred from DomPresentationUtils.js
  public static String getCssPath(final Node node, final boolean optimized) {
    LinkedList<String> steps = new LinkedList<String>();

    Node currentNode = node;
    while (currentNode != null && !(currentNode instanceof Document)) {
      PathStep step = getCssPathStep(currentNode, optimized, currentNode == node);
      if (step == null) {
        throw new RuntimeException("Cannot convert to css path.");
      }

      steps.add(step.getValue());
      if (step.isOptimized()) {
        break;
      }
      currentNode = currentNode.parent();
    }

    String result = Joiner.on(" > ").join(steps.descendingIterator());

    return result;
  }

  private static PathStep getCssPathStep(final Node node, final boolean optimized, final boolean isTarget) {
    String id = node.attr("id");

    if (optimized) {
      if (!Strings.isNullOrEmpty(id)) {
        return new PathStep(getIdSelector(id), true);
      }
      String nodeNameLower = node.nodeName().toLowerCase();
      if (nodeNameLower.equals("body") || nodeNameLower.equals("head") || nodeNameLower.equals("html")) {
        return new PathStep(node.nodeName(), true);
      }
    }

    String nodeName = node.nodeName();

    if (!Strings.isNullOrEmpty(id)) {
      return new PathStep(nodeName + getIdSelector(id), true);
    }

    Node parent = node.parent();

    if (parent == null || parent instanceof Document) {
      return new PathStep(nodeName, true);
    }


    Set<String> prefixedOwnClassNamesArray = prefixedElementClassNames(node);
    boolean needsClassNames = false;
    boolean needsNthChlid = false;
    int ownIndex = -1;
    int elementIndex = -1;
    List<Node> siblings = parent.childNodes();
    for (int i = 0; (ownIndex == -1 || !needsNthChlid) && i < siblings.size(); i++) {
      Node sibling = siblings.get(i);
      elementIndex++;

      if (sibling.equals(node)) {
        ownIndex = elementIndex;
        continue;
      }

      if (needsNthChlid) {
        continue;
      }

      if (!sibling.nodeName().equals(nodeName)) {
        continue;
      }

      needsClassNames = true;

      Set<String> ownClassNames = Sets.newHashSet(prefixedOwnClassNamesArray);
      if (ownClassNames.isEmpty()) {
        needsNthChlid = true;
        continue;
      }

      Set<String> siblingClassNameArray = prefixedElementClassNames(sibling);
      for (String siblingClass : siblingClassNameArray) {
        ownClassNames.remove(siblingClass);
        if (ownClassNames.isEmpty()) {
          needsNthChlid = true;
          break;
        }
      }
    }

    StringBuffer result = new StringBuffer(nodeName);

    if (isTarget && nodeName.toLowerCase().equals("input") && !Strings.isNullOrEmpty(node.attr("type")) && Strings.isNullOrEmpty(id)) {
      result.append("[type=\"").append(node.attr("type")).append("\"]");
    }

    if (needsNthChlid) {
      result.append(":nth-child(").append(ownIndex + 1).append(")");
    } else if (needsClassNames) {
      for (String prefixedName : prefixedOwnClassNamesArray) {
        result.append(".").append(escapeIdentifierIfNeeded(prefixedName));
      }
    }

    return new PathStep(result.toString(), false);
  }

  private static Set<String> prefixedElementClassNames(final Node node) {

    String classAttribute = node.attr("class").trim();
    if (Strings.isNullOrEmpty(classAttribute)) {
      return Sets.newHashSet();
    }

    return Sets.newHashSet(classAttribute.split("\\s+"));
  }

  private static String getIdSelector(final String id) {
    return "#" + escapeIdentifierIfNeeded(id);
  }

  private static String escapeIdentifierIfNeeded(final String id) {
    return id;
  }


  public static String getXPathSelector(final Node node) {
    return getXPath(node, true);
  }

  public static String getXPath(final Node node, final boolean optimized) {
    if (node instanceof Document) {
      return "/";
    }


    boolean isOptimized = false;
    LinkedList<String> steps = new LinkedList<String>();
    Node currentNode = node;
    while (currentNode != null && !(currentNode instanceof Document)) {
      PathStep step = getXPathStep(currentNode, optimized);
      if (step == null) {
        throw new RuntimeException("Cannot convert to css path.");
      }
      steps.add(step.getValue());

      isOptimized = step.isOptimized();

      if (step.isOptimized()) {
        break;
      }

      currentNode = currentNode.parent();
    }

    String result = (!steps.isEmpty() && isOptimized ? "" : "/")
      + Joiner.on("/").join(steps.descendingIterator());

    return result;
  }

  private static PathStep getXPathStep(final Node node, final boolean optimized) {
    String ownValue;

    int ownIndex = getXPathIndex(node);

    if (ownIndex == -1) {
      return null;
    }

    String nodeType = node.getClass().getSimpleName();

    if (node instanceof Element) {
      if (optimized && !Strings.isNullOrEmpty(node.attr("id"))) {
        return new PathStep("//*[@id=\"" + node.attr("id") + "\"]", true);
      }
      ownValue = ((Element) node).tagName();
    } else if (node instanceof TextNode || node instanceof DataNode) {
      ownValue = "text()";
    } else if (node instanceof Comment) {
      ownValue = "comment()";
    } else {
      ownValue = "";
    }

    if (ownIndex > 0) {
      ownValue += "[" + ownIndex + "]";
    }

    return new PathStep(ownValue, false);

  }

  private static int getXPathIndex(final Node node) {

    if (node.parentNode() == null) {
      return -1;
    }

    List<Node> siblings = node.parentNode().childNodes();
    if (siblings.isEmpty()) {
      return 0;
    }
    boolean hasSameNamedElements = false;
    for (int i = 0; i < siblings.size(); i++) {
      Node sibling = siblings.get(i);
      if (areNodesSimilar(node, sibling) && !sibling.equals(node)) {
        hasSameNamedElements = true;
        break;
      }
    }
    if (!hasSameNamedElements) {
      return 0;
    }

    int ownIndex = 1;
    for (int i = 0; i < siblings.size(); i++) {
      Node sibling = siblings.get(i);
      if (areNodesSimilar(node, sibling)) {
        if (sibling.equals(node)) {
          return ownIndex;
        }
        ++ownIndex;
      }
    }
    return -1;
  }

  private static boolean areNodesSimilar(final Node left, final Node right) {
    if (left.equals(right)) {
      return true;
    }
    if (left instanceof Element && right instanceof Element) {
      return ((Element) left).tagName().equals(((Element) right).tagName());
    }

    // ignore that 'XPath treats CDATA as text nodes.'
    return left.nodeName().equals(right.nodeName());
  }


}
