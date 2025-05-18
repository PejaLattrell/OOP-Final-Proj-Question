package com.resumebuilder;

import com.resumebuilder.controller.ResumeController;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new ResumeController());
    }
}