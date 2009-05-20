package com.chrisbartley.idea.actions;

import java.util.List;
import java.util.ListIterator;
import javax.swing.Icon;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class MutableActionGroup extends BaseActionGroup
   {
   private final MutableActionGroupStrategy strategy;

   public MutableActionGroup(final MutableActionGroupStrategy strategy)
      {
      this(strategy, null, false, null);
      }

   public MutableActionGroup(final MutableActionGroupStrategy strategy, final String shortName, final boolean popup)
      {
      this(strategy, shortName, popup, null);
      }

   public MutableActionGroup(final MutableActionGroupStrategy strategy, final String shortName, final boolean popup, final Icon icon)
      {
      super(shortName, popup);
      this.strategy = strategy;
      this.getTemplatePresentation().setIcon(icon);
      }

   public void update(final AnActionEvent event)
      {
      // remove all items from this group
      this.removeAll();

      if (getProject(event) != null)
         {
         // copy the actions from the project into this group
         final List actions = strategy.getActions(event);
         for (final ListIterator listIterator = actions.listIterator(); listIterator.hasNext();)
            {
            this.add((AnAction)listIterator.next());
            }
         event.getPresentation().setEnabled((actions != null) && (actions.size() > 0));
         }
      else
         {
         event.getPresentation().setEnabled(false);
         }
      strategy.preparePresentation(event);
      }
   }
