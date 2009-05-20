package com.chrisbartley.idea.workspaces;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class Icons
   {
   public static final Icon WORKSPACES = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/workspace.png"));

   public static final Icon INCLUDED = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/check.png"));
   public static final Icon EXCLUDED = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/noCheck.png"));

   public static final Icon OPENED = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/check.png"));
   public static final Icon PARTIALLY_OPENED = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/greyCheck.png"));
   public static final Icon UNOPENED = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/noCheck.png"));

   public static final Icon ACTIVE_WORKSPACE = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/activeWorkspace.png"));
   public static final Icon INACTIVE_WORKSPACE = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/inactiveWorkspace.png"));

   public static final Icon ADD_WORKSPACE = new ImageIcon(Icons.class.getResource("/general/add.png"));
   public static final Icon REMOVE_WORKSPACE = new ImageIcon(Icons.class.getResource("/general/remove.png"));

   public static final Icon CONFIGURE_WORKSPACE = new ImageIcon(Icons.class.getResource("/actions/properties.png"));

   public static final Icon CLOSE_WORKSPACE = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/workspaceClosed.png"));
   public static final Icon OPEN_WORKSPACE = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/workspaceOpen.png"));

   public static final Icon INCLUDE_WORKSPACE_ITEM = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/include.png"));
   public static final Icon EXCLUDE_WORKSPACE_ITEM = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/exclude.png"));

   public static final Icon EXCLUDE_WORKSPACED = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/excludeWorkspaced.png"));

   public static final Icon WORKSPACES_CONFIGURABLE = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/workspacesConfigurable.png"));

   public static final Icon PINNED = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/pinned.png"));
   public static final Icon UNPINNED = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/unpinned.png"));

   public static final Icon PINNED_AND_OPEN = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/pinnedAndOpen.png"));
   public static final Icon PINNED_AND_PARTIALLY_OPEN = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/pinnedAndPartiallyOpen.png"));

    public static final Icon ADD_CURR_FILE = new ImageIcon(Icons.class.getResource("/com/chrisbartley/idea/workspaces/icons/addFile.png"));

   /** Prevent instantiation */
   private Icons()
      {
      super();
      }
   }