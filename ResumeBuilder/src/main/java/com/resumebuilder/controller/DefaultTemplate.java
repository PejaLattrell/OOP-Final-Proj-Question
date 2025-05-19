package com.resumebuilder.controller;

import com.resumebuilder.model.ResumeData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DefaultTemplate implements ResumeTemplate {
    @Override
    public void generate(PDPageContentStream contentStream, ResumeData data, PDDocument document, BufferedImage image) throws Exception {
        float leftMargin = 50;
        float rightMargin = 50;
        float pageWidth = PDRectangle.A4.getWidth();
        float maxTextWidth = pageWidth - leftMargin - rightMargin;
        float yPosition = PDRectangle.A4.getHeight() - 50;

        // Full Name (Top Left)
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
        String fullName = data.getPersonalInformation().getFields().get(0).trim().isEmpty() ? "Your Name" : data.getPersonalInformation().getFields().get(0);
        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, yPosition);
        contentStream.showText(sanitizeText(fullName.toUpperCase()));
        contentStream.endText();
        yPosition -= 25;

        // Contact Information (Below Name, Left)
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        List<String> contactFields = data.getContactInformation().getFields();
        String[] contactLabels = {"Contact Number: ", "Email: ", "Address: "};
        for (int i = 0; i < contactLabels.length; i++) {
            String fieldText = contactFields.get(i).trim().isEmpty() ? "" : contactFields.get(i);
            contentStream.beginText();
            contentStream.newLineAtOffset(leftMargin, yPosition);
            contentStream.showText(sanitizeText(contactLabels[i] + fieldText));
            contentStream.endText();
            yPosition -= 15;
        }

        // Image (Top Right)
        float imageBottomY = yPosition;
        if (image != null) {
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);
            float scale = 0.2f;
            float imageWidth = image.getWidth() * scale;
            float imageHeight = image.getHeight() * scale;
            float imageX = pageWidth - rightMargin - imageWidth;
            float imageY = PDRectangle.A4.getHeight() - 50 - imageHeight;
            contentStream.drawImage(pdImage, imageX, imageY, imageWidth, imageHeight);
            imageBottomY = Math.min(imageY, yPosition);
        }

        // Horizontal Line
        yPosition = imageBottomY - 20;
        contentStream.setLineWidth(1);
        contentStream.moveTo(leftMargin, yPosition);
        contentStream.lineTo(pageWidth - rightMargin, yPosition);
        contentStream.stroke();
        yPosition -= 20;

        // Objective
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, yPosition);
        contentStream.showText("OBJECTIVE");
        contentStream.endText();
        yPosition -= 20;

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        String objective = data.getObjective().getText().trim().isEmpty() ? "" : data.getObjective().getText();
        yPosition = drawWrappedText(contentStream, objective, leftMargin, yPosition, maxTextWidth);
        yPosition -= 20;

        // Personal Information
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, yPosition);
        contentStream.showText("PERSONAL INFORMATION");
        contentStream.endText();
        yPosition -= 20;

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        List<String> personalFields = data.getPersonalInformation().getFields();
        String[] labels = {"Age: ", "Sex: ", "Date of Birth: ", "Place of Birth: ", "Citizenship: ", "Height: ", "Weight: ", "Religion: ", "Languages: "};
        for (int i = 1; i < personalFields.size(); i++) {
            String fieldText = personalFields.get(i).trim().isEmpty() ? "" : personalFields.get(i);
            contentStream.beginText();
            contentStream.newLineAtOffset(leftMargin, yPosition);
            contentStream.showText(sanitizeText(labels[i - 1] + fieldText));
            contentStream.endText();
            yPosition -= 15;
        }
        yPosition -= 20;

        // Work Experience
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, yPosition);
        contentStream.showText("WORK EXPERIENCE");
        contentStream.endText();
        yPosition -= 20;

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        String experience = data.getWorkExperience().getText().trim().isEmpty() ? "" : data.getWorkExperience().getText();
        yPosition = drawWrappedText(contentStream, experience, leftMargin, yPosition, maxTextWidth);
        yPosition -= 20;

        // Skills
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, yPosition);
        contentStream.showText("SKILLS");
        contentStream.endText();
        yPosition -= 20;

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        String skills = data.getSkills().getText().trim().isEmpty() ? "" : data.getSkills().getText();
        yPosition = drawBulletedText(contentStream, skills, leftMargin, yPosition, maxTextWidth);
        yPosition -= 20;

        // Education
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, yPosition);
        contentStream.showText("EDUCATION");
        contentStream.endText();
        yPosition -= 20;

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, yPosition);
        contentStream.showText("College: " + (data.getEducation().getCollegeName().trim().isEmpty() ? "" : sanitizeText(data.getEducation().getCollegeName())));
        contentStream.endText();
        yPosition -= 15;

        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, yPosition);
        contentStream.showText("Senior High School: " + (data.getEducation().getShsName().trim().isEmpty() ? "" : sanitizeText(data.getEducation().getShsName())));
        contentStream.endText();
        yPosition -= 15;

        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, yPosition);
        contentStream.showText("Junior High School: " + (data.getEducation().getJhsName().trim().isEmpty() ? "" : sanitizeText(data.getEducation().getJhsName())));
        contentStream.endText();
    }

    @Override
    public boolean supportsImage() {
        return true;
    }

    @Override
    public String getName() {
        return "Default";
    }

    private float drawWrappedText(PDPageContentStream contentStream, String text, float x, float y, float maxWidth) throws Exception {
        String[] lines = text.split("\n");
        float fontSize = 10;
        float leading = 1.5f * fontSize;

        for (String line : lines) {
            List<String> wrappedLines = wrapText(line, maxWidth, new PDType1Font(Standard14Fonts.FontName.HELVETICA), fontSize);
            for (String wrappedLine : wrappedLines) {
                contentStream.beginText();
                contentStream.newLineAtOffset(x, y);
                contentStream.showText(wrappedLine);
                contentStream.endText();
                y -= leading;
            }
        }
        return y;
    }

    private float drawBulletedText(PDPageContentStream contentStream, String text, float x, float y, float maxWidth) throws Exception {
        String[] lines = text.split("\n");
        float fontSize = 10;
        float leading = 1.5f * fontSize;
        float bulletIndent = 10;

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            List<String> wrappedLines = wrapText(line, maxWidth - bulletIndent, new PDType1Font(Standard14Fonts.FontName.HELVETICA), fontSize);
            for (int i = 0; i < wrappedLines.size(); i++) {
                String wrappedLine = wrappedLines.get(i);
                contentStream.beginText();
                contentStream.newLineAtOffset(x, y);
                if (i == 0) {
                    contentStream.showText("\u2022 ");
                }
                contentStream.newLineAtOffset(bulletIndent, 0);
                contentStream.showText(wrappedLine);
                contentStream.endText();
                y -= leading;
            }
        }
        return y;
    }

    private List<String> wrapText(String text, float maxWidth, PDType1Font font, float fontSize) throws Exception {
        List<String> lines = new ArrayList<>();
        String sanitizedText = sanitizeText(text);
        String[] words = sanitizedText.split(" ");
        StringBuilder line = new StringBuilder();
        float lineWidth = 0;

        for (String word : words) {
            try {
                float wordWidth = font.getStringWidth(word + " ") / 1000 * fontSize;
                if (lineWidth + wordWidth <= maxWidth) {
                    line.append(word).append(" ");
                    lineWidth += wordWidth;
                } else {
                    lines.add(line.toString().trim());
                    line = new StringBuilder(word + " ");
                    lineWidth = wordWidth;
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Error encoding word: " + word + " - " + e.getMessage());
                line.append("? ");
                lineWidth += font.getStringWidth("? ") / 1000 * fontSize;
            }
        }

        if (line.length() > 0) {
            lines.add(line.toString().trim());
        }

        return lines;
    }

    private String sanitizeText(String text) {
        if (text == null) return "";
        return text.replaceAll("[^\\x20-\\x7E]", " ").trim();
    }
}