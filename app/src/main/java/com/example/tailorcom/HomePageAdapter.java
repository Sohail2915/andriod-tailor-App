package com.example.tailorcom;

import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_ADD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_ad_layout, viewGroup, false);
                return new BannerSliderViewholder(bannerSliderView);
            case HomePageModel.STRIP_ADD_BANNER:
                View stripAddView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_ad_layout, viewGroup, false);
                return new StripAdBannerViewholder(stripAddView);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout, viewGroup, false);
                return new GridProductViewHolder (gridProductView);
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelsList();
                ((BannerSliderViewholder) viewHolder).setBannersSliderViewpager(sliderModelList);
                break;
            case HomePageModel.STRIP_ADD_BANNER:
                int resource = homePageModelList.get(position).getResource();
                String color = homePageModelList.get(position).getBackgroundColor();
                ((StripAdBannerViewholder) viewHolder).setStripAd(resource, color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontalLayoutTitle = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((HorizonatlProductViewHolder) viewHolder).setHorizontalProductLayout(horizontalProductScrollModelList, horizontalLayoutTitle);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridLayoutTitle = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewHolder) viewHolder).setGridProductLayout(gridProductScrollModelList, gridLayoutTitle);

                break;
            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
    }

    public class BannerSliderViewholder extends RecyclerView.ViewHolder {
        private ViewPager bannersSliderViewpager;
        private int currentPage = 2;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;

        public BannerSliderViewholder(@NonNull View itemView) {
            super(itemView);
            bannersSliderViewpager = itemView.findViewById(R.id.banner_slider_view_pagger);


        }

        private void setBannersSliderViewpager(final List<SliderModel> sliderModelsList) {
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelsList);
            bannersSliderViewpager.setAdapter(sliderAdapter);
            bannersSliderViewpager.setClipToPadding(false);
            bannersSliderViewpager.setPageMargin(20);

            bannersSliderViewpager.setCurrentItem(currentPage);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                }

                @Override
                public void onPageSelected(int i) {
                    currentPage = i;
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if (i == ViewPager.SCROLL_STATE_IDLE) {
                        pageLooper(sliderModelsList);
                    }
                }
            };
            bannersSliderViewpager.addOnPageChangeListener(onPageChangeListener);

            startBannerSlideShow(sliderModelsList);

            bannersSliderViewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(sliderModelsList);
                    stopBannerSlideShow();

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow(sliderModelsList);
                    }
                    return false;
                }
            });

        }

        private void pageLooper(List<SliderModel> sliderModelsList) {
            if (currentPage == sliderModelsList.size() - 2) {
                currentPage = 2;
                bannersSliderViewpager.setCurrentItem(currentPage, false);
            }
            if (currentPage == 1) {
                currentPage = sliderModelsList.size() - 3;
                bannersSliderViewpager.setCurrentItem(currentPage, false);
            }

        }

        private void startBannerSlideShow(final List<SliderModel> sliderModelsList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelsList.size()) {
                        currentPage = 1;
                    }
                    bannersSliderViewpager.setCurrentItem(currentPage++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }

        private void stopBannerSlideShow() {
            timer.cancel();
        }
    }

    public class StripAdBannerViewholder extends RecyclerView.ViewHolder {
        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;
        public StripAdBannerViewholder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);
        }

        private void setStripAd(int resource, String color) {
            stripAdImage.setImageResource(resource);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class HorizonatlProductViewHolder extends RecyclerView.ViewHolder{
        private TextView horizonatlLayoutTitle;
        private Button horizonatlLayoutViewAllBtn;
        private RecyclerView horizontalRecyclerView;
        public HorizonatlProductViewHolder(@NonNull View itemView) {
            super(itemView);
            horizonatlLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizonatlLayoutViewAllBtn = itemView.findViewById(R.id.horizontal_scroll_viewall_btn);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recylerview);
        }
        private void setHorizontalProductLayout (List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title){
            horizonatlLayoutTitle.setText(title);
            if (horizontalProductScrollModelList.size() > 8){
                horizonatlLayoutViewAllBtn.setVisibility(View.VISIBLE);
            }else {
                horizonatlLayoutViewAllBtn.setVisibility(View.INVISIBLE);
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);
            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public class GridProductViewHolder extends RecyclerView.ViewHolder{
        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllbtn;
        private GridView gridView;

        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllbtn = itemView.findViewById(R.id.grid_layout_product_viewall_btn);
            gridView = itemView.findViewById(R.id.grid_product_layout_gridview);
        }
        private void setGridProductLayout(List<HorizontalProductScrollModel>horizontalProductScrollModelList,String title){
            gridLayoutTitle.setText(title);
            gridView.setAdapter(new GridProductVLayoutAdapter(horizontalProductScrollModelList));
        }
    }
}
