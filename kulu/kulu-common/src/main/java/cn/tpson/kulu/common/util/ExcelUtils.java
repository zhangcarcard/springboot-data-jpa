package cn.tpson.kulu.common.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    private ExcelUtils() {
        throw new AssertionError("No ExcelUtils instances for you!");
    }

    /**
     *
     * @param sheetName
     * @param rows
     * @param out
     */
    public static void write(String sheetName, List<String[]> rows, OutputStream out) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);
            sheet.setDefaultColumnWidth(20);
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);//水平居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);

            int rowNum = 0;
            for (String[] r : rows) {
                String[] fields = r;
                Row row = sheet.createRow(rowNum++);
                row.setHeightInPoints(20);

                int cellNum = 0;
                for (String field : fields) {
                    Cell cell = row.createCell(cellNum++);
                    cell.setCellStyle(style);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(field);
                }
            }

            workbook.write(out);
        } catch (Exception e) {
            if (log.isErrorEnabled()) log.error("导出Excel出错", e);
        }
    }

    public static List<String[]> read(InputStream in, boolean xlsx) {
        final List<String[]> rows = new ArrayList(32);
        try (Workbook workbook = xlsx ? new XSSFWorkbook(in) : new HSSFWorkbook(in)) {
            Sheet sheet = workbook.getSheetAt(0);
            sheet.forEach(row -> {
                final String[] r = new String[row.getLastCellNum()];
                row.forEach(cell -> {
                    cell.setCellType(CellType.STRING);
                    r[cell.getColumnIndex()] = cell.getStringCellValue();
                });
                rows.add(r);
            });
        } catch (Exception e) {
            if (log.isErrorEnabled())log.error("读取Excel出错", e);
        }

        return rows;
    }
}
