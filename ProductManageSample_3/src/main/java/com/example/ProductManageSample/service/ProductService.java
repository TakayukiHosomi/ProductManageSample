package com.example.ProductManageSample.service;

import com.example.ProductManageSample.model.Products;
import com.example.ProductManageSample.repository.OrderRepository;
import com.example.ProductManageSample.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

 //ProductServiceは、商品に関連するビジネスロジックを提供する
 //ProductRepositoryを使用してデータベース操作を行う

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 全ての商品を取得
     *
     * @return 全ての商品リスト
     */
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * 指定されたIDの商品を取得
     *
     * @param id 商品ID
     * @return 指定されたIDの商品、存在しない場合はnull
     */
    public Products getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * 商品名に基づいて商品を検索
     *
     * @param name 商品名
     * @return 指定された商品名に一致する商品
     */
    public Products findProductByName(String name) {
        return productRepository.findByName(name);
    }

    /**
     * 新しい商品を追加
     *
     * @param product 追加する商品
     * @return 追加された商品
     */
    public Products addProduct(Products product) {
        return productRepository.save(product);
    }

    /**
     * 指定されたカテゴリに属する商品を取得
     *
     * @param category カテゴリID
     * @return 指定されたカテゴリに一致する商品リスト
     */
    public List<Products> getProductsByCategory(int category) {
        return productRepository.findByCategory(category);
    }

    /**
     * 発注数が0でない商品を取得
     *
     * @return 発注数が0でない製品リスト
     */
    public List<Products> getOrderedProducts() {
        return productRepository.findByOrderCountGreaterThan(0); // 0より大きい発注数の商品を取得
    }

    /**
     * 製品を保存
     *
     * @param product 保存する商品
     */
    public void saveProduct(Products product) {
        productRepository.save(product);
    }

    /**
     * 指定されたIDの商品を更新
     *
     * @param id 更新する商品ID
     * @param newProduct 新しい商品情報
     * @return 更新された商品、存在しない場合はnull
     */
    public Products updateProduct(Long id, Products newProduct) {
        Products existingProduct = getProductById(id);
        if (existingProduct != null) {
            existingProduct.setName(newProduct.getName());
            existingProduct.setStock(newProduct.getStock());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    /**
     * 指定されたIDの商品を削除します。
     *
     * @param id 商品ID
     * @return 削除が成功したかどうか
     */
    public boolean deleteProduct(Long id) {
        Products products = getProductById(id);
        if (products != null) {
            productRepository.delete(products);
            return true;
        }
        return false;
    }

    /**
     * 発注数を更新します。
     *
     * @param productId 商品ID
     * @param orderCount 新しい発注数
     */
    public void updateOrderCount(Long productId, int orderCount) {
        Products product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setOrderCount(orderCount);
            productRepository.save(product);
        }
    }
}