package com.chrisbartley.idea.workspaces;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import com.chrisbartley.swing.event.MasterAndSlaveComponentsItemListener;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
final class WorkspacesConfigurationPanel extends JPanel
   {
   private final JCheckBox isDisplayMainMenuUI = new JCheckBox("Workspaces Menu");
   private final JLabel pinAnUnpinnedOpenWorkspaceSelectedFromMenuLabel = new JLabel("Selecting an unpinned, open (i.e. marked with a black check) workspace should...");
   private final JRadioButton isPinAnUnpinnedOpenWorkspaceSelectedFromMenuTrue = new JRadioButton("Pin the workspace");
   private final JRadioButton isPinAnUnpinnedOpenWorkspaceSelectedFromMenuFalse = new JRadioButton("Close the workspace");
   private final Component[] isDisplayMainMenuUIControlledComponents = new Component[]{pinAnUnpinnedOpenWorkspaceSelectedFromMenuLabel, isPinAnUnpinnedOpenWorkspaceSelectedFromMenuTrue, isPinAnUnpinnedOpenWorkspaceSelectedFromMenuFalse};

   private final JCheckBox isDisplayToolWindowUI = new JCheckBox("Workspaces Tool Window");
   private final JCheckBox isDisplayButtonActionsInContextMenu = new JCheckBox("Include the Open, Close, Toggle Pin, Remove, and Properties actions in the context menu (effective upon restart)");
   private final Component[] isDisplayToolWindowUIControlledComponents = new Component[]{isDisplayButtonActionsInContextMenu};

   private final JCheckBox isAutoPin = new JCheckBox("Automatically pin workspaces when...");
   private final JRadioButton isAutoPinUponExplicitOpenOnlyTrue = new JRadioButton("Explicitly opened");
   private final JRadioButton isAutoPinUponExplicitOpenOnlyFalse = new JRadioButton("Implicitly or explicitly opened");
   private final JCheckBox isAutoPinUponCreateWorkspace = new JCheckBox("Created");
   private final Component[] isAutoPinControlledComponents = new Component[]{isAutoPinUponExplicitOpenOnlyTrue, isAutoPinUponExplicitOpenOnlyFalse, isAutoPinUponCreateWorkspace};

   private final JCheckBox isAutoUnpin = new JCheckBox("Automatically unpin workspaces when...");
   private final JRadioButton isAutoUnpinUponExplicitCloseOnlyTrue = new JRadioButton("Explicitly closed");
   private final JRadioButton isAutoUnpinUponExplicitCloseOnlyFalse = new JRadioButton("Implicitly or explicitly closed");
   private final Component[] isAutoUnpinControlledComponents = new Component[]{isAutoUnpinUponExplicitCloseOnlyTrue, isAutoUnpinUponExplicitCloseOnlyFalse};

   public WorkspacesConfigurationPanel()
      {
      super(new BorderLayout());
      createPanel();
      }

   private void createPanel()
      {
      // set up the Main Menu UI checkbox
      isDisplayMainMenuUI.addItemListener(new MasterAndSlaveComponentsItemListener(isDisplayMainMenuUI, isDisplayMainMenuUIControlledComponents));
      final ButtonGroup isPinAnUnpinnedOpenWorkspaceSelectedFromMenuGroup = new ButtonGroup();
      isPinAnUnpinnedOpenWorkspaceSelectedFromMenuGroup.add(isPinAnUnpinnedOpenWorkspaceSelectedFromMenuTrue);
      isPinAnUnpinnedOpenWorkspaceSelectedFromMenuGroup.add(isPinAnUnpinnedOpenWorkspaceSelectedFromMenuFalse);

      // set up the Tool Window UI checkbox
      isDisplayToolWindowUI.addItemListener(new MasterAndSlaveComponentsItemListener(isDisplayToolWindowUI, isDisplayToolWindowUIControlledComponents));

      // set up the auto pinning checkbox and associated radio buttons
      isAutoPin.addItemListener(new MasterAndSlaveComponentsItemListener(isAutoPin, isAutoPinControlledComponents));
      final ButtonGroup autoPinningButtonGroup = new ButtonGroup();
      autoPinningButtonGroup.add(isAutoPinUponExplicitOpenOnlyTrue);
      autoPinningButtonGroup.add(isAutoPinUponExplicitOpenOnlyFalse);

      // set up the auto unpinning checkbox and associated radio buttons
      isAutoUnpin.addItemListener(new MasterAndSlaveComponentsItemListener(isAutoUnpin, isAutoUnpinControlledComponents));
      final ButtonGroup autoUnpinningButtonGroup = new ButtonGroup();
      autoUnpinningButtonGroup.add(isAutoUnpinUponExplicitCloseOnlyTrue);
      autoUnpinningButtonGroup.add(isAutoUnpinUponExplicitCloseOnlyFalse);

      // create the bordered panel that holds the UI options
      final JPanel uiOptionsSubPanel1 = createCheckBoxControlledComponentsPanel(isDisplayMainMenuUI, isDisplayMainMenuUIControlledComponents);
      final JPanel uiOptionsSubPanel2 = createCheckBoxControlledComponentsPanel(isDisplayToolWindowUI, isDisplayToolWindowUIControlledComponents);
      final JPanel uiOptionsPanel = createTitledBorderedPanel("UI Options", new JPanel[]{uiOptionsSubPanel1, uiOptionsSubPanel2});

      // create the bordered panel that holds the auto pinning/unpinning stuff
      final JPanel autoPinningUnpinningSubPanel1 = createCheckBoxControlledComponentsPanel(isAutoPin, isAutoPinControlledComponents);
      final JPanel autoPinningUnpinningSubPanel2 = createCheckBoxControlledComponentsPanel(isAutoUnpin, isAutoUnpinControlledComponents);
      final JPanel autoPinningUnpinningPanel = createTitledBorderedPanel("Automatic Pinning/Unpinning", new JPanel[]{autoPinningUnpinningSubPanel1, autoPinningUnpinningSubPanel2});

      // create the main panel and add the other panels to it
      final JPanel workspacesOptionsPanel = new JPanel();
      workspacesOptionsPanel.setLayout(new BoxLayout(workspacesOptionsPanel, BoxLayout.Y_AXIS));
      workspacesOptionsPanel.add(uiOptionsPanel);
      workspacesOptionsPanel.add(Box.createRigidArea(new Dimension(0, 3)));
      workspacesOptionsPanel.add(autoPinningUnpinningPanel);
      workspacesOptionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

      add(workspacesOptionsPanel, BorderLayout.NORTH);
      }

   private JPanel createTitledBorderedPanel(final String title, final JPanel[] subPanels)
      {
      final JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setBorder(BorderFactory.createTitledBorder(title));
      panel.setAlignmentX(Component.LEFT_ALIGNMENT);
      for (int i = 0; i < subPanels.length; i++)
         {
         panel.add(subPanels[i]);
         }

      return panel;
      }

   private JPanel createCheckBoxControlledComponentsPanel(final JCheckBox checkBox, final Component[] controlledComponents)
      {
      final JPanel optionsPanel = new JPanel();
      optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
      for (int i = 0; i < controlledComponents.length; i++)
         {
         optionsPanel.add(controlledComponents[i]);
         }
      optionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

      final JPanel indentedOptionsPanel = new JPanel();
      indentedOptionsPanel.setLayout(new BoxLayout(indentedOptionsPanel, BoxLayout.X_AXIS));
      indentedOptionsPanel.add(Box.createRigidArea(new Dimension(15, 0)));
      indentedOptionsPanel.add(optionsPanel);
      indentedOptionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

      final JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.add(checkBox);
      panel.add(indentedOptionsPanel);
      panel.setAlignmentX(Component.LEFT_ALIGNMENT);
      return panel;
      }

   public void copyConfigurationTo(final WorkspacesConfiguration configuration)
      {
      // main menu UI options
      configuration.setDisplayMainMenuUI(isDisplayMainMenuUI());
      configuration.setPinAnUnpinnedOpenWorkspaceSelectedFromMenu(isPinAnUnpinnedOpenWorkspaceSelectedFromMenu());

      // tool window UI options
      configuration.setDisplayToolWindowUI(isDisplayToolWindowUI());
      configuration.setDisplayButtonActionsInContextMenu(isDisplayButtonActionsInContextMenu());

      // Auto pinning/unpinning options
      configuration.setAutoPin(isAutoPin());
      configuration.setAutoPinUponExplicitOpenOnly(isAutoPinUponExplicitOpenOnly());
      configuration.setAutoPinUponCreateWorkspace(isAutoPinUponCreateWorkspace());
      configuration.setAutoUnpin(isAutoUnpin());
      configuration.setAutoUnpinUponExplicitCloseOnly(isAutoUnpinUponExplicitCloseOnly());
      }

   public void copyConfigurationFrom(final WorkspacesConfiguration configuration)
      {
      // main menu UI options
      setDisplayMainMenuUI(configuration.isDisplayMainMenuUI());
      setPinAnUnpinnedOpenWorkspaceSelectedFromMenu(configuration.isPinAnUnpinnedOpenWorkspaceSelectedFromMenu());

      // tool window UI options
      setDisplayToolWindowUI(configuration.isDisplayToolWindowUI());
      setDisplayButtonActionsInContextMenu(configuration.isDisplayButtonActionsInContextMenu());

      // Auto pinning/unpinning options
      setAutoPin(configuration.isAutoPin());
      setAutoPinUponExplicitOpenOnly(configuration.isAutoPinUponExplicitOpenOnly());
      setAutoPinUponCreateWorkspace(configuration.isAutoPinUponCreateWorkspace());
      setAutoUnpin(configuration.isAutoUnpin());
      setAutoUnpinUponExplicitCloseOnly(configuration.isAutoUnpinUponExplicitCloseOnly());
      }

   private boolean isDisplayMainMenuUI()
      {
      return isDisplayMainMenuUI.isSelected();
      }

   private void setDisplayMainMenuUI(final boolean isDisplayMainMenuUI)
      {
      this.isDisplayMainMenuUI.setSelected(isDisplayMainMenuUI);
      this.pinAnUnpinnedOpenWorkspaceSelectedFromMenuLabel.setEnabled(isDisplayMainMenuUI);
      this.isPinAnUnpinnedOpenWorkspaceSelectedFromMenuTrue.setEnabled(isDisplayMainMenuUI);
      this.isPinAnUnpinnedOpenWorkspaceSelectedFromMenuTrue.setEnabled(isDisplayMainMenuUI);
      }

   private boolean isPinAnUnpinnedOpenWorkspaceSelectedFromMenu()
      {
      return isPinAnUnpinnedOpenWorkspaceSelectedFromMenuTrue.isSelected();
      }

   private void setPinAnUnpinnedOpenWorkspaceSelectedFromMenu(final boolean isPinAnUnpinnedOpenWorkspaceSelectedFromMenu)
      {
      this.isPinAnUnpinnedOpenWorkspaceSelectedFromMenuTrue.setSelected(isPinAnUnpinnedOpenWorkspaceSelectedFromMenu);
      this.isPinAnUnpinnedOpenWorkspaceSelectedFromMenuFalse.setSelected(!isPinAnUnpinnedOpenWorkspaceSelectedFromMenu);
      }

   private boolean isDisplayToolWindowUI()
      {
      return isDisplayToolWindowUI.isSelected();
      }

   private void setDisplayToolWindowUI(final boolean isDisplayToolWindowUI)
      {
      this.isDisplayToolWindowUI.setSelected(isDisplayToolWindowUI);
      this.isDisplayButtonActionsInContextMenu.setEnabled(isDisplayToolWindowUI);
      }

   private boolean isDisplayButtonActionsInContextMenu()
      {
      return isDisplayButtonActionsInContextMenu.isSelected();
      }

   private void setDisplayButtonActionsInContextMenu(final boolean isDisplayButtonActionsInContextMenu)
      {
      this.isDisplayButtonActionsInContextMenu.setSelected(isDisplayButtonActionsInContextMenu);
      }

   private boolean isAutoPin()
      {
      return isAutoPin.isSelected();
      }

   private void setAutoPin(final boolean autoPin)
      {
      this.isAutoPin.setSelected(autoPin);
      this.isAutoPinUponExplicitOpenOnlyTrue.setEnabled(autoPin);
      this.isAutoPinUponExplicitOpenOnlyFalse.setEnabled(autoPin);
      this.isAutoPinUponCreateWorkspace.setEnabled(autoPin);
      }

   private boolean isAutoPinUponExplicitOpenOnly()
      {
      return isAutoPinUponExplicitOpenOnlyTrue.isSelected();
      }

   private void setAutoPinUponExplicitOpenOnly(final boolean autoPinUponExplicitOpenOnly)
      {
      this.isAutoPinUponExplicitOpenOnlyTrue.setSelected(autoPinUponExplicitOpenOnly);
      this.isAutoPinUponExplicitOpenOnlyFalse.setSelected(!autoPinUponExplicitOpenOnly);
      }

   private boolean isAutoPinUponCreateWorkspace()
      {
      return isAutoPinUponCreateWorkspace.isSelected();
      }

   private void setAutoPinUponCreateWorkspace(final boolean autoPinUponCreateWorkspace)
      {
      this.isAutoPinUponCreateWorkspace.setSelected(autoPinUponCreateWorkspace);
      }

   private boolean isAutoUnpin()
      {
      return isAutoUnpin.isSelected();
      }

   private void setAutoUnpin(final boolean autoUnpin)
      {
      this.isAutoUnpin.setSelected(autoUnpin);
      this.isAutoUnpinUponExplicitCloseOnlyTrue.setEnabled(autoUnpin);
      this.isAutoUnpinUponExplicitCloseOnlyFalse.setEnabled(autoUnpin);
      }

   private boolean isAutoUnpinUponExplicitCloseOnly()
      {
      return isAutoUnpinUponExplicitCloseOnlyTrue.isSelected();
      }

   private void setAutoUnpinUponExplicitCloseOnly(final boolean autoUnpinUponExplicitCloseOnly)
      {
      this.isAutoUnpinUponExplicitCloseOnlyTrue.setSelected(autoUnpinUponExplicitCloseOnly);
      this.isAutoUnpinUponExplicitCloseOnlyFalse.setSelected(!autoUnpinUponExplicitCloseOnly);
      }
   }