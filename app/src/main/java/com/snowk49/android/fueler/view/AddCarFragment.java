package com.snowk49.android.fueler.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.snowk49.android.fueler.MainActivity;
import com.snowk49.android.fueler.R;
import com.snowk49.android.fueler.model.Car;
import com.snowk49.android.fueler.model.FuelerDbHelper;
import com.snowk49.android.fueler.presenter.CarListViewAdapter;
import com.snowk49.android.fueler.singleton.DatabaseFactory;
import com.snowk49.android.fueler.singleton.FragmentChanger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class AddCarFragment extends Fragment implements View.OnClickListener {

    public static final int MAXIMUM_FILE_NAME = 100;
    public static final int IMAGE_QUALITY = 100;

    private MainActivity context;
    private EditText carNameEditText;
    private ImageView carImageView;
    private Button changeCarImageButton;
    private CarListViewAdapter adapter;

    @Override
    public void onAttach(Context context) {
        this.context = (MainActivity) context;
        this.context.setTitle(R.string.add_car);

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_car_fragment, container, false);

        carNameEditText = (EditText) view.findViewById(R.id.car_name_edit_text);
        carImageView = (ImageView) view.findViewById(R.id.car_image_view);
        changeCarImageButton = (Button) view.findViewById(R.id.change_car_image_button);

        changeCarImageButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.action_done_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            String carName = carNameEditText.getText().toString();
            Uri localImage = moveImageToInteral(carImageView);

            if (carName.trim().length() > 0) {
                Car car = new Car(carName, localImage);
                FuelerDbHelper dbHelper = DatabaseFactory.getInstance();

                dbHelper.getCarTable().insert(car);

                adapter.fetchAll();
                FragmentChanger.getInstance().goBackFragment();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.change_car_image_button) {
            // TODO: Implementing take pho and choose photo here
        }
    }

    public void setAdapter(CarListViewAdapter adapter) {
        this.adapter = adapter;
    }

    Uri moveImageToInteral(ImageView imageView) {
        String fileName = getRandomString(MAXIMUM_FILE_NAME);
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        try {
            FileOutputStream imageOutputStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);

            bitmap.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY, imageOutputStream);
            imageOutputStream.close();

            return Uri.fromFile(new File(context.getFilesDir(), fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    String getRandomString(int length) {
        char[] chArray = new char[length];
        Random random = new Random();
        int range = 'Z' - 'A';
        boolean isCapital;

        for (int i = 0; i < length; i++) {
            isCapital = random.nextBoolean();
            int randomValue = random.nextInt(range);

            if (isCapital) {
                chArray[i] = (char) (randomValue + 'A');
            } else {
                chArray[i] = (char) (randomValue + 'a');
            }
        }

        return new String(chArray);
    }
}
