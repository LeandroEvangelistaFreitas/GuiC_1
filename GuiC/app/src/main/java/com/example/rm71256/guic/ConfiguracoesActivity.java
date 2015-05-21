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


public class ConfiguracoesActivity extends Activity {
    private ListView listView;
    private List<String> listaTipo;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracoes);

        listView = (ListView) findViewById(R.id.listaTipos);
        listaTipo = new ArrayList<String>();


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listaTipo);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adaptador);
        try
        {
            HttpRequest client = new HttpRequest();
            client.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        //Botao Preferencias
		ImageButton botaoPreferencias = (ImageButton) findViewById(R.id.botaoPreferencias);
        botaoPreferencias.setOnClickListener(new View.OnClickListener() {
            
                        @Override
                        public void onClick(View v) {
            				Intent i = new Intent();
                            i.setClass(ConfiguracoesActivity.this,
                                    HomeActivity.class);  
            		        startActivity(i); 
                        }
                    });
		//Botao Pesquisar
        ImageButton botaoPesquisar = (ImageButton) findViewById(R.id.botaoPesquisar);
        botaoPesquisar.setOnClickListener(new View.OnClickListener() {
            
                        @Override
                        public void onClick(View v) {
            				Intent i = new Intent();
                            i.setClass(ConfiguracoesActivity.this,
                                    PesquisarActivity.class);  
            		        startActivity(i); 
                        }
                    });
		
	}

    public String request() throws Exception
    {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://192.168.1.35/guic/lista_tipo.php");
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
                listaTipo.clear();
                JSONArray tipos = new JSONArray(retorno);

                for(int i = 0; i<tipos.length();i++)
                {
                    JSONObject e = (JSONObject) tipos.get(i);
                    listaTipo.add(e.getString("descricao"));
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

