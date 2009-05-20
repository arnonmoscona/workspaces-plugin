package com.chrisbartley.idea.actions;

import com.intellij.openapi.actionSystem.*;
import com.chrisbartley.idea.workspaces.WorkspacesConfigurable;
import com.chrisbartley.idea.workspaces.Icons;
import com.chrisbartley.idea.workspaces.actions.CreateWorkspaceAction;
import com.chrisbartley.idea.workspaces.actions.CloseAllWorkspacesAction;
import com.chrisbartley.idea.workspaces.actions.CloseAllNonWorkspaceFilesAction;

public final class EditorPopupMenuAction extends AnAction {
    private static final String EDITOR_POPUP_MENU_GROUP_ID = "EditorPopupMenu";
    private static final String WORKSPACE_EDIT_POPUP_MENU_GROUP_ID = "Workspaces.EditMenu";

    public void actionPerformed(AnActionEvent event) {
    }

    public void update(final AnActionEvent event) {
        final ActionManager manager = ActionManager.getInstance();
        final DefaultActionGroup editorGroup = getActionGroup(manager, EDITOR_POPUP_MENU_GROUP_ID);
        final DefaultActionGroup workspaceEditMenuGroup = getActionGroup(manager, WORKSPACE_EDIT_POPUP_MENU_GROUP_ID);

        if (editorGroup != null) {

            if (workspaceEditMenuGroup != null) {
                workspaceEditMenuGroup.removeAll();
                DefaultActionGroup workspaceEditMenuGroup2 = new DefaultActionGroup("Wor_kspaces", true);

                final MutableActionGroup toggleWorkspaceOpennessActionGroup = new MutableActionGroup(new WorkspacesConfigurable.ToggleWorkspaceOpennessActionGroup());
                final MutableActionGroup closeAllWorkspacesExceptThisActionGroup = new MutableActionGroup(new WorkspacesConfigurable.CloseAllWorkspacesExceptThisActionGroup(), "Close All Workspaces Except", true);
                final MutableActionGroup addCurrentEditorToWorkspaceGroup = new MutableActionGroup(new WorkspacesConfigurable.AddCurrentEditorToWorkspaceActionGroup(), "Add Currently Selected File to ", true);
                final MutableActionGroup togglePinActionGroup = new MutableActionGroup(new WorkspacesConfigurable.ToggleWorkspacePinActionGroup(), "Toggle Pin", true, Icons.PINNED);
                final MutableActionGroup configureActionGroup = new MutableActionGroup(new WorkspacesConfigurable.ConfigureWorkspaceActionGroup(), "Properties", true, Icons.CONFIGURE_WORKSPACE);
                final MutableActionGroup removeActionGroup = new MutableActionGroup(new WorkspacesConfigurable.RemoveWorkspaceActionGroup(), "Remove", true);
                final RegisterableAction createWorkspaceAction = new CreateWorkspaceAction();
                final RegisterableAction closeAllWorkspacesAction = new CloseAllWorkspacesAction();
                final RegisterableAction closeAllNonWorkspaceFilesAction = new CloseAllNonWorkspaceFilesAction();

                workspaceEditMenuGroup2.add(toggleWorkspaceOpennessActionGroup);
                workspaceEditMenuGroup2.addSeparator();
                workspaceEditMenuGroup2.add(closeAllWorkspacesExceptThisActionGroup);
                workspaceEditMenuGroup2.add(closeAllWorkspacesAction);
                workspaceEditMenuGroup2.add(closeAllNonWorkspaceFilesAction);
                workspaceEditMenuGroup2.addSeparator();
                workspaceEditMenuGroup2.add(addCurrentEditorToWorkspaceGroup);
                workspaceEditMenuGroup2.addSeparator();
                workspaceEditMenuGroup2.add(togglePinActionGroup);
                workspaceEditMenuGroup2.add(configureActionGroup);
                workspaceEditMenuGroup2.addSeparator();
                workspaceEditMenuGroup2.add(removeActionGroup);
                workspaceEditMenuGroup2.add(createWorkspaceAction);

                createWorkspaceAction.register();
                closeAllWorkspacesAction.register();
                closeAllNonWorkspaceFilesAction.register();

                editorGroup.add(workspaceEditMenuGroup2);
            }
        }
    }

    private DefaultActionGroup getActionGroup(final ActionManager manager, String groupToGet) {
        final AnAction action = manager.getAction(groupToGet);
        return (action instanceof DefaultActionGroup) ? (DefaultActionGroup) action : null;
    }

}
