package com.chrisbartley.swing.event;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractButton;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public class MasterAndSlaveComponentsItemListener implements ItemListener
   {
   private final AbstractButton masterComponent;
   private final Component[] slaveComponents;

   public MasterAndSlaveComponentsItemListener(final AbstractButton masterComponent, final Component[] slaveComponents)
      {
      this.masterComponent = masterComponent;
      this.slaveComponents = slaveComponents;
      }

   public void itemStateChanged(final ItemEvent e)
      {
      for (int i = 0; i < slaveComponents.length; i++)
         {
         slaveComponents[i].setEnabled(masterComponent.isSelected());
         }
      }
   }
