package com.resumebuilder.controller;

import com.resumebuilder.model.ResumeData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TwoColumnTemplate implements ResumeTemplate {
    @Override
    public void generate(PDPageContentStream contentStream, ResumeData data, PDDocument document, BufferedImage image) throws Exception {
        float leftMargin = 50;
        float rightMargin = 50;
        float pageWidth = PDRectangle.A4.getWidth();
        float columnWidth = (pageWidth - leftMargin - rightMargin - 20) / 2;
        float leftColumnX = leftMargin;
        float rightColumnX = leftMargin + columnWidth + 20;
        float yPositionLeft = PDRectangle.A4.getHeight() - 50;
        float yPositionRight = yPositionLeft;

        // Full Name (Centered at the Top)
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
        String fullName = data.getPersonalInformation().getFields().get(0).trim().isEmpty() ? "Your Name" : data.getPersonalInformation().getFields().get(0);
        float nameWidth = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD).getStringWidth(sanitizeText(fullName.toUpperCase())) / 1000 * 16;
        contentStream.beginText();
        contentStream.newLineAtOffset((pageWidth - nameWidth) / 2, yPositionLeft);
        contentStream.showText(sanitizeText(fullName.toUpperCase()));
        contentStream.endText();
        yPositionLeft -= 25;
        yPositionRight = yPositionLeft;

        // Horizontal Line Below Name
        contentStream.setLineWidth(1);
        contentStream.moveTo(leftMargin, yPositionLeft);
        contentStream.lineTo(pageWidth - rightMargin, yPositionLeft);
        contentStream.stroke();
        yPositionLeft -= 20;
        yPositionRight = yPositionLeft;

        // Left Column: Personal Information
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(leftColumnX, yPositionLeft);
        contentStream.showText("PERSONAL INFORMATION");
        contentStream.endText();
        yPositionLeft -= 20;

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        List<String> personalFields = data.getPersonalInformation().getFields();
        String[] labels = {
            "AGE:", "SEX:", "DATE OF BIRTH:", "PLACE OF BIRTH:",
            "CITIZENSHIP:", "HEIGHT:", "WEIGHT:", "RELIGION:",
            "LANGUAGES:"
        };
        for (int i = 1; i < personalFields.size(); i++) {
            String fieldText = personalFields.get(i).trim().isEmpty() ? "" : personalFields.get(i);
            contentStream.beginText();
            contentStream.newLineAtOffset(leftColumnX, yPositionLeft);
            contentStream.showText(sanitizeText(labels[i - 1] + " " + fieldText));
            contentStream.endText();
            yPositionLeft -= 15;
        }
        yPositionLeft -= 10;

        // Separator Line
        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(leftColumnX, yPositionLeft);
        contentStream.lineTo(leftColumnX + columnWidth, yPositionLeft);
        contentStream.stroke();
        yPositionLeft -= 10;

        // Left Column: Work Experience
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(leftColumnX, yPositionLeft);
        contentStream.showText("WORK EXPERIENCE");
        contentStream.endText();
        yPositionLeft -= 20;

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        String experience = data.getWorkExperience().getText().trim().isEmpty() ? "" : data.getWorkExperience().getText();
        yPositionLeft = drawWrappedText(contentStream, experience, leftColumnX, yPositionLeft, columnWidth);
        yPositionLeft -= 10;

        // Separator Line
        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(leftColumnX, yPositionLeft);
        contentStream.lineTo(leftColumnX + columnWidth, yPositionLeft);
        contentStream.stroke();
        yPositionLeft -= 10;

        // Left Column: Skills
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(leftColumnX, yPositionLeft);
        contentStream.showText("SKILLS");
        contentStream.endText();
        yPositionLeft -= 20;

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        String skills = data.getSkills().getText().trim().isEmpty() ? "" : data.getSkills().getText();
        yPositionLeft = drawBulletedText(contentStream, skills, leftColumnX, yPositionLeft, columnWidth);

        // Right Column: Contact Information
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX, yPositionRight);
        contentStream.showText("CONTACT INFORMATION");
        contentStream.endText();
        yPositionRight -= 20;

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        List<String> contactFields = data.getContactInformation().getFields();
        String[] contactLabels = {"CONTACT NUMBER:", "EMAIL ADDRESS:", "ADDRESS:"};
        for (int i = 0; i < contactLabels.length; i++) {
            String fieldText = contactFields.get(i).trim().isEmpty() ? "" : contactFields.get(i);
            contentStream.beginText();
            contentStream.newLineAtOffset(rightColumnX, yPositionRight);
            contentStream.showText(sanitizeText(contactLabels[i] + " " + fieldText));
            contentStream.endText();
            yPositionRight -= 15;
        }
        yPositionRight -= 10;

        // Separator Line
        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(rightColumnX, yPositionRight);
        contentStream.lineTo(rightColumnX + columnWidth, yPositionRight);
        contentStream.stroke();
        yPositionRight -= 10;

        // Right Column: Education
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX, yPositionRight);
        contentStream.showText("EDUCATION");
        contentStream.endText();
        yPositionRight -= 20;

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX, yPositionRight);
        contentStream.showText("College: " + (data.getEducation().getCollegeName().trim().isEmpty() ? "" : sanitizeText(data.getEducation().getCollegeName())));
        contentStream.endText();
        yPositionRight -= 15;

        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX, yPositionRight);
        contentStream.showText("Senior High School: " + (data.getEducation().getShsName().trim().isEmpty() ? "" : sanitizeText(data.getEducation().getShsName())));
        contentStream.endText();
        yPositionRight -= 15;

        contentStream.beginText();
        contentStream.newLineAtOffset(rightColumnX, yPositionRight);
        contentStream.showText("Junior High School: " + (data.getEducation().getJhsName().trim().isEmpty() ? "" : sanitizeText(data.getEducation().getJhsName())));
        contentStream.endText();
    }

    @Override
    public boolean supportsImage() {
        return false;
    }

    @Override
    public String getName() {
        return "Two-Column";
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