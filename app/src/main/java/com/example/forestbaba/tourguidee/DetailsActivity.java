package com.example.forestbaba.tourguidee;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    String url = "https://www.newyorkpass.com/new-york-attractions/";
    int position;
    String newUrl;
    public ImageView imagevater;
    public TextView title1, overview1, openingtimes1, gettingthere1, gettingthere2, attractionsNearby, doyouknow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activiy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tour Guide");
        setSupportActionBar(toolbar);
        initUi();


        String sessionId = getIntent().getStringExtra("POSITION");
        position = Integer.parseInt(sessionId);
        new Fetch().execute();

    }


    private class Fetch extends AsyncTask<Void, Void, Void> {
        String title, sub;
        Document document;
        Elements elements, elements2, elements3, elements4, elements5, elements6, elements7;
        String cv, cv2, cv3, cv4, cv5, cv6, cv7, cv8;
        Element e1;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(DetailsActivity.this);
            mProgressDialog.setTitle("Tour Guide");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                document = Jsoup.connect(url).get();
                Elements atag2 = document.body().select(".att_strip li a[href]");

                newUrl = atag2.get(position).attr("abs:href");
                document = Jsoup.connect(newUrl).get();

                elements = document.body().select("#attraction_header_info");
                elements2 = document.body().select("#attraction_overview_glide");
                elements3 = document.body().select("#attraction_times");
                elements4 = document.body().select("#attraction_map_left ul li");
                elements5 = document.body().select(".schema_mask");
                elements6 = document.body().select("#attraction_overview_glide ul li");
                elements7 = document.body().select("#attraction_near .att_strip li h3");

                Element image = elements5.select("img").first();
                String imageurl = image.absUrl("src");

                cv = elements.select("h1 span").text();
                cv2 = elements.select("p").text();
                cv3 = elements2.select("p").text();
                cv4 = elements3.select("p").text();
                url = elements5.attr("abs:src");


                for (int i = 0; i < elements4.size(); i++) {
                    sb3.append(+i + 1 + ". ").append(elements4.get(i).text()).append("\n\n");

                }
                for (int i = 0; i < elements6.size(); i++) {
                    sb.append(+i + 1 + ". ").append(elements6.get(i).text()).append("\n\n");
                }
                for (int i = 0; i < elements7.size(); i++) {
                    sb2.append(i + 1 + ". ").append(elements7.get(i).text()).append("\n\n");

                }

                cv6 = imageurl;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            title1.setText("" + cv);
            overview1.setText("" + cv2);
            openingtimes1.setText("" + cv3);
            gettingthere1.setText(cv4);
            gettingthere2.setText(sb3);


            doyouknow.setText(sb);
            attractionsNearby.setText(sb2);

            Picasso.get().load(cv6).resize(300, 300).into(imagevater);
            mProgressDialog.dismiss();
        }
    }

    private void initUi() {
        title1 = (TextView) findViewById(R.id.title);
        overview1 = (TextView) findViewById(R.id.overview);
        openingtimes1 = (TextView) findViewById(R.id.opening_times);
        gettingthere1 = (TextView) findViewById(R.id.getting_there);
        gettingthere2 = (TextView) findViewById(R.id.getting_there2);
        doyouknow = (TextView) findViewById(R.id.do_you_know);
        attractionsNearby = (TextView) findViewById(R.id.attractions_nearby);
        imagevater = (ImageView) findViewById(R.id.imagevater);
        imagevater = (ImageView) findViewById(R.id.imagevater);
    }

}
