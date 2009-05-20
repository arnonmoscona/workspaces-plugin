package com.chrisbartley.idea.actions;

import javax.swing.Icon;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.project.Project;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public abstract class BaseAction extends AnAction
   {
   public BaseAction()
      {
      super();
      }

   public BaseAction(final String text)
      {
      super(text);
      }

   public BaseAction(final String text, final String description, final Icon icon)
      {
      super(text, description, icon);
      }

   /** Convenience method for retrieving the {@link Project} */
   protected final Project getProject(final AnActionEvent event)
      {
      return (Project)event.getDataContext().getData(DataConstants.PROJECT);
      }
   }