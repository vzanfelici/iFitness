package br.edu.ifsp.arq.dmos5.ifitness;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;

import br.edu.ifsp.arq.dmos5.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmos5.ifitness.viewmodel.UsuarioViewModel;

public class MainActivity extends AppCompatActivity {

    private LinearLayout esporteDetalhe;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView txtTitulo;
    private TextView txtLogin;

    private UsuarioViewModel usuarioViewModel;

    private ImageView imgInstagram;
    private ImageView imgFacebook;
    private ImageView imgGitHub;
    private ImageView imgYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioViewModel = new ViewModelProvider(this)
                .get(UsuarioViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtTitulo = findViewById(R.id.toolbar_titulo);
        txtTitulo.setText(getString(R.string.app_name));

        drawerLayout = findViewById(R.id.nav_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.toogle_open,
                R.string.toogle_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_account:
                        intent = new Intent(MainActivity.this, UsuarioPerfilActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_activities:
                        intent = new Intent(MainActivity.this, HistoricoAtividadesActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_stats:
                        intent = new Intent(MainActivity.this, EstatisticaAtividadeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_ranking:
                        intent = new Intent(MainActivity.this, RankingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_settings:
                        intent = new Intent(MainActivity.this,
                                SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        usuarioViewModel.logout();
                        finish();
                        startActivity(getIntent());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        esporteDetalhe = findViewById(R.id.ll_esporte_detalhe);
        esporteDetalhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        EsporteDetalheActivity.class);
                startActivity(intent);
            }
        });

        txtLogin = navigationView.getHeaderView(0)
                .findViewById(R.id.header_profile_name);
        txtLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        UsuarioLoginActivity.class);
                startActivity(intent);
            }
        });

        imgInstagram = findViewById(R.id.img_insta);
        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.instagram.com/ifspcampusararaquara";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        imgFacebook = findViewById(R.id.img_facebook);
        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.facebook.com/IFSParq";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        imgGitHub = findViewById(R.id.img_git);
        imgGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.github.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        imgYoutube = findViewById(R.id.img_yt);
        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.youtube.com/c/IFSPAraraquaraa";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


        usuarioViewModel.isLogged().observe(MainActivity.this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if(usuario != null){
                    txtLogin.setText(usuario.getNome());
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}