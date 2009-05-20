package com.chrisbartley.idea.actions;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import com.chrisbartley.idea.util.Icons;
import com.chrisbartley.idea.util.ReorderableListModel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public class MoveDownAction extends AnAction
   {
   private final JList list;
   private final ReorderableListModel listModel;

   /**
    * @param list The list to manipulate.  The list's <code>ListModel</code> must be a
    * {@link ReorderableListModel}.
    */
   public MoveDownAction(final JList list)
      {
      super("Move down", "Move the selected item(s) down", Icons.MOVE_DOWN);
      this.list = list;
      this.listModel = (ReorderableListModel)list.getModel();
      }

   public void actionPerformed(final AnActionEvent event)
      {
      final ListSelectionModel selectionModel = list.getSelectionModel();
      if (!selectionModel.isSelectionEmpty())
         {
         // shift the indices
         final int[] selectedIndices = list.getSelectedIndices();
         listModel.shiftElements(selectedIndices, true);

         // now update the list selection
         for (int i = 0; i < selectedIndices.length; i++)
            {
            selectedIndices[i] = selectedIndices[i] + 1;
            }
         list.setSelectedIndices(selectedIndices);
         list.ensureIndexIsVisible(selectedIndices[selectedIndices.length - 1]);
         }
      }

   public void update(final AnActionEvent event)
      {
      final ListSelectionModel selectionModel = list.getSelectionModel();
      final int lastSelectedIndex = selectionModel.getMaxSelectionIndex();
      event.getPresentation().setEnabled((lastSelectedIndex >= 0) && (lastSelectedIndex < (listModel.size() - 1)));
      }
   }
