package com.chrisbartley.idea.workspaces;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
final class WorkspacesToolWindowListCellRenderer extends JPanel implements ListCellRenderer
   {
   private final FileEditorManager fileEditorManager;
   private final JLabel isPinnedArea = new JLabel();
   private final JLabel isOpenArea = new JLabel();
   private final JLabel nameArea = new JLabel();
   private final JLabel openFilesArea = new JLabel();
   private final JLabel numFilesArea = new JLabel();
   private final JLabel isActiveArea = new JLabel();

   public WorkspacesToolWindowListCellRenderer(final Project project)
      {
      this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      this.setAlignmentX(Component.LEFT_ALIGNMENT);
      this.setOpaque(true);
      fileEditorManager = FileEditorManager.getInstance(project);
      add(Box.createRigidArea(new Dimension(3, 0)));
      add(isPinnedArea);
      add(isOpenArea);
      add(nameArea);
      add(Box.createGlue());
      add(new JLabel("[ "));
      add(openFilesArea);
      add(new JLabel(" / "));
      add(numFilesArea);
      add(new JLabel(" ]"));
      add(Box.createRigidArea(new Dimension(3, 0)));
      add(isActiveArea);
      add(Box.createRigidArea(new Dimension(3, 0)));
      }

   public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus)
      {
      if (isSelected)
         {
         this.setForeground(list.getSelectionForeground());
         this.setBackground(list.getSelectionBackground());
         }
      else
         {
         this.setForeground(list.getForeground());
         this.setBackground(list.getBackground());
         }

      final Workspace workspace = (Workspace)value;
      final WorkspaceState workspaceState = workspace.getState(fileEditorManager);

      nameArea.setText(workspace.getName());
      openFilesArea.setText(String.valueOf(workspaceState.getNumOpenFiles()));
      numFilesArea.setText(String.valueOf(workspaceState.getNumFiles()));
      isPinnedArea.setIcon(workspaceState.getPinnedStatusIcon());
      isOpenArea.setIcon(workspaceState.getOpenedStatusIcon());
      isActiveArea.setIcon(workspace.contains(fileEditorManager.getSelectedFile()) ? Icons.ACTIVE_WORKSPACE : Icons.INACTIVE_WORKSPACE);

      return this;
      }
   }
