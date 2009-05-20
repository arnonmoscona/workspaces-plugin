package com.chrisbartley.idea.workspaces.actions;

import com.chrisbartley.idea.actions.MutableActionGroupStrategy;
import com.chrisbartley.idea.workspaces.WorkspaceManager;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public abstract class WorkspaceMutableActionGroupStrategy extends MutableActionGroupStrategy
   {
   /** Convenience method for retrieving the {@link WorkspaceManager} */
   protected final WorkspaceManager getWorkspaceManager(final AnActionEvent event)
      {
      return (WorkspaceManager)getProject(event).getComponent(WorkspaceManager.class);
      }
   }