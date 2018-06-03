import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;





public class ExcelConverter {
	static String[] param;
	static HSSFWorkbook workbook = new HSSFWorkbook();
    static HSSFSheet firstSheet = workbook.createSheet("FIRST SHEET");
    static HSSFRow row1 = firstSheet.createRow(0);
	
	public ExcelConverter(String FileName) throws IOException
	{
		try 
		{
			FileReader FILE = new FileReader(FileName);
			BufferedReader br = new BufferedReader(FILE);
			String line, data = "";


			while ( (line = br.readLine()) != null)       
				data += line;

			param = data.split(",");

			FILE.close();
		
		}
		
		catch(IOException e)
		{
			throw new IOException("File not found!");
		}
	}//End ExcelConverter Constructor

	
//===================================================================
	public void writeParams()
	{
		int cellNum = 0;
		
		for(int i = 0; i < param.length; i++)
		{
			Cell cell = row1.createCell(cellNum++);
			cell.setCellValue(param[i]);
			
		}
	}
	
//===================================================================

	public static void main(String[] args) throws IOException {
		
		ExcelConverter in = new ExcelConverter("input.txt");
		in.writeParams();

        // To write out the workbook into a file we need to create an output
        // stream where the workbook content will be written to.
        try (FileOutputStream fos = new FileOutputStream(new File("CreateExcelDemo.xls"))) {
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


