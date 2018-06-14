import java.io.*;
import java.util.*;
import java.io.File.*;
import java.util.HashMap;
import java.util.Scanner;


import org.apache.poi.xssf.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;





public class ExcelConverter {
	
	static File dir = new File("/Users/michaeledwards/Documents/ExcelConverterProject/Indiaanalyzed2018_05"); //Sets the filepath for one of the folders used to store .txt files. End program will include all three numbers.
	static List<String> fileList = new ArrayList<String>();
	static List<String> fullStateList = new ArrayList<String>();
	
	static List<String> readStates = new ArrayList<String>();
	static XSSFWorkbook workbook = new XSSFWorkbook(); //Creates a new workbook (.xlsx file)
    static XSSFSheet firstSheet = workbook.createSheet("FIRST SHEET"); //Creates a new sheet for the spreadsheet
    static XSSFRow row1 = firstSheet.createRow(0); //"Creates" a row on the first sheet
    static XSSFRow rowN = null; //For use in subsequent rows that isn't the first row that lists the parameters
    static String [] state = new String [] { //First array that stores the states as outlined by the client.
    	"ATEX", "ATI", "C", "EMO+", "EMO-" ,
    	"EV+", "EV-", "G", "IP", "L",
    	"META", "O", "OE", "P", "QF",
    	"QS", "RT", "S/G", "SJ", "SUM+",
    	"SUM-", "SUM~"
    };
    
    static String [] state2 = new String [] { //Second array that stores the states as outlined by the client.
        	"ATEX", "ATI", "C", "EMO+", "EMO-" ,
        	"EV+", "EV-", "G", "IP", "L",
        	"META", "O", "OE", "P", "QF",
        	"QS", "RT", "S/G", "SJ", "SUM+",
        	"SUM-", "SUM~"
        };
    
    static HashMap<String, Integer>  dataset = new HashMap<String, Integer>();
	
	public ExcelConverter() 
	{
		
	}//End Empty constructor

//===================================================================
	public void fileReader(String fileName) throws FileNotFoundException
	{
		
		try 
		{
			
			FileReader FILE = new FileReader(fileName);
			BufferedReader br = new BufferedReader(FILE);
			String line, data = "";
			


			while ( (line = br.readLine()) != null)       
				readStates.add(line.substring(4));//data += line.split(";", 2);

			
		
			

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
			
			if(i == 0) //Sets the very first cell in the sheet as the parameter "Name" Column
			{
				Cell cell = row1.createCell(cellNum++);
				cell.setCellValue("NAME");
			}
			
			else if(i == 1) //Sets the second cell in the sheet as the parameter "Game Type"
			{
				Cell cell = row1.createCell(cellNum++);
				cell.setCellValue("GAME TYPE");
			}
			
			else //For everything else, we'll be using the Cartesan product in order to account for all the different
				//combinations of states.
				
			{
				for(int j = 0; j < state.length; j++)
				{
					for(int k = 0; k < state2.length; k++)
					{
						Cell cell = row1.createCell(cellNum++);
						cell.setCellValue(state[j] + ";" + state[k]);
						fullStateList.add(state[j] + ";" + state[k]);
						
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
	public void compareAgainstHash()
	{
		Integer tmp = 0;
		
			for(int i = 0; i < readStates.size(); i++)
			{
				if(dataset.containsKey(readStates.get(i)))
				{
					dataset.put(readStates.get(i), tmp + 1);
				}
				
			}
		
		
	}//end compareAgainstHash
	
//===================================================================
	public List<String> getName(List<String> fileList)
	{
		List<String> nameList = new ArrayList<String>(); //create an array list named "nameList" Will use in order to store just the name from the file name.
	
		
		for(int i = 0; i < fileList.size(); i++) //Begin for loop in order to traverse the inputed fileList
		{
			nameList.add(fileList.get(i).substring(0, 4)); //Adds the first four characters within the string from fileList. These four characters appear to be the "name" consistent across all .txt files
		}//End for loop
		
		return nameList; //Returns nameList
		
	}//end getName
//===================================================================
	public void writeNamesAndGame(List<String> nameList, List<String>gameList)
	{
		int rownum = 1; //Keeps track of the row number
		
		for(int i = 0; i < nameList.size(); i++)
		{
			rowN = firstSheet.createRow(rownum); //Creates a row based off of the rownum variable
			Cell cell0 = rowN.createCell(0); //Creates a cell that will always be at position 0 on any row
			cell0.setCellValue(nameList.get(i)); //Inserts a name from nameList at position i
			
			Cell cell1 = rowN.createCell(1); //Creates a cell that will always be at position 0 on any row
			cell1.setCellValue(gameList.get(i)); //Inserts a game type from nameList at position i
			
			rownum++;
		}//end for
		
	}//end writeNamesAndGame
//===================================================================
	public List<String> getGameType(List<String> fileList)
	{
		List<String> gameList = new ArrayList<String>(); //creates a list that will store game types
		
		
		for(int i = 0; i < fileList.size(); i++) //loops through the entire fileList
		{
			String[] array = fileList.get(i).split("\\."); //Splits the string by "." and places them into an array. This will continuously initialize as it loops through
			gameList.add(array[2]); //Adds whatever is in array[2]. [2] is where the game type would be stored after splitting the String
		}
		
		return gameList; 
	}
	
//===================================================================
	public void populateHash() //Creates and populates a Hash table that associates every state with a value initialized at 0
	{
		for(int i = 0; i < fullStateList.size(); i++)
		{
			dataset.put(fullStateList.get(i), 0);
		}
	} 
	
//===================================================================

//===================================================================

	public static void main(String[] args) throws IOException {
		
		
		
		
		ExcelConverter in = new ExcelConverter();
		in.writeParams(); //write out parameters in excel sheet
		in.dirReader(); //reads directory and pulls out names and game type
		in.populateHash(); //populates has with all combinations of states
		in.fileReader("input.txt"); //reads a given filepath or individual text
		in.compareAgainstHash(); //compares what is read against the Hash table
		
		
		System.out.println(readStates + " \n");
		System.out.println("Size: " + readStates.size() + " \n");
		System.out.println(readStates.get(5));
		System.out.println("------------ \n");
		System.out.println(dataset.values());
	
		
		in.writeNamesAndGame(in.getName(fileList), in.getGameType(fileList));
		

        // To write out the workbook into a file we need to create an output
        // stream where the workbook content will be written to.
        try (FileOutputStream fos = new FileOutputStream(new File("CreateExcelDemo.xlsx"))) {
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


