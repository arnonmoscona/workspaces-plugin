package com.chrisbartley.idea.workspaces;

import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.chrisbartley.idea.actions.MoveDownAction;
import com.chrisbartley.idea.actions.MoveUpAction;
import com.chrisbartley.idea.util.ReorderableListModel;
import com.chrisbartley.idea.workspaces.actions.CloseAllWorkspacesExceptThisAction;
import com.chrisbartley.idea.workspaces.actions.CloseWorkspaceAction;
import com.chrisbartley.idea.workspaces.actions.ConfigureWorkspaceAction;
import com.chrisbartley.idea.workspaces.actions.CreateWorkspaceAction;
import com.chrisbartley.idea.workspaces.actions.OpenWorkspaceAction;
import com.chrisbartley.idea.workspaces.actions.RefreshListModelActionGroup;
import com.chrisbartley.idea.workspaces.actions.RemoveWorkspaceAction;
import com.chrisbartley.idea.workspaces.actions.ToggleWorkspacePinAction;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPopupMenu;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Separator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
final class WorkspacesToolWindow extends JPanel
   {
   public WorkspacesToolWindow(final Project project, final WorkspacesConfiguration workspacesConfiguration, final JList jList)
      {
      super(new BorderLayout());

      // create the buttons
      final RefreshListModelActionGroup toolbarGroup = new RefreshListModelActionGroup((ReorderableListModel)jList.getModel());
      final OpenWorkspaceAction openWorkspaceAction = new OpenWorkspaceAction(jList);
      final CloseWorkspaceAction closeWorkspaceAction = new CloseWorkspaceAction(jList);
      final ToggleWorkspacePinAction toggleWorkspacePinnedAction = new ToggleWorkspacePinAction(jList);
      final ConfigureWorkspaceAction configureWorkspaceAction = new ConfigureWorkspaceAction(jList);
      final RemoveWorkspaceAction removeAction = new RemoveWorkspaceAction(jList, true);
      toolbarGroup.add(openWorkspaceAction);
      toolbarGroup.add(closeWorkspaceAction);
      toolbarGroup.add(toggleWorkspacePinnedAction);
      toolbarGroup.add(new CreateWorkspaceAction());
      toolbarGroup.add(removeAction);
      toolbarGroup.add(new MoveUpAction(jList));
      toolbarGroup.add(new MoveDownAction(jList));
      toolbarGroup.add(configureWorkspaceAction);
      final ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(WorkspaceActionPlaces.WORKSPACES_TOOL_WINDOW, toolbarGroup, true);

      // create the popup menu and add the mouse listener to the JList
      final DefaultActionGroup popupActionGroup = new DefaultActionGroup("WorkspacesPopup", true);
      final WorkspacesConfigurable workspacesConfigurable = (WorkspacesConfigurable)ApplicationManager.getApplication().getComponent(WorkspacesConfigurable.class);
      if (workspacesConfiguration.isDisplayButtonActionsInContextMenu())
         {
         popupActionGroup.add(openWorkspaceAction);
         popupActionGroup.add(closeWorkspaceAction);
         popupActionGroup.add(toggleWorkspacePinnedAction);
         popupActionGroup.add(Separator.getInstance());
         }
      final CloseAllWorkspacesExceptThisAction closeAllWorkspacesExceptThisAction = new CloseAllWorkspacesExceptThisAction(jList);
      popupActionGroup.add(closeAllWorkspacesExceptThisAction);
      popupActionGroup.add(workspacesConfigurable.getCloseAllWorkspacesAction());
      popupActionGroup.add(workspacesConfigurable.getCloseAllNonWorkspaceFilesAction());
      if (workspacesConfiguration.isDisplayButtonActionsInContextMenu())
         {
         popupActionGroup.add(Separator.getInstance());
         popupActionGroup.add(removeAction);
         popupActionGroup.add(Separator.getInstance());
         popupActionGroup.add(configureWorkspaceAction);
         }
      final ActionPopupMenu popup = ActionManager.getInstance().createActionPopupMenu(WorkspaceActionPlaces.WORKSPACES_TOOL_WINDOW_POPUP, popupActionGroup);

      jList.addMouseListener(new WorkspacesToolWindowMouseListener(jList, project, popup.getComponent()));

      // add the list box and buttons to this panel
      this.add(toolbar.getComponent(), BorderLayout.NORTH);
      this.add(new JScrollPane(jList), BorderLayout.CENTER);
      }
   }
