package com.chrisbartley.idea.workspaces;

import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputAdapter;
import com.chrisbartley.idea.util.RefreshableListModel;
import com.chrisbartley.idea.util.WrappedListModel;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
final class WorkspacesToolWindowMouseListener extends MouseInputAdapter
   {
   private final JList list;
   private final Project project;
   private final JPopupMenu jPopupMenu;

   /**
    * @param list The list to manipulate.  The list's <code>ListModel</code> must be a
    * {@link RefreshableListModel}.
    * @param project The project within which this listener's tool window appears.
    * @param jPopupMenu The popup menu to show
    */
   public WorkspacesToolWindowMouseListener(final JList list, final Project project, final JPopupMenu jPopupMenu)
      {
      this.list = list;
      this.project = project;
      this.jPopupMenu = jPopupMenu;
      }

   public void mouseClicked(final MouseEvent e)
      {
      if (e.getButton() == MouseEvent.BUTTON3)
         {
         // Make right-clicking work like in IDEA.  That is, if right-click on a selected index, just
         // show the popup.  But, if right-click is on an UNselected index, then set the selection
         // to that index and then show the popup.
         final int indexOfClickedItem = list.locationToIndex(e.getPoint());
         if ((indexOfClickedItem >= 0) && (!list.isSelectedIndex(indexOfClickedItem)))
            {
            list.setSelectedIndex(indexOfClickedItem);
            }

         // show the popup
         jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
         }
      else if (e.getButton() == MouseEvent.BUTTON1)
         {
         // open a workspace upon double-click
         if (e.getClickCount() == 2)
            {
            openWorkspace(list.locationToIndex(e.getPoint()));
            }
         }
      }

   private void openWorkspace(final int index)
      {
      final Workspace workspace = getWorkspaceByIndex(index);
      if (workspace != null)
         {
         final WorkspaceManager workspaceManager = (WorkspaceManager)project.getComponent(WorkspaceManager.class);
         workspaceManager.openWorkspace(workspace);

         // refresh the newly-opened workspace's item in the list
         final RefreshableListModel model = (RefreshableListModel)list.getModel();
         model.refresh(index);
         }
      }

   private Workspace getWorkspaceByIndex(final int index)
      {
      final WrappedListModel model = (WrappedListModel)list.getModel();
      return (model.isIndexWithinBounds(index) ? (Workspace)model.get(index) : null);
      }
   }
