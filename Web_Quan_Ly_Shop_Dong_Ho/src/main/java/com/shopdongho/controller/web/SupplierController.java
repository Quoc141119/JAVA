package com.shopdongho.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shopdongho.model.ProductModel;
import com.shopdongho.service.ProductService;
import com.shopdongho.service.SupplierService;

@Controller(value = "supplierControllerOfWeb")
@RequestMapping(value = "/supplier") 
public class SupplierController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired 
	private SupplierService supplierService;
	
	@RequestMapping(value = "/list")
	public String showSupplier(Model model, HttpServletRequest request) {
		model.addAttribute("lstSupplier", supplierService.findAll());
		model.addAttribute("lstProductTopSell", productService.getTopSellId());
		
		int start = 0, end = 9;
		if(request.getParameter("start") != null)
			start = Integer.parseInt(request.getParameter("start"));
		if(request.getParameter("end") != null)
			end = Integer.parseInt(request.getParameter("end"));

		if(request.getParameter("id") != null) {
			int idSupplier = Integer.parseInt(request.getParameter("id"));
			List<ProductModel> products = productService.findProductBySupplierID(idSupplier);
			request.setAttribute("kqProducts", products);
			
			if(products.size() < 9)
				end = products.size();
			
			model.addAttribute("lstProduct", productService.getListByPage(products, start, end));
		}else {
			List<ProductModel> products = productService.getAllProductInStock();
			request.setAttribute("kqProducts", products);
			if(products.size() < 9)
				end = products.size();
			
			model.addAttribute("lstProduct", productService.getListByPage(products, start, end));
		}
		return "web/supplier";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model, HttpServletRequest request) {
		model.addAttribute("lstSupplier", supplierService.findAll());
		model.addAttribute("lstProductTopSell", productService.getTopSellId());
		if(request.getParameter("name") != null) {
			String searchName = request.getParameter("name");
			List<ProductModel> kqProducts = new ArrayList<ProductModel>();
			kqProducts = productService.searchProductByName(searchName);
			model.addAttribute("lstProduct", kqProducts);
			request.setAttribute("kqProducts", kqProducts);
		}
		return "web/supplier";
	}
	
}
