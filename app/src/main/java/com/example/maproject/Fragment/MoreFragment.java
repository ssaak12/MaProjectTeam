package com.example.maproject.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.maproject.API.Service.apiServiceProvince;
import com.example.maproject.API.model.Province;
import com.example.maproject.Adapter.ProvinceAdapter;
import com.example.maproject.databinding.FragmentMoreBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoreFragment extends Fragment {

    private FragmentMoreBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadProvinceList();
    }

    private void loadProvinceList()
    {
//        create Retrofit Object

        Retrofit httpClien = new Retrofit.Builder()
                .baseUrl("https://tests3bk.s3.ap-southeast-1.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()).build();

//        create service object
        apiServiceProvince apiService = httpClien.create(apiServiceProvince.class);

        Call<List<Province>> task = apiService.loadProvinceList();

        task.enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if(response.isSuccessful())
                {
                    showProvinceList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                Toast.makeText(getContext(), "Province List Fail", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void showProvinceList(List<Province> provinceList)
    {
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerview.setLayoutManager(LayoutManager);

        ProvinceAdapter adapter = new ProvinceAdapter();
        adapter.submitList(provinceList);
        binding.recyclerview.setAdapter(adapter);
    }
}
