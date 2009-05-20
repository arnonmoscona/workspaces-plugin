package com.chrisbartley.idea.workspaces.actions;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import com.chrisbartley.idea.util.IncludableItem;
import com.chrisbartley.idea.workspaces.Icons;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
final class ConfigureWorkspaceFileListCellRenderer extends JLabel implements ListCellRenderer
   {
   public ConfigureWorkspaceFileListCellRenderer()
      {
      setOpaque(true);
      setHorizontalAlignment(SwingConstants.LEFT);
      }

   public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus)
      {
      final IncludableItem item = (IncludableItem)value;
      final String url = (String)item.getItem();
      final VirtualFile virtualFile = VirtualFileManager.getInstance().findFileByUrl(url);

      setText(virtualFile.getPresentableUrl());
      setIcon(item.isIncluded() ? Icons.INCLUDED : Icons.EXCLUDED);

      if (isSelected)
         {
         setBackground(list.getSelectionBackground());
         setForeground(list.getSelectionForeground());
         }
      else
         {
         setBackground(list.getBackground());
         setForeground(list.getForeground());
         }

      return this;
      }
   }
