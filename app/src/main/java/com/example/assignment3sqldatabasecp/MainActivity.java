package com.example.assignment3sqldatabasecp;

import static com.example.assignment3sqldatabasecp.PokeDBProvider.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.ContentValues;


public class MainActivity extends AppCompatActivity {
    //variables
    Button reset, submit, changeColorsButton;
    RadioGroup gender;
    Spinner levelSpin;
    EditText nationalET, nameET, speciesET, heightET, weightET, hpET, attackET, defenseET;
    int clicks = 0, contentV=0;
    private ListView listView;
    Cursor mCursor;
    ContentValues newValues = new ContentValues();

    //listeners
    View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //set everything to default status
            nationalET.setText(R.string.ntnlDefault);
            nameET.setText(R.string.nameDefault);
            speciesET.setText(R.string.speciesDefault);
            heightET.setText(R.string.heightDefault);
            weightET.setText(R.string.weightDefault);
            hpET.setText(R.string.baseStatsDefault);
            attackET.setText(R.string.baseStatsDefault);
            defenseET.setText(R.string.baseStatsDefault);
            levelSpin.setSelection(0);
            gender.clearCheck();
        }
    };

    View.OnClickListener colorsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                ConstraintLayout mainPage;
                mainPage = findViewById(contentV);
                if(clicks == 3){
                    reset.setBackgroundColor(getResources().getColor(R.color.buttonPurple));
                    submit.setBackgroundColor(getResources().getColor(R.color.buttonPurple));
                    changeColorsButton.setBackgroundColor(getResources().getColor(R.color.buttonPurple));
                    mainPage.setBackgroundColor(getResources().getColor(R.color.backgroundPurple));
                    clicks = 0;
                } else if (clicks==0) {
                    //change colors to green
                    reset.setBackgroundColor(getResources().getColor(R.color.buttonGreen));
                    submit.setBackgroundColor(getResources().getColor(R.color.buttonGreen));
                    changeColorsButton.setBackgroundColor(getResources().getColor(R.color.buttonGreen));
                    mainPage.setBackgroundColor(getResources().getColor(R.color.backgroundGreen));
                    clicks ++;
                } else if (clicks==1) {
                    //change colors to pink
                    reset.setBackgroundColor(getResources().getColor(R.color.buttonPink));
                    submit.setBackgroundColor(getResources().getColor(R.color.buttonPink));
                    changeColorsButton.setBackgroundColor(getResources().getColor(R.color.buttonPink));
                    mainPage.setBackgroundColor(getResources().getColor(R.color.backgroundPink));
                    clicks++;
                } else if (clicks ==2) {
                    //change colors to blue
                    reset.setBackgroundColor(getResources().getColor(R.color.buttonBlue));
                    submit.setBackgroundColor(getResources().getColor(R.color.buttonBlue));
                    changeColorsButton.setBackgroundColor(getResources().getColor(R.color.buttonBlue));
                    mainPage.setBackgroundColor(getResources().getColor(R.color.backgroundBlue));
                    clicks ++;
                }
            }
    };

    View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Validate the input fields
            boolean isValid = true;

            // Validate National Number
            String nationalNumStr = nationalET.getText().toString();
            try {
                int nationalNum = Integer.parseInt(nationalNumStr);
                if (nationalNum < 0 || nationalNum > 1010) {
                    nationalET.setTextColor(getResources().getColor(R.color.red));
                    isValid = false;
                } else {
                    nationalET.setTextColor(getResources().getColor(R.color.black));
                    newValues.put(PokeDBProvider.COLUMN1_NAME, nationalNum);
                }
                //make sure the input is a number
            } catch (NumberFormatException e) {
                nationalET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            }

            // Validate Name
            String name = nameET.getText().toString();
            boolean isAlphabetical = true;
            if (name.isEmpty() || name.length() < 3 || name.length() > 12) {
                isAlphabetical = false;
            } else {
                for (int i = 0; i < name.length(); i++) {
                    char c = name.charAt(i);
                    if (!Character.isLetter(c)) {
                        isAlphabetical = false;
                        break;
                    }
                }
            }
            if (!isAlphabetical) {
                nameET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            } else {
                nameET.setTextColor(getResources().getColor(R.color.black));
                newValues.put(PokeDBProvider.COLUMN2_NAME, name);
            }

            String speciesStr = speciesET.getText().toString();
            if (speciesStr.isEmpty()) {
                speciesET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            } else {
                nationalET.setTextColor(getResources().getColor(R.color.black));
                newValues.put(PokeDBProvider.COLUMN3_NAME, speciesStr);
            }

            String heightStr = heightET.getText().toString();
            if (heightStr.endsWith("m")) {
                heightStr = heightStr.substring(0, heightStr.length() - 1);
            }
            if (heightStr.isEmpty() || Double.parseDouble(heightStr) < 0.3 || Double.parseDouble(heightStr) > 19.99) {
                heightET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            } else {
                heightET.setTextColor(getResources().getColor(R.color.black));
                newValues.put(PokeDBProvider.COLUMN5_NAME, heightStr);
            }

            String weightStr = weightET.getText().toString();
            if (weightStr.endsWith("kg")) {
                weightStr = weightStr.substring(0, weightStr.length() - 2);
            }
            if (weightStr.isEmpty() || Double.parseDouble(weightStr) < 0.1 || Double.parseDouble(weightStr) > 820.00) {
                weightET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            } else {
                weightET.setTextColor(getResources().getColor(R.color.black));
                newValues.put(PokeDBProvider.COLUMN6_NAME, weightStr);
            }

            String hpStr = hpET.getText().toString();
            boolean isHpValid = false;
            try {
                int hp = Integer.parseInt(hpStr);
                if (hp >= 1 && hp <= 362) {
                    isHpValid = true;
                }
            } catch (NumberFormatException e) {
                hpET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            }
            if (!isHpValid) {
                hpET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            } else {
                hpET.setTextColor(getResources().getColor(R.color.black));
                newValues.put(PokeDBProvider.COLUMN8_NAME, hpStr);
            }

            String attackStr = attackET.getText().toString();
            boolean isAttackValid = false;
            try {
                int attack = Integer.parseInt(attackStr);
                if (attack >= 5 && attack <= 526) {
                    isAttackValid = true;
                }
            } catch (NumberFormatException e) {
                attackET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            }
            if (!isAttackValid) {
                attackET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            } else {
                attackET.setTextColor(getResources().getColor(R.color.black));
                newValues.put(PokeDBProvider.COLUMN9_NAME, attackStr);
            }

            String defenseStr = defenseET.getText().toString();
            boolean isDefenseValid = false;
            try {
                int defense = Integer.parseInt(defenseStr);
                if (defense >= 5 && defense <= 614) {
                    isDefenseValid = true;
                }
            } catch (NumberFormatException e) {
                defenseET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            }
            if (!isDefenseValid) {
                defenseET.setTextColor(getResources().getColor(R.color.red));
                isValid = false;
            } else {
                defenseET.setTextColor(getResources().getColor(R.color.black));
                newValues.put(PokeDBProvider.COLUMN10_NAME, defenseStr);
            }

            String selectedLevel = levelSpin.getSelectedItem().toString();
            if (selectedLevel.isEmpty()) {
                isValid = false;
            } else{
                newValues.put(PokeDBProvider.COLUMN7_NAME, selectedLevel);
            }
            
            int selectedGender = gender.getCheckedRadioButtonId();
            if (selectedGender == -1) {
                isValid = false;
            }else {
                newValues.put(PokeDBProvider.COLUMN4_NAME, selectedGender);
            }
            }
    };
    AdapterView.OnItemSelectedListener spinListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    TextWatcher weightWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String input = charSequence.toString();
            if (!input.endsWith("kg")) {
                input += " kg";
                weightET.setText(input);
                weightET.setSelection(input.length()-3);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
    TextWatcher heightWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String input = charSequence.toString();
            if (!input.endsWith("m")) {
                input += " m";
                heightET.setText(input);
                heightET.setSelection(input.length()-2);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentV = R.id.mainPageC;

        reset = findViewById(R.id.resetButton);
        submit = findViewById(R.id.submitButton);
        changeColorsButton = findViewById(R.id.backgroundButton);

        nationalET = findViewById(R.id.ntnlNumEdit);
        nameET = findViewById(R.id.nameEdit);
        speciesET = findViewById(R.id.speciesEdit);
        heightET = findViewById(R.id.heightEdit);
        weightET = findViewById(R.id.weightEdit);
        hpET = findViewById(R.id.hpEdit);
        attackET = findViewById(R.id.attackEdit);
        defenseET = findViewById(R.id.defenseEdit);
        gender = findViewById(R.id.radioGroup);
        levelSpin = findViewById(R.id.spinner);

        reset.setOnClickListener(resetListener);
        submit.setOnClickListener(submitListener);
        changeColorsButton.setOnClickListener(colorsListener);
        reset.setBackgroundColor(getResources().getColor(R.color.buttonPurple));
        submit.setBackgroundColor(getResources().getColor(R.color.buttonPurple));
        changeColorsButton.setBackgroundColor(getResources().getColor(R.color.buttonPurple));
        levelSpin.setOnItemSelectedListener(spinListener);

        weightET.addTextChangedListener(weightWatcher);
        heightET.addTextChangedListener(heightWatcher);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.levelValues, android.R.layout.simple_spinner_dropdown_item);
        levelSpin.setAdapter(adapter);




    }
}
