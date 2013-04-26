package eu.jahnestacado.output.pdfgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.os.Environment;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class QLToPDF {
	private final static Font symbolFont = new Font(
			Font.FontFamily.ZAPFDINGBATS, 15, Font.BOLD);
	private final static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN,
			18, Font.UNDERLINE);
	private final static Font questionFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 14, Font.ITALIC);
	private final static Font resultsFont = new Font(
			Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private final static URL imgPath = QLToPDF.class.getResource("/eu/jahnestacado/output/pdfgenerator/images/uva_logo.jpg");
	private final static Map<String, String> boolValues;
	private final static String DIR =  Environment.getExternalStorageDirectory().getPath()+"/QL/";
	static {
		boolValues = new HashMap<String, String>();
		boolValues.put("true", "Yes");
		boolValues.put("false", "No");
	}

	private final static Map<String, String> yesOrNoSymbol;
	static {
		yesOrNoSymbol = new HashMap<String, String>();
		yesOrNoSymbol.put("true", "4"); // ** Values '4' and '6' corresponds to 'tick' and 'X' symbols of
		yesOrNoSymbol.put("false", "6"); // ** the ZAPFDINGBATS font.
	}

	private final String formName;
	private final Map<String, String> pdfContent;

	private QLToPDF(OutputState state) {
		formName = state.getFormName();
		pdfContent = state.getContent();
	}

	public static void generatePdf(OutputState state)
			throws MalformedURLException, DocumentException, IOException {
		QLToPDF generator = new QLToPDF(state);
		generator.putContent();

	}

	private void putContent() throws FileNotFoundException, DocumentException{
		checkDir();
		String path = DIR + "/" + formName + ".pdf";
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(path));
		document.open();
		Image img;
		try {
			img = Image.getInstance(imgPath);
			document.add(setHeaderLogo(img));
			document.add(setTitle(formName));
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String key : pdfContent.keySet()) {
			Paragraph p1 = new Paragraph(key, questionFont);

			p1 = getProperDisplayedValue(p1, pdfContent.get(key));
			addEmptyLine(p1, 1);
			document.add(new Paragraph(p1));
		}
		document.close();

	}

	private void checkDir() {
		boolean exists = (new File(DIR)).exists();
		if (!exists) {
			new File(DIR).mkdirs();
		}
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private static Paragraph setHeaderLogo(Image img) {
		Paragraph logoHeader = new Paragraph();
		img.scalePercent(15f);
		Chunk imgChunk = new Chunk(img, 0, 0, true);
		logoHeader.add(imgChunk);
		logoHeader.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(logoHeader, 1);
		return logoHeader;
	}

	private static Paragraph setTitle(String formName) {
		Paragraph header = new Paragraph();
		addEmptyLine(header, 1);
		Paragraph title = new Paragraph(formName + " Questionnaire", titleFont);
		header.add(title);
		addEmptyLine(header, 2);
		title.setAlignment(Element.ALIGN_CENTER);
		return header;
	}

	private Paragraph getProperDisplayedValue(Paragraph paragraph, String value) {
		paragraph.add(new Chunk("     "));
		if (boolValues.containsKey(value)) {
			paragraph.add(new Chunk(yesOrNoSymbol.get(value), symbolFont));
			paragraph.add(new Chunk("  (" + boolValues.get(value) + ")",
					resultsFont));
		} else {
			paragraph.add(new Chunk("  " + value, resultsFont));

		}
		return paragraph;

	}
	
	public static String getDir(){
		return DIR;
	}

}