package com.chrisbartley.idea.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public class ReorderableListModel extends WrappedListModel
   {
   public ReorderableListModel(final List wrappedList)
      {
      super(wrappedList);
      }

   /**
    * <p>
    * Shifts the elements specified by the given indices up or down, and moves the others
    * accordingly. Does nothing if the <code>indicesToShift</code> is <code>null</code> or empty or
    * if shifting cannot be performed because <code>indicesToShift</code> contains the first (if
    * <code>isShiftTowardsEndOfList</code> is <code>false</code>) or last (if
    * <code>isShiftTowardsEndOfList</code> is <code>true</code>) index.
    * </p>
    *
    * @param indicesToShift the indices to shift
    * @param isShiftTowardsEndOfList <code>true</code> to shift towards the end of the list,
    *        <code>false</code> to shift towards the beginning
    * @throws ArrayIndexOutOfBoundsException if any of the given indices is out of bounds
    */
   public void shiftElements(final int[] indicesToShift, final boolean isShiftTowardsEndOfList)
      {
      if ((indicesToShift != null) && (indicesToShift.length > 0))
         {
         // make sure all indices are within bounds
         Arrays.sort(indicesToShift);
         final int firstIndexToShift = indicesToShift[0];
         final int lastIndexToShift = indicesToShift[indicesToShift.length - 1];
         assertIndexIsWithinBounds(firstIndexToShift);
         assertIndexIsWithinBounds(lastIndexToShift);

         // don't do any shifting if we're incrementing and the last index is included or we're
         // decrementing and the first index is included.
         if (((isShiftTowardsEndOfList) && (lastIndexToShift < size() - 1)) || ((!isShiftTowardsEndOfList) && (firstIndexToShift > 0)))
            {
            // first determine which elements to shift (also weeds out duplicates)
            final boolean[] elementsWillBeShifted = new boolean[size()];
            for (int i = 0; i < indicesToShift.length; i++)
               {
               elementsWillBeShifted[indicesToShift[i]] = true;
               }

            // compute the shift amount, starting index (inclusive), and ending index (exclusive)
            final int offsetToNextElement = isShiftTowardsEndOfList ? -1 : 1;
            final int startingIndex = isShiftTowardsEndOfList ? lastIndexToShift + 1 : firstIndexToShift - 1;
            final int endingIndex = isShiftTowardsEndOfList ? firstIndexToShift : lastIndexToShift;

            // run through the list, shifting elements and sending out notifications as appropriate
            int currentIndex = startingIndex;
            while (currentIndex != endingIndex)
               {
               final int indexOfNextElement = currentIndex + offsetToNextElement;
               final boolean shiftStateOfNextElement = elementsWillBeShifted[indexOfNextElement];
               if (shiftStateOfNextElement)
                  {
                  swapElements(currentIndex, indexOfNextElement);
                  }
               if ((shiftStateOfNextElement) || (elementsWillBeShifted[currentIndex]))
                  {
                  fireContentsChanged(this, currentIndex, currentIndex);
                  }
               currentIndex += offsetToNextElement;
               }
            fireContentsChanged(this, currentIndex, currentIndex);
            }
         }
      }

   /** Swaps the elements located at the given indices. Assumes the indices are valid. */
   protected void swapElements(final int index1, final int index2)
      {
      final Object element1 = getElementAt(index1);
      final Object element2 = getElementAt(index2);
      set(index2, element1);
      set(index1, element2);
      }
   }
