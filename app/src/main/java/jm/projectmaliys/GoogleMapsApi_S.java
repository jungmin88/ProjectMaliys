package jm.projectmaliys;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsApi_S extends AppCompatActivity implements OnMapReadyCallback
{
    private GoogleMap gMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_maps_api__s);
/*
        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();
        String[] diaryDate = resources.getStringArray(R.array.diary_date);
        String[] diaryContents = resources.getStringArray(R.array.diary_contents);
        //TextView diaryContent = (TextView) findViewById(R.id.diary_content);
        String[] diaryAvator = resources.getStringArray(R.array.diary_avator);
        TypedArray diaryPictures = resources.obtainTypedArray(R.array.diary_picture);
        ImageView diaryPicture = (ImageView) findViewById(R.id.image);
        diaryPictures.recycle();
*/
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initilizeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    //맵이 사용가능해지면 호출되는 콜백메소드이다.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // 시작위치 지정하기
        LatLng seoul = new LatLng(37.550947, 126.989296);
        gMap.addMarker(new MarkerOptions().position(seoul).title("서울타워"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));

        //줌 애니메이션
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        gMap.animateCamera(zoom);

        //마커 표시하기
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(37.555744, 126.970431))
                .title("서울역")
                .snippet("Seoul Station");//부제
        gMap.addMarker(marker).showInfoWindow();

        //자신의 위치
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap.setMyLocationEnabled(true);


        //이벤트 처리하기
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle() + "를 클릭했습니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

}
