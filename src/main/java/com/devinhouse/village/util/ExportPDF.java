package com.devinhouse.village.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.devinhouse.village.model.transport.VillageReportDTO;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
 
 
public class ExportPDF {
    private VillageReportDTO report;
     
    public ExportPDF(VillageReportDTO report) {
        this.report = report;
    }
    
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document pdfReportPage = new Document(PageSize.A4);

		pdfReportPage.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(20);
		font.setColor(Color.BLACK);

		Paragraph title = new Paragraph("Relatório da vila", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);

		pdfReportPage.add(title);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(75f);
		table.setWidths(new float[] { 2.5f, 1.2f });
		table.setSpacingBefore(10);

		writeTableHeader(table);
		writeTableData(table);

		pdfReportPage.add(table);

		pdfReportPage.close();

	}
	
	public byte[] export() throws DocumentException, IOException {
		Document pdfReportPage = new Document(PageSize.A4);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	
		PdfWriter.getInstance(pdfReportPage, byteArrayOutputStream);

		pdfReportPage.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(20);
		font.setColor(Color.BLACK);

		Paragraph title = new Paragraph("Relatório da vila", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);

		pdfReportPage.add(title);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(75f);
		table.setWidths(new float[] { 2.5f, 1.2f });
		table.setSpacingBefore(10);

		writeTableHeader(table);
		writeTableData(table);

		pdfReportPage.add(table);

		pdfReportPage.close();
		
		byte[] pdfBytes = byteArrayOutputStream.toByteArray();
		
		
		
		return pdfBytes;

	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		cell.setRight(cell.getLeft() - ((cell.getLeft() / 100) * 50));

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Métrica", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Valor", font));
		table.addCell(cell);
	}

	private void writeTableData(PdfPTable table) {

		table.addCell("Custo total da vila");
		table.addCell(report.getCost().toString());

		table.addCell("Orçamento da vila");
		table.addCell(report.getInitialBudget().toString());

		table.addCell("Custo de todos os moradores somados");
		table.addCell(report.getResidentsCostSum().toString());

		table.addCell("Morador com maior custo");
		table.addCell(report.getResidentWithHigherCost());

	}
}
