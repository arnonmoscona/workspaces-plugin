package com.chrisbartley.idea.workspaces.actions;

import java.util.Arrays;
import java.util.HashSet;
import javax.swing.JList;
import com.chrisbartley.idea.util.RefreshableListModel;
import com.chrisbartley.idea.workspaces.Icons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class CloseWorkspaceAction extends BaseWorkspaceAction
   {
   private final JList list;

   /**
    * @param list The list from which workspaces to be closed will be determined.  The list's list
    * model must be a {@link RefreshableListModel}.
    */
   public CloseWorkspaceAction(final JList list)
      {
      super("Close", "Close the selected workspace(s)", Icons.CLOSE_WORKSPACE);
      this.list = list;
      }

   public void actionPerformed(final AnActionEvent event)
      {
      final Project project = getProject(event);
      if (project != null)
         {
         if (!list.getSelectionModel().isSelectionEmpty())
            {
            getWorkspaceManager(project).closeWorkspaces(new HashSet(Arrays.asList(list.getSelectedValues())));

            // refresh the list model
            final RefreshableListModel model = (RefreshableListModel)list.getModel();
            model.refresh(list.getSelectedIndices());
            }
         }
      }

   public void update(final AnActionEvent event)
      {
      event.getPresentation().setEnabled(!list.getSelectionModel().isSelectionEmpty());
      }
   }