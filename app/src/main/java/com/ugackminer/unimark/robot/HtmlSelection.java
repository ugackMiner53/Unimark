package com.ugackminer.unimark.robot;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

// Inspired by MadProgrammer's answer at https://stackoverflow.com/questions/71550362/how-to-copy-text-html-and-text-plain-to-clipboard-using-java
// Licensed and modified under CC-BY-SA 4.0 available at https://creativecommons.org/licenses/by-sa/4.0/

public class HtmlSelection implements Transferable {

        private static DataFlavor[] flavors = new DataFlavor[] {DataFlavor.stringFlavor, DataFlavor.allHtmlFlavor};
        
        private String html;
        private String text;

        public HtmlSelection(String html, String text) {
            this.html = html;
            this.text = text;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor unknownFlavor) {
            for (DataFlavor flavor : flavors) {
                if (flavor.equals(unknownFlavor)) {
                    return true;
                }
            }
            return false;
        }

        public String getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            return (flavor.equals(DataFlavor.allHtmlFlavor) ? html : text);
        }
    }