package br.edu.ifsp.arq.dmos5.ifitness;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.dmos5.ifitness.model.Atividade;
import br.edu.ifsp.arq.dmos5.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmos5.ifitness.viewmodel.UsuarioViewModel;

public class EstatisticaAtividadeActivity extends AppCompatActivity {
    private TextView txtDistancia;
    private TextView txtDuracao;
    private TextView txtCalorias;
    private TextView txtPontuacao;
    private TextView txtEmblemas;

    private Button btnRanking;

    private Toolbar toolbar;
    private TextView txtTitulo;

    private Usuario usuario;
    private Atividade atividade;
    private UsuarioViewModel usuarioViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica_atividade);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Estatística do Usuário");

        txtDistancia = findViewById(R.id.txt_estatistica_distancia);
        txtDuracao = findViewById(R.id.txt_estatistica_duracao);
        txtCalorias = findViewById(R.id.txt_estatistica_calorias);
        txtPontuacao = findViewById(R.id.txt_estatistica_pontuacao);
        txtEmblemas = findViewById(R.id.txt_estatistica_emblemas);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    EstatisticaAtividadeActivity.this.usuario = usuario;

                    txtPontuacao.setText(usuario.getPontuacao()+" pontos");

                    usuarioViewModel.getAtividadesById().observe(EstatisticaAtividadeActivity.this, new Observer<List<Atividade>>() {

                        Double distanciaTotal = 0.0;
                        Double duracaoTotal = 0.0;
                        Double caloriasTotal = 0.0;

                        int i = 0;
                        String txtEmblemasAux = "";

                        @Override
                        public void onChanged(List<Atividade> atividades) {
                            for (Atividade a : atividades) {
                                if (a != null) {
                                    EstatisticaAtividadeActivity.this.atividade = a;

                                    distanciaTotal = distanciaTotal+atividade.getDistancia();
                                    duracaoTotal = duracaoTotal+atividade.getDuracao();

                                    if (atividade.getCategoriaAtividade().equals("Caminhada")){
                                        // CAMINHADA - (3,5 METs x PESO kg) x (DURAÇÃO min/60 min)
                                        caloriasTotal = caloriasTotal + (3.5*usuario.getPeso()) * (atividade.getDuracao()/60);
                                    } else if (atividade.getCategoriaAtividade().equals("Ciclismo")) {
                                        // BICICLETA - (7,5 METs x PESO kg) x (DURAÇÃO min/60 min)
                                        caloriasTotal = caloriasTotal + (7.5*usuario.getPeso()) * (atividade.getDuracao()/60);
                                    } else if (atividade.getCategoriaAtividade().equals("Natação")) {
                                        // NATAÇÃO - (3,5 METs x PESO kg) x (DURACÃO min/60 min)
                                        caloriasTotal = caloriasTotal + (3.5*usuario.getPeso()) * (atividade.getDuracao()/60);
                                    } else {
                                        // CORRIDA - (8 METs x PESO kg) x (DURAÇÃO min/60 min)
                                        caloriasTotal = caloriasTotal + (8*usuario.getPeso()) * (atividade.getDuracao()/60);
                                    }


                                    if (i==atividades.size()-1){
                                        List<String> emblemas = usuario.getEmblemas();

                                        txtDistancia.setText(distanciaTotal.toString() + " km");
                                        txtDuracao.setText(duracaoTotal.toString() + " min");
                                        txtCalorias.setText("≅ " + caloriasTotal.toString() + " kcal");

                                        if (emblemas != null){
                                            for (String emblema: emblemas){
                                                if (txtEmblemasAux.isEmpty()){
                                                    txtEmblemasAux = emblema+"\n";
                                                } else {
                                                    txtEmblemasAux = txtEmblemasAux+"\n"+emblema+"\n";
                                                }
                                            }

                                            if (!emblemas.isEmpty()){
                                                txtEmblemas.setText(txtEmblemasAux);
                                            }
                                        }
                                    }
                                    i++;
                                }
                            }
                        }
                    });

                } else {
                    startActivity(new Intent(EstatisticaAtividadeActivity.this,
                            UsuarioLoginActivity.class));
                    finish();
                }
            }
        });

        btnRanking = findViewById(R.id.btn_estatistica_ranking);
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EstatisticaAtividadeActivity.this, RankingActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

