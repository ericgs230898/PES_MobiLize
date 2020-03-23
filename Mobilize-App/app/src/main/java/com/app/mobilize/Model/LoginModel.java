package com.app.mobilize.Model;

import androidx.annotation.NonNull;

import com.app.mobilize.Presentador.Interface.LoginInterface;
import com.app.mobilize.Presentador.LoginPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginModel implements LoginInterface.Model {

    private LoginInterface.TaskListener listener;
    private FirebaseAuth mAuth;

    public LoginModel(LoginInterface.TaskListener taskListener) {
        this.listener = taskListener;
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void doLogin(String email, String password) {
        mAuth.getCurrentUser().reload();
        if (mAuth.getCurrentUser().isEmailVerified() == false){
            listener.onError("Confirma primero tu dirección de correo electrónico!");
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        listener.onSuccess();
                    } else {
                        listener.onError("Error en el correo electrónico o contraseña");
                    }
                }
            });
        }
    }
}
