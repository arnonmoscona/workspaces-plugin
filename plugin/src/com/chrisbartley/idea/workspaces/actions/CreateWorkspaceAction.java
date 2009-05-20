package com.chrisbartley.idea.workspaces.actions;

import java.util.Set;
import com.chrisbartley.idea.util.VirtualFileUtils;
import com.chrisbartley.idea.workspaces.Icons;
import com.chrisbartley.idea.workspaces.WorkspaceActionPlaces;
import com.chrisbartley.idea.workspaces.WorkspaceManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class CreateWorkspaceAction extends BaseWorkspaceAction
   {
   public CreateWorkspaceAction()
      {
      super("Create New from Open File(s)...", "Create a new workspace from some or all of the currently open files", null);
      }

   /**
    * Gets info about the new workspace to create, and tells the manager to create it.
    */
   public void actionPerformed(final AnActionEvent event)
      {
      final Project project = getProject(event);
      if (project != null)
         {
         final WorkspaceManager workspaceManager = getWorkspaceManager(project);
         final Set boundFileUrls = workspaceManager.getBoundFileUrls();
         final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);

         final CreateWorkspaceDialog dialog = new CreateWorkspaceDialog("Create a New Workspace", "Create", boundFileUrls, VirtualFileUtils.getUrls(fileEditorManager.getOpenFiles()));
         dialog.pack();
         dialog.show();
         if (dialog.getExitCode() == CreateWorkspaceDialog.CREATE_EXIT_CODE)
            {
            workspaceManager.createWorkspace(dialog.getNewWorkspaceName(), dialog.getNewWorkspaceUrls());
            }
         }
      }

   /**
    * Disables the action if no project is open or if no files are open.
    */
   public void update(final AnActionEvent event)
      {
      final Project project = getProject(event);
      final Presentation presentation = event.getPresentation();
      if (project != null)
         {
         final VirtualFile[] openFiles = FileEditorManager.getInstance(project).getOpenFiles();
         final String text;
         final String description;
         switch (openFiles.length)
            {
            case 0:
               {
               text = "Create New from Open File(s)...";
               description = "Create a new workspace from some or all of the currently open files";
               break;
               }
            case 1:
               {
               text = "Create New from Open File...";
               description = "Create a new workspace from the currently open file";
               break;
               }
            default:
               {
               text = "Create New from Open Files...";
               description = "Create a new workspace from some or all of the currently open files";
               break;
               }
            }
         presentation.setText(text);
         presentation.setDescription(description);
         presentation.setEnabled((openFiles != null) && (openFiles.length > 0));
         }
      else
         {
         presentation.setText("Create New from Open File(s)...");
         presentation.setDescription("Create a new workspace from some or all of the currently open files");
         presentation.setEnabled(false);
         }

      // only show the icon if it's being displayed in the workspaces tool window
      if (WorkspaceActionPlaces.WORKSPACES_TOOL_WINDOW.equals(event.getPlace()))
         {
         presentation.setIcon(Icons.ADD_WORKSPACE);
         }
      }
   }
