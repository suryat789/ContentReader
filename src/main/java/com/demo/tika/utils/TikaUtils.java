package com.demo.tika.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.asm.ClassParser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class TikaUtils {

	public static String detectFileType(File file) throws IOException {
		final Tika tika = new Tika();
		return tika.detect(file);
	}

	public static String getFileContents(String fileType, File file) throws IOException, SAXException, TikaException {
		final BodyContentHandler handler = new BodyContentHandler();
		final Metadata metadata = new Metadata();
		final FileInputStream inputstream = new FileInputStream(file);
		final ParseContext pcontext = new ParseContext();
		String content = null;

		if (fileType.contains("pdf")) {
			content = getPdfContents(file, inputstream, handler, metadata, pcontext);
		} else if (fileType.contains("msword") || fileType.contains("officedocument")) {
			content = getMsOfficeContents(file, inputstream, handler, metadata, pcontext);
		} else if (fileType.contains("plain")) {
			content = getTextContents(file, inputstream, handler, metadata, pcontext);
		} else if (fileType.contains("java")) {
			content = getClassFileContents(file, inputstream, handler, metadata, pcontext);
		}

		System.out.println("File Contents:" + content);
		return content;
	}

	public static String getPdfContents(File file, InputStream inputstream, BodyContentHandler handler, Metadata metadata, ParseContext pcontext)
			throws IOException, SAXException, TikaException {
		final PDFParser pdfparser = new PDFParser();
		pdfparser.parse(inputstream, handler, metadata, pcontext);
		return handler.toString();
	}

	public static String getClassFileContents(File file, InputStream inputstream, BodyContentHandler handler, Metadata metadata, ParseContext pcontext)
			throws IOException, SAXException, TikaException {
		final ClassParser ClassParser = new ClassParser();
		ClassParser.parse(inputstream, handler, metadata, pcontext);
		return handler.toString();
	}

	public static String getMsOfficeContents(File file, InputStream inputstream, BodyContentHandler handler, Metadata metadata, ParseContext pcontext)
			throws IOException, SAXException, TikaException {
		final OOXMLParser msofficeparser = new OOXMLParser();
		msofficeparser.parse(inputstream, handler, metadata, pcontext);
		return handler.toString();
	}

	public static String getTextContents(File file, InputStream inputstream, BodyContentHandler handler, Metadata metadata, ParseContext pcontext)
			throws IOException, SAXException, TikaException {
		final TXTParser TexTParser = new TXTParser();
		TexTParser.parse(inputstream, handler, metadata, pcontext);
		return handler.toString();
	}
}
