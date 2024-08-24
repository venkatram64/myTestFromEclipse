package com.srijan.eapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.srijan.eapp.model.Card;
import com.srijan.eapp.model.Charge;
import com.srijan.eapp.model.Customer;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentSource;
import com.stripe.model.PaymentSourceCollection;
import com.stripe.model.Token;

public class StripeService {	
	
	private String apiSecretKey;
	
	public StripeService(String apiSecretKey) {
		this.apiSecretKey = apiSecretKey;
	}

    public com.stripe.model.Customer createCustomer(Customer customer) throws StripeException {

        Stripe.apiKey = this.apiSecretKey;
        Map<String, Object> customerParams = new HashMap<>();
        if(customer.getFirstName() != null){
            customerParams.put("name", customer.getFirstName());
        }
        customerParams.put("email", customer.getEmail());
        customerParams.put("description", "Customer for " + customer.getEmail());
        //customerParams.put("source", token);

        com.stripe.model.Customer stripeCustomer = com.stripe.model.Customer.create(customerParams);

        return stripeCustomer;
    }

    public com.stripe.model.Customer getCustomerByCustomerId(String custid) throws StripeException{
        Stripe.apiKey = this.apiSecretKey;
        com.stripe.model.Customer stripeCustomer = com.stripe.model.Customer.retrieve(custid);
        return stripeCustomer;
    }

    public com.stripe.model.Customer addCardDetails(Card card, String customerId) throws StripeException {

        Stripe.apiKey = this.apiSecretKey;
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", card.getCardNumber());
        cardParams.put("exp_month", card.getExpMonth());
        cardParams.put("exp_year", card.getExpYear());
        cardParams.put("cvc", card.getCvc());

        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);
        Token token = Token.create(tokenParams);

        com.stripe.model.Customer stripeCustomer = this.getCustomerByCustomerId(customerId);
        Map<String, Object> source = new HashMap<>();
        source.put("source",token.getId());
        stripeCustomer.getSources().create(source);
        return stripeCustomer;

    }

    public com.stripe.model.Customer addCardDetailsByAvoidingDuplicates(Card card, String customerId) throws StripeException {

        Stripe.apiKey = this.apiSecretKey;
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", card.getCardNumber());
        cardParams.put("exp_month", card.getExpMonth());
        cardParams.put("exp_year", card.getExpYear());
        cardParams.put("cvc", card.getCvc());

        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);
        Token token = Token.create(tokenParams);

        com.stripe.model.Customer stripeCustomer = this.getCustomerByCustomerId(customerId);
        PaymentSourceCollection allCardDetails = stripeCustomer.getSources();
        List<PaymentSource> psList = allCardDetails.getData();
        Boolean cardIsNotExist = true;
        for(int i = 0; i < allCardDetails.getData().size(); i++){
            com.stripe.model.Card ecard = (com.stripe.model.Card)stripeCustomer.getSources().getData().get(i);
            if(ecard.getFingerprint().equals(token.getCard().getFingerprint())){
                cardIsNotExist = false;
                break;
            }
        }
        if(cardIsNotExist){
            Map<String, Object> source = new HashMap<>();
            source.put("source",token.getId());
            stripeCustomer.getSources().create(source);
        }else{
            System.out.println("Card allready exists.");
        }

        return stripeCustomer;

    }
    
    //is used card is already registered, use multiple times
    public com.stripe.model.Customer charge(Charge charge, String custId) throws StripeException {
        Stripe.apiKey = this.apiSecretKey;
        com.stripe.model.Customer stripeCustomer = this.getCustomerByCustomerId(custId);
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount",charge.getAmount() * 100);
        chargeParams.put("currency",charge.getCurrency());
        chargeParams.put("description", charge.getDescription());
        chargeParams.put("customer", stripeCustomer.getId());

        com.stripe.model.Charge stripeCharge = com.stripe.model.Charge.create(chargeParams);
        return stripeCustomer;
    }

    public com.stripe.model.Token getToken(Card card) throws StripeException {

        Stripe.apiKey = this.apiSecretKey;
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", card.getCardNumber());
        cardParams.put("exp_month", card.getExpMonth());
        cardParams.put("exp_year", card.getExpYear());
        cardParams.put("cvc", card.getCvc());

        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);
        Token token = Token.create(tokenParams);
        return token;
    }

    //charge by token working....
    public com.stripe.model.Charge chargeByToken(Card card, Charge charge) throws StripeException {
        Stripe.apiKey = this.apiSecretKey;
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount",charge.getAmount() * 100);
        chargeParams.put("currency",charge.getCurrency());
        chargeParams.put("description", "buying the product");
        //chargeParams.put("description", charge.getDescription());
        Token token = this.getToken(card);
        chargeParams.put("source", token.getId());
        com.stripe.model.Charge stripeCharge = com.stripe.model.Charge.create(chargeParams);
        return stripeCharge;
    }
    //https://stripe.com/docs/api/orders/create
    public com.stripe.model.Order createOrder() throws StripeException {
    	Map<String, Object> items = new HashMap<>();
    	
    	Map<String, Object> address = new HashMap<>();
    	
    	Map<String, Object> shipping = new HashMap<>();
    	
    	Map<String, Object> params = new HashMap<>();    	
    
    	params.put("currency", "inr");
    	params.put("email", "jenny.rosen@example.com");
    	params.put("items", items);
    	params.put("shipping", shipping);
    	
    	com.stripe.model.Order order = com.stripe.model.Order.create(params);
    	Map<String, Object> payParams = new HashMap<>();
    	payParams.put("source", "tok_visa");    	
    	order = order.pay(payParams);
    	
    	return order;
    }

}
