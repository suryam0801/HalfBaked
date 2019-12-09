package com.codingdemos.tablayout;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.codingdemos.tablayout.Adapter.FeedAdapter;
import com.codingdemos.tablayout.Common.HTTPDataHandler;
import com.codingdemos.tablayout.Model.RSSObject;
import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryThree extends Fragment{

    Toolbar toolbar;
    RecyclerView recyclerView;
    RSSObject rssObject;
    View v;

    private final String RSS_link="http://timesofindia.indiatimes.com/rssfeeds/5880659.cms";
    private final String RSS_to_Json_API = "https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);


        v = inflater.inflate(R.layout.category_three, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadRSS();

        return v;
    }

    private void loadRSS(){
        AsyncTask<String, String, String> loadRSSAsync = new AsyncTask<String, String, String>() {

            ProgressDialog mDialog = new ProgressDialog(getActivity());

            @Override
            protected void onPreExecute() {
//                mDialog.setMessage("Please Wait...");
//                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(params[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
//                mDialog.dismiss();
                rssObject = new Gson().fromJson(s,RSSObject.class);
                FeedAdapter adapter = new FeedAdapter(rssObject,getActivity());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

        StringBuilder url_get_data = new StringBuilder(RSS_to_Json_API);
        url_get_data.append(RSS_link);
        loadRSSAsync.execute(url_get_data.toString());

    }
}
