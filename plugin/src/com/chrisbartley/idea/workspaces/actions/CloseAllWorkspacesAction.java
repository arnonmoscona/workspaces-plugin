package com.chrisbartley.idea.workspaces.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class CloseAllWorkspacesAction extends BaseWorkspaceAction
   {
   public CloseAllWorkspacesAction()
      {
      super("Close All Workspaces", "Close all workspaces", null);
      }

   public void actionPerformed(final AnActionEvent event)
      {
      final Project project = getProject(event);
      if (project != null)
         {
         getWorkspaceManager(project).closeAllWorkspaces();
         }
      }

   public void update(final AnActionEvent event)
      {
      final Project project = getProject(event);
      if (project != null)
         {
         event.getPresentation().setEnabled(getWorkspaceManager(project).getWorkspaceCount() > 0);
         }
      else
         {
         event.getPresentation().setEnabled(false);
         }
      }
   }