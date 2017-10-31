package videoshop.controller;

import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import videoshop.model.Disc;

@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes("wishlist")

class WishlistController{
	private final OrderManager<Order> orderManager;

	/**
	 * Creates a new {@link CartController} with the given {@link OrderManager}.
	 * 
	 * @param orderManager must not be {@literal null}.
	 */
	@Autowired
	public WishlistController(OrderManager<Order> orderManager) {

		Assert.notNull(orderManager, "OrderManager must not be null!");
		this.orderManager = orderManager;
	}

	
	@ModelAttribute("wishlist")
	public Cart initializeWishlist() {
		return new Cart();	
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	String deletefromWishlist(@RequestParam("pid") Disc disc,@RequestParam("delete") String id, @ModelAttribute Cart wishlist) {
	//Funktion zum löschen einzelner Wunschlistenelemente
	wishlist.removeItem(id);
	return "wishlist";
	}


	@RequestMapping(value = "/wishlist", method = RequestMethod.POST)
	String addToWishlist(@RequestParam("pid") Disc disc, @ModelAttribute Cart wishlist) {
	int i=1;
	wishlist.addOrUpdateItem(disc,Quantity.of(i));
	System.out.println(disc.getName());
	System.out.println(wishlist.isEmpty());
		switch (disc.getType()) {
			case DVD:
				return "redirect:dvdCatalog";
			case BLURAY:
				return "redirect:blurayCatalog";
			default:
				return "redirect:blurayCatalog"; }
	}
	
	@RequestMapping(value = "/wishlist", method = RequestMethod.GET)
	String openWishlist() {
	return "wishlist";
	}
}




