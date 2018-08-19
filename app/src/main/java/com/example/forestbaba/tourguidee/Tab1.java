package com.example.forestbaba.tourguidee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tab1 extends Fragment {
    private RecyclerView recyclerView1;
    private ModelAdapter mAdapter;
    String url = "https://www.newyorkpass.com/new-york-attractions/";
    String imageUrl = "https://www.newyorkpass.com";
    ProgressDialog mProgressDialog;
    private String titleOfPlace, summ, impath;
    private List<Model> modelList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View view =inflater.inflate(R.layout.tab1, container, false);
        recyclerView1 = (RecyclerView)view.findViewById(R.id.recycler_view_1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(mLayoutManager);
        mAdapter = new ModelAdapter(modelList, getContext(), new ModelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("POSITION", "" + position);
                startActivity(intent);            }
        });
        new Fetch().execute();

        return view;
    }

    private class Fetch extends AsyncTask<Void, Void, Void> {
        String title, sub;
        Document document;
        Elements elements, elements2, elements3, span, atag, atag2, img;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setTitle("Tour Guide");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                document = Jsoup.connect(url).get();
                elements = document.body().select(".att_strip li article");
                elements2 = document.body().select(".att_strip li article h3");
                elements3 = document.body().select(".att_strip li article h3 span");
                span = document.body().select(".att_strip li h3 span");
                atag = document.body().select(".att_strip li a");
                atag2 = document.body().select(".att_strip li article a[href]");
                img = document.body().select(".att_strip_img");

                String url = document.select("a").first().attr("abs:href");

                for (int i = 0; i <= 25; i++) {
                    for (Element elem : elements2) {
                        titleOfPlace = elem.ownText();
                    }

                    summ = elements3.get(i).text();
                    Element cv = atag2.get(i);
                    String iimage = elements.get(i).select(".att_strip li .att_strip_img img").text();

                    Element featureImage = elements.get(i).select("div.att_strip_img").first();
                    String temp = featureImage.getElementsByAttribute("style").toString();
                    String imageStrg = temp.substring(temp.indexOf("(") + 1, temp.indexOf(")"));

                    modelList.add(new Model(elements2.get(i).text(), elements3.get(i).text(),
                            elements.get(i).select("h3").text(),
                            "" + url + imageStrg));
                }

                Elements select = document.select("#filtereditems > ul > li");
                for (int i = 0; i < select.size(); i++) {
                    String li = select.get(i).className();
                    String lii = select.get(i).cssSelector();
                    String li2 = select.get(i).id();
                    String li3 = select.get(i).wholeText();
                    String x = select.get(50).cssSelector();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            recyclerView1.setItemAnimator(new DefaultItemAnimator());
            recyclerView1.setAdapter(mAdapter);
            mProgressDialog.dismiss();
        }
    }
}
