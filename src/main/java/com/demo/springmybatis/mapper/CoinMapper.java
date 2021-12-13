package com.demo.springmybatis.mapper;

import com.demo.springmybatis.domain.ChargeCoin;
import com.demo.springmybatis.domain.PayCoin;

import java.util.List;

public interface CoinMapper {

	public void create(ChargeCoin chargeCoin) throws Exception;

	public void charge(ChargeCoin chargeCoin) throws Exception;

	public List<ChargeCoin> list(int userNo) throws Exception;
	
	public void createPayHistory(PayCoin payCoin) throws Exception;

	public List<PayCoin> listPayHistory(int userNo) throws Exception;

	public void pay(PayCoin payCoin) throws Exception;	

}
