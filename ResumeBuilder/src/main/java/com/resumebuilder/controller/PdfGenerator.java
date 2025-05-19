package com.resumebuilder.controller;

import com.resumebuilder.model.ResumeData;
import com.resumebuilder.view.ResumeFrame;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PdfGenerator {

    public void generatePDF(ResumeFrame view, ResumeData data, String templateName) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Resume As");
        fileChooser.setSelectedFile(new File("resume.pdf"));

        int userSelection = fileChooser.showSaveDialog(view);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            BufferedImage image = null;
            ResumeTemplate template = createTemplate(templateName);
            if (template.supportsImage()) {
                image = promptForImage(view);
            }
            try {
                generatePDFToFile(view, data, template, fileToSave, image);
                JOptionPane.showMessageDialog(view, "Resume saved as: " + fileToSave.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error saving PDF: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public void generatePDFToFile(ResumeFrame view, ResumeData data, String templateName, File file, BufferedImage image) throws Exception {
        ResumeTemplate template = createTemplate(templateName);
        generatePDFToFile(view, data, template, file, image);
    }

    private void generatePDFToFile(ResumeFrame view, ResumeData data, ResumeTemplate template, File file, BufferedImage image) throws Exception {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                template.generate(contentStream, data, document, image);
            }

            document.save(file);
        }
    }

    private ResumeTemplate createTemplate(String templateName) {
        if ("Default".equalsIgnoreCase(templateName)) {
            return new DefaultTemplate();
        } else if ("Two-Column".equalsIgnoreCase(templateName)) {
            return new TwoColumnTemplate();
        } else {
            throw new IllegalArgumentException("Unknown template: " + templateName);
        }
    }

    private BufferedImage promptForImage(ResumeFrame view) {
        int option = JOptionPane.showConfirmDialog(view, "Would you like to add a profile picture?", "Add Picture", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Profile Image");
            int result = fileChooser.showOpenDialog(view);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    return ImageIO.read(fileChooser.getSelectedFile());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error loading image: " + ex.getMessage());
                    return null;
                }
            }
        }
        return null;
    }
}