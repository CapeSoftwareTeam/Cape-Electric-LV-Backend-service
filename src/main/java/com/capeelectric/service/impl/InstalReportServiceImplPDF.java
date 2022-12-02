package com.capeelectric.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capeelectric.exception.InstalReportException;
import com.capeelectric.exception.PdfException;
import com.capeelectric.model.ReportDetails;
import com.capeelectric.model.ReportDetailsComment;
import com.capeelectric.model.SignatorDetails;
import com.capeelectric.model.Site;
import com.capeelectric.model.SitePersons;
import com.capeelectric.repository.SiteRepository;
import com.capeelectric.service.InstalReportPDFService;
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
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class InstalReportServiceImplPDF implements InstalReportPDFService {

	private static final Logger logger = LoggerFactory.getLogger(InstalReportServiceImplPDF.class);

	@Autowired
	private SiteRepository siteRepository;

	public List<ReportDetails> printBasicInfromation(String userName, Integer siteId,
			Optional<ReportDetails> reportDetailsRepo) throws InstalReportException, PdfException {

		logger.debug("called printBasicInfromation function userName: {},siteId : {}", userName, siteId);

		if (userName != null && !userName.isEmpty() && siteId != null && siteId != 0) {

			Document document = new Document(PageSize.A4, 36, 36, 50, 36);
			document.setMargins(68, 68, 62, 68);

			try {
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PrintInstalReportData.pdf"));

				document.open();

				Font font14B = new Font(BaseFont.createFont(), 14, Font.NORMAL | Font.BOLD, BaseColor.BLACK);
				Paragraph certificate1 = new Paragraph("TESTING, INSPECTION & CERTIFICATION", font14B);
				certificate1.setAlignment(Element.ALIGN_CENTER);
				document.add(certificate1);

				Paragraph certificate2 = new Paragraph("(TIC)", font14B);
				certificate2.setAlignment(Element.ALIGN_CENTER);
				document.add(certificate2);

				Font font9 = new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD, BaseColor.BLACK);
				Paragraph certificate3 = new Paragraph("LOW VOLTAGE ELECTRICAL INSTALLATION)", font9);
				certificate3.setAlignment(Element.ALIGN_CENTER);
				document.add(certificate3);

				Font font10 = new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.NORMAL, BaseColor.BLACK);
				Paragraph certificate4 = new Paragraph(25,
						"As per IEC 60364-6 (IS732 code of practice of electrical wiring installations)", font10);
				certificate4.setAlignment(Element.ALIGN_CENTER);
				document.add(certificate4);

				Paragraph gap1 = new Paragraph(40, " ", font9);
				document.add(gap1);

				Font font10N = new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.NORMAL, BaseColor.BLACK);
				Paragraph certificate5 = new Paragraph(
						"I/we hereby certify that the low voltage electrical installation of M/S .......................................................... at premise ....................................................................... has been verified following the rules prescribed in "
								+ "IEC 60364 - 6 and IS 732:2019,"
								+ " based on the verification report no  .................................................... dt  .........  ..........  .................  subject to the extent and limitations, observations, recommendations, and summary included in the test report part5. It is recommended that this certificate to be read along with full test report.",
						font10N);
				certificate5.setAlignment(Element.ALIGN_LEFT);
				document.add(certificate5);

				Paragraph gap2 = new Paragraph(20, " ", font9);
				document.add(gap2);

				Font fonta = new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.NORMAL, BaseColor.BLACK);
				Paragraph certificate6 = new Paragraph("Name:", fonta);
				document.add(certificate6);

				Font gapFont1 = new Font(BaseFont.createFont(), 5, Font.ITALIC | Font.NORMAL, BaseColor.BLACK);
				Paragraph gap5 = new Paragraph(" ", gapFont1);
				document.add(gap5);

				Font fonta1 = new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.NORMAL, BaseColor.BLACK);
				Paragraph certificate7 = new Paragraph("Designation:", fonta1);
				document.add(certificate7);

				Paragraph gap3 = new Paragraph(" ", gapFont1);
				document.add(gap3);

				Paragraph certificate8 = new Paragraph("Organization:", fonta1);
				document.add(certificate8);

				PdfPTable tableIEC = new PdfPTable(1);
				tableIEC.setSpacingAfter(20f); // Space after table
				tableIEC.setSpacingBefore(20f); // Space before table
				tableIEC.setWidthPercentage(100);

				Font font22 = new Font(BaseFont.createFont(), 10, Font.NORMAL, BaseColor.BLACK);

				PdfPCell cell100 = new PdfPCell(new Phrase("IEC 60364-6 (IS732:6) \r\n"
						+ "Initial / periodic verification of a new / existing installation up to 1000 V AC and 1500 V DC)\r\n"
						+ "", font22));
				cell100.setGrayFill(0.82f);
				cell100.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableIEC.addCell(cell100);
				document.add(tableIEC);

				document.newPage();

				List<Site> siteDetails = siteRepository.findBysiteId(siteId);
				Site siteInformation1 = siteDetails.get(0);

				Set<SitePersons> sitePersonDetails = siteInformation1.getSitePersons();
				List<SitePersons> convertion1 = new ArrayList<>(sitePersonDetails);
//				SitePersons sitepersons = convertion1.get(0);

//				Optional<ReportDetails> reportDetails = installationReportRepository.findBySiteId(siteId);
				ReportDetails report = reportDetailsRepo.get();

				Set<SignatorDetails> signatureDetails = report.getSignatorDetails();
				List<SignatorDetails> convertion = new ArrayList<>(signatureDetails);

				List<ReportDetailsComment> reportComments = report.getReportDetailsComment();
				ReportDetailsComment comments = reportComments.get(0);

				document.newPage();

				Font font12B = new Font(BaseFont.createFont(), 12, Font.NORMAL | Font.BOLD, BaseColor.BLACK);
				Paragraph paragraph1 = new Paragraph("TIC of LV electrical installation", font12B);
				paragraph1.setAlignment(Element.ALIGN_CENTER);
				document.add(paragraph1);

				float[] pointColumnWidths10 = { 100F };
				PdfPTable part1 = new PdfPTable(pointColumnWidths10);

				part1.setWidthPercentage(100); // Width 100%
				part1.setSpacingBefore(10f); // Space before table
//				table0.setSpacingAfter(10f); // Space after table
				part1.setWidthPercentage(100);
				part1.getDefaultCell().setBorder(0);

				PdfPCell basic = new PdfPCell(new Paragraph("Part - 1: Basic Information",
						new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD)));
				basic.setBackgroundColor(new GrayColor(0.82f));
				basic.setHorizontalAlignment(Element.ALIGN_LEFT);
				basic.setBorder(PdfPCell.NO_BORDER);
				part1.addCell(basic);
				document.add(part1);

				PdfPTable Section1 = new PdfPTable(pointColumnWidths10);

				Section1.setWidthPercentage(100); // Width 100%
				Section1.setSpacingBefore(10f); // Space before table
				Section1.setSpacingAfter(5f); // Space after table
				Section1.setWidthPercentage(100);
				Section1.getDefaultCell().setBorder(0);

				PdfPCell Description = new PdfPCell(new Paragraph("Section - 1: Description and extent of installation",
						new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD)));
				Description.setBackgroundColor(new GrayColor(0.82f));
				Description.setHorizontalAlignment(Element.ALIGN_LEFT);
				Description.setBorder(PdfPCell.NO_BORDER);
				Section1.addCell(Description);
				document.add(Section1);

				PdfPTable Details = new PdfPTable(pointColumnWidths10);

				Details.setWidthPercentage(100); // Width 100%
				Details.setSpacingBefore(5f); // Space before table
				Details.setSpacingAfter(5f); // Space after table
				Details.setWidthPercentage(100);
				Details.getDefaultCell().setBorder(0);

				PdfPCell client = new PdfPCell(new Paragraph("Details of the client ",
						new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD)));
				client.setBackgroundColor(new GrayColor(0.82f));
				client.setHorizontalAlignment(Element.ALIGN_LEFT);
				client.setBorder(PdfPCell.NO_BORDER);
				Details.addCell(client);
				document.add(Details);

				float[] pointColumnWidths = { 90F, 90F };

				PdfPTable table0 = new PdfPTable(pointColumnWidths);

				table0.setWidthPercentage(100); // Width 100%
//				table0.setSpacingBefore(5f); // Space before table
				table0.setWidthPercentage(100);
				table0.getDefaultCell().setBorder(0);

				for (Site siteInformation : siteDetails) {

					PdfPCell site1 = new PdfPCell(new Paragraph(siteInformation.getCompanyName(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					table0.addCell(new Phrase("Client name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site1.setHorizontalAlignment(Element.ALIGN_LEFT);
					site1.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site1);

					PdfPCell site60 = new PdfPCell(
							new Paragraph("Department", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site60.setBackgroundColor(new GrayColor(0.93f));
					site60.setHorizontalAlignment(Element.ALIGN_LEFT);
					site60.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site60);
					PdfPCell site2 = new PdfPCell(new Paragraph(siteInformation.getDepartmentName(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site2.setHorizontalAlignment(Element.ALIGN_LEFT);
					site2.setBackgroundColor(new GrayColor(0.93f));
					site2.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site2);

					PdfPCell site3 = new PdfPCell(
							new Paragraph(siteInformation.getSite(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					table0.addCell(new Phrase("Site Name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site3.setHorizontalAlignment(Element.ALIGN_LEFT);
					site3.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site3);

				}

				for (SitePersons sitepersons : convertion1) {

					PdfPCell site61 = new PdfPCell(
							new Paragraph("Person In-charge:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site61.setBackgroundColor(new GrayColor(0.93f));
					site61.setHorizontalAlignment(Element.ALIGN_LEFT);
					site61.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site61);
					PdfPCell site4 = new PdfPCell(new Paragraph(sitepersons.getPersonIncharge(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site4.setHorizontalAlignment(Element.ALIGN_LEFT);
					site4.setBackgroundColor(new GrayColor(0.93f));
					site4.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site4);

					PdfPCell site5 = new PdfPCell(new Paragraph(sitepersons.getContactNo(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					table0.addCell(new Phrase("Contact No:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				site5.setFixedHeight(30f);
					site5.setHorizontalAlignment(Element.ALIGN_LEFT);
					site5.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site5);

					PdfPCell site62 = new PdfPCell(
							new Paragraph("Mail id:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site62.setBackgroundColor(new GrayColor(0.93f));
					site62.setHorizontalAlignment(Element.ALIGN_LEFT);
					site62.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site62);
					PdfPCell site6 = new PdfPCell(new Paragraph(sitepersons.getPersonInchargeEmail(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site6.setBackgroundColor(new GrayColor(0.93f));
					site6.setHorizontalAlignment(Element.ALIGN_LEFT);
					site6.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site6);

				}

				for (Site siteInformation : siteDetails) {

					PdfPCell site7 = new PdfPCell(new Paragraph(siteInformation.getAddressLine_1(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					table0.addCell(new Phrase("Site Address line1:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				site7.setFixedHeight(30f);
					site7.setHorizontalAlignment(Element.ALIGN_LEFT);
					site7.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site7);

					PdfPCell site63 = new PdfPCell(
							new Paragraph("Site Address line2:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site63.setBackgroundColor(new GrayColor(0.93f));
					site63.setHorizontalAlignment(Element.ALIGN_LEFT);
					site63.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site63);
					PdfPCell site8 = new PdfPCell(new Paragraph(siteInformation.getAddressLine_2(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site8.setHorizontalAlignment(Element.ALIGN_LEFT);
					site8.setBackgroundColor(new GrayColor(0.93f));
					site8.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site8);

					PdfPCell site64 = new PdfPCell(new Paragraph(siteInformation.getState(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					table0.addCell(new Phrase("State:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				    site64.setFixedHeight(30f);
					site64.setHorizontalAlignment(Element.ALIGN_LEFT);
					site64.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site64);

					PdfPCell pin = new PdfPCell(
							new Paragraph("Pin code:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					pin.setBackgroundColor(new GrayColor(0.93f));
					pin.setHorizontalAlignment(Element.ALIGN_LEFT);
					pin.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(pin);
					PdfPCell site11 = new PdfPCell(new Paragraph(siteInformation.getZipCode(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					site11.setHorizontalAlignment(Element.ALIGN_LEFT);
					site11.setBackgroundColor(new GrayColor(0.93f));
					site11.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site11);

					PdfPCell site65 = new PdfPCell(new Paragraph(siteInformation.getCountry(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					table0.addCell(new Phrase("Country:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				site65.setFixedHeight(30f);
					site65.setHorizontalAlignment(Element.ALIGN_LEFT);
					site65.setBorder(PdfPCell.NO_BORDER);
					table0.addCell(site65);
					document.add(table0);

				}

				PdfPTable table1 = new PdfPTable(pointColumnWidths);

				table1.setWidthPercentage(100); // Width 100%
//				table1.setSpacingBefore(10f); // Space before table
				table1.setSpacingAfter(10f); // Space after table
				table1.setWidthPercentage(100);
				table1.getDefaultCell().setBorder(0);

				PdfPCell Report = new PdfPCell(
						new Paragraph("Description of Report:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				Report.setBackgroundColor(new GrayColor(0.93f));
				Report.setHorizontalAlignment(Element.ALIGN_LEFT);
				Report.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(Report);
				PdfPCell cell = new PdfPCell(
						new Paragraph(report.getDescriptionReport(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(new GrayColor(0.93f));
				cell.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(cell);

				PdfPCell site66 = new PdfPCell(
						new Paragraph(report.getReasonOfReport(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				table1.addCell(new Phrase("Reason for this report:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				site66.setFixedHeight(30f);
				site66.setHorizontalAlignment(Element.ALIGN_LEFT);
				site66.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(site66);

				PdfPCell Type = new PdfPCell(
						new Paragraph("Type of installation:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				Type.setBackgroundColor(new GrayColor(0.93f));
				Type.setHorizontalAlignment(Element.ALIGN_LEFT);
				Type.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(Type);
				PdfPCell cell2 = new PdfPCell(
						new Paragraph(report.getInstallationType(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setBackgroundColor(new GrayColor(0.93f));
				cell2.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(cell2);

				PdfPCell site67 = new PdfPCell(new Paragraph(report.getDescriptionPremise(),
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				table1.addCell(
						new Phrase("Description of the premise:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				site67.setFixedHeight(30f);
				site67.setHorizontalAlignment(Element.ALIGN_LEFT);
				site67.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(site67);

				PdfPCell age = new PdfPCell(new Paragraph("Estimated age of the wiring system years:",
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				age.setBackgroundColor(new GrayColor(0.93f));
				age.setHorizontalAlignment(Element.ALIGN_LEFT);
				age.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(age);
				PdfPCell cell4 = new PdfPCell(
						new Paragraph(report.getEstimatedWireAge(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setBackgroundColor(new GrayColor(0.93f));
				cell4.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(cell4);

				PdfPCell site68 = new PdfPCell(
						new Paragraph(report.getEvidanceAddition(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				table1.addCell(new Phrase("Evidence of addition / alterations:",
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				site68.setFixedHeight(30f);
				site68.setHorizontalAlignment(Element.ALIGN_LEFT);
				site68.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(site68);

				if (report.getEvidanceAddition().equalsIgnoreCase("Yes")) {
					PdfPCell estimated = new PdfPCell(new Paragraph("If yes estimated age years:",
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					estimated.setBackgroundColor(new GrayColor(0.93f));
					estimated.setHorizontalAlignment(Element.ALIGN_LEFT);
					estimated.setBorder(PdfPCell.NO_BORDER);
					table1.addCell(estimated);
					PdfPCell cell6 = new PdfPCell(new Paragraph(report.getEvidanceWireAge(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell6.setBackgroundColor(new GrayColor(0.93f));
					cell6.setBorder(PdfPCell.NO_BORDER);
					table1.addCell(cell6);
				}

				PdfPCell site69 = new PdfPCell(
						new Paragraph(report.getPreviousRecords(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				table1.addCell(
						new Phrase("Previous records available:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				site69.setFixedHeight(30f);
				site69.setHorizontalAlignment(Element.ALIGN_LEFT);
				site69.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(site69);

				if (report.getPreviousRecords().equalsIgnoreCase("Yes")) {
					PdfPCell Last = new PdfPCell(new Paragraph("Last date of inspection:",
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					Last.setBackgroundColor(new GrayColor(0.93f));
					Last.setHorizontalAlignment(Element.ALIGN_LEFT);
					Last.setBorder(PdfPCell.NO_BORDER);
					table1.addCell(Last);
					PdfPCell cell8 = new PdfPCell(new Paragraph(report.getLastInspection(),
							new Font(BaseFont.createFont(), 10, Font.NORMAL)));
					cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell8.setBackgroundColor(new GrayColor(0.93f));
					cell8.setBorder(PdfPCell.NO_BORDER);
					table1.addCell(cell8);
				}

				PdfPCell site70 = new PdfPCell(new Paragraph(report.getExtentInstallation(),
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				table1.addCell(new Phrase("Extent of installation covered by this report:",
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				site70.setFixedHeight(30f);
				site70.setHorizontalAlignment(Element.ALIGN_LEFT);
				site70.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(site70);

				PdfPCell person = new PdfPCell(new Paragraph("Details of client / person ordering this report:",
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				person.setBackgroundColor(new GrayColor(0.93f));
				person.setHorizontalAlignment(Element.ALIGN_LEFT);
				person.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(person);
				PdfPCell cell10 = new PdfPCell(
						new Paragraph(report.getClientDetails(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				cell10.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell10.setBackgroundColor(new GrayColor(0.93f));
				cell10.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(cell10);

				PdfPCell Designation = new PdfPCell(
						new Paragraph("Designation:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				Designation.setBackgroundColor(new GrayColor(0.93f));
				Designation.setHorizontalAlignment(Element.ALIGN_LEFT);
				Designation.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(Designation);
				PdfPCell cell14 = new PdfPCell(
						new Paragraph(report.getDesignation(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				cell14.setHorizontalAlignment(Element.ALIGN_LEFT);
//				cell14.setBackgroundColor(new GrayColor(0.93f));
				cell14.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(cell14);

				PdfPCell company = new PdfPCell(
						new Paragraph("Company:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				company.setBackgroundColor(new GrayColor(0.93f));
				company.setHorizontalAlignment(Element.ALIGN_LEFT);
				company.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(company);
				PdfPCell company1 = new PdfPCell(
						new Paragraph(report.getCompany(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				company1.setHorizontalAlignment(Element.ALIGN_LEFT);
				company1.setBackgroundColor(new GrayColor(0.93f));
				company1.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(company1);

				PdfPCell site72 = new PdfPCell(
						new Paragraph(report.getVerifiedEngineer(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				table1.addCell(new Phrase("Name of engineer who carries out verification:",
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				site72.setFixedHeight(30f);
				site72.setHorizontalAlignment(Element.ALIGN_LEFT);
				site72.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(site72);

				PdfPCell Designation1 = new PdfPCell(
						new Paragraph("Designation:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				Designation1.setBackgroundColor(new GrayColor(0.93f));
				Designation1.setHorizontalAlignment(Element.ALIGN_LEFT);
				Designation1.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(Designation1);
				PdfPCell cell104 = new PdfPCell(new Paragraph(report.getInspectorDesignation(),
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				cell104.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell104.setBackgroundColor(new GrayColor(0.93f));
				cell104.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(cell104);

				PdfPCell site73 = new PdfPCell(new Paragraph(report.getInspectorCompanyName(),
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				table1.addCell(new Phrase("Company:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				site73.setFixedHeight(30f);
				site73.setHorizontalAlignment(Element.ALIGN_LEFT);
				site73.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(site73);

				PdfPCell site71 = new PdfPCell(new Paragraph("Details of installation referred in this report:",
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				site71.setBackgroundColor(new GrayColor(0.93f));
				site71.setHorizontalAlignment(Element.ALIGN_LEFT);
				site71.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(site71);
				PdfPCell cell102 = new PdfPCell(new Paragraph(report.getInstallationDetails(),
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				cell102.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell102.setBackgroundColor(new GrayColor(0.93f));
				cell102.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(cell102);

				PdfPCell Date = new PdfPCell(new Paragraph("Date of starting the verification:",
						new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//				Date.setBackgroundColor(new GrayColor(0.93f));
				Date.setHorizontalAlignment(Element.ALIGN_LEFT);
				Date.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(Date);
				PdfPCell cell12 = new PdfPCell(
						new Paragraph(report.getVerificationDate(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
//				cell12.setBackgroundColor(new GrayColor(0.93f));
				cell12.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(cell12);

				PdfPCell Read = new PdfPCell(
						new Paragraph("Read and confirmed the extent and limitations (part 5, section 1):",
								new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				Read.setBackgroundColor(new GrayColor(0.93f));
				Read.setHorizontalAlignment(Element.ALIGN_LEFT);
				Read.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(Read);
				PdfPCell cell16 = new PdfPCell(
						new Paragraph(report.getLimitations(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
				cell16.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell16.setBackgroundColor(new GrayColor(0.93f));
				cell16.setBorder(PdfPCell.NO_BORDER);
				table1.addCell(cell16);
				document.add(table1);

				document.newPage();

				PdfPTable section2 = new PdfPTable(pointColumnWidths10);
				section2.setWidthPercentage(100); // Width 100%
				section2.setSpacingBefore(5f); // Space before table
				section2.setSpacingAfter(5f); // Space after table
				section2.setWidthPercentage(100);
				section2.getDefaultCell().setBorder(0);

				PdfPCell Liability = new PdfPCell(new Paragraph("Section - 2: Liability and declaration",
						new Font(BaseFont.createFont(), 11, Font.BOLD)));
				Liability.setBackgroundColor(new GrayColor(0.82f));
				Liability.setHorizontalAlignment(Element.ALIGN_LEFT);
				Liability.setBorder(PdfPCell.NO_BORDER);
				section2.addCell(Liability);
				document.add(section2);

				PdfPTable Dsection2 = new PdfPTable(pointColumnWidths10);
				Dsection2.setWidthPercentage(100); // Width 100%
				Dsection2.setSpacingBefore(5f); // Space before table
				Dsection2.setSpacingAfter(8f); // Space after table
				Dsection2.getDefaultCell().setBorder(0);

				PdfPCell Declaration = new PdfPCell(
						new Paragraph("Declaration of designer of the electrical installation",
								new Font(BaseFont.createFont(), 10, Font.BOLD)));
				Declaration.setBackgroundColor(new GrayColor(0.82f));
//				Declaration.setFixedHeight(30f);
				Declaration.setHorizontalAlignment(Element.ALIGN_LEFT);
				Declaration.setBorder(PdfPCell.NO_BORDER);
				Dsection2.addCell(Declaration);
				document.add(Dsection2);

//				Paragraph paragraph10 = new Paragraph(20,"Declaration of designer of the electrical installation", font11B);
//				document.add(paragraph10);

				Font noteFont = new Font(BaseFont.createFont(), 8, Font.NORMAL | Font.NORMAL, BaseColor.BLACK);
				Paragraph paragraph11 = new Paragraph(
						"I/we being the person responsible for the design of the electrical installation (as indicated by my/our signatures below), particulars of which are described above, having exercised reasonable skill and care when carrying out the design hereby declare that design work for which I/We have been responsible is to the best of my/our knowledge and belief in accordance with IEC 60364. The extent of liability of the signatory or signatories is limited to the work described above as the subject of this report.",
						noteFont);
				document.add(paragraph11);

				Paragraph paragraph12 = new Paragraph("Designer: 1", font10N);
				document.add(paragraph12);

				for (SignatorDetails arr : convertion) {
					if (arr.getSignatorRole().equalsIgnoreCase("designer1")) {
						declarationDesigner(document, arr);
					} else if (arr.getSignatorRole().equalsIgnoreCase("designer2")) {
						Paragraph paragraph13 = new Paragraph("Designer: 2", font10N);
						document.add(paragraph13);
						declarationDesigner2(document, arr);
					}
				}

				PdfPTable DeclarationC = new PdfPTable(pointColumnWidths10);
				DeclarationC.setWidthPercentage(100); // Width 100%
				DeclarationC.setSpacingBefore(8f); // Space before table
				DeclarationC.setSpacingAfter(8f); // Space after table
				DeclarationC.setWidthPercentage(100);
				DeclarationC.getDefaultCell().setBorder(0);

				PdfPCell contactor = new PdfPCell(new Paragraph("Declaration of contractor of installation",
						new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD)));
				contactor.setBackgroundColor(new GrayColor(0.82f));
//				contactor.setFixedHeight(30f);
				contactor.setHorizontalAlignment(Element.ALIGN_LEFT);
				contactor.setBorder(PdfPCell.NO_BORDER);
				DeclarationC.addCell(contactor);
				document.add(DeclarationC);

//				Paragraph gap7 = new Paragraph(" ", gapFont1);
//				document.add(gap7);

				Paragraph paragraph15 = new Paragraph(
						"I/we being the person responsible for the construction of the electrical installation (as indicated by my/our signatures below), particulars of which are described above, having exercised reasonable skill and care when carrying out the design hereby declare that the design work for which I/We have been responsible is to the best of my/our knowledge and belief in accordance with IEC 60364. The extent of liability of the signatory is limited to the work described above as the subject of this report.",
						noteFont);
				document.add(paragraph15);

				Paragraph paragraph16 = new Paragraph("Contractor ", font10N);
				document.add(paragraph16);

				for (SignatorDetails arr : convertion) {
					if (arr.getSignatorRole().equalsIgnoreCase("contractor")) {
						declarationContractor(document, arr);
					}
				}

				PdfPTable DeclarationI = new PdfPTable(pointColumnWidths10);
				DeclarationI.setWidthPercentage(100); // Width 100%
				DeclarationI.setSpacingBefore(8f); // Space before table
				DeclarationI.setSpacingAfter(8f); // Space after table
				DeclarationI.setWidthPercentage(100);
				DeclarationI.getDefaultCell().setBorder(0);

				PdfPCell Inspector = new PdfPCell(new Paragraph("Declaration of Safety Engineer (Inspector)",
						new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD)));
				Inspector.setBackgroundColor(new GrayColor(0.82f));
//				Inspector.setFixedHeight(30f);
				Inspector.setHorizontalAlignment(Element.ALIGN_LEFT);
				Inspector.setBorder(PdfPCell.NO_BORDER);
				DeclarationI.addCell(Inspector);
				document.add(DeclarationI);

//				Paragraph gap8 = new Paragraph(" ", gapFont1);
//				document.add(gap8);

				Paragraph paragraph18 = new Paragraph(
						"I/we being the person responsible for the inspection & testing  of the electrical installation (as indicated by my/our signatures below), particulars of which are described above, having exercised reasonable skill and care when carrying out the design hereby declare that the design work for which I/We have been responsible is to the best of my/our knowledge and belief in accordance with IEC 60364. The extent of liability of the signatory is limited to the work described above as the subject of this report.",
						noteFont);
				document.add(paragraph18);

				Paragraph paragraph19 = new Paragraph("Inspector", font10N);
				document.add(paragraph19);

				for (SignatorDetails arr : convertion) {
					if (arr.getSignatorRole().equalsIgnoreCase("inspector")) {
						declarationInspector(document, arr);
					}
				}

				PdfPTable NextInspection = new PdfPTable(pointColumnWidths10);
				NextInspection.setWidthPercentage(100); // Width 100%
				NextInspection.setSpacingBefore(8f); // Space before table
				NextInspection.setSpacingAfter(5f); // Space after table
				NextInspection.setWidthPercentage(100);
				NextInspection.getDefaultCell().setBorder(0);

				PdfPCell section3 = new PdfPCell(new Paragraph("Section - 3: Next inspection",
						new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD)));
				section3.setBackgroundColor(new GrayColor(0.82f));
//				section3.setFixedHeight(30f);
				section3.setHorizontalAlignment(Element.ALIGN_LEFT);
				section3.setBorder(PdfPCell.NO_BORDER);
				NextInspection.addCell(section3);
				document.add(NextInspection);

				Paragraph paragraph22 = new Paragraph(
						"I/we recommend that this installation is further inspected and tested after an interval of not more than__"
								+ report.getNextInspection() + "__years",
						font10N);
				document.add(paragraph22);

				PdfPTable signatories = new PdfPTable(pointColumnWidths10);
				signatories.setWidthPercentage(100); // Width 100%
				signatories.setSpacingBefore(10f); // Space before table
				signatories.setSpacingAfter(5f); // Space after table
				signatories.setWidthPercentage(100);
				signatories.getDefaultCell().setBorder(0);

				PdfPCell section4 = new PdfPCell(new Paragraph("Section - 4: Details of the Designers",
						new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD)));
				section4.setBackgroundColor(new GrayColor(0.82f));
//				section4.setFixedHeight(30f);
				section4.setHorizontalAlignment(Element.ALIGN_LEFT);
				section4.setBorder(PdfPCell.NO_BORDER);
				signatories.addCell(section4);
				document.add(signatories);

				PdfPTable designer = new PdfPTable(pointColumnWidths10);
				designer.setWidthPercentage(100); // Width 100%
				designer.setSpacingBefore(5f); // Space before table
				designer.setSpacingAfter(5f); // Space after table
				designer.setWidthPercentage(100);
				designer.getDefaultCell().setBorder(0);

				PdfPCell designer1 = new PdfPCell(
						new Paragraph("Designer - 1", new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD)));
				designer1.setBackgroundColor(new GrayColor(0.82f));
//				designer1.setFixedHeight(30f);
				designer1.setHorizontalAlignment(Element.ALIGN_LEFT);
				designer1.setBorder(PdfPCell.NO_BORDER);
				designer.addCell(designer1);
				document.add(designer);

				for (SignatorDetails arr : convertion) {

					if (arr.getSignatorRole().equalsIgnoreCase("designer1")) {
						designer(document, arr);
					}
					else 
						if (arr.getSignatorRole().equalsIgnoreCase("designer2")) {
							if (arr.getSignatorStatus() != null && !arr.getSignatorStatus().equalsIgnoreCase("R")) {
							PdfPTable designer2 = new PdfPTable(pointColumnWidths10);
							designer2.setWidthPercentage(100); // Width 100%
							designer2.setSpacingBefore(5f); // Space before table
							designer2.setSpacingAfter(5f); // Space after table
							designer2.getDefaultCell().setBorder(0);

							PdfPCell ddesigner = new PdfPCell(new Paragraph("Designer - 2",
									new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD)));
							ddesigner.setBackgroundColor(new GrayColor(0.82f));
//			    	        ddesigner.setFixedHeight(30f);
							ddesigner.setHorizontalAlignment(Element.ALIGN_LEFT);
							ddesigner.setBorder(PdfPCell.NO_BORDER);
							designer2.addCell(ddesigner);
							document.add(designer2);

							designer(document, arr);

						}
					}
				}

				PdfPTable contractor = new PdfPTable(pointColumnWidths10);
				contractor.setWidthPercentage(100); // Width 100%
				contractor.setSpacingBefore(5f); // Space before table
				contractor.setSpacingAfter(5f); // Space after table
				contractor.setWidthPercentage(100);
				contractor.getDefaultCell().setBorder(0);

				PdfPCell section5 = new PdfPCell(new Paragraph("Section - 5: Details of the Contractors",
						new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD)));
				section5.setBackgroundColor(new GrayColor(0.82f));
//				section5.setFixedHeight(30f);
				section5.setHorizontalAlignment(Element.ALIGN_LEFT);
				section5.setBorder(PdfPCell.NO_BORDER);
				contractor.addCell(section5);
				document.add(contractor);

				PdfPTable contractor0 = new PdfPTable(pointColumnWidths10);
				contractor0.setWidthPercentage(100); // Width 100%
				contractor0.setSpacingBefore(5f); // Space before table
				contractor0.setSpacingAfter(5f); // Space after table
				contractor0.getDefaultCell().setBorder(0);

				PdfPCell section5C = new PdfPCell(
						new Paragraph("Contractor", new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD)));
				section5C.setBackgroundColor(new GrayColor(0.82f));
//				section5C.setFixedHeight(30f);
				section5C.setHorizontalAlignment(Element.ALIGN_LEFT);
				section5C.setBorder(PdfPCell.NO_BORDER);
				contractor0.addCell(section5C);
				document.add(contractor0);

				for (SignatorDetails arr : convertion) {
					if (arr.getSignatorRole().equalsIgnoreCase("contractor")) {
						contractor(document, arr);
					}
				}

				PdfPTable inspector = new PdfPTable(pointColumnWidths10);
				inspector.setWidthPercentage(100); // Width 100%
				inspector.setSpacingBefore(5f); // Space before table
				inspector.setSpacingAfter(5f); // Space after table
				inspector.setWidthPercentage(100);
				inspector.getDefaultCell().setBorder(0);

				PdfPCell section6 = new PdfPCell(new Paragraph("Section - 6: Details of Inspector",
						new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD)));
				section6.setBackgroundColor(new GrayColor(0.82f));
//				section6.setFixedHeight(30f);
				section6.setHorizontalAlignment(Element.ALIGN_LEFT);
				section6.setBorder(PdfPCell.NO_BORDER);
				inspector.addCell(section6);
				document.add(inspector);

				PdfPTable inspector0 = new PdfPTable(pointColumnWidths10);
				inspector0.setWidthPercentage(100); // Width 100%
				inspector0.setSpacingBefore(5f); // Space before table
				inspector0.setSpacingAfter(5f); // Space after table
				inspector0.setWidthPercentage(100);
				inspector0.getDefaultCell().setBorder(0);

				PdfPCell section6I = new PdfPCell(
						new Paragraph("Inspector", new Font(BaseFont.createFont(), 10, Font.NORMAL | Font.BOLD)));
				section6I.setBackgroundColor(new GrayColor(0.82f));
//				section6I.setFixedHeight(30f);
				section6I.setHorizontalAlignment(Element.ALIGN_LEFT);
				section6I.setBorder(PdfPCell.NO_BORDER);
				inspector0.addCell(section6I);
				document.add(inspector0);

				for (SignatorDetails arr : convertion) {
					if (arr.getSignatorRole().equalsIgnoreCase("inspector")) {
						inspector(document, arr);
					}
				}

				if (comments.getViewerUserName() != null && comments.getInspectorUserName() != null) {

					document.newPage();

					PdfPTable table199 = new PdfPTable(1);
					table199.setWidthPercentage(100); // Width 100%
					table199.setSpacingBefore(10f); // Space before table
					table199.setWidthPercentage(100);
					table199.getDefaultCell().setBorder(0);

					Font font = new Font(BaseFont.createFont(), 11, Font.NORMAL | Font.BOLD);
					PdfPCell cell65 = new PdfPCell(new Paragraph("Section - 7: Viewer And Inspector Comment:", font));
					cell65.setBorder(PdfPCell.NO_BORDER);
					cell65.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table199.addCell(cell65);
					document.add(table199);
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
					cell371.setFixedHeight(25f);
					cell371.setGrayFill(0.92f);
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
				throw new PdfException("BasicInfromation PDF creation failed"); 
			}

		} else

		{
			throw new InstalReportException("Invalid Inputs");
		}
		return null;
	}

	private void tableData(PdfPTable table44, List<ReportDetailsComment> reportComments)
			throws DocumentException, IOException {

		Collections.sort(reportComments, new Comparator<ReportDetailsComment>() {
			public int compare(ReportDetailsComment periodic1, ReportDetailsComment periodic2) {
				if (periodic1.getViewerDate() != null && periodic2.getViewerDate() != null) {
					return periodic1.getViewerDate().compareTo(periodic2.getViewerDate());
				} else {
					return 0;
				}
			}
		});

		Collections.sort(reportComments, new Comparator<ReportDetailsComment>() {
			public int compare(ReportDetailsComment periodic1, ReportDetailsComment periodic2) {
				if (periodic1.getInspectorDate() != null && periodic2.getInspectorDate() != null) {
					return periodic1.getInspectorDate().compareTo(periodic2.getInspectorDate());
				} else {
					return 0;
				}
			}
		});

		for (ReportDetailsComment arr : reportComments) {

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

	private void declarationDesigner2(Document document, SignatorDetails observation)
			throws DocumentException, IOException {
		PdfPTable table2 = new PdfPTable(6); // 6 columns.
		table2.setWidthPercentage(100); // Width 100%
		table2.setSpacingBefore(10f); // Space before table
		table2.setSpacingAfter(10f); // Space after table
		table2.setWidthPercentage(100);
		table2.getDefaultCell().setBorder(0);

		String signature = observation.getDeclarationSignature();
		Base64 decoder = new Base64();
		byte[] imageByte1 = decoder.decode(signature);
		String s = new String(imageByte1);
		String signature_list[] = s.split(",");
		String sL = signature_list[0];
		String sL1 = signature_list[1];
		byte[] imageByte = decoder.decode(sL1);

		addRow1(table2, "Signature:", imageByte, "Date:", observation.getDeclarationDate(), "Name:",
				observation.getDeclarationName());
		document.add(table2);

	}

	private void designer2(Document document, SignatorDetails observation) throws DocumentException, IOException {
		float[] pointColumnWidths5 = { 60F, 90F };
		PdfPTable table6 = new PdfPTable(pointColumnWidths5);

		table6.setWidthPercentage(100); // Width 100%
//		table6.setSpacingBefore(10f); // Space before table
//		table6.setSpacingAfter(10f); // Space after table
		table6.setWidthPercentage(100);
		table6.getDefaultCell().setBorder(0);

		PdfPCell cell31 = new PdfPCell(
				new Paragraph(observation.getPersonName(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table6.addCell(new Phrase("Person name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell31.setFixedHeight(30f);
		cell31.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell31.setBorder(PdfPCell.NO_BORDER);
		table6.addCell(cell31);

		PdfPCell cell32 = new PdfPCell(
				new Paragraph(observation.getPersonContactNo(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table6.addCell(new Phrase("Contact no:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell32.setFixedHeight(30f);
		cell32.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell32.setBorder(PdfPCell.NO_BORDER);
		table6.addCell(cell32);

		PdfPCell cell33 = new PdfPCell(
				new Paragraph(observation.getPersonMailID(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table6.addCell(new Phrase("Mail ID:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell33.setFixedHeight(30f);
		cell33.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell33.setBorder(PdfPCell.NO_BORDER);
		table6.addCell(cell33);
		document.add(table6);
	}

	private void declarationInspector(Document document, SignatorDetails observation6)
			throws DocumentException, IOException {
		PdfPTable table4 = new PdfPTable(6); // 6 columns.
		table4.setWidthPercentage(100); // Width 100%
		table4.setSpacingBefore(10f); // Space before table
		table4.setSpacingAfter(10f); // Space after table
		table4.setWidthPercentage(100);
		table4.getDefaultCell().setBorder(0);

		String signature = observation6.getDeclarationSignature();
		Base64 decoder = new Base64();
		byte[] imageByte1 = decoder.decode(signature);
		String s = new String(imageByte1);
		String signature_list[] = s.split(",");
		String sL = signature_list[0];
		String sL1 = signature_list[1];
		byte[] imageByte = decoder.decode(sL1);

		addRow1(table4, "Signature: ", imageByte, "Date: ", observation6.getDeclarationDate(), "Name: ",
				observation6.getDeclarationName());
		document.add(table4);
	}

	private void declarationContractor(Document document, SignatorDetails observation5)
			throws DocumentException, IOException {
		PdfPTable table3 = new PdfPTable(6); // 6 columns.
		table3.setWidthPercentage(100); // Width 100%
		table3.setSpacingBefore(10f); // Space before table
		table3.setSpacingAfter(10f); // Space after table
		table3.setWidthPercentage(100);
		table3.getDefaultCell().setBorder(0);

		String signature = observation5.getDeclarationSignature();
		Base64 decoder = new Base64();
		byte[] imageByte1 = decoder.decode(signature);
		String s = new String(imageByte1);
		String signature_list[] = s.split(",");
		String sL = signature_list[0];
		String sL1 = signature_list[1];
		byte[] imageByte = decoder.decode(sL1);

		addRow1(table3, "Signature: ", imageByte, "Date: ", observation5.getDeclarationDate(), "Name: ",
				observation5.getDeclarationName());
		document.add(table3);
	}

	private void declarationDesigner(Document document, SignatorDetails observation)
			throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(6); // 6 columns.
		table.setWidthPercentage(100); // Width 100%
		table.setSpacingBefore(10f); // Space before table
		table.setSpacingAfter(10f); // Space after table
		table.setWidthPercentage(100);
		table.getDefaultCell().setBorder(0);

		String signature = observation.getDeclarationSignature();
		Base64 decoder = new Base64();
		byte[] imageByte1 = decoder.decode(signature);
		String s = new String(imageByte1);
		String signature_list[] = s.split(",");
		String sL = signature_list[0];
		String sL1 = signature_list[1];
		byte[] imageByte = decoder.decode(sL1);

		addRow1(table, "Signature: ", imageByte, "Date: ", observation.getDeclarationDate(), "Name: ",
				observation.getDeclarationName());

		document.add(table);

	}

	private void addRow1(PdfPTable table, String string1, byte[] imageByte, String string3, String string4,
			String string5, String string6) throws DocumentException, IOException {

		Image image = Image.getInstance(imageByte);
		image.setAbsolutePosition(0, 0);
		image.scaleToFit(30, 50);

		PdfPCell nameCell = new PdfPCell(new Paragraph(string1, new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		PdfPCell valueCell1 = new PdfPCell(image);
		PdfPCell valueCell2 = new PdfPCell(new Paragraph(string3, new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		PdfPCell valueCell3 = new PdfPCell(new Paragraph(string4, new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		PdfPCell valueCell4 = new PdfPCell(new Paragraph(string5, new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		PdfPCell valueCell5 = new PdfPCell(new Paragraph(string6, new Font(BaseFont.createFont(), 10, Font.NORMAL)));

		nameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		nameCell.setBorder(0);
//		nameCell.setBackgroundColor(new GrayColor(0.85f));

		valueCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		// valueCell1.setBorder(0);
//		valueCell1.setBackgroundColor(new GrayColor(0.85f));

		valueCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		valueCell2.setBorder(0);
//		valueCell2.setBackgroundColor(new GrayColor(0.85f));

		valueCell3.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		valueCell3.setBorder(0);
//		valueCell3.setBackgroundColor(new GrayColor(0.85f));

		valueCell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
		valueCell4.setBorder(0);
//		valueCell4.setBackgroundColor(new GrayColor(0.85f));

		valueCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
		valueCell5.setBorder(0);
//		valueCell5.setBackgroundColor(new GrayColor(0.85f));

		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		table.addCell(nameCell);
		table.addCell(valueCell1);
		table.addCell(valueCell2);
		table.addCell(valueCell3);
		table.addCell(valueCell4);
		table.addCell(valueCell5);

	}

	private void designer(Document document, SignatorDetails observation) throws DocumentException, IOException {
		float[] pointColumnWidths1 = { 60F, 90F };
		PdfPTable table5 = new PdfPTable(pointColumnWidths1);

		table5.setWidthPercentage(100); // Width 100%
//		table5.setSpacingBefore(5f); // Space before table
//		table5.setSpacingAfter(5f); // Space after table
		table5.setWidthPercentage(100);
		table5.getDefaultCell().setBorder(0);

		PdfPCell cell17 = new PdfPCell(
				new Paragraph(observation.getPersonName(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table5.addCell(new Phrase("Person name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell17.setFixedHeight(30f);
		cell17.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell17.setBorder(PdfPCell.NO_BORDER);
		table5.addCell(cell17);

		PdfPCell cell18 = new PdfPCell(
				new Paragraph(observation.getPersonContactNo(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table5.addCell(new Phrase("Contact no:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell18.setFixedHeight(30f);
		cell18.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell18.setBorder(PdfPCell.NO_BORDER);
		table5.addCell(cell18);

		PdfPCell cell19 = new PdfPCell(
				new Paragraph(observation.getPersonMailID(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table5.addCell(new Phrase("Mail ID:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell19.setFixedHeight(30f);
		cell19.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell19.setBorder(PdfPCell.NO_BORDER);
		table5.addCell(cell19);

		PdfPCell cell21 = new PdfPCell(
				new Paragraph(observation.getManagerName(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table5.addCell(new Phrase("Manager name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell21.setFixedHeight(30f);
		cell21.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell21.setBorder(PdfPCell.NO_BORDER);
		table5.addCell(cell21);

		if (observation.getManagerContactNo() != null && !observation.getManagerContactNo().isEmpty()) {
			PdfPCell cell22 = new PdfPCell(
					new Paragraph(observation.getManagerContactNo(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			table5.addCell(new Phrase("Contact no:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//	     	cell22.setFixedHeight(30f);
			cell22.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell22.setBorder(PdfPCell.NO_BORDER);
			table5.addCell(cell22);
		}

		PdfPCell cell23 = new PdfPCell(
				new Paragraph(observation.getManagerMailID(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table5.addCell(new Phrase("Mail ID:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell23.setFixedHeight(30f);
		cell23.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell23.setBorder(PdfPCell.NO_BORDER);
		table5.addCell(cell23);

		PdfPCell cell24 = new PdfPCell(
				new Paragraph(observation.getCompanyName(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table5.addCell(new Phrase("Company name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell24.setFixedHeight(30f);
		cell24.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell24.setBorder(PdfPCell.NO_BORDER);
		table5.addCell(cell24);

		PdfPCell cell25 = new PdfPCell(
				new Paragraph(observation.getAddressLine1(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table5.addCell(new Phrase("Address line1:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		cell25.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell25.setBorder(PdfPCell.NO_BORDER);
		table5.addCell(cell25);

		if (observation.getAddressLine2() != null && !observation.getAddressLine2().isEmpty()) {
			PdfPCell cell26 = new PdfPCell(
					new Paragraph(observation.getAddressLine2(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			table5.addCell(new Phrase("Address line2:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			cell26.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell26.setBorder(PdfPCell.NO_BORDER);
			table5.addCell(cell26);
		}

		if (observation.getLandMark() != null && !observation.getLandMark().isEmpty()) {
			PdfPCell cell27 = new PdfPCell(
					new Paragraph(observation.getLandMark(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			table5.addCell(new Phrase("Landmark:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			cell27.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell27.setBorder(PdfPCell.NO_BORDER);
			table5.addCell(cell27);
		}

		PdfPCell cell28 = new PdfPCell(
				new Paragraph(observation.getState(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table5.addCell(new Phrase("State:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell28.setFixedHeight(30f);
		cell28.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell28.setBorder(PdfPCell.NO_BORDER);
		table5.addCell(cell28);

		PdfPCell cell29 = new PdfPCell(
				new Paragraph(observation.getPinCode(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table5.addCell(new Phrase("Pin code:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell29.setFixedHeight(30f);
		cell29.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell29.setBorder(PdfPCell.NO_BORDER);
		table5.addCell(cell29);

		PdfPCell cell30 = new PdfPCell(
				new Paragraph(observation.getCountry(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table5.addCell(new Phrase("Country:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell30.setFixedHeight(30f);
		cell30.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell30.setBorder(PdfPCell.NO_BORDER);
		table5.addCell(cell30);
		document.add(table5);
	}

	private void contractor(Document document, SignatorDetails observation2) throws DocumentException, IOException {
		float[] pointColumnWidths2 = { 60F, 90F, };
		PdfPTable table7 = new PdfPTable(pointColumnWidths2);

		table7.setWidthPercentage(100); // Width 100%
//		table7.setSpacingBefore(10f); // Space before table
//		table7.setSpacingAfter(10f); // Space after table
		table7.setWidthPercentage(100);
		table7.getDefaultCell().setBorder(0);

		PdfPCell cell34 = new PdfPCell(
				new Paragraph(observation2.getPersonName(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table7.addCell(new Phrase("Person name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell34.setFixedHeight(30f);
		cell34.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell34.setBorder(PdfPCell.NO_BORDER);
		table7.addCell(cell34);

		PdfPCell cell35 = new PdfPCell(
				new Paragraph(observation2.getPersonContactNo(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table7.addCell(new Phrase("Contact no:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell35.setFixedHeight(30f);
		cell35.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell35.setBorder(PdfPCell.NO_BORDER);
		table7.addCell(cell35);

		PdfPCell cell36 = new PdfPCell(
				new Paragraph(observation2.getPersonMailID(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table7.addCell(new Phrase("Mail ID:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell36.setFixedHeight(30f);
		cell36.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell36.setBorder(PdfPCell.NO_BORDER);
		table7.addCell(cell36);

		PdfPCell cell37 = new PdfPCell(
				new Paragraph(observation2.getManagerName(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table7.addCell(new Phrase("Manager name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		cell37.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell37.setBorder(PdfPCell.NO_BORDER);
		table7.addCell(cell37);

		if (observation2.getManagerContactNo() != null && !observation2.getManagerContactNo().isEmpty()) {
			PdfPCell cell38 = new PdfPCell(new Paragraph(observation2.getManagerContactNo(),
					new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			table7.addCell(new Phrase("Contact no:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			cell38.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell38.setBorder(PdfPCell.NO_BORDER);
			table7.addCell(cell38);
		}

		PdfPCell cell39 = new PdfPCell(
				new Paragraph(observation2.getManagerMailID(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table7.addCell(new Phrase("Mail ID:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		cell39.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell39.setBorder(PdfPCell.NO_BORDER);
		table7.addCell(cell39);

		PdfPCell cell40 = new PdfPCell(
				new Paragraph(observation2.getCompanyName(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table7.addCell(new Phrase("Company name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell40.setFixedHeight(30f);
		cell40.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell40.setBorder(PdfPCell.NO_BORDER);
		table7.addCell(cell40);

		PdfPCell cell41 = new PdfPCell(
				new Paragraph(observation2.getAddressLine1(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table7.addCell(new Phrase("Address line1:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		cell41.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell41.setBorder(PdfPCell.NO_BORDER);
		table7.addCell(cell41);

		if (observation2.getAddressLine2() != null && !observation2.getAddressLine2().isEmpty()) {
			PdfPCell cell42 = new PdfPCell(
					new Paragraph(observation2.getAddressLine2(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			table7.addCell(new Phrase("Address line2:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			cell42.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell42.setBorder(PdfPCell.NO_BORDER);
			table7.addCell(cell42);
		}

		if (observation2.getLandMark() != null && !observation2.getLandMark().isEmpty()) {
			PdfPCell cell43 = new PdfPCell(
					new Paragraph(observation2.getLandMark(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			table7.addCell(new Phrase("Landmark:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			cell43.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell43.setBorder(PdfPCell.NO_BORDER);
			table7.addCell(cell43);
		}

		PdfPCell cell44 = new PdfPCell(
				new Paragraph(observation2.getState(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table7.addCell(new Phrase("State:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell44.setFixedHeight(30f);
		cell44.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell44.setBorder(PdfPCell.NO_BORDER);
		table7.addCell(cell44);

		PdfPCell cell45 = new PdfPCell(
				new Paragraph(observation2.getPinCode(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table7.addCell(new Phrase("Pin code:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell45.setFixedHeight(30f);
		cell45.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell45.setBorder(PdfPCell.NO_BORDER);
		table7.addCell(cell45);

		PdfPCell cell46 = new PdfPCell(
				new Paragraph(observation2.getCountry(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table7.addCell(new Phrase("Country:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell46.setFixedHeight(30f);
		cell46.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell46.setBorder(PdfPCell.NO_BORDER);
		table7.addCell(cell46);
		document.add(table7);
	}

	private void inspector(Document document, SignatorDetails observation3) throws DocumentException, IOException {
		float[] pointColumnWidths3 = { 60F, 90F };
		PdfPTable table8 = new PdfPTable(pointColumnWidths3);

		table8.setWidthPercentage(100); // Width 100%
//		table8.setSpacingBefore(10f); // Space before table
//		table8.setSpacingAfter(10f); // Space after table
		table8.setWidthPercentage(100);
		table8.getDefaultCell().setBorder(0);

		PdfPCell cell47 = new PdfPCell(
				new Paragraph(observation3.getPersonName(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table8.addCell(new Phrase("Person name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell47.setFixedHeight(30f);
		cell47.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell47.setBorder(PdfPCell.NO_BORDER);
		table8.addCell(cell47);

		PdfPCell cell48 = new PdfPCell(
				new Paragraph(observation3.getPersonContactNo(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table8.addCell(new Phrase("Contact no:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell48.setFixedHeight(30f);
		cell48.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell48.setBorder(PdfPCell.NO_BORDER);
		table8.addCell(cell48);

		PdfPCell cell49 = new PdfPCell(
				new Paragraph(observation3.getPersonMailID(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table8.addCell(new Phrase("Mail ID:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell49.setFixedHeight(30f);
		cell49.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell49.setBorder(PdfPCell.NO_BORDER);
		table8.addCell(cell49);

		PdfPCell cell50 = new PdfPCell(
				new Paragraph(observation3.getManagerName(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table8.addCell(new Phrase("Manager name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell50.setFixedHeight(30f);
		cell50.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell50.setBorder(PdfPCell.NO_BORDER);
		table8.addCell(cell50);

		if (observation3.getManagerContactNo() != null && !observation3.getManagerContactNo().isEmpty()) {
			PdfPCell cell51 = new PdfPCell(new Paragraph(observation3.getManagerContactNo(),
					new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			table8.addCell(new Phrase("Contact no:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			cell51.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell51.setBorder(PdfPCell.NO_BORDER);
			table8.addCell(cell51);
		}

		PdfPCell cell52 = new PdfPCell(
				new Paragraph(observation3.getManagerMailID(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table8.addCell(new Phrase("Mail ID:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell52.setFixedHeight(30f);
		cell52.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell52.setBorder(PdfPCell.NO_BORDER);
		table8.addCell(cell52);

		PdfPCell cell53 = new PdfPCell(
				new Paragraph(observation3.getCompanyName(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table8.addCell(new Phrase("Company name:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell53.setFixedHeight(30f);
		cell53.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell53.setBorder(PdfPCell.NO_BORDER);
		table8.addCell(cell53);

		PdfPCell cell54 = new PdfPCell(
				new Paragraph(observation3.getAddressLine1(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table8.addCell(new Phrase("Address line1:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell54.setFixedHeight(30f);
		cell54.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell54.setBorder(PdfPCell.NO_BORDER);
		table8.addCell(cell54);

		if (observation3.getAddressLine2() != null && !observation3.getAddressLine2().isEmpty()) {
			PdfPCell cell55 = new PdfPCell(
					new Paragraph(observation3.getAddressLine2(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			table8.addCell(new Phrase("Address line2:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			cell55.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell55.setBorder(PdfPCell.NO_BORDER);
			table8.addCell(cell55);
		}

		if (observation3.getLandMark() != null && !observation3.getLandMark().isEmpty()) {
			PdfPCell cell56 = new PdfPCell(
					new Paragraph(observation3.getLandMark(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			table8.addCell(new Phrase("Landmark:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
			cell56.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell56.setBorder(PdfPCell.NO_BORDER);
			table8.addCell(cell56);
		}

		PdfPCell cell57 = new PdfPCell(
				new Paragraph(observation3.getState(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table8.addCell(new Phrase("State:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell57.setFixedHeight(30f);
		cell57.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell57.setBorder(PdfPCell.NO_BORDER);
		table8.addCell(cell57);

		PdfPCell cell58 = new PdfPCell(
				new Paragraph(observation3.getPinCode(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table8.addCell(new Phrase("Pin code:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell58.setFixedHeight(30f);
		cell58.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell58.setBorder(PdfPCell.NO_BORDER);
		table8.addCell(cell58);

		PdfPCell cell59 = new PdfPCell(
				new Paragraph(observation3.getCountry(), new Font(BaseFont.createFont(), 10, Font.NORMAL)));
		table8.addCell(new Phrase("Country:", new Font(BaseFont.createFont(), 10, Font.NORMAL)));
//		cell59.setFixedHeight(30f);
		cell59.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell59.setBorder(PdfPCell.NO_BORDER);
		table8.addCell(cell59);
		document.add(table8);
	}

}