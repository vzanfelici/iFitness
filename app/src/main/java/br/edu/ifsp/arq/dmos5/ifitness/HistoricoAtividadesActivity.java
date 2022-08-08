package br.edu.ifsp.arq.dmos5.ifitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import br.edu.ifsp.arq.dmos5.ifitness.model.Atividade;
import br.edu.ifsp.arq.dmos5.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmos5.ifitness.viewmodel.UsuarioViewModel;

public class HistoricoAtividadesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitulo;

    private TextView txtAtividadesNome;
    private TextView txtSemAtividades;

    private UsuarioViewModel usuarioViewModel;

    private Usuario usuario;

    private Atividade atividade;

    private LinearLayout llHistorico1;
    private LinearLayout llHistorico2;
    private LinearLayout llHistorico3;
    private LinearLayout llHistorico4;
    private LinearLayout llHistorico5;

    private TextView txtAtividadeDuracao1;
    private TextView txtAtividadeDistancia1;
    private TextView txtAtividadeCategoria1;
    private TextView txtAtividadeData1;

    private TextView txtAtividadeDuracao2;
    private TextView txtAtividadeDistancia2;
    private TextView txtAtividadeCategoria2;
    private TextView txtAtividadeData2;

    private TextView txtAtividadeDuracao3;
    private TextView txtAtividadeDistancia3;
    private TextView txtAtividadeCategoria3;
    private TextView txtAtividadeData3;

    private TextView txtAtividadeDuracao4;
    private TextView txtAtividadeDistancia4;
    private TextView txtAtividadeCategoria4;
    private TextView txtAtividadeData4;

    private TextView txtAtividadeDuracao5;
    private TextView txtAtividadeDistancia5;
    private TextView txtAtividadeCategoria5;
    private TextView txtAtividadeData5;

    int indice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_atividades);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        configuracao();

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);

        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if(usuario != null){
                    HistoricoAtividadesActivity.this.usuario = usuario;

                    txtAtividadesNome.setText(String.format("Atividades - %s", usuario.getNome()));

                    usuarioViewModel.getAtividadesById().observe(HistoricoAtividadesActivity.this, new Observer<List<Atividade>>() {
                        @Override
                        public void onChanged(List<Atividade> atividades) {
                            for (Atividade a: atividades){
                                if(a!=null){
                                    HistoricoAtividadesActivity.this.atividade = a;
                                    adicionarValor(atividade);
                                }
                            }
                        }
                    });

                }else{
                    startActivity(new Intent(HistoricoAtividadesActivity.this,
                            UsuarioLoginActivity.class));
                    finish();
                }
            }
        });
    }

    private void configuracao() {
        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Histórico de Atividades");
        txtAtividadesNome = findViewById(R.id.txt_historico_atividades_nome_usuario);
        txtSemAtividades = findViewById(R.id.txt_historico_sem_atividades);

        llHistorico1 = findViewById(R.id.ll_historico_atividades_1);
        llHistorico2 = findViewById(R.id.ll_historico_atividades_2);
        llHistorico3 = findViewById(R.id.ll_historico_atividades_3);
        llHistorico4 = findViewById(R.id.ll_historico_atividades_4);
        llHistorico5 = findViewById(R.id.ll_historico_atividades_5);
        llHistorico1.setVisibility(View.INVISIBLE);
        llHistorico2.setVisibility(View.INVISIBLE);
        llHistorico3.setVisibility(View.INVISIBLE);
        llHistorico4.setVisibility(View.INVISIBLE);
        llHistorico5.setVisibility(View.INVISIBLE);

        txtAtividadeDuracao1 = findViewById(R.id.txt_historico_duracao_1);
        txtAtividadeDistancia1 = findViewById(R.id.txt_historico_distancia_1);
        txtAtividadeCategoria1 = findViewById(R.id.txt_historico_categoria_1);
        txtAtividadeData1 = findViewById(R.id.txt_historico_data_1);

        txtAtividadeDuracao2 = findViewById(R.id.txt_historico_duracao_2);
        txtAtividadeDistancia2 = findViewById(R.id.txt_historico_distancia_2);
        txtAtividadeCategoria2 = findViewById(R.id.txt_historico_categoria_2);
        txtAtividadeData2 = findViewById(R.id.txt_historico_data_2);

        txtAtividadeDuracao3 = findViewById(R.id.txt_historico_duracao_3);
        txtAtividadeDistancia3 = findViewById(R.id.txt_historico_distancia_3);
        txtAtividadeCategoria3 = findViewById(R.id.txt_historico_categoria_3);
        txtAtividadeData3 = findViewById(R.id.txt_historico_data_3);

        txtAtividadeDuracao4 = findViewById(R.id.txt_historico_duracao_4);
        txtAtividadeDistancia4 = findViewById(R.id.txt_historico_distancia_4);
        txtAtividadeCategoria4 = findViewById(R.id.txt_historico_categoria_4);
        txtAtividadeData4 = findViewById(R.id.txt_historico_data_4);

        txtAtividadeDuracao5 = findViewById(R.id.txt_historico_duracao_5);
        txtAtividadeDistancia5 = findViewById(R.id.txt_historico_distancia_5);
        txtAtividadeCategoria5 = findViewById(R.id.txt_historico_categoria_5);
        txtAtividadeData5 = findViewById(R.id.txt_historico_data_5);
    }

    private void adicionarValor(Atividade atividade) {
        switch (indice){
            case 0:
                txtSemAtividades.setVisibility(View.INVISIBLE);
                llHistorico2.setVisibility(View.INVISIBLE);
                llHistorico3.setVisibility(View.INVISIBLE);
                llHistorico4.setVisibility(View.INVISIBLE);
                llHistorico5.setVisibility(View.INVISIBLE);

                llHistorico1.setVisibility(View.VISIBLE);
                txtAtividadeDuracao1.setText(atividade.getDuracao().toString());
                txtAtividadeDistancia1.setText(atividade.getDistancia().toString());
                txtAtividadeCategoria1.setText(atividade.getCategoriaAtividade());
                txtAtividadeData1.setText(atividade.getDataAtividade());

                llHistorico1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HistoricoAtividadesActivity.this,
                                EsporteDetalheActivity.class);
                        intent.putExtra("atividadeSelecionada", atividade);
                        finish();
                        startActivity(intent);
                    }
                });

                break;
            case 1:
                txtSemAtividades.setVisibility(View.INVISIBLE);
                llHistorico3.setVisibility(View.INVISIBLE);
                llHistorico4.setVisibility(View.INVISIBLE);
                llHistorico5.setVisibility(View.INVISIBLE);

                llHistorico2.setVisibility(View.VISIBLE);
                txtAtividadeDuracao2.setText(atividade.getDuracao().toString());
                txtAtividadeDistancia2.setText(atividade.getDistancia().toString());
                txtAtividadeCategoria2.setText(atividade.getCategoriaAtividade());
                txtAtividadeData2.setText(atividade.getDataAtividade());

                llHistorico2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HistoricoAtividadesActivity.this,
                                EsporteDetalheActivity.class);
                        intent.putExtra("atividadeSelecionada", atividade);
                        finish();
                        startActivity(intent);
                    }
                });

                break;
            case 2:
                txtSemAtividades.setVisibility(View.INVISIBLE);
                llHistorico4.setVisibility(View.INVISIBLE);
                llHistorico5.setVisibility(View.INVISIBLE);

                llHistorico3.setVisibility(View.VISIBLE);
                txtAtividadeDuracao3.setText(atividade.getDuracao().toString());
                txtAtividadeDistancia3.setText(atividade.getDistancia().toString());
                txtAtividadeCategoria3.setText(atividade.getCategoriaAtividade());
                txtAtividadeData3.setText(atividade.getDataAtividade());

                llHistorico3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HistoricoAtividadesActivity.this,
                                EsporteDetalheActivity.class);
                        intent.putExtra("atividadeSelecionada", atividade);
                        finish();
                        startActivity(intent);
                    }
                });

                break;
            case 3:
                txtSemAtividades.setVisibility(View.INVISIBLE);
                llHistorico5.setVisibility(View.INVISIBLE);

                llHistorico4.setVisibility(View.VISIBLE);
                txtAtividadeDuracao4.setText(atividade.getDuracao().toString());
                txtAtividadeDistancia4.setText(atividade.getDistancia().toString());
                txtAtividadeCategoria4.setText(atividade.getCategoriaAtividade());
                txtAtividadeData4.setText(atividade.getDataAtividade());

                llHistorico4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HistoricoAtividadesActivity.this,
                                EsporteDetalheActivity.class);
                        intent.putExtra("atividadeSelecionada", atividade);
                        finish();
                        startActivity(intent);
                    }
                });

                break;
            case 4:
                txtSemAtividades.setVisibility(View.INVISIBLE);

                llHistorico5.setVisibility(View.VISIBLE);
                txtAtividadeDuracao5.setText(atividade.getDuracao().toString());
                txtAtividadeDistancia5.setText(atividade.getDistancia().toString());
                txtAtividadeCategoria5.setText(atividade.getCategoriaAtividade());
                txtAtividadeData5.setText(atividade.getDataAtividade());

                llHistorico5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HistoricoAtividadesActivity.this,
                                EsporteDetalheActivity.class);
                        intent.putExtra("atividadeSelecionada", atividade);
                        finish();
                        startActivity(intent);
                    }
                });

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