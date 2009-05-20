package com.chrisbartley.idea.workspaces.actions;

import javax.swing.JList;
import com.chrisbartley.idea.workspaces.Icons;
import com.chrisbartley.idea.workspaces.Workspace;
import com.chrisbartley.idea.workspaces.WorkspaceActionPlaces;
import com.chrisbartley.idea.workspaces.WorkspaceManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class RemoveWorkspaceAction extends BaseWorkspaceAction
   {
   private static final int YES_RESPONSE = 0;

   private final Workspace workspace;
   private final JList list;
   private final boolean showConfirmation;

   /**
    * @param workspace The workspace to be removed.
    */
   public RemoveWorkspaceAction(final Workspace workspace, final boolean showConfirmation)
      {
      this(workspace.getName(), "Remove '" + workspace.getName() + "'", null, workspace, showConfirmation);
      }

   /**
    * @param list The list to manipulate.
    * @param showConfirmation Whether to show a confirmation dialog before removing the selected item(s).
    */
   public RemoveWorkspaceAction(final JList list, final boolean showConfirmation)
      {
      this("Remove", "Remove the selected workspace(s)", list, null, showConfirmation);
      }

   private RemoveWorkspaceAction(final String text, final String description, final JList list, final Workspace workspace, final boolean showConfirmation)
      {
      super(text, description, null);
      this.workspace = workspace;
      this.list = list;
      this.showConfirmation = showConfirmation;
      }

   public void actionPerformed(final AnActionEvent event)
      {
      final Project project = getProject(event);
      if (project != null)
         {
         // get the indices to remove
         int[] indicesToRemove = null;
         if (list != null)
            {
            if (!list.getSelectionModel().isSelectionEmpty())
               {
               indicesToRemove = list.getSelectedIndices();
               }
            }
         else
            {
            final int index = getWorkspaceManager(project).indexOf(workspace);
            if (index >= 0)
               {
               indicesToRemove = new int[]{index};
               }
            }

         if (indicesToRemove != null)
            {
            int confirmationResult = YES_RESPONSE;
            if (showConfirmation)
               {
               final String prompt = (indicesToRemove.length == 1) ? "Remove the selected workspace?" : "Remove the selected workspaces?";
               confirmationResult = Messages.showYesNoDialog(project, prompt, "Confirm Remove", Messages.getQuestionIcon());
               }
            if (confirmationResult == YES_RESPONSE)
               {
               final WorkspaceManager workspaceManager = getWorkspaceManager(project);
               workspaceManager.remove(indicesToRemove);

               // disable this action if there are no more workspaces
               if (workspaceManager.getWorkspaceCount() == 0)
                  {
                  event.getPresentation().setEnabled(false);
                  }
               }
            }
         }
      }

   public void update(final AnActionEvent event)
      {
      if (getProject(event) != null)
         {
         if (list != null)
            {
            event.getPresentation().setEnabled(!list.getSelectionModel().isSelectionEmpty());
            }

         // only show the icon if it's being displayed in the workspaces tool window
         if (WorkspaceActionPlaces.WORKSPACES_TOOL_WINDOW.equals(event.getPlace()))
            {
            event.getPresentation().setIcon(Icons.REMOVE_WORKSPACE);
            }
         }
      }
   }
