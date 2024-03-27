package com.Nkosopa.NMarket.Services.Other;

import com.Nkosopa.NMarket.DTO.Other.OrderDTO;

public interface iOrderService {

    OrderDTO confirmOrder(long cartId);

    OrderDTO cancelOrder(long orderId);
}
