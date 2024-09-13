package com.foodorder.Online_Food_Order.entity;

import java.util.HashMap;
import java.util.Map;



public enum Category {

	   PIZZA(0),
	    BIRYANI(1),
	    BURGER(2),
	    FULLMEALS(3),
	    SANDWICH(4),
	    CHICKENFRY(5),
	    ROLES(6),
	    SOUP(7),
		CURRY(8),
		BEVERAGES(9),
		MANCHURIAN(10);
	

	    private int value;
	    private static Map map = new HashMap<>();

	    private Category(int value) {
	        this.value = value;
	    }

	    static {
	        for (Category category : Category.values()) {
	            map.put(category.value, category);
	        }
	    }

	    public static Category valueOf(int category) {
	        return (Category) map.get(category);
	    }

	    public int getValue() {
	        return value;
	    }
	

}
