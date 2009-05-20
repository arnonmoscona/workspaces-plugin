package com.chrisbartley.idea.util;

import java.util.Arrays;
import javax.swing.AbstractListModel;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public abstract class RefreshableListModel extends AbstractListModel
   {
   public final void refreshAll()
      {
      fireContentsChanged(this, 0, getSize() - 1);
      }

   public final void refresh(final int[] indices)
      {
      if (indices != null)
         {
         for (int i = 0; i < indices.length; i++)
            {
            refresh(indices[i]);
            }
         }
      }

   public final void refresh(final int index)
      {
      if ((index >= 0) && (index < getSize()))
         {
         fireContentsChanged(this, index, index);
         }
      }

   public void refreshAllButThese(final int[] indicesToIgnore)
      {
      if (indicesToIgnore != null)
         {
         if (indicesToIgnore.length == 0)
            {
            refreshAll();
            }
         else
            {
            final int[] indicesNotToRefresh = new int[indicesToIgnore.length];
            System.arraycopy(indicesToIgnore, 0, indicesNotToRefresh, 0, indicesToIgnore.length);
            for (int i = 0; i < getSize(); i++)
               {
               if (Arrays.binarySearch(indicesNotToRefresh, i) < 0)
                  {
                  refresh(i);
                  }
               }
            }
         }
      }
   }
