package au.edu.utas.sddhewa.assignment.ui.create.raffle;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTable;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.ui.CustomAlertDialog;
import au.edu.utas.sddhewa.assignment.ui.DatePickerFragment;
import au.edu.utas.sddhewa.assignment.ui.create.Create;
import au.edu.utas.sddhewa.assignment.ui.home.Home;
import au.edu.utas.sddhewa.assignment.util.RaffleType;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CreateRaffle extends Fragment implements Create {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private View createRaffle;

    private Context context;
    private SQLiteDatabase db;
    private FragmentManager fragmentManager;
    private PackageManager packageManager;

    private String currentPhotoPath;

    private TextView name;
    private TextView description;
    private Spinner raffleType;
    private TextView startDate;
    private TextView drawDate;
    private TextView location;
    private TextView ticketPrice;
    private Spinner numTickets;
    private TextView maxTickets;
    private CheckBox addCoverImage;
    private ImageView coverImage;


    public CreateRaffle(Context context, SQLiteDatabase db, FragmentManager fragmentManager,
                        PackageManager packageManager) {
        // Required empty public constructor
        this.context = context;
        this.db = db;
        this.fragmentManager = fragmentManager;
        this.packageManager = packageManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        createRaffle = inflater.inflate(R.layout.fragment_create_raffle, container, false);

        final CreateRaffle createRaffleObj = this;

        name = createRaffle.findViewById(R.id.txtName);
        description = createRaffle.findViewById(R.id.txtDesc);
        raffleType = createRaffle.findViewById(R.id.raffle_type_spinner);
        startDate = createRaffle.findViewById(R.id.txtStartDate);
        drawDate = createRaffle.findViewById(R.id.txtCR_DrawDate);
        location = createRaffle.findViewById(R.id.txtLocation);
        ticketPrice = createRaffle.findViewById(R.id.txTicketPrice);
        numTickets = createRaffle.findViewById(R.id.no_tickets_spinner);
        maxTickets = createRaffle.findViewById(R.id.txtMaxTickets);
        addCoverImage = createRaffle.findViewById(R.id.cbxCoverImage);

        Button startDateSelect = createRaffle.findViewById(R.id.btnCalander);
        Button drawDateSelect = createRaffle.findViewById(R.id.btnCalanderD);
        Button discard = createRaffle.findViewById(R.id.btnDiscard);
        Button create = createRaffle.findViewById(R.id.btnCreate);
        final Button camera = createRaffle.findViewById(R.id.btnTakeImage);

        final TextView lblCoverImage = createRaffle.findViewById(R.id.lblCoverImage);
        coverImage = createRaffle.findViewById(R.id.imgCover);

        coverImage.setVisibility(View.INVISIBLE);
        camera.setVisibility(View.INVISIBLE);
        lblCoverImage.setVisibility(View.INVISIBLE);

        final ArrayList<String> raffleTypes = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.raffle_types_array)));

        final ArrayAdapter<String> raffleTypesAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, raffleTypes);

        raffleType.setAdapter(raffleTypesAdapter);

        final ArrayList<String> numTickets = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.raffle_tickets_array)));

        final ArrayAdapter<String> numTicketsAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, numTickets);

        this.numTickets.setAdapter(numTicketsAdapter);

        addCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addCoverImage.isChecked()) {
                    lblCoverImage.setVisibility(View.VISIBLE);
                    coverImage.setVisibility(View.VISIBLE);
                    camera.setVisibility(View.VISIBLE);
                } else {
                    lblCoverImage.setVisibility(View.INVISIBLE);
                    coverImage.setVisibility(View.INVISIBLE);
                    camera.setVisibility(View.INVISIBLE);
                }
            }
        });

        startDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(startDate, createRaffle);
                newFragment.show(fragmentManager, "datePicker");
            }
        });

        drawDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(drawDate, createRaffle);
                newFragment.show(fragmentManager, "datePicker");
            }
        });

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialog fragment = new CustomAlertDialog(createRaffleObj);
                fragment.show(fragmentManager, "alert");
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEntity();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestToTakeAPicture();
            }
        });

        return createRaffle;
    }

    public void resetForm() {
        name.setText("");
        description.setText("");
        raffleType.setSelection(0);
        startDate.setText("");
        drawDate.setText("");
        location.setText("");
        ticketPrice.setText("");
        numTickets.setSelection(0);
        maxTickets.setText("");
        addCoverImage.setChecked(false);

        fragmentManager.beginTransaction().
                replace(R.id.fragment_container,
                        new Home(db, fragmentManager, context)).commit();
    }

    public void createEntity() {

        RaffleType type =
                raffleType.getSelectedItemId() == 0 ? RaffleType.NORMAL_RAFFLE : RaffleType.MARGIN_RAFFLE;

        try {
            Raffle raffle = new Raffle(name.getText().toString(), description.getText().toString(), type,
                    startDate.getText().toString(), drawDate.getText().toString(), true,
                    location.getText().toString(), Float.parseFloat(ticketPrice.getText().toString()),
                    Integer.parseInt(numTickets.getSelectedItem().toString()), 0,
                    Integer.parseInt(maxTickets.getText().toString()));

            if (addCoverImage.isChecked()) {
                Bitmap bitmap = ((BitmapDrawable) coverImage.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                byte imageInByte[] = stream.toByteArray();

                raffle.setRaffleCover(imageInByte);
            }

            if (RaffleTable.insert(db, raffle) != -1) {
                Log.d("###### Create Raffle", "insert successful");
                Toast toast = Toast.makeText(context, R.string.create_raffle_success, Toast.LENGTH_LONG);
                resetForm();
                toast.show();
            } else {
                Log.d("###### Create Raffle", "insert error");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay!
                Log.d("######", "gave permission");
                dispatchTakePictureIntent();
            } else {
                // permission denied, boo! }
                Log.d("######", "Permissions Denied");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            ImageView myImageView = createRaffle.findViewById(R.id.imgCover);
            setPic(myImageView);
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            Log.d("######", "takePictureIntent.resolveActivity(packageManager is NOT null");
            File photoFile = null;
            try {
                Log.d("######", "inside try catch");
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "au.edu.utas.sddhewa.assignment",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Log.d("######", "takePictureIntent.resolveActivity(packageManager is null");
        }
    }

    private void requestToTakeAPicture(){
        requestPermissions(new String[] { Manifest.permission.CAMERA }, REQUEST_IMAGE_CAPTURE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        Log.d("######", "in create image file");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic(ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }
}