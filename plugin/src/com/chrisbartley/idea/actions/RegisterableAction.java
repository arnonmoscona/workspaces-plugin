package com.chrisbartley.idea.actions;

import javax.swing.Icon;
import com.intellij.openapi.actionSystem.ActionManager;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public abstract class RegisterableAction extends BaseAction
   {
   public RegisterableAction()
      {
      super();
      }

   public RegisterableAction(final String text)
      {
      super(text);
      }

   public RegisterableAction(final String text, final String description, final Icon icon)
      {
      super(text, description, icon);
      }

   public final void register()
      {
      final ActionManager actionManager = ActionManager.getInstance();
      final String actionId = getActionRegistrationId();
      if (actionManager.getAction(actionId) == null)
         {
         actionManager.registerAction(actionId, this);
         }
      }

   public final void unregister()
      {
      final ActionManager actionManager = ActionManager.getInstance();
      final String actionId = getActionRegistrationId();
      if (actionManager.getAction(actionId) != null)
         {
         actionManager.unregisterAction(actionId);
         }
      }

   protected String getActionRegistrationId()
      {
      return this.getClass().getName();
      }
   }
