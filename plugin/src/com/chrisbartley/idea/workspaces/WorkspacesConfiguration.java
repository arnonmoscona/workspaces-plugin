package com.chrisbartley.idea.workspaces;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.NamedJDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class WorkspacesConfiguration implements ApplicationComponent, NamedJDOMExternalizable
   {
   private static final String COMPONENT_NAME = "WorkspacesConfiguration";
   private static final String EXTERNALIZED_FILENAME = "workspaces";

   // main menu UI options
   public boolean isDisplayMainMenuUI = true;
   public boolean isPinAnUnpinnedOpenWorkspaceSelectedFromMenu = true;

   // tool window UI options
   public boolean isDisplayToolWindowUI = true;
   public boolean isDisplayButtonActionsInContextMenu = true;

   // Auto pinning/unpinning options
   public boolean isAutoPin = true;
   public boolean isAutoPinUponExplicitOpenOnly = true;
   public boolean isAutoPinUponCreateWorkspace = true;
   public boolean isAutoUnpin = true;
   public boolean isAutoUnpinUponExplicitCloseOnly = true;

   public WorkspacesConfiguration()
      {
      super();
      }

   public String getComponentName()
      {
      return COMPONENT_NAME;
      }

   public void initComponent()
      {
      // do nothing
      }

   public void disposeComponent()
      {
      // do nothing
      }

   public boolean isDisplayMainMenuUI()
      {
      return isDisplayMainMenuUI;
      }

   public void setDisplayMainMenuUI(final boolean displayMainMenuUI)
      {
      this.isDisplayMainMenuUI = displayMainMenuUI;
      }

   public boolean isPinAnUnpinnedOpenWorkspaceSelectedFromMenu()
      {
      return isPinAnUnpinnedOpenWorkspaceSelectedFromMenu;
      }

   public void setPinAnUnpinnedOpenWorkspaceSelectedFromMenu(final boolean pinAnUnpinnedOpenWorkspaceSelectedFromMenu)
      {
      isPinAnUnpinnedOpenWorkspaceSelectedFromMenu = pinAnUnpinnedOpenWorkspaceSelectedFromMenu;
      }

   public boolean isDisplayToolWindowUI()
      {
      return isDisplayToolWindowUI;
      }

   public void setDisplayToolWindowUI(final boolean displayToolWindowUI)
      {
      this.isDisplayToolWindowUI = displayToolWindowUI;
      }

   public boolean isDisplayButtonActionsInContextMenu()
      {
      return isDisplayButtonActionsInContextMenu;
      }

   public void setDisplayButtonActionsInContextMenu(final boolean displayButtonActionsInContextMenu)
      {
      isDisplayButtonActionsInContextMenu = displayButtonActionsInContextMenu;
      }

   public boolean isAutoPin()
      {
      return isAutoPin;
      }

   public void setAutoPin(final boolean autoPin)
      {
      this.isAutoPin = autoPin;
      }

   public boolean isAutoPinUponExplicitOpenOnly()
      {
      return isAutoPinUponExplicitOpenOnly;
      }

   public void setAutoPinUponExplicitOpenOnly(final boolean autoPinUponExplicitOpenOnly)
      {
      this.isAutoPinUponExplicitOpenOnly = autoPinUponExplicitOpenOnly;
      }

   public boolean isAutoPinUponCreateWorkspace()
      {
      return isAutoPinUponCreateWorkspace;
      }

   public void setAutoPinUponCreateWorkspace(final boolean autoPinUponCreateWorkspace)
      {
      isAutoPinUponCreateWorkspace = autoPinUponCreateWorkspace;
      }

   public boolean isAutoUnpin()
      {
      return isAutoUnpin;
      }

   public void setAutoUnpin(final boolean autoUnpin)
      {
      isAutoUnpin = autoUnpin;
      }

   public boolean isAutoUnpinUponExplicitCloseOnly()
      {
      return isAutoUnpinUponExplicitCloseOnly;
      }

   public void setAutoUnpinUponExplicitCloseOnly(final boolean autoUnpinUponExplicitCloseOnly)
      {
      isAutoUnpinUponExplicitCloseOnly = autoUnpinUponExplicitCloseOnly;
      }

   public void readExternal(final Element parentElement) throws InvalidDataException
      {
      DefaultJDOMExternalizer.readExternal(this, parentElement);
      }

   public void writeExternal(final Element parentElement) throws WriteExternalException
      {
      DefaultJDOMExternalizer.writeExternal(this, parentElement);
      }

   public String getExternalFileName()
      {
      return EXTERNALIZED_FILENAME;
      }

   public boolean equals(final Object bj)
      {
      if (this == bj)
         {
         return true;
         }
      if (!(bj instanceof WorkspacesConfiguration))
         {
         return false;
         }

      final WorkspacesConfiguration workspacesConfiguration = (WorkspacesConfiguration)bj;

      if (isAutoPin != workspacesConfiguration.isAutoPin())
         {
         return false;
         }
      if (isAutoPinUponCreateWorkspace != workspacesConfiguration.isAutoPinUponCreateWorkspace())
         {
         return false;
         }
      if (isAutoPinUponExplicitOpenOnly != workspacesConfiguration.isAutoPinUponExplicitOpenOnly())
         {
         return false;
         }
      if (isAutoUnpin != workspacesConfiguration.isAutoUnpin())
         {
         return false;
         }
      if (isAutoUnpinUponExplicitCloseOnly != workspacesConfiguration.isAutoUnpinUponExplicitCloseOnly())
         {
         return false;
         }
      if (isDisplayButtonActionsInContextMenu != workspacesConfiguration.isDisplayButtonActionsInContextMenu())
         {
         return false;
         }
      if (isDisplayMainMenuUI != workspacesConfiguration.isDisplayMainMenuUI())
         {
         return false;
         }
      if (isDisplayToolWindowUI != workspacesConfiguration.isDisplayToolWindowUI())
         {
         return false;
         }
      if (isPinAnUnpinnedOpenWorkspaceSelectedFromMenu != workspacesConfiguration.isPinAnUnpinnedOpenWorkspaceSelectedFromMenu())
         {
         return false;
         }

      return true;
      }

   public int hashCode()
      {
      int result;
      result = (isDisplayMainMenuUI ? 1 : 0);
      result = 29 * result + (isPinAnUnpinnedOpenWorkspaceSelectedFromMenu ? 1 : 0);
      result = 29 * result + (isDisplayToolWindowUI ? 1 : 0);
      result = 29 * result + (isDisplayButtonActionsInContextMenu ? 1 : 0);
      result = 29 * result + (isAutoPin ? 1 : 0);
      result = 29 * result + (isAutoPinUponExplicitOpenOnly ? 1 : 0);
      result = 29 * result + (isAutoPinUponCreateWorkspace ? 1 : 0);
      result = 29 * result + (isAutoUnpin ? 1 : 0);
      result = 29 * result + (isAutoUnpinUponExplicitCloseOnly ? 1 : 0);
      return result;
      }
   }