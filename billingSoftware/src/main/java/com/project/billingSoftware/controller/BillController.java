package com.project.billingSoftware.controller;

import com.project.billingSoftware.model.Bill;
import com.project.billingSoftware.model.Order;
import com.project.billingSoftware.model.Product;
import com.project.billingSoftware.model.OrderStatus;
import com.project.billingSoftware.service.BillService;
import com.project.billingSoftware.service.OrderService;
import com.project.billingSoftware.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/bills")
public class BillController {

	@Autowired
	private BillService billService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/generate")
	public String showBillForm(Model model, @RequestParam(value = "error", required = false) String error) {
		model.addAttribute("bill", new Bill());
		model.addAttribute("products", productService.getAllProducts());
		model.addAttribute("errorMessage", error); // ✅ Show error if present
		return "bill/generate-bill";
	}

	@GetMapping("/view")
	public String viewAllBills(Model model) {
		List<Bill> bills = billService.getAllBills();
		model.addAttribute("bills", bills);
		return "bill/view-bills"; // ✅ Ensure this HTML file exists
	}

	@GetMapping("/view/{id}")
	public String viewBillById(@PathVariable Long id, Model model) {
		Optional<Bill> bill = billService.getBillById(id);
		if (!bill.isPresent()) {
			return "redirect:/bills/view?error=Bill not found";
		}
		model.addAttribute("bill", bill.get());
		return "bill/view-bills"; // Ensure this HTML exists
	}

	@DeleteMapping("/delete/{billNumber}")
	public ResponseEntity<String> deleteBill(@PathVariable String billNumber) {
		billService.deleteBillByNumber(billNumber);
		return ResponseEntity.ok("Bill deleted successfully");
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseEntity<?> saveBill(@RequestBody BillRequest request) {
		// Extracting data
		String customerName = request.getCustomerName();
		String customerEmail = request.getCustomerEmail();
		String customerPhone = request.getCustomerPhone();
		Double totalAmount = request.getTotalAmount();
		String paymentMethod = request.getPaymentMethod();
		List<Long> productIds = request.getProductIds();
		List<Integer> quantities = request.getQuantities();

		// Generate Bill Number
		String billNumber = "BILL-" + UUID.randomUUID().toString().substring(0, 8);

		StringBuilder productDetails = new StringBuilder();

		// Update stock and build product details
		for (int i = 0; i < productIds.size(); i++) {
			Optional<Product> optionalProduct = productService.getProductById(productIds.get(i));

			if (!optionalProduct.isPresent()) {
				return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Product not found"));
			}

			Product product = optionalProduct.get();
			int requestedQuantity = quantities.get(i);

			if (product.getStock() < requestedQuantity) {
				return ResponseEntity.badRequest()
						.body(Collections.singletonMap("error", "Insufficient stock for " + product.getName()));
			}

			product.setStock(product.getStock() - requestedQuantity);
			productService.saveProduct(product); // Update stock
			productDetails.append(product.getName()).append(" (").append(requestedQuantity).append("), ");
		}

		// ✅ Save Bill
		Bill bill = new Bill();
		bill.setBillNumber(billNumber);
		bill.setCustomerName(customerName);
		bill.setCustomerEmail(customerEmail);
		bill.setTotalAmount(totalAmount);
		bill.setPaymentMethod(paymentMethod);
		bill.setProductDetails(productDetails.toString());

		Bill savedBill = billService.saveBill(bill);
		System.out.println("Saved Bill ID: " + savedBill.getId());

		// ✅ Save Order
		Order order = new Order();
		order.setBill(savedBill);
		order.setCustomerName(customerName);
		order.setCustomerEmail(customerEmail);
		order.setCustomerPhone(customerPhone);
		order.setTotalAmount(totalAmount);
		order.setDate(LocalDateTime.now());
		order.setStatus(OrderStatus.PAID);

		Order savedOrder = orderService.saveOrder(order);
		System.out.println("Saved Order ID: " + savedOrder.getId());

		return ResponseEntity.ok(Collections.singletonMap("message", "Bill and Order saved successfully!"));
	}

}
