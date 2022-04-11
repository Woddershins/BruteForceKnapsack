package main;

import java.util.*;
import java.io.*;
/*
 * Dylan Kiefling
 * 1-29
 * A brute force knapsack packer
 */
public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		//declare variableas
		int numItems, optimalPackingIndex = -1;
		long startTime, endTime, totalTime;
		double weight, size, value, maxValue = 0, weightCapacity, sizeCapacity, totalValue = 0;
		String weights, sizes, values;
		String[] weightList, sizeList, valueList;
		ArrayList<Item> tempList = new ArrayList<Item>();
		ArrayList<Item> optimalList = new ArrayList<Item>();
		ArrayList<ArrayList<Item>> copyList = new ArrayList<ArrayList<Item>>();
		ArrayList<ArrayList<Item>> subsetList = new ArrayList<ArrayList<Item>>();
		
		// create scanner
		Scanner kbReader = new Scanner(System.in);
		
		//read in inputs
		System.out.println("Enter knapsack weight capacity:");
		weightCapacity = kbReader.nextDouble();
		
		System.out.println("Enter knapsack size capacity:");
		sizeCapacity = kbReader.nextDouble();
		
		System.out.println("Enter number of items");
		numItems = kbReader.nextInt();
		kbReader.nextLine();
		
		System.out.println("Enter weights of items.");
		weights=kbReader.nextLine();
		weightList=weights.split(" ");

		System.out.println("Enter sizes of items.");
		sizes=kbReader.nextLine();
		sizeList=sizes.split(" ");

		System.out.println("Enter values of items.");
		values=kbReader.nextLine();
		valueList=values.split(" ");

		//used for run time of algorithm
		startTime = System.nanoTime();
		
		//creates array of items
		Item[] itemArray = new Item[numItems];

		//adds inputs to item array
		for(int i=0;i<numItems;i++) {
			weight=Double.valueOf(weightList[i]);
			size=Double.valueOf(sizeList[i]);
			value=Double.valueOf(valueList[i]);
			Item temp = new Item(weight,size,value);
			itemArray[i] = temp;

		}
		
		//creates subsets and adds them to subset list
		for(int i=0;i<numItems;i++) {
			if(subsetList.isEmpty()) {
				tempList.add(itemArray[0]);
				subsetList.add(tempList);
			}else {
				for(ArrayList<Item> item: subsetList) {
					ArrayList<Item> temp=new ArrayList<>(item);
					copyList.add(temp);
				}
				
				for (ArrayList<Item> currentList : copyList) {
					currentList.add(itemArray[i]);
				}
				
				ArrayList<Item> lastItem=new ArrayList<Item>();
				lastItem.add(itemArray[i]);
				copyList.add(lastItem);
				subsetList.addAll(copyList);
				copyList.clear();
			}
		}
		
		//determines which packing is optimal
		//if no optional packing is found the flag remains -1
		for (int i=0; i<subsetList.size();i++) {
			weight = 0;
			size = 0;
			value =0;
			tempList.clear();
			tempList.addAll(subsetList.get(i));
			for (int j=0;j<tempList.size();j++) {
				weight += tempList.get(j).getWeight();
				size += tempList.get(j).getSize();
				value += tempList.get(j).getValue();
				if(size==sizeCapacity && weight==weightCapacity) {
					if(value>maxValue) {
						maxValue=value;
						optimalPackingIndex=i;
					}
				}
			}
		}
		
		//prints result
		if(optimalPackingIndex>=0) {
			System.out.println("Found an optimal packing:");
			optimalList = subsetList.get(optimalPackingIndex);
			for(int i=0;i<optimalList.size();i++) {
				System.out.printf("Item %d: %.2f, %.2f, %.2f\n", i+1,optimalList.get(i).getWeight(),optimalList.get(i).getSize(),optimalList.get(i).getValue());
				totalValue+=optimalList.get(i).getValue();
			}
			System.out.println("Total value: " + totalValue);
		}else {
			System.out.println("No Optimal Packing.");
		}
		
		//determines and prints run time
		endTime=System.nanoTime();
		totalTime=endTime-startTime;
		System.out.println("Total Run Time: " + totalTime/1000000000 + " seconds");
	}
}
