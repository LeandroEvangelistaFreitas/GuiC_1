package com.example.rm71256.guic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity 
{
	

	
	private final int DURACAO_DA_TELA = 5000;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Handler().postDelayed(new Runnable() 
		{
			
			@Override
			public void run()
			{
				
	            
                Intent minhaAcao = new Intent(MainActivity.this,
                        HomeActivity.class);
				MainActivity.this.startActivity(minhaAcao);
				MainActivity.this.finish();
			}
			
		
        
	   }, DURACAO_DA_TELA);

		    
	}


}




