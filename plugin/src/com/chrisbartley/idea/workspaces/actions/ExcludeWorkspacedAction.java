package com.chrisbartley.idea.workspaces.actions;

import java.util.ListIterator;
import java.util.Set;
import com.chrisbartley.idea.util.IncludableItem;
import com.chrisbartley.idea.util.WrappedListModel;
import com.chrisbartley.idea.workspaces.Icons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
final class ExcludeWorkspacedAction extends AnAction
   {
   private final Set boundFileUrls;
   private final WrappedListModel listModel;

   /**
    * @param listModel The list to manipulate.  Must be a list of {@link IncludableItem} objects.
    */
   public ExcludeWorkspacedAction(final Set boundFileUrls, final WrappedListModel listModel)
      {
      super("Exclude bound files", "Exclude files that are already bound to a workspace", Icons.EXCLUDE_WORKSPACED);
      this.boundFileUrls = boundFileUrls;
      this.listModel = listModel;
      }

   public void actionPerformed(final AnActionEvent event)
      {
      if (listModel.getSize() > 0)
         {
         for (final ListIterator listIterator = listModel.listIterator(); listIterator.hasNext();)
            {
            final IncludableItem item = (IncludableItem)listIterator.next();
            if (boundFileUrls.contains(item.getItem()))
               {
               item.setIncluded(false);
               }
            }

         listModel.refreshAll();
         }
      }

   public void update(final AnActionEvent event)
      {
      event.getPresentation().setEnabled(listModel.getSize() > 0);
      }
   }
