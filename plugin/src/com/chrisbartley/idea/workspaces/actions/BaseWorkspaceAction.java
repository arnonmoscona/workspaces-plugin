package com.chrisbartley.idea.workspaces.actions;

import javax.swing.Icon;
import com.chrisbartley.idea.actions.RegisterableAction;
import com.chrisbartley.idea.workspaces.WorkspaceManager;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public abstract class BaseWorkspaceAction extends RegisterableAction
   {
   public BaseWorkspaceAction()
      {
      super();
      }

   public BaseWorkspaceAction(final String text)
      {
      super(text);
      }

   public BaseWorkspaceAction(final String text, final String description, final Icon icon)
      {
      super(text, description, icon);
      }

   /** Convenience method for retrieving the {@link WorkspaceManager} */
   final WorkspaceManager getWorkspaceManager(final Project project)
      {
      if (project != null)
         {
         return (WorkspaceManager)project.getComponent(WorkspaceManager.class);
         }
      return null;
      }
   }