
package com.project.billingSoftware.controller;

import com.project.billingSoftware.model.Product;
import com.project.billingSoftware.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products") // ✅ Ensures all product-related routes start with /products
public class ProductController {

	@Autowired
	private ProductService productService;

	// ✅ Show Product List
	@GetMapping("")
	public String viewProductList(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "products"; // ✅ Should match src/main/resources/templates/products.html
	}

	// ✅ Show Add Product Form
	@GetMapping("/add")
	public String showAddProductForm(Model model) {
		model.addAttribute("product", new Product());
		return "add-product"; // ✅ Should match src/main/resources/templates/add-product.html
	}

	// ✅ Save Product
	@PostMapping("/save")
	public String saveProduct(@ModelAttribute Product product) {
		productService.saveProduct(product);
		return "redirect:/products";
	}

	// ✅ Edit Product
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		Product product = productService.getProductById(id).orElse(null);
		if (product == null) {
			return "redirect:/products"; // Redirect if product not found
		}
		model.addAttribute("product", product);
		return "edit-product"; // ✅ Should match src/main/resources/templates/edit-product.html
	}

	@PostMapping("/update/{id}")
	public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
		product.setId(id);
		productService.saveProduct(product);
		return "redirect:/products";
	}

	@GetMapping("/list")
	public ResponseEntity<?> getAllProducts() {
		List<Product> products = productService.getAllProducts();

		if (products == null || products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products available.");
		}

		return ResponseEntity.ok(products);
	}

	@GetMapping("/list-view")
	public String showProductListPage(Model model) {
		List<Product> products = productService.getAllProducts(); // Fetch products from DB
		model.addAttribute("products", products); // Pass product list to the view
		return "products"; // Ensure this matches your template file name
	}

	@PutMapping("/{id}/updateStock")
	public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestParam int quantity) {
		productService.updateStock(id, quantity);
		return ResponseEntity.ok("Stock updated successfully");
	}

	// ✅ Delete Product
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return "redirect:/products";
	}
}
