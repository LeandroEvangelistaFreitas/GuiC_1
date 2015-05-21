package com.example.rm71256.guic;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class PesquisarActivity extends Activity
{
    private ListView listView;
    private List<String> listaPesquisa;
    
    private EditText et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisar);
        listView = (ListView) findViewById(R.id.listaProcurar);
        et = (EditText) findViewById(R.id.Procurar);
        listaPesquisa = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaPesquisa);
        listView.setAdapter(adapter);

        try
        {
            HttpRequest client = new HttpRequest();
            client.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }






        ImageButton botaoPreferencias_Pesquisar = (ImageButton) findViewById(R.id.botaoPreferencias_Pesquisar);
        botaoPreferencias_Pesquisar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PesquisarActivity.this,
                        HomeActivity.class);
                startActivity(i);
            }
        });


        ImageButton botaoConfiguracoes_Pesquisar = (ImageButton) findViewById(R.id.botaoConfiguracoes_Pesquisar);
        botaoConfiguracoes_Pesquisar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PesquisarActivity.this,
                        ConfiguracoesActivity.class);
                startActivity(i);
            }
        });
    }

    public String request() throws Exception
    {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://192.168.1.35/guic/lista_tudo.php");
        HttpResponse response = client.execute(get);
        int status = response.getStatusLine().getStatusCode();

        if (status == 200)
        {
            InputStream conteudo = response.getEntity().getContent();
            BufferedReader buf = new BufferedReader(new InputStreamReader(conteudo));
            String line = buf.readLine();
            while (line != null) {
                builder.append(line);
                line = buf.readLine();
            }

            return builder.toString();
        } else {

            return "ERRO";
        }

    }
    private class HttpRequest extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            String ret = "ERRO";
            try {
                ret = request();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        }
        @Override
        protected void onPostExecute(String retorno)
        {
            try {
                listaPesquisa.clear();
                JSONArray eventos = new JSONArray(retorno);
                int textlength = et.getText().length();

                for(int i = 0; i<eventos.length();i++)
                {
                    JSONObject e = (JSONObject) eventos.get(i);

                        listaPesquisa.add(e.getString("nome") + "\nCidade: " + e.getString("cidade")
                                + "\nData: " + e.getString("horario") + "\nResponável: " + e.getString("palestrante") +
                                "\nPreco: " + e.getString("preco") + "\nTipo: " + e.getString("descricao"));


                }


                listView.invalidateViews();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }






    }
}
