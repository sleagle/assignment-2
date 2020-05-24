package au.edu.utas.sddhewa.assignment.ui.edit;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import java.util.Date;

import au.edu.utas.sddhewa.assignment.R;
import au.edu.utas.sddhewa.assignment.db.table.RaffleTable;
import au.edu.utas.sddhewa.assignment.modal.Raffle;
import au.edu.utas.sddhewa.assignment.ui.DatePickerFragment;
import au.edu.utas.sddhewa.assignment.ui.FormInteraction;
import au.edu.utas.sddhewa.assignment.ui.alert.CustomDismissAlertDialog;
import au.edu.utas.sddhewa.assignment.ui.alert.CustomErrorDialog;
import au.edu.utas.sddhewa.assignment.ui.home.Home;
import au.edu.utas.sddhewa.assignment.util.AlertType;
import au.edu.utas.sddhewa.assignment.util.RaffleType;
import au.edu.utas.sddhewa.assignment.util.Utility;

public class EditRaffle extends Fragment implements FormInteraction {

    private View editRaffle;

    private Context context;
    private SQLiteDatabase db;
    private FragmentManager fragmentManager;
    private PackageManager packageManager;
    private final Raffle raffle;

    private String currentPhotoPath;

    private TextView name;
    private TextView description;
    private TextView raffleType;
    private TextView startDate;
    private TextView drawDate;
    private TextView prize;
    private TextView location;
    private TextView ticketPrice;
    private TextView numTickets;
    private TextView maxTickets;
    private CheckBox addCoverImage;
    private ImageView coverImage;


    public EditRaffle(Context context, SQLiteDatabase db, FragmentManager fragmentManager,
                        PackageManager packageManager, Bundle bundle) {
        // Required empty public constructor
        this.context = context;
        this.db = db;
        this.fragmentManager = fragmentManager;
        this.packageManager = packageManager;
        this.raffle = bundle.getParcelable(Utility.KEY_SELECTED_RAFFLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        editRaffle = inflater.inflate(R.layout.fragment_edit_raffle, container, false);

        final EditRaffle editRaffle = this;

        name = this.editRaffle.findViewById(R.id.txtName_edit);
        name.setText(raffle.getName());
        name.setEnabled(false);

        description = this.editRaffle.findViewById(R.id.txtDesc_edit);
        description.setText(raffle.getDescription());

        raffleType = this.editRaffle.findViewById(R.id.txtRaffleType_edit);
        raffleType.setText(raffle.getTypeId().name);
        raffleType.setEnabled(false);

        prize = this.editRaffle.findViewById(R.id.txtPrize_edit);
        prize.setText(String.valueOf(raffle.getPrize()));

        startDate = this.editRaffle.findViewById(R.id.txtStartDate_edit);
        startDate.setText(raffle.getStartingDateString());

        ticketPrice = this.editRaffle.findViewById(R.id.txTicketPrice_edit);
        ticketPrice.setText(String.valueOf(raffle.getTicketPrice()));

        if (raffle.getTicketsSold() > 0) {
            prize.setEnabled(false);
            startDate.setEnabled(false);
            ticketPrice.setEnabled(false);
        }

        drawDate = this.editRaffle.findViewById(R.id.txtCR_DrawDate_edit);
        drawDate.setText(raffle.getDrawDate());

        location = this.editRaffle.findViewById(R.id.txtLocation_edit);
        location.setText(raffle.getLocation());

        numTickets = this.editRaffle.findViewById(R.id.txtNumTickets_edit);
        numTickets.setText(String.valueOf(raffle.getNoOfTickets()));

        maxTickets = this.editRaffle.findViewById(R.id.txtMaxTickets_edit);
        maxTickets.setText(String.valueOf(raffle.getMaxTickets()));

        addCoverImage = this.editRaffle.findViewById(R.id.cbxCoverImage_edit);
        addCoverImage.setChecked(false);

        coverImage = this.editRaffle.findViewById(R.id.imgCover_edit);
        byte[] coverImageData = raffle.getRaffleCover();
        if (coverImageData != null) {
            addCoverImage.setChecked(true);
            Bitmap bitmap = BitmapFactory.decodeByteArray(coverImageData, 0, coverImageData.length);
            coverImage.setImageBitmap(bitmap);
        }


        Button startDateSelect = this.editRaffle.findViewById(R.id.btnCalander_edit);
        Button drawDateSelect = this.editRaffle.findViewById(R.id.btnCalanderD_edit);
        Button discard = this.editRaffle.findViewById(R.id.btnDiscard_edit);
        Button create = this.editRaffle.findViewById(R.id.btnCreate_edit);
        final Button camera = this.editRaffle.findViewById(R.id.btnTakeImage_edit);

        coverImage.setVisibility(View.INVISIBLE);
        camera.setVisibility(View.INVISIBLE);

        if (addCoverImage.isChecked()) {
            coverImage.setVisibility(View.VISIBLE);
            camera.setVisibility(View.VISIBLE);
        }

        addCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addCoverImage.isChecked()) {
                    coverImage.setVisibility(View.VISIBLE);
                    camera.setVisibility(View.VISIBLE);
                } else {

                    coverImage.setVisibility(View.INVISIBLE);
                    camera.setVisibility(View.INVISIBLE);
                }
            }
        });

        startDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(startDate, EditRaffle.this.editRaffle);
                newFragment.show(fragmentManager, "datePicker");
            }
        });

        drawDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(drawDate, EditRaffle.this.editRaffle);
                newFragment.show(fragmentManager, "datePicker");
            }
        });

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDismissAlertDialog fragment = new CustomDismissAlertDialog(editRaffle);
                fragment.show(fragmentManager, "alert");
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateForm()) {
                    createOrUpdateEntity();
                }
                else {
                    CustomErrorDialog errorDialog = new CustomErrorDialog(AlertType.CREATE_ERROR_RAFFLE);
                    errorDialog.show(fragmentManager, "error");
                }
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestToTakeAPicture();
            }
        });

        return this.editRaffle;
    }

    public void resetForm() {
        name.setText("");
        description.setText("");
        raffleType.setText("");
        startDate.setText("");
        drawDate.setText("");
        prize.setText("");
        location.setText("");
        ticketPrice.setText("");
        numTickets.setText("");
        maxTickets.setText("");
        addCoverImage.setChecked(false);

        fragmentManager.beginTransaction().
                replace(R.id.fragment_container,
                        new Home(db, fragmentManager, context)).commit();
    }

    public void createOrUpdateEntity() {

        try {

            raffle.setDescription(description.getText().toString());
            raffle.setStartingDate(startDate.getText().toString());
            raffle.setDrawDate(drawDate.getText().toString());
            raffle.setPrize(Float.parseFloat(prize.getText().toString()));
            raffle.setLocation(location.getText().toString());
            raffle.setTicketPrice(Float.parseFloat(ticketPrice.getText().toString()));
            raffle.setNoOfTickets(Integer.parseInt(numTickets.getText().toString()));
            raffle.setMaxTickets(Integer.parseInt(maxTickets.getText().toString()));

            if (addCoverImage.isChecked()) {
                Bitmap bitmap = ((BitmapDrawable) coverImage.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                byte[] imageInByte = stream.toByteArray();

                raffle.setRaffleCover(imageInByte);
            }

            if (RaffleTable.update(db, raffle) != -1) {
                Log.d("###### Update Raffle", "insert successful");
                Toast toast = Toast.makeText(context, R.string.update_raffle_success, Toast.LENGTH_LONG);
                resetForm();
                toast.show();
            } else {
                Log.d("###### Update Raffle", "update error");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
     * code snippets referring to Week 7 android lecture and android developer guide:
     * https://developer.android.com/guide/topics/media/camera
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Utility.REQUEST_IMAGE_CAPTURE) {
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

    /*
     * code snippets referring to Week 7 android lecture and android developer guide:
     * https://developer.android.com/guide/topics/media/camera
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Utility.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            ImageView myImageView = editRaffle.findViewById(R.id.imgCover_edit);
            setPic(myImageView);
        }
    }

    /*
     * code snippets referring to Week 7 android lecture and android developer guide:
     * https://developer.android.com/guide/topics/media/camera
     */
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
                startActivityForResult(takePictureIntent, Utility.REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Log.d("######", "takePictureIntent.resolveActivity(packageManager is null");
        }
    }

    /*
     * code snippets referring to Week 7 android lecture and android developer guide:
     * https://developer.android.com/guide/topics/media/camera
     */
    private void requestToTakeAPicture(){
        requestPermissions(new String[] { Manifest.permission.CAMERA }, Utility.REQUEST_IMAGE_CAPTURE);
    }

    /*
     * code snippets referring to Week 7 android lecture and android developer guide:
     * https://developer.android.com/guide/topics/media/camera
     */
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

    /*
     * code snippets referring to Week 7 android lecture and android developer guide:
     * https://developer.android.com/guide/topics/media/camera
     */
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

    private boolean validateForm() {

        Log.d("### name", name.getText().toString());
        return name.getText().toString().equals("") || description.getText().toString().equals("") ||
                startDate.getText().toString().equals("") || drawDate.getText().toString().equals("")
                || prize.getText().toString().equals("") || location.getText().toString().equals("")
                || ticketPrice.getText().toString().equals("") || numTickets.getText().toString().equals("")
                || maxTickets.getText().toString().equals("");
    }
}
