package com.ugackminer.unimark.robot;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.List;

// Taken from stackoverflow, https://stackoverflow.com/questions/71550362/how-to-copy-text-html-and-text-plain-to-clipboard-using-java
// Will change up later

public class HtmlSelection implements Transferable {

        private static List<DataFlavor> htmlFlavors = new ArrayList<>(3);

        static {
            htmlFlavors.add(DataFlavor.stringFlavor);
            htmlFlavors.add(DataFlavor.allHtmlFlavor);
        }

        private String html;
        private String plainText;

        public HtmlSelection(String html, String plainText) {
            this.html = html;
            this.plainText = plainText;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return (DataFlavor[]) htmlFlavors.toArray(new DataFlavor[htmlFlavors.size()]);
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return htmlFlavors.contains(flavor);
        }

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {

            String toBeExported = plainText;
            if (flavor == DataFlavor.stringFlavor) {
                toBeExported = plainText;
            } else if (flavor == DataFlavor.allHtmlFlavor) {
                toBeExported = html;
            }

            if (String.class.equals(flavor.getRepresentationClass())) {
                return toBeExported;
            }
            throw new UnsupportedFlavorException(flavor);
        }
    }