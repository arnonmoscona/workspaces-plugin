package com.chrisbartley.swing.event;

import javax.swing.Action;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public class ButtonTogglingDocumentListener implements DocumentListener
   {
   private final Action buttonToToggle;
   private final DocumentEventValidator documentEventValidator;

   public ButtonTogglingDocumentListener(final Action buttonToToggle, final DocumentEventValidator documentEventValidator)
      {
      this.buttonToToggle = buttonToToggle;
      this.documentEventValidator = documentEventValidator;
      }

   /**
    * Enables the button if {@link DocumentEventValidator#isValid DocumentEventValidator.isValid(event)}
    * returns <code>true</code>; disables the button otherwise.
    */
   public void insertUpdate(final DocumentEvent event)
      {
      toggleButton(event);
      }

   /**
    * Enables the button if {@link DocumentEventValidator#isValid DocumentEventValidator.isValid(event)}
    * returns <code>true</code>; disables the button otherwise.
    */
   public void removeUpdate(final DocumentEvent event)
      {
      toggleButton(event);
      }

   /**
    * Enables the button if {@link DocumentEventValidator#isValid DocumentEventValidator.isValid(event)}
    * returns <code>true</code>; disables the button otherwise.
    */
   public void changedUpdate(final DocumentEvent event)
      {
      toggleButton(event);
      }

   /**
    * Enables the button if {@link DocumentEventValidator#isValid DocumentEventValidator.isValid(event)}
    * returns <code>true</code>; disables the button otherwise.
    */
   private void toggleButton(final DocumentEvent event)
      {
      buttonToToggle.setEnabled(documentEventValidator.isValid(event));
      }
   }
