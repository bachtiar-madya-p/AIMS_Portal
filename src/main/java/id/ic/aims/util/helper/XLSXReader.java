package id.ic.aims.util.helper;

import id.ic.aims.util.property.Property;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import id.ic.aims.manager.PropertyManager;
import id.ic.aims.util.log.AppLogger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XLSXReader {

    private static XLSXReader instance;

    private AppLogger log = new AppLogger(this.getClass());

    private XLSXReader() {
        String methodName = "Constructor";
        start(methodName);

        String filePath = PropertyManager.getInstance().getProperty(Property.COUNTRY_XLS_FILE);
        log.debug(methodName, "Country xls file location : " + filePath);

        log.debug(methodName, "Build nationality map to memory");
        MyInfoNationalityMap.buildNationalityMap(mapToList(filePath, 0, 0, 1));

        log.debug(methodName, "Build country map to memory");
        MyInfoCountiesMap.buildCountiesMap(mapToList(filePath, 1, 0, 1));

        completed(methodName);
    }
    private Map<String, String> mapToList(String filePath, int sheetIndex, int sourceColIndex, int resultColIndex) {
        String methodName = "mapToList";
        start(methodName);
        Map<String, String> result = new HashMap<>();
        XSSFWorkbook wb = null;
        try {

            wb = new XSSFWorkbook(new File(filePath));
            XSSFSheet sheet = wb.getSheetAt(sheetIndex);

            for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);
                if (row != null && row.getCell(0) != null) {
                    result.put(row.getCell(sourceColIndex).getStringCellValue(),
                            row.getCell(resultColIndex).getStringCellValue());

                }
            }
        } catch (IOException | InvalidFormatException e) {
            log.error(methodName, e.getMessage());
        } finally {
            try {
                assert wb != null;
                wb.close();
            } catch (IOException e) {
                log.error(methodName, e.getMessage());
            }
        }
        completed(methodName);
        return result;
    }

    protected void start(String methodName) {
        log.debug(methodName, "Start");
    }

    protected void completed(String methodName) {
        log.debug(methodName, "Completed");
    }

    public static XLSXReader getInstance() {
        if (instance == null) {
            instance = new XLSXReader();
        }
        return instance;
    }
}
