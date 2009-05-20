package com.chrisbartley.idea.util;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public class IncludableItem
   {
   private final Object item;
   private boolean isIncluded = true;

   public IncludableItem(final Object item)
      {
      this.item = item;
      }

   public Object getItem()
      {
      return item;
      }

   public boolean isIncluded()
      {
      return isIncluded;
      }

   public void setIncluded(final boolean included)
      {
      isIncluded = included;
      }
   }