package com.chrisbartley.idea.actions;

import java.util.List;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public abstract class MutableActionGroupStrategy
   {
   public abstract List getActions(final AnActionEvent event);

   public void preparePresentation(final AnActionEvent event)
      {
      // do nothing
      }

   /** Convenience method for retrieving the {@link Project} */
   protected final Project getProject(final AnActionEvent event)
      {
      return (Project)event.getDataContext().getData(DataConstants.PROJECT);
      }
   }