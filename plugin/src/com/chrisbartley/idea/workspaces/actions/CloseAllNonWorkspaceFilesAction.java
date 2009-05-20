package com.chrisbartley.idea.workspaces.actions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import com.chrisbartley.idea.util.VirtualFileUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class CloseAllNonWorkspaceFilesAction extends BaseWorkspaceAction
   {
   public CloseAllNonWorkspaceFilesAction()
      {
      super("Close All Non-Workspace Files", "Close all files which are not bound to a workspace", null);
      }

   public void actionPerformed(final AnActionEvent event)
      {
      final Project project = getProject(event);
      if (project != null)
         {
         final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
         final VirtualFile[] openFiles = fileEditorManager.getOpenFiles();
         if (openFiles.length > 0)
            {
            final Set openNonWorkspacedUrls = getOpenNonWorkspacedUrls(project, openFiles);
            for (final Iterator iterator = openNonWorkspacedUrls.iterator(); iterator.hasNext();)
               {
               final String url = (String)iterator.next();
               VirtualFileUtils.closeFileByUrl(fileEditorManager, url);
               }
            }
         }
      }

   public void update(final AnActionEvent event)
      {
      final Project project = getProject(event);
      if (project != null)
         {
         final VirtualFile[] openFiles = FileEditorManager.getInstance(project).getOpenFiles();
         if (openFiles.length > 0)
            {
            final Set openNonWorkspacedUrls = getOpenNonWorkspacedUrls(project, openFiles);
            event.getPresentation().setEnabled(openNonWorkspacedUrls.size() > 0);
            }
         else
            {
            event.getPresentation().setEnabled(false);
            }
         }
      else
         {
         event.getPresentation().setEnabled(false);
         }
      }

   private Set getOpenNonWorkspacedUrls(final Project project, final VirtualFile[] openFiles)
      {
      final Set openUrls = new HashSet(VirtualFileUtils.getUrls(openFiles));
      openUrls.removeAll(getWorkspaceManager(project).getBoundFileUrls());
      return openUrls;
      }
   }