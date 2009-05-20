package com.chrisbartley.swing.event;

import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public class NonEmptyDocumentValidator implements DocumentEventValidator
   {
   /**
    * Returns <code>true</code> if the {@link Document} returned by <code>event.getDocument()</code>
    * has a length greater than zero; returns <code>false</code> otherwise.
    */
   public boolean isValid(final DocumentEvent event)
      {
      return (event.getDocument().getLength() > 0);
      }
   }
