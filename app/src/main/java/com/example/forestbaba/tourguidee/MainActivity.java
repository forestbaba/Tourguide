package com.example.forestbaba.tourguidee;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//public class MainActivity extends AppCompatActivity
//{
//    private List<Model> modelList = new ArrayList<>();
//    private RecyclerView recyclerView;
//    private ModelAdapter mAdapter;
//    String url = "https://www.newyorkpass.com/new-york-attractions/";
//    String imageUrl = "https://www.newyorkpass.com";
//    ProgressDialog mProgressDialog;
//    private String titleOfPlace,summ,impath;

//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        new Fetch().execute();
//
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        mAdapter = new ModelAdapter(modelList,this);
//        }
//    private class Fetch extends AsyncTask<Void, Void, Void> {
//        String title, sub;
//        Document document;
//        Elements elements,elements2,elements3,span,atag,atag2,img;

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mProgressDialog = new ProgressDialog(MainActivity.this);
//            mProgressDialog.setTitle("Tour Guide");
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                 document = Jsoup.connect(url).get();
//                 elements = document.body().select(".att_strip li article");
//                 elements2 = document.body().select(".att_strip li article h3");
//                 elements3 = document.body().select(".att_strip li article h3 span");
//                 span = document.body().select(".att_strip li h3 span");
//                 atag = document.body().select(".att_strip li a");
//                 atag2 = document.body().select(".att_strip li article a[href]");
//                 img = document.body().select(".att_strip_img");
//
//                String url = document.select("a").first().attr("abs:href");
//
//                for (int i = 0; i < elements.size(); i++)
//                {
//                    for(Element elem : elements2){
//                        titleOfPlace = elem.ownText();
//                    }
//
//                    summ = elements3.get(i).text();
//                    Element cv = atag2.get(i);
//                    String iimage = elements.get(i).select(".att_strip li .att_strip_img img").text();
//
//                    Element featureImage = elements.get(i).select("div.att_strip_img").first();
//                    String temp = featureImage.getElementsByAttribute("style").toString();
//                    String imageStrg = temp.substring(temp.indexOf("(") + 1, temp.indexOf(")"));
//
//                    modelList.add( new Model(elements2.get(i).text(), elements3.get(i).text(),
//                            elements.get(i).select("h3").text(),
//                            ""+url+imageStrg));
//                    }
//
//                Elements select = document.select("#filtereditems > ul > li");
//                for (int i = 0; i < select.size(); i++) {
//                    String li = select.get(i).className();
//                    String lii = select.get(i).cssSelector();
//                    String li2 = select.get(i).id();
//                    String li3 = select.get(i).wholeText();
//                    String x = select.get(50).cssSelector();
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//            recyclerView.setLayoutManager(mLayoutManager);
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.setAdapter(mAdapter);
//            mProgressDialog.dismiss();
//        }
//    }
//}

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Travels"));
        tabLayout.addTab(tabLayout.newTab().setText("Monuments"));
        tabLayout.addTab(tabLayout.newTab().setText("Night Life"));
        tabLayout.addTab(tabLayout.newTab().setText("Spotlight"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(this);
    }
}