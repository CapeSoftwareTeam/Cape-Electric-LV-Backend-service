package com.capeelectric.util;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

	private static final int DARK_GRAY = 0;
	private PdfTemplate t;
	private Image total;

	public void onOpenDocument(PdfWriter writer, Document document) {
		t = writer.getDirectContent().createTemplate(50, 16);
		try {
			total = Image.getInstance(t);
			total.setRole(PdfName.ARTIFACT);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		addFooter(writer, document);

	}

	private void addFooter(PdfWriter writer, Document document) {
		PdfPTable footer = new PdfPTable(2);
		footer.getDefaultCell().setBorder(0);
		try {
			final com.itextpdf.text.Rectangle pageSize = document.getPageSize();
			final PdfContentByte directContent = writer.getDirectContent();
			directContent.setColorFill(new GrayColor(DARK_GRAY));

//			String file = "file:///D:/project%20cape/siva/Cape-Back-end/src/main/resources/image/rush-logo.png";
//			Image image = Image.getInstance(file);
//			image.scaleToFit(185, 185);
//			image.setAbsolutePosition(-3, -9);
//			document.add(image);
			
			Font font = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.DARK_GRAY);

			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
					new Phrase("Testing Inspection and Verification (TIC) of LV electrical installation", font), 300,
					40, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
					new Phrase("Electrical safety in Industrial and Commercial premises", font), 302, 30, 0);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
					new Phrase("as per IEC 60364 – 6 (IS 732)", font), 300, 20, 0);

			footer.setWidths(new int[] { 2, 1 });
			footer.setTotalWidth(70);
			footer.setLockedWidth(true);
			footer.getDefaultCell().setFixedHeight(40);
			footer.getDefaultCell().setBorderColor(BaseColor.BLACK);
			footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

			footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()),
					new Font(BaseFont.createFont(), 8)));

			PdfPCell totalPageCount = new PdfPCell(total);
			totalPageCount.setBorder(0);
			footer.addCell(totalPageCount);

			PdfContentByte canvas = writer.getDirectContent();
			canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
			footer.writeSelectedRows(0, -1, 470, 45, canvas);
			footer.getDefaultCell().setBorder(0);
			canvas.endMarkedContentSequence();
		} catch (DocumentException | IOException de) {
			throw new ExceptionConverter(de);
		}
	}

	public void onCloseDocument(PdfWriter writer, Document document) {
		int totalLength = String.valueOf(writer.getPageNumber()).length();
		int totalWidth = totalLength * 5;
		ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
				new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)), totalWidth,
				6, 0);
	}
}
