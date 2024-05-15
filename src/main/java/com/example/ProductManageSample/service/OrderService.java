package com.example.ProductManageSample.service;

import com.example.ProductManageSample.model.Orders;
import com.example.ProductManageSample.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//OrderServiceは、注文に関連するビジネスロジックを提供する
//OrderRepositoryを使用してデータベース操作を行う

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 指定されたIDの発注を取得
     *
     * @param id 発注ID
     * @return 指定されたIDの発注、存在しない場合はnull
     */
    public Orders getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * 全ての発注を取得
     *
     * @return 全ての爬虫リスト
     */
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * 商品名に基づいて発注を検索
     *
     * @param name 商品名
     * @return 指定された商品名に一致する発注
     */
    public Orders findOrderByName(String name) {
        return orderRepository.findByName(name);
    }

    /**
     * 指定されたIDの発注を削除
     *
     * @param id 発注ID
     */
    public void deleteOrder(Long id) {
        Orders orders = getOrderById(id);
        if (orders != null) {
            orderRepository.delete(orders);
        }
    }

    /**
     * 発注を保存
     *
     * @param order 保存する発注
     */
    public void saveOrder(Orders order) {
        orderRepository.save(order);
    }
}