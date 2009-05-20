package com.chrisbartley.idea.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public class WrappedListModel extends RefreshableListModel implements List
   {
   private final List wrappedList;

   public WrappedListModel(final List wrappedList)
      {
      this.wrappedList = wrappedList;
      }

   public int getSize()
      {
      return wrappedList.size();
      }

   public Object getElementAt(final int index)
      {
      return wrappedList.get(index);
      }

   public int size()
      {
      return wrappedList.size();
      }

   public boolean isEmpty()
      {
      return wrappedList.isEmpty();
      }

   public boolean contains(final Object o)
      {
      return wrappedList.contains(o);
      }

   public Iterator iterator()
      {
      return wrappedList.iterator();
      }

   public Object[] toArray()
      {
      return wrappedList.toArray();
      }

   public Object[] toArray(final Object[] a)
      {
      return wrappedList.toArray(a);
      }

   public boolean add(final Object o)
      {
      final int index = wrappedList.size();
      final boolean wasAdded = wrappedList.add(o);
      fireIntervalAdded(this, index, index);
      return wasAdded;
      }

   public boolean remove(final Object o)
      {
      final int index = wrappedList.indexOf(o);
      final boolean wasRemoved = wrappedList.remove(o);
      if (index >= 0)
         {
         fireIntervalRemoved(this, index, index);
         }
      return wasRemoved;
      }

   public boolean containsAll(final Collection c)
      {
      return wrappedList.containsAll(c);
      }

   public boolean addAll(final Collection c)
      {
      return addAll(size(), c);
      }

   public boolean addAll(final int index, final Collection c)
      {
      final boolean wasModified = wrappedList.addAll(index, c);
      if (wasModified)
         {
         fireIntervalAdded(this, index, index + c.size());
         }
      return wasModified;
      }

   /**
    * WARNING: does not fire any list events (because i'm too lazy to implement it)
    * @todo add list event firing
    */
   public boolean removeAll(final Collection c)
      {
      return wrappedList.removeAll(c);
      }

   /**
    * WARNING: does not fire any list events (because i'm too lazy to implement it)
    * @todo add list event firing
    */
   public boolean retainAll(final Collection c)
      {
      return wrappedList.retainAll(c);
      }

   public void clear()
      {
      final int index1 = wrappedList.size() - 1;
      wrappedList.clear();
      if (index1 >= 0)
         {
         fireIntervalRemoved(this, 0, index1);
         }
      }

   public boolean equals(final Object o)
      {
      return wrappedList.equals(o);
      }

   public int hashCode()
      {
      return wrappedList.hashCode();
      }

   public Object get(final int index)
      {
      return wrappedList.get(index);
      }

   public Object set(final int index, final Object element)
      {
      final Object oldElement = wrappedList.set(index, element);
      fireContentsChanged(this, index, index);
      return oldElement;
      }

   public void add(final int index, final Object element)
      {
      wrappedList.add(index, element);
      fireIntervalAdded(this, index, index);
      }

   public Object remove(final int index)
      {
      final Object oldElement = wrappedList.remove(index);
      fireIntervalRemoved(this, index, index);
      return oldElement;
      }

   public int indexOf(final Object o)
      {
      return wrappedList.indexOf(o);
      }

   public int lastIndexOf(final Object o)
      {
      return wrappedList.lastIndexOf(o);
      }

   public ListIterator listIterator()
      {
      return wrappedList.listIterator();
      }

   public ListIterator listIterator(final int index)
      {
      return wrappedList.listIterator(index);
      }

   public List subList(final int fromIndex, final int toIndex)
      {
      return wrappedList.subList(fromIndex, toIndex);
      }

   public boolean isIndexWithinBounds(final int index)
      {
      return ((index >= 0) && (index < this.size()));
      }

   protected void assertIndexIsWithinBounds(final int index) throws ArrayIndexOutOfBoundsException
      {
      if (!isIndexWithinBounds(index))
         {
         throw new ArrayIndexOutOfBoundsException("The index '" + index + "' is out of bounds.");
         }
      }
   }
