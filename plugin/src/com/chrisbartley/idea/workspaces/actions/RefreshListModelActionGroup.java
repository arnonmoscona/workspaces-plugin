package com.chrisbartley.idea.workspaces.actions;

import com.chrisbartley.idea.actions.BaseActionGroup;
import com.chrisbartley.idea.util.RefreshableListModel;
import com.chrisbartley.idea.workspaces.WorkspaceManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class RefreshListModelActionGroup extends BaseActionGroup
   {
   private final RefreshableListModel listModel;

   public RefreshListModelActionGroup(final RefreshableListModel listModel)
      {
      this(null, false, listModel);
      }

   public RefreshListModelActionGroup(final String shortName, final boolean popup, final RefreshableListModel listModel)
      {
      super(shortName, popup);
      this.listModel = listModel;
      }

   public void update(final AnActionEvent event)
      {
      final Project project = getProject(event);
      if (project != null)
         {
         final WorkspaceManager workspaceManager = (WorkspaceManager)project.getComponent(WorkspaceManager.class);
         workspaceManager.updateImplicitAutoPinningAndUnpinning();
         listModel.refreshAll();
         }
      }
   }