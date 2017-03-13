package com.jpm.fx.tradereport;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jpm.fx.tradereport.model.Instruction;
import com.jpm.fx.tradereport.model.TradeInstruction;
import com.jpm.fx.tradereport.service.RankingsService;
import com.jpm.fx.tradereport.service.USDSettlementService;
import com.jpm.fx.tradereport.util.FileReaderUtil;

/**
 * Main class which calls methods to generate all reports
 *
 */
public class DailyReportGenerator {
	
	final static Logger logger = Logger.getLogger("DailyReportGenerator");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		logger.log(Level.INFO,"This is debug : ");
		
		List<TradeInstruction> instructions = FileReaderUtil.processInputFile();
		System.out.println("Generating Incoming report..");
		System.out.println(USDSettlementService.generateAmountSettledReport(instructions, Instruction.SELL.value()));
		System.out.println("Generating Outgoing report.."); 
		System.out.println( USDSettlementService.generateAmountSettledReport(instructions, Instruction.BUY.value()));
		System.out.println("Generating Ranking report..");
		System.out.println(RankingsService.prepareRankings(instructions));
		
	}
	

}
