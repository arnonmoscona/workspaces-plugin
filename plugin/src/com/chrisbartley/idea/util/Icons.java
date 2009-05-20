package com.chrisbartley.idea.util;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author <a href="http://www.intellij.org/twiki/bin/view/Main/ChrisBartley">Chris Bartley</a>
 */
public final class Icons{
    public static final Icon MOVE_DOWN = new ImageIcon(Icons.class.getResource("/actions/moveDown.png"));
    public static final Icon MOVE_UP = new ImageIcon(Icons.class.getResource("/actions/moveUp.png"));

    /**
     * Prevent instantiation
     */
    private Icons() {
        super();
    }
}