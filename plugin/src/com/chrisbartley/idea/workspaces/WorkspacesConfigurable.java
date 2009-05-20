package com.chrisbartley.idea.workspaces;

import java.util.List;
import javax.swing.Icon;
import javax.swing.JComponent;
import com.chrisbartley.idea.actions.MutableActionGroup;
import com.chrisbartley.idea.actions.RegisterableAction;
import com.chrisbartley.idea.workspaces.actions.CloseAllNonWorkspaceFilesAction;
import com.chrisbartley.idea.workspaces.actions.CloseAllWorkspacesAction;
import com.chrisbartley.idea.workspaces.actions.CreateWorkspaceAction;
import com.chrisbartley.idea.workspaces.actions.WorkspaceMutableActionGroupStrategy;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Anchor;
import com.intellij.openapi.actionSystem.Constraints;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class WorkspacesConfigurable implements ApplicationComponent, Configurable
   {
   private static final String COMPONENT_NAME = "WorkspacesConfigurable";
   private static final String DISPLAY_NAME = "Workspaces";
   private static final Constraints WORKSPACES_MENU_PLACEMENT = new Constraints(Anchor.BEFORE, "HelpMenu");

   private WorkspacesConfiguration workspacesConfiguration;
   private WorkspacesConfigurationPanel configurationPanel;

   private DefaultActionGroup workspacesMenu = new DefaultActionGroup("Wor_kspaces", false);
   private final MutableActionGroup toggleWorkspaceOpennessActionGroup = new MutableActionGroup(new ToggleWorkspaceOpennessActionGroup());
   private final MutableActionGroup closeAllWorkspacesExceptThisActionGroup = new MutableActionGroup(new CloseAllWorkspacesExceptThisActionGroup(), "Close All Workspaces Except", true);

   private final MutableActionGroup togglePinActionGroup = new MutableActionGroup(new ToggleWorkspacePinActionGroup(), "Toggle Pin", true, Icons.PINNED);
   private final MutableActionGroup configureActionGroup = new MutableActionGroup(new ConfigureWorkspaceActionGroup(), "Properties", true, Icons.CONFIGURE_WORKSPACE);
   private final MutableActionGroup removeActionGroup = new MutableActionGroup(new RemoveWorkspaceActionGroup(), "Remove", true);

   private final RegisterableAction createWorkspaceAction = new CreateWorkspaceAction();
   private final RegisterableAction closeAllWorkspacesAction = new CloseAllWorkspacesAction();
   private final RegisterableAction closeAllNonWorkspaceFilesAction = new CloseAllNonWorkspaceFilesAction();

   public WorkspacesConfigurable()
      {
      super();
      }

   public String getComponentName()
      {
      return COMPONENT_NAME;
      }

   public void initComponent()
      {
      // load the configuration
      workspacesConfiguration = (WorkspacesConfiguration)ApplicationManager.getApplication().getComponent(WorkspacesConfiguration.class);

      // build the Workspaces menu
      workspacesMenu.add(toggleWorkspaceOpennessActionGroup);
      workspacesMenu.addSeparator();
      workspacesMenu.add(closeAllWorkspacesExceptThisActionGroup);
      workspacesMenu.add(closeAllWorkspacesAction);
      workspacesMenu.add(closeAllNonWorkspaceFilesAction);
      workspacesMenu.addSeparator();
      workspacesMenu.add(togglePinActionGroup);
      workspacesMenu.add(configureActionGroup);
      workspacesMenu.addSeparator();
      workspacesMenu.add(removeActionGroup);
      workspacesMenu.add(createWorkspaceAction);

      // register the Workspace actions
      createWorkspaceAction.register();
      closeAllWorkspacesAction.register();
      closeAllNonWorkspaceFilesAction.register();

      // show the menu
      if (workspacesConfiguration.isDisplayMainMenuUI())
         {
         showHideWorkspacesMenu(true);
         }
      }

   public void disposeComponent()
      {
      // unregister the Workspace actions
      createWorkspaceAction.unregister();
      closeAllWorkspacesAction.unregister();
      closeAllNonWorkspaceFilesAction.unregister();

      // empty the groups
      toggleWorkspaceOpennessActionGroup.removeAll();
      closeAllWorkspacesExceptThisActionGroup.removeAll();
      togglePinActionGroup.removeAll();
      configureActionGroup.removeAll();
      removeActionGroup.removeAll();

      // empty the menu
      workspacesMenu.removeAll();

      // hide the menu
      if (workspacesConfiguration.isDisplayMainMenuUI())
         {
         showHideWorkspacesMenu(false);
         }
      }

   public String getDisplayName()
      {
      return DISPLAY_NAME;
      }

   public Icon getIcon()
      {
      return Icons.WORKSPACES_CONFIGURABLE;
      }

   public String getHelpTopic()
      {
      return null;
      }

   public JComponent createComponent()
      {
      configurationPanel = new WorkspacesConfigurationPanel();
      return configurationPanel;
      }

   public boolean isModified()
      {
      final WorkspacesConfiguration configuration = new WorkspacesConfiguration();
      saveToConfiguration(configuration);
      return !configuration.equals(workspacesConfiguration);
      }

   public void apply() throws ConfigurationException
      {
      saveToConfiguration(workspacesConfiguration);
      showHideWorkspacesMenu(workspacesConfiguration.isDisplayMainMenuUI());
      showHideToolWindow(workspacesConfiguration.isDisplayToolWindowUI());
      }

   private void saveToConfiguration(final WorkspacesConfiguration configuration)
      {
      configurationPanel.copyConfigurationTo(configuration);
      }

   public void reset()
      {
      configurationPanel.copyConfigurationFrom(workspacesConfiguration);
      }

   public void disposeUIResources()
      {
      configurationPanel = null;
      }

   private void showHideWorkspacesMenu(final boolean show)
      {
      final DefaultActionGroup mainMenu = (DefaultActionGroup)ActionManager.getInstance().getAction(ActionPlaces.MAIN_MENU);

      // remove the Workspaces menu from the main menu
      mainMenu.remove(workspacesMenu);

      if (show)
         {
         // add the Workspaces menu to the main menu
         mainMenu.add(workspacesMenu, WORKSPACES_MENU_PLACEMENT);
         }
      }

   private void showHideToolWindow(final boolean show)
      {
      final Project[] projects = ProjectManager.getInstance().getOpenProjects();
      for (int i = 0; i < projects.length; i++)
         {
         final Project project = projects[i];
         final WorkspaceManager workspaceManager = (WorkspaceManager)project.getComponent(WorkspaceManager.class);
         workspaceManager.showHideToolWindow(show);
         }
      }

   public RegisterableAction getCloseAllWorkspacesAction()
      {
      return closeAllWorkspacesAction;
      }

   public RegisterableAction getCloseAllNonWorkspaceFilesAction()
      {
      return closeAllNonWorkspaceFilesAction;
      }

   private static final class ToggleWorkspaceOpennessActionGroup extends WorkspaceMutableActionGroupStrategy
      {
      public List getActions(final AnActionEvent event)
         {
         return getWorkspaceManager(event).getToggleWorkspaceOpennessActions();
         }
      }

   private static final class CloseAllWorkspacesExceptThisActionGroup extends WorkspaceMutableActionGroupStrategy
      {
      public List getActions(final AnActionEvent event)
         {
         return getWorkspaceManager(event).getCloseAllWorkspacesExceptThisActions();
         }
      }

   private static final class ToggleWorkspacePinActionGroup extends WorkspaceMutableActionGroupStrategy
      {
      public List getActions(final AnActionEvent event)
         {
         return getWorkspaceManager(event).getToggleWorkspacePinActions();
         }
      }

   private static final class ConfigureWorkspaceActionGroup extends WorkspaceMutableActionGroupStrategy
      {
      public List getActions(final AnActionEvent event)
         {
         return getWorkspaceManager(event).getConfigureWorkspaceActions();
         }
      }

   private static final class RemoveWorkspaceActionGroup extends WorkspaceMutableActionGroupStrategy
      {
      public List getActions(final AnActionEvent event)
         {
         return getWorkspaceManager(event).getRemoveWorkspaceActions();
         }
      }
   }