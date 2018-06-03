import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;					//org.apache.poi enables developer to create .xls files
import org.apache.poi.poifs.filesystem.POIFSFileSystem;			//through java IDEs
import org.apache.poi.ss.usermodel.Cell;





public class ExcelConverter {
	static String[] param; //array for .txt parameter content
	static HSSFWorkbook workbook = new HSSFWorkbook(); //Creates a new workbook
    static HSSFSheet firstSheet = workbook.createSheet("FIRST SHEET"); //Creates a new sheet within the workbook
    static HSSFRow row1 = firstSheet.createRow(0); //Creates the first row within the first sheet
	
	public ExcelConverter(String FileName) throws IOException //Constructor for class ExcelConverter
	{
		try 
		{
			FileReader FILE = new FileReader(FileName); //Reads file
			BufferedReader br = new BufferedReader(FILE);
			String line, data = "";


			while ( (line = br.readLine()) != null)       
				data += line;

			param = data.split(",");

			FILE.close(); //Closes input stream
		
		}
		
		catch(IOException e)
		{
			throw new IOException("File not found!");
		}
	}//End ExcelConverter Constructor

	
//===================================================================
	public void writeParams() //Method takes value from param[] and creates cells inputting data within the first row
	{			  //of the .xls sheet
		int cellNum = 0; //Keeps count of cell number
		
		for(int i = 0; i < param.length; i++)
		{
			Cell cell = row1.createCell(cellNum++); //Creates new cell along row 1, looping for the length of param
			cell.setCellValue(param[i]); //Inputs value into cell from param[i]
			
		}
	}//End write parameters
	
//===================================================================

	public static void main(String[] args) throws IOException {
		
		ExcelConverter in = new ExcelConverter("input.txt"); //Creates a new ExcelConverter
		in.writeParams(); //Writes in parameters

     
        try (FileOutputStream fos = new FileOutputStream(new File("CreateExcelDemo.xls"))) {    
	// To write out the workbook into a file we need to create an output
        // stream where the workbook content will be written to.
            workbook.write(fos);
		
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


