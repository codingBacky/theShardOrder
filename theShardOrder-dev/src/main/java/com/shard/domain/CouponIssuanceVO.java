package com.shard.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponIssuanceVO {
	
	private int couponNum;
	
	private int issueNum;
	
	private Date issueDate;
	
	private Date issueED;
	
	private String email;
	
	private int orderId;
}	
	
