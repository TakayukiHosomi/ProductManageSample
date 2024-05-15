package com.example.ProductManageSample.repository;

import com.example.ProductManageSample.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//ProductRepositoryはProductsエンティティに対するデータアクセス操作を提供するインターフェースです。
@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    //指定されたカテゴリの商品一覧を取得するメソッド
    List<Products> findByCategory(int category);

    //発注数が指定された値よりも大きい商品を取得するメソッド（ここでは0より大きいもの）
    List<Products> findByOrderCountGreaterThan(int orderCount);

    //商品名に基づいて商品を検索するメソッド
    Products findByName(String name);
}