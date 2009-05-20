package com.chrisbartley.idea.workspaces.actions;

import javax.swing.JList;
import com.chrisbartley.idea.util.IncludableItem;
import com.chrisbartley.idea.util.RefreshableListModel;
import com.chrisbartley.idea.workspaces.Icons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
final class IncludeAction extends AnAction
   {
   private final JList list;
   private final RefreshableListModel listModel;

   /**
    * @param list The list to manipulate.  Must be a list of {@link IncludableItem} objects.  The
    * list's <code>ListModel</code> must be a {@link RefreshableListModel}.
    */
   public IncludeAction(final JList list)
      {
      super("Include", "Include the selected files(s)", Icons.INCLUDE_WORKSPACE_ITEM);
      this.list = list;
      this.listModel = (RefreshableListModel)list.getModel();
      }

   public void actionPerformed(final AnActionEvent event)
      {
      if (!list.getSelectionModel().isSelectionEmpty())
         {
         final Object[] selectedItems = list.getSelectedValues();
         for (int i = 0; i < selectedItems.length; i++)
            {
            final IncludableItem item = (IncludableItem)selectedItems[i];
            item.setIncluded(true);
            }
         listModel.refresh(list.getSelectedIndices());
         }
      }

   public void update(final AnActionEvent event)
      {
      event.getPresentation().setEnabled(!list.getSelectionModel().isSelectionEmpty());
      }
   }
