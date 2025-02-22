package com.example.demo.application;

import com.example.demo.infrastructure.LineItemDAO;
import com.example.demo.infrastructure.ProductDAO;
import com.example.demo.model.Cart;
import com.example.demo.model.LineItem;
import com.example.demo.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CartServiceTest {

    private Product product1;
    private Product product2;

    private List<LineItem> lineItems;

    private LineItemDAO lineItemDAO;
    private ProductDAO productDAO;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        lineItemDAO = mock(LineItemDAO.class);
        productDAO = mock(ProductDAO.class);
        cartService = new CartService(lineItemDAO, productDAO);

        lineItems = new ArrayList<>();
        given(lineItemDAO.findAll()).willReturn(lineItems);

        product1 = new Product("product-1", "product #1", 5000);
        product2 = new Product("product-2", "product #2", 3000);
        given(productDAO.find(product1.getId())).willReturn(product1);
        given(productDAO.find(product2.getId())).willReturn(product2);
    }

    @Test
    @DisplayName("장바구니가 비어있으면 총가격은 0원")
    void totalPriceIsZero() {
        given(lineItemDAO.findAll()).willReturn(List.of());

        Cart cart = cartService.getCart();

        assertThat(cart.getTotalPrice()).isEqualTo(0);
    }

    @Test
    @DisplayName("장바구니에 있는 하나의 상품의 가격을 모두 더해서 총 가격을 구한다.")
    void calculateTotalPriceWithOneLineItem() {
        int quantity = 2;
        addProductInCart(product1, quantity);

        Cart cart = cartService.getCart();

        assertThat(cart.getTotalPrice()).isEqualTo(product1.getPrice() * quantity);
    }

    @Test
    @DisplayName("장바구니에 있는 여러 상품의 가격을 모두 더해서 총 가격을 구한다.")
    void calculateTotalPriceWithManyLineItems() {
        int quantity1 = 2;
        int quantity2 = 3;
        addProductInCart(product1, quantity1);
        addProductInCart(product2, quantity2);

        Cart cart = cartService.getCart();

        assertThat(cart.getTotalPrice()).isEqualTo(product1.getPrice() * quantity1
                + product2.getPrice() * quantity2);
    }

    // 상품담기

    @Test
    @DisplayName("비어있는 장바구니에 상품 담기")
    void addProduct(){
        String productId = product1.getId();
        int quantity = 1;

        cartService.addProduct(productId, quantity);

        verify(lineItemDAO).add(argThat(lineItem ->
                lineItem.getProductId().equals(productId)
                 && lineItem.getQuantity() == quantity));
    }

    @Test
    @DisplayName("장바구니에 없는 상품 담기")
    void addNewProduct(){
        String productId = product1.getId();
        int quantity = 1;

        lineItems.add(new LineItem(product2.getId(), 5));

        cartService.addProduct(productId, quantity);

        verify(lineItemDAO).add(argThat(lineItem ->
                lineItem.getProductId().equals(productId)
                        && lineItem.getQuantity() == quantity));
    }

    @Test
    @DisplayName("장바구니에 이미있는 상품 담기")
    void addExistingProduct(){
        String productId = product1.getId();
        int oldQuantity = 1;
        int newQuantity = 2;

        lineItems.add(new LineItem(product1.getId(), oldQuantity));

        cartService.addProduct(productId, newQuantity);

        verify(lineItemDAO).upadate(argThat(lineItem ->
                lineItem.getProductId().equals(productId)
                        && lineItem.getQuantity() == oldQuantity + newQuantity));
    }



    private void addProductInCart(Product product, int quantity) {
        String id = "item-"+(lineItems.size() +1);
        LineItem lineItem = new LineItem(id, product.getId(), quantity);
        lineItems.add(lineItem);
    }
}
