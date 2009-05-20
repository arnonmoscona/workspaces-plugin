package com.chrisbartley.idea.workspaces;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import com.chrisbartley.idea.util.VirtualFileUtils;
import com.chrisbartley.idea.util.JDOMExternalizableList;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jdom.Element;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class Workspace implements JDOMExternalizable
   {
   public String name;
   public JDOMExternalizableList fileUrls = new JDOMExternalizableList();
   public boolean isPinned;

   public Workspace()
      {
      super();
      }

   public Workspace(final String name, final List fileUrls, final boolean isPinned)
      {
      this.name = name;
      this.fileUrls.addAll(fileUrls);
      this.isPinned = isPinned;
      }

   public String getName()
      {
      return name;
      }

   public List getFileUrls()
      {
      return Collections.unmodifiableList(fileUrls);
      }

   public boolean isPinned()
      {
      return isPinned;
      }

   public void setPinned(final boolean pinned)
      {
      isPinned = pinned;
      }

   public void update(final String newName, final List newUrls)
      {
      if ((newName != null) && (newName.length() > 0))
         {
         name = newName;
         }
      if ((newUrls != null) && (newUrls.size() > 0))
         {
         fileUrls.clear();
         fileUrls.addAll(newUrls);
         }
      }

   public void readExternal(final Element parentElement) throws InvalidDataException
      {
      DefaultJDOMExternalizer.readExternal(this, parentElement);
      }

   public void writeExternal(final Element parentElement) throws WriteExternalException
      {
      DefaultJDOMExternalizer.writeExternal(this, parentElement);
      }

   /**
    * Opens the files in order, then selects the first file if <code>selectFirstFile</code> is
    * <code>true</code>.
    */
   public void open(final FileEditorManager fileEditorManager, final boolean selectFirstFile, final boolean willAutoPin)
      {
      if (willAutoPin)
         {
         this.isPinned = true;
         }
      if (fileUrls.size() > 0)
         {
         // open all the files
         final VirtualFileManager virtualFileManager = VirtualFileManager.getInstance();
         for (ListIterator listIterator = fileUrls.listIterator(); listIterator.hasNext();)
            {
            openFile((String)listIterator.next(), virtualFileManager, fileEditorManager);
            }

         // now optionally select the first file
         if (selectFirstFile)
            {
            openFile((String)fileUrls.get(0), virtualFileManager, fileEditorManager);
            }
         }
      }

   private void openFile(final String url, final VirtualFileManager virtualFileManager, final FileEditorManager fileEditorManager)
      {
      final VirtualFile virtualFile = virtualFileManager.findFileByUrl(url);
      fileEditorManager.openFile(virtualFile, false);
      }

   /** Closes all files in this workspace, except those in the given {@link Set} of URLs */
   public void close(final FileEditorManager fileEditorManager, final Set urlsNotToBeClosed, final boolean willAutoUnpin)
      {
      if (willAutoUnpin)
         {
         this.isPinned = false;
         }
      for (final ListIterator listIterator = fileUrls.listIterator(); listIterator.hasNext();)
         {
         final String url = (String)listIterator.next();
         if ((urlsNotToBeClosed == null) || !urlsNotToBeClosed.contains(url))
            {
            VirtualFileUtils.closeFileByUrl(fileEditorManager, url);
            }
         }
      }

   public boolean contains(final VirtualFile[] filesToCheck)
      {
      if ((filesToCheck != null) && (filesToCheck.length != 0))
         {
         final Set fileUrlsSet = new HashSet(fileUrls);
         for (int i = 0; i < filesToCheck.length; i++)
            {
            if (fileUrlsSet.contains(filesToCheck[i].getUrl()))
               {
               return true;
               }
            }
         }
      return false;
      }

   public WorkspaceState getState(final FileEditorManager fileEditorManager)
      {
      return new WorkspaceState(getOpenFileUrls(fileEditorManager), fileUrls.size(), isPinned);
      }

   private Set getOpenFileUrls(final FileEditorManager fileEditorManager)
      {
      final Set boundUrls = new HashSet();
      final VirtualFile[] openFiles = fileEditorManager.getOpenFiles();
      if ((openFiles != null) && (openFiles.length > 0))
         {
         boundUrls.addAll(VirtualFileUtils.getUrls(openFiles));
         boundUrls.retainAll(fileUrls);
         }
      return boundUrls;
      }

   public String toString()
      {
      return getName();
      }

   public boolean equals(final Object o)
      {
      if (this == o)
         {
         return true;
         }
      if (!(o instanceof Workspace))
         {
         return false;
         }

      final Workspace workspace = (Workspace)o;

      if (isPinned != workspace.isPinned)
         {
         return false;
         }
      if (fileUrls != null ? !fileUrls.equals(workspace.fileUrls) : workspace.fileUrls != null)
         {
         return false;
         }
      if (name != null ? !name.equals(workspace.name) : workspace.name != null)
         {
         return false;
         }

      return true;
      }

   public int hashCode()
      {
      int result;
      result = (name != null ? name.hashCode() : 0);
      result = 29 * result + (fileUrls != null ? fileUrls.hashCode() : 0);
      result = 29 * result + (isPinned ? 1 : 0);
      return result;
      }
   }
