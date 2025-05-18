package com.resumebuilder.controller;

import com.resumebuilder.model.ResumeData;
import com.resumebuilder.view.ResumeFrame;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ResumeController {
    private ResumeFrame view;
    private ResumeData model;
    private PdfGenerator pdfGenerator;

    public ResumeController() {
        model = new ResumeData();
        pdfGenerator = new PdfGenerator();
        view = new ResumeFrame(this);
        model = view.getData();
    }

    public void handlePreview(String template) {
        try {
            model = view.getData();
            java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("resume_preview_", ".pdf");
            File tempPDF = tempFile.toFile();
            tempPDF.deleteOnExit();

            pdfGenerator.generatePDFToFile(view, model, template, tempPDF, null);

            try (org.apache.pdfbox.pdmodel.PDDocument document = org.apache.pdfbox.Loader.loadPDF(tempPDF)) {
                org.apache.pdfbox.rendering.PDFRenderer pdfRenderer = new org.apache.pdfbox.rendering.PDFRenderer(document);
                java.awt.image.BufferedImage image = pdfRenderer.renderImageWithDPI(0, 300);

                int maxWidth = 600;
                int maxHeight = 800;
                double scale = Math.min((double) maxWidth / image.getWidth(), (double) maxHeight / image.getHeight());
                int scaledWidth = (int) (image.getWidth() * scale);
                int scaledHeight = (int) (image.getHeight() * scale);
                java.awt.Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, java.awt.Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImage);

                JDialog previewDialog = new JDialog(view, "Resume Preview", true);
                previewDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                previewDialog.setLayout(new java.awt.BorderLayout());

                JLabel pdfLabel = new JLabel(icon);
                JScrollPane scrollPane = new JScrollPane(pdfLabel);
                previewDialog.add(scrollPane, java.awt.BorderLayout.CENTER);

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(e -> previewDialog.dispose());
                previewDialog.add(closeButton, java.awt.BorderLayout.SOUTH);

                previewDialog.setSize(scaledWidth + 20, scaledHeight + 80);
                previewDialog.setLocationRelativeTo(view);
                previewDialog.setVisible(true);

                document.close();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error generating preview: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void handleGenerate(String template) {
        model = view.getData();
        pdfGenerator.generatePDF(view, model, template);
    }
}