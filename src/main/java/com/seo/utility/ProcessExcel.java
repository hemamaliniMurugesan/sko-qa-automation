package com.seo.utility;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Read and write excel
 * Can read as rows or as table
 * @author Hemamalini
 *
 */
public class ProcessExcel 
{
	private static LinkedHashMap<String, List<HashMap<String, String>>> DATA_AS_TABLE = new LinkedHashMap<String, List<HashMap<String, String>>>();
	private static LinkedHashMap<String, ArrayList<ArrayList<String>>> DATA_AS_ROWS = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
	
	public static LinkedHashMap<String, List<HashMap<String, String>>> getLastReadExcelAsTable()
	{
		return DATA_AS_TABLE;
	}
	
	public static LinkedHashMap<String, ArrayList<ArrayList<String>>> getLastReadExcelAsRows()
	{
		return DATA_AS_ROWS;
	}
	
	/**
	 * Read excel file as rows
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static LinkedHashMap<String, ArrayList<ArrayList<String>>> readExcelFileAsRows(String path) throws IOException
	{
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		LinkedHashMap<String, ArrayList<ArrayList<String>>> data = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
		try
		{
			for(int sheet = 0; sheet < workbook.getNumberOfSheets(); sheet++)
			{
				XSSFSheet excelSheet = workbook.getSheetAt(sheet);
				int rowSize = excelSheet.getLastRowNum();
				if(rowSize > 0)
				{
					ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();//to store more than 1 rows, created arraylist and for each row have multiple elements, so it stored in arraylist.
					for(int i = 0; i <= rowSize; i++)
					{
						ArrayList<String> rowData = new ArrayList<String>();// this is for iterating elements in row
						XSSFRow excelRow =  excelSheet.getRow(i);//we getting each row in sheet
						for(int j = 0; j < excelRow.getLastCellNum(); j++)
						{
							DataFormatter formatter = new DataFormatter();
							String value = formatter.formatCellValue(excelRow.getCell(j));
							if(!value.trim().isEmpty())
							{
								rowData.add(value);
							}
						}
						if(rowData.size() > 0)
						{
							rows.add(rowData);
						}
					}
					data.put(excelSheet.getSheetName(), rows);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			workbook.close();
			DATA_AS_ROWS = data;
		}
		return data;
	}
	
	public static LinkedHashMap<String, List<HashMap<String, String>>> readExcelFileAsTable(String path) throws IOException
	{
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		try
		{
			for(int sheet = 0; sheet < workbook.getNumberOfSheets(); sheet++)
			{
				XSSFSheet excelSheet = workbook.getSheetAt(sheet);
				int rowSize = excelSheet.getLastRowNum();
				int columnSize = 0;
				if(rowSize > 1)
				{
					List<String> columns = new ArrayList<String>();
					XSSFRow headingRow =  excelSheet.getRow(0);
					columnSize = headingRow.getLastCellNum();
					for(int column = 0; column < columnSize; column++)
					{
						Cell cell = headingRow.getCell(column);
						String value = cell.getStringCellValue();
						columns.add(value);
					}
					List<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
					for(int i = 1; i <= rowSize; i++)
					{
						HashMap<String, String> rowData = new HashMap<String, String>();
						XSSFRow excelRow =  excelSheet.getRow(i);
						for(int j = 0; j < columnSize; j++)
						{
							DataFormatter formatter = new DataFormatter();
							String value = formatter.formatCellValue(excelRow.getCell(j));
							rowData.put(columns.get(j), value);
						}
						rows.add(rowData);
					}
					DATA_AS_TABLE.put(excelSheet.getSheetName(), rows);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			workbook.close();
		}
		return DATA_AS_TABLE;
	}
	
	public static boolean writeExcelFileAsRows(LinkedHashMap<String, ArrayList<ArrayList<String>>> data, String filePathToWrite, String fileName)
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		for(Entry<String, ArrayList<ArrayList<String>>> entry: data.entrySet())
		{
			String sheetName = entry.getKey();
			String sheetTabColor = "";
			if(sheetName.indexOf(Utils.DELIMITTER) >= 0)
			{
				String[] sheetAndColor = sheetName.split(Utils.DELIMITTER);
				sheetName = sheetAndColor[0];
				sheetTabColor = sheetAndColor[1];
			}
			ArrayList<ArrayList<String>> rowsData = entry.getValue();
			XSSFSheet sheet = workbook.createSheet(sheetName);
			boolean sheetHasFailedCell = false;
			for(int i = 0; i < rowsData.size(); i++)
			{
				ArrayList<String> rowData = rowsData.get(i);
				XSSFRow row = sheet.createRow(i);
				row.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
				for(int j = 0; j < rowData.size(); j++)
				{
					sheet.setColumnWidth(j, 40 * 256);
					String cellValue = rowData.get(j);
					Cell cell = row.createCell(j);
					CellStyle style = workbook.createCellStyle();
	                style.setWrapText(true);
	                cell.setCellStyle(style);
	                
	                if(cellValue.indexOf( Utils.DELIMITTER ) >= 0)
	                {
	                	String[] styles = cellValue.split(Utils.DELIMITTER);
		                cellValue = styles[0];
		                if(styles.length > 1)
		                {
		                	for(int k = 1; k < styles.length; k++)
		                	{
		                		String styleToDo = styles[k];
		                		if(styleToDo.equalsIgnoreCase("bold"))
		                		{
		                			cell = changeCellFontBold(workbook, cell);
		                		}
		                		if(styleToDo.indexOf("color") >= 0)
		                		{
		                			String color = "";
		                			if(styleToDo.equalsIgnoreCase("colorGreen"))
		                			{
		                				color = Utils.HEXA_GREEN_COLOR;
		                			}
		                			else if(styleToDo.equalsIgnoreCase("colorRed"))
		                			{
		                				color = Utils.HEXA_RED_COLOR;
		                			}
		                			cell = changeCellFontColor(workbook, cell, color);
		                		}
		                		if(styleToDo.indexOf("background") >= 0)
		                		{
		                			if(styleToDo.equalsIgnoreCase("backgroundlime"))
		                			{
		                				cell = changeBackGroundColor(cell, IndexedColors.LEMON_CHIFFON);
		                			}
		                			else if(styleToDo.equalsIgnoreCase("backgroundLT"))
		                			{
		                				cell = changeBackGroundColor(cell, IndexedColors.LIGHT_TURQUOISE);
		                			}
		                		}
		                		if(styleToDo.equalsIgnoreCase("border"))
		                		{
		                			cell = setCellBorder(cell);
		                		}
		                	}
		                }
	                }
					if(cellValue.indexOf(" - failed") >= 0)
					{
						String originalValue = cellValue.replaceAll(" - failed", "");
						cell.setCellValue(originalValue);
						errorCell(workbook, cell);
						sheetHasFailedCell = true;
					}
					else if(cellValue.indexOf(" - ignored") >= 0)
					{
						String originalValue = cellValue.replaceAll(" - ignored", "");
						cell.setCellValue(originalValue);
						ignoreCell(workbook, cell);
					}
					else if(cellValue.indexOf(" - header") >= 0)
					{
						String originalValue = cellValue.replaceAll(" - header", "");
						cell.setCellValue(originalValue);
						headerCell(workbook, cell);
					}
					else if(cellValue.indexOf(" - webElementNotFound") >= 0)
					{
						String originalValue = cellValue.replaceAll(" - webElementNotFound", "");
						cell.setCellValue(originalValue);
						webElementNotFoundCell(workbook, cell);
					}
					else
					{
						cell.setCellValue((String) cellValue);
					}
				}
			}
			if(sheetTabColor.equalsIgnoreCase("green"))
			{
				sheet.setTabColor(3);
			}
			else if(sheetTabColor.equalsIgnoreCase("red") || sheetHasFailedCell)
			{
				sheet.setTabColor(2);
			}
		}
		try (FileOutputStream outputStream = new FileOutputStream(filePathToWrite + "\\" + fileName)) {
            workbook.write(outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static Cell changeCellFontColor(XSSFWorkbook wb, Cell cell, String color) {
		CellStyle style = cell.getCellStyle();
		XSSFFont font = wb.createFont();
		font.setColor(new XSSFColor(Color.decode(color)));
		style.setFont(font);
		cell.setCellStyle(style);
		return cell;
	}
	
	public static Cell changeCellFontBold(XSSFWorkbook wb, Cell cell) {
		CellStyle style = cell.getCellStyle();
		XSSFFont font = wb.createFont();
		font.setBold(true);
		style.setFont(font);
		cell.setCellStyle(style);
		return cell;
	}
	
	public static Cell changeBackGroundColor(Cell cell, IndexedColors color)
	{
		CellStyle style = cell.getCellStyle();
		style.setFillForegroundColor(color.getIndex());
		style.setFillBackgroundColor(color.getIndex());  
        style.setFillPattern(CellStyle.BIG_SPOTS);
        cell.setCellStyle(style);
        return cell;
	}
	
	public static Cell setCellBorder(Cell cell)
	{
		CellStyle style = cell.getCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		cell.setCellStyle(style);
		return cell;
	}
	
	public static void errorCell(XSSFWorkbook wb, Cell cell) {
		CellStyle style = cell.getCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillBackgroundColor(IndexedColors.RED.getIndex());  
        style.setFillPattern(CellStyle.BIG_SPOTS);
        cell.setCellStyle(style);
	}
	
	public static void webElementNotFoundCell(XSSFWorkbook wb, Cell cell)
	{
		CellStyle style = cell.getCellStyle();
		style.setFillForegroundColor(IndexedColors.ORCHID.getIndex());
		style.setFillBackgroundColor(IndexedColors.ORCHID.getIndex());  
        style.setFillPattern(CellStyle.BIG_SPOTS);
        cell.setCellStyle(style);
	}
	public static void ignoreCell(XSSFWorkbook wb, Cell cell) {
		CellStyle style = cell.getCellStyle();
		style.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		style.setFillBackgroundColor(IndexedColors.GOLD.getIndex());  
        style.setFillPattern(CellStyle.BIG_SPOTS);
        cell.setCellStyle(style);
	}
	
	public static void headerCell(XSSFWorkbook wb, Cell cell) {
		CellStyle style = cell.getCellStyle();
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		style.setFillBackgroundColor(IndexedColors.PALE_BLUE.getIndex());  
        style.setFillPattern(CellStyle.BIG_SPOTS);
        cell.setCellStyle(style);
	}
}
