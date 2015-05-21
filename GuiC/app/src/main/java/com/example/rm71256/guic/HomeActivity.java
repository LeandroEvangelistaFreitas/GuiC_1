package com.example.rm71256.guic;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class HomeActivity extends Activity {
    private ListView listView;
    private List <String> listaEventos;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        listView = (ListView) findViewById(R.id.listaPreferencia);
        listaEventos = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaEventos);
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

        //Botao Pesquisar
        ImageButton botaoPesquisar = (ImageButton) findViewById(R.id.botaoPesquisar);
        botaoPesquisar.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(HomeActivity.this,
                        PesquisarActivity.class);
                startActivity(i);
            }
        });

        ImageButton botaoConfiguracoes = (ImageButton) findViewById(R.id.botaoConfiguracoes);
        botaoConfiguracoes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(HomeActivity.this,
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
                listaEventos.clear();
                JSONArray eventos = new JSONArray(retorno);

                for(int i = 0; i<eventos.length();i++)
                {
                    JSONObject e = (JSONObject) eventos.get(i);
                        listaEventos.add(e.getString("nome") + "\nCidade: " + e.getString("cidade")
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













        // URL de conexão
        // String URL = "http://127.0.0.1/guic/teste.php";

      /*  String URL = "http://teste.obraedecoracao/bepro/request.php";


        String retorno;
        boolean Resultado = false;

        try {
            Resultado = new Consulta().execute().get();
        } catch (Exception ex) {
            ex.getMessage();
        }


        try
        {
            JSONArray meuJson = new JSONArray();
            JSONObject dados = new JSONObject();

            for (int i = 0; i < meuJson.length(); i++)
            {
                dados = meuJson.getJSONObject(i);

                      /*  String nome = dados.getString("nome");
                        String cidade = dados.getString("cidade");
                        String horario = dados.getString("horario");
                        String palestrante = dados.getString("palestrante");
                        String preco = dados.getString("preco");
                        String descricao = dados.getString("descricao");*/

        /*        String preco = dados.getString("preco");
                String produto = dados.getString("produto");
                String estabelecimento = dados.getString("estabelecimento");


            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        listView = (ListView) findViewById(R.id.eventList);



        listaEventos = new ArrayList<String>();

        listaEventosDialog = new ArrayList<String>();



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaEventos);

        listView.setAdapter(adapter);



        try {



            HttpRequest client = new HttpRequest();

            client.execute();

        }catch (Exception e){
            e.printStackTrace();
        }

        */




