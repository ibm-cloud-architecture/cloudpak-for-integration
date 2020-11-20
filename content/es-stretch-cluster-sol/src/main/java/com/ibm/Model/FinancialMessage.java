package com.ibm.Model;

public class FinancialMessage {
    
    public String user_id;
    public String stock_symbol;
    public String exchange_id;
    public String trade_type;
    public String date_created;
    public String date_submitted;
    public int quantity;
    public double stock_price;
    public double total_cost;
    public int institution_id;
    public int country_id;
    public boolean compliance_services;
    public boolean technical_validation;
    public boolean schema_validation;
    public boolean business_validation;
    public boolean trade_enrichment;


    public FinancialMessage() {

    }

    public FinancialMessage(String user_id, String stock_symbol, String exchange_id, String trade_type, 
                            String date_created, String date_submitted, int quantity, double stock_price,
                            double total_cost, int institution_id, int country_id, 
                            boolean compliance_services, boolean technical_validation, boolean schema_validation,
                            boolean business_validation, boolean trade_enrichment) {

        this.user_id = user_id;
        this.stock_symbol = stock_symbol;
        this.exchange_id = exchange_id;
        this.trade_type = trade_type;
        this.date_created = date_created;
        this.date_submitted = date_submitted;
        this.quantity = quantity;
        this.stock_price = stock_price;
        this.total_cost = total_cost;
        this.institution_id = institution_id;
        this.country_id = country_id;
        this.compliance_services = compliance_services;
        this.technical_validation = technical_validation;
        this.schema_validation = schema_validation;
        this.business_validation = business_validation;
        this.trade_enrichment = trade_enrichment;
    }

    public static FinancialMessage from (FinancialMessage message) {
        return new FinancialMessage (
                message.user_id,
                message.stock_symbol,
                message.exchange_id,
                message.trade_type,
                message.date_created,
                message.date_submitted,
                message.quantity,
                message.stock_price,
                message.total_cost,
                message.institution_id,
                message.country_id,
                message.compliance_services,
                message.technical_validation,
                message.schema_validation,
                message.business_validation,
                message.trade_enrichment);
    }
}