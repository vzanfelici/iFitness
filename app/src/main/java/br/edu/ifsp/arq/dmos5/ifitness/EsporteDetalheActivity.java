package br.edu.ifsp.arq.dmos5.ifitness;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.edu.ifsp.arq.dmos5.ifitness.model.Atividade;
import br.edu.ifsp.arq.dmos5.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmos5.ifitness.viewmodel.UsuarioViewModel;

public class EsporteDetalheActivity extends AppCompatActivity {

    private Chip chipCaminhada;
    private Chip chipCiclismo;
    private Chip chipCorrida;
    private Chip chipNatacao;

    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextView txtDescricao;
    private ImageView imgEsporte;

    private TextInputEditText txtDistancia;
    private TextInputEditText txtTempo;

    private TextInputEditText txtDataRealizacao;
    private ImageButton btnCal;
    private DatePickerDialog.OnDateSetListener setListener;

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuario;
    private Atividade atividade;

    private Button btnConcluir;
    private Button btnAlterar;
    private Button btnExcluir;

    private String[] nomesEsportes = {"Caminhada", "Ciclismo", "Corrida", "Natação"};

    private String categoriaEsporte = nomesEsportes[0];

    private String descCaminhada = "Caminhar melhora sua saúde, " +
            "melhora a memória e a capacidade cognitiva, " +
            "melhora o seu humor e diminui o estresse, " +
            "é energizante, mas também ajuda você a dormir, " +
            "é um exercício seguro e fácil para iniciantes, " +
            "pode ser um treino vigoroso, " +
            "ajuda na construção de laços familiares e comunitários, e " +
            "é grátis e pode ser feito em qualquer lugar.";

    private String descCiclismo = "Ciclismo ajuda no emagrecimento, " +
            "melhora a resistência muscular, " +
            "melhora a respiração, " +
            "auxília no equilíbrio, " +
            "contribui com a saúde do coração, " +
            "elimina toxinas, " +
            "promove o bem-estar, " +
            "melhora o sistema imunológico, e " +
            "contribui para boas noites de sono.";

    private String descCorrida = "Corrida melhora o sono, " +
            "diminui a tensão e o estresse, " +
            "previne a osteoporose, " +
            "ajuda a controlar o colesterol, " +
            "aumenta a autoestima, " +
            "é uma aliada na redução de gordura corporal, e " +
            "combate a depressão.";

    private String descNatacao = "Natação ajuda o sistema respiratório, " +
            "aumenta a longevidade, " +
            "trabalha todo o corpo, " +
            "ajuda a relaxar, " +
            "ajuda no fortalecimento e correção corporal, " +
            "mantém a pressão arterial baixa, " +
            "controla a glicose, e " +
            "desenvolve a parte cognitiva.";


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esporte_detalhe);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText("Realizar Atividade");

        txtDescricao = findViewById(R.id.txt_descricao_esporte_detalhe);
        imgEsporte = findViewById(R.id.img_esporte_detalhe);
        btnConcluir = findViewById(R.id.esporte_detalhe_btn_concluir);
        btnAlterar = findViewById(R.id.esporte_detalhe_btn_alterar);
        btnExcluir = findViewById(R.id.esporte_detalhe_btn_excluir);

        txtDistancia = findViewById(R.id.txt_produto_detalhe_distancia);
        txtTempo = findViewById(R.id.txt_produto_detalhe_tempo);

        txtDataRealizacao = findViewById(R.id.txt_produto_detalhe_data_realizacao);
        btnCal = findViewById(R.id.btn_atividade_calendario);
        Calendar calendar = Calendar.getInstance();
        final int ano = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EsporteDetalheActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener, ano, mes, dia);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int diaMes) {
                mes = mes + 1;
                String date = diaMes + "/" + mes + "/" + ano;
                txtDataRealizacao.setText(date);
            }
        };

        chipCaminhada = findViewById(R.id.chip_group_tipo_caminhada);
        chipCaminhada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriaEsporte = nomesEsportes[0];
                txtDescricao.setText(descCaminhada);
                imgEsporte.setImageResource(R.drawable.walking);
            }
        });

        // -- iniciar activity na corrida
        chipCaminhada.setChecked(true);
        txtDescricao.setText(descCaminhada);
        imgEsporte.setImageResource(R.drawable.walking);

        chipCiclismo = findViewById(R.id.chip_group_tipo_ciclismo);
        chipCiclismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriaEsporte = nomesEsportes[1];
                txtDescricao.setText(descCiclismo);
                imgEsporte.setImageResource(R.drawable.cycling);
            }
        });

        chipCorrida = findViewById(R.id.chip_group_tipo_corrida);
        chipCorrida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriaEsporte = nomesEsportes[2];
                txtDescricao.setText(descCorrida);
                imgEsporte.setImageResource(R.drawable.running);
            }
        });

        chipNatacao = findViewById(R.id.chip_group_tipo_natacao);
        chipNatacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriaEsporte = nomesEsportes[3];
                txtDescricao.setText(descNatacao);
                imgEsporte.setImageResource(R.drawable.swimming);
            }
        });

        Intent intent = getIntent();
        atividade = (Atividade) intent.getSerializableExtra("atividadeSelecionada");
        if (atividade != null) {
            String atividadeEscolhida = atividade.getCategoriaAtividade();
            if (atividadeEscolhida.equals(nomesEsportes[0])) {
                categoriaEsporte = nomesEsportes[0];
                txtDescricao.setText(descCaminhada);
                imgEsporte.setImageResource(R.drawable.walking);
                chipCaminhada.setChecked(true);
            } else if (atividadeEscolhida.equals(nomesEsportes[1])) {
                categoriaEsporte = nomesEsportes[1];
                txtDescricao.setText(descCiclismo);
                imgEsporte.setImageResource(R.drawable.cycling);
                chipCiclismo.setChecked(true);
            } else if (atividadeEscolhida.equals(nomesEsportes[2])) {
                categoriaEsporte = nomesEsportes[2];
                txtDescricao.setText(descCorrida);
                imgEsporte.setImageResource(R.drawable.running);
                chipCorrida.setChecked(true);
            } else {
                categoriaEsporte = nomesEsportes[3];
                txtDescricao.setText(descNatacao);
                imgEsporte.setImageResource(R.drawable.swimming);
                chipNatacao.setChecked(true);
            }

            txtDistancia.setText(atividade.getDistancia().toString());
            txtTempo.setText(atividade.getDuracao().toString());
            txtDataRealizacao.setText(atividade.getDataAtividade());

            btnConcluir.setBackgroundColor(getResources().getColor(R.color.gray));
            btnConcluir.setClickable(false);
            btnConcluir.setEnabled(false);

            btnAlterar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btnAlterar.setClickable(true);
            btnAlterar.setEnabled(true);

            btnExcluir.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btnExcluir.setClickable(true);
            btnExcluir.setEnabled(true);
        }

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioViewModel.isLogged().observe(EsporteDetalheActivity.this, new Observer<Usuario>() {
                    @Override
                    public void onChanged(Usuario usuario) {
                        if (usuario != null) {
                            EsporteDetalheActivity.this.usuario = usuario;

                            if (validate()) {
                                Atividade atividade = new Atividade(
                                        usuario,
                                        categoriaEsporte,
                                        Double.parseDouble(txtDistancia.getText().toString()),
                                        Double.parseDouble(txtTempo.getText().toString()),
                                        txtDataRealizacao.getText().toString()
                                );

                                usuarioViewModel.createAtividade(atividade);

                                int pontuacaoUsuario = usuario.getPontuacao();
                                Double pontuacaoAtividadeDouble = Double.parseDouble(atividade.getDistancia().toString());
                                int pontuacaoAtividadeInt = pontuacaoAtividadeDouble.intValue();
                                int pontuacaoTotal = pontuacaoUsuario + pontuacaoAtividadeInt;
                                if (pontuacaoTotal>=0){
                                    usuario.setPontuacao(pontuacaoTotal);
                                } else {
                                    usuario.setPontuacao(0);
                                }

                                List<String> arrayEmblemas = new ArrayList<>();

                                usuarioViewModel.getAtividadesById().observe(EsporteDetalheActivity.this, new Observer<List<Atividade>>() {
                                    Double distanciaTotal=0.0;
                                    int i=0;

                                    @Override
                                    public void onChanged(List<Atividade> atividades) {
                                        for (Atividade a: atividades) {
                                            if (a != null) {
                                                distanciaTotal = distanciaTotal+a.getDistancia();

                                                if (i==atividades.size()-1){

                                                    if (distanciaTotal>=15 && distanciaTotal<25){
                                                        arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                    } else if (distanciaTotal>=25 && distanciaTotal<50){
                                                        arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                        arrayEmblemas.add("Troféu Bronze (25 km)");
                                                    } else if (distanciaTotal>=50 && distanciaTotal<100){
                                                        arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                        arrayEmblemas.add("Troféu Bronze (25 km)");
                                                        arrayEmblemas.add("Troféu Prata (50 km)");
                                                    }  else if (distanciaTotal>=100 && distanciaTotal<150){
                                                        arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                        arrayEmblemas.add("Troféu Bronze (25 km)");
                                                        arrayEmblemas.add("Troféu Prata (50 km)");
                                                        arrayEmblemas.add("Troféu Ouro (100 km)");
                                                    } else if (distanciaTotal>=150){
                                                        arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                        arrayEmblemas.add("Troféu Bronze (25 km)");
                                                        arrayEmblemas.add("Troféu Prata (50 km)");
                                                        arrayEmblemas.add("Troféu Ouro (100 km)");
                                                        arrayEmblemas.add("Troféu Platinum (150 km)");
                                                    } else {
                                                        arrayEmblemas.removeAll(arrayEmblemas);
                                                    }
                                                }
                                                i++;
                                            }
                                        }
                                        usuario.setEmblemas(arrayEmblemas);
                                        usuarioViewModel.update(usuario);
                                    }
                                });

                                usuarioViewModel.update(usuario);

                                Toast.makeText(EsporteDetalheActivity.this, "Atividade registrada com Sucesso", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } else {
                            startActivity(new Intent(EsporteDetalheActivity.this,
                                    UsuarioLoginActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioViewModel.isLogged().observe(EsporteDetalheActivity.this, new Observer<Usuario>() {
                    @Override
                    public void onChanged(Usuario usuario) {
                        if (usuario != null) {
                            EsporteDetalheActivity.this.usuario = usuario;

                            if (validate()) {
                                Double pontuacaoAtividadeDoubleAntigo = Double.parseDouble(atividade.getDistancia().toString());
                                int pontuacaoAtividadeIntAntigo = pontuacaoAtividadeDoubleAntigo.intValue();

                                atividade.setDuracao(Double.parseDouble(txtTempo.getText().toString()));
                                atividade.setDistancia(Double.parseDouble(txtDistancia.getText().toString()));
                                atividade.setDataAtividade(txtDataRealizacao.getText().toString());
                                atividade.setCategoriaAtividade(categoriaEsporte);

                                usuarioViewModel.updateAtividade(atividade);

                                int pontuacaoUsuario = usuario.getPontuacao();
                                Double pontuacaoAtividadeDouble = Double.parseDouble(atividade.getDistancia().toString());
                                int pontuacaoAtividadeInt = pontuacaoAtividadeDouble.intValue();
                                int pontuacaoTotal = pontuacaoUsuario + pontuacaoAtividadeInt - pontuacaoAtividadeIntAntigo;
                                if (pontuacaoTotal>=0){
                                    usuario.setPontuacao(pontuacaoTotal);
                                } else {
                                    usuario.setPontuacao(0);
                                }

                                List<String> arrayEmblemas = new ArrayList<>();

                                usuarioViewModel.getAtividadesById().observe(EsporteDetalheActivity.this, new Observer<List<Atividade>>() {
                                    Double distanciaTotal=0.0;
                                    int i=0;

                                    @Override
                                    public void onChanged(List<Atividade> atividades) {
                                        for (Atividade a: atividades) {
                                            if (a != null) {
                                                distanciaTotal = distanciaTotal+a.getDistancia();

                                                if (i==atividades.size()-1){

                                                    if (distanciaTotal>=15 && distanciaTotal<25){
                                                        arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                    } else if (distanciaTotal>=25 && distanciaTotal<50){
                                                        arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                        arrayEmblemas.add("Troféu Bronze (25 km)");
                                                    } else if (distanciaTotal>=50 && distanciaTotal<100){
                                                        arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                        arrayEmblemas.add("Troféu Bronze (25 km)");
                                                        arrayEmblemas.add("Troféu Prata (50 km)");
                                                    }  else if (distanciaTotal>=100 && distanciaTotal<150){
                                                        arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                        arrayEmblemas.add("Troféu Bronze (25 km)");
                                                        arrayEmblemas.add("Troféu Prata (50 km)");
                                                        arrayEmblemas.add("Troféu Ouro (100 km)");
                                                    } else if (distanciaTotal>=150){
                                                        arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                        arrayEmblemas.add("Troféu Bronze (25 km)");
                                                        arrayEmblemas.add("Troféu Prata (50 km)");
                                                        arrayEmblemas.add("Troféu Ouro (100 km)");
                                                        arrayEmblemas.add("Troféu Platinum (150 km)");
                                                    } else {
                                                        arrayEmblemas.removeAll(arrayEmblemas);
                                                    }
                                                }
                                                i++;
                                            }
                                        }
                                        usuario.setEmblemas(arrayEmblemas);
                                        usuarioViewModel.update(usuario);
                                    }
                                });

                                usuarioViewModel.update(usuario);

                                Toast.makeText(EsporteDetalheActivity.this, "Atividade atualizada com Sucesso", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                });
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioViewModel.isLogged().observe(EsporteDetalheActivity.this, new Observer<Usuario>() {
                    @Override
                    public void onChanged(Usuario usuario) {
                        if (usuario != null) {
                            EsporteDetalheActivity.this.usuario = usuario;

                            usuarioViewModel.delete(atividade);

                            int pontuacaoUsuario = usuario.getPontuacao();
                            Double pontuacaoAtividadeDouble = Double.parseDouble(atividade.getDistancia().toString());
                            int pontuacaoAtividadeInt = pontuacaoAtividadeDouble.intValue();
                            int pontuacaoTotal = pontuacaoUsuario - pontuacaoAtividadeInt;
                            if (pontuacaoTotal>=0){
                                usuario.setPontuacao(pontuacaoTotal);
                            } else {
                                usuario.setPontuacao(0);
                            }

                            List<String> arrayEmblemas = new ArrayList<>();

                            usuarioViewModel.getAtividadesById().observe(EsporteDetalheActivity.this, new Observer<List<Atividade>>() {
                                Double distanciaTotal=0.0;
                                int i=0;

                                @Override
                                public void onChanged(List<Atividade> atividades) {
                                    for (Atividade a: atividades) {
                                        if (a != null) {
                                            distanciaTotal = distanciaTotal+a.getDistancia();

                                            if (i==atividades.size()-1){
                                                System.out.println("Distancia total: " + distanciaTotal);

                                                if (distanciaTotal>=15 && distanciaTotal<25){
                                                    arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                } else if (distanciaTotal>=25 && distanciaTotal<50){
                                                    arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                    arrayEmblemas.add("Troféu Bronze (25 km)");
                                                } else if (distanciaTotal>=50 && distanciaTotal<100){
                                                    arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                    arrayEmblemas.add("Troféu Bronze (25 km)");
                                                    arrayEmblemas.add("Troféu Prata (50 km)");
                                                }  else if (distanciaTotal>=100 && distanciaTotal<150){
                                                    arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                    arrayEmblemas.add("Troféu Bronze (25 km)");
                                                    arrayEmblemas.add("Troféu Prata (50 km)");
                                                    arrayEmblemas.add("Troféu Ouro (100 km)");
                                                } else if (distanciaTotal>=150){
                                                    arrayEmblemas.add("Troféu Iniciante (15 km)");
                                                    arrayEmblemas.add("Troféu Bronze (25 km)");
                                                    arrayEmblemas.add("Troféu Prata (50 km)");
                                                    arrayEmblemas.add("Troféu Ouro (100 km)");
                                                    arrayEmblemas.add("Troféu Platinum (150 km)");
                                                } else {
                                                    arrayEmblemas.removeAll(arrayEmblemas);
                                                }

                                            }
                                            i++;
                                        }
                                    }
                                    usuario.setEmblemas(arrayEmblemas);
                                    usuarioViewModel.update(usuario);
                                }
                            });

                            usuarioViewModel.update(usuario);

                            Toast.makeText(EsporteDetalheActivity.this, "Atividade removida com Sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });

    }

    private boolean validate() {
        boolean isValid = true;

        String distancia = txtDistancia.getText().toString();
        String tempo = txtTempo.getText().toString();

        Pattern pattern = Pattern.compile("[a-zA-Z\\s]+");
        Matcher match = pattern.matcher(distancia);
        Matcher match2 = pattern.matcher(tempo);

        if (txtDistancia.getText().toString().trim().isEmpty()) {
            txtDistancia.setError("Preencha o campo Distância");
            isValid = false;
        } else if (match.find()) {
            txtDistancia.setError("O campo Distância não pode conter letras.");
            isValid = false;
        } else {
            txtDistancia.setError(null);
        }

        if (txtTempo.getText().toString().trim().isEmpty()) {
            txtTempo.setError("Preencha o campo Tempo");
            isValid = false;
        } else if (match2.find()) {
            txtTempo.setError("O campo Tempo não pode conter letras.");
            isValid = false;
        } else {
            txtTempo.setError(null);
        }

        if (txtDataRealizacao.getText().toString().trim().isEmpty()) {
            txtDataRealizacao.setError("Preencha o campo Data de Realização");
            isValid = false;
        } else {
            txtDataRealizacao.setError(null);
        }

        return isValid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}