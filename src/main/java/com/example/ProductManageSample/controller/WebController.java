package com.example.ProductManageSample.controller;

import com.example.ProductManageSample.model.Orders;
import com.example.ProductManageSample.model.Products;
import com.example.ProductManageSample.service.OrderService;
import com.example.ProductManageSample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

// このコントローラーはウェブページからの操作を管理します
@Controller
public class WebController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    // 全ての商品を取得する（Html用）
    @GetMapping("/products/list")
    public String productList(Model model) {
        // productServiceから全ての商品を取得
        List<Products> products = productService.getAllProducts();
        // 取得した商品リストをモデルに追加
        model.addAttribute("products", products);
        // products.html テンプレートを返す
        return "products";
    }

    // カテゴリを選択した際の商品ページへのリダイレクト
    @GetMapping("/products/category")
    public String productListByCategory(@RequestParam int category, Model model) {
        // 指定されたカテゴリに基づいてproductServiceから商品を取得
        List<Products> products = productService.getProductsByCategory(category);
        // 取得した商品リストをモデルに追加
        model.addAttribute("products", products);
        // products.html テンプレートを返す
        return "products";
    }

    // 商品発注画面取得（Html用）
    @GetMapping("/ordering")
    public String showOrders(Model model) {
        // orderServiceから全ての注文を取得
        List<Orders> orders = orderService.getAllOrders();
        // 取得した注文リストをモデルに追加
        model.addAttribute("orders", orders);
        // order.html テンプレートを返す
        return "order";
    }

    // 注文確認画面を表示する
    @PostMapping("/orderConfirmation")
    public String showOrderConfirmation(@RequestParam Map<String, String> allParams, Model model) {
        // リクエストパラメータをモデルに追加
        model.addAttribute("orderParams", allParams);
        // order_confirmation.html テンプレートを返す
        return "order_confirmation";
    }

    // 注文を登録する
    @PostMapping("/registerOrder")
    public String registerOrder(@RequestParam Map<String, String> allParams) {
        // 全てのリクエストパラメータを処理
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            String productName = entry.getKey();
            int orderCount = Integer.parseInt(entry.getValue());

            // 注文が既に存在するかチェック
            Orders existingOrder = orderService.findOrderByName(productName);
            if (existingOrder != null) {
                // 既存の注文がある場合は、注文数を更新（新発注と既存発注を加算）
                existingOrder.setOrderCount(existingOrder.getOrderCount() + orderCount);
                orderService.saveOrder(existingOrder);
            } else {
                // 新しい発注を作成
                Orders order = new Orders();
                order.setName(productName);
                order.setOrderCount(orderCount);
                orderService.saveOrder(order);
            }
        }
        // ホームページにリダイレクト
        return "redirect:/";
    }

    // 注文を納品する
    @PostMapping("/deliverOrders")
    public ResponseEntity<?> deliverOrders() {
        try {
            // orderServiceから全ての注文を取得
            List<Orders> orders = orderService.getAllOrders();
            // 全ての注文を処理
            for (Orders order : orders) {
                String productName = order.getName();
                // productServiceから商品を名前で検索
                Products products = productService.findProductByName(productName);
                if (products != null) {
                    // 商品が見つかった場合は、在庫を更新
                    products.setStock(products.getStock() + order.getOrderCount());
                    productService.saveProduct(products);
                    // 発注中商品テーブルから削除
                    orderService.deleteOrder(order.getId());
                }
            }
            // 成功時は200 OKを返す
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // エラー発生時は500 内部サーバーエラーを返す
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}