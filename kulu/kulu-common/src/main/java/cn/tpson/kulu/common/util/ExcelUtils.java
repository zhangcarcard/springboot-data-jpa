package cn.tpson.kulu.common.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

	private ExcelUtils() {
		throw new AssertionError("No ExcelUtils instances for you!");
	}

	public static void export(List<String[]> rows, String[] titles, OutputStream out) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = create(workbook);
            CellStyle style = createCellStyle(workbook);

            int rowNum = 0;
            // 标题行.
            Row titleRow = createRow(sheet, rowNum++);
            for (int i = 0; i < titles.length; ++i) {
                createCell(titles[i], i, titleRow, style);
            }

            // 数据行.
            for (int j = 0; j < rows.size(); ++j) {
                Row row = createRow(sheet,j + 1);
                String[] r = rows.get(j);
                for (int k = 0; k < r.length; ++k) {
                    createCell(r[k], k, row, style);
                }
            }

            // 导出.
            workbook.write(out);
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("导出Excel出错", e);
            }
        }
    }

    public static void httpExport(List<String[]> rows, String[] titles, String filename, HttpServletResponse resp) {
        resp.setContentType("application/octet-stream");
        try (ServletOutputStream out = resp.getOutputStream()) {
            resp.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            export(rows, titles, out);
        } catch (IOException e) {
            throw new RuntimeException("无法导出报表.");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected static CellStyle createCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        return style;
    }

    protected static Sheet create(Workbook workbook) {
        Sheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(20);

        return sheet;
    }

    protected static Row createRow(Sheet sheet, int index) {
        Row row = sheet.createRow(index);
        row.setHeightInPoints(20);

        return row;
    }

    protected static Cell createCell(String value, int index, Row row, CellStyle style) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(style);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(value);

        return cell;
    }
}
