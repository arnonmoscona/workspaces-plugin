package com.chrisbartley.idea.workspaces.actions;

import com.chrisbartley.idea.workspaces.Workspace;
import com.chrisbartley.idea.workspaces.WorkspaceState;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class ToggleWorkspaceOpennessAction extends BaseWorkspaceAction
   {
   private final Project project;
   private final Workspace workspace;

   public ToggleWorkspaceOpennessAction(final Project project, final Workspace workspace)
      {
      super(workspace.getName(), "Open the '" + workspace.getName() + "' workspace", null);
      this.project = project;
      this.workspace = workspace;
      }

   protected String getActionRegistrationId()
      {
      return "ToggleWorkspaceOpenClosed." + project.getProjectFile().getNameWithoutExtension() + "." + workspace.getName();
      }

   public void actionPerformed(final AnActionEvent event)
      {
      //final Project project = getProject(event);
      if (project != null)
         {
         getWorkspaceManager(project).toggleWorkspaceOpenness(workspace);
         }
      }

   public void update(final AnActionEvent event)
      {
      //final Project project = getProject(event);
      if (project != null)
         {
         final WorkspaceState workspaceState = workspace.getState(FileEditorManager.getInstance(project));

         event.getPresentation().setIcon(workspaceState.getComboStatusIcon());
         event.getPresentation().setDescription(getWorkspaceManager(project).getToggleWorkspaceOpennessDescription(workspace));
         }
      }

   public String toString()
      {
      return workspace.getName();
      }
   }