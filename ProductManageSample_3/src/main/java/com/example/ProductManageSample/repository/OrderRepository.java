package com.example.ProductManageSample.repository;

import com.example.ProductManageSample.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Ordersエンティティに対するデータアクセス操作を提供するインターフェースです。
@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    //指定された商品名に一致する注文検索します
    Orders findByName(String name);
}