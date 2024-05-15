package com.example.ProductManageSample.controller;

import com.example.ProductManageSample.model.Products;
import com.example.ProductManageSample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// このコントローラーはCURLコマンドやPostmanなどを使用してデータベース操作を管理します
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /*private final ProductService productService;

    // ProductServiceのコンストラクタ
    public ProductController(ProductService productService) {
        this.productService = productService;
    }*/

    // 全ての製品を取得する
    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }

    // 指定されたIDの商品を取得する
    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
        Products products = productService.getProductById(id);
        if (products != null) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 商品を追加する
    @PostMapping
    public ResponseEntity<Products> addProduct(@RequestBody Products products) {
        Products newProduct = productService.addProduct(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    // 指定されたIDの商品を更新する
    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id, @RequestBody Products newProduct) {
        Products updatedProduct = productService.updateProduct(id, newProduct);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 特定のIDの商品を削除する
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean result = productService.deleteProduct(id);
        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
