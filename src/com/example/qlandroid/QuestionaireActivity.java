package com.example.qlandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.values.Value;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;

public class QuestionaireActivity extends Activity {
    /** Called when the activity is first created. */
     
    //initialize a button and a counter
    Button btn;
    int counter = 0;
    List<IQLRow> rows;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.questionnaire);

        TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);
        VariableUpdater varUpdater=new VariableUpdater(new LinkedHashMap<String,Value>());
        List<IQLRow> questionRows = new ArrayList<IQLRow>();
		Map<String, Value> declaredVar = new HashMap<String, Value>();
	
		UIGenerator generator = new UIGenerator(questionRows,varUpdater, declaredVar,this);
		generator.generate(MainActivity.getForm());
		rows = generator.getQuestionRows();
		
		
        for(IQLRow row : rows){
        	 table.addView(row.getElement());
        }
      //  Toast.makeText(getApplicationContext(), rows.get(0).getWidth()+"   "+ rows.get(1).getWidth(),
				//Toast.LENGTH_LONG).show();
        
    }

  
   
}
