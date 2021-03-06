package com.dicoding.hendropurwoko.moviefavorite;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dicoding.hendropurwoko.moviefavorite.adapter.CPAdapter;

import static com.dicoding.hendropurwoko.moviefavorite.database.MovieContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    private CPAdapter cpAdapter;
    ProgressDialog progressDialog;
    RecyclerView rvCP;

    private final int LOAD_NOTES_ID = 110;
    private Cursor list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Favorite Movies");
        getSupportActionBar().setSubtitle("Content Provider");

        rvCP = (RecyclerView) findViewById(R.id.recycler_view_cp);

        new LoadDataContentProvider().execute();
    }

    private class LoadDataContentProvider extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage(getString(R.string.please_wait));//ambil resource string
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Cursor aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing())
                progressDialog.dismiss();

            display();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            list = getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );

            return list;
        }
    }

    private void display(){
        cpAdapter = new CPAdapter(MainActivity.this);
        rvCP.setLayoutManager(new LinearLayoutManager(this));
        rvCP.setAdapter(cpAdapter);
        cpAdapter.setList(list);
        rvCP.setAdapter(cpAdapter);
    }
}
