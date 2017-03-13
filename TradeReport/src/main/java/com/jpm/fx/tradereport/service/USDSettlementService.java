package com.jpm.fx.tradereport.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jpm.fx.tradereport.handler.TradeReportHandler;
import com.jpm.fx.tradereport.model.TradeInstruction;

public class USDSettlementService {

	private static Logger logger = Logger.getLogger("RankingsService");
	
	/**
	 * 
	 * @param instructions
	 * @param direction
	 * @return
	 */
	public static Map<String,Double> generateAmountSettledReport(List<TradeInstruction> instructions, String direction) {
		
		Map<String,Double> settledAmountMap = new HashMap<>();
		TradeReportHandler tradeReportHandler = new TradeReportHandler();
		
		instructions = filterByDirection(instructions, direction);
		//logger.info("Instructions for " + direction+ " are " + instructions);
		instructions.forEach(trade -> {
			tradeReportHandler.generateDialyReport(trade, settledAmountMap);
		});
		
		return settledAmountMap;
	}
	
	/**
	 * Return a new list based on the Buy/Sell indicator 
	 * @param instructions
	 * @param direction
	 * @return
	 */
	public static List<TradeInstruction> filterByDirection(List<TradeInstruction> instructions, String direction) {
		return instructions.stream().filter(trade -> (trade != null && trade.getDirection().equals(direction))).collect(Collectors.toList());
		
	}
	
}




