package edu.unl.webautomator.core.model;



/**
 * Created by gigony on 12/7/14.
 */
public class WebState implements State {

  private WebDocument webDoc;

  public WebState(final WebDocument doc) {
    this.webDoc = doc;
  }


  public final WebDocument getWebDoc() {
    return this.webDoc;
  }

  public final void setWebDoc(final WebDocument webDoc) {
    this.webDoc = webDoc;
  }
}
