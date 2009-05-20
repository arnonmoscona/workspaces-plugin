package com.chrisbartley.idea.workspaces;

import java.util.Set;
import javax.swing.Icon;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class WorkspaceState
   {
   private final Set openUrls;
   private final int numOpenFiles;
   private final int numFiles;
   private final boolean isPinned;

   public WorkspaceState(final Set openUrls, final int numFiles, final boolean isPinned)
      {
      this.openUrls = openUrls;
      this.isPinned = isPinned;
      this.numOpenFiles = openUrls.size();
      this.numFiles = numFiles;
      }

   public Set getOpenUrls()
      {
      return openUrls;
      }

   public boolean isOpen()
      {
      return (numOpenFiles == numFiles);
      }

   public boolean isPartiallyOpen()
      {
      return (numOpenFiles > 0) && (numOpenFiles < numFiles);
      }

   public boolean isClosed()
      {
      return (numOpenFiles == 0);
      }

   public Icon getOpenedStatusIcon()
      {
      if (numOpenFiles > 0)
         {
         return (numOpenFiles == numFiles) ? Icons.OPENED : Icons.PARTIALLY_OPENED;
         }
      else
         {
         return Icons.UNOPENED;
         }
      }

   public int getNumOpenFiles()
      {
      return numOpenFiles;
      }

   public int getNumFiles()
      {
      return numFiles;
      }

   public boolean isPinned()
      {
      return isPinned;
      }

   public Icon getPinnedStatusIcon()
      {
      return (isPinned) ? Icons.PINNED : Icons.UNPINNED;
      }

   public Icon getComboStatusIcon()
      {
      if (isPinned)
         {
         if (numOpenFiles > 0)
            {
            return (numOpenFiles == numFiles) ? Icons.PINNED_AND_OPEN : Icons.PINNED_AND_PARTIALLY_OPEN;
            }
         else
            {
            return Icons.PINNED;
            }
         }
      else
         {
         return getOpenedStatusIcon();
         }
      }

   public String toString()
      {
      final StringBuffer str = new StringBuffer();

      str.append("isOpen()          = " + isOpen()).append("\n");
      str.append("isPartiallyOpen() = " + isPartiallyOpen()).append("\n");
      str.append("isClosed()        = " + isClosed()).append("\n");
      str.append("isPinned()        = " + isPinned()).append("\n");
      str.append("num files         = " + this.getNumFiles()).append("\n");
      str.append("num open files    = " + this.getNumOpenFiles());

      return str.toString();
      }
   }
