package de.jpwinkler.daf.doorsbridge.user32;

import com.sun.jna.win32.StdCallLibrary;

public interface WndEnumProc extends StdCallLibrary.StdCallCallback {
	boolean callback(int hWnd, int lParam);
}