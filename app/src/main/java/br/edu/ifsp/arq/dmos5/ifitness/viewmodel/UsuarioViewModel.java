package br.edu.ifsp.arq.dmos5.ifitness.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Optional;

import br.edu.ifsp.arq.dmos5.ifitness.model.Atividade;
import br.edu.ifsp.arq.dmos5.ifitness.model.Usuario;
import br.edu.ifsp.arq.dmos5.ifitness.repository.UsuariosRepository;

public class UsuarioViewModel extends AndroidViewModel {

    public static final String USUARIO_ID = "USUARIO_ID";

    private UsuariosRepository usuariosRepository;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        usuariosRepository = new UsuariosRepository(application);
    }

    public void createUsuario(Usuario usuario){
        usuariosRepository.createUsuario(usuario);
    }

    public void update(Usuario usuario){
        usuariosRepository.update(usuario);
    }

    public void updateAtividade(Atividade atividade){
        usuariosRepository.updateAtividade(atividade);
    }

    public LiveData<Usuario> login(String email, String senha) {
        return usuariosRepository.login(email, senha);
    }

    public void logout(){
        PreferenceManager.getDefaultSharedPreferences(getApplication())
                .edit().remove(USUARIO_ID)
                .apply();
    }

    public LiveData<Usuario> isLogged(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        Optional<String> id = Optional.ofNullable(sharedPreferences.getString(USUARIO_ID, null));
        if(!id.isPresent()){
            return new MutableLiveData<>(null);
        }
        return usuariosRepository.load(id.get());
    }

    public void resetPassword(String email) {
        usuariosRepository.resetPassword(email);
    }

    public void createAtividade(Atividade atividade) {
        usuariosRepository.createAtividade(atividade);
    }

    public MutableLiveData<List<Atividade>> getAtividadesById() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        Optional<String> id = Optional.ofNullable(sharedPreferences.getString(USUARIO_ID, null));
        if(!id.isPresent()){
            return new MutableLiveData<>(null);
        }
        return usuariosRepository.getAtividadesById(id.get());
    }

    public MutableLiveData<List<Usuario>> getUsuarios(){
        return usuariosRepository.getUsuarios();
    };


    public void delete(Atividade atividade) {
        usuariosRepository.delete(atividade);
    }
}
