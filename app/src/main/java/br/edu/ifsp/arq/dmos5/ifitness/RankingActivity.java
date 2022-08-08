package br.edu.ifsp.arq.dmos5.ifitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import br.edu.ifsp.arq.dmos5.ifitness.model.Atividade;
import br.edu.ifsp.arq.dmos5.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmos5.ifitness.viewmodel.UsuarioViewModel;

public class RankingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;

    private TextView txtSemUsuario;

    private UsuarioViewModel usuarioViewModel;

    private Usuario usuario;

    private Atividade atividade;

    private LinearLayout llRanking1;
    private LinearLayout llRanking2;
    private LinearLayout llRanking3;
    private LinearLayout llRanking4;
    private LinearLayout llRanking5;

    private TextView txtRankingNome1;
    private TextView txtRankingPontuacao1;

    private TextView txtRankingNome2;
    private TextView txtRankingPontuacao2;

    private TextView txtRankingNome3;
    private TextView txtRankingPontuacao3;

    private TextView txtRankingNome4;
    private TextView txtRankingPontuacao4;

    private TextView txtRankingNome5;
    private TextView txtRankingPontuacao5;

    int indice=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ranking);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        configuracao();

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);

        usuarioViewModel.getUsuarios().observe(RankingActivity.this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                for (Usuario u: usuarios){
                    if(u!=null){
                        RankingActivity.this.usuario = u;
                        adicionarValor(usuario);
                    }
                }
            }
        });
    }

    private void configuracao() {
        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Ranking de Usuários");
        txtSemUsuario = findViewById(R.id.txt_ranking_sem_usuario);

        llRanking1 = findViewById(R.id.ll_ranking_1);
        llRanking2 = findViewById(R.id.ll_ranking_2);
        llRanking3 = findViewById(R.id.ll_ranking_3);
        llRanking4 = findViewById(R.id.ll_ranking_4);
        llRanking5 = findViewById(R.id.ll_ranking_5);
        llRanking1.setVisibility(View.INVISIBLE);
        llRanking2.setVisibility(View.INVISIBLE);
        llRanking3.setVisibility(View.INVISIBLE);
        llRanking4.setVisibility(View.INVISIBLE);
        llRanking5.setVisibility(View.INVISIBLE);

        txtRankingNome1 = findViewById(R.id.txt_ranking_nome_1);
        txtRankingPontuacao1 = findViewById(R.id.txt_ranking_pontuacao_1);

        txtRankingNome2 = findViewById(R.id.txt_ranking_nome_2);
        txtRankingPontuacao2 = findViewById(R.id.txt_ranking_pontuacao_2);

        txtRankingNome3 = findViewById(R.id.txt_ranking_nome_3);
        txtRankingPontuacao3 = findViewById(R.id.txt_ranking_pontuacao_3);

        txtRankingNome4 = findViewById(R.id.txt_ranking_nome_4);
        txtRankingPontuacao4 = findViewById(R.id.txt_ranking_pontuacao_4);

        txtRankingNome5 = findViewById(R.id.txt_ranking_nome_5);
        txtRankingPontuacao5 = findViewById(R.id.txt_ranking_pontuacao_5);
    }

    private void adicionarValor(Usuario usuario) {
        switch (indice){
            case 0:
                txtSemUsuario.setVisibility(View.INVISIBLE);
                llRanking2.setVisibility(View.INVISIBLE);
                llRanking3.setVisibility(View.INVISIBLE);
                llRanking4.setVisibility(View.INVISIBLE);
                llRanking5.setVisibility(View.INVISIBLE);

                llRanking1.setVisibility(View.VISIBLE);
                txtRankingNome1.setText(usuario.getNome());
                txtRankingPontuacao1.setText(usuario.getPontuacao()+" pontos");

                break;
            case 1:
                txtSemUsuario.setVisibility(View.INVISIBLE);
                llRanking3.setVisibility(View.INVISIBLE);
                llRanking4.setVisibility(View.INVISIBLE);
                llRanking5.setVisibility(View.INVISIBLE);

                llRanking2.setVisibility(View.VISIBLE);
                txtRankingNome2.setText(usuario.getNome());
                txtRankingPontuacao2.setText(usuario.getPontuacao()+" pontos");

                break;
            case 2:
                txtSemUsuario.setVisibility(View.INVISIBLE);
                llRanking4.setVisibility(View.INVISIBLE);
                llRanking5.setVisibility(View.INVISIBLE);

                llRanking3.setVisibility(View.VISIBLE);
                txtRankingNome3.setText(usuario.getNome());
                txtRankingPontuacao3.setText(usuario.getPontuacao()+" pontos");

                break;
            case 3:
                txtSemUsuario.setVisibility(View.INVISIBLE);
                llRanking5.setVisibility(View.INVISIBLE);

                llRanking4.setVisibility(View.VISIBLE);
                txtRankingNome4.setText(usuario.getNome());
                txtRankingPontuacao4.setText(usuario.getPontuacao()+" pontos");

                break;
            case 4:
                txtSemUsuario.setVisibility(View.INVISIBLE);

                llRanking5.setVisibility(View.VISIBLE);
                txtRankingNome5.setText(usuario.getNome());
                txtRankingPontuacao5.setText(usuario.getPontuacao()+" pontos");

                break;
        }
        indice++;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

