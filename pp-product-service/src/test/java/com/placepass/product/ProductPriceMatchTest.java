package com.placepass.product;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.placepass.product.application.pricematch.PriceMatchAppService;
import com.placepass.product.application.pricematch.dto.GetPriceMatchRQ;
import com.placepass.product.application.pricematch.dto.PriceBreakDownDTO;
import com.placepass.product.application.pricematch.dto.PricePerAgeBandDTO;
import com.placepass.product.application.pricematch.dto.QuantityDTO;
import com.placepass.product.application.pricematch.dto.TotalDTO;

@Ignore
public class ProductPriceMatchTest {

  @Test
  public void returnAllAgeBandTest() {

      /**
       * 
       * Input 
       * Prices : [{ADULT MIN:1, MAX:1, SG:1, RP:395}, {ADULT MIN:2, MAX:2, SG:2, RP:330}, {CHILD MIN:1, MAX:4, SG:2, RP:300}, {INFANT MIN:1, MAX:4, SG:3, RP:100}, {SENIOR MIN:1, MAX:2, SG:3, RP:150}] 
       * Qty    : [{ADULT Q:1}]
       * 
       * Output expected 
       * Prices : [{ADULT MIN:1, MAX:1, SG:1, RP:395}, {CHILD MIN:1, MAX:4, SG:2, RP:NULL}, {INFANT MIN:1, MAX:4, SG:3, RP:NULL}, {SENIOR MIN:1, MAX:2, SG:4, RP:NULL}]
       * 
       * 
       **/

      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();

      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);

      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(1);
      price2.setPriceType("ADULT");
      price2.setCurrencyCode("USD");
      price2.setDescription("Adult Price");
      price2.setRetailPrice(330F);
      price2.setFinalPrice(330F);
      price2.setRoundedFinalPrice(330F);
      price2.setMaxBuy(2);
      price2.setMinBuy(2);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(2);
      price3.setPriceType("CHILD");
      price3.setCurrencyCode("USD");
      price3.setDescription("Child Price");
      price3.setRetailPrice(300F);
      price3.setFinalPrice(300F);
      price3.setRoundedFinalPrice(300F);
      price3.setMaxBuy(4);
      price3.setMinBuy(1);
      inputPrices.add(price3);
      
      PricePerAgeBandDTO price4 = new PricePerAgeBandDTO();
      price4.setPriceGroupSortOrder(3);
      price4.setAgeBandId(3);
      price4.setPriceType("INFANT");
      price4.setCurrencyCode("USD");
      price4.setDescription("Infant Price");
      price4.setRetailPrice(100F);
      price4.setFinalPrice(100F);
      price4.setRoundedFinalPrice(100F);
      price4.setMaxBuy(4);
      price4.setMinBuy(1);
      inputPrices.add(price4);
      
      PricePerAgeBandDTO price5 = new PricePerAgeBandDTO();
      price5.setPriceGroupSortOrder(4);
      price5.setAgeBandId(5);
      price5.setPriceType("SENIOR");
      price5.setCurrencyCode("USD");
      price5.setDescription("Senior Price");
      price5.setRetailPrice(150F);
      price5.setFinalPrice(150F);
      price5.setRoundedFinalPrice(150F);
      price5.setMaxBuy(2);
      price5.setMinBuy(1);
      inputPrices.add(price5);

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(1);
      inputQuantities.add(quantity1);

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities);

      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(395F);
      expectedTotal.setFinalTotal(395F);
      expectedTotal.setRoundedFinalTotal(395F);

      int adultPriceCount = 0;
      int childPriceCount = 0;
      int infantPriceCount = 0;
      int seniorPriceCount = 0;

      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {

          if(priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1){
              adultPriceCount++;
          }
          if(priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2){
              childPriceCount++;
          }
          if(priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5){
              seniorPriceCount++;
          }
          if(priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 3){
              infantPriceCount++;
          }
      }

      Assert.assertEquals("Expected prices count found", 4, outputPrices.size());
      Assert.assertEquals("Expected ADULT prices count found", 1, adultPriceCount);
      Assert.assertEquals("Expected CHILD prices count found", 1, childPriceCount);
      Assert.assertEquals("Expected INFANT prices count found", 1, infantPriceCount);
      Assert.assertEquals("Expected SENIOR prices count found", 1, seniorPriceCount);
      
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), -1);
          
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
          
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 3) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
          
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting ADULT price with TotalDTO price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(expectedTotal.getRetailTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(expectedTotal.getFinalTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(expectedTotal.getRoundedFinalTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
          }
      }

  }
  

  @Test
  public void adultOrSeniorTest() {

      /**
       * 
       * Input 
       * Prices : [{ADULT MIN:1, MAX:1, SG:1, RP:395}, {ADULT MIN:2, MAX:2, SG:2, RP:330}, {CHILD MIN:1, MAX:4, SG:2, RP:300}] 
       * Qty    : [{CHILD Q:1}]
       * 
       * Output expected 
       * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
       * 
       * 
       **/

      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();

      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);

      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(1);
      price2.setPriceType("ADULT");
      price2.setCurrencyCode("USD");
      price2.setDescription("Adult Price");
      price2.setRetailPrice(330F);
      price2.setFinalPrice(330F);
      price2.setRoundedFinalPrice(330F);
      price2.setMaxBuy(2);
      price2.setMinBuy(2);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(2);
      price3.setPriceType("CHILD");
      price3.setCurrencyCode("USD");
      price3.setDescription("Child Price");
      price3.setRetailPrice(300F);
      price3.setFinalPrice(300F);
      price3.setRoundedFinalPrice(300F);
      price3.setMaxBuy(4);
      price3.setMinBuy(1);
      inputPrices.add(price3);

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(2);
      quantity1.setQuantity(1);
      inputQuantities.add(quantity1);

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities);
      
      PricePerAgeBandDTO expectedChildPrice = new PricePerAgeBandDTO();
      expectedChildPrice.setPriceGroupSortOrder(2);
      expectedChildPrice.setAgeBandId(2);
      expectedChildPrice.setPriceType("CHILD");
      expectedChildPrice.setCurrencyCode("USD");
      expectedChildPrice.setDescription("Child Price");
      expectedChildPrice.setRetailPrice(null);
      expectedChildPrice.setFinalPrice(null);
      expectedChildPrice.setRoundedFinalPrice(null);
      expectedChildPrice.setMaxBuy(4);
      expectedChildPrice.setMinBuy(1);
      inputPrices.add(expectedChildPrice);

      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
     
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
           Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
           Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
           Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
      }
      
  }

  
  @Test
  public void supportedAgeBandTest() {

      /**
       * 
       * Input 
       * Prices : [{ADULT MIN:1, MAX:1, RP:395}, {ADULT MIN:2, MAX:2, RP:330}, {CHILD MIN:1, MAX:4, RP:300}] 
       * Qty    : [{ADULT Q:1}, {INFANT Q:1}]
       * 
       * Output expected 
       * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
       * 
       * 
       **/

      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();

      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);

      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(1);
      price2.setPriceType("ADULT");
      price2.setCurrencyCode("USD");
      price2.setDescription("Adult Price");
      price2.setRetailPrice(330F);
      price2.setFinalPrice(330F);
      price2.setRoundedFinalPrice(330F);
      price2.setMaxBuy(2);
      price2.setMinBuy(2);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(2);
      price3.setPriceType("CHILD");
      price3.setCurrencyCode("USD");
      price3.setDescription("Child Price");
      price3.setRetailPrice(300F);
      price3.setFinalPrice(300F);
      price3.setRoundedFinalPrice(300F);
      price3.setMaxBuy(4);
      price3.setMinBuy(1);
      inputPrices.add(price3);

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(1);
      inputQuantities.add(quantity1);

      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(3);
      quantity2.setQuantity(1);
      inputQuantities.add(quantity2);

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities);

      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
      }
  }

  @Test
  public void supportedAgeBandTest1() {

      /**
       *  
       * Input 
       * Prices : [{ADULT MIN:1, MAX:1, RP:395}, {ADULT MIN:2, MAX:2, RP:330}, {CHILD MIN:1, MAX:4, RP:300}] 
       * Qty    : [{ADULT Q:1}, {ID:6 Q:1}]
       * 
       * Output expected 
       * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
       * 
       * 
       **/

      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();

      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);

      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(1);
      price2.setPriceType("ADULT");
      price2.setCurrencyCode("USD");
      price2.setDescription("Adult Price");
      price2.setRetailPrice(330F);
      price2.setFinalPrice(330F);
      price2.setRoundedFinalPrice(330F);
      price2.setMaxBuy(2);
      price2.setMinBuy(2);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(2);
      price3.setPriceType("CHILD");
      price3.setCurrencyCode("USD");
      price3.setDescription("Child Price");
      price3.setRetailPrice(300F);
      price3.setFinalPrice(300F);
      price3.setRoundedFinalPrice(300F);
      price3.setMaxBuy(4);
      price3.setMinBuy(1);
      inputPrices.add(price3);

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(1);
      inputQuantities.add(quantity1);

      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(6);
      quantity2.setQuantity(1);
      inputQuantities.add(quantity2);

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities);

      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());

      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
      }
  }
      
  @Test
  public void returnPriceObjectFromCorrectGroupTest() {

      /**
       *
       * Input 
       * Prices : [{ADULT MIN:2, MAX:2, RP:395}, {ADULT MIN:3, MAX:3, RP:330}, {CHILD MIN:1, MAX:1, RP:300}] 
       * Qty    : [{ADULT Q:3} {CHILD Q:1}]
       * 
       * Output expected 
       * Prices : [{ADULT MIN:3, MAX:3, RP:330}, {CHILD MIN:1, MAX:1, RP:300}]
       * 
       * 
       **/

      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();

      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(2);
      price1.setMinBuy(2);
      inputPrices.add(price1);

      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(1);
      price2.setPriceType("ADULT");
      price2.setCurrencyCode("USD");
      price2.setDescription("Adult Price");
      price2.setRetailPrice(330F);
      price2.setFinalPrice(330F);
      price2.setRoundedFinalPrice(330F);
      price2.setMaxBuy(3);
      price2.setMinBuy(3);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(2);
      price3.setPriceType("CHILD");
      price3.setCurrencyCode("USD");
      price3.setDescription("Child Price");
      price3.setRetailPrice(300F);
      price3.setFinalPrice(300F);
      price3.setRoundedFinalPrice(300F);
      price3.setMaxBuy(1);
      price3.setMinBuy(1);
      inputPrices.add(price3);

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(3);
      inputQuantities.add(quantity1);

      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(2);
      quantity2.setQuantity(1);
      inputQuantities.add(quantity2);

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities);

      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals("Expected Adult retail price found", priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), price2.getRetailPrice());
              Assert.assertEquals("Expected Adult max buy found", priceBreakDownDTO.getPricePerAgeBand().getMaxBuy(), price2.getMaxBuy());
              
          }
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals("Expected Adult retail price found", priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), price3.getRetailPrice());
              Assert.assertEquals("Expected Adult max buy found", priceBreakDownDTO.getPricePerAgeBand().getMaxBuy(), price3.getMaxBuy());
          }
      }
      
  }
  
  @Test
  public void returnCorrectPriceObjectTest2() {

      /**
       *
       * Input 
       * Prices : [{ADULT MIN:2, MAX:2, SG:1, RP:395}, {ADULT MIN:3, MAX:3, SG:2, RP:330}, {CHILD MIN:1, MAX:1, SG:2, RP:300}, {ADULT MIN:4, MAX:4, SG:3, RP:320}] 
       * Qty    : [{ADULT Q:4} ]
       * 
       * Output expected 
       * Prices : [{ADULT MIN:4, MAX:4, SG:3, RP:320*4}]
       * 
       * 
       **/

      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();

      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(2);
      price1.setMinBuy(2);
      inputPrices.add(price1);

      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(1);
      price2.setPriceType("ADULT");
      price2.setCurrencyCode("USD");
      price2.setDescription("Adult Price");
      price2.setRetailPrice(330F);
      price2.setFinalPrice(330F);
      price2.setRoundedFinalPrice(330F);
      price2.setMaxBuy(3);
      price2.setMinBuy(3);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(2);
      price3.setPriceType("CHILD");
      price3.setCurrencyCode("USD");
      price3.setDescription("Child Price");
      price3.setRetailPrice(300F);
      price3.setFinalPrice(300F);
      price3.setRoundedFinalPrice(300F);
      price3.setMaxBuy(1);
      price3.setMinBuy(1);
      inputPrices.add(price3);
      
      PricePerAgeBandDTO price4 = new PricePerAgeBandDTO();
      price4.setPriceGroupSortOrder(3);
      price4.setAgeBandId(1);
      price4.setPriceType("ADULT");
      price4.setCurrencyCode("USD");
      price4.setDescription("Adult Price");
      price4.setRetailPrice(320F);
      price4.setFinalPrice(320F);
      price4.setRoundedFinalPrice(320F);
      price4.setMaxBuy(4);
      price4.setMinBuy(4);
      inputPrices.add(price4);
      
      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(4);
      inputQuantities.add(quantity1);

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities);

      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      int adultPriceCount = 0;
      
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              adultPriceCount++;
              Assert.assertEquals("Expected Adult retail price found", priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), price4.getRetailPrice());
              Assert.assertEquals("Expected Adult max buy found", priceBreakDownDTO.getPricePerAgeBand().getMaxBuy(), price4.getMaxBuy());
          }
      }
        
      Assert.assertEquals("Expected ADULT prices count found", 1, adultPriceCount);
  }

  @Test
  public void returnNoPriceObjectTest() {

      /**
       * 
       * Input 
       * Prices : [{ADULT MIN:2, MAX:2, RP:395}, {ADULT MIN:3, MAX:3, RP:330}, {CHILD MIN:1, MAX:1, RP:300}] 
       * Qty    : [{ADULT Q:2} {CHILD Q:1}]
       * 
       * Output expected 
       * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
       * 
       **/

      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();

      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(2);
      price1.setMinBuy(2);
      inputPrices.add(price1);

      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(1);
      price2.setPriceType("ADULT");
      price2.setCurrencyCode("USD");
      price2.setDescription("Adult Price");
      price2.setRetailPrice(330F);
      price2.setFinalPrice(330F);
      price2.setRoundedFinalPrice(330F);
      price2.setMaxBuy(3);
      price2.setMinBuy(3);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(2);
      price3.setPriceType("CHILD");
      price3.setCurrencyCode("USD");
      price3.setDescription("Child Price");
      price3.setRetailPrice(300F);
      price3.setFinalPrice(300F);
      price3.setRoundedFinalPrice(300F);
      price3.setMaxBuy(1);
      price3.setMinBuy(1);
      inputPrices.add(price3);

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(2);
      inputQuantities.add(quantity1);

      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(2);
      quantity2.setQuantity(1);
      inputQuantities.add(quantity2);

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities);

      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());

      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
      }
  }

  
  @Test
  public void returnCorrectFinalTotalTest(){

      /**
       *
       * Input 
       * Prices : [{ADULT MIN:2, MAX:2, RP:300}, {CHILD MIN:0, MAX:4, RP:200}, {ADULT MIN:3, MAX:3, RP:250}] 
       * Qty    : [{ADULT Q:2} {CHILD Q:1}]
       * 
       * Output expected 
       * Prices : [{{ADULT MIN:2, MAX:2, RP:300} {TOTAL RP:600, FP:600, FRP:600}}, {{CHILD MIN:0, MAX:4, RP:200} {TOTAL RP:200, FP:200, FRP:200}}]
       * Total  : {TOTAL RP:800, FP:800, FRP:800}
       * 
       **/

      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();

      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(300F);
      price1.setFinalPrice(300F);
      price1.setRoundedFinalPrice(300F);
      price1.setMaxBuy(2);
      price1.setMinBuy(2);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(1);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(2);
      inputQuantities.add(quantity1);
      
      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(2);
      quantity2.setQuantity(1);
      inputQuantities.add(quantity2);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities);       
      
      List<PriceBreakDownDTO> priceBreakdowns = new PriceMatchAppService().getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      TotalDTO totalDTO = new PriceMatchAppService().getTotalForQuantities(priceBreakdowns);

      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(800F);
      expectedTotal.setFinalTotal(800F);
      expectedTotal.setRoundedFinalTotal(800F);
      // need clarification
      Assert.assertEquals(800, totalDTO.getRetailTotal(), expectedTotal.getRetailTotal());
      Assert.assertEquals(990, totalDTO.getFinalTotal(), expectedTotal.getFinalTotal());
      Assert.assertEquals(990, totalDTO.getRoundedFinalTotal(), expectedTotal.getRoundedFinalTotal());
      
  }
  
  @Test
  public void getQuantityNullTest() {
      
      /**
      *
      * Input 
      * Prices : [{ADULT MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}, {ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : NULL
      * 
      * Output expected 
      * Prices : [{{ADULT MIN:3, MAX:3, RP:250} {TOTAL RP:250, FP:250, FRP:250}}, {{CHILD MIN:0, MAX:4, RP:NULL} {TOTAL RP:NULL, FP:NULL, FRP:NULL}}]
      * Total  : {TOTAL RP:250, FP:250, FRP:250}
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = null;
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(250F);
      expectedTotal.setFinalTotal(250F);
      expectedTotal.setRoundedFinalTotal(250F);
      
      // When input quantities are null, then we set ADULT price as the lowest price from the matrix (irrespective of the group sort order)
      /* asserting ADULT price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(250F, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(250F, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(250F, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), -1);
          }
      }
      
      /* asserting TotalDTO price with ADULT price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(expectedTotal.getRetailTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(expectedTotal.getFinalTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(expectedTotal.getRoundedFinalTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), -1);
          }
      }
  }
  
  
  @Test
  public void getQuantityListEmptyTest() {
      
      /**
      *
      * Input 
      * Prices : [{ADULT MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}, {ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : []
      * 
      * Output expected 
      * Prices : [{{ADULT MIN:1, MAX:1, RP:250} {TOTAL RP:250, FP:250, FRP:250}}, {{CHILD MIN:0, MAX:4, RP:NULL} {TOTAL RP:NULL, FP:NULL, FRP:NULL}}]
      * Total  : {TOTAL RP:250, FP:250, FRP:250}
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(250F);
      expectedTotal.setFinalTotal(250F);
      expectedTotal.setRoundedFinalTotal(250F);
      
      // When input quantities are empty, then we set ADULT price as the lowest price from the matrix (irrespective of the group sort order)
      /* asserting ADULT price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(250F, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(250F, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(250F, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), -1);
          }
      }
      
      /* asserting TotalDTO price with ADULT price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(expectedTotal.getRetailTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(expectedTotal.getFinalTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(expectedTotal.getRoundedFinalTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), -1);
          }
      }
  }
  
  
  @Test
  public void getTotalAgeBandQuantityExceedsTest() {
      
      /**
      *
      * Input 
      * Prices : [{ADULT MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}, {ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : [{ADULT Q:2}, {CHILD Q:8}]
      * 
      * Output expected 
      * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(2);
      inputQuantities.add(quantity1);
      
      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(2);
      quantity2.setQuantity(8);
      inputQuantities.add(quantity2);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(null);
      expectedTotal.setFinalTotal(null);
      expectedTotal.setRoundedFinalTotal(null);
      
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal());
          Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal());
          Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal());
      }
      
      int adultPriceCount = 0;
      int childPriceCount = 0;

      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {

          if(priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1){
              adultPriceCount++;
          }
          if(priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2){
              childPriceCount++;
          }
      }
      Assert.assertEquals("Expected prices count found", 2, outputPrices.size());
      Assert.assertEquals("Expected ADULT prices count found", 1, adultPriceCount);
      Assert.assertEquals("Expected CHILD prices count found", 1, childPriceCount);
  }
  
  
  @Test
  public void assertAdultQuantityZeroTest() {
      
      /**
      *
      * Input 
      * Prices : [{ADULT MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}, {ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : [{ADULT Q:0}]
      * 
      * Output expected 
      * Prices : [{ADULT RP:250, FP:250, FRP:250}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(0);
      inputQuantities.add(quantity1);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(2, outputPrices.size());
      
      // {ADULT MIN:3, MAX:3, RP:250} - best price for ADULT, when requested qty is zero for ADULT
      /* When the ADULT qty is zero in the quantities, then we return the best price for ADULT as the Default and the TotalDTO price should be same as Default ADULT price */
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(250F);
      expectedTotal.setFinalTotal(250F);
      expectedTotal.setRoundedFinalTotal(250F);
      
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(expectedTotal.getRetailTotal(), priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(expectedTotal.getFinalTotal(), priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(expectedTotal.getRoundedFinalTotal(), priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), -1);
          }
      }
  }
  
  
  @Test
  public void getAdultQuantityWithLesserThanMinBuyTest() {
      
      /**
      *
      * Input 
      * Prices : [{ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : [{ADULT Q:2}]
      * 
      * Output expected 
      * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(1);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(2);
      inputQuantities.add(quantity1);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(1, outputPrices.size());
      
      /* When the quantities qty is less than ADULT qty, then we return null for ADULT price and the TotalDTO price also null */
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(null);
      expectedTotal.setFinalTotal(null);
      expectedTotal.setRoundedFinalTotal(null);
      
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(expectedTotal.getRetailTotal(), priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(expectedTotal.getFinalTotal(), priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(expectedTotal.getRoundedFinalTotal(), priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
      }
  }
  
  
  @Test
  public void getAdultQuantityWithHigherThanMaxBuyTest() {
      
      /**
      *
      * Input 
      * Prices : [{ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : [{ADULT Q:4}]
      * 
      * Output expected 
      * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(1);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(4);
      inputQuantities.add(quantity1);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(1, outputPrices.size());
      
      /* When the quantities qty is higher than ADULT qty, then we return null for ADULT price and the TotalDTO price also null */
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(null);
      expectedTotal.setFinalTotal(null);
      expectedTotal.setRoundedFinalTotal(null);
      
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(expectedTotal.getRetailTotal(), priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(expectedTotal.getFinalTotal(), priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(expectedTotal.getRoundedFinalTotal(), priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
      }
  }
  
  
  @Test
  public void getAdultQuantityIsInBetweenMinAndMaxBuyTest() {
      
      /**
      *
      * Input 
      * Prices : [{ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : [{ADULT Q:3}]
      * 
      * Output expected 
      * Prices : [{ADULT RP:750, FP:750, FRP:750}]
      * Total Price : [{RP:750, FP:750, FRP:750}]
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(1);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(3);
      inputQuantities.add(quantity1);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(1, outputPrices.size());
      
      /* When the quantities qty is in between ADULT qty min and max range, then we return ADULT price and the TotalDTO price also */
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(750F);
      expectedTotal.setFinalTotal(750F);
      expectedTotal.setRoundedFinalTotal(750F);
      
      /* asserting the ADULT object price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          Assert.assertEquals(250F, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), -1);
          Assert.assertEquals(250F, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), -1);
          Assert.assertEquals(250F, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), -1);
      }
      
      /* asserting the price per ageband ADULT total price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          Assert.assertEquals(750F, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), -1);
          Assert.assertEquals(750F, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), -1);
          Assert.assertEquals(750F, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), -1);
      }
      
      /* asserting the TotalDTO price with price per ageband ADULT total price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          Assert.assertEquals(expectedTotal.getRetailTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
          Assert.assertEquals(expectedTotal.getFinalTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
          Assert.assertEquals(expectedTotal.getRoundedFinalTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
      }
  }
  
  
  @Test
  public void getAdultQuantityZeroWithChildTest() {
      
      /**
      *
      * Input 
      * Prices : [{ADULT MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}, {ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : [{ADULT Q:0}, {CHILD Q:1}]
      * 
      * Output expected 
      * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(0);
      inputQuantities.add(quantity1);
      
      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(2);
      quantity2.setQuantity(1);
      inputQuantities.add(quantity2);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(2, outputPrices.size());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(null);
      expectedTotal.setFinalTotal(null);
      expectedTotal.setRoundedFinalTotal(null);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          
          Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
      }
      
      /* asserting TotalDTO price with any age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          
          Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal());
          Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal());
          Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal());
      }
  }
  
  
  @Test
  public void getAdultQuantityWithChildQuantityZeroTest() {
      
      /**
      *
      * Input 
      * Prices : [{ADULT MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}, {ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : [{ADULT Q:1}, {CHILD Q:0}]
      * 
      * Output expected 
      * Prices : [{ADULT RP:395, FP:395, FRP:395}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(1);
      inputQuantities.add(quantity1);
      
      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(2);
      quantity2.setQuantity(0);
      inputQuantities.add(quantity2);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(2, outputPrices.size());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(395F);
      expectedTotal.setFinalTotal(395F);
      expectedTotal.setRoundedFinalTotal(395F);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), -1);
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), -1);
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting TotalDTO price with ADULT age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal());
          }
      }
  }
  
  
  @Test
  public void getAdultQuantityWithChildQuantityZeroTest1() {
      
      /**
      *
      * Input 
      * Prices : [{ADULT MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}, {ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : [{ADULT Q:2}, {CHILD Q:0}]
      * 
      * Output expected 
      * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(1);
      price1.setPriceType("ADULT");
      price1.setCurrencyCode("USD");
      price1.setDescription("Adult Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(2);
      inputQuantities.add(quantity1);
      
      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(2);
      quantity2.setQuantity(0);
      inputQuantities.add(quantity2);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(2, outputPrices.size());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(null);
      expectedTotal.setFinalTotal(null);
      expectedTotal.setRoundedFinalTotal(null);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting TotalDTO price with ADULT age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal());
          }
      }
  }
  
  
  @Test
  public void getSeniorWithoutAdultWithQuantitiesNullTest() {
      
      /**
      *
      * Input 
      * Prices : [{SENIOR MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}] 
      * Qty    : NULL
      * 
      * Output expected 
      * Prices : [{SENIOR RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = null;
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(5);
      price1.setPriceType("SENIOR");
      price1.setCurrencyCode("USD");
      price1.setDescription("Senior Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(2, outputPrices.size());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(395F);
      expectedTotal.setFinalTotal(395F);
      expectedTotal.setRoundedFinalTotal(395F);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5) {
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), -1);
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5) {
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), -1);
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting TotalDTO price with ADULT age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5) {
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal());
          }
      }
  }
  
  
  @Test
  public void getSeniorWithoutAdultWithQuantitiesEmptyTest() {
      
      /**
      *
      * Input 
      * Prices : [{SENIOR MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}] 
      * Qty    : []
      * 
      * Output expected 
      * Prices : [{SENIOR RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(5);
      price1.setPriceType("SENIOR");
      price1.setCurrencyCode("USD");
      price1.setDescription("Senior Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(2, outputPrices.size());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(395F);
      expectedTotal.setFinalTotal(395F);
      expectedTotal.setRoundedFinalTotal(395F);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5) {
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), -1);
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5) {
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), -1);
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting TotalDTO price with ADULT age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5) {
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal());
          }
      }
  }
  
  @Test
  public void getSeniorQuantityWithLesserThanMinBuyTest() {
      
      /**
      *
      * Input 
      * Prices : [{SENIOR MIN:3, MAX:3, RP:320}] 
      * Qty    : [{SENIOR Q:2}]
      * 
      * Output expected 
      * Prices : [{SENIOR RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(5);
      price1.setPriceType("SENIOR");
      price1.setCurrencyCode("USD");
      price1.setDescription("Senior Price");
      price1.setRetailPrice(320F);
      price1.setFinalPrice(320F);
      price1.setRoundedFinalPrice(320F);
      price1.setMaxBuy(3);
      price1.setMinBuy(3);
      inputPrices.add(price1);
      
      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(5);
      quantity1.setQuantity(2);
      inputQuantities.add(quantity1);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      /* When the quantities qty is less than SENIOR qty, then we return null for SENIOR price and the TotalDTO price also null */
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(null);
      expectedTotal.setFinalTotal(null);
      expectedTotal.setRoundedFinalTotal(null);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
              
      }
      
      /* asserting TotalDTO price with ADULT age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal());
      }
  }
  
  
  @Test
  public void getSeniorQuantityWithHigherThanMaxBuyTest() {
      
      /**
      *
      * Input 
      * Prices : [{SENIOR MIN:3, MAX:3, RP:320}] 
      * Qty    : [{SENIOR Q:4}]
      * 
      * Output expected 
      * Prices : [{SENIOR RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(5);
      price1.setPriceType("SENIOR");
      price1.setCurrencyCode("USD");
      price1.setDescription("Senior Price");
      price1.setRetailPrice(320F);
      price1.setFinalPrice(320F);
      price1.setRoundedFinalPrice(320F);
      price1.setMaxBuy(3);
      price1.setMinBuy(3);
      inputPrices.add(price1);
      
      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(5);
      quantity1.setQuantity(4);
      inputQuantities.add(quantity1);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      /* When the quantities qty is less than SENIOR qty, then we return null for SENIOR price and the TotalDTO price also null */
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(null);
      expectedTotal.setFinalTotal(null);
      expectedTotal.setRoundedFinalTotal(null);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
              
      }
      
      /* asserting TotalDTO price with ADULT age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal());
      }
  }
  
  
  @Test
  public void getSeniorQuantityIsInBetweenMinAndMaxBuyTest() {
      
      /**
      *
      * Input 
      * Prices : [{SENIOR MIN:3, MAX:3, RP:320}] 
      * Qty    : [{SENIOR Q:3}]
      * 
      * Output expected 
      * Prices : [{SENIOR RP:320, FP:320, FRP:320}]
      * Total Price : [{RP:960, FP:960, FRP:960}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(5);
      price1.setPriceType("SENIOR");
      price1.setCurrencyCode("USD");
      price1.setDescription("Senior Price");
      price1.setRetailPrice(320F);
      price1.setFinalPrice(320F);
      price1.setRoundedFinalPrice(320F);
      price1.setMaxBuy(3);
      price1.setMinBuy(3);
      inputPrices.add(price1);
      
      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(5);
      quantity1.setQuantity(3);
      inputQuantities.add(quantity1);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      /* When the quantities qty is less than SENIOR qty, then we return null for SENIOR price and the TotalDTO price also null */
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(960F);
      expectedTotal.setFinalTotal(960F);
      expectedTotal.setRoundedFinalTotal(960F);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(320F, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(320F, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(320F, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), -1);
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(960F, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(960F, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(960F, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), -1);
              
      }
      
      /* asserting TotalDTO price with ADULT age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
              Assert.assertEquals(priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal(), -1);
              Assert.assertEquals(priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal(), -1);
              Assert.assertEquals(priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal(), -1);
      }
  }
  
  
  @Test
  public void getSeniorQuantityZeroWithChildTest() {
      
      /**
      *
      * Input 
      * Prices : [{SENIOR MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}] 
      * Qty    : [{SENIOR Q:0}, {CHILD Q:1}]
      * 
      * Output expected 
      * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(5);
      price1.setPriceType("SENIOR");
      price1.setCurrencyCode("USD");
      price1.setDescription("Senior Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(5);
      quantity1.setQuantity(0);
      inputQuantities.add(quantity1);
      
      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(2);
      quantity2.setQuantity(1);
      inputQuantities.add(quantity2);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(2, outputPrices.size());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(null);
      expectedTotal.setFinalTotal(null);
      expectedTotal.setRoundedFinalTotal(null);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          
          Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
          Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
      }
      
      /* asserting TotalDTO price with any age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          
          Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal());
          Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal());
          Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal());
      }
  }
  
  
  @Test
  public void getSeniorQuantityWithChildQuantityZeroTest() {
      
      /**
      *
      * Input 
      * Prices : [{SENIOR MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}] 
      * Qty    : [{SENIOR Q:1}, {CHILD Q:0}]
      * 
      * Output expected 
      * Prices : [{SENIOR RP:395, FP:395, FRP:395}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(5);
      price1.setPriceType("SENIOR");
      price1.setCurrencyCode("USD");
      price1.setDescription("Senior Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(1);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(5);
      quantity1.setQuantity(1);
      inputQuantities.add(quantity1);
      
      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(2);
      quantity2.setQuantity(0);
      inputQuantities.add(quantity2);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(2, outputPrices.size());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(395F);
      expectedTotal.setFinalTotal(395F);
      expectedTotal.setRoundedFinalTotal(395F);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5) {
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), -1);
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5) {
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), -1);
              Assert.assertEquals(395F, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), -1);
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting TotalDTO price with ADULT age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 5) {
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal());
              Assert.assertEquals(priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal());
          }
      }
  }
  
  
  @Test
  public void getSeniorAdultAndChildQuantityTest() {
      
      /**
      *
      * Input 
      * Prices : [{SENIOR MIN:1, MAX:1, RP:395}, {ADULT MIN:2, MAX:2, RP:300}, {CHILD MIN:2, MAX:2, RP:200}] 
      * Qty    : [{ADULT Q:2}, {CHILD Q:2}]
      * 
      * Output expected 
      * Prices : [{ADULT RP:NULL, FP:NULL, FRP:NULL}, {SENIOR RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(5);
      price1.setPriceType("SENIOR");
      price1.setCurrencyCode("USD");
      price1.setDescription("Senior Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(1);
      price2.setAgeBandId(1);
      price2.setPriceType("ADULT");
      price2.setCurrencyCode("USD");
      price2.setDescription("Adult Price");
      price2.setRetailPrice(300F);
      price2.setFinalPrice(300F);
      price2.setRoundedFinalPrice(300F);
      price2.setMaxBuy(2);
      price2.setMinBuy(2);
      inputPrices.add(price2);
      
      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(2);
      price3.setPriceType("CHILD");
      price3.setCurrencyCode("USD");
      price3.setDescription("Child Price");
      price3.setRetailPrice(200F);
      price3.setFinalPrice(200F);
      price3.setRoundedFinalPrice(200F);
      price3.setMaxBuy(2);
      price3.setMinBuy(2);
      inputPrices.add(price3);

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(1);
      quantity1.setQuantity(2);
      inputQuantities.add(quantity1);
      
      QuantityDTO quantity2 = new QuantityDTO();
      quantity2.setAgeBandId(2);
      quantity2.setQuantity(2);
      inputQuantities.add(quantity2);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      //Assert.assertEquals(2, outputPrices.size());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(null);
      expectedTotal.setFinalTotal(null);
      expectedTotal.setRoundedFinalTotal(null);
      
      /* asserting age band price */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting price per ageband total prices */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
              
          } else if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 2) {
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(null, priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
          }
      }
      
      /* asserting TotalDTO price with ADULT age band of the priceBreakDownDTO */
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(expectedTotal.getRetailTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice());
              Assert.assertEquals(expectedTotal.getFinalTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice());
              Assert.assertEquals(expectedTotal.getRoundedFinalTotal(), priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice());
          }
      }
  }
  
  @Test
  public void getSeniorQuantityZeroTest() {
      
      /**
      *
      * Input 
      * Prices : [{SENIOR MIN:1, MAX:1, RP:395}, {CHILD MIN:0, MAX:4, RP:200}, {ADULT MIN:3, MAX:3, RP:250}] 
      * Qty    : [{SENIOR Q:0}]
      * 
      * Output expected 
      * Prices : [{ADULT MIN:3, MAX:3, RP:250}, {SENIOR RP:NULL, FP:NULL, FRP:NULL}, {CHILD RP:NULL, FP:NULL, FRP:NULL}]
      * 
      **/
      
      GetPriceMatchRQ getPriceMatchRQ = new GetPriceMatchRQ();
      List<QuantityDTO> inputQuantities = new ArrayList<>();
      List<PricePerAgeBandDTO> inputPrices = new ArrayList<>();
      
      PricePerAgeBandDTO price1 = new PricePerAgeBandDTO();
      price1.setPriceGroupSortOrder(1);
      price1.setAgeBandId(5);
      price1.setPriceType("SENIOR");
      price1.setCurrencyCode("USD");
      price1.setDescription("Senior Price");
      price1.setRetailPrice(395F);
      price1.setFinalPrice(395F);
      price1.setRoundedFinalPrice(395F);
      price1.setMaxBuy(1);
      price1.setMinBuy(1);
      inputPrices.add(price1);
      
      PricePerAgeBandDTO price2 = new PricePerAgeBandDTO();
      price2.setPriceGroupSortOrder(2);
      price2.setAgeBandId(2);
      price2.setPriceType("CHILD");
      price2.setCurrencyCode("USD");
      price2.setDescription("Child Price");
      price2.setRetailPrice(200F);
      price2.setFinalPrice(200F);
      price2.setRoundedFinalPrice(200F);
      price2.setMaxBuy(4);
      price2.setMinBuy(0);
      inputPrices.add(price2);

      PricePerAgeBandDTO price3 = new PricePerAgeBandDTO();
      price3.setPriceGroupSortOrder(2);
      price3.setAgeBandId(1);
      price3.setPriceType("ADULT");
      price3.setCurrencyCode("USD");
      price3.setDescription("Adult Price");
      price3.setRetailPrice(250F);
      price3.setFinalPrice(250F);
      price3.setRoundedFinalPrice(250F);
      price3.setMaxBuy(3);
      price3.setMinBuy(3);
      inputPrices.add(price3);      

      QuantityDTO quantity1 = new QuantityDTO();
      quantity1.setAgeBandId(5);
      quantity1.setQuantity(0);
      inputQuantities.add(quantity1);
      
      getPriceMatchRQ.setPrices(inputPrices);
      getPriceMatchRQ.setQuantities(inputQuantities); 
      
      List<PriceBreakDownDTO> outputPrices = PriceMatchAppService.getMatchedPriceList(getPriceMatchRQ.getPrices(), getPriceMatchRQ.getQuantities());
      
      Assert.assertEquals(3, outputPrices.size());
      
      TotalDTO expectedTotal = new TotalDTO();
      expectedTotal.setCurrencyCode("USD");
      expectedTotal.setRetailTotal(250F);
      expectedTotal.setFinalTotal(250F);
      expectedTotal.setRoundedFinalTotal(250F);
      
      for (PriceBreakDownDTO priceBreakDownDTO : outputPrices) {
          if (priceBreakDownDTO.getPricePerAgeBand().getAgeBandId() == 1) {
              Assert.assertEquals(priceBreakDownDTO.getTotalPricePerAgeBand().getRetailPrice(), expectedTotal.getRetailTotal(), -1);
              Assert.assertEquals(priceBreakDownDTO.getTotalPricePerAgeBand().getFinalPrice(), expectedTotal.getFinalTotal(), -1);
              Assert.assertEquals(priceBreakDownDTO.getTotalPricePerAgeBand().getRoundedFinalPrice(), expectedTotal.getRoundedFinalTotal(), -1);
          }
        }
    }
}