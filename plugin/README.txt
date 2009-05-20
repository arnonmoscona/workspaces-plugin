====================================================================================================

                                Workspaces Plugin for IntelliJ IDEA

====================================================================================================

ABOUT
-----

This plugin enables you to create and manage named groups of files, or "workspaces", within a
project. You can bind a name to a set of one or more files and then open/close that set of files as
a group. You can create as many workspaces as you want and arrange them in the Workspaces tool
window in any order you like.

This plugin was tested with IntelliJ IDEA Build 963 on Windows 2000.

====================================================================================================

HOW TO USE WORKSPACES
---------------------

All interaction with and management of workspaces is performed via the "Workspaces" tool window
and/or the "Workspaces" menu in the main menu.

In the Workspaces tool window, you should see icons for opening, closing, creating, removing,
rearranging, and configuring workspaces.  Open workspaces are marked with a black check.  Partially
open workspaces are marked with a grey check.  The number of files open and the number of files
bound to each workspace is shown on the right (e.g. [ 2 / 3 ] means 2 of the 3 files belonging to
that workspace are currently open).  Workspaces which contain the currently selected file are marked
with a green circle.

In the Workspaces menu, you should see all workspaces you have created and the menu items "Close All
Workspaces", "Close All Non-Workspace Files", and "Create New From Open Files...".  Open and
partially-open workspaces will be marked with a black or grey check as appropriate.

====================================================================================================

INSTALLATION
------------

Just drop the jar file in your plugins directory and start IDEA.

====================================================================================================

ACKNOWLEDGEMENT
---------------

Thanks to Jon Steelman for the original idea.  I asked a question on the
jetbrains.intellij.eap.features newsgroup about whether there'd be any interest in being able to
lock editor tabs to prevent them from being closed (because i often found myself with a bunch of
unrelated files open and just wanted to get back to the task at hand).  Jon mentioned his workspace
idea as an alternative, and I was sold instantly.  After some thinking, i realized that it could
be implemented with a plugin (mostly...i don't know of any way to restrict searches to arbitrary
files) and here you have it...  :-)

====================================================================================================

Questions, bug reports, and feature requests are welcome...

Chris Bartley
chris@chrisbartley.com
http://www.chrisbartley.com/

====================================================================================================

KNOWN ISSUES
------------

* In "Create a New Workspace" and "Workspace Properties" dialogs, Create/OK button should be
  disabled if no files are checked (can't have an empty workspace).  What's the proper way to do
  this with Swing?
* Current scheme of refreshing lists (RefreshableListModel) and especially the workspace window
  (RefreshListModelActionGroup) seems like a big ugly hack.  What's the proper way to do this?  I'd
  like some sort of listener that fires upon opening/closing files, but don't know of one.
  FileEditorManagerListener isn't good enough since files can be closed without being selected.
* Current scheme of computing open file counts is pretty inefficient.
* Use of IncludableItem seems a bit hokey.  Is there a better Swing-ish way to do it?
* CreateWorkspaceDialog and ConfigureWorkspaceDialog should be refactored (they're pretty much
  identical)
* Needs javadocs

====================================================================================================

TO DO
-----

* Make Include/Exclude icons clearer.
* Don't allow workspaces with duplicate names.  Perhaps prompt to replace the existing one.
* Handle moved/deleted/renamed files which belong to one or more workspaces.
* Consider implementing an action which is similar to "Close All Workspaces Except" but would
  also close all non-workspaced files (basically a shortcut for doing "Close All Workspaces
  Except" followed by "Close All Non-Workspace Files").  I'm not sure what to call this, though.
  Possibly "Close All Except", but that might be confusing.  Suggestions are welcome!  :D
* Provide docs on pinning (especially the subtle problems that arise when pinning/unpinning upon
  implicit open/close is activated).
* Allow creation of a workspace from project/source/classpath pane's right-click menu
* Allow addition of file(s) to an existing workspace via right-click menu option in project, source,
  and editor context menus
* Provide "Add Open Files" option in Workspaces menu and in tool window's right-click menu
* See if editor tab context menu is customizable to add "Close all workspaced files" and "Close all
  non-workspaced files".
* Internationalize

====================================================================================================

VERSION HISTORY
---------------

v 0.12  (2003.11.07)

   - Workspaces menu is now accessible via ALT-K.
   - When creating a workspace containing only one file, the default workspace name now no longer
     includes the extension.
   - Updated plugin.xml file

v 0.11  (2003.10.29)

   - converted for 957 API [Vince Mallet]

v 0.10  (2003.09.24)

   - converted for 929 API [Sven Krause]

v 0.9   (2002.11.05)

   - Workspaces tool window enhancements:
      - Double-clicking a workspace now opens the workspace.
      - Right-clicking displays a popup menu.
      - Option in global prefs to show/hide the Open, Close, Toggle Pin, and Properties actions in
        the popup menu (since they're available via the tool window's buttons, I prefer not to see
        them in the popup menu, but my preference might not be all that popular).
      - When selecting items in the Workspaces tool window, the entire row is selected now (instead
        of just the workspace name).
      - Counts in the Workspaces tool window are now black (instead of blue).
   - Workspaces menu enhancements:
      - Addition of "Close All Workspaces Except" action (provides a submenu to choose the workspace)
      - Addition of "Toggle Pin" action (provides a submenu to choose the workspace to toggle)
      - Addition of "Properties" action (provides a submenu to choose the workspace to configure)
      - Addition of "Remove" action (provides a submenu to choose the workspace to remove)
      - Performing a "Remove" via the menu doesn't interfere with the selection of workspaces in the
        tool window.
   - Option in global prefs to automatically pin workspaces when created.
   - In "Create a New Workspace" and "Workspace Properties" dialogs, the "Workspace Name" text field
     has default focus.
   - Global preferences are now persisted in [CONFIG_HOME]/options/workspaces.xml.  This may cause
     you to lose your current settings.  Sorry.

v 0.8.3 (2002.10.29)

   - Updated to work with Open API changes introduced in IDEA build 664.

v 0.8.2 (2002.10.18)

   - Added global options for pinning/unpinning of workspaces upon implicit open/close.
   - Added global option for Workspaces menu UI to specify what happens when an unpinned, open (i.e.
     marked with a black check) workspace is selected.  You can choose to either have the workspace
     pinned or closed.
   - Menu option "Close All Workspaces" is now only enabled if at least one workspace exists.
   - Menu option "Close All Non-Workspace File" is now only enabled if one or more non-workspaced
     files are open.
   - Removed file closing prevention introduced in 0.8 (since pinning does the same thing and is
     more powerful).
   - Fixed a (recently introduced) bug in the workspace creation and configuration dialogs where
     excluded files were marked with a grey check instead of no check.

v 0.8.1 (2002.10.16)

   - Added ability to "pin" and "unpin" a workspace to prevent its open files from being closed when
     another workspace is (explicitly) closed.  (This is more or less a test to see whether this
     is more intuitive than the file closing prevention scheme introduced in 0.8)
   - No longer inlcuding generated Javadocs with the distribution since it nearly doubles the size.
     If you want the generated Javadocs, just run the Ant task.

v 0.8 (2002.10.15)

   - Global preferences panel (in IDE Settings)
   - Option to not close files belonging to other open workspaces (configurable in preferences panel)
   - Workspaces now accessible via tool window and/or menu (configurable in preferences panel)
   - "Close All Workspaces" action (currently only available in Workspaces menu)
   - "Close All Non-Workspace Files" action (currently only available in Workspaces menu)
   - When creating a workspace containing only one file, the workspace name defaults to the name of
     the file (instead of "Untitled").

v 0.7 (2002.10.11)

   - New UI (tool window instead of menu)
   - Renaming of workspaces
   - Removal of files from existing workspaces
   - Rearrangement of files in existing workspaces
   - Rearrangement buttons now work with multi-select
   - Open/close/remove multiple workspaces
   - Confirmation dialog for remove
   - Not backwards compatible with previous versions (sorry).

v 0.6.1 (2002.09.26)

   - Fixed to work with IDEA Build 650's Open API.  Will not work with previous versions.

v 0.6 (2002.09.25)

   - Renamed jar file (changed from "WorkspacesPlugin.jar" to "Workspaces.jar")
   - Files are opened in the order they are stored in the workspace.  The first file is selected
     upon reopening the workspace.
   - Refactored

v 0.5 (2002.09.25)

   - Preview release to get feedback on usability and desired features.  No source available yet.

====================================================================================================

LEGAL CRAP
----------

I hereby abandon any property rights to the Workspaces plugin, and release all of the source code,
compiled code, and documentation contained in this distribution into the Public Domain. The
Workspaces plugin comes with NO WARRANTY or guarantee of fitness for any purpose.  Use at your own
risk.

You may use it freely in both commercial and non-commercial applications, bundle it with your
software distribution, include it on a CD-ROM, list the source code in a book, mirror the
documentation at your own web site, or use it in any other way you see fit.

====================================================================================================
