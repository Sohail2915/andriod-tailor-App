package com.example.tailorcom;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {


    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    private EditText registeredEmail;
    private Button resetPasswordBtn;
    private TextView goBack;

    private FrameLayout parentFrameLayout;

    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);


        registeredEmail=view.findViewById(R.id.forgot_password_email);
        resetPasswordBtn=view.findViewById(R.id.reset_password_btn);
        goBack=view.findViewById(R.id.forgot_password_goback_tv);

        emailIconContainer=view.findViewById(R.id.foget_pass_email_iconContainer);
        emailIcon= view.findViewById(R.id.forgot_pass_email_icon_red);
        emailIconText=view.findViewById(R.id.forgot_pass_email_iconText);
        progressBar=view.findViewById(R.id.forgot_pass_progressBar);

        parentFrameLayout=getActivity().findViewById(R.id.register_framlayout);

        firebaseAuth=FirebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registeredEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        resetPasswordBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIconText.setVisibility(View.GONE);

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                resetPasswordBtn.setEnabled(false);
                resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.sendPasswordResetEmail(registeredEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            ScaleAnimation scaleAnimation =new ScaleAnimation(1,0,1,0,emailIcon.getWidth()/2,emailIcon.getHeight()/2);
                            scaleAnimation.setDuration(100);
                            scaleAnimation.setInterpolator(new AccelerateInterpolator());
                            scaleAnimation.setRepeatMode(Animation.REVERSE);
                            scaleAnimation.setRepeatCount(1);
                            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }
                                @Override
                                public void onAnimationEnd(Animation animation) {
                                   //emailIconText.setText("Recovery email sent successfully! check your inbox");
                                    emailIconText.setTextColor(getResources().getColor(R.color.successGreen));

                                    TransitionManager.beginDelayedTransition(emailIconContainer);
                                    emailIcon.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                    emailIcon.setImageResource(R.mipmap.green_email);

                                }
                            });
                            emailIcon.startAnimation(scaleAnimation);
                            Toast.makeText(getActivity(),"Recovery email sent successfully! check your inbox", Toast.LENGTH_LONG).show();
                        }
                        else {
                            String error=task.getException().getMessage();

                            resetPasswordBtn.setEnabled(true);
                            resetPasswordBtn.setTextColor(Color.rgb(255,255,255));

                            emailIconText.setText(error);
                            emailIconText.setTextColor(getResources().getColor(R.color.colorPrimary));
                            TransitionManager.beginDelayedTransition(emailIconContainer);
                            emailIconText.setVisibility(View.VISIBLE);
                        }
                        progressBar.setVisibility(View.GONE);

                    }
                });
            }
        });

        goBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });


    }
    private void checkInputs()
    {
        if(TextUtils.isEmpty(registeredEmail.getText())){
            resetPasswordBtn.setEnabled(false);
            resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));
        }else {
            resetPasswordBtn.setEnabled(true);
            resetPasswordBtn.setTextColor(Color.rgb(255,255,255));
        }
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.sildeout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
