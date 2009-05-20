package com.chrisbartley.idea.workspaces.actions;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import com.chrisbartley.idea.actions.MoveDownAction;
import com.chrisbartley.idea.actions.MoveUpAction;
import com.chrisbartley.idea.util.IncludableItem;
import com.chrisbartley.idea.util.ReorderableListModel;
import com.chrisbartley.idea.util.WrappedListModel;
import com.chrisbartley.idea.workspaces.Workspace;
import com.chrisbartley.idea.workspaces.WorkspaceActionPlaces;
import com.chrisbartley.swing.event.ButtonTogglingDocumentListener;
import com.chrisbartley.swing.event.NonEmptyDocumentValidator;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.DialogWrapper;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
final class ConfigureWorkspaceDialog extends DialogWrapper
   {
   private static final NonEmptyDocumentValidator NON_EMPTY_DOCUMENT_VALIDATOR = new NonEmptyDocumentValidator();

   private final JTextField workspaceNameTextField = new JTextField(20);
   private final WrappedListModel listModel;

   public ConfigureWorkspaceDialog(final String windowTitle, final String okButtonLabel, final Workspace oldWorkspace)
      {
      super(false);
      setTitle(windowTitle);
      setOKButtonText(okButtonLabel);
      this.workspaceNameTextField.setText(oldWorkspace.getName());
      this.workspaceNameTextField.setCaretPosition(0);
      this.workspaceNameTextField.moveCaretPosition((oldWorkspace.getName() == null) ? 0 : oldWorkspace.getName().length());
      this.listModel = new ReorderableListModel(createListModel(oldWorkspace.getFileUrls()));
      init();
      }

   private ArrayList createListModel(final List fileUrls)
      {
      final ArrayList includableItems = new ArrayList();
      for (final ListIterator listIterator = fileUrls.listIterator(); listIterator.hasNext();)
         {
         final String url = (String)listIterator.next();
         includableItems.add(new IncludableItem(url));
         }
      return includableItems;
      }

   protected JComponent createCenterPanel()
      {
      // create the list and its scroll pane
      final JList fileList = new JList(listModel);
      fileList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      fileList.setCellRenderer(new ConfigureWorkspaceFileListCellRenderer());
      final JScrollPane scrollPane = new JScrollPane(fileList);

      // set up the text field
      workspaceNameTextField.addActionListener(new ActionListener()
         {
         public void actionPerformed(final ActionEvent e)
            {
            doOKAction();
            }
         });
      workspaceNameTextField.getDocument().addDocumentListener(new ButtonTogglingDocumentListener(getOKAction(), NON_EMPTY_DOCUMENT_VALIDATOR));
      workspaceNameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);

      final JPanel topPane = new JPanel();
      topPane.setLayout(new BoxLayout(topPane, BoxLayout.Y_AXIS));
      topPane.add(new JLabel("Workspace Name:"));
      topPane.add(Box.createRigidArea(new Dimension(0, 5)));
      topPane.add(workspaceNameTextField);

      final DefaultActionGroup toolbarGroup = new DefaultActionGroup();
      toolbarGroup.add(new IncludeAction(fileList));
      toolbarGroup.add(new ExcludeAction(fileList));
      toolbarGroup.add(new MoveUpAction(fileList));
      toolbarGroup.add(new MoveDownAction(fileList));
      final ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(WorkspaceActionPlaces.CONFIGURE_WORKSPACE_DIALOG, toolbarGroup, true);
      toolbar.getComponent().setAlignmentX(Component.LEFT_ALIGNMENT);
      topPane.add(toolbar.getComponent());

      final JPanel panel = new JPanel(new BorderLayout());
      panel.add(topPane, BorderLayout.NORTH);
      panel.add(scrollPane, BorderLayout.CENTER);

      return panel;
      }

   protected Action[] createActions()
      {
      getOKAction().putValue(DialogWrapper.DEFAULT_ACTION, Boolean.TRUE);
      getCancelAction().putValue(DialogWrapper.DEFAULT_ACTION, null);
      return new Action[]{getOKAction(), getCancelAction()};
      }

   /**
    * @return component which should be focused when the the dialog appears on the screen.
    */
   public JComponent getPreferredFocusedComponent()
      {
      return workspaceNameTextField;
      }

   public String getNewWorkspaceName()
      {
      return workspaceNameTextField.getText();
      }

   public List getNewWorkspaceUrls()
      {
      if (!listModel.isEmpty())
         {
         final List selectedUrls = new ArrayList();
         for (final ListIterator listIterator = listModel.listIterator(); listIterator.hasNext();)
            {
            final IncludableItem includableItem = (IncludableItem)listIterator.next();
            if (includableItem.isIncluded())
               {
               selectedUrls.add(includableItem.getItem());
               }
            }
         return selectedUrls;
         }
      return null;
      }
   }