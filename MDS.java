/** Starter code for MDS
 *  @author rbk
 */

// Change to your net id
package sxa190016;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

// If you want to create additional classes, place them in this file as subclasses of MDS

public class MDS {
	
	public static class Value {
		Money price;
		List<Long> desc;
		
		public Value(Money price, List<Long> desc)
		{
			this.price = price;
			this.desc = getList(desc);
		}
		
		public static List<Long> getList(List<Long> desc)
		{
			List<Long> newList = new LinkedList<Long>();
			for(long x : desc)
			{
				newList.add(x);
			}
			return newList;
		}
	}
	
    // Add fields of MDS here
	TreeMap<Long, Value> tree;
	HashMap<Long, TreeSet<Long>> table;
	
    // Constructors
    public MDS() {
    	this.tree = new TreeMap<Long, Value>();
    	this.table = new HashMap<Long, TreeSet<Long>>();
    }

    /* Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated. 
       Returns 1 if the item is new, and 0 otherwise.
    */
    public int insert(long id, Money price, java.util.List<Long> list) {
    	int result;
    	Value value = this.tree.get(id);
    	if(value==null)
    	{
    		result = 1;
    		this.tree.put(id, new Value(price, list));
    		for(long x: list)
    		{
    			TreeSet<Long> set = this.table.get(x);
    			if(set==null)
    			{
    				set = new TreeSet<Long>();
    				set.add(id);
    				this.table.put(x, set);
    			}
    			else
    			{
    				set.add(id);
    			}
    		}
    	}
    	else
    	{
    		result = 0;
    		if(list!=null && list.size()>0)
    		{
    			for(long x : value.desc)
    			{
    				TreeSet<Long> set = this.table.get(x);
    				if(set.size()<=1)
    				{
    					this.table.remove(x);
    				}
    				else
    				{
        				set.remove(id);
    				}
    			}
    			for(long x: list)
        		{
        			TreeSet<Long> set = this.table.get(x);
        			if(set==null)
        			{
        				set = new TreeSet<Long>();
        				set.add(id);
        				this.table.put(x, set);
        			}
        			else
        			{
        				set.add(id);
        			}
        		}
    			value.desc = Value.getList(list);
    		}
			value.price = price;
    	}
	return result;
    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    public Money find(long id) {
    	Value value = this.tree.get(id);
	return value==null?new Money():value.price;
    }

    /* 
       c. Delete(id): delete item from storage.  Returns the sum of the
       long ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
    */
    public long delete(long id) {
    	Value value = this.tree.remove(id);
    	long result = 0;
    	if(value!=null)
    	{
    		for(long x: value.desc)
    		{
    			result += x;
    			TreeSet<Long> set = this.table.get(x);
				if(set.size()<=1)
				{
					this.table.remove(x);
				}
				else
				{
    				set.remove(id);
				}
    		}
    	}
	return result;
    }

    /* 
       d. FindMinPrice(n): given a long int, find items whose description
       contains that number (exact match with one of the long ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
    */
    public Money findMinPrice(long n) {
	return new Money();
    }

    /* 
       e. FindMaxPrice(n): given a long int, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
    */
    public Money findMaxPrice(long n) {
	return new Money();
    }

    /* 
       f. FindPriceRange(n,low,high): given a long int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
    */
    public int findPriceRange(long n, Money low, Money high) {
	return 0;
    }

    /* 
       g. PriceHike(l,h,r): increase the price of every product, whose id is
       in the range [l,h] by r%.  Discard any fractional pennies in the new
       prices of items.  Returns the sum of the net increases of the prices.
    */
    public Money priceHike(long l, long h, double rate) {
	return new Money();
    }

    /*
      h. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    public long removeNames(long id, java.util.List<Long> list) {
    	Value value = this.tree.get(id);
    	long result = 0;
    	if(value!=null)
    	{
        	for(long x: list)
        	{
        		if(value.desc.remove(x))
        		{
        			result += x;
        			TreeSet<Long> set = this.table.get(x);
    				if(set.size()<=1)
    				{
    					this.table.remove(x);
    				}
    				else
    				{
        				set.remove(id);
    				}
        		}
        	}
    	}
	return result;
    }
    
    // Do not modify the Money class in a way that breaks LP3Driver.java
    public static class Money implements Comparable<Money> { 
	long d;  int c;
	public Money() { d = 0; c = 0; }
	public Money(long d, int c) { this.d = d; this.c = c; }
	public Money(String s) {
	    String[] part = s.split("\\.");
	    int len = part.length;
	    if(len < 1) { d = 0; c = 0; }
	    else if(part.length == 1) { d = Long.parseLong(s);  c = 0; }
	    else { d = Long.parseLong(part[0]);  c = (part[1].length()==1?Integer.parseInt(part[1])*10:Integer.parseInt(part[1])); }
	}
	public long dollars() { return d; }
	public int cents() { return c; }
	public int compareTo(Money other) { // Complete this, if needed
	    return 0;
	}
	public String toString() { return d + "." + String.format("%02d", c); }
    }
    
    public static void main(String args [])
    {
    	MDS mds =new MDS();
    	List<Long> list1 = new LinkedList<Long>();
    	list1.add(475l);
    	list1.add(1238l);
    	list1.add(9742l);
    	mds.insert(22, new Money("19.97"), list1);
    	for (Map.Entry<Long, MDS.Value> entry : mds.tree.entrySet()) {
		     System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue().price + " " + entry.getValue().desc);
		}
		
		for (Map.Entry<Long, TreeSet<Long>> entry : mds.table.entrySet()) {
		     System.out.println("Hash: " + entry.getKey() + ". Values: " + entry.getValue());
		}
    }

}
