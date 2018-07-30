package com.dicoding.hendropurwoko.mysubmission05.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.hendropurwoko.mysubmission05.activity.DetailActivity;
import com.dicoding.hendropurwoko.mysubmission05.activity.MainActivity;
import com.dicoding.hendropurwoko.mysubmission05.database.MovieModel;
import com.dicoding.hendropurwoko.mysubmission05.R;

import java.util.ArrayList;

public class CPAdapter extends RecyclerView.Adapter<CPAdapter.ViewHolder> {
    Context context;

    private int REQUEST_CODE = 100;
    ArrayList<MovieModel> movieModels = new ArrayList<>();
    Context c ;

    private Cursor list;

    public CPAdapter(Context c) {this.c = c; }

    public void setList(Cursor list) {
        this.list = list;
    }

    @Override
    public CPAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_layout, parent, false);
        return new ViewHolder(view);
    }

    private MovieModel getItem(int position){
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieModel(list);
    }

    @Override
    public void onBindViewHolder(CPAdapter.ViewHolder holder, final int position) {
        final MovieModel movieModel = getItem(position);

        holder.tvTitle.setText(movieModel.getTitle());
        holder.tvOverview.setText(movieModel.getOverview());
        holder.tvReleaseDate.setText(movieModel.getRelease_date());

        Glide.with(c)
                .load(movieModel.getPoster())
                .apply(new RequestOptions().override(350, 350))
                .into(holder.ivPoster);

        holder.btDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(movieModel.getId()));
                bundle.putString("title", movieModel.getTitle().toString().trim());
                bundle.putString("overview", movieModel.getOverview().toString().trim());
                bundle.putString("release_date", movieModel.getRelease_date().toString().trim());
                bundle.putString("popularity", movieModel.getPopularity().toString().trim());
                bundle.putString("poster", movieModel.getPoster().toString().trim());
                bundle.putString("favorite", String.valueOf(MainActivity.stFavorite));

                Intent detailIntent = new Intent(v.getContext(), DetailActivity.class);
                detailIntent.putExtras(bundle);
                ((Activity) context).startActivityForResult(detailIntent, REQUEST_CODE);
            }
        });

        holder.btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, c.getResources().getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_SUBJECT, movieModels.get(position).getTitle().toString().trim());
                intent.putExtra(Intent.EXTRA_TEXT, movieModels.get(position).getTitle().toString().trim() + "\n\n" + movieModels.get(position).getOverview().toString().trim());
                v.getContext().startActivity(Intent.createChooser(intent, c.getResources().getString(R.string.share)));
            }
        });
    }

    public void addItem(ArrayList<MovieModel> movieList) {
        this.movieModels = movieList;
        notifyDataSetChanged();
    }

    public ArrayList<MovieModel> getListItems() {
        return this.movieModels;
    }

    public int getItemCount() {
        return list.getCount();
    }

    public void replaceAll(Cursor items) {
        list = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview, tvReleaseDate, tvFavorite;
        ImageView ivPoster;
        Button btDetail, btShare;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title_now_playing);
            tvOverview = (TextView)itemView.findViewById(R.id.tv_overview_now_playing);
            tvReleaseDate = (TextView)itemView.findViewById(R.id.tv_release_date_now_playing);
            ivPoster = (ImageView)itemView.findViewById(R.id.iv_poster_now_playing);
            btDetail = (Button)itemView.findViewById(R.id.bt_detail_now_playing);
            btShare = (Button)itemView.findViewById(R.id.bt_share_now_playing);
            tvFavorite = (TextView) itemView.findViewById(R.id.tv_favorite);
        }
    }
}