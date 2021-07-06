package com.mind.bowser.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mind.bowser.dto.CreatePayment;
import com.mind.bowser.dto.CreatePaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentCreateParams.Builder;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	  

	@PostMapping("/createPayment")
	public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {
		PaymentIntentCreateParams createParams=new PaymentIntentCreateParams.Builder()
				.setCurrency("usd")
				.setAmount(15*100L)
		.build();
		
		PaymentIntent intent=PaymentIntent.create(createParams);
		return new CreatePaymentResponse(intent.getClientSecret());
		
	}
}
