package com.example.foodzen;

import android.widget.Filter;

import com.example.foodzen.CollectionAdapters.AdapterRestaurantItems;
import com.example.foodzen.CollectionModels.ModelAddProducts;
import com.example.foodzen.CollectionModels.ModelFoodItem;

import java.util.ArrayList;

public class FilterFoodItems extends Filter {

    private AdapterRestaurantItems adapterRestaurantItems;
    private ArrayList<ModelAddProducts>filteraddList;


    public FilterFoodItems(AdapterRestaurantItems adapterRestaurantItems, ArrayList<ModelAddProducts> modelAddProductsArrayList) {
        this.adapterRestaurantItems = adapterRestaurantItems;
        this.filteraddList = modelAddProductsArrayList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults=new FilterResults();
        if(constraint!=null && constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<ModelAddProducts>filteredModels=new ArrayList<>();

            for(int i=0;i<filteraddList.size();i++){

                if(filteraddList.get(i).getpName().toUpperCase().contains(constraint)){
                    filteredModels.add(filteraddList.get(i));
                }
            }

            filterResults.count=filteredModels.size();
            filterResults.values=filteredModels;

        }
        else{
            filterResults.count=filteraddList.size();
            filterResults.values=filteraddList;
        }
        return filterResults;
    }


    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterRestaurantItems.modelAddProductsArrayList=(ArrayList<ModelAddProducts>) results.values;
        adapterRestaurantItems.notifyDataSetChanged();
    }

}
