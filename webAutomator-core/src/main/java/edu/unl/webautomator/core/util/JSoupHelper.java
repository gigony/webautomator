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

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by gigony on 12/11/14.
 */
public final class JSoupHelper {
  private static final Set<String> PRESERVE_WHITESPACE_TAGS = Sets.newHashSet("pre", "plaintext", "title", "textarea");

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

  public static String normalizeHtmlString(final Document document) {
    String html = document.toString();
    html = html.replaceAll("[\\s\\n]+", " ");
    return html;
  }

  public static Document parse(final String html) {
    Document dom = Jsoup.parse(html);
    stripText(dom);
    return dom;
  }

//  public static Document getDomFromFrameContentString(final String html) {
//    Document dom = Jsoup.parse(html);
//    dom.outputSettings().prettyPrint(false);
//
//
//    Elements frameElements = dom.getElementsByTag("frame");
//
//    // append iframe elements
//    frameElements.addAll(dom.getElementsByTag("iframe"));
//
//    for (Element elem : frameElements) {
//      String id = JSoupHelper.getIdOrName(elem);
//
//      if (!"".equals(id)) {
//        Comment comment = (Comment) elem.childNodes().get(0);
//        String innerHtml = comment.getData().trim();
//        Document innerDom = Jsoup.parse(innerHtml);
//        innerDom.outputSettings().prettyPrint(false);
//
//        elem.empty();
//        elem.appendChild(innerDom.child(0)); // set dom object
//      }
//    }
//    return dom;
//  }
//
//  public static String getPageSourceWithFrameContent(final Document dom) {
//
//    Elements frameElements = dom.getElementsByTag("frame");
//
//    // append iframe elements
//    frameElements.addAll(dom.getElementsByTag("iframe"));
//
//    for (Element elem : frameElements) {
//      String id = JSoupHelper.getIdOrName(elem);
//
//      if (!"".equals(id)) {
//        String innerHtml = elem.html().trim();
//        elem.empty();
//        elem.appendChild(new Comment(innerHtml, ""));
//      }
//    }
//    return dom.toString();
//  }

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

    String result = Joiner.on(">").join(steps.descendingIterator());

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

    // @TODO needs performance test
//    String nodeType = node.getClass().getSimpleName();

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


  public static boolean areElementSame(final Node left, final Node right) {
    if (left == right) {
      return true;
    }
    if (left == null && right != null) {
      return false;
    }
    if (left != null && right == null) {
      return false;
    }

    if (!left.nodeName().equals(right.nodeName())) {
      return false;
    }


    if (left instanceof TextNode && right instanceof TextNode) {
      String leftText = ((TextNode) left).getWholeText();
      String rightText = ((TextNode) right).getWholeText();
      if (!leftText.equals(rightText)) {
        return false;
      }
    }

    Attributes leftAttrs = left.attributes();
    Attributes rightAttrs = right.attributes();

    if (!leftAttrs.equals(rightAttrs)) {
      return false;
    }

    if (!left.attr("class").equals(right.attr("class"))) {
      return false;
    }

    List<Node> leftChildren = left.childNodes();
    List<Node> rightChildren = right.childNodes();
    if (leftChildren.size() != rightChildren.size()) {
      return false;
    }
    int childNum = leftChildren.size();
    for (int i = 0; i < childNum; i++) {
      if (!areElementSame(leftChildren.get(i), rightChildren.get(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Remove unnecessary text nodes from a given node and its child nodes.
   * This is necessary because output htmls from a Document object seem to not
   * match with original html when many serialization/deserialization operations are performed.
   * @param node Document object
   * @return
   */
  public static boolean stripText(final Node node) {

    int childSize = node.childNodeSize();

    for (int i = 0; i < childSize; i++) {
      boolean result = stripText(node.childNode(i));
      if (result) {
        childSize--;
        i--;
      }
    }

    boolean removed = false;
    if (node.nodeName().equals("#text")) {
      String parentTag = node.parentNode().nodeName();
      if (!PRESERVE_WHITESPACE_TAGS.contains(parentTag)) {
        TextNode textNode = (TextNode) node;
        String text = textNode.getWholeText();
        text = text.trim();
        if (text.equals("")) {
          node.remove();
          removed = true;
        } else {
          node.attr("text", text); //rewrite text node
        }
      }
    }
    return removed;
  }


}
