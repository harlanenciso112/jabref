/*  Copyright (C) 2003-2015 JabRef contributors.
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package net.sf.jabref.logic.util.io;

import java.util.LinkedList;

import net.sf.jabref.JabRefPreferences;

public class FileHistory {

    private final JabRefPreferences prefs;
    private final LinkedList<String> history = new LinkedList<>();


    public FileHistory(JabRefPreferences prefs) {
        this.prefs = prefs;
        String[] old = prefs.getStringArray(JabRefPreferences.RECENT_FILES);
        if ((old != null) && (old.length > 0)) {
            for (int i = 0; i < old.length; i++) {
                history.addFirst(old[i]);
            }
        }
    }

    public int size() {
        return history.size();
    }

    /**
     * Adds the filename to the top of the list. If it already is in the list, it is merely moved to the top.
     *
     * @param filename a <code>String</code> value
     */

    public void newFile(String filename) {
        int i = 0;
        while (i < history.size()) {
            if (history.get(i).equals(filename)) {
                history.remove(i);
                i--;
            }
            i++;
        }
        history.addFirst(filename);
        while (history.size() > prefs.getInt(JabRefPreferences.HISTORY_SIZE)) {
            history.removeLast();
        }
    }

    public String getFileName(int i) {
        return history.get(i);
    }

    public void removeItem(String filename) {
        int i = 0;
        while (i < history.size()) {
            if (history.get(i).equals(filename)) {
                history.remove(i);
                return;
            }
            i++;
        }
    }

    public void storeHistory() {
        if (!history.isEmpty()) {
            String[] names = new String[history.size()];
            for (int i = 0; i < names.length; i++) {
                names[i] = history.get(i);
            }
            prefs.putStringArray(JabRefPreferences.RECENT_FILES, names);
        }
    }

}
