package com.chrisbartley.idea.workspaces.actions;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

import com.chrisbartley.idea.util.VirtualFileUtils;
import com.chrisbartley.idea.workspaces.Workspace;
import com.chrisbartley.idea.workspaces.Icons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author John De Lello JohnD@DelWare.com
 */
public final class AddCurrentEditorToWorkspaceAction extends BaseWorkspaceAction {
    private final JList list;
    private Workspace workspace;

    public AddCurrentEditorToWorkspaceAction(final Workspace workspace) {
        this(workspace.getName(), "Add Currently Selected File to '" + workspace.getName() + "'", null, null, workspace);
    }

    public AddCurrentEditorToWorkspaceAction(final JList list) {
        this("Add Current Editor File to Workspace", "Add Current Editor File to Workspace", Icons.ADD_CURR_FILE, list, null);
    }

    private AddCurrentEditorToWorkspaceAction(final String text, final String description, final Icon icon, final JList list, final Workspace workspace) {
        super(text, description, icon);
        this.list = list;
        this.workspace = workspace;
    }

    public void actionPerformed(final AnActionEvent event) {
        final Project project = getProject(event);
        if (project != null) {
            if ((list != null) && (!list.getSelectionModel().isSelectionEmpty())) {
                final Object[] selectedWorkspaces = list.getSelectedValues();
                if (selectedWorkspaces.length == 1) {
                    this.workspace = (Workspace) selectedWorkspaces[0];
                }
            }

            if (this.workspace != null) {
                final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);

                VirtualFile[] selectedFiles = fileEditorManager.getSelectedFiles();
                List selectedFileList = VirtualFileUtils.getUrls(selectedFiles);

                if (selectedFileList != null && selectedFileList.size() > 0) {
                    List currentFileList = workspace.getFileUrls();
                    List newFileList = new ArrayList();

                    newFileList.addAll(currentFileList);
                    newFileList.addAll(selectedFileList);

                    workspace.update(workspace.getName(), newFileList);
                }
            }
        }
    }

    public void update(final AnActionEvent event) {
        final Project project = getProject(event);
        if (project != null) {
            if (list != null) {
                event.getPresentation().setEnabled(!list.getSelectionModel().isSelectionEmpty());
                if (list.getSelectedValues().length == 1) {
                    event.getPresentation().setText("Add the current editor file to this workspace.");
                    event.getPresentation().setDescription("Add the current editor file to this workspace.");
                } else {
                    event.getPresentation().setText("Add the current editor files to this workspace.");
                    event.getPresentation().setDescription("Add the current editor files to this workspace.");
                }
            } else {
                event.getPresentation().setEnabled(true);
            }
        }
    }
}