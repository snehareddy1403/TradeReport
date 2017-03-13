package com.jpm.fx.tradereport.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.fx.tradereport.model.TradeInstruction;
import com.jpm.fx.tradereport.util.FileReaderUtil;

public class USDSettlementServiceTest {

	List<TradeInstruction> instructions;
	
	@Before
	public void setUp(){
		instructions = FileReaderUtil.processInputFile();
	}
	
	@Test
	public void givenBuyGenerateOutGoingReport() {
		//when
		Map<String, Double> data = USDSettlementService.generateAmountSettledReport(instructions, "B");
		assertEquals("23025.0", data.get("2017-01-02").toString());
	}

	@Test
	public void givenSellGenerateIncomingReport() {
		//when
		Map<String, Double> data = USDSettlementService.generateAmountSettledReport(instructions, "S");
		assertEquals("3000.0", data.get("2017-01-02").toString());
	}
	
	@Test
	public void givenAEDSettleOnSunday() {
		//when
		assertEquals(instructions.get(2).getCurrency().toString(), "AED");
		assertEquals(instructions.get(2).getSettlementDate().toString(), "2017-01-07");
		Map<String, Double> data = USDSettlementService.generateAmountSettledReport(instructions, "S");
		assertEquals("14899.5", data.get("2017-01-08").toString());
	}
	
	@Test
	public void givenSGPSettleOnMonday() {
		//when
		assertEquals(instructions.get(0).getCurrency().toString(), "SGP");
		assertEquals(instructions.get(0).getSettlementDate().toString(), "2017-01-01");
		Map<String, Double> data = USDSettlementService.generateAmountSettledReport(instructions, "B");
		assertEquals("23025.0", data.get("2017-01-02").toString());
	}
	
	@Test
	public void givenAllInstructionsGenerateRankReport() {
		//when
		Map<String, Map<String, Double>> data = RankingsService.prepareRankings(instructions);
		assertEquals("{foo=20025.0, kiwi=14899.5, orange=3000.0}", data.get("Incoming Rankings:").toString());
		assertEquals("20025.0", data.get("Incoming Rankings:").get("foo").toString());
		assertEquals("{apple=22729.5, bar=14899.5, orange=12949.5}", data.get("Outgoing Rankings:").toString());
		assertEquals("22729.5", data.get("Outgoing Rankings:").get("apple").toString());
	}
	
	@After
	public void end(){
		instructions = null;
	}
}
