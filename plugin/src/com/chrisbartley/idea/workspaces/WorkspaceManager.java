package com.chrisbartley.idea.workspaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import com.chrisbartley.idea.util.ReorderableListModel;
import com.chrisbartley.idea.workspaces.actions.CloseAllWorkspacesExceptThisAction;
import com.chrisbartley.idea.workspaces.actions.ConfigureWorkspaceAction;
import com.chrisbartley.idea.workspaces.actions.RemoveWorkspaceAction;
import com.chrisbartley.idea.workspaces.actions.ToggleWorkspaceOpennessAction;
import com.chrisbartley.idea.workspaces.actions.ToggleWorkspacePinAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.JDOMExternalizableList;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jdom.Element;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class WorkspaceManager implements ProjectComponent, JDOMExternalizable
   {
   private static final String COMPONENT_NAME = "WorkspaceManager";
   private static final String TOOL_WINDOW_ID = "Workspaces";

   public JDOMExternalizableList workspaces = new JDOMExternalizableList();

   private WorkspacesConfiguration workspacesConfiguration;
   private final Project project;
   private ReorderableListModel workspacesModel;
   private final JList jList = new JList();

   public WorkspaceManager(final Project project)
      {
      super();
      this.project = project;
      }

   public String getComponentName()
      {
      return COMPONENT_NAME;
      }

   public void initComponent()
      {
      // load the configuration
      workspacesConfiguration = (WorkspacesConfiguration)ApplicationManager.getApplication().getComponent(WorkspacesConfiguration.class);

      // create the list model
      workspacesModel = new ReorderableListModel(workspaces);
      jList.setModel(workspacesModel);
      jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      jList.setCellRenderer(new WorkspacesToolWindowListCellRenderer(project));
      }

   public void disposeComponent()
      {
      }

   public void projectOpened()
      {
      if (workspacesConfiguration.isDisplayToolWindowUI())
         {
         showHideToolWindow(true);
         }
      }

   public void projectClosed()
      {
      showHideToolWindow(false);
      }

   public void updateImplicitAutoPinningAndUnpinning()
      {
      final boolean isAutoPinImplicitly = (workspacesConfiguration.isAutoPin() && !workspacesConfiguration.isAutoPinUponExplicitOpenOnly());
      final boolean isAutoUnpinImplicitly = (workspacesConfiguration.isAutoUnpin() && !workspacesConfiguration.isAutoUnpinUponExplicitCloseOnly());
      if (isAutoPinImplicitly || isAutoUnpinImplicitly)
         {
         final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
         for (final ListIterator listIterator = workspacesModel.listIterator(); listIterator.hasNext();)
            {
            final Workspace workspace = (Workspace)listIterator.next();
            final WorkspaceState workspaceState = workspace.getState(fileEditorManager);
            if (isAutoPinImplicitly && workspaceState.isOpen())
               {
               workspace.setPinned(true);
               }
            if (isAutoUnpinImplicitly && workspaceState.isClosed())
               {
               workspace.setPinned(false);
               }
            }
         }
      }

   public void showHideToolWindow(final boolean show)
      {
      final ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
      if (show)
         {
         registerToolWindow(toolWindowManager);
         }
      else
         {
         unregisterToolWindow(toolWindowManager);
         }
      }

   private void registerToolWindow(final ToolWindowManager toolWindowManager)
      {
      if (toolWindowManager.getToolWindow(TOOL_WINDOW_ID) == null)
         {
         final ToolWindow workspacesToolWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, new WorkspacesToolWindow(project, workspacesConfiguration, jList), ToolWindowAnchor.LEFT);
         workspacesToolWindow.setIcon(Icons.WORKSPACES);
         }
      }

   private void unregisterToolWindow(final ToolWindowManager toolWindowManager)
      {
      if (toolWindowManager.getToolWindow(TOOL_WINDOW_ID) != null)
         {
         toolWindowManager.unregisterToolWindow(TOOL_WINDOW_ID);
         }
      }

   public Set getBoundFileUrls()
      {
      final Set fileUrls = new HashSet();
      for (final ListIterator listIterator = workspacesModel.listIterator(); listIterator.hasNext();)
         {
         final Workspace workspace = (Workspace)listIterator.next();
         fileUrls.addAll(workspace.getFileUrls());
         }
      return Collections.unmodifiableSet(fileUrls);
      }

   public void readExternal(final Element parentElement) throws InvalidDataException
      {
      DefaultJDOMExternalizer.readExternal(this, parentElement);
      }

   public void writeExternal(final Element parentElement) throws WriteExternalException
      {
      DefaultJDOMExternalizer.writeExternal(this, parentElement);
      }

   public int getWorkspaceCount()
      {
      return workspacesModel.size();
      }

   public void createWorkspace(final String name, final List urls)
      {
      if ((name != null) && (!urls.isEmpty()))
         {
         workspacesModel.add(new Workspace(name, urls, workspacesConfiguration.isAutoPin() && workspacesConfiguration.isAutoPinUponCreateWorkspace()));
         }
      }

   public void openWorkspace(final Workspace workspace)
      {
      if (workspace != null)
         {
         final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
         workspace.open(fileEditorManager, true, workspacesConfiguration.isAutoPin());
         }
      }

   public void openWorkspaces(final List workspacesToOpen)
      {
      if ((workspacesToOpen != null) && (workspacesToOpen.size() > 0))
         {
         final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
         int i = 1;
         for (final ListIterator listIterator = workspacesToOpen.listIterator(); listIterator.hasNext();)
            {
            final Workspace workspace = (Workspace)listIterator.next();
            workspace.open(fileEditorManager, (i == workspacesToOpen.size()), workspacesConfiguration.isAutoPin());
            i++;
            }
         }
      }

   public void toggleWorkspacePinnedness(final List workspacesToToggle)
      {
      if ((workspacesToToggle != null) && (workspacesToToggle.size() > 0))
         {
         for (final ListIterator listIterator = workspacesToToggle.listIterator(); listIterator.hasNext();)
            {
            toggleWorkspacePinnedness((Workspace)listIterator.next());
            }
         }
      }

   public void toggleWorkspacePinnedness(final Workspace workspace)
      {
      if (workspace != null)
         {
         workspace.setPinned(!workspace.isPinned());
         }
      }

   public void toggleWorkspaceOpenness(final Workspace workspace)
      {
      if (workspace != null)
         {
         final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
         final WorkspaceState workspaceState = workspace.getState(fileEditorManager);
         if (workspaceState.isOpen())
            {
            if (workspaceState.isPinned())
               {
               closeWorkspace(workspace);
               }
            else
               {
               if (workspacesConfiguration.isPinAnUnpinnedOpenWorkspaceSelectedFromMenu())
                  {
                  workspace.setPinned(true);
                  }
               else
                  {
                  closeWorkspace(workspace);
                  }
               }
            }
         else
            {
            openWorkspace(workspace);
            }
         }
      }

   public String getToggleWorkspaceOpennessDescription(final Workspace workspace)
      {
      final String actionStr;
      if (workspace != null)
         {
         final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
         final WorkspaceState workspaceState = workspace.getState(fileEditorManager);
         if (workspaceState.isOpen())
            {
            if (workspaceState.isPinned())
               {
               actionStr = "Close";
               }
            else
               {
               if (workspacesConfiguration.isPinAnUnpinnedOpenWorkspaceSelectedFromMenu())
                  {
                  actionStr = "Pin";
                  }
               else
                  {
                  actionStr = "Close";
                  }
               }
            }
         else
            {
            actionStr = "Open";
            }
         return actionStr + " the '" + workspace.getName() + "' workspace";
         }
      return null;
      }

   public void closeWorkspace(final Workspace workspace)
      {
      if (workspace != null)
         {
         final Set workspacesToClose = new HashSet();
         workspacesToClose.add(workspace);
         closeWorkspaces(workspacesToClose);
         }
      }

   public void closeWorkspaces(final Set workspacesToClose)
      {
      if ((workspacesToClose != null) && (workspacesToClose.size() > 0))
         {
         final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
         final Set urlsNotToBeClosed = getUrlsNotToBeClosed(workspacesToClose);
         for (final Iterator iterator = workspacesToClose.iterator(); iterator.hasNext();)
            {
            final Workspace workspace = (Workspace)iterator.next();
            workspace.close(fileEditorManager, urlsNotToBeClosed, workspacesConfiguration.isAutoUnpin());
            }
         }
      }

   public void closeAllWorkspaces()
      {
      final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
      for (final ListIterator listIterator = workspacesModel.listIterator(); listIterator.hasNext();)
         {
         final Workspace workspace = (Workspace)listIterator.next();
         workspace.close(fileEditorManager, null, workspacesConfiguration.isAutoUnpin());
         }
      }

   public void closeAllButThisWorkspace(final Workspace workspace)
      {
      final Set workspacesToBeClosed = new HashSet(workspacesModel);
      if (workspace != null)
         {
         workspacesToBeClosed.remove(workspace);
         }
      closeWorkspaces(workspacesToBeClosed);
      }

   public void closeAllButTheseWorkspaces(final List workspacesNotToClose)
      {
      final Set workspacesToBeClosed = new HashSet(workspacesModel);
      if (workspacesNotToClose != null)
         {
         workspacesToBeClosed.removeAll(workspacesNotToClose);
         }
      closeWorkspaces(workspacesToBeClosed);
      }

   private Set getUrlsNotToBeClosed(final Set workspacesToClose)
      {
      final Set urlsNotToBeClosed = new HashSet();

      final FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);

      // build a set of URLs that shouldn't be closed
      for (final ListIterator listIterator = workspacesModel.listIterator(); listIterator.hasNext();)
         {
         final Workspace workspace = (Workspace)listIterator.next();
         if (!workspacesToClose.contains(workspace))
            {
            final WorkspaceState workspaceState = workspace.getState(fileEditorManager);

            if (workspaceState.isPinned())
               {
               urlsNotToBeClosed.addAll(workspaceState.getOpenUrls());
               }
            }
         }
      return urlsNotToBeClosed;
      }

   public List getToggleWorkspaceOpennessActions()
      {
      // update the pinned state
      updateImplicitAutoPinningAndUnpinning();

      // build and return the list of actions
      final List actions = new ArrayList();
      for (final ListIterator listIterator = workspacesModel.listIterator(); listIterator.hasNext();)
         {
         actions.add(new ToggleWorkspaceOpennessAction((Workspace)listIterator.next()));
         }

      return actions;
      }

   public List getCloseAllWorkspacesExceptThisActions()
      {
      // build and return the list of actions
      final List actions = new ArrayList();
      for (final ListIterator listIterator = workspacesModel.listIterator(); listIterator.hasNext();)
         {
         actions.add(new CloseAllWorkspacesExceptThisAction((Workspace)listIterator.next()));
         }

      return actions;
      }

   public List getToggleWorkspacePinActions()
      {
      // build and return the list of actions
      final List actions = new ArrayList();
      for (final ListIterator listIterator = workspacesModel.listIterator(); listIterator.hasNext();)
         {
         actions.add(new ToggleWorkspacePinAction((Workspace)listIterator.next()));
         }

      return actions;
      }

   public List getConfigureWorkspaceActions()
      {
      // build and return the list of actions
      final List actions = new ArrayList();
      for (final ListIterator listIterator = workspacesModel.listIterator(); listIterator.hasNext();)
         {
         actions.add(new ConfigureWorkspaceAction((Workspace)listIterator.next()));
         }

      return actions;
      }

   public List getRemoveWorkspaceActions()
      {
      // build and return the list of actions
      final List actions = new ArrayList();
      for (final ListIterator listIterator = workspacesModel.listIterator(); listIterator.hasNext();)
         {
         actions.add(new RemoveWorkspaceAction((Workspace)listIterator.next(), true));
         }

      return actions;
      }

   public int indexOf(final Workspace workspace)
      {
      if (workspace != null)
         {
         return workspacesModel.indexOf(workspace);
         }
      return -1;
      }

   public void remove(final int[] indicesToRemove)
      {
      final int[] selectedIndices = jList.getSelectedIndices();

      // remove all selected indices (in reverse order)
      for (int i = indicesToRemove.length - 1; i >= 0; i--)
         {
         workspacesModel.remove(indicesToRemove[i]);
         }

      // now update the selection
      if (workspacesModel.isEmpty())
         {
         jList.getSelectionModel().clearSelection();
         }
      else
         {
         if ((selectedIndices != null) && (selectedIndices.length > 0))
            {
            // the selected indices might not equal the indices to remove if removal is not performed
            // via the tool window.
            if (selectedIndices.equals(indicesToRemove))
               {
               int firstSelectedIndex = selectedIndices[0];
               if (firstSelectedIndex == workspacesModel.size())
                  {
                  firstSelectedIndex--;
                  }
               jList.setSelectedIndex(firstSelectedIndex);
               }
            else
               {
               // make a copy of the array of indices to remove in case the client doesn't want the order changed
               final int[] indicesToRemoveCopy = new int[indicesToRemove.length];
               System.arraycopy(indicesToRemove, 0, indicesToRemoveCopy, 0, indicesToRemove.length);

               // figure out which of the selected indices are not being removed and decrement their
               // indices appropriately
               Arrays.sort(indicesToRemoveCopy);
               int sizeOfNewSelectedIndicesArray = 0;
               for (int i = 0; i < selectedIndices.length; i++)
                  {
                  final int selectedIndex = selectedIndices[i];
                  final int pos = Arrays.binarySearch(indicesToRemoveCopy, selectedIndex);
                  if (pos >= 0)
                     {
                     selectedIndices[i] = -1;   // flag this index as having been removed from the list
                     }
                  else
                     {
                     // decrement the index appropriately (figure out how many indices to remove are less than this one)
                     final int numberOfSmallerIndices = -(pos + 1);
                     selectedIndices[i] -= numberOfSmallerIndices;
                     sizeOfNewSelectedIndicesArray++;
                     }
                  }

               // weed out all the removed selected indices, and then set the new selection
               if (sizeOfNewSelectedIndicesArray > 0)
                  {
                  final int[] newSelectedIndeces = new int[sizeOfNewSelectedIndicesArray];
                  int currentPos = 0;
                  for (int i = 0; i < selectedIndices.length; i++)
                     {
                     if (selectedIndices[i] != -1)
                        {
                        newSelectedIndeces[currentPos++] = selectedIndices[i];
                        }
                     }
                  jList.setSelectedIndices(newSelectedIndeces);
                  }
               }
            }
         }
      }
   }
