package com.chrisbartley.idea.workspaces.actions;

import javax.swing.Icon;
import javax.swing.JList;
import com.chrisbartley.idea.workspaces.Icons;
import com.chrisbartley.idea.workspaces.Workspace;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class ConfigureWorkspaceAction extends BaseWorkspaceAction
   {
   private final JList list;
   private Workspace workspace;

   /**
    * @param workspace The workspace to be configured.
    */
   public ConfigureWorkspaceAction(final Workspace workspace)
      {
      this(workspace.getName(), "Configure '" + workspace.getName() + "'", null, null, workspace);
      }

   /**
    * @param list The list containing the workspace to be configured.
    */
   public ConfigureWorkspaceAction(final JList list)
      {
      this("Properties", "Configure the selected workspace", Icons.CONFIGURE_WORKSPACE, list, null);
      }

   private ConfigureWorkspaceAction(final String text, final String description, final Icon icon, final JList list, final Workspace workspace)
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
         // get the workspace
         if ((list != null) && (!list.getSelectionModel().isSelectionEmpty()))
            {
            final Object[] selectedWorkspaces = list.getSelectedValues();
            if (selectedWorkspaces.length == 1)
               {
               this.workspace = (Workspace)selectedWorkspaces[0];
               }
            }

         // show the dialog
         if (this.workspace != null)
            {
            final ConfigureWorkspaceDialog dialog = new ConfigureWorkspaceDialog("Workspace Properties", "OK", this.workspace);
            dialog.pack();
            dialog.show();
            if (dialog.getExitCode() == ConfigureWorkspaceDialog.OK_EXIT_CODE)
               {
               this.workspace.update(dialog.getNewWorkspaceName(), dialog.getNewWorkspaceUrls());
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
            event.getPresentation().setEnabled((!list.getSelectionModel().isSelectionEmpty()) && (list.getSelectedIndices().length == 1));
            }
         else
            {
            event.getPresentation().setEnabled(true);
            }
         }
      }
   }
