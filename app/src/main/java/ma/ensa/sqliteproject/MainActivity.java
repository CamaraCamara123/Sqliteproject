package ma.ensa.sqliteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ma.ensa.sqliteproject.classes.Etudiant;
import ma.ensa.sqliteproject.service.EtudiantService;

public class MainActivity extends AppCompatActivity {
    private EditText nom;
    private EditText prenom;
    private Button add;


    private EditText id;
    private Button rechercher;
    private TextView res;

    //Méthode pour vider les champs après l’ajout
    void clear(){
        nom.setText("");
        prenom.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EtudiantService es = new EtudiantService(this);

        nom = (EditText)findViewById(R.id.nom);
        prenom = (EditText)findViewById(R.id.prenom);
        add = (Button)findViewById(R.id.bn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insertion des étudiants
                es.create(new Etudiant(nom.getText().toString(), prenom.getText().toString()));
                clear();
                //Parcourir la liste des étudiants
                for(Etudiant e : es.findAll()){
                    Log.d(e.getId()+"", e.getNom()+" "+e.getPrenom());
                }
            }
        });

        id = (EditText)findViewById(R.id.id);
        rechercher = (Button)findViewById(R.id.load);
        res = (TextView)findViewById(R.id.res);

        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Etudiant e = es.findById(Integer.parseInt(id.getText().toString()));
                res.setText(e.getNom()+" "+e.getPrenom());
            }
        });

    }

}