package com.chrisbartley.idea.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public abstract class BaseActionGroup extends DefaultActionGroup
   {
   public BaseActionGroup()
      {
      super();
      }

   public BaseActionGroup(final String shortName, final boolean popup)
      {
      super(shortName, popup);
      }

   /** Convenience method for retrieving the {@link com.intellij.openapi.project.Project} */
   protected final Project getProject(final AnActionEvent event)
      {
      return (Project)event.getDataContext().getData(DataConstants.PROJECT);
      }
   }