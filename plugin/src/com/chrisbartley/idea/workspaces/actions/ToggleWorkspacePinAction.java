package com.chrisbartley.idea.workspaces.actions;

import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.JList;
import com.chrisbartley.idea.util.RefreshableListModel;
import com.chrisbartley.idea.workspaces.Icons;
import com.chrisbartley.idea.workspaces.Workspace;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class ToggleWorkspacePinAction extends BaseWorkspaceAction
   {
   private final JList list;
   private final Workspace workspace;

   /**
    * @param workspace The workspace whose pin should be toggled.
    */
   public ToggleWorkspacePinAction(final Workspace workspace)
      {
      this(workspace.getName(), "Pin/unpin '" + workspace.getName() + "'", null, null, workspace);
      }

   /**
    * @param list The list from which workspaces to be pinned/unpinned will be determined.  The
    * list's list model must be a {@link com.chrisbartley.idea.util.RefreshableListModel}.
    */
   public ToggleWorkspacePinAction(final JList list)
      {
      this("Toggle Pin", "Pin/unpin the selected workspace(s)", Icons.PINNED, list, null);
      }

   private ToggleWorkspacePinAction(final String text, final String description, final Icon icon, final JList list, final Workspace workspace)
      {
      super(text, description, icon);
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
               getWorkspaceManager(project).toggleWorkspacePinnedness(Arrays.asList(list.getSelectedValues()));

               // refresh the list model
               final RefreshableListModel model = (RefreshableListModel)list.getModel();
               model.refresh(list.getSelectedIndices());
               }
            }
         else
            {
            getWorkspaceManager(project).toggleWorkspacePinnedness(workspace);
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
         else
            {
            event.getPresentation().setEnabled(true);
            event.getPresentation().setDescription((workspace.isPinned() ? "Unpin" : "Pin") + " '" + workspace.getName() + "'");
            }
         }
      }
   }
