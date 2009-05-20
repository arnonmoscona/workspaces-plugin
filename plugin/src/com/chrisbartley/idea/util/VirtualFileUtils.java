package com.chrisbartley.idea.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class VirtualFileUtils {
    private VirtualFileUtils() {
        super();
    }


    public static VirtualFile getFirstFileFromUrlList(final VirtualFile[] virtualFiles) {
        List list = getUrls(Arrays.asList(virtualFiles));
        VirtualFile vFile = null;

        if(list != null && list.size() > 0){
            vFile = (VirtualFile) list.get(0);
        }

        return vFile;
    }

    public static List getUrls(final VirtualFile[] virtualFiles) {
        return getUrls(Arrays.asList(virtualFiles));
    }

    public static List getUrls(final List virtualFiles) {
        final List urls = new ArrayList();
        if (virtualFiles != null) {
            for (final ListIterator listIterator = virtualFiles.listIterator(); listIterator.hasNext();) {
                final VirtualFile virtualFile = (VirtualFile) listIterator.next();
                urls.add(virtualFile.getUrl());
            }
        }
        return urls;
    }

    public static void closeFileByUrl(final FileEditorManager fileEditorManager, final String url) {
        if ((fileEditorManager != null) && (url != null)) {
            final VirtualFile virtualFile = VirtualFileManager.getInstance().findFileByUrl(url);
            if ((virtualFile != null) && (fileEditorManager.isFileOpen(virtualFile))) {
                fileEditorManager.closeFile(virtualFile);
            }
        }
    }
}
