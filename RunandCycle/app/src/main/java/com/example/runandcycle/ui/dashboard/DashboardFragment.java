package com.example.runandcycle.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.runandcycle.R;
import com.example.runandcycle.databinding.FragmentDashboardBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class DashboardFragment extends Fragment  {


        FusedLocationProviderClient client;
        private FragmentDashboardBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        client = LocationServices.getFusedLocationProviderClient(getActivity());



       SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
       if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) ==
        PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
           LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
           if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
               client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                   @Override
                   public void onComplete(@NonNull Task<Location> task) {
                       Location location = task.getResult();
                       if(location != null){
                           supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                               @Override
                               public void onMapReady(@NonNull GoogleMap googleMap) {
                                   LatLng locationworking = new LatLng(location.getLatitude(),location.getLongitude());
                                   googleMap.addMarker(new MarkerOptions().position(locationworking).title("denayer"));
                                   googleMap.moveCamera(CameraUpdateFactory.newLatLng(locationworking));
                                   googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                           locationworking,10));
                               }
                           });
                       }

                   }
               });
           }else {
               startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
           }

        }
        else{
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    LatLng denayer = new LatLng(51.069522,4.501010);
                    googleMap.addMarker(new MarkerOptions().position(denayer).title("denayer"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(denayer));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            denayer,10));
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(@NonNull LatLng latLng) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(latLng);
                            markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                            googleMap.clear();
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    latLng,15
                            ));
                            googleMap.addMarker(markerOptions);

                        }
                    });
                }
            });
        }

        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}