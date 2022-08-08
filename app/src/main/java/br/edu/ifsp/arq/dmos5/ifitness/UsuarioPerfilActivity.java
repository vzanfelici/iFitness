package br.edu.ifsp.arq.dmos5.ifitness;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.edu.ifsp.arq.dmos5.ifitness.model.Atividade;
import br.edu.ifsp.arq.dmos5.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmos5.ifitness.viewmodel.UsuarioViewModel;

public class UsuarioPerfilActivity extends AppCompatActivity {

    private final int REQUEST_TAKE_PHOTO = 1;

    private Toolbar toolbar;
    private TextView txtTitulo;
    private TextInputEditText txtNome;
    private TextInputEditText txtEmail;
    private TextInputEditText txtTelefone;
    private TextInputEditText txtPeso;
    private Spinner spnGen;
    private TextView txtEmblemas;
    private Button btnAtualizar;

    private TextInputEditText txtData;
    private ImageButton btnCal;
    private DatePickerDialog.OnDateSetListener setListener;

    private UsuarioViewModel usuarioViewModel;

    private Usuario usuario;
    private Atividade atividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_perfil);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText(R.string.usuario_perfil_titulo);
        txtNome = findViewById(R.id.txt_edit_perfil_nome);
        txtEmail = findViewById(R.id.txt_edit_perfil_email);
        txtTelefone = findViewById(R.id.txt_edit_perfil_telefone);
        txtPeso = findViewById(R.id.txt_edit_perfil_peso);
        txtEmblemas = null;

        spnGen = findViewById(R.id.sp_generos);

        txtData = findViewById(R.id.txt_edit_perfil_data_nascimento);
        btnCal = findViewById(R.id.btn_calendario);
        Calendar calendar = Calendar.getInstance();
        final int ano = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UsuarioPerfilActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener, ano, mes, dia);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int diaMes) {
                mes = mes+1;
                String date = diaMes+"/"+mes+"/"+ano;
                txtData.setText(date);
            }
        };

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);

        usuarioViewModel.isLogged().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if(usuario != null){
                    UsuarioPerfilActivity.this.usuario = usuario;
                    txtNome.setText(usuario.getNome());
                    txtEmail.setText(usuario.getEmail());

                    Double peso = usuario.getPeso();
                    String telefone = usuario.getTelefone();
                    String data = usuario.getDataNascimento();
                    String genero = usuario.getGenero();
                    if (telefone!=null){
                        txtTelefone.setText(telefone);
                    }
                    if (data!=null){
                        txtData.setText(data);
                    }
                    if (peso!=null){
                        txtPeso.setText(peso.toString());
                    }
                    if (genero!=null){
                        for (int i=0; i<spnGen.getCount(); i++){
                            if (spnGen.getItemAtPosition(i).equals(genero)){
                                spnGen.setSelection(i);
                            }
                        }
                    }

                }else{
                    startActivity(new Intent(UsuarioPerfilActivity.this,
                            UsuarioLoginActivity.class));
                    finish();
                }
            }
        });

        btnAtualizar = findViewById(R.id.btn_usuario_atualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }

    public void update(){
        if(!validate()){
            return;
        }

        usuario.setNome(txtNome.getText().toString());
        usuario.setEmail(txtEmail.getText().toString());
        usuario.setPeso(Double.parseDouble(txtPeso.getText().toString()));
        usuario.setTelefone(txtTelefone.getText().toString());
        usuario.setDataNascimento(txtData.getText().toString());
        usuario.setGenero(spnGen.getSelectedItem().toString());

        usuarioViewModel.update(usuario);

        Toast.makeText(this, getString(R.string.msg_perfil_sucesso), Toast.LENGTH_SHORT).show();
    }

    private boolean validate(){
        boolean isValid = true;
        if(txtNome.getText().toString().trim().isEmpty()){
            txtNome.setError("Preencha o campo nome");
            isValid = false;
        }else{
            txtNome.setError(null);
        }

        if(txtEmail.getText().toString().trim().isEmpty()){
            txtEmail.setError("Preencha o campo email");
            isValid = false;
        }else{
            txtEmail.setError(null);
        }

        if (txtPeso.getText().toString().trim().isEmpty()){
            txtPeso.setError("Preencha o campo peso");
            isValid = false;
        } else {
            txtPeso.setError(null);
        }

        if(txtTelefone.getText().toString().trim().isEmpty()){
            txtTelefone.setError("Preencha o campo telefone");
            isValid = false;
        }else{
            txtTelefone.setError(null);
        }

        if(txtData.getText().toString().trim().isEmpty()){
            txtData.setError("Preencha o campo data");
            isValid = false;
        }else{
            txtData.setError(null);
        }

        if (spnGen.getSelectedItemPosition()==0){
            TextView errorText = (TextView)spnGen.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            isValid = false;
        }

        return isValid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}