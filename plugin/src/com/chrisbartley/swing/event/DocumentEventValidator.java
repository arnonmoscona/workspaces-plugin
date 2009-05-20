package com.chrisbartley.swing.event;

import javax.swing.event.DocumentEvent;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public interface DocumentEventValidator
   {
   public boolean isValid(final DocumentEvent event);
   }