package cn.com.fintheircing.admin.useritem.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReadExcelUtil {
    private static Logger logger = LoggerFactory.getLogger(ReadExcelUtil.class);
    public static Map<Integer, Map<Integer,Object>> readExcelContentz(MultipartFile file) throws Exception{

        Map<Integer, Map<Integer, Object>> content = new HashMap<Integer, Map<Integer, Object>>();
        // 上传文件名
        Workbook wb = getWb(file);
        if (wb == null) {
            throw new Exception("Workbook对象为空！");
        }
        Sheet sheet = wb.getSheetAt(0);

        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        //获得第一行
        Row row = sheet.getRow(0);
        //第一行列的个数
        int colNum = row.getLastCellNum();
        // 正文内容应该从第二行开始,第一行为表头的标题
        int b= 0;
        for (int i = 1; i <= rowNum; i++) {
            //第二行
            row = sheet.getRow(i);

            //列
            int j = 0;
            int a=0;
            Map<Integer, Object> cellValue = new HashMap<Integer, Object>();
            while (j < colNum ) {
                //获得第i+1的第J列
                Object obj = getCellFormatValue(row.getCell(j));
                    j++;
                    cellValue.put(a, obj);
                    a++;
            }
            content.put(b, cellValue);
            b++;

        }
        return content;
    }
    //根据Cell类型设置数据
    private static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                case Cell.CELL_TYPE_FORMULA: {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换时间格式
                        Date date = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cellvalue = formater.format(date);
//                        cellvalue = date;
                    } else {
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    private static Workbook getWb(MultipartFile mf){
        String filepath = mf.getOriginalFilename();
        String ext = filepath.substring(filepath.lastIndexOf("."));
//        String contentType = mf.getContentType();
        Workbook wb = null;
        try {
            InputStream is = mf.getInputStream();
            if(".xls".equals(ext)){
                wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext)){
                wb = new XSSFWorkbook(is);
            }else{
                wb=null;
            }
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        }
        return wb;
    }

}
