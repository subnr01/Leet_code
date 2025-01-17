#include <string>
#include <fstream>
#include "DynamicArray.h"
#include <iostream>
#include <stdexcept>
#include <vector>
#include <stdlib.h>

typedef float Element;





		//Default Constructor
		DynamicArray::DynamicArray(){
			dynamicArray=0;
			arraySize=0;
			arrayCapacity=0;
		}
		
		//Explicit Value Constructor
		DynamicArray::DynamicArray(int size, Element e){
			
			arraySize=0;
			
			
			if(size>=0){
			arrayCapacity=size;
			dynamicArray=new Element[size];
			for(int i=0; i<size; i++){
				dynamicArray[i]=e;
				arraySize=i+1;
			}}
			else{
			std::cout<<"Incorrect Size inputted"<<std::endl;
			exit(EXIT_FAILURE);
			
			}
		}
		
		//Destructor
		DynamicArray::~DynamicArray(){
		delete []dynamicArray;
		}

		//Add to end

		void DynamicArray::push_back(Element e){
			//double check to see if array size=capacity	
			//if size is at capacity increase capacity
			//if not add one to size and element
			//check to see if element e is not null?
			try{
			if(arraySize>=arrayCapacity){
				arrayCapacity=arrayCapacity*2;
				Element* temp=new Element[arrayCapacity];
				for(int i=0; i<arraySize; i++){
					temp[i]=dynamicArray[i];
				}
				arraySize++;
				temp[arraySize]=e;
				
				delete[]dynamicArray;
				dynamicArray=temp;
				

				
			}
			else if(e==NULL){
				int temp1;
				temp1=e;
				std::cout<<temp1<<" not valid";
			}
			else{

				sizeTemp=arraySize+1
				dynamicArray[sizeTemp]=e;
				arraySize+=1;
			}
			
			}
			catch(std::out_of_range& oor){
				std::cerr<<"Out of range error"<<oor.what()<<'\n';
			}
	

		}
		
		//Remove from end and return
		Element DynamicArray::pop_back(){
			Element temp=-1;
			if(arraySize > 0 )
			{
			temp = dynamicArray[arraySize];
			arraySize--;
			 
			  
			  
			}
			
		
			return temp;
			
			}

		
		
		//Search, return position in array, -1 if not found
		int DynamicArray::search(Element e){
			int temp=0;
			for(int i=0; i<arraySize;i++){	
				if(dynamicArray[i]==e){
					temp=i;
					
				}
			}
			if(temp==0){
				return -1;
			}
			else{
				return temp;
			}

				
			
		}

		//Indicate whether or not the provided index is valid
		bool DynamicArray::valid_index(int index){
			if(index<0){
				return false;
			}
			else if(index==NULL){
				return false;
			}
			else if(index>=arraySize){
				return false;
			}
			else{
				return true;
			}
		

		}

		//Return the size of the array (number of elements currently in the array)
		int DynamicArray::size(){
			return arraySize;
		}

		//Return the capacity of the array (number of elements the array can currently hold)
		int DynamicArray::capacity(){
			return arrayCapacity;
		}

