package com.store.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.store.models.Order;
import com.store.models.OrderItem;
import com.store.models.Product;
import com.store.services.OrderItemService;
import com.store.services.OrderService;

@Controller
public class OrderController
{
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderItemService orderItemService;

	@GetMapping("/orderView")
	public String view(Model model)
	{
		model.addAttribute("orders", orderService.getOrders());
		return "orderView.html";
	}
	
	@GetMapping("/orderAdd")
	public String add(Model model)
	{
		model.addAttribute("orders", new Product());
		return "productAdd.html";
	}

	@PostMapping("/orderAddUpdate")
	public String addPost(@Validated Order order, BindingResult bindingResult, Model model)
	{
		orderService.addOrder(order);
		model.addAttribute("orders", orderService.getOrders());
		return "orderView.html";
	}

	@PostMapping("/orderEdit")
	public String edit(@Validated Order order, BindingResult bindingResult, Model model)
	{
		Order ord = orderService.getOrder(order.getId());
		System.out.println(ord.getUser());
		model.addAttribute("order", orderService.getOrder(order.getId()));
		return "orderEdit.html";
	}

	@PostMapping("/orderUpdate")
	public String editPost(@Validated Order order, BindingResult bindingResult, Model model)
	{
		Order newOrder = orderService.getOrder(order.getId());
		newOrder.setStatus(order.getStatus());
		orderService.updateOrder(order.getId(), newOrder);
		model.addAttribute("orders", orderService.getOrders());
		return "orderView.html";
	}
	
	@PostMapping("/orderDelete")
	public String delete(@Validated Order order, BindingResult bindingResult, Model model)
	{
		orderService.removeOrder(order.getId());
		model.addAttribute("orders", orderService.getOrders());
		return "orderView.html";
	}
	
	@PostMapping("/orderDetails")
	public String details(@Validated Order order, BindingResult bindingResult, Model model)
	{
		List<OrderItem> items = orderItemService.getOrderItemsByOrderId(order.getId());
		System.out.println(items);
		model.addAttribute("items", items);
		model.addAttribute("order", orderService.getOrder(order.getId()));
		return "orderDetails.html";
	}
}
