package br.com.clean_up_mobile.fragment;

import br.com.clean_up_mobile.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DiaristaServicoFragment extends Fragment{
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  View myFragmentView = inflater.inflate(R.layout.fragment_cliente_servico, container, false);

	  return myFragmentView;
	 }
}
