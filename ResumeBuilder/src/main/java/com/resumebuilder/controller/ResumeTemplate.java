package com.resumebuilder.controller;

import com.resumebuilder.model.ResumeData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.awt.image.BufferedImage;

public interface ResumeTemplate {
    void generate(PDPageContentStream contentStream, ResumeData data, PDDocument document, BufferedImage image) throws Exception;
    boolean supportsImage();
    String getName();
}