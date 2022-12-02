package com.capeelectric.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capeelectric.exception.ObservationException;
import com.capeelectric.exception.PdfException;
import com.capeelectric.exception.SummaryException;
import com.capeelectric.model.Summary;
import com.capeelectric.model.SummaryComment;
import com.capeelectric.model.SummaryDeclaration;
import com.capeelectric.model.SummaryInnerObservation;
import com.capeelectric.model.SummaryObservation;
import com.capeelectric.repository.SummaryRepository;
import com.capeelectric.service.PrintService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PrintServiceImpl implements PrintService {

	private static final Logger logger = LoggerFactory.getLogger(PrintServiceImpl.class);

	@Autowired
	private SummaryRepository summaryRepository;

	@Override
	public void printSummary(String userName, Integer siteId) throws SummaryException, ObservationException, PdfException {

		logger.debug("called printSummary function userName: {},siteId : {}", userName, siteId);

		if (userName != null && !userName.isEmpty() && siteId != null && siteId != 0) {
			Document document = new Document(PageSize.A4, 68, 68, 62, 68);

			try {

				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Summary.pdf"));

				List<Summary> summary = summaryRepository.findByUserNameAndSiteId(userName, siteId);
				Summary summary11 = summary.get(0);

				List<SummaryDeclaration> declaration1 = summary11.getSummaryDeclaration();
				List<SummaryObservation> summaryObser = summary11.getSummaryObservation();
				SummaryDeclaration declaration = declaration1.get(0);
				SummaryDeclaration declaration11 = declaration1.get(1);

				List<SummaryComment> ReportComments = summary11.getSummaryComment();
				SummaryComment comments = ReportComments.get(0);

				document.open();
				Font font = new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD, BaseColor.BLACK);
				Paragraph paragraphOne = new Paragraph("TIC of LV electrical installation ", font);
				paragraphOne.setAlignment(Element.ALIGN_CENTER);

				Font font2 = new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD, BaseColor.BLACK);
				PdfPTable table10 = new PdfPTable(1);
				table10.setWidthPercentage(100); // Width 100%
				table10.setSpacingBefore(10f); // Space before table
				table10.getDefaultCell().setBorder(0);

				PdfPCell cell9 = new PdfPCell(
						new Paragraph("Part - 5: Observations, Recommendations and Summary", font2));
				cell9.setBorder(PdfPCell.NO_BORDER);
				cell9.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table10.addCell(cell9);

				PdfPTable table13 = new PdfPTable(1);
				table13.setWidthPercentage(100); // Width 100%
				table13.setSpacingBefore(10f); // Space before table
				table13.getDefaultCell().setBorder(0);

				PdfPCell cell8 = new PdfPCell(
						new Paragraph("Section - 1: Extent and limitations of inspection and testing ", font2));
				cell8.setBorder(PdfPCell.NO_BORDER);
				cell8.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table13.addCell(cell8);

				Font font5 = new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD, BaseColor.BLACK);
				document.add(paragraphOne);
				document.add(table10);
				document.add(table13);

				float[] pointColumnWidths = { 90F, 90F };

				for (Summary summary1 : summary) {

					PdfPTable table4 = new PdfPTable(pointColumnWidths);
					table4.setSpacingBefore(5f); // Space before table
					table4.setWidthPercentage(100);
					table4.getDefaultCell().setBorder(0);

					Font font8 = new Font(BaseFont.createFont(), 9, Font.NORMAL, BaseColor.BLACK);
					Font font9 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
					PdfPCell cell = new PdfPCell(new Paragraph(summary1.getExtentInstallation(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					table4.addCell(new Phrase("Extent of installation covered by this Report:", font8));
					cell.setBorder(PdfPCell.NO_BORDER);
					table4.addCell(cell);

					PdfPCell cell1 = new PdfPCell(new Paragraph("Agreed limitations including the reasons:", font9));
					cell1.setBorder(PdfPCell.NO_BORDER);
					cell1.setGrayFill(0.92f);
					table4.addCell(cell1);
					PdfPCell cell22 = new PdfPCell(new Paragraph(summary1.getAgreedLimitations(), font9));
					cell22.setGrayFill(0.92f);
					cell22.setBorder(PdfPCell.NO_BORDER);
					table4.addCell(cell22);

					PdfPCell cell2 = new PdfPCell(new Paragraph(summary1.getAgreedWith(), font9));
					cell2.setBorder(PdfPCell.NO_BORDER);

					table4.addCell(new Phrase("Agreed with:", font8));
					table4.addCell(cell2);

					PdfPCell cell123 = new PdfPCell(
							new Paragraph("Operational limitations including the reasons:", font9));
					cell123.setBorder(PdfPCell.NO_BORDER);
					cell123.setGrayFill(0.92f);
					table4.addCell(cell123);
					PdfPCell cell24 = new PdfPCell(new Paragraph(summary1.getOperationalLimitations(), font9));
					cell24.setGrayFill(0.92f);
					cell24.setBorder(PdfPCell.NO_BORDER);
					table4.addCell(cell24);

					document.add(table4);

//					Paragraph paragraphOne5 = new Paragraph(
//							"The inspection and testing detailed in this report have been carried out in accordance with IEC60364. It should be note that cables concealed within trunk/trench and conduits, under floors which are generally within the fabric of the building or underground, have not been inspected unless it is specifically agreed between the client and inspector prior to the inspection :  "
//									+ summary1.getInspectionTestingDetailed(),
//							font8);
//					document.add(table4);
//					document.add(paragraphOne5);

					PdfPTable table15 = new PdfPTable(1);
					table15.setWidthPercentage(100); // Width 100%
					table15.setSpacingBefore(10f); // Space before table
					table15.getDefaultCell().setBorder(0);

					PdfPCell cell25 = new PdfPCell(new Paragraph(15, "Section - 2: Observations ", font5));
					cell25.setBorder(PdfPCell.NO_BORDER);
					cell25.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table15.addCell(cell25);
					document.add(table15);

					Paragraph paragraphOne6 = new Paragraph(
							"Referring to attached inspection report and test results and subject to the limitations specified at the extent and limitations of inspection and testing :  "
									+ summary1.getLimitationsInspection(),
							font8);
					document.add(paragraphOne6);

					if (summary1.getLimitationsInspection().equals("The following observations are made")) {

//						Inspection Observation Start from here

						PdfPTable obs1 = new PdfPTable(1);
						obs1.setWidthPercentage(100); // Width 100%
						obs1.setSpacingBefore(10f); // Space before table
						obs1.getDefaultCell().setBorder(0);

						PdfPCell ObservationCell1 = new PdfPCell(
								new Paragraph("Supply characteristics observations ", font5));
						ObservationCell1.setBorder(PdfPCell.NO_BORDER);
						ObservationCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
						obs1.addCell(ObservationCell1);
						document.add(obs1);

						PdfPTable Obsevation = new PdfPTable(pointColumnWidths);
						Obsevation.setWidthPercentage(100); // Width 100%
						Obsevation.setSpacingBefore(10f); // Space before table
						Obsevation.getDefaultCell().setBorder(0);

						PdfPTable InnAlterOBS = new PdfPTable(pointColumnWidths);
						InnAlterOBS.setWidthPercentage(100); // Width 100%
//						InnAlterOBS.setSpacingBefore(5f); // Space before table
						InnAlterOBS.getDefaultCell().setBorder(0);

						PdfPTable Obsevation2 = new PdfPTable(pointColumnWidths);
						Obsevation2.setWidthPercentage(100); // Width 100%
//						Obsevation2.setSpacingBefore(5f); // Space before table
						Obsevation2.getDefaultCell().setBorder(0);

						PdfPTable obs3 = new PdfPTable(4);
						obs3.setWidthPercentage(100); // Width 100%
						// obs3.setSpacingBefore(10f); // Space before table
						// obs3.getDefaultCell().setBorder(0);

						observationHead(document);

						for (SummaryObservation supplyObs : summaryObser) {
							if (supplyObs.getObservationComponentDetails().equalsIgnoreCase("mainsObservations")) {

								PdfPCell MainsOBS1 = new PdfPCell(new Paragraph("Mains Observation", font9));
								MainsOBS1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								MainsOBS1.setGrayFill(0.92f);
								obs3.addCell(MainsOBS1);

								PdfPCell MainsOBS22 = new PdfPCell(new Paragraph(supplyObs.getObservations(), font9));
								MainsOBS1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(MainsOBS22);

								PdfPCell MainsOBS3 = new PdfPCell(new Paragraph(supplyObs.getFurtherActions(), font9));
								MainsOBS1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(MainsOBS3);

								PdfPCell MainsOBS4 = new PdfPCell(new Paragraph(supplyObs.getComment(), font9));
								obs3.addCell(MainsOBS4);
							}
						}
						int i = 1;
						int j = 0;
						for (SummaryObservation supplyObs : summaryObser) {

							if (supplyObs.getObservationComponentDetails().contains("alternate")) {

								PdfPCell alOb1 = new PdfPCell(new Paragraph("Alternate Observations - " + i, font9));
								alOb1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								alOb1.setGrayFill(0.92f);
								obs3.addCell(alOb1);

								PdfPCell alOb11 = new PdfPCell(new Paragraph(supplyObs.getObservations(), font9));
								alOb11.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(alOb11);

								PdfPCell alOb111 = new PdfPCell(new Paragraph(supplyObs.getFurtherActions(), font9));
								alOb111.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(alOb111);

								PdfPCell alOb1111 = new PdfPCell(new Paragraph(supplyObs.getComment(), font9));
								alOb1111.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(alOb1111);
								++i;
								++j;

							}

						}
						for (SummaryObservation supplyObs : summaryObser) {

							if (supplyObs.getObservationComponentDetails().equals("earthElectrodeObservations")) {

								PdfPCell eEO = new PdfPCell(new Paragraph("Earth Electrode Observation", font9));
								// eEO.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								eEO.setGrayFill(0.92f);
								obs3.addCell(eEO);

								PdfPCell eEO1 = new PdfPCell(new Paragraph(supplyObs.getObservations(), font9));
								eEO1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(eEO1);

								PdfPCell eEO11 = new PdfPCell(new Paragraph(supplyObs.getFurtherActions(), font9));
								eEO11.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(eEO11);

								PdfPCell eEO111 = new PdfPCell(new Paragraph(supplyObs.getComment(), font9));
								eEO111.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(eEO111);
							}

							if (supplyObs.getObservationComponentDetails().equalsIgnoreCase("boundingObservations")) {

								PdfPCell bCO = new PdfPCell(new Paragraph("Bonding Conductor Observation", font9));
								// bCO.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								bCO.setGrayFill(0.92f);
								obs3.addCell(bCO);

								PdfPCell bCO1 = new PdfPCell(new Paragraph(supplyObs.getObservations(), font9));
								bCO1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(bCO1);

								PdfPCell bCO11 = new PdfPCell(new Paragraph(supplyObs.getFurtherActions(), font9));
								bCO11.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(bCO11);

								PdfPCell bCO111 = new PdfPCell(new Paragraph(supplyObs.getComment(), font9));
								bCO111.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(bCO111);
							}

							if (supplyObs.getObservationComponentDetails().equalsIgnoreCase("earthingObservations")) {

								PdfPCell eCO = new PdfPCell(new Paragraph("Earthing Conductor Observation", font9));
								// eCO.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								eCO.setGrayFill(0.92f);
								obs3.addCell(eCO);

								PdfPCell eCO1 = new PdfPCell(new Paragraph(supplyObs.getObservations(), font9));
								eCO1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obs3.addCell(eCO1);

								PdfPCell eCO11 = new PdfPCell(new Paragraph(supplyObs.getFurtherActions(), font9));
								eCO11.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

								obs3.addCell(eCO11);

								PdfPCell eCO111 = new PdfPCell(new Paragraph(supplyObs.getComment(), font9));
								eCO111.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

								obs3.addCell(eCO111);

							}

						}
						document.add(obs3);

//						Inspection Observation Start from here

						PdfPTable obs2 = new PdfPTable(1);
						obs2.setWidthPercentage(100); // Width 100%
						obs2.setSpacingBefore(10f); // Space before table
						obs2.getDefaultCell().setBorder(0);

						PdfPCell InspectionOBS = new PdfPCell(new Paragraph("Inspection observations ", font5));
						InspectionOBS.setBorder(PdfPCell.NO_BORDER);
						InspectionOBS.setBackgroundColor(BaseColor.LIGHT_GRAY);
						obs2.addCell(InspectionOBS);
						document.add(obs2);

						PdfPTable Obsevation1 = new PdfPTable(pointColumnWidths);
						Obsevation1.setWidthPercentage(100); // Width 100%
						Obsevation1.setSpacingBefore(10f); // Space before table
						Obsevation1.getDefaultCell().setBorder(0);

						PdfPTable InnInspOBS = new PdfPTable(pointColumnWidths);
						InnInspOBS.setWidthPercentage(100); // Width 100%
//						InnInspOBS.setSpacingBefore(5f); // Space before table
						InnInspOBS.getDefaultCell().setBorder(0);

						PdfPTable obsInsp = new PdfPTable(4);
						obsInsp.setWidthPercentage(100); // Width 100%
						// obsInsp.setSpacingBefore(10f); // Space before table
						// obs3.getDefaultCell().setBorder(0);

						observationHead(document);

						for (SummaryObservation inspectionObs : summaryObser) {

							if (inspectionObs.getObservationComponentDetails()
									.equalsIgnoreCase("inspectionComponent")) {

								PdfPCell inspOb1111 = new PdfPCell(new Paragraph("Inspection Observations", font9));
								inspOb1111.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								inspOb1111.setGrayFill(0.92f);
								obsInsp.addCell(inspOb1111);

								PdfPCell inspOb2 = new PdfPCell(new Paragraph(inspectionObs.getObservations(), font9));
								inspOb2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obsInsp.addCell(inspOb2);

								PdfPCell inspOb3 = new PdfPCell(
										new Paragraph(inspectionObs.getFurtherActions(), font9));
								inspOb3.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obsInsp.addCell(inspOb3);

								PdfPCell inspOb4 = new PdfPCell(new Paragraph(inspectionObs.getComment(), font9));
								inspOb4.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obsInsp.addCell(inspOb4);

							}

//							if (inspectionObs.getObservationComponentDetails().equalsIgnoreCase("consumer-UnitIter")) {

							List<SummaryInnerObservation> inspectionInnerObservation = inspectionObs
									.getSummaryInnerObservation();
							int i1 = 1;
							for (SummaryInnerObservation inspectionConsumerObservation : inspectionInnerObservation) {

								PdfPCell consOb = new PdfPCell(new Paragraph("Consumer Observations- " + i1, font9));
								consOb.setHorizontalAlignment(Element.ALIGN_RIGHT);
								consOb.setGrayFill(0.92f);
								obsInsp.addCell(consOb);

								PdfPCell consOb1 = new PdfPCell(
										new Paragraph(inspectionConsumerObservation.getObservations(), font9));
								consOb1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obsInsp.addCell(consOb1);

								PdfPCell consOb2 = new PdfPCell(
										new Paragraph(inspectionConsumerObservation.getFurtherActions(), font9));
								consOb2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obsInsp.addCell(consOb2);

								PdfPCell consOb3 = new PdfPCell(
										new Paragraph(inspectionConsumerObservation.getComment(), font9));
								consOb3.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obsInsp.addCell(consOb3);

								++i1;
//								}
							}
						}
						document.add(obsInsp);

//						Testing Observation Start from here

						PdfPTable obs31 = new PdfPTable(1);
						obs31.setWidthPercentage(100); // Width 100%
						obs31.setSpacingBefore(10f); // Space before table
						obs31.getDefaultCell().setBorder(0);

						PdfPCell TestingOBS = new PdfPCell(new Paragraph("Testing observations ", font5));
						TestingOBS.setBorder(PdfPCell.NO_BORDER);
						TestingOBS.setBackgroundColor(BaseColor.LIGHT_GRAY);
						obs31.addCell(TestingOBS);
						document.add(obs31);

						PdfPTable obsTest = new PdfPTable(4);
						obsTest.setWidthPercentage(100); // Width 100%
						// obsTest.setSpacingBefore(10f); // Space before table
						// obs3.getDefaultCell().setBorder(0);

						observationHead(document);

						PdfPTable InnTestingOBS = new PdfPTable(pointColumnWidths);
						InnTestingOBS.setWidthPercentage(100); // Width 100%
						InnTestingOBS.setSpacingBefore(10f); // Space before table
						InnTestingOBS.getDefaultCell().setBorder(0);

						int i1 = 1;
						for (SummaryObservation testingObs : summaryObser) {

							if (testingObs.getObservationComponentDetails().equalsIgnoreCase("circuit")) {
								PdfPCell testOb4 = new PdfPCell(
										new Paragraph("Circuit details observation - " + i1, font9));
								// MainsOBS1.setBorder(PdfPCell.NO_BORDER);
								// testOb4.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								testOb4.setGrayFill(0.92f);
								obsTest.addCell(testOb4);

								PdfPCell testOb5 = new PdfPCell(new Paragraph(testingObs.getObservations(), font9));
								testOb5.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obsTest.addCell(testOb5);

								PdfPCell testOb6 = new PdfPCell(new Paragraph(testingObs.getFurtherActions(), font9));
								testOb6.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
								obsTest.addCell(testOb6);

								PdfPCell testOb7 = new PdfPCell(new Paragraph(testingObs.getComment(), font9));
								testOb7.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

								obsTest.addCell(testOb7);

								++i1;
							}
						}
						document.add(obsTest);

					}

					PdfPTable table16 = new PdfPTable(1);
					table16.setWidthPercentage(100); // Width 100%
					table16.setSpacingBefore(10f); // Space before table
					table16.getDefaultCell().setBorder(0);
					PdfPCell cell26 = new PdfPCell(new Paragraph("2.1: Observations classified as ", font5));
					cell26.setBorder(PdfPCell.NO_BORDER);
					cell26.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table16.addCell(cell26);
					document.add(table16);

					Paragraph paragraphThree22 = new Paragraph(
							"Code C1 “dangers present” or Code C2 “potentially dangerous” are acted upon the matter of urgency and remedial action to be taken immediately, \r\nCode C3 “Improvement recommended” should be given due consideration, \r\nCode R1 “required further investigation” should start investigation without any delay. \r\nSubject to necessary remedial action being taken, I/We recommended that the installation is further inspected and tested by :",
							font9);

					document.add(paragraphThree22);

					float[] pointColumnWidthsDate = { 14F, 190F };

					PdfPTable table11 = new PdfPTable(pointColumnWidthsDate);
					table11.setWidthPercentage(100); // Width 100%
					table11.setSpacingBefore(10f); // Space before table
					table11.setSpacingAfter(5f); // Space after table
					table11.getDefaultCell().setBorder(0);

					PdfPCell cell23 = new PdfPCell(new Paragraph(summary1.getRecommendationsDate(), font9));
					table11.addCell(new Phrase("Date:", font8));
					cell23.setBorder(PdfPCell.NO_BORDER);
					table11.addCell(cell23);
					document.add(table11);

					PdfPTable table22 = new PdfPTable(pointColumnWidths);
					table22.setSpacingBefore(10f); // Space before table
					table22.setSpacingAfter(10f); // Space after table
					table22.setWidthPercentage(100);
					table22.getDefaultCell().setBorder(0);

					PdfPTable table17 = new PdfPTable(1);
					table17.setWidthPercentage(100); // Width 100%
					table17.setSpacingBefore(5f); // Space before table
					table17.getDefaultCell().setBorder(0);

					PdfPCell cell27 = new PdfPCell(
							new Paragraph(15, "Section - 3:Summary And Conditions Of The Installation ", font5));
					cell27.setBorder(PdfPCell.NO_BORDER);
					cell27.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table17.addCell(cell27);
					document.add(table17);

					float[] pointColumnWidths1 = { 90F, 90F };

					PdfPTable table7 = new PdfPTable(pointColumnWidths1); // 3 columns.
					table7.setWidthPercentage(100); // Width 100%
					table7.setSpacingBefore(10f); // Space before table
//					table7.setSpacingAfter(10f); // Space after table
					table7.getDefaultCell().setBorder(0);

					PdfPCell cell132 = new PdfPCell(new Paragraph(
							"General condition of the installation in terms of electrical safety:", font9));
					cell132.setBorder(PdfPCell.NO_BORDER);
					cell132.setGrayFill(0.92f);
					table7.addCell(cell132);
					PdfPCell cell29 = new PdfPCell(new Paragraph(summary1.getGeneralConditionInstallation(), font9));
					cell29.setGrayFill(0.92f);
					cell29.setBorder(PdfPCell.NO_BORDER);
					table7.addCell(cell29);

					PdfPTable table8 = new PdfPTable(pointColumnWidths1); // 3 columns.
					table8.setWidthPercentage(100); // Width 100%
					table8.setSpacingBefore(10f); // Space before table
					table8.getDefaultCell().setBorder(0);

					PdfPCell cell30 = new PdfPCell(new Paragraph(summary1.getOverallAssessmentInstallation(), font9));
					cell30.setBorder(PdfPCell.NO_BORDER);
					cell30.setGrayFill(0.92f);
					PdfPCell cell31 = new PdfPCell(new Paragraph(
							"Overall assessment of the installation in terms of suitability on continuous use:",
							font9));
					cell31.setGrayFill(0.92f);
					cell31.setBorder(PdfPCell.NO_BORDER);
					table8.addCell(cell31);
					table8.addCell(cell30);
					document.add(table7);
					document.add(table8);

				}

//				document.newPage();

				PdfPTable table18 = new PdfPTable(1);
				table18.setWidthPercentage(100); // Width 100%
				table18.setSpacingBefore(10f); // Space before table
				table18.getDefaultCell().setBorder(0);

				PdfPCell cell28 = new PdfPCell(new Paragraph(15, "Section - 4:Declaration", font5));
				cell28.setBorder(PdfPCell.NO_BORDER);
				cell28.setBackgroundColor(BaseColor.LIGHT_GRAY);
				table18.addCell(cell28);
				document.add(table18);

				Font font09 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);

				Paragraph paragraph1 = new Paragraph(
						"I/we being the person responsible for the inspection & testing  of the electrical installation (as indicated by my/our signatures below), particulars of which are described in this report, having exercised reasonable skill and care when carrying out the inspection and testing, hereby declare that information in this report including the observations provides an accurate assessment of condition of electrical installation taking into account the stated extent and limitations in part 5 of this report",
						font09);

				document.add(paragraph1);

//				for (SummaryDeclaration declaration : declaration1) {

				PdfPTable table9 = new PdfPTable(2);
				table9.setWidthPercentage(100); // Width 100%
				table9.setSpacingBefore(10f); // Space before table
				table9.getDefaultCell().setBorder(0);

				addRow(table9, "Inspected and Tested  By ", "Authorized By");

				float[] pointColumnWidthsSec5 = { 40F, 90F, 40F, 90F };

				PdfPTable table = new PdfPTable(pointColumnWidthsSec5); // 3 columns.
				table.setWidthPercentage(100); // Width 100%

				PdfPTable table1 = new PdfPTable(pointColumnWidthsSec5); // 3 columns.
				table1.setWidthPercentage(100); // Width 100%

				PdfPTable table2 = new PdfPTable(pointColumnWidthsSec5); // 3 columns.
				table2.setWidthPercentage(100); // Width 100%

				// conversin code for signature in Inspeted
				String signature = declaration.getSignature();
				Base64 decoder = new Base64();
				byte[] imageByte = decoder.decode(signature);
				String s = new String(imageByte);
				String inspectedSignature_list[] = s.split(",");
				String inspectedSignature1 = inspectedSignature_list[1];
				byte[] inspetedImageByte = decoder.decode(inspectedSignature1);

				// conversion code for signature in Autherized
				String autherizedsignature = declaration11.getSignature();
				Base64 autherizeddecoder = new Base64();
				byte[] autherizedimageByte = autherizeddecoder.decode(autherizedsignature);
				String autherizedString = new String(autherizedimageByte);
				String autherizedsignature_list[] = autherizedString.split(",");
				String autherizedSignature1 = autherizedsignature_list[1];
				byte[] autherizedImageByte = decoder.decode(autherizedSignature1);

				addRow(table, "Name", declaration.getName(), "Name", declaration11.getName());
				addRow(table, "Company", declaration.getCompany(), "Company", declaration11.getCompany());
				document.add(table9);
				document.add(table);
				addRow1(table1, "Signature	", inspetedImageByte, "Signature	", autherizedImageByte);
				document.add(table1);

				addRow(table2, "Position", declaration.getPosition(), "Position", declaration11.getPosition());
				addRow(table2, "Address", declaration.getAddress(), "Address", declaration11.getAddress());
				addRow(table2, "Date", declaration.getDate(), "Date", declaration11.getDate());
				document.add(table2);

				if (comments.getViewerUserName() != null && comments.getInspectorUserName() != null) {

					document.newPage();

					PdfPTable table19911 = new PdfPTable(1);
					table19911.setSpacingBefore(10f); // Space before table
					table19911.setWidthPercentage(100);
					table19911.getDefaultCell().setBorder(0);
					PdfPCell cell6511 = new PdfPCell(
							new Paragraph(15, "Section - 6: Viewer And Inspector Comment:", font));
					cell6511.setBorder(PdfPCell.NO_BORDER);
					cell6511.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table19911.addCell(cell6511);
					document.add(table19911);

					Font font91111 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);

					float[] pointColumnWidths4 = { 90F, 90F, 90F, 90F };

					PdfPTable table440 = new PdfPTable(pointColumnWidths4);
					table440.setWidthPercentage(100); // Width 100%
					table440.setSpacingBefore(10f); // Space before table

					PdfPCell cell550 = new PdfPCell(new Paragraph(comments.getViewerUserName(), font91111));
					cell550.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell3710 = new PdfPCell(new Paragraph("ViewerUserName:", font91111));
					cell3710.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell3710.setFixedHeight(25f);
					cell3710.setGrayFill(0.92f);
					table440.addCell(cell3710);
					table440.addCell(cell550);

					PdfPCell cell3801 = new PdfPCell(new Paragraph(comments.getInspectorUserName(), font91111));
					cell3801.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell37101 = new PdfPCell(new Paragraph("InspectorUserName:", font91111));
					cell37101.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell37101.setFixedHeight(25f);
					cell37101.setGrayFill(0.92f);
					table440.addCell(cell37101);
					table440.addCell(cell3801);

					PdfPCell cell5610 = new PdfPCell(new Paragraph("ViewerComment Date:", font91111));
					cell5610.setGrayFill(0.92f);
					cell5610.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cell56110 = new PdfPCell(new Paragraph("ViewerComment:", font91111));
					cell56110.setGrayFill(0.92f);
					cell56110.setHorizontalAlignment(Element.ALIGN_CENTER);
					table440.addCell(cell56110);
					table440.addCell(cell5610);

					PdfPCell cell4010 = new PdfPCell(new Paragraph("InspectorComment Date:", font91111));
					cell4010.setGrayFill(0.92f);
					cell4010.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell cell39101 = new PdfPCell(new Paragraph("InspectorComment:", font91111));
					cell39101.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell39101.setGrayFill(0.92f);
					table440.addCell(cell39101);
					table440.addCell(cell4010);

					tableData1(table440, ReportComments);
					document.add(table440);
				}
				document.close();
				writer.close();
			} catch (Exception e) {
				logger.debug("Summary PDF creation Failed for SiteId : {}", siteId);
				throw new PdfException("Summary PDF creation Failed"); 
			}

		} else

		{
			throw new SummaryException("Invalid Inputs");
		}
	}

	private void observationHead(Document document) throws DocumentException, IOException {
		PdfPTable obsHead = new PdfPTable(4);
		obsHead.setWidthPercentage(100); // Width 100%
		obsHead.setSpacingBefore(10f); // Space before table

		Font font9 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);

		PdfPCell testOb = new PdfPCell(new Paragraph("", font9));
		// MainsOBS1.setBorder(PdfPCell.NO_BORDER);
		testOb.setGrayFill(0.92f);
		obsHead.addCell(testOb);

		PdfPCell testOb1 = new PdfPCell(new Paragraph("Observations Provided", font9));
		testOb1.setGrayFill(0.92f);
		obsHead.addCell(testOb1);

		PdfPCell testOb2 = new PdfPCell(new Paragraph("Code Classification", font9));
		testOb2.setGrayFill(0.92f);
		obsHead.addCell(testOb2);

		PdfPCell testOb3 = new PdfPCell(new Paragraph("Remedial Actions To Be Taken", font9));
		testOb3.setGrayFill(0.92f);
		obsHead.addCell(testOb3);
		document.add(obsHead);
	}

	private void tableData1(PdfPTable table440, List<SummaryComment> reportComments)
			throws DocumentException, IOException {

		Collections.sort(reportComments, new Comparator<SummaryComment>() {
			public int compare(SummaryComment periodic1, SummaryComment periodic2) {
				if (periodic1.getViewerDate() != null && periodic2.getViewerDate() != null) {
					return periodic1.getViewerDate().compareTo(periodic2.getViewerDate());
				} else {
					return 0;
				}
			}
		});

		Collections.sort(reportComments, new Comparator<SummaryComment>() {
			public int compare(SummaryComment periodic1, SummaryComment periodic2) {
				if (periodic1.getInspectorDate() != null && periodic2.getInspectorDate() != null) {
					return periodic1.getInspectorDate().compareTo(periodic2.getInspectorDate());
				} else {
					return 0;
				}
			}
		});

		for (SummaryComment arr : reportComments) {
			Font font = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
			PdfPCell cell = new PdfPCell();

			if (arr.getViewerComment() != null) {
				cell.setPhrase(new Phrase(arr.getViewerComment(), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table440.addCell(cell);
			} else {
				cell.setPhrase(new Phrase("No viewer comment available", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table440.addCell(cell);
			}

			if (arr.getViewerDate() != null) {
				cell.setPhrase(new Phrase(arr.getViewerDate().toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table440.addCell(cell);
			} else {
				cell.setPhrase(new Phrase("No viewer date available", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table440.addCell(cell);
			}

			if (arr.getInspectorComment() != null) {
				cell.setPhrase(new Phrase(arr.getInspectorComment(), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table440.addCell(cell);
			} else {
				cell.setPhrase(new Phrase("No inspector comment available", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table440.addCell(cell);
			}

			if (arr.getInspectorDate() != null) {
				cell.setPhrase(new Phrase(arr.getInspectorDate().toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table440.addCell(cell);
			} else {
				cell.setPhrase(new Phrase("No viewer date available", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table440.addCell(cell);
			}

		}
	}

	private void addRow(PdfPTable table9, String string, String string2) throws DocumentException, IOException {
		Font font8 = new Font(BaseFont.createFont(), 10, Font.BOLD, BaseColor.BLACK);
		PdfPCell nameCell = new PdfPCell(new Paragraph(string, font8));
		PdfPCell nameCell1 = new PdfPCell(new Paragraph(string2, font8));
		nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		nameCell.setGrayFill(0.92f);
		nameCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		nameCell1.setGrayFill(0.92f);
		table9.addCell(nameCell);
		table9.addCell(nameCell1);
	}

	private void addRow(PdfPTable table6, String string, String string2, String string7, String string4)
			throws DocumentException, IOException {
		Font font = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
		PdfPCell nameCell = new PdfPCell(new Paragraph(string, font));
		PdfPCell valueCell1 = new PdfPCell(new Paragraph(string2, new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		PdfPCell valueCell2 = new PdfPCell(new Paragraph(string7, font));
		PdfPCell valueCell3 = new PdfPCell(new Paragraph(string4, new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		nameCell.setGrayFill(0.92f);
		valueCell2.setGrayFill(0.92f);
		table6.addCell(nameCell);
		table6.addCell(valueCell1);
		table6.addCell(valueCell2);
		table6.addCell(valueCell3);

	}

	private void addRow1(PdfPTable table1, String string, byte[] inspetedImageByte, String string2,
			byte[] autherizedImageByte) throws MalformedURLException, IOException, DocumentException {

		Font font = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);
		PdfPCell nameCell = new PdfPCell(new Paragraph(string, font));

		Image image = Image.getInstance(inspetedImageByte);
		image.setAbsolutePosition(0, 0);
		image.scaleToFit(30, 50);

		Image Autherizedimage = Image.getInstance(autherizedImageByte);
		Autherizedimage.setAbsolutePosition(0, 0);
		Autherizedimage.scaleToFit(30, 50);
		PdfPCell valueCell1 = new PdfPCell(image);
		PdfPCell valueCell2 = new PdfPCell(new Paragraph(string2, font));
		PdfPCell valueCell3 = new PdfPCell(Autherizedimage);
		nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		valueCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		nameCell.setGrayFill(0.92f);
		valueCell2.setGrayFill(0.92f);
		table1.addCell(nameCell);
		table1.addCell(valueCell1);
		table1.addCell(valueCell2);
		table1.addCell(valueCell3);

	}

}
