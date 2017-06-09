package com.thesis.anti.ragging;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thesis.anti.ragging.models.ServerRequest;
import com.thesis.anti.ragging.models.ServerResponse;
import com.thesis.anti.ragging.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment  implements View.OnClickListener{

    private AppCompatButton btn_register;
    private EditText et_email,et_password,et_name,et_discipline,et_institute,et_id,et_author1,et_author2,et_author3;
    private TextView tv_login;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){

        btn_register = (AppCompatButton)view.findViewById(R.id.btn_register);
        tv_login = (TextView)view.findViewById(R.id.tv_login);
        et_name = (EditText)view.findViewById(R.id.et_name);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_password = (EditText)view.findViewById(R.id.et_password);
        et_discipline=(EditText)view.findViewById(R.id.et_discipline);
        et_institute=(EditText)view.findViewById(R.id.et_institute);
        et_id=(EditText)view.findViewById(R.id.et_id);

        et_author1=(EditText)view.findViewById(R.id.et_author1);
        et_author2=(EditText)view.findViewById(R.id.et_author2);
        et_author3=(EditText)view.findViewById(R.id.et_author3);

        progress = (ProgressBar)view.findViewById(R.id.progress);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:

                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String discipline = et_discipline.getText().toString();
                String institute = et_institute.getText().toString();
                String id = et_id.getText().toString();

                String author1 = et_author1.getText().toString();
                String author2 = et_author2.getText().toString();
                String author3 = et_author3.getText().toString();

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !discipline.isEmpty()
                        && !id.isEmpty() && !institute.isEmpty() && !author1.isEmpty() && !author2.isEmpty() && !author3.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    //Snackbar.make(getView(), institute + author1, Snackbar.LENGTH_LONG).show();
                    registerProcess(name,email,password,discipline,institute,id,author1,author2,author3);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;

        }

    }

    private void registerProcess(String name, String email, String password, String discipline, String institute, String id, String author1, String author2, String author3){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setDiscipline(discipline);
        user.setInstitute(institute);
        user.setStudent_id(id);
        user.setAuthor1(author1);
        user.setAuthor2(author2);
        user.setAuthor3(author3);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage() , Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }

    private void goToLogin(){

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }
}
