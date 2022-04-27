package io.github.sterphius.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import io.github.sterphius.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesParsing {

    public static final String pathToZip = "src/test/resources/files/zip.zip",
            csvName = "research-and-development-survey-2016-2019-csv-notes.csv",
            pdfName = "sample.pdf",
            xlsxName = "sample.xlsx",
            pathToJson = "files/sample.json",
            expectedTeacherName = "Dmitrii";

    InputStream getIsByFilenameFromZip(String pathToZip, String fileName) throws Exception {
        ZipFile zipFile = new ZipFile(pathToZip);
        ZipEntry zipEntry = zipFile.getEntry(fileName);
        InputStream is = zipFile.getInputStream(zipEntry);
        return is;
    }

    @Test
    void parseCSV() throws Exception {
        try (InputStream is = getIsByFilenameFromZip(pathToZip, csvName);
             CSVReader csvReader = new CSVReader(new InputStreamReader(is))) {
            List<String[]> content = csvReader.readAll();
            assertThat(content.get(0)).contains("Footnotes");
        }
    }

    @Test
    void parsePDF() throws Exception {
        try (InputStream is = getIsByFilenameFromZip(pathToZip, pdfName)) {
            PDF pdf = new PDF(is);
            assertThat(pdf.creator).isEqualTo("Rave (http://www.nevrona.com/rave)");
        }
    }

    @Test
    void parseXLSX() throws Exception {
        try (InputStream is = getIsByFilenameFromZip(pathToZip, xlsxName)) {
            XLS xls = new XLS(is);
            assertThat(xls.excel.getSheetAt(0)
                    .getRow(1)
                    .getCell(1)
                    .getNumericCellValue()).isEqualTo(5);
        }
    }

    @Test
    void parseJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(pathToJson)) {
            Teacher teacher = mapper.readValue(is, Teacher.class);
            assertThat(teacher.name).isEqualTo(expectedTeacherName);
        }
    }

    //Over-engineering code below
    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(pdfName, "Rave (http://www.nevrona.com/rave)"),
                Arguments.of(xlsxName, 5.0),
                Arguments.of(csvName, "Footnotes")
        );
    }

    @MethodSource("testData")
    @ParameterizedTest()
    void parseFile(String fileName, Object expectedResult) throws Exception {

        ZipFile zipFile = new ZipFile(pathToZip);
        ZipEntry zipEntry = zipFile.getEntry(fileName);

        try (InputStream is = zipFile.getInputStream(zipEntry)) {

            String extension = "";

            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            } else {
                throw new Exception("Cannot separate extension from filename");
            }

            switch (extension) {
                case ("xlsx"):
                    XLS xls = new XLS(is);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(1)
                            .getCell(1)
                            .getNumericCellValue()).isEqualTo(expectedResult);
                    break;
                case ("pdf"):
                    PDF pdf = new PDF(is);
                    assertThat(pdf.creator).isEqualTo(expectedResult);
                    break;
                case ("csv"):
                    CSVReader csvReader = new CSVReader(new InputStreamReader(is));
                    List<String[]> content = csvReader.readAll();
                    assertThat(content.get(0)).contains(expectedResult.toString());
                    break;
                default:
                    throw new Exception("Unexpected case");
            }
        }
    }
}