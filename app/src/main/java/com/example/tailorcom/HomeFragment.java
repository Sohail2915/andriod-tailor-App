package com.example.tailorcom;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView testing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecyclerView = view.findViewById(R.id.category_recylerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Brands"));
        categoryModelList.add(new CategoryModel("link", "Designing"));
        categoryModelList.add(new CategoryModel("link", "T-Shirt Printing"));
        categoryModelList.add(new CategoryModel("link", "Casual Shawls"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));
        categoryModelList.add(new CategoryModel("link", "Home"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        //////// Banner slider

        List<SliderModel> sliderModelsList = new ArrayList<SliderModel>();
        sliderModelsList.add(new SliderModel(R.mipmap.banner2, "#077AE4"));
        sliderModelsList.add(new SliderModel(R.mipmap.banner3, "#077AE4"));
        sliderModelsList.add(new SliderModel(R.mipmap.banner, "#077AE4"));

        sliderModelsList.add(new SliderModel(R.mipmap.banner1, "#077AE4"));
        sliderModelsList.add(new SliderModel(R.mipmap.banner2, "#077AE4"));

        sliderModelsList.add(new SliderModel(R.mipmap.banner3, "#077AE4"));
        sliderModelsList.add(new SliderModel(R.mipmap.banner, "#077AE4"));
        sliderModelsList.add(new SliderModel(R.mipmap.banner1, "#077AE4"));
        /*sliderModelsList.add(new SliderModel(R.mipmap.mark));
        sliderModelsList.add(new SliderModel(R.mipmap.cart_black));

        sliderModelsList.add(new SliderModel(R.mipmap.red_email));
        sliderModelsList.add(new SliderModel(R.mipmap.home_icon));
        sliderModelsList.add(new SliderModel(R.mipmap.green_email));
        sliderModelsList.add(new SliderModel(R.mipmap.add_profile_picture));
        sliderModelsList.add(new SliderModel(R.mipmap.error_icon));
        sliderModelsList.add(new SliderModel(R.mipmap.add1));
        sliderModelsList.add(new SliderModel(R.mipmap.mark));
        sliderModelsList.add(new SliderModel(R.mipmap.cart_black));


        sliderModelsList.add(new SliderModel(R.mipmap.red_email));
        sliderModelsList.add(new SliderModel(R.mipmap.home_icon));*/



        /////// Banner slider

        /////// Horizontal Product Layout

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.custom_error_icon, "3pc Suit", "Black Cotton", "Rs.6550/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.red_email, "3pc Suit", "Black Cotton", "Rs.6550/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.green_email, "3pc Suit", "Black Cotton", "Rs.6550/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.cart_black, "3pc Suit", "Black Cotton", "Rs.6550/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.custom_error_icon, "3pc Suit", "Black Cotton", "Rs.6550/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.bell, "3pc Suit", "Black Cotton", "Rs.6550/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.cart_black, "3pc Suit", "Black Cotton", "Rs.6550/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.custom_error_icon, "3pc Suit", "Black Cotton", "Rs.6550/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.cart_black, "3pc Suit", "Black Cotton", "Rs.6550/-"));

        /////// Horizontal Product Layout
        ///////////////////////
        testing = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManger = new LinearLayoutManager(getContext());
        testingLayoutManger.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManger);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0, sliderModelsList));
        homePageModelList.add(new HomePageModel(1, R.mipmap.stripadd, "#000000"));
        //homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModelList));
        //homePageModelList.add(new HomePageModel(1, R.mipmap.stripadd, "#ff0000"));
        //homePageModelList.add(new HomePageModel(3,"Deals of thr day",horizontalProductScrollModelList));
        //homePageModelList.add(new HomePageModel(2,"Deals of thr day",horizontalProductScrollModelList));
        //homePageModelList.add(new HomePageModel(1, R.mipmap.banner, "#ffff00"));
        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        ///////////////////////

        return view;
    }


}
