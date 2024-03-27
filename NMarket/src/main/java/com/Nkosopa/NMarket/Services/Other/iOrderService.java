package com.Nkosopa.NMarket.Services.Other;

public interface iOrderService {
    void confirmOrder(long orderId);

    void cancelOrder(long orderId);
}
