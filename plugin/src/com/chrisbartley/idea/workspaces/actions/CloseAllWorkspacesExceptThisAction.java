package com.chrisbartley.idea.workspaces.actions;

import java.util.Arrays;
import javax.swing.JList;
import com.chrisbartley.idea.util.RefreshableListModel;
import com.chrisbartley.idea.workspaces.Workspace;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class CloseAllWorkspacesExceptThisAction extends BaseWorkspaceAction
   {
   private final JList list;
   private final Workspace workspace;

   /**
    * @param workspace The workspace not to be closed.
    */
   public CloseAllWorkspacesExceptThisAction(final Workspace workspace)
      {
      this(workspace.getName(), "Close all workspaces except '" + workspace.getName() + "'", null, workspace);
      }

   /**
    * @param list The list from which workspaces to be closed will be determined.  The list's list
    * model must be a {@link RefreshableListModel}.
    */
   public CloseAllWorkspacesExceptThisAction(final JList list)
      {
      this(null, null, list, null);
      }

   private CloseAllWorkspacesExceptThisAction(final String text, final String description, final JList list, final Workspace workspace)
      {
      super(text, description, null);
      this.list = list;
      this.workspace = workspace;
      }

   public void actionPerformed(final AnActionEvent event)
      {
      final Project project = getProject(event);
      if (project != null)
         {
         if (list != null)
            {
            if (!list.getSelectionModel().isSelectionEmpty())
               {
               getWorkspaceManager(project).closeAllButTheseWorkspaces(Arrays.asList(list.getSelectedValues()));

               // refresh the list model
               final RefreshableListModel model = (RefreshableListModel)list.getModel();
               model.refreshAllButThese(list.getSelectedIndices());
               }
            }
         else
            {
            getWorkspaceManager(project).closeAllButThisWorkspace(workspace);
            }
         }
      }

   public void update(final AnActionEvent event)
      {
      final Project project = getProject(event);
      if (project != null)
         {
         if (list != null)
            {
            event.getPresentation().setEnabled(!list.getSelectionModel().isSelectionEmpty());
            if (list.getSelectedValues().length == 1)
               {
               event.getPresentation().setText("Close All Workspaces Except This");
               event.getPresentation().setDescription("Close all workspaces except the currently selected one");
               }
            else
               {
               event.getPresentation().setText("Close All Workspaces Except These");
               event.getPresentation().setDescription("Close all workspaces except the currently selected ones");
               }
            }
         else
            {
            event.getPresentation().setEnabled(true);
            }
         }
      }
   }