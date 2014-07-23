package net.zoneland.ums.common.util.helper;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * Microsoft Office excel 2003 Helper
 *
 * @author hh
 * @date Oct 13, 2010 2:28:01 PM
 */
public class MSExcelHelper {

    /**
     * 给定一个xsl 文件的路径，该函数会忽略空行，空列会添加一个空字符串代替。
     *
     * @param classPathFileName 相对于ClassPath的文件路径
     * @param sheetIndex 指定解析第几个sheet的工作区
     * @param columns 指定每行的列数，如果传入-1， 则根据内容来判断。
     * @return 将内容转换为集合
     */
    public static List<List<String>> getXlsText(String classPathFileName, int sheetIndex,
                                                int columns) throws IOException {
        InputStream is = MSExcelHelper.class.getResourceAsStream(classPathFileName);
        return readXlsText(is, sheetIndex, columns);
    }

    /**
     *  给定一个xsl InputStream，该函数会忽略空行，空列会添加一个空字符串代替。
     * @param is
     * @param sheetIndex
     * @param columns
     * @return
     * @throws IOException
     */
    public static List<List<String>> readXlsText(InputStream is, int sheetIndex, int columns)
                                                                                             throws IOException {
        List<List<String>> rows = new ArrayList<List<String>>();
        List<String> cells = null;
        HSSFWorkbook work = new HSSFWorkbook(is);

        //获取xsl的工作区数量
        int sheets = work.getNumberOfSheets(), cellLength = 0;
        if (sheets < sheetIndex) {
            return null;
        }
        //获取指定位置的工作区
        HSSFSheet sheet = work.getSheetAt(sheetIndex);
        HSSFRow row = null;
        Iterator<Row> iteRow = sheet.rowIterator();
        while (iteRow.hasNext()) {
            row = (HSSFRow) iteRow.next();
            cells = new ArrayList<String>();
            cellLength = (columns == -1) ? row.getLastCellNum() : columns;
            for (int j = 0; j <= cellLength; j++) {
                Cell cell = row.getCell(j);
                if (cell != null && HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                    //					Double d = cell.getNumericCellValue();
                    //					cells.add( d+"");
                    DecimalFormat df = new DecimalFormat("0");
                    cells.add((cell == null ? "" : df.format(cell.getNumericCellValue())));
                } else {
                    cells.add((cell == null ? "" : cell.toString()));
                }
            }
            //将解析的一行添加至集合
            rows.add(cells);
        }
        is.close();
        return rows;
    }

    /**
     * 支持2007excel
     * 
     * @param is
     * @param sheetIndex
     * @param columns
     * @return
     * @throws IOException
     */
    public static List<List<String>> readXlsText2007(InputStream is, int sheetIndex, int columns)
                                                                                                 throws IOException {
        List<List<String>> rows = new ArrayList<List<String>>();
        List<String> cells = null;
        XSSFWorkbook work = new XSSFWorkbook(is);

        //获取xsl的工作区数量
        int sheets = work.getNumberOfSheets(), cellLength = 0;
        if (sheets < sheetIndex) {
            return null;
        }
        //获取指定位置的工作区
        XSSFSheet sheet = work.getSheetAt(sheetIndex);
        XSSFRow row = null;
        Iterator<Row> iteRow = sheet.rowIterator();
        while (iteRow.hasNext()) {
            row = (XSSFRow) iteRow.next();
            cells = new ArrayList<String>();
            cellLength = (columns == -1) ? row.getLastCellNum() : columns;
            for (int j = 0; j <= cellLength; j++) {
                Cell cell = row.getCell(j);
                if (cell != null && XSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                    //                  Double d = cell.getNumericCellValue();
                    //                  cells.add( d+"");
                    DecimalFormat df = new DecimalFormat("0");
                    cells.add((cell == null ? "" : df.format(cell.getNumericCellValue())));
                } else {
                    cells.add((cell == null ? "" : cell.toString()));
                }
            }
            //将解析的一行添加至集合
            rows.add(cells);
        }
        is.close();
        return rows;
    }

    /**
     * 获取xls的第1个sheet的内容
     *
     * @see #getXlsText(String, int, int)
     *
     * @param classPathFileName xls相对于ClassPath的路径
     * @param columns 指定列数
     */
    public static List<List<String>> getXlsText(String classPathFileName, int columns)
                                                                                      throws IOException {
        return getXlsText(classPathFileName, 0, columns);
    }

    /**
     * 获取xls的第1个sheet的内容
     *
     * @see #getXlsText(String, int, int)
     * @param classPathFileName xls相对于ClassPath的路径
     */
    public static List<List<String>> getXlsText(String classPathFileName) throws IOException {
        return getXlsText(classPathFileName, 0, -1);
    }

    /**
     * 将数据写入Excel表中
     * @param fullPath 完整的路径
     * @param data 数据
     */
    public static void writeTextForXls(HSSFWorkbook workbook, List<List<Object>> data)
                                                                                      throws IOException {
        HSSFSheet sheet = workbook.createSheet();
        for (int i = 0; i < data.size(); i++) {
            HSSFRow row = sheet.createRow(i);
            List<Object> tmp = data.get(i);
            for (int j = 0; j < tmp.size(); j++) {
                setHSSFCellValue(tmp.get(j), row.createCell(j));
            }
        }
    }

    /**
     * 将数据写入Excel表中
     * @param data 数据
     */
    public static HSSFWorkbook writeTextForXls(List<List<Object>> data) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        for (int i = 0; i < data.size(); i++) {
            HSSFRow row = sheet.createRow(i);
            List<Object> tmp = data.get(i);
            for (int j = 0; j < tmp.size(); j++) {
                setHSSFCellValue(tmp.get(j), row.createCell(j));
            }
        }
        return workbook;
    }

    /**
     * 生成sheet表
     * 
     * @param workbook
     * @param data
     * @return
     * @throws IOException
     */
    public static HSSFSheet writeSheetTextForxls(HSSFWorkbook workbook, List<List<Object>> data)
                                                                                                throws IOException {
        HSSFSheet sheet = workbook.createSheet();
        for (int i = 0; i < data.size(); i++) {
            HSSFRow row = sheet.createRow(i);
            List<Object> tmp = data.get(i);
            for (int j = 0; j < tmp.size(); j++) {
                setHSSFCellValue(tmp.get(j), row.createCell(j));
            }
        }
        return sheet;
    }

    /**
     * 设置单元格的参数值
     *
     * @param value
     * @param cell
     */
    private static void setHSSFCellValue(Object value, HSSFCell cell) {
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(value == null ? "" : value.toString()));
    }

    //测试
    public static void main(String[] args) throws Exception {
        System.out.println(getXlsText("/user.xls"));
        System.out.println(getXlsText("/user.xls", 8)); //指定每行的列数为8列
    }
}
