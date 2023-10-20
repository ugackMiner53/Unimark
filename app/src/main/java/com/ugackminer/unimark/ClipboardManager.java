package com.ugackminer.unimark;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ClipboardManager
{
    Clipboard clipboard;

    public ClipboardManager(Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    public Transferable getClipboardTransferable() {
        return this.clipboard.getContents(this);
    }

    public String getClipboardText() {
        try {
            return (String)this.getClipboardTransferable().getTransferData(DataFlavor.stringFlavor);
        } catch (Exception err) {
            return null;
        }
    }

    public void setClipboardTransferable(Transferable newContents) {
        this.clipboard.setContents(newContents, null);
    }

    public void setClipboardText(String text) {
        StringSelection clipboardSelection = new StringSelection(text);
        this.clipboard.setContents(clipboardSelection, null);
    }

}