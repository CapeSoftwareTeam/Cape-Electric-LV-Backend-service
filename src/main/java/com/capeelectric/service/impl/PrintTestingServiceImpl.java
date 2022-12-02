package com.capeelectric.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capeelectric.exception.PdfException;
import com.capeelectric.exception.PeriodicTestingException;
import com.capeelectric.model.TestDistRecords;
import com.capeelectric.model.TestDistribution;
import com.capeelectric.model.TestIncomingDistribution;
import com.capeelectric.model.Testing;
import com.capeelectric.model.TestingEquipment;
import com.capeelectric.model.TestingRecords;
import com.capeelectric.model.TestingReport;
import com.capeelectric.model.TestingReportComment;
import com.capeelectric.service.PrintTestingService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PrintTestingServiceImpl implements PrintTestingService {

	private static final Logger logger = LoggerFactory.getLogger(PrintTestingServiceImpl.class);

//	@Autowired
//	private TestingReportRepository testingReportRepository;

	@Override
	public void printTesting(String userName, Integer siteId, Optional<TestingReport> testingRepo) throws PeriodicTestingException, PdfException {
		
		logger.debug("called printTesting function userName: {},siteId : {}", userName,siteId);
		
		if (userName != null && !userName.isEmpty() && siteId != null && siteId != 0) {
			Document document = new Document(PageSize.A4, 68, 68, 62, 68);
			try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Testing.pdf"));

//				TestingReport testingReference = testingReportRepository.findByUserNameAndSiteId(userName, siteId);
				
				TestingReport testingReference = testingRepo.get();
				
				List<Testing> testing = testingReference.getTesting();

				Testing testRecords = testing.get(0);
				List<TestingEquipment> testEquipment = testRecords.getTestingEquipment();

				List<TestingReportComment> reportComments = testingReference.getTestingComment();
				TestingReportComment comments = reportComments.get(0);

				document.open();

				Font font = new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD, BaseColor.BLACK);

				Paragraph paragraphOne = new Paragraph("TIC of LV electrical installation ", font);
				paragraphOne.setAlignment(Element.ALIGN_CENTER);

				document.add(paragraphOne);

				PdfPTable table13 = new PdfPTable(1);
				table13.setWidthPercentage(100); // Width 100%
				table13.setSpacingBefore(10f); // Space before table
				table13.setWidthPercentage(100);
				table13.getDefaultCell().setBorder(0);

				PdfPCell cell8 = new PdfPCell(new Paragraph(30, "Part - 4: Testing ", font));
				cell8.setBorder(PdfPCell.NO_BORDER);
				cell8.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table13.addCell(cell8);

				document.add(table13);

				for (Testing testing1 : testing) {
					if (!testing1.getTestingStatus().equalsIgnoreCase("R")) {
						List<TestDistRecords> testDistRecords = testing1.getTestDistRecords();
						for (TestDistRecords testDistRecord : testDistRecords)
							testingIteration(document, testing1, testDistRecord, testEquipment);
					}
				}

				PdfPTable table19 = new PdfPTable(1);
				table19.setWidthPercentage(100); // Width 100%
				table19.setSpacingBefore(10f); // Space before table
				table19.setWidthPercentage(100);
				table19.getDefaultCell().setBorder(0);

				if (comments.getViewerUserName() != null && comments.getInspectorUserName() != null) {
					document.newPage();

					PdfPCell cell65 = new PdfPCell(
							new Paragraph(15, "Section - 3: Viewer And Inspector Comment:", font));
					cell65.setBorder(PdfPCell.NO_BORDER);
					cell65.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table19.addCell(cell65);
					document.add(table19);

					Font font91 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
					float[] pointColumnWidths4 = { 90F, 90F, 90F, 90F };

					PdfPTable table44 = new PdfPTable(pointColumnWidths4);
					table44.setWidthPercentage(100); // Width 100%
					table44.setSpacingBefore(10f); // Space before table
					table44.setWidthPercentage(100);

					PdfPCell cell55 = new PdfPCell(new Paragraph(comments.getViewerUserName(), font91));
					cell55.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell371 = new PdfPCell(new Paragraph("ViewerUserName:", font91));
					cell371.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell371.setGrayFill(0.92f);
					cell371.setFixedHeight(25f);
					table44.addCell(cell371);
					table44.addCell(cell55);

					PdfPCell cell381 = new PdfPCell(new Paragraph(comments.getInspectorUserName(), font91));
					cell381.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell3711 = new PdfPCell(new Paragraph("InspectorUserName:", font91));
					cell3711.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell3711.setFixedHeight(25f);
					cell3711.setGrayFill(0.92f);
					table44.addCell(cell3711);
					table44.addCell(cell381);

					PdfPCell cell561 = new PdfPCell(new Paragraph("ViewerComment Date:", font91));
					cell561.setGrayFill(0.92f);
					cell561.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cell5611 = new PdfPCell(new Paragraph("ViewerComment:", font91));
					cell5611.setGrayFill(0.92f);
					cell5611.setHorizontalAlignment(Element.ALIGN_CENTER);
					table44.addCell(cell5611);
					table44.addCell(cell561);

					PdfPCell cell401 = new PdfPCell(new Paragraph("InspectorComment Date:", font91));
					cell401.setGrayFill(0.92f);
					cell401.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell391 = new PdfPCell(new Paragraph("InspectorComment:", font91));
					cell391.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell391.setGrayFill(0.92f);
					table44.addCell(cell391);
					table44.addCell(cell401);

					tableData(table44, reportComments);

					document.add(table44);

				}
				document.close();
				writer.close();

			} catch (Exception e) {
				logger.debug("Testing PDF creation Failed for SiteId : {}", siteId);
				throw new PdfException("Testing PDF creation Failed"); 
			}

		} else {
			throw new PeriodicTestingException("Invalid Inputs");
		}

	}

	private void testingIteration(Document document, Testing testing1, TestDistRecords testDistRecord,
			List<TestingEquipment> testEquipment) throws DocumentException, IOException {

		new Font(BaseFont.createFont(), 9, Font.NORMAL, BaseColor.BLACK);
		Font font5 = new Font(BaseFont.createFont(), 9, Font.NORMAL, BaseColor.BLACK);

		List<TestDistribution> testDistribution = testDistRecord.getTestDistribution();
		List<TestingRecords> testRecords = testDistRecord.getTestingRecords();

		TestingReport testreport = testing1.getTestingReport();
		List<TestIncomingDistribution> testIncomingDistribution = testreport.getTestIncomingDistribution();

		float[] pointColumnWidths = { 60F, 80F };
		PdfPTable table = new PdfPTable(pointColumnWidths);
		table.setWidthPercentage(100); // Width 100%
		table.setSpacingBefore(5f); // Space before table
		table.setWidthPercentage(100);
		table.getDefaultCell().setBorder(0);

		Font font7 = new Font(BaseFont.createFont(), 9, Font.NORMAL, BaseColor.BLACK);
		Font font2 = new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD, BaseColor.BLACK);

		float[] pointColumnWidths2 = { 60F, 80F };
		PdfPTable table100 = new PdfPTable(pointColumnWidths2);

		table100.setWidthPercentage(100); // Width 100%
		table100.setSpacingBefore(5f); // Space before table
		// table100.setSpacingAfter(5f); // Space after table
		table100.setWidthPercentage(100);
		table100.getDefaultCell().setBorder(0);

		PdfPCell cell80 = new PdfPCell(
				new Paragraph("Location number:", new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD)));
		cell80.setBackgroundColor(new BaseColor(203, 183, 162));
		cell80.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell80.setBorder(PdfPCell.NO_BORDER);
		table100.addCell(cell80);
		PdfPCell cell81 = new PdfPCell(new Paragraph(testing1.getLocationNumber().toString(),
				new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD)));
		cell81.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell81.setBackgroundColor(new BaseColor(203, 183, 162));
		cell81.setBorder(PdfPCell.NO_BORDER);
		table100.addCell(cell81);
		document.add(table100);

		PdfPTable table1001 = new PdfPTable(pointColumnWidths2);

		table1001.setWidthPercentage(100); // Width 100%
		table1001.setSpacingBefore(5f); // Space before table
		// table1001.setSpacingAfter(5f); // Space after table
		table1001.setWidthPercentage(100);
		table1001.getDefaultCell().setBorder(0);
		PdfPCell cell36 = new PdfPCell(
				new Paragraph("Location name:", new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD)));
		cell36.setBackgroundColor(new BaseColor(203, 183, 162));
		cell36.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell36.setBorder(PdfPCell.NO_BORDER);
		table1001.addCell(cell36);
		PdfPCell cell811 = new PdfPCell(new Paragraph(testing1.getLocationName(),
				new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD)));
		cell811.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell811.setBackgroundColor(new BaseColor(203, 183, 162));
		cell811.setBorder(PdfPCell.NO_BORDER);
		table1001.addCell(cell811);
		document.add(table1001);

		PdfPTable table13 = new PdfPTable(1);
		table13.setWidthPercentage(100); // Width 100%
		table13.setSpacingBefore(5f); // Space before table
		table13.setWidthPercentage(100);
		table13.getDefaultCell().setBorder(0);

		PdfPCell cell30 = new PdfPCell(new Paragraph(30, "Section - 1: Basics information", font2));
		cell30.setBorder(PdfPCell.NO_BORDER);
		cell30.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table13.addCell(cell30);
		document.add(table13);

		PdfPCell cell3 = new PdfPCell(new Paragraph(testing1.getTestEngineerName(), font7));
		table.addCell(new Phrase("Test Engineer Name: ", font7));
		cell3.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell3);

		PdfPCell cell04 = new PdfPCell(new Paragraph("Designation:", font7));
		cell04.setBorder(PdfPCell.NO_BORDER);
		cell04.setGrayFill(0.92f);
		table.addCell(cell04);
		PdfPCell cell203 = new PdfPCell(new Paragraph(testing1.getDesignation(), font7));
		cell203.setGrayFill(0.92f);
		cell203.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell203);

		PdfPCell cell4 = new PdfPCell(new Paragraph("Date:", font7));
		cell4.setBorder(PdfPCell.NO_BORDER);
		// cell4.setGrayFill(0.92f);
		table.addCell(cell4);
		PdfPCell cell23 = new PdfPCell(new Paragraph(testing1.getDate(), font7));
		// cell23.setGrayFill(0.92f);
		cell23.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell23);

		PdfPCell cell41 = new PdfPCell(new Paragraph("Company Name:", font7));
		cell41.setBorder(PdfPCell.NO_BORDER);
		cell41.setGrayFill(0.92f);
		table.addCell(cell41);
		PdfPCell cell231 = new PdfPCell(new Paragraph(testing1.getCompanyName(), font7));
		cell231.setGrayFill(0.92f);
		cell231.setBorder(PdfPCell.NO_BORDER);
		table.addCell(cell231);
		document.add(table);

		PdfPTable table104 = new PdfPTable(1);
		table104.setWidthPercentage(100); // Width 100%
		table104.setSpacingBefore(5f); // Space before table
		table104.setWidthPercentage(100);
		table104.getDefaultCell().setBorder(0);

		PdfPCell cell031 = new PdfPCell(new Paragraph(30, "Details of testing instruments:", font2));
		cell031.setBorder(PdfPCell.NO_BORDER);
		cell031.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table104.addCell(cell031);
		document.add(table104);

		PdfPTable table11 = new PdfPTable(5);
		table11.setWidthPercentage(100); // Width 100%
		table11.setSpacingBefore(10f); // Space before table
		// table11.setSpacingAfter(5f); // Space after table

		tableHeader1(table11);
		tableData1(table11, testEquipment);

		document.add(table11);

		PdfPTable table14 = new PdfPTable(1);
		table14.setWidthPercentage(100); // Width 100%
		table14.setSpacingBefore(10f); // Space before table
		table14.setWidthPercentage(100);
		table14.getDefaultCell().setBorder(0);

		PdfPCell cell31 = new PdfPCell(new Paragraph(30, "Section - 2: Detailed Testing:", font2));
		cell31.setBorder(PdfPCell.NO_BORDER);
		cell31.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table14.addCell(cell31);
		document.add(table14);

		PdfPTable table1 = new PdfPTable(pointColumnWidths);
		table1.setWidthPercentage(100); // Width 100%
		table1.setSpacingBefore(5f); // Space after table
		table1.getDefaultCell().setBorder(0);

		for (TestDistribution distribution : testDistribution) {
			PdfPCell cell13 = new PdfPCell(new Paragraph(distribution.getDistributionBoardDetails(), font5));
			table1.addCell(new Phrase("Distribution board details:", font5));
			cell13.setBorder(PdfPCell.NO_BORDER);
			table1.addCell(cell13);

			PdfPCell cell14 = new PdfPCell(new Paragraph("Distribution board name:", font5));
			cell14.setBorder(PdfPCell.NO_BORDER);
			cell14.setGrayFill(0.92f);
			table1.addCell(cell14);
			PdfPCell cell28 = new PdfPCell(new Paragraph(distribution.getReferance(), font5));
			cell28.setGrayFill(0.92f);
			cell28.setBorder(PdfPCell.NO_BORDER);
			table1.addCell(cell28);

			PdfPCell cell15 = new PdfPCell(new Paragraph(distribution.getLocation(), font5));
			table1.addCell(new Phrase("Location:", font5));
			cell15.setBorder(PdfPCell.NO_BORDER);
			table1.addCell(cell15);

			PdfPCell cell16 = new PdfPCell(new Paragraph("Correct supply polarity:", font7));
			cell16.setBorder(PdfPCell.NO_BORDER);
			cell16.setGrayFill(0.92f);
			table1.addCell(cell16);
			PdfPCell cell29 = new PdfPCell(new Paragraph(distribution.getCorrectSupplyPolarity(), font5));
			cell29.setGrayFill(0.92f);
			cell29.setBorder(PdfPCell.NO_BORDER);
			table1.addCell(cell29);

			PdfPCell cell17 = new PdfPCell(new Paragraph(distribution.getNumOutputCircuitsSpare(), font5));
			table1.addCell(new Phrase("Total number of output circuits including spare:", font5));
			cell17.setBorder(PdfPCell.NO_BORDER);
			table1.addCell(cell17);

			PdfPCell cell19 = new PdfPCell(new Paragraph("Number of output circuits tested:", font7));
			cell19.setBorder(PdfPCell.NO_BORDER);
			cell19.setGrayFill(0.92f);
			table1.addCell(cell19);
			PdfPCell cell32 = new PdfPCell(new Paragraph(distribution.getNumOutputCircuitsUse(), font5));
			cell32.setGrayFill(0.92f);
			cell32.setBorder(PdfPCell.NO_BORDER);
			table1.addCell(cell32);

			PdfPCell cell18 = new PdfPCell(new Paragraph(distribution.getInstalledEquipmentVulnarable(), font5));
			table1.addCell(new Phrase("Installed equipment vulnarable to testing:", font5));
			cell18.setBorder(PdfPCell.NO_BORDER);
			table1.addCell(cell18);

			document.add(table1);
			String ratingsInAmpsvalue = distribution.getRatingsAmps();

			String[] ratingAmpIter = ratingsInAmpsvalue.split(",");

			for (String arr : ratingAmpIter) {
				ratingAmpsiterate(document, arr);
			}
		}

		for (TestIncomingDistribution testincomingDist : testIncomingDistribution) {
			List<TestDistribution> testDistribution22 = testDistRecord.getTestDistribution();
			TestDistribution distribution = testDistribution22.get(0);

			if (testincomingDist.getSourceFromSupply().equals(distribution.getSourceFromSupply())) {

				String incomingVoltage = testincomingDist.getIncomingVoltage();
				String incomingVoltage_list[] = incomingVoltage.split(",");
				String IV1 = incomingVoltage_list[0];
				String IV2 = incomingVoltage_list[1];
				String IV3 = incomingVoltage_list[2];
				String IV4 = incomingVoltage_list[3];
				String IV5 = incomingVoltage_list[4];
				String IV6 = incomingVoltage_list[5];
				String IV7 = incomingVoltage_list[6];
				String IV8 = incomingVoltage_list[7];
				String IV9 = incomingVoltage_list[8];

				String incomingIPF = testincomingDist.getIncomingFaultCurrent();
				String incomingIPF_list[] = incomingIPF.split(",");
				String IPF1 = incomingIPF_list[0];
				String IPF2 = incomingIPF_list[1];
				String IPF3 = incomingIPF_list[2];
				String IPF4 = incomingIPF_list[3];
				String IPF5 = incomingIPF_list[4];
				String IPF6 = incomingIPF_list[5];
				String IPF7 = incomingIPF_list[6];
				String IPF8 = incomingIPF_list[7];
				String IPF9 = incomingIPF_list[8];

				String incomingZS = testincomingDist.getIncomingLoopImpedance();
				String incomingZS_list[] = incomingZS.split(",");
				String ZS1 = incomingZS_list[0];
				String ZS2 = incomingZS_list[1];
				String ZS3 = incomingZS_list[2];
				String ZS4 = incomingZS_list[3];
				String ZS5 = incomingZS_list[4];
				String ZS6 = incomingZS_list[5];
				String ZS7 = incomingZS_list[6];
				String ZS8 = incomingZS_list[7];
				String ZS9 = incomingZS_list[8];

				String incomingActualLoadCurrent = testincomingDist.getIncomingActualLoad();
				String incomingActual_Load_list[] = incomingActualLoadCurrent.split(",");
				String loadCurrent = incomingActual_Load_list[0];
				String loadCurrent2 = incomingActual_Load_list[1];
				String loadCurrent3 = incomingActual_Load_list[2];
				String loadCurrent4 = incomingActual_Load_list[3];

				Font font9 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);

				PdfPTable table250 = new PdfPTable(pointColumnWidths);
				table250.setWidthPercentage(100); // Width 100%
				table250.setSpacingBefore(5f);
				table250.setSpacingAfter(5f);
				table250.getDefaultCell().setBorder(0);

				PdfPCell celllabel = new PdfPCell(new Paragraph("Source of incoming:", font9));
				celllabel.setBorder(PdfPCell.NO_BORDER);
				celllabel.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table250.addCell(celllabel);
				PdfPCell cell57 = new PdfPCell(new Paragraph(testincomingDist.getSourceFromSupply(), font9));
				cell57.setGrayFill(0.92f);
				cell57.setBorder(PdfPCell.NO_BORDER);
				cell57.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table250.addCell(cell57);

				PdfPTable table2 = new PdfPTable(10);
				table2.setWidthPercentage(100); // Width 100%
				table2.setSpacingBefore(5f);
				// table2.setSpacingAfter(5);
				table2.getDefaultCell().setBorder(0);

				float[] pointColumnWidths43 = { 45F, 90F, 90F, 135F, 90F };

				PdfPTable table22 = new PdfPTable(pointColumnWidths43);
				table22.setWidthPercentage(100); // Width 100%
				table22.getDefaultCell().setBorder(0);

				tableHead(table2);

				addRow(table2, "Nominal Voltage U/U0 (V)", IV1, IV2, IV3, IV4, IV5, IV6, IV7, IV8, IV9);
				addRow(table2, "External Loop Impedance Ze (ohms)", ZS1, ZS2, ZS3, ZS4, ZS5, ZS6, ZS7, ZS8, ZS9);
				addRow(table2, "Prospective fault current Ipfc (kA)", IPF1, IPF2, IPF3, IPF4, IPF5, IPF6, IPF7, IPF8,
						IPF9);

				addRow1(table22, "Actual load current connected to this source (A)", "\r\n" + loadCurrent,
						"\r\n" + loadCurrent2, "\r\n" + loadCurrent3, "\r\n" + loadCurrent4);

				document.add(table250);
				document.add(table2);
				document.add(table22);

			}
		}
		document.newPage();

		for (TestingRecords testingRecords11 : testRecords) {
			if (!testingRecords11.getTestingRecordStatus().equalsIgnoreCase("R")) {
				testingTableIteration(document, testingRecords11);
			}
		}
	}

	private void tableHeader1(PdfPTable table11) throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
		cell.setPadding(5);
		Font font1 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
		cell.setPhrase(new Phrase("Equipment Name", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setGrayFill(0.92f);
		table11.addCell(cell);
		cell.setPhrase(new Phrase("Equipment Make", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setGrayFill(0.92f);
		table11.addCell(cell);
		cell.setPhrase(new Phrase("Equipment Model", font1));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setGrayFill(0.92f);
		table11.addCell(cell);
		cell.setPhrase(new Phrase("Equipment SL.NO", font1));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table11.addCell(cell);
		cell.setPhrase(new Phrase("Calibration Due Date", font1));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table11.addCell(cell);

	}

	private void tableData1(PdfPTable table11, List<TestingEquipment> testEquipment)
			throws DocumentException, IOException {
		for (TestingEquipment arr : testEquipment) {
			if (!arr.getTestingEquipmentStatus().equalsIgnoreCase("R")) {
				PdfPCell cell = new PdfPCell();
				Font font6 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
				cell.setPhrase(new Phrase(arr.getEquipmentName(), font6));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table11.addCell(cell);
				cell.setPhrase(new Phrase(arr.getEquipmentMake(), font6));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table11.addCell(cell);
				cell.setPhrase(new Phrase(arr.getEquipmentModel(), font6));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table11.addCell(cell);
				cell.setPhrase(new Phrase(arr.getEquipmentSerialNo(), font6));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table11.addCell(cell);
				cell.setPhrase(new Phrase(arr.getEquipmentCalibrationDueDate().toString().split(" ")[0], font6));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				split(" ")[0]
				table11.addCell(cell);
			}
		}
	}

	private void addRow1(PdfPTable table22, String string, String loadCurrent, String loadCurrent2, String loadCurrent3,
			String loadCurrent4) throws DocumentException, IOException {
		Font font = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);

		Font font1 = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.BLACK);
		PdfPCell nameCell = new PdfPCell(new Paragraph(string, font1));
		nameCell.setGrayFill(0.92f);
		PdfPCell valueCell2 = new PdfPCell(new Paragraph(loadCurrent, font));
		PdfPCell valueCell3 = new PdfPCell(new Paragraph(loadCurrent2, font));
		PdfPCell valueCell4 = new PdfPCell(new Paragraph(loadCurrent3, font));
		PdfPCell valueCell5 = new PdfPCell(new Paragraph(loadCurrent4, font));

		valueCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell5.setHorizontalAlignment(Element.ALIGN_CENTER);

		table22.addCell(nameCell);
		table22.addCell(valueCell2);
		table22.addCell(valueCell3);
		table22.addCell(valueCell4);
		table22.addCell(valueCell5);

	}

	private void ratingAmpsiterate(Document document, String arr) throws DocumentException, IOException {

		Font font7 = new Font(BaseFont.createFont(), 9, Font.NORMAL, BaseColor.BLACK);
		float[] pointColumnWidths2 = { 60F, 80F };
		PdfPTable table9 = new PdfPTable(pointColumnWidths2);
		table9.setWidthPercentage(100); // Width 100%
		table9.setSpacingBefore(5f); // Space after table
		table9.setSpacingAfter(5f); // Space after table
		table9.getDefaultCell().setBorder(0);

		PdfPCell cell1 = new PdfPCell(new Paragraph("Ratings In Amps:", font7));
		cell1.setBorder(PdfPCell.NO_BORDER);
		cell1.setGrayFill(0.92f);
		table9.addCell(cell1);
		PdfPCell cell22 = new PdfPCell(new Paragraph(arr, font7));
		cell22.setGrayFill(0.92f);
		cell22.setBorder(PdfPCell.NO_BORDER);
		table9.addCell(cell22);
		document.add(table9);
	}

	private void tableData(PdfPTable table44, List<TestingReportComment> reportComments)
			throws DocumentException, IOException {

		Collections.sort(reportComments, new Comparator<TestingReportComment>() {
			public int compare(TestingReportComment periodic1, TestingReportComment periodic2) {
				if (periodic1.getViewerDate() != null && periodic2.getViewerDate() != null) {
					return periodic1.getViewerDate().compareTo(periodic2.getViewerDate());
				} else {
					return 0;
				}
			}
		});

		Collections.sort(reportComments, new Comparator<TestingReportComment>() {
			public int compare(TestingReportComment periodic1, TestingReportComment periodic2) {
				if (periodic1.getInspectorDate() != null && periodic2.getInspectorDate() != null) {
					return periodic1.getInspectorDate().compareTo(periodic2.getInspectorDate());
				} else {
					return 0;
				}
			}
		});
		for (TestingReportComment arr : reportComments) {
			Font font = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
			PdfPCell cell = new PdfPCell();

			if (arr.getViewerComment() != null) {
				cell.setPhrase(new Phrase(arr.getViewerComment(), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table44.addCell(cell);
			} else {
				cell.setPhrase(new Phrase("No viewer comment available", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table44.addCell(cell);
			}

			if (arr.getViewerDate() != null) {
				cell.setPhrase(new Phrase(arr.getViewerDate().toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table44.addCell(cell);
			} else {
				cell.setPhrase(new Phrase("No viewer date available", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table44.addCell(cell);
			}

			if (arr.getInspectorComment() != null) {
				cell.setPhrase(new Phrase(arr.getInspectorComment(), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table44.addCell(cell);
			} else {
				cell.setPhrase(new Phrase("No inspector comment available", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table44.addCell(cell);
			}

			if (arr.getInspectorDate() != null) {
				cell.setPhrase(new Phrase(arr.getInspectorDate().toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table44.addCell(cell);
			} else {
				cell.setPhrase(new Phrase("No inspector date available", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table44.addCell(cell);
			}

		}
	}

	private void testingTableIteration(Document document, TestingRecords testingRecords1)
			throws DocumentException, IOException {

		PdfPTable table466 = new PdfPTable(12);
		table466.setSpacingAfter(20f); // Space after table
		table466.setSpacingBefore(20f); // Space before table
		table466.setWidthPercentage(100);
		Font font22 = new Font(BaseFont.createFont(), 12, Font.NORMAL, BaseColor.BLACK);
		Font font23 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
		Font font25 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
		Font font26 = new Font(BaseFont.createFont(), 9, Font.NORMAL, BaseColor.BLACK);
		Font font27 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
		PdfPCell cell1;
		cell1 = new PdfPCell(new Phrase("Circuit details", font22));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(12);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Circuit no", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getCircuitNo(), font27));
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Description", font25));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(3);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getCircuitDesc(), font27));
		cell1.setColspan(6);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("\r\n\r\n\r\n\r\n\r\n\r\nOCPD", font23));
		cell1.setGrayFill(0.92f);
		cell1.setRowspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Standard No", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getCircuitStandardNo(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Make", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getCircuitMake(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Type", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getCircuitType(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("No of Poles", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getNumberOfPoles(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Model/ current curve", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getCircuitCurrentCurve(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Rating (A)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getCircuitRating(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Breaking capacity (KA)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getCircuitBreakingCapacity(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Short circuit setting  (A)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getShortCircuitSetting(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("E and F setting  (A)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.geteFSetting(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("\r\n\r\nConductor details", font23));
		cell1.setGrayFill(0.92f);
		cell1.setRowspan(4);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Installation reference method", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getConductorInstallation(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Cross sectional area (sq.mm)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setRowspan(3);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Phase", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getConductorPhase(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Neutral", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getConductorNeutral(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("PE", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getConductorPecpc(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(
				"\r\n\r\n\r\nContinuity (ohms)\r\n"
						+ "Applicable to live conductor in final circuits and protective conductors only\r\n" + "",
				font23));
		cell1.setGrayFill(0.92f);
		cell1.setRowspan(5);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Length (meter)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getContinutiyApproximateLength(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("(R1+R2)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getContinutiyRR(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("R2", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getContinutiyR(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Maximum\r\nAllowed\r\nvalue", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase("\r\n\r\n" + testingRecords1.getMaximumAllowedValue(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Continuity Remarks", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getContinuityRemarks(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Polarity", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(3);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getContinutiyPolarity(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Parameters", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(3);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("L1-L2", font26));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("L2-L3", font26));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("L1-L3", font26));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("L1-N", font26));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("L2-N", font26));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("L3-N", font26));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("L1-PE", font26));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("L2-PE", font26));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("L3-PE", font26));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);

		cell1 = new PdfPCell(new Phrase("Insulation resistance (M.ohms)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(3);
		table466.addCell(cell1);

		String insulationResistance = testingRecords1.getInsulationResistance();
		String insulationResistance_list[] = insulationResistance.split(",");
		String IR1 = insulationResistance_list[0];
		String IR2 = insulationResistance_list[1];
		String IR3 = insulationResistance_list[2];
		String IR4 = insulationResistance_list[3];
		String IR5 = insulationResistance_list[4];
		String IR6 = insulationResistance_list[5];
		String IR7 = insulationResistance_list[6];
		String IR8 = insulationResistance_list[7];
		String IR9 = insulationResistance_list[8];
		cell1 = new PdfPCell(new Phrase(IR1, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(IR2, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(IR3, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(IR4, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(IR5, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(IR6, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(IR7, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(IR8, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(IR9, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(" Voltage (v)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(3);
		table466.addCell(cell1);
		String voltage = testingRecords1.getTestVoltage();
		String voltage_list[] = voltage.split(",");
		String V1 = voltage_list[0];
		String V2 = voltage_list[1];
		String V3 = voltage_list[2];
		String V4 = voltage_list[3];
		String V5 = voltage_list[4];
		String V6 = voltage_list[5];
		String V7 = voltage_list[6];
		String V8 = voltage_list[7];
		String V9 = voltage_list[8];
		cell1 = new PdfPCell(new Phrase(V1, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(V2, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(V3, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(V4, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(V5, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(V6, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(V7, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(V8, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(V9, font23));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase("Fault loop impedance (ohms) ", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(3);
		table466.addCell(cell1);
		String faultCurrent = testingRecords1.getTestLoopImpedance();
		String faultCurrent_list[] = faultCurrent.split(",");
		String FC1 = faultCurrent_list[0];
		String FC2 = faultCurrent_list[1];
		String FC3 = faultCurrent_list[2];
		String FC4 = faultCurrent_list[3];
		String FC5 = faultCurrent_list[4];
		String FC6 = faultCurrent_list[5];
		String FC7 = faultCurrent_list[6];
		String FC8 = faultCurrent_list[7];
		String FC9 = faultCurrent_list[8];

		cell1 = new PdfPCell(new Phrase(FC1, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC2, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC3, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC4, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC5, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC6, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC7, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC8, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC9, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase("Disconnection Time (s)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(3);
		table466.addCell(cell1);
		String disconnectionTime = testingRecords1.getDisconnectionTime();
		String disconnectionTime_list[] = disconnectionTime.split(",");
		String DT1 = disconnectionTime_list[0];
		String DT2 = disconnectionTime_list[1];
		String DT3 = disconnectionTime_list[2];
		String DT4 = disconnectionTime_list[3];
		String DT5 = disconnectionTime_list[4];
		String DT6 = disconnectionTime_list[5];
		String DT7 = disconnectionTime_list[6];
		String DT8 = disconnectionTime_list[7];
		String DT9 = disconnectionTime_list[8];
		cell1 = new PdfPCell(new Phrase(DT1, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(DT2, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(DT3, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(DT4, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(DT5, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(DT6, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(DT7, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(DT8, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(DT9, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase("Actual Short circuit / fault current (KA)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(3);
		table466.addCell(cell1);
		String faultCurrent1 = testingRecords1.getTestFaultCurrent();
		String faultCurrent1_list[] = faultCurrent1.split(",");
		String FC11 = faultCurrent1_list[0];
		String FC22 = faultCurrent1_list[1];
		String FC33 = faultCurrent1_list[2];
		String FC44 = faultCurrent1_list[3];
		String FC55 = faultCurrent1_list[4];
		String FC66 = faultCurrent1_list[5];
		String FC77 = faultCurrent1_list[6];
		String FC88 = faultCurrent1_list[7];
		String FC99 = faultCurrent1_list[8];

		cell1 = new PdfPCell(new Phrase(FC11, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC22, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC33, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC44, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC55, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC66, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC77, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC88, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(FC99, font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase("\r\n\r\n\r\n\r\nRCD", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setRowspan(5);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase("Type (A/B/AC/F)", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(2);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getRcdType(), font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(9);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase("Sensitivity", font23));
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(2);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getRcdCurrent(), font27));
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(9);
		table466.addCell(cell1);
		
		Phrase operatingTime = new Phrase("Operating time I", font23);
		operatingTime.add(new Phrase("dn",new Font(BaseFont.createFont(), 6, Font.NORMAL, BaseColor.BLACK)));
		
		cell1 = new PdfPCell(operatingTime);
		cell1.setGrayFill(0.92f);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setColspan(2);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getRcdOperatingCurrent(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		
		Phrase operatingTimeFive = new Phrase("Operating time 5* I", font23);
		operatingTimeFive.add(new Phrase("dn",new Font(BaseFont.createFont(), 6, Font.NORMAL, BaseColor.BLACK)));
 		 
 		
		cell1 = new PdfPCell(operatingTimeFive);
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getRcdOperatingFiveCurrent(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase("Test button operation", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getRcdTestButtonOperation(), font27));
		cell1.setColspan(9);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase("	Remarks", font23));
		cell1.setGrayFill(0.92f);
		cell1.setColspan(2);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		cell1 = new PdfPCell(new Phrase(testingRecords1.getRcdRemarks(), font27));
		cell1.setColspan(10);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table466.addCell(cell1);
		document.add(table466);
		document.newPage();
	}

	private void tableHead(PdfPTable table2) throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
		cell.setPadding(10);
		Font font1 = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.BLACK);
		Font font = new Font(BaseFont.createFont(), 9, Font.NORMAL, BaseColor.BLACK);
		cell.setPhrase(new Phrase("Nature of supply parameters", font1));
		cell.setGrayFill(0.92f);
		table2.addCell(cell);
		cell.setPhrase(new Phrase("\r\nL1-L2", font));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell);
		cell.setPhrase(new Phrase("\r\nL2-L3", font));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell);
		cell.setPhrase(new Phrase("\r\nL1-L3", font));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell);
		cell.setPhrase(new Phrase("\r\nL1-N", font));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell);
		cell.setPhrase(new Phrase("\r\nL1-N", font));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell);
		cell.setPhrase(new Phrase("\r\nL3-N", font));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell);
		cell.setPhrase(new Phrase("\r\nL1-PE", font));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell);
		cell.setPhrase(new Phrase("\r\nL2-PE", font));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell);
		cell.setPhrase(new Phrase("\r\nL3-PE", font));
		cell.setGrayFill(0.92f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cell);
	}

	private void addRow(PdfPTable table2, String string, String string2, String string3, String string4, String string5,
			String string6, String string7, String string8, String string9, String string10)
			throws DocumentException, IOException {

		Font font = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
		Font font1 = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.BLACK);

		PdfPCell nameCell = new PdfPCell(new Paragraph(string, font1));
		nameCell.setGrayFill(0.92f);
		PdfPCell valueCell1 = new PdfPCell(new Paragraph(string2, font));
		PdfPCell valueCell2 = new PdfPCell(new Paragraph(string3, font));
		PdfPCell valueCell3 = new PdfPCell(new Paragraph(string4, font));
		PdfPCell valueCell4 = new PdfPCell(new Paragraph(string5, font));
		PdfPCell valueCell5 = new PdfPCell(new Paragraph(string6, font));
		PdfPCell valueCell6 = new PdfPCell(new Paragraph(string7, font));
		PdfPCell valueCell7 = new PdfPCell(new Paragraph(string8, font));
		PdfPCell valueCell8 = new PdfPCell(new Paragraph(string9, font));
		PdfPCell valueCell9 = new PdfPCell(new Paragraph(string10, font));
		valueCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell9.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(nameCell);
		table2.addCell(valueCell1);
		table2.addCell(valueCell2);
		table2.addCell(valueCell3);
		table2.addCell(valueCell4);
		table2.addCell(valueCell5);
		table2.addCell(valueCell6);
		table2.addCell(valueCell7);
		table2.addCell(valueCell8);
		table2.addCell(valueCell9);

	}

}
