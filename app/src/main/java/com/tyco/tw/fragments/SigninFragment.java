package com.tyco.tw.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tyco.tw.R;
import com.tyco.tw.callbacks.TasksCallback;
import com.tyco.tw.controllers.TasksController;
import com.tyco.tw.models.Tasks;
import com.tyco.tw.network.RetrofitError;
import com.tyco.tw.network.TWApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SigninFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SigninFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SigninFragment extends Fragment implements TasksCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.usernameTIL)
    TextInputEditText _usernameTV;
    @BindView(R.id.passwordTIL)
    TextInputEditText _passwordTV;
    @BindView(R.id.progressBar)
    ProgressBar _progressBar;
    @BindView(R.id.progressBarText)
    TextView _progressBarText;
    @BindView(R.id.signinBTN)
    Button _signinButton;


    public SigninFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SigninFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        setHasOptionsMenu(true);

        ButterKnife.bind(this, view);

        _progressBar.setVisibility(View.INVISIBLE);

        return view;
    }


    // User triggers Sign in
    @OnClick(R.id.signinBTN) void signIn() {

        CharSequence username = _usernameTV.getText();
        if (username.length() < 3) {
            _usernameTV.setError(getResources().getString(R.string.invalid_username));
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            _usernameTV.setError(getResources().getString(R.string.invalid_username));
            return;
        }
        if (_passwordTV.getText().length() < 8) {
            _passwordTV.setError(getResources().getString(R.string.invalid_password));
            return;
        }

        disableUI();

        TWApiService.tasks(_usernameTV.getText().toString(), _passwordTV.getText().toString(), this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onShowDialog(String title, String message);
        void onShowTasks();
    }

    @Override
    public void requestUnsuccessful(RetrofitError retrofitError) {
        if (isVisible()) {
            enableUI();
            mListener.onShowDialog(getString(R.string.error_title), retrofitError.getMessage());
        }
    }

    @Override
    public void requestFailure(RetrofitError retrofitError, Throwable t) {
        if (isVisible()) {
            enableUI();
            mListener.onShowDialog(getString(R.string.error_title), retrofitError.getMessage());
        }
    }

    @Override
    public void tasksSuccess(Tasks tasks) {
        enableUI();
        TasksController.setTasks(tasks);
        if (mListener != null) {
            mListener.onShowTasks();
        }
    }



    private void disableUI() {
        _usernameTV.setEnabled(false);
        _passwordTV.setEnabled(false);
        _progressBar.setVisibility(View.VISIBLE);
        _progressBarText.setVisibility(View.VISIBLE);
        _signinButton.setEnabled(false);
    }

    private void enableUI() {
        _usernameTV.setEnabled(true);
        _passwordTV.setEnabled(true);
        _progressBar.setVisibility(View.INVISIBLE);
        _progressBarText.setVisibility(View.INVISIBLE);
        _signinButton.setEnabled(true);
    }
}
