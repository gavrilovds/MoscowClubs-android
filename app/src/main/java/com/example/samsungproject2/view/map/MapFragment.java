package com.example.samsungproject2.view.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.samsungproject2.R;
import com.example.samsungproject2.databinding.FragmentMapBinding;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.viewmodel.map.MapViewModel;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.List;


public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private MapView mapview;
    private MapViewModel mapViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        MapKitFactory.initialize(getContext());
        mapViewModel = new ViewModelProvider(requireActivity()).get(MapViewModel.class);
        mapview = binding.mapview;
        mapViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Club>>() {
            @Override
            public void onChanged(List<Club> clubs) {
                mapview.getMap().move(
                        new CameraPosition(new Point(55.7522, 37.6156), 12.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.LINEAR, 0),
                        null);
                for (Club club : clubs) {
                    mapview.getMap().getMapObjects().addPlacemark(new Point(club.getLatitude(), club.getLongitude()),
                            ImageProvider.fromResource(getContext(), R.drawable.club_location))
                            .addTapListener(new MapObjectTapListener() {
                        @Override
                        public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
                            Log.d("help", "onMapObjectTap: ");
                            Bundle bundle = new Bundle();
                            bundle.putString("CLUB_NAME", club.getName());
                            bundle.putString("FROM", "map_fragment");
                            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                                    .navigate(R.id.action_bottom_nav_map_to_clubInfoFragment, bundle);
                            return false;
                        }
                    });
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview = binding.mapview;
        mapview.onStart();
    }

    @Override
    public void onStop() {
        mapview = binding.mapview;
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}