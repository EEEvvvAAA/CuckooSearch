package evg.utils;

import com.google.common.io.Resources;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Reader {

    public static String getFilePath(String fileName){
        URL url = Resources.getResource(fileName);
        return url.getPath();
    }

    public static Double[][] getData(String filePath, boolean isThreeMachineTask) throws InvalidFormatException {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);

        int n = (int)cell.getNumericCellValue();
        int m = n;
        if (isThreeMachineTask)
            m = 3;
        Double[][] matrix = new Double[m][n];


        for (int i = 1; i <= m; i++) {
            row = sheet.getRow(i);
            for (int j = 0; j < n; j++) {
                cell = row.getCell(j);
                matrix[i-1][j] = cell.getNumericCellValue();
            }

        }
        return matrix;
    }
}
