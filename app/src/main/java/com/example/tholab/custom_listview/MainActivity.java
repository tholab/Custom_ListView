package com.example.tholab.custom_listview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ArrayList<HashMap<String, Object>> searchResult;

    ArrayList<HashMap<String, Object>> daftarmakanan;

    LayoutInflater inflater;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText kotakpencarian = findViewById(R.id.makanan);
        ListView list_makanan = findViewById(R.id.list);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        String namamakanan[] = {"Wingko","Lumpia","Tahu Isi","Tahu Bakso","Mie"};
        String detailmakanan[] = {"Harga : Rp.2.000",
                                    "Harga : Rp.4.000",
                                    "Harga : Rp.1.500",
                                    "Harga : Rp.3.000",
                                    "Harga : Rp.10.000"};
        int [] gambar = {R.drawable.wingko, R.drawable.lumpia, R.drawable.tahu, R.drawable.tahubakso, R.drawable.mie};

        daftarmakanan = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> temp;
        int nomor_makanan = namamakanan.length;

        for (int i=0;i<nomor_makanan;i++){
            temp = new HashMap<String, Object>();
                    temp.put("namamakanan", namamakanan[i]);
                    temp.put("detailmakanan", detailmakanan[i]);
                    temp.put("gambar", gambar[i]);

            daftarmakanan.add(temp);
        }

        searchResult = new ArrayList<HashMap<String, Object>>(daftarmakanan);

          adapter = new CustomAdapter(this, R.layout.activity_daftar_makanan, searchResult);

       list_makanan.setAdapter(adapter);

       list_makanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String str = searchResult.get(position).get("namamakanan").toString();
               switch (str){
                   case "Wingko" :
                       Intent a = new Intent(MainActivity.this, wingko.class);
                       startActivity(a);
                       break;
                   case "Lumpia" :
                       Intent b = new Intent(MainActivity.this, Lumpia.class);
                       startActivity(b);
                       break;
                   case "Tahu Isi" :
                       Intent c = new Intent(MainActivity.this, tahuIsi.class);
                       startActivity(c);
                       break;
                   case "Tahu Bakso" :
                       Intent d = new Intent(MainActivity.this, tahuBakso.class);
                       startActivity(d);
                       break;
                   case "Mie" :
                       Intent e = new Intent(MainActivity.this, mie.class);
                       startActivity(e);
                       break;
               }

           }
       });




        kotakpencarian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchstring = kotakpencarian.getText().toString();
                int textLength = searchstring.length();
                searchResult.clear();

                for (int i=0;i<daftarmakanan.size();i++){
                    String hasil_namamakanan = daftarmakanan.get(i).get("namamakanan").toString();
                    if (textLength<=hasil_namamakanan.length()){
                        if (searchstring.equalsIgnoreCase(hasil_namamakanan.substring(0, textLength)))
                        searchResult.add(daftarmakanan.get(i));
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>> {
        public CustomAdapter(Context context, int textViewResourceId, ArrayList<HashMap<String, Object>> Strings) {
            super(context, textViewResourceId, Strings);
        }

        private class ViewHolder{
            ImageView gambar;
            TextView namamakanan, detailmakanan;
        }
        ViewHolder viewHolder;

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView==null){
                convertView = inflater.inflate(R.layout.activity_daftar_makanan, null);
                viewHolder = new ViewHolder();

                viewHolder.gambar = (ImageView) convertView.findViewById(R.id.gambar);
                viewHolder.namamakanan = (TextView) convertView.findViewById(R.id.namamakanan);
                viewHolder.detailmakanan = (TextView) convertView.findViewById(R.id.detailmakanan);

                convertView.setTag(viewHolder);
            } else
                viewHolder =(ViewHolder) convertView.getTag();

                int gambarId = (Integer) searchResult.get(position).get("gambar");

                viewHolder.gambar.setImageDrawable(getResources().getDrawable(gambarId));
                viewHolder.namamakanan.setText(searchResult.get(position).get("namamakanan").toString());
                viewHolder.detailmakanan.setText(searchResult.get(position).get("detailmakanan").toString());
                return convertView;
        }
    }
}
