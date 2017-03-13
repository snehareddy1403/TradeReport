package com.jpm.fx.tradereport.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jpm.fx.tradereport.model.TradeInstruction;
import com.jpm.fx.tradereport.validator.TradeValidator;

/**
 * Reads data from csv file.
 * returns list of TradeInstructions
 *
 *@return List<TradeInstruction>
 */
public class FileReaderUtil {

	private static final Logger logger = Logger.getLogger("");
	public static String FILE_NAME = "tradeData.csv";
	
	/**
	 * Reads all the records from csv file into TradeInstruction
	 * @return List<TradeInstruction>
	 */
	public static List<TradeInstruction> processInputFile() {
		
		List<TradeInstruction> inputList = new ArrayList<TradeInstruction>();
		BufferedReader reader = null;
		try{
			
			InputStream in = FileReaderUtil.class.getResourceAsStream(FILE_NAME); 
			 reader = new BufferedReader(new InputStreamReader(in));
			inputList = reader.lines().skip(1).map(mapToItem).collect(Collectors.toList());
			
		}  finally {
			try {
				if(reader != null)
					reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inputList ;
	}

	/**
	 * Map columns to fields in object
	 */
	private static Function<String, TradeInstruction> mapToItem = (line) -> {
		String[] p = line.split(",");
		TradeInstruction item = new TradeInstruction();
		item.setEntity(p[0]);
		item.setDirection(p[1]);
		try {
			item.setRate(Double.valueOf(p[2]));
		} catch(Exception e) {
			item.setRate(null);
		}
		item.setCurrency(p[3]);
		item.setInstructionDate(p[4]);
		item.setSettlementDate(p[5]);
		try {
			item.setNoOfUnits(Integer.valueOf(p[6]));
		} catch(Exception e) {
			item.setNoOfUnits(0);
		}
		try {
			item.setPricePerUnit(Double.valueOf(p[7]));
		} catch(Exception e) {
			item.setPricePerUnit(null);
		}
		
		return (TradeValidator.validateRecord(item))?item:null;
	};
	
}
