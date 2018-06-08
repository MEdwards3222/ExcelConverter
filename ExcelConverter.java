import java.io.*;
import java.util.*;
import java.io.File.*;


import org.apache.poi.xssf.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;





public class ExcelConverter {
	
	static File dir = new File("/Users/michaeledwards/Documents/ExcelConverterProject/Indiaanalyzed2018_05");
	static List<String> fileList = new ArrayList<String>();
	static String[] param;
	static XSSFWorkbook workbook = new XSSFWorkbook();
    static XSSFSheet firstSheet = workbook.createSheet("FIRST SHEET");
    static XSSFRow row1 = firstSheet.createRow(0);
    static String [] state = new String [] {
    	"ATEX", "ATI", "C", "EMO+", "EMO-" ,
    	"EV+", "EV-", "G", "IP", "L",
    	"META", "O", "OE", "P", "QF",
    	"QS", "RT", "S/G", "SJ", "SUM+",
    	"SUM-", "SUM~"
    };
    
    static String [] state2 = new String [] {
        	"ATEX", "ATI", "C", "EMO+", "EMO-" ,
        	"EV+", "EV-", "G", "IP", "L",
        	"META", "O", "OE", "P", "QF",
        	"QS", "RT", "S/G", "SJ", "SUM+",
        	"SUM-", "SUM~"
        };
	
	public ExcelConverter() 
	{
		
	}//End Empty constructor

//===================================================================
	public void fileReader() throws FileNotFoundException
	{
		
		try 
		{
			String FileName = "null";
			FileReader FILE = new FileReader(FileName);
			BufferedReader br = new BufferedReader(FILE);
			String line, data = "";


			while ( (line = br.readLine()) != null)       
				data += line;

			param = data.split(",");

			FILE.close();
		
		}//Placeholder until I figure proper format for provided .txt
		//Consider using substring method "https://docs.oracle.com/javase/7/docs/api/java/lang/String.html"
		
		catch(IOException e)
		{
			throw new FileNotFoundException("File not found!");
		}
 
	}//end fileReader (Incomplete)
	
	
//===================================================================
	public void writeParams() 
	{
		int cellNum = 0; //Keeps track of cell position within row
		
		for(int i = 0; i < 3; i++)
		{
			//Cell cell = row1.createCell(cellNum++);
			//cell.setCellValue(param[i]);
			
			if(i == 0)
			{
				Cell cell = row1.createCell(cellNum++);
				cell.setCellValue("NAME");
			}
			
			else if(i == 1)
			{
				Cell cell = row1.createCell(cellNum++);
				cell.setCellValue("GAME TYPE");
			}
			
			else
			{
				for(int j = 0; j < state.length; j++)
				{
					for(int k = 0; k < state2.length; k++)
					{
						Cell cell = row1.createCell(cellNum++);
						cell.setCellValue(state[j] + "&" + state[k]);
						
					}
				}
			}
			
		}
	}
	
//===================================================================
	
	public void dirReader()  //reads file in a directory and stores listed items in a List
	{
		fileList = Arrays.asList(dir.list(
				new FilenameFilter() {
					@Override public boolean accept(File dir, String name) {
						return name.endsWith(".txt");
					}
				}));
		
	}//end dirReader
//===================================================================

	public static void main(String[] args) throws IOException {
		
		ExcelConverter in = new ExcelConverter();
		in.writeParams();
		in.dirReader();
		System.out.println(fileList);

        // To write out the workbook into a file we need to create an output
        // stream where the workbook content will be written to.
        try (FileOutputStream fos = new FileOutputStream(new File("CreateExcelDemo.xlsx"))) {
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


